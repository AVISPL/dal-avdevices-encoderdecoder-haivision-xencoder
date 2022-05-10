/*
 *  * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.common;

import java.util.Objects;

/**
 * EncoderUtil class provides during the monitoring and controlling process
 *
 * @author Kevin / Symphony Dev Team<br>
 * Created on 4/19/2022
 * @since 1.0.0
 */
public class EncoderUtil {

	/**
	 * Retrieves the URL for monitoring process
	 *
	 * @param encoderMonitoringMetric is instance of EncoderMonitoringMetric
	 * @return URL is instance of AxisURL
	 * @throws Exception if the name is not supported
	 */
	public static String getMonitorCommand(EncoderMonitoringMetric encoderMonitoringMetric) {
		Objects.requireNonNull(encoderMonitoringMetric);
		switch (encoderMonitoringMetric) {
			case AUDIO_STATISTICS:
				return EncoderCommand.OPERATION_AUDENC.getName() + EncoderCommand.GET.getName() + EncoderCommand.ALL.getName() + EncoderCommand.STATS.getName();
			case AUDIO_CONFIG:
				return EncoderCommand.OPERATION_AUDENC.getName() + EncoderCommand.GET.getName() + EncoderCommand.ALL.getName() + EncoderCommand.CONFIG.getName();
			case VIDEO_STATISTICS:
				return EncoderCommand.OPERATION_VIDENC.getName() + EncoderCommand.GET.getName() + EncoderCommand.ALL.getName() + EncoderCommand.STATS.getName();
			case VIDEO_CONFIG:
				return EncoderCommand.OPERATION_VIDENC.getName() + EncoderCommand.GET.getName() + EncoderCommand.ALL.getName() + EncoderCommand.CONFIG.getName();
			case SYSTEM_INFORMATION:
				return EncoderCommand.ADMIN_HAIVERSION.getName();
			case STREAM_STATISTICS:
				return EncoderCommand.OPERATION_STREAM.getName() + EncoderCommand.GET.getName() + EncoderCommand.ALL.getName() + EncoderCommand.STATS.getName();
			case STREAM_CONFIG:
				return EncoderCommand.OPERATION_STREAM.getName() + EncoderCommand.GET.getName() + EncoderCommand.ALL.getName() + EncoderCommand.CONFIG.getName();
			case TEMPERATURE:
				return EncoderCommand.OPERATION_TEMPERATURE.getName() + EncoderCommand.GET.getName();
			case INPUT:
				return EncoderCommand.OPERATION_VIDIN.getName() + EncoderCommand.GET.getName() + EncoderCommand.ALL.getName();
			case ACCOUNT:
			case ROLE_BASED:
				return EncoderConstant.NONE;
			default:
				throw new IllegalArgumentException("Do not support encoderMonitoringMetric: " + encoderMonitoringMetric.name());
		}
	}
}