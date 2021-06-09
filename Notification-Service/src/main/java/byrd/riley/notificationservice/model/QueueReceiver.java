package byrd.riley.notificationservice.model;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

@RabbitListener(queues = {"task_queue"}, id = "mainListener")
public class QueueReceiver {

	private Texter texter;

	private Emailer emailer;
	
	public QueueReceiver(Texter texter) {
		this.texter = texter;
	}
	
	public QueueReceiver(Emailer emailer) {
		this.emailer = emailer;
	}
	
	public QueueReceiver(Texter texter, Emailer emailer) {
		this.texter = texter;
		this.emailer = emailer;
	}
	
	
	@RabbitHandler
	public void receive(String in) {
		System.out.println(" [x] Received '" + in + "'");
		
		String[] messageContents = in.split("\r\n");
    	
    	if(messageContents[0].equalsIgnoreCase("email")) {
    		String email = messageContents[1];
    		String messageBody = messageContents[2];
    		emailer.sendEmail(email, "My Email", messageBody);
    	} else if(messageContents[0].equalsIgnoreCase("text")) {
    		String number = messageContents[1];
    		String messageBody = messageContents[2];
    		texter.sendText(number, messageBody);
    	}
        System.out.println(" [x] Done");
	}
	
}
