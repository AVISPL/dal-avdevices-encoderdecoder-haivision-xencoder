/*
 *  * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dropdownlist;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * BitRateEnum class defined the enum for monitoring and controlling process
 *
 * @author Kevin / Symphony Dev Team<br>
 * Created on 4/25/2022
 * @since 1.0.0
 */
public enum BitRateEnum {

	NUMBER_56("56 kbps", "56", false, true),
	NUMBER_64("64 kbps", "64", false, true),
	NUMBER_80("80 kbps", "80", true, false),
	NUMBER_96("96 kbps", "96", true, true),
	NUMBER_128("128 kbps", "128", true, true),
	NUMBER_160("160 kbps", "160", false, true),
	NUMBER_192("192 kbps", "192", true, false),
	NUMBER_256("256 kbps", "256", true, false),
	NUMBER_320("320 kbps", "320", true, false);

	private final String name;
	private final String value;
	private final boolean isStereo;
	private final boolean isMono;

	/**
	 * BitRateEnum instantiation
	 *
	 * @param name {@code {@link #name}}
	 * @param value {@code {@link #value}}
	 * @param isStereo {@code {@link #isStereo}}
	 * @param isMono {@code {@link #isMono}}
	 */
	BitRateEnum(String name, String value, boolean isStereo, boolean isMono) {
		this.name = name;
		this.value = value;
		this.isStereo = isStereo;
		this.isMono = isMono;
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
	 * Retrieves {@code {@link #isStereo}}
	 *
	 * @return value of {@link #isStereo}
	 */
	public boolean isStereo() {
		return isStereo;
	}

	/**
	 * Retrieves {@code {@link #isMono}}
	 *
	 * @return value of {@link #isMono}
	 */
	public boolean isMono() {
		return isMono;
	}

	/**
	 * Retrieves all name of BitRateDropdown by stereo mode
	 *
	 * @param isStereoMode the isStereoMode is boolean value stereoMode or monoMode
	 * @return list name of BitRate
	 */
	public static String[] getArrayOfNameByStereoOrMonoMode(boolean isStereoMode) {
		List<String> channelModeList = new LinkedList<>();
		for (BitRateEnum bitRateDropdown : BitRateEnum.values()) {
			if (isStereoMode) {
				if (bitRateDropdown.isStereo()) {
					channelModeList.add(bitRateDropdown.getName());
				}
			} else {
				if (bitRateDropdown.isMono()) {
					channelModeList.add(bitRateDropdown.getName());
				}
			}
		}
		return channelModeList.toArray(new String[channelModeList.size()]);
	}

	/**
	 * Get default bitRate value for the BitRateDropdown list
	 *
	 * @param bitRate the bitRate is String value
	 * @param channelMode the channelMode is mode of ChannelModeDropdown
	 * @return bitrate/defaultBitRate
	 */
	public static String getDefaultBitRate(String bitRate, String channelMode) {
		String defaultBitRate = NUMBER_56.getName();
		boolean isStereo = ChannelModeEnum.STEREO.getName().equals(channelMode);
		if (isStereo) {
			defaultBitRate = NUMBER_80.getName();
		}
		return Arrays.asList(BitRateEnum.getArrayOfNameByStereoOrMonoMode(isStereo)).contains(bitRate) ? bitRate : defaultBitRate;
	}
}