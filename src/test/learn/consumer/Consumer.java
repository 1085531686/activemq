package test.learn.consumer;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.RedeliveryPolicy;

import test.learn.dto.Oper;

public class Consumer implements MessageListener {

	private Connection connection = null;
	private Session session = null;
	private MessageConsumer messageConsumer = null;

	public Consumer(String queueName, String brokerUrl) {
		try {
			ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
					"mq", "mq", brokerUrl);

			this.connection = connectionFactory.createConnection();
		
			//啥意思
			RedeliveryPolicy policy = connectionFactory.getRedeliveryPolicy();
			policy.setMaximumRedeliveries(-1);
			
			this.session =this.connection.createSession(true, Session.SESSION_TRANSACTED);
			
			this.connection.start();
			
			Destination destination = this.session.createQueue(queueName);
			
			this.messageConsumer = this.session.createConsumer(destination);
			
			this.messageConsumer .setMessageListener(this);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void onMessage(Message message) {
		// TODO Auto-generated method stub
		ObjectMessage messageObj = (ObjectMessage)message;
		
		try {
		  Oper oper =(Oper)messageObj.getObject();
		 if(true){
			 System.out.println(oper);
			 
			 this.session.commit();
		 }else{
			 this.session.rollback();
		 }
		  
			
		} catch (Exception e) {
			try {
				this.session.rollback();
			} catch (JMSException e1) {
				//回滚异常
				e1.printStackTrace();
			}
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void destroy() {
	     try {
	       if (this.messageConsumer != null) {
	         this.messageConsumer.close();
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
