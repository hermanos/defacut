package com.itnt.bootcamp.model;

import java.io.Serializable;

/**
 * 
 * @author GabiRadu
 * 
 */
public class RemoteTodoItem implements Serializable {
	private String created_at;
	private boolean done;
	private long id;
	private String title;
	private String updated_at;

	public RemoteTodoItem() {

	}

	public RemoteTodoItem(String created, boolean done, String title,
			String updated) {
		this.created_at = created;
		this.done = done;
		this.title = title;
		this.updated_at = updated;
	}
	
	public RemoteTodoItem(long id, String created, boolean done, String title,
			String updated) {
		this.id = id;
		this.created_at = created;
		this.done = done;
		this.title = title;
		this.updated_at = updated;
	}

	public String getCreated() {
		return created_at;
	}

	public void setCreated(String created) {
		this.created_at = created;
	}

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUpdated() {
		return updated_at;
	}

	public void setUpdated(String updated) {
		this.updated_at = updated;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("remote item : " + title);
		buffer.append("remote created : " + created_at);
		buffer.append("remote udpated : " + updated_at);
		buffer.append("remote done : " + done);
		buffer.append("remote id : " + id);
		return buffer.toString();
	}

}
