/*
 * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */

package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder;

import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.avispl.symphony.api.dal.dto.control.ControllableProperty;
import com.avispl.symphony.api.dal.dto.monitor.ExtendedStatistics;
import com.avispl.symphony.api.dal.error.ResourceNotReachableException;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.common.AudioControllingMetric;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.common.AudioMonitoringMetric;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.common.EncoderConstant;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.common.EncoderMonitoringMetric;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.common.StreamControllingMetric;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.common.StreamMonitoringMetric;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.common.SystemMonitoringMetric;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.common.VideoControllingMetric;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.common.VideoMonitoringMetric;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dropdownlist.AlgorithmEnum;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dropdownlist.AudioActionEnum;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dropdownlist.BitRateEnum;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dropdownlist.ChannelModeEnum;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dropdownlist.ConnectionModeEnum;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dropdownlist.EncryptionEnum;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dropdownlist.EntropyCodingEnum;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dropdownlist.FecEnum;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dropdownlist.InputEnum;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dropdownlist.LanguageEnum;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dropdownlist.ProtocolEnum;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dropdownlist.ResolutionEnum;
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
		String propValue = InputEnum.SDI_1_1_2.getValue();
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);

		Assertions.assertEquals(propValue, stats.get(propName));
	}

	/**
	 * Test get Audio control: with Input properties is SDI 2 (1-2)
	 *
	 * @throws Exception When fail to controlProperty
	 */
	@Test
	@Tag("RealDevice")
	void testControlInputSDI2() throws Exception {
		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		ControllableProperty controllableProperty = new ControllableProperty();
		String propName = "Audio Encoder 0#" + AudioControllingMetric.INPUT.getName();
		String propValue = InputEnum.SDI_2_1_2.getValue();
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);

		String propNameApplyChange = "Audio Encoder 0#" + AudioControllingMetric.APPLY_CHANGE.getName();
		String propValueApplyChange = "1";
		controllableProperty.setProperty(propNameApplyChange);
		controllableProperty.setValue(propValueApplyChange);
		Assertions.assertThrows(ResourceNotReachableException.class, () -> haivisionXEncoderCommunicator.controlProperty(controllableProperty), "expect throw exception because SDI mode note exits");
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
		String propValue = ChannelModeEnum.STEREO.getValue();
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);

		Assertions.assertEquals(propValue, stats.get(propName));
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
		String propValue = BitRateEnum.NUMBER_128.getValue();
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);

		Assertions.assertEquals(propValue, stats.get(propName));
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
		String propValue = AlgorithmEnum.MPEG_4.getName();
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);

		Assertions.assertEquals(propValue, stats.get(propName));
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
		String propValue = LanguageEnum.ENGLISH.getValue();
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);

		Assertions.assertEquals(propValue, stats.get(propName));
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
		String propValue = AudioActionEnum.START.getName();
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);

		Assertions.assertEquals(propValue, stats.get(propName));
	}


	/**
	 * Test get Audio control: with Apply Change properties
	 *
	 * @throws Exception When fail to controlProperty
	 */
	@Test
	@Tag("RealDevice")
	void testControlApplyChange() throws Exception {
		haivisionXEncoderCommunicator.getMultipleStatistics();
		ControllableProperty controllableProperty = new ControllableProperty();
		String propName = "HD Video Encoder 0#BitRate";
		String propValue = "2";
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);

		String propNameAction = "Audio Encoder 0#" + AudioControllingMetric.ACTION.getName();
		String propValueAction = AudioActionEnum.START.getName();
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

		Assertions.assertEquals(propValue, stats.get(propName));
		Assertions.assertEquals(propValueAction, stats.get(propNameAction));
	}

	// UT for video Control--------------------------------------------------------------------------------------------------------------------------

	/**
	 * Test Video control: with Input properties is BNC-1
	 *
	 * @throws Exception When fail to controlProperty
	 */
	@Test
	@Tag("RealDevice")
	void testVideoControlInput() throws Exception {
		haivisionXEncoderCommunicator.getMultipleStatistics();
		ControllableProperty controllableProperty = new ControllableProperty();
		String propName = "HD Video Encoder 0#" + VideoControllingMetric.INPUT.getName();
		String propValue = "BNC-1";
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);

		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		Assertions.assertEquals(propValue, stats.get(propName));
	}

	/**
	 * Test Video control: with BitRate properties in range 32-25000
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
		Assertions.assertEquals(propValue, stats.get(propName));
	}

	/**
	 * Test Video control: with BitRate properties out off min range <32
	 * <p>
	 * Expect bitRate to take the minimum value of 32
	 *
	 * @throws Exception When fail to controlProperty
	 */
	@Test
	@Tag("RealDevice")
	void testVideoControlBitRateOutOfMinRange() throws Exception {
		haivisionXEncoderCommunicator.getMultipleStatistics();
		ControllableProperty controllableProperty = new ControllableProperty();
		String propName = "HD Video Encoder 0#" + VideoControllingMetric.BITRATE.getName();
		String gOPSize = "HD Video Encoder 0#" + VideoControllingMetric.GOP_SIZE.getName();
		String propValue = "32";
		String gOPSizeValue = "105";

		controllableProperty.setProperty(gOPSize);
		controllableProperty.setValue(gOPSizeValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);
		haivisionXEncoderCommunicator.getMultipleStatistics();
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);

		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		Assertions.assertEquals("32", stats.get(propName));
	}

	/**
	 * Test Video control: with BitRate properties out off max range >25000
	 * <p>
	 * Expect bitRate to take the Maximum value of 25000
	 *
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
		Assertions.assertEquals("25000", stats.get(propName));
	}

	/**
	 * Test Video control: with Resolution properties is Automatic
	 *
	 * @throws Exception When fail to controlProperty
	 */
	@Test
	@Tag("RealDevice")
	void testVideoControlResolutionIsAutomatic() throws Exception {
		haivisionXEncoderCommunicator.getMultipleStatistics();
		ControllableProperty controllableProperty = new ControllableProperty();
		String propName = "HD Video Encoder 0#" + VideoControllingMetric.RESOLUTION.getName();
		String propValue = ResolutionEnum.RESOLUTION_AUTOMATIC.getName();
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);

		String croppingName = "HD Video Encoder 0#" + VideoControllingMetric.CROPPING.getName();
		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();

		Assertions.assertEquals(propValue, stats.get(propName));
		Assertions.assertNull(stats.get(croppingName));
	}

	/**
	 * Test Video control: with Resolution properties different Automatic
	 *
	 * @throws Exception When fail to controlProperty
	 */
	@Test
	@Tag("RealDevice")
	void testVideoControlResolutionDifferentAutomatic() throws Exception {
		haivisionXEncoderCommunicator.getMultipleStatistics();
		ControllableProperty controllableProperty = new ControllableProperty();
		String propName = "HD Video Encoder 0#" + VideoControllingMetric.RESOLUTION.getName();
		String propValue = ResolutionEnum.RESOLUTION_800_600.getName();
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);

		String croppingName = "HD Video Encoder 0#" + VideoControllingMetric.CROPPING.getName();
		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();

		Assertions.assertEquals(propValue, stats.get(propName));
		Assertions.assertNotNull(stats.get(croppingName));
	}

	/**
	 * Test Video control: with Frame Rate properties is 60
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

		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		Assertions.assertEquals(propValue, stats.get(propName));
	}

	/**
	 * Test Video control: with Framing properties is IBP
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

		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		Assertions.assertEquals(propValue, stats.get(propName));
	}

	/**
	 * Test Video control: with GOP Size properties in range 1-1000
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
		Assertions.assertEquals(propValue, stats.get(propName));
	}

	/**
	 * Test Video control: with GOP Size properties out off min range <1
	 * <p>
	 * Expect GOPSize to take the minimum value of 1
	 *
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
		Assertions.assertEquals("1", stats.get(propName));
	}

	/**
	 * Test Video control: with GOP Size properties out off max range >1000
	 * <p>
	 * Expect GOPSize to take the Maximum value of 1000
	 *
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
		Assertions.assertEquals("1000", stats.get(propName));
	}

	/**
	 * Test Video control: with Aspect Ratio properties is mode 3:2
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

		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		Assertions.assertEquals(propValue, stats.get(propName));
	}

	/**
	 * Test Video control: with Closed Caption properties is mode enable
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

		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		Assertions.assertEquals(propValue, stats.get(propName));
	}

	/**
	 * Test Video control: with Closed Caption properties is mode disable
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

		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		Assertions.assertEquals(propValue, stats.get(propName));
	}

	/**
	 * Test Video control: with time code source properties is system
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

		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		Assertions.assertEquals(propValue, stats.get(propName));
	}

	/**
	 * Test Video control: with time code source properties is None
	 *
	 * @throws Exception When fail to controlProperty
	 */
	@Test
	@Tag("RealDevice")
	void testVideoControlTimeCodeSourceIsNone() throws Exception {
		haivisionXEncoderCommunicator.getMultipleStatistics();
		ControllableProperty controllableProperty = new ControllableProperty();
		String propName = "HD Video Encoder 0#" + VideoControllingMetric.TIME_CODE_SOURCE.getName();
		String propValue = TimeCodeSource.NONE.getValue();
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);

		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		Assertions.assertEquals(propValue, stats.get(propName));
	}

	/**
	 * Test Video control: with Entropy Coding properties is CAVLC
	 *
	 * @throws Exception When fail to controlProperty
	 */
	@Test
	@Tag("RealDevice")
	void testVideoControlEntropyCodingIsCAVLC() throws Exception {
		haivisionXEncoderCommunicator.getMultipleStatistics();
		ControllableProperty controllableProperty = new ControllableProperty();
		String propName = "HD Video Encoder 0#" + VideoControllingMetric.ENTROPY_CODING.getName();
		String propValue = EntropyCodingEnum.CAVLC.getName();
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);

		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		Assertions.assertEquals(propValue, stats.get(propName));
	}

	/**
	 * Test Video control: with Entropy Coding properties is CABAC
	 *
	 * @throws Exception When fail to controlProperty
	 */
	@Test
	@Tag("RealDevice")
	void testVideoControlEntropyCodingIsCABAC() throws Exception {
		haivisionXEncoderCommunicator.getMultipleStatistics();
		ControllableProperty controllableProperty = new ControllableProperty();
		String propName = "HD Video Encoder 0#" + VideoControllingMetric.ENTROPY_CODING.getName();
		String propValue = EntropyCodingEnum.CABAC.getName();
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);

		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		Assertions.assertEquals(propValue, stats.get(propName));
	}

	/**
	 * Test Video control: with Partitioning properties is mode enable
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

		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		Assertions.assertEquals(propValue, stats.get(propName));
	}

	/**
	 * Test Video control: with Partitioning properties is mode disable
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

		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		Assertions.assertEquals(propValue, stats.get(propName));
	}

	/**
	 * Test Video control: with Intra Refresh properties is mode enable
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
		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		Assertions.assertEquals(propValue, stats.get(propName));
		Assertions.assertNotNull(stats.get(intraRefreshRate));
	}

	/**
	 * Test Video control: with Intra Refresh properties is mode disable
	 *
	 * @throws Exception When fail to controlProperty
	 */
	@Test
	@Tag("RealDevice")
	void testVideoControlIntraRefreshDisable() throws Exception {
		haivisionXEncoderCommunicator.getMultipleStatistics();
		ControllableProperty controllableProperty = new ControllableProperty();
		String propName = "HD Video Encoder 0#" + VideoControllingMetric.INTRA_REFRESH.getName();
		String propValue = "0";
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);

		String intraRefreshRate = "HD Video Encoder 0#" + VideoControllingMetric.INTRA_REFRESH_RATE.getName();
		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		Assertions.assertEquals(propValue, stats.get(propName));
		Assertions.assertEquals("", stats.get(intraRefreshRate));
	}

	/**
	 * Test Video control: with Partial Image Skip properties is mode enable
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

		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		Assertions.assertEquals(propValue, stats.get(propName));
	}

	/**
	 * Test Video control: with Partial Image Skip properties is mode disable
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

		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		Assertions.assertEquals(propValue, stats.get(propName));
	}

	//UT for control create stream----------------------------------------------------------------------

	/**
	 * Test stream control: with name content
	 *
	 * @throws Exception When fail to controlProperty
	 */
	@Test
	@Tag("RealDevice")
	void testCreateStreamWithNameContent() throws Exception {
		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		String propName = EncoderConstant.CREATE_STREAM + "#" + StreamControllingMetric.NAME.getName();
		String propValue = "stream Test ";
		Assertions.assertEquals("", stats.get(propName));
		ControllableProperty controllableProperty = new ControllableProperty();
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);

		extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		stats = extendedStatistics.getStatistics();
		Assertions.assertEquals(propValue, stats.get(propName));
	}

	/**
	 * Test stream control: with Video source
	 *
	 * @throws Exception When fail to controlProperty
	 */
	@Test
	@Tag("RealDevice")
	void testCreateStreamWithVideoSource() throws Exception {
		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		String propName = EncoderConstant.CREATE_STREAM + "#" + StreamControllingMetric.SOURCE_VIDEO.getName();
		String propValue = "HD Video Encoder 1";
		Assertions.assertEquals("HD Video Encoder 0", stats.get(propName));
		ControllableProperty controllableProperty = new ControllableProperty();
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);

		extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		stats = extendedStatistics.getStatistics();
		Assertions.assertEquals(propValue, stats.get(propName));
	}

	/**
	 * Test stream control: with Audio source
	 *
	 * @throws Exception When fail to controlProperty
	 */
	@Test
	@Tag("RealDevice")
	void testCreateStreamWithAudioSource() throws Exception {
		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		String propName = EncoderConstant.CREATE_STREAM + "#" + StreamControllingMetric.SOURCE_AUDIO.getName() + " 0";
		String propValue = "Audio Encoder 1";
		Assertions.assertEquals("Audio Encoder 0", stats.get(propName));
		ControllableProperty controllableProperty = new ControllableProperty();
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);

		extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		stats = extendedStatistics.getStatistics();
		Assertions.assertEquals(propValue, stats.get(propName));
	}

	/**
	 * Test stream control: with add audio source
	 *
	 * @throws Exception When fail to controlProperty
	 */
	@Test
	@Tag("RealDevice")
	void testCreateStreamWithAddAudioSource() throws Exception {
		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		String propName = EncoderConstant.CREATE_STREAM + "#" + StreamControllingMetric.SOURCE_AUDIO.getName() + " 0";
		String propName2 = EncoderConstant.CREATE_STREAM + "#" + StreamControllingMetric.SOURCE_AUDIO.getName() + " 1";
		String propValue = "Audio Encoder 1";
		Assertions.assertEquals("Audio Encoder 0", stats.get(propName));
		Assertions.assertNull(stats.get(propName2));
		// Add audio source
		ControllableProperty controllableProperty = new ControllableProperty();
		controllableProperty.setProperty(propName2);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);
		extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		stats = extendedStatistics.getStatistics();

		Assertions.assertEquals("Audio Encoder 0", stats.get(propName));
		Assertions.assertEquals(propValue, stats.get(propName2));
	}

	/**
	 * Test stream control: with Destination Address
	 *
	 * @throws Exception When fail to controlProperty
	 */
	@Test
	@Tag("RealDevice")
	void testCreateStreamWithDestinationAddress() throws Exception {
		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		String propName = EncoderConstant.CREATE_STREAM + "#" + StreamControllingMetric.STREAMING_DESTINATION_ADDRESS.getName();
		String propValue = "127.0.0.3";
		Assertions.assertEquals("", stats.get(propName));

		ControllableProperty controllableProperty = new ControllableProperty();
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);
		extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		stats = extendedStatistics.getStatistics();

		Assertions.assertEquals(propValue, stats.get(propName));
	}

	/**
	 * Test stream control: with Destination Port in range 1025-65535
	 *
	 * @throws Exception When fail to controlProperty
	 */
	@Test
	@Tag("RealDevice")
	void testCreateStreamWithDestinationPort() throws Exception {
		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		String propName = EncoderConstant.CREATE_STREAM + "#" + StreamControllingMetric.STREAMING_DESTINATION_PORT.getName();
		String propValue = "8080";
		Assertions.assertEquals("", stats.get(propName));

		ControllableProperty controllableProperty = new ControllableProperty();
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);
		extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		stats = extendedStatistics.getStatistics();

		Assertions.assertEquals(propValue, stats.get(propName));
	}


	/**
	 * Test stream control: with Destination Port greater than Max range > 65535
	 *
	 * @throws Exception When fail to controlProperty
	 */
	@Test
	@Tag("RealDevice")
	void testCreateStreamWithDestinationGreaterThanPort() throws Exception {
		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		String propName = EncoderConstant.CREATE_STREAM + "#" + StreamControllingMetric.STREAMING_DESTINATION_PORT.getName();
		String propValue = "65536";
		Assertions.assertEquals("", stats.get(propName));

		ControllableProperty controllableProperty = new ControllableProperty();
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);
		extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		stats = extendedStatistics.getStatistics();

		Assertions.assertEquals("65535", stats.get(propName));
	}

	/**
	 * Test stream control: with Destination Port out of min range < 1025
	 *
	 * @throws Exception When fail to controlProperty
	 */
	@Test
	@Tag("RealDevice")
	void testCreateStreamWithDestinationLessPort() throws Exception {
		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		String propName = EncoderConstant.CREATE_STREAM + "#" + StreamControllingMetric.STREAMING_DESTINATION_PORT.getName();
		String propValue = "1024";
		Assertions.assertEquals("", stats.get(propName));

		ControllableProperty controllableProperty = new ControllableProperty();
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);
		extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		stats = extendedStatistics.getStatistics();

		Assertions.assertEquals("1025", stats.get(propName));
	}

	/**
	 * Test stream control: with FEC property
	 *
	 * @throws Exception When fail to controlProperty
	 */
	@Test
	@Tag("RealDevice")
	void testCreateStreamWithFEC() throws Exception {
		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		String propName = EncoderConstant.CREATE_STREAM + "#" + StreamControllingMetric.PARAMETER_FEC.getName();
		String propValue = FecEnum.VF.getValue();
		Assertions.assertEquals("None", stats.get(propName));

		ControllableProperty controllableProperty = new ControllableProperty();
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);
		extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		stats = extendedStatistics.getStatistics();

		Assertions.assertEquals(propValue, stats.get(propName));
	}

	/**
	 * Test stream control: with Traffic Shaping property enable (Idle Cells and Delayed Audio display)
	 *
	 * @throws Exception When fail to controlProperty
	 */
	@Test
	@Tag("RealDevice")
	void testCreateStreamWithTrafficShapingEnable() throws Exception {
		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		String propName = EncoderConstant.CREATE_STREAM + "#" + StreamControllingMetric.PARAMETER_TRAFFIC_SHAPING.getName();
		String propIdleName = EncoderConstant.CREATE_STREAM + "#" + StreamControllingMetric.PARAMETER_IDLE_CELLS.getName();
		String propDelayedName = EncoderConstant.CREATE_STREAM + "#" + StreamControllingMetric.PARAMETER_DELAYED_AUDIO.getName();
		String propValue = "1";
		Assertions.assertEquals("0", stats.get(propName));
		Assertions.assertNull(stats.get(propIdleName));
		Assertions.assertNull(stats.get(propDelayedName));

		ControllableProperty controllableProperty = new ControllableProperty();
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);
		extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		stats = extendedStatistics.getStatistics();

		//Expect Traffic Shaping enable
		Assertions.assertEquals(propValue, stats.get(propName));
		Assertions.assertEquals("0", stats.get(propIdleName));
		Assertions.assertEquals("0", stats.get(propDelayedName));
	}


	/**
	 * Test stream control: with Traffic Shaping property Disable
	 *
	 * @throws Exception When fail to controlProperty
	 */
	@Test
	@Tag("RealDevice")
	void testCreateStreamWithTrafficShapingDisable() throws Exception {
		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		String propName = EncoderConstant.CREATE_STREAM + "#" + StreamControllingMetric.PARAMETER_TRAFFIC_SHAPING.getName();
		ControllableProperty controllableProperty = new ControllableProperty();
		controllableProperty.setProperty(propName);
		controllableProperty.setValue("1");
		//init Traffic Shaping enable
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);
		String propIdleName = EncoderConstant.CREATE_STREAM + "#" + StreamControllingMetric.PARAMETER_IDLE_CELLS.getName();
		String propDelayedName = EncoderConstant.CREATE_STREAM + "#" + StreamControllingMetric.PARAMETER_DELAYED_AUDIO.getName();
		String propValue = "1";
		Assertions.assertEquals("1", stats.get(propName));

		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);
		//Expect Traffic Shaping enable
		Assertions.assertEquals(propValue, stats.get(propName));
		Assertions.assertEquals("0", stats.get(propIdleName));
		Assertions.assertEquals("0", stats.get(propDelayedName));

		String propValue2 = "0";
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue2);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);
		extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		stats = extendedStatistics.getStatistics();

		//Expect Idle Cells disable
		Assertions.assertEquals(propValue2, stats.get(propName));
		Assertions.assertNull(stats.get(propIdleName));
		Assertions.assertNull(stats.get(propDelayedName));
	}

	/**
	 * Test stream control: with Idle Cells property enable and disable
	 *
	 * @throws Exception When fail to controlProperty
	 */
	@Test
	@Tag("RealDevice")
	void testCreateStreamWithIdleCellsEnableAnDisable() throws Exception {
		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		String propName = EncoderConstant.CREATE_STREAM + "#" + StreamControllingMetric.PARAMETER_TRAFFIC_SHAPING.getName();
		ControllableProperty controllableProperty = new ControllableProperty();
		controllableProperty.setProperty(propName);
		controllableProperty.setValue("1");
		//init  Idle Cell enable
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);
		propName = EncoderConstant.CREATE_STREAM + "#" + StreamControllingMetric.PARAMETER_IDLE_CELLS.getName();
		Assertions.assertEquals("0", stats.get(propName));

		String propValue = "1";
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);
		extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		stats = extendedStatistics.getStatistics();
		//Expect Idle Cells enable it
		Assertions.assertEquals(propValue, stats.get(propName));

		String propValue2 = "0";
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue2);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);
		extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		stats = extendedStatistics.getStatistics();
		//Expect Idle Cells disable
		Assertions.assertEquals(propValue2, stats.get(propName));
	}

	/**
	 * Test stream control: with Delayed Audio property enable and disable
	 *
	 * @throws Exception When fail to controlProperty
	 */
	@Test
	@Tag("RealDevice")
	void testCreateStreamWithDelayedAudioEnableAnDisable() throws Exception {
		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		String propName = EncoderConstant.CREATE_STREAM + "#" + StreamControllingMetric.PARAMETER_TRAFFIC_SHAPING.getName();
		ControllableProperty controllableProperty = new ControllableProperty();
		controllableProperty.setProperty(propName);
		controllableProperty.setValue("1");

		//init Delayed Audio enable
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);
		propName = EncoderConstant.CREATE_STREAM + "#" + StreamControllingMetric.PARAMETER_DELAYED_AUDIO.getName();
		Assertions.assertEquals("0", stats.get(propName));

		String propValue = "1";
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);
		extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		stats = extendedStatistics.getStatistics();
		//Expect Delayed Audio enable
		Assertions.assertEquals(propValue, stats.get(propName));

		String propValue2 = "0";
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue2);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);
		extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		stats = extendedStatistics.getStatistics();
		//Expect Delayed Audio disable
		Assertions.assertEquals(propValue2, stats.get(propName));
	}

	/**
	 * Test stream control: with Bandwidth Overhead value in range 5-50
	 *
	 * @throws Exception When fail to controlProperty
	 */
	@Test
	@Tag("RealDevice")
	void testCreateStreamWithBandwidthOverheadInRange() throws Exception {
		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		ControllableProperty controllableProperty = new ControllableProperty();
		String propName = EncoderConstant.CREATE_STREAM + "#" + StreamControllingMetric.PARAMETER_TRAFFIC_SHAPING.getName();
		String propValue = "1";
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		//enable Traffic Shaping
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);
		Assertions.assertEquals("1", stats.get(propName));

		String bandwidthName = EncoderConstant.CREATE_STREAM + "#" + StreamControllingMetric.PARAMETER_BANDWIDTH_OVERHEAD.getName();
		propValue = "40";
		controllableProperty.setProperty(bandwidthName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);
		extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		stats = extendedStatistics.getStatistics();

		//Expect Bandwidth Overhead is 40
		Assertions.assertEquals(propValue, stats.get(bandwidthName));
	}

	/**
	 * Test stream control: with Bandwidth Overhead out of min range < 5
	 *
	 * @throws Exception When fail to controlProperty
	 */
	@Test
	@Tag("RealDevice")
	void testCreateStreamWithBandwidthOverheadOutOfMinRange() throws Exception {
		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		ControllableProperty controllableProperty = new ControllableProperty();
		String propName = EncoderConstant.CREATE_STREAM + "#" + StreamControllingMetric.PARAMETER_TRAFFIC_SHAPING.getName();
		String propValue = "1";
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		//enable Traffic Shaping
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);
		Assertions.assertEquals("1", stats.get(propName));

		String bandwidthName = EncoderConstant.CREATE_STREAM + "#" + StreamControllingMetric.PARAMETER_BANDWIDTH_OVERHEAD.getName();
		propValue = "0";
		controllableProperty.setProperty(bandwidthName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);
		extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		stats = extendedStatistics.getStatistics();

		//Expect Bandwidth Overhead is 5
		Assertions.assertEquals("5", stats.get(bandwidthName));
	}

	/**
	 * Test stream control: with Bandwidth Overhead out of max range > 50
	 *
	 * @throws Exception When fail to controlProperty
	 */
	@Test
	@Tag("RealDevice")
	void testCreateStreamWithBandwidthOverheadOutOfMaxRange() throws Exception {
		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		ControllableProperty controllableProperty = new ControllableProperty();
		String propName = EncoderConstant.CREATE_STREAM + "#" + StreamControllingMetric.PARAMETER_TRAFFIC_SHAPING.getName();
		String propValue = "1";
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		//enable Traffic Shaping
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);
		Assertions.assertEquals("1", stats.get(propName));

		String bandwidthName = EncoderConstant.CREATE_STREAM + "#" + StreamControllingMetric.PARAMETER_BANDWIDTH_OVERHEAD.getName();
		propValue = "51";
		controllableProperty.setProperty(bandwidthName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);
		extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		stats = extendedStatistics.getStatistics();

		//Expect Bandwidth Overhead is 5
		Assertions.assertEquals("50", stats.get(bandwidthName));
	}

	/**
	 * Test stream control: with Still Image
	 *
	 * @throws Exception When fail to controlProperty
	 */
	@Test
	@Tag("RealDevice")
	void testCreateStreamWithBandwidthStillImage() throws Exception {
		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		ControllableProperty controllableProperty = new ControllableProperty();
		String propName = EncoderConstant.CREATE_STREAM + "#" + StreamControllingMetric.STILL_IMAGE.getName();
		String propValue = "ColorBars720 (1280x720)";
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);
		Assertions.assertEquals(propValue, stats.get(propName));
	}

	/**
	 * Test stream control: with SAPTransmit enable Transmit SAP
	 *
	 * @throws Exception When fail to controlProperty
	 */
	@Test
	void testEnableSAPForTheCreateOutputStream() throws Exception {
		haivisionXEncoderCommunicator.getMultipleStatistics();
		ControllableProperty controllableProperty = new ControllableProperty();
		String propName = EncoderConstant.CREATE_STREAM + EncoderConstant.HASH + StreamControllingMetric.SAP_TRANSMIT.getName();
		String propValue = "1";
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);
		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		Assertions.assertEquals(propValue, stats.get(propName));
	}


	/**
	 * Test stream control: with SAPTransmit disable Transmit SAP
	 *
	 * @throws Exception When fail to controlProperty
	 */
	@Test
	void testDisableSAPForTheCreateOutputStream() throws Exception {
		haivisionXEncoderCommunicator.getMultipleStatistics();
		ControllableProperty controllableProperty = new ControllableProperty();
		String propName = EncoderConstant.CREATE_STREAM + EncoderConstant.HASH + StreamControllingMetric.SAP_TRANSMIT.getName();
		String propValue = "0";
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);
		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		Assertions.assertEquals(propValue, stats.get(propName));
	}

	/**
	 * Test stream control: with SAP name
	 *
	 * @throws Exception When fail to controlProperty
	 */
	@Test
	void testEditSapNameSuccess() throws Exception {
		haivisionXEncoderCommunicator.getMultipleStatistics();
		ControllableProperty controllableProperty = new ControllableProperty();
		String propName = EncoderConstant.CREATE_STREAM + EncoderConstant.HASH + StreamControllingMetric.SAP_TRANSMIT.getName();
		String propValue = "1";
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);
		propName = EncoderConstant.CREATE_STREAM + EncoderConstant.HASH + StreamControllingMetric.SAP_NAME.getName();
		propValue = "Name of SAP";
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);

		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		Assertions.assertEquals(propValue, stats.get(propName));
	}

	/**
	 * Test stream control: with SAP keywords
	 *
	 * @throws Exception When fail to controlProperty
	 */
	@Test
	void testEditSapKeywordsSuccess() throws Exception {
		haivisionXEncoderCommunicator.getMultipleStatistics();
		ControllableProperty controllableProperty = new ControllableProperty();
		String propName = EncoderConstant.CREATE_STREAM + EncoderConstant.HASH + StreamControllingMetric.SAP_TRANSMIT.getName();
		String propValue = "1";
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);
		propName = EncoderConstant.CREATE_STREAM + EncoderConstant.HASH + StreamControllingMetric.SAP_KEYWORDS.getName();
		propValue = "There are some keywords of SAP";
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);

		haivisionXEncoderCommunicator.controlProperty(controllableProperty);
		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		Assertions.assertEquals(propValue, stats.get(propName));
	}

	/**
	 * Test stream control: with SAP description
	 *
	 * @throws Exception When fail to controlProperty
	 */
	@Test
	void testEditSapDescriptionSuccess() throws Exception {
		haivisionXEncoderCommunicator.getMultipleStatistics();
		ControllableProperty controllableProperty = new ControllableProperty();
		String propName = EncoderConstant.CREATE_STREAM + EncoderConstant.HASH + StreamControllingMetric.SAP_TRANSMIT.getName();
		String propValue = "1";
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);
		propName = EncoderConstant.CREATE_STREAM + EncoderConstant.HASH + StreamControllingMetric.SAP_DESCRIPTION.getName();
		propValue = "There is SAP description";
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);

		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		Assertions.assertEquals(propValue, stats.get(propName));
	}

	/**
	 * Test stream control: with SAP author
	 *
	 * @throws Exception When fail to controlProperty
	 */
	@Test
	void testEditSapAuthorSuccess() throws Exception {
		haivisionXEncoderCommunicator.getMultipleStatistics();
		ControllableProperty controllableProperty = new ControllableProperty();
		String propName = EncoderConstant.CREATE_STREAM + EncoderConstant.HASH + StreamControllingMetric.SAP_TRANSMIT.getName();
		String propValue = "1";
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);
		propName = EncoderConstant.CREATE_STREAM + EncoderConstant.HASH + StreamControllingMetric.SAP_AUTHOR.getName();
		propValue = "SAP author here";
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);

		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		Assertions.assertEquals(propValue, stats.get(propName));
	}

	/**
	 * Test stream control: with SAP copyright
	 *
	 * @throws Exception When fail to controlProperty
	 */
	@Test
	void testEditSapCopyrightSuccess() throws Exception {
		haivisionXEncoderCommunicator.getMultipleStatistics();
		ControllableProperty controllableProperty = new ControllableProperty();
		String propName = EncoderConstant.CREATE_STREAM + EncoderConstant.HASH + StreamControllingMetric.SAP_TRANSMIT.getName();
		String propValue = "1";
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);
		propName = EncoderConstant.CREATE_STREAM + EncoderConstant.HASH + StreamControllingMetric.SAP_COPYRIGHT.getName();
		propValue = "There is SAP copyright";
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);

		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		Assertions.assertEquals(propValue, stats.get(propName));
	}

	/**
	 * Test stream control: with SAP address
	 *
	 * @throws Exception When fail to controlProperty
	 */
	@Test
	void testEditSapAddressSuccess() throws Exception {
		haivisionXEncoderCommunicator.getMultipleStatistics();
		ControllableProperty controllableProperty = new ControllableProperty();
		String propName = EncoderConstant.CREATE_STREAM + EncoderConstant.HASH + StreamControllingMetric.SAP_TRANSMIT.getName();
		String propValue = "1";
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);
		propName = EncoderConstant.CREATE_STREAM + EncoderConstant.HASH + StreamControllingMetric.SAP_ADDRESS.getName();
		propValue = "There is SAP address";
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);

		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		Assertions.assertEquals(propValue, stats.get(propName));
	}

	/**
	 * Test stream control: with SAP Port in range 1025-65535
	 *
	 * @throws Exception When fail to controlProperty
	 */
	@Test
	@Tag("RealDevice")
	void testCreateStreamWithSAPPortInRange() throws Exception {
		haivisionXEncoderCommunicator.getMultipleStatistics();
		ControllableProperty controllableProperty = new ControllableProperty();
		String propName = EncoderConstant.CREATE_STREAM + EncoderConstant.HASH + StreamControllingMetric.SAP_TRANSMIT.getName();
		String propValue = "1";
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);
		propName = EncoderConstant.CREATE_STREAM + "#" + StreamControllingMetric.SAP_PORT.getName();
		propValue = "8080";
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);

		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		Assertions.assertEquals(propValue, stats.get(propName));
	}

	/**
	 * Test stream control: with SAP Port out of min range < 1025
	 *
	 * @throws Exception When fail to controlProperty
	 */
	@Test
	@Tag("RealDevice")
	void testCreateStreamWithSAPPortOutOfMinRange() throws Exception {
		haivisionXEncoderCommunicator.getMultipleStatistics();
		ControllableProperty controllableProperty = new ControllableProperty();
		String propName = EncoderConstant.CREATE_STREAM + EncoderConstant.HASH + StreamControllingMetric.SAP_TRANSMIT.getName();
		String propValue = "1";
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);
		propName = EncoderConstant.CREATE_STREAM + "#" + StreamControllingMetric.SAP_PORT.getName();
		propValue = "1024";
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);

		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		Assertions.assertEquals("1025", stats.get(propName));
	}

	/**
	 * Test stream control: with SAP Port out of max range > 65535
	 *
	 * @throws Exception When fail to controlProperty
	 */
	@Test
	@Tag("RealDevice")
	void testCreateStreamWithSAPPortOutOfMaxRange() throws Exception {
		haivisionXEncoderCommunicator.getMultipleStatistics();
		ControllableProperty controllableProperty = new ControllableProperty();
		String propName = EncoderConstant.CREATE_STREAM + EncoderConstant.HASH + StreamControllingMetric.SAP_TRANSMIT.getName();
		String propValue = "1";
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);
		propName = EncoderConstant.CREATE_STREAM + "#" + StreamControllingMetric.SAP_PORT.getName();
		propValue = "65536";
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);

		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		Assertions.assertEquals("65535", stats.get(propName));
	}

	/**
	 * Test stream control: with SAP Port out of max range > 65535
	 *
	 * @throws Exception When fail to controlProperty
	 */
	@Test
	@Tag("RealDevice")
	void testCreateStreamWithSAPPort() throws Exception {
		haivisionXEncoderCommunicator.getMultipleStatistics();
		ControllableProperty controllableProperty = new ControllableProperty();
		String propName = EncoderConstant.CREATE_STREAM + EncoderConstant.HASH + StreamControllingMetric.SAP_TRANSMIT.getName();
		String propValue = "1";
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);
		propName = EncoderConstant.CREATE_STREAM + "#" + StreamControllingMetric.SAP_NAME.getName();
		propValue = "TEST0002s";
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);

		propName = EncoderConstant.CREATE_STREAM + EncoderConstant.HASH + StreamControllingMetric.NAME.getName();
		propValue = "TEST000s2";
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);
		propName = EncoderConstant.CREATE_STREAM + EncoderConstant.HASH + StreamControllingMetric.STREAMING_DESTINATION_PORT.getName();
		propValue = "12611";
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);
		propName = EncoderConstant.CREATE_STREAM + EncoderConstant.HASH + StreamControllingMetric.STREAMING_DESTINATION_ADDRESS.getName();
		propValue = "129.0.0.6";
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);

		propName = EncoderConstant.CREATE_STREAM + EncoderConstant.HASH + StreamControllingMetric.ACTION.getName();
		propValue = "1";
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);

		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		Assertions.assertEquals("65535", stats.get(propName));
	}


	/**
	 * Test stream control: with MTU in range 232-1500
	 *
	 * @throws Exception When fail to controlProperty
	 */
	@Test
	@Tag("RealDevice")
	void testCreateStreamWithMTUInRange() throws Exception {
		haivisionXEncoderCommunicator.getMultipleStatistics();
		ControllableProperty controllableProperty = new ControllableProperty();
		String propName = EncoderConstant.CREATE_STREAM + EncoderConstant.HASH + StreamControllingMetric.PARAMETER_MTU.getName();
		String propValue = "232";
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);

		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		Assertions.assertEquals("232", stats.get(propName));
	}

	/**
	 * Test stream control: with MTU out of min range < 232
	 *
	 * @throws Exception When fail to controlProperty
	 */
	@Test
	@Tag("RealDevice")
	void testCreateStreamWithMTUOutOfMinRange() throws Exception {
		haivisionXEncoderCommunicator.getMultipleStatistics();
		ControllableProperty controllableProperty = new ControllableProperty();
		String propName = EncoderConstant.CREATE_STREAM + EncoderConstant.HASH + StreamControllingMetric.PARAMETER_MTU.getName();
		String propValue = "231";
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);

		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		Assertions.assertEquals("232", stats.get(propName));
	}

	/**
	 * Test stream control: with MTU out of min range 1500
	 *
	 * @throws Exception When fail to controlProperty
	 */
	@Test
	@Tag("RealDevice")
	void testCreateStreamWithMTUOutOfMaxRange() throws Exception {
		haivisionXEncoderCommunicator.getMultipleStatistics();
		ControllableProperty controllableProperty = new ControllableProperty();
		String propName = EncoderConstant.CREATE_STREAM + EncoderConstant.HASH + StreamControllingMetric.PARAMETER_MTU.getName();
		String propValue = "1501";
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);

		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		Assertions.assertEquals("1500", stats.get(propName));
	}

	/**
	 * Test stream control: with TTL in range 1-255
	 *
	 * @throws Exception When fail to controlProperty
	 */
	@Test
	@Tag("RealDevice")
	void testCreateStreamWithTTLInRange() throws Exception {
		haivisionXEncoderCommunicator.getMultipleStatistics();
		ControllableProperty controllableProperty = new ControllableProperty();
		String propName = EncoderConstant.CREATE_STREAM + EncoderConstant.HASH + StreamControllingMetric.PARAMETER_TTL.getName();
		String propValue = "5";
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);

		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		Assertions.assertEquals("5", stats.get(propName));
	}

	/**
	 * Test stream control: with TTL out of min range < 1
	 *
	 * @throws Exception When fail to controlProperty
	 */
	@Test
	@Tag("RealDevice")
	void testCreateStreamWithTTLOutOfMinRange() throws Exception {
		haivisionXEncoderCommunicator.getMultipleStatistics();
		ControllableProperty controllableProperty = new ControllableProperty();
		String propName = EncoderConstant.CREATE_STREAM + EncoderConstant.HASH + StreamControllingMetric.PARAMETER_TTL.getName();
		String propValue = "0";
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);

		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		Assertions.assertEquals("1", stats.get(propName));
	}

	/**
	 * Test stream control: with TTL out of max range 1500
	 *
	 * @throws Exception When fail to controlProperty
	 */
	@Test
	@Tag("RealDevice")
	void testCreateStreamWithTTLOutOfMaxRange() throws Exception {
		haivisionXEncoderCommunicator.getMultipleStatistics();
		ControllableProperty controllableProperty = new ControllableProperty();
		String propName = EncoderConstant.CREATE_STREAM + EncoderConstant.HASH + StreamControllingMetric.PARAMETER_TTL.getName();
		String propValue = "256";
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);

		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		Assertions.assertEquals("255", stats.get(propName));
	}

	/**
	 * Test stream control: with ToS in range 0-255
	 *
	 * @throws Exception When fail to controlProperty
	 */
	@Test
	@Tag("RealDevice")
	void testCreateStreamWithToSInRange() throws Exception {
		haivisionXEncoderCommunicator.getMultipleStatistics();
		ControllableProperty controllableProperty = new ControllableProperty();
		String propName = EncoderConstant.CREATE_STREAM + EncoderConstant.HASH + StreamControllingMetric.PARAMETER_TOS.getName();
		String propValue = "0";
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);

		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		Assertions.assertEquals("0x00", stats.get(propName));
	}

	/**
	 * Test stream control: with ToS out of min range < 0x00
	 *
	 * @throws Exception When fail to controlProperty
	 */
	@Test
	@Tag("RealDevice")
	void testCreateStreamWithToSOutOfMinRange() throws Exception {
		haivisionXEncoderCommunicator.getMultipleStatistics();
		ControllableProperty controllableProperty = new ControllableProperty();
		String propName = EncoderConstant.CREATE_STREAM + EncoderConstant.HASH + StreamControllingMetric.PARAMETER_TOS.getName();
		String propValue = "1";
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);

		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		Assertions.assertEquals("0x01", stats.get(propName));
	}

	/**
	 * Test stream control: with ToS out of max range 0xFF
	 *
	 * @throws Exception When fail to controlProperty
	 */
	@Test
	@Tag("RealDevice")
	void testCreateStreamWithToSOutOfMaxRange() throws Exception {
		haivisionXEncoderCommunicator.getMultipleStatistics();
		ControllableProperty controllableProperty = new ControllableProperty();
		String propName = EncoderConstant.CREATE_STREAM + EncoderConstant.HASH + StreamControllingMetric.PARAMETER_TOS.getName();
		String propValue = "256";
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);

		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		Assertions.assertEquals("0xFF", stats.get(propName));
	}

	/**
	 * Test stream control: with SourceAddAudio
	 *
	 * @throws Exception When fail to controlProperty
	 */
	@Test
	@Tag("RealDevice")
	void testCreateStreamWithAddSourceAddAudio() throws Exception {
		haivisionXEncoderCommunicator.getMultipleStatistics();
		ControllableProperty controllableProperty = new ControllableProperty();
		String propName = EncoderConstant.CREATE_STREAM + EncoderConstant.HASH + StreamControllingMetric.ADD_SOURCE_AUDIO.getName();
		String propName1 = EncoderConstant.CREATE_STREAM + EncoderConstant.HASH + "SourceAudio 1";

		String propValue = "1";
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);

		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		Assertions.assertEquals("Audio Encoder 1", stats.get(propName1));
	}

	/**
	 * Test stream control: with change source audio 0
	 *
	 * @throws Exception When fail to controlProperty
	 */
	@Test
	@Tag("RealDevice")
	void testCreateStreamWithChangeSourceAudio() throws Exception {
		haivisionXEncoderCommunicator.getMultipleStatistics();
		ControllableProperty controllableProperty = new ControllableProperty();
		String propName = EncoderConstant.CREATE_STREAM + EncoderConstant.HASH + EncoderConstant.SOURCE_AUDIO_0;
		String propValue = "Audio Encoder 5";
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);

		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		Assertions.assertEquals("Audio Encoder 5", stats.get(propName));
	}

	/**
	 * Test stream control: with change HD Video Encoder
	 *
	 * @throws Exception When fail to controlProperty
	 */
	@Test
	@Tag("RealDevice")
	void testCreateStreamWithSourceVideo() throws Exception {
		haivisionXEncoderCommunicator.getMultipleStatistics();
		ControllableProperty controllableProperty = new ControllableProperty();
		String propName = EncoderConstant.CREATE_STREAM + EncoderConstant.HASH + StreamControllingMetric.SOURCE_VIDEO.getName();
		String propValue = "HD Video Encoder 4";
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);

		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		Assertions.assertEquals("HD Video Encoder 4", stats.get(propName));
	}

	/**
	 * Test stream control: with Protocol Direct RTP test sourceType is Audio
	 *
	 * @throws Exception When fail to controlProperty
	 */
	@Test
	@Tag("RealDevice")
	void testCreateStreamWithSourceTypeIsAudio() throws Exception {
		haivisionXEncoderCommunicator.getMultipleStatistics();
		ControllableProperty controllableProperty = new ControllableProperty();
		String propName = EncoderConstant.CREATE_STREAM + EncoderConstant.HASH + StreamControllingMetric.STREAMING_PROTOCOL.getName();
		String propValue = ProtocolEnum.DIRECT_RTP.getName();
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);
		propName = EncoderConstant.CREATE_STREAM + EncoderConstant.HASH + StreamControllingMetric.STREAM_SOURCE_TYPE.getName();
		propValue = EncoderConstant.AUDIO;
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);

		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		Assertions.assertEquals(propValue, stats.get(propName));
	}

	/**
	 * Test stream control: with Protocol Direct RTP test sourceType is Audio
	 *
	 * @throws Exception When fail to controlProperty
	 */
	@Test
	@Tag("RealDevice")
	void testCreateStreamWithSourceTypeIsVideo() throws Exception {
		haivisionXEncoderCommunicator.getMultipleStatistics();
		ControllableProperty controllableProperty = new ControllableProperty();
		String propName = EncoderConstant.CREATE_STREAM + EncoderConstant.HASH + StreamControllingMetric.STREAMING_PROTOCOL.getName();
		String propValue = ProtocolEnum.DIRECT_RTP.getName();
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);
		String propName2 = EncoderConstant.CREATE_STREAM + EncoderConstant.HASH + StreamControllingMetric.STREAM_SOURCE_TYPE.getName();
		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		Assertions.assertEquals("Video", stats.get(propName2));
	}

	/**
	 * Test stream control: with Protocol RTMP test publishName
	 *
	 * @throws Exception When fail to controlProperty
	 */
	@Test
	@Tag("RealDevice")
	void testCreateStreamWithPublishName() throws Exception {
		haivisionXEncoderCommunicator.getMultipleStatistics();
		ControllableProperty controllableProperty = new ControllableProperty();
		String propName = EncoderConstant.CREATE_STREAM + EncoderConstant.HASH + StreamControllingMetric.STREAMING_PROTOCOL.getName();
		String propValue = ProtocolEnum.RTMP.getName();
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);

		propName = EncoderConstant.CREATE_STREAM + EncoderConstant.HASH + StreamControllingMetric.RTMP_PUBLISH_NAME.getName();
		propValue = "ABC";
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);
		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		Assertions.assertEquals(propValue, stats.get(propName));
	}

	/**
	 * Test stream control: with Encryption with AES-256
	 *
	 * @throws Exception When fail to controlProperty
	 */
	@Test
	@Tag("RealDevice")
	void testCreateStreamTestEncrytion() throws Exception {
		haivisionXEncoderCommunicator.getMultipleStatistics();
		ControllableProperty controllableProperty = new ControllableProperty();
		String propName = EncoderConstant.CREATE_STREAM + EncoderConstant.HASH + StreamControllingMetric.STREAMING_PROTOCOL.getName();
		String propValue = ProtocolEnum.TS_SRT.getName();
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);

		propName = EncoderConstant.CREATE_STREAM + EncoderConstant.HASH + StreamControllingMetric.STREAM_ENCRYPTION.getName();
		String pasPhraseName = EncoderConstant.CREATE_STREAM + EncoderConstant.HASH + StreamControllingMetric.STREAM_PASSPHRASE.getName();
		propValue = EncryptionEnum.AES_256.getName();
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);
		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		Assertions.assertEquals(propValue, stats.get(propName));
		Assertions.assertNotNull(stats.get(pasPhraseName));
	}

	/**
	 * Test stream control: with Encryption with Value is None
	 *
	 * @throws Exception When fail to controlProperty
	 */
	@Test
	@Tag("RealDevice")
	void testCreateStreamTestEncrytionIsNone() throws Exception {
		haivisionXEncoderCommunicator.getMultipleStatistics();
		ControllableProperty controllableProperty = new ControllableProperty();
		String propName = EncoderConstant.CREATE_STREAM + EncoderConstant.HASH + StreamControllingMetric.STREAMING_PROTOCOL.getName();
		String propValue = ProtocolEnum.TS_SRT.getName();
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);

		propName = EncoderConstant.CREATE_STREAM + EncoderConstant.HASH + StreamControllingMetric.STREAM_ENCRYPTION.getName();
		String pasPhraseName = EncoderConstant.CREATE_STREAM + EncoderConstant.HASH + StreamControllingMetric.STREAM_PASSPHRASE.getName();
		propValue = EncryptionEnum.NONE.getName();
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);
		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		Assertions.assertEquals(propValue, stats.get(propName));
		Assertions.assertNull(stats.get(pasPhraseName));
	}

	/**
	 * Test stream control: with Latency in range 20-8000
	 *
	 * @throws Exception When fail to controlProperty
	 */
	@Test
	@Tag("RealDevice")
	void testCreateStreamWithLatencyInRnange() throws Exception {
		haivisionXEncoderCommunicator.getMultipleStatistics();
		ControllableProperty controllableProperty = new ControllableProperty();
		String propName = EncoderConstant.CREATE_STREAM + EncoderConstant.HASH + StreamControllingMetric.STREAMING_PROTOCOL.getName();
		String propValue = ProtocolEnum.TS_SRT.getName();
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);

		propName = EncoderConstant.CREATE_STREAM + EncoderConstant.HASH + StreamControllingMetric.STREAM_LATENCY.getName();
		propValue = "21";
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);
		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		Assertions.assertEquals(propValue, stats.get(propName));
	}

	/**
	 * Test stream control: with Latency out of min range <20
	 *
	 * @throws Exception When fail to controlProperty
	 */
	@Test
	@Tag("RealDevice")
	void testCreateStreamWithLatencyOutOfMinRange() throws Exception {
		haivisionXEncoderCommunicator.getMultipleStatistics();
		ControllableProperty controllableProperty = new ControllableProperty();
		String propName = EncoderConstant.CREATE_STREAM + EncoderConstant.HASH + StreamControllingMetric.STREAMING_PROTOCOL.getName();
		String propValue = ProtocolEnum.TS_SRT.getName();
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);

		propName = EncoderConstant.CREATE_STREAM + EncoderConstant.HASH + StreamControllingMetric.STREAM_LATENCY.getName();
		propValue = "19";
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);
		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		Assertions.assertEquals("20", stats.get(propName));
	}

	/**
	 * Test stream control: with Latency out of max range 8000
	 *
	 * @throws Exception When fail to controlProperty
	 */
	@Test
	@Tag("RealDevice")
	void testCreateStreamWithLatencyOutOfMaxRange() throws Exception {
		haivisionXEncoderCommunicator.getMultipleStatistics();
		ControllableProperty controllableProperty = new ControllableProperty();
		String propName = EncoderConstant.CREATE_STREAM + EncoderConstant.HASH + StreamControllingMetric.STREAMING_PROTOCOL.getName();
		String propValue = ProtocolEnum.TS_SRT.getName();
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);

		propName = EncoderConstant.CREATE_STREAM + EncoderConstant.HASH + StreamControllingMetric.STREAM_LATENCY.getName();
		propValue = "8001";
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);
		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		Assertions.assertEquals("8000", stats.get(propName));
	}

	/**
	 * Test stream control: with Connection Address
	 *
	 * @throws Exception When fail to controlProperty
	 */
	@Test
	@Tag("RealDevice")
	void testCreateStreamWithConnectionAddress() throws Exception {
		haivisionXEncoderCommunicator.getMultipleStatistics();
		ControllableProperty controllableProperty = new ControllableProperty();
		String propName = EncoderConstant.CREATE_STREAM + EncoderConstant.HASH + StreamControllingMetric.STREAMING_PROTOCOL.getName();
		String propValue = ProtocolEnum.TS_SRT.getName();
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);

		propName = EncoderConstant.CREATE_STREAM + EncoderConstant.HASH + StreamControllingMetric.STREAM_CONNECTION_ADDRESS.getName();
		propValue = "127.0.0.1";
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);
		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		Assertions.assertEquals(propValue, stats.get(propName));
	}

	/**
	 * Test stream control: with Connection Source Port, Destination Port with SRT mode
	 *
	 * @throws Exception When fail to controlProperty
	 */
	@Test
	@Tag("RealDevice")
	void testCreateStreamWithConnectionSourcePort() throws Exception {
		haivisionXEncoderCommunicator.getMultipleStatistics();
		ControllableProperty controllableProperty = new ControllableProperty();
		String propName = EncoderConstant.CREATE_STREAM + EncoderConstant.HASH + StreamControllingMetric.STREAMING_PROTOCOL.getName();
		String propValue = ProtocolEnum.TS_SRT.getName();
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);

		propName = EncoderConstant.CREATE_STREAM + EncoderConstant.HASH + StreamControllingMetric.STREAM_CONNECTION_SOURCE_PORT.getName();
		propValue = "4444";
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);
		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		Assertions.assertEquals(propValue, stats.get(propName));
	}

	/**
	 * Test stream control: with Connection Destination Port with SRT mode
	 *
	 * @throws Exception When fail to controlProperty
	 */
	@Test
	@Tag("RealDevice")
	void testCreateStreamWithConnectionDestinationPort() throws Exception {
		haivisionXEncoderCommunicator.getMultipleStatistics();
		ControllableProperty controllableProperty = new ControllableProperty();
		String propName = EncoderConstant.CREATE_STREAM + EncoderConstant.HASH + StreamControllingMetric.STREAMING_PROTOCOL.getName();
		String propValue = ProtocolEnum.TS_SRT.getName();
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);

		propName = EncoderConstant.CREATE_STREAM + EncoderConstant.HASH + StreamControllingMetric.STREAMING_DESTINATION_PORT.getName();
		propValue = "4444";
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);
		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		Assertions.assertEquals(propValue, stats.get(propName));
	}

	/**
	 * Test stream control: with Connection Destination Port with SRT mode
	 *
	 * @throws Exception When fail to controlProperty
	 */
	@Test
	@Tag("RealDevice")
	void testCreateStreamWithConnectionPort() throws Exception {
		haivisionXEncoderCommunicator.getMultipleStatistics();
		ControllableProperty controllableProperty = new ControllableProperty();
		String propName = EncoderConstant.CREATE_STREAM + EncoderConstant.HASH + StreamControllingMetric.STREAMING_PROTOCOL.getName();
		String propValue = ProtocolEnum.TS_SRT.getName();
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);

		propName = EncoderConstant.CREATE_STREAM + EncoderConstant.HASH + StreamControllingMetric.STREAMING_DESTINATION_PORT.getName();
		propValue = "4444";
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);

		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		Assertions.assertEquals(propValue, stats.get(propName));
	}

	/**
	 * Test stream control: with ConnectionMode is Caller
	 *
	 * @throws Exception When fail to controlProperty
	 */
	@Test
	@Tag("RealDevice")
	void testCreateStreamWithConnectionModeCaller() throws Exception {
		haivisionXEncoderCommunicator.getMultipleStatistics();
		ControllableProperty controllableProperty = new ControllableProperty();
		String propName = EncoderConstant.CREATE_STREAM + EncoderConstant.HASH + StreamControllingMetric.STREAMING_PROTOCOL.getName();
		String propValue = ProtocolEnum.TS_SRT.getName();
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);

		propName = EncoderConstant.CREATE_STREAM + EncoderConstant.HASH + StreamControllingMetric.STREAM_CONNECTION_MODE.getName();
		propValue = ConnectionModeEnum.CALLER.getName();
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);
		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		Assertions.assertEquals(propValue, stats.get(propName));
	}

	/**
	 * Test stream control: with ConnectionMode is Rendezvous
	 *
	 * @throws Exception When fail to controlProperty
	 */
	@Test
	@Tag("RealDevice")
	void testCreateStreamWithConnectionModeRendezvous() throws Exception {
		haivisionXEncoderCommunicator.getMultipleStatistics();
		ControllableProperty controllableProperty = new ControllableProperty();
		String propName = EncoderConstant.CREATE_STREAM + EncoderConstant.HASH + StreamControllingMetric.STREAMING_PROTOCOL.getName();
		String propValue = ProtocolEnum.TS_SRT.getName();
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);

		propName = EncoderConstant.CREATE_STREAM + EncoderConstant.HASH + StreamControllingMetric.STREAM_CONNECTION_MODE.getName();
		propValue = ConnectionModeEnum.RENDEZVOUS.getName();
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);
		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		Assertions.assertEquals(propValue, stats.get(propName));
	}

	/**
	 * Test stream control: with ConnectionMode is Listener
	 *
	 * @throws Exception When fail to controlProperty
	 */
	@Test
	@Tag("RealDevice")
	void testCreateStreamWithConnectionModeListener() throws Exception {
		haivisionXEncoderCommunicator.getMultipleStatistics();
		ControllableProperty controllableProperty = new ControllableProperty();
		String propName = EncoderConstant.CREATE_STREAM + EncoderConstant.HASH + StreamControllingMetric.STREAMING_PROTOCOL.getName();
		String propValue = ProtocolEnum.TS_SRT.getName();
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);

		propName = EncoderConstant.CREATE_STREAM + EncoderConstant.HASH + StreamControllingMetric.STREAM_CONNECTION_MODE.getName();
		propValue = ConnectionModeEnum.LISTENER.getName();
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);
		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		Assertions.assertEquals(propValue, stats.get(propName));
	}

	/**
	 * Test stream control: with Protocol SRT
	 *
	 * @throws Exception When fail to controlProperty
	 */
	@Test
	@Tag("RealDevice")
	void testCreateStreamWithProtocolSRT() throws Exception {
		haivisionXEncoderCommunicator.getMultipleStatistics();
		ControllableProperty controllableProperty = new ControllableProperty();
		String propName = EncoderConstant.CREATE_STREAM + EncoderConstant.HASH + StreamControllingMetric.STREAMING_PROTOCOL.getName();
		String propValue = ProtocolEnum.TS_SRT.getName();
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);

		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		Assertions.assertEquals(propValue, stats.get(propName));
	}

	/**
	 * Test stream control: with Protocol UDP
	 *
	 * @throws Exception When fail to controlProperty
	 */
	@Test
	@Tag("RealDevice")
	void testCreateStreamWithProtocolUDP() throws Exception {
		haivisionXEncoderCommunicator.getMultipleStatistics();
		ControllableProperty controllableProperty = new ControllableProperty();
		String propName = EncoderConstant.CREATE_STREAM + EncoderConstant.HASH + StreamControllingMetric.STREAMING_PROTOCOL.getName();
		String propValue = ProtocolEnum.TS_UDP.getName();
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);

		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		Assertions.assertEquals(propValue, stats.get(propName));
	}

	/**
	 * Test stream control: with Protocol RTP
	 *
	 * @throws Exception When fail to controlProperty
	 */
	@Test
	@Tag("RealDevice")
	void testCreateStreamWithProtocolRTP() throws Exception {
		haivisionXEncoderCommunicator.getMultipleStatistics();
		ControllableProperty controllableProperty = new ControllableProperty();
		String propName = EncoderConstant.CREATE_STREAM + EncoderConstant.HASH + StreamControllingMetric.STREAMING_PROTOCOL.getName();
		String propValue = ProtocolEnum.TS_RTP.getName();
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);

		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		Assertions.assertEquals(propValue, stats.get(propName));
	}

	/**
	 * Test stream control: with Protocol Direct-RTP
	 *
	 * @throws Exception When fail to controlProperty
	 */
	@Test
	@Tag("RealDevice")
	void testCreateStreamWithProtocolDirectRTP() throws Exception {
		haivisionXEncoderCommunicator.getMultipleStatistics();
		ControllableProperty controllableProperty = new ControllableProperty();
		String propName = EncoderConstant.CREATE_STREAM + EncoderConstant.HASH + StreamControllingMetric.STREAMING_PROTOCOL.getName();
		String propValue = ProtocolEnum.DIRECT_RTP.getName();
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);

		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		Assertions.assertEquals(propValue, stats.get(propName));
	}


	/**
	 * Test stream control: with Protocol RTMP
	 *
	 * @throws Exception When fail to controlProperty
	 */
	@Test
	@Tag("RealDevice")
	void testCreateStreamWithProtocolRTMP() throws Exception {
		haivisionXEncoderCommunicator.getMultipleStatistics();
		ControllableProperty controllableProperty = new ControllableProperty();
		String propName = EncoderConstant.CREATE_STREAM + EncoderConstant.HASH + StreamControllingMetric.STREAMING_PROTOCOL.getName();
		String propValue = ProtocolEnum.RTMP.getName();
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);

		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		Assertions.assertEquals(propValue, stats.get(propName));
	}

	//filter----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Test filter: with audio id 1,2
	 *
	 * @throws Exception When fail to controlProperty
	 */
	@Test
	@Tag("RealDevice")
	void testFilterByAudioId() throws Exception {
		haivisionXEncoderCommunicator.setAudioFilter("1,2");
		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		String propName1 = "Audio Encoder 1#State";
		String propName2 = "Audio Encoder 2#State";
		Map<String, String> stats = extendedStatistics.getStatistics();
		Assertions.assertNotNull(stats.get(propName1));
		Assertions.assertNotNull(stats.get(propName2));
	}

	/**
	 * Test filter: with audio id not exits
	 *
	 * @throws Exception When fail to controlProperty
	 */
	@Test
	@Tag("RealDevice")
	void testFilterByAudioIdNotExits() throws Exception {
		haivisionXEncoderCommunicator.setAudioFilter("8,9,10");
		haivisionXEncoderCommunicator.getMultipleStatistics();

		String propName = "Audio Encoder 0#State";
		String propName1 = "Audio Encoder 1State";
		String propName2 = "Audio Encoder 2#State";
		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		Assertions.assertNull(stats.get(propName));
		Assertions.assertNull(stats.get(propName1));
		Assertions.assertNull(stats.get(propName2));
	}

	/**
	 * Test filter: with video id 0,1
	 *
	 * @throws Exception When fail to controlProperty
	 */
	@Test
	@Tag("RealDevice")
	void testFilterByVideoId() throws Exception {
		haivisionXEncoderCommunicator.setVideoFilter("0,1");
		haivisionXEncoderCommunicator.getMultipleStatistics();

		String propName = "HD Video Encoder 0#State";
		String propName1 = "HD Video Encoder 1#State";
		String propName2 = "HD Video Encoder 2#State";
		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		Assertions.assertNotNull(stats.get(propName));
		Assertions.assertNotNull(stats.get(propName1));
		//Expect null because filter only video id are 0,1
		Assertions.assertNull(stats.get(propName2));
	}

	/**
	 * Test filter: with video id not exits
	 *
	 * @throws Exception When fail to controlProperty
	 */
	@Test
	@Tag("RealDevice")
	void testFilterByVideoIdNotExits() throws Exception {
		haivisionXEncoderCommunicator.setVideoFilter("5,6,7");
		haivisionXEncoderCommunicator.getMultipleStatistics();

		String propName = "HD Video Encoder 0#State";
		String propName1 = "HD Video Encoder 1#State";
		String propName2 = "HD Video Encoder 2#State";
		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		//Expect null because filter video id are 5,6,7
		Assertions.assertNull(stats.get(propName));
		Assertions.assertNull(stats.get(propName1));
		Assertions.assertNull(stats.get(propName2));
	}


	/**
	 * Test filter: with Stream name filter
	 *
	 * @throws Exception When fail to controlProperty
	 */
	@Test
	@Tag("RealDevice")
	void testFilterByStreamName() throws Exception {
		haivisionXEncoderCommunicator.setStreamNameFilter("TEST000s2");
		String propName = "Stream TEST000s2#State";
		String propName1 = "Stream talkback#State";
		String propName2 = "Stream RTP1#State";
		String audioName = "Audio Encoder 0 Statistics#State";
		String audioName1 = "Audio Encoder 1 Statistics#State";
		String audioName2 = "Audio Encoder 2 Statistics#State";
		String audioName3 = "Audio Encoder 3 Statistics#State";
		String audioName4 = "Audio Encoder 4 Statistics#State";
		String audioName5 = "Audio Encoder 5 Statistics#State";
		String audioName6 = "Audio Encoder 6 Statistics#State";
		String audioName7 = "Audio Encoder 7 Statistics#State";
		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		Assertions.assertNotNull(stats.get(propName));
		//Expect not null because stream asign 6 audio are 0,1,2,3,4,5,6
		Assertions.assertNotNull(stats.get(audioName));
		Assertions.assertNotNull(stats.get(audioName1));
		Assertions.assertNotNull(stats.get(audioName2));
		Assertions.assertNotNull(stats.get(audioName3));
		Assertions.assertNotNull(stats.get(audioName4));
		Assertions.assertNotNull(stats.get(audioName5));
		Assertions.assertNotNull(stats.get(audioName6));
		//Expect null because filter only stream TEST000s2
		Assertions.assertNull(stats.get(propName1));
		Assertions.assertNull(stats.get(propName2));
		Assertions.assertNull(stats.get(audioName7));
	}

	/**
	 * Test filter: with Stream name filter not exits
	 *
	 * @throws Exception When fail to controlProperty
	 */
	@Test
	@Tag("RealDevice")
	void testFilterByStreamNameNotExits() throws Exception {
		haivisionXEncoderCommunicator.setStreamNameFilter("TEST000s2000");
		String propName = "Stream TEST000s2000#State";
		String audioName = "Audio Encoder 0 Statistics#State";
		String audioName1 = "Audio Encoder 1 Statistics#State";
		String audioName2 = "Audio Encoder 2 Statistics#State";
		String audioName3 = "Audio Encoder 3 Statistics#State";
		String audioName4 = "Audio Encoder 4 Statistics#State";
		String audioName5 = "Audio Encoder 5 Statistics#State";
		String audioName6 = "Audio Encoder 6 Statistics#State";
		String audioName7 = "Audio Encoder 7 Statistics#State";
		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		//Expect  null because stream name not exits
		Assertions.assertNull(stats.get(audioName));
		Assertions.assertNull(stats.get(audioName1));
		Assertions.assertNull(stats.get(audioName2));
		Assertions.assertNull(stats.get(audioName3));
		Assertions.assertNull(stats.get(audioName4));
		Assertions.assertNull(stats.get(audioName5));
		Assertions.assertNull(stats.get(audioName6));
		Assertions.assertNull(stats.get(audioName7));
		//Expect null because stream name not exits
		Assertions.assertNull(stats.get(propName));
	}

	/**
	 * Test filter: with port filter
	 *
	 * @throws Exception When fail to controlProperty
	 */
	@Test
	@Tag("RealDevice")
	void testFilterByPort() throws Exception {
		haivisionXEncoderCommunicator.setPortNumberFilter("12611");
		String propName = "Stream TEST000s2#State";
		String propName1 = "Stream talkback#State";
		String propName2 = "Stream RTP1#State";
		String audioName = "Audio Encoder 0 Statistics#State";
		String audioName1 = "Audio Encoder 1 Statistics#State";
		String audioName2 = "Audio Encoder 2 Statistics#State";
		String audioName3 = "Audio Encoder 3 Statistics#State";
		String audioName4 = "Audio Encoder 4 Statistics#State";
		String audioName5 = "Audio Encoder 5 Statistics#State";
		String audioName6 = "Audio Encoder 6 Statistics#State";
		String audioName7 = "Audio Encoder 7 Statistics#State";
		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		Assertions.assertNotNull(stats.get(propName));
		//Expect not null because stream asign 6 audio are 0,1,2,3,4,5,6
		Assertions.assertNotNull(stats.get(audioName));
		Assertions.assertNotNull(stats.get(audioName1));
		Assertions.assertNotNull(stats.get(audioName2));
		Assertions.assertNotNull(stats.get(audioName3));
		Assertions.assertNotNull(stats.get(audioName4));
		Assertions.assertNotNull(stats.get(audioName5));
		Assertions.assertNotNull(stats.get(audioName6));
		//Expect null because filter only stream TEST000s2
		Assertions.assertNull(stats.get(propName1));
		Assertions.assertNull(stats.get(propName2));
		Assertions.assertNull(stats.get(audioName7));
	}

	/**
	 * Test filter: with port filter and port range
	 *
	 * @throws Exception When fail to controlProperty
	 */
	@Test
	@Tag("RealDevice")
	void testFilterByPortRange() throws Exception {
		haivisionXEncoderCommunicator.setPortNumberFilter("1234,112610-12611");
		String propName = "Stream TEST000s2#State";
		String propName1 = "Stream talkback#State";
		String propName2 = "Stream RTP1#State";
		String audioName = "Audio Encoder 0 Statistics#State";
		String audioName1 = "Audio Encoder 1 Statistics#State";
		String audioName2 = "Audio Encoder 2 Statistics#State";
		String audioName3 = "Audio Encoder 3 Statistics#State";
		String audioName4 = "Audio Encoder 4 Statistics#State";
		String audioName5 = "Audio Encoder 5 Statistics#State";
		String audioName6 = "Audio Encoder 6 Statistics#State";
		String audioName7 = "Audio Encoder 7 Statistics#State";
		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		Assertions.assertNotNull(stats.get(propName));
		Assertions.assertNotNull(stats.get(propName2));
		//Expect not null because stream asign 6 audio are 0,1,2,3,4,5,6
		Assertions.assertNotNull(stats.get(audioName));
		Assertions.assertNotNull(stats.get(audioName1));
		Assertions.assertNotNull(stats.get(audioName2));
		Assertions.assertNotNull(stats.get(audioName3));
		Assertions.assertNotNull(stats.get(audioName4));
		Assertions.assertNotNull(stats.get(audioName5));
		Assertions.assertNotNull(stats.get(audioName6));
		//Expect null because filter only stream TEST000s2
		Assertions.assertNull(stats.get(propName1));
		Assertions.assertNull(stats.get(audioName7));
	}

	/**
	 * Test filter: with port filter not exits
	 *
	 * @throws Exception When fail to controlProperty
	 */
	@Test
	@Tag("RealDevice")
	void testFilterByPortFilterNotExits() throws Exception {
		haivisionXEncoderCommunicator.setPortNumberFilter("222222,444444-666666");
		String propName = "Stream TEST000s2#State";
		String propName1 = "Stream talkback#State";
		String propName2 = "Stream RTP1#State";
		String audioName = "Audio Encoder 0 Statistics#State";
		String audioName1 = "Audio Encoder 1 Statistics#State";
		String audioName2 = "Audio Encoder 2 Statistics#State";
		String audioName3 = "Audio Encoder 3 Statistics#State";
		String audioName4 = "Audio Encoder 4 Statistics#State";
		String audioName5 = "Audio Encoder 5 Statistics#State";
		String audioName6 = "Audio Encoder 6 Statistics#State";
		String audioName7 = "Audio Encoder 7 Statistics#State";
		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		Assertions.assertNull(stats.get(propName));
		//Expect not null because stream asign 6 audio are 0,1,2,3,4,5,6
		Assertions.assertNull(stats.get(audioName));
		Assertions.assertNull(stats.get(audioName1));
		Assertions.assertNull(stats.get(audioName2));
		Assertions.assertNull(stats.get(audioName3));
		Assertions.assertNull(stats.get(audioName4));
		Assertions.assertNull(stats.get(audioName5));
		Assertions.assertNull(stats.get(audioName6));
		//Expect null because filter only stream TEST000s2
		Assertions.assertNull(stats.get(propName1));
		Assertions.assertNull(stats.get(propName2));
		Assertions.assertNull(stats.get(audioName7));
	}

	/**
	 * Test filter: with stream status
	 *
	 * @throws Exception When fail to controlProperty
	 */
	@Test
	@Tag("RealDevice")
	void testFilterByStreamStatus() throws Exception {
		haivisionXEncoderCommunicator.setStreamStatusFilter("STREAMING");
		String propName = "Stream TEST000s2#State";
		String propName1 = "Stream talkback#State";
		String propName2 = "Stream RTP1#State";
		String audioName = "Audio Encoder 0 Statistics#State";
		String audioName1 = "Audio Encoder 1 Statistics#State";
		String audioName2 = "Audio Encoder 2 Statistics#State";
		String audioName3 = "Audio Encoder 3 Statistics#State";
		String audioName4 = "Audio Encoder 4 Statistics#State";
		String audioName5 = "Audio Encoder 5 Statistics#State";
		String audioName6 = "Audio Encoder 6 Statistics#State";
		String audioName7 = "Audio Encoder 7 Statistics#State";
		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		Assertions.assertNotNull(stats.get(propName));
		Assertions.assertNotNull(stats.get(propName1));
		//Expect not null because stream asign 6 audio are 0,1,2,3,4,5,6
		Assertions.assertNotNull(stats.get(audioName));
		Assertions.assertNotNull(stats.get(audioName1));
		Assertions.assertNotNull(stats.get(audioName2));
		Assertions.assertNotNull(stats.get(audioName3));
		Assertions.assertNotNull(stats.get(audioName4));
		Assertions.assertNotNull(stats.get(audioName5));
		Assertions.assertNotNull(stats.get(audioName6));
		//Expect null because state Streaming just TEST000s2 and talkback
		Assertions.assertNull(stats.get(audioName7));
		Assertions.assertNull(stats.get(propName2));
	}

	/**
	 * Test filter: with stream status not exits
	 *
	 * @throws Exception When fail to controlProperty
	 */
	@Test
	@Tag("RealDevice")
	void testFilterByStreamStatusNotExits() throws Exception {
		haivisionXEncoderCommunicator.setStreamStatusFilter("STREAMING01");
		String propName = "Stream TEST000s2#State";
		String propName1 = "Stream talkback#State";
		String propName2 = "Stream RTP1#State";
		String audioName = "Audio Encoder 0 Statistics#State";
		String audioName1 = "Audio Encoder 1 Statistics#State";
		String audioName2 = "Audio Encoder 2 Statistics#State";
		String audioName3 = "Audio Encoder 3 Statistics#State";
		String audioName4 = "Audio Encoder 4 Statistics#State";
		String audioName5 = "Audio Encoder 5 Statistics#State";
		String audioName6 = "Audio Encoder 6 Statistics#State";
		String audioName7 = "Audio Encoder 7 Statistics#State";
		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		Assertions.assertNull(stats.get(propName));
		Assertions.assertNull(stats.get(propName1));
		Assertions.assertNull(stats.get(audioName));
		Assertions.assertNull(stats.get(audioName1));
		Assertions.assertNull(stats.get(audioName2));
		Assertions.assertNull(stats.get(audioName3));
		Assertions.assertNull(stats.get(audioName4));
		Assertions.assertNull(stats.get(audioName5));
		Assertions.assertNull(stats.get(audioName6));
		Assertions.assertNull(stats.get(audioName7));
		Assertions.assertNull(stats.get(propName2));
	}

	/**
	 * Test filter: with port and status
	 *
	 * @throws Exception When fail to controlProperty
	 */
	@Test
	@Tag("RealDevice")
	void testFilterByPortAndStatus() throws Exception {
		haivisionXEncoderCommunicator.setStreamStatusFilter("STREAMING");
		haivisionXEncoderCommunicator.setPortNumberFilter("1780");
		String propName = "Stream TEST000s2#State";
		String propName1 = "Stream talkback#State";
		String propName2 = "Stream RTP1#State";
		String audioName = "Audio Encoder 0 Statistics#State";
		String audioName1 = "Audio Encoder 1 Statistics#State";
		String audioName2 = "Audio Encoder 2 Statistics#State";
		String audioName3 = "Audio Encoder 3 Statistics#State";
		String audioName4 = "Audio Encoder 4 Statistics#State";
		String audioName5 = "Audio Encoder 5 Statistics#State";
		String audioName6 = "Audio Encoder 6 Statistics#State";
		String audioName7 = "Audio Encoder 7 Statistics#State";
		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		Assertions.assertNotNull(stats.get(propName1));
		Assertions.assertNotNull(stats.get(audioName));

		Assertions.assertNull(stats.get(propName));
		Assertions.assertNull(stats.get(audioName1));
		Assertions.assertNull(stats.get(audioName2));
		Assertions.assertNull(stats.get(audioName3));
		Assertions.assertNull(stats.get(audioName4));
		Assertions.assertNull(stats.get(audioName5));
		Assertions.assertNull(stats.get(audioName6));
		Assertions.assertNull(stats.get(audioName7));
		Assertions.assertNull(stats.get(propName2));
	}

	/**
	 * Test filter: with stream Or port and status
	 *
	 * @throws Exception When fail to controlProperty
	 */
	@Test
	@Tag("RealDevice")
	void testFilterByStreamNameOrPortAndStatus() throws Exception {
		haivisionXEncoderCommunicator.setStreamNameFilter("RTP1");
		haivisionXEncoderCommunicator.setStreamStatusFilter("STREAMING");
		haivisionXEncoderCommunicator.setPortNumberFilter("1780");
		String propName = "Stream TEST000s2#State";
		String propName1 = "Stream talkback#State";
		String propName2 = "Stream RTP1#State";
		String audioName = "Audio Encoder 0 Statistics#State";
		String audioName1 = "Audio Encoder 1 Statistics#State";
		String audioName2 = "Audio Encoder 2 Statistics#State";
		String audioName3 = "Audio Encoder 3 Statistics#State";
		String audioName4 = "Audio Encoder 4 Statistics#State";
		String audioName5 = "Audio Encoder 5 Statistics#State";
		String audioName6 = "Audio Encoder 6 Statistics#State";
		String audioName7 = "Audio Encoder 7 Statistics#State";
		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		Assertions.assertNotNull(stats.get(propName1));
		Assertions.assertNotNull(stats.get(audioName));
		Assertions.assertNotNull(stats.get(propName2));

		Assertions.assertNull(stats.get(propName));
		Assertions.assertNull(stats.get(audioName1));
		Assertions.assertNull(stats.get(audioName2));
		Assertions.assertNull(stats.get(audioName3));
		Assertions.assertNull(stats.get(audioName4));
		Assertions.assertNull(stats.get(audioName5));
		Assertions.assertNull(stats.get(audioName6));
		Assertions.assertNull(stats.get(audioName7));
	}

	/**
	 * Test Video control: with Apply change Aspect Ratio properties is mode 3:2
	 *
	 * @throws Exception When fail to controlProperty
	 */
	@Test
	@Tag("RealDevice")
	void testVideoControlApplyChangeAspectRatioMode_3_2() throws Exception {
		haivisionXEncoderCommunicator.destroy();
		haivisionXEncoderCommunicator = new HaivisionXEncoderCommunicator();
		haivisionXEncoderCommunicator.setConfigManagement("true");
		haivisionXEncoderCommunicator.setHost("***REMOVED***");
		haivisionXEncoderCommunicator.setPort(22);
		haivisionXEncoderCommunicator.setLogin("tmaguest");
		haivisionXEncoderCommunicator.setPassword("11111111");
		haivisionXEncoderCommunicator.init();
		haivisionXEncoderCommunicator.connect();
		haivisionXEncoderCommunicator.getMultipleStatistics();
		ControllableProperty controllableProperty = new ControllableProperty();
		String propName = "HD Video Encoder 0#" + VideoControllingMetric.ASPECT_RATIO.getName();
		String propValue = "3:2";
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);

		String propNameApplyChange = "HD Video Encoder 0#" + AudioControllingMetric.APPLY_CHANGE.getName();
		String propValueApplyChange = "1";
		controllableProperty.setProperty(propNameApplyChange);
		controllableProperty.setValue(propValueApplyChange);
		Assertions.assertThrows(ResourceNotReachableException.class, () -> haivisionXEncoderCommunicator.controlProperty(controllableProperty),
				"Expect throw exception because user with role based is guest");
	}


	/**
	 * Test control with role based: test role based is guest
	 *
	 * @throws Exception When fail to controlProperty
	 */
	@Test
	@Tag("RealDevice")
	void testControlWithRoleBased() throws Exception {
		haivisionXEncoderCommunicator.destroy();
		haivisionXEncoderCommunicator = new HaivisionXEncoderCommunicator();
		haivisionXEncoderCommunicator.setConfigManagement("true");
		haivisionXEncoderCommunicator.setHost("***REMOVED***");
		haivisionXEncoderCommunicator.setPort(22);
		haivisionXEncoderCommunicator.setLogin("tmaguest");
		haivisionXEncoderCommunicator.setPassword("11111111");
		haivisionXEncoderCommunicator.init();
		haivisionXEncoderCommunicator.connect();
		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		ControllableProperty controllableProperty = new ControllableProperty();
		String propName = "Audio Encoder 0#" + AudioControllingMetric.INPUT.getName();
		String propValue = InputEnum.SDI_1_3_4.getValue();
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);

		String propNameApplyChange = "Audio Encoder 0#" + AudioControllingMetric.APPLY_CHANGE.getName();
		String propValueApplyChange = "1";
		controllableProperty.setProperty(propNameApplyChange);
		controllableProperty.setValue(propValueApplyChange);
		Assertions.assertThrows(ResourceNotReachableException.class, () -> haivisionXEncoderCommunicator.controlProperty(controllableProperty),
				"Expect throw exception because user with role based is guest");
	}

	/**
	 * Test create stream with role based: create stream with role is guest
	 *
	 * @throws Exception When fail to controlProperty
	 */
	@Test
	@Tag("RealDevice")
	void testCreateStreamWithRoleGuest() throws Exception {
		haivisionXEncoderCommunicator.destroy();
		haivisionXEncoderCommunicator = new HaivisionXEncoderCommunicator();
		haivisionXEncoderCommunicator.setConfigManagement("true");
		haivisionXEncoderCommunicator.setHost("***REMOVED***");
		haivisionXEncoderCommunicator.setPort(22);
		haivisionXEncoderCommunicator.setLogin("tmaguest");
		haivisionXEncoderCommunicator.setPassword("11111111");
		haivisionXEncoderCommunicator.init();
		haivisionXEncoderCommunicator.connect();
		haivisionXEncoderCommunicator.getMultipleStatistics();
		ControllableProperty controllableProperty = new ControllableProperty();
		String propName = EncoderConstant.CREATE_STREAM + EncoderConstant.HASH + StreamControllingMetric.SAP_TRANSMIT.getName();
		String propValue = "1";
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);
		propName = EncoderConstant.CREATE_STREAM + "#" + StreamControllingMetric.SAP_NAME.getName();
		propValue = "TEST0002s";
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);

		propName = EncoderConstant.CREATE_STREAM + EncoderConstant.HASH + StreamControllingMetric.NAME.getName();
		propValue = "TEST000s2";
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);
		propName = EncoderConstant.CREATE_STREAM + EncoderConstant.HASH + StreamControllingMetric.STREAMING_DESTINATION_PORT.getName();
		propValue = "12611";
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);
		propName = EncoderConstant.CREATE_STREAM + EncoderConstant.HASH + StreamControllingMetric.STREAMING_DESTINATION_ADDRESS.getName();
		propValue = "129.0.0.6";
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		haivisionXEncoderCommunicator.controlProperty(controllableProperty);

		propName = EncoderConstant.CREATE_STREAM + EncoderConstant.HASH + StreamControllingMetric.ACTION.getName();
		propValue = "1";
		controllableProperty.setProperty(propName);
		controllableProperty.setValue(propValue);
		Assertions.assertThrows(ResourceNotReachableException.class, () -> haivisionXEncoderCommunicator.controlProperty(controllableProperty),
				"Expect throw exception because user with role based is guest");
	}
}
