package com.wei.springBootMongDB.domain;

import java.io.Serializable;
import java.util.regex.Pattern;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * a email class.
 * 
 * @author Wei WANG
 *
 */
public class Email implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private static final String EMAIL_REGEX = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*"
			+ "@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private static final Pattern PATTERN = Pattern.compile(EMAIL_REGEX);
	
	@Field("email")
	private String email;
	
	public Email(String email){
		Assert.isTrue(isValid(email), "Invalid eamil address!");
		this.email = email;
	}
	
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		Assert.isTrue(isValid(email), "Invalid eamil address!");
		this.email = email;
	}
	
	public String toString(){
		return this.email;
	}
	
	/**
	 * Returns whether the given {@link String} is a valid {@link EmailAddress}
	 * which means you can safely instantiate the class.
	 * 
	 * @param eamilAddress
	 * @return
	 */
	public static boolean isValid(String eamilAddress) {
		return eamilAddress == null ? false : PATTERN.matcher(eamilAddress).matches();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
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
		Email other = (Email) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		return true;
	}
	
	

	//------------------------------------------------------
	// 	Spring date Mongodb mapping-converter
	//------------------------------------------------------
	
	/**
	 * (Spring date Mongodb mapping-converter) 
	 * java.lang.String  --> org.DB.DataPersistence.domains.Email
	 * 
	 * @author Wei WANG
	 */
	@Component
	public static class StringToEmailConverter implements Converter<String, Email>{

		@Override
		public Email convert(String source) {
			return StringUtils.hasText(source) ? new Email(source) : null;
		}
	}
	
	/**
	 * (Spring date Mongodb mapping-converter) 
	 * org.DB.DataPersistence.domains.Email --> java.lang.String 
	 * 
	 * @author Wei WANG
	 */
	@Component
	public static class EmailToStringConverter implements Converter<Email, String>{

		@Override
		public String convert(Email source) {
			return source == null ? null : source.email;
		}
	}
}
