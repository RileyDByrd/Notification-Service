package byrd.riley.notificationservice.test.controller;

import static org.junit.jupiter.api.Assertions.assertThrows;

import byrd.riley.notificationservice.test.model.EmbeddedAMQPBroker;
import byrd.riley.notificationservice.test.model.QueueSender;
import byrd.riley.notificationservice.test.model.TestSMSConfiguration;
import byrd.riley.notificationservice.test.model.config.IntegrationTestConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.amqp.rabbit.support.ListenerExecutionFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@SpringBootTest(classes = IntegrationTest.class)
@Import(IntegrationTestConfiguration.class)
public class IntegrationTest {

	@RegisterExtension
	static final EmbeddedAMQPBroker BROKER = new EmbeddedAMQPBroker();
	
	@Autowired
	private QueueSender queueSender;
	
	
	@Test
	void validSendAndReceiveThenText() {
		queueSender.send(TestSMSConfiguration.VALID_RECEIVER_NUMBER, "This should be the passing integration test message body.");
	}
	
	@Test
	void invalidSendAndReceiveThenText() {
		// Really, a com.twilio.exception.ApiException is being thrown, but our listener is encountering it and in turn throwing an exception of its own.
		assertThrows(ListenerExecutionFailedException.class, () -> queueSender.send(TestSMSConfiguration.INVALID_RECEIVER_NUMBER, "This should be the failing integration test message body."));
	}
}
