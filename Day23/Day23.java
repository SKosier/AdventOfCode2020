import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;

public class Day23 {
	public static void main(String[] args) {
		cups("562893147", 0, 100, true);
		cups("562893147", 1000000, 10000000, false);
	}

	public static void cups(String initialCups, int addCupsUntil, int rotations, boolean first) {
		MyList list = new MyList("562893147", addCupsUntil);
		for (int i = 0; i < rotations; i++) {
			Queue<Integer> elements = new ArrayBlockingQueue<Integer>(10);
			Set<Integer> set = new HashSet<>();
			elements.add(list.removeNext());
			elements.add(list.removeNext());
			elements.add(list.removeNext());
			set.addAll(elements);

			int dest = list.getCurrent() - 1;
			while (elements.contains(dest)) {
				dest--;
				if (dest == 0) break;
			}
			if (dest == 0) {
				dest = list.findBiggest(set);
			}

			list.addAfter(dest, elements);
			list.moveFor(1);
		}

		if (first) System.out.println(list);
		else System.out.println(list.getResult());
	}
}
