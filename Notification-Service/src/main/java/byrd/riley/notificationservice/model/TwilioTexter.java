package byrd.riley.notificationservice.model;

import com.twilio.http.TwilioRestClient;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class TwilioTexter implements Texter {

	private final TwilioRestClient client;
	private final SMSConfigurable config;
	
	public TwilioTexter() { this(new SMSConfiguration()); }

	public TwilioTexter(SMSConfigurable config) {
		this.config = config;
		client = (new TwilioRestClient.Builder(
			this.config.getAccountSID(),
			this.config.getAuthToken()
		)).build();
	}
	
	@Override
	public void sendText(String receiver, String messageBody) {
		sendText(receiver, config.getDefaultSenderPhone(), messageBody);
	}

	@Override
	public void sendText(String receiver, String sender, String messageBody) {
		Message message = Message.creator(
			new PhoneNumber(receiver),
			new PhoneNumber(sender),
			messageBody
		).create(client);
		
		System.out.println("Error " + message.getErrorCode() + ": " + message.getErrorMessage());
	}
}
