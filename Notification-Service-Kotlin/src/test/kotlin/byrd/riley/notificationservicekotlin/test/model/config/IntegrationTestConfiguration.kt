package byrd.riley.notificationservicekotlin.test.model.config

import byrd.riley.notificationservicekotlin.model.*
import byrd.riley.notificationservicekotlin.test.model.QueueSender
import byrd.riley.notificationservicekotlin.test.model.TestSMSConfiguration
import org.springframework.amqp.core.Queue
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.test.TestRabbitTemplate
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean

@TestConfiguration
class IntegrationTestConfiguration {

    @Bean
    fun queue(): Queue = Queue(TestSMSConfiguration.QUEUE_NAME)

    @Bean
    fun texter(): Texter = TwilioTexter(TestSMSConfiguration())

    @Bean
    fun queueReceiver(): QueueReceiver = QueueReceiver(texter())

    @Bean
    fun testRabbitTemplate(connectionFactory: ConnectionFactory): TestRabbitTemplate = TestRabbitTemplate(connectionFactory)

    @Bean
    fun queueSender(connectionFactory: ConnectionFactory): QueueSender = QueueSender(testRabbitTemplate(connectionFactory))

}