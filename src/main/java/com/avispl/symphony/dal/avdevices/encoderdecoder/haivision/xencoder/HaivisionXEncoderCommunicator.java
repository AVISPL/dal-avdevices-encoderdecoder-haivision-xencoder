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
import java.util.UUID;
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
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.common.HaivisionCommand;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.common.AudioMonitoringMetric;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.common.HaivisionConstant;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.common.HaivisionMonitoringMetric;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.common.HaivisionUtil;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.common.StreamMonitoringMetric;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.common.SystemMonitoringMetric;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.common.VideoMonitoringMetric;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dto.AuthenticationRole;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dto.SystemInfoResponse;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dto.audio.AudioConfig;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dto.audio.AudioStatistics;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dto.stream.StreamConfig;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dto.stream.StreamStatistics;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dto.video.VideoConfig;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dto.video.VideoStatistics;
import com.avispl.symphony.dal.communicator.SshCommunicator;
import com.avispl.symphony.dal.util.StringUtils;

/**
 /**
 * An implementation of SshCommunicator to provide communication and interaction with Haivision Makito X Encoders
 * Supported features are:
 * <p>
 * Monitoring:
 * <li>Info System</li>
 * <li>Audio encoder statistics</li>
 * <li>Video encoder statistics</li>
 * <li>Output stream encoder statistics</li>
 * <p>
 * Controlling:
 * <li>Start/Stop /Edit audio encoder config</li>
 * <li>Start/Stop /Edit video encoder config</li>
 * <li>Create/ Edit/ Delete / Start/Stop /Edit output stream </li>
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

	private final String uuidDay = UUID.randomUUID().toString().replace(HaivisionConstant.DASH, "");

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
	private List<AudioConfig> audioConfigList = new ArrayList<>();

	/**
	 * List of video statistics
	 */
	private final List<VideoStatistics> videoStatisticsList = new ArrayList<>();

	/**
	 * List of video config
	 */
	private List<VideoConfig> videoConfigList = new ArrayList<>();

	/**
	 * List of video statistics
	 */
	private List<StreamStatistics> streamStatisticsList = new ArrayList<>();

	/**
	 * List of video statistics
	 */
	private List<StreamConfig> streamConfigList = new ArrayList<>();

	/**
	 * Constructor
	 */
	public HaivisionXEncoderCommunicator() {
		this.setCommandErrorList(Collections.singletonList("~"));
		this.setCommandSuccessList(Collections.singletonList("~$ "));
		this.setLoginSuccessList(Collections.singletonList("~$ "));
	}

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
			if (HaivisionConstant.OPERATOR.equals(roleBased) || HaivisionConstant.ADMIN.equals(roleBased) && isConfigManagement) {
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
		isEmergencyDelivery = true;
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
	 * @param stats list statistics property
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
		getFilteredForEncoderStatistics();
		for (HaivisionMonitoringMetric haivisionMonitoringMetric : HaivisionMonitoringMetric.values()) {
			populateDataByMetric(stats, advancedControllableProperties, haivisionMonitoringMetric);
		}
	}

	/**
	 * Populate data statistics and config by the metric
	 *
	 * @param stats list statistics property
	 * @param advancedControllableProperties the advancedControllableProperties is list AdvancedControllableProperties
	 * @param haivisionMonitoringMetric is instance in HaivisionMonitoringMetric
	 */
	private void populateDataByMetric(Map<String, String> stats, List<AdvancedControllableProperty> advancedControllableProperties, HaivisionMonitoringMetric haivisionMonitoringMetric) {
		switch (haivisionMonitoringMetric) {
			case AUDIO_STATISTICS:
				popularAudioStatisticsData(stats);
				break;
			case VIDEO_STATISTICS:
				popularVideoStatisticsData(stats);
				break;
			case STREAM_STATISTICS:
				popularStreamStatisticsData(stats);
				break;
			case AUDIO_CONFIG:
				popularAudioConfigData(stats,advancedControllableProperties);
				break;
			default:
				if (logger.isDebugEnabled()) {
					logger.debug("The metric not support popular data" + haivisionMonitoringMetric.getName());
				}
		}
	}

	/**
	 * Populate audio config
	 *
	 * @param stats list statistics property
	 * @param advancedControllableProperties the advancedControllableProperties is list AdvancedControllableProperties
	 */
	private void popularAudioConfigData(Map<String, String> stats, List<AdvancedControllableProperty> advancedControllableProperties) {
		Objects.requireNonNull(stats);
		Objects.requireNonNull(advancedControllableProperties);
		//TODO
	}


	/**
	 * Populate stream statistics
	 *
	 * @param stats list statistics property
	 */
	private void popularStreamStatisticsData(Map<String, String> stats) {
		for (StreamStatistics streamStatistics : streamStatisticsList) {
			String streamName = streamStatistics.getName();
			String metricName = streamName + HaivisionConstant.SPACE + HaivisionConstant.STATISTICS + HaivisionConstant.HASH;
			for (StreamMonitoringMetric streamMonitoringMetric : StreamMonitoringMetric.values()) {
				String value = checkForNullData(streamStatistics.getValueByMetric(streamMonitoringMetric));
				if (StreamMonitoringMetric.UPTIME.equals(streamMonitoringMetric)) {
					stats.put(metricName + streamMonitoringMetric.getName(), formatTimeData(value));
					continue;
				}
				stats.put(metricName + streamMonitoringMetric.getName(), replaceCommaByEmptyString(value));
			}
		}
	}

	/**
	 * Populate audio statistics
	 *
	 * @param stats list statistics property
	 */
	private void popularAudioStatisticsData(Map<String, String> stats) {
		for (AudioStatistics audioStatistics : audioStatisticsList) {
			String audioName = audioStatistics.getName();
			String metricName = audioName + HaivisionConstant.SPACE + HaivisionConstant.STATISTICS + HaivisionConstant.HASH;
			for (AudioMonitoringMetric audioMetric : AudioMonitoringMetric.values()) {
				String value = checkForNullData(audioStatistics.getValueByMetric(audioMetric));
				if (audioMetric.equals(AudioMonitoringMetric.ENCODED_BYTES) || audioMetric.equals(AudioMonitoringMetric.ENCODED_FRAMES)) {
					stats.put(metricName + audioMetric.getName(), replaceCommaByEmptyString(value));
				} else {
					stats.put(metricName + audioMetric.getName(), value);
				}
			}
		}
	}

	/**
	 * Populate video statistics
	 *
	 * @param stats list statistics property
	 */
	private void popularVideoStatisticsData(Map<String, String> stats) {
		for (VideoStatistics videoStatistics : videoStatisticsList) {
			String videoName = videoStatistics.getName();
			String metricName = videoName + HaivisionConstant.SPACE + HaivisionConstant.STATISTICS + HaivisionConstant.HASH;
			for (VideoMonitoringMetric videoMetric : VideoMonitoringMetric.values()) {
				String value = checkForNullData(videoStatistics.getValueByMetric(videoMetric));
				if (VideoMonitoringMetric.UPTIME.equals(videoMetric)) {
					stats.put(metricName + videoMetric.getName(), formatTimeData(value));
				} else {
					stats.put(metricName + videoMetric.getName(), value);
				}
			}
		}
	}

	/**
	 * Replace comma by empty string
	 *
	 * @param value the value is string
	 * @return value is replace the comma
	 */
	private String replaceCommaByEmptyString(String value) {
		if (StringUtils.isNullOrEmpty(value)) {
			return HaivisionConstant.NONE;
		}
		return value.replace(HaivisionConstant.COMMA, HaivisionConstant.EMPTY_STRING);
	}

	/**
	 * Format time data
	 *
	 * @param time the time is String
	 * @return String
	 */
	private String formatTimeData(String time) {
		if (HaivisionConstant.NONE.equals(time)) {
			return HaivisionConstant.NONE;
		}
		int index = time.indexOf(HaivisionConstant.SPACE);
		if (index > -1) {
			time = time.substring(0, index);
		}
		return time.replace("d", uuidDay).replace("s", HaivisionConstant.SECOND).replace(uuidDay, HaivisionConstant.DAY)
				.replace("h", HaivisionConstant.HOUR).replace("m", HaivisionConstant.MINUTE);
	}

	/**
	 * Filter the list of aggregated devices based on filter option in Adapter Properties
	 */
	private void getFilteredForEncoderStatistics() {
		filterAudio();
		filterVideo();
		filterStreamName();
	}

	/**
	 * Filter by audio id
	 */
	private void filterAudio() {
		List<String> audioNameList = extractListNameFilter(this.audioFilter);
		if (!StringUtils.isNullOrEmpty(audioFilter) && !audioNameList.isEmpty()) {
			List<AudioConfig> newAudioConfigList = new ArrayList<>();
			for (AudioConfig audioConfig : audioConfigList) {
				if (audioNameList.contains(audioConfig.getId())) {
					newAudioConfigList.add(audioConfig);
				}
			}
			audioConfigList = newAudioConfigList;
		}
	}

	/**
	 * Filter by video id
	 */
	private void filterVideo() {
		List<String> videoNameList = extractListNameFilter(this.videoFilter);
		if (!StringUtils.isNullOrEmpty(videoFilter) && !videoNameList.isEmpty()) {
			List<VideoConfig> newVideoConfigList = new ArrayList<>();
			for (VideoConfig videoConfig : videoConfigList) {
				if (videoNameList.contains(videoConfig.getId())) {
					newVideoConfigList.add(videoConfig);
				}
			}
			videoConfigList = newVideoConfigList;
		}
	}

	/**
	 * Filter the name of Stream
	 */
	private void filterStreamName() {
		List<String> streamNameList = extractListNameFilter(this.streamNameFilter);
		if (!streamNameList.isEmpty()) {
			//Filter stream config by name
			List<StreamConfig> streamConfigFilter = new ArrayList<>();
			for (StreamConfig streamConfigItem : streamConfigList) {
				if (streamNameList.contains(streamConfigItem.getName())) {
					streamConfigFilter.add(streamConfigItem);
					break;
				}
			}
			streamConfigList = streamConfigFilter;

			//Filter stream statics by name
			List<StreamStatistics> streamStatisticsFilter = new ArrayList<>();
			for (StreamStatistics streamStatisticsItem : streamStatisticsList) {
				if (streamNameList.contains(streamStatisticsItem.getName())) {
					streamStatisticsFilter.add(streamStatisticsItem);
					break;
				}
			}
			streamStatisticsList = streamStatisticsFilter;
		}
	}

	/**
	 * Get list name by adapter filter
	 *
	 * @param filterName the name is name of filter
	 * @return List<String> is split list of String
	 */
	private List<String> extractListNameFilter(String filterName) {
		List<String> listName = new ArrayList<>();
		if (!StringUtils.isNullOrEmpty(filterName)) {
			String[] nameStringFilter = filterName.split(HaivisionConstant.COMMA);
			for (String listNameItem : nameStringFilter) {
				listName.add(listNameItem.trim());
			}
		}
		return listName;
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

		switch (metric) {
			case SYSTEM_INFORMATION:
				retrieveSystemInfoStatus(stats);
				break;
			case AUDIO_STATISTICS:
			case VIDEO_STATISTICS:
			case AUDIO_CONFIG:
			case VIDEO_CONFIG:
			case STREAM_STATISTICS:
			case STREAM_CONFIG:
				popularRetrieveDataByMetric(metric);
				break;
			case ACCOUNT:
			case ROLE_BASED:
				break;
			default:
				throw new IllegalArgumentException("Do not support haivisionStatisticsMetric: " + metric.name());
		}
	}

	/**
	 * Retrieve video encoder configure
	 *
	 * @param metric the metric is instance HaivisionMonitoringMetric
	 */
	private void popularRetrieveDataByMetric(HaivisionMonitoringMetric metric) {
		try {
			String request = String.valueOf(HaivisionUtil.getMonitorCommand(metric));
			String responseData = send(request);
			Map<String, String> result;
			if (responseData != null) {
				responseData = responseData.substring(request.length() + 2, responseData.lastIndexOf(HaivisionConstant.REGEX_DATA));
				String[] responseDataList = responseData.split(HaivisionConstant.REGEX_SPLIT_DATA);
				for (String responseDataItem : responseDataList) {

					if (HaivisionMonitoringMetric.STREAM_CONFIG.equals(metric) || HaivisionMonitoringMetric.STREAM_STATISTICS.equals(metric)) {
						result = popularConvertDataToObject(responseDataItem.replace("\r\n\t\t\t", "").replace("\t", ""), request, false);
					} else {
						result = popularConvertDataToObject(responseDataItem.replace("\t", ""), request, false);
					}
					if (!result.isEmpty() && result.get("Name") != null) {
						retrieveDataDetails(result, metric);
					}
				}
			}
		} catch (Exception e) {
			failedMonitor.put(metric.getName(), e.getMessage());
		}
	}

	/**
	 * Retrieve data by metric
	 *
	 * @param mappingData is Map<String,String> instance
	 * @param metric the metric is instance HaivisionMonitoringMetric
	 */
	private void retrieveDataDetails(Map<String, String> mappingData, HaivisionMonitoringMetric metric) {
		switch (metric) {
			case AUDIO_STATISTICS:
				AudioStatistics audioStatistics = objectMapper.convertValue(mappingData, AudioStatistics.class);
				audioStatisticsList.add(audioStatistics);
				break;
			case AUDIO_CONFIG:
				AudioConfig audioConfig = objectMapper.convertValue(mappingData, AudioConfig.class);
				audioConfigList.add(audioConfig);
				break;
			case VIDEO_STATISTICS:
				VideoStatistics videoStatistics = objectMapper.convertValue(mappingData, VideoStatistics.class);
				videoStatisticsList.add(videoStatistics);
				break;
			case VIDEO_CONFIG:
				VideoConfig videoConfigResponse = objectMapper.convertValue(mappingData, VideoConfig.class);
				videoConfigList.add(videoConfigResponse);
				break;
			case STREAM_STATISTICS:
				StreamStatistics streamStatistics = objectMapper.convertValue(mappingData, StreamStatistics.class);
				streamStatisticsList.add(streamStatistics);
				break;
			case STREAM_CONFIG:
				StreamConfig streamConfig = objectMapper.convertValue(mappingData, StreamConfig.class);
				streamConfigList.add(streamConfig);
				break;
			default:
				throw new IllegalArgumentException("Do not support haivisionStatisticsMetric: " + metric.name());
		}
	}

	/**
	 * Retrieve system information status encoder
	 *
	 * @param stats list statistics property
	 */
	private void retrieveSystemInfoStatus(Map<String, String> stats) {
		try {
			String request = String.valueOf(HaivisionUtil.getMonitorCommand(HaivisionMonitoringMetric.SYSTEM_INFORMATION));
			String responseData = send(request);
			if (responseData != null) {
				SystemInfoResponse systemInfoResponse = objectMapper.convertValue(popularConvertDataToObject(responseData, request, true), SystemInfoResponse.class);
				for (SystemMonitoringMetric systemInfoMetric : SystemMonitoringMetric.values()) {
					stats.put(systemInfoMetric.getName(), checkForNullData(systemInfoResponse.getValueByMetric(systemInfoMetric)));
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
			stats.put(systemInfoMetric.getName(), HaivisionConstant.NONE);
		}
	}

	/**
	 * This method is used to retrieve User Role by send command "account {accountName} get"
	 *
	 * @throws ResourceNotReachableException When there is no valid User Role data or having an Exception
	 */
	private String retrieveUserRole() {
		try {
			String request = HaivisionCommand.ACCOUNT.getName() + getLogin() + HaivisionConstant.SPACE + HaivisionCommand.GET.getName();
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
				responseData = responseData.substring(request.length() + 2, responseData.lastIndexOf(HaivisionConstant.REGEX_DATA)).replace("\t", "");
			}
			return Arrays.stream(responseData.split(HaivisionConstant.REGEX_DATA))
					.map(item -> item.split(HaivisionConstant.COLON, 2))
					.collect(Collectors.toMap(
							key -> key[0].trim().replace("\"", ""),
							value -> value.length == 2 ? value[1].trim().replace("\"", "") : ""
					));
		} catch (Exception e) {
			return Collections.emptyMap();
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
