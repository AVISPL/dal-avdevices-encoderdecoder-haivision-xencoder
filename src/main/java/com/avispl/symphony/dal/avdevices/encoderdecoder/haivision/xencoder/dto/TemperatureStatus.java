/*
 *  * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * TemperatureStatus Response DTO class
 *
 * @author Kevin / Symphony Dev Team<br>
 * Created on 4/25/2022
 * @since 1.0.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TemperatureStatus {

	@JsonAlias("Current Temperature")
	private String temperature;

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
}