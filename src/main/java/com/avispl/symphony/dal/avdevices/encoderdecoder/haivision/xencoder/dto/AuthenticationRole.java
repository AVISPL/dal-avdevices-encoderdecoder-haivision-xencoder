/*
 * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Authentication cookie information
 *
 * @author Kevin / Symphony Dev Team<br>
 * Created on 4/19/2022
 * @since 1.0.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthenticationRole {

	@JsonAlias("Role")
	private String role;

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
}