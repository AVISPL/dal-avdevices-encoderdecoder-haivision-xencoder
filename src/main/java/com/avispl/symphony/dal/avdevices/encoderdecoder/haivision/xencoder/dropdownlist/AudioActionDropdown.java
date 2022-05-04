/*
 *  * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dropdownlist;

import java.util.LinkedList;
import java.util.List;

/**
 * StateDropdown class defined the enum for monitoring and controlling process
 *
 * @author Kevin / Symphony Dev Team<br>
 * Created on 4/25/2022
 * @since 1.0.0
 */
public enum AudioActionDropdown {

	MUTED("Muted", false, true),
	START("Start", false, true),
	STOP("Stop", true, false),
	NONE("None", true, true);

	private final String name;
	private final boolean isStartAction;
	private final boolean isStopAction;

	/**
	 * AudioStateDropdown instantiation
	 *
	 * @param name {@code {@link #name}}
	 * @param isStartAction {@code {@link #isStartAction}}
	 * @param isStopAction {@code {@link #isStopAction}}
	 */
	AudioActionDropdown(String name,boolean isStartAction,boolean isStopAction) {
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
	 * Retrieves {@code {@link #isStartAction}}
	 *
	 * @return value of {@link #isStartAction}
	 */
	public boolean isStartAction() {
		return isStartAction;
	}

	/**
	 * Retrieves {@code {@link #isStopAction}}
	 *
	 * @return value of {@link #isStopAction}
	 */
	public boolean isStopAction() {
		return isStopAction;
	}

	/**
	 * Retrieves all names of audioActionDropdown enum with stop action
	 *
	 * @param isStart the isStart is boolean value with action start or stop
	 * @return list name of audioActionDropdown
	 */
	public static String[] getArrayOfNameByStartOrStopAction(boolean isStart) {
		List<String> actionList = new LinkedList<>();
		for (AudioActionDropdown audioActionDropdown : AudioActionDropdown.values()) {
			if (audioActionDropdown.isStopAction() && !isStart) {
				actionList.add(audioActionDropdown.getName());
			}
			else if(audioActionDropdown.isStartAction() && isStart){
				actionList.add(audioActionDropdown.getName());
			}
		}
		return actionList.toArray(new String[actionList.size()]);
	}
}