package com.alexpages.ordermanager.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtils {
	
	private static final ZoneId ZONE_ID = ZoneId.of("Europe/Madrid");
	private DateUtils() {
		 throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
	}
	
	public static LocalDateTime dateToLocalDateTime(Date date) 
	{
		LocalDateTime ldtDate = null;
		if(date != null){
			ldtDate = date	.toInstant()
							.atZone(ZONE_ID)
							.toLocalDateTime();
		}
		return ldtDate;
	}
	
	public static String localDateTimeToString(LocalDateTime localDateTime)
	{
		String sLocalDateTime = null;
		if(localDateTime != null) {
			ZonedDateTime date = ZonedDateTime.of(localDateTime, ZONE_ID);
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss Z");
			sLocalDateTime = date.format(formatter);
		}
		
		return sLocalDateTime;
	}

	public static LocalDateTime toLocalDateTime(LocalDate localDate) 
	{
		LocalDateTime localDateTime = null;
		if (localDate != null) {
			LocalTime midnight = LocalTime.MIDNIGHT;
			localDateTime = LocalDateTime.of(localDate, midnight);
		}
		return localDateTime;
	}
}