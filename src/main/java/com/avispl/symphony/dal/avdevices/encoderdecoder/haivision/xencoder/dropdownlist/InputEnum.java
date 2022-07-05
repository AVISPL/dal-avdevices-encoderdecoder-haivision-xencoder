/*
 *  * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dropdownlist;

/**
 * InputEnum class defined the enum for monitoring and controlling process
 *
 * @author Kevin / Symphony Dev Team<br>
 * Created on 4/25/2022
 * @since 1.0.0
 */
public enum InputEnum {

	ANALOG("Analog", "Analog"),
	SDI_1_1_2("SDI 1 (1-2)", "SDI1Ch1+2"),
	SDI_1_3_4("SDI 1 (3-4)", "SDI1Ch3+4"),
	SDI_1_5_6("SDI 1 (5-6)", "SDI1Ch5+6"),
	SDI_1_7_8("SDI 1 (7-8)", "SDI1Ch7+8"),
	SDI_1_9_10("SDI 1 (9-10)", "SDI1Ch9+10"),
	SDI_1_11_12("SDI 1 (11-12)", "SDI1Ch11+12"),
	SDI_1_13_14("SDI 1 (13-14)", "SDI1Ch13+14"),
	SDI_1_15_16("SDI 1 (15-16)", "SDI1Ch15+16"),
	SDI_2_1_2("SDI 2 (1-2)", "SDI2Ch1+2"),
	SDI_2_3_4("SDI 2 (3-4)", "SDI2Ch3+4"),
	SDI_2_5_6("SDI 2 (5-6)", "SDI2Ch5+6"),
	SDI_2_7_8("SDI 2 (7-8)", "SDI2Ch7+8"),
	SDI_2_9_10("SDI 2 (9-10)", "SDI2Ch9+10"),
	SDI_2_11_12("SDI 2 (11-12)", "SDI2Ch11+12"),
	SDI_2_13_14("SDI 2 (13-14)", "SDI2Ch13+14"),
	SDI_2_15_16("SDI 2 (15-16)", "SDI2Ch15+16");

	private final String name;
	private final String value;

	/**
	 * InputEnum instantiation
	 *
	 * @param name {@code {@link #name}}
	 * @param value {@code {@link #value}}
	 */
	InputEnum(String name, String value) {
		this.name = name;
		this.value = value;
	}

	/**
	 * Retrieves {@code {@link #name}}
	 *
	 * @return value of {@link #name}
	 */
	public String getName() {
		return name;
	}

	/**
	 * Retrieves {@code {@link #value}}
	 *
	 * @return value of {@link #value}
	 */
	public String getValue() {
		return value;
	}
}