package com.github.mnadee.populator.provider;

import com.github.mnadee.populator.Constants;

public class LookupValueProvider implements ValueProvider {

	@Override
	public boolean canProvide(String indicator) {
		return Constants.LOOKUP_INDICATOR.equalsIgnoreCase(indicator);
	}

	@Override
	public String provide(String type, String[] params) {
		return params[0]+":" + params[1]+ ":" + "done";
	}
}
