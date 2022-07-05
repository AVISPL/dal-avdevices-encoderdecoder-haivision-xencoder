/*
 *  * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.common;

/**
 * VideoMonitoringMetric class defined the enum of the monitoring metrics
 *
 * @author Kevin / Symphony Dev Team<br>
 * Created on 4/19/2022
 * @since 1.0.0
 */
public enum VideoMonitoringMetric {

	STATE("State"),
	UPTIME("UpTime"),
	INPUT_PRESENT("InputPresent"),
	ENCODER_FRAMES("EncodedFrames"),
	ENCODER_BYTES("EncodedBytes"),
	ENCODER_FRAME_RATE("EncodedFrameRate"),
	DROPPED_FRAMES("DroppedFrames"),
	ENCODER_BITRATE("EncodedBitrate (kbps)"),
	ENCODER_RESETS("EncoderResets"),
	ENCODER_PTS("EncoderPTS"),
	ENCODER_LOAD("EncoderLoad (%)"),
	CLOSED_CAPTIONING("ClosedCaptioning"),
	PROFILE("H.264Profile"),
	LEVEL("H.264Level"),
	TIME_CODE_SOURCE("TimeCodeSource"),
	TIME_CODE("TimeCode"),
	EXTRACTED_CC_BYTES("ExtractedCCBytes"),
	CC_ERRORS("CCErrors"),
	EXTRACTED_CSD_BYTES("ExtractedCSDBytes"),
	INPUT_TYPE("InputType"),
	INPUT_FORMAT("InputFormat"),
	ASPECT_RATIO("AspectRatio"),
	RESOLUTION("OutputResolution");

	public final String name;

	/**
	 * VideoMonitoringMetric instantiation
	 *
	 * @param name {@code {@link #name}}
	 */
	VideoMonitoringMetric(String name) {
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