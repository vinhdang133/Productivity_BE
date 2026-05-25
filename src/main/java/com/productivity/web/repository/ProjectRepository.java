package com.productivity.web.repository;


import com.productivity.web.entity.Account;
import com.productivity.web.entity.Project;
import com.productivity.web.entity.enums.ProjectStatus;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository {
    List<Project> getProjects();
    List<Project> findByUserAndStatus(Account user, ProjectStatus status);
    Optional<Project> findByIdandUser(Long id, Account User);
}
