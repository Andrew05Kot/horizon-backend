package com.kot.horizon.architecture.model;


public interface Archivable extends BaseEntity{

    boolean isArchived();

    void setArchived();
    
}
