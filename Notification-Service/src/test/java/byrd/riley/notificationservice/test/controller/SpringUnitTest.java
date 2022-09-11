package byrd.riley.notificationservice.test.controller;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;

import byrd.riley.notificationservice.model.Texter;
import byrd.riley.notificationservice.test.model.QueueSender;
import byrd.riley.notificationservice.test.model.TestSMSConfiguration;
import byrd.riley.notificationservice.test.model.config.SpringUnitTestConfiguration;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@SpringBootTest(classes = SpringUnitTest.class)
@Import(SpringUnitTestConfiguration.class)
public class SpringUnitTest {

	@Autowired
	private Texter mockTexter;

	@Autowired
	private QueueSender queueSender;

	// Have our Strings outside our method so that they may be assigned from an anonymous inner-type.
	private final String expectedMessage = "This message was passed as a parameter and sendText was successfully called once.";
	private String actualMessage = "";
    
    @Test
    void rabbitMQCallsTexter() {
    	// If the sendText method is called, set our actualMessage variable to the second parameter (i.e. argument 1).
    	// expectedMessage should end up as the second parameter, so they should be equal.
    	doAnswer(invocation -> {
    		actualMessage = invocation.getArgument(1);
    		
    		return null;
    	}).when(mockTexter).sendText(anyString(), anyString());
    	
    	// Fake adding a message to the queue. This will be picked up (really received directly)
    	// by our queueReceiver, which will call our sendText method.
    	queueSender.send(TestSMSConfiguration.VALID_RECEIVER_NUMBER, expectedMessage);
    	
    	// Was our sendText method called once; was it called with the phone number we supplied;
    	// and does the actual message that was used match the expected one (i.e. the one that we supplied)?
    	verify(mockTexter).sendText(TestSMSConfiguration.VALID_RECEIVER_NUMBER, actualMessage);
    }
}
