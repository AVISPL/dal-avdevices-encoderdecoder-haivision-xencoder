package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.command;

/**
 * DeviceInfoCommand
 *
 * @author Kevin / Symphony Dev Team<br>
 * Created on 4/19/2022
 * @since 1.0.0
 */
public enum SystemInformation {
	HAIVERSION("haiversion");

	private final String name;

	/**
	 * Haiversion command
	 *
	 * @param name {@code {@link #name}}
	 */
	SystemInformation(String name) {
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
