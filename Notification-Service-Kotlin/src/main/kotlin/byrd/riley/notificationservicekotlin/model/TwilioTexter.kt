package byrd.riley.notificationservicekotlin.model

import com.twilio.http.TwilioRestClient
import com.twilio.rest.api.v2010.account.Message
import com.twilio.type.PhoneNumber

class TwilioTexter(private val config: SMSConfigurable) : Texter {
    private val client = TwilioRestClient.Builder(
        config.accountSID,
        config.authToken
    ).build()

    constructor(): this(SMSConfiguration())

    override fun sendText(receiver: String, messageBody: String) {
        sendText(receiver, config.defaultSenderPhone, messageBody)
    }

    override fun sendText(receiver: String, sender: String, messageBody: String) {
        val message = Message.creator(
            PhoneNumber(receiver),
            PhoneNumber(sender),
            messageBody
        ).create(client)

        print("Error " + message.errorCode + ": " + message.errorMessage)
    }

}