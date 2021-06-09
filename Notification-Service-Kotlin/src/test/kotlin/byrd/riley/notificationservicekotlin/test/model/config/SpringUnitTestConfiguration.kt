package byrd.riley.notificationservicekotlin.test.model.config

import byrd.riley.notificationservicekotlin.model.QueueReceiver
import byrd.riley.notificationservicekotlin.test.model.QueueSender
import byrd.riley.notificationservicekotlin.model.Texter
import com.ninjasquad.springmockk.MockkBean
import com.rabbitmq.client.Channel
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.springframework.amqp.rabbit.annotation.EnableRabbit
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory
import org.springframework.amqp.rabbit.connection.Connection
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.test.TestRabbitTemplate
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import javax.annotation.PostConstruct

@TestConfiguration
@EnableRabbit
class SpringUnitTestConfiguration {

    @PostConstruct
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
    }

    // Mock a connection factory such that it tells callers that the channel is always open.
    // We're mocking the connection factory so that the Rabbit Listeners and TestRabbitTemplate
    // don't throw exceptions when they can't connect to a nonexistent broker.
    // The @MockK annotation is preferred because it uses field names in stack traces.
    @MockK lateinit var factory: ConnectionFactory
    @MockK lateinit var connection: Connection
    @MockK lateinit var channel: Channel

    @Bean
    fun connectionFactory(): ConnectionFactory {
        every { factory.createConnection() } returns connection
        every { connection.createChannel(any()) } returns channel
        every { channel.isOpen } returns true

        return factory
    }

    // Tell Spring Boot to use our fake connection factory for Rabbit Listeners. This
    // replaces the default bean.
    @Bean
    fun rabbitListenerContainerFactory(): SimpleRabbitListenerContainerFactory =
        SimpleRabbitListenerContainerFactory().apply {
            setConnectionFactory(connectionFactory())
        }

    // Supply a mocked bean in the config class rather than a mock in the main test class of
    // Texter because QueueReceiver needs an instance of Texter.
    @MockkBean
    lateinit var texter: Texter

    // Make a bean of QueueReceiver so that we may test its call of sendText (i.e. our actual code).
    @Bean
    fun queueReceiver(): QueueReceiver = QueueReceiver(texter)

    // Make a bean of TestRabbitTemplate so that we may call our QueueReceiver but pretend
    // that we're adding something to the queue, which QueueReceiver is taking off.
    @Bean
    fun testRabbitTemplate(): TestRabbitTemplate = TestRabbitTemplate(connectionFactory())

    @Bean
    fun queueSender(): QueueSender = QueueSender(testRabbitTemplate())
}