package byrd.riley.notificationservice.test.controller;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;

import byrd.riley.notificationservice.model.QueueReceiver;
import byrd.riley.notificationservice.test.model.QueueSender;
import byrd.riley.notificationservice.test.model.TestNotificationConfiguration;
import byrd.riley.notificationservice.test.model.config.SpringUnitTestConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import byrd.riley.notificationservice.model.Notifier;

@SpringBootApplication
@SpringBootTest(classes = SpringUnitTest.class)
//@RabbitListenerTest
@Import(SpringUnitTestConfiguration.class)
public class SpringUnitTest {
	
	@Autowired
	public Notifier mockNotifier;
	
	@Autowired
	public QueueReceiver queueReceiver;

	// @RabbitListenerTest at the class level and RabbitListenerTestHarness are to be used together if you want to use them.
//	@Autowired
//	public RabbitListenerTestHarness testListener;
	
	@Autowired	
	private QueueSender queueSender;
	
	
	// Have our Strings outside our method so that they may be assigned from an anonymous inner-type.
	String expectedMessage = "This message was passed as a parameter and sendText was successfully called once.";
	String actualMessage = "";
    
    @Test
    void rabbitMQCallsNotifier() {
    	// If the sendText method is called, set our actualMessage variable to the second parameter (i.e. argument 1).
    	// expectedMessage should end up as the second parameter, so they should be equal.
    	doAnswer(invocation -> {
    		actualMessage = invocation.getArgument(1);
    		
    		return null;
    	}).when(mockNotifier).sendText(anyString(), anyString());
    	
    	// Fake adding a message to the queue. This will be picked up (really received directly)
    	// by our queueReceiver, which will call our sendText method.
    	queueSender.send(TestNotificationConfiguration.VALID_RECEIVER_NUMBER, expectedMessage);
    	
    	// Was our sendText method called once; was it called with the phone number we supplied;
    	// and does the actual message that was used match the expected one (i.e. the one that we supplied)?
    	verify(mockNotifier).sendText(TestNotificationConfiguration.VALID_RECEIVER_NUMBER, actualMessage);
    }
}
