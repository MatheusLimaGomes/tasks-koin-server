package br.com.tasks.di

import br.com.tasks.core.domain.data.repository.TaskRepository
import br.com.tasks.core.domain.data.service.TaskService
import br.com.tasks.core.domain.usecase.ValidateCreateTaskRequest
import br.com.tasks.core.domain.usecase.ValidateCreateTaskRequestImpl
import br.com.tasks.core.domain.usecase.ValidateUpdateTaskRequestImpl
import br.com.tasks.core.domain.usecase.ValidateUpdateTaskRequest
import br.com.tasks.data.repository.TaskRepositoryImpl
import br.com.tasks.data.service.TaskServiceImpl
import br.com.tasks.utils.extensions.Constants.MONGODB_URI_LOCAL
import br.com.tasks.utils.extensions.Constants.LOCAL_DB__NAME
import com.mongodb.kotlin.client.coroutine.MongoClient
import org.koin.dsl.module

val databaseModule = module {
    single {
        val client = MongoClient.create(connectionString = MONGODB_URI_LOCAL)
        client.getDatabase(LOCAL_DB__NAME)
    }
}
val repositoryModule = module {
    single<TaskRepository> {
        TaskRepositoryImpl(get())
    }
}
val serviceModule = module {
    single<TaskService> {
        TaskServiceImpl(get(), get(),get())
    }
}
val useCaseModule = module {
    single<ValidateCreateTaskRequest> {
        ValidateCreateTaskRequestImpl()
    }
    single<ValidateUpdateTaskRequest> {
        ValidateUpdateTaskRequestImpl()
    }
}