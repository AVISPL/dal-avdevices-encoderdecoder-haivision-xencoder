package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dropdownlist;

/**
 * VideoControllingMetric  class defined the enum for the controlling process
 *
 * @author Kevin / Symphony Dev Team<br>
 * Created on 5/5/2022
 * @since 1.0.0
 */
public enum VideoControllingMetric {

	STATE("State"),
	INPUT_FORMAT("InputFormat"),
	INPUT("Input"),
	BITRATE("BitRate"),
	RESOLUTION("Resolution"),
	CROPPING("Cropping"),
	FRAME_RATE("FrameRate"),
	FRAMING("Framing"),
	GOP_SIZE("GOPSize"),
	ASPECT_RATIO("AspectRatio"),
	CLOSED_CAPTION("ClosedCaption"),
	TIME_CODE_SOURCE("TimeCodeSource"),
	ENTROPY_CODING("EntropyCoding"),
	PARTITIONING("Partitioning"),
	INTRA_REFRESH("IntraRefresh"),
	INTRA_REFRESH_RATE("IntraRefreshRate"),
	PARTIAL_IMAGE_SKIP("PartialImageSkip"),
	APPLY_CHANGE("ApplyChange"),
	ACTION("Action"),
	CANCEL("Cancel");


	private final String name;

	/**
	 * VideoControllingMetric instantiation
	 *
	 * @param name {@code {@link #name}}
	 */
	VideoControllingMetric(String name) {
		this.name = name;
	}

	/**
	 * Retrieves {@code {@link #name}}
	 *
	 * @return value of {@link #name}
	 */
	public String getName() {
		return name;
	}

	/**
	 * Retrieves metric by name
	 *
	 * @param name {@code {@link #name}}
	 * @return name of metric
	 * @throws Exception if can not find the enum with name
	 */
	public static VideoControllingMetric getByName(String name) {
		for (VideoControllingMetric metric : VideoControllingMetric.values()) {
			if (metric.getName().equals(name)) {
				return metric;
			}
		}
		throw new IllegalArgumentException("Can not find the enum with name: " + name);
	}
}