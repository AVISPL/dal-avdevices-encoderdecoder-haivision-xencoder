/*
 *  * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dropdownlist;

import java.util.ArrayList;
import java.util.List;

/**
 * FecEnum class defined the enum for monitoring and controlling process
 *
 * @author Kevin / Symphony Dev Team<br>
 * Created on 5/17/2022
 * @since 1.0.0
 */
public enum FecEnum {

	VF("VF", "Furnace", "yes", true, false),
	PRO_MPEG("Pro-MPEG", "Pro-MPEG", "yes", false, true),
	NONE("None", "", "no", true, true);

	private final String name;
	private final String value;
	private final String paramValue;
	private final boolean isUDPMode;
	private final boolean isRTPMode;

	/**
	 * FecEnum instantiation
	 *
	 * @param name {@code {@link #name}}
	 * @param value {@code {@link #value}}
	 * @param paramValue {@code {@link #paramValue}}
	 * @param isUDPMode {@code {@link #isUDPMode}}
	 * @param isRTPMode {@code {@link #isRTPMode}}
	 */
	FecEnum(String name, String value, String paramValue, boolean isUDPMode, boolean isRTPMode) {
		this.name = name;
		this.value = value;
		this.paramValue = paramValue;
		this.isUDPMode = isUDPMode;
		this.isRTPMode = isRTPMode;
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
	 * Retrieves {@code {@link #paramValue}}
	 *
	 * @return value of {@link #paramValue}
	 */
	public String getParamValue() {
		return paramValue;
	}

	/**
	 * Retrieves {@code {@link #isUDPMode}}
	 *
	 * @return value of {@link #isUDPMode}
	 */
	public boolean isUDPMode() {
		return isUDPMode;
	}

	/**
	 * Retrieves {@code {@link #isRTPMode}}
	 *
	 * @return value of {@link #isRTPMode}
	 */
	public boolean isRTPMode() {
		return isRTPMode;
	}

	/**
	 * Receive array is all names of FECEnum with mode TS over UDP or TS over RTP
	 *
	 * @return Array is list name of FEC
	 */
	public static String[] getArrayOfNameByUDPOrRTPMode(boolean isUDPMode) {
		List<String> arrayFec = new ArrayList<>();
		for (FecEnum fecEnum : FecEnum.values()) {
			if (isUDPMode) {
				if (fecEnum.isUDPMode()) {
					arrayFec.add(fecEnum.getName());
				}
			} else {
				if (fecEnum.isRTPMode()) {
					arrayFec.add(fecEnum.getName());
				}
			}
		}
		return arrayFec.toArray(new String[arrayFec.size()]);
	}
}