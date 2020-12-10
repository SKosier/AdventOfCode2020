import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Day10 {
	public static void main(String[] args) throws IOException {
		List<String> lines = Files.readAllLines(Paths.get("input10.txt"));
		List<Integer> adapters = new ArrayList<>();
		for (String s : lines)
			adapters.add(Integer.parseInt(s));
		adapters.add(0);
		adapters.sort((a, b) -> a.compareTo(b));
		adapters.add(adapters.get(adapters.size() - 1) + 3);

		int dif1 = 0, dif3 = 0;
		for (int i = 0; i < adapters.size() - 1; i++) {
			if (Math.abs(adapters.get(i) - adapters.get(i + 1)) == 1)
				++dif1;
			else if (Math.abs(adapters.get(i) - adapters.get(i + 1)) == 3)
				++dif3;
		}
		System.out.println(dif1 * dif3);
		System.out.println(countArrangements(adapters.get(adapters.size()-1), new HashSet<>(adapters), 0, new HashMap<>()));
	}

	private static long countArrangements(int last, Set<Integer> adapters, int curr, Map<Integer, Long> map) {
		if(adapters.contains(curr) == false) return 0;
		if(curr == last) return 1; 
		if(map.containsKey(curr)) return map.get(curr);
		
		long sum = 0;
		if (adapters.contains(curr + 1)) {
			long fromNum = countArrangements(last, adapters, curr + 1, map);
			sum += fromNum;
		}
		if (adapters.contains(curr + 2)) {
			long fromNum= countArrangements(last, adapters, curr + 2, map);
			sum += fromNum;
		}
		if (adapters.contains(curr + 3)) {
			long fromNum =countArrangements(last, adapters, curr + 3, map); 
			sum += fromNum;
		}
		map.put(curr, sum);
		return sum;
	}
}

//0 1 4 5 6 7 10 11 12 15 16 19 22
