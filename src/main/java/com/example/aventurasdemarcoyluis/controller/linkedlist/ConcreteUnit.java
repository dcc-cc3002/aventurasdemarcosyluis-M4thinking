package com.example.aventurasdemarcoyluis.controller.linkedlist;

class ConcreteUnit implements IUnit {
	private Object value;
	private IUnit next;
	public ConcreteUnit(Object o, IUnit IUnit) {
		value = o;
		next = IUnit;
	}

	@Override
	public Object getValue() {
		return value;
	}

	@Override
	public IUnit getNext() {
		return next;
	}

	@Override
	public boolean includes(Object o) {
		return value.equals(o) || next.includes(o);
	}
	@Override
	public int size() { return 1 + next.size(); }

	@Override
	public void setNext(Object o, IUnit next) {
		this.next = next;
	}

	@Override
	public String print() {
		return value.toString()+ " " + next.print();
	}
}
