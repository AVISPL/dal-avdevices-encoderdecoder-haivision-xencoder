/*
 *  * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dropdownList;

/**
 * ChannelModeDropdown class defined the enum for monitoring and controlling process
 *
 * @author Kevin / Symphony Dev Team<br>
 * Created on 4/25/2022
 * @since 1.0.0
 */
public enum ChannelModeDropdown {

	STEREO("Stereo"),
	MONO_LEFT("Mono Left"),
	MONO_RIGHT("Mono Right");

	private final String name;

	/**
	 * ChannelModeDropdown instantiation
	 *
	 * @param name {@code {@link #name}}
	 */
	ChannelModeDropdown(String name) {
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