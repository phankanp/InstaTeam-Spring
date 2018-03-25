package phan.spring.hibernate.instateam.dao;

import phan.spring.hibernate.instateam.model.Collaborator;

import java.util.List;

public interface CollaboratorDao<T> {
    List<Collaborator> findAll();

    Collaborator findById(Long id);

    void save(T t);

    void delete(T t);
}

