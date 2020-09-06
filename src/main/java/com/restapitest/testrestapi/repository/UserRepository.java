package com.restapitest.testrestapi.repository;

import com.restapitest.testrestapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
