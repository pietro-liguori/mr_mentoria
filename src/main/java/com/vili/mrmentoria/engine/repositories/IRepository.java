package com.vili.mrmentoria.engine.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import com.vili.mrmentoria.engine.IEntity;

@NoRepositoryBean
public interface IRepository<T extends IEntity> extends JpaRepository<T, Long>, JpaSpecificationExecutor<T> {

}
