package com.example.aventurasdemarcoyluis.controller.linkedlist;

public interface ITurnList {
	void addFirst(Object o);
	boolean includes(Object o);
	int size();
	boolean isEmpty();
	void remove(int index);
	String print();
}
