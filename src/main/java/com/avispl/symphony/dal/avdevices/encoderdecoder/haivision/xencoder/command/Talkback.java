package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.command;

/**
 * TalkbackCommand
 *
 * @author Kevin / Symphony Dev Team<br>
 * Created on 4/19/2022
 * @since 1.0.0
 */
public enum Talkback {
	START("start "),
	STOP("stop "),
	SET("set "),
	GET("get ");

	private final String name;

	/**
	 * Talkback command
	 *
	 * @param name {@code {@link #name}}
	 */
	Talkback(String name) {
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
