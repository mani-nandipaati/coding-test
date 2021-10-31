package com.connectgroup;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.connectgroup.data.LogEntry;

public class DataFiltererTest {
	
	private static final String MULTI_LINE_FILE="src/test/resources/multi-lines";
	private static final String SINGLE_LINE_FILE="src/test/resources/single-line";
	private static final String COUNTRY_CODE_GB="GB";
	private static final String COUNTRY_CODE_US="US";
	private static final String INVALID_COUNTRY_CODE="IV";
	private static final Long TIME_LIMIT=539L;
	
    @Test
    public void shouldReturnEmptyCollection_WhenLogFileIsEmpty() throws FileNotFoundException {
        Assert.assertTrue(DataFilterer.filterByCountry(openFile("src/test/resources/empty"), "GB").isEmpty());
    }
    
    @Test
    public void filterByCountry_ShouldReturnNonEmptyListOfLogEntry() throws FileNotFoundException
    {
    	
    	/*act*/
    	Collection<?> actualListOfLogEntryGB=	DataFilterer.filterByCountry(openFile(MULTI_LINE_FILE), COUNTRY_CODE_GB);
    	
    	/*assert*/
    	Assert.assertFalse(actualListOfLogEntryGB.isEmpty());
    	
    	
    	
    }
    @Test
    public void filterByCountry_ShouldFilterByCode() throws FileNotFoundException
    {
    	/*act*/
    	Collection<?> actualListOfLogEntryGB=	DataFilterer.filterByCountry(openFile(MULTI_LINE_FILE), COUNTRY_CODE_GB);
    	
    	/*assert*/
    	Assert.assertEquals(expectedListOfLogEntryForGB(),actualListOfLogEntryGB);
    }
    
    
    @Test
    public void filterByCountry_ShouldFilterByCode_WithSingleLogEntry() throws FileNotFoundException
    {
    	/*act*/
    	Collection<?> actualListOfLogEntryGB=	DataFilterer.filterByCountry(openFile(SINGLE_LINE_FILE), COUNTRY_CODE_GB);
    	
    	/*assert*/
    	Assert.assertEquals(expectedListOfLogEntryForGBSingleLog(),actualListOfLogEntryGB);
    }
    
    @Test
    public void filterByCountry_ShouldReturnEmptyForInvalidCountry() throws FileNotFoundException
    {
    	/*act*/
    	Collection<?> actualListOfLogEntryGB=	DataFilterer.filterByCountry(openFile(SINGLE_LINE_FILE), INVALID_COUNTRY_CODE);
    	
    	/*assert*/
    	Assert.assertTrue(actualListOfLogEntryGB.isEmpty());
    }
    
    @Test
   public void filterByCountryWithResponseTimeAboveLimit_ShouldReturnEmptyCollectionWhenLogFileIsEmpty() throws FileNotFoundException
   {
    	/*act*/  
    	Assert.assertTrue(DataFilterer.filterByCountryWithResponseTimeAboveLimit(openFile("src/test/resources/empty"), "GB",TIME_LIMIT).isEmpty());
   }
    
    @Test
    public void  filterByCountryWithResponseTimeAboveLimit_ShouldFilterByCountryCodeANdResponseTime() throws FileNotFoundException
    {
    	/*act*/
    	Collection<?> actualListOfLogEntryUS= DataFilterer.filterByCountryWithResponseTimeAboveLimit(openFile(MULTI_LINE_FILE), COUNTRY_CODE_US, TIME_LIMIT);
    	
    	/*assert*/
    	
    	Assert.assertEquals(expectedListOfLogEntryForUSAboveLimit(),actualListOfLogEntryUS);
    }
    
    @Test
    public void filterByResponseTimeAboveAverage_ShouldReturnEmptyCollectionWhenLogFileIsEmpty() throws FileNotFoundException
    {
    	/*act*/
    	Assert.assertTrue(DataFilterer.filterByResponseTimeAboveAverage(openFile("src/test/resources/empty")).isEmpty());
    }
    
    @Test
    public void filterByResponseTimeAboveAverage_ShouldReturnListOfLogEntryWithAboveAverageReponseTime() throws FileNotFoundException
    {
    	/*act*/
    	Collection<?> actualListOfLogEntry= DataFilterer.filterByResponseTimeAboveAverage(openFile(MULTI_LINE_FILE));
    	
/*     /*assert*/
    	Assert.assertFalse(actualListOfLogEntry.isEmpty());
    	Assert.assertEquals(expectedListOfLogEntryWithAboveAverageReponseTime(),actualListOfLogEntry);
    }
    

    
    private List<?> expectedListOfLogEntryForGB()
    {
    	LogEntry logEntryGB= new LogEntry();
    	long timestamp = Long.parseLong("1432917066");
    	logEntryGB.setRequestTimeStamp(LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault()));
    	logEntryGB.setCountryCode("GB");
    	logEntryGB.setResponseTime(Long.parseLong("37"));
    	return Arrays.asList(logEntryGB);
    }
    
    
    private List<?> expectedListOfLogEntryForGBSingleLog()
    {
    	LogEntry logEntryGB= new LogEntry();
    	long timestamp = Long.parseLong("1431592497");
    	logEntryGB.setRequestTimeStamp(LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault()));
    	logEntryGB.setCountryCode("GB");
    	logEntryGB.setResponseTime(Long.parseLong("200"));
    	return Arrays.asList(logEntryGB);
    }
    
    
    
    private List<?> expectedListOfLogEntryForUSAboveLimit()
    {
    	LogEntry entryOne= new LogEntry();
    	long entryOneTimestamp = Long.parseLong("1433666287");
    	entryOne.setRequestTimeStamp(LocalDateTime.ofInstant(Instant.ofEpochMilli(entryOneTimestamp), ZoneId.systemDefault()));
    	entryOne.setCountryCode("US");
    	entryOne.setResponseTime(Long.parseLong("789"));
		
		LogEntry entryTwo= new LogEntry();
    	long entryTwoTimestamp = Long.parseLong("1432484176");
    	entryTwo.setRequestTimeStamp(LocalDateTime.ofInstant(Instant.ofEpochMilli(entryTwoTimestamp), ZoneId.systemDefault()));
    	entryTwo.setCountryCode("US");
    	entryTwo.setResponseTime(Long.parseLong("850"));
    	
    	return Arrays.asList(entryOne,entryTwo);
    	
    }
    
    private List<?> expectedListOfLogEntryWithAboveAverageReponseTime()
    {
    	
		LogEntry entryOne= new LogEntry();
    	long entryOneTimestamp = Long.parseLong("1433190845");
    	entryOne.setRequestTimeStamp(LocalDateTime.ofInstant(Instant.ofEpochMilli(entryOneTimestamp), ZoneId.systemDefault()));
    	entryOne.setCountryCode("US");
    	entryOne.setResponseTime(Long.parseLong("539"));
		
    	LogEntry entryTwo= new LogEntry();
    	long entryTwoTimestamp = Long.parseLong("1433666287");
    	entryTwo.setRequestTimeStamp(LocalDateTime.ofInstant(Instant.ofEpochMilli(entryTwoTimestamp), ZoneId.systemDefault()));
    	entryTwo.setCountryCode("US");
    	entryTwo.setResponseTime(Long.parseLong("789"));
		
		LogEntry entryThree= new LogEntry();
    	long entryThreeTimestamp = Long.parseLong("1432484176");
    	entryThree.setRequestTimeStamp(LocalDateTime.ofInstant(Instant.ofEpochMilli(entryThreeTimestamp), ZoneId.systemDefault()));
    	entryThree.setCountryCode("US");
    	entryThree.setResponseTime(Long.parseLong("850"));
    	
    	return Arrays.asList(entryOne,entryTwo,entryThree);
    	
    }
    
    private FileReader openFile(String filename) throws FileNotFoundException {
        return new FileReader(new File(filename));
    }
    
}
