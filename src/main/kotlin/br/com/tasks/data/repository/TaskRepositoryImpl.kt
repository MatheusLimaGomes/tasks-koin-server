package br.com.tasks.data.repository

import br.com.tasks.core.domain.data.repository.TaskRepository
import br.com.tasks.core.domain.model.Task
import br.com.tasks.data.request.UpdateTaskRequest
import br.com.tasks.utils.extensions.Constants.TASKS_COLLECTION
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq
import org.litote.kmongo.setValue

class TaskRepositoryImpl constructor(
    database: CoroutineDatabase
): TaskRepository {
    private  val tasksCollection = database.getCollection<Task>(TASKS_COLLECTION)

    override suspend fun getTasks(): List<Task> = tasksCollection.find().toList()

    override suspend fun getTaskById(id: String): Task? = tasksCollection.findOne(id)

    override suspend fun insert(task: Task): Boolean = tasksCollection.insertOne(task).wasAcknowledged()

    override suspend fun update(id: String, updateTaskRequest: UpdateTaskRequest, currentTask: Task): Boolean {
        return tasksCollection.updateOneById(
            id = id,
            update = Task(
                id = currentTask.id,
                title = updateTaskRequest.title,
                description = updateTaskRequest.description,
                priority = updateTaskRequest.priority,
                dueDate = currentTask.dueDate,
                completed = currentTask.completed,
                createAt = currentTask.createAt
            )
        ).wasAcknowledged()
    }

    override suspend fun delete(id: String): Boolean = tasksCollection.deleteOneById(id).wasAcknowledged()

    override suspend fun completeTask(id: String): Long {
        val updateResult = tasksCollection.updateOne(Task::id eq id, setValue(Task::completed, true))
        return updateResult.modifiedCount
    }

}