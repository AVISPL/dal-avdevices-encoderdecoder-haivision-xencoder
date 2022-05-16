/*
 *  * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dropdownlist;

import java.util.LinkedList;
import java.util.List;

import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.common.EncoderConstant;

/**
 * AudioActionEnum class defined the enum for monitoring and controlling process
 *
 * @author Kevin / Symphony Dev Team<br>
 * Created on 4/25/2022
 * @since 1.0.0
 */
public enum AudioActionEnum {

	MUTED("Mute", true, false, false),
	UN_MUTED("Unmute", false, false, true),
	START("Start", false, true, false),
	STOP("Stop", true, false, true),
	NONE("None", true, true, true);

	private final String name;
	private final boolean isStartAction;
	private final boolean isStopAction;
	private final boolean isMuteAction;

	/**
	 * AudioActionEnum instantiation
	 *
	 * @param name {@code {@link #name}}
	 * @param isStartAction {@code {@link #isStartAction}}
	 * @param isStopAction {@code {@link #isStopAction}}
	 * @param isMuteAction {@code {@link #isMuteAction}}
	 */
	AudioActionEnum(String name, boolean isStartAction, boolean isStopAction, boolean isMuteAction) {
		this.name = name;
		this.isStartAction = isStartAction;
		this.isStopAction = isStopAction;
		this.isMuteAction = isMuteAction;
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
	 * Retrieves {@code {@link #isMuteAction}}
	 *
	 * @return value of {@link #isMuteAction}
	 */
	public boolean isMuteAction() {
		return isMuteAction;
	}

	/**
	 * Retrieves all names of audioActionDropdown by action
	 *
	 * @param action the action is String instance in AudioActionDropdown
	 * @return list name of audioActionDropdown
	 */
	public static String[] getArrayOfEnumByAction(String action) {
		List<String> actionList = new LinkedList<>();
		for (AudioActionEnum audioActionDropdown : AudioActionEnum.values()) {
			switch (action) {
				case EncoderConstant.WORKING:
					if (audioActionDropdown.isStartAction()) {
						actionList.add(audioActionDropdown.getName());
					}
					break;
				case EncoderConstant.STOPPED:
					if (audioActionDropdown.isStopAction()) {
						actionList.add(audioActionDropdown.getName());
					}
					break;
				case EncoderConstant.MUTED:
					if (audioActionDropdown.isMuteAction()) {
						actionList.add(audioActionDropdown.getName());
					}
					break;
				default:
					throw new IllegalArgumentException("Do not support state action is: " + action);
			}
		}
		return actionList.toArray(new String[actionList.size()]);
	}
}