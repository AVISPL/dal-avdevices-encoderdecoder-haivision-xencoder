/*
 *  * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dropdownlist;

/**
 * AspectRatioDropdown class defined the enum for monitoring and controlling process
 *
 * @author Kevin / Symphony Dev Team<br>
 * Created on 5/10/2022
 * @since 1.0.0
 */
public enum AspectRatioDropdown {

	SPECT_RATIO_0("Automatic"),
	ASPECT_RATIO_1("WSS/AFD"),
	ASPECT_RATIO_13("3:2"),
	ASPECT_RATIO_2("4:3"),
	ASPECT_RATIO_3("5:3"),
	ASPECT_RATIO_4("5:4"),
	ASPECT_RATIO_5("16:9"),
	ASPECT_RATIO_14("16:10"),
	ASPECT_RATIO_15("17:9");

	private final String name;

	/**
	 * AspectRatioDropdown instantiation
	 *
	 * @param name {@code {@link #name}}
	 */
	AspectRatioDropdown(String name) {
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