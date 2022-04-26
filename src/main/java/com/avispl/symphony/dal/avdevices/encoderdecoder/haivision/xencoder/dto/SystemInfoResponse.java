/*
 *  * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.common.EncoderConstant;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.common.SystemMonitoringMetric;

/**
 * SystemInfoResponse Response DTO class
 *
 * @author Kevin / Symphony Dev Team<br>
 * Created on 4/19/2022
 * @since 1.0.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SystemInfoResponse {

	@JsonAlias("Serial Number")
	private String serialNumber;

	@JsonAlias("Hardware Compatibility")
	private String hardwareCompatibility;

	@JsonAlias("CPLD Version")
	private String cpldVevision;

	@JsonAlias("Boot Version")
	private String bootVersion;

	@JsonAlias("Card Type")
	private String cardType;

	@JsonAlias("Part Number")
	private String partNumber;

	@JsonAlias("Firmware Date")
	private String firmwareDate;

	@JsonAlias("Firmware Version")
	private String firmwareVersion;

	@JsonAlias("Firmware Options")
	private String firmwareOptions;

	@JsonAlias("Firmware Time")
	private String firmwareTime;

	@JsonAlias("Temperature")
	private String temperature;


	/**
	 * Retrieves {@code {@link #serialNumber}}
	 *
	 * @return value of {@link #serialNumber}
	 */
	public String getSerialNumber() {
		return serialNumber;
	}

	/**
	 * Sets {@code serialNumber}
	 *
	 * @param serialNumber the {@code java.lang.String} field
	 */
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	/**
	 * Retrieves {@code {@link #hardwareCompatibility}}
	 *
	 * @return value of {@link #hardwareCompatibility}
	 */
	public String getHardwareCompatibility() {
		return hardwareCompatibility;
	}

	/**
	 * Sets {@code hardwareCompatibility}
	 *
	 * @param hardwareCompatibility the {@code java.lang.String} field
	 */
	public void setHardwareCompatibility(String hardwareCompatibility) {
		this.hardwareCompatibility = hardwareCompatibility;
	}

	/**
	 * Retrieves {@code {@link #cpldVevision }}
	 *
	 * @return value of {@link #cpldVevision}
	 */
	public String getCpldVevision() {
		return cpldVevision;
	}

	/**
	 * Sets {@code cpldRevision}
	 *
	 * @param cpldVevision the {@code java.lang.String} field
	 */
	public void setCpldVevision(String cpldVevision) {
		this.cpldVevision = cpldVevision;
	}

	/**
	 * Retrieves {@code {@link #bootVersion}}
	 *
	 * @return value of {@link #bootVersion}
	 */
	public String getBootVersion() {
		return bootVersion;
	}

	/**
	 * Sets {@code bootVersion}
	 *
	 * @param bootVersion the {@code java.lang.String} field
	 */
	public void setBootVersion(String bootVersion) {
		this.bootVersion = bootVersion;
	}

	/**
	 * Retrieves {@code {@link #cardType}}
	 *
	 * @return value of {@link #cardType}
	 */
	public String getCardType() {
		return cardType;
	}

	/**
	 * Sets {@code cardType}
	 *
	 * @param cardType the {@code java.lang.String} field
	 */
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	/**
	 * Retrieves {@code {@link #partNumber}}
	 *
	 * @return value of {@link #partNumber}
	 */
	public String getPartNumber() {
		return partNumber;
	}

	/**
	 * Sets {@code partNumber}
	 *
	 * @param partNumber the {@code java.lang.String} field
	 */
	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}

	/**
	 * Retrieves {@code {@link #firmwareDate}}
	 *
	 * @return value of {@link #firmwareDate}
	 */
	public String getFirmwareDate() {
		return firmwareDate;
	}

	/**
	 * Sets {@code firmwareDate}
	 *
	 * @param firmwareDate the {@code java.lang.String} field
	 */
	public void setFirmwareDate(String firmwareDate) {
		this.firmwareDate = firmwareDate;
	}

	/**
	 * Retrieves {@code {@link #firmwareVersion}}
	 *
	 * @return value of {@link #firmwareVersion}
	 */
	public String getFirmwareVersion() {
		return firmwareVersion;
	}

	/**
	 * Sets {@code firmwareVersion}
	 *
	 * @param firmwareVersion the {@code java.lang.String} field
	 */
	public void setFirmwareVersion(String firmwareVersion) {
		this.firmwareVersion = firmwareVersion;
	}

	/**
	 * Retrieves {@code {@link #firmwareOptions}}
	 *
	 * @return value of {@link #firmwareOptions}
	 */
	public String getFirmwareOptions() {
		return firmwareOptions;
	}

	/**
	 * Sets {@code firmwareOptions}
	 *
	 * @param firmwareOptions the {@code java.lang.String} field
	 */
	public void setFirmwareOptions(String firmwareOptions) {
		this.firmwareOptions = firmwareOptions;
	}

	/**
	 * Retrieves {@code {@link #firmwareTime }}
	 *
	 * @return value of {@link #firmwareTime}
	 */
	public String getFirmwareTime() {
		return firmwareTime;
	}

	/**
	 * Sets {@code uptime}
	 *
	 * @param firmwareTime the {@code java.lang.String} field
	 */
	public void setFirmwareTime(String firmwareTime) {
		this.firmwareTime = firmwareTime;
	}

	/**
	 * Retrieves {@code {@link #temperature}}
	 *
	 * @return value of {@link #temperature}
	 */
	public String getTemperature() {
		return temperature;
	}

	/**
	 * Sets {@code temperature}
	 *
	 * @param temperature the {@code java.lang.String} field
	 */
	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}

	/**
	 * Get the value by the metric monitoring
	 *
	 * @param metric the metric is metric monitoring
	 * @return String value of encoder monitoring properties by metric
	 */
	public String getValueByMetric(SystemMonitoringMetric metric) {
		switch (metric) {
			case UPTIME:
				return getFirmwareTime();
			case SERIAL_NUMBER:
				return getSerialNumber();
			case HARDWARE_COMPATIBILITY:
				return getHardwareCompatibility();
			case CPLD_VEVISION:
				return getCpldVevision();
			case BOOT_VERSION:
				return getBootVersion();
			case CARD_TYPE:
				return getCardType();
			case PART_NUMBER:
				return getPartNumber();
			case FIRMWARE_DATE:
				return getFirmwareDate();
			case FIRMWARE_VERSION:
				return getFirmwareVersion();
			case FIRMWARE_OPTIONS:
				return getFirmwareOptions();
			case TEMPERATURE:
				return getTemperature();
			default:
				return EncoderConstant.NONE;
		}
	}
}