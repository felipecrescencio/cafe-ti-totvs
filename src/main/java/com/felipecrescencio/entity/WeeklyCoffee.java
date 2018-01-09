package com.felipecrescencio.entity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class WeeklyCoffee {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String name;

    @Basic
    @Temporal(TemporalType.DATE)
    private Calendar dayOfWeek;

    private String whoBroughtName;

    public WeeklyCoffee() {}

    public WeeklyCoffee(String name, Calendar dayOfWeek, String whoBroughtName) {
		super();
		this.name = name;
		this.dayOfWeek = dayOfWeek;
		this.whoBroughtName = whoBroughtName;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Calendar getDayOfWeek() {
		return dayOfWeek;
	}
	public void setDayOfWeek(Calendar dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}
	public String getWhoBroughtName() {
		return whoBroughtName;
	}
	public void setWhoBroughtName(String whoBroughtName) {
		this.whoBroughtName = whoBroughtName;
	}

    @Override
    public String toString() {
    	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    	return String.format(
                "WeeklyCoffee[id=%d, name='%s', dayOfWeek='%s', whoBroughtName=%s]",
                id, name, sdf.format(dayOfWeek.getTime()) , whoBroughtName);
    }
}
