/*
 *  * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dropdownList;

import java.util.HashMap;
import java.util.Map;

/**
 * AlgorithmDropdown class defined the enum for monitoring and controlling process
 *
 * @author Kevin / Symphony Dev Team<br>
 * Created on 4/25/2022
 * @since 1.0.0
 */
public enum AlgorithmDropdown {

	MPEG_2("MPEG-2 ADTS", "MPEG2-ADTS", "adts"),
	MPEG_4("MPEG-4 LOAS/LATM", "LOAS", "loas");

	private final String name;
	private final String value;
	private final String paramValue;

	/**
	 * AlgorithmDropdown instantiation
	 *
	 * @param name {@code {@link #name}}
	 * @param value {@code {@link #value}}
	 * @param paramValue {@code {@link #paramValue}}
	 */
	AlgorithmDropdown(String name, String value, String paramValue) {
		this.name = name;
		this.value = value;
		this.paramValue = paramValue;
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
	 * Retrieves {@code {@link #paramValue}}
	 *
	 * @return value of {@link #paramValue}
	 */
	public String getParamValue() {
		return paramValue;
	}

	/**
	 * Retrieves param value to name map of AlgorithmDropdown
	 *
	 * @return Map<String, String> are map param value and value
	 */
	public static Map<String, String> getParamValueToValueNameMap() {
		Map<String, String> valueToName = new HashMap<>();
		for (AlgorithmDropdown algorithmDropdown : AlgorithmDropdown.values()) {
			valueToName.put(algorithmDropdown.getName(), algorithmDropdown.getParamValue());
		}
		return valueToName;
	}

	/**
	 * Retrieves name to value map of AlgorithmDropdown
	 *
	 * @return Map<String, String> are map name and value
	 */
	public static Map<String, String> getNameToValueMap() {
		Map<String, String> nameToValue = new HashMap<>();
		for (AlgorithmDropdown algorithmDropdown : AlgorithmDropdown.values()) {
			nameToValue.put(algorithmDropdown.getValue(), algorithmDropdown.getName());
		}
		return nameToValue;
	}
}