package com.seel.apod.util.validation;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.stereotype.Service;

@Service
public class DateValidator {

	public DateValidator() {}

	public boolean isValid(String inDate, String format) {
		boolean isValid = true;

		try
		{
			DateTimeFormatter dtf = DateTimeFormat.forPattern(format);
			dtf.parseDateTime(inDate);
		}
		catch (Exception exc) {
			isValid = false;
		}

		return isValid;
	}
}
