package byrd.riley.notificationservice.model;

public interface Texter {

	void sendText(String receiver, String messageBody);
	
	void sendText(String receiver, String sender, String messageBody);
}
