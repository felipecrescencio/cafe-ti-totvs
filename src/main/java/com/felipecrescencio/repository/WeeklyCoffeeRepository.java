package com.felipecrescencio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.felipecrescencio.entity.WeeklyCoffee;

@Repository
public interface WeeklyCoffeeRepository extends JpaRepository<WeeklyCoffee, Long> {
	
}
