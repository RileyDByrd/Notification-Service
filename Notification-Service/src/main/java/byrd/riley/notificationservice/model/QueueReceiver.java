package byrd.riley.notificationservice.model;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

@RabbitListener(queues = {"task_queue"}, id = "mainListener")
public class QueueReceiver {

	private Texter texter;

	private Emailer emailer;
	
	public QueueReceiver(Texter texter) {
		this(texter, null);
	}
	
	public QueueReceiver(Emailer emailer) {
		this(null, emailer);
	}
	
	public QueueReceiver(Texter texter, Emailer emailer) {
		this.texter = texter;
		this.emailer = emailer;
	}
	
	
	@RabbitHandler
	public void receive(String in) {
		System.out.println(" [x] Received '" + in + "'");
		
		String[] messageContents = in.split("\r\n");
		String messageType = messageContents[0];
		String receiver = messageContents[1];
		String messageBody = messageContents[2];

    	if(messageType.equalsIgnoreCase("email"))
    		emailer.sendEmail(receiver, "My Email", messageBody);
    	else if(messageType.equalsIgnoreCase("text"))
    		texter.sendText(receiver, messageBody);

        System.out.println(" [x] Done");
	}
	
}
