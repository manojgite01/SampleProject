package com.smartapp.soartm.log;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

/** This class is used to send the message on the response queue*/
public class MessageSender {
	
	/**Injecting JmsTemplate, this variable is used to put the message on request and response queue.*/
	private JmsTemplate jmsTemplate;

	/**
	 * This method is used to put the message on request and response queue using JmsTemplate.
	 * MessageCreator is implemented in sendMessage method. Message text and corelationId are set into Message inside createMessage. 
	 * @param destination
	 * @param text
	 * @param correlationId
	 */
	public void sendMessage(Destination destination, final String text, final String correlationId){
		
		MessageCreator creator = new MessageCreator(){
			public Message createMessage(Session session) throws JMSException{
				TextMessage message = null;
					message = session.createTextMessage();
					message.setText(text);
					message.setJMSCorrelationID(correlationId);
				return message;
			}
		};
		jmsTemplate.send(destination, creator);
	}
		
		
		/**
		 * This method is used to put the message on request and response queue using JmsTemplate.
		 * MessageCreator is implemented in sendMessage method. Message text and corelationId are set into Message inside createMessage. 
		 * @param destination
		 * @param text
		 * @param correlationId
		 */
		public void sendMessage(Destination destination, final String text ){
			MessageCreator creator = new MessageCreator(){
				
				public Message createMessage(Session session) throws JMSException{
					TextMessage message = null;
						message = session.createTextMessage();
						message.setText(text);
					return message;
				}
			};
			jmsTemplate.send(destination, creator);
		}


		public JmsTemplate getJmsTemplate() {
			return jmsTemplate;
		}


		public void setJmsTemplate(JmsTemplate jmsTemplate) {
			this.jmsTemplate = jmsTemplate;
		}
		
		

}
