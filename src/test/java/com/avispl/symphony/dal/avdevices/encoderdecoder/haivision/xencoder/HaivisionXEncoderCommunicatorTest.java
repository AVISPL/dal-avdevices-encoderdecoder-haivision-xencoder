/*
 * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */

package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder;

import java.util.Collections;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.avispl.symphony.api.dal.dto.control.ControllableProperty;
import com.avispl.symphony.api.dal.dto.monitor.ExtendedStatistics;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.common.AudioControllingMetric;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.common.AudioMonitoringMetric;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.common.EncoderConstant;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.common.EncoderMonitoringMetric;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.common.StreamMonitoringMetric;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.common.SystemMonitoringMetric;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.common.VideoMonitoringMetric;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dropdownlist.AlgorithmDropdown;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dropdownlist.AudioActionDropdown;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dropdownlist.BitRateDropdown;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dropdownlist.ChannelModeDropdown;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dropdownlist.InputDropdown;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dropdownlist.LanguageDropdown;

/**
 * Unit test for HaivisionXEncoderCommunicator
 *
 * @author Kevin / Symphony Dev Team<br>
 * Created on 4/19/2022
 * @since 1.0.0
 */
public class HaivisionXEncoderCommunicatorTest {
	private HaivisionXEncoderCommunicator haivisionXEncoderCommunicator;

	@BeforeEach()
	public void setUp() throws Exception {
		haivisionXEncoderCommunicator = new HaivisionXEncoderCommunicator();
		haivisionXEncoderCommunicator.setConfigManagement("true");
		haivisionXEncoderCommunicator.setHost("***REMOVED***");
		haivisionXEncoderCommunicator.setPort(22);
		haivisionXEncoderCommunicator.setLogin("admin");
		haivisionXEncoderCommunicator.setPassword("AVIadm1n");
		haivisionXEncoderCommunicator.setCommandErrorList(Collections.singletonList("~"));
		haivisionXEncoderCommunicator.setCommandSuccessList(Collections.singletonList("~$ "));
		haivisionXEncoderCommunicator.setLoginSuccessList(Collections.singletonList("~$ "));
		haivisionXEncoderCommunicator.init();
		haivisionXEncoderCommunicator.connect();
	}

	@AfterEach()
	public void destroy() throws Exception {
		haivisionXEncoderCommunicator.disconnect();
	}

	/**
	 * Test get statistics: with System info
	 *
	 * @throws Exception When fail to getMultipleStatistics
	 */
	@Test
	@Tag("RealDevice")
	void testGetMultipleStatisticsWithSystemInfo() throws Exception {
		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		for (SystemMonitoringMetric metric : SystemMonitoringMetric.values()) {
			Assertions.assertNotNull(stats.get(metric.getName()));
		}
	}

	/**
	 * Test get statistics: with audio statistics
	 *
	 * @throws Exception When fail to getMultipleStatistics
	 */
	@Test
	@Tag("RealDevice")
	void testGetMultipleStatisticsWithAudioStatistics() throws Exception {
		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		for (AudioMonitoringMetric metric : AudioMonitoringMetric.values()) {
			Assertions.assertNotNull(stats.get("Audio Encoder 0 Statistics" + EncoderConstant.HASH + metric.getName()));
			Assertions.assertNotNull(stats.get("Audio Encoder 1 Statistics" + EncoderConstant.HASH + metric.getName()));
			Assertions.assertNotNull(stats.get("Audio Encoder 2 Statistics" + EncoderConstant.HASH + metric.getName()));
			Assertions.assertNotNull(stats.get("Audio Encoder 3 Statistics" + EncoderConstant.HASH + metric.getName()));
			Assertions.assertNotNull(stats.get("Audio Encoder 4 Statistics" + EncoderConstant.HASH + metric.getName()));
			Assertions.assertNotNull(stats.get("Audio Encoder 5 Statistics" + EncoderConstant.HASH + metric.getName()));
			Assertions.assertNotNull(stats.get("Audio Encoder 6 Statistics" + EncoderConstant.HASH + metric.getName()));
			Assertions.assertNotNull(stats.get("Audio Encoder 7 Statistics" + EncoderConstant.HASH + metric.getName()));
		}
	}

	/**
	 * Test get statistics: with Video statistics
	 *
	 * @throws Exception When fail to getMultipleStatistics
	 */
	@Test
	@Tag("RealDevice")
	void testGetMultipleStatisticsWithVideoStatistics() throws Exception {
		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		for (VideoMonitoringMetric metric : VideoMonitoringMetric.values()) {
			Assertions.assertNotNull(stats.get("HD Video Encoder 0 Statistics" + EncoderConstant.HASH + metric.getName()));
			Assertions.assertNotNull(stats.get("HD Video Encoder 1 Statistics" + EncoderConstant.HASH + metric.getName()));
			Assertions.assertNotNull(stats.get("HD Video Encoder 2 Statistics" + EncoderConstant.HASH + metric.getName()));
			Assertions.assertNotNull(stats.get("HD Video Encoder 3 Statistics" + EncoderConstant.HASH + metric.getName()));
		}
	}

	/**
	 * Test get statistics: with stream statistics
	 *
	 * @throws Exception When fail to getMultipleStatistics
	 */
	@Test
	@Tag("RealDevice")
	void testGetMultipleStatisticsWithStreamStatistics() throws Exception {
		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		for (StreamMonitoringMetric metric : StreamMonitoringMetric.values()) {
			Assertions.assertNotNull(stats.get("Stream Ivantest02 Statistics" + EncoderConstant.HASH + metric.getName()));
		}
	}

	/**
	 * Test get statistics: with temperatures status
	 *
	 * @throws Exception When fail to getMultipleStatistics
	 */
	@Test
	@Tag("RealDevice")
	void testGetMultipleStatisticsWithTemperatureStatus() throws Exception {
		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		Assertions.assertNotNull(stats.get(EncoderMonitoringMetric.TEMPERATURE.getName()));
	}

	/**
	 * Test get Audio control: with Input properties is SDI 1 (1-2)
	 *
	 * @throws Exception When fail to controlProperty
	 */
	@Test
	@Tag("RealDevice")
	void testControlInput() throws Exception {
		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		ControllableProperty controllableProperty = new ControllableProperty();
		String propName = "Audio Encoder 0#" + AudioControllingMetric.INPUT.getName();
		String propValue = InputDropdown.SDI_1_1_2.getValue();
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);

		Assertions.assertEquals(propValue,stats.get(propName));
	}

	/**
	 * Test get Audio control: with Channel Mode properties is stereo
	 *
	 * @throws Exception When fail to controlProperty
	 */
	@Test
	@Tag("RealDevice")
	void testControlChannelModeIsStereo() throws Exception {
		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		ControllableProperty controllableProperty = new ControllableProperty();
		String propName = "Audio Encoder 0#" + AudioControllingMetric.CHANGE_MODE.getName();
		String propValue = ChannelModeDropdown.STEREO.getValue();
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);

		Assertions.assertEquals(propValue,stats.get(propName));
	}

	/**
	 * Test get Audio control: with Bitrate properties is 128 kbps
	 *
	 * @throws Exception When fail to controlProperty
	 */
	@Test
	@Tag("RealDevice")
	void testControlBitrate() throws Exception {
		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		ControllableProperty controllableProperty = new ControllableProperty();
		String propName = "Audio Encoder 0#" + AudioControllingMetric.BITRATE.getName();
		String propValue = BitRateDropdown.NUMBER_128.getValue();
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);

		Assertions.assertEquals(propValue,stats.get(propName));
	}

	/**
	 * Test get Audio control: with Algorithm properties is MPEG-4 LOAS/LATM
	 *
	 * @throws Exception When fail to controlProperty
	 */
	@Test
	@Tag("RealDevice")
	void testControlAlgorithmModeIsLOAS() throws Exception {
		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		ControllableProperty controllableProperty = new ControllableProperty();
		String propName = "Audio Encoder 0#" + AudioControllingMetric.ALGORITHM.getName();
		String propValue = AlgorithmDropdown.MPEG_4.getName();
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);

		Assertions.assertEquals(propValue,stats.get(propName));
	}

	/**
	 * Test get Audio control: with Language properties is English
	 *
	 * @throws Exception When fail to controlProperty
	 */
	@Test
	@Tag("RealDevice")
	void testControlLanguageIsEnglish() throws Exception {
		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		ControllableProperty controllableProperty = new ControllableProperty();
		String propName = "Audio Encoder 0#" + AudioControllingMetric.LANGUAGE.getName();
		String propValue = LanguageDropdown.ENGLISH.getValue();
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);

		Assertions.assertEquals(propValue,stats.get(propName));
	}

	/**
	 * Test get Audio control: with Action properties is Start
	 *
	 * @throws Exception When fail to controlProperty
	 */
	@Test
	@Tag("RealDevice")
	void testControlActionIsStart() throws Exception {
		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		ControllableProperty controllableProperty = new ControllableProperty();
		String propName = "Audio Encoder 0#" + AudioControllingMetric.ACTION.getName();
		String propValue = AudioActionDropdown.START.getName();
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);

		Assertions.assertEquals(propValue,stats.get(propName));
	}


	/**
	 * Test get Audio control: with Apply Change properties
	 *
	 * @throws Exception When fail to controlProperty
	 */
	@Test
	@Tag("RealDevice")
	void testControlApplyChange() throws Exception {
		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		ControllableProperty controllableProperty = new ControllableProperty();
		String propName = "HD Video Encoder 0#BitRate";
		String propValue = "-2";
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);

		String propNameAction = "Audio Encoder 0#" + AudioControllingMetric.ACTION.getName();
		String propValueAction = AudioActionDropdown.START.getName();
		controllableProperty.setProperty(propNameAction);
		controllableProperty.setValue(propValueAction);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);

		String propNameApplyChange = "Audio Encoder 0#" + AudioControllingMetric.APPLY_CHANGE.getName();
		String propValueApplyChange = "1";
		controllableProperty.setProperty(propNameApplyChange);
		controllableProperty.setValue(propValueApplyChange);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);

		Assertions.assertEquals(propValue,stats.get(propName));
		Assertions.assertEquals(propValueAction,stats.get(propNameAction));
	}
}
