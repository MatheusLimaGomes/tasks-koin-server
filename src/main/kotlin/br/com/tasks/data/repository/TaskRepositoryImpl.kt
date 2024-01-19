package br.com.tasks.data.repository

import br.com.tasks.core.domain.data.repository.TaskRepository
import br.com.tasks.core.domain.model.Task
import br.com.tasks.data.request.UpdateTaskRequest
import br.com.tasks.utils.extensions.Constants.TASKS_COLLECTION
import com.mongodb.client.model.Filters
import com.mongodb.client.model.Updates
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.toList

class TaskRepositoryImpl (
    database: MongoDatabase
): TaskRepository {
    private  val tasksCollection = database.getCollection<Task>(TASKS_COLLECTION)

    override suspend fun getTasks(): List<Task> = tasksCollection.find().toList()

    override suspend fun getTaskById(id: String): Task? = tasksCollection.find(Filters.eq(Task:: _id.name, id)).firstOrNull()

    override suspend fun insert(task: Task): Boolean = tasksCollection.insertOne(task).wasAcknowledged()

    override suspend fun update(id: String, updateTaskRequest: UpdateTaskRequest, currentTask: Task): Boolean {
        val result = tasksCollection.updateOne(
            filter = Filters.eq(Task:: _id.name, id),
            update = listOf(
                Updates.set(Task::title.name, updateTaskRequest.title),
                Updates.set(Task::description.name, updateTaskRequest.description),
                Updates.set(Task::priority.name, updateTaskRequest.priority)
            )
        )
        return result.modifiedCount > 0
    }

    override suspend fun delete(id: String): Boolean {
      val result = tasksCollection.deleteOne(Filters.eq(Task::_id.name, id))
      return result.deletedCount > 0
    }
    override suspend fun completeTask(id: String): Long {
        val updateResult = tasksCollection.updateOne(
            filter = Filters.eq(Task::_id.name, id),
            update = listOf(
                Updates.set(Task::completed.name, true)
            )
        )
        return updateResult.modifiedCount
    }

}