package phan.spring.hibernate.instateam.dao;

import phan.spring.hibernate.instateam.model.Role;

import java.util.List;

public interface RoleDao<T> {
    List<Role> findAll();

    Role findById(Long id);

    void save(T t);

    void delete(T t);
}
