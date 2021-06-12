package com.github.mnadee.populator;

import java.util.HashMap;
import java.util.Map;

public class RowInstance {

	private Map<String, String> value = new HashMap<>();

	public void setField(String field, String val) {
		value.put(field, val);
	}

	public String getField(String field) {
		return value.get(field);
	}

	@Override
	public String toString() {
		return "RowInstance [value=" + value + "]";
	}
}
