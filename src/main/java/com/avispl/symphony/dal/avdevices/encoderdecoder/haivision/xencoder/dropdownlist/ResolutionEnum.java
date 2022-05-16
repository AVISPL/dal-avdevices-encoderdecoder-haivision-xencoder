/*
 *  * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dropdownlist;

/**
 * ResolutionEnum class defined the enum for monitoring and controlling process
 *
 * @author Kevin / Symphony Dev Team<br>
 * Created on 5/10/2022
 * @since 1.0.0
 */
public enum ResolutionEnum {

	RESOLUTION_AUTOMATIC("Automatic", "Input/Auto"),
	RESOLUTION_1920_1080P("1920x1080p", "1920x1080p"),
	RESOLUTION_1920_1080I("1920x1080i", "1920x1080i"),
	RESOLUTION_1440_1080P("1440x1080p", "1440x1080p"),
	RESOLUTION_1440_1080I("1440x1080i", "1440x1080i"),
	RESOLUTION_1280_720("1280x720", "1280x720p"),
	RESOLUTION_1280_1024P("1280x1024", "1280x1024p"),
	RESOLUTION_1280_768("1280x768", "1280x768p"),
	RESOLUTION_1024_768("1024x768", "1024x768p"),
	RESOLUTION_960_1080P("960x1080p", "960x1080p"),
	RESOLUTION_960_1080I("960x1080i", "960x1080i"),
	RESOLUTION_960_720("960x720", "960x720p"),
	RESOLUTION_720_576P("720x576p", "720x576p"),
	RESOLUTION_720_576I("720x576i", "720x576i"),
	RESOLUTION_720_480P("720x480p", "720x480p"),
	RESOLUTION_720_480I("720x480i", "720x480i"),
	RESOLUTION_704_576P("704x576p", "704x576p"),
	RESOLUTION_704_576I("704x576i", "704x576i"),
	RESOLUTION_640_720("640x720", "640x720p"),
	RESOLUTION_540_576P("540x576p", "540x576p"),
	RESOLUTION_540_576I("540x576i", "540x576i"),
	RESOLUTION_540_480("540x480i", "540x480i"),
	RESOLUTION_540_480P("540x480p", "540x480p"),
	RESOLUTION_352_480P("352x480p", "352x480p"),
	RESOLUTION_352_480I("352x480i", "352x480i"),
	RESOLUTION_352_576P("352x576p", "352x576p"),
	RESOLUTION_352_576I("352x576i", "352x576i"),
	RESOLUTION_352_288P("352x288p", "352x288p"),
	RESOLUTION_352_288I("352x288i", "352x288i");

	private final String name;
	private final String value;

	/**
	 * ResolutionEnum instantiation
	 *
	 * @param name {@code {@link #name}}
	 * @param value {@code {@link #value}}
	 */
	ResolutionEnum(String name, String value) {
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
}