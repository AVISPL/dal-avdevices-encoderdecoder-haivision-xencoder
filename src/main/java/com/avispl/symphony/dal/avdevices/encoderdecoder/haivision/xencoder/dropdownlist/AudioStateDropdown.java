/*
 *  * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dropdownlist;

import java.util.HashMap;
import java.util.Map;

/**
 * StateDropdown class defined the enum for monitoring and controlling process
 *
 * @author Kevin / Symphony Dev Team<br>
 * Created on 4/25/2022
 * @since 1.0.0
 */
public enum AudioStateDropdown {

	STOPPED("Stopped", "STOPPED"),
	WORKING("Working", "WORKING"),
	MUTED("Muted", "MUTED"),
	FAILED("Failed", "FAILED");

	private final String name;
	private final String value;

	/**
	 * VideoDropdown instantiation
	 *
	 * @param name {@code {@link #name}}
	 * @param value {@code {@link #value}}
	 */
	AudioStateDropdown(String name, String value) {
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

	/**
	 * Retrieves name to value map of StateDropdown
	 *
	 * @return Map<String, String> are map value and name
	 */
	public static Map<String, String> getNameToValueMap() {
		Map<String, String> nameToValue = new HashMap<>();
		for (AudioStateDropdown stateDropdown : AudioStateDropdown.values()) {
			nameToValue.put(stateDropdown.getValue(), stateDropdown.getName());
		}
		return nameToValue;
	}
}