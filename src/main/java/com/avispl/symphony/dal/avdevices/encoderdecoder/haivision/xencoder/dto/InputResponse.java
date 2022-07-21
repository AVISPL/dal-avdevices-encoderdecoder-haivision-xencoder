/*
 * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * InputResponse Response DTO class
 *
 * @author Kevin / Symphony Dev Team<br>
 * Created on 5/10/2022
 * @since 1.0.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class InputResponse {

	@JsonAlias("Input ID")
	private String role;

	@JsonAlias("Name")
	private String name;

	/**
	 * Retrieves {@code {@link #role}}
	 *
	 * @return value of {@link #role}
	 */
	public String getRole() {
		return role;
	}

	/**
	 * Sets {@code role}
	 *
	 * @param role the {@code java.lang.String} field
	 */
	public void setRole(String role) {
		this.role = role;
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
}