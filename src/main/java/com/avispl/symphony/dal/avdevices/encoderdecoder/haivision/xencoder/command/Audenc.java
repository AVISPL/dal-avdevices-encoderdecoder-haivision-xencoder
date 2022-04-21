package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.command;

/**
 * AccountCommand
 *
 * @author Kevin / Symphony Dev Team<br>
 * Created on 4/19/2022
 * @since 1.0.0
 */
public enum Audenc {
	AUDENC("audenc "),
	START("start "),
	STOP("stop "),
	SET("set "),
	GET("get "),
	ALL("all "),
	STATS("stats"),
	CONFIG("config");

	private final String name;

	/**
	 * Audenc command
	 *
	 * @param name {@code {@link #name}}
	 */
	Audenc(String name) {
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
