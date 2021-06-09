package byrd.riley.notificationservicekotlin.test.model

import org.springframework.amqp.rabbit.test.TestRabbitTemplate

class QueueSender(private val testRabbitTemplate: TestRabbitTemplate) {

    fun send(receiverPhone: String, messageBody: String) {
        val message =
            "text\r\n" +
            "${receiverPhone}\r\n" +
            messageBody

        testRabbitTemplate.convertAndSend(TestSMSConfiguration.QUEUE_NAME, message)
        print(" [x] Sent '${message}'")
    }
}