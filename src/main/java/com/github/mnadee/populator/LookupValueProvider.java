package com.github.mnadee.populator;

public class LookupValueProvider implements ValueProvider {

	private static final String LOOKUP_INDICATOR = "L";

	@Override
	public boolean canProvide(String indicator) {
		return LOOKUP_INDICATOR.equalsIgnoreCase(indicator);
	}

	@Override
	public String provide(String type, String[] params) {
		return params[0]+":" + params[1]+ ":" + "done";
	}
}
