package phan.spring.hibernate.instateam.service;

import phan.spring.hibernate.instateam.model.Project;

import java.util.List;

public interface ProjectService {
    List<Project> findAll();

    Project findById(Long id);

    void save(Project project);

    void delete(Project project);
}
