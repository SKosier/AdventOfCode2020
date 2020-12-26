import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Day20 {
	public static void main(String[] args) throws IOException {
		List<String> lines = Files.readAllLines(Paths.get("input20.txt"));
		Map<Long, Tile> tilesMap = new HashMap<>();
		List<Tile> tiles = new ArrayList<>();
		Tile tile = new Tile();

		for (String line : lines) {
			if (line.isEmpty()) {
				tiles.add(tile);
				tilesMap.put(tile.id, tile);
				tile = new Tile();

			} else if (line.startsWith("Tile")) {
				tile.id = Long.parseLong(line.substring(line.indexOf(" "), line.length() - 1).trim());

			} else {
				tile.cameras.add(line);
			}
		}
		tiles.add(tile);
		tilesMap.put(tile.id, tile);

		long prod = 1L;
		Map<Long, String> corners = new HashMap<>();
		for (Tile t1 : tiles) {
			boolean neighbour_up = false, neighbour_down = false, neighbour_left = false, neighbour_right = false;
			int neighbours = 0;

			for (Tile t2 : tiles) {
				if (t1.id == t2.id)
					continue;
				if (isBorderWith(t1.getTop(), t2)) {
					neighbour_up = true;
					++neighbours;
				}
				if (isBorderWith(t1.getLeft(), t2)) {
					neighbour_left = true;
					++neighbours;
				}
				if (isBorderWith(t1.getRight(), t2)) {
					neighbour_right = true;
					++neighbours;
				}
				if (isBorderWith(t1.getBottom(), t2)) {
					neighbour_down = true;
					++neighbours;
				}
			}

			if (neighbours == 2) {
				prod *= t1.id;
				if (neighbour_up == false && neighbour_left == false)
					corners.put(t1.id, "nw");
				if (neighbour_up == false && neighbour_right == false)
					corners.put(t1.id, "ne");
				if (neighbour_down == false && neighbour_left == false)
					corners.put(t1.id, "sw");
				if (neighbour_down == false && neighbour_right == false)
					corners.put(t1.id, "se");
			}
		}
		System.out.println(prod);

		Tile upLeft = null;
		Long corner = corners.entrySet().iterator().next().getKey();
		if (corners.get(corner).equals("nw")) {
			upLeft = tilesMap.get(corner);

		} else if (corners.get(corner).equals("ne")) {
			upLeft = rotate90(tilesMap.get(corner), 3);

		} else if (corners.get(corner).equals("se")) {
			upLeft = rotate90(tilesMap.get(corner), 2);
		} else {
			upLeft = rotate90(tilesMap.get(corner), 1);
		}

		List<String> image = createImage(upLeft, tiles);
		int monsters = findMonsters(image);
		long water = image.stream().flatMap(e -> e.chars().boxed()).filter(e -> e == '#').count();
		long onlyWater = water - monsters * 15;
		System.out.println(onlyWater);
	}

	public static int findMonsters(List<String> imageOriginal) {
		String[] monster = {
		"                  #",
		"#    ##    ##    ###",
		" #  #  #  #  #  #   "};
		List<Pair<Integer, Integer>> monsterCoordinates = new ArrayList<>();
		for(int i = 0; i < monster.length; i++) {
			for(int j = 0; j < monster[i].length(); j++) {
				if(monster[i].charAt(j) == '#') monsterCoordinates.add(new Pair<>(i, j));
			}
		}
		
		int monsters = 0;
		int imageSize = imageOriginal.size();
		int monsterH = monster.length;
		int monsterW = monster[0].length();
		
		List<List<String>> images = getAllImages(imageOriginal);
		
		for(List<String> image : images) {
			for(int i = 0; i < imageSize - monsterH; i++) {
				for(int j = 0; j < imageSize - monsterW; j++) {
					boolean isMonster = true;
					for(Pair<Integer, Integer> p : monsterCoordinates) {
						if(image.get(i + p.getFirst()).charAt(j + p.getSecond()) == '#') continue;
						else isMonster = false;
					}
					if(isMonster) ++monsters;
				}
			}
		}
		return monsters;
	}
	
	private static List<List<String>> getAllImages(List<String> imageOriginal) {
		List<List<String>> images = new ArrayList<>();
		Tile t = new Tile();
		t.cameras = imageOriginal;
		
		for(int i = 0; i < 4; i++) {
			Tile newTile = rotate90(t, i);
			images.add(newTile.cameras);
			t = newTile;
		}
		
		t = flip(t);
		
		for(int i = 0; i < 4; i++) {
			Tile newTile = rotate90(t, i);
			images.add(newTile.cameras);
			t = newTile;
		}
		
		return images;
	}

	private static List<String> createImage(Tile upLeft, List<Tile> tiles) {
		int size = (int) Math.sqrt(tiles.size());
		int rowSize = upLeft.getSize();
		
		List<List<Tile>> tileImage = new ArrayList<>();
		Set<Long> usedTiles = new HashSet<>();
		for (int i = 0; i < size; i++) {
			List<Tile> row = matchRow(upLeft, size, tiles, usedTiles);
			tileImage.add(row);
			upLeft = findMatchingTile(row.get(0), row.get(0).cameras.get(rowSize - 1), 'n' ,tiles, usedTiles);
			
			for(Tile t : row) usedTiles.add(t.id);
		}

		List<String> image = removeBorders(tileImage);
		return image;
	}

	private static List<Tile> matchRow(Tile left, int size, List<Tile> tiles, Set<Long> usedTiles) {
		List<Tile> row = new ArrayList<>();
		row.add(left);
		for(int i = 1; i < size; i++) {
			left = findMatchingTile(left, left.getRight(), 'w', tiles, usedTiles);
			row.add(left);
		}
		return row;
	}

	private static Tile findMatchingTile(Tile neighbour, String border, char borderLooking, List<Tile> tiles, Set<Long> usedTiles) {
		for(Tile t : tiles) {
			if(usedTiles.contains(t.id) || t.id == neighbour.id) continue;
			if(isBorderWith(border, t)) {
				fixOrientation(t, border, borderLooking);
				return t;
			}
		}
		return null;
	}
	
	private static List<String> removeBorders(List<List<Tile>> tileImage) {
		List<String> image = new ArrayList<>();
		for (List<Tile> row : tileImage) {
			for (int i = 1; i <= 8; i++) {
				StringBuilder sb = new StringBuilder();
		
				for (Tile t : row) {
					sb.append(t.cameras.get(i).substring(1, 9));
				}
				
				String line = sb.toString();
				image.add(line);
			}
		}
		return image;
	}

	private static void fixOrientation(Tile t, String border, char borderLooking) {
		StringBuilder sb = new StringBuilder();
		String reversed = sb.append(border).reverse().toString();
		if(borderLooking == 'n') {
			if(border.equals(t.getTop())) {
				return;
			} else if(reversed.equals(t.getTop())) {
				flip(t);
			
			} else if(border.equals(t.getBottom())) {
				flip(rotate90(t, 2));
			} else if(reversed.equals(t.getBottom())) {
				rotate90(t, 2);
			
			} else if(border.equals(t.getRight())) {
				rotate90(t, 3);
			} else if(reversed.equals(t.getRight())) {
				flip(rotate90(t, 3));
			
			} else if(border.equals(t.getLeft())) {
				flip(rotate90(t, 1));
			} else if(reversed.equals(t.getLeft())) {
				rotate90(t, 1);
			}
		
		} else if(borderLooking == 'w') {
			if(border.equals(t.getLeft())) {
				return;
			} else if(reversed.equals(t.getLeft())) {
				rotate90(flip(t), 2);
			
			} else if(border.equals(t.getRight())) {
				flip(t);
			} else if(reversed.equals(t.getRight())) {
				rotate90(t, 2);
			
			} else if(border.equals(t.getTop())) {
				rotate90(flip(t), 3);
			} else if(reversed.equals(t.getTop())) {
				rotate90(t, 3);
			
			} else if(border.equals(t.getBottom())) {
				rotate90(t, 1);
			} else if(reversed.equals(t.getBottom())) {
				rotate90(flip(t), 1);
			}
		}
	}

	private static Tile rotate90(Tile tile, int times) {
		times = times % 4;
		int rowSize = tile.cameras.get(0).length();

		while (times != 0) {
			List<String> cameras = new ArrayList<>();
			
			for (int i = 0; i < rowSize; i++) {
				StringBuilder sb = new StringBuilder();
				for (int j = tile.cameras.size() - 1; j >= 0; j--) {
					sb.append(tile.cameras.get(j).charAt(i));
				}
				cameras.add(sb.toString());
			}
			
			--times;
			tile.cameras = cameras;
		}
		return tile;
	}
	
	private static Tile flip(Tile t) {
		List<String> cameras = new ArrayList<>();
		for(String s : t.cameras) {
			StringBuilder sb = new StringBuilder();
			cameras.add(sb.append(s).reverse().toString());
		}
		t.cameras = cameras;
		return t;
	}

	private static boolean isBorderWith(String border, Tile t2) {
		StringBuilder sb = new StringBuilder();
		String reversed = sb.append(border).reverse().toString();
		if (border.equals(t2.getTop()) || border.equals(t2.getBottom())
				|| border.equals(t2.getRight()) || border.equals(t2.getLeft()))
			return true;
		if (reversed.equals(t2.getTop()) || reversed.equals(t2.getBottom()) || reversed.equals(t2.getRight())
				|| reversed.equals(t2.getLeft()))
			return true;
		return false;
	}
}

class Tile {
	long id;
	List<String> cameras = new ArrayList<>();

	public int getSize() {
		return cameras.size();
	}
	
	public String getLeft() {
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < getSize(); i++) {
			sb.append(cameras.get(i).charAt(0));
		}
		return sb.toString();
	}
	
	public String getRight() {
		int size = getSize();
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < size; i++) {
			sb.append(cameras.get(i).charAt(size-1));
		}
		return sb.toString();
	}
	
	public String getTop() {
		return cameras.get(0);
	}
	
	public String getBottom() {
		return cameras.get(getSize()-1);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (String s : cameras)
			sb.append(s).append("\n");
		return sb.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cameras == null) ? 0 : cameras.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tile other = (Tile) obj;
		if (cameras == null) {
			if (other.cameras != null)
				return false;
		} else if (!cameras.equals(other.cameras))
			return false;
		if (id != other.id)
			return false;
		return true;
	}
}
