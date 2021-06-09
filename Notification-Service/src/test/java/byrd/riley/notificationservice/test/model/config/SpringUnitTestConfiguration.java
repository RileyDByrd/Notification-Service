package byrd.riley.notificationservice.test.model.config;

import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import byrd.riley.notificationservice.model.QueueReceiver;
import byrd.riley.notificationservice.model.Texter;
import byrd.riley.notificationservice.test.model.QueueSender;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.test.TestRabbitTemplate;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;

import com.rabbitmq.client.Channel;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@TestConfiguration
@EnableRabbit
public class SpringUnitTestConfiguration {

    private AutoCloseable myMocks;

    @PostConstruct
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @PreDestroy
    public void tearDown() throws Exception {
        myMocks.close();
    }

	// Mock a connection factory such that it tells callers that the channel is always open.
	// We're mocking the connection factory so that the Rabbit Listeners and TestRabbitTemplate
	// don't throw exceptions when they can't connect to a nonexistent broker.
	// The @Mock annotation is preferred because it uses field names in stack traces.
	@Mock private ConnectionFactory factory;
	@Mock private Connection connection;
	@Mock private Channel channel;

    @Bean
    public ConnectionFactory connectionFactory() {
        doReturn(connection).when(factory).createConnection();
        doReturn(channel).when(connection).createChannel(anyBoolean());
        when(channel.isOpen()).thenReturn(true);
        return factory;
    }
    
    // Tell Spring Boot to use our fake connection factory for Rabbit Listeners. This
    // replaces the default bean.
    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory());
        return factory;
    }
    
    // Supply a mocked bean in the config class rather than a mock in the main test class of
    // Texter because QueueReceiver needs an instance of Texter.
    @MockBean
    public Texter texter;
	
    // Make a bean of QueueReceiver so that we may test its call of sendText (i.e. our actual code).
	@Bean
	public QueueReceiver queueReceiver() {
		return new QueueReceiver(texter);
	}
	
	// Make a bean of TestRabbitTemplate so that we may call our QueueReceiver but pretend
	// that we're adding something to the queue, which QueueReceiver is taking off.
	@Bean
	public TestRabbitTemplate testRabbitTemplate() {
		return new TestRabbitTemplate(connectionFactory());
	}
	
	@Bean
	public QueueSender queueSender() {
		return new QueueSender(testRabbitTemplate());
	}
}
