/*
 *  * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dropdownList;

/**
 * InputDropdown class defined the enum for monitoring and controlling process
 *
 * @author Kevin / Symphony Dev Team<br>
 * Created on 4/25/2022
 * @since 1.0.0
 */
public enum InputDropdown {

	ANALOG("Analog"),
	SDI_1_1_2("SDI 1 (1-2)"),
	SDI_1_3_4("SDI 1 (3-4)"),
	SDI_1_5_6("SDI 1 (5-6)"),
	SDI_1_7_8("SDI 1 (7-8)"),
	SDI_1_9_10("SDI 1 (9-10)"),
	SDI_1_11_12("SDI 1 (11-12) "),
	SDI_1_13_14("SDI 1 (13-14)"),
	SDI_1_15_16("SDI 1 (15-16)");

	private final String name;

	/**
	 * InputDropdown instantiation
	 *
	 * @param name {@code {@link #name}}
	 */
	InputDropdown(String name) {
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