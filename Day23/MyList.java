import java.util.Queue;
import java.util.Set;

public class MyList {
	Node head;
	Node last;
	Node current;
	Node[] nodes;
	
	int MAXIMUM;

	public MyList(String cups, int addCupsUntil) {
		if(cups.length() > addCupsUntil) {
			nodes = new Node[cups.length()+1];
			MAXIMUM = cups.length();
		
		} else {
			nodes = new Node[addCupsUntil + 1];
			MAXIMUM = addCupsUntil;
		}
		
		for (char value : cups.toCharArray()) {
			Node el = add(Integer.parseInt(String.valueOf(value)));
			nodes[el.value] =  el;
		}
		
		for(int i = cups.length() + 1; i <= addCupsUntil; i++) {
			Node el = add(i);
			nodes[i] = el;
		}
	}

	public Node add(int value) {
		Node element = new Node(value);
		if (head == null) {
			head = element;
			head.next = element;

		} else if (head == last) {
			head.next = element;

		} else {
			last.next = element;
		}

		last = element;
		element.next = head;
		current = head;
		return element;
	}

	public int removeNext() {
		Node node = current;
		int returnValue = node.next.value;
		if(returnValue == head.value) {
			head = current;
		}
		
		node.next = node.next.next;
		return returnValue;
	}

	public void addAfter(int dest, Queue<Integer> elements) {
		Node node = nodes[dest];

		while (elements.isEmpty() == false) {
			Node newNode = new Node(elements.remove());
			newNode.next = node.next;
			node.next = newNode;
			node = node.next;
			nodes[newNode.value] = newNode;
		}
	}
	
	public Node moveFor(int noOfMoves) {
		for (int i = 0; i < noOfMoves; i++) {
			current = current.next;
		}
		return current;
	}

	public int findBiggest(Set<Integer> set) {
		int max = MAXIMUM;
		while(set.contains(max)) {
			max--;
		}
		
		return max;
	}

	public int getCurrent() {
		return current.value;
	}
	
	public long getResult() {
		long result = (long) nodes[1].next.value * (long) nodes[1].next.next.value;
		return result;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		Node node = head;
		for(int i = 0; i < 9; i++) {
			sb.append(node);
			node = node.next;
		}
		return sb.toString();
	}

	class Node {
		int value;
		Node next;

		public Node(int value) {
			this.value = value;
		}

		public String toString() {
			return String.valueOf(value);
		}
	}
}
