package com.vili.mrmentoria.engine.services;

import com.vili.mrmentoria.engine.IEntity;

public interface IService<T extends IEntity> extends SearchService<T>, PersistenceService<T> {

}
