package byrd.riley.notificationservice.test.model.config;

import byrd.riley.notificationservice.model.QueueReceiver;
import byrd.riley.notificationservice.test.model.QueueSender;
import byrd.riley.notificationservice.test.model.TestNotificationConfiguration;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.test.TestRabbitTemplate;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import byrd.riley.notificationservice.model.Notifier;

@TestConfiguration
public class IntegrationTestConfiguration {
	
	@Bean
	public Queue queue() {
		return new Queue(TestNotificationConfiguration.QUEUE_NAME);
	}
	
	@Bean
	public Notifier notifier() throws Exception {
		return new Notifier(new TestNotificationConfiguration());
	}
	
	@Bean
	public QueueReceiver queueReceiver() throws Exception {
		return new QueueReceiver(notifier());
	}
	
	@Bean
	public TestRabbitTemplate testRabbitTemplate(ConnectionFactory connectionFactory) {
		return new TestRabbitTemplate(connectionFactory);
	}
	
	@Bean
	public QueueSender queueSender(ConnectionFactory connectionFactory) {
		return new QueueSender(testRabbitTemplate(connectionFactory));
	}
}
