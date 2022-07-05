/*
 *  * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dto.stream;

/**
 * Audio DTO class
 *
 * @author Kevin / Symphony Dev Team<br>
 * Created on 4/21/2022
 * @since 1.0.0
 */
public class Audio {

	private String audioId;
	private String audioName;

	/**
	 * Retrieves {@code {@link #audioId}}
	 *
	 * @return value of {@link #audioId}
	 */
	public String getAudioId() {
		return audioId;
	}

	/**
	 * Sets {@code audioId}
	 *
	 * @param audioId the {@code java.lang.String} field
	 */
	public void setAudioId(String audioId) {
		this.audioId = audioId;
	}

	/**
	 * Retrieves {@code {@link #audioName}}
	 *
	 * @return value of {@link #audioName}
	 */
	public String getAudioName() {
		return audioName;
	}

	/**
	 * Sets {@code audioName}
	 *
	 * @param audioName the {@code java.lang.String} field
	 */
	public void setAudioName(String audioName) {
		this.audioName = audioName;
	}
}