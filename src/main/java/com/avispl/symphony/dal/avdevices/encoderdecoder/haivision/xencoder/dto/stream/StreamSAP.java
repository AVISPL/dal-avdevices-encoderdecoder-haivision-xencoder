/*
 *  * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dto.stream;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * StreamSAP DTO class
 *
 * @author Kevin / Symphony Dev Team<br>
 * Created on 4/21/2022
 * @since 1.0.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class StreamSAP {

	@JsonAlias("Session ID")
	private String id;

	@JsonAlias("Advertise")
	private String advertise;

	@JsonAlias("Name")
	private String name;

	@JsonAlias("Description")
	private String desc;

	@JsonAlias("Keywords")
	private String keywords;

	@JsonAlias("Author")
	private String author;

	@JsonAlias("Copyright")
	private String copyright;

	@JsonAlias("Advertisement Address")
	private String address;

	@JsonAlias("Advertisement Port")
	private String port;

	@JsonAlias("Streams")
	private String streamId;

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
	 * Retrieves {@code {@link #advertise}}
	 *
	 * @return value of {@link #advertise}
	 */
	public String getAdvertise() {
		return advertise;
	}

	/**
	 * Sets {@code advertise}
	 *
	 * @param advertise the {@code java.lang.String} field
	 */
	public void setAdvertise(String advertise) {
		this.advertise = advertise;
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
	 * Retrieves {@code {@link #desc}}
	 *
	 * @return value of {@link #desc}
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * Sets {@code desc}
	 *
	 * @param desc the {@code java.lang.String} field
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}

	/**
	 * Retrieves {@code {@link #keywords}}
	 *
	 * @return value of {@link #keywords}
	 */
	public String getKeywords() {
		return keywords;
	}

	/**
	 * Sets {@code keywords}
	 *
	 * @param keywords the {@code java.lang.String} field
	 */
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	/**
	 * Retrieves {@code {@link #author}}
	 *
	 * @return value of {@link #author}
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * Sets {@code author}
	 *
	 * @param author the {@code java.lang.String} field
	 */
	public void setAuthor(String author) {
		this.author = author;
	}

	/**
	 * Retrieves {@code {@link #copyright}}
	 *
	 * @return value of {@link #copyright}
	 */
	public String getCopyright() {
		return copyright;
	}

	/**
	 * Sets {@code copyright}
	 *
	 * @param copyright the {@code java.lang.String} field
	 */
	public void setCopyright(String copyright) {
		this.copyright = copyright;
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
	 * Retrieves {@code {@link #streamId}}
	 *
	 * @return value of {@link #streamId}
	 */
	public String getStreamId() {
		return streamId;
	}

	/**
	 * Sets {@code streamId}
	 *
	 * @param streamId the {@code java.lang.String} field
	 */
	public void setStreamId(String streamId) {
		this.streamId = streamId;
	}
}