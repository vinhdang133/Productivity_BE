package com.productivity.web.repository;


import com.productivity.web.entity.Account;
import com.productivity.web.entity.Label;
import com.productivity.web.entity.enums.ProjectStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LabelRepository  extends JpaRepository<Label, Long> {


    // Lấy danh sách Label của user
    List<Label> findByUser_Id(Long userId);

    // Kiểm tra tên Label đã tồn tại trong user
    boolean existsByUser_IdAndNameIgnoreCase(
            Long userId,
            String name
    );


    // Tìm Label thuộc user cụ thể
    Optional<Label> findByIdAndUser_Id(
            Long id,
            Long userId
    );


    List<Label> findAllByUser_Id(Long id);
    Optional<Label> findByUser_IdAndNameIgnoreCase(Long userId, String name);
}
