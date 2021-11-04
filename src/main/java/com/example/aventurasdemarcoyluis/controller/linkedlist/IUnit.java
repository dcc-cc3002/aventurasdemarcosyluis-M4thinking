package com.example.aventurasdemarcoyluis.controller.linkedlist;

interface IUnit {
	Object getValue();
	IUnit getNext();
	boolean includes(Object o);
	int size();
	void setNext(Object o, IUnit next);
	String print();
}

