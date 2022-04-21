package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.command;

/**
 * ServiceCommand
 *
 * @author Kevin / Symphony Dev Team<br>
 * Created on 4/19/2022
 * @since 1.0.0
 */
public enum Service {
	START("start "),
	STOP("stop "),
	STATUS("status ");

	private final String name;

	/**
	 * Service command
	 *
	 * @param name {@code {@link #name}}
	 */
	Service(String name) {
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
