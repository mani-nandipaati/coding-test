package com.connectgroup;

import java.io.Reader;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.connectgroup.data.LogEntry;
import com.connectgroup.utils.FileParser;

public class DataFilterer {

  public static Collection<?> filterByCountry(Reader source, String country) {
		List<LogEntry> logEntries = FileParser.readFile(source);
		return  logEntries.stream()
				.filter(data -> data.getCountryCode().equalsIgnoreCase(country))
				.collect(Collectors.toList());

	}

  public static Collection<?> filterByCountryWithResponseTimeAboveLimit(Reader source, String country, long limit) {
	  List<LogEntry> logEntries = FileParser.readFile(source);
		return  logEntries.stream()
				.filter(data -> data.getCountryCode().equalsIgnoreCase(country))
				.filter(data -> data.getResponseTime() > limit)
				.collect(Collectors.toList());
	}

  public static Collection<?> filterByResponseTimeAboveAverage(Reader source) {
		
	  List<LogEntry> logEntries = FileParser.readFile(source);
		
		if(logEntries.isEmpty())
		{
			return Collections.emptyList();
		}
		
		Double averageReponseTime=logEntries.stream().mapToDouble(LogEntry::getResponseTime).average().getAsDouble();
		return logEntries.stream()
				.filter(data-> data.getResponseTime()>averageReponseTime)
				.collect(Collectors.toList());
	}
}