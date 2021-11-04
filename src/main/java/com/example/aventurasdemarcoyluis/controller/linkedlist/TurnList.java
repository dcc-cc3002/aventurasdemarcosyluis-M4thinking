package com.example.aventurasdemarcoyluis.controller.linkedlist;

public class TurnList implements ITurnList {
	private IUnit first;

	public TurnList() { first = new NullIUnit(); }

	@Override
	public void addFirst(Object o) { first = new ConcreteUnit(o, first); }

	@Override
	public boolean includes(Object o) { return first.includes(o); }

	@Override
	public int size() { return first.size(); }

	@Override
	public boolean isEmpty() { return this.size() == 0; }

	@Override
	public void remove(int index) {
		if (index == 0) {
			// removing the first element must be handled specially
			first = first.getNext();
		} else {
			// removing some element further down in the list;
			// traverse to the node before the one we want to remove
			if(index <= this.size()){
				IUnit current = first;
				for (int i = 0; i < index - 1; i++) {
					current = current.getNext();
				}

				// change its next pointer to skip past the offending node
				current.setNext(current.getValue(),current.getNext().getNext());
			}

		}
	}

	@Override
	public String print() {
		return first.print();
	}
}
