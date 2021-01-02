package com.demo.dao;

import com.demo.model.Role;
import com.demo.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Page<Role> findByUserId(Long userId, Pageable pageable);
}
