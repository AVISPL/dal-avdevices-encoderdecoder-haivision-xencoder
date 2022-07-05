/*
 *  * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dropdownlist;

/**
 * FramingEnum class defined the enum for monitoring and controlling process
 *
 * @author Kevin / Symphony Dev Team<br>
 * Created on 5/10/2022
 * @since 1.0.0
 */
public enum FramingEnum {

	I("I"),
	IP("IP"),
	IBP("IBP"),
	IBBP("IBBP");

	private final String name;

	/**
	 * FramingEnum instantiation
	 *
	 * @param name {@code {@link #name}}
	 */
	FramingEnum(String name) {
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