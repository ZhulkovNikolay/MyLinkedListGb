package com.company;

import java.util.Iterator;
import java.util.NoSuchElementException;

class MyLinkedList<Item> implements Iterable<Item>  {

    @Override
    public Iterator<Item> iterator() {
        return new MyLinkedListIterator();
    }
    //нам нужен внутренний скрытый класс, который как раз реализует итератор
    private class MyLinkedListIterator implements Iterator<Item> {

        Node current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    //внутренний класс, реализующий узел связанного списка
    private class Node {
        Item item;
        Node next;
        Node previous;

        //37 min
        public Node(Node previous, Item item, Node next) {
            this.previous = previous;
            this.item = item;
            this.next = next;
        }
    }

    //---------------------------------

    private Node first = null;
    private Node last = null;
    private int size = 0;

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public Item getFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("List is empty");
        }
        return first.item;
    }

    public void addFirst(Item item) {
        //запоминаем узел, который был до нашего действия
        Node oldFirst = first;
        first = new Node(null, item, oldFirst); // сюда кладем наш новый объект и наш старый следом.
        if (isEmpty()) {
            last = first;
        } else {
            //в случае если список НЕ пустой
            oldFirst.previous = first;
        }
        size++;
    }

    //возвращаем элемент который удалили. Если пользователь захочет с ним поработать
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("List Is Empty");
        }
        Node second = first.next; //сохраняем узел заранее
        Item item = first.item; // соъраняем удаляемый объект, чтобы потом его можно было вернуть
        first.item = null;
        first.next = null;
        first = second;
        size--;
        if (isEmpty()) {
            // если список пуст, то первый элемент и так будет Null
            // а последний нужно обработать ручками
            last = null;
        } else {
            second.previous = null;
        }

        return item;
    }

    // кроме ссылки на 1 элемент, нужно хранить ссылку на послеждний элемент списка
    // добавим его в переменные
    public Item getLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("list is empty");
        }
        return last.item;
    }

    public void addLast(Item item) {
        Node oldLast = last;
        last = new Node(oldLast, item, null);
        if (isEmpty()) {
            first = last;
        } else {
            oldLast.next = last;
        }
        size++;
    }

    //чтобы убрать последний элемент, нужно иметь ссылку на предпоследний элемент
    //для этого список нужно апгрейдить до двусвязанного
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("list is empty");
        }
        Item item = last.item;
        Node previous = last.previous;
        last.previous = null;
        //возможно эта строчка лишняя. Сборщик ее уберет(с) 54мин
       // last.item = null;
        last = previous;
        size--;
        if (isEmpty()) {
            first = null;
        } else {
            last.next = null;
        }
        return item;
    }

    //берем элемент по индексу
    public Item get(int index) {
        if (index < 0 || index > size - 1) {
            throw new IndexOutOfBoundsException();
        }
        int currentIndex = 0;
        Node current = first;
        //мы идем по индексу от начала и до конца.
        //для эффективности, если вдруг нужный элдемент окажется в самом конце
        //мы будем выбирать с какой стороны подходить к элементу
        while (currentIndex < index) {
            current = current.next;
            currentIndex++;
        }
        return current.item;
    }

    public void set(int index, Item item) {
        if (index < 0 || index > size - 1) {
            throw new IndexOutOfBoundsException();
        }
        int currentIndex = 0;
        Node current = first;
        while (currentIndex < index) {
            current = current.next;
            currentIndex++;
        }
        current.item = item;
    }

    public int indexOf(Item item) {
        Node current = first;
        int currentIndex = 0;
        while (current != null && !current.item.equals(item)) {
            current = current.next;
            currentIndex++;
        }
        return current != null ? currentIndex : -1;
    }

    public boolean contains(Item item) {
        return indexOf(item) > -1;
    }

    public Item remove(Item item) {
        Node current = first;
        //бежим по списку, ищем узел который хоти удалить
        while (current != null && !current.item.equals(item)) {
            current = current.next;
        }
        //если его не нашли, значит удалять нечего
        if (current == null) {
            return null;
        }

        if (current == first) {
            removeFirst();
        }
        if (current == last) {
            return removeLast();
        }

        Node next = current.next;
        Node previous = current.previous;
        previous.next = next;
        next.previous = previous;
        size--;
        current.previous = null;
        current.next = null;
        return current.item;
    }

    public void add(int index, Item item) { //add before
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        if (index == 0) {
            addFirst(item);
            return;
        } else if (index == size) {
            addLast(item);
            return;
        }

        int currentIndex = 0;
        Node current = first;
        while (currentIndex < index) {
            current = current.next;
            currentIndex++;
        }
        Node newNode = new Node(current.previous, item, current);
        Node previous = current.previous;
        previous.next = newNode;
        current.previous = newNode;
        size++;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        Node current = first;
        while (current != null) {
            s.append(current.item.toString());
            s.append(", ");
            current = current.next;
        }

        return s.toString();
    }
}