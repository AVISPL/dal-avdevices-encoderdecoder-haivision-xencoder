/*
 *  * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dropdownlist;

/**
 * ConnectionModeEnum class defined the enum for monitoring and controlling process
 *
 * @author Kevin / Symphony Dev Team<br>
 * Created on 5/24/2022
 * @since 1.0.0
 */
public enum ConnectionModeEnum {

	CALLER("Caller"),
	LISTENER("Listener"),
	RENDEZVOUS("Rendezvous");

	private final String name;

	/**
	 * ConnectionModeEnum instantiation
	 *
	 * @param name {@code {@link #name}}
	 */
	ConnectionModeEnum(String name) {
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
