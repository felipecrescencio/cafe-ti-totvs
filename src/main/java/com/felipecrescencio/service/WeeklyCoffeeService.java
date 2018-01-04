package com.felipecrescencio.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.felipecrescencio.entity.WeeklyCoffee;

public interface WeeklyCoffeeService {
    Page<WeeklyCoffee> findAll(Pageable pageable);

    List<WeeklyCoffee> findAll();

    WeeklyCoffee findById(int id);

    WeeklyCoffee create(WeeklyCoffee wc) throws Exception;

    WeeklyCoffee update(WeeklyCoffee wc) throws Exception;

    void delete(int id) throws Exception;
}