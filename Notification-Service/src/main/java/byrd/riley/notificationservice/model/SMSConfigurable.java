package byrd.riley.notificationservice.model;

public interface SMSConfigurable {
	
	String getAccountSID();
	
	String getAuthToken();
	
	String getDefaultSenderPhone();
	
}
