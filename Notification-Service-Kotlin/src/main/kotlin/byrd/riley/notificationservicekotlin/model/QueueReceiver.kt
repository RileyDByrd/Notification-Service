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

        val messageType: String = messageContents[0]
        val receiver: String = messageContents[1]
        val messageBody: String = messageContents[2]

        if(messageType.equals("email", true))
            emailer?.sendEmail(receiver, "My Email", messageBody)
        else if(messageType.equals("text", true))
            texter?.sendText(receiver, messageBody)

        print(" [x] Done")
    }
}