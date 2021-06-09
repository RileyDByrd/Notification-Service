package byrd.riley.notificationservice.test.model.config;

import byrd.riley.notificationservice.model.QueueReceiver;
import byrd.riley.notificationservice.model.Texter;
import byrd.riley.notificationservice.test.model.QueueSender;
import byrd.riley.notificationservice.test.model.TestSMSConfiguration;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.test.TestRabbitTemplate;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import byrd.riley.notificationservice.model.TwilioTexter;

@TestConfiguration
public class IntegrationTestConfiguration {
	
	@Bean
	public Queue queue() {
		return new Queue(TestSMSConfiguration.QUEUE_NAME);
	}
	
	@Bean
	public Texter texter() throws Exception {
		return new TwilioTexter(new TestSMSConfiguration());
	}
	
	@Bean
	public QueueReceiver queueReceiver() throws Exception {
		return new QueueReceiver(texter());
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
