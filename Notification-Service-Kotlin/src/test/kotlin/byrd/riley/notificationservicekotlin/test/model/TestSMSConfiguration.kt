package byrd.riley.notificationservicekotlin.test.model

import byrd.riley.notificationservicekotlin.model.SMSConfigurable
import kotlin.RuntimeException

class TestSMSConfiguration: SMSConfigurable {

    companion object {
        const val VALID_RECEIVER_NUMBER = "+18778894546"
        const val INVALID_RECEIVER_NUMBER = "+15005550001"
        const val QUEUE_NAME = "task_queue"
    }

    override val accountSID: String = System.getenv("TEST_ACCOUNT_SID")
        ?: throw RuntimeException("You forgot to set the TWILIO_TEST_ACCOUNT_SID environment variable.")
    override val authToken: String = System.getenv("TEST_AUTH_TOKEN")
        ?: throw RuntimeException("You forgot to set the TWILIO_TEST_AUTH_TOKEN environment variable.")
    override val defaultSenderPhone: String = "+15005550006"
}