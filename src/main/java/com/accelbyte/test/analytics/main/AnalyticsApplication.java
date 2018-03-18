package com.accelbyte.test.analytics.main;

import java.nio.file.Path;
import java.util.Date;
import java.util.List;
import java.util.Queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.accelbyte.test.analytics.model.CommandArgument;
import com.accelbyte.test.analytics.service.LogProcessor;
import com.beust.jcommander.JCommander;

@SpringBootApplication
@ComponentScan("com.accelbyte.test.analytics")
public class AnalyticsApplication implements CommandLineRunner{

	@Autowired
	private LogProcessor logProcessor;
	private static CommandArgument ca;
	
	public static void main(String[] args) {
		SpringApplication.run(AnalyticsApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		JCommander jc=new JCommander();
		ca=new CommandArgument();
		jc.addObject(ca);
        try {
        	jc.parse(args);
        	Date date=new Date();
    		List<Path> lf=logProcessor.getListFileByDirectory(ca.getDirectory());
    		Queue<String> listPossiblePath = logProcessor.getPossibleFile(lf, ca.getMin(), date);
    		logProcessor.showLog(listPossiblePath, ca.getMin(), date);
		} catch (Exception e) {
			jc.usage();
		}

    }
}
