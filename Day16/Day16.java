import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Day16 {
	public static void main(String[] args) throws IOException {
		List<String> lines = Files.readAllLines(Paths.get("input16.txt"));
		List<List<Integer>> constraints = new ArrayList<>();
		int i = 0;
		for (String line : lines) {
			if (line.isEmpty()) break;
			
			String[] parts = line.split(":")[1].split("or");
			List<Integer> numbers = new ArrayList<>();
			numbers.add(Integer.parseInt(parts[0].split("-")[0].trim()));
			numbers.add(Integer.parseInt(parts[0].split("-")[1].trim()));
			numbers.add(Integer.parseInt(parts[1].split("-")[0].trim()));
			numbers.add(Integer.parseInt(parts[1].split("-")[1].trim()));
			constraints.add(numbers);
			++i;
		}

		List<Integer> myTicket = new ArrayList<>();
		String[] myValues = lines.get(i + 2).split(",");
		for (String v : myValues) myTicket.add(Integer.parseInt(v));

		long sum = 0;
		List<List<Integer>> validTickets = new ArrayList<>();
		for (i = i + 5; i < lines.size(); i++) {
			List<Integer> ticket = new ArrayList<>();
			String[] values = lines.get(i).split(",");
			boolean isValid = true;
			for (String v : values) {
				int value = Integer.parseInt(v);
				ticket.add(value);
				
				if (!doesntsatisfy(constraints, value)) {
					sum += value;
					isValid = false;
				}
			}
			if (isValid) validTickets.add(ticket);
		}
		System.out.println(sum);

		int MAX = validTickets.get(0).size();
		Map<Integer, List<Integer>> constCount = new HashMap<>();
		for (int j = 0; j < constraints.size(); j++) {
			List<Integer> list = constraints.get(j);
			Integer[] fields = new Integer[MAX];
			Arrays.fill(fields, 0);

			for (List<Integer> ticket : validTickets) {
				for (int k = 0; k < ticket.size(); k++) {
					int field = ticket.get(k);
					if ((field >= list.get(0) && field <= list.get(1)) || (field >= list.get(2) && field <= list.get(3))) {
						fields[k]++;
					}
				}
			}
			constCount.put(j, Arrays.asList(fields));
		}

		Map<Integer, Integer> const_field = new HashMap<>();
		Set<Integer> indices = new HashSet<>();
		MAX = validTickets.size();
		
		for (int j = 1; j <= constraints.size(); j++) {
			for (Map.Entry<Integer, List<Integer>> entry : constCount.entrySet()) {
				Set<Integer> set = findIndices(entry.getValue(), MAX);
				
				if (set.size() == j && j != 1) {
					int index = findNewIndex(indices, set);
					const_field.put(entry.getKey(), index);
					indices.add(index);

				} else if (set.size() == j && j == 1) {
					const_field.put(entry.getKey(), entry.getValue().indexOf(MAX));
					indices.add(entry.getValue().indexOf(MAX));
				}
			}
		}

		long prod = 1;
		for (int j = 0; j < 6; j++) {
			prod *= myTicket.get(const_field.get(j));
		}
		System.out.println(prod);
	}

	private static int findNewIndex(Set<Integer> alreadyChecked, Set<Integer> set) {
		for (int s : set) {
			if (!alreadyChecked.contains(s)) return s;
		}
		return -1;
	}

	private static Set<Integer> findIndices(List<Integer> value, int MAX) {
		Set<Integer> set = new HashSet<>();
		for (int i = 0; i < value.size(); i++) {
			if (value.get(i) == MAX) set.add(i);
		}
		return set;
	}

	private static boolean doesntsatisfy(List<List<Integer>> constraints, int value) {
		boolean flag = false;
		for (List<Integer> list : constraints) {
			for (int i = 0; i < list.size(); i = i + 2) {
				if (value >= list.get(i) && value <= list.get(i + 1)) flag = true;
			}
		}
		return flag;
	}
}
