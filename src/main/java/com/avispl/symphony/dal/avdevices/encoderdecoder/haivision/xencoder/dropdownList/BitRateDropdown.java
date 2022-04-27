/*
 *  * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dropdownList;

import java.util.LinkedList;
import java.util.List;

/**
 * BitRateDropdown class defined the enum for monitoring and controlling process
 *
 * @author Kevin / Symphony Dev Team<br>
 * Created on 4/25/2022
 * @since 1.0.0
 */
public enum BitRateDropdown {

	NUMBER_56("56 kbps", false, true),
	NUMBER_64("64 kbps", false, true),
	NUMBER_80("80 kbps", true, false),
	NUMBER_96("96 kbps", true, true),
	NUMBER_128("128 kbps", true, true),
	NUMBER_160("160 kbps", false, true),
	NUMBER_192("192 kbps", true, false),
	NUMBER_256("256 kbps", true, false),
	NUMBER_320("320 kbps", true, false);
	private final String name;
	private final boolean isStereo;
	private final boolean isMono;

	/**
	 * BitRateDropdown instantiation
	 *
	 * @param name {@code {@link #name}}
	 * @param isStereo {@code {@link #isStereo}}
	 * @param isMono {@code {@link #isMono}}
	 */
	BitRateDropdown(String name, boolean isStereo, boolean isMono) {
		this.name = name;
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
	 * Retrieves all name of BitRateDropdown is stereo mode
	 *
	 * @return list name of BitRate
	 */
	public static String[] namesIsStereo() {
		List<String> list = new LinkedList<>();
		for (BitRateDropdown bitRateDropdown : BitRateDropdown.values()) {
			if (bitRateDropdown.isStereo()) {
				list.add(bitRateDropdown.getName());
			}
		}
		return list.toArray(new String[list.size()]);
	}

	/**
	 * Retrieves all name of BitRateDropdown is mono mode
	 *
	 * @return list name of BitRate
	 */
	public static String[] namesIsMono() {
		List<String> list = new LinkedList<>();
		for (BitRateDropdown bitRateDropdown : BitRateDropdown.values()) {
			if (bitRateDropdown.isMono()) {
				list.add(bitRateDropdown.getName());
			}
		}
		return list.toArray(new String[list.size()]);
	}
}