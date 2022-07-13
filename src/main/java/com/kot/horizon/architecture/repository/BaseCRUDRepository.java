package com.kot.horizon.architecture.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import com.kot.horizon.architecture.model.BaseEntity;

@NoRepositoryBean
public interface BaseCRUDRepository<E extends BaseEntity> extends JpaRepository<E, Long>, JpaSpecificationExecutor<E> {

}
