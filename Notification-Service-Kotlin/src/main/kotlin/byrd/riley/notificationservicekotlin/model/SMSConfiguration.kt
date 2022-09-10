package byrd.riley.notificationservicekotlin.model

class SMSConfiguration(
    override val accountSID: String,
    override val authToken: String,
    override val defaultSenderPhone: String
): SMSConfigurable {

    constructor(): this(
        System.getenv("ACCOUNT_SID"),
        System.getenv("AUTH_TOKEN"),
        System.getenv("DEFAULT_SENDER_PHONE")
    )

}