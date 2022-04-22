package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dropdownList;

import java.util.HashMap;
import java.util.Map;

/**
 * AudioStateDropdown class defined the enum for monitoring and controlling process
 *
 * @author Kevin / Symphony Dev Team<br>
 * Created on 4/22/2022
 * @since 1.0.0
 */
public enum AudioStateDropdown {
	STOPPED("STOPPED", 0),
	WORKING("WORKING", 3),
	MUTED("MUTED", 67),
	FAILED("FAILED", 128);

	private final String name;
	private final int value;

	/**
	 * VideoDropdown instantiation
	 *
	 * @param name {@code {@link #name}}
	 * @param value {@code {@link #value}}
	 */
	AudioStateDropdown(String name, int value) {
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
	public int getValue() {
		return value;
	}

	/**
	 * Retrieves name to value map of StateDropdown
	 *
	 * @return Map<Integer, String> are map value and name
	 */
	public static Map<Integer, String> getNameToValueMap() {
		Map<Integer, String> nameToValue = new HashMap<>();
		for (AudioStateDropdown stateDropdown : AudioStateDropdown.values()) {
			nameToValue.put(stateDropdown.getValue(), stateDropdown.getName());
		}
		return nameToValue;
	}

	/**
	 * Retrieves name to value map of stateDropdown
	 *
	 * @return Map<String, Integer> are map name and value
	 */
	public static Map<String, Integer> getValueToNameMap() {
		Map<String, Integer> valueToName = new HashMap<>();
		for (AudioStateDropdown stateDropdown : AudioStateDropdown.values()) {
			valueToName.put(stateDropdown.getName(), stateDropdown.getValue());
		}
		return valueToName;
	}
}