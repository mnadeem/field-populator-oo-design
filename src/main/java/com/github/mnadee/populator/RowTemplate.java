package com.github.mnadee.populator;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.mnadee.populator.provider.FunctionValueProvider;
import com.github.mnadee.populator.provider.HardCodedValueProvider;
import com.github.mnadee.populator.provider.LookupValueProvider;
import com.github.mnadee.populator.provider.RuleValueProvider;
import com.github.mnadee.populator.provider.ValueProvider;

public class RowTemplate {

	private static final char COLUMN_END_INDICATOR_QUOTES = '"';
	private static final char COLUMN_START_INDICATOR_QUOTES = '"';
	private static final char COLUMN_END_INDICATOR_COLON = ']';
	private static final char COLUMN_START_INDICATOR_COLON = '[';

	private static final String SEPERATOR_COMMA = ",";
	private static final String SEPERATOR_COLON = ":";

	private final List<ValueProvider> providers;
	private final Map<String, String> template;

	private RowInstance built = null;

	public RowTemplate(List<ValueProvider> providers) {
		this.providers = providers;
		this.template = new HashMap<>();
	}

	public RowTemplate set(String field, String value) {
		template.put(field, value);
		built = null;
		return this;
	}

	public RowInstance build() {
		if (built == null) {
			built = doBuild();
		}
		return built;
	}

	private RowInstance doBuild() {
		RowInstance rowInstance = new RowInstance();
		for (Map.Entry<String, String> entry : template.entrySet()) {
			updateAndGetFieldValue(rowInstance, entry.getKey(), entry.getValue());
		}
		return rowInstance;
	}

	private String updateAndGetFieldValue(RowInstance rowInstance, String field, String fieldValue) {
		String val = getFieldValue(rowInstance, field, fieldValue);
		rowInstance.set(field, val);
		return val;
	}

	private String getFieldValue(RowInstance rowInstance, String field, String fieldValue) {
		String[] values = fieldValue.split(SEPERATOR_COLON);
		int count = values.length;

		String indicator = values[0];
		String type = null;
		String[] params = null;

		if (count == 2) {
			params = params(values[1]); 
		} else {
			type = values[1];
			params = params(values[2]); 
		}
		return updateRowInstance(rowInstance, field, indicator, type, params);
	}

	private String[] params(String params) {
		return params.split(SEPERATOR_COMMA);
	}

	private String updateRowInstance(RowInstance rowInstance, String field, String indicator, String type, String[] params) {

		String[] evaluatedParam = evaluateParams(rowInstance, params);

		String value = rowInstance.get(field);
		if (value == null) {
			for (ValueProvider provider : this.providers) {
				if (provider.canProvide(indicator)) {				
					value = provider.provide(type, evaluatedParam);
				}
				if (value != null) {
					break ;
				}
			}
			if (value == null) {
				throw new IllegalStateException("Can't handle : " + indicator);
			}
		}
		return value;
	}

	private String[] evaluateParams(RowInstance rowInstance, String[] params) {
		String[] evaluatedParams = new String[params.length];
		for (int i = 0; i < params.length; i++) {
			String param = params[i];
			String paramVal = null;
			if (param.startsWith("[")) {				
				String fieldName = extractValueBetween(param, COLUMN_START_INDICATOR_COLON, COLUMN_END_INDICATOR_COLON);
				if (rowInstance.get(fieldName) == null) {				
					paramVal = updateAndGetFieldValue(rowInstance, fieldName, template.get(fieldName));
				}
			} else if(param.startsWith("\"")) {
				paramVal = extractValueBetween(param, COLUMN_START_INDICATOR_QUOTES, COLUMN_END_INDICATOR_QUOTES);
			} else {
				paramVal = getRuleProvider().provide(null, new String[]{param});
			}
			evaluatedParams[i] = paramVal;
		}

		return evaluatedParams;
	}

	private String extractValueBetween(String val, char start, char end) {
		return val.substring(val.indexOf(start) + 1, val.lastIndexOf(end));
	}

	private ValueProvider getRuleProvider() {
		for (ValueProvider valueProvider : providers) {
			if (valueProvider.canProvide(Constants.RULE_INDICATOR)) {
				return valueProvider;
			}
		}
		throw new IllegalStateException("Rule Provider not found");
	}

	public static void main(String[] args) {
		RowTemplate rowTemplate = new RowTemplate(providers());
		RowInstance rowInstance = rowTemplate.set("x", "L:Value:[y],\"xk1\"")
				.set("y", "L:Value:[z],\"yk1\"")
				.set("z", "R:1")
				.set("a", "F:Min non zero:1007,1024")
				.build();
		
		System.out.println(rowInstance);
	}

	private static List<ValueProvider> providers() {
		return Arrays.asList(new HardCodedValueProvider(), new FunctionValueProvider(), new LookupValueProvider(), new RuleValueProvider());
	}
}
