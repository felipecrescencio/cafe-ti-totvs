package com.felipecrescencio.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.felipecrescencio.entity.WeeklyCoffee;
import com.felipecrescencio.repository.WeeklyCoffeeRepository;

/**
 * Created by isdzulqor on 3/27/17.
 */
@Service
public class WeeklyCoffeeServiceBean implements WeeklyCoffeeService {
    @Autowired
    private WeeklyCoffeeRepository weeklyCoffeeRepository;

    @Override
    public Page<WeeklyCoffee> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<WeeklyCoffee> findAll() {
        return weeklyCoffeeRepository.findAll();
    }

    @Override
    public WeeklyCoffee findById(int id) {
        return null;
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    @Override
    public WeeklyCoffee create(WeeklyCoffee weeklyCoffee) throws Exception {
    	WeeklyCoffee toBeSavedWeeklyCoffee = new WeeklyCoffee();
        BeanUtils.copyProperties(weeklyCoffee, toBeSavedWeeklyCoffee);
        toBeSavedWeeklyCoffee.setName(weeklyCoffee.getName());
        toBeSavedWeeklyCoffee.setWhoBroughtName(weeklyCoffee.getWhoBroughtName());
        toBeSavedWeeklyCoffee.setDayOfWeek(weeklyCoffee.getDayOfWeek());
        return weeklyCoffeeRepository.save(toBeSavedWeeklyCoffee);
    }

    @Override
    public WeeklyCoffee update(WeeklyCoffee weeklyCoffee) throws Exception {
    	
    	WeeklyCoffee toBeSavedWeeklyCoffee = weeklyCoffeeRepository.findOne(weeklyCoffee.getId());
        BeanUtils.copyProperties(weeklyCoffee, toBeSavedWeeklyCoffee);
        toBeSavedWeeklyCoffee.setName(weeklyCoffee.getName());
        toBeSavedWeeklyCoffee.setWhoBroughtName(weeklyCoffee.getWhoBroughtName());
        toBeSavedWeeklyCoffee.setDayOfWeek(weeklyCoffee.getDayOfWeek());
        return weeklyCoffeeRepository.save(toBeSavedWeeklyCoffee);
    }

    @Override
    public void delete(int id) throws Exception {

    }
}
