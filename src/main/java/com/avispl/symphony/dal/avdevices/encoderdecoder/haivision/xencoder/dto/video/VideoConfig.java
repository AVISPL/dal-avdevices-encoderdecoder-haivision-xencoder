/*
 *  * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dto.video;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * VideoConfig DTO class
 *
 * @author Kevin / Symphony Dev Team<br>
 * Created on 4/19/2022
 * @since 1.0.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class VideoConfig  {

	@JsonAlias("Encoder ID")
	private String id;

	@JsonAlias("Video Bitrate")
	private String bitrate;

	@JsonAlias("Video GOP Size")
	private String gopSize;

	@JsonAlias("Closed Captioning")
	private String closedCaption;

	@JsonAlias("Video Input")
	private String inputInterface;

	@JsonAlias("Time Code")
	private String timeCode;

	@JsonAlias("Aspect Ratio")
	private String aspectRatio;

	@JsonAlias("Output Resolution")
	private String resolution;

	@JsonAlias("Video Format")
	private String inputFormat;

	@JsonAlias("Name")
	private String name;

	@JsonAlias("Video GOP Structure")
	private String framing;

	@JsonAlias("Encoded Picture Rate")
	private String frameRate;

	@JsonAlias("Video Resize Mode")
	private String cropping;

	@JsonAlias("Intra Refresh")
	private String intraRefresh;

	@JsonAlias("Refresh Rate")
	private String intraRefreshRate;

	@JsonAlias("")
	private String state;

	@JsonAlias("Video Entropy Coding")
	private String entropyCoding;

	@JsonAlias("Picture Partitioning")
	private String picturePartitioning;

	@JsonAlias("Partial Frame Skip")
	private String partialFrameSkip;

	/**
	 * Retrieves {@code {@link #id}}
	 *
	 * @return value of {@link #id}
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets {@code id}
	 *
	 * @param id the {@code java.lang.String} field
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Retrieves {@code {@link #bitrate}}
	 *
	 * @return value of {@link #bitrate}
	 */
	public String getBitrate() {
		return bitrate;
	}

	/**
	 * Sets {@code bitrate}
	 *
	 * @param bitrate the {@code java.lang.String} field
	 */
	public void setBitrate(String bitrate) {
		this.bitrate = bitrate;
	}

	/**
	 * Retrieves {@code {@link #gopSize}}
	 *
	 * @return value of {@link #gopSize}
	 */
	public String getGopSize() {
		return gopSize;
	}

	/**
	 * Sets {@code gopSize}
	 *
	 * @param gopSize the {@code java.lang.String} field
	 */
	public void setGopSize(String gopSize) {
		this.gopSize = gopSize;
	}

	/**
	 * Retrieves {@code {@link #closedCaption}}
	 *
	 * @return value of {@link #closedCaption}
	 */
	public String getClosedCaption() {
		return closedCaption;
	}

	/**
	 * Sets {@code closedCaption}
	 *
	 * @param closedCaption the {@code java.lang.String} field
	 */
	public void setClosedCaption(String closedCaption) {
		this.closedCaption = closedCaption;
	}

	/**
	 * Retrieves {@code {@link #inputInterface}}
	 *
	 * @return value of {@link #inputInterface}
	 */
	public String getInputInterface() {
		return inputInterface;
	}

	/**
	 * Sets {@code inputInterface}
	 *
	 * @param inputInterface the {@code java.lang.String} field
	 */
	public void setInputInterface(String inputInterface) {
		this.inputInterface = inputInterface;
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
	 * Retrieves {@code {@link #aspectRatio}}
	 *
	 * @return value of {@link #aspectRatio}
	 */
	public String getAspectRatio() {
		return aspectRatio;
	}

	/**
	 * Sets {@code aspectRatio}
	 *
	 * @param aspectRatio the {@code java.lang.String} field
	 */
	public void setAspectRatio(String aspectRatio) {
		this.aspectRatio = aspectRatio;
	}

	/**
	 * Retrieves {@code {@link #resolution}}
	 *
	 * @return value of {@link #resolution}
	 */
	public String getResolution() {
		return resolution;
	}

	/**
	 * Sets {@code resolution}
	 *
	 * @param resolution the {@code java.lang.String} field
	 */
	public void setResolution(String resolution) {
		this.resolution = resolution;
	}

	/**
	 * Retrieves {@code {@link #inputFormat}}
	 *
	 * @return value of {@link #inputFormat}
	 */
	public String getInputFormat() {
		return inputFormat;
	}

	/**
	 * Sets {@code inputFormat}
	 *
	 * @param inputFormat the {@code java.lang.String} field
	 */
	public void setInputFormat(String inputFormat) {
		this.inputFormat = inputFormat;
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
	 * Sets {@code name}
	 *
	 * @param name the {@code java.lang.String} field
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Retrieves {@code {@link #framing}}
	 *
	 * @return value of {@link #framing}
	 */
	public String getFraming() {
		return framing;
	}

	/**
	 * Sets {@code framing}
	 *
	 * @param framing the {@code java.lang.String} field
	 */
	public void setFraming(String framing) {
		this.framing = framing;
	}

	/**
	 * Retrieves {@code {@link #frameRate}}
	 *
	 * @return value of {@link #frameRate}
	 */
	public String getFrameRate() {
		return frameRate;
	}

	/**
	 * Sets {@code frameRate}
	 *
	 * @param frameRate the {@code java.lang.String} field
	 */
	public void setFrameRate(String frameRate) {
		this.frameRate = frameRate;
	}

	/**
	 * Retrieves {@code {@link #cropping}}
	 *
	 * @return value of {@link #cropping}
	 */
	public String getCropping() {
		return cropping;
	}

	/**
	 * Sets {@code cropping}
	 *
	 * @param cropping the {@code java.lang.String} field
	 */
	public void setCropping(String cropping) {
		this.cropping = cropping;
	}

	/**
	 * Retrieves {@code {@link #intraRefresh}}
	 *
	 * @return value of {@link #intraRefresh}
	 */
	public String getIntraRefresh() {
		return intraRefresh;
	}

	/**
	 * Sets {@code intraRefresh}
	 *
	 * @param intraRefresh the {@code java.lang.String} field
	 */
	public void setIntraRefresh(String intraRefresh) {
		this.intraRefresh = intraRefresh;
	}

	/**
	 * Retrieves {@code {@link #intraRefreshRate}}
	 *
	 * @return value of {@link #intraRefreshRate}
	 */
	public String getIntraRefreshRate() {
		return intraRefreshRate;
	}

	/**
	 * Sets {@code intraRefreshRate}
	 *
	 * @param intraRefreshRate the {@code java.lang.String} field
	 */
	public void setIntraRefreshRate(String intraRefreshRate) {
		this.intraRefreshRate = intraRefreshRate;
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
	 * Retrieves {@code {@link #entropyCoding}}
	 *
	 * @return value of {@link #entropyCoding}
	 */
	public String getEntropyCoding() {
		return entropyCoding;
	}

	/**
	 * Sets {@code entropyCoding}
	 *
	 * @param entropyCoding the {@code java.lang.String} field
	 */
	public void setEntropyCoding(String entropyCoding) {
		this.entropyCoding = entropyCoding;
	}

	/**
	 * Retrieves {@code {@link #picturePartitioning}}
	 *
	 * @return value of {@link #picturePartitioning}
	 */
	public String getPicturePartitioning() {
		return picturePartitioning;
	}

	/**
	 * Sets {@code picturePartitioning}
	 *
	 * @param picturePartitioning the {@code java.lang.String} field
	 */
	public void setPicturePartitioning(String picturePartitioning) {
		this.picturePartitioning = picturePartitioning;
	}

	/**
	 * Retrieves {@code {@link #partialFrameSkip}}
	 *
	 * @return value of {@link #partialFrameSkip}
	 */
	public String getPartialFrameSkip() {
		return partialFrameSkip;
	}

	/**
	 * Sets {@code partialFrameSkip}
	 *
	 * @param partialFrameSkip the {@code java.lang.String} field
	 */
	public void setPartialFrameSkip(String partialFrameSkip) {
		this.partialFrameSkip = partialFrameSkip;
	}
}