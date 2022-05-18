/*
 *  * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.common;

/**
 * StreamControllingMetric class provides during the monitoring and controlling process
 *
 * @author Kevin / Symphony Dev Team<br>
 * Created on 5/13/2022
 * @since 1.0.0
 */
public enum StreamControllingMetric {

	// create default stream
	NAME("Name"),
	SOURCE_VIDEO("SourceVideo"),
	SOURCE_AUDIO("SourceAudio"),
	ADD_SOURCE_AUDIO("AddSourceAudio"),
	STREAMING_PROTOCOL("StreamingProtocol"),
	STREAMING_DESTINATION_ADDRESS("StreamingDestinationAddress"),
	STREAMING_DESTINATION_PORT("StreamingDestinationPort"),
	PARAMETER_FEC("ParameterFEC"),
	PARAMETER_TRAFFIC_SHAPING("ParameterTrafficShaping"),
	PARAMETER_MTU("ParameterMTU"),
	PARAMETER_TTL("ParameterTTL"),
	PARAMETER_TOS("ParameterToS"),
	PARAMETER_VF_ENCRYPTION("ParameterVFEncryption"),
	STILL_IMAGE("StillImage"),
	SAP_TRANSMIT("SAPTransmit"),
	// end create default stream
	PARAMETER_CELLS("ParameterIdleCells"),
	PARAMETER_DELAYED_AUDIO("ParameterDelayedAudio");

	private String name;

	/**
	 * StreamControllingMetric instantiation
	 *
	 * @param name {@code {@link #name}}
	 */
	StreamControllingMetric(String name) {
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