package br.com.tasks.utils.extensions

enum class SuccessCodes(val message: String) {

    REGISTER_TASK("Sucesso ao cadastrar a tarefa"),
    UPDATE_TASK("Sucesso ao atualizar a tarefa"),
    DELETE_TASK("Sucesso ao deletar a tarefa"),
    COMPLETE_TASK("Sucesso ao concluir a tarefa")
}