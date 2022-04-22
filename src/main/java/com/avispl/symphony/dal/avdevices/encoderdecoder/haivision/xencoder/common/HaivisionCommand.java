package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.common;

/**
 * HaivisionCommand class defined the enum for the monitoring process
 *
 * @author Kevin / Symphony Dev Team<br>
 * Created on 4/22/2022
 * @since 1.0.0
 */
public enum HaivisionCommand {

	STREAM("stream "),
	CREATE("create "),
	START("start "),
	STOP("stop "),
	PAUSE("pause "),
	RESUME("resume "),
	DELETE("delete "),
	ALL("all "),
	STATS("stats "),
	CONFIG("config "),
	GET("get "),
	VIDENC("videnc "),
	SET("set "),
	HAIVERSION("haiversion"),
	STATUS("status "),
	AUDENC("audenc "),
	ACCOUNT("account ");

	private final String name;

	/**
	 * Service command
	 *
	 * @param name {@code {@link #name}}
	 */
	HaivisionCommand(String name) {
		this.name = name;
	}

	/**
	 * Retrieves {@code {@link #name}}
	 *
	 * @return value of {@link #name}
	 */
	public String getName() {
		return name;
	}
}