package com.github.mnadee.populator;

public class FunctionValueProvider implements ValueProvider {

	private static final String FUNCTION_INDICATOR = "F";

	@Override
	public boolean canProvide(String indicator) {
		return FUNCTION_INDICATOR.equalsIgnoreCase(indicator);
	}

	@Override
	public String provide(String type, String[] params) {
		return params[0] + "(" + "done" + ")";
	}
}
