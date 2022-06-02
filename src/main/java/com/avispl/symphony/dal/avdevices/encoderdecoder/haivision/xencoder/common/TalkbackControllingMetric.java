/*
 *  * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.common;

/**
 * TalkbackControllingMetric class defined the enum of the controlling metrics
 *
 * @author Kevin / Symphony Dev Team<br>
 * Created on 5/31/2022
 * @since 1.0.0
 */
public enum TalkbackControllingMetric {

	ACTIVE("Active"),
	PORT("Port"),
	APPLY_CHANGE("ApplyChange"),
	CANCEL("Cancel");

	private final String name;

	/**
	 * TalkbackControllingMetric instantiation
	 *
	 * @param name {@code {@link #name}}
	 */
	TalkbackControllingMetric(String name) {
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
	public static TalkbackControllingMetric getByName(String name) {
		for (TalkbackControllingMetric metric : TalkbackControllingMetric.values()) {
			if (metric.getName().equals(name)) {
				return metric;
			}
		}
		throw new IllegalArgumentException("Can not find the enum with name: " + name);
	}
}
