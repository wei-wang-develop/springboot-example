package com.wei.springBootMongDB.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Component;

/**
 * Base class for document classes.
 * 
 * @author Wei WANG
 * @version v1.0
 */
public class AbstractDocument {

	@Id
	private ObjectId id;

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractDocument other = (AbstractDocument) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "AbstractDocument [id=" + id + "]";
	}

	//------------------------------------------------------
	// 	Spring date Mongodb mapping-converter
	//------------------------------------------------------



	/**
	 * (Spring date Mongodb mapping-converter) 
	 * Date -> Instant + System default time zone = LocalDateTime
	 * java.time.LcalDateTime-->java.util.Date
	 * 
	 * @author Wei WANG
	 */
	@Component
	public static class LocalDateTimeToDateConverter implements Converter<LocalDateTime, Date> {

		@Override
		public Date convert(LocalDateTime source) {
			return source == null ? null : Date.from(source.atZone(ZoneId.systemDefault()).toInstant());
		}

	}
	
	
	
	/**
	 * (Spring date Mongodb mapping-converter) 
	 * java.time.LcalDateTime-->java.util.Date
	 * 
	 * @author Wei WANG
	 */
	@Component
	public static class LocalDateToDateConverter implements Converter<LocalDate, Date> {

		@Override
		public Date convert(LocalDate source) {
			return source == null ? null : Date.from(source.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
		}

	}

	/**
	 * (Spring date Mongodb mapping-converter) 
	 * Date -> Instant + System default time zone = LocalDate
	 * java.util.Date-->java.time.LcalDateTime
	 * 
	 * @author Wei WANG
	 */
	@Component
	public static class DateToLocalDateConverter implements Converter<Date, LocalDate> {

		@Override
		public LocalDate convert(Date source) {
			return source == null ? null : source.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		}
	}
	

	/**
	 * (Spring date Mongodb mapping-converter) 
	 * java.util.Date-->java.time.LcalDateTime
	 * 
	 * @author Wei WANG
	 */
	@Component
	public static class DateToLocalDateTimeConverter implements Converter<Date, LocalDateTime> {

		@Override
		public LocalDateTime convert(Date source) {
			return source == null ? null : LocalDateTime.ofInstant(source.toInstant(), ZoneId.systemDefault());
		}
	}
	
	/**
	 * (Spring date Mongodb mapping-converter) 
	 * java.lang.Double --> java.math.BigDecimal
	 * @author Wei WANG
	 */
	/*
	@Component
	public class DoubleToBigDecimalConverter implements Converter<Double, BigDecimal>{

		@Override
		public BigDecimal convert(Double source) {
			return source == null ? null : new BigDecimal(Double.toString(source)) ;
		}
		
	}*/
	
	/**
	 * (Spring date Mongodb mapping-converter) 
	 *  java.math.BigDecimal --> java.lang.Double
	 * @author Wei WANG
	 */
	/*
	@Component
	public class BigDecimalToDoubleConverter implements Converter<BigDecimal, Double>{

		@Override
		public Double convert(BigDecimal source) {
			return source == null ? null : source.doubleValue();
		}
		
	}*/

}
