package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.common;

import java.util.Objects;

import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.command.Audenc;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.command.SystemInformation;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.command.Videnc;

/**
 * HaivisionCommand
 *
 * @author Kevin / Symphony Dev Team<br>
 * Created on 4/19/2022
 * @since 1.0.0
 */
public class HaivisionCommand {

	/**
	 * Retrieves the URL for monitoring process
	 *
	 * @param haivisionMonitoringMetric is instance of HaivisionMonitoringMetric
	 * @return URL is instance of AxisURL
	 * @throws Exception if the name is not supported
	 */
	public static Object getMonitorCommand(HaivisionMonitoringMetric haivisionMonitoringMetric) {
		Objects.requireNonNull(haivisionMonitoringMetric);
		switch (haivisionMonitoringMetric) {
			case AUDIO_STATISTICS:
				return Audenc.AUDENC.getName() + Audenc.GET.getName() + Audenc.ALL.getName() + Audenc.STATS.getName();
			case AUDIO_CONFIG:
				return Audenc.AUDENC.getName() + Audenc.GET.getName() + Audenc.ALL.getName() + Audenc.CONFIG.getName();
			case VIDEO_STATISTICS:
				return Videnc.VIDENC.getName() + Videnc.GET.getName() + Videnc.ALL.getName() + Videnc.STATS.getName();
			case VIDEO_CONFIG:
				return Videnc.VIDENC.getName() + Videnc.GET.getName() + Videnc.ALL.getName() + Videnc.CONFIG.getName();
			case SYSTEM_INFORMATION:
				return SystemInformation.HAIVERSION.getName();
			case ACCOUNT:
			case ROLE_BASED:
				break;
			default:
				throw new IllegalArgumentException("Do not support axisStatisticsMetric: " + haivisionMonitoringMetric.name());
		}
		return null;
	}
}