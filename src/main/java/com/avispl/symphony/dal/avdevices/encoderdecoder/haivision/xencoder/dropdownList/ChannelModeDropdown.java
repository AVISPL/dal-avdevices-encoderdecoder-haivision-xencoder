/*
 *  * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dropdownList;

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

	STEREO("Stereo", "stereo"),
	MONO_LEFT("Mono Left", "mono"),
	MONO_RIGHT("Mono Right", "monoright");

	private final String name;
	private final String value;

	/**
	 * ChannelModeDropdown instantiation
	 *
	 * @param name {@code {@link #name}}
	 */
	ChannelModeDropdown(String name, String value) {
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
	 * Retrieves name to value map of ChannelModeDropdown
	 *
	 * @return Map<String, String> are map name and value
	 */
	public static Map<String, String> getValueToNameMap() {
		Map<String, String> valueToName = new HashMap<>();
		for (ChannelModeDropdown channelModeDropdown : ChannelModeDropdown.values()) {
			valueToName.put(channelModeDropdown.getName(), channelModeDropdown.getValue());
		}
		return valueToName;
	}
}