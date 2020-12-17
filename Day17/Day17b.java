import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day17b {
	public static void main(String[] args) throws IOException {
		List<String> lines = Files.readAllLines(Paths.get("input17.txt"));
		Set<Quatro<Integer, Integer, Integer, Integer>> cubes = new HashSet<>();
	
		for (int i = 0; i < lines.size(); i++) {
			for (int j = 0; j < lines.get(i).length(); j++) {
				if(lines.get(i).charAt(j) == '#') cubes.add(new Quatro<Integer, Integer, Integer, Integer>(i, j, 0, 0));
			}
		}
		
		for(int i = 0; i < 6; i++) {
			cubes = changeStates(cubes);
		}
		
		System.out.println(cubes.size());
	}

	private static Set<Quatro<Integer, Integer, Integer, Integer>> changeStates(Set<Quatro<Integer, Integer, Integer, Integer>> cubes) {
		Set<Quatro<Integer, Integer, Integer, Integer>> updated = new HashSet<>();
		for(int i = -10; i < 15; i++) {
			for(int j = -10; j < 15; j++) {
				for(int k = -10; k < 15; k++) {
					for(int l = -10; l < 15; l++) {
						if(shouldBeActive(i, j, k, l, cubes)) updated.add(new Quatro<>(i, j, k, l));
					}
				}
			}
		}
		return updated;
	}

	private static boolean shouldBeActive(int x, int y, int z, int w, Set<Quatro<Integer, Integer, Integer, Integer>> cubes) {
		int actNeighb = 0;
		for(int i = x-1; i <= x+1; i++) {
			for(int j = y-1; j <= y+1; j++) {
				for(int k = z-1; k <= z+1; k++) {
					for(int l = w-1; l <= w+1; l++) {
						if(i == x && j == y && k == z && l == w) continue;
						if(cubes.contains(new Quatro<Integer, Integer, Integer, Integer>(i, j, k, l))) {
							actNeighb++;
						}
					}
				}
			}
		}
		if(actNeighb == 3) return true;
		if(actNeighb == 2 && cubes.contains(new Quatro<Integer, Integer, Integer, Integer>(x, y, z, w))) return true;
		return false;
	}
}
