/*
 * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
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
import com.avispl.symphony.api.dal.error.CommandFailureException;
import com.avispl.symphony.api.dal.error.ResourceNotReachableException;
import com.avispl.symphony.api.dal.monitor.Monitorable;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.common.AudioControllingMetric;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.common.AudioMonitoringMetric;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.common.EncoderCommand;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.common.EncoderConstant;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.common.EncoderMonitoringMetric;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.common.EncoderUtil;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.common.StreamMonitoringMetric;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.common.SystemMonitoringMetric;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.common.VideoMonitoringMetric;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dropdownList.AlgorithmDropdown;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dropdownList.AudioActionDropdown;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dropdownList.AudioLevel;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dropdownList.AudioStateDropdown;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dropdownList.BitRateDropdown;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dropdownList.ChannelModeDropdown;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dropdownList.DropdownList;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dropdownList.InputDropdown;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dropdownList.LanguageDropdown;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dropdownList.SampleRateDropdown;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dto.AuthenticationRole;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dto.SystemInfoResponse;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dto.TemperatureStatus;
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
	private ExtendedStatistics localExtendedStatistics;
	private Integer noOfMonitoringMetric = 0;
	private Map<String, String> failedMonitor = new HashMap<>();
	ObjectMapper objectMapper = new ObjectMapper();
	private boolean isEmergencyDelivery;

	private final String uuidDay = UUID.randomUUID().toString().replace(EncoderConstant.DASH, EncoderConstant.EMPTY_STRING);

	Map<String, AudioStatistics> nameToAudioStatistics = new HashMap<>();
	Map<String, AudioConfig> nameToAudioConfig = new HashMap<>();
	Map<String, VideoConfig> nameToVideoConfig = new HashMap<>();

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
	 * Retrieves {@code {@link #configManagement}}
	 *
	 * @return value of {@link #configManagement}
	 */
	public String getConfigManagement() {
		return configManagement;
	}

	/**
	 * Sets {@code configManagement}
	 *
	 * @param configManagement the {@code java.lang.String} field
	 */
	public void setConfigManagement(String configManagement) {
		this.configManagement = configManagement;
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
	 * HaivisionXEncoderCommunicator constructor
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
			logger.debug(String.format("Getting statistics from Makito X Encoder at host %s with port %s", this.host, this.getPort()));
		}
		ExtendedStatistics extendedStatistics = new ExtendedStatistics();
		Map<String, String> stats = new HashMap<>();
		List<AdvancedControllableProperty> advancedControllableProperties = new ArrayList<>();

		if (!isEmergencyDelivery) {
			roleBased = retrieveUserRole();
			isConfigManagement = isConfigManagementProperties();
			localExtendedStatistics = new ExtendedStatistics();
			populateInformationFromDevice(stats, advancedControllableProperties);
			if ((EncoderConstant.OPERATOR.equals(roleBased) || EncoderConstant.ADMIN.equals(roleBased)) && isConfigManagement) {
				extendedStatistics.setControllableProperties(advancedControllableProperties);
			}
			extendedStatistics.setStatistics(stats);
			localExtendedStatistics = extendedStatistics;
		}

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
		if (localExtendedStatistics == null) {
			return;
		}
		Map<String, String> extendedStatistics = localExtendedStatistics.getStatistics();
		List<AdvancedControllableProperty> advancedControllableProperties = localExtendedStatistics.getControllableProperties();
		String propertiesAudioAndVideo = property.substring(0, EncoderConstant.AUDIO.length());
		if (EncoderConstant.AUDIO.equals(propertiesAudioAndVideo)) {
			controlAudioProperty(property, value, extendedStatistics, advancedControllableProperties);
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
	 * @param stats list statistics property
	 * @param advancedControllableProperties the advancedControllableProperties is list AdvancedControllableProperties
	 */
	private void populateInformationFromDevice(Map<String, String> stats, List<AdvancedControllableProperty> advancedControllableProperties) {
		for (EncoderMonitoringMetric encoderMonitoringMetric : EncoderMonitoringMetric.values()) {
			if (EncoderMonitoringMetric.ACCOUNT.equals(encoderMonitoringMetric)) {
				continue;
			}
			retrieveDataByMetric(stats, encoderMonitoringMetric);
		}
		if (noOfMonitoringMetric == 0) {
			noOfMonitoringMetric = getNumberMonitoringMetric();
		}
		if (failedMonitor.size() == noOfMonitoringMetric) {
			StringBuilder stringBuilder = new StringBuilder();
			for (Map.Entry<String, String> messageFailed : failedMonitor.entrySet()) {
				stringBuilder.append(messageFailed.getValue());
			}
			failedMonitor.clear();
			throw new ResourceNotReachableException("Get monitoring data failed: " + stringBuilder);
		}
		getFilteredForEncoderStatistics();
		for (EncoderMonitoringMetric encoderMonitoringMetric : EncoderMonitoringMetric.values()) {
			populateDataByMetric(stats, advancedControllableProperties, encoderMonitoringMetric);
		}
	}

	/**
	 * Populate data statistics and config by the metric
	 *
	 * @param stats list statistics property
	 * @param advancedControllableProperties the advancedControllableProperties is list AdvancedControllableProperties
	 * @param encoderMonitoringMetric is instance in EncoderMonitoringMetric
	 */
	private void populateDataByMetric(Map<String, String> stats, List<AdvancedControllableProperty> advancedControllableProperties, EncoderMonitoringMetric encoderMonitoringMetric) {
		switch (encoderMonitoringMetric) {
			case AUDIO_STATISTICS:
				populateAudioStatisticsData(stats);
				break;
			case VIDEO_STATISTICS:
				populateVideoStatisticsData(stats);
				break;
			case STREAM_STATISTICS:
				populateStreamStatisticsData(stats);
				break;
			case AUDIO_CONFIG:
				populateAudioConfigData(stats, advancedControllableProperties);
				break;
			case TEMPERATURE:
			case ACCOUNT:
			case SYSTEM_INFORMATION:
			case ROLE_BASED:
				break;
			default:
				if (logger.isDebugEnabled()) {
					logger.debug("The metric not support populate data" + encoderMonitoringMetric.getName());
				}
		}
	}

	/**
	 * Populate audio config
	 *
	 * @param stats list statistics property
	 * @param advancedControllableProperties the advancedControllableProperties is list AdvancedControllableProperties
	 */
	private void populateAudioConfigData(Map<String, String> stats, List<AdvancedControllableProperty> advancedControllableProperties) {
		Objects.requireNonNull(stats);
		Objects.requireNonNull(advancedControllableProperties);
		if ((EncoderConstant.ADMIN.equals(roleBased) || EncoderConstant.OPERATOR.equals(roleBased)) && isConfigManagement) {
			for (AudioConfig audioConfig : audioConfigList) {
				addControlAudioConfig(stats, advancedControllableProperties, audioConfig);
			}
		}
	}

	/**
	 * Populate stream statistics
	 *
	 * @param stats list statistics property
	 */
	private void populateStreamStatisticsData(Map<String, String> stats) {
		for (StreamStatistics streamStatistics : streamStatisticsList) {
			String streamName = streamStatistics.getName();
			if (EncoderConstant.NONE_STREAM_NAME.equals(streamName)) {
				streamName = handleStreamNameIsEmpty(streamStatistics.getId());
			}
			String metricName = EncoderConstant.STREAM + EncoderConstant.SPACE + streamName + EncoderConstant.SPACE + EncoderConstant.STATISTICS + EncoderConstant.HASH;
			for (StreamMonitoringMetric streamMonitoringMetric : StreamMonitoringMetric.values()) {
				String streamValue = getDefaultValueForNullData(streamStatistics.getValueByMetric(streamMonitoringMetric));
				String streamKeyName = metricName + streamMonitoringMetric.getName();

				//Normalize for the stream metric
				switch (streamMonitoringMetric) {
					case UPTIME:
						stats.put(streamKeyName, formatTimeData(streamValue));
						break;
					case OCCURRED:
						int len = streamValue.lastIndexOf(EncoderConstant.SPACE);
						if (len > 1) {
							streamValue = streamValue.substring(0, len);
						}
						stats.put(streamKeyName, formatTimeData(streamValue));
						break;
					case BITRATE:
					case LATENCY:
					case MAX_BANDWIDTH:
					case PATH_MAX_BANDWIDTH:
						streamValue = convertValueByIndexOfIsSpace(streamValue);
						if (EncoderConstant.UNKNOWN.equals(streamValue)) {
							streamValue = String.valueOf(EncoderConstant.NUMBER_ONE);
						}
						stats.put(streamKeyName, streamValue);
						break;
					case RTT:
						streamValue = convertValueByIndexOfIsSpace(streamValue);
						boolean isValidValueOrLessThanOne = EncoderConstant.NONE.equals(streamName) && Integer.parseInt(streamValue) < EncoderConstant.NUMBER_ONE;
						if (isValidValueOrLessThanOne) {
							streamValue = EncoderConstant.LESS_THAN + EncoderConstant.SPACE + EncoderConstant.NUMBER_ONE;
						}
						stats.put(streamKeyName, streamValue);
						break;
					case LOCAL_BUFFER_LEVEL:
						stats.put(streamKeyName, convertValueByIndexOfIsSpace(streamValue));
						break;
					case RESENT_PACKET:
					case RESENT_BYTES:
					case DROPPED_BYTES:
					case DROPPED_PACKETS:
					case RECEIVED_ACKS:
					case RECEIVED_NAKS:
					case SENT_BYTES:
					case SENT_PACKETS:
					case UNSENT_BYTES:
					case UNSENT_PACKETS:
						stats.put(streamKeyName, replaceCommaByEmptyString(streamValue));
						break;
					default:
						stats.put(streamKeyName, streamValue);
						break;
				}
			}
		}
	}

	/**
	 * Populate audio statistics
	 *
	 * @param stats list statistics property
	 */
	private void populateAudioStatisticsData(Map<String, String> stats) {
		for (AudioStatistics audioStatistics : audioStatisticsList) {
			String audioName = audioStatistics.getName();
			String metricName = audioName + EncoderConstant.SPACE + EncoderConstant.STATISTICS + EncoderConstant.HASH;
			for (AudioMonitoringMetric audioMetric : AudioMonitoringMetric.values()) {
				String audioValue = getDefaultValueForNullData(audioStatistics.getValueByMetric(audioMetric));
				String audioKeyName = metricName + audioMetric.getName();
				if (audioMetric.equals(AudioMonitoringMetric.ENCODED_BITRATE)) {
					stats.put(audioKeyName, convertValueByIndexOfIsSpace(audioValue));
					continue;
				}
				if (audioMetric.equals(AudioMonitoringMetric.ENCODED_BYTES) || audioMetric.equals(AudioMonitoringMetric.ENCODED_FRAMES)) {
					stats.put(audioKeyName, replaceCommaByEmptyString(audioValue));
				} else {
					stats.put(audioKeyName, audioValue);
				}
			}
		}
	}

	/**
	 * Populate video statistics
	 *
	 * @param stats list statistics property
	 */
	private void populateVideoStatisticsData(Map<String, String> stats) {
		for (VideoStatistics videoStatistics : videoStatisticsList) {
			String videoName = videoStatistics.getName();
			String metricName = videoName + EncoderConstant.SPACE + EncoderConstant.STATISTICS + EncoderConstant.HASH;
			for (VideoMonitoringMetric videoMetric : VideoMonitoringMetric.values()) {
				String videoValue = getDefaultValueForNullData(videoStatistics.getValueByMetric(videoMetric));
				String videoKeyName = metricName + videoMetric.getName();

				//Normalize for the video metric
				switch (videoMetric) {
					case INPUT_FORMAT:
						boolean isValidValue = videoValue.equals(EncoderConstant.NONE) || videoValue.equals(EncoderConstant.UNKNOWN);
						if (isValidValue) {
							videoValue = EncoderConstant.NO_INPUT;
						}
						stats.put(videoKeyName, videoValue);
						break;
					case INPUT_TYPE:
						if (videoValue.equals(EncoderConstant.NONE)) {
							videoValue = EncoderConstant.INVALID;
						}
						stats.put(videoKeyName, videoValue);
						break;
					case ENCODER_LOAD:
					case ENCODER_BITRATE:
						stats.put(videoKeyName, convertValueByIndexOfIsSpace(videoValue).replace(EncoderConstant.PERCENT, EncoderConstant.EMPTY_STRING));
						break;
					case UPTIME:
						stats.put(videoKeyName, formatTimeData(videoValue));
						break;
					case RESOLUTION:
						if (EncoderConstant.NONE.equals(videoValue)) {
							videoValue = nameToVideoConfig.get(videoName).getResolution();
						}
						stats.put(videoKeyName, videoValue);
						break;
					default:
						stats.put(videoKeyName, videoValue);
						break;
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
			return EncoderConstant.NONE;
		}
		return value.replace(EncoderConstant.COMMA, EncoderConstant.EMPTY_STRING);
	}

	/**
	 * Format time data such as x day(s) x hour(s) x minute(s) x minute(s)
	 *
	 * @param time the time is String
	 * @return String
	 */
	private String formatTimeData(String time) {
		if (EncoderConstant.NONE.equals(time)) {
			return EncoderConstant.NONE;
		}
		int index = time.indexOf(EncoderConstant.SPACE);
		if (index > -1) {
			time = time.substring(0, index);
		}
		return time.replace("d", uuidDay).replace("s", EncoderConstant.SECOND).replace(uuidDay, EncoderConstant.DAY)
				.replace("h", EncoderConstant.HOUR).replace("m", EncoderConstant.MINUTE);
	}

	/**
	 * Filter option in Adapter Properties
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
			String[] nameStringFilter = filterName.split(EncoderConstant.COMMA);
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
		for (EncoderMonitoringMetric metric : EncoderMonitoringMetric.values()) {
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
	 */
	private void retrieveDataByMetric(Map<String, String> stats, EncoderMonitoringMetric metric) {
		Objects.requireNonNull(metric);

		switch (metric) {
			case SYSTEM_INFORMATION:
				retrieveSystemInfoStatus(stats);
				break;
			case TEMPERATURE:
				retrieveTemperatureStatus(stats);
				break;
			case AUDIO_STATISTICS:
			case VIDEO_STATISTICS:
			case AUDIO_CONFIG:
			case VIDEO_CONFIG:
			case STREAM_STATISTICS:
			case STREAM_CONFIG:
				populateRetrieveDataByMetric(metric);
				break;
			case ACCOUNT:
			case ROLE_BASED:
				break;
			default:
				throw new IllegalArgumentException("Do not support encoderStatisticsMetric: " + metric.name());
		}
	}

	/**
	 * Retrieve temperature status encoder
	 *
	 * @param stats list statistics property
	 */
	private void retrieveTemperatureStatus(Map<String, String> stats) {
		try {
			String request = String.valueOf(EncoderUtil.getMonitorCommand(EncoderMonitoringMetric.TEMPERATURE));
			String responseData = send(request);
			if (responseData != null) {
				TemperatureStatus systemInfoResponse = objectMapper.convertValue(populateConvertDataToObject(responseData, request, true), TemperatureStatus.class);
				String temperatureStatus = getDefaultValueForNullData(systemInfoResponse.getTemperature());
				int index = temperatureStatus.indexOf(EncoderConstant.SPACE);
				if (index != -1) {
					temperatureStatus = temperatureStatus.substring(0, index);
				}
				stats.put(EncoderMonitoringMetric.TEMPERATURE.getName(), temperatureStatus);
			} else {
				stats.put(EncoderMonitoringMetric.TEMPERATURE.getName(), EncoderConstant.NONE);
			}
		} catch (Exception e) {
			stats.put(EncoderMonitoringMetric.TEMPERATURE.getName(), EncoderConstant.NONE);
			failedMonitor.put(EncoderMonitoringMetric.TEMPERATURE.getName(), e.getMessage());
		}
	}

	/**
	 * Retrieve video encoder configure
	 *
	 * @param metric the metric is instance encoderMonitoringMetric
	 */
	private void populateRetrieveDataByMetric(EncoderMonitoringMetric metric) {
		try {
			String request = String.valueOf(EncoderUtil.getMonitorCommand(metric));
			String responseData = send(request);
			Map<String, String> result;
			if (responseData != null) {
				responseData = responseData.substring(request.length() + EncoderConstant.NUMBER_TWO, responseData.lastIndexOf(EncoderConstant.REGEX_DATA));
				String[] responseDataList = responseData.split(EncoderConstant.REGEX_SPLIT_DATA);
				for (String responseDataItem : responseDataList) {

					if (EncoderMonitoringMetric.STREAM_CONFIG.equals(metric) || EncoderMonitoringMetric.STREAM_STATISTICS.equals(metric)) {
						result = populateConvertDataToObject(responseDataItem.replace("\r\n\t\t\t", EncoderConstant.EMPTY_STRING).replace("\t", EncoderConstant.EMPTY_STRING), request, false);
					} else {
						result = populateConvertDataToObject(responseDataItem.replace("\t", EncoderConstant.EMPTY_STRING), request, false);
					}
					if (!result.isEmpty() && result.get(EncoderConstant.NAME) != null) {
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
	 * @param metric the metric is instance encoderMonitoringMetric
	 */
	private void retrieveDataDetails(Map<String, String> mappingData, EncoderMonitoringMetric metric) {
		switch (metric) {
			case AUDIO_STATISTICS:
				AudioStatistics audioStatistics = objectMapper.convertValue(mappingData, AudioStatistics.class);
				nameToAudioStatistics.put(audioStatistics.getName(), audioStatistics);
				audioStatisticsList.add(audioStatistics);
				break;
			case AUDIO_CONFIG:
				AudioConfig audioConfig = objectMapper.convertValue(mappingData, AudioConfig.class);
				nameToAudioConfig.put(audioConfig.getName(), audioConfig);
				audioConfigList.add(audioConfig);
				break;
			case VIDEO_STATISTICS:
				VideoStatistics videoStatistics = objectMapper.convertValue(mappingData, VideoStatistics.class);
				videoStatisticsList.add(videoStatistics);
				break;
			case VIDEO_CONFIG:
				VideoConfig videoConfigResponse = objectMapper.convertValue(mappingData, VideoConfig.class);
				nameToVideoConfig.put(videoConfigResponse.getName(), videoConfigResponse);
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
				throw new IllegalArgumentException("Do not support encoderStatisticsMetric: " + metric.name());
		}
	}

	/**
	 * Retrieve system information status encoder
	 *
	 * @param stats list statistics property
	 */
	private void retrieveSystemInfoStatus(Map<String, String> stats) {
		try {
			String request = String.valueOf(EncoderUtil.getMonitorCommand(EncoderMonitoringMetric.SYSTEM_INFORMATION));
			String responseData = send(request);
			if (responseData != null) {
				SystemInfoResponse systemInfoResponse = objectMapper.convertValue(populateConvertDataToObject(responseData, request, true), SystemInfoResponse.class);
				for (SystemMonitoringMetric systemInfoMetric : SystemMonitoringMetric.values()) {
					stats.put(systemInfoMetric.getName(), getDefaultValueForNullData(systemInfoResponse.getValueByMetric(systemInfoMetric)));
				}
			} else {
				contributeNoneValueForSystemInfo(stats);
			}
		} catch (Exception e) {
			contributeNoneValueForSystemInfo(stats);
			failedMonitor.put(EncoderConstant.SYSTEM_INFO_STATUS, e.getMessage());
		}
	}

	/**
	 * Get default None if the value is Null or Empty
	 *
	 * @param value the value of monitoring properties
	 * @return String (none/value)
	 */
	private String getDefaultValueForNullData(String value) {
		if (StringUtils.isNullOrEmpty(value)) {
			return EncoderConstant.NONE;
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
			stats.put(systemInfoMetric.getName(), EncoderConstant.NONE);
		}
	}

	/**
	 * This method is used to retrieve User Role by send command "account {accountName} get"
	 *
	 * @throws ResourceNotReachableException When there is no valid User Role data or having an Exception
	 */
	private String retrieveUserRole() {
		try {
			String request = EncoderCommand.ADMIN_ACCOUNT.getName() + getLogin() + EncoderConstant.SPACE + EncoderCommand.GET.getName();
			String response = send(request);
			AuthenticationRole authenticationRole = null;
			if (response != null) {
				Map<String, String> result = populateConvertDataToObject(response, request, true);
				authenticationRole = objectMapper.convertValue(result, AuthenticationRole.class);
			}
			if (authenticationRole == null || StringUtils.isNullOrEmpty(authenticationRole.getRole())) {
				throw new ResourceNotReachableException("Role based is empty");
			}
			return authenticationRole.getRole();
		} catch (Exception e) {
			throw new ResourceNotReachableException("Retrieve role based error: " + e.getMessage(), e);
		}
	}

	/**
	 * Convert String data to Map<String,String>
	 *
	 * @param responseData the responseData is value retrieve to command
	 * @param request the request is command to retrieve the data
	 * @param option the option is boolean if option == true will remove command String in responseData
	 * @return Map<String, String> instance
	 */
	private Map<String, String> populateConvertDataToObject(String responseData, String request, boolean option) {
		try {
			if (option) {
				responseData = responseData.substring(request.length() + EncoderConstant.NUMBER_TWO, responseData.lastIndexOf(EncoderConstant.REGEX_DATA)).replace("\t", EncoderConstant.EMPTY_STRING);
			}
			return Arrays.stream(responseData.split(EncoderConstant.REGEX_DATA))
					.map(item -> item.split(EncoderConstant.COLON, EncoderConstant.NUMBER_TWO))
					.collect(Collectors.toMap(
							key -> replaceDoubleQuotes(key[0]),
							//handle case attribute is empty such as Statistics: or Configuration:
							value -> value.length == EncoderConstant.NUMBER_TWO ? replaceDoubleQuotes(value[1]) : EncoderConstant.EMPTY_STRING
					));
		} catch (Exception e) {
			logger.error("Error while convert data: ", e);
			return Collections.emptyMap();
		}
	}

	/**
	 * Parsing stream name empty to default name ( {protocol}://@{address}:{(port)} )
	 *
	 * @param streamId the streamId is ID of stream
	 * @return String is name of stream output
	 */
	private String handleStreamNameIsEmpty(String streamId) {
		String streamName = EncoderConstant.EMPTY_STRING;
		for (StreamConfig streamConfigItem : streamConfigList) {
			if (streamConfigItem.getId().equals(streamId)) {
				String protocol = streamConfigItem.getEncapsulation();
				String address = streamConfigItem.getAddress();
				String port = streamConfigItem.getPort();
				streamName = protocol + "://@" + address + "(" + port + ")";
				break;
			}
		}
		return streamName;
	}

	/**
	 * Format value by double quotes
	 *
	 * @param value the value is String
	 * @return value the value has been replaced with double quotes if exit
	 */
	private String replaceDoubleQuotes(String value) {
		value = value.trim();
		if (!StringUtils.isNullOrEmpty(value)) {
			int len = value.length() - 1;
			String firstQuotes = value.substring(0, 1);
			String lastQuotes = value.substring(len);
			if (EncoderConstant.QUOTES.equals(firstQuotes) && EncoderConstant.QUOTES.equals(lastQuotes)) {
				value = value.substring(1, len);
			}
		}
		return value;
	}

	/**
	 * Convert String by index of is space
	 *
	 * @param value the value is string value
	 * @return value extracted
	 */
	private String convertValueByIndexOfIsSpace(String value) {
		try {
			return value.substring(0, value.indexOf(EncoderConstant.SPACE));
		} catch (Exception e) {
			return value;
		}
	}

	/**
	 * This method is used to handle input from adapter properties in case is config management
	 *
	 * @return boolean is configManagement
	 */
	private boolean isConfigManagementProperties() {
		return !StringUtils.isNullOrEmpty(configManagement) && EncoderConstant.TRUE.equalsIgnoreCase(configManagement);
	}

	//region perform controls
	//--------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Add control monitoring data for audio config
	 *
	 * @param stats list statistics property
	 * @param advancedControllableProperties the advancedControllableProperties is list AdvancedControllableProperties
	 * @param audioConfig is instance in AudioConfig DTO
	 */
	private void addControlAudioConfig(Map<String, String> stats, List<AdvancedControllableProperty> advancedControllableProperties, AudioConfig audioConfig) {

		String[] dropdownInput = DropdownList.Names(InputDropdown.class);
		String[] dropdownMode = DropdownList.Names(ChannelModeDropdown.class);
		String[] dropdownAlgorithm = DropdownList.Names(AlgorithmDropdown.class);
		String[] dropdownSampleRate = DropdownList.Names(SampleRateDropdown.class);
		String[] dropdownLanguage = DropdownList.Names(LanguageDropdown.class);
		String[] dropdownLevel = DropdownList.Names(AudioLevel.class);
		String[] dropdownAction = AudioActionDropdown.namesIsStartAction();
		String[] dropdownBitRate = BitRateDropdown.namesIsMono();
		Map<String, String> languageDropdown = LanguageDropdown.getNameToValueMap();
		Map<String, String> stateDropdown = AudioStateDropdown.getNameToValueMap();
		Map<String, String> inputMap = InputDropdown.getNameToValueMap();
		Map<String, String> algorithmName = AlgorithmDropdown.getNameToValueMap();
		String audioName = audioConfig.getName();
		String value;
		for (AudioControllingMetric audioMetric : AudioControllingMetric.values()) {
			String audioKeyName = audioName + EncoderConstant.HASH + audioMetric.getName();
			switch (audioMetric) {
				case STATE:
					String stateAudio = nameToAudioStatistics.get(audioConfig.getName()).getState();
					stats.put(audioKeyName, stateAudio);
					break;
				case INPUT:
					value = inputMap.get(audioConfig.getInterfaceName());
					AdvancedControllableProperty inputDropdownControlProperty = controlDropdown(stats, dropdownInput, audioKeyName, value);
					addAdvanceControlProperties(advancedControllableProperties, inputDropdownControlProperty);
					break;
				case CHANGE_MODE:
					value = audioConfig.getMode();
					AdvancedControllableProperty channelModeControlProperty = controlDropdown(stats, dropdownMode, audioKeyName, value);
					addAdvanceControlProperties(advancedControllableProperties, channelModeControlProperty);
					break;
				case BITRATE:
					value = audioConfig.getBitRate();
					String mode = audioConfig.getMode();
					if (mode.equals(ChannelModeDropdown.STEREO.getName())) {
						dropdownBitRate = BitRateDropdown.namesIsStereo();
					}
					AdvancedControllableProperty bitRateControlProperty = controlDropdown(stats, dropdownBitRate, audioKeyName, value);
					addAdvanceControlProperties(advancedControllableProperties, bitRateControlProperty);
					break;
				case SAMPLE_RATE:
					value = audioConfig.getSampleRate();
					AdvancedControllableProperty samPleRateControlProperty = controlDropdown(stats, dropdownSampleRate, audioKeyName, value);
					addAdvanceControlProperties(advancedControllableProperties, samPleRateControlProperty);
					break;
				case ALGORITHM:
					value = algorithmName.get(audioConfig.getAlgorithm());
					AdvancedControllableProperty algorithmControlProperty = controlDropdown(stats, dropdownAlgorithm, audioKeyName, value);
					addAdvanceControlProperties(advancedControllableProperties, algorithmControlProperty);
					break;
				case LANGUAGE:
					String language = audioConfig.getLang();
					value = languageDropdown.get(language);
					if (StringUtils.isNullOrEmpty(value)) {
						value = EncoderConstant.NONE;
					}
					AdvancedControllableProperty languageControlProperty = controlDropdownAcceptNoneValue(stats, dropdownLanguage, audioKeyName, value);
					addAdvanceControlProperties(advancedControllableProperties, languageControlProperty);
					break;
				case ACTION:
					stateAudio = nameToAudioStatistics.get(audioConfig.getName()).getState();
					//define action = Start
					if (AudioStateDropdown.STOPPED.getValue().equalsIgnoreCase(stateAudio)) {
						dropdownAction = AudioActionDropdown.namesIsStopAction();
					}
					value = stateDropdown.get(stateAudio);
					AdvancedControllableProperty actionDropdownControlProperty = controlDropdownAcceptNoneValue(stats, dropdownAction, audioKeyName, value);
					addAdvanceControlProperties(advancedControllableProperties, actionDropdownControlProperty);
					break;
				case LEVEL:
					if (InputDropdown.ANALOG.getName().equalsIgnoreCase(audioConfig.getInterfaceName())) {
						value = audioConfig.getLevel();
						int len = value.indexOf(EncoderConstant.SPACE);
						//subString the value by space IfAbsent
						if (len > -1) {
							value = value.substring(0, len);
						}
						AdvancedControllableProperty levelDropdownControlProperty = controlDropdownAcceptNoneValue(stats, dropdownLevel, audioKeyName, value);
						addAdvanceControlProperties(advancedControllableProperties, levelDropdownControlProperty);
					}
					break;
				case CANCEL:
				case APPLY_CHANGE:
					break;
				default:
					if (logger.isDebugEnabled()) {
						logger.debug(String.format("Controlling audio group config %s is not supported.", audioMetric.getName()));
					}
			}
		}
		stats.put(audioName + EncoderConstant.HASH + EncoderConstant.EDITED, EncoderConstant.FALSE);
	}

	/**
	 * Control Audio encoder
	 *
	 * @param property the property is the filed name of controlling metric
	 * @param value the value is value of metric
	 * @param extendedStatistics list extendedStatistics
	 * @param advancedControllableProperties the advancedControllableProperties is advancedControllableProperties instance
	 */
	private void controlAudioProperty(String property, String value, Map<String, String> extendedStatistics, List<AdvancedControllableProperty> advancedControllableProperties) {
		String[] audioProperty = property.split(EncoderConstant.HASH);
		String audioName = audioProperty[0];
		String propertyName = audioProperty[1];
		AudioControllingMetric audioControllingMetric = AudioControllingMetric.getByName(propertyName);
		String levelName = audioName + EncoderConstant.HASH + AudioControllingMetric.LEVEL.getName();
		String bitRateName = audioName + EncoderConstant.HASH + AudioControllingMetric.BITRATE.getName();
		isEmergencyDelivery = true;
		switch (audioControllingMetric) {
			case LEVEL:
				nameToAudioConfig.get(audioName).setLevel(value);
				updateValueForTheControllableProperty(property, value, extendedStatistics, advancedControllableProperties);
				break;
			case ACTION:
			case BITRATE:
			case LANGUAGE:
			case ALGORITHM:
			case SAMPLE_RATE:
				updateValueForTheControllableProperty(property, value, extendedStatistics, advancedControllableProperties);
				break;
			case INPUT:
				String[] inputDropdown = DropdownList.Names(InputDropdown.class);
				String[] levelDropdown = DropdownList.Names(AudioLevel.class);
				AdvancedControllableProperty inputDropdownControlProperty = controlDropdown(extendedStatistics, inputDropdown, property, value);
				addAdvanceControlProperties(advancedControllableProperties, inputDropdownControlProperty);
				String level = nameToAudioConfig.get(audioName).getLevel();
				if (StringUtils.isNullOrEmpty(level)) {
					level = EncoderConstant.DEFAULT_AUDIO_LEVEL;
				}
				if (InputDropdown.ANALOG.getName().equalsIgnoreCase(value)) {
					AdvancedControllableProperty levelProperty = controlDropdown(extendedStatistics, levelDropdown, levelName, level);
					addAdvanceControlProperties(advancedControllableProperties, levelProperty);
				} else {
					extendedStatistics.remove(levelName);
					advancedControllableProperties.removeIf(item -> item.getName().equals(levelName));
				}
				break;
			case CHANGE_MODE:
				String[] dropdownMode = DropdownList.Names(ChannelModeDropdown.class);
				AdvancedControllableProperty channelModeControlProperty = controlDropdown(extendedStatistics, dropdownMode, property, value);
				addAdvanceControlProperties(advancedControllableProperties, channelModeControlProperty);

				//Update bitRate with channel mode
				String bitRate = extendedStatistics.get(bitRateName);
				String defaultBitrate = BitRateDropdown.getDefaultBitRate(bitRate, value);

				//default channel mode is IsStereo
				String[] dropdownBitRate = BitRateDropdown.namesIsStereo();
				if (!ChannelModeDropdown.STEREO.getName().equals(value)) {
					dropdownBitRate = BitRateDropdown.namesIsMono();
				}
				AdvancedControllableProperty bitRateControlProperty = controlDropdown(extendedStatistics, dropdownBitRate, bitRateName, defaultBitrate);
				addAdvanceControlProperties(advancedControllableProperties, bitRateControlProperty);
				break;
			case APPLY_CHANGE:
				AudioConfig audioConfig = convertAudioByValue(extendedStatistics, audioName);

				// sent request to apply all change for all metric
				setAudioApplyChange(audioConfig.retrieveAudioPayloadData(), audioConfig.getId());

				//sent request to action for the metric
				setActionAudioControl(audioConfig);
				isEmergencyDelivery = false;
				break;
			case CANCEL:
				isEmergencyDelivery = false;
				break;
			default:
				if (logger.isDebugEnabled()) {
					logger.debug(String.format("Controlling audio group config %s is not supported.", audioControllingMetric.getName()));
				}
		}
		//Editing
		if (isEmergencyDelivery) {
			propertyName = audioName;
			extendedStatistics.put(propertyName + EncoderConstant.HASH + AudioControllingMetric.APPLY_CHANGE.getName(), EncoderConstant.EMPTY_STRING);
			advancedControllableProperties.add(createButton(propertyName + EncoderConstant.HASH + AudioControllingMetric.APPLY_CHANGE.getName(), EncoderConstant.APPLY, EncoderConstant.APPLYING, 0));

			extendedStatistics.put(propertyName + EncoderConstant.HASH + EncoderConstant.EDITED, EncoderConstant.TRUE);
			extendedStatistics.put(propertyName + EncoderConstant.HASH + EncoderConstant.CANCEL, EncoderConstant.EMPTY_STRING);
			advancedControllableProperties.add(createButton(propertyName + EncoderConstant.HASH + EncoderConstant.CANCEL, EncoderConstant.CANCEL, EncoderConstant.CANCEL, 0));
		}
	}

	/**
	 * Sent request to action audio
	 *
	 * @param audioConfig is instance AudioConfig DTO
	 */
	private void setActionAudioControl(AudioConfig audioConfig) {
		String audioId = audioConfig.getId();
		String action = audioConfig.getAction();
		String request = EncoderCommand.OPERATION_AUDENC.getName() + audioId + EncoderConstant.SPACE + action;
		if (!EncoderConstant.NONE.equals(action)) {
			try {
				String responseData = send(request);
				if (!responseData.contains(EncoderConstant.SUCCESS_RESPONSE)) {
					throw new CommandFailureException(this.getHost(), request, responseData);
				}
			} catch (Exception e) {
				throw new CommandFailureException(this.getHost(), request, "Error while setting action audio config: " + e.getMessage(), e);
			}
		}
	}

	/**
	 * Save audio apply change
	 *
	 * @param data the data is request param
	 * @param audioId the audioId is id of audio encoder
	 */
	private void setAudioApplyChange(String data, String audioId) {
		String request = EncoderCommand.OPERATION_AUDENC.getName() + audioId + EncoderConstant.SPACE + EncoderCommand.SET + data;
		try {
			String responseData = send(request);
			if (!responseData.contains(EncoderConstant.SUCCESS_RESPONSE)) {
				throw new CommandFailureException(this.getHost(), request, responseData);
			}
		} catch (Exception e) {
			throw new CommandFailureException(this.getHost(), request, "Error while setting audio config: " + e.getMessage(), e);
		}
	}

	/**
	 * Change audioConfig by value
	 *
	 * @param extendedStatistics list extendedStatistics
	 * @param audioName the audio name is name of audio
	 * @return AudioConfig is instance in AudioConfig
	 */
	private AudioConfig convertAudioByValue(Map<String, String> extendedStatistics, String audioName) {
		AudioConfig audioConfig = new AudioConfig();
		Map<String, String> languageMap = LanguageDropdown.getValueToNameMap();
		Map<String, String> channelModeMap = ChannelModeDropdown.getValueToNameMap();
		Map<String, String> bitRateMap = BitRateDropdown.getValueToNameMap();
		Map<String, String> algorithmParamMap = AlgorithmDropdown.getParamValueToValueNameMap();
		Map<String, String> inputMap = InputDropdown.getValueToNameMap();
		String id = EncoderConstant.EMPTY_STRING;
		AudioConfig audio = nameToAudioConfig.get(audioName);
		String language = languageMap.get(extendedStatistics.get(audioName + EncoderConstant.HASH + AudioControllingMetric.LANGUAGE.getName()));
		if (!EncoderConstant.NONE.equals(language) || EncoderConstant.EMPTY_STRING.equals(language)) {
			audioConfig.setLang(language);
		}
		if (audio != null) {
			id = audio.getId();
		}
		audioConfig.setId(id);
		audioConfig.setInterfaceName(inputMap.get(extendedStatistics.get(audioName + EncoderConstant.HASH + AudioControllingMetric.INPUT.getName())));
		audioConfig.setBitRate(bitRateMap.get(extendedStatistics.get(audioName + EncoderConstant.HASH + AudioControllingMetric.BITRATE.getName())));
		audioConfig.setMode(channelModeMap.get(extendedStatistics.get(audioName + EncoderConstant.HASH + AudioControllingMetric.CHANGE_MODE.getName())));
		audioConfig.setAlgorithm(algorithmParamMap.get(extendedStatistics.get(audioName + EncoderConstant.HASH + AudioControllingMetric.ALGORITHM.getName())));
		audioConfig.setLevel(extendedStatistics.get(audioName + EncoderConstant.HASH + AudioControllingMetric.LEVEL.getName()));
		audioConfig.setAction(extendedStatistics.get(audioName + EncoderConstant.HASH + AudioControllingMetric.ACTION.getName()));

		return audioConfig;
	}

	/**
	 * Update the value for the control metric
	 *
	 * @param property is name of the metric
	 * @param value the value is value of properties
	 * @param extendedStatistics list statistics property
	 * @param advancedControllableProperties the advancedControllableProperties is list AdvancedControllableProperties
	 */
	private void updateValueForTheControllableProperty(String property, String value, Map<String, String> extendedStatistics, List<AdvancedControllableProperty> advancedControllableProperties) {
		for (AdvancedControllableProperty advancedControllableProperty : advancedControllableProperties) {
			if (advancedControllableProperty.getName().equals(property)) {
				extendedStatistics.put(property, value);
				advancedControllableProperty.setValue(value);
				break;
			}
		}
	}

	/**
	 * Add advancedControllableProperties if  advancedControllableProperties different empty
	 *
	 * @param advancedControllableProperties advancedControllableProperties is the list that store all controllable properties
	 * @param property the property is item advancedControllableProperties
	 */
	private void addAdvanceControlProperties(List<AdvancedControllableProperty> advancedControllableProperties, AdvancedControllableProperty property) {
		if (property != null) {
			advancedControllableProperties.removeIf(item -> item.getName().equals(property.getName()));
			advancedControllableProperties.add(property);
		}
	}

	/**
	 * Add dropdown is control property for metric
	 *
	 * @param stats list statistic
	 * @param options list select
	 * @param name String name of metric
	 * @return AdvancedControllableProperty dropdown instance if add dropdown success else will is null
	 */
	private AdvancedControllableProperty controlDropdownAcceptNoneValue(Map<String, String> stats, String[] options, String name, String value) {

		//handle case accept None value
		String nameMetric = name.split(EncoderConstant.HASH)[1];
		if (nameMetric.equals(AudioControllingMetric.ACTION.getName())) {
			stats.put(name, EncoderConstant.NONE);
			return createDropdown(name, options, EncoderConstant.NONE);
		}
		stats.put(name, value);
		return createDropdown(name, options, value);
	}

	/**
	 * Add dropdown is control property for metric
	 *
	 * @param stats list statistic
	 * @param options list select
	 * @param name String name of metric
	 * @return AdvancedControllableProperty dropdown instance if add dropdown success else will is null
	 */
	private AdvancedControllableProperty controlDropdown(Map<String, String> stats, String[] options, String name, String value) {
		stats.put(name, value);
		if (!EncoderConstant.NONE.equals(value)) {
			return createDropdown(name, options, value);
		}
		return null;
	}

	/***
	 * Create dropdown advanced controllable property
	 *
	 * @param name the name of the control
	 * @param initialValue initial value of the control
	 * @return AdvancedControllableProperty dropdown instance
	 */
	private AdvancedControllableProperty createDropdown(String name, String[] values, String initialValue) {
		AdvancedControllableProperty.DropDown dropDown = new AdvancedControllableProperty.DropDown();
		dropDown.setOptions(values);
		dropDown.setLabels(values);

		return new AdvancedControllableProperty(name, new Date(), dropDown, initialValue);
	}

	/**
	 * Create a button.
	 *
	 * @param name name of the button
	 * @param label label of the button
	 * @param labelPressed label of the button after pressing it
	 * @param gracePeriod grace period of button
	 * @return This returns the instance of {@link AdvancedControllableProperty} type Button.
	 */
	private AdvancedControllableProperty createButton(String name, String label, String labelPressed, long gracePeriod) {
		AdvancedControllableProperty.Button button = new AdvancedControllableProperty.Button();
		button.setLabel(label);
		button.setLabelPressed(labelPressed);
		button.setGracePeriod(gracePeriod);
		return new AdvancedControllableProperty(name, new Date(), button, "");
	}
	//--------------------------------------------------------------------------------------------------------------------------------
	//endregion
}
