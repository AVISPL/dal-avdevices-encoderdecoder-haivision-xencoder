/*
 * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.locks.ReentrantLock;
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
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.common.StreamControllingMetric;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.common.StreamMonitoringMetric;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.common.SystemMonitoringMetric;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.common.VideoControllingMetric;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.common.VideoMonitoringMetric;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dropdownlist.AlgorithmEnum;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dropdownlist.AspectRatioEnum;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dropdownlist.AudioActionEnum;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dropdownlist.AudioLevel;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dropdownlist.AudioStateEnum;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dropdownlist.BitRateEnum;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dropdownlist.ChannelModeEnum;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dropdownlist.EntropyCodingEnum;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dropdownlist.EnumTypeHandler;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dropdownlist.FecEnum;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dropdownlist.FrameRateEnum;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dropdownlist.FramingEnum;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dropdownlist.InputEnum;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dropdownlist.LanguageEnum;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dropdownlist.ProtocolEnum;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dropdownlist.ResolutionEnum;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dropdownlist.SampleRateEnum;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dropdownlist.TimeCodeSource;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dropdownlist.VideoActionEnum;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dropdownlist.VideoStateEnum;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dto.AuthenticationRole;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dto.InputResponse;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dto.SystemInfoResponse;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dto.TemperatureStatus;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dto.audio.AudioConfig;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dto.audio.AudioStatistics;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dto.stream.Audio;
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

	private ExtendedStatistics localExtendedStatistics;
	private ExtendedStatistics localCreateOutputStream;
	private boolean isEmergencyDelivery;
	private boolean isCreateStreamCalled;
	private boolean isConfigManagement;
	private ObjectMapper objectMapper = new ObjectMapper();
	private Integer noOfMonitoringMetric = 0;
	private String roleBased;
	private final Map<String, String> failedMonitor = new HashMap<>();
	private final Map<String, String> localStatsStreamOutput = new HashMap<>();
	private final String uuidDay = UUID.randomUUID().toString().replace(EncoderConstant.DASH, EncoderConstant.EMPTY_STRING);

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
	 * List of StillImage
	 */
	private List<String> stillImageList = new ArrayList<>();

	/**
	 * Map of Audio statistics with key name of AudioStatistics, value is AudioStatistics
	 */
	private Map<String, AudioStatistics> mapOfNameAndAudioStatistics = new HashMap<>();

	/**
	 * Map of Video statistics with key name of VideoStatistics, value is VideoStatistics
	 */
	private Map<String, VideoStatistics> mapOfNameAndVideoStatistics = new HashMap<>();

	/**
	 * Map of Audio Config with key is the name of AudioConfig, value is AudioConfig
	 */
	private Map<String, AudioConfig> mapOfNameAndAudioConfig = new HashMap<>();

	/**
	 * Map of Video Config with key is the name of VideoConfig, value is VideoConfig
	 */
	private Map<String, VideoConfig> mapOfNameAndVideoConfig = new HashMap<>();

	/**
	 * Map of Input Response with key is the name of InputResponse, value is InputResponse
	 */
	private Map<String, InputResponse> mapOfNameAndInputResponse = new HashMap<>();

	/**
	 * Map of Audio with key is the name AudioConfig, value is Audio
	 */
	private Map<String, Audio> mapOfNameAndSourceAudio = new HashMap<>();

	/**
	 * Map of Id and Name with key is the Id of AudioConfig, value is name of AudioConfig
	 */
	private Map<String, String> mapOfIdAndNameAudioConfig = new HashMap<>();

	/**
	 * ReentrantLock to prevent null pointer exception to localExtendedStatistics when controlProperty method is called before GetMultipleStatistics method.
	 */
	private final ReentrantLock reentrantLock = new ReentrantLock();

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
		reentrantLock.lock();
		try {
			ExtendedStatistics extendedStatistics = new ExtendedStatistics();
			Map<String, String> stats = new HashMap<>();
			Map<String, String> statsCreateOutputStream = new HashMap<>();
			List<AdvancedControllableProperty> advancedControllableProperties = new ArrayList<>();
			List<AdvancedControllableProperty> createStreamAdvancedControllable = new ArrayList<>();
			failedMonitor.clear();
			if (!isEmergencyDelivery) {
				roleBased = retrieveUserRole();
				isConfigManagement = isConfigManagementProperties();
				populateInformationFromDevice(stats, advancedControllableProperties);
				if ((EncoderConstant.OPERATOR.equals(roleBased) || EncoderConstant.ADMIN.equals(roleBased)) && isConfigManagement) {
					extendedStatistics.setControllableProperties(advancedControllableProperties);
				}
				extendedStatistics.setStatistics(stats);
				localExtendedStatistics = extendedStatistics;
			}
			if (EncoderConstant.ADMIN.equals(roleBased) || EncoderConstant.OPERATOR.equals(roleBased) && !isCreateStreamCalled && isConfigManagement) {
				localCreateOutputStream = new ExtendedStatistics();
				createOutputStream(statsCreateOutputStream, createStreamAdvancedControllable);
				localCreateOutputStream.setStatistics(statsCreateOutputStream);
				localCreateOutputStream.setControllableProperties(createStreamAdvancedControllable);
				localStatsStreamOutput.putAll(statsCreateOutputStream);
				isCreateStreamCalled = true;
			}

			// add all stats of create output stream into local stats
			Map<String, String> localStats = localExtendedStatistics.getStatistics();
			Map<String, String> localStreamStats = localCreateOutputStream.getStatistics();
			localStats.putAll(localStreamStats);

			//remove and update control property for create output stream into localExtendedStatistics
			List<AdvancedControllableProperty> localAdvancedControl = localExtendedStatistics.getControllableProperties();
			List<AdvancedControllableProperty> localStreamAdvancedControl = localCreateOutputStream.getControllableProperties();
			List<String> nameList = localStreamAdvancedControl.stream().map(AdvancedControllableProperty::getName).collect(Collectors.toList());
			localAdvancedControl.removeIf(item -> nameList.contains(item.getName()));
			localAdvancedControl.addAll(localStreamAdvancedControl);

		} finally {
			reentrantLock.unlock();
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
		reentrantLock.lock();
		try {
			if (localExtendedStatistics == null) {
				return;
			}
			Map<String, String> extendedStatistics = localExtendedStatistics.getStatistics();
			List<AdvancedControllableProperty> advancedControllableProperties = localExtendedStatistics.getControllableProperties();
			String propertiesAudioAndVideo = property.substring(0, EncoderConstant.AUDIO.length());
			if (EncoderConstant.AUDIO.equals(propertiesAudioAndVideo)) {
				controlAudioProperty(property, value, extendedStatistics, advancedControllableProperties);
			} else {
				controlVideoProperty(property, value, extendedStatistics, advancedControllableProperties);
			}
		} finally {
			reentrantLock.unlock();
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

		//clear data before fetching data
		clearBeforeFetchingData();
		retrieveStillImageList();
		for (EncoderMonitoringMetric encoderMonitoringMetric : EncoderMonitoringMetric.values()) {
			if (EncoderMonitoringMetric.ACCOUNT.equals(encoderMonitoringMetric) || EncoderMonitoringMetric.STILL_IMAGE.equals(encoderMonitoringMetric)) {
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
	 * Retrieve list name of Still Image
	 */
	private void retrieveStillImageList() {
		try {
			String response = send(EncoderUtil.getMonitorCommand(EncoderMonitoringMetric.STILL_IMAGE));
			if (response != null) {
				String[] stillList = response.split(EncoderConstant.REGEX_DATA);
				for (String stileImageItem : stillList) {
					if (stileImageItem.contains(EncoderConstant.MP4)) {
						stillImageList.add(stileImageItem.trim().replace(EncoderConstant.SPACE + EncoderConstant.SPACE, EncoderConstant.EMPTY_STRING));
					}
				}
				stillImageList.add(EncoderConstant.NONE);
			}
		} catch (Exception e) {
			failedMonitor.put(EncoderMonitoringMetric.STILL_IMAGE.getName(), e.getMessage());
		}
	}

	/**
	 * Clear data before fetching data
	 */
	private void clearBeforeFetchingData() {
		//audio
		audioStatisticsList.clear();
		audioConfigList.clear();
		mapOfNameAndAudioConfig.clear();
		mapOfNameAndAudioStatistics.clear();

		//video
		videoStatisticsList.clear();
		videoConfigList.clear();
		mapOfNameAndVideoConfig.clear();
		mapOfNameAndVideoStatistics.clear();

		//input
		mapOfNameAndInputResponse.clear();

		//stream
		streamConfigList.clear();
		streamStatisticsList.clear();

		//Still Image
		stillImageList.clear();
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
			case VIDEO_CONFIG:
				populateConfigData(stats, advancedControllableProperties, encoderMonitoringMetric);
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
	 * Populate controlling properties for audio, video, stream, etc configs
	 *
	 * @param stats list statistics property
	 * @param advancedControllableProperties the advancedControllableProperties is list AdvancedControllableProperties
	 * @param metric the metric instance in EncoderMonitoringMetric
	 */
	private void populateConfigData(Map<String, String> stats, List<AdvancedControllableProperty> advancedControllableProperties, EncoderMonitoringMetric metric) {
		Objects.requireNonNull(stats);
		Objects.requireNonNull(advancedControllableProperties);
		if ((EncoderConstant.ADMIN.equals(roleBased) || EncoderConstant.OPERATOR.equals(roleBased)) && isConfigManagement) {
			switch (metric) {
				case AUDIO_CONFIG:
					for (AudioConfig audioConfig : audioConfigList) {
						addControlAudioConfig(stats, advancedControllableProperties, audioConfig);
					}
					break;
				case VIDEO_CONFIG:
					for (VideoConfig videoConfig : videoConfigList) {
						addControlVideoConfig(stats, advancedControllableProperties, videoConfig);
					}
					break;
				case ACCOUNT:
				case ROLE_BASED:
				case TEMPERATURE:
				case AUDIO_STATISTICS:
				case VIDEO_STATISTICS:
				case STREAM_STATISTICS:
				case SYSTEM_INFORMATION:
					break;
				default:
					throw new IllegalStateException(String.format("The metric %s is not supported: ", metric.getName()));
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
						// Example streamValue format 2h2d2s ago => we only take 2h2d2s
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
						streamValue = convertValueByIndexOfSpace(streamValue);
						if (EncoderConstant.UNKNOWN.equals(streamValue)) {
							streamValue = String.valueOf(EncoderConstant.NUMBER_ONE);
						}
						stats.put(streamKeyName, streamValue);
						break;
					case RTT:
						streamValue = convertValueByIndexOfSpace(streamValue);
						boolean isValidValueOrLessThanOne = EncoderConstant.NONE.equals(streamName) && Integer.parseInt(streamValue) < EncoderConstant.NUMBER_ONE;
						if (isValidValueOrLessThanOne) {
							streamValue = EncoderConstant.LESS_THAN + EncoderConstant.SPACE + EncoderConstant.NUMBER_ONE;
						}
						stats.put(streamKeyName, streamValue);
						break;
					case LOCAL_BUFFER_LEVEL:
						stats.put(streamKeyName, convertValueByIndexOfSpace(streamValue));
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
					stats.put(audioKeyName, convertValueByIndexOfSpace(audioValue));
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
						stats.put(videoKeyName, convertValueByIndexOfSpace(videoValue).replace(EncoderConstant.PERCENT, EncoderConstant.EMPTY_STRING));
						break;
					case UPTIME:
						stats.put(videoKeyName, formatTimeData(videoValue));
						break;
					case RESOLUTION:
						if (EncoderConstant.NONE.equals(videoValue)) {
							videoValue = mapOfNameAndVideoConfig.get(videoName).getResolution();
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
	 * @throws IllegalArgumentException if the name is not supported
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
			case INPUT:
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
			case STILL_IMAGE:
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
	 * @throws IllegalArgumentException if the name is not supported
	 */
	private void retrieveDataDetails(Map<String, String> mappingData, EncoderMonitoringMetric metric) {
		switch (metric) {
			case AUDIO_STATISTICS:
				AudioStatistics audioStatistics = objectMapper.convertValue(mappingData, AudioStatistics.class);
				mapOfNameAndAudioStatistics.put(audioStatistics.getName(), audioStatistics);
				audioStatisticsList.add(audioStatistics);
				break;
			case AUDIO_CONFIG:
				AudioConfig audioConfig = objectMapper.convertValue(mappingData, AudioConfig.class);
				String audioName = audioConfig.getName();
				String language = audioConfig.getLang();
				if (!language.contains(EncoderConstant.NONE)) {
					language = language.substring(language.indexOf(EncoderConstant.SPACE));
					audioName = String.format("%s %s", audioName, language);
				}
				audioConfig.setName(audioName);
				mapOfNameAndAudioConfig.put(audioName, audioConfig);
				mapOfIdAndNameAudioConfig.put(audioConfig.getId(), audioName);
				audioConfigList.add(audioConfig);
				break;
			case VIDEO_STATISTICS:
				VideoStatistics videoStatistics = objectMapper.convertValue(mappingData, VideoStatistics.class);
				mapOfNameAndVideoStatistics.put(videoStatistics.getName(), videoStatistics);
				videoStatisticsList.add(videoStatistics);
				break;
			case VIDEO_CONFIG:
				VideoConfig videoConfigResponse = objectMapper.convertValue(mappingData, VideoConfig.class);
				mapOfNameAndVideoConfig.put(videoConfigResponse.getName(), videoConfigResponse);
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
			case INPUT:
				InputResponse inputResponse = objectMapper.convertValue(mappingData, InputResponse.class);
				mapOfNameAndInputResponse.put(inputResponse.getName(), inputResponse);
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
				streamName = String.format("%s://@%s(%s)", protocol, address, port);
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
	 * Convert value by the index of space. If it does not contain space, return the value instead.
	 *
	 * @param value the value is string value
	 * @return value extracted
	 */
	private String convertValueByIndexOfSpace(String value) {
		try {
			//Example format of value 10 ms => we only take 10
			return value.substring(0, value.indexOf(EncoderConstant.SPACE));
		} catch (Exception e) {
			//if exception occur (no space in value) we return the initial value
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

		String[] dropdownInput = EnumTypeHandler.getEnumNames(InputEnum.class);
		String[] dropdownMode = EnumTypeHandler.getEnumNames(ChannelModeEnum.class);
		String[] dropdownAlgorithm = EnumTypeHandler.getEnumNames(AlgorithmEnum.class);
		String[] dropdownSampleRate = EnumTypeHandler.getEnumNames(SampleRateEnum.class);
		String[] dropdownLanguage = EnumTypeHandler.getEnumNames(LanguageEnum.class);
		String[] dropdownLevel = EnumTypeHandler.getEnumNames(AudioLevel.class);
		String[] dropdownBitRate = BitRateEnum.getArrayOfNameByStereoOrMonoMode(false);
		Map<String, String> languageMap = EnumTypeHandler.getMapOfEnumNames(LanguageEnum.class, false);
		Map<String, String> stateDropdown = EnumTypeHandler.getMapOfEnumNames(AudioStateEnum.class, false);
		Map<String, String> inputMap = EnumTypeHandler.getMapOfEnumNames(InputEnum.class, false);
		Map<String, String> algorithmName = AlgorithmEnum.getMapOfAlgorithmDropdown(true);

		Map<String, String> channelModeMap = EnumTypeHandler.getMapOfEnumNames(ChannelModeEnum.class, false);
		String audioName = audioConfig.getName();
		int indexName = audioName.indexOf(EncoderConstant.SPACE, EncoderConstant.AUDIO_ENCODER.length());
		if (indexName != -1) {
			audioName = audioName.substring(0, indexName);
		}
		String value;
		for (AudioControllingMetric audioMetric : AudioControllingMetric.values()) {
			String audioKeyName = audioName + EncoderConstant.HASH + audioMetric.getName();
			switch (audioMetric) {
				case STATE:
					String stateAudio = mapOfNameAndAudioStatistics.get(audioName).getState();
					stats.put(audioKeyName, stateAudio);
					break;
				case INPUT:
					value = inputMap.get(audioConfig.getInterfaceName());
					AdvancedControllableProperty inputDropdownControlProperty = controlDropdown(stats, dropdownInput, audioKeyName, value);
					addOrUpdateAdvanceControlProperties(advancedControllableProperties, inputDropdownControlProperty);
					break;
				case CHANGE_MODE:
					value = channelModeMap.get(audioConfig.getMode());
					AdvancedControllableProperty channelModeControlProperty = controlDropdown(stats, dropdownMode, audioKeyName, value);
					addOrUpdateAdvanceControlProperties(advancedControllableProperties, channelModeControlProperty);
					break;
				case BITRATE:
					value = audioConfig.getBitRate();
					String mode = audioConfig.getMode();
					if (mode.equals(ChannelModeEnum.STEREO.getName())) {
						dropdownBitRate = BitRateEnum.getArrayOfNameByStereoOrMonoMode(true);
					}
					AdvancedControllableProperty bitRateControlProperty = controlDropdown(stats, dropdownBitRate, audioKeyName, value);
					addOrUpdateAdvanceControlProperties(advancedControllableProperties, bitRateControlProperty);
					break;
				case SAMPLE_RATE:
					value = audioConfig.getSampleRate();
					AdvancedControllableProperty samPleRateControlProperty = controlDropdown(stats, dropdownSampleRate, audioKeyName, value);
					addOrUpdateAdvanceControlProperties(advancedControllableProperties, samPleRateControlProperty);
					break;
				case ALGORITHM:
					value = algorithmName.get(audioConfig.getAlgorithm());
					AdvancedControllableProperty algorithmControlProperty = controlDropdown(stats, dropdownAlgorithm, audioKeyName, value);
					addOrUpdateAdvanceControlProperties(advancedControllableProperties, algorithmControlProperty);
					break;
				case LANGUAGE:
					String language = audioConfig.getLang();
					value = languageMap.get(language);
					if (StringUtils.isNullOrEmpty(value)) {
						value = EncoderConstant.NONE;
					}
					AdvancedControllableProperty languageControlProperty = controlDropdownAcceptNoneValue(stats, dropdownLanguage, audioKeyName, value);
					addOrUpdateAdvanceControlProperties(advancedControllableProperties, languageControlProperty);
					break;
				case ACTION:
					stateAudio = mapOfNameAndAudioStatistics.get(audioName).getState();
					String[] dropdownAction = AudioActionEnum.getArrayOfEnumByAction(stateAudio);
					value = stateDropdown.get(stateAudio);
					AdvancedControllableProperty actionDropdownControlProperty = controlDropdownAcceptNoneValue(stats, dropdownAction, audioKeyName, value);
					addOrUpdateAdvanceControlProperties(advancedControllableProperties, actionDropdownControlProperty);
					break;
				case LEVEL:
					if (InputEnum.ANALOG.getName().equalsIgnoreCase(audioConfig.getInterfaceName())) {
						value = audioConfig.getLevel();
						int len = value.indexOf(EncoderConstant.SPACE);
						//subString the value by space IfAbsent
						if (len > -1) {
							value = value.substring(0, len);
						}
						AdvancedControllableProperty levelDropdownControlProperty = controlDropdownAcceptNoneValue(stats, dropdownLevel, audioKeyName, value);
						addOrUpdateAdvanceControlProperties(advancedControllableProperties, levelDropdownControlProperty);
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
	 * Control Audio Property
	 *
	 * @param property the property is the filed name of controlling metric
	 * @param value the value is value of metric
	 * @param stats list of stats
	 * @param advancedControllableProperties the advancedControllableProperties is advancedControllableProperties instance
	 */
	private void controlAudioProperty(String property, String value, Map<String, String> stats, List<AdvancedControllableProperty> advancedControllableProperties) {
		// property format: GroupName#PropertyName
		String[] audioProperty = property.split(EncoderConstant.HASH);
		String audioName = audioProperty[0];
		String propertyName = audioProperty[1];
		AudioControllingMetric audioControllingMetric = AudioControllingMetric.getByName(propertyName);
		String levelName = audioName + EncoderConstant.HASH + AudioControllingMetric.LEVEL.getName();
		String bitRateName = audioName + EncoderConstant.HASH + AudioControllingMetric.BITRATE.getName();
		isEmergencyDelivery = true;
		switch (audioControllingMetric) {
			case LEVEL:
				mapOfNameAndAudioConfig.get(audioName).setLevel(value);
				updateValueForTheControllableProperty(property, value, stats, advancedControllableProperties);
				break;
			case ACTION:
			case BITRATE:
			case LANGUAGE:
			case ALGORITHM:
			case SAMPLE_RATE:
				updateValueForTheControllableProperty(property, value, stats, advancedControllableProperties);
				break;
			case INPUT:
				String[] inputDropdown = EnumTypeHandler.getEnumNames(InputEnum.class);
				String[] levelDropdown = EnumTypeHandler.getEnumNames(AudioLevel.class);
				AdvancedControllableProperty inputDropdownControlProperty = controlDropdown(stats, inputDropdown, property, value);
				addOrUpdateAdvanceControlProperties(advancedControllableProperties, inputDropdownControlProperty);
				String level = mapOfNameAndAudioConfig.get(audioName).getLevel();
				if (StringUtils.isNullOrEmpty(level)) {
					level = EncoderConstant.DEFAULT_AUDIO_LEVEL;
				}
				if (InputEnum.ANALOG.getName().equalsIgnoreCase(value)) {
					AdvancedControllableProperty levelProperty = controlDropdown(stats, levelDropdown, levelName, level);
					addOrUpdateAdvanceControlProperties(advancedControllableProperties, levelProperty);
				} else {
					stats.remove(levelName);
					advancedControllableProperties.removeIf(item -> item.getName().equals(levelName));
				}
				break;
			case CHANGE_MODE:
				String[] dropdownMode = EnumTypeHandler.getEnumNames(ChannelModeEnum.class);
				AdvancedControllableProperty channelModeControlProperty = controlDropdown(stats, dropdownMode, property, value);
				addOrUpdateAdvanceControlProperties(advancedControllableProperties, channelModeControlProperty);

				//Update bitRate with channel mode
				String bitRate = stats.get(bitRateName);
				String defaultBitrate = BitRateEnum.getDefaultBitRate(bitRate, value);

				//default channel mode is IsStereo
				String[] dropdownBitRate = BitRateEnum.getArrayOfNameByStereoOrMonoMode(true);
				if (!ChannelModeEnum.STEREO.getName().equals(value)) {
					dropdownBitRate = BitRateEnum.getArrayOfNameByStereoOrMonoMode(false);
				}
				AdvancedControllableProperty bitRateControlProperty = controlDropdown(stats, dropdownBitRate, bitRateName, defaultBitrate);
				addOrUpdateAdvanceControlProperties(advancedControllableProperties, bitRateControlProperty);
				break;
			case APPLY_CHANGE:
				AudioConfig audioConfig = convertAudioByValue(stats, audioName);

				// sent request to apply all change for all metric
				sendCommandToSaveAllAudioProperties(audioConfig, audioConfig.getId());

				//sent request to action for the metric
				sendCommandToSetAudioAction(audioConfig);
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
			stats.put(propertyName + EncoderConstant.HASH + AudioControllingMetric.APPLY_CHANGE.getName(), EncoderConstant.EMPTY_STRING);
			advancedControllableProperties.add(createButton(propertyName + EncoderConstant.HASH + AudioControllingMetric.APPLY_CHANGE.getName(), EncoderConstant.APPLY, EncoderConstant.APPLYING, 0));

			stats.put(propertyName + EncoderConstant.HASH + EncoderConstant.EDITED, EncoderConstant.TRUE);
			stats.put(propertyName + EncoderConstant.HASH + EncoderConstant.CANCEL, EncoderConstant.EMPTY_STRING);
			advancedControllableProperties.add(createButton(propertyName + EncoderConstant.HASH + EncoderConstant.CANCEL, EncoderConstant.CANCEL, EncoderConstant.CANCELING, 0));
		}
	}

	/**
	 * Send command to set audio action
	 *
	 * @param audioConfig is instance AudioConfig DTO
	 * @throws ResourceNotReachableException if set action audio config failed
	 */
	private void sendCommandToSetAudioAction(AudioConfig audioConfig) {
		String audioId = audioConfig.getId();
		String action = audioConfig.getAction();
		String request = EncoderCommand.OPERATION_AUDENC.getName() + audioId + EncoderConstant.SPACE + action;
		if (!EncoderConstant.NONE.equals(action)) {
			try {
				String responseData = send(request);
				if (!responseData.contains(EncoderConstant.SUCCESS_RESPONSE)) {
					throw new ResourceNotReachableException(String.format("Change action %s failed", audioConfig.getAction()));
				}
			} catch (Exception e) {
				logger.error(String.format(EncoderConstant.COMMAND_FAILED_FORMAT, this.host, request, e));
				throw new ResourceNotReachableException("Error while setting action audio config: " + e.getMessage(), e);
			}
		}
	}

	/**
	 * Send command to apply all audio properties
	 *
	 * @param audioConfig the audioConfig is instance in AudioConfig
	 * @param audioId the audioId is id of audio encoder
	 * @throws Exception if set audio config failed
	 */
	private void sendCommandToSaveAllAudioProperties(AudioConfig audioConfig, String audioId) {
		String data = audioConfig.toString();
		String request = EncoderCommand.OPERATION_AUDENC.getName() + audioId + EncoderConstant.SPACE + EncoderCommand.SET + data;
		try {
			String responseData = send(request);
			if (responseData.contains(EncoderConstant.ERROR_INPUT)) {
				throw new ResourceNotReachableException(String.format("The INPUT invalid value, the adapter doesn't support INPUT: %s", audioConfig.getInterfaceName()));
			} else if (!responseData.contains(EncoderConstant.SUCCESS_RESPONSE)) {
				throw new ResourceNotReachableException(responseData);
			}
		} catch (Exception e) {
			logger.error(String.format(EncoderConstant.COMMAND_FAILED_FORMAT, this.host, request, e));
			throw new ResourceNotReachableException("Error while setting audio config: " + e.getMessage(), e);
		}
	}

	/**
	 * Convert audioConfig by value
	 *
	 * @param stats list of stats
	 * @param audioName the audio name is name of audio
	 * @return AudioConfig is instance in AudioConfig
	 */
	private AudioConfig convertAudioByValue(Map<String, String> stats, String audioName) {
		AudioConfig audioConfig = new AudioConfig();
		Map<String, String> languageMap = LanguageEnum.getParamValueToNameMap();
		Map<String, String> channelModeMap = ChannelModeEnum.ChannelModeEnum();
		Map<String, String> inputMap = EnumTypeHandler.getMapOfEnumNames(InputEnum.class, true);
		Map<String, String> bitRateMap = EnumTypeHandler.getMapOfEnumNames(BitRateEnum.class, true);
		Map<String, String> algorithmParamMap = AlgorithmEnum.getMapOfAlgorithmDropdown(false);
		String id = EncoderConstant.EMPTY_STRING;
		AudioConfig audio = mapOfNameAndAudioConfig.get(audioName);
		if (audio == null) {
			for (Map.Entry<String, AudioConfig> audioKey : mapOfNameAndAudioConfig.entrySet()) {
				if (audioKey.getKey().contains(audioName)) {
					audio = mapOfNameAndAudioConfig.get(audioKey.getKey());
					break;
				}
			}
		}
		if (audio != null) {
			id = audio.getId();
		}
		audioConfig.setId(id);
		audioConfig.setLang(languageMap.get(stats.get(audioName + EncoderConstant.HASH + AudioControllingMetric.LANGUAGE.getName())));
		audioConfig.setInterfaceName(inputMap.get(stats.get(audioName + EncoderConstant.HASH + AudioControllingMetric.INPUT.getName())));
		audioConfig.setBitRate(bitRateMap.get(stats.get(audioName + EncoderConstant.HASH + AudioControllingMetric.BITRATE.getName())));
		audioConfig.setMode(channelModeMap.get(stats.get(audioName + EncoderConstant.HASH + AudioControllingMetric.CHANGE_MODE.getName())));
		audioConfig.setAlgorithm(algorithmParamMap.get(stats.get(audioName + EncoderConstant.HASH + AudioControllingMetric.ALGORITHM.getName())));
		audioConfig.setLevel(stats.get(audioName + EncoderConstant.HASH + AudioControllingMetric.LEVEL.getName()));
		audioConfig.setAction(stats.get(audioName + EncoderConstant.HASH + AudioControllingMetric.ACTION.getName()));

		return audioConfig;
	}

	//region perform controls video
	//--------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Add control monitoring data for video config
	 *
	 * @param stats list statistics property
	 * @param advancedControllableProperties the advancedControllableProperties is list AdvancedControllableProperties
	 * @param videoConfig is instance in VideoConfig DTO
	 */
	private void addControlVideoConfig(Map<String, String> stats, List<AdvancedControllableProperty> advancedControllableProperties, VideoConfig videoConfig) {
		Objects.requireNonNull(stats);
		Objects.requireNonNull(advancedControllableProperties);
		Objects.requireNonNull(videoConfig);

		String[] dropdownInput = mapOfNameAndInputResponse.keySet().toArray(new String[mapOfNameAndInputResponse.size()]);
		String[] dropdownResolution = EnumTypeHandler.getEnumNames(ResolutionEnum.class);
		String[] dropdownFrameRate = EnumTypeHandler.getEnumNames(FrameRateEnum.class);
		String[] dropdownFraming = EnumTypeHandler.getEnumNames(FramingEnum.class);
		String[] dropdownAspectRatio = EnumTypeHandler.getEnumNames(AspectRatioEnum.class);
		String[] dropdownTimeCodeSource = EnumTypeHandler.getEnumNames(TimeCodeSource.class);
		String[] dropdownEntropyCoding = EnumTypeHandler.getEnumNames(EntropyCodingEnum.class);
		String[] dropdownAction = VideoActionEnum.getVideoAction(true);

		Map<String, String> videoStateDropdown = EnumTypeHandler.getMapOfEnumNames(VideoStateEnum.class, false);
		Map<String, String> timeCodeSourceMap = EnumTypeHandler.getMapOfEnumNames(TimeCodeSource.class, false);
		Map<String, String> resolutionValueToNameMap = EnumTypeHandler.getMapOfEnumNames(ResolutionEnum.class, false);
		String videoName = videoConfig.getName();
		String value;

		for (VideoControllingMetric videoMetric : VideoControllingMetric.values()) {
			String videoKeyName = videoName + EncoderConstant.HASH + videoMetric.getName();
			switch (videoMetric) {
				case STATE:
					String stateVideo = mapOfNameAndVideoStatistics.get(videoConfig.getName()).getState();
					stats.put(videoKeyName, stateVideo);
					break;
				case INPUT_FORMAT:
					String videoValue = getDefaultValueForNullData(mapOfNameAndVideoStatistics.get(videoName).getInputFormat());
					boolean isValidValue = videoValue.equals(EncoderConstant.NONE) || videoValue.equals(EncoderConstant.UNKNOWN);
					if (isValidValue) {
						videoValue = EncoderConstant.NO_INPUT;
					}
					stats.put(videoKeyName, videoValue);
					break;
				case INPUT:
					value = videoConfig.getInputInterface();
					AdvancedControllableProperty inputControlProperty = controlDropdown(stats, dropdownInput, videoKeyName, value);
					addOrUpdateAdvanceControlProperties(advancedControllableProperties, inputControlProperty);
					break;
				case BITRATE:
					value = convertValueByIndexOfSpace(videoConfig.getBitrate());
					AdvancedControllableProperty bitRateControlProperty = controlTextOrNumeric(stats, videoKeyName, value, true);
					addOrUpdateAdvanceControlProperties(advancedControllableProperties, bitRateControlProperty);
					break;
				case RESOLUTION:
					value = resolutionValueToNameMap.get(videoConfig.getResolution());
					AdvancedControllableProperty resolutionControlProperty = controlDropdown(stats, dropdownResolution, videoKeyName, value);
					addOrUpdateAdvanceControlProperties(advancedControllableProperties, resolutionControlProperty);
					break;
				case CROPPING:
					String resolutionMode = videoConfig.getResolution();
					if (!EncoderConstant.INPUT_AUDIO.equals(resolutionMode)) {
						handleControlCroppingMetric(stats, advancedControllableProperties, videoKeyName, videoConfig);
					}
					break;
				case FRAME_RATE:
					value = videoConfig.getFrameRate();
					if (EncoderConstant.INPUT_AUDIO.equals(value)) {
						value = FrameRateEnum.FAME_RATE_0.getName();
					}
					AdvancedControllableProperty frameRateControlProperty = controlDropdown(stats, dropdownFrameRate, videoKeyName, value);
					addOrUpdateAdvanceControlProperties(advancedControllableProperties, frameRateControlProperty);
					break;
				case FRAMING:
					value = videoConfig.getFraming();
					AdvancedControllableProperty framingControlProperty = controlDropdown(stats, dropdownFraming, videoKeyName, value);
					addOrUpdateAdvanceControlProperties(advancedControllableProperties, framingControlProperty);
					break;
				case GOP_SIZE:
					value = videoConfig.getGopSize();
					AdvancedControllableProperty gopSizeControlProperty = controlTextOrNumeric(stats, videoKeyName, value, true);
					addOrUpdateAdvanceControlProperties(advancedControllableProperties, gopSizeControlProperty);
					break;
				case ASPECT_RATIO:
					value = videoConfig.getAspectRatio();
					AdvancedControllableProperty aspectRatioControlProperty = controlDropdown(stats, dropdownAspectRatio, videoKeyName, value);
					addOrUpdateAdvanceControlProperties(advancedControllableProperties, aspectRatioControlProperty);
					break;
				case CLOSED_CAPTION:
					value = convertByState(videoConfig.getClosedCaption(), true);
					AdvancedControllableProperty closedCaptionControlProperty = controlSwitch(stats, videoKeyName, value, EncoderConstant.DISABLE, EncoderConstant.ENABLE);
					addOrUpdateAdvanceControlProperties(advancedControllableProperties, closedCaptionControlProperty);
					break;
				case TIME_CODE_SOURCE:
					value = timeCodeSourceMap.get(videoConfig.getTimeCode());
					AdvancedControllableProperty timeCodeControlProperty = controlDropdownAcceptNoneValue(stats, dropdownTimeCodeSource, videoKeyName, value);
					addOrUpdateAdvanceControlProperties(advancedControllableProperties, timeCodeControlProperty);
					break;
				case ENTROPY_CODING:
					value = videoConfig.getEntropyCoding();
					AdvancedControllableProperty entropyCodingControlProperty = controlDropdown(stats, dropdownEntropyCoding, videoKeyName, value);
					addOrUpdateAdvanceControlProperties(advancedControllableProperties, entropyCodingControlProperty);
					break;
				case PARTITIONING:
					value = convertByState(videoConfig.getPicturePartitioning(), true);
					AdvancedControllableProperty picturePartitioningControlProperty = controlSwitch(stats, videoKeyName, value, EncoderConstant.DISABLE, EncoderConstant.ENABLE);
					addOrUpdateAdvanceControlProperties(advancedControllableProperties, picturePartitioningControlProperty);
					break;
				case INTRA_REFRESH:
					value = convertByState(videoConfig.getIntraRefresh(), true);
					AdvancedControllableProperty intraRefreshControlProperty = controlSwitch(stats, videoKeyName, value, EncoderConstant.DISABLE, EncoderConstant.ENABLE);
					addOrUpdateAdvanceControlProperties(advancedControllableProperties, intraRefreshControlProperty);
					break;
				case INTRA_REFRESH_RATE:
					value = videoConfig.getIntraRefresh();
					if (EncoderConstant.ON.equals(value)) {
						String refreshRate = videoConfig.getIntraRefreshRate();
						AdvancedControllableProperty refreshRateControlProperty = controlTextOrNumeric(stats, videoKeyName, refreshRate, true);
						addOrUpdateAdvanceControlProperties(advancedControllableProperties, refreshRateControlProperty);
					} else {
						stats.put(videoKeyName, EncoderConstant.EMPTY_STRING);
					}
					break;
				case PARTIAL_IMAGE_SKIP:
					value = convertByState(videoConfig.getPartialFrameSkip(), true);
					AdvancedControllableProperty imageSkipControlProperty = controlSwitch(stats, videoKeyName, value, EncoderConstant.DISABLE, EncoderConstant.ENABLE);
					addOrUpdateAdvanceControlProperties(advancedControllableProperties, imageSkipControlProperty);
					break;
				case ACTION:
					stateVideo = mapOfNameAndVideoStatistics.get(videoConfig.getName()).getState();
					value = videoStateDropdown.get(stateVideo);
					if (VideoStateEnum.STOPPED.getName().equals(value)) {
						dropdownAction = VideoActionEnum.getVideoAction(false);
					}
					AdvancedControllableProperty actionDropdownControlProperty = controlDropdownAcceptNoneValue(stats, dropdownAction, videoKeyName, value);
					addOrUpdateAdvanceControlProperties(advancedControllableProperties, actionDropdownControlProperty);
					break;
				case APPLY_CHANGE:
				case CANCEL:
					break;
				default:
					if (logger.isDebugEnabled()) {
						logger.debug(String.format("Controlling video group config %s is not supported.", videoMetric.getName()));
					}
					break;
			}
			stats.put(videoName + EncoderConstant.HASH + EncoderConstant.EDITED, EncoderConstant.FALSE);
		}
	}

	/**
	 * Control Audio property
	 *
	 * @param property the property is the filed name of controlling metric
	 * @param value the value is value of metric
	 * @param extendedStatistics list extendedStatistics
	 * @param advancedControllableProperties the advancedControllableProperties is advancedControllableProperties instance
	 */
	private void controlVideoProperty(String property, String value, Map<String, String> extendedStatistics, List<AdvancedControllableProperty> advancedControllableProperties) {
		// property format: GroupName#PropertyName
		String[] videoProperty = property.split(EncoderConstant.HASH);
		String videoName = videoProperty[0];
		String propertyName = videoProperty[1];
		VideoControllingMetric videoControllingMetric = VideoControllingMetric.getByName(propertyName);
		String videoKeyName = videoName + EncoderConstant.HASH + videoControllingMetric.getName();
		isEmergencyDelivery = true;
		switch (videoControllingMetric) {
			case GOP_SIZE:
				int gopSize = getValueByRange(EncoderConstant.MIN_GOP_SIZE, EncoderConstant.MAX_GOP_SIZE, value);
				updateValueForTheControllableProperty(videoKeyName, String.valueOf(gopSize), extendedStatistics, advancedControllableProperties);
				break;
			case BITRATE:
				int bitrate = getValueByRange(EncoderConstant.MIN_BITRATE, EncoderConstant.MAX_BITRATE, value);
				updateValueForTheControllableProperty(videoKeyName, String.valueOf(bitrate), extendedStatistics, advancedControllableProperties);
				break;
			case RESOLUTION:
				updateValueForTheControllableProperty(videoKeyName, value, extendedStatistics, advancedControllableProperties);
				String cropping = videoName + EncoderConstant.HASH + VideoControllingMetric.CROPPING.getName();
				if (ResolutionEnum.RESOLUTION_AUTOMATIC.getName().equals(value)) {
					extendedStatistics.remove(cropping);
					advancedControllableProperties.removeIf(item -> item.getName().equals(cropping));
				} else {
					String croppingMode = extendedStatistics.get(cropping);
					VideoConfig videoConfig = mapOfNameAndVideoConfig.get(videoName);
					if (croppingMode == null) {
						handleControlCroppingMetric(extendedStatistics, advancedControllableProperties, cropping, videoConfig);
					}
				}
				break;
			case CROPPING:
				updateValueForTheControllableProperty(videoKeyName, value, extendedStatistics, advancedControllableProperties);

				//update cropping
				String crop = EncoderConstant.ZERO == Integer.parseInt(value) ? EncoderConstant.SCALE : EncoderConstant.CROP;
				VideoConfig videoConfig = mapOfNameAndVideoConfig.get(videoName);
				videoConfig.setCropping(crop);
				break;
			case INTRA_REFRESH:
				updateValueForTheControllableProperty(videoKeyName, value, extendedStatistics, advancedControllableProperties);
				String intraRefreshRateName = videoName + EncoderConstant.HASH + VideoControllingMetric.INTRA_REFRESH_RATE.getName();
				if (EncoderConstant.ZERO == Integer.parseInt(value)) {
					extendedStatistics.put(intraRefreshRateName, EncoderConstant.EMPTY_STRING);
					advancedControllableProperties.removeIf(item -> item.getName().equals(intraRefreshRateName));
				} else {
					videoConfig = mapOfNameAndVideoConfig.get(videoName);
					String intraRefreshRateValue = videoConfig.getIntraRefreshRate();
					if (StringUtils.isNullOrEmpty(intraRefreshRateValue)) {
						intraRefreshRateValue = String.valueOf(EncoderConstant.DEFAULT_REFRESH_RATE);
					}
					AdvancedControllableProperty refreshRateControlProperty = controlTextOrNumeric(extendedStatistics, intraRefreshRateName, intraRefreshRateValue, true);
					addOrUpdateAdvanceControlProperties(advancedControllableProperties, refreshRateControlProperty);
				}
				break;
			case INTRA_REFRESH_RATE:
				int intraRefreshRate = getValueByRange(EncoderConstant.MIN_REFRESH_RATE, EncoderConstant.MAX_REFRESH_RATE, value);
				updateValueForTheControllableProperty(videoKeyName, String.valueOf(intraRefreshRate), extendedStatistics, advancedControllableProperties);

				//update intra refresh rate
				videoConfig = mapOfNameAndVideoConfig.get(videoName);
				videoConfig.setIntraRefreshRate(String.valueOf(intraRefreshRate));
				break;
			//dropdown control
			case INPUT:
			case FRAME_RATE:
			case FRAMING:
			case ASPECT_RATIO:
			case TIME_CODE_SOURCE:
			case ENTROPY_CODING:
			case ACTION:
				//switch control
			case CLOSED_CAPTION:
			case PARTITIONING:
			case PARTIAL_IMAGE_SKIP:
				updateValueForTheControllableProperty(videoKeyName, value, extendedStatistics, advancedControllableProperties);
				break;
			case APPLY_CHANGE:
				VideoConfig videoConfigData = convertVideoByValue(extendedStatistics, videoName);

				// sent request to apply all change for all metric
				sendCommandToSaveAllVideoProperties(videoConfigData);

				//sent request to action for the metric
				sendCommandToSetVideoAction(videoConfigData);
				isEmergencyDelivery = false;
				break;
			case CANCEL:
				isEmergencyDelivery = false;
				break;
			default:
				if (logger.isDebugEnabled()) {
					logger.debug(String.format("Controlling video group config %s is not supported.", videoControllingMetric.getName()));
				}
				break;
		}
		if (isEmergencyDelivery) {
			propertyName = videoName;
			extendedStatistics.put(propertyName + EncoderConstant.HASH + VideoControllingMetric.APPLY_CHANGE.getName(), EncoderConstant.EMPTY_STRING);
			advancedControllableProperties.add(createButton(propertyName + EncoderConstant.HASH + VideoControllingMetric.APPLY_CHANGE.getName(), EncoderConstant.APPLY, EncoderConstant.APPLYING, 0));

			extendedStatistics.put(propertyName + EncoderConstant.HASH + EncoderConstant.EDITED, EncoderConstant.TRUE);
			extendedStatistics.put(propertyName + EncoderConstant.HASH + EncoderConstant.CANCEL, EncoderConstant.EMPTY_STRING);
			advancedControllableProperties.add(createButton(propertyName + EncoderConstant.HASH + EncoderConstant.CANCEL, EncoderConstant.CANCEL, EncoderConstant.CANCELING, 0));
		}
	}

	/**
	 * Handle add control for cropping metric
	 *
	 * @param extendedStatistics list statistics property
	 * @param advancedControllableProperties the advancedControllableProperties is list AdvancedControllableProperties
	 * @param cropping the cropping is property with format is GroupName#Cropping
	 * @param videoConfig is instance in VideoConfig DTO
	 */
	private void handleControlCroppingMetric(Map<String, String> extendedStatistics, List<AdvancedControllableProperty> advancedControllableProperties, String cropping, VideoConfig videoConfig) {
		String croppingMode;
		croppingMode = videoConfig.getCropping();
		int croppingValue = EncoderConstant.ZERO;
		if (EncoderConstant.CROP.equals(croppingMode)) {
			croppingValue = EncoderConstant.NUMBER_ONE;
		}
		AdvancedControllableProperty croppingControlProperty = controlSwitch(extendedStatistics, cropping, String.valueOf(croppingValue), EncoderConstant.DISABLE, EncoderConstant.ENABLE);
		addOrUpdateAdvanceControlProperties(advancedControllableProperties, croppingControlProperty);
	}

	/**
	 * Send command to set video action
	 *
	 * @param videoConfigData is instance VideoConfig DTO
	 * @throws CommandFailureException if set action video config failed
	 */
	private void sendCommandToSetVideoAction(VideoConfig videoConfigData) {
		String videoId = videoConfigData.getId();
		String action = videoConfigData.getAction();
		String request = EncoderCommand.OPERATION_VIDENC.getName() + videoId + EncoderConstant.SPACE + action;
		if (!EncoderConstant.NONE.equals(action)) {
			try {
				String responseData = send(request);
				if (!responseData.contains(EncoderConstant.SUCCESS_RESPONSE)) {
					throw new ResourceNotReachableException(String.format("Change video %s failed", videoConfigData.getAction()));
				}
			} catch (Exception e) {
				logger.error(String.format(EncoderConstant.COMMAND_FAILED_FORMAT, this.host, request, e));
				throw new ResourceNotReachableException("Error while setting action video config: " + e.getMessage(), e);
			}
		}
	}

	/**
	 * Send command to set video action
	 *
	 * @param videoConfigData is instance VideoConfig DTO
	 * @throws ResourceNotReachableException if set action video config failed
	 */
	private void sendCommandToSaveAllVideoProperties(VideoConfig videoConfigData) {
		String data = videoConfigData.toString();
		String videoId = videoConfigData.getId();
		String request = EncoderCommand.OPERATION_VIDENC.getName() + videoId + EncoderConstant.SPACE + EncoderCommand.SET + data;
		try {
			String responseData = send(request);
			if (!responseData.contains(EncoderConstant.SUCCESS_RESPONSE)) {
				throw new CommandFailureException(this.host, request, responseData);
			}
		} catch (Exception e) {
			throw new CommandFailureException(this.host, request, "Error while setting video config: " + e.getMessage(), e);
		}
	}

	/**
	 * Convert videoConfig by value
	 *
	 * @param stats list of stats
	 * @param videoName the videoName is name of video
	 * @return videoConfig is instance in VideoConfig
	 */
	private VideoConfig convertVideoByValue(Map<String, String> stats, String videoName) {
		VideoConfig videoConfig = new VideoConfig();

		String propertyName = videoName + EncoderConstant.HASH;
		String id = mapOfNameAndVideoConfig.get(videoName).getId();
		String bitrate = stats.get(propertyName + VideoControllingMetric.BITRATE.getName());
		String action = stats.get(propertyName + VideoControllingMetric.ACTION.getName());
		String gopSize = stats.get(propertyName + VideoControllingMetric.GOP_SIZE.getName());
		String timeCode = stats.get(propertyName + VideoControllingMetric.TIME_CODE_SOURCE.getName());
		String aspectRatio = stats.get(propertyName + VideoControllingMetric.ASPECT_RATIO.getName());
		String resolution = stats.get(propertyName + VideoControllingMetric.RESOLUTION.getName());
		String framing = stats.get(propertyName + VideoControllingMetric.FRAMING.getName());
		String frameRate = stats.get(propertyName + VideoControllingMetric.FRAME_RATE.getName());
		String intraRefreshRate = stats.get(propertyName + VideoControllingMetric.INTRA_REFRESH_RATE.getName());
		String entropyCoding = stats.get(propertyName + VideoControllingMetric.ENTROPY_CODING.getName());
		String cropping = stats.get(propertyName + VideoControllingMetric.CROPPING.getName());
		String intraRefresh = convertByState(stats.get(propertyName + VideoControllingMetric.INTRA_REFRESH.getName()), false);
		String closedCaption = convertByState(stats.get(propertyName + VideoControllingMetric.CLOSED_CAPTION.getName()), false);
		String inputInterface = stats.get(propertyName + VideoControllingMetric.INPUT.getName()).replace(EncoderConstant.DASH, "");
		String picturePartitioning = convertByState(stats.get(propertyName + VideoControllingMetric.PARTITIONING.getName()), false);
		String partialFrameSkip = convertByState(stats.get(propertyName + VideoControllingMetric.PARTIAL_IMAGE_SKIP.getName()), false);

		if (ResolutionEnum.RESOLUTION_AUTOMATIC.getName().equals(resolution)) {
			resolution = EncoderConstant.AUTO;
		}
		if (ResolutionEnum.RESOLUTION_352_288.getName().equals(resolution) || ResolutionEnum.RESOLUTION_720_576.getName().equals(resolution) || ResolutionEnum.RESOLUTION_720_480.getName()
				.equals(resolution)) {
			resolution = String.format("%sp", resolution);
		}
		if (FrameRateEnum.FAME_RATE_0.getName().equals(frameRate)) {
			frameRate = EncoderConstant.AUTO;
		}
		if (cropping != null) {
			String croppingValue = EncoderConstant.SCALE;
			if (EncoderConstant.NUMBER_ONE == Integer.parseInt(cropping)) {
				croppingValue = EncoderConstant.CROP;
			}
			cropping = croppingValue;
		}
		videoConfig.setId(id);
		videoConfig.setAction(action);
		videoConfig.setBitrate(bitrate);
		videoConfig.setGopSize(gopSize);
		videoConfig.setTimeCode(timeCode);
		videoConfig.setAspectRatio(aspectRatio);
		videoConfig.setResolution(resolution);
		videoConfig.setFraming(framing);
		videoConfig.setFrameRate(frameRate);
		videoConfig.setIntraRefreshRate(intraRefreshRate);
		videoConfig.setEntropyCoding(entropyCoding);
		videoConfig.setCropping(cropping);
		videoConfig.setIntraRefresh(intraRefresh);
		videoConfig.setClosedCaption(closedCaption);
		videoConfig.setInputInterface(inputInterface);
		videoConfig.setPicturePartitioning(picturePartitioning);
		videoConfig.setPartialFrameSkip(partialFrameSkip);

		return videoConfig;
	}

	/**
	 * Change Off/On to 0/1 of value and vice versa.
	 * -if value is Off/On return 0/1
	 * -if value is 0/1 return Off/On
	 * -If value is null, return an empty string.
	 *
	 * @param value the value is value to be converted
	 * @param state the state is boolean value if state is true => convert to 0/1. if state is false => convert to Off/On
	 * @return String is On/Off value if state is true or if state is false will be 0/1
	 */
	private String convertByState(String value, boolean state) {
		String defaultValue = EncoderConstant.EMPTY_STRING;
		if (!StringUtils.isNullOrEmpty(value)) {
			if (state) {
				defaultValue = String.valueOf(EncoderConstant.ZERO);
				if (EncoderConstant.ON.equals(value)) {
					defaultValue = String.valueOf(EncoderConstant.NUMBER_ONE);
				}
			} else {
				defaultValue = EncoderConstant.OFF;
				if (EncoderConstant.NUMBER_ONE == Integer.parseInt(value)) {
					defaultValue = EncoderConstant.ON;
				}
			}
		}
		return defaultValue;
	}

	/**
	 * Get value by range if the value out of range return the initial value
	 *
	 * @param min is the minimum value
	 * @param max is the maximum value
	 * @param value is the value to compare between min and max value
	 * @return int is value or initial value
	 */
	private int getValueByRange(int min, int max, String value) {
		int initial = min;
		try {
			int valueCompare = Integer.parseInt(value);
			if (min <= valueCompare && valueCompare <= max) {
				return valueCompare;
			}
			if (valueCompare > max) {
				initial = max;
			}
			return initial;
		} catch (Exception e) {
			//example value  1xxxxxxx, return max value
			//example value -1xxxxxxx, return min value
			if (!value.contains(EncoderConstant.DASH)) {
				initial = max;
			}
			return initial;
		}
	}

//--------------------------------------------------------------------------------------------------------------------------------
// create Stream

	/**
	 * Create output stream
	 *
	 * @param stats is list statistics
	 * @param advancedControllableProperties is list advancedControllableProperties
	 */
	private void createOutputStream(Map<String, String> stats, List<AdvancedControllableProperty> advancedControllableProperties) {
		String streamKey = EncoderConstant.CREATE_STREAM + EncoderConstant.HASH;
		String[] videoNames = mapOfNameAndVideoConfig.keySet().toArray(new String[mapOfNameAndVideoConfig.size()]);
		String[] protocolDropdown = EnumTypeHandler.getEnumNames(ProtocolEnum.class);
		String[] fecDropdown = FecEnum.getArrayOfNameByUDPMode();
		String[] stillImageDropdown = stillImageList.toArray(new String[stillImageList.size()]);

		String contentName = streamKey + StreamControllingMetric.NAME.getName();
		String sourceVideoName = streamKey + StreamControllingMetric.SOURCE_VIDEO.getName();
		String protocolName = streamKey + StreamControllingMetric.STREAMING_PROTOCOL.getName();
		String addressName = streamKey + StreamControllingMetric.STREAMING_DESTINATION_ADDRESS.getName();
		String portName = streamKey + StreamControllingMetric.STREAMING_DESTINATION_PORT.getName();
		String fecName = streamKey + StreamControllingMetric.PARAMETER_FEC.getName();
		String trafficShapingName = streamKey + StreamControllingMetric.PARAMETER_TRAFFIC_SHAPING.getName();
		String mtuName = streamKey + StreamControllingMetric.PARAMETER_MTU.getName();
		String ttlName = streamKey + StreamControllingMetric.PARAMETER_TTL.getName();
		String tosName = streamKey + StreamControllingMetric.PARAMETER_TOS.getName();
		String transmitSAPName = streamKey + StreamControllingMetric.SAP_TRANSMIT.getName();
		String stillImageName = streamKey + StreamControllingMetric.STILL_IMAGE.getName();
		String vfEncryption = streamKey + StreamControllingMetric.PARAMETER_VF_ENCRYPTION.getName();

		//add stream name
		advancedControllableProperties.add(controlTextOrNumeric(stats, contentName, EncoderConstant.EMPTY_STRING, false));
		//add source video
		advancedControllableProperties.add(controlDropdown(stats, videoNames, sourceVideoName, videoNames[0]));
		//add audio source. Initialize source audio list
		for (int index = 0; index < EncoderConstant.MAX_SOURCE_AUDIO_DROPDOWN; index++) {
			mapOfNameAndSourceAudio.put(StreamControllingMetric.SOURCE_AUDIO.getName() + EncoderConstant.SPACE + index, null);
		}
		addSourceAudioForOutputStream(stats, advancedControllableProperties);
		// add Streaming Protocol default Ts over UDP
		advancedControllableProperties.add(controlDropdown(stats, protocolDropdown, protocolName, ProtocolEnum.TS_UDP.getValue()));
		//add Streaming Destination Address
		advancedControllableProperties.add(controlTextOrNumeric(stats, addressName, EncoderConstant.EMPTY_STRING, false));
		//add Streaming Destination Port
		advancedControllableProperties.add(controlTextOrNumeric(stats, portName, EncoderConstant.EMPTY_STRING, false));
		//add FEC
		advancedControllableProperties.add(controlDropdownAcceptNoneValue(stats, fecDropdown, fecName, EncoderConstant.NONE));
		//add Traffic Shaping
		advancedControllableProperties.add(controlSwitch(stats, trafficShapingName, String.valueOf(EncoderConstant.ZERO), EncoderConstant.DISABLE, EncoderConstant.ENABLE));
		//add MTU and TTL
		advancedControllableProperties.add(controlTextOrNumeric(stats, mtuName, String.valueOf(EncoderConstant.DEFAULT_MTU), true));
		advancedControllableProperties.add(controlTextOrNumeric(stats, ttlName, String.valueOf(EncoderConstant.DEFAULT_TTL), true));
		//add TOS
		advancedControllableProperties.add(controlTextOrNumeric(stats, tosName, String.valueOf(EncoderConstant.DEFAULT_TOS), false));
		// add monitoring VF Encryption
		stats.put(vfEncryption, EncoderConstant.OFF);
		//add Transmit SAP
		advancedControllableProperties.add(controlSwitch(stats, transmitSAPName, String.valueOf(EncoderConstant.ZERO), EncoderConstant.DISABLE, EncoderConstant.ENABLE));
		//add still image
		advancedControllableProperties.add(controlDropdownAcceptNoneValue(stats, stillImageDropdown, stillImageName, EncoderConstant.NONE));
	}

	/**
	 * Add new Source Audio for output stream
	 *
	 * @param stats is list of stats
	 * @param advancedControllablePropertyList is list AdvancedControllableProperty instance
	 * @throws ResourceNotReachableException if assign more than 7 source audio
	 */
	private void addSourceAudioForOutputStream(Map<String, String> stats, List<AdvancedControllableProperty> advancedControllablePropertyList) {
		mapOfNameAndAudioConfig.put(EncoderConstant.NONE, new AudioConfig());
		String prefixName = EncoderConstant.CREATE_STREAM + EncoderConstant.HASH;
		String[] audioNames = mapOfNameAndAudioConfig.keySet().toArray(new String[mapOfNameAndAudioConfig.size()]);
		Arrays.sort(audioNames);
		Map<Integer, String> audioIdList = new HashMap<>();
		int countSource = 0;

		//Check list source audio assigned in stream
		for (Map.Entry<String, Audio> audioKey : mapOfNameAndSourceAudio.entrySet()) {
			if (audioKey.getValue() != null && audioKey.getValue().getAudioId() != null) {
				audioIdList.put(Integer.valueOf(audioKey.getValue().getAudioId()), audioKey.getValue().getAudioName());
				countSource++;
			}
		}
		//Maximum of source audio is 8,
		if (countSource == EncoderConstant.MAX_SOURCE_AUDIO_DROPDOWN) {
			throw new ResourceNotReachableException("The audio source just assign max audio source is 8.");
		}
		for (Map.Entry<String, Audio> audioEntry : mapOfNameAndSourceAudio.entrySet()) {
			String defaultName = audioNames[0];
			for (int i = 0; i < EncoderConstant.MAX_SOURCE_AUDIO_DROPDOWN; i++) {
				if (!audioIdList.containsKey(i)) {
					defaultName = mapOfIdAndNameAudioConfig.get(String.valueOf(i));
					break;
				}
			}
			if (audioEntry.getValue() == null) {
				advancedControllablePropertyList.add(controlDropdown(stats, audioNames, prefixName + audioEntry.getKey(), defaultName));
				Audio audio = new Audio();
				audio.setAudioId(mapOfNameAndAudioConfig.get(defaultName).getId());
				audioEntry.setValue(audio);
				break;
			}
		}
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
	 * Add/Update advancedControllableProperties if  advancedControllableProperties different empty
	 *
	 * @param advancedControllableProperties advancedControllableProperties is the list that store all controllable properties
	 * @param property the property is item advancedControllableProperties
	 */
	private void addOrUpdateAdvanceControlProperties(List<AdvancedControllableProperty> advancedControllableProperties, AdvancedControllableProperty property) {
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
		// if response data is null or none. Only display monitoring data not display controlling data
		return null;
	}

	/**
	 * Add text or numberic is control property for metric
	 *
	 * @param stats list statistic
	 * @param name String name of metric
	 * @return AdvancedControllableProperty text and numeric instance
	 */
	private AdvancedControllableProperty controlTextOrNumeric(Map<String, String> stats, String name, String value, boolean isNumeric) {
		stats.put(name, value);

		return isNumeric ? createNumeric(name, value) : createText(name, value);
	}

	/**
	 * Add switch is control property for metric
	 *
	 * @param stats list statistic
	 * @param name String name of metric
	 * @return AdvancedControllableProperty switch instance
	 */
	private AdvancedControllableProperty controlSwitch(Map<String, String> stats, String name, String value, String labelOff, String labelOn) {
		stats.put(name, value);
		if (!EncoderConstant.NONE.equals(value)) {
			return createSwitch(name, Integer.parseInt(value), labelOff, labelOn);
		}
		return null;
	}

	/**
	 * Create switch is control property for metric
	 *
	 * @param name the name of property
	 * @param status initial status (0|1)
	 * @return AdvancedControllableProperty switch instance
	 */
	private AdvancedControllableProperty createSwitch(String name, int status, String labelOff, String labelOn) {
		AdvancedControllableProperty.Switch toggle = new AdvancedControllableProperty.Switch();
		toggle.setLabelOff(labelOff);
		toggle.setLabelOn(labelOn);

		AdvancedControllableProperty advancedControllableProperty = new AdvancedControllableProperty();
		advancedControllableProperty.setName(name);
		advancedControllableProperty.setValue(status);
		advancedControllableProperty.setType(toggle);
		advancedControllableProperty.setTimestamp(new Date());

		return advancedControllableProperty;
	}

	/**
	 * Create text is control property for metric
	 *
	 * @param name the name of the property
	 * @param stringValue character string
	 * @return AdvancedControllableProperty Text instance
	 */
	private AdvancedControllableProperty createText(String name, String stringValue) {
		AdvancedControllableProperty.Text text = new AdvancedControllableProperty.Text();

		return new AdvancedControllableProperty(name, new Date(), text, stringValue);
	}

	/**
	 * Create Numeric is control property for metric
	 *
	 * @param name the name of the property
	 * @param initialValue the initialValue is number
	 * @return AdvancedControllableProperty Numeric instance
	 */
	private AdvancedControllableProperty createNumeric(String name, String initialValue) {
		AdvancedControllableProperty.Numeric numeric = new AdvancedControllableProperty.Numeric();

		return new AdvancedControllableProperty(name, new Date(), numeric, initialValue);
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
