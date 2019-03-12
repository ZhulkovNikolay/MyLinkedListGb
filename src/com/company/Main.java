package com.company;

public class Main {

    public static void main(String[] args) {

        MyLinkedList<Character> list = new MyLinkedList<>();
        list.addFirst('a');
        list.addFirst('b');
        list.addLast('c');
        System.out.println(list.size());
        System.out.println(list);
        System.out.println(list.get(1));
        list.set(1, 'd');
        System.out.println(list.get(1));
        list.addLast('e');
        list.addFirst('g');
        list.add(2, 'f');
        System.out.println(list);
        list.removeLast();
        list.removeFirst();
        System.out.println(list.size());
       // System.out.println(list);
        //проверяем итератор
        for (Character character : list) {
            System.out.print(character + ", ");
        }
        //System.out.println(list.remove('a'));//??? 'd' null
        System.out.println(list);
        list.removeFirst();
        list.removeFirst();
        list.removeFirst();
        System.out.println(list);
        System.out.println(list.size());
        list.addFirst('x');
        System.out.println(list);
    }
}


