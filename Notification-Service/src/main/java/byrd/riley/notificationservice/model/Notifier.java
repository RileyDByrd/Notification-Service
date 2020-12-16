package byrd.riley.notificationservice.model;

import com.twilio.http.TwilioRestClient;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class Notifier implements Notifying {

	private final TwilioRestClient CLIENT;
	private final NotificationConfigurable CONFIG;
	
	public Notifier() { this(new NotificationConfiguration()); }

	public Notifier(NotificationConfigurable config) {
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
