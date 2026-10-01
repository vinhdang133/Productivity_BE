package com.productivity.web.repository;

import com.productivity.web.entity.Account;
import com.productivity.web.entity.FocusSession;
import com.productivity.web.entity.enums.SessionStatus;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.jar.JarEntry;

@Repository
public interface FocusSessionRepository extends JpaRepository<FocusSession, Long> {

    List<FocusSession> findByUserOrderByStartedAtDesc(Account user);

    // 2. Lấy một session và đảm bảo nó thuộc user
    Optional<FocusSession> findByIdAndUser(Long id, Account user);

    // 3. Kiểm tra/lấy session đang chạy của user
    Optional<FocusSession> findByUserAndStatus(
            Account user,
            SessionStatus status
    );
    List<FocusSession> findAllByUserOrderByStartedAtDesc(Account user);


}
