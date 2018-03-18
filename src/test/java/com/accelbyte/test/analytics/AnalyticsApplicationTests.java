package com.accelbyte.test.analytics;

import static org.junit.Assert.assertEquals;

import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Queue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.accelbyte.test.analytics.main.AnalyticsApplication;
import com.accelbyte.test.analytics.model.LogDetail;
import com.accelbyte.test.analytics.service.LogProcessor;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={AnalyticsApplication.class})
public class AnalyticsApplicationTests {

	@Autowired
	private LogProcessor logProcessor;
	
	@Test
	public void checkCountFileOnFoder() {
		List<Path> list=logProcessor.getListFileByDirectory("apache-samples/random");
		assertEquals(list.size(), 3);
	}
	
	@Test
	public void checkPossibleFile() throws ParseException {
		List<Path> list=logProcessor.getListFileByDirectory("apache-samples/random");
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MMM/yyyy HH:mm:ss");
        String dateInString = "07/Mar/2004 16:00:00";
        Date date = formatter.parse(dateInString);
		Queue<String> listPath=logProcessor.getPossibleFile(list, 1, date);
		assertEquals(listPath.size(), 2);
	}
	
	@Test
	public void checkLineToObjectConverter(){
		Exception ex = null;
		LogDetail ld = null;
		try {
			ld= logProcessor.convertToLogProperties("64.242.88.10 identifier bob [07/Mar/2004:16:47:12 -0800] \"GET /robots.txt HTTP/1.1\" 200 68");
		} catch (ParseException e) {
			ex=e;
		}
		assertEquals(null,ex);
		assertEquals("64.242.88.10", ld.getIp());
		assertEquals("identifier", ld.getUserIdentifier());
		assertEquals("bob", ld.getUserName());
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MMM/yyyy HH:mm:ss");
        String dateInString = "07/Mar/2004 16:47:12";
        try {
			Date date = formatter.parse(dateInString);
			assertEquals(true, date.equals(ld.getDateCreated()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

}
