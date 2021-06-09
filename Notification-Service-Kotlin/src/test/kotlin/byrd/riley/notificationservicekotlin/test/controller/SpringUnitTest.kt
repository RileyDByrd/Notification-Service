package byrd.riley.notificationservicekotlin.test.controller

import byrd.riley.notificationservicekotlin.model.QueueReceiver
import byrd.riley.notificationservicekotlin.test.model.QueueSender
import byrd.riley.notificationservicekotlin.test.model.TestSMSConfiguration
import byrd.riley.notificationservicekotlin.model.Texter
import byrd.riley.notificationservicekotlin.test.model.config.SpringUnitTestConfiguration
import io.mockk.every
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import

@SpringBootApplication
@SpringBootTest(classes = [SpringUnitTest::class])
@Import(SpringUnitTestConfiguration::class)
class SpringUnitTest {

    @Autowired
    private lateinit var mockTexter: Texter

    @Autowired
    private lateinit var queueReceiver: QueueReceiver

    @Autowired
    private lateinit var queueSender: QueueSender

    // Have our Strings outside our method so that they may be assigned from an anonymous inner-type.
    var expectedMessage = "This message was passed as a parameter and sendText was successfully called once."
    var actualMessage = ""

    @Test
    fun rabbitMQCallsTexter() {
        // If the sendText method is called, set our actualMessage variable to the second parameter (i.e. argument 1).
        // expectedMessage should end up as the second parameter, so they should be equal.
        every { mockTexter.sendText(any(), any()) } answers {
            actualMessage = secondArg()
        }

        // Fake adding a message to the queue. This will be picked up (really received directly)
        // by our queueReceiver, which will call our sendText method.
        queueSender.send(TestSMSConfiguration.VALID_RECEIVER_NUMBER, expectedMessage)

        // Was our sendText method called once; was it called with the phone number we supplied;
        // and does the actual message that was used match the expected one (i.e. the one that we supplied)?
        verify { mockTexter.sendText(TestSMSConfiguration.VALID_RECEIVER_NUMBER, actualMessage) }
    }
}