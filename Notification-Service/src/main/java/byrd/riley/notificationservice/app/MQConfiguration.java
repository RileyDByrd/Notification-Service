package byrd.riley.notificationservice.app;

import byrd.riley.notificationservice.model.Emailer;
import byrd.riley.notificationservice.model.QueueReceiver;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import byrd.riley.notificationservice.model.Notifier;

@Configuration
public class MQConfiguration {

	@Bean
	public Queue queue() {
		return new Queue("task_queue");
	}
	
	@Bean
	public Notifier notifier() {
		return new Notifier();
	}
	
	@Bean
	public JavaMailSender javaMailSender() {
		return new JavaMailSenderImpl();
	}
	
	@Bean
	public Emailer emailer() {
		return new Emailer(javaMailSender());
	}
	
	@Bean
	public QueueReceiver queueReceiver() {
		return new QueueReceiver(notifier(), emailer());
	}
}
