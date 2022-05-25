/*
 *  * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dropdownlist;

/**
 * EncryptionEnum class defined the enum for monitoring and controlling process
 *
 * @author Kevin / Symphony Dev Team<br>
 * Created on 5/25/2022
 * @since 1.0.0
 */
public enum EncryptionEnum {
	NONE("None"),
	AES_128("AES-128"),
	AES_256("AES-256");

	private final String name;

	/**
	 * EncryptionEnum instantiation
	 *
	 * @param name {@code {@link #name}}
	 */
	EncryptionEnum(String name) {
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