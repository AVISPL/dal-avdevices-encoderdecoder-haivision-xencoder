/*
 *  * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dropdownlist;

import java.util.LinkedList;
import java.util.List;

import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.common.EncoderConstant;

/**
 * ResolutionDropdown class defined the enum for monitoring and controlling process
 *
 * @author Kevin / Symphony Dev Team<br>
 * Created on 5/10/2022
 * @since 1.0.0
 */
public enum ResolutionDropdown {

	RESOLUTION_AUTOMATIC("Automatic","Input/Auto", false),
	RESOLUTION_1920_1080P("1920x1080p", "1920x1080p",true),
	RESOLUTION_1920_1080I("1920x1080i","1920x1080i", true),
	RESOLUTION_1440_1080P("1440x1080p","1440x1080p", true),
	RESOLUTION_1440_1080I("1440x1080i", "1440x1080i", true),
	RESOLUTION_1280_720("1280x720","1280x720p", true),
	RESOLUTION_960_1080P("960x1080p","960x1080p", true),
	RESOLUTION_960_1080I("960x1080i","960x1080i", true),
	RESOLUTION_960_720("960x720","960x720p", true),
	RESOLUTION_720_576P("720x576p","720x576p", true),
	RESOLUTION_720_576I("720x576i","720x576i", true),
	RESOLUTION_720_480P("720x480p","720x480p", true),
	RESOLUTION_720_480I("720x480i", "720x480i", true),
	RESOLUTION_704_576P("704x576p","704x576p", true),
	RESOLUTION_704_576I("704x576i","704x576i", true),
	RESOLUTION_640_720("640x720","640x720p", true),
	RESOLUTION_540_576P("540x576p","540x576p", true),
	RESOLUTION_540_576I("540x576i","540x576i", true),
	RESOLUTION_540_480("540x480i","540x480i", true),
	RESOLUTION_540_480P("540x480p", "540x480p", true),
	RESOLUTION_352_480P("352x480p","352x480p", true),
	RESOLUTION_352_480I("352x480i","352x480i", true),
	RESOLUTION_352_576P("352x576p","352x576p", true),
	RESOLUTION_352_576I("352x576i", "352x576i", true),
	RESOLUTION_352_288P("352x288p","352x288p", true),
	RESOLUTION_352_288I("352x288i","352x288i", true);

	private final String name;
	private final String value;
	private final boolean isCropping;

	/**
	 * ResolutionDropdown instantiation
	 *
	 * @param name {@code {@link #name}}
	 * @param value {@code {@link #value}}
	 * @param isCropping {@code {@link #isCropping}}
	 */
	ResolutionDropdown(String name, String value,boolean isCropping) {
		this.name = name;
		this.value = value;
		this.isCropping = isCropping;
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
	 * Retrieves {@code {@link #isCropping}}
	 *
	 * @return value of {@link #isCropping}
	 */
	public boolean isCropping() {
		return isCropping;
	}

	/**
	 * Retrieves all name of ResolutionDropdown
	 *
	 * @return list name of ResolutionDropdown
	 */
	public static List<String> getDropdownListNotCropping() {
		List<String> list = new LinkedList<>();
		for (ResolutionDropdown resolutionDropdown : ResolutionDropdown.values()) {
			if (!resolutionDropdown.isCropping()) {
				list.add(resolutionDropdown.getName());
			}
		}
		list.add(EncoderConstant.NONE);
		return list;
	}

	/**
	 * Check if enum is cropping or not
	 *
	 * @param name of enum
	 */
	public static boolean checkIsCropping(String name) {
		boolean isCropping = false;
		for (ResolutionDropdown resolution : ResolutionDropdown.values()) {
			if (resolution.getName().equals(name)) {
				isCropping = resolution.isCropping();
				break;
			}
		}
		return isCropping;
	}
}