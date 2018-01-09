package com.felipecrescencio.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.felipecrescencio.bot.CafeTiTotvsBot;
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
    
    public String processMessage(String message2) {
    	/*			mention
		for(MessageEntity me : update.getMessage().getEntities()) {
			log.info("message ent: "+ me);
		}
*/
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Calendar c = Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo"));
		
		int tokenSum=0;
		
		handleToken:
		for(String token : message2.split(" ")) {
			try {
				c.setTime(sdf.parse(token));
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

				c.set(Calendar.DAY_OF_MONTH, 6 - today2);
				WeeklyCoffee wc2 = findByDate(c);
				
				if(wc2 != null) {
					return "O café de "+ sdf.format(c.getTime()) +" é do(a) "+ wc2.getName() +".";
				}

				return "";
			case 5:
				int today = c.get(Calendar.DAY_OF_WEEK);
				
				if(today == Calendar.SATURDAY)
					today++;

				c.set(Calendar.DAY_OF_MONTH, 6 - today);
				WeeklyCoffee wc = findByDate(c);
				
				if(wc != null) {
					return "O café dessa semana é do(a) "+ wc.getName() +".";
				}

				return "";
		}
		
		return null;
    }

	@Override
	public WeeklyCoffee findByDate(Calendar date) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		log.info("Date param:"+ sdf.format(date));
		for(WeeklyCoffee wc : weeklyCoffeeRepository.findAll()) {
			log.info("wc date:"+ sdf.format(wc.getDayOfWeek()));
			log.info("wc.getDayOfWeek().equals(date)"+ wc.getDayOfWeek().equals(date));
			if(wc.getDayOfWeek().equals(date)) {
				return wc;
			}
		}
		return null;
	}
}
