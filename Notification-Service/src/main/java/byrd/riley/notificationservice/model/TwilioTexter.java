package byrd.riley.notificationservice.model;

import com.twilio.http.TwilioRestClient;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class TwilioTexter implements Texter {

	private final TwilioRestClient CLIENT;
	private final SMSConfigurable CONFIG;
	
	public TwilioTexter() { this(new SMSConfiguration()); }

	public TwilioTexter(SMSConfigurable config) {
		CONFIG = config;
		CLIENT = (new TwilioRestClient.Builder(
			CONFIG.getAccountSID(),
			CONFIG.getAuthToken()
		)).build();
	}
	
	@Override
	public void sendText(String receiver, String messageBody) {
		sendText(receiver, CONFIG.getDefaultSenderPhone(), messageBody);
	}

	@Override
	public void sendText(String receiver, String sender, String messageBody) {
		Message message = Message.creator(
			new PhoneNumber(receiver),
			new PhoneNumber(sender),
			messageBody
		).create(CLIENT);
		
		System.out.println("Error " + message.getErrorCode() + ": " + message.getErrorMessage());
	}
}
