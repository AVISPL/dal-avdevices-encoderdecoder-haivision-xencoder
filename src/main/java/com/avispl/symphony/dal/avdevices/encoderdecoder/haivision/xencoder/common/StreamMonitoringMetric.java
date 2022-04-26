/*
 *  * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.common;

/**
 * StreamMonitoringMetric class defined the enum for the monitoring process
 *
 * @author Kevin / Symphony Dev Team<br>
 * Created on 4/22/2022
 * @since 1.0.0
 */
public enum StreamMonitoringMetric {

	STATE("State"),
	UPTIME("UpTime"),
	NAME("Name"),
	SOURCE_PORT("SourcePort"),
	SSRC("SSRC"),
	SENT_PACKETS("SentPackets"),
	SENTBYTES("SentBytes"),
	BITRATE("Bitrate"),
	UNSENT_PACKETS("UnsentPackets"),
	UNSENT_BYTES("UnsentBytes"),
	LAST_ERROR("LastError"),
	OCCURRED("Occurred"),
	RECONNECTIONS("Reconnections"),
	AES_ENCRYPTION("AesEncryption"),
	RESENT_PACKET("ResentPacket"),
	RESENT_BYTES("ResentBytes"),
	DROPPED_PACKETS("DroppedPackets"),
	DROPPED_BYTES("DroppedBytes"),
	RECEIVED_ACKS("ReceivedACKs"),
	RECEIVED_NAKS("ReceivedNAKs"),
	MTU("MTU"),
	MAX_BANDWIDTH("MaxBandwidth"),
	PATH_MAX_BANDWIDTH("PathMaxBandwidth"),
	RTT("RTT"),
	LOCAL_BUFFER_LEVEL("LocalBufferLevel"),
	LATENCY("Latency");

	private String name;
	/**
	 * StreamMonitoringMetric instantiation
	 *
	 * @param name {@code {@link #name}}
	 */
	StreamMonitoringMetric(String name) {
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