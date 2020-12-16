package byrd.riley.notificationservice.model;

public class NotificationConfiguration implements NotificationConfigurable {
	
	private final String ACCOUNT_SID;
	private final String AUTH_TOKEN;
	private final String DEFAULT_SENDER_PHONE;
	
	public NotificationConfiguration() {
		ACCOUNT_SID = System.getenv("ACCOUNT_SID");
		AUTH_TOKEN = System.getenv("AUTH_TOKEN");
		DEFAULT_SENDER_PHONE = System.getenv("DEFAULT_SENDER_PHONE");
	}

	public NotificationConfiguration(String accountSID, String authToken, String defaultSenderPhone) {
		this.ACCOUNT_SID = accountSID;
		this.AUTH_TOKEN = authToken;
		this.DEFAULT_SENDER_PHONE = defaultSenderPhone;
	}

	
	@Override
	public String getAccountSID() {
		return ACCOUNT_SID;
	}

	@Override
	public String getAuthToken() {
		return AUTH_TOKEN;
	}

	@Override
	public String getDefaultSenderPhone() {
		return DEFAULT_SENDER_PHONE;
	}

}
