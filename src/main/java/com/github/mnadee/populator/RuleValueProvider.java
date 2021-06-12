package com.github.mnadee.populator;

public class RuleValueProvider implements ValueProvider {

	private static final String RULE_INDICATOR = "R";

	@Override
	public boolean canProvide(String indicator) {
		return RULE_INDICATOR.equalsIgnoreCase(indicator);
	}

	@Override
	public String provide(String type, String[] params) {
		return params[0] + "(" + "done" + ")";
	}
}
