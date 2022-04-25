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
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.common.HaivisionConstant;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.common.HaivisionMonitoringMetric;
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
	private HaivisionXEncoderCommunicator haivisionX4DecoderCommunicator;

	@BeforeEach()
	public void setUp() throws Exception {
		haivisionX4DecoderCommunicator = new HaivisionXEncoderCommunicator();
		haivisionX4DecoderCommunicator.setHost("***REMOVED***");
		haivisionX4DecoderCommunicator.setPort(22);
		haivisionX4DecoderCommunicator.setLogin("admin");
		haivisionX4DecoderCommunicator.setPassword("AVIadm1n");
		haivisionX4DecoderCommunicator.setCommandErrorList(Collections.singletonList("~"));
		haivisionX4DecoderCommunicator.setCommandSuccessList(Collections.singletonList("~$ "));
		haivisionX4DecoderCommunicator.setLoginSuccessList(Collections.singletonList("~$ "));
		haivisionX4DecoderCommunicator.init();
		haivisionX4DecoderCommunicator.connect();
	}

	@AfterEach()
	public void destroy() throws Exception {
		haivisionX4DecoderCommunicator.disconnect();
	}

	/**
	 * Test get statistics: with System info
	 *
	 * @throws Exception When fail to getMultipleStatistics
	 */
	@Test
	@Tag("RealDevice")
	void testGetMultipleStatisticsWithSystemInfo() throws Exception {
		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionX4DecoderCommunicator.getMultipleStatistics().get(0);
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
		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionX4DecoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		for (AudioMonitoringMetric metric : AudioMonitoringMetric.values()) {
			Assertions.assertNotNull(stats.get("Audio Encoder 0 Statistics" + HaivisionConstant.HASH + metric.getName()));
			Assertions.assertNotNull(stats.get("Audio Encoder 1 Statistics" + HaivisionConstant.HASH + metric.getName()));
			Assertions.assertNotNull(stats.get("Audio Encoder 2 Statistics" + HaivisionConstant.HASH + metric.getName()));
			Assertions.assertNotNull(stats.get("Audio Encoder 3 Statistics" + HaivisionConstant.HASH + metric.getName()));
			Assertions.assertNotNull(stats.get("Audio Encoder 4 Statistics" + HaivisionConstant.HASH + metric.getName()));
			Assertions.assertNotNull(stats.get("Audio Encoder 5 Statistics" + HaivisionConstant.HASH + metric.getName()));
			Assertions.assertNotNull(stats.get("Audio Encoder 6 Statistics" + HaivisionConstant.HASH + metric.getName()));
			Assertions.assertNotNull(stats.get("Audio Encoder 7 Statistics" + HaivisionConstant.HASH + metric.getName()));
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
		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionX4DecoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		for (VideoMonitoringMetric metric : VideoMonitoringMetric.values()) {
			Assertions.assertNotNull(stats.get("HD Video Encoder 0 Statistics" + HaivisionConstant.HASH + metric.getName()));
			Assertions.assertNotNull(stats.get("HD Video Encoder 1 Statistics" + HaivisionConstant.HASH + metric.getName()));
			Assertions.assertNotNull(stats.get("HD Video Encoder 2 Statistics" + HaivisionConstant.HASH + metric.getName()));
			Assertions.assertNotNull(stats.get("HD Video Encoder 3 Statistics" + HaivisionConstant.HASH + metric.getName()));
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
		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionX4DecoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		for (StreamMonitoringMetric metric : StreamMonitoringMetric.values()) {
			Assertions.assertNotNull(stats.get("Stream Ivantest02 Statistics" + HaivisionConstant.HASH + metric.getName()));
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
		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionX4DecoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();
		Assertions.assertNotNull(stats.get(HaivisionMonitoringMetric.TEMPERATURE.getName()));
	}
}
