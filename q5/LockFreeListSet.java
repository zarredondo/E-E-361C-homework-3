package q5;

import java.util.concurrent.atomic.AtomicReference;

public class LockFreeListSet implements ListSet {
    // you are free to add members
    Node example = new Node(3);
    AtomicReference<Node> head = new AtomicReference<>(example);

    public LockFreeListSet() {
//        // implement your constructor here
//        example = new Node(10);
//        head = new AtomicReference<>(example);
    }


    public boolean add(int value) {
        // implement your add method here
        AtomicReference<Node> n, c, p;
        n = new AtomicReference<>(new Node(value));
        head = new AtomicReference<>(example);
        Node node, oldP, oldN;

        boolean successful = false;

        while (!successful) {
            Node asdf = head.get();
            if (head.get() == null) {
                head.compareAndSet(asdf, n.get());
                return true;
            }

            c = head;
            p = head;

            while (c.get() != null) {
                if (n.get().value < c.get().value && n.get().value > p.get().value) {
                    // then insert after c
                    // p.next = n;
                    // n.next = c;
                    oldP = p.get();
                    node = new Node(p.get().value);
                    node.next = n.get();
                    p.compareAndSet(oldP, node);

                    oldN = n.get();
                    node = new Node(n.get().value);
                    node.next = c.get();
                    n.compareAndSet(oldN, node);

                    return true;
                } else if (n.get().value == c.get().value) {
                    return false;
                }
                Node pNode = p.get();
                Node cNode = c.get();
                Node cNextNode = cNode.next;

                p.compareAndSet(pNode, cNode);
                c.compareAndSet(cNode, cNextNode);
            }

            // instead of p.next = n; ??
            oldP = p.get();
            node = new Node(p.get().value);
            node.next = n.get();
            successful = p.compareAndSet(oldP, node);
        }
        return false;
    }

    public boolean remove(int value) {
        // implement your remove method here
        return false;
    }

    public boolean contains(int value) {
        // implement your contains method here
        return false;
    }

    protected class Node {
        public Integer value;
        public Node next;
        public Node(Integer x) {
            value = x;
            next = null;
        }
    }

    /*
    return the string of list, if: 1 -> 2 -> 3, then return "1,2,3,"
    check simpleTest for more info
    */
    public String toString() {
        String s = "";

        if (head == null) {
            return s;
        }
        AtomicReference<Node> c = head;
        while (c.get() != null) {
            s += c.get().value + ",";
            c.compareAndSet(c.get(), c.get().next);
        }
        return s;
    }
}