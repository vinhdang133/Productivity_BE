package com.productivity.web.repository;

import com.productivity.web.entity.Account;
import com.productivity.web.entity.Project;
import com.productivity.web.entity.Task;
import com.productivity.web.entity.enums.Priority;
import com.productivity.web.entity.enums.TaskStatus;
import org.apache.catalina.User;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {

    List<Task> findByUserOrderBySortOrderAscCreatedAtDesc(Account user) ;

    Optional<Task> findByIdAndUser(Long id, Account user) ;

    List<Task> findByUserAndStatus(Account user, TaskStatus status) ;

    List<Task> findByUserAndProject(Account user, Project project) ;

    List<Task> findByUserAndTitleContainingIgnoreCase(Account user, String keyword) ;

    List<Task> findByUserAndPriority(Account user, Priority priority) ;
}
