/*
 * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
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
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dropdownlist.ConnectionModeEnum;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dropdownlist.EncryptionEnum;
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
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dropdownlist.SourceType;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dropdownlist.StreamActionEnum;
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
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dto.stream.StreamSAP;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dto.stream.StreamStatistics;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dto.video.VideoConfig;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dto.video.VideoStatistics;
import com.avispl.symphony.dal.communicator.SshCommunicator;
import com.avispl.symphony.dal.util.StringUtils;

/**
 * /**
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

	private boolean isOperatorOrAdministratorRole;
	private boolean isConfigManagement;
	private boolean isEmergencyDelivery;
	private boolean isCreateStreamCalled;
	private Integer noOfMonitoringMetric = 0;
	private ExtendedStatistics localExtendedStatistics;
	private ExtendedStatistics localCreateOutputStream;
	private final ObjectMapper objectMapper = new ObjectMapper();
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
	 * List of Services and its statuses
	 */
	private final Map<String, Integer> serviceMap = new HashMap<>();

	/**
	 * Map of talkback
	 */
	private final Map<String, String> talkbackMap = new HashMap<>();

	/**
	 * List of StillImage
	 */
	private final List<String> stillImageList = new ArrayList<>();

	/**
	 * Map of Audio statistics with key name of AudioStatistics, value is AudioStatistics
	 */
	private final Map<String, AudioStatistics> nameAndAudioStatisticsMap = new HashMap<>();

	/**
	 * Map of Video statistics with key name of VideoStatistics, value is VideoStatistics
	 */
	private final Map<String, VideoStatistics> nameAndVideoStatisticsMap = new HashMap<>();

	/**
	 * Map of Audio Config with key is the name of AudioConfig, value is AudioConfig
	 */
	private final Map<String, AudioConfig> nameAndAudioConfigMap = new HashMap<>();

	/**
	 * Map of Video Config with key is the name of VideoConfig, value is VideoConfig
	 */
	private final Map<String, VideoConfig> nameAndVideoConfigMap = new HashMap<>();

	/**
	 * Map of Input Response with key is the name of InputResponse, value is InputResponse
	 */
	private final Map<String, InputResponse> nameAndInputResponseMap = new HashMap<>();

	/**
	 * Map of Stream SAP with key is the id of Stream SAP, value is Stream SAP
	 */
	private final Map<String, StreamSAP> idAndStreamSAPMap = new HashMap<>();

	/**
	 * Map of Audio with key is the name AudioConfig, value is Audio
	 */
	private final Map<String, Audio> nameAndSourceAudioMap = new HashMap<>();

	/**
	 * Map of id and Name with key is the id of AudioConfig, value is name of AudioConfig
	 */
	private final Map<String, String> idAndNameAudioConfigMap = new HashMap<>();

	/**
	 * Map of StreamStatistics with key is the id of StreamStatistics, value is StreamStatistics
	 */
	private final Map<String, StreamStatistics> idAndStreamStatisticsMap = new HashMap<>();

	/**
	 * Map of name and id with key is the name of StreamConfig, value is id of StreamConfig
	 */
	Map<String, String> nameAndIdForStreamConfigMap = new HashMap<>();

	/**
	 * HashSet of output statistics for the adapter filter
	 */
	private final Set<StreamConfig> streamConfigPortAndStatusSet = new HashSet<>();

	/**
	 * HashSet of output statistics for the adapter filter
	 */
	private final Set<StreamConfig> streamConfigNameFilterSet = new HashSet<>();

	/**
	 * List port extracted from port filter
	 */
	private final List<Integer> portNumberList = new ArrayList<>();

	/**
	 * List port range extracted from filter
	 */
	private final List<String> portNumberRangeList = new ArrayList<>();

	/**
	 * ReentrantLock to prevent null pointer exception to localExtendedStatistics when controlProperty method is called before GetMultipleStatistics method.
	 */
	private final ReentrantLock reentrantLock = new ReentrantLock();

	/**
	 * Prevent case where {@link HaivisionXEncoderCommunicator#controlProperty(ControllableProperty)} slow down -
	 * the getMultipleStatistics interval if it's fail to send the cmd
	 */
	private static final int controlSSHTimeout = 3000;

	/**
	 * Set back to default timeout value in {@link SshCommunicator}
	 */
	private final int statisticsSSHTimeout = 30000;

	/**
	 * HaivisionXEncoderCommunicator constructor
	 */
	public HaivisionXEncoderCommunicator() {
		this.setCommandErrorList(Collections.singletonList("~"));
		this.setCommandSuccessList(Arrays.asList("(y,N): ", "~$ "));
		this.setLoginSuccessList(Collections.singletonList("~$ "));
	}

	@Override
	protected void internalDestroy() {
		isEmergencyDelivery = false;
		if (localExtendedStatistics != null && localExtendedStatistics.getStatistics() != null && localExtendedStatistics.getControllableProperties() != null) {
			localExtendedStatistics.getStatistics().clear();
			localExtendedStatistics.getControllableProperties().clear();
		}
		//Filter
		streamConfigPortAndStatusSet.clear();
		streamConfigNameFilterSet.clear();
		portNumberList.clear();
		portNumberRangeList.clear();
		streamConfigNameFilterSet.clear();
		super.internalDestroy();
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
			Map<String, String> stats = new HashMap<>();
			Map<String, String> statsCreateOutputStream = new HashMap<>();
			ExtendedStatistics extendedStatistics = new ExtendedStatistics();
			List<AdvancedControllableProperty> advancedControllableProperties = new ArrayList<>();
			List<AdvancedControllableProperty> createStreamAdvancedControllable = new ArrayList<>();
			failedMonitor.clear();

			if (!isEmergencyDelivery) {
				isOperatorOrAdministratorRole = isOperatorOrAdministratorRole(retrieveUserRole());
				isConfigManagement = isConfigManagementProperties();
				populateInformationFromDevice(stats, advancedControllableProperties);
				if (isOperatorOrAdministratorRole && isConfigManagement) {
					extendedStatistics.setControllableProperties(advancedControllableProperties);
				}
				extendedStatistics.setStatistics(stats);
				localExtendedStatistics = extendedStatistics;
			}

			if (isOperatorOrAdministratorRole && isConfigManagement) {
				// Populate default create stream and control - this only be called once per monitoring cycle.
				if (!isCreateStreamCalled) {
					localCreateOutputStream = new ExtendedStatistics();
					createOutputStream(statsCreateOutputStream, createStreamAdvancedControllable);
					localCreateOutputStream.setStatistics(statsCreateOutputStream);
					localCreateOutputStream.setControllableProperties(createStreamAdvancedControllable);
					localStatsStreamOutput.putAll(statsCreateOutputStream);
					isCreateStreamCalled = true;
				}
				updateDropdownListForTheControllingMetric();
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
			}
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
			this.timeout = controlSSHTimeout;
			if (localExtendedStatistics == null || localCreateOutputStream == null) {
				if (logger.isDebugEnabled()) {
					logger.debug(String.format("Error while controlling %s metric", property));
				}
				return;
			}
			Map<String, String> extendedStatistics = localExtendedStatistics.getStatistics();
			Map<String, String> updateCreateOutputStream = localCreateOutputStream.getStatistics();
			List<AdvancedControllableProperty> advancedControllableProperties = localExtendedStatistics.getControllableProperties();
			List<AdvancedControllableProperty> advancedControllableCreateOutputProperties = localCreateOutputStream.getControllableProperties();
			String[] streamProperty = property.split(EncoderConstant.HASH);
			String propertyName = streamProperty[0];
			String propertyValue = streamProperty[1];
			if (propertyName.contains(EncoderConstant.CREATE_STREAM)) {
				controlCreateStreamProperty(property, value, updateCreateOutputStream, advancedControllableCreateOutputProperties);
				if (!updateCreateOutputStream.isEmpty()) {
					localStatsStreamOutput.putAll(updateCreateOutputStream);
				}
			} else if (EncoderMonitoringMetric.SERVICE.getName().equals(propertyName)) {
				controlServiceProperty(property, propertyValue, value);
			} else {
				//Example Audio property Audio Encoder 0
				String propertiesAudio = property.substring(0, EncoderConstant.AUDIO.length());
				//Example Stream name Stream Encoder 0
				String propertiesStream = property.substring(0, EncoderConstant.STREAM.length());
				if (EncoderConstant.AUDIO.equals(propertiesAudio)) {
					controlAudioProperty(property, value, extendedStatistics, advancedControllableProperties);
				} else if (EncoderConstant.STREAM.equals(propertiesStream)) {
					controlStreamProperty(property, value, extendedStatistics, advancedControllableProperties);
				} else {
					controlVideoProperty(property, value, extendedStatistics, advancedControllableProperties);
				}
			}
		} finally {
			this.timeout = statisticsSSHTimeout;
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
	 * Check roles based on user as operator or administrator
	 *
	 * @param roleBased the role is String
	 * @return boolean type, returns true if roleBased is operator or administrator and vice versa
	 */
	private boolean isOperatorOrAdministratorRole(String roleBased) {
		return EncoderConstant.OPERATOR.equals(roleBased) || EncoderConstant.ADMIN.equals(roleBased);
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
		// retrieve list of services and its statues
		retrieveServiceList();
		// retrieve talkback udp port
		retrieveTalkbackUdpPort();
		for (EncoderMonitoringMetric encoderMonitoringMetric : EncoderMonitoringMetric.values()) {
			if (EncoderMonitoringMetric.ACCOUNT.equals(encoderMonitoringMetric) || EncoderMonitoringMetric.STILL_IMAGE.equals(encoderMonitoringMetric)
					|| EncoderMonitoringMetric.SERVICE.equals(encoderMonitoringMetric) || EncoderMonitoringMetric.TALKBACK.equals(encoderMonitoringMetric)) {
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
	 * Retrieve list of services name and its status
	 */
	private void retrieveServiceList() {
		try {
			String response = send(EncoderUtil.getMonitorCommand(EncoderMonitoringMetric.SERVICE));
			if (response != null) {
				// Example response format: service all status \r\nems service is currently disabled
				// \r\nems service is disabled at system startup\r\nhttp service is currently enabled ...
				String[] serviceList = response.split(EncoderConstant.REGEX_DATA);
				for (String rawService : serviceList) {
					// Only take string that contains 'currently' not 'at system startup'
					// Not support Onvif service.
					if (rawService.contains(EncoderConstant.CURRENTLY) && !rawService.contains(EncoderConstant.ONVIF) && !rawService.contains(EncoderConstant.PASS_THROUGH)) {
						// Example rawService format: 'ems service is currently disabled' => Take 'ems' as serviceName, 'disabled' as its status.
						String[] arrayOfRawServices = rawService.split(EncoderConstant.SPACE);
						String serviceName = arrayOfRawServices[0].trim();
						String serviceStatus = arrayOfRawServices[arrayOfRawServices.length - 1].trim().toLowerCase();
						int serviceStatusInInteger = EncoderConstant.ENABLED.equals(serviceStatus) ? 1 : 0;
						serviceMap.put(serviceName, serviceStatusInInteger);
					}
				}
			}
		} catch (Exception e) {
			failedMonitor.put(EncoderMonitoringMetric.SERVICE.getName(), e.getMessage());
		}
	}

	/**
	 * Retrieve talkback udp port encoder
	 */
	private void retrieveTalkbackUdpPort() {
		try {
			String request = String.valueOf(EncoderUtil.getMonitorCommand(EncoderMonitoringMetric.TALKBACK));
			String responseData = send(request);
			String udpPort = EncoderConstant.EMPTY_STRING;
			String state = EncoderConstant.EMPTY_STRING;
			if (responseData != null) {
				String[] arrayOfRawResponseData = responseData.split(EncoderConstant.REGEX_DATA);
				for (String rawResponse : arrayOfRawResponseData) {
					if (rawResponse.contains(EncoderConstant.UDP_PORT)) {
						// Example format of rawResponse: '  UDP Port\t\t: 9178'
						String[] arrayOfRawUdpPort = rawResponse.split("\t\t:");
						udpPort = arrayOfRawUdpPort[arrayOfRawUdpPort.length - 1].trim();
					} else if (rawResponse.contains(EncoderConstant.STATE)) {
						String[] arrayOfRawState = rawResponse.split("\t\t\t:");
						// DISABLED/LISTENING
						state = arrayOfRawState[arrayOfRawState.length - 1].trim();
					}
					if (!StringUtils.isNullOrEmpty(udpPort) && !StringUtils.isNullOrEmpty(state)) {
						break;
					}
				}
				// check if udpPort string is a valid number
				if (udpPort.matches(EncoderConstant.REGEX_CHECK_STRING_IS_NUMBER)) {
					talkbackMap.put(EncoderConstant.PORT, udpPort);
				}
				talkbackMap.put(EncoderConstant.LISTENING_STATE, state);
				if (talkbackMap.size() != 2) {
					throw new ResourceNotReachableException("Fail to get talkback");
				}
			}
		} catch (Exception e) {
			failedMonitor.put(EncoderMonitoringMetric.TALKBACK.getName(), e.getMessage());
		}
	}

	/**
	 * Clear data before fetching data
	 */
	private void clearBeforeFetchingData() {
		//audio
		audioStatisticsList.clear();
		audioConfigList.clear();
		nameAndAudioConfigMap.clear();
		nameAndAudioStatisticsMap.clear();
		idAndNameAudioConfigMap.clear();

		//video
		videoStatisticsList.clear();
		videoConfigList.clear();
		nameAndVideoConfigMap.clear();
		nameAndVideoStatisticsMap.clear();

		//input
		nameAndInputResponseMap.clear();

		//stream
		streamConfigList.clear();
		streamStatisticsList.clear();
		idAndStreamStatisticsMap.clear();
		nameAndIdForStreamConfigMap.clear();
		idAndStreamSAPMap.clear();

		//session SAP
		idAndStreamSAPMap.clear();

		//Still Image
		stillImageList.clear();

		// Service Map
		serviceMap.clear();

		// Talkback
		talkbackMap.clear();
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
			case STREAM_CONFIG:
				populateConfigData(stats, advancedControllableProperties, encoderMonitoringMetric);
				break;
			case TEMPERATURE:
			case ACCOUNT:
			case SYSTEM_INFORMATION:
			case ROLE_BASED:
				break;
			case SERVICE:
				populateServiceConfigData(stats, advancedControllableProperties);
				break;
			case TALKBACK:
				populateTalkbackConfigData(stats, advancedControllableProperties);
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
		if (isOperatorOrAdministratorRole && isConfigManagement) {
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
				case STREAM_CONFIG:
					for (StreamConfig streamConfig : streamConfigList) {
						addControlStreamConfig(stats, advancedControllableProperties, streamConfig);
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
	 * Convert content name to audio and video source
	 *
	 * @param streamConfig the streamConfig is instance in StreamConfig DTO
	 */
	private void convertSourceAudioList(StreamConfig streamConfig) {
		String[] sourceList = streamConfig.getContents().split(EncoderConstant.COMMA);

		List<Audio> audioList = new ArrayList<>();
		String video = null;
		for (String name : sourceList) {
			//Example format: Video ("HD Video Encoder 0":0), Audio ("Audio Encoder 0":0)
			name = name.replace("\"", EncoderConstant.EMPTY_STRING).replace(")", EncoderConstant.EMPTY_STRING).replace("(", EncoderConstant.EMPTY_STRING);
			String[] listName = name.split(EncoderConstant.COLON);
			if (name.contains(EncoderConstant.AUDIO)) {
				String[] audioId = listName[1].split(EncoderConstant.COLON);
				Audio audio = new Audio();
				audio.setAudioId(audioId[0]);
				audio.setAudioName(idAndNameAudioConfigMap.get(audioId[0]));
				audioList.add(audio);
			}
			if (name.contains(EncoderConstant.VIDEO)) {
				String videoName = listName[0];
				video = videoName.substring(EncoderConstant.VIDEO.length()).trim();
			}
		}
		streamConfig.setAudioList(audioList);
		streamConfig.setVideo(video);
	}

	/**
	 * Add control monitoring data for stream config
	 *
	 * @param stats list statistics property
	 * @param advancedControllableProperties the advancedControllableProperties is list AdvancedControllableProperties
	 * @param streamConfig is instance in StreamConfig DTO
	 */
	private void addControlStreamConfig(Map<String, String> stats, List<AdvancedControllableProperty> advancedControllableProperties, StreamConfig streamConfig) {
		convertSourceAudioList(streamConfig);
		String streamName = streamConfig.getName();
		if (EncoderConstant.NONE_STREAM_NAME.equals(streamName)) {
			streamName = handleStreamNameIsEmpty(streamConfig.getId());
		}
		String streamKey = EncoderConstant.STREAM + EncoderConstant.SPACE + streamName + EncoderConstant.HASH;
		String property;
		for (StreamControllingMetric streamMetric : StreamControllingMetric.values()) {
			property = streamKey + streamMetric.getName();
			switch (streamMetric) {
				case STATE:
					String stateStream = idAndStreamStatisticsMap.get(streamConfig.getId()).getState();
					stats.put(property, stateStream);
					break;
				case ACTION:
					stateStream = idAndStreamStatisticsMap.get(streamConfig.getId()).getState();
					String[] actionDropdown = StreamActionEnum.getAllStreamActionByState(stateStream);
					AdvancedControllableProperty actionProperty = controlDropdownAcceptNoneValue(stats, actionDropdown, property, EncoderConstant.NONE);
					addOrUpdateAdvanceControlProperties(advancedControllableProperties, actionProperty);
					isEmergencyDelivery = false;
					break;
				case NAME:
					String name = streamConfig.getName();
					if (EncoderConstant.NONE_STREAM_NAME.equals(name)) {
						name = EncoderConstant.EMPTY_STRING;
					}
					stats.put(property, name);
					break;
				case SOURCE_VIDEO:
					stats.put(property, streamConfig.getVideo());
					break;
				case SOURCE_AUDIO:
					int i = 0;
					for (Audio audio : streamConfig.getAudioList()) {
						if (audio != null && audio.getAudioId() != null) {
							String sourceAudioName = streamKey + EncoderConstant.SOURCE_AUDIO + String.format(" %s", i);
							stats.put(sourceAudioName, audio.getAudioName());
							i++;
						}
					}
					break;
				case STILL_IMAGE:
					String stillImage = getNoneValueIfPresent(streamConfig.getStillImageFile());
					stats.put(property, stillImage);
					break;
				case PARAMETER_TOS:
					stats.put(property, convertValueByIndexOfSpace(streamConfig.getTos()));
					break;
				case PARAMETER_TTL:
					stats.put(property, streamConfig.getTtl());
					break;
				case STREAMING_PROTOCOL:
					String protocol = ProtocolEnum.getNameOfProtocolEnumByValue(streamConfig.getEncapsulation());
					stats.put(property, protocol);

					if (ProtocolEnum.TS_SRT.getName().equalsIgnoreCase(protocol)) {
						String networkAdapter = streamKey + StreamControllingMetric.STREAM_NETWORK_ADAPTIVE.getName();
						String networkAdaptive = EncoderConstant.DISABLE;
						if (EncoderConstant.ON.equals(streamConfig.getNetworkAdaptive())) {
							networkAdaptive = EncoderConstant.ENABLE;
						}
						stats.put(networkAdapter, networkAdaptive);

						String latency = streamKey + StreamControllingMetric.STREAM_LATENCY.getName();
						stats.put(latency, convertValueByIndexOfSpace(streamConfig.getAddedLatency()));
						String encryption = streamKey + StreamControllingMetric.STREAM_ENCRYPTION.getName();
						String passPhrase = streamKey + StreamControllingMetric.STREAM_PASSPHRASE.getName();
						String encryptionValue = streamConfig.getAesEncryption();
						if (EncoderConstant.OFF.equals(encryptionValue)) {
							encryptionValue = EncoderConstant.NONE;
						} else {
							Map<String, String> encryptionMap = EnumTypeHandler.getMapOfEnumNames(EncryptionEnum.class, false);
							encryptionValue = encryptionMap.get(streamConfig.getKeyLength());
							stats.put(passPhrase, EncoderConstant.PASSPHRASE);
						}
						stats.put(encryption, encryptionValue);
					}
					break;
				case STREAM_CONNECTION_MODE:
					protocol = ProtocolEnum.getNameOfProtocolEnumByValue(streamConfig.getEncapsulation());
					if (ProtocolEnum.TS_SRT.getName().equalsIgnoreCase(protocol)) {
						String connectionMode = streamKey + StreamControllingMetric.STREAM_CONNECTION_MODE.getName();
						String connectionModeValue = streamConfig.getSrtMode();
						stats.put(connectionMode, connectionModeValue);
						String connectionAddress = streamKey + StreamControllingMetric.STREAM_CONNECTION_ADDRESS.getName();
						if (ConnectionModeEnum.CALLER.getName().equals(connectionModeValue) || ConnectionModeEnum.RENDEZVOUS.getName().equals(connectionModeValue)) {
							stats.put(connectionAddress, streamConfig.getAddress());

							String connectionDestinationPort = streamKey + StreamControllingMetric.STREAM_CONNECTION_DESTINATION_PORT.getName();
							stats.put(connectionDestinationPort, streamConfig.getPort());

							String connectionSourcePort = streamKey + StreamControllingMetric.STREAM_CONNECTION_SOURCE_PORT.getName();
							stats.put(connectionSourcePort, streamConfig.getSourcePort());
						}
						if (ConnectionModeEnum.LISTENER.getName().equals(connectionModeValue)) {
							String connectionPort = streamKey + StreamControllingMetric.STREAM_CONNECTION_PORT.getName();
							stats.put(connectionPort, streamConfig.getPort());
						}
					}
					String destinationAddress = streamKey + StreamControllingMetric.STREAMING_DESTINATION_ADDRESS.getName();
					String port = streamKey + StreamControllingMetric.STREAMING_DESTINATION_PORT.getName();
					if (ProtocolEnum.RTMP.getName().equalsIgnoreCase(protocol)) {
						stats.put(destinationAddress, streamConfig.getAddress());
					}
					if (!ProtocolEnum.TS_SRT.getName().equalsIgnoreCase(protocol) && !ProtocolEnum.RTMP.getName().equalsIgnoreCase(protocol)) {
						stats.put(destinationAddress, streamConfig.getAddress());
						stats.put(port, convertValueByIndexOfSpace(streamConfig.getPort()));
					}
					break;
				case PARAMETER_AVERAGE_BANDWIDTH:
					stats.put(property, convertValueByIndexOfSpace(replaceCommaByEmptyString(streamConfig.getAverageBandwidth())));
					break;
				case PARAMETER_TRAFFIC_SHAPING:
					String shaping = EncoderConstant.DISABLE;
					if (EncoderConstant.ON.equals(streamConfig.getShaping())) {
						shaping = EncoderConstant.ENABLE;
					}
					stats.put(property, shaping);
					break;
				case PARAMETER_BANDWIDTH_OVERHEAD:
					protocol = streamConfig.getEncapsulation();
					String bandWidthValue = streamConfig.getBandwidthOverhead() == null ? streamConfig.getMaxTrafficOverhead() : streamConfig.getBandwidthOverhead();
					if (bandWidthValue != null) {
						int len = bandWidthValue.indexOf("%");
						if (len > -1) {
							bandWidthValue = bandWidthValue.substring(0, len);
						}
						if (ProtocolEnum.TS_SRT.getValue().equalsIgnoreCase(protocol)) {
							stats.put(property, bandWidthValue);
						} else {
							String trafficShapingValue = streamConfig.getShaping();
							if (EncoderConstant.ON.equals(trafficShapingValue)) {
								stats.put(property, bandWidthValue);
							}
						}
					}
					break;
				case PARAMETER_MTU:
					protocol = streamConfig.getEncapsulation();
					if (!ProtocolEnum.RTMP.getName().equals(protocol)) {
						stats.put(property, streamConfig.getMtu());
					}
					break;
				case PARAMETER_IDLE_CELLS:
					protocol = streamConfig.getEncapsulation();
					if ((ProtocolEnum.TS_UDP.getValue().equalsIgnoreCase(protocol) || ProtocolEnum.TS_RTP.getValue().equalsIgnoreCase(protocol)
							|| ProtocolEnum.TS_SRT.getValue().equalsIgnoreCase(protocol)) && EncoderConstant.ON.equals(streamConfig.getShaping())) {
						String idleCellsValue = EncoderConstant.DISABLE;
						if (EncoderConstant.ON.equals(streamConfig.getIdleCells())) {
							idleCellsValue = EncoderConstant.ENABLE;
						}
						stats.put(property, idleCellsValue);
					}
					break;
				case PARAMETER_DELAYED_AUDIO:
					protocol = streamConfig.getEncapsulation();
					if ((ProtocolEnum.TS_UDP.getValue().equalsIgnoreCase(protocol) || ProtocolEnum.TS_RTP.getValue().equalsIgnoreCase(protocol)
							|| ProtocolEnum.TS_SRT.getValue().equalsIgnoreCase(protocol)) && EncoderConstant.ON.equals(streamConfig.getShaping())) {
						String delayAudio = EncoderConstant.DISABLE;
						if (EncoderConstant.ON.equals(streamConfig.getDelayAudio())) {
							delayAudio = EncoderConstant.ENABLE;
						}
						stats.put(property, delayAudio);
					}
					break;
				case PARAMETER_FEC:
					protocol = streamConfig.getEncapsulation();
					if (ProtocolEnum.TS_UDP.getValue().equalsIgnoreCase(protocol) || ProtocolEnum.TS_RTP.getValue().equalsIgnoreCase(protocol)) {
						String fec = streamConfig.getFec();
						String fecValue = EncoderConstant.NONE;
						if (!StringUtils.isNullOrEmpty(fec) && fec.contains(FecEnum.VF.getValue())) {
							fecValue = FecEnum.VF.getName();
						}
						stats.put(property, fecValue);
					}
					break;
				case SAP_TRANSMIT:
					protocol = streamConfig.getEncapsulation();
					if (ProtocolEnum.TS_UDP.getValue().equalsIgnoreCase(protocol) || ProtocolEnum.TS_RTP.getValue().equalsIgnoreCase(protocol)) {
						StreamSAP sap = streamConfig.getSap();
						String transmit = EncoderConstant.DISABLE;
						if (sap != null) {
							transmit = sap.getAdvertise();
						}
						if (EncoderConstant.YES.equals(transmit)) {
							transmit = EncoderConstant.ENABLE;
						}
						stats.put(property, transmit);
						if (EncoderConstant.ENABLE.equals(transmit)) {
							stats.put(streamKey + StreamControllingMetric.SAP_NAME.getName(), getNoneValueIfPresent(sap.getName()));
							stats.put(streamKey + StreamControllingMetric.SAP_DESCRIPTION.getName(), getNoneValueIfPresent(sap.getDesc()));
							stats.put(streamKey + StreamControllingMetric.SAP_KEYWORDS.getName(), getNoneValueIfPresent(sap.getKeywords()));
							stats.put(streamKey + StreamControllingMetric.SAP_AUTHOR.getName(), getNoneValueIfPresent(sap.getAuthor()));
							stats.put(streamKey + StreamControllingMetric.SAP_COPYRIGHT.getName(), getNoneValueIfPresent(sap.getCopyright()));
							stats.put(streamKey + StreamControllingMetric.SAP_ADDRESS.getName(), getNoneValueIfPresent(sap.getAddress()));
							stats.put(streamKey + StreamControllingMetric.SAP_PORT.getName(), getNoneValueIfPresent(sap.getPort()));
						}
					}
					break;
				case RTMP_PUBLISH_NAME:
					protocol = streamConfig.getEncapsulation();
					if (ProtocolEnum.RTMP.getValue().equalsIgnoreCase(protocol)) {
						stats.put(property, getEmptyValueForNullData(streamConfig.getPublishName()));
					}
					break;
				case RTMP_USERNAME:
					protocol = streamConfig.getEncapsulation();
					if (ProtocolEnum.RTMP.getValue().equalsIgnoreCase(protocol)) {
						stats.put(property, getEmptyValueForNullData(streamConfig.getUsername()));
					}
					break;
				case RTMP_PASSWORD:
				case RTMP_CONFIRMATION_PASSWORD:
					protocol = streamConfig.getEncapsulation();
					if (ProtocolEnum.RTMP.getValue().equalsIgnoreCase(protocol)) {
						String password = getEmptyValueForNullData(streamConfig.getPassword());
						if (!StringUtils.isNullOrEmpty(password)) {
							password = EncoderConstant.PASSPHRASE;
						}
						stats.put(property, password);
					}
					break;
				case CANCEL:
				case APPLY_CHANGE:
					break;
				default:
					if (logger.isDebugEnabled()) {
						logger.debug(String.format("Controlling Stream group config %s is not supported.", streamMetric.getName()));
					}
					break;
			}
			stats.put(streamKey + EncoderConstant.EDITED, EncoderConstant.FALSE);
		}
	}

	/**
	 * Get None value if Value is (None)
	 *
	 * @param value the value is String value
	 * @return None if value is (None) else return value
	 */
	private String getNoneValueIfPresent(String value) {
		String defaultValue = value;
		//Value example (None) return None
		if (!StringUtils.isNullOrEmpty(value) && value.contains(EncoderConstant.NONE) && EncoderConstant.NONE.equals(value.substring(1, value.length() - 1))) {
			defaultValue = EncoderConstant.NONE;
		}
		return defaultValue;
	}

	/**
	 * Populate service statistics and controls
	 *
	 * @param stats map statistics property
	 * @param advancedControllableProperties list controls property
	 */
	private void populateServiceConfigData(Map<String, String> stats, List<AdvancedControllableProperty> advancedControllableProperties) {
		Objects.requireNonNull(stats);
		Objects.requireNonNull(advancedControllableProperties);
		if (isOperatorOrAdministratorRole && isConfigManagement) {
			for (Entry<String, Integer> serviceSet : serviceMap.entrySet()) {
				if (serviceSet.getKey().contains(EncoderConstant.PASS_THROUGH)) {
					continue;
				}
				String serviceName = serviceSet.getKey();
				String capitalizeFirstLetterServiceName = serviceName.substring(0, 1).toUpperCase() + serviceName.substring(1);
				String property = EncoderMonitoringMetric.SERVICE.getName() + EncoderConstant.HASH + capitalizeFirstLetterServiceName;
				stats.put(property, EncoderConstant.EMPTY_STRING);
				advancedControllableProperties.add(createSwitch(property, serviceMap.get(serviceName), EncoderConstant.DISABLE, EncoderConstant.ENABLE));
			}
		}
	}

	/**
	 * Populate talkback statistics and controls
	 *
	 * @param stats map statistics property
	 * @param advancedControllableProperties list controls property
	 */
	private void populateTalkbackConfigData(Map<String, String> stats, List<AdvancedControllableProperty> advancedControllableProperties) {
		Objects.requireNonNull(stats);
		Objects.requireNonNull(advancedControllableProperties);
		// talkbackMap should contain udp port and talkback state, or else this if is not reachable
		if (isOperatorOrAdministratorRole && isConfigManagement && talkbackMap.size() == 2) {
			String groupName = EncoderMonitoringMetric.SERVICE.getName() + EncoderConstant.HASH;
			String portProperty = groupName + EncoderConstant.TALKBACK_PORT;
			String port = talkbackMap.get(EncoderConstant.PORT);
			stats.put(portProperty, port);
			advancedControllableProperties.add(createNumeric(portProperty, port));

			String statePortProperty = groupName + EncoderConstant.LISTENING_STATE;
			String state = talkbackMap.get(EncoderConstant.LISTENING_STATE);
			stats.put(statePortProperty, state);
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
				String streamValue = getDefaultValueForNullOrNoneData(streamStatistics.getValueByMetric(streamMonitoringMetric), true);
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
				String audioValue = getDefaultValueForNullOrNoneData(audioStatistics.getValueByMetric(audioMetric), true);
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
				String videoValue = getDefaultValueForNullOrNoneData(videoStatistics.getValueByMetric(videoMetric), true);
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
							videoValue = nameAndVideoConfigMap.get(videoName).getResolution();
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
		filterPortNumber();
		filterStreamStatus();
		if (isAdapterFilteringExisting()) {
			filterAudioAndVideoStatisticsList();
			List<String> idList = streamConfigList.stream().map(StreamConfig::getId).collect(Collectors.toList());
			streamStatisticsList.removeIf(item -> !idList.contains(item.getId()));
		}
	}

	/**
	 * Filter list Audio statistics by output stream response
	 */
	private void filterAudioAndVideoStatisticsList() {
		List<Audio> audioList = new ArrayList<>();
		List<String> videoList = new ArrayList<>();
		for (StreamConfig streamConfig : streamConfigList) {
			convertSourceAudioList(streamConfig);
			audioList.addAll(streamConfig.getAudioList());
			videoList.add(streamConfig.getVideo());
		}
		//Filter stream statics by name
		List<String> idList = audioList.stream().map(Audio::getAudioId).collect(Collectors.toList());
		audioStatisticsList.removeIf(item -> !idList.contains(item.getEncoderId()));
		videoStatisticsList.removeIf(item -> !videoList.contains(item.getName()));
	}

	/**
	 * Check the adapter filter
	 *
	 * @return boolean type is adapter filtering
	 */
	private boolean isAdapterFilteringExisting() {
		return !StringUtils.isNullOrEmpty(streamStatusFilter) || !StringUtils.isNullOrEmpty(portNumberFilter) || !StringUtils.isNullOrEmpty(streamNameFilter);
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
			List<String> idList = audioConfigList.stream().map(AudioConfig::getId).collect(Collectors.toList());
			audioStatisticsList.removeIf(item -> !idList.contains(item.getEncoderId()));
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
			List<String> idList = videoConfigList.stream().map(VideoConfig::getId).collect(Collectors.toList());
			videoStatisticsList.removeIf(item -> !idList.contains(item.getId()));
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
				}
			}
			streamConfigNameFilterSet.addAll(streamConfigFilter);
		}
	}

	/**
	 * Filter the port of Stream
	 */
	private void filterPortNumber() {
		extractPortNumberList(this.portNumberFilter);
		if (!portNumberList.isEmpty()) {
			List<StreamConfig> portFilterSet = new ArrayList<>();
			for (int portNumber : portNumberList) {
				for (StreamConfig streamConfig : streamConfigList) {
					String port = getPortToStreamConfig(streamConfig);
					if (portNumber == Integer.parseInt(port)) {
						portFilterSet.add(streamConfig);
					}
				}
				if (!portFilterSet.isEmpty()) {
					streamConfigPortAndStatusSet.addAll(portFilterSet);
				}
			}
		}
		if (!portNumberRangeList.isEmpty()) {
			filterPortNumberRange();
		}
	}

	/**
	 * Filter the status of Stream
	 */
	private void filterStreamStatus() {
		List<StreamConfig> streamStatusFilterList = new ArrayList<>();
		List<String> streamStatusList = extractListNameFilter(this.streamStatusFilter);
		if (!streamStatusList.isEmpty()) {
			for (String streamStatus : streamStatusList) {
				if (StringUtils.isNullOrEmpty(portNumberFilter)) {
					for (StreamConfig streamConfig : streamConfigList) {
						String stateStream = idAndStreamStatisticsMap.get(streamConfig.getId()).getState();
						if (streamStatus.equalsIgnoreCase(stateStream)) {
							streamStatusFilterList.add(streamConfig);
						}
					}
				} else {
					//And port number or port range with stream status
					for (StreamConfig streamConfig : streamConfigPortAndStatusSet) {
						String stateStream = idAndStreamStatisticsMap.get(streamConfig.getId()).getState();
						if (streamStatus.equalsIgnoreCase(stateStream)) {
							streamStatusFilterList.add(streamConfig);
						}
					}
				}
			}
			streamConfigList.clear();
			if (!streamStatusFilterList.isEmpty()) {
				streamConfigList.addAll(streamStatusFilterList);
			}
			streamConfigList.addAll(streamConfigNameFilterSet);
		}
		if (streamStatusList.isEmpty() && !StringUtils.isNullOrEmpty(this.portNumberFilter)) {
			streamConfigList.clear();
			streamConfigList.addAll(streamConfigPortAndStatusSet);
			streamConfigList.addAll(streamConfigNameFilterSet);
		}
		if (!StringUtils.isNullOrEmpty(this.streamNameFilter) && StringUtils.isNullOrEmpty(this.portNumberFilter) && StringUtils.isNullOrEmpty(this.streamStatusFilter)) {
			streamConfigList.clear();
			streamConfigList.addAll(streamConfigNameFilterSet);
		}
	}

	/**
	 * Filter the port number range of Stream
	 */
	private void filterPortNumberRange() {
		List<StreamConfig> portRangeFilterStreamSet = new ArrayList<>();
		for (String portNumberRange : portNumberRangeList) {
			String[] rangeList = portNumberRange.split(EncoderConstant.DASH);
			int mixPort = Integer.parseInt(rangeList[0].trim());
			int maxPort = Integer.parseInt(rangeList[1].trim());
			if (Integer.parseInt(rangeList[0].trim()) > Integer.parseInt(rangeList[1].trim())) {
				mixPort = Integer.parseInt(rangeList[1].trim());
				maxPort = Integer.parseInt(rangeList[0].trim());
			}
			for (StreamConfig streamConfig : streamConfigList) {
				int port = Integer.parseInt(getPortToStreamConfig(streamConfig));
				if (mixPort <= port && port <= maxPort) {
					portRangeFilterStreamSet.add(streamConfig);
				}
			}
		}
		if (!portRangeFilterStreamSet.isEmpty()) {
			streamConfigPortAndStatusSet.addAll(portRangeFilterStreamSet);
		}
	}

	/**
	 * Get port of streamConfig
	 *
	 * @param streamConfig the streamConfig is streamConfig DTO
	 * @return string is port of streamConfig
	 */
	private String getPortToStreamConfig(StreamConfig streamConfig) {
		String port = streamConfig.getPort();
		if (StringUtils.isNullOrEmpty(port)) {
			port = streamConfig.getTcpPort();
		}
		if (StringUtils.isNullOrEmpty(port)) {
			port = streamConfig.getDestinationPort();
		}
		return port;
	}

	/**
	 * Get portNumber list from the portNumber string
	 *
	 * @param portNumber the portNumber is the port of stream
	 */
	private void extractPortNumberList(String portNumber) {
		if (!StringUtils.isNullOrEmpty(portNumber)) {
			String[] portNumberListString = portNumberFilter.split(EncoderConstant.COMMA);
			for (String portNumberItem : portNumberListString) {
				try {
					portNumberList.add(Integer.valueOf(portNumberItem.trim()));
				} catch (Exception e) {
					try {
						int index = portNumberItem.trim().indexOf(EncoderConstant.DASH);
						Integer.parseInt(portNumberItem.substring(0, index).trim());
						Integer.parseInt(portNumberItem.substring(index + 1).trim());
						portNumberRangeList.add(portNumberItem);
					} catch (Exception ex) {
						//Case failed example: port = 12abc, abc, abc123
						if (logger.isDebugEnabled()) {
							logger.debug("The port range not correct format" + portNumberItem);
						}
					}
				}
			}
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
			case SESSION:
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
				String temperatureStatus = getDefaultValueForNullOrNoneData(systemInfoResponse.getTemperature(), true);
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
				nameAndAudioStatisticsMap.put(audioStatistics.getName(), audioStatistics);
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
				nameAndAudioConfigMap.put(audioName, audioConfig);
				idAndNameAudioConfigMap.put(audioConfig.getId(), audioName);
				audioConfigList.add(audioConfig);
				break;
			case VIDEO_STATISTICS:
				VideoStatistics videoStatistics = objectMapper.convertValue(mappingData, VideoStatistics.class);
				nameAndVideoStatisticsMap.put(videoStatistics.getName(), videoStatistics);
				videoStatisticsList.add(videoStatistics);
				break;
			case VIDEO_CONFIG:
				VideoConfig videoConfigResponse = objectMapper.convertValue(mappingData, VideoConfig.class);
				nameAndVideoConfigMap.put(videoConfigResponse.getName(), videoConfigResponse);
				videoConfigList.add(videoConfigResponse);
				break;
			case STREAM_STATISTICS:
				StreamStatistics streamStatistics = objectMapper.convertValue(mappingData, StreamStatistics.class);
				idAndStreamStatisticsMap.put(streamStatistics.getId(), streamStatistics);
				streamStatisticsList.add(streamStatistics);
				break;
			case STREAM_CONFIG:
				StreamConfig streamConfig = objectMapper.convertValue(mappingData, StreamConfig.class);
				streamConfigList.add(streamConfig);
				String streamName = streamConfig.getName();
				if (EncoderConstant.NONE_STREAM_NAME.equals(streamName)) {
					streamName = handleStreamNameIsEmpty(streamConfig.getId());
				}
				nameAndIdForStreamConfigMap.put(EncoderConstant.STREAM + EncoderConstant.SPACE + streamName, streamConfig.getId());
				break;
			case INPUT:
				InputResponse inputResponse = objectMapper.convertValue(mappingData, InputResponse.class);
				nameAndInputResponseMap.put(inputResponse.getName(), inputResponse);
				break;
			case SESSION:
				StreamSAP streamSAP = objectMapper.convertValue(mappingData, StreamSAP.class);
				idAndStreamSAPMap.put(streamSAP.getStreamId(), streamSAP);
				for (StreamConfig streamConfigItem : streamConfigList) {
					if (streamConfigItem.getId().equals(streamSAP.getStreamId())) {
						streamConfigItem.setSap(streamSAP);
					}
				}
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
					stats.put(systemInfoMetric.getName(), getDefaultValueForNullOrNoneData(systemInfoResponse.getValueByMetric(systemInfoMetric), true));
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
	 * Get default value by Null/None value. if value different Null/None return the value instead.
	 *
	 * @param value the value of monitoring properties
	 * @param isCheckNullData is boolean type if isCheckNullData = true convert null to None value and vice versa convert None to Empty String
	 * @return String (none/value)
	 */
	private String getDefaultValueForNullOrNoneData(String value, boolean isCheckNullData) {
		if (isCheckNullData) {
			if (StringUtils.isNullOrEmpty(value)) {
				value = EncoderConstant.NONE;
			}
		} else {
			if (EncoderConstant.NONE.equals(value)) {
				value = EncoderConstant.EMPTY_STRING;
			}
		}
		return value;
	}

	/**
	 * Get empty value by null or empty value, if value different Null/Empty return the value instead.
	 *
	 * @param value the value of properties
	 * @return String (Empty String/value)
	 */
	private String getEmptyValueForNullData(String value) {
		if (StringUtils.isNullOrEmpty(value)) {
			return EncoderConstant.EMPTY_STRING;
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
				String address = getEmptyValueForNullData(streamConfigItem.getAddress());
				String port = convertValueByIndexOfSpace(streamConfigItem.getPort());
				String protocolValue = ProtocolEnum.getNameOfProtocolEnumByValue(protocol);
				if (ProtocolEnum.TS_SRT.getName().equals(protocolValue)) {
					port = convertValueByIndexOfSpace(streamConfigItem.getDestinationPort());
					if (StringUtils.isNullOrEmpty(port)) {
						port = convertValueByIndexOfSpace(streamConfigItem.getPort());
					}
				}
				streamName = String.format("%s://@%s:%s", protocol, address, port);
				if (ProtocolEnum.RTMP.getName().equals(protocol)) {
					port = streamConfigItem.getTcpPort();
					streamName = String.format("%s:%s", address, port);
					if (address.contains(port)) {
						streamName = String.format("%s", address);
					}
				}
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
					String stateAudio = nameAndAudioStatisticsMap.get(audioName).getState();
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
					stateAudio = nameAndAudioStatisticsMap.get(audioName).getState();
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
				nameAndAudioConfigMap.get(audioName).setLevel(value);
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
				String level = EncoderConstant.EMPTY_STRING;
				for (Entry<String, AudioConfig> audioNameConfig : nameAndAudioConfigMap.entrySet()) {
					if (audioNameConfig.getKey().contains(audioName)) {
						level = nameAndAudioConfigMap.get(audioNameConfig.getKey()).getLevel();
						break;
					}
				}
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
	 * Control Service Property
	 *
	 * @param property the property of the metric (contains group name, hash, property value)
	 * @param propertyValue the propertyValue is the filed name of controlling metric
	 * @param value the value is value of metric
	 */
	private void controlServiceProperty(String property, String propertyValue, String value) {
		String[] serviceNameProperty = property.split(EncoderConstant.HASH);
		String propertyName = serviceNameProperty[1];
		if (EncoderConstant.TALKBACK_PORT.equals(propertyName)) {
			sendCommandToSavePortTalkbackProperties(value);
		} else {
			String serviceName = propertyValue.toLowerCase();
			String action = String.valueOf(EncoderConstant.NUMBER_ONE).equals(value) ? EncoderCommand.START.getName() : EncoderCommand.STOP.getName();
			String request = EncoderCommand.ADMIN_SERVICE.getName() + serviceName + EncoderConstant.SPACE + action;
			try {
				String responseData = send(request);
				String failErrorMessage = String.format("Change action %s failed", action);
				if (EncoderCommand.STOP.getName().equals(action)) {
					responseData = send(EncoderConstant.CONFIRM_STOP_SERVICE);
					if (!responseData.contains(EncoderConstant.SUCCESS_STOP_SERVICE_RESPONSE)) {
						throw new ResourceNotReachableException(failErrorMessage);
					}
				}
				action = EncoderCommand.STOP.getName().equals(action) ? action.trim().concat("ped") : action.trim().concat("ed");
				if (StringUtils.isNullOrEmpty(responseData) || !responseData.contains(action)) {
					throw new ResourceNotReachableException(failErrorMessage);
				}
			} catch (Exception e) {
				throw new ResourceNotReachableException("Error while setting action service config: " + e.getMessage(), e);
			}
		}
	}

	/**
	 * Send command to set new udp port for talkback and start/stop talkback
	 *
	 * @param udpPort new udp port to be set
	 * @throws ResourceNotReachableException if set port talkback failed
	 */
	private void sendCommandToSavePortTalkbackProperties(String udpPort) {
		String setUdpPortRequest = EncoderCommand.OPERATION_TALKBACK.getName() + EncoderConstant.SPACE + EncoderCommand.SET + " port=" + udpPort;
		try {
			String responseData = send(setUdpPortRequest);
			if (responseData.contains(EncoderConstant.ERROR_TALKBACK_PORT)) {
				throw new ResourceNotReachableException(String.format("Invalid input value, the adapter doesn't support for port: %s", udpPort));
			} else if (!responseData.contains(EncoderConstant.SUCCESS_RESPONSE)) {
				throw new ResourceNotReachableException(responseData);
			}
		} catch (Exception e) {
			throw new ResourceNotReachableException("Error while setting port: " + e.getMessage(), e);
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
				throw new ResourceNotReachableException("Error while setting action audio config: " + e.getMessage(), e);
			}
		}
	}

	/**
	 * Send command to apply all audio properties
	 *
	 * @param audioConfig the audioConfig is instance in AudioConfig
	 * @param audioId the audioId is id of audio encoder
	 * @throws ResourceNotReachableException if set audio config failed
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
		AudioConfig audio = nameAndAudioConfigMap.get(audioName);
		if (audio == null) {
			for (Map.Entry<String, AudioConfig> audioKey : nameAndAudioConfigMap.entrySet()) {
				if (audioKey.getKey().contains(audioName)) {
					audio = nameAndAudioConfigMap.get(audioKey.getKey());
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

		String[] dropdownInput = nameAndInputResponseMap.keySet().toArray(new String[0]);
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
					String stateVideo = nameAndVideoStatisticsMap.get(videoConfig.getName()).getState();
					stats.put(videoKeyName, stateVideo);
					break;
				case INPUT_FORMAT:
					String videoValue = getDefaultValueForNullOrNoneData(nameAndVideoStatisticsMap.get(videoName).getInputFormat(), true);
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
					stateVideo = nameAndVideoStatisticsMap.get(videoConfig.getName()).getState();
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
					VideoConfig videoConfig = nameAndVideoConfigMap.get(videoName);
					if (croppingMode == null) {
						handleControlCroppingMetric(extendedStatistics, advancedControllableProperties, cropping, videoConfig);
					}
				}
				break;
			case CROPPING:
				updateValueForTheControllableProperty(videoKeyName, value, extendedStatistics, advancedControllableProperties);

				//update cropping
				String crop = EncoderConstant.ZERO == Integer.parseInt(value) ? EncoderConstant.SCALE : EncoderConstant.CROP;
				VideoConfig videoConfig = nameAndVideoConfigMap.get(videoName);
				videoConfig.setCropping(crop);
				break;
			case INTRA_REFRESH:
				updateValueForTheControllableProperty(videoKeyName, value, extendedStatistics, advancedControllableProperties);
				String intraRefreshRateName = videoName + EncoderConstant.HASH + VideoControllingMetric.INTRA_REFRESH_RATE.getName();
				if (EncoderConstant.ZERO == Integer.parseInt(value)) {
					extendedStatistics.put(intraRefreshRateName, EncoderConstant.EMPTY_STRING);
					advancedControllableProperties.removeIf(item -> item.getName().equals(intraRefreshRateName));
				} else {
					videoConfig = nameAndVideoConfigMap.get(videoName);
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
				videoConfig = nameAndVideoConfigMap.get(videoName);
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
		String id = nameAndVideoConfigMap.get(videoName).getId();
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
		String inputInterface = stats.get(propertyName + VideoControllingMetric.INPUT.getName()).replace(EncoderConstant.DASH, EncoderConstant.EMPTY_STRING);
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
// create Stream stream

	/**
	 * Create output stream
	 *
	 * @param stats is list of statistics
	 * @param advancedControllableProperties is list advancedControllableProperties
	 */
	private void createOutputStream(Map<String, String> stats, List<AdvancedControllableProperty> advancedControllableProperties) {
		String streamKey = EncoderConstant.CREATE_STREAM + EncoderConstant.HASH;
		nameAndVideoConfigMap.put(EncoderConstant.NONE, new VideoConfig());
		String[] videoNames = nameAndVideoConfigMap.keySet().toArray(new String[0]);
		Arrays.sort(videoNames);
		String[] protocolDropdown = EnumTypeHandler.getEnumNames(ProtocolEnum.class);
		String[] fecDropdown = FecEnum.getArrayOfNameByUDPOrRTPMode(true);
		String[] stillImageDropdown = stillImageList.toArray(new String[0]);

		String contentName = streamKey + StreamControllingMetric.NAME.getName();
		String sourceVideoName = streamKey + StreamControllingMetric.SOURCE_VIDEO.getName();
		String addSourceAudioName = streamKey + StreamControllingMetric.ADD_SOURCE_AUDIO.getName();
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
		String editedName = streamKey + EncoderConstant.EDITED;

		//add stream name
		advancedControllableProperties.add(controlTextOrNumeric(stats, contentName, EncoderConstant.EMPTY_STRING, false));
		//add source video
		advancedControllableProperties.add(controlDropdown(stats, videoNames, sourceVideoName, videoNames[0]));
		//add audio source. Initialize source audio list
		for (int index = 0; index < EncoderConstant.MAX_SOURCE_AUDIO_DROPDOWN; index++) {
			nameAndSourceAudioMap.put(StreamControllingMetric.SOURCE_AUDIO.getName() + EncoderConstant.SPACE + index, null);
		}
		addSourceAudioForOutputStream(stats, advancedControllableProperties);
		//AddSourceAudio
		stats.put(addSourceAudioName, EncoderConstant.EMPTY_STRING);
		advancedControllableProperties.add(createButton(addSourceAudioName, EncoderConstant.PLUS, EncoderConstant.ADDING, 0));
		// add Streaming Protocol default Ts over UDP
		advancedControllableProperties.add(controlDropdown(stats, protocolDropdown, protocolName, ProtocolEnum.TS_UDP.getName()));
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
		advancedControllableProperties.add(controlTextOrNumeric(stats, tosName, EncoderConstant.DEFAULT_TOS, false));
		// add monitoring VF Encryption
		stats.put(vfEncryption, EncoderConstant.OFF);
		//add Transmit SAP
		advancedControllableProperties.add(controlSwitch(stats, transmitSAPName, String.valueOf(EncoderConstant.ZERO), EncoderConstant.DISABLE, EncoderConstant.ENABLE));
		//add still image
		advancedControllableProperties.add(controlDropdownAcceptNoneValue(stats, stillImageDropdown, stillImageName, EncoderConstant.NONE));
		// add edited = false
		stats.put(editedName, EncoderConstant.FALSE);
	}

	/**
	 * Update dropdown list for the control
	 */
	private void updateDropdownListForTheControllingMetric() {
		Map<String, String> stats = localCreateOutputStream.getStatistics();
		List<AdvancedControllableProperty> advancedControllableProperties = localCreateOutputStream.getControllableProperties();
		//update still Image
		String stillImageName = EncoderConstant.CREATE_STREAM + EncoderConstant.HASH + StreamControllingMetric.STILL_IMAGE.getName();
		String stillImageValue = getEmptyValueForNullData(stats.get(stillImageName));
		String[] stillImageDropdown = stillImageList.toArray(new String[0]);
		if (!stillImageList.contains(stillImageValue)) {
			stillImageValue = EncoderConstant.NONE;
		}
		AdvancedControllableProperty stillImageProperty = controlDropdownAcceptNoneValue(stats, stillImageDropdown, stillImageName, stillImageValue);
		addOrUpdateAdvanceControlProperties(advancedControllableProperties, stillImageProperty);

		String audioSourceName;
		for (int i = 0; i <= 7; i++) {
			audioSourceName = EncoderConstant.CREATE_STREAM + EncoderConstant.HASH + StreamControllingMetric.SOURCE_AUDIO.getName() + EncoderConstant.SPACE + i;
			String propertyName = StreamControllingMetric.SOURCE_AUDIO.getName() + EncoderConstant.SPACE + i;
			String value = stats.get(audioSourceName);
			if (!StringUtils.isNullOrEmpty(value)) {
				for (String key : nameAndAudioConfigMap.keySet()) {
					if (key.contains(value) || value.contains(key)) {
						value = key;
						break;
					}
				}
				updateSourceAudioDropdownList(audioSourceName, value, stats, advancedControllableProperties, propertyName);
			}
		}
	}

	/**
	 * Add new Source Audio for output stream
	 *
	 * @param stats is list of stats
	 * @param advancedControllablePropertyList is list AdvancedControllableProperty instance
	 * @throws ResourceNotReachableException if assign more than 7 source audio
	 */
	private void addSourceAudioForOutputStream(Map<String, String> stats, List<AdvancedControllableProperty> advancedControllablePropertyList) {
		nameAndAudioConfigMap.put(EncoderConstant.NONE, new AudioConfig());
		String prefixName = EncoderConstant.CREATE_STREAM + EncoderConstant.HASH;
		String[] audioNames = nameAndAudioConfigMap.keySet().toArray(new String[0]);
		Arrays.sort(audioNames);
		Map<Integer, String> audioIdList = new HashMap<>();
		int countSource = 0;

		//Check list source audio assigned in stream
		for (Map.Entry<String, Audio> audioKey : nameAndSourceAudioMap.entrySet()) {
			//This is a special case, when the source sound is None. Audio source 0 always visible and assigned in stream
			if (audioKey.getValue() != null) {
				//count the number of sources assigned in stream
				countSource++;
			}
			if (audioKey.getValue() != null && audioKey.getValue().getAudioId() != null) {
				audioIdList.put(Integer.valueOf(audioKey.getValue().getAudioId()), audioKey.getValue().getAudioName());
			}
		}

		//Maximum amount of sources audio is 8 with index from 0-7
		// if countSource >= 8 throw exception
		if (countSource >= EncoderConstant.MAX_SOURCE_AUDIO_DROPDOWN) {
			throw new ResourceNotReachableException("The audio source just assign max audio source is 8 with index source from SourceAudio 0 to SourceAudio 7.");
		}
		for (Map.Entry<String, Audio> audioEntry : nameAndSourceAudioMap.entrySet()) {
			String defaultName = audioNames[0];
			for (int i = 0; i < EncoderConstant.MAX_SOURCE_AUDIO_DROPDOWN; i++) {
				if (!audioIdList.containsKey(i)) {
					defaultName = idAndNameAudioConfigMap.get(String.valueOf(i));
					break;
				}
			}
			if (audioEntry.getValue() == null) {
				advancedControllablePropertyList.add(controlDropdown(stats, audioNames, prefixName + audioEntry.getKey(), defaultName));
				Audio audio = new Audio();
				audio.setAudioId(nameAndAudioConfigMap.get(defaultName).getId());
				audioEntry.setValue(audio);
				break;
			}
		}
	}

	/**
	 * Control Stream property
	 *
	 * @param property the property is the filed name of controlling metric
	 * @param value the value is value of metric
	 * @param extendedStatistics list extendedStatistics
	 * @param advancedControllableProperties the advancedControllableProperties is advancedControllableProperties instance
	 */
	private void controlStreamProperty(String property, String value, Map<String, String> extendedStatistics, List<AdvancedControllableProperty> advancedControllableProperties) {
		isEmergencyDelivery = true;
		String[] streamProperty = property.split(EncoderConstant.HASH);
		String streamName = streamProperty[0];
		String propertyName = streamProperty[1];
		StreamControllingMetric streamControllingMetric = EnumTypeHandler.getMetricOfEnumByName(StreamControllingMetric.class, propertyName);
		switch (streamControllingMetric) {
			case CANCEL:
				isEmergencyDelivery = false;
				break;
			case ACTION:
				updateValueForTheControllableProperty(property, value, extendedStatistics, advancedControllableProperties);
				break;
			case APPLY_CHANGE:
				String streamId = nameAndIdForStreamConfigMap.get(streamName);
				String action = extendedStatistics.get(streamName + EncoderConstant.HASH + StreamControllingMetric.ACTION.getName());
				sendCommandToSetStreamAction(streamId, action);
				isEmergencyDelivery = false;
				break;
			default:
				if (logger.isDebugEnabled()) {
					logger.debug(String.format("Controlling stream create output group %s is not supported.", streamControllingMetric.getName()));
				}
				break;
		}
		if (isEmergencyDelivery) {
			propertyName = streamName;
			extendedStatistics.put(propertyName + EncoderConstant.HASH + StreamControllingMetric.APPLY_CHANGE.getName(), EncoderConstant.EMPTY_STRING);
			advancedControllableProperties.add(createButton(propertyName + EncoderConstant.HASH + StreamControllingMetric.APPLY_CHANGE.getName(), EncoderConstant.APPLY, EncoderConstant.APPLYING, 0));

			extendedStatistics.put(propertyName + EncoderConstant.HASH + EncoderConstant.EDITED, EncoderConstant.TRUE);
			extendedStatistics.put(propertyName + EncoderConstant.HASH + EncoderConstant.CANCEL, EncoderConstant.EMPTY_STRING);
			advancedControllableProperties.add(createButton(propertyName + EncoderConstant.HASH + EncoderConstant.CANCEL, EncoderConstant.CANCEL, EncoderConstant.CANCELING, 0));
		}
	}

	/**
	 * Send command to set stream action
	 *
	 * @param streamID the streamID is ID of stream
	 * @param action the action is action state of stream
	 */
	private void sendCommandToSetStreamAction(String streamID, String action) {
		String request = EncoderCommand.OPERATION_STREAM.getName() + streamID + EncoderConstant.SPACE + action.toLowerCase();
		if (!EncoderConstant.NONE.equals(action)) {
			try {
				String responseData = send(request);
				if (!responseData.contains(EncoderConstant.SUCCESS_RESPONSE)) {
					throw new ResourceNotReachableException(String.format("Change stream %s failed", action));
				}
			} catch (Exception e) {
				throw new ResourceNotReachableException("Error while setting action stream config: " + e.getMessage(), e);
			}
		}
	}

	/**
	 * Control create a stream property
	 *
	 * @param property the property is the filed name of controlling metric
	 * @param value the value is value of metric
	 * @param stats list stats
	 * @param advancedControllableProperties the advancedControllableProperties is advancedControllableProperties instance
	 */
	private void controlCreateStreamProperty(String property, String value, Map<String, String> stats, List<AdvancedControllableProperty> advancedControllableProperties) {
		// property format: GroupName#PropertyName
		isCreateStreamCalled = true;
		String[] streamProperty = property.split(EncoderConstant.HASH);
		String streamName = streamProperty[0];
		String propertyName = streamProperty[1];
		if (propertyName.contains(StreamControllingMetric.SOURCE_AUDIO.getName())) {
			updateSourceAudioDropdownList(property, value, stats, advancedControllableProperties, propertyName);
		} else {
			StreamControllingMetric streamControllingMetric = EnumTypeHandler.getMetricOfEnumByName(StreamControllingMetric.class, propertyName);
			String streamKey = streamName + EncoderConstant.HASH;
			switch (streamControllingMetric) {
				case NAME:
				case SOURCE_VIDEO:
				case SOURCE_AUDIO:
				case PARAMETER_FEC:
				case PARAMETER_IDLE_CELLS:
				case PARAMETER_DELAYED_AUDIO:
				case STREAMING_DESTINATION_ADDRESS:
				case SAP_NAME:
				case SAP_KEYWORDS:
				case SAP_DESCRIPTION:
				case SAP_AUTHOR:
				case SAP_COPYRIGHT:
				case SAP_ADDRESS:
				case STREAM_PASSPHRASE:
				case STREAM_NETWORK_ADAPTIVE:
				case RTMP_PUBLISH_NAME:
				case RTMP_USERNAME:
				case RTMP_PASSWORD:
				case RTMP_CONFIRMATION_PASSWORD:
					updateValueForTheControllableProperty(property, value, stats, advancedControllableProperties);
					break;
				case STILL_IMAGE:
					String[] stillImageDropdown = stillImageList.toArray(new String[0]);
					if (!stillImageList.contains(value)) {
						value = EncoderConstant.NONE;
					}
					AdvancedControllableProperty stillImageProperty = controlDropdownAcceptNoneValue(stats, stillImageDropdown, property, value);
					addOrUpdateAdvanceControlProperties(advancedControllableProperties, stillImageProperty);
					break;
				case STREAM_ENCRYPTION:
					updateValueForTheControllableProperty(property, value, stats, advancedControllableProperties);
					String passPhraseName = streamKey + StreamControllingMetric.STREAM_PASSPHRASE.getName();
					if (!EncoderConstant.NONE.equals(value)) {
						String passPhraseValue = getEmptyValueForNullData(localStatsStreamOutput.get(passPhraseName));
						AdvancedControllableProperty passPhraseProperty = controlTextOrNumeric(stats, passPhraseName, passPhraseValue, false);
						addOrUpdateAdvanceControlProperties(advancedControllableProperties, passPhraseProperty);
					} else {
						stats.remove(passPhraseName);
					}
					break;
				case PARAMETER_TRAFFIC_SHAPING:
					populateStreamCreateModeTrafficShapingMode(property, value, stats, advancedControllableProperties, streamName, true);
					break;
				case PARAMETER_MTU:
					value = String.valueOf(getValueByRange(EncoderConstant.MIN_MTU, EncoderConstant.MAX_MTU, value));
					updateValueForTheControllableProperty(property, value, stats, advancedControllableProperties);
					break;
				case PARAMETER_TTL:
					value = String.valueOf(getValueByRange(EncoderConstant.MIN_TTL, EncoderConstant.MAX_TTL, value));
					updateValueForTheControllableProperty(property, value, stats, advancedControllableProperties);
					break;
				case PARAMETER_TOS:
					value = getTOSValueByRange(value);
					updateValueForTheControllableProperty(property, value, stats, advancedControllableProperties);
					break;
				case ADD_SOURCE_AUDIO:
					addSourceAudioForOutputStream(stats, advancedControllableProperties);
					break;
				case STREAM_CONNECTION_SOURCE_PORT:
				case STREAMING_DESTINATION_PORT:
				case SAP_PORT:
					value = String.valueOf(getValueByRange(EncoderConstant.MIN_PORT, EncoderConstant.MAX_PORT, value));
					updateValueForTheControllableProperty(property, value, stats, advancedControllableProperties);
					break;
				case SAP_TRANSMIT:
					populateStreamCreateWithSAPMode(value, property, stats, advancedControllableProperties, streamName);
					break;
				case PARAMETER_BANDWIDTH_OVERHEAD:
					value = String.valueOf(getValueByRange(EncoderConstant.MIN_BANDWIDTH_OVERHEAD, EncoderConstant.MAX_BANDWIDTH_OVERHEAD, value));
					updateValueForTheControllableProperty(property, value, stats, advancedControllableProperties);
					break;
				case STREAMING_PROTOCOL:
					updateValueForTheControllableProperty(property, value, stats, advancedControllableProperties);
					populateCreateStreamWithProtocolMode(value, stats, advancedControllableProperties, streamName);
					break;
				case ACTION:
					StreamConfig streamConfig = convertStreamConfigByValue(stats);
					sendCommandToCreateStream(streamConfig);
					isCreateStreamCalled = false;
					break;
				case CANCEL:
					isCreateStreamCalled = false;
					break;
				case STREAM_LATENCY:
					value = String.valueOf(getValueByRange(EncoderConstant.MIN_LATENCY, EncoderConstant.MAX_LATENCY, value));
					updateValueForTheControllableProperty(property, value, stats, advancedControllableProperties);
					break;
				case STREAM_CONNECTION_ADDRESS:
					updateValueForTheControllableProperty(property, value, stats, advancedControllableProperties);
					String addressName = streamName + EncoderConstant.HASH + StreamControllingMetric.STREAMING_DESTINATION_ADDRESS.getName();
					localStatsStreamOutput.put(addressName, value);
					break;
				case STREAM_CONNECTION_DESTINATION_PORT:
					value = String.valueOf(getValueByRange(EncoderConstant.MIN_PORT, EncoderConstant.MAX_PORT, value));
					updateValueForTheControllableProperty(property, value, stats, advancedControllableProperties);
					String portName = streamName + EncoderConstant.HASH + StreamControllingMetric.STREAMING_DESTINATION_PORT.getName();
					localStatsStreamOutput.put(portName, value);
					break;
				case STREAM_CONNECTION_PORT:
					value = String.valueOf(getValueByRange(EncoderConstant.MIN_PORT, EncoderConstant.MAX_PORT, value));
					updateValueForTheControllableProperty(property, value, stats, advancedControllableProperties);
					portName = streamName + EncoderConstant.HASH + StreamControllingMetric.STREAMING_DESTINATION_PORT.getName();
					localStatsStreamOutput.put(portName, value);
					break;
				case STREAM_CONNECTION_MODE:
					updateValueForTheControllableProperty(property, value, stats, advancedControllableProperties);
					ConnectionModeEnum connectionModeEnum = EnumTypeHandler.getMetricOfEnumByName(ConnectionModeEnum.class, value);
					switch (connectionModeEnum) {
						case CALLER:
							addControlForSRTWithModeCaller(stats, advancedControllableProperties);
							String connectionSourcePortName = streamKey + StreamControllingMetric.STREAM_CONNECTION_SOURCE_PORT.getName();

							updateValueForTheControllableProperty(connectionSourcePortName, EncoderConstant.EMPTY_STRING, stats, advancedControllableProperties);
							stats.remove(streamKey + StreamControllingMetric.STREAM_CONNECTION_PORT.getName());
							break;
						case RENDEZVOUS:
							addControlForSRTWithModeCaller(stats, advancedControllableProperties);
							connectionSourcePortName = streamKey + StreamControllingMetric.STREAM_CONNECTION_SOURCE_PORT.getName();
							//update connection source port
							stats.put(connectionSourcePortName, getEmptyValueForNullData(localStatsStreamOutput.get(streamKey + StreamControllingMetric.STREAMING_DESTINATION_PORT.getName())));

							stats.remove(streamKey + StreamControllingMetric.STREAM_CONNECTION_PORT.getName());
							advancedControllableProperties.removeIf(item -> item.getName().equals(connectionSourcePortName));
							break;
						case LISTENER:
							//add connection port
							String connectionPort = streamKey + StreamControllingMetric.STREAM_CONNECTION_PORT.getName();
							String connectionPortValue = getEmptyValueForNullData(localStatsStreamOutput.get(streamKey + StreamControllingMetric.STREAMING_DESTINATION_PORT.getName()));

							AdvancedControllableProperty connectionPortProperty = controlTextOrNumeric(stats, connectionPort, connectionPortValue, true);
							addOrUpdateAdvanceControlProperties(advancedControllableProperties, connectionPortProperty);

							//remove connection source port, destination port and connection address
							stats.remove(streamKey + StreamControllingMetric.STREAM_CONNECTION_SOURCE_PORT.getName());
							stats.remove(streamKey + StreamControllingMetric.STREAM_CONNECTION_DESTINATION_PORT.getName());
							stats.remove(streamKey + StreamControllingMetric.STREAM_CONNECTION_ADDRESS.getName());
							break;
						default:
							break;
					}
					break;
				case STREAM_SOURCE_TYPE:
					updateValueForTheControllableProperty(property, value, stats, advancedControllableProperties);
					if (SourceType.AUDIO.getName().equals(value)) {
						addSourceAudioForOutputStream(stats, advancedControllableProperties);
						stats.remove(streamKey + StreamControllingMetric.SOURCE_VIDEO.getName());
						break;
					}
					if (SourceType.VIDEO.getName().equals(value)) {
						nameAndVideoConfigMap.put(EncoderConstant.NONE, new VideoConfig());
						String[] videoNames = nameAndVideoConfigMap.keySet().toArray(new String[0]);
						Arrays.sort(videoNames);
						String videoName = streamKey + StreamControllingMetric.SOURCE_VIDEO.getName();
						//add video source
						AdvancedControllableProperty videoSourceProperty = controlDropdown(stats, videoNames, videoName, videoNames[0]);
						addOrUpdateAdvanceControlProperties(advancedControllableProperties, videoSourceProperty);
						stats.remove(streamKey + EncoderConstant.SOURCE_AUDIO_0);
					}
					break;
				default:
					if (logger.isDebugEnabled()) {
						logger.debug(String.format("Controlling stream create output group %s is not supported.", streamControllingMetric.getName()));
					}
					break;
			}
		}
		Map<String, String> extendedStats = localExtendedStatistics.getStatistics();
		if (isCreateStreamCalled) {
			//add Edited = True
			stats.put(streamName + EncoderConstant.HASH + EncoderConstant.EDITED, EncoderConstant.TRUE);

			//Add action = Create
			stats.put(streamName + EncoderConstant.HASH + StreamControllingMetric.ACTION.getName(), EncoderConstant.EMPTY_STRING);
			advancedControllableProperties.add(createButton(streamName + EncoderConstant.HASH + VideoControllingMetric.ACTION.getName(), EncoderConstant.CREATE, EncoderConstant.CREATING, 0));

			//Add Cancel Button
			stats.put(streamName + EncoderConstant.HASH + EncoderConstant.CANCEL, EncoderConstant.EMPTY_STRING);
			advancedControllableProperties.add(createButton(streamName + EncoderConstant.HASH + EncoderConstant.CANCEL, EncoderConstant.CANCEL, EncoderConstant.CANCELING, 0));

			//remove all old value of property in localStatsStreamOutput and update it with new value and new property
			localStatsStreamOutput.keySet().stream().filter(extendedStats::containsKey).collect(Collectors.toList()).forEach(extendedStats::remove);
			extendedStats.putAll(stats);
			List<AdvancedControllableProperty> listControlProperty = localExtendedStatistics.getControllableProperties();

			//update or add props form advancedControllableProperties to listControlProperty
			List<String> newPropNames = advancedControllableProperties.stream().map(AdvancedControllableProperty::getName).collect(Collectors.toList());
			listControlProperty.removeIf(item -> newPropNames.contains(item.getName()));
			listControlProperty.addAll(new ArrayList<>(advancedControllableProperties));
		} else {
			stats.clear();
			isEmergencyDelivery = false;
			localStatsStreamOutput.clear();
			localCreateOutputStream = new ExtendedStatistics();
		}
	}

	/**
	 * Update value and dropdown list of source audio
	 *
	 * @param property the property is the filed name of controlling metric
	 * @param stats list stats
	 * @param advancedControllableProperties the advancedControllableProperties is advancedControllableProperties instance
	 * @param value the value is value of metric
	 * @param sourceName the sourceName is name of source audio
	 */
	private void updateSourceAudioDropdownList(String property, String value, Map<String, String> stats, List<AdvancedControllableProperty> advancedControllableProperties, String sourceName) {
		if (EncoderConstant.NONE.equals(value) && !EncoderConstant.SOURCE_AUDIO_0.equals(sourceName)) {
			stats.remove(property);
			nameAndSourceAudioMap.replace(sourceName, null);
		} else {
			nameAndAudioConfigMap.put(EncoderConstant.NONE, new AudioConfig());
			String[] audioNames = nameAndAudioConfigMap.keySet().toArray(new String[0]);
			Arrays.sort(audioNames);
			AdvancedControllableProperty sourceAudioControlProperty = controlDropdownAcceptNoneValue(stats, audioNames, property, value);
			addOrUpdateAdvanceControlProperties(advancedControllableProperties, sourceAudioControlProperty);
			Audio audio = new Audio();
			audio.setAudioId(nameAndAudioConfigMap.get(value).getId());
			nameAndSourceAudioMap.put(sourceName, audio);
		}
	}

	/**
	 * Remove property with case protocol different SRT mode
	 *
	 * @param stats the stats are list of stats
	 */
	private void removeControlWithModeDifferentSRT(Map<String, String> stats) {
		String streamKey = EncoderConstant.CREATE_STREAM + EncoderConstant.HASH;
		stats.remove(streamKey + StreamControllingMetric.STREAM_LATENCY.getName());
		stats.remove(streamKey + StreamControllingMetric.STREAM_ENCRYPTION.getName());
		stats.remove(streamKey + StreamControllingMetric.STREAM_CONNECTION_SOURCE_PORT.getName());
		stats.remove(streamKey + StreamControllingMetric.STREAM_CONNECTION_MODE.getName());
		stats.remove(streamKey + StreamControllingMetric.STREAM_CONNECTION_DESTINATION_PORT.getName());
		stats.remove(streamKey + StreamControllingMetric.STREAM_CONNECTION_ADDRESS.getName());
		stats.remove(streamKey + StreamControllingMetric.STREAM_NETWORK_ADAPTIVE.getName());
		stats.remove(streamKey + StreamControllingMetric.RTMP_USERNAME.getName());
		stats.remove(streamKey + StreamControllingMetric.RTMP_PUBLISH_NAME.getName());
		stats.remove(streamKey + StreamControllingMetric.RTMP_PASSWORD.getName());
		stats.remove(streamKey + StreamControllingMetric.RTMP_CONFIRMATION_PASSWORD.getName());
		stats.remove(streamKey + StreamControllingMetric.STREAM_SOURCE_TYPE.getName());
		stats.remove(streamKey + StreamControllingMetric.STREAM_PASSPHRASE.getName());
	}

	/**
	 * Control property with protocol mode
	 *
	 * @param value the value is value of metric
	 * @param stats the stats are list of stats
	 * @param advancedControllableProperties the advancedControllableProperties is advancedControllableProperties instance
	 * @param streamName the streamName is name of stream
	 */
	private void populateCreateStreamWithProtocolMode(String value, Map<String, String> stats, List<AdvancedControllableProperty> advancedControllableProperties, String streamName) {
		ProtocolEnum protocolEnum = EnumTypeHandler.getMetricOfEnumByName(ProtocolEnum.class, value);
		String streamKey = streamName + EncoderConstant.HASH;
		String sapTransmitName = streamKey + StreamControllingMetric.SAP_TRANSMIT.getName();
		removeControlForSAPMode(stats);
		String mtuName = streamKey + StreamControllingMetric.PARAMETER_MTU.getName();
		String mtuValue = getEmptyValueForNullData(localStatsStreamOutput.get(mtuName));
		if (StringUtils.isNullOrEmpty(mtuValue)) {
			mtuValue = String.valueOf(EncoderConstant.DEFAULT_MTU);
		}
		AdvancedControllableProperty mtuPropertyControl = controlTextOrNumeric(stats, mtuName, mtuValue, true);
		addOrUpdateAdvanceControlProperties(advancedControllableProperties, mtuPropertyControl);
		switch (protocolEnum) {
			case TS_UDP:
				String[] fecDropdown = FecEnum.getArrayOfNameByUDPOrRTPMode(true);
				String fecName = streamKey + StreamControllingMetric.PARAMETER_FEC.getName();
				String destinationPort = streamKey + StreamControllingMetric.STREAMING_DESTINATION_PORT.getName();
				String destinationAddress = streamKey + StreamControllingMetric.STREAMING_DESTINATION_ADDRESS.getName();
				String vfEncryptionName = streamKey + StreamControllingMetric.PARAMETER_VF_ENCRYPTION.getName();
				String vfEncryptionValue = getEmptyValueForNullData(localStatsStreamOutput.get(vfEncryptionName));
				String trafficShaping = streamKey + StreamControllingMetric.PARAMETER_TRAFFIC_SHAPING.getName();
				String trafficShapingValue = getEmptyValueForNullData(localStatsStreamOutput.get(streamKey + StreamControllingMetric.PARAMETER_TRAFFIC_SHAPING.getName()));

				String fecValue = FecEnum.getNameOfFecEnumByMode(getEmptyValueForNullData(localStatsStreamOutput.get(fecName)));
				if (StringUtils.isNullOrEmpty(vfEncryptionValue)) {
					vfEncryptionValue = EncoderConstant.OFF;
				}
				stats.put(vfEncryptionName, vfEncryptionValue);
				//add fec control
				AdvancedControllableProperty fecControlProperty = controlDropdownAcceptNoneValue(stats, fecDropdown, fecName, fecValue);
				addOrUpdateAdvanceControlProperties(advancedControllableProperties, fecControlProperty);

				//add destination port
				String destinationPortValue = getEmptyValueForNullData(localStatsStreamOutput.get(destinationPort));
				AdvancedControllableProperty destinationPortProperty = controlTextOrNumeric(stats, destinationPort, destinationPortValue, true);
				addOrUpdateAdvanceControlProperties(advancedControllableProperties, destinationPortProperty);

				//add destination address port
				String destinationAddressValue = getEmptyValueForNullData(localStatsStreamOutput.get(destinationAddress));
				AdvancedControllableProperty destinationAddressProperty = controlTextOrNumeric(stats, destinationAddress, destinationAddressValue, false);
				addOrUpdateAdvanceControlProperties(advancedControllableProperties, destinationAddressProperty);

				populateStreamCreateModeTrafficShapingMode(trafficShaping, trafficShapingValue, stats, advancedControllableProperties, streamName, false);
				populateStreamCreateWithSAPMode(null, sapTransmitName, stats, advancedControllableProperties, streamName);

				addNewAudioAndVideoSource(stats, advancedControllableProperties);

				removeControlWithModeDifferentSRT(stats);
				stats.remove(streamKey + StreamControllingMetric.STREAM_SOURCE_TYPE.getName());
				break;
			case TS_RTP:
				fecName = streamKey + StreamControllingMetric.PARAMETER_FEC.getName();
				fecDropdown = FecEnum.getArrayOfNameByUDPOrRTPMode(false);
				fecValue = FecEnum.getNameOfFecEnumByMode(getEmptyValueForNullData(localStatsStreamOutput.get(fecName)));

				fecControlProperty = controlDropdownAcceptNoneValue(stats, fecDropdown, fecName, fecValue);
				addOrUpdateAdvanceControlProperties(advancedControllableProperties, fecControlProperty);

				addButtonSourceAudio(stats, advancedControllableProperties);
				addNewAudioAndVideoSource(stats, advancedControllableProperties);

				stats.remove(streamKey + StreamControllingMetric.PARAMETER_VF_ENCRYPTION.getName());
				stats.remove(streamKey + StreamControllingMetric.STREAM_SOURCE_TYPE.getName());
				populateStreamCreateWithSAPMode(null, sapTransmitName, stats, advancedControllableProperties, streamName);
				removeControlWithModeDifferentSRT(stats);
				break;
			case DIRECT_RTP:
				removeControlWithModeDifferentSRT(stats);
				String sourceTypeName = streamKey + StreamControllingMetric.STREAM_SOURCE_TYPE.getName();
				String[] sourceTypeDropdown = EnumTypeHandler.getEnumNames(SourceType.class);
				AdvancedControllableProperty sourceTypeProperty = controlDropdownAcceptNoneValue(stats, sourceTypeDropdown, sourceTypeName, SourceType.VIDEO.getName());
				addOrUpdateAdvanceControlProperties(advancedControllableProperties, sourceTypeProperty);

				for (Map.Entry<String, Audio> audioKey : nameAndSourceAudioMap.entrySet()) {
					String key = audioKey.getKey();
					stats.remove(streamKey + key);
					nameAndSourceAudioMap.replace(key, null);
				}

				destinationAddress = streamKey + StreamControllingMetric.STREAMING_DESTINATION_ADDRESS.getName();
				destinationAddressValue = getEmptyValueForNullData(localStatsStreamOutput.get(destinationAddress));
				AdvancedControllableProperty addressProperty = controlTextOrNumeric(stats, destinationAddress, destinationAddressValue, false);
				addOrUpdateAdvanceControlProperties(advancedControllableProperties, addressProperty);

				destinationPort = streamKey + StreamControllingMetric.STREAMING_DESTINATION_PORT.getName();
				destinationPortValue = getEmptyValueForNullData(localStatsStreamOutput.get(destinationPort));
				AdvancedControllableProperty portProperty = controlTextOrNumeric(stats, destinationPort, destinationPortValue, false);
				addOrUpdateAdvanceControlProperties(advancedControllableProperties, portProperty);

				// remove Fec, VF Encryption, idleCells, delayedAudio, Transmit SAP
				stats.remove(streamKey + StreamControllingMetric.PARAMETER_VF_ENCRYPTION.getName());
				stats.remove(streamKey + StreamControllingMetric.PARAMETER_FEC.getName());
				stats.remove(streamKey + StreamControllingMetric.PARAMETER_IDLE_CELLS.getName());
				stats.remove(streamKey + StreamControllingMetric.PARAMETER_DELAYED_AUDIO.getName());
				stats.remove(streamKey + StreamControllingMetric.SAP_TRANSMIT.getName());
				stats.remove(streamKey + StreamControllingMetric.ADD_SOURCE_AUDIO.getName());
				break;
			case RTMP:
				//Name Property
				String rtmpPublishName = streamKey + StreamControllingMetric.RTMP_PUBLISH_NAME.getName();
				String rtmpUsername = streamKey + StreamControllingMetric.RTMP_USERNAME.getName();
				String rtmpPassword = streamKey + StreamControllingMetric.RTMP_PASSWORD.getName();
				String rtmpConfirmationPassword = streamKey + StreamControllingMetric.RTMP_CONFIRMATION_PASSWORD.getName();
				String contentName = streamKey + StreamControllingMetric.NAME.getName();
				String destinationAddressName = streamKey + StreamControllingMetric.STREAMING_DESTINATION_ADDRESS.getName();

				//remove portAudio destinationPort mtuName
				stats.remove(streamKey + StreamControllingMetric.PARAMETER_MTU.getName());
				stats.remove(streamKey + StreamControllingMetric.STREAMING_DESTINATION_PORT.getName());

				stats.remove(streamKey + StreamControllingMetric.PARAMETER_FEC.getName());
				stats.remove(streamKey + StreamControllingMetric.PARAMETER_IDLE_CELLS.getName());
				stats.remove(streamKey + StreamControllingMetric.PARAMETER_DELAYED_AUDIO.getName());
				stats.remove(streamKey + StreamControllingMetric.SAP_TRANSMIT.getName());
				stats.remove(streamKey + StreamControllingMetric.ADD_SOURCE_AUDIO.getName());
				stats.remove(streamKey + StreamControllingMetric.PARAMETER_VF_ENCRYPTION.getName());
				stats.remove(streamKey + StreamControllingMetric.STREAM_SOURCE_TYPE.getName());
				removeControlForSAPMode(stats);
				removeControlWithModeDifferentSRT(stats);

				//Value Property
				String contentNameValue = getEmptyValueForNullData(localStatsStreamOutput.get(contentName));
				String rtmpPublishNameValue = getEmptyValueForNullData(localStatsStreamOutput.get(rtmpPublishName));
				String rtmpUsernameValue = getEmptyValueForNullData(localStatsStreamOutput.get(rtmpUsername));
				String rtmpPasswordValue = getEmptyValueForNullData(localStatsStreamOutput.get(rtmpPassword));
				String rtmpConfirmationPasswordValue = getEmptyValueForNullData(localStatsStreamOutput.get(rtmpConfirmationPassword));
				String destinationAddressNameValue = getEmptyValueForNullData(localStatsStreamOutput.get(destinationAddressName));

				if (StringUtils.isNullOrEmpty(rtmpPublishNameValue)) {
					rtmpPublishNameValue = contentNameValue;
				}
				//RTMPPublishName
				AdvancedControllableProperty rtmpPublishNameProperty = controlTextOrNumeric(stats, rtmpPublishName, rtmpPublishNameValue, false);
				addOrUpdateAdvanceControlProperties(advancedControllableProperties, rtmpPublishNameProperty);

				//RTMPUsername
				AdvancedControllableProperty rtmpUsernameProperty = controlTextOrNumeric(stats, rtmpUsername, rtmpUsernameValue, false);
				addOrUpdateAdvanceControlProperties(advancedControllableProperties, rtmpUsernameProperty);

				//RTMPPassword
				AdvancedControllableProperty rtmpPasswordProperty = controlTextOrNumeric(stats, rtmpPassword, rtmpPasswordValue, false);
				addOrUpdateAdvanceControlProperties(advancedControllableProperties, rtmpPasswordProperty);

				//RTMPConfirmationPassword
				AdvancedControllableProperty rtmpConfirmationPasswordProperty = controlTextOrNumeric(stats, rtmpConfirmationPassword, rtmpConfirmationPasswordValue, false);
				addOrUpdateAdvanceControlProperties(advancedControllableProperties, rtmpConfirmationPasswordProperty);

				//destinationAddressName
				AdvancedControllableProperty destinationAddressNameProperty = controlTextOrNumeric(stats, destinationAddressName, destinationAddressNameValue, false);
				addOrUpdateAdvanceControlProperties(advancedControllableProperties, destinationAddressNameProperty);

				//remove MTU
				String mtuNameProperties = streamKey + StreamControllingMetric.PARAMETER_MTU.getName();
				stats.remove(mtuNameProperties);

				//clean audio source and update
				clearSourceAudioBeforeAddNew(stats);
				addAndUpdateSourceAudio(stats, advancedControllableProperties);

				break;
			case TS_SRT:
				//Name Property
				String destinationPortName = streamKey + StreamControllingMetric.STREAMING_DESTINATION_PORT.getName();
				String connectionModeName = streamKey + StreamControllingMetric.STREAM_CONNECTION_MODE.getName();
				String connectionSourcePortName = streamKey + StreamControllingMetric.STREAM_CONNECTION_SOURCE_PORT.getName();
				String connectionPortName = streamKey + StreamControllingMetric.STREAM_CONNECTION_PORT.getName();

				//Value Property
				String connectionModeValue = getEmptyValueForNullData(localStatsStreamOutput.get(connectionModeName));
				String connectionPortValue = getEmptyValueForNullData(localStatsStreamOutput.get(destinationPortName));

				//add connection mode
				String[] connectionModeDropdown = EnumTypeHandler.getEnumNames(ConnectionModeEnum.class);
				if (StringUtils.isNullOrEmpty(connectionModeValue) || ConnectionModeEnum.CALLER.getName().equals(connectionModeValue) || ConnectionModeEnum.RENDEZVOUS.getName().equals(connectionModeValue)) {
					connectionModeValue = ConnectionModeEnum.CALLER.getName();

					AdvancedControllableProperty connectionModeProperty = controlDropdown(stats, connectionModeDropdown, connectionModeName, connectionModeValue);
					addOrUpdateAdvanceControlProperties(advancedControllableProperties, connectionModeProperty);

					addControlForSRTWithModeCaller(stats, advancedControllableProperties);
				}

				if (ConnectionModeEnum.LISTENER.getName().equals(connectionModeValue)) {
					//Add connection port
					AdvancedControllableProperty connectionPortProperty = controlTextOrNumeric(stats, connectionPortName, connectionPortValue, true);
					addOrUpdateAdvanceControlProperties(advancedControllableProperties, connectionPortProperty);
				}

				if (ConnectionModeEnum.RENDEZVOUS.getName().equals(connectionModeValue)) {
					//Add connection destination source port
					String connectionSourcePortValue = getEmptyValueForNullData(localStatsStreamOutput.get(destinationPortName));
					stats.put(connectionSourcePortName, connectionSourcePortValue);
					advancedControllableProperties.removeIf(item -> item.getName().equals(connectionSourcePortName));
				}

				//Add Audio or Video Source
				addNewAudioAndVideoSource(stats, advancedControllableProperties);

				trafficShapingValue = getEmptyValueForNullData(localStatsStreamOutput.get(streamName + EncoderConstant.HASH + StreamControllingMetric.PARAMETER_TRAFFIC_SHAPING.getName()));
				if (!StringUtils.isNullOrEmpty(trafficShapingValue) && !String.valueOf(EncoderConstant.ZERO).equals(trafficShapingValue)) {
					String idleCellsName = streamName + EncoderConstant.HASH + StreamControllingMetric.PARAMETER_IDLE_CELLS.getName();
					String idleCellsValue = localStatsStreamOutput.get(idleCellsName);
					//add idleCells
					if (idleCellsValue == null) {
						idleCellsValue = String.valueOf(EncoderConstant.ZERO);
					}
					AdvancedControllableProperty idleCellsControlProperty = controlSwitch(stats, idleCellsName, idleCellsValue, EncoderConstant.DISABLE, EncoderConstant.ENABLE);
					addOrUpdateAdvanceControlProperties(advancedControllableProperties, idleCellsControlProperty);
				}
				populateBandwidthOverheadWithTrafficShaping(stats, advancedControllableProperties, false, connectionModeValue);
				populatePropertyForSRTMode(stats, advancedControllableProperties);
				stats.remove(streamKey + StreamControllingMetric.STREAMING_DESTINATION_PORT.getName());
				stats.remove(streamKey + StreamControllingMetric.STREAMING_DESTINATION_ADDRESS.getName());
				stats.remove(streamKey + StreamControllingMetric.PARAMETER_VF_ENCRYPTION.getName());
				stats.remove(streamKey + StreamControllingMetric.SAP_TRANSMIT.getName());
				stats.remove(streamKey + StreamControllingMetric.PARAMETER_FEC.getName());
				stats.remove(streamKey + StreamControllingMetric.RTMP_PUBLISH_NAME.getName());
				stats.remove(streamKey + StreamControllingMetric.RTMP_USERNAME.getName());
				stats.remove(streamKey + StreamControllingMetric.RTMP_PASSWORD.getName());
				stats.remove(streamKey + StreamControllingMetric.RTMP_CONFIRMATION_PASSWORD.getName());
				stats.remove(streamKey + StreamControllingMetric.STREAM_SOURCE_TYPE.getName());
				removeControlForSAPMode(stats);
				break;
			default:
				break;
		}
	}

	/**
	 * Clear source Audio before add new audio source
	 *
	 * @param stats the stats are list of stats
	 */
	private void clearSourceAudioBeforeAddNew(Map<String, String> stats) {
		String streamKey = EncoderConstant.CREATE_STREAM + EncoderConstant.HASH;
		for (Entry<String, Audio> audioKey : nameAndSourceAudioMap.entrySet()) {
			String key = audioKey.getKey();
			if (!EncoderConstant.SOURCE_AUDIO_0.equals(key)) {
				stats.remove(streamKey + key);
				nameAndSourceAudioMap.replace(key, null);
			}
		}
	}

	/**
	 * Add new audio And video source
	 *
	 * @param stats the stats are list of stats
	 * @param advancedControllableProperties the advancedControllableProperties is advancedControllableProperties instance
	 */
	private void addNewAudioAndVideoSource(Map<String, String> stats, List<AdvancedControllableProperty> advancedControllableProperties) {
		//AddSourceAudio
		addButtonSourceAudio(stats, advancedControllableProperties);

		addAndUpdateSourceAudio(stats, advancedControllableProperties);
		addAndUpdateVideoSource(stats, advancedControllableProperties);
	}

	/**
	 * Add button source Audio
	 *
	 * @param stats the stats are list of stats
	 * @param advancedControllableProperties the advancedControllableProperties is advancedControllableProperties instance
	 */
	private void addButtonSourceAudio(Map<String, String> stats, List<AdvancedControllableProperty> advancedControllableProperties) {
		String streamKey = EncoderConstant.CREATE_STREAM + EncoderConstant.HASH;
		String addSourceAudioName = streamKey + StreamControllingMetric.ADD_SOURCE_AUDIO.getName();
		stats.put(addSourceAudioName, EncoderConstant.EMPTY_STRING);
		advancedControllableProperties.add(createButton(addSourceAudioName, EncoderConstant.PLUS, EncoderConstant.ADDING, 0));
	}

	/**
	 * Add and update Video source
	 *
	 * @param stats the stats are list of stats
	 * @param advancedControllableProperties the advancedControllableProperties is advancedControllableProperties instance
	 */
	private void addAndUpdateVideoSource(Map<String, String> stats, List<AdvancedControllableProperty> advancedControllableProperties) {
		String streamKey = EncoderConstant.CREATE_STREAM + EncoderConstant.HASH;
		String sourceVideoName = streamKey + StreamControllingMetric.SOURCE_VIDEO.getName();
		String sourceVideoValue = getEmptyValueForNullData(localStatsStreamOutput.get(sourceVideoName));
		String sourceVideo = getEmptyValueForNullData(stats.get(sourceVideoName));
		if (StringUtils.isNullOrEmpty(sourceVideo)) {
			if (StringUtils.isNullOrEmpty(sourceVideoValue)) {
				nameAndVideoConfigMap.put(EncoderConstant.NONE, new VideoConfig());
				String[] videoNames = nameAndVideoConfigMap.keySet().toArray(new String[0]);
				Arrays.sort(videoNames);
				//add video source
				AdvancedControllableProperty videoSourceProperty = controlDropdown(stats, videoNames, sourceVideoName, videoNames[0]);
				addOrUpdateAdvanceControlProperties(advancedControllableProperties, videoSourceProperty);
			} else {
				updateValueForTheControllableProperty(sourceVideoName, sourceVideoValue, stats, advancedControllableProperties);
			}
		}
	}

	/**
	 * Add and update Audio source
	 *
	 * @param stats the stats are list of stats
	 * @param advancedControllableProperties the advancedControllableProperties is advancedControllableProperties instance
	 */
	private void addAndUpdateSourceAudio(Map<String, String> stats, List<AdvancedControllableProperty> advancedControllableProperties) {

		String sourceAudioName = EncoderConstant.CREATE_STREAM + EncoderConstant.HASH + EncoderConstant.SOURCE_AUDIO_0;
		String sourceAudioValue = getEmptyValueForNullData(localStatsStreamOutput.get(sourceAudioName));
		String audioName = getEmptyValueForNullData(stats.get(sourceAudioName));
		if (StringUtils.isNullOrEmpty(audioName)) {
			if (StringUtils.isNullOrEmpty(sourceAudioValue)) {
				addSourceAudioForOutputStream(stats, advancedControllableProperties);
			} else {
				updateValueForTheControllableProperty(sourceAudioName, sourceAudioValue, stats, advancedControllableProperties);
				Audio audio = new Audio();
				audio.setAudioId(nameAndAudioConfigMap.get(sourceAudioValue).getId());
				nameAndSourceAudioMap.put(EncoderConstant.SOURCE_AUDIO_0, audio);
			}
		}
	}

	/**
	 * Add Control for the protocol is SRT with mode Caller
	 *
	 * @param stats the stats are list of stats
	 * @param advancedControllableProperties the advancedControllableProperties is advancedControllableProperties instance
	 */
	private void addControlForSRTWithModeCaller(Map<String, String> stats, List<AdvancedControllableProperty> advancedControllableProperties) {
		//Name Property
		String streamKey = EncoderConstant.CREATE_STREAM + EncoderConstant.HASH;
		String destinationPortName = streamKey + StreamControllingMetric.STREAMING_DESTINATION_PORT.getName();
		String connectionAddressName = streamKey + StreamControllingMetric.STREAM_CONNECTION_ADDRESS.getName();
		String connectionSourcePortName = streamKey + StreamControllingMetric.STREAM_CONNECTION_SOURCE_PORT.getName();
		String connectionDestinationPortName = streamKey + StreamControllingMetric.STREAM_CONNECTION_DESTINATION_PORT.getName();
		String destinationAddressName = streamKey + StreamControllingMetric.STREAMING_DESTINATION_ADDRESS.getName();

		//Value Property
		String connectionAddressValue = getEmptyValueForNullData(localStatsStreamOutput.get(connectionAddressName));
		String connectionDestinationPortValue = getEmptyValueForNullData(localStatsStreamOutput.get(connectionDestinationPortName));
		String connectionSourcePortValue = getEmptyValueForNullData(localStatsStreamOutput.get(connectionSourcePortName));
		AdvancedControllableProperty destinationAddressProperty;
		//add connection destination address
		if (StringUtils.isNullOrEmpty(connectionAddressValue)) {
			connectionAddressValue = getEmptyValueForNullData(localStatsStreamOutput.get(destinationAddressName));
		}
		destinationAddressProperty = controlTextOrNumeric(stats, connectionAddressName, connectionAddressValue, false);
		addOrUpdateAdvanceControlProperties(advancedControllableProperties, destinationAddressProperty);

		//add connection destination port
		if (StringUtils.isNullOrEmpty(connectionDestinationPortValue)) {
			connectionDestinationPortValue = getEmptyValueForNullData(localStatsStreamOutput.get(destinationPortName));
		}
		AdvancedControllableProperty connectionDestinationPortProperty = controlTextOrNumeric(stats, connectionDestinationPortName, connectionDestinationPortValue, true);
		addOrUpdateAdvanceControlProperties(advancedControllableProperties, connectionDestinationPortProperty);

		//Add connection destination source port
		AdvancedControllableProperty connectionSourcePortProperty = controlTextOrNumeric(stats, connectionSourcePortName, connectionSourcePortValue, true);
		addOrUpdateAdvanceControlProperties(advancedControllableProperties, connectionSourcePortProperty);
	}

	/**
	 * Control property with SRT mode protocol
	 *
	 * @param stats the stats are list of stats
	 * @param advancedControllableProperties the advancedControllableProperties is advancedControllableProperties instance
	 */
	private void populatePropertyForSRTMode(Map<String, String> stats, List<AdvancedControllableProperty> advancedControllableProperties) {
		//Add
		String streamKey = EncoderConstant.CREATE_STREAM + EncoderConstant.HASH;
		String networkAdaptiveName = streamKey + StreamControllingMetric.STREAM_NETWORK_ADAPTIVE.getName();
		String latencyName = streamKey + StreamControllingMetric.STREAM_LATENCY.getName();
		String encryptionName = streamKey + StreamControllingMetric.STREAM_ENCRYPTION.getName();
		String passphraseName = streamKey + StreamControllingMetric.STREAM_PASSPHRASE.getName();

		String networkAdaptiveValue = getEmptyValueForNullData(localStatsStreamOutput.get(networkAdaptiveName));
		String latencyValue = getEmptyValueForNullData(localStatsStreamOutput.get(latencyName));
		String encryptionValue = getEmptyValueForNullData(localStatsStreamOutput.get(encryptionName));
		String passphraseValue = getEmptyValueForNullData(localStatsStreamOutput.get(passphraseName));

		//add network Adaptive
		if (StringUtils.isNullOrEmpty(networkAdaptiveValue)) {
			networkAdaptiveValue = String.valueOf(EncoderConstant.ZERO);
		}
		AdvancedControllableProperty networkAdaptiveProperty = controlSwitch(stats, networkAdaptiveName, networkAdaptiveValue, EncoderConstant.DISABLE, EncoderConstant.ENABLE);
		addOrUpdateAdvanceControlProperties(advancedControllableProperties, networkAdaptiveProperty);

		//Add Latency
		if (StringUtils.isNullOrEmpty(latencyValue)) {
			latencyValue = String.valueOf(EncoderConstant.DEFAULT_LATENCY);
		}
		AdvancedControllableProperty latencyProperty = controlTextOrNumeric(stats, latencyName, latencyValue, true);
		addOrUpdateAdvanceControlProperties(advancedControllableProperties, latencyProperty);

		//Add Encryption
		String[] encryptionDropdown = EnumTypeHandler.getEnumNames(EncryptionEnum.class);
		if (StringUtils.isNullOrEmpty(encryptionValue)) {
			encryptionValue = EncoderConstant.NONE;
		}
		AdvancedControllableProperty encryptionProperty = controlDropdownAcceptNoneValue(stats, encryptionDropdown, encryptionName, encryptionValue);
		addOrUpdateAdvanceControlProperties(advancedControllableProperties, encryptionProperty);

		if (!EncoderConstant.NONE.equals(encryptionValue)) {
			AdvancedControllableProperty passphraseProperty = controlTextOrNumeric(stats, passphraseName, passphraseValue, false);
			addOrUpdateAdvanceControlProperties(advancedControllableProperties, passphraseProperty);
		}
	}

	/**
	 * Control property with mode is SAP
	 *
	 * @param value the value is value of SAP transmit
	 * @param property the property is the filed name of controlling metric
	 * @param stats is list of stats
	 * @param advancedControllableProperties the advancedControllableProperties is advancedControllableProperties instance
	 * @param streamName is name of Stream
	 */
	private void populateStreamCreateWithSAPMode(String value, String property, Map<String, String> stats, List<AdvancedControllableProperty> advancedControllableProperties, String streamName) {
		String sapTransmit = value;
		if (StringUtils.isNullOrEmpty(value)) {
			sapTransmit = getEmptyValueForNullData(localStatsStreamOutput.get(property));
			if (StringUtils.isNullOrEmpty(sapTransmit)) {
				sapTransmit = String.valueOf(EncoderConstant.ZERO);
			}
		}

		AdvancedControllableProperty sapTransmitProperty = controlSwitch(stats, property, sapTransmit, EncoderConstant.DISABLE, EncoderConstant.ENABLE);
		addOrUpdateAdvanceControlProperties(advancedControllableProperties, sapTransmitProperty);
		removeControlForSAPMode(stats);
		if (sapTransmit.equals(String.valueOf(EncoderConstant.NUMBER_ONE))) {

			String keyProperty = streamName + EncoderConstant.HASH;
			String sapName = keyProperty + StreamControllingMetric.SAP_NAME.getName();
			String keywordsName = keyProperty + StreamControllingMetric.SAP_KEYWORDS.getName();
			String descName = keyProperty + StreamControllingMetric.SAP_DESCRIPTION.getName();
			String authorName = keyProperty + StreamControllingMetric.SAP_AUTHOR.getName();
			String copyrightName = keyProperty + StreamControllingMetric.SAP_COPYRIGHT.getName();
			String addressName = keyProperty + StreamControllingMetric.SAP_ADDRESS.getName();
			String portName = keyProperty + StreamControllingMetric.SAP_PORT.getName();
			String sapNameValue = getEmptyValueForNullData(localStatsStreamOutput.get(sapName));
			String keywordsNameValue = getEmptyValueForNullData(localStatsStreamOutput.get(keywordsName));
			String descNameValue = getEmptyValueForNullData(localStatsStreamOutput.get(descName));
			String authorNameValue = getEmptyValueForNullData(localStatsStreamOutput.get(authorName));
			String copyrightNameValue = getEmptyValueForNullData(localStatsStreamOutput.get(copyrightName));
			String addressNameValue = getEmptyValueForNullData(localStatsStreamOutput.get(addressName));
			String portNameValue = getEmptyValueForNullData(localStatsStreamOutput.get(portName));

			//add sapName
			AdvancedControllableProperty sapNameControlProperty = controlTextOrNumeric(stats, sapName, sapNameValue, false);
			addOrUpdateAdvanceControlProperties(advancedControllableProperties, sapNameControlProperty);

			//add keywordsName
			AdvancedControllableProperty sapKeywordsControlProperty = controlTextOrNumeric(stats, keywordsName, keywordsNameValue, false);
			addOrUpdateAdvanceControlProperties(advancedControllableProperties, sapKeywordsControlProperty);

			//add descName
			AdvancedControllableProperty sapDescControlProperty = controlTextOrNumeric(stats, descName, descNameValue, false);
			addOrUpdateAdvanceControlProperties(advancedControllableProperties, sapDescControlProperty);

			//add authorName
			AdvancedControllableProperty sapAuthorControlProperty = controlTextOrNumeric(stats, authorName, authorNameValue, false);
			addOrUpdateAdvanceControlProperties(advancedControllableProperties, sapAuthorControlProperty);

			//add copyrightName
			AdvancedControllableProperty sapCopyrightControlProperty = controlTextOrNumeric(stats, copyrightName, copyrightNameValue, false);
			addOrUpdateAdvanceControlProperties(advancedControllableProperties, sapCopyrightControlProperty);

			//add addressName
			AdvancedControllableProperty sapAddressControlProperty = controlTextOrNumeric(stats, addressName, addressNameValue, false);
			addOrUpdateAdvanceControlProperties(advancedControllableProperties, sapAddressControlProperty);

			//add portName
			if (StringUtils.isNullOrEmpty(portNameValue)) {
				portNameValue = String.valueOf(EncoderConstant.DEFAULT_SAP_PORT);
			}
			AdvancedControllableProperty sapPortControlProperty = controlTextOrNumeric(stats, portName, portNameValue, true);
			addOrUpdateAdvanceControlProperties(advancedControllableProperties, sapPortControlProperty);
		}
	}

	/**
	 * Remove property for SAP mode
	 *
	 * @param stats the stats are list of stats
	 */
	private void removeControlForSAPMode(Map<String, String> stats) {
		String keyProperty = EncoderConstant.CREATE_STREAM + EncoderConstant.HASH;
		String sapName = keyProperty + StreamControllingMetric.SAP_NAME.getName();
		String keywordsName = keyProperty + StreamControllingMetric.SAP_KEYWORDS.getName();
		String descName = keyProperty + StreamControllingMetric.SAP_DESCRIPTION.getName();
		String authorName = keyProperty + StreamControllingMetric.SAP_AUTHOR.getName();
		String copyrightName = keyProperty + StreamControllingMetric.SAP_COPYRIGHT.getName();
		String addressName = keyProperty + StreamControllingMetric.SAP_ADDRESS.getName();
		String portName = keyProperty + StreamControllingMetric.SAP_PORT.getName();

		stats.remove(sapName);
		stats.remove(keywordsName);
		stats.remove(descName);
		stats.remove(authorName);
		stats.remove(copyrightName);
		stats.remove(addressName);
		stats.remove(portName);
	}

	/**
	 * Control property with mode is Traffic Shaping
	 *
	 * @param property the property is the filed name of controlling metric
	 * @param value the value is value of metric
	 * @param stats is list of stats
	 * @param advancedControllableProperties the advancedControllableProperties is advancedControllableProperties instance
	 * @param streamName is name of Stream
	 * @param isTrafficShaping boolean type , if isTrafficShaping is true update bandwidthOverhead with old value or default value, if isTrafficShaping is false update default value
	 */
	private void populateStreamCreateModeTrafficShapingMode(String property, String value, Map<String, String> stats, List<AdvancedControllableProperty> advancedControllableProperties,
			String streamName, boolean isTrafficShaping) {
		updateValueForTheControllableProperty(property, value, stats, advancedControllableProperties);
		String idleCellsName = streamName + EncoderConstant.HASH + StreamControllingMetric.PARAMETER_IDLE_CELLS.getName();
		String delayedAudioName = streamName + EncoderConstant.HASH + StreamControllingMetric.PARAMETER_DELAYED_AUDIO.getName();
		String bandwidthOverheadName = streamName + EncoderConstant.HASH + StreamControllingMetric.PARAMETER_BANDWIDTH_OVERHEAD.getName();

		//remove idleCells, delayedAudio, bandwidthOverhead control if shaping disable
		stats.remove(idleCellsName);
		stats.remove(delayedAudioName);
		stats.remove(bandwidthOverheadName);

		String protocolMode = getEmptyValueForNullData(localStatsStreamOutput.get(streamName + EncoderConstant.HASH + StreamControllingMetric.STREAMING_PROTOCOL.getName()));
		if (value.equals(String.valueOf(EncoderConstant.NUMBER_ONE))) {
			String idleCellsValue = localStatsStreamOutput.get(idleCellsName);
			String delayedAudioValue = localStatsStreamOutput.get(delayedAudioName);

			if (!ProtocolEnum.DIRECT_RTP.getName().equals(protocolMode) && !ProtocolEnum.RTMP.getName().equals(protocolMode)) {
				//add idleCells
				if (idleCellsValue == null) {
					idleCellsValue = String.valueOf(EncoderConstant.ZERO);
				}
				AdvancedControllableProperty idleCellsControlProperty = controlSwitch(stats, idleCellsName, idleCellsValue, EncoderConstant.DISABLE, EncoderConstant.ENABLE);
				addOrUpdateAdvanceControlProperties(advancedControllableProperties, idleCellsControlProperty);

				//add delayedAudio
				if (delayedAudioValue == null) {
					delayedAudioValue = String.valueOf(EncoderConstant.ZERO);
				}
				AdvancedControllableProperty delayedAudioControlProperty = controlSwitch(stats, delayedAudioName, delayedAudioValue, EncoderConstant.DISABLE, EncoderConstant.ENABLE);
				addOrUpdateAdvanceControlProperties(advancedControllableProperties, delayedAudioControlProperty);
			}
			populateBandwidthOverheadWithTrafficShaping(stats, advancedControllableProperties, isTrafficShaping, protocolMode);
		}
	}

	/**
	 * populate bandwidth over head with traffic shaping
	 * if traffic shaping is true get default bandwidth over head is 15 or old value
	 * if traffic shaping is false get default bandwidth overhead is 25
	 *
	 * @param stats list of stats
	 * @param advancedControllableProperties the advancedControllableProperties is advancedControllableProperties instance
	 * @param isTrafficShaping boolean type , if isTrafficShaping is true update bandwidthOverhead with old value or default value, if isTrafficShaping is false update default value
	 * @param protocolMode is value instance in ProtocolEnum
	 */
	private void populateBandwidthOverheadWithTrafficShaping(Map<String, String> stats, List<AdvancedControllableProperty> advancedControllableProperties, boolean isTrafficShaping,
			String protocolMode) {
		String bandwidthOverheadName = EncoderConstant.CREATE_STREAM + EncoderConstant.HASH + StreamControllingMetric.PARAMETER_BANDWIDTH_OVERHEAD.getName();
		String bandwidthOverheadValue = localStatsStreamOutput.get(bandwidthOverheadName);
		String defaultBandwidth = String.valueOf(EncoderConstant.DEFAULT_BANDWIDTH_OVERHEAD);
		if (ProtocolEnum.TS_SRT.getName().equals(protocolMode)) {
			defaultBandwidth = String.valueOf(EncoderConstant.DEFAULT_BANDWIDTH_OVERHEAD_SRT);
		}
		AdvancedControllableProperty bandwidthOverheadProperty = controlTextOrNumeric(stats, bandwidthOverheadName, defaultBandwidth, true);
		addOrUpdateAdvanceControlProperties(advancedControllableProperties, bandwidthOverheadProperty);
		if (isTrafficShaping) {
			//add bandwidth overhead
			if (bandwidthOverheadValue == null) {
				bandwidthOverheadValue = defaultBandwidth;
			}
			bandwidthOverheadProperty = controlTextOrNumeric(stats, bandwidthOverheadName, bandwidthOverheadValue, true);
			addOrUpdateAdvanceControlProperties(advancedControllableProperties, bandwidthOverheadProperty);
		}
	}

	/**
	 * Send command to create new stream
	 *
	 * @param streamConfig the streamConfig is instance in StreamConfig
	 * @throws ResourceNotReachableException if create new stream failed
	 */
	private void sendCommandToCreateStream(StreamConfig streamConfig) {
		String data = streamConfig.toString();
		String request = EncoderCommand.OPERATION_STREAM.getName() + EncoderCommand.OPERATION_CREATE.getName() + data;
		try {
			String responseData = send(request.replace("\r", EncoderConstant.EMPTY_STRING));
			if (!responseData.contains(EncoderConstant.SUCCESS_RESPONSE)) {
				throw new ResourceNotReachableException(responseData);
			}
			StreamSAP streamSAP = streamConfig.getSap();
			if (streamSAP != null && !StringUtils.isNullOrEmpty(streamSAP.getName())) {
				sendCommandToCreateStreamTransmitSAP(responseData, streamSAP);
			}
		} catch (Exception e) {
			throw new ResourceNotReachableException("Error while creating new stream: " + e.getMessage(), e);
		}
	}

	/**
	 * Send command to create new stream
	 *
	 * @param data the data is response data of the device
	 * @param streamSAP the streamSAP is instance in StreamSAP DTO
	 * @throws ResourceNotReachableException if create new stream failed
	 */
	private void sendCommandToCreateStreamTransmitSAP(String data, StreamSAP streamSAP) {
		String idStream = getIdStreamFromResponse(data);
		String dataRequest = streamSAP.toString();
		String request =
				EncoderCommand.OPERATION_SESSION.getName() + EncoderCommand.OPERATION_CREATE.getName() + EncoderCommand.OPERATION_STREAM.getName().trim() + EncoderConstant.EQUAL + idStream + dataRequest;
		try {
			String responseData = send(request.replace("\r", EncoderConstant.EMPTY_STRING));
			if (!responseData.contains(EncoderConstant.SUCCESS_RESPONSE)) {
				throw new ResourceNotReachableException(String.format("Create stream successfully with stream ID: %s. But can't create Transmit SAP session for stream", idStream) + responseData);
			}
		} catch (Exception e) {
			throw new ResourceNotReachableException("Error while creating new SAP: " + e.getMessage(), e);
		}
	}

	/**
	 * Get ID Stream from responseData
	 *
	 * @param responseData the responseData is data received from device
	 * @return String is id of stream
	 * @throws ResourceNotReachableException if get stream id failed
	 */
	private String getIdStreamFromResponse(String responseData) {
		//Example responseData: stream create  name=TEST000s2 \r\n Stream created successfully - ID: 1. \r\n etc
		try {
			String[] sapIdList = responseData.split(EncoderConstant.REGEX_DATA);
			for (String sapId : sapIdList) {
				if (sapId.contains(EncoderConstant.STREAM_CREATE_RESPONSE)) {
					String[] idList = sapId.split(EncoderConstant.SPACE);
					return idList[idList.length - 1].replace(EncoderConstant.DOT, EncoderConstant.EMPTY_STRING).trim();
				}
			}
			throw new ResourceNotReachableException("Error while getting id stream to response");
		} catch (Exception e) {
			throw new ResourceNotReachableException(e.getMessage(), e);
		}
	}

	/**
	 * Convert StreamConfig by value
	 *
	 * @param stats the stats are list of stats
	 * @return StreamConfig is instance in StreamConfig
	 * @throws ResourceNotReachableException if stream name with mode RTMP is empty or Encryption different none and passphrase is empty
	 */
	private StreamConfig convertStreamConfigByValue(Map<String, String> stats) {
		String streamName = EncoderConstant.CREATE_STREAM + EncoderConstant.HASH;

		StreamConfig streamConfig = new StreamConfig();
		String contentValue = stats.get(streamName + StreamControllingMetric.NAME.getName());
		String fecValue = stats.get(streamName + StreamControllingMetric.PARAMETER_FEC.getName());
		String mtuValue = stats.get(streamName + StreamControllingMetric.PARAMETER_MTU.getName());
		String ttlValue = stats.get(streamName + StreamControllingMetric.PARAMETER_TTL.getName());
		String tosValue = stats.get(streamName + StreamControllingMetric.PARAMETER_TOS.getName());
		String sourceVideoValue = stats.get(streamName + StreamControllingMetric.SOURCE_VIDEO.getName());
		String protocolValue = stats.get(streamName + StreamControllingMetric.STREAMING_PROTOCOL.getName());
		String portValue = localStatsStreamOutput.get(streamName + StreamControllingMetric.STREAMING_DESTINATION_PORT.getName());
		String addressValue = localStatsStreamOutput.get(streamName + StreamControllingMetric.STREAMING_DESTINATION_ADDRESS.getName());
		String trafficShapingValue = stats.get(streamName + StreamControllingMetric.PARAMETER_TRAFFIC_SHAPING.getName());
		String stillImageValue = stats.get(streamName + StreamControllingMetric.STILL_IMAGE.getName());
		String idleCellsValue = stats.get(streamName + StreamControllingMetric.PARAMETER_IDLE_CELLS.getName());
		String delayedAudioValue = stats.get(streamName + StreamControllingMetric.PARAMETER_DELAYED_AUDIO.getName());
		String bandwidthOverHeadValue = stats.get(streamName + StreamControllingMetric.PARAMETER_BANDWIDTH_OVERHEAD.getName());
		String connectionValue = stats.get(streamName + StreamControllingMetric.STREAM_CONNECTION_MODE.getName());
		String sourcePortValue = stats.get(streamName + StreamControllingMetric.STREAM_CONNECTION_SOURCE_PORT.getName());
		String encryptionValue = getEmptyValueForNullData(stats.get(streamName + StreamControllingMetric.STREAM_ENCRYPTION.getName()));
		String passPhrase = getEmptyValueForNullData(stats.get(streamName + StreamControllingMetric.STREAM_PASSPHRASE.getName()));
		String publishName = getEmptyValueForNullData(stats.get(streamName + StreamControllingMetric.RTMP_PUBLISH_NAME.getName()));
		String userName = getEmptyValueForNullData(stats.get(streamName + StreamControllingMetric.RTMP_USERNAME.getName()));
		String password = getEmptyValueForNullData(stats.get(streamName + StreamControllingMetric.RTMP_PASSWORD.getName()));
		String comfirmPassword = getEmptyValueForNullData(stats.get(streamName + StreamControllingMetric.RTMP_CONFIRMATION_PASSWORD.getName()));
		VideoConfig videoConfig = nameAndVideoConfigMap.get(sourceVideoValue);
		String videoId = EncoderConstant.EMPTY_STRING;
		if (videoConfig != null) {
			videoId = videoConfig.getId();
		}
		trafficShapingValue = convertByState(trafficShapingValue, false);
		idleCellsValue = convertByState(idleCellsValue, false);
		delayedAudioValue = convertByState(delayedAudioValue, false);
		stillImageValue = getDefaultValueForNullOrNoneData(stillImageValue, false);
		protocolValue = ProtocolEnum.getProtocolEnumByName(protocolValue);
		String fecMode = EncoderConstant.EMPTY_STRING;
		List<Audio> audioList = new ArrayList<>();
		for (Audio audioName : nameAndSourceAudioMap.values()) {
			if (audioName != null && audioName.getAudioId() != null) {
				audioList.add(audioName);
			}
		}
		if (!FecEnum.NONE.getName().equals(fecValue)) {
			fecMode = EncoderConstant.YES;
		}
		if (!StringUtils.isNullOrEmpty(stillImageValue)) {
			//subString from 0 to .pm4 of stillImageValue with format havisionImage.pm4 => return haivisionImage
			stillImageValue = stillImageValue.substring(0, stillImageValue.lastIndexOf(EncoderConstant.MP4) - 1);
		}
		if (ProtocolEnum.TS_SRT.getValue().equals(protocolValue) && ConnectionModeEnum.LISTENER.getName().equals(connectionValue)) {
			addressValue = EncoderConstant.EMPTY_STRING;
		}
		if (ProtocolEnum.RTMP.getValue().equals(protocolValue)) {
			validateRTMPAddress(addressValue);
			if (StringUtils.isNullOrEmpty(contentValue)) {
				throw new ResourceNotReachableException("Error while creating new stream, stream name can't empty");
			}
			if (!password.equals(comfirmPassword)) {
				throw new ResourceNotReachableException("Password and Confirmation Password do not match");
			}
		}
		if ((!ProtocolEnum.TS_SRT.getValue().equals(protocolValue) || !ConnectionModeEnum.LISTENER.getName().equals(connectionValue)) && StringUtils.isNullOrEmpty(addressValue)) {
			throw new ResourceNotReachableException("Address can't null or empty. Address length must be between 1 and 511 characters.");
		}
		if (!StringUtils.isNullOrEmpty(encryptionValue) && !EncoderConstant.NONE.equals(encryptionValue) && (StringUtils.isNullOrEmpty(passPhrase)
				|| passPhrase.length() <= EncoderConstant.MIN_PASSPHRASE)) {
			throw new ResourceNotReachableException("PassPhrase can't null or empty. Passphrase length must be between 10 and 79 characters.");
		}
		if (StringUtils.isNullOrEmpty(portValue) && !ProtocolEnum.RTMP.getValue().equals(protocolValue)) {
			throw new ResourceNotReachableException("Port can't null or empty. Please re-enter the port.");
		}
		if (ProtocolEnum.DIRECT_RTP.getValue().equals(protocolValue) || ProtocolEnum.TS_RTP.getValue().equals(protocolValue)) {
			int port = Integer.parseInt(portValue);
			if (port % 2 != 0) {
				throw new ResourceNotReachableException("Even numbered UDP port required");
			}
		}

		if (ProtocolEnum.RTMP.getValue().equals(protocolValue)) {
			streamConfig.setPublishName(publishName);
			streamConfig.setUsername(userName);
			streamConfig.setPassword(password);
		}
		streamConfig.setPassphrase(passPhrase);
		streamConfig.setAesEncryption(encryptionValue);
		StreamSAP streamSAP = getStreamSAPFromStatsByStreamName(stats, streamName);
		streamConfig.setFec(fecMode);
		streamConfig.setSap(streamSAP);
		streamConfig.setMtu(mtuValue);
		streamConfig.setTtl(ttlValue);
		streamConfig.setTos(tosValue);
		streamConfig.setVideo(videoId);
		streamConfig.setPort(portValue);
		streamConfig.setSourcePort(sourcePortValue);
		streamConfig.setAudioList(audioList);
		streamConfig.setAddress(addressValue);
		streamConfig.setSrtMode(connectionValue);
		streamConfig.setEncapsulation(protocolValue);
		streamConfig.setShaping(trafficShapingValue);
		streamConfig.setName(contentValue);
		streamConfig.setStillImageFile(stillImageValue);
		streamConfig.setIdleCells(idleCellsValue);
		streamConfig.setDelayAudio(delayedAudioValue);
		streamConfig.setBandwidthOverhead(bandwidthOverHeadValue);

		return streamConfig;
	}

	/**
	 * Correct Ip address with format rtmp://addressName/
	 *
	 * @param addressValue the addressValue is an address
	 * @throws ResourceNotReachableException if Ip address invalid value
	 */
	private void validateRTMPAddress(String addressValue) {
		try {
			if (StringUtils.isNullOrEmpty(addressValue)) {
				throw new ResourceNotReachableException("Invalid IP Address, IP address can't empty.");
			}
			int lenIpFormat = EncoderConstant.ADDRESS_FORMAT.length();
			int lenIpAddress = addressValue.length() - 1;
			if (addressValue.length() < lenIpFormat) {
				throw new ResourceNotReachableException("Invalid IP Address, IP Address must begin rtmp://IpAddress:port/uri");
			}
			String formatAddress = addressValue.substring(0, lenIpFormat);
			String ipAddress = addressValue.substring(lenIpFormat, lenIpAddress);
			if (!EncoderConstant.ADDRESS_FORMAT.equals(formatAddress) || StringUtils.isNullOrEmpty(ipAddress)) {
				throw new ResourceNotReachableException("Invalid stream address contains invalid characters. IP Address format rtmp://IpAddress:port/uri");
			}
		} catch (Exception e) {
			throw new ResourceNotReachableException("Error while creating IP Address " + e.getMessage(), e);
		}
	}


	/**
	 * Get stream SAP from stats by stream name
	 *
	 * @param stats is list of stats
	 * @param streamName the streamName is name of stream
	 * @return StreamSAP the StreamSAP instance in StreamSAP DTO
	 * @throws ResourceNotReachableException if SAP name is Null or Empty
	 */
	private StreamSAP getStreamSAPFromStatsByStreamName(Map<String, String> stats, String streamName) {
		StreamSAP streamSAP = new StreamSAP();
		String transmitSAP = convertByState(stats.get(streamName + StreamControllingMetric.SAP_TRANSMIT.getName()), false);
		if (transmitSAP.equals(EncoderConstant.ON)) {
			String sapName = stats.get(streamName + StreamControllingMetric.SAP_NAME.getName());
			String sapKeywords = stats.get(streamName + StreamControllingMetric.SAP_KEYWORDS.getName());
			String sapDesc = stats.get(streamName + StreamControllingMetric.SAP_DESCRIPTION.getName());
			String sapCopyright = stats.get(streamName + StreamControllingMetric.SAP_COPYRIGHT.getName());
			String sapAddress = stats.get(streamName + StreamControllingMetric.SAP_ADDRESS.getName());
			String sapPort = stats.get(streamName + StreamControllingMetric.SAP_PORT.getName());
			String sapAuthor = stats.get(streamName + StreamControllingMetric.SAP_AUTHOR.getName());

			if (StringUtils.isNullOrEmpty(sapName)) {
				throw new ResourceNotReachableException("Create stream failed, the SAP name can't Null or Empty");
			}
			streamSAP.setAdvertise(EncoderConstant.YES);
			streamSAP.setName(sapName);
			streamSAP.setKeywords(sapKeywords);
			streamSAP.setDesc(sapDesc);
			streamSAP.setCopyright(sapCopyright);
			streamSAP.setAddress(sapAddress);
			streamSAP.setPort(sapPort);
			streamSAP.setAuthor(sapAuthor);
		}
		return streamSAP;
	}

	/**
	 * Get TOS by Range
	 *
	 * @param value is tos instance
	 * @return String is value, if value out of range return min or max value
	 * @throws NumberFormatException if convert tos failed
	 */
	private String getTOSValueByRange(String value) {
		try {
			int defaultValue = 0;
			if (value.startsWith(EncoderConstant.HEX_PREFIX)) {
				defaultValue = Integer.parseInt(value.replace(EncoderConstant.HEX_PREFIX, EncoderConstant.EMPTY_STRING), 16);
			} else {
				defaultValue = (int) Float.parseFloat(value);
			}
			String defaultHexValue = EncoderConstant.HEX_PREFIX + String.format("%02X", 0xFF & defaultValue);
			if (defaultValue < Integer.parseInt(EncoderConstant.MIN_TOS, 16)) {
				defaultHexValue = EncoderConstant.HEX_PREFIX + EncoderConstant.MIN_TOS;
			}
			if (defaultValue > Integer.parseInt(EncoderConstant.MAX_TOS, 16)) {
				defaultHexValue = EncoderConstant.HEX_PREFIX + EncoderConstant.MAX_TOS;
			}
			return defaultHexValue;
		} catch (Exception var60) {
			throw new NumberFormatException("Value of ParameterToS is invalid. TOS must be hex value range to 00-FF");
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
	 * Add text or numeric is control property for metric
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
		return new AdvancedControllableProperty(name, new Date(), button, EncoderConstant.EMPTY_STRING);
	}
	//--------------------------------------------------------------------------------------------------------------------------------
	//endregion
}
