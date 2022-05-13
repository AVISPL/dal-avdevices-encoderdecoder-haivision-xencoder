/*
 *  * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dropdownlist;

/**
 * FramingDropdown class defined the enum for monitoring and controlling process
 *
 * @author Kevin / Symphony Dev Team<br>
 * Created on 5/10/2022
 * @since 1.0.0
 */
public enum FramingDropdown {

	I("I"),
	IP("IP"),
	IBP("IBP"),
	IBBP("IBBP"),
	IBBBP("IBBBP");

	private final String name;

	/**
	 * FramingDropdown instantiation
	 *
	 * @param name {@code {@link #name}}
	 */
	FramingDropdown(String name) {
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