import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;

public class Day22 {
	public static void main(String[] args) throws IOException {
		List<String> lines = Files.readAllLines(Paths.get("input22.txt"));
		Queue<Integer> first = new ArrayBlockingQueue<>(50);
		Queue<Integer> second = new ArrayBlockingQueue<>(50);
		boolean firstPlayer = true;

		for (int i = 1; i < lines.size(); i++) {
			String line = lines.get(i);
			if (line.isEmpty()) {
				firstPlayer = false;
				++i;
				continue;
			}

			if (firstPlayer)
				first.add(Integer.parseInt(line));
			else
				second.add(Integer.parseInt(line));
		}

		Queue<Integer> firstP = copy(first);
		Queue<Integer> secondP = copy(second);
		System.out.println(sumPoints(playNormal(firstP, secondP)));

		int winner = playAdvanced(first, second, new HashSet<>());
		if (winner == 1) System.out.println(sumPoints(first));
		else System.out.println(sumPoints(second));
	}

	private static Queue<Integer> playNormal(Queue<Integer> first, Queue<Integer> second) {
		while (true) {
			int card1 = first.remove();
			int card2 = second.remove();

			if (card1 > card2) {
				first.add(card1);
				first.add(card2);
			} else {
				second.add(card2);
				second.add(card1);
			}

			if (first.size() == 0 || second.size() == 0)
				break;
		}

		if (first.size() == 0) return second;
		else return first;
	}

	private static int playAdvanced(Queue<Integer> first, Queue<Integer> second, Set<List<Integer>> situations) {
		while (true) {
			List<Integer> situation = makeList(copy(first), copy(second));
			if (situations.contains(situation)) {
				return 1;
			}
			situations.add(situation);

			int card1 = first.remove();
			int card2 = second.remove();
			int winner = -1;

			if (first.size() >= card1 && second.size() >= card2) {
				Queue<Integer> fCopy = takeFirst(card1, first);
				Queue<Integer> sCopy = takeFirst(card2, second);
				winner = playAdvanced(fCopy, sCopy, new HashSet<>());
			} else {
				winner = card1 > card2 ? 1 : 2;
			}

			if (winner == 1) {
				first.add(card1);
				first.add(card2);
			} else {
				second.add(card2);
				second.add(card1);
			}

			if (first.size() == 0 || second.size() == 0)
				break;
		}

		if (first.size() == 0) return 2;
		else return 1;
	}

	private static Queue<Integer> takeFirst(int noOfCards, Queue<Integer> first) {
		Queue<Integer> queue = new ArrayBlockingQueue<>(50);
		boolean flag = true;
		int head = -1;
		int counter = 0;
		while (true) {
			if (flag) {
				flag = false;
				head = first.peek();

				queue.add(head);
				first.add(head);
				first.remove();
				++counter;
			}

			int value = first.peek();
			if (value == head)
				break;
			if (counter < noOfCards) {
				queue.add(value);
			}
			first.add(value);
			first.remove();
			++counter;
		}
		return queue;
	}

	private static Queue<Integer> copy(Queue<Integer> first) {
		Queue<Integer> queue = new ArrayBlockingQueue<>(50);
		boolean flag = true;
		int head = -1;
		while (true) {
			if (flag) {
				flag = false;
				head = first.peek();

				queue.add(head);
				first.add(head);
				first.remove();
			}

			int value = first.peek();
			if (value == head)
				break;
			queue.add(value);
			first.add(value);
			first.remove();
		}
		return queue;
	}
	
	private static List<Integer> makeList(Queue<Integer> firstCopy, Queue<Integer> secondCopy) {
		List<Integer> list = new ArrayList<>();
		while (!firstCopy.isEmpty()) {
			list.add(firstCopy.remove());
		}
		list.add(-1);
		while (!secondCopy.isEmpty()) {
			list.add(secondCopy.remove());
		}
		return list;
	}

	private static int sumPoints(Queue<Integer> first) {
		int sum = 0;
		int size = first.size();
		while (first.size() != 0) {
			sum += size * first.remove();
			size--;
		}
		return sum;
	}
}
