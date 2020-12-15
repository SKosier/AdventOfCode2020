import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day15 {
	public static void main(String[] args) {
		List<Integer> list = List.of(15, 5, 1, 4, 7, 0);
		printNth(2020, list);
		printNth(30000000, list);
	}

	private static void printNth(int n, List<Integer> list) {
		Map<Integer, Integer> map = list.stream().collect(Collectors.toMap(e -> e, e -> list.indexOf(e) + 1));
		int last = 0, next = 0, i = map.size() + 1;
		boolean lastWasSpoken = false;

		while (i < n + 1) {
			if (!lastWasSpoken) {
				next = 0;

			} else {
				next = i - 1 - map.get(last);
				map.put(last, i - 1);
			}

			lastWasSpoken = map.containsKey(next);
			if (!lastWasSpoken) map.put(next, i);
			++i;
			last = next;
		}
		System.out.println(next);
	}
}
