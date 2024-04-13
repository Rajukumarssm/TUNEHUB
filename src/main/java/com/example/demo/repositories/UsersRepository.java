package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.Users;

public interface UsersRepository extends JpaRepository<Users, Integer> {

	public Users findByEmail(String email);
	//return type of above method is Users type
	// findByEmail is a custom query method provided by Spring Data JPA. It's a
	// derived query method, meaning that Spring Data JPA will automatically
	// generate the query based on the method name.

}
