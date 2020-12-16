package byrd.riley.notificationservice.model;

public interface Notifying {

	void sendText(String receiver, String messageBody);
	
	void sendText(String receiver, String sender, String messageBody);
}
