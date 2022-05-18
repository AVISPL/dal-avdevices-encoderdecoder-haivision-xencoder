/*
 *  * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.common;

/**
 * StreamMonitoringMetric class defined the enum of the monitoring metrics
 *
 * @author Kevin / Symphony Dev Team<br>
 * Created on 4/22/2022
 * @since 1.0.0
 */
public enum StreamMonitoringMetric {

	STATE("State"),
	UPTIME("UpTime"),
	SOURCE_PORT("SourcePort"),
	SSRC("SSRC"),
	SENT_PACKETS("SentPackets"),
	SENT_BYTES("SentBytes"),
	BITRATE("Bitrate (kbps)"),
	UNSENT_PACKETS("UnsentPackets"),
	UNSENT_BYTES("UnsentBytes"),
	LAST_ERROR("LastError"),
	OCCURRED("Occurred"),
	RECONNECTIONS("Reconnections"),
	AES_ENCRYPTION("AesEncryption"),
	RESENT_PACKET("ResentPackets"),
	RESENT_BYTES("ResentBytes"),
	DROPPED_PACKETS("DroppedPackets"),
	DROPPED_BYTES("DroppedBytes"),
	RECEIVED_ACKS("ReceivedACKs"),
	RECEIVED_NAKS("ReceivedNAKs"),
	MAX_BANDWIDTH("MaxBandwidth (kbps)"),
	PATH_MAX_BANDWIDTH("PathMaxBandwidth"),
	RTT("RTT (ms)"),
	LOCAL_BUFFER_LEVEL("Buffer (ms)"),
	LATENCY("Latency (ms)");

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