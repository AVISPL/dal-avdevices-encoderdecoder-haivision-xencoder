/*
 *  * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.common;

import java.util.Objects;

/**
 * HaivisionCommand class provides during the monitoring and controlling process
 *
 * @author Kevin / Symphony Dev Team<br>
 * Created on 4/19/2022
 * @since 1.0.0
 */
public class HaivisionUtil {

	/**
	 * Retrieves the URL for monitoring process
	 *
	 * @param haivisionMonitoringMetric is instance of HaivisionMonitoringMetric
	 * @return URL is instance of AxisURL
	 * @throws Exception if the name is not supported
	 */
	public static String getMonitorCommand(HaivisionMonitoringMetric haivisionMonitoringMetric) {
		Objects.requireNonNull(haivisionMonitoringMetric);
		switch (haivisionMonitoringMetric) {
			case AUDIO_STATISTICS:
				return HaivisionCommand.AUDENC.getName() + HaivisionCommand.GET.getName() + HaivisionCommand.ALL.getName() + HaivisionCommand.STATS.getName();
			case AUDIO_CONFIG:
				return HaivisionCommand.AUDENC.getName() + HaivisionCommand.GET.getName() + HaivisionCommand.ALL.getName() + HaivisionCommand.CONFIG.getName();
			case VIDEO_STATISTICS:
				return HaivisionCommand.VIDENC.getName() + HaivisionCommand.GET.getName() + HaivisionCommand.ALL.getName() + HaivisionCommand.STATS.getName();
			case VIDEO_CONFIG:
				return HaivisionCommand.VIDENC.getName() + HaivisionCommand.GET.getName() + HaivisionCommand.ALL.getName() + HaivisionCommand.CONFIG.getName();
			case SYSTEM_INFORMATION:
				return HaivisionCommand.HAIVERSION.getName();
			case STREAM_STATISTICS:
				return HaivisionCommand.STREAM.getName() + HaivisionCommand.GET.getName() + HaivisionCommand.ALL.getName() + HaivisionCommand.STATS.getName();
			case STREAM_CONFIG:
				return HaivisionCommand.STREAM.getName() + HaivisionCommand.GET.getName() + HaivisionCommand.ALL.getName() + HaivisionCommand.CONFIG.getName();
			case ACCOUNT:
			case ROLE_BASED:
				break;
			default:
				throw new IllegalArgumentException("Do not support haivisionMonitoringMetric: " + haivisionMonitoringMetric.name());
		}
		return HaivisionConstant.NONE;
	}
}