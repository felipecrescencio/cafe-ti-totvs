package com.felipecrescencio.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface WeeklyCoffeeRepository extends CrudRepository<WeeklyCoffee, Long> {
    List<WeeklyCoffee> findByName(String name);
    List<WeeklyCoffee> findByDayOfWeek(String dayOfWeek);
}
