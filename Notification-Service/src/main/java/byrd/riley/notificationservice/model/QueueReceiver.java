package byrd.riley.notificationservice.model;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

@RabbitListener(queues = {"task_queue"}, id = "mainListener")
public class QueueReceiver {

	private Notifier notifier;

	private Emailer emailer;
	
	public QueueReceiver(Notifier notifier) {
		this.notifier = notifier;
	}
	
	public QueueReceiver(Emailer emailer) {
		this.emailer = emailer;
	}
	
	public QueueReceiver(Notifier notifier, Emailer emailer) {
		this.notifier = notifier;
		this.emailer = emailer;
	}
	
	
	@RabbitHandler
	public void receive(String in) {
		System.out.println(" [x] Received '" + in + "'");
		
		String[] messageContents = in.split("\r\n");
    	
    	if(messageContents[0].equalsIgnoreCase("email")) {
    		String email = messageContents[1];
    		String messageBody = messageContents[2];
    		emailer.sendEmail(email, "TechBill Email", messageBody);
    	}
    	else if(messageContents[0].equalsIgnoreCase("text")) {
    		String number = messageContents[1];
    		String messageBody = messageContents[2];
    		notifier.sendText(number, messageBody);
    	}
        System.out.println(" [x] Done");
	}
	
}
