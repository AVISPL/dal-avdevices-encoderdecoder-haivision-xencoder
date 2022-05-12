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
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.common.VideoControllingMetric;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.common.VideoMonitoringMetric;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dropdownlist.AlgorithmDropdown;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dropdownlist.AudioActionDropdown;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dropdownlist.BitRateDropdown;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dropdownlist.ChannelModeDropdown;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dropdownlist.EntropyCodingDropdown;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dropdownlist.InputDropdown;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dropdownlist.LanguageDropdown;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dropdownlist.ResolutionDropdown;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dropdownlist.TimeCodeSource;

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
		haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		ControllableProperty controllableProperty = new ControllableProperty();
		String propName = "HD Video Encoder 0#BitRate";
		String propValue = "2";
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

		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();

		Assertions.assertEquals(propValue,stats.get(propName));
		Assertions.assertEquals(propValueAction,stats.get(propNameAction));
	}

	// UT for video Control--------------------------------------------------------------------------------------------------------------------------

	/**
	 * Test get Video control: with Input properties is BNC-1
	 *
	 * @throws Exception When fail to controlProperty
	 */
	@Test
	@Tag("RealDevice")
	void testVideoControlInput() throws Exception {
		haivisionXEncoderCommunicator.getMultipleStatistics();
		ControllableProperty controllableProperty = new ControllableProperty();
		String propName = "HD Video Encoder 0#" +VideoControllingMetric.INPUT.getName();
		String propValue = "BNC-1";
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);

		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats  = extendedStatistics.getStatistics();
		Assertions.assertEquals(propValue,stats.get(propName));
	}

	/**
	 * Test get Video control: with BitRate properties in range 32-25000
	 *
	 * @throws Exception When fail to controlProperty
	 */
	@Test
	@Tag("RealDevice")
	void testVideoControlBitRateInRange() throws Exception {
		haivisionXEncoderCommunicator.getMultipleStatistics();
		ControllableProperty controllableProperty = new ControllableProperty();
		String propName = "HD Video Encoder 0#" + VideoControllingMetric.BITRATE.getName();
		String propValue = "46";
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);

		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		Assertions.assertEquals(propValue,stats.get(propName));
	}

	/**
	 * Test get Video control: with BitRate properties out off min range <32
	 *
	 * Expect bitRate to take the minimum value of 32
	 * @throws Exception When fail to controlProperty
	 */
	@Test
	@Tag("RealDevice")
	void testVideoControlBitRateOutOfMinRange() throws Exception {
		haivisionXEncoderCommunicator.getMultipleStatistics();
		ControllableProperty controllableProperty = new ControllableProperty();
		String propName = "HD Video Encoder 0#" + VideoControllingMetric.BITRATE.getName();
		String propValue = "30";
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);

		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		Assertions.assertEquals("32",stats.get(propName));
	}

	/**
	 * Test get Video control: with BitRate properties out off max range >25000
	 *
	 * Expect bitRate to take the Maximum value of 25000
	 * @throws Exception When fail to controlProperty
	 */
	@Test
	@Tag("RealDevice")
	void testVideoControlBitRateOutOfMaxRange() throws Exception {
		haivisionXEncoderCommunicator.getMultipleStatistics();
		ControllableProperty controllableProperty = new ControllableProperty();
		String propName = "HD Video Encoder 0#" + VideoControllingMetric.BITRATE.getName();
		String propValue = "25001";
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);

		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		Assertions.assertEquals("25000",stats.get(propName));
	}

	/**
	 * Test get Video control: with Resolution properties is Automatic
	 *
	 * @throws Exception When fail to controlProperty
	 */
	@Test
	@Tag("RealDevice")
	void testVideoControlResolutionIsAutomatic() throws Exception {
		haivisionXEncoderCommunicator.getMultipleStatistics();
		ControllableProperty controllableProperty = new ControllableProperty();
		String propName = "HD Video Encoder 0#" + VideoControllingMetric.RESOLUTION.getName();
		String propValue = ResolutionDropdown.RESOLUTION_AUTOMATIC.getName();
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);

		String croppingName = "HD Video Encoder 0#" + VideoControllingMetric.CROPPING.getName();
		ExtendedStatistics extendedStatistics = (ExtendedStatistics)  haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();

		Assertions.assertEquals(propValue,stats.get(propName));
		Assertions.assertNull(stats.get(croppingName));
	}

	/**
	 * Test get Video control: with Resolution properties different Automatic
	 *
	 * @throws Exception When fail to controlProperty
	 */
	@Test
	@Tag("RealDevice")
	void testVideoControlResolutionDifferentAutomatic() throws Exception {
		haivisionXEncoderCommunicator.getMultipleStatistics();
		ControllableProperty controllableProperty = new ControllableProperty();
		String propName = "HD Video Encoder 0#" + VideoControllingMetric.RESOLUTION.getName();
		String propValue = ResolutionDropdown.RESOLUTION_720_576I.getName();
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);

		String croppingName = "HD Video Encoder 0#" + VideoControllingMetric.CROPPING.getName();
		ExtendedStatistics extendedStatistics = (ExtendedStatistics)  haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();

		Assertions.assertEquals(propValue,stats.get(propName));
		Assertions.assertNotNull(stats.get(croppingName));
	}

	/**
	 * Test get Video control: with Frame Rate properties is 60
	 *
	 * @throws Exception When fail to controlProperty
	 */
	@Test
	@Tag("RealDevice")
	void testVideoControlFrameRate() throws Exception {
		haivisionXEncoderCommunicator.getMultipleStatistics();
		ControllableProperty controllableProperty = new ControllableProperty();
		String propName = "HD Video Encoder 0#" + VideoControllingMetric.FRAME_RATE.getName();
		String propValue = "60";
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);

		ExtendedStatistics extendedStatistics = (ExtendedStatistics)  haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		Assertions.assertEquals(propValue,stats.get(propName));
	}

	/**
	 * Test get Video control: with Framing properties is IBP
	 *
	 * @throws Exception When fail to controlProperty
	 */
	@Test
	@Tag("RealDevice")
	void testVideoControlFramingIsIBP() throws Exception {
		haivisionXEncoderCommunicator.getMultipleStatistics();
		ControllableProperty controllableProperty = new ControllableProperty();
		String propName = "HD Video Encoder 0#" + VideoControllingMetric.FRAMING.getName();
		String propValue = "IBP";
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);

		ExtendedStatistics extendedStatistics = (ExtendedStatistics)  haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		Assertions.assertEquals(propValue,stats.get(propName));
	}

	/**
	 * Test get Video control: with GOP Size properties in range 1-1000
	 *
	 * @throws Exception When fail to controlProperty
	 */
	@Test
	@Tag("RealDevice")
	void testVideoControlGOPSizeInRange() throws Exception {
		haivisionXEncoderCommunicator.getMultipleStatistics();
		ControllableProperty controllableProperty = new ControllableProperty();
		String propName = "HD Video Encoder 0#" + VideoControllingMetric.GOP_SIZE.getName();
		String propValue = "50";
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);

		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		Assertions.assertEquals(propValue,stats.get(propName));
	}

	/**
	 * Test get Video control: with GOP Size properties out off min range <1
	 *
	 * Expect GOPSize to take the minimum value of 1
	 * @throws Exception When fail to controlProperty
	 */
	@Test
	@Tag("RealDevice")
	void testVideoControlGOPSizeOutOfMinRange() throws Exception {
		haivisionXEncoderCommunicator.getMultipleStatistics();
		ControllableProperty controllableProperty = new ControllableProperty();
		String propName = "HD Video Encoder 0#" + VideoControllingMetric.GOP_SIZE.getName();
		String propValue = "-1";
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);

		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		Assertions.assertEquals("1",stats.get(propName));
	}

	/**
	 * Test get Video control: with GOP Size properties out off max range >1000
	 *
	 * Expect GOPSize to take the Maximum value of 1000
	 * @throws Exception When fail to controlProperty
	 */
	@Test
	@Tag("RealDevice")
	void testVideoControlGOPSizeOutOfMaxRange() throws Exception {
		haivisionXEncoderCommunicator.getMultipleStatistics();
		ControllableProperty controllableProperty = new ControllableProperty();
		String propName = "HD Video Encoder 0#" + VideoControllingMetric.GOP_SIZE.getName();
		String propValue = "10001";
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);

		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		Assertions.assertEquals("1000",stats.get(propName));
	}

	/**
	 * Test get Video control: with Aspect Ratio properties is mode 3:2
	 *
	 * @throws Exception When fail to controlProperty
	 */
	@Test
	@Tag("RealDevice")
	void testVideoControlAspectRatioMode_3_2() throws Exception {
		haivisionXEncoderCommunicator.getMultipleStatistics();
		ControllableProperty controllableProperty = new ControllableProperty();
		String propName = "HD Video Encoder 0#" + VideoControllingMetric.ASPECT_RATIO.getName();
		String propValue = "3:2";
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);

		ExtendedStatistics extendedStatistics = (ExtendedStatistics)  haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		Assertions.assertEquals(propValue,stats.get(propName));
	}

	/**
	 * Test get Video control: with Closed Caption properties is mode enable
	 *
	 * @throws Exception When fail to controlProperty
	 */
	@Test
	@Tag("RealDevice")
	void testVideoControlClosedCaptionEnable() throws Exception {
		haivisionXEncoderCommunicator.getMultipleStatistics();
		ControllableProperty controllableProperty = new ControllableProperty();
		String propName = "HD Video Encoder 0#" + VideoControllingMetric.CLOSED_CAPTION.getName();
		String propValue = "1";
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);

		ExtendedStatistics extendedStatistics = (ExtendedStatistics)  haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		Assertions.assertEquals(propValue,stats.get(propName));
	}

	/**
	 * Test get Video control: with Closed Caption properties is mode disable
	 *
	 * @throws Exception When fail to controlProperty
	 */
	@Test
	@Tag("RealDevice")
	void testVideoControlClosedCaptionDisable() throws Exception {
		haivisionXEncoderCommunicator.getMultipleStatistics();
		ControllableProperty controllableProperty = new ControllableProperty();
		String propName = "HD Video Encoder 0#" + VideoControllingMetric.CLOSED_CAPTION.getName();
		String propValue = "0";
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);

		ExtendedStatistics extendedStatistics = (ExtendedStatistics)  haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		Assertions.assertEquals(propValue,stats.get(propName));
	}

	/**
	 * Test get Video control: with time code source properties is system
	 *
	 * @throws Exception When fail to controlProperty
	 */
	@Test
	@Tag("RealDevice")
	void testVideoControlTimeCodeSourceIsSystem() throws Exception {
		haivisionXEncoderCommunicator.getMultipleStatistics();
		ControllableProperty controllableProperty = new ControllableProperty();
		String propName = "HD Video Encoder 0#" + VideoControllingMetric.TIME_CODE_SOURCE.getName();
		String propValue = TimeCodeSource.SYSTEM.getValue();
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);

		ExtendedStatistics extendedStatistics = (ExtendedStatistics)  haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		Assertions.assertEquals(propValue,stats.get(propName));
	}

	/**
	 * Test get Video control: with time code source properties is None
	 *
	 * @throws Exception When fail to controlProperty
	 */
	@Test
	@Tag("RealDevice")
	void testVideoControlTimeCodeSourceIsNone() throws Exception {
		haivisionXEncoderCommunicator.getMultipleStatistics();
		ControllableProperty controllableProperty = new ControllableProperty();
		String propName = "HD Video Encoder 0#" + VideoControllingMetric.TIME_CODE_SOURCE.getName();
		String propValue = TimeCodeSource.None.getValue();
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);

		ExtendedStatistics extendedStatistics = (ExtendedStatistics)  haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		Assertions.assertEquals(propValue,stats.get(propName));
	}

	/**
	 * Test get Video control: with Entropy Coding properties is CAVLC
	 *
	 * @throws Exception When fail to controlProperty
	 */
	@Test
	@Tag("RealDevice")
	void testVideoControlEntropyCodingIsCAVLC() throws Exception {
		haivisionXEncoderCommunicator.getMultipleStatistics();
		ControllableProperty controllableProperty = new ControllableProperty();
		String propName = "HD Video Encoder 0#" + VideoControllingMetric.ENTROPY_CODING.getName();
		String propValue = EntropyCodingDropdown.CAVLC.getName();
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);

		ExtendedStatistics extendedStatistics = (ExtendedStatistics)  haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		Assertions.assertEquals(propValue,stats.get(propName));
	}

	/**
	 * Test get Video control: with Entropy Coding properties is CABAC
	 *
	 * @throws Exception When fail to controlProperty
	 */
	@Test
	@Tag("RealDevice")
	void testVideoControlEntropyCodingIsCABAC() throws Exception {
		haivisionXEncoderCommunicator.getMultipleStatistics();
		ControllableProperty controllableProperty = new ControllableProperty();
		String propName = "HD Video Encoder 0#" + VideoControllingMetric.ENTROPY_CODING.getName();
		String propValue = EntropyCodingDropdown.CABAC.getName();
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);

		ExtendedStatistics extendedStatistics = (ExtendedStatistics)  haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		Assertions.assertEquals(propValue,stats.get(propName));
	}

	/**
	 * Test get Video control: with Partitioning properties is mode enable
	 *
	 * @throws Exception When fail to controlProperty
	 */
	@Test
	@Tag("RealDevice")
	void testVideoControlPartitioningEnable() throws Exception {
		haivisionXEncoderCommunicator.getMultipleStatistics();
		ControllableProperty controllableProperty = new ControllableProperty();
		String propName = "HD Video Encoder 0#" + VideoControllingMetric.PARTITIONING.getName();
		String propValue = "1";
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);

		ExtendedStatistics extendedStatistics = (ExtendedStatistics)  haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		Assertions.assertEquals(propValue,stats.get(propName));
	}

	/**
	 * Test get Video control: with Partitioning properties is mode disable
	 *
	 * @throws Exception When fail to controlProperty
	 */
	@Test
	@Tag("RealDevice")
	void testVideoControlPartitioningDisable() throws Exception {
		haivisionXEncoderCommunicator.getMultipleStatistics();
		ControllableProperty controllableProperty = new ControllableProperty();
		String propName = "HD Video Encoder 0#" + VideoControllingMetric.PARTITIONING.getName();
		String propValue = "0";
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);

		ExtendedStatistics extendedStatistics = (ExtendedStatistics)  haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		Assertions.assertEquals(propValue,stats.get(propName));
	}

	/**
	 * Test get Video control: with Intra Refresh properties is mode enable
	 *
	 * @throws Exception When fail to controlProperty
	 */
	@Test
	@Tag("RealDevice")
	void testVideoControlIntraRefreshEnable() throws Exception {
		haivisionXEncoderCommunicator.getMultipleStatistics();
		ControllableProperty controllableProperty = new ControllableProperty();
		String propName = "HD Video Encoder 0#" + VideoControllingMetric.PARTITIONING.getName();
		String propValue = "1";
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);

		String intraRefreshRate = "HD Video Encoder 0#" + VideoControllingMetric.INTRA_REFRESH_RATE.getName();
		ExtendedStatistics extendedStatistics = (ExtendedStatistics)  haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		Assertions.assertEquals(propValue,stats.get(propName));
		Assertions.assertNotNull(stats.get(intraRefreshRate));
	}

	/**
	 * Test get Video control: with Intra Refresh properties is mode disable
	 *
	 * @throws Exception When fail to controlProperty
	 */
	@Test
	@Tag("RealDevice")
	void testVideoControlIntraRefreshDisable() throws Exception {
		haivisionXEncoderCommunicator.getMultipleStatistics();
		ControllableProperty controllableProperty = new ControllableProperty();
		String propName = "HD Video Encoder 0#" + VideoControllingMetric.PARTITIONING.getName();
		String propValue = "0";
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);

		String intraRefreshRate = "HD Video Encoder 0#" + VideoControllingMetric.INTRA_REFRESH_RATE.getName();
		ExtendedStatistics extendedStatistics = (ExtendedStatistics)  haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		Assertions.assertEquals(propValue,stats.get(propName));
		Assertions.assertNull(stats.get(intraRefreshRate));
	}

	/**
	 * Test get Video control: with Partial Image Skip properties is mode enable
	 *
	 * @throws Exception When fail to controlProperty
	 */
	@Test
	@Tag("RealDevice")
	void testVideoControlPartialImageSkipEnable() throws Exception {
		haivisionXEncoderCommunicator.getMultipleStatistics();
		ControllableProperty controllableProperty = new ControllableProperty();
		String propName = "HD Video Encoder 0#" + VideoControllingMetric.PARTIAL_IMAGE_SKIP.getName();
		String propValue = "1";
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);

		ExtendedStatistics extendedStatistics = (ExtendedStatistics)  haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		Assertions.assertEquals(propValue,stats.get(propName));
	}

	/**
	 * Test get Video control: with Partial Image Skip properties is mode disable
	 *
	 * @throws Exception When fail to controlProperty
	 */
	@Test
	@Tag("RealDevice")
	void testVideoControlPartialImageSkipDisable() throws Exception {
		haivisionXEncoderCommunicator.getMultipleStatistics();
		ControllableProperty controllableProperty = new ControllableProperty();
		String propName = "HD Video Encoder 0#" + VideoControllingMetric.PARTIAL_IMAGE_SKIP.getName();
		String propValue = "0";
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);

		ExtendedStatistics extendedStatistics = (ExtendedStatistics)  haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		Assertions.assertEquals(propValue,stats.get(propName));
	}
}
