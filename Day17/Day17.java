import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day17 {
	public static void main(String[] args) throws IOException {
		List<String> lines = Files.readAllLines(Paths.get("input17.txt"));
		Set<Triple<Integer, Integer, Integer>> cubes = new HashSet<>();
	
		for (int i = 0; i < lines.size(); i++) {
			for (int j = 0; j < lines.get(i).length(); j++) {
				if(lines.get(i).charAt(j) == '#') cubes.add(new Triple<Integer, Integer, Integer>(i, j, 0));
			}
		}
		
		for(int i = 0; i < 6; i++) {
			cubes = changeStates(cubes);
		}
		
		System.out.println(cubes.size());
	}

	private static Set<Triple<Integer, Integer, Integer>> changeStates(Set<Triple<Integer, Integer, Integer>> cubes) {
		Set<Triple<Integer, Integer, Integer>> updated = new HashSet<>();
		for(int i = -10; i < 15; i++) {
			for(int j = -10; j < 15; j++) {
				for(int k = -10; k < 15; k++) {
					if(shouldBeActive(i, j, k, cubes)) updated.add(new Triple<>(i, j, k));
				}
			}
		}
		return updated;
	}

	private static boolean shouldBeActive(int x, int y, int z, Set<Triple<Integer, Integer, Integer>> cubes) {
		int actNeighb = 0;
		for(int i = x-1; i <= x+1; i++) {
			for(int j = y-1; j <= y+1; j++) {
				for(int k = z-1; k <= z+1; k++) {
					if(i == x && j == y && k == z) continue;
					if(cubes.contains(new Triple<Integer, Integer, Integer>(i, j, k))) {
						actNeighb++;
					}
				}
			}
		}
		if(actNeighb == 3) return true;
		if(actNeighb == 2 && cubes.contains(new Triple<Integer, Integer, Integer>(x, y, z))) return true;
		return false;
	}
}
