package com.productivity.web.repository;

import com.productivity.web.entity.Account;
import com.productivity.web.entity.Streak;
import com.productivity.web.entity.enums.ProjectStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface StreakRepository extends JpaRepository<Streak,Long> {

    Optional<Streak> findById(Long id);



    List<Streak> findByUserAndStatus(Account user, ProjectStatus status);

    Optional<Streak> findByUserOrderByStreakDate(Account user, LocalDate streakDate);
     boolean existsByUserAndStreakDate(Account user, LocalDate streakDate);


}
