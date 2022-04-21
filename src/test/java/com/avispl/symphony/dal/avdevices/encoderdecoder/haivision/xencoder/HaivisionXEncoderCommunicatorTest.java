/*
 * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */

package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder;

import java.util.Collections;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.avispl.symphony.api.dal.dto.monitor.ExtendedStatistics;

/**
 * Unit test for HaivisionX4DecoderCommunicator
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
	 * Test HaivisionX4DecoderCommunicator.getMultipleStatistics successful with valid username password
	 * Expected retrieve valid device monitoring data
	 */
	@Tag("RealDevice")
	@Test
	void testHaivisionX4DecoderCommunicatorGetMonitoringDataSuccessful() throws Exception {
		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionX4DecoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();

	}
}
