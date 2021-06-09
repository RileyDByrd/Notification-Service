package byrd.riley.notificationservicekotlin.app

import byrd.riley.notificationservicekotlin.model.Emailer
import byrd.riley.notificationservicekotlin.model.QueueReceiver
import byrd.riley.notificationservicekotlin.model.Texter
import byrd.riley.notificationservicekotlin.model.TwilioTexter
import org.springframework.amqp.core.Queue
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl

@Configuration
class MQConfiguration {

    @Bean
    fun queue(): Queue = Queue("task_queue")

    @Bean
    fun texter(): Texter = TwilioTexter()

    @Bean
    fun javaMailSender(): JavaMailSender = JavaMailSenderImpl()

    @Bean
    fun emailer(): Emailer = Emailer(javaMailSender())

    @Bean
    fun queueReceiver(): QueueReceiver = QueueReceiver(texter(), emailer())
}