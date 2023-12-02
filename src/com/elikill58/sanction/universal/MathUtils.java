package com.elikill58.sanction.universal;

public class MathUtils {

	public static int getMultipleOf(int i, int multiple) {
		return getMultipleOf(i, multiple, 1);
	}

	public static int getMultipleOf(int i, int multiple, int more) {
		while (i % multiple != 0)
			i += more;
		return i;
	}
}
