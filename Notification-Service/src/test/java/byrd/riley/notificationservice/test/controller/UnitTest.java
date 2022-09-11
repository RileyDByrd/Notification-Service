package byrd.riley.notificationservice.test.controller;

import static org.junit.jupiter.api.Assertions.assertThrows;

import byrd.riley.notificationservice.model.SMSConfigurable;
import byrd.riley.notificationservice.model.Texter;
import byrd.riley.notificationservice.test.model.TestSMSConfiguration;
import org.junit.jupiter.api.Test;

import com.twilio.exception.ApiException;

import byrd.riley.notificationservice.model.TwilioTexter;

class UnitTest {

	private SMSConfigurable config = new TestSMSConfiguration();
	private Texter texter = new TwilioTexter(config);

	@Test
	void passingSendText() {
		texter.sendText(TestSMSConfiguration.VALID_RECEIVER_NUMBER, "Test message.");
	}

	@Test
	void failingSendText() {
		assertThrows(ApiException.class, () -> texter.sendText(TestSMSConfiguration.INVALID_RECEIVER_NUMBER, "Test message."));
	}
}
