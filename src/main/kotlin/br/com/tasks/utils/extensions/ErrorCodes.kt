package br.com.tasks.utils.extensions

enum class ErrorCodes(val message:String) {
    REGISTER_TASK("Erro ao cadastrar a tarefa"),
    UPDATE_TASK("Erro ao atualizar a tarefa"),
    DELETE_TASK("Erro ao deletar a tarefa"),
    TASK_NOT_FOUND("Nenhuma tarefa com este id foi encontrada"),
    EMPTY_FIELDS("Preencha todos os campos"),
    COMPLETE_TASK("Erro ao concluir a tarefa")
}