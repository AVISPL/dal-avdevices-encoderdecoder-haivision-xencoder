/*
 *  * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.common;

/**
 * EncoderCommand class defined the enum for the monitoring process
 *
 * @author Kevin / Symphony Dev Team<br>
 * Created on 4/22/2022
 * @since 1.0.0
 */
public enum EncoderCommand {

	//common command
	GET("get "),
	ALL("all "),
	START("start "),
	STOP("stop "),
	SET("set "),
	STATS("stats "),
	CONFIG("config "),
	PAUSE("pause "),
	RESUME("resume "),
	LIST(" list "),

	//stream command
	OPERATION_STREAM("stream "),
	OPERATION_CREATE("create "),
	OPERATION_DELETE("delete "),
	OPERATION_SESSION("session "),

	//video command
	OPERATION_VIDENC("videnc "),

	//audio command
	OPERATION_AUDENC("audenc "),

	//input command
	OPERATION_VIDIN("vidin "),
	OPERATION_STILL("still "),

	// still image command


	//temperatures command
	OPERATION_TEMPERATURE("temperature "),

	//system info command
	ADMIN_HAIVERSION("haiversion"),

	//account command
	ADMIN_ACCOUNT("account "),

	//service command
	ADMIN_SERVICE("service "),
	ADMIN_STATUS("status ");

	private final String name;

	/**
	 * Service command
	 *
	 * @param name {@code {@link #name}}
	 */
	EncoderCommand(String name) {
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