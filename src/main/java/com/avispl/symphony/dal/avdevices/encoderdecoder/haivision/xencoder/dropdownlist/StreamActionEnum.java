/*
 *  * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dropdownlist;

import java.util.LinkedList;
import java.util.List;

/**
 * StreamActionEnum class defined the enum for monitoring and controlling process
 *
 * @author Kevin / Symphony Dev Team<br>
 * Created on 6/1/2022
 * @since 1.0.0
 */
public enum StreamActionEnum {

	STOP("Stop", true, false, true),
	START("Start", false, true, false),
	PAUSE("Pause", true, false, false),
	RESUME("Resume", false, false, true),
	DELETE("Delete", true, true, true),
	NONE("None", true, true, true);

	private final String name;
	private boolean isStartAction;
	private boolean isStopAction;
	private boolean isPauseAction;

	/**
	 * StreamActionEnum instantiation
	 *
	 * @param name {@code {@link #name}}
	 * @param isStartAction {@code {@link #isStartAction}}
	 * @param isStopAction {@code {@link #isStopAction}}
	 * @param isPauseAction {@code {@link #isPauseAction}}
	 */
	StreamActionEnum(String name, boolean isStartAction, boolean isStopAction, boolean isPauseAction) {
		this.name = name;
		this.isStartAction = isStartAction;
		this.isStopAction = isStopAction;
		this.isPauseAction = isPauseAction;
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
	 * Retrieves {@code {@link #isPauseAction}}
	 *
	 * @return value of {@link #isPauseAction}
	 */
	public boolean isPauseAction() {
		return isPauseAction;
	}

	/**
	 * Retrieves all names of StreamActionEnum by action. Default return all names of isStopAction
	 *
	 * @param streamState the streamState is String name instance in StreamStateEnum
	 * @return list name of StreamActionEnum
	 */
	public static String[] getArrayOfEnumByAction(String streamState) {
		List<String> streamActionList = new LinkedList<>();
		StreamStateEnum streamStateEnum = EnumTypeHandler.getMetricOfEnumByName(StreamStateEnum.class, streamState);
		for (StreamActionEnum streamAction : StreamActionEnum.values()) {
			switch (streamStateEnum) {
				case STREAMING:
				case RESOLVING:
				case CONNECTING:
					if (streamAction.isStartAction()) {
						streamActionList.add(streamAction.getName());
					}
					break;
				case PAUSED:
					if (streamAction.isPauseAction()) {
						streamActionList.add(streamAction.getName());
					}
					break;
				case STOPPED:
				default:
					if (streamAction.isStopAction()) {
						streamActionList.add(streamAction.getName());
					}
					break;
			}
		}
		return streamActionList.toArray(new String[streamActionList.size()]);
	}
}