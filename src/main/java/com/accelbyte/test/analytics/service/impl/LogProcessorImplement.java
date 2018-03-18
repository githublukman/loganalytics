package com.accelbyte.test.analytics.service.impl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.charset.Charset;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Queue;

import org.springframework.stereotype.Service;

import com.accelbyte.test.analytics.model.LogDetail;
import com.accelbyte.test.analytics.service.LogProcessor;

@Service
public class LogProcessorImplement implements LogProcessor{

	@Override
	public List<Path> getListFileByDirectory(String path) {
		List<Path> files = new ArrayList<>();
		try(DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(path),"http-*.{log}")) {
		    for(Path p : stream) 
		    	if(Files.isReadable(p) && Files.isRegularFile(p))
		    		files.add(p);
		}catch (IOException e) {
			e.printStackTrace();
		}
		return OrderListPathNewest(files);
	}

	private List<Path> OrderListPathNewest(List<Path> listPath){
		Collections.sort(listPath, new Comparator<Path>() {
		    public int compare(Path o1, Path o2) {
		        try {
		            return Files.getLastModifiedTime(o2).compareTo(Files.getLastModifiedTime(o1));
		        } catch (IOException e) {
		        	return 0;
		        }
		    }
		});
		return listPath;
	}
	
	@Override
	public Queue<String> getPossibleFile(List<Path> paths, int minute, Date nowSystem) {
		Calendar nowCal = Calendar.getInstance();
		nowCal.setTime(nowSystem);
	    nowCal.add(Calendar.MINUTE, (minute*-1));
		Date now=new Date(nowCal.getTimeInMillis());
		Queue<String> possibleQueue=new ArrayDeque<>();
		boolean stopper=false;
		for(Path path:paths){
			try (SeekableByteChannel byteChannel = Files.newByteChannel(path, StandardOpenOption.READ)) {
				ByteBuffer byteBuffer = ByteBuffer.allocateDirect(512);
				Charset charset = Charset.defaultCharset();
				while (byteChannel.read(byteBuffer) > 0) {
					byteBuffer.flip();
					LogDetail logDetail = convertToLogProperties(charset.decode(byteBuffer).toString());
					if(((now.getTime()-logDetail.getDateCreated().getTime()) / (60 * 1000)) < 0)
						possibleQueue.add(path.toFile().getAbsolutePath());
					else stopper=true;
					byteBuffer.clear();
					break;
				}
				if(stopper) break;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return possibleQueue;
	}
	
	@Override
	public LogDetail convertToLogProperties(String line){
		String formatOne[] = line.split("\"");
		formatOne[0] = formatOne[0].replace("[", "").replace("]", "");
		List<String> data=new ArrayList<>();
		for(String perline:formatOne)
			for(String prop:perline.split(" "))
				if(prop.trim().length()>0) data.add(prop);
		LogDetail logDetail=new LogDetail();
		try {
			logDetail.setIp(data.get(0));
			logDetail.setUserIdentifier(data.get(1));
			logDetail.setUserName(data.get(2));
			logDetail.setDateCreated(new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss").parse(data.get(3)));
			logDetail.setHttpMethod(data.get(5));
			logDetail.setHttpPath(data.get(6));
			logDetail.setHttpVersion(data.get(7));
			logDetail.setHttpStatusCode(Integer.parseInt(data.get(8)));
			logDetail.setAdditionalInfo(data.get(9));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return logDetail;
	}

	@Override
	public void showLog(Queue<String> listPathPossiblePath, int minute, Date nowSystem) {
		Calendar nowCal = Calendar.getInstance();
		nowCal.setTime(nowSystem);
	    nowCal.add(Calendar.MINUTE, (minute*-1));
		Date now=new Date(nowCal.getTimeInMillis());
		for(String path:listPathPossiblePath){
			try(BufferedReader br = new BufferedReader(new FileReader(path))) {
			    for(String line; (line = br.readLine()) != null; ) {
					LogDetail logDetail = convertToLogProperties(line);
					if(((now.getTime()-logDetail.getDateCreated().getTime()) / (60 * 1000)) >= 0)
						System.out.println(line);
			    }
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}	
}
