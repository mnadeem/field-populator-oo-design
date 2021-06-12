package com.github.mnadee.populator;

public class HardCodedValueProvider implements ValueProvider {

	private static final String HARD_CODE_INDICATOR = "H";

	@Override
	public boolean canProvide(String indicator) {
		return HARD_CODE_INDICATOR.equalsIgnoreCase(indicator);
	}

	@Override
	public String provide(String type, String[] params) {
		return params[0] + "done";
	}
}
