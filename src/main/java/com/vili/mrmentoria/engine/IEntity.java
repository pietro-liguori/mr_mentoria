package com.vili.mrmentoria.engine;

import java.io.Serializable;
import java.util.Date;

public interface IEntity extends Serializable {
	
	Long getId();
	
	void setId(Long id);
	
	Class<?> getEntityType();
	
	void setEntityType(Class<?> type);
	
	Date getCreatedAt();
	
	void setCreatedAt(Date date);
	
	Date getUpdatedAt();
	
	void setUpdatedAt(Date date);
	
	boolean isActive();
	
	void setActive(boolean active);
	
	default void init() {
		setEntityType(this.getClass());
		setActive(true);
	}
}
