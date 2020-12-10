import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;

public class Day9 {
	public static void main(String[] args) throws IOException {
		List<String> listString = Files.readAllLines(Paths.get("input9.txt"));
		Set<Long> preamble = new HashSet<>();
		Queue<Long> queue = new ArrayBlockingQueue<>(25);
		List<Long> list = new ArrayList<>();
		for (String s : listString) list.add(Long.parseLong(s));

		for (int i = 0; i < 25; i++) {
			preamble.add(list.get(i));
			queue.add(list.get(i));
		}

		int i = 25;
		boolean found = false;
		Long invalid;
		
		while (true) {
			Long next = list.get(i++);
			found = false;
			for (Long a : preamble) {
				if (preamble.contains(next - a) && 2 * a != next) {
					found = true;
					break;
				}
			}

			if (found) {
				preamble.remove(queue.remove());
				queue.add(next);
				preamble.add(next);

			} else {
				invalid = next;
				System.out.println(invalid);
				break;
			}
		}

		System.out.println(encryptionWeakness(list, invalid));
	}

	private static Long encryptionWeakness(List<Long> list, Long invalid) {
		for (int i = 0; i < list.size(); i++) {
			Long sum = 0L;
			List<Long> elem = new ArrayList<>();
			for (int j = i + 1; j < list.size(); j++) {
				sum += list.get(j);
				elem.add(list.get(j));
				if (sum.equals(invalid)) {
					elem.sort((a, b) -> a.compareTo(b));
					return elem.get(0) + elem.get(elem.size() - 1);
				}
			}
		}
		return -1L;
	}
}
