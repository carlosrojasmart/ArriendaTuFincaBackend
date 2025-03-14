package com.arriendatufinca.arriendatufinca.Repositories;

import com.arriendatufinca.arriendatufinca.Entities.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}