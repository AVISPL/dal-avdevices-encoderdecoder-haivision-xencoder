/*
 *  * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dropdownlist;

/**
 * StreamStateEnum class defined the enum for monitoring and controlling process
 *
 * @author Kevin / Symphony Dev Team<br>
 * Created on 6/1/2022
 * @since 1.0.0
 */
public enum StreamStateEnum {

	STREAMING("STREAMING"),
	PAUSED("PAUSED"),
	RESOLVING("RESOLVING"),
	CONNECTING("CONNECTING"),
	LISTENING("LISTENING"),
	FAILED("FAILED"),
	SCRAMBLED("SCRAMBLED"),
	PUBLISHING("PUBLISHING"),
	SECURING("SECURING"),
	STOPPED("STOPPED");

	private final String name;

	/**
	 * StreamStateEnum instantiation
	 *
	 * @param name {@code {@link #name}}
	 */
	StreamStateEnum(String name) {
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