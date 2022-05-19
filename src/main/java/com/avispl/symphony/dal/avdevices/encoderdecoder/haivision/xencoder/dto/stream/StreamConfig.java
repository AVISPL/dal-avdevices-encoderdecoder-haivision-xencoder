/*
 *  * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dto.stream;

import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dropdownlist.ProtocolEnum;
import com.avispl.symphony.dal.util.StringUtils;

/**
 * StreamStatistics DTO class
 *
 * @author Kevin / Symphony Dev Team<br>
 * Created on 4/21/2022
 * @since 1.0.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class StreamConfig {

	private String state;
	private StreamSAP sap;
	private List<Audio> audioList;
	private String video;

	@JsonAlias("Stream ID")
	private String id;

	@JsonAlias("Name")
	private String name;

	@JsonAlias("Address")
	private String address;

	@JsonAlias("UDP Port")
	private String port;

	@JsonAlias("Encapsulation")
	private String encapsulation;

	@JsonAlias("Mode")
	private String srtMode;

	@JsonAlias("Contents")
	private String contents;

	@JsonAlias("Still Image File")
	private String stillImageFile;

	@JsonAlias("Transport Stream ID")
	private String transportStreamID ;

	@JsonAlias("Program Number")
	private String programNumber;

	@JsonAlias("MTU")
	private String mtu;

	@JsonAlias("TTL")
	private String ttl;

	@JsonAlias("TOS")
	private String tos;

	@JsonAlias("Bandwidth")
	private String bandwidth;

	@JsonAlias("Traffic Shaping")
	private String shaping;

	@JsonAlias("AES Encryption")
	private String aesEncryption;

	@JsonAlias("Network Adaptive")
	private String networkAdaptive;

	@JsonAlias("Max Traffic Overhead")
	private String maxTrafficOverhead;

	@JsonAlias("Added Latency")
	private String addedLatency;

	@JsonAlias("Persistent")
	private String persistent;

	@JsonAlias("FEC")
	private String fec;

	@JsonAlias("Idle Cells")
	private String idleCells;

	@JsonAlias("Delayed Audio")
	private String delayAudio;

	@JsonAlias("Ceiling")
	private String bandwidthOverhead;

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
	 * Retrieves {@code {@link #sap}}
	 *
	 * @return value of {@link #sap}
	 */
	public StreamSAP getSap() {
		return sap;
	}

	/**
	 * Sets {@code sap}
	 *
	 * @param sap the {@code com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dto.stream.StreamSAP} field
	 */
	public void setSap(StreamSAP sap) {
		this.sap = sap;
	}

	/**
	 * Retrieves {@code {@link #audioList}}
	 *
	 * @return value of {@link #audioList}
	 */
	public List<Audio> getAudioList() {
		return audioList;
	}

	/**
	 * Sets {@code audioList}
	 *
	 * @param audioList the {@code java.util.List<com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dto.stream.Audio>} field
	 */
	public void setAudioList(List<Audio> audioList) {
		this.audioList = audioList;
	}

	/**
	 * Retrieves {@code {@link #video}}
	 *
	 * @return value of {@link #video}
	 */
	public String getVideo() {
		return video;
	}

	/**
	 * Sets {@code video}
	 *
	 * @param video the {@code java.lang.String} field
	 */
	public void setVideo(String video) {
		this.video = video;
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
	 * Retrieves {@code {@link #address}}
	 *
	 * @return value of {@link #address}
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Sets {@code address}
	 *
	 * @param address the {@code java.lang.String} field
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * Retrieves {@code {@link #port}}
	 *
	 * @return value of {@link #port}
	 */
	public String getPort() {
		return port;
	}

	/**
	 * Sets {@code port}
	 *
	 * @param port the {@code java.lang.String} field
	 */
	public void setPort(String port) {
		this.port = port;
	}

	/**
	 * Retrieves {@code {@link #encapsulation}}
	 *
	 * @return value of {@link #encapsulation}
	 */
	public String getEncapsulation() {
		return encapsulation;
	}

	/**
	 * Sets {@code encapsulation}
	 *
	 * @param encapsulation the {@code java.lang.String} field
	 */
	public void setEncapsulation(String encapsulation) {
		this.encapsulation = encapsulation;
	}

	/**
	 * Retrieves {@code {@link #srtMode}}
	 *
	 * @return value of {@link #srtMode}
	 */
	public String getSrtMode() {
		return srtMode;
	}

	/**
	 * Sets {@code srtMode}
	 *
	 * @param srtMode the {@code java.lang.String} field
	 */
	public void setSrtMode(String srtMode) {
		this.srtMode = srtMode;
	}

	/**
	 * Retrieves {@code {@link #contents}}
	 *
	 * @return value of {@link #contents}
	 */
	public String getContents() {
		return contents;
	}

	/**
	 * Sets {@code contents}
	 *
	 * @param contents the {@code java.lang.String} field
	 */
	public void setContents(String contents) {
		this.contents = contents;
	}

	/**
	 * Retrieves {@code {@link #stillImageFile}}
	 *
	 * @return value of {@link #stillImageFile}
	 */
	public String getStillImageFile() {
		return stillImageFile;
	}

	/**
	 * Sets {@code stillImageFile}
	 *
	 * @param stillImageFile the {@code java.lang.String} field
	 */
	public void setStillImageFile(String stillImageFile) {
		this.stillImageFile = stillImageFile;
	}

	/**
	 * Retrieves {@code {@link #transportStreamID}}
	 *
	 * @return value of {@link #transportStreamID}
	 */
	public String getTransportStreamID() {
		return transportStreamID;
	}

	/**
	 * Sets {@code transportStreamID}
	 *
	 * @param transportStreamID the {@code java.lang.String} field
	 */
	public void setTransportStreamID(String transportStreamID) {
		this.transportStreamID = transportStreamID;
	}

	/**
	 * Retrieves {@code {@link #programNumber}}
	 *
	 * @return value of {@link #programNumber}
	 */
	public String getProgramNumber() {
		return programNumber;
	}

	/**
	 * Sets {@code programNumber}
	 *
	 * @param programNumber the {@code java.lang.String} field
	 */
	public void setProgramNumber(String programNumber) {
		this.programNumber = programNumber;
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
	 * Retrieves {@code {@link #ttl}}
	 *
	 * @return value of {@link #ttl}
	 */
	public String getTtl() {
		return ttl;
	}

	/**
	 * Sets {@code ttl}
	 *
	 * @param ttl the {@code java.lang.String} field
	 */
	public void setTtl(String ttl) {
		this.ttl = ttl;
	}

	/**
	 * Retrieves {@code {@link #tos}}
	 *
	 * @return value of {@link #tos}
	 */
	public String getTos() {
		return tos;
	}

	/**
	 * Sets {@code tos}
	 *
	 * @param tos the {@code java.lang.String} field
	 */
	public void setTos(String tos) {
		this.tos = tos;
	}

	/**
	 * Retrieves {@code {@link #bandwidth}}
	 *
	 * @return value of {@link #bandwidth}
	 */
	public String getBandwidth() {
		return bandwidth;
	}

	/**
	 * Sets {@code bandwidth}
	 *
	 * @param bandwidth the {@code java.lang.String} field
	 */
	public void setBandwidth(String bandwidth) {
		this.bandwidth = bandwidth;
	}

	/**
	 * Retrieves {@code {@link #shaping}}
	 *
	 * @return value of {@link #shaping}
	 */
	public String getShaping() {
		return shaping;
	}

	/**
	 * Sets {@code shaping}
	 *
	 * @param shaping the {@code java.lang.String} field
	 */
	public void setShaping(String shaping) {
		this.shaping = shaping;
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
	 * Retrieves {@code {@link #networkAdaptive}}
	 *
	 * @return value of {@link #networkAdaptive}
	 */
	public String getNetworkAdaptive() {
		return networkAdaptive;
	}

	/**
	 * Sets {@code networkAdaptive}
	 *
	 * @param networkAdaptive the {@code java.lang.String} field
	 */
	public void setNetworkAdaptive(String networkAdaptive) {
		this.networkAdaptive = networkAdaptive;
	}

	/**
	 * Retrieves {@code {@link #maxTrafficOverhead}}
	 *
	 * @return value of {@link #maxTrafficOverhead}
	 */
	public String getMaxTrafficOverhead() {
		return maxTrafficOverhead;
	}

	/**
	 * Sets {@code maxTrafficOverhead}
	 *
	 * @param maxTrafficOverhead the {@code java.lang.String} field
	 */
	public void setMaxTrafficOverhead(String maxTrafficOverhead) {
		this.maxTrafficOverhead = maxTrafficOverhead;
	}

	/**
	 * Retrieves {@code {@link #addedLatency}}
	 *
	 * @return value of {@link #addedLatency}
	 */
	public String getAddedLatency() {
		return addedLatency;
	}

	/**
	 * Sets {@code addedLatency}
	 *
	 * @param addedLatency the {@code java.lang.String} field
	 */
	public void setAddedLatency(String addedLatency) {
		this.addedLatency = addedLatency;
	}

	/**
	 * Retrieves {@code {@link #persistent}}
	 *
	 * @return value of {@link #persistent}
	 */
	public String getPersistent() {
		return persistent;
	}

	/**
	 * Sets {@code persistent}
	 *
	 * @param persistent the {@code java.lang.String} field
	 */
	public void setPersistent(String persistent) {
		this.persistent = persistent;
	}

	/**
	 * Retrieves {@code {@link #fec}}
	 *
	 * @return value of {@link #fec}
	 */
	public String getFec() {
		return fec;
	}

	/**
	 * Sets {@code fec}
	 *
	 * @param fec the {@code java.lang.String} field
	 */
	public void setFec(String fec) {
		this.fec = fec;
	}

	/**
	 * Retrieves {@code {@link #idleCells}}
	 *
	 * @return value of {@link #idleCells}
	 */
	public String getIdleCells() {
		return idleCells;
	}

	/**
	 * Sets {@code idleCells}
	 *
	 * @param idleCells the {@code java.lang.String} field
	 */
	public void setIdleCells(String idleCells) {
		this.idleCells = idleCells;
	}

	/**
	 * Retrieves {@code {@link #delayAudio}}
	 *
	 * @return value of {@link #delayAudio}
	 */
	public String getDelayAudio() {
		return delayAudio;
	}

	/**
	 * Sets {@code delayAudio}
	 *
	 * @param delayAudio the {@code java.lang.String} field
	 */
	public void setDelayAudio(String delayAudio) {
		this.delayAudio = delayAudio;
	}

	/**
	 * Retrieves {@code {@link #bandwidthOverhead}}
	 *
	 * @return value of {@link #bandwidthOverhead}
	 */
	public String getBandwidthOverhead() {
		return bandwidthOverhead;
	}

	/**
	 * Sets {@code bandwidthOverhead}
	 *
	 * @param bandwidthOverhead the {@code java.lang.String} field
	 */
	public void setBandwidthOverhead(String bandwidthOverhead) {
		this.bandwidthOverhead = bandwidthOverhead;
	}

	@Override
	public String toString() {
		String paramRequest = "";
		String audioSource = audioList.stream().map(Audio::getAudioId).collect(Collectors.toList()).toString().replace("[", "").replace("]", "");
		if (ProtocolEnum.TS_UDP.getValue().equals(encapsulation)) {
			String nameValue = getFormatNameByValue(name, "name");
			String videoSrcValue = getFormatNameByValue(video, "videoSrc");
			String audioSrcValue = getFormatNameByValue(audioSource, "audioSrc");
			String protocolValue = getFormatNameByValue(encapsulation, "encapsulation");
			String addressValue = getFormatNameByValue(address, "addR");
			String portValue = getFormatNameByValue(port, "port");
			String fecValue = getFormatNameByValue(fec, "efc");
			String trafficShapingValue = getFormatNameByValue(shaping, "shaping");
			String idleCellsValue = getFormatNameByValue(idleCells, "idleCells");
			String delayAudioValue = getFormatNameByValue(delayAudio, "delayAudio");
			String mtuValue = getFormatNameByValue(mtu, "mtu");
			String ttlValue = getFormatNameByValue(ttl, "ttl");
			String tosValue = getFormatNameByValue(tos, "tos");
			String stillImageValue = getFormatNameByValue(stillImageFile, "stillImage");
			paramRequest = String.format(" %s %s %s %s %s %s %s %s %s %s %s %s %s %s", nameValue, videoSrcValue, audioSrcValue, protocolValue, addressValue, portValue, fecValue, trafficShapingValue,
					idleCellsValue, delayAudioValue, mtuValue, ttlValue, tosValue, stillImageValue);
		}
		return paramRequest;
	}

	/**
	 * Get format name if value different null or empty
	 *
	 * @param value is value of StreamConfig instance
	 * @param name is name of param request to send command
	 * @return String is format name or empty string
	 */
	private String getFormatNameByValue(String value, String name) {
		return StringUtils.isNullOrEmpty(value) ? "" : String.format("%s=%s", name, value);
	}
}