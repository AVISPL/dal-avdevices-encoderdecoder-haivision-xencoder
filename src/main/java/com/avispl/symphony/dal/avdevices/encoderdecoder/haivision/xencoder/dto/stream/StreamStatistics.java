/*
 *  * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dto.stream;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.common.EncoderConstant;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.common.StreamMonitoringMetric;

/**
 * StreamStatistics DTO class
 *
 * @author Kevin / Symphony Dev Team<br>
 * Created on 4/21/2022
 * @since 1.0.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class StreamStatistics {

	@JsonAlias("State")
	private String state;

	@JsonAlias("Up Time")
	private String upTime;

	@JsonAlias("Name")
	private String name;

	@JsonAlias("Stream ID")
	private String id;

	@JsonAlias("Source Port")
	private String sourcePort;

	@JsonAlias("SSRC")
	private String sSRC;

	@JsonAlias("Sent Packets")
	private String sentPackets;

	@JsonAlias("Sent Bytes")
	private String sentBytes;

	@JsonAlias("Bitrate")
	private String bitrate;

	@JsonAlias("Unsent Packets")
	private String unsentPackets;

	@JsonAlias("Unsent Bytes")
	private String unsentBytes;

	@JsonAlias("Last Error")
	private String lastError;

	@JsonAlias("Occurred")
	private String occurred;

	@JsonAlias("Reconnections")
	private String reconnections;

	@JsonAlias("AES Encryption")
	private String aesEncryption;

	@JsonAlias("Resent Packet")
	private String resentPacket;

	@JsonAlias("Resent Bytes")
	private String resentBytes;

	@JsonAlias("Dropped Packets")
	private String droppedPackets;

	@JsonAlias("Dropped Bytes")
	private String droppedBytes;

	@JsonAlias("Received ACKs")
	private String receivedACKs;

	@JsonAlias("Received NAKs")
	private String receivedNAKs;

	@JsonAlias("MTU")
	private String mtu;

	@JsonAlias("Max Bandwidth")
	private String maxBandwidth;

	@JsonAlias("Path Max Bandwidth")
	private String pathMaxBandwidth;

	@JsonAlias("RTT")
	private String rtt;

	@JsonAlias("Local Buffer Level")
	private String localBufferLevel;

	@JsonAlias("Latency")
	private String latency;

	/**
	 * Retrieves {@code {@link #state}}
	 *
	 * @return value of {@link #state}
	 */
	public String getState() {
		return state;
	}

	/**
	 * Sets {@code state}
	 *
	 * @param state the {@code java.lang.String} field
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * Retrieves {@code {@link #upTime}}
	 *
	 * @return value of {@link #upTime}
	 */
	public String getUpTime() {
		return upTime;
	}

	/**
	 * Sets {@code upTime}
	 *
	 * @param upTime the {@code java.lang.String} field
	 */
	public void setUpTime(String upTime) {
		this.upTime = upTime;
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
	 * Sets {@code name}
	 *
	 * @param name the {@code java.lang.String} field
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Retrieves {@code {@link #id}}
	 *
	 * @return value of {@link #id}
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets {@code id}
	 *
	 * @param id the {@code java.lang.String} field
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Retrieves {@code {@link #sourcePort}}
	 *
	 * @return value of {@link #sourcePort}
	 */
	public String getSourcePort() {
		return sourcePort;
	}

	/**
	 * Sets {@code sourcePort}
	 *
	 * @param sourcePort the {@code java.lang.String} field
	 */
	public void setSourcePort(String sourcePort) {
		this.sourcePort = sourcePort;
	}

	/**
	 * Retrieves {@code {@link #sSRC}}
	 *
	 * @return value of {@link #sSRC}
	 */
	public String getsSRC() {
		return sSRC;
	}

	/**
	 * Sets {@code sSRC}
	 *
	 * @param sSRC the {@code java.lang.String} field
	 */
	public void setsSRC(String sSRC) {
		this.sSRC = sSRC;
	}

	/**
	 * Retrieves {@code {@link #sentPackets}}
	 *
	 * @return value of {@link #sentPackets}
	 */
	public String getSentPackets() {
		return sentPackets;
	}

	/**
	 * Sets {@code sentPackets}
	 *
	 * @param sentPackets the {@code java.lang.String} field
	 */
	public void setSentPackets(String sentPackets) {
		this.sentPackets = sentPackets;
	}

	/**
	 * Retrieves {@code {@link #sentBytes}}
	 *
	 * @return value of {@link #sentBytes}
	 */
	public String getSentBytes() {
		return sentBytes;
	}

	/**
	 * Sets {@code sentBytes}
	 *
	 * @param sentBytes the {@code java.lang.String} field
	 */
	public void setSentBytes(String sentBytes) {
		this.sentBytes = sentBytes;
	}

	/**
	 * Retrieves {@code {@link #bitrate}}
	 *
	 * @return value of {@link #bitrate}
	 */
	public String getBitrate() {
		return bitrate;
	}

	/**
	 * Sets {@code bitrate}
	 *
	 * @param bitrate the {@code java.lang.String} field
	 */
	public void setBitrate(String bitrate) {
		this.bitrate = bitrate;
	}

	/**
	 * Retrieves {@code {@link #unsentPackets}}
	 *
	 * @return value of {@link #unsentPackets}
	 */
	public String getUnsentPackets() {
		return unsentPackets;
	}

	/**
	 * Sets {@code unsentPackets}
	 *
	 * @param unsentPackets the {@code java.lang.String} field
	 */
	public void setUnsentPackets(String unsentPackets) {
		this.unsentPackets = unsentPackets;
	}

	/**
	 * Retrieves {@code {@link #unsentBytes}}
	 *
	 * @return value of {@link #unsentBytes}
	 */
	public String getUnsentBytes() {
		return unsentBytes;
	}

	/**
	 * Sets {@code unsentBytes}
	 *
	 * @param unsentBytes the {@code java.lang.String} field
	 */
	public void setUnsentBytes(String unsentBytes) {
		this.unsentBytes = unsentBytes;
	}

	/**
	 * Retrieves {@code {@link #lastError}}
	 *
	 * @return value of {@link #lastError}
	 */
	public String getLastError() {
		return lastError;
	}

	/**
	 * Sets {@code lastError}
	 *
	 * @param lastError the {@code java.lang.String} field
	 */
	public void setLastError(String lastError) {
		this.lastError = lastError;
	}

	/**
	 * Retrieves {@code {@link #occurred}}
	 *
	 * @return value of {@link #occurred}
	 */
	public String getOccurred() {
		return occurred;
	}

	/**
	 * Sets {@code occurred}
	 *
	 * @param occurred the {@code java.lang.String} field
	 */
	public void setOccurred(String occurred) {
		this.occurred = occurred;
	}

	/**
	 * Retrieves {@code {@link #reconnections}}
	 *
	 * @return value of {@link #reconnections}
	 */
	public String getReconnections() {
		return reconnections;
	}

	/**
	 * Sets {@code reconnections}
	 *
	 * @param reconnections the {@code java.lang.String} field
	 */
	public void setReconnections(String reconnections) {
		this.reconnections = reconnections;
	}

	/**
	 * Retrieves {@code {@link #aesEncryption}}
	 *
	 * @return value of {@link #aesEncryption}
	 */
	public String getAesEncryption() {
		return aesEncryption;
	}

	/**
	 * Sets {@code aesEncryption}
	 *
	 * @param aesEncryption the {@code java.lang.String} field
	 */
	public void setAesEncryption(String aesEncryption) {
		this.aesEncryption = aesEncryption;
	}

	/**
	 * Retrieves {@code {@link #resentPacket}}
	 *
	 * @return value of {@link #resentPacket}
	 */
	public String getResentPacket() {
		return resentPacket;
	}

	/**
	 * Sets {@code resentPacket}
	 *
	 * @param resentPacket the {@code java.lang.String} field
	 */
	public void setResentPacket(String resentPacket) {
		this.resentPacket = resentPacket;
	}

	/**
	 * Retrieves {@code {@link #resentBytes}}
	 *
	 * @return value of {@link #resentBytes}
	 */
	public String getResentBytes() {
		return resentBytes;
	}

	/**
	 * Sets {@code resentBytes}
	 *
	 * @param resentBytes the {@code java.lang.String} field
	 */
	public void setResentBytes(String resentBytes) {
		this.resentBytes = resentBytes;
	}

	/**
	 * Retrieves {@code {@link #droppedPackets}}
	 *
	 * @return value of {@link #droppedPackets}
	 */
	public String getDroppedPackets() {
		return droppedPackets;
	}

	/**
	 * Sets {@code droppedPackets}
	 *
	 * @param droppedPackets the {@code java.lang.String} field
	 */
	public void setDroppedPackets(String droppedPackets) {
		this.droppedPackets = droppedPackets;
	}

	/**
	 * Retrieves {@code {@link #droppedBytes}}
	 *
	 * @return value of {@link #droppedBytes}
	 */
	public String getDroppedBytes() {
		return droppedBytes;
	}

	/**
	 * Sets {@code droppedBytes}
	 *
	 * @param droppedBytes the {@code java.lang.String} field
	 */
	public void setDroppedBytes(String droppedBytes) {
		this.droppedBytes = droppedBytes;
	}

	/**
	 * Retrieves {@code {@link #receivedACKs}}
	 *
	 * @return value of {@link #receivedACKs}
	 */
	public String getReceivedACKs() {
		return receivedACKs;
	}

	/**
	 * Sets {@code receivedACKs}
	 *
	 * @param receivedACKs the {@code java.lang.String} field
	 */
	public void setReceivedACKs(String receivedACKs) {
		this.receivedACKs = receivedACKs;
	}

	/**
	 * Retrieves {@code {@link #receivedNAKs}}
	 *
	 * @return value of {@link #receivedNAKs}
	 */
	public String getReceivedNAKs() {
		return receivedNAKs;
	}

	/**
	 * Sets {@code receivedNAKs}
	 *
	 * @param receivedNAKs the {@code java.lang.String} field
	 */
	public void setReceivedNAKs(String receivedNAKs) {
		this.receivedNAKs = receivedNAKs;
	}

	/**
	 * Retrieves {@code {@link #mtu}}
	 *
	 * @return value of {@link #mtu}
	 */
	public String getMtu() {
		return mtu;
	}

	/**
	 * Sets {@code mtu}
	 *
	 * @param mtu the {@code java.lang.String} field
	 */
	public void setMtu(String mtu) {
		this.mtu = mtu;
	}

	/**
	 * Retrieves {@code {@link #maxBandwidth}}
	 *
	 * @return value of {@link #maxBandwidth}
	 */
	public String getMaxBandwidth() {
		return maxBandwidth;
	}

	/**
	 * Sets {@code maxBandwidth}
	 *
	 * @param maxBandwidth the {@code java.lang.String} field
	 */
	public void setMaxBandwidth(String maxBandwidth) {
		this.maxBandwidth = maxBandwidth;
	}

	/**
	 * Retrieves {@code {@link #pathMaxBandwidth}}
	 *
	 * @return value of {@link #pathMaxBandwidth}
	 */
	public String getPathMaxBandwidth() {
		return pathMaxBandwidth;
	}

	/**
	 * Sets {@code pathMaxBandwidth}
	 *
	 * @param pathMaxBandwidth the {@code java.lang.String} field
	 */
	public void setPathMaxBandwidth(String pathMaxBandwidth) {
		this.pathMaxBandwidth = pathMaxBandwidth;
	}

	/**
	 * Retrieves {@code {@link #rtt}}
	 *
	 * @return value of {@link #rtt}
	 */
	public String getRtt() {
		return rtt;
	}

	/**
	 * Sets {@code rtt}
	 *
	 * @param rtt the {@code java.lang.String} field
	 */
	public void setRtt(String rtt) {
		this.rtt = rtt;
	}

	/**
	 * Retrieves {@code {@link #localBufferLevel}}
	 *
	 * @return value of {@link #localBufferLevel}
	 */
	public String getLocalBufferLevel() {
		return localBufferLevel;
	}

	/**
	 * Sets {@code localBufferLevel}
	 *
	 * @param localBufferLevel the {@code java.lang.String} field
	 */
	public void setLocalBufferLevel(String localBufferLevel) {
		this.localBufferLevel = localBufferLevel;
	}

	/**
	 * Retrieves {@code {@link #latency}}
	 *
	 * @return value of {@link #latency}
	 */
	public String getLatency() {
		return latency;
	}

	/**
	 * Sets {@code latency}
	 *
	 * @param latency the {@code java.lang.String} field
	 */
	public void setLatency(String latency) {
		this.latency = latency;
	}

	/**
	 * Get the value by the metric monitoring
	 *
	 * @param metric the metric is metric monitoring
	 * @return String value of encoder monitoring properties by metric
	 */
	public String getValueByMetric(StreamMonitoringMetric metric) {
		switch (metric) {
			case STATE:
				return getState();
			case UPTIME:
				return getUpTime();
			case NAME:
				return getName();
			case SOURCE_PORT:
				return getSourcePort();
			case SSRC:
				return getsSRC();
			case SENT_PACKETS:
				return getSentPackets();
			case SENT_BYTES:
				return getSentBytes();
			case BITRATE:
				return getBitrate();
			case UNSENT_PACKETS:
				return getUnsentPackets();
			case UNSENT_BYTES:
				return getUnsentBytes();
			case LAST_ERROR:
				return getLastError();
			case OCCURRED:
				return getOccurred();
			case RECONNECTIONS:
				return getReconnections();
			case AES_ENCRYPTION:
				return getAesEncryption();
			case RESENT_PACKET:
				return getResentPacket();
			case RESENT_BYTES:
				return getResentBytes();
			case DROPPED_PACKETS:
				return getDroppedPackets();
			case DROPPED_BYTES:
				return getDroppedBytes();
			case RECEIVED_ACKS:
				return getReceivedACKs();
			case RECEIVED_NAKS:
				return getReceivedNAKs();
			case MTU:
				return getMtu();
			case MAX_BANDWIDTH:
				return getMaxBandwidth();
			case PATH_MAX_BANDWIDTH:
				return getPathMaxBandwidth();
			case RTT:
				return getRtt();
			case LOCAL_BUFFER_LEVEL:
				return getLocalBufferLevel();
			case LATENCY:
				return getLatency();
			default:
				return EncoderConstant.NONE;
		}
	}
}