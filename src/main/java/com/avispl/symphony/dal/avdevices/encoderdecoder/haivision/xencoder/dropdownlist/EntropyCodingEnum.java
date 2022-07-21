/*
 *  * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dropdownlist;

/**
 * EntropyCodingEnum class defined the enum for monitoring and controlling process
 *
 * @author Kevin / Symphony Dev Team<br>
 * Created on 5/10/2022
 * @since 1.0.0
 */
public enum EntropyCodingEnum {

	CAVLC("CAVLC"),
	CABAC("CABAC");

	private final String name;

	/**
	 * EntropyCodingEnum instantiation
	 *
	 * @param name {@code {@link #name}}
	 */
	EntropyCodingEnum(String name) {
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