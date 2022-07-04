/*
 *  * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.common;

/**
 * EncoderMonitoringMetric class defined the enum of the monitoring metrics
 *
 * @author Kevin / Symphony Dev Team<br>
 * Created on 4/19/2022
 * @since 1.0.0
 */
public enum EncoderMonitoringMetric {

	ROLE_BASED("RoleBased", false, true),
	ACCOUNT("Account", false, true),
	TEMPERATURE("Temperature", true, false),
	SYSTEM_INFORMATION("SystemInformation", true, false),
	AUDIO_STATISTICS("AudioStatistics", true, false),
	AUDIO_CONFIG("AudioConfig", true, false),
	VIDEO_STATISTICS("AudioStatistics", true, false),
	VIDEO_CONFIG("AudioConfig", true, false),
	STREAM_STATISTICS("StreamStatistics", true, false),
	INPUT("Vidin", true, true),
	STREAM_CONFIG("StreamConfig", true, false),
	STILL_IMAGE("StillImage", true, true),
	TALKBACK("TalkBack", true, true),
	SESSION("Session", true, false),
	SERVICE("Services", true, true);

	private final String name;
	private final boolean isMonitoring;
	private final boolean isControlling;

	/**
	 * EncoderMonitoringMetric instantiation
	 *
	 * @param name {@code {@link #name}}
	 * @param isMonitoring {@code {@link #isMonitoring}}
	 * @param isControlling {@code {@link #isControlling}}
	 */
	EncoderMonitoringMetric(String name, boolean isMonitoring, boolean isControlling) {
		this.name = name;
		this.isMonitoring = isMonitoring;
		this.isControlling = isControlling;
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

	/**
	 * Retrieves {@code {@link #isControlling}}
	 *
	 * @return value of {@link #isControlling}
	 */
	public boolean isControlling() {
		return isControlling;
	}
}