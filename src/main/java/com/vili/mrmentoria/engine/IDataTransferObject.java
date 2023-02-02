package com.vili.mrmentoria.engine;

public interface IDataTransferObject {

	Long getId();
	
	void setId(Long id);
	
	boolean isActive();
	
	void setActive(boolean active);

	default DTOType getMethod() {
		if (getId() == null) {
			return DTOType.INSERT;
		} else {
			return DTOType.UPDATE;
		}
	}
	
	default boolean isInsert() {
		return getId() == null;
	}

	default boolean isUpdate() {
		return getId() != null;
	}
	
	IEntity toEntity();
}
