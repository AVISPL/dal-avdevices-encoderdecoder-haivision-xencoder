/*
 *  * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.common;

/**
 * SystemInfoMonitoringMetric class defined the enum for the monitoring process
 *
 * @author Kevin / Symphony Dev Team<br>
 * Created on 4/19/2022
 * @since 1.0.0
 */
public enum SystemMonitoringMetric {

	UPTIME("Uptime"),
	SERIAL_NUMBER("SerialNumber"),
	HARDWARE_COMPATIBILITY("HardwareCompatibility"),
	CPLD_VEVISION("CpldVevision"),
	BOOT_VERSION("BootVersion"),
	CARD_TYPE("CardType"),
	PART_NUMBER("PartNumber"),
	FIRMWARE_DATE("FirmwareDate"),
	FIRMWARE_VERSION("FirmwareVersion"),
	FIRMWARE_OPTIONS("FirmwareOptions"),
	TEMPERATURE("Temperature");

	private final String name;

	/**
	 * SystemMonitoringMetric instantiation
	 *
	 * @param name {@code {@link #name}}
	 */
	SystemMonitoringMetric(String name) {
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