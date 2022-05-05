/*
 *  * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.xencoder.dropdownlist;

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

	ALBANIAN("Albanian (sqi)", "sqi (Albanian)","sqi"),
	ARABIC("Arabic (ara)", "ara (Arabic)","ara"),
	ARMENIAN("Armenian (hye)", "hye (Armenian)","hye"),
	ARMENIAN1("Armenian (arm)", "arm (Armenian)","arm"),
	BULGARIAN("Bulgarian (bul)", "bul (Bulgarian)","bul"),
	CHINESE("Chinese (chi)", "chi (Chinese)","chi"),
	CHINESE1("Chinese (zho)", "zho (Chinese)","zho"),
	CROATIAN("Croatian (hrv)", "hrv (Croatian)","hrv"),
	CZECH("Czech (cze)", "cze (Czech)","cze"),
	CZECH1("Czech (ces)", "ces (Czech)","ces"),
	DANISH("Danish (dan)", "dan (Danish)","dan"),
	DUTCH("Dutch (dut)", "dut (Dutch)","dut"),
	DUTCH1("Dutch (nld)", "nld (Dutch)","nld"),
	ENGLISH("English (eng)", "eng (English)","eng"),
	ESTONIAN("Estonian (est)", "est (Estonian)","est"),
	FINNISH("Finnish (fin)", "fin (Finnish)","fin"),
	FRENCH("French (fre)", "fre (French)","fre"),
	FRENCH1("French (fra)", "fra (French)","fra"),
	GERMAN("German (ger)", "ger (German)","ger"),
	GERMAN1("German (deu)", "deu (German)","deu"),
	GREEK("Greek (gre)", "gre (Greek)","gre"),
	GREEK1("Greek (ell)", "ell (Greek)","ell"),
	HEBREW("Hebrew (heb)", "heb ()Hebrew","heb"),
	HINDI("Hindi (hin)", "hin (Hindi)","hin"),
	HUNGARIAN("Hungarian (hun)", "hun (Hungarian)","hun"),
	INDONESIAN("Indonesian (ind)", "ind (Indonesian)","ind"),
	IRISH("Irish (gle)", "gle (Irish)","gle"),
	ICELANDIC("Icelandic (ice)", "ice (Icelandic)","ice"),
	ICELANDIC1("Icelandic (isl)", "isl (Icelandic)","isl"),
	ITALIAN("Italian (ita)", "ita (Italian)","ita"),
	JAPANESE("Japanese (jpn)", "jpn (Japanese)","jpn"),
	KHMER("Khmer (khm)", "khm (Khmer)","khm"),
	KOREAN("Korean (kor)", "kor (Korean)","kor"),
	LATVIAN("Latvian (lav)", "lav (Latvian)","lav"),
	LITHUANIAN("Lithuanian (lit)", "lit (Lithuanian)","lit"),
	MALAY("Malay (may)", "may (Malay)","may"),
	MALAY1("Malay (msa)", "msa (Malay)","msa"),
	MALTESE("Maltese (mlt)", "mlt (Maltese)","mlt"),
	MONGOLIAN("Mongolian (mon)", "mon (Mongolian)","mon"),
	NORWEGIAN("Norwegian (nor)", "nor (Norwegian)","nor"),
	PUNJABI("Punjabi (pan)", "pan (Punjabi)","pan"),
	PERSIAN("Persian (per)", "per (Persian)","per"),
	PERSIAN1("Persian (fas)", "fas (Persian)","fas"),
	POLISH("Polish (pol)", "pol (Polish)","pol"),
	PORTUGUESE("Portuguese (por)", "por (Portuguese)","por"),
	ROMANIAN("Romanian (rum)", "rum (Romanian)","rum"),
	ROMANIAN1("Romanian (ron)", "ron (Romanian)","ron"),
	RUSSIAN("Russian (rus)", "rus (Russian)","rus"),
	SLOVAK("Slovak (slo)", "slo (Slovak)","slo"),
	SLOVAK1("Slovak (slk)", "slk (Slovak)","slk"),
	SLOVENIAN("Slovenian (slv)", "slv (Slovenian)","slv"),
	SPANISH("Spanish (spa)", "spa (Spanish)","spa"),
	SWAHILI("Swahili (swa)", "swa (Swahili)","swa"),
	SWEDISH("Swedish (swe)", "swe (Swedish)","swe"),
	TURKISH("Turkish (tur)", "tur (Turkish)","tur"),
	UKRAINIAN("Ukrainian (ukr)", "ukr (Ukrainian)","ukr"),
	VIETNAMESE("Vietnamese (vie)", "vie (Vietnamese)","vie"),
	NONE("None","None","None");

	private final String name;
	private final String value;
	private final String paramValue;

	/**
	 * LanguageDropdown instantiation
	 *
	 * @param name {@code {@link #name}}
	 * @param value {@code {@link #value}}
	 * @param paramValue {@code {@link #paramValue}}
	 */
	LanguageDropdown(String name, String value,String paramValue) {
		this.name = name;
		this.value = value;
		this.paramValue = paramValue;
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
	 * Retrieves {@code {@link #paramValue}}
	 *
	 * @return value of {@link #paramValue}
	 */
	public String getParamValue() {
		return paramValue;
	}

	/**
	 * Retrieves param value to value name of LanguageDropdown
	 *
	 * @return Map<String, String> are map param value and value name
	 */
	public static Map<String, String> getParamValueToNameMap() {
		Map<String, String> nameMap = new HashMap<>();
		for (LanguageDropdown languageDropdown : LanguageDropdown.values()) {
			nameMap.put(languageDropdown.getName(), languageDropdown.getParamValue());
		}
		return nameMap;
	}
}