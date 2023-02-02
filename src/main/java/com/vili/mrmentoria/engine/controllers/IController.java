package com.vili.mrmentoria.engine.controllers;

import com.vili.mrmentoria.engine.IDataTransferObject;
import com.vili.mrmentoria.engine.IEntity;

public interface IController<E extends IEntity, T extends IDataTransferObject> extends SearchController<E>, PersistenceController<E, T> {

}