/*
 *  * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dto.video;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.common.EncoderConstant;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.common.VideoMonitoringMetric;

/**
 * VideoStatistics DTO class
 *
 * @author Kevin / Symphony Dev Team<br>
 * Created on 4/19/2022
 * @since 1.0.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class VideoStatistics {

	@JsonAlias("Name")
	private String name;

	@JsonAlias("State")
	private String state;

	@JsonAlias("Up Time")
	private String upTime;

	@JsonAlias("Input Present")
	private String inputPresent;

	@JsonAlias("Encoded Frames")
	private String encodedFrames;

	@JsonAlias("Encoded Bytes")
	private String encodedBytes;

	@JsonAlias("Encoded Frame Rate")
	private String encodedFrameRate;

	@JsonAlias("Dropped Frames")
	private String droppedFrames;

	@JsonAlias("Encoded Bitrate")
	private String encodedBitrate;

	@JsonAlias("Encoder Resets")
	private String encoderResets;

	@JsonAlias("Encoder PTS")
	private String encoderPTS;

	@JsonAlias("Encoder Load")
	private String encoderLoad;

	@JsonAlias("Closed Captioning")
	private String closedCaptioning;

	@JsonAlias("H.264 Profile")
	private String profile;

	@JsonAlias("H.264 Level")
	private String level;

	@JsonAlias("Timecode Source")
	private String timeCodeSource;

	@JsonAlias("Timecode")
	private String timeCode;

	/**
	 * Retrieves {@code {@link #name}}
	 *
	 * @return value of {@link #name}
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets {@code name}
	 *
	 * @param name the {@code java.lang.String} field
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Retrieves {@code {@link #state}}
	 *
	 * @return value of {@link #state}
	 */
	public String getState() {
		return state;
	}

	/**
	 * Sets {@code state}
	 *
	 * @param state the {@code java.lang.String} field
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * Retrieves {@code {@link #upTime}}
	 *
	 * @return value of {@link #upTime}
	 */
	public String getUpTime() {
		return upTime;
	}

	/**
	 * Sets {@code upTime}
	 *
	 * @param upTime the {@code java.lang.String} field
	 */
	public void setUpTime(String upTime) {
		this.upTime = upTime;
	}

	/**
	 * Retrieves {@code {@link #inputPresent}}
	 *
	 * @return value of {@link #inputPresent}
	 */
	public String getInputPresent() {
		return inputPresent;
	}

	/**
	 * Sets {@code inputPresent}
	 *
	 * @param inputPresent the {@code java.lang.String} field
	 */
	public void setInputPresent(String inputPresent) {
		this.inputPresent = inputPresent;
	}

	/**
	 * Retrieves {@code {@link #encodedFrames}}
	 *
	 * @return value of {@link #encodedFrames}
	 */
	public String getEncodedFrames() {
		return encodedFrames;
	}

	/**
	 * Sets {@code encodedFrames}
	 *
	 * @param encodedFrames the {@code java.lang.String} field
	 */
	public void setEncodedFrames(String encodedFrames) {
		this.encodedFrames = encodedFrames;
	}

	/**
	 * Retrieves {@code {@link #encodedBytes}}
	 *
	 * @return value of {@link #encodedBytes}
	 */
	public String getEncodedBytes() {
		return encodedBytes;
	}

	/**
	 * Sets {@code encodedBytes}
	 *
	 * @param encodedBytes the {@code java.lang.String} field
	 */
	public void setEncodedBytes(String encodedBytes) {
		this.encodedBytes = encodedBytes;
	}

	/**
	 * Retrieves {@code {@link #encodedFrameRate}}
	 *
	 * @return value of {@link #encodedFrameRate}
	 */
	public String getEncodedFrameRate() {
		return encodedFrameRate;
	}

	/**
	 * Sets {@code encodedFrameRate}
	 *
	 * @param encodedFrameRate the {@code java.lang.String} field
	 */
	public void setEncodedFrameRate(String encodedFrameRate) {
		this.encodedFrameRate = encodedFrameRate;
	}

	/**
	 * Retrieves {@code {@link #droppedFrames}}
	 *
	 * @return value of {@link #droppedFrames}
	 */
	public String getDroppedFrames() {
		return droppedFrames;
	}

	/**
	 * Sets {@code droppedFrames}
	 *
	 * @param droppedFrames the {@code java.lang.String} field
	 */
	public void setDroppedFrames(String droppedFrames) {
		this.droppedFrames = droppedFrames;
	}

	/**
	 * Retrieves {@code {@link #encodedBitrate}}
	 *
	 * @return value of {@link #encodedBitrate}
	 */
	public String getEncodedBitrate() {
		return encodedBitrate;
	}

	/**
	 * Sets {@code encodedBitrate}
	 *
	 * @param encodedBitrate the {@code java.lang.String} field
	 */
	public void setEncodedBitrate(String encodedBitrate) {
		this.encodedBitrate = encodedBitrate;
	}

	/**
	 * Retrieves {@code {@link #encoderResets}}
	 *
	 * @return value of {@link #encoderResets}
	 */
	public String getEncoderResets() {
		return encoderResets;
	}

	/**
	 * Sets {@code encoderResets}
	 *
	 * @param encoderResets the {@code java.lang.String} field
	 */
	public void setEncoderResets(String encoderResets) {
		this.encoderResets = encoderResets;
	}

	/**
	 * Retrieves {@code {@link #encoderPTS}}
	 *
	 * @return value of {@link #encoderPTS}
	 */
	public String getEncoderPTS() {
		return encoderPTS;
	}

	/**
	 * Sets {@code encoderPTS}
	 *
	 * @param encoderPTS the {@code java.lang.String} field
	 */
	public void setEncoderPTS(String encoderPTS) {
		this.encoderPTS = encoderPTS;
	}

	/**
	 * Retrieves {@code {@link #encoderLoad}}
	 *
	 * @return value of {@link #encoderLoad}
	 */
	public String getEncoderLoad() {
		return encoderLoad;
	}

	/**
	 * Sets {@code encoderLoad}
	 *
	 * @param encoderLoad the {@code java.lang.String} field
	 */
	public void setEncoderLoad(String encoderLoad) {
		this.encoderLoad = encoderLoad;
	}

	/**
	 * Retrieves {@code {@link #closedCaptioning}}
	 *
	 * @return value of {@link #closedCaptioning}
	 */
	public String getClosedCaptioning() {
		return closedCaptioning;
	}

	/**
	 * Sets {@code closedCaptioning}
	 *
	 * @param closedCaptioning the {@code java.lang.String} field
	 */
	public void setClosedCaptioning(String closedCaptioning) {
		this.closedCaptioning = closedCaptioning;
	}

	/**
	 * Retrieves {@code {@link #profile}}
	 *
	 * @return value of {@link #profile}
	 */
	public String getProfile() {
		return profile;
	}

	/**
	 * Sets {@code profile}
	 *
	 * @param profile the {@code java.lang.String} field
	 */
	public void setProfile(String profile) {
		this.profile = profile;
	}

	/**
	 * Retrieves {@code {@link #level}}
	 *
	 * @return value of {@link #level}
	 */
	public String getLevel() {
		return level;
	}

	/**
	 * Sets {@code level}
	 *
	 * @param level the {@code java.lang.String} field
	 */
	public void setLevel(String level) {
		this.level = level;
	}

	/**
	 * Retrieves {@code {@link #timeCodeSource}}
	 *
	 * @return value of {@link #timeCodeSource}
	 */
	public String getTimeCodeSource() {
		return timeCodeSource;
	}

	/**
	 * Sets {@code timeCodeSource}
	 *
	 * @param timeCodeSource the {@code java.lang.String} field
	 */
	public void setTimeCodeSource(String timeCodeSource) {
		this.timeCodeSource = timeCodeSource;
	}

	/**
	 * Retrieves {@code {@link #timeCode}}
	 *
	 * @return value of {@link #timeCode}
	 */
	public String getTimeCode() {
		return timeCode;
	}

	/**
	 * Sets {@code timeCode}
	 *
	 * @param timeCode the {@code java.lang.String} field
	 */
	public void setTimeCode(String timeCode) {
		this.timeCode = timeCode;
	}

	/**
	 * Get the value by the metric monitoring
	 *
	 * @param metric the metric is metric monitoring
	 * @return String value of encoder monitoring properties by metric
	 */
	public String getValueByMetric(VideoMonitoringMetric metric) {
		switch (metric) {
			case STATE:
				return getState();
			case UPTIME:
				return getUpTime();
			case INPUT_PRESENT:
				return getInputPresent();
			case ENCODER_FRAMES:
				return getEncodedFrames();
			case ENCODER_BYTES:
				return getEncodedBytes();
			case ENCODER_FRAME_RATE:
				return getEncodedFrameRate();
			case DROPPED_FRAMES:
				return getDroppedFrames();
			case ENCODER_BITRATE:
				return getEncodedBitrate();
			case ENCODER_RESETS:
				return getEncoderResets();
			case ENCODER_PTS:
				return getEncoderPTS();
			case ENCODER_LOAD:
				return getEncoderLoad();
			case CLOSED_CAPTIONING:
				return getClosedCaptioning();
			case PROFILE:
				return getProfile();
			case LEVEL:
				return getLevel();
			case TIME_CODE_SOURCE:
				return getTimeCodeSource();
			case TIME_CODE:
				return getTimeCode();
			default:
				return EncoderConstant.NONE;
		}
	}
}