package com.connectgroup.utils;

import java.io.BufferedReader;
import java.io.Reader;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.connectgroup.data.LogEntry;

public class FileParser {
	
	private FileParser() {}
	
    public static List<LogEntry> readFile(Reader source) {

		List<LogEntry> logEntries = new ArrayList<>();
		try (BufferedReader buffReader = new BufferedReader(source)) {
			String line = buffReader.readLine();

			if (line == null || line.isEmpty()) {
				return Collections.emptyList();
			}

			while ((line = buffReader.readLine()) != null) {
				LogEntry data = convertToLogEntry(line);
				logEntries.add(data);
			}
			return logEntries;
		} catch (Exception e) {
			return List.of();
		}
	
    }

	private static LogEntry convertToLogEntry(String line) {
		LogEntry data = new LogEntry();
		String[] splitString = line.split(",");
		long timestamp = Long.parseLong(splitString[0]);
		data.setRequestTimeStamp(LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault()));
		data.setCountryCode(splitString[1]);
		data.setResponseTime(Long.parseLong(splitString[2]));
		return data;
	}


}
