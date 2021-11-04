package com.example.aventurasdemarcoyluis.controller.linkedlist;

public class NullIUnit implements IUnit {

	@Override
	public Object getValue() {
		return null;
	}

	@Override
	public IUnit getNext() {
		return null;
	}

	@Override
	public boolean includes(Object o) {
		return false;
	}
	@Override
	public int size() {
		return 0;
	}

	@Override
	public void setNext(Object o, IUnit next) {}

	@Override
	public String print() {
		return "";
	}
}
