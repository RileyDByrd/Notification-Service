package byrd.riley.notificationservice.test.controller;

import static org.junit.jupiter.api.Assertions.assertThrows;

import byrd.riley.notificationservice.test.model.TestNotificationConfiguration;
import org.junit.jupiter.api.Test;

import com.twilio.exception.ApiException;

import byrd.riley.notificationservice.model.Notifier;

class UnitTest {
    
	@Test
	void passingSendText() throws Exception {
		TestNotificationConfiguration config = new TestNotificationConfiguration();
		Notifier notifier = new Notifier(config);
		notifier.sendText(TestNotificationConfiguration.VALID_RECEIVER_NUMBER, "Test message.");
	}

	@Test
	void failingSendText() throws Exception {
		TestNotificationConfiguration config = new TestNotificationConfiguration();
		Notifier notifier = new Notifier(config);
		assertThrows(ApiException.class, () -> notifier.sendText(TestNotificationConfiguration.INVALID_RECEIVER_NUMBER, "Test message."));
	}
}
