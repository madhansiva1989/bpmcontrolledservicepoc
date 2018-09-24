package com.claim.processing.domain.model;

public class ValueType<K> {

	private K value;
	private String type;

	public ValueType(K value, String type) {
		super();
		this.value = value;
		this.type = type;
	}

	public ValueType(K value) {
		super();
		this.value = value;
		this.type = value.getClass().getSimpleName();
	}

	public K getValue() {
		return value;
	}

	public void setValue(K value) {
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
