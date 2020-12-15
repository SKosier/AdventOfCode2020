import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

public class Day13 {
	public static void main(String[] args) throws IOException {
		List<String> lines = Files.readAllLines(Paths.get("input13.txt"));
		int start = Integer.parseInt(lines.get(0));
		String[] parts = lines.get(1).split(",");
		List<Integer> buses = new LinkedList<>();
		for (String p : parts)
			if (!p.equals("x"))
				buses.add(Integer.parseInt(p));

		int min = Integer.MAX_VALUE;
		int id = 0;
		for (int bus : buses) {
			int nextBus = ((start / bus) * bus) + bus - start;
			if (nextBus < min) {
				min = nextBus;
				id = bus;
			}
		}
		System.out.println(id * min);

		long sum = 0, prod = 1;
		for (int i = 0; i < parts.length; i++) {
			if (parts[i].charAt(0) == 'x') continue;
			int k = Integer.parseInt(parts[i]);
			while (((sum + i) % k) != 0) {
				sum += prod;
			}

			prod *= k;
		}
		System.out.println(sum);
	}
}
