import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Day1 {
	public static void main(String[] args) throws IOException {
		List<String> input = Files.readAllLines(Paths.get("input.txt"));
		Set<Integer> set = new HashSet<>();
		for (String line : input) {
			int num = Integer.parseInt(line);
			if (set.contains(2020 - num)) System.out.println("First part: " + num * (2020 - num) + "\n");
			set.add(num);
		}

		List<Integer> list = new ArrayList<>(set);
		Map<Integer, Integer> map = set.stream().collect(Collectors.toMap(x -> x, x -> x));

		for (int i = 0; i < list.size(); i++) {
			for (int j = 0; j < list.size(); j++) {
				if (list.get(i) + list.get(j) > 2020) continue;
				map.put(list.get(i) + list.get(j), list.get(i) * list.get(j));
			}
		}

		for (int i = 0; i < list.size(); i++) {
			if (map.containsKey(2020 - list.get(i))) System.out.println(map.get(2020 - list.get(i)) * list.get(i));
		}
	}
}
