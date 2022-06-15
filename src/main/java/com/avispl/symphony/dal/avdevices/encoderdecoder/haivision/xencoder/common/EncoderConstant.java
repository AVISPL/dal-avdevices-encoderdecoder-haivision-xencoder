/*
 *  * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.common;

/**
 * EncoderConstant class provides the constant during the monitoring and controlling process
 *
 * @author Kevin / Symphony Dev Team<br>
 * Created on 4/19/2022
 * @since 1.0.0
 */
public class EncoderConstant {

	public static final String STATISTICS = "Statistics";
	public static final String COLON = ":";
	public static final String SPACE = " ";
	public static final String NONE = "None";
	public static final String DAY = " day(s) ";
	public static final String HOUR = " hour(s) ";
	public static final String MINUTE = " minute(s) ";
	public static final String SECOND = " second(s)";
	public static final String DASH = "-";
	public static final String COMMA = ",";
	public static final String HASH = "#";
	public static final String SYSTEM_INFO_STATUS = "System Info Status";
	public static final String ADMIN = "Administrator";
	public static final String OPERATOR_GUEST = "Operator/Guest";
	public static final String GUEST_ROLE_MESSAGE = "You have insufficient privileges to perform this operation";
	public static final String GUEST_ROLE_MESSAGE_ERR = "Your role (Guest) doesn't have sufficient privileges to perform this operation.";
	public static final String STREAM = "Stream";
	public static final String TRUE = "True";
	public static final String EMPTY_STRING = "";
	public static final String REGEX_DATA = "\r\n";
	public static final String REGEX_SPLIT_DATA = "\r\n\r";
	public static final String NONE_STREAM_NAME = "(None)";
	public static final String QUOTES = "\"";
	public static final String NAME = "Name";
	public static final int NUMBER_ONE = 1;
	public static final int NUMBER_TWO = 2;
	public static final int ZERO = 0;
	public static final String EDITED = "Edited";
	public static final String FALSE = "False";
	public static final String AUDIO = "Audio";
	public static final String CANCEL = "Cancel";
	public static final String CANCELING = "Canceling";
	public static final String APPLY = "Apply";
	public static final String APPLYING = "Applying";
	public static final String UNKNOWN = "Unknown";
	public static final String NO_INPUT = "No Input";
	public static final String INVALID = "Invalid";
	public static final String PERCENT = "%";
	public static final String LESS_THAN = "<";
	public static final String DEFAULT_AUDIO_LEVEL = "6";
	public static final String SUCCESS_RESPONSE = "successfully";
	public static final String WORKING = "WORKING";
	public static final String STOPPED = "STOPPED";
	public static final String MUTED = "MUTED";
	public static final String ERROR_INPUT = "Unable to set property \"input\"";
	public static final String ERROR_TALKBACK_ACTION_START = "Unable to start talkback: \"Talkback already started\"";
	public static final String DISABLE = "Disable";
	public static final String ENABLE = "Enable";
	public static final String CROP = "Crop";
	public static final String SCALE = "Scale";
	public static final String INPUT_AUDIO = "Input/Auto";
	public static final String ON = "On";
	public static final String OFF = "Off";
	public static final int MIN_BITRATE = 32;
	public static final int MAX_BITRATE = 25000;
	public static final int MIN_GOP_SIZE = 1;
	public static final int MAX_GOP_SIZE = 1000;
	public static final int MIN_REFRESH_RATE = 1;
	public static final int MAX_REFRESH_RATE = 5000;
	public static final int DEFAULT_REFRESH_RATE = 60;
	public static final String VIDEO = "Video";
	public static final String AUTO = "Auto";
	public static final String COMMAND_FAILED_FORMAT = "%s %s %s";
	public static final String CREATE_STREAM = "CreateStream";
	public static final String AUDIO_ENCODER = "Audio Encoder ";
	public static final String MP4 = "mp4";
	public static final String DEFAULT_TOS = "0x80";
	public static final String MAX_TOS = "FF";
	public static final String MIN_TOS = "00";
	public static final int MAX_SOURCE_AUDIO_DROPDOWN = 8;
	public static final int DEFAULT_MTU = 1496;
	public static final int MIN_MTU = 232;
	public static final int MAX_MTU = 1500;
	public static final int DEFAULT_TTL = 64;
	public static final int MIN_TTL = 1;
	public static final int MAX_TTL = 255;
	public static final int MIN_PORT = 1025;
	public static final int MAX_PORT = 65535;
	public static final int DEFAULT_BANDWIDTH_OVERHEAD = 15;
	public static final int DEFAULT_BANDWIDTH_OVERHEAD_SRT = 25;
	public static final int MIN_BANDWIDTH_OVERHEAD = 5;
	public static final int MAX_BANDWIDTH_OVERHEAD = 50;
	public static final int DEFAULT_SAP_PORT = 9875;
	public static final int DEFAULT_LATENCY = 250;
	public static final int MIN_LATENCY = 20;
	public static final int MAX_LATENCY = 8000;
	public static final int MIN_PASSPHRASE = 9;
	public static final int MIN_TALKBACK_PORT = 1;
	public static final int MAX_TALKBACK_PORT = 65535;
	public static final String PLUS = "+";
	public static final String ADDING = "Adding";
	public static final String HEX_PREFIX = "0x";
	public static final String CREATE = "Create";
	public static final String CREATING = "Creating";
	public static final String YES = "Yes";
	public static final String SOURCE_AUDIO_0 = "SourceAudio 0";
	public static final String EQUAL = "=";
	public static final String DOT = ".";
	public static final String STREAM_CREATE_RESPONSE = "Stream created successfully";
	public static final String ADDRESS_FORMAT = "rtmp://";
	public static final String SOURCE_AUDIO = "SourceAudio";
	public static final String PASSPHRASE = "**********";
	public static final String CURRENTLY = "currently";
	public static final String ENABLED = "enabled";
	public static final String STRING_ONE = "1";
	public static final String CONFIRM_STOP_SERVICE = "y";
	public static final String SUCCESS_STOP_SERVICE_RESPONSE = "stopped";
	public static final String ONVIF = "onvif";
	public static final String TALKBACK = "talkback";
	public static final String TALKBACK_PORT = "TalkbackPort";
	public static final String PORT = "Port";
	public static final String LISTENING = "LISTENING";
	public static final String LISTENING_STATE = "TalkbackListeningState";
	public static final String DISABLED = "DISABLED";
	public static final String ACTIVE = "Active";
	public static final String STRING_ZERO = "0";
	public static final String REGEX_CHECK_STRING_IS_NUMBER = "-?\\d+(\\.\\d+)?";
	public static final String PASS_THROUGH = "passthrough";
	public static final String STATE = "State";
	public static final String UDP_PORT = "UDP Port";
	public static final String UNRESOLVED = ":UNRESOLVED";
	public static final String EIGHTH_STRING_FORMAT = " %s %s %s %s %s %s %s %s ";
	public static final String ELEVEN_STRING_FORMAT = " %s %s %s %s %s %s %s %s %s %s %s";
	public static final String SEVEN_STRING_FORMAT = " %s %s %s %s %s %s %s ";
	public static final String FIVE_STRING_FORMAT = " %s %s %s %s %s ";
}
