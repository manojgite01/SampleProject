package com.smartapp.soartm.log;

import java.util.concurrent.ArrayBlockingQueue;



import javax.jms.Destination;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * SoartmLoggingTask is used for logging the messages asynchronously.
 * This class implements Runnable interface.
 */
public class SoartmLoggingTask implements Runnable{

	/**singleton instance of ArrayBlockingQueue. Same instance of ArrayBlockingQueue is injected in SoartmeLogger*/
	private ArrayBlockingQueue<String> blockingQueue;
	/** Instance of MessageSender*/
    private MessageSender  soartmMessageSender ;
    /**Instance of Destination. This variable will contain the soartm logging queue details*/
	private Destination soartmLogQueue;
	private static Logger logger = LoggerFactory.getLogger(SoartmLoggingTask.class);
	
	/**
	 * This method will be invoked by Spring Scheduler after every 10 secs. 
	 * During execution of run(), ArrayBlockingQueue is iterated until all messages from it are send to MessageSender to be put on a Queue.
	 * Spring scheduler will not invoke the run() until and unless the current thread is executed.
	 */
	public void run() {
		try{
			while(!blockingQueue.isEmpty()){
				String xmlString = blockingQueue.take();
				soartmMessageSender.sendMessage(soartmLogQueue, xmlString);
			}
		}catch(Throwable ex){
			logger.error("Error while sending Soartmlogging message : " + ex );
		}
	}

	public void setBlockingQueue(ArrayBlockingQueue<String> blockingQueue) {
		this.blockingQueue = blockingQueue;
	}


	public void setSoartmMessageSender(MessageSender soartmMessageSender) {
		this.soartmMessageSender = soartmMessageSender;
	}


	public void setSoartmLogQueue(Destination soartmLogQueue) {
		this.soartmLogQueue = soartmLogQueue;
	}
	
}
