/*
 * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.avispl.symphony.api.dal.control.Controller;
import com.avispl.symphony.api.dal.dto.control.AdvancedControllableProperty;
import com.avispl.symphony.api.dal.dto.control.ControllableProperty;
import com.avispl.symphony.api.dal.dto.monitor.ExtendedStatistics;
import com.avispl.symphony.api.dal.dto.monitor.Statistics;
import com.avispl.symphony.api.dal.error.ResourceNotReachableException;
import com.avispl.symphony.api.dal.monitor.Monitorable;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.command.Account;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.common.HaivisionCommand;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.common.HaivisionConstant;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.common.HaivisionMonitoringMetric;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.common.SystemMonitoringMetric;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dto.AudioConfig;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dto.AudioStatistics;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dto.AuthenticationRole;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dto.SystemInfoResponse;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dto.VideoConfig;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dto.VideoStatistics;
import com.avispl.symphony.dal.communicator.SshCommunicator;
import com.avispl.symphony.dal.util.StringUtils;

/**
 * HaivisionXEncoderCommunicator
 *
 * @author Kevin / Symphony Dev Team<br>
 * Created on 4/19/2022
 * @since 1.0.0
 */
public class HaivisionXEncoderCommunicator extends SshCommunicator implements Monitorable, Controller {

	private String roleBased;
	private boolean isConfigManagement;
	private boolean isEmergencyDelivery;
	private ExtendedStatistics localExtendedStatistics;
	private Integer countMonitoringNumber = null;
	private Map<String, String> failedMonitor = new HashMap<>();
	ObjectMapper objectMapper = new ObjectMapper();

	//The properties adapter
	private String streamNameFilter;
	private String portNumberFilter;
	private String streamStatusFilter;
	private String configManagement;
	private String audioFilter;
	private String videoFilter;

	/**
	 * Retrieves {@code {@link #streamNameFilter}}
	 *
	 * @return value of {@link #streamNameFilter}
	 */
	public String getStreamNameFilter() {
		return streamNameFilter;
	}

	/**
	 * Sets {@code streamNameFilter}
	 *
	 * @param streamNameFilter the {@code java.lang.String} field
	 */
	public void setStreamNameFilter(String streamNameFilter) {
		this.streamNameFilter = streamNameFilter;
	}

	/**
	 * Retrieves {@code {@link #portNumberFilter}}
	 *
	 * @return value of {@link #portNumberFilter}
	 */
	public String getPortNumberFilter() {
		return portNumberFilter;
	}

	/**
	 * Sets {@code portNumberFilter}
	 *
	 * @param portNumberFilter the {@code java.lang.String} field
	 */
	public void setPortNumberFilter(String portNumberFilter) {
		this.portNumberFilter = portNumberFilter;
	}

	/**
	 * Retrieves {@code {@link #streamStatusFilter}}
	 *
	 * @return value of {@link #streamStatusFilter}
	 */
	public String getStreamStatusFilter() {
		return streamStatusFilter;
	}

	/**
	 * Sets {@code streamStatusFilter}
	 *
	 * @param streamStatusFilter the {@code java.lang.String} field
	 */
	public void setStreamStatusFilter(String streamStatusFilter) {
		this.streamStatusFilter = streamStatusFilter;
	}

	/**
	 * Retrieves {@code {@link #audioFilter}}
	 *
	 * @return value of {@link #audioFilter}
	 */
	public String getAudioFilter() {
		return audioFilter;
	}

	/**
	 * Sets {@code audioFilter}
	 *
	 * @param audioFilter the {@code java.lang.String} field
	 */
	public void setAudioFilter(String audioFilter) {
		this.audioFilter = audioFilter;
	}

	/**
	 * Retrieves {@code {@link #videoFilter}}
	 *
	 * @return value of {@link #videoFilter}
	 */
	public String getVideoFilter() {
		return videoFilter;
	}

	/**
	 * Sets {@code videoFilter}
	 *
	 * @param videoFilter the {@code java.lang.String} field
	 */
	public void setVideoFilter(String videoFilter) {
		this.videoFilter = videoFilter;
	}

	/**
	 * List of audio statistics
	 */
	private final List<AudioStatistics> audioStatisticsList = new ArrayList<>();

	/**
	 * List of audio Config
	 */
	private final List<AudioConfig> audioConfigList = new ArrayList<>();

	/**
	 * List of video statistics
	 */
	private final List<VideoStatistics> videoStatisticsList = new ArrayList<>();

	/**
	 * List of video config
	 */
	private final List<VideoConfig> videoConfigList = new ArrayList<>();

	/**
	 * {@inheritDoc}
	 * <p>
	 * This method is called by Symphony to get the list of statistics to be displayed
	 *
	 * @return List<Statistics> This returns the list of statistics
	 */
	@Override
	public List<Statistics> getMultipleStatistics() {
		if (logger.isDebugEnabled()) {
			logger.debug(String.format("Getting statistics from the device X at host %s with port %s", this.host, this.getPort()));
		}
		ExtendedStatistics extendedStatistics = new ExtendedStatistics();
		Map<String, String> stats = new HashMap<>();
		List<AdvancedControllableProperty> advancedControllableProperties = new ArrayList<>();

		if (!StringUtils.isNullOrEmpty(getPassword()) && !StringUtils.isNullOrEmpty(getLogin())) {
			roleBased = retrieveUserRole();
		}
		isConfigManagement = handleAdapterPropertyIsConfigManagementFromUser();

		if (!isEmergencyDelivery) {
			localExtendedStatistics = new ExtendedStatistics();
			populateInformationFromDevice(stats, advancedControllableProperties);
			if (HaivisionConstant.OPERATOR.equals(roleBased) || HaivisionConstant.ADMIN.equals(roleBased)) {
				extendedStatistics.setControllableProperties(advancedControllableProperties);
			}
			extendedStatistics.setStatistics(stats);
			localExtendedStatistics = extendedStatistics;
		}
		extendedStatistics.setStatistics(stats);
		localExtendedStatistics = extendedStatistics;
		return Collections.singletonList(localExtendedStatistics);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void controlProperty(ControllableProperty controllableProperty) {
		String property = controllableProperty.getProperty();
		String value = String.valueOf(controllableProperty.getValue());
		if (logger.isDebugEnabled()) {
			logger.debug("controlProperty property" + property);
			logger.debug("controlProperty value" + value);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void controlProperties(List<ControllableProperty> list) {
		if (CollectionUtils.isEmpty(list)) {
			throw new IllegalArgumentException("Controllable properties cannot be null or empty");
		}
		for (ControllableProperty controllableProperty : list) {
			controlProperty(controllableProperty);
		}
	}

	/**
	 * Retrieve data and add to stats of the device
	 *
	 * @param stats list statistics
	 * @param advancedControllableProperties the advancedControllableProperties is list AdvancedControllableProperties
	 */
	private void populateInformationFromDevice(Map<String, String> stats, List<AdvancedControllableProperty> advancedControllableProperties) {
		retrieveUserRole();
		for (HaivisionMonitoringMetric haivisionMonitoringMetric : HaivisionMonitoringMetric.values()) {
			if (HaivisionMonitoringMetric.ACCOUNT.equals(haivisionMonitoringMetric)) {
				continue;
			}
			retrieveDataByMetric(stats, haivisionMonitoringMetric);
		}
		if (countMonitoringNumber == null) {
			countMonitoringNumber = getNumberMonitoringMetric();
		}
		if (failedMonitor.size() == countMonitoringNumber) {
			StringBuilder stringBuilder = new StringBuilder();
			for (Map.Entry<String, String> messageFailed : failedMonitor.entrySet()) {
				stringBuilder.append(messageFailed.getValue());
			}
			failedMonitor.clear();
			throw new ResourceNotReachableException("Get monitoring data failed: " + stringBuilder);
		}
	}

	/**
	 * Count metric monitoring in the metrics
	 *
	 * @return number monitoring in the metric
	 */
	private int getNumberMonitoringMetric() {
		int countMonitoringMetric = 0;
		for (HaivisionMonitoringMetric metric : HaivisionMonitoringMetric.values()) {
			if (metric.isMonitoring()) {
				countMonitoringMetric++;
			}
		}
		return countMonitoringMetric;
	}

	/**
	 * Retrieve data from the device
	 *
	 * @param metric list metric of device
	 * @param stats stats list statistics property
	 * @return data from metric of device or none if metric does not exist
	 */
	private void retrieveDataByMetric(Map<String, String> stats, HaivisionMonitoringMetric metric) {
		Objects.requireNonNull(metric);
		audioStatisticsList.clear();
		switch (metric) {
			case SYSTEM_INFORMATION:
				retrieveSystemInfoStatus(stats);
				break;
			case AUDIO_STATISTICS:
				retrieveAudioStatistics(metric);
				break;
			case VIDEO_STATISTICS:
				retrieveVideoStatistics(metric);
				break;
			case AUDIO_CONFIG:
				retrieveAudioConfig(metric);
				break;
			case VIDEO_CONFIG:
				retrieveVideoConfig(metric);
			case ACCOUNT:
			case ROLE_BASED:
				break;
			default:
				throw new IllegalArgumentException("Do not support haivisionStatisticsMetric: " + metric.name());
		}
	}

	private void retrieveVideoConfig(HaivisionMonitoringMetric metric) {
		try {
			String request = String.valueOf(HaivisionCommand.getMonitorCommand(metric));
			String responseData = send(request);
			if (responseData != null) {
				responseData = responseData.substring(request.length() + 2, responseData.lastIndexOf("\r\n"));
				String[] responseDataList = responseData.split("\r\n\r");
				for (String responseDataItem : responseDataList) {
					Map<String, String> result = popularConvertDataToObject(responseDataItem.replace("\t", "").replace("Configuration:\r\n", ""), request, false);
					if (result != null) {
						VideoConfig videoConfigResponse = objectMapper.convertValue(result, VideoConfig.class);
						videoConfigList.add(videoConfigResponse);
					}
				}
			}
		} catch (Exception e) {
			failedMonitor.put(metric.getName(), e.getMessage());
		}
	}

	private void retrieveAudioConfig(HaivisionMonitoringMetric metric) {
		try {
			String request = String.valueOf(HaivisionCommand.getMonitorCommand(metric));
			String responseData = send(request);
			if (responseData != null) {
				responseData = responseData.substring(request.length() + 2, responseData.lastIndexOf("\r\n"));
				String[] responseDataList = responseData.split("\r\n\r");
				for (String responseDataItem : responseDataList) {
					Map<String, String> result = popularConvertDataToObject(responseDataItem.replace("\t", "").replace("Configuration:\r\n", ""), request, false);
					if (result != null) {
						AudioConfig audioConfigResponse = objectMapper.convertValue(result, AudioConfig.class);
						audioConfigList.add(audioConfigResponse);
					}
				}
			}
		} catch (Exception e) {
			failedMonitor.put(metric.getName(), e.getMessage());
		}
	}

	/**
	 * Retrieve audio encoder statistics
	 *
	 * @param metric the metric is instance HaivisionMonitoringMetric
	 */
	private void retrieveAudioStatistics(HaivisionMonitoringMetric metric) {
		try {
			String request = String.valueOf(HaivisionCommand.getMonitorCommand(HaivisionMonitoringMetric.AUDIO_STATISTICS));
			String responseData = send(request);
			if (responseData != null) {
				responseData = responseData.substring(request.length() + 2, responseData.lastIndexOf("\r\n"));
				String[] responseDataList = responseData.split("\r\n\r");
				for (String responseDataItem : responseDataList) {
					Map<String, String> result = popularConvertDataToObject(responseDataItem.replace("\t", "").replace("Statistics:\r\n", ""), request, false);
					if (result != null) {
						AudioStatistics audioStatisticsResponse = objectMapper.convertValue(result, AudioStatistics.class);
						audioStatisticsList.add(audioStatisticsResponse);
					}
				}
			}
		} catch (Exception e) {
			failedMonitor.put(metric.getName(), e.getMessage());
		}
	}

	/**
	 * Retrieve video encoder statistics
	 *
	 * @param metric the metric is instance HaivisionMonitoringMetric
	 */
	private void retrieveVideoStatistics(HaivisionMonitoringMetric metric) {
		try {
			String request = String.valueOf(HaivisionCommand.getMonitorCommand(metric));
			String responseData = send(request);
			if (responseData != null) {
				responseData = responseData.substring(request.length() + 2, responseData.lastIndexOf("\r\n"));
				String[] responseDataList = responseData.split("\r\n\r");
				for (String responseDataItem : responseDataList) {
					Map<String, String> result = popularConvertDataToObject(responseDataItem.replace("\t", "").replace("Statistics:\r\n", ""), request, false);
					if (result != null) {
						VideoStatistics videoStatistics = objectMapper.convertValue(result, VideoStatistics.class);
						videoStatisticsList.add(videoStatistics);
					}
				}
			}
		} catch (Exception e) {
			failedMonitor.put(metric.getName(), e.getMessage());
		}
	}

	/**
	 * Retrieve system information status encoder
	 *
	 * @param stats list statistics property
	 */
	private void retrieveSystemInfoStatus(Map<String, String> stats) {
		try {
			String request = String.valueOf(HaivisionCommand.getMonitorCommand(HaivisionMonitoringMetric.SYSTEM_INFORMATION));
			String responseData = send(request);
			if (responseData != null) {
				SystemInfoResponse systemInfoResponse = objectMapper.convertValue(popularConvertDataToObject(responseData, request, true), SystemInfoResponse.class);
				for (SystemMonitoringMetric systemInfoMetric : SystemMonitoringMetric.values()) {
					stats.put(HaivisionConstant.SYSTEM_INFO_STATUS + HaivisionConstant.HASH + systemInfoMetric.getName(), checkForNullData(systemInfoResponse.getValueByMetric(systemInfoMetric)));
				}
			} else {
				contributeNoneValueForSystemInfo(stats);
			}
		} catch (Exception e) {
			contributeNoneValueForSystemInfo(stats);
			failedMonitor.put(HaivisionConstant.SYSTEM_INFO_STATUS, e.getMessage());
		}
	}

	/**
	 * check for null data
	 *
	 * @param value the value of monitoring properties
	 * @return String (none/value)
	 */
	private String checkForNullData(String value) {
		if (StringUtils.isNullOrEmpty(value)) {
			return HaivisionConstant.NONE;
		}
		return value;
	}

	/**
	 * Value of list statistics property of system info is none
	 *
	 * @param stats list statistics
	 */
	private void contributeNoneValueForSystemInfo(Map<String, String> stats) {
		for (SystemMonitoringMetric systemInfoMetric : SystemMonitoringMetric.values()) {
			stats.put(HaivisionConstant.SYSTEM_INFO_STATUS + HaivisionConstant.HASH + systemInfoMetric.getName(), HaivisionConstant.NONE);
		}
	}

	/**
	 * This method is used to retrieve User Role by send command "account {accountName} get"
	 *
	 * @throws ResourceNotReachableException When there is no valid User Role data or having an Exception
	 */
	private String retrieveUserRole() {
		try {
			String request = Account.ACCOUNT.getName() + getLogin() + HaivisionConstant.SPACE + Account.GET.getName();
			String response = send(request);
			AuthenticationRole authenticationRole = null;
			if (response != null) {
				//subString from command request to value
				Map<String, String> result = popularConvertDataToObject(response, request, true);
				authenticationRole = objectMapper.convertValue(result, AuthenticationRole.class);
			}
			if (authenticationRole == null || StringUtils.isNullOrEmpty(authenticationRole.getRole())) {
				throw new ResourceNotReachableException("Role based is empty");
			}
			return authenticationRole.getRole();
		} catch (Exception e) {
			throw new ResourceNotReachableException("Retrieve role based error: " + e.getMessage());
		}
	}

	/**
	 * Convert String data to Map<String,String>
	 *
	 * @param responseData the responseData is value retrieve to command
	 * @param request the request is command to retrieve the data
	 * @return Map<String, String> instance
	 */
	private Map<String, String> popularConvertDataToObject(String responseData, String request, boolean option) {
		try {
			if (option) {
				responseData = responseData.substring(request.length() + 2, responseData.lastIndexOf("\r\n")).replace("\t", "");
			}
			return Arrays.stream(responseData.split("\r\n"))
					.map(item -> item.split(HaivisionConstant.COLON))
					.collect(Collectors.toMap(
							key -> key[0].trim(),
							value -> value[1].trim()
					));
		} catch (Exception e) {
			return new HashMap<>();
		}
	}

	/**
	 * This method is used to handle  input from adapter properties in case is config management
	 *
	 * @return boolean is configManagement
	 */
	public boolean handleAdapterPropertyIsConfigManagementFromUser() {
		return !StringUtils.isNullOrEmpty(configManagement) && HaivisionConstant.TRUE.equalsIgnoreCase(configManagement);
	}
	//region perform controls
	//--------------------------------------------------------------------------------------------------------------------------------

	//--------------------------------------------------------------------------------------------------------------------------------
	//endregion
}
