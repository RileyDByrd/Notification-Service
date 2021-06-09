package byrd.riley.notificationservicekotlin.test.model

import byrd.riley.notificationservicekotlin.model.SMSConfigurable

class TestSMSConfiguration: SMSConfigurable {

    companion object {
        const val VALID_RECEIVER_NUMBER = "+18778894546"
        const val INVALID_RECEIVER_NUMBER = "+15005550001"
        const val QUEUE_NAME = "task_queue"
    }

    override val accountSID: String = System.getenv("TEST_ACCOUNT_SID")
    override val authToken: String = System.getenv("TEST_AUTH_TOKEN")
    override val defaultSenderPhone: String = "+15005550006"
}