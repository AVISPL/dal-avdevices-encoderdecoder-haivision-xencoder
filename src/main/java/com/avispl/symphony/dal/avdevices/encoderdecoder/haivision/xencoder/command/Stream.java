package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.command;

/**
 * StreamCommand
 *
 * @author Kevin / Symphony Dev Team<br>
 * Created on 4/19/2022
 * @since 1.0.0
 */
public enum Stream {
	CREATE("create "),
	START("start "),
	STOP("stop "),
	PAUSE("pause "),
	RESUME("resume "),
	DELETE("delete "),
	GET("get ");

	private final String name;

	/**
	 * Stream command
	 *
	 * @param name {@code {@link #name}}
	 */
	Stream(String name) {
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
