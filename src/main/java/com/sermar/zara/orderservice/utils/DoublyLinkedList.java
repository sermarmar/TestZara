package com.sermar.zara.orderservice.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class DoublyLinkedList<T> {

    private Node<T> head;
    private Node<T> tail;

    private int size;

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public T get(int index) {
        if(index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Out of range");
        }

        Node<T> currentNode = head;
        for(int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }

        return currentNode.data;
    }

    public List<T> getAll() {
        List<T> list = new ArrayList<>();

        Node<T> temporal = this.head;
        while(temporal != null) {
            list.add(temporal.data);
            temporal = temporal.next;
        }

        return list;
    }

    public void add(T data) {
        Node<T> newNode = new Node<>(data);
        if(isEmpty()) {
            head = newNode;
            tail = newNode;
        }
        else {
            Node<T> temporal = tail;
            tail = newNode;
            tail.setPrev(temporal);
            temporal.setNext(tail);
        }

        size++;
    }

    public void remove() {
        if(isEmpty()) {
            throw new NoSuchElementException("List is empty");
        }

        if(head == tail) {
            head = null;
            tail = null;
        }
        else {
            head = head.next;
            head.prev = null;
        }
        size--;
    }

    public void removeAll() {
        if(isEmpty()) {
            throw new NoSuchElementException("List is empty");
        }

        head = null;
        tail = null;
        size = 0;
    }
}
