package com.citsamex.service.tony;

public abstract class AbstractCondition {
	private AbstractCondition next;

	public AbstractCondition getNext() {
		return next;
	}

	public AbstractCondition setNext(AbstractCondition next) {
		this.next = next;
		return this;
	}

	public abstract String visit(String tony);
}
