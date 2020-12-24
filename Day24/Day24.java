import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day24 {
	public static void main(String[] args) throws IOException {
		List<String> lines = Files.readAllLines(Paths.get("input24.txt"));
		Map<Pair<Integer, Integer>, Character> map = new HashMap<>();

		for (String line : lines) {
			flip(line, map);
		}
		System.out.println(countBlack(map));

		for (int i = 0; i < 100; i++) {
			map = flipDay(map);
		}
		System.out.println(countBlack(map));
	}

	private static int countBlack(Map<Pair<Integer, Integer>, Character> map) {
		int black = 0;
		for (Pair<Integer, Integer> key : map.keySet()) {
			if (map.get(key) == 'b') ++black;
		}
		return black;
	}

	// nw w sw e e | nw, ne, e, w, sw, se
	private static void flip(String line, Map<Pair<Integer, Integer>, Character> map) {
		int row = 0, column = 0;
		for (int i = 0; i < line.length(); i++) {
			if (line.charAt(i) == 'e') {
				column++;

			} else if (line.charAt(i) == 'w') {
				column--;

			} else if (line.charAt(i) == 's' && line.charAt(i + 1) == 'e') {
				if (row % 2 == 0) {
					column++;
				} else {
					//column is not changing
				}

				row--;
				++i;

			} else if (line.charAt(i) == 's' && line.charAt(i + 1) == 'w') {
				if (row % 2 == 0) {
					//column is not changing
				} else {
					column--;
				}

				row--;
				++i;

			} else if (line.charAt(i) == 'n' && line.charAt(i + 1) == 'e') {
				if (row % 2 == 0) {
					column++;
				} else {
					//column is not changing
				}

				row++;
				++i;

			} else if (line.charAt(i) == 'n' && line.charAt(i + 1) == 'w') {
				if (row % 2 == 0) {
					//column is not changing
				} else {
					column--;
				}

				row++;
				++i;
			}
		}

		Pair<Integer, Integer> dimension = new Pair<Integer, Integer>(row, column);
		if (map.containsKey(dimension)) {
			char color = map.get(dimension);
			if (color == 'b') map.put(dimension, 'w');
			else map.put(dimension, 'b');

		} else {
			map.put(dimension, 'b');
		}
	}

	private static Map<Pair<Integer, Integer>, Character> flipDay(Map<Pair<Integer, Integer>, Character> map) {
		Map<Pair<Integer, Integer>, Character> newMap = new HashMap<>(map);
		int size = 70;
		for (int i = -size; i < size; i++) {
			for (int j = -size; j < size; j++) {
				
				Pair<Integer, Integer> pos = new Pair<Integer, Integer>(i, j);
				if (shouldFlip(pos, map)) {
					if (!newMap.containsKey(pos)) newMap.put(pos, 'b');
				
					else {
						if (newMap.get(pos) == 'b') newMap.put(pos, 'w');
						else if (newMap.get(pos) == 'w') newMap.put(pos, 'b');
					}
				}
			}
		}

		return newMap;
	}

	private static boolean shouldFlip(Pair<Integer, Integer> pos, Map<Pair<Integer, Integer>, Character> map) {
		int blackNeighbour = 0;
		if (map.get(new Pair<Integer, Integer>(pos.getFirst(), pos.getSecond() + 1)) != null
		 && map.get(new Pair<Integer, Integer>(pos.getFirst(), pos.getSecond() + 1)) == 'b') {
			++blackNeighbour;// east
		}
		
		if (map.get(new Pair<Integer, Integer>(pos.getFirst(), pos.getSecond() - 1)) != null
		 && map.get(new Pair<Integer, Integer>(pos.getFirst(), pos.getSecond() - 1)) == 'b') {
			++blackNeighbour; // west
		}

		if(pos.getFirst() % 2 == 0) {
			if (map.get(new Pair<Integer, Integer>(pos.getFirst() + 1, pos.getSecond())) != null
			 && map.get(new Pair<Integer, Integer>(pos.getFirst() + 1, pos.getSecond())) == 'b') {
				++blackNeighbour;// north west
			}
			
			if (map.get(new Pair<Integer, Integer>(pos.getFirst() + 1, pos.getSecond() + 1)) != null
			 && map.get(new Pair<Integer, Integer>(pos.getFirst() + 1, pos.getSecond() + 1)) == 'b') {
				++blackNeighbour; // north east
			}
			
			if (map.get(new Pair<Integer, Integer>(pos.getFirst() - 1, pos.getSecond())) != null
			 && map.get(new Pair<Integer, Integer>(pos.getFirst() - 1, pos.getSecond())) == 'b') {
				++blackNeighbour;// south west
			}
					
			if (map.get(new Pair<Integer, Integer>(pos.getFirst() - 1, pos.getSecond() + 1)) != null
			 && map.get(new Pair<Integer, Integer>(pos.getFirst() - 1, pos.getSecond() + 1)) == 'b') {
				++blackNeighbour; // south east
			}
		
		} else {
			if (map.get(new Pair<Integer, Integer>(pos.getFirst() + 1, pos.getSecond() - 1)) != null
			 && map.get(new Pair<Integer, Integer>(pos.getFirst() + 1, pos.getSecond() - 1)) == 'b') {
				++blackNeighbour;// north west
			}
			
			if (map.get(new Pair<Integer, Integer>(pos.getFirst() + 1, pos.getSecond())) != null
			 && map.get(new Pair<Integer, Integer>(pos.getFirst() + 1, pos.getSecond())) == 'b') {
				++blackNeighbour; // north east
			}
			
			if (map.get(new Pair<Integer, Integer>(pos.getFirst() - 1, pos.getSecond() - 1)) != null
			 && map.get(new Pair<Integer, Integer>(pos.getFirst() - 1, pos.getSecond() - 1)) == 'b') {
				++blackNeighbour;// south west
			}
					
			if (map.get(new Pair<Integer, Integer>(pos.getFirst() - 1, pos.getSecond())) != null
			 && map.get(new Pair<Integer, Integer>(pos.getFirst() - 1, pos.getSecond())) == 'b') {
				++blackNeighbour; // south east
			}
		}
		
		if(map.containsKey(pos) && map.get(pos) == 'b') {
			if(blackNeighbour == 0 || blackNeighbour > 2) return true;
		
		} else {
			if(blackNeighbour == 2) return true;
		}
		
		return false;
	}
}
