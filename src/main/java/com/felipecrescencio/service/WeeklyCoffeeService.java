package com.felipecrescencio.service;

import java.util.Calendar;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.felipecrescencio.entity.WeeklyCoffee;

public interface WeeklyCoffeeService {
    Page<WeeklyCoffee> findAll(Pageable pageable);

    List<WeeklyCoffee> findAll();

    WeeklyCoffee findById(long id);
    
    WeeklyCoffee findByDate(Calendar date);

    WeeklyCoffee create(WeeklyCoffee wc) throws Exception;

    WeeklyCoffee update(WeeklyCoffee wc) throws Exception;

    void delete(long id) throws Exception;
    
    String processMessage(String message2);
}
