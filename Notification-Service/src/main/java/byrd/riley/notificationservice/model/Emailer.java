package byrd.riley.notificationservice.model;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class Emailer {

	private final JavaMailSender emailSender;
	
	public Emailer(JavaMailSender emailSender) {
		this.emailSender = emailSender;
	}
	
	public void sendEmail(String receiver, String subject, String messageBody) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("noreply@techbill.org");
		message.setTo(receiver);
		message.setSubject(subject);
		message.setText(messageBody);
		emailSender.send(message);
	}
}
