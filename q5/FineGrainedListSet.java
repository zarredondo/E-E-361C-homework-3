package q5;

import java.util.concurrent.locks.ReentrantLock;

public class FineGrainedListSet implements ListSet {
// you are free to add members
	Node head;

  public FineGrainedListSet() {
    // implement your constructor here
    head = null;
  }
	  
  public boolean add(int value) {
      // implement your add method here
      Node n = new Node(value);
      if (head == null) {
        head = n;
        return true;
      }

      Node c = head;
      Node p = head;
      while (c != null) {
          if (n.value < c.value && n.value > p.value) {
              // then insert after c
              p.lock.lock();
              n.lock.lock();
              p.next = n;
              n.next = c;
              p.lock.unlock();
              n.lock.unlock();
              return true;
          } else if (n.value == c.value) {
              return false;
          }
          p = c;
          c = c.next;
      }
      p.lock.lock();
      p.next = n;
      p.lock.unlock();

      return true;
  }
	  
  public boolean remove(int value) {
    // implement your remove method here	
    Node c = head;
    Node prev = head;
    if(c.value == value){
      head = c.next;
    }
    while (c != null) {
      if(c.value == value){
        prev.lock.lock();
        prev.next = c.next;
        prev.lock.unlock();
        return true;
      }else {
        prev = c;
        c = c.next;
      }
    }
    return false;
  }
	  
  public boolean contains(int value) {
      // implement your contains method here
      Node c = head;
      while (c != null) {
          if (c.value == value) {
              return true;
          } else {
              c = c.next;
          }
      }
      return false;
  }
	  
  protected class Node {
      ReentrantLock lock;
      public int value;
      public Node next;

      public Node (int x) {
          value = x;
          next = null;
          lock = new ReentrantLock();
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
      Node c = head;
      while (c != null) {
          s += c.value + ",";
          c = c.next;
      }
      return s;
    }
}
