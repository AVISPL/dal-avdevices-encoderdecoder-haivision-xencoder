/*
 *  * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.common;

/**
 * HaivisionMonitoringMetric class defined the enum for the monitoring process
 *
 * @author Kevin / Symphony Dev Team<br>
 * Created on 4/19/2022
 * @since 1.0.0
 */
public enum HaivisionMonitoringMetric {

	ROLE_BASED("RoleBased", false),
	ACCOUNT("Account", false),
	TEMPERATURE("TemperatureStatus", true),
	SYSTEM_INFORMATION("SystemInformation", true),
	AUDIO_STATISTICS("AudioStatistics", true),
	AUDIO_CONFIG("AudioConfig", true),
	VIDEO_STATISTICS("AudioStatistics", true),
	VIDEO_CONFIG("AudioConfig", true),
	STREAM_STATISTICS("StreamStatistics", true),
	STREAM_CONFIG("StreamConfig", true);

	private final String name;
	private final boolean isMonitoring;

	/**
	 * AxisMonitoringMetric instantiation
	 *
	 * @param name {@code {@link #name}}
	 * @param isMonitoring {@code {@link #isMonitoring}}
	 */
	HaivisionMonitoringMetric(String name, boolean isMonitoring) {
		this.name = name;
		this.isMonitoring = isMonitoring;
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
	 * Retrieves {@code {@link #isMonitoring}}
	 *
	 * @return value of {@link #isMonitoring}
	 */
	public boolean isMonitoring() {
		return isMonitoring;
	}
}