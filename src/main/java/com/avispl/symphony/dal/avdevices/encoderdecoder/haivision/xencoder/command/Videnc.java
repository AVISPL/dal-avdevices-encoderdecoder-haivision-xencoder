package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.command;

/**
 * TalkbackCommand
 *
 * @author Kevin / Symphony Dev Team<br>
 * Created on 4/19/2022
 * @since 1.0.0
 */
public enum Videnc {
	VIDENC("videnc "),
	START("start "),
	STOP("stop "),
	SET("set "),
	GET("get "),
	ALL("all "),
	STATS("stats "),
	CONFIG("config ");

	private final String name;

	/**
	 * Talkback command
	 *
	 * @param name {@code {@link #name}}
	 */
	Videnc(String name) {
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
