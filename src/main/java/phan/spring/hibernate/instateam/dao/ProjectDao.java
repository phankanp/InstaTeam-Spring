package phan.spring.hibernate.instateam.dao;

import phan.spring.hibernate.instateam.model.Project;

import java.util.List;

public interface ProjectDao<T> {
    List<Project> findAll();

    Project findById(Long id);

    void save(T t);

    void delete(T t);
}
