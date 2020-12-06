import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day6 {
	public static void main(String[] args) throws IOException {
		List<String> lines = Files.readAllLines(Paths.get("input6.txt"));
		Set<Character> answers = new HashSet<>();
		int sum = 0;

		for (String line : lines) {
			if (line.isEmpty()) {
				sum += answers.size();
				answers = new HashSet<>();
				continue;
			}
			for (char c : line.toCharArray()) answers.add(c);
		}
		sum += answers.size();
		System.out.println(sum);

		Set<Character> startSet = IntStream.rangeClosed('a', 'z').mapToObj(c -> (char) c).collect(Collectors.toSet());
		Set<Character> set1 = new HashSet<>(startSet);
		int sum2 = 0;

		for (String line : lines) {
			if (line.isEmpty()) {
				sum2 += set1.size();
				set1 = new HashSet<>(startSet);
				continue;
			}

			Set<Character> set2 = new HashSet<>();
			for (char c : line.toCharArray()) set2.add(c);
			set1.retainAll(set2);
		}
		sum2 += set1.size();
		System.out.println(sum2);
	}
}
