/*
 *  * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dropdownlist;

import java.util.LinkedList;
import java.util.List;

/**
 * VideoActionDropdown class defined the enum for monitoring and controlling process
 *
 * @author Kevin / Symphony Dev Team<br>
 * Created on 5/10/2022
 * @since 1.0.0
 */
public enum VideoActionDropdown {

	START("Start", false, true),
	STOP("Stop", true, false),
	NONE("None", true, true);

	private final String name;
	private final boolean isStartAction;
	private final boolean isStopAction;

	/**
	 * VideoActionDropdown instantiation
	 *
	 * @param name {@code {@link #name}}
	 * @param isStartAction {@code {@link #isStartAction}}
	 * @param isStopAction {@code {@link #isStopAction}}
	 */
	VideoActionDropdown(String name, boolean isStartAction, boolean isStopAction) {
		this.name = name;
		this.isStartAction = isStartAction;
		this.isStopAction = isStopAction;
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
	 * Retrieves all names of VideoActionDropdown by action
	 *
	 * @param action the action is String instance in VideoActionDropdown
	 * @return list name of VideoActionDropdown
	 */
	public static String[] getVideoAction(boolean action) {
		List<String> actionList = new LinkedList<>();
		for (VideoActionDropdown videoActionDropdown : VideoActionDropdown.values()) {
			if (action) {
				if (videoActionDropdown.isStartAction) {
					actionList.add(videoActionDropdown.getName());
				}
			} else {
				if (videoActionDropdown.isStopAction) {
					actionList.add(videoActionDropdown.getName());
				}
			}
		}
		return actionList.toArray(new String[actionList.size()]);
	}
}