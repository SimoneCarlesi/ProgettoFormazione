package it.perigea.formazione.aggregator.converter;

import java.time.Instant;
import java.util.Date;

public class ObjectIdConverter {
	
	public static Instant convertToInstantFrom(String objectId) {
	    return convertToDateFrom(objectId).toInstant();
	}

	public static Date convertToDateFrom(String objectId) {
	    return new Date(convertToTimestampFrom(objectId));
	}

	public static long convertToTimestampFrom(String objectId) {
	    return Long.parseLong(objectId.substring(0, 8), 16) * 1000;
	}
}
