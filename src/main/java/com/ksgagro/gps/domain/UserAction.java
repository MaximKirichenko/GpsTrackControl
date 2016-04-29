package com.ksgagro.gps.domain;

import java.util.Date;

public class UserAction {
	private int id;
	private User user;
	private String action;
	private Date date;
	
	
	public UserAction() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public UserAction(User user, String action, Date date) {
		super();
		this.user = user;
		this.action = action;
		this.date = date;
	}


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserAction other = (UserAction) obj;
		if (id != other.id)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "UserAction [id=" + id + ", user=" + user + ", action=" + action + ", date=" + date + "]";
	}
	

}
