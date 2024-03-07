package com.elikill58.sanction.universal;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class UniversalUtils {

	private UniversalUtils() {
	}

	public static String truncate(String tooLongString, int limit) {
		char[] chars = tooLongString.toCharArray();

		if (chars.length < limit)
			return tooLongString;

		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < limit; i++) {
			builder.append(chars[i]);
		}

		return builder.toString();
	}

	public static boolean isInteger(String obj) {
		try {
			Integer.parseInt(obj);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static List<UUID> convertToUUID(List<String> l) {
		List<UUID> list = new ArrayList<>();
		for (String s : l) {
			try {
				list.add(UUID.fromString(s));
			} catch (Exception e) {
			}
		}
		return list;
	}

	public static String replacePlaceholder(String message, Object... placeholders) {
		for (int index = 0; index <= placeholders.length - 1; index += 2)
			message = message.replace(placeholders[index].toString(), placeholders[index + 1].toString());
		return message;
	}

	public static List<String> replacePlaceholder(List<String> messages, Object... placeholders) {
		for(int i = 0; i < messages.size(); i++) {
			messages.set(i, replacePlaceholder(messages.get(i), placeholders));
		}
		return messages;
	}
}
