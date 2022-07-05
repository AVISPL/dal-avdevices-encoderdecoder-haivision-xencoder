/*
 *  * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dropdownlist;

/**
 * AudioLevel class defined the enum for monitoring and controlling process
 *
 * @author Kevin / Symphony Dev Team<br>
 * Created on 4/27/2022
 * @since 1.0.0
 */
public enum AudioLevel {

	LEVEL_5("5"),
	LEVEL_6("6"),
	LEVEL_7("7"),
	LEVEL_8("8"),
	LEVEL_9("9"),
	LEVEL_10("10"),
	LEVEL_11("11"),
	LEVEL_12("12"),
	LEVEL_13("13"),
	LEVEL_14("14"),
	LEVEL_15("15"),
	LEVEL_16("16"),
	LEVEL_17("17"),
	LEVEL_18("18"),
	LEVEL_19("19"),
	LEVEL_20("20");

	private final String name;

	/**
	 * AudioLevel instantiation
	 *
	 * @param name {@code {@link #name}}
	 */
	AudioLevel(String name) {
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