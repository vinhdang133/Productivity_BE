package com.productivity.web.repository;


import com.productivity.web.entity.Account;
import com.productivity.web.entity.Project;
import com.productivity.web.entity.enums.ProjectStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project,Long> {
    List<Project> getProjects();
    List<Project> findByUserAndStatus(Account user, ProjectStatus status);
    Optional<Project> findByIdAndUser(Long id, Account user);
    List<Project> findByUser(Account user);

    List<Project> findByStatus(ProjectStatus status);

}
