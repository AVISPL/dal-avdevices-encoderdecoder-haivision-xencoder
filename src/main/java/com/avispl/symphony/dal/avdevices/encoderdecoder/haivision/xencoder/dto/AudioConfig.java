package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * AudioConfig
 *
 * @author Kevin / Symphony Dev Team<br>
 * Created on 4/19/2022
 * @since 1.0.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AudioConfig {

	@JsonAlias("Encoder ID")
	private String id;

	@JsonAlias("Audio Input")
	private String interfaceName;

	@JsonAlias("Audio Bitrate")
	private String bitRate;

	@JsonAlias("Audio Samplerate")
	private String sampleRate;

	@JsonAlias("Audio Mode")
	private String mode;

	@JsonAlias("Audio Algorithm")
	private String algorithm;

	@JsonAlias("Name")
	private String name;

	@JsonAlias("Audio Language")
	private String lang;

	@JsonAlias("Audio Input Level")
	private String level;

	private String state;

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
	 * Retrieves {@code {@link #interfaceName}}
	 *
	 * @return value of {@link #interfaceName}
	 */
	public String getInterfaceName() {
		return interfaceName;
	}

	/**
	 * Sets {@code interfaceName}
	 *
	 * @param interfaceName the {@code java.lang.String} field
	 */
	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	/**
	 * Retrieves {@code {@link #bitRate}}
	 *
	 * @return value of {@link #bitRate}
	 */
	public String getBitRate() {
		return bitRate;
	}

	/**
	 * Sets {@code bitRate}
	 *
	 * @param bitRate the {@code java.lang.String} field
	 */
	public void setBitRate(String bitRate) {
		this.bitRate = bitRate;
	}

	/**
	 * Retrieves {@code {@link #sampleRate}}
	 *
	 * @return value of {@link #sampleRate}
	 */
	public String getSampleRate() {
		return sampleRate;
	}

	/**
	 * Sets {@code sampleRate}
	 *
	 * @param sampleRate the {@code java.lang.String} field
	 */
	public void setSampleRate(String sampleRate) {
		this.sampleRate = sampleRate;
	}

	/**
	 * Retrieves {@code {@link #mode}}
	 *
	 * @return value of {@link #mode}
	 */
	public String getMode() {
		return mode;
	}

	/**
	 * Sets {@code mode}
	 *
	 * @param mode the {@code java.lang.String} field
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}

	/**
	 * Retrieves {@code {@link #algorithm}}
	 *
	 * @return value of {@link #algorithm}
	 */
	public String getAlgorithm() {
		return algorithm;
	}

	/**
	 * Sets {@code algorithm}
	 *
	 * @param algorithm the {@code java.lang.String} field
	 */
	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
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
	 * Retrieves {@code {@link #lang}}
	 *
	 * @return value of {@link #lang}
	 */
	public String getLang() {
		return lang;
	}

	/**
	 * Sets {@code lang}
	 *
	 * @param lang the {@code java.lang.String} field
	 */
	public void setLang(String lang) {
		this.lang = lang;
	}

	/**
	 * Retrieves {@code {@link #level}}
	 *
	 * @return value of {@link #level}
	 */
	public String getLevel() {
		return level;
	}

	/**
	 * Sets {@code level}
	 *
	 * @param level the {@code java.lang.String} field
	 */
	public void setLevel(String level) {
		this.level = level;
	}

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
}