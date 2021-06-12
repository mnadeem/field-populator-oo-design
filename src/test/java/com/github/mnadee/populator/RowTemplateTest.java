package com.github.mnadee.populator;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.github.mnadee.populator.provider.FunctionValueProvider;
import com.github.mnadee.populator.provider.HardCodedValueProvider;
import com.github.mnadee.populator.provider.LookupValueProvider;
import com.github.mnadee.populator.provider.RuleValueProvider;
import com.github.mnadee.populator.provider.ValueProvider;

public class RowTemplateTest {
	
	@Test
	public void basicTest() {		
		RowTemplate rowTemplate = new RowTemplate(providers());
		RowInstance rowInstance = rowTemplate.set("x", "R:58")
		        .set("y", "H:R")
		        .set("z", "L:Value:\"k1\",\"k2\"")
		        .set("a", "F:Min non zero:1007,1024")
		        .build();
		
		System.out.println(rowInstance);
	}
	
	@Test
	public void columnReferenceTest() {
		
		RowTemplate rowTemplate = new RowTemplate(providers());
		RowInstance rowInstance = rowTemplate.set("x", "L:Value:[y],\"xk1\"")
				.set("y", "L:Value:[z],\"yk1\"")
				.set("z", "R:1")
				.set("a", "F:Min non zero:1007,1024")
				.build();
		
		System.out.println(rowInstance);
	}

	private List<ValueProvider> providers() {
		return Arrays.asList(new HardCodedValueProvider(), new FunctionValueProvider(), new LookupValueProvider(), new RuleValueProvider());
	}
}
