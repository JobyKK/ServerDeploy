package demo.jms_sevices;

import javax.annotation.PostConstruct;
import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class JMSListener {

	@Autowired
	public RequestHandler requestHandler;
	
	public JMSListener(){
	}
	
	private String url = "tcp://46.101.138.142:61616";
	
	@PostConstruct
	public void start() throws JMSException{
		ActiveMQConnectionFactory connectionFactory  = new ActiveMQConnectionFactory(url);
	    Connection connection = connectionFactory.createConnection();
	    connection.setClientID("ConsumerSynchronous");
	    connection.start();
	 
	    System.out.println("Started");
	 
	    Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
	    Queue queue  = session.createQueue("sendQueue");
	    MessageConsumer consumer = session.createConsumer(queue);
	 
	    MessageListener listener = new MessageListener() {
			@Override
			public void onMessage(Message message) {
				
				try {
					MessageProducer replyProducer = session.createProducer(message.getJMSReplyTo());
					TextMessage msg = (TextMessage)message;
					TextMessage response = session.createTextMessage();
					//handle request
			        response = session.createTextMessage(requestHandler.handle(msg.getText()));
		
			        response.setJMSCorrelationID(message.getJMSCorrelationID());
			 
			        replyProducer.send(response);
			        System.out.println("Reply sent!");
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
			
	    };
	    consumer.setMessageListener(listener);
	}
	
}
