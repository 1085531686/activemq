package test.learn.producer;

import java.io.Serializable;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

public class Producer {

	private Connection connection = null;
	private Session session = null;
	private MessageProducer producer = null;

	public Producer(String queueName, String brokerURL) {
		try {
			ConnectionFactory connectinFactory = new ActiveMQConnectionFactory(
					"mq", "mq", brokerURL);

			this.connection = connectinFactory.createConnection();
		
			this.session = this.connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		    
			this.connection.start();
			
			Destination destination  =this.session.createQueue(queueName);
			this.producer = this.session.createProducer(destination);
			this.producer.setDeliveryMode(DeliveryMode.PERSISTENT);
			
		} catch (JMSException e) {
			e.printStackTrace();
		}

	}
	
	public void sendObject (Serializable obj) throws JMSException{
		ObjectMessage message  =this.session.createObjectMessage(obj);
		this.producer.send(message);
	}
	
	public void destroy() {
	     try {
	       if (this.producer != null) {
	         this.producer.close();
	       }
	       if (this.session != null) {
	         this.session.close();
	       }
	       if (this.connection != null)
	         this.connection.close();
	     }
	     catch (JMSException localJMSException)
	     {
	     }
	   }
}
