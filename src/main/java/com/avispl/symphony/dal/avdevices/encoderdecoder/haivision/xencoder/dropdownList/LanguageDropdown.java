/*
 *  * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dropdownList;

import java.util.HashMap;
import java.util.Map;

/**
 * LanguageDropdown class defined the enum for monitoring and controlling process
 *
 * @author Kevin / Symphony Dev Team<br>
 * Created on 4/25/2022
 * @since 1.0.0
 */
public enum LanguageDropdown {

	ALBANIAN("Albanian (sqi)", "sqi (Albanian)"),
	ARABIC("Arabic (ara)", "ara (Arabic)"),
	ARMENIAN("Armenian (hye)", "hye (Armenian)"),
	ARMENIAN1("Armenian (arm)", "arm (Armenian)"),
	BULGARIAN("Bulgarian (bul)", "bul (Bulgarian)"),
	CHINESE("Chinese (chi)", "chi (Chinese)"),
	CHINESE1("Chinese (zho)", "zho (Chinese)"),
	CROATIAN("Croatian (hrv)", "hrv (Croatian)"),
	CZECH("Czech (cze)", "cze (Czech)"),
	CZECH1("Czech (ces)", "ces (Czech)"),
	DANISH("Danish (dan)", "dan (Danish)"),
	DUTCH("Dutch (dut)", "dut (Dutch)"),
	DUTCH1("Dutch (nld)", "nld (Dutch)"),
	ENGLISH("English (eng)", "eng (English)"),
	ESTONIAN("Estonian (est)", "est (Estonian)"),
	FINNISH("Finnish (fin)", "fin (Finnish)"),
	FRENCH("French (fre)", "fre (French)"),
	FRENCH1("French (fra)", "fra (French)"),
	GERMAN("German (ger)", "ger (German)"),
	GERMAN1("German (deu)", "deu (German)"),
	GREEK("Greek (gre)", "gre (Greek)"),
	GREEK1("Greek (ell)", "ell (Greek)"),
	HEBREW("Hebrew (heb)", "heb ()Hebrew"),
	HINDI("Hindi (hin)", "hin (Hindi)"),
	HUNGARIAN("Hungarian (hun)", "hun (Hungarian)"),
	INDONESIAN("Indonesian (ind)", "ind (Indonesian)"),
	IRISH("Irish (gle)", "gle (Irish)"),
	ICELANDIC("Icelandic (ice)", "ice (Icelandic)"),
	ICELANDIC1("Icelandic (isl)", "isl (Icelandic)"),
	ITALIAN("Italian (ita)", "ita (Italian)"),
	JAPANESE("Japanese (jpn)", "jpn (Japanese)"),
	KHMER("Khmer (khm)", "khm (Khmer)"),
	KOREAN("Korean (kor)", "kor (Korean)"),
	LATVIAN("Latvian (lav)", "lav (Latvian)"),
	LITHUANIAN("Lithuanian (lit)", "lit (Lithuanian)"),
	MALAY("Malay (may)", "may (Malay)"),
	MALAY1("Malay (msa)", "msa (Malay)"),
	MALTESE("Maltese (mlt)", "mlt (Maltese)"),
	MONGOLIAN("Mongolian (mon)", "mon (Mongolian)"),
	NORWEGIAN("Norwegian (nor)", "nor (Norwegian)"),
	PUNJABI("Punjabi (pan)", "pan (Punjabi)"),
	PERSIAN("Persian (per)", "per (Persian)"),
	PERSIAN1("Persian (fas)", "fas (Persian)"),
	POLISH("Polish (pol)", "pol (Polish)"),
	PORTUGUESE("Portuguese (por)", "por (Portuguese)"),
	ROMANIAN("Romanian (rum)", "rum (Romanian)"),
	ROMANIAN1("Romanian (ron)", "ron (Romanian)"),
	RUSSIAN("Russian (rus)", "rus (Russian)"),
	SLOVAK("Slovak (slo)", "slo (Slovak)"),
	SLOVAK1("Slovak (slk)", "slk (Slovak)"),
	SLOVENIAN("Slovenian (slv)", "slv (Slovenian)"),
	SPANISH("Spanish (spa)", "spa (Spanish)"),
	SWAHILI("Swahili (swa)", "swa (Swahili)"),
	SWEDISH("Swedish (swe)", "swe (Swedish)"),
	TURKISH("Turkish (tur)", "tur (Turkish)"),
	UKRAINIAN("Ukrainian (ukr)", "ukr (Ukrainian)"),
	VIETNAMESE("Vietnamese (vie)", "vie (Vietnamese)"),
	NONE("None","None");

	private final String name;
	private final String value;

	/**
	 * LanguageDropdown instantiation
	 *
	 * @param name {@code {@link #name}}
	 * @param value {@code {@link #value}}
	 */
	LanguageDropdown(String name, String value) {
		this.name = name;
		this.value = value;
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
	 * Retrieves {@code {@link #value}}
	 *
	 * @return value of {@link #value}
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Retrieves name to value map of languageDropdown
	 *
	 * @return Map<String, Integer> are map value and name
	 */
	public static Map<String, String> getNameToValueMap() {
		Map<String, String> nameToValue = new HashMap<>();
		for (LanguageDropdown languageDropdown : LanguageDropdown.values()) {
			nameToValue.put(languageDropdown.getValue(), languageDropdown.getName());
		}
		return nameToValue;
	}

	/**
	 * Retrieves name to value map of languageDropdown
	 *
	 * @return Map<Integer, String> are name and value
	 */
	public static Map<String, String> getValueToNameMap() {
		Map<String, String> valueToName = new HashMap<>();
		for (LanguageDropdown languageDropdown : LanguageDropdown.values()) {
			valueToName.put(languageDropdown.getName(), languageDropdown.getValue());
		}
		return valueToName;
	}
}