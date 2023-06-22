package com.example.telinhas.constants

class GenerationConstants {

    object Project {
        const val TITLE = "Geração Saudável"
    }
    
    object ShearedPreference {
        const val NAME = "userShared"
    }

    object Biometric {
        const val SUBTITLE = "Use sua biometria cadastrada"
        const val DESCRIPTION = "Caso não consiga, cancele e faça o login por email e senha"
        const val BUTTON_TEXT = "Cancelar"
    }

    object Reminder {
        const val NOTIFY = "Hora de beber água!"
        const val ID = "generation"
        const val NAME = "generationReminderChannel"
        const val DESCRIPTION = "Generation for Alarm Manager"
        const val SELECT_THE_TIME = "Seleciona a hora do alarme"
        const val REMINDER_ACTIVATED = "Lembrete ativado com sucesso!"

    }

    object User {
        const val NAME = "Nome"
        const val EMAIL = "Email"
        const val PROGRESSION = "Hoje você ainda tem que beber:"
        const val LEVEL_COMPLETED = "Você atingiu seu objetivo, parabens!"
        const val KILOGRAM = "Quilograma"
    }

    object Firebase {
        const val COLLECTION = "Usuários"
        const val AMOUNT_OF_WATER = "Quantidade de água"
        const val DRANK = "Quantidade que bebeu hoje"
        const val SUM = "Soma da quantidade de água"
    }

    object Success {
        const val SUCCESSFULLY_LOGGED_IN = "Logado com sucesso!"
        const val SUCCESSFULLY_REGISTERED = "Usuário registrado com sucesso!"
        const val EMAIL_SEND_SUCCESSFULLY = "Email enviado com sucesso"
    }

    object Exception {
        const val EMPTY_FIELD = "Preencha todos os campos"
        const val MINIMUM_PASSWORD = "Digite uma senha com no minimo 6 caractere"
        const val VALID_EMAIL = "Email invalido!"
        const val ALREADY_REGISTERED = "Email já cadastrado!"
        const val NO_CONNECTION = "Sem conexão com a internet"
        const val ERROR = "Erro ao entrar com usuario"
    }

    object Calculation {
        const val WALTER_MULTIPLICATION = 35
    }
}