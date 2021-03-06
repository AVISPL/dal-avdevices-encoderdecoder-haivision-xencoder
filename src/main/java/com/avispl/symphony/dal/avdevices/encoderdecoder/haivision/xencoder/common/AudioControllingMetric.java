/*
 *  * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.common;

/**
 * AudioControllingMetric class defined the enum of the controlling metrics
 *
 * @author Kevin / Symphony Dev Team<br>
 * Created on 4/25/2022
 * @since 1.0.0
 */
public enum AudioControllingMetric {

	STATE("State"),
	INPUT("Input"),
	CHANGE_MODE("ChangeMode"),
	BITRATE("BitRate"),
	SAMPLE_RATE("SampleRate"),
	ALGORITHM("ACC-LC Algorithm"),
	ACTION("Action"),
	APPLY_CHANGE("ApplyChange"),
	LANGUAGE("Language"),
	LEVEL("0dBFSAudioLevel"),
	CANCEL("Cancel");

	private final String name;

	/**
	 * AudioControllingMetric instantiation
	 *
	 * @param name {@code {@link #name}}
	 */
	AudioControllingMetric(String name) {
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

	/**
	 * Get name of enum by metric
	 *
	 * @param name {@code {@link #name}}
	 * @return name of metric
	 * @throws IllegalArgumentException if can not find the enum with name
	 */
	public static AudioControllingMetric getByName(String name) {
		for (AudioControllingMetric metric : AudioControllingMetric.values()) {
			if (metric.getName().equals(name)) {
				return metric;
			}
		}
		throw new IllegalArgumentException("Can not find the enum with name: " + name);
	}
}