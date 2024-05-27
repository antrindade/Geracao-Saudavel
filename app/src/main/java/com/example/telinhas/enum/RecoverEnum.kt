package com.example.telinhas.enum

enum class RecoverEnum(val message: String) {
    EMAIL_SEND_SUCCESSFULLY("Email enviado com sucesso"),
    AUTH_INVALID_CREDENTIALS("O endereço de email está mal formatado."),
    NETWORK_EXCEPTION("Sem conexão com a Internet."),
    GENERATION_EXCEPTION("Ocorreu um erro ao enviar o email de redefinição de senha.")
}