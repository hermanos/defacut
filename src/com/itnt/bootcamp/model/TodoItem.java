package com.itnt.bootcamp.model;

public class TodoItem implements Comparable<TodoItem> {
	private long id;
	private String title;
	private String description;
	private Priority priority;

	public TodoItem() {
		this(null, null, null);
	}

	public TodoItem(String title, String description, Priority priority) {
		this.setTitle(title);
		this.setDescription(description);
		this.setPriority(priority);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Priority getPriority() {
		return priority;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Boolean isDone() {
		return getPriority() == Priority.DONE;
	}

	@Override
	public int compareTo(TodoItem another) {
		if (isDone()) {
			return (another.isDone()) ? 0 : 1;
		} else {
			if (another.isDone()) {
				return -1;
			} else {
				return (another.getPriority().ordinal() - getPriority().ordinal());
			}
		}		
	}
}
