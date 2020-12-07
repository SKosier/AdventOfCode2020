import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

public class Day7 {
	public static void main(String[] args) throws IOException {
		List<String> lines = Files.readAllLines(Paths.get("input7.txt"));
		Map<String, Set<String>> colorMap = new HashMap<>();
		Map<String, List<Pair<String, Integer>>> colorMap2 = new HashMap<>();
		
		for (String line : lines) {
			String[] parts = line.split("bags contain");
			String[] bags = parts[1].split(",");
			String parentBag = parts[0].strip();

			List<Pair<String, Integer>> list = new LinkedList<>();
			
			for (String bag : bags) {
				bag = bag.strip();
				if(bag.split(" ")[0].equals("no")) continue;
				
				String nn = bag.split(" ")[1] + " " + bag.split(" ")[2];
				if (colorMap.containsKey(nn)) {
					colorMap.get(nn).add(parentBag);

				} else {
					Set<String> set = new HashSet<>();
					set.add(parentBag);
					colorMap.put(nn, set);
				}
				
				Pair<String, Integer> pair = new Pair<String, Integer>(nn, Integer.parseInt(bag.split(" ")[0]));
				list.add(pair);
			}
			colorMap2.put(parentBag, list);
		}
		
		System.out.println(DFS(colorMap,"shiny gold"));
		System.out.println(DFS2(colorMap2,"shiny gold", new HashSet<>()));
	}

	private static int DFS2(Map<String, List<Pair<String, Integer>>> colorMap, String bag, Set<String> checked) {
		checked.add(bag);
		
		List<Pair<String, Integer>> pairs = colorMap.get(bag);
		if(pairs.isEmpty()) return 0;
		
		int sum = 0;
		for(Pair<String, Integer> p : pairs) {
			sum += p.getSecond() + p.getSecond() * DFS2(colorMap, p.getFirst(), checked);
		}
		return sum;
	}
                         
	private static int DFS(Map<String, Set<String>> colorMap, String bag) {
		Set<String> toCheck = colorMap.get(bag);
		Set<String> checked = new HashSet<>(toCheck);
		Stack<String> stack = new Stack<>();
		stack.addAll(toCheck);
		
		while (stack.isEmpty() == false) {
			String color = stack.pop();
			Set<String> neigh = colorMap.get(color);
			if (neigh == null || neigh.isEmpty()) continue;
			for (String n : neigh) {
				if (!checked.contains(n)) {
					stack.push(n);
					checked.add(n);
				}
			}
		}
		return checked.size();
	}
}
