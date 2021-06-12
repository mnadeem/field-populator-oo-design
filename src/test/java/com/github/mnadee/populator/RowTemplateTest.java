package com.github.mnadee.populator;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import com.github.mnadee.populator.provider.FunctionValueProvider;
import com.github.mnadee.populator.provider.HardCodedValueProvider;
import com.github.mnadee.populator.provider.LookupValueProvider;
import com.github.mnadee.populator.provider.RuleValueProvider;
import com.github.mnadee.populator.provider.ValueProvider;

public class RowTemplateTest {
	
	@Test(expected = IllegalStateException.class)
	public void validationTest() {

		new RowTemplate(Collections.emptyList());
	}

	@Test(expected = IllegalStateException.class)
	public void cyclic1() {

		RowTemplate rowTemplate = new RowTemplate(providers());
		rowTemplate.field("x", "L:Value:[y],\"xk1\"")
				.field("y", "L:Value:[z],\"yk1\"")
				.field("z", "R:[x]")
				.field("a", "F:Min non zero:1007,1024")
				.buildInstance();
	}
	
	@Test(expected = IllegalStateException.class)
	public void cyclic2() {

		RowTemplate rowTemplate = new RowTemplate(providers());
		rowTemplate.field("x", "L:Value:[y],\"xk1\"")
				.field("y", "L:Value:[z],\"yk1\"")
				.field("z", "R:[a]")
				.field("a", "F:Min non zero:[x],1024")
				.buildInstance();
	}

	@Test
	public void basicTest() {

		RowTemplate rowTemplate = new RowTemplate(providers());
		RowInstance rowInstance = rowTemplate.field("x", "R:58")
		        .field("y", "H:R")
		        .field("z", "L:Value:\"k1\",\"k2\"")
		        .field("a", "F:Min non zero:1007,1024")
		        .buildInstance();
		
		System.out.println(rowInstance);
	}

	@Test
	public void columnReferenceTest() {

		RowTemplate rowTemplate = new RowTemplate(providers());
		RowInstance rowInstance = rowTemplate.field("x", "L:Value:[y],\"xk1\"")
				.field("y", "L:Value:[z],\"yk1\"")
				.field("z", "R:1")
				.field("a", "F:Min non zero:[x],1024")
				.buildInstance();
		
		System.out.println(rowInstance);
	}

	private List<ValueProvider> providers() {
		return Arrays.asList(new HardCodedValueProvider(), new FunctionValueProvider(), new LookupValueProvider(), new RuleValueProvider());
	}
}
