package q5;

public class CoarseGrainedListSet implements ListSet {
    // you are free to add members
    Node head;

    public CoarseGrainedListSet() {
        // implement your constructor here
        head = null;
  }
  
  public synchronized boolean add(int value) {
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
              p.next = n;
              n.next = c;
              return true;
          } else if (n.value == c.value) {
              return false;
          }
          p = c;
          c = c.next;
      }
      p.next = n;

      return true;

      //return false;
  }
  
  public synchronized boolean remove (int value) {
	// implement your remove method here
      Node c = head;
      Node prev = head;
      if (c.value == value){
          head = c.next;
      }
      while (c != null){
          if (c.value == value) {
              prev.next = c.next;
              return true;
          } else {
              prev = c;
              c = c.next;
          }
      }
      return false;
  }
  
  public synchronized boolean contains(int value) {
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
	  public int value;
	  public Node next;
		    
	  public Node(int x) {
		  value = x;
		  next = null;
	  }
  }

    /*
    return the string of list, if: 1 -> 2 -> 3, then return "1,2,3,"
    check simpleTest for more info
    */
    public synchronized String toString() {
        String s = "";

        if (head == null) {
          return s;
        }
        Node c = head;
        while (c != null) {
          System.out.println(c.value);
          s += c.value + ",";
          c = c.next;
        }
        return s;
    }
}
