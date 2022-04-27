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

import com.avispl.symphony.api.dal.dto.monitor.ExtendedStatistics;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.common.AudioMonitoringMetric;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.common.EncoderConstant;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.common.EncoderMonitoringMetric;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.common.StreamMonitoringMetric;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.common.SystemMonitoringMetric;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.common.VideoMonitoringMetric;

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
		haivisionXEncoderCommunicator.setConfigManagement("true");
		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionXEncoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		Assertions.assertNotNull(stats.get(EncoderMonitoringMetric.TEMPERATURE.getName()));
	}
}
