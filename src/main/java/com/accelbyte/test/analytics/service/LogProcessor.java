package com.accelbyte.test.analytics.service;

import java.nio.file.Path;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Queue;

import com.accelbyte.test.analytics.model.LogDetail;

public interface LogProcessor{

	List<Path> getListFileByDirectory(String path);
	Queue<String> getPossibleFile(List<Path> paths, int minute, Date now);
	LogDetail convertToLogProperties(String line) throws ParseException;
	void showLog(Queue<String> listPathPossiblePath, int minute, Date now);
}
