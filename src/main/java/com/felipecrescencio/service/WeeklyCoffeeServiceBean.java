package com.felipecrescencio.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.felipecrescencio.entity.WeeklyCoffee;
import com.felipecrescencio.repository.WeeklyCoffeeRepository;

@Service
public class WeeklyCoffeeServiceBean implements WeeklyCoffeeService {
	private static final Logger log = LoggerFactory.getLogger(WeeklyCoffeeServiceBean.class);

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
    public WeeklyCoffee findById(long id) {
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
    public void delete(long id) throws Exception {
    	weeklyCoffeeRepository.delete(id);
    }
    
    public String processMessage(String message2) {
    	/*			mention
		for(MessageEntity me : update.getMessage().getEntities()) {
			log.info("message ent: "+ me);
		}
*/
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//		Calendar c = Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo"));
		
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		
		int tokenSum=0;
		
		handleToken:
		for(String token : message2.split(" ")) {
			try {
				Date d = sdf.parse(token);
				c.setTime(d);
				tokenSum = 1;
				break handleToken;
			} catch (ParseException e) {
				//Just go ahead
			}

			if(token.equalsIgnoreCase("cafe") || token.equalsIgnoreCase("café")) {
				tokenSum += 2;
			} else if(token.equalsIgnoreCase("semana")) {
				tokenSum += 3;
			}
		}
		
		log.info("Tokensum: "+ tokenSum);
		
		switch(tokenSum) {
			case 1:
				int today2 = c.get(Calendar.DAY_OF_WEEK);
				
				if(today2 == Calendar.SATURDAY)
					today2++;

				c.add(Calendar.DAY_OF_MONTH, 6 - today2);
				WeeklyCoffee wc2 = findByDate(c);
				
				if(wc2 != null) {
					return "O café de "+ sdf.format(c.getTime()) +" é do(a) "+ wc2.getName() +".";
				}

				return "";
			case 5:
				int today = c.get(Calendar.DAY_OF_WEEK);
				
				if(today == Calendar.SATURDAY)
					today++;

				c.set(Calendar.DAY_OF_WEEK, 9 - today);
				WeeklyCoffee wc = findByDate(c);
				
				if(wc != null) {
					String s = "O café dessa semana é do(a) "+ wc.getName();
					
					log.info("wc.getName(): "+ wc.getName());
					log.info("wc.getWhoBroughtName(): "+ wc.getWhoBroughtName());
					log.info("wc.getName().equalsIgnoreCase(wc.getWhoBroughtName()): "+ wc.getName().equalsIgnoreCase(wc.getWhoBroughtName())); 
					
					
					if(!wc.getName().equalsIgnoreCase(wc.getWhoBroughtName()))
						s += ", mas quem irá trazer é o(a) "+ wc.getWhoBroughtName();
					else
						s += ".";
						
					return s;
				}

				return "";
		}
		
		return null;
    }

	@Override
	public WeeklyCoffee findByDate(Calendar date) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		log.info("Date param:"+ sdf.format(date.getTime()));
		for(WeeklyCoffee wc : weeklyCoffeeRepository.findAll()) {
			log.info("wc date:"+ sdf.format(wc.getDayOfWeek().getTime()));
			log.info("DateUtils.isSameDay(wc.getDayOfWeek(), date): "+ DateUtils.isSameDay(wc.getDayOfWeek(), date));
//			if(wc.getDayOfWeek().equals(date)) {
			if(DateUtils.isSameDay(wc.getDayOfWeek(), date)) {
				return wc;
			}
		}
		return null;
	}
}
