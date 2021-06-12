package com.github.mnadee.populator.provider;

public interface ValueProvider {

	boolean canProvide(String indicator);

	String provide(String type, String[] params);
}
