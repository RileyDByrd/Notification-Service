package byrd.riley.notificationservicekotlin.model

import org.springframework.amqp.rabbit.annotation.RabbitHandler
import org.springframework.amqp.rabbit.annotation.RabbitListener

@RabbitListener(queues = ["task_queue"], id = "mainListener")
class QueueReceiver(private val texter: Texter?, private val emailer: Emailer?) {

    constructor(texter: Texter?): this(texter, null)
    constructor(emailer: Emailer?): this(null, emailer)

    @RabbitHandler
    fun receive(`in`: String) {
        print(" [x] Received '${`in`}'")

        val messageContents: List<String> = `in`.split("\r\n")

        if(messageContents[0].equals("email", true)) {
            val email: String = messageContents[1]
            val messageBody: String = messageContents[2]
            emailer?.sendEmail(email, "My Email", messageBody)
        } else if(messageContents[0].equals("text", true)) {
            val number: String = messageContents[1]
            val messageBody: String = messageContents[2]
            texter?.sendText(number, messageBody)
        }

        print(" [x] Done")
    }
}