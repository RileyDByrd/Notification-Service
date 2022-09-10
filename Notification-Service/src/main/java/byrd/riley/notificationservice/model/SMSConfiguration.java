package byrd.riley.notificationservice.model;

public class SMSConfiguration implements SMSConfigurable {
	
	private final String accountSid;
	private final String authToken;
	private final String defaultSenderPhone;
	
	public SMSConfiguration() {
		this(
			System.getenv("ACCOUNT_SID"),
			System.getenv("AUTH_TOKEN"),
			System.getenv("DEFAULT_SENDER_PHONE")
		);
	}

	public SMSConfiguration(String accountSID, String authToken, String defaultSenderPhone) {
		this.accountSid = accountSID;
		this.authToken = authToken;
		this.defaultSenderPhone = defaultSenderPhone;
	}

	
	@Override
	public String getAccountSID() {
		return accountSid;
	}

	@Override
	public String getAuthToken() {
		return authToken;
	}

	@Override
	public String getDefaultSenderPhone() {
		return defaultSenderPhone;
	}

}
