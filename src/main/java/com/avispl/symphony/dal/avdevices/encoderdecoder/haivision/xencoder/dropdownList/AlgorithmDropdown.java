/*
 *  * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dropdownList;

import java.util.HashMap;
import java.util.Map;

/**
 * AlgorithmDropdown class defined the enum for monitoring and controlling process
 *
 * @author Kevin / Symphony Dev Team<br>
 * Created on 4/25/2022
 * @since 1.0.0
 */
public enum AlgorithmDropdown {

	MPEG_2("MPEG-2 ADTS"),
	MPEG_4("MPEG-4 LOAS/LATM");

	private final String name;

	/**
	 * AlgorithmDropdown instantiation
	 *
	 * @param name {@code {@link #name}}
	 */
	AlgorithmDropdown(String name) {
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