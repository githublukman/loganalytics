package com.accelbyte.test.analytics.model;

import com.beust.jcommander.Parameter;

public class CommandArgument {

	@Parameter(names={"--min", "-m"}, description = "minute from now", required = true)
    private Integer min;
    @Parameter(names={"--dir", "-d"}, description = "absolute path of direcotry", required = true)
    private String directory;
	public Integer getMin() {
		return min;
	}
	public void setMin(Integer min) {
		this.min = min;
	}
	public String getDirectory() {
		return directory;
	}
	public void setDirectory(String directory) {
		this.directory = directory;
	}
}
