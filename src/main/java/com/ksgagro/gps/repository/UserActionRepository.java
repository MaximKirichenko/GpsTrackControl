package com.ksgagro.gps.repository;

import java.util.Date;

public interface UserActionRepository {
	public void setAction(String userLogin, String action, Date date);

}
