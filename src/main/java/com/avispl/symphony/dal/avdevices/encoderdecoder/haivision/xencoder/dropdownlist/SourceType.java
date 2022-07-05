package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dropdownlist;

/**
 * SourceType
 *
 * @author Kevin / Symphony Dev Team<br>
 * Created on 5/27/2022
 * @since 1.0.0
 */
public enum SourceType {

	AUDIO("Audio"),
	VIDEO("Video");

	private final String name;

	/**
	 * SourceType instantiation
	 *
	 * @param name {@code {@link #name}}
	 */
	SourceType(String name) {
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