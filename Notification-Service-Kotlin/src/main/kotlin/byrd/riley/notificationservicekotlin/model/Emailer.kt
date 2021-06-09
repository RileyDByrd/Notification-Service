package byrd.riley.notificationservicekotlin.model

import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender

class Emailer(private val emailSender: JavaMailSender) {
    fun sendEmail(receiver: String, subject: String, messageBody: String) {
        val message = SimpleMailMessage().apply {
            setFrom("noreply@example.com")
            setTo(receiver)
            setSubject(subject)
            setText(messageBody)
        }

        emailSender.send(message)
    }
}