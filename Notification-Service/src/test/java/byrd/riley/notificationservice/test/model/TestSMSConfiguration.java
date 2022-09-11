package byrd.riley.notificationservice.test.model;

import byrd.riley.notificationservice.model.SMSConfigurable;

public class TestSMSConfiguration implements SMSConfigurable {

	public static final String VALID_RECEIVER_NUMBER = "+18778894546";
    public static final String INVALID_RECEIVER_NUMBER = "+15005550001";
    public static final String QUEUE_NAME = "task_queue";

	private final static String ACCOUNT_SID = System.getenv("TEST_ACCOUNT_SID");
	private final static String AUTH_TOKEN = System.getenv("TEST_AUTH_TOKEN");
	private final static String DEFAULT_SENDER_PHONE = "+15005550006";

	public TestSMSConfiguration() {
		if(ACCOUNT_SID.isBlank())
			throw new RuntimeException("You forgot to set the TWILIO_TEST_ACCOUNT_SID environment variable.");
		if(AUTH_TOKEN.isBlank())
			throw new RuntimeException("You forgot to set the TWILIO_TEST_AUTH_TOKEN environment variable.");
		if(DEFAULT_SENDER_PHONE.isBlank())
			throw new RuntimeException("You forgot to set the TWILIO_TEST_DEFAULT_SENDER_PHONE variable.");
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
