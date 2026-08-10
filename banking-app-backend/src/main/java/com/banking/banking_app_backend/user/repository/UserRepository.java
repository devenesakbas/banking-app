package com.banking.banking_app_backend.user.repository;

import com.banking.banking_app_backend.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
