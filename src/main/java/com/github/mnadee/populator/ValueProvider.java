package com.github.mnadee.populator;

public interface ValueProvider {

	boolean canProvide(String indicator);

	String provide(String type, String[] params);
}
