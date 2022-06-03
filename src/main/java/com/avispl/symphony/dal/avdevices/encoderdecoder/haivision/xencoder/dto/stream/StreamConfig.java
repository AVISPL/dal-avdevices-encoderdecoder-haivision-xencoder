/*
 *  * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dto.stream;

import static com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dropdownlist.EnumTypeHandler.replaceSpecialCharacter;

import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.common.EncoderConstant;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dropdownlist.ConnectionModeEnum;
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

	@JsonAlias("Destination Port")
	private String destinationPort;

	@JsonAlias("Source Port")
	private String sourcePort;

	@JsonAlias("Encapsulation")
	private String encapsulation;

	@JsonAlias("Mode")
	private String srtMode;

	@JsonAlias("Contents")
	private String contents;

	@JsonAlias("Still Image File")
	private String stillImageFile;

	@JsonAlias("Transport Stream ID")
	private String transportStreamID;

	@JsonAlias("Program Number")
	private String programNumber;

	@JsonAlias("MTU")
	private String mtu;

	@JsonAlias("TTL")
	private String ttl;

	@JsonAlias("TOS")
	private String tos;

	@JsonAlias("Bandwidth")
	private String averageBandwidth;

	@JsonAlias("Traffic Shaping")
	private String shaping;

	@JsonAlias("AES Encryption")
	private String aesEncryption;

	@JsonAlias("Key Length")
	private String keyLength;

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

	@JsonAlias("Publish Name")
	private String publishName;

	@JsonAlias("TCP Port")
	private String tcpPort;

	@JsonAlias("User Name")
	private String username;

	@JsonAlias("Password")
	private String password;

	private String passphrase;

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
	 * Retrieves {@code {@link #averageBandwidth }}
	 *
	 * @return value of {@link #averageBandwidth}
	 */
	public String getAverageBandwidth() {
		return averageBandwidth;
	}

	/**
	 * Sets {@code bandwidth}
	 *
	 * @param averageBandwidth the {@code java.lang.String} field
	 */
	public void setAverageBandwidth(String averageBandwidth) {
		this.averageBandwidth = averageBandwidth;
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
	 * Retrieves {@code {@link #publishName}}
	 *
	 * @return value of {@link #publishName}
	 */
	public String getPublishName() {
		return publishName;
	}

	/**
	 * Sets {@code publishName}
	 *
	 * @param publishName the {@code java.lang.String} field
	 */
	public void setPublishName(String publishName) {
		this.publishName = publishName;
	}

	/**
	 * Retrieves {@code {@link #tcpPort}}
	 *
	 * @return value of {@link #tcpPort}
	 */
	public String getTcpPort() {
		return tcpPort;
	}

	/**
	 * Sets {@code tcpPort}
	 *
	 * @param tcpPort the {@code java.lang.String} field
	 */
	public void setTcpPort(String tcpPort) {
		this.tcpPort = tcpPort;
	}

	/**
	 * Retrieves {@code {@link #username}}
	 *
	 * @return value of {@link #username}
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Sets {@code username}
	 *
	 * @param username the {@code java.lang.String} field
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Retrieves {@code {@link #password}}
	 *
	 * @return value of {@link #password}
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets {@code password}
	 *
	 * @param password the {@code java.lang.String} field
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Retrieves {@code {@link #passphrase}}
	 *
	 * @return value of {@link #passphrase}
	 */
	public String getPassphrase() {
		return passphrase;
	}

	/**
	 * Sets {@code passphrase}
	 *
	 * @param passphrase the {@code java.lang.String} field
	 */
	public void setPassphrase(String passphrase) {
		this.passphrase = passphrase;
	}

	/**
	 * Retrieves {@code {@link #destinationPort}}
	 *
	 * @return value of {@link #destinationPort}
	 */
	public String getDestinationPort() {
		return destinationPort;
	}

	/**
	 * Sets {@code destinationPort}
	 *
	 * @param destinationPort the {@code java.lang.String} field
	 */
	public void setDestinationPort(String destinationPort) {
		this.destinationPort = destinationPort;
	}

	/**
	 * Retrieves {@code {@link #keyLength}}
	 *
	 * @return value of {@link #keyLength}
	 */
	public String getKeyLength() {
		return keyLength;
	}

	/**
	 * Sets {@code keyLength}
	 *
	 * @param keyLength the {@code java.lang.String} field
	 */
	public void setKeyLength(String keyLength) {
		this.keyLength = keyLength;
	}

	/**
	 * /**
	 * Get To String of stream configs
	 *
	 * @return String is full param of stream config
	 */
	@Override
	public String toString() {
		String paramRequest = "";
		String audioSource = audioList.stream().map(Audio::getAudioId).collect(Collectors.toList()).toString();
		audioSource = audioSource.replace("[", EncoderConstant.EMPTY_STRING).replace("]", EncoderConstant.EMPTY_STRING);
		String nameValue = getFormatNameByValue(name, "name");
		String videoSrcValue = getFormatNameByValue(video, "videoSrc");
		String audioSrcValue = getFormatNameByValue(audioSource, "audioSrc");
		String protocolValue = getFormatNameByValue(encapsulation, "encapsulation");
		String addressValue = getFormatNameByValue(address, "addR");
		String stillImageValue = getFormatNameByValue(stillImageFile, "stillImage");
		String ttlValue = getFormatNameByValue(ttl, "ttl");
		String tosValue = getFormatNameByValue(tos, "tos");
		String portValue = getFormatNameByValue(port, "port");
		String fecValue = getFormatNameByValue(fec, "fec");
		String trafficShapingValue = getFormatNameByValue(shaping, "shaping");
		String idleCellsValue = getFormatNameByValue(idleCells, "idleCells");
		String delayAudioValue = getFormatNameByValue(delayAudio, "delayAudio");
		String bandwidthOverHeadValue = getFormatNameByValue(bandwidthOverhead, "ceiling");
		String mtuValue = getFormatNameByValue(mtu, "mtu");
		String publishNameValue = getFormatNameByValue(publishName, "publish");
		String usernameValue = getFormatNameByValue(username, "username");
		String passwordValue = getFormatNameByValue(password, "password");
		String sourcePortValue = getFormatNameByValue(sourcePort, "sourcePort");
		String srtModeValue = getFormatNameByValue(srtMode, "mode");
		String networkAdaptiveValue = getFormatNameByValue(networkAdaptive, "adaptive");
		String latencyValue = getFormatNameByValue(networkAdaptive, "latency");
		String encryptionValue = getFormatNameByValue(aesEncryption, "encryption");
		String passphraseValue = getFormatNameByValue(passphrase, "passphrase");

		if (ProtocolEnum.TS_UDP.getValue().equals(encapsulation) || ProtocolEnum.TS_RTP.getValue().equals(encapsulation)) {
			paramRequest = String.format(EncoderConstant.EIGHTH_STRING_FORMAT, fecValue, trafficShapingValue, idleCellsValue, delayAudioValue, mtuValue, ttlValue, tosValue, bandwidthOverHeadValue);
		}
		if (ProtocolEnum.RTMP.getValue().equals(encapsulation)) {
			paramRequest = String.format(EncoderConstant.THREE_STRING_FORMAT, publishNameValue, usernameValue, passwordValue);
		}
		if (ProtocolEnum.DIRECT_RTP.getValue().equals(encapsulation)) {
			paramRequest = String.format(" %s %s ", mtuValue, bandwidthOverHeadValue);
		}
		if (ProtocolEnum.TS_SRT.getValue().equals(encapsulation)) {
			paramRequest = String.format(EncoderConstant.ELEVEN_STRING_FORMAT, ttlValue, tosValue, bandwidthOverHeadValue, idleCellsValue, srtModeValue, networkAdaptiveValue, latencyValue, encryptionValue,
					passphraseValue, mtuValue, trafficShapingValue);
			if (ConnectionModeEnum.CALLER.getName().equals(srtMode)) {
				paramRequest = paramRequest + String.format(" %s ", sourcePortValue);
			}
			if (ConnectionModeEnum.LISTENER.getName().equals(srtMode)) {
				paramRequest = paramRequest + String.format(" %s", bandwidthOverHeadValue);
			}
		}
		paramRequest = paramRequest + String.format(EncoderConstant.SEVEN_STRING_FORMAT, nameValue, videoSrcValue, audioSrcValue, protocolValue, addressValue, stillImageValue, portValue);
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
		return StringUtils.isNullOrEmpty(value) ? "" : String.format("%s=%s", name, replaceSpecialCharacter(value));
	}
}