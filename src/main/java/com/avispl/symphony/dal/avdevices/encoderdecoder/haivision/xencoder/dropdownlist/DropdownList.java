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
/**
 * DropdownList class defined the enum for monitoring and controlling process
 *
 * @author Kevin / Symphony Dev Team<br>
 * Created on 4/25/2022
 * @since 1.0.0
 */
public class DropdownList {

	/**
	 * Get an array of all enum names
	 *
	 * @param enumType the enumtype is enum class
	 */
	public static <T extends Enum<T>> String[] getArrayOfEnumNames(Class<T> enumType) {
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
}