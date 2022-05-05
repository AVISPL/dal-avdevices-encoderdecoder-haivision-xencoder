/*
 *  * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dropdownlist;

import java.util.HashMap;
import java.util.Map;

/**
 * ChannelModeDropdown class defined the enum for monitoring and controlling process
 *
 * @author Kevin / Symphony Dev Team<br>
 * Created on 4/25/2022
 * @since 1.0.0
 */
public enum ChannelModeDropdown {

	STEREO("Stereo", "Stereo","stereo"),
	MONO_LEFT("Mono Left", "Mono","mono"),
	MONO_RIGHT("Mono Right", "Mono (Right Channel)","monoright");

	private final String name;
	private final String value;
	private final String paramValue;

	/**
	 * ChannelModeDropdown instantiation
	 *
	 * @param name {@code {@link #name}}
	 * @param value {@code {@link #value}}
	 * @param paramValue {@code {@link #paramValue}}
	 */
	ChannelModeDropdown(String name, String value,String paramValue) {
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
	 * Retrieves param value to value name of ChannelModeDropdown
	 *
	 * @return Map<String, String> are map param value and value name
	 */
	public static Map<String, String> getParamValueToNameMap() {
		Map<String, String> nameMap = new HashMap<>();
		for (ChannelModeDropdown channelModeDropdown : ChannelModeDropdown.values()) {
			nameMap.put(channelModeDropdown.getName(), channelModeDropdown.getParamValue());
		}
		return nameMap;
	}
}