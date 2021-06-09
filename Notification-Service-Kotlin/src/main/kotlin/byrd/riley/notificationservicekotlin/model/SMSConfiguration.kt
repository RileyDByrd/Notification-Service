package byrd.riley.notificationservicekotlin.model

class SMSConfiguration: SMSConfigurable {
    override val accountSID: String
    override val authToken: String
    override val defaultSenderPhone: String

    constructor() {
        accountSID = System.getenv("ACCOUNT_SID")
        authToken = System.getenv("AUTH_TOKEN")
        defaultSenderPhone = System.getenv("DEFAULT_SENDER_PHONE")
    }

    constructor(accountSID: String, authToken: String, defaultSenderPhone: String) {
        this.accountSID = accountSID
        this.authToken = authToken
        this.defaultSenderPhone = defaultSenderPhone
    }
}