package nonabili.chatserviceserver.util.error

class CustomError(val reason: ErrorState): RuntimeException(reason.message)