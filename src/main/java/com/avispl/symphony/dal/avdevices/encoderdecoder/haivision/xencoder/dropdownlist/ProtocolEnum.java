/*
 *  * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dropdownlist;

/**
 * ProtocolEnum class defined the enum for monitoring and controlling process
 *
 * @author Kevin / Symphony Dev Team<br>
 * Created on 5/16/2022
 * @since 1.0.0
 */
public enum ProtocolEnum {

	TS_UDP("TS over UDP", "ts-udp"),
	TS_RTP("TS over RTP", "ts-rtp"),
	TS_SRT("TS over SRT", "ts-srt"),
	DIRECT_RTP("Direct-RTP", "direct-rtp"),
	RTMP("RTMP", "rtmp");

	private final String name;
	private final String value;

	/**
	 * ProtocolEnum instantiation
	 *
	 * @param name {@code {@link #name}}
	 * @param value {@code {@link #value}}
	 */
	ProtocolEnum(String name, String value) {
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

	/**
	 * Get value of Protocol by name
	 *
	 * @param name the name is name of ProtocolEnum
	 * @return String is protocol value
	 */
	public static String getProtocolEnumByName(String name) {
		String defaultValue = name;
		for (ProtocolEnum protocolEnum : ProtocolEnum.values()) {
			if (protocolEnum.getName().equals(name)) {
				defaultValue = protocolEnum.getValue();
				break;
			}
		}
		return defaultValue;
	}

	/**
	 * Get name of Protocol by value
	 *
	 * @param value the value is value of ProtocolEnum
	 * @return String is protocol value
	 */
	public static String getNameOfProtocolEnumByValue(String value) {
		String defaultValue = value;
		for (ProtocolEnum protocolEnum : ProtocolEnum.values()) {
			if (protocolEnum.getValue().equalsIgnoreCase(value)) {
				defaultValue = protocolEnum.getName();
				break;
			}
		}
		return defaultValue;
	}
}