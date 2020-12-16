package byrd.riley.notificationservice.test.model;

import org.springframework.amqp.rabbit.test.TestRabbitTemplate;

public class QueueSender {

	private TestRabbitTemplate testRabbitTemplate;
	
	public QueueSender(TestRabbitTemplate testRabbitTemplate) {
		this.testRabbitTemplate = testRabbitTemplate;
	}

    public void send(String receiverPhone, String messageBody) {
        String message =
    		"text\r\n" +
    		receiverPhone + "\r\n" +
    		messageBody;
        testRabbitTemplate.convertAndSend(TestNotificationConfiguration.QUEUE_NAME, message);
        System.out.println(" [x] Sent '" + message + "'");
    }
}
