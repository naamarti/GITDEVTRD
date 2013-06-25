package com.totalbanksolutions.framework.queue;

public class QueueItem 
{

	/*
	 * V.Catrini
	 * Don't really care if "QueueItem" needs to be renamed,
	 * but this should contain the details of the record in the queue
	 */
	private long queueId;
	private QueueTopic topic;
	private QueueStatus status;
	private Message message;
	

	public long getQueueId() {
		return queueId;
	}
	public void setQueueId(long queueId) {
		this.queueId = queueId;
	}
	public QueueTopic getTopic() {
		return topic;
	}
	public void setTopic(QueueTopic topic) {
		this.topic = topic;
	}
	public QueueStatus getStatus() {
		return status;
	}
	public void setStatus(QueueStatus status) {
		this.status = status;
	}
	public Message getMessage() {
		return message;
	}
	public void setMessage(Message message) {
		this.message = message;
	}

	
}
