package byrd.riley.notificationservicekotlin.model

interface SMSConfigurable {
    val accountSID: String
    val authToken: String
    val defaultSenderPhone: String
}