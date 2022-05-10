/*
 *  * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dropdownlist;

/**
 * EntropyCodingDropdown
 *
 * @author Kevin / Symphony Dev Team<br>
 * Created on 5/10/2022
 * @since 1.0.0
 */
public enum EntropyCodingDropdown {

	CAVLC("CAVLC"),
	CABAC("WSS/CABAC");

	private final String name;

	/**
	 * EntropyCodingDropdown instantiation
	 *
	 * @param name {@code {@link #name}}
	 */
	EntropyCodingDropdown(String name) {
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