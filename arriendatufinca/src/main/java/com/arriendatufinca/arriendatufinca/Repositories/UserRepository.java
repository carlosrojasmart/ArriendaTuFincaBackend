package com.arriendatufinca.arriendatufinca.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.arriendatufinca.arriendatufinca.Entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
}