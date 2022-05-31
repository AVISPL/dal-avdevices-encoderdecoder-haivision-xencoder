/*
 *  * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dropdownlist;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.avispl.symphony.api.dal.error.ResourceNotReachableException;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.common.EncoderConstant;

/**
 * EnumTypeHandler class defined the enum for monitoring and controlling process
 *
 * @author Kevin / Symphony Dev Team<br>
 * Created on 4/25/2022
 * @since 1.0.0
 */
public class EnumTypeHandler {

	/**
	 * Get an array of all enum names
	 *
	 * @param enumType the enumtype is enum class
	 */
	public static <T extends Enum<T>> String[] getEnumNames(Class<T> enumType) {
		List<String> names = new ArrayList<>();
		for (T c : enumType.getEnumConstants()) {
			try {
				Method method = c.getClass().getMethod("getName");
				String name = (String) method.invoke(c); // getName executed
				names.add(name);
			} catch (Exception e) {
				throw new ResourceNotReachableException("Error to convert enum " + enumType.getSimpleName() + " to names", e);
			}
		}
		return names.toArray(new String[names.size()]);
	}

	/**
	 * Get a map<String,String> of enum names are name and value or value and name
	 *
	 * @param enumType the enumtype is enum class
	 * @param isNameToValue the isNameToValue is boolean value get nameToValue or valueToName
	 */
	public static <T extends Enum<T>> Map<String, String> getMapOfEnumNames(Class<T> enumType, boolean isNameToValue) {
		Map<String, String> nameMap = new HashMap<>();
		for (T c : enumType.getEnumConstants()) {
			try {
				Method methodName = c.getClass().getMethod("getName");
				Method methodValue = c.getClass().getMethod("getValue");
				String name = (String) methodName.invoke(c); // getName executed
				String value = (String) methodValue.invoke(c); // getValue executed
				if (isNameToValue) {
					nameMap.put(name, value);
				} else {
					nameMap.put(value, name);
				}
			} catch (Exception e) {
				throw new ResourceNotReachableException("Error to convert enum " + enumType.getSimpleName() + " to names", e);
			}
		}
		return nameMap;
	}

	/**
	 * Get metric name of enum by name
	 *
	 * @param enumType the enumtype is enum class
	 * @param name is String
	 * @return T is metric instance
	 */
	public static <T extends Enum<T>> T getMetricOfEnumByName(Class<T> enumType, String name) {
		try {
			for (T metric : enumType.getEnumConstants()) {
				Method methodName = metric.getClass().getMethod("getName");
				String nameMetric = (String) methodName.invoke(metric); // getName executed
				if (name.equals(nameMetric)) {
					return metric;
				}
			}
			throw new ResourceNotReachableException("Fail to get enum " + enumType.getSimpleName() + " with name is " + name);
		} catch (Exception e) {
			throw new ResourceNotReachableException(e.getMessage(), e);
		}
	}

	/**
	 * Replace special character as space,(,), etc to 'character'
	 *
	 * @param str the str is String value
	 * @return String the String is String to be converted
	 */
	public static String replaceSpecialCharacter(String str) {
		return str.replace(EncoderConstant.SPACE, "' '").replace("(", "'('").replace(")", "')'");
	}

	/**
	 * Replace special character as [,] to empty string
	 *
	 * @param str the str is String value
	 * @return String the String is String to be converted
	 */
	public static String replaceSpecialCharacterToEmptyString(String str) {
		return str.replace("[", EncoderConstant.EMPTY_STRING).replace("]", EncoderConstant.EMPTY_STRING);
	}
}