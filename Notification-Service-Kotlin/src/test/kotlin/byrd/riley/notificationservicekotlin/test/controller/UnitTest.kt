package byrd.riley.notificationservicekotlin.test.controller

import byrd.riley.notificationservicekotlin.model.SMSConfigurable
import byrd.riley.notificationservicekotlin.test.model.TestSMSConfiguration
import byrd.riley.notificationservicekotlin.model.Texter
import byrd.riley.notificationservicekotlin.model.TwilioTexter
import com.twilio.exception.ApiException
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

class UnitTest {

    private val config: SMSConfigurable = TestSMSConfiguration()
    private val texter: Texter = TwilioTexter(config)

    @Test
    fun passingSendText() {
        texter.sendText(TestSMSConfiguration.VALID_RECEIVER_NUMBER, "Test message.")
    }

    @Test
    fun failingSendText() {
        assertThrows(ApiException::class.java) { texter.sendText(TestSMSConfiguration.INVALID_RECEIVER_NUMBER, "Test message.") }
    }
}