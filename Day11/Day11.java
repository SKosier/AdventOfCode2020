import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Day11 {
	public static int length;
	public static int size;

	public static void main(String[] args) throws IOException {
		List<String> lines = Files.readAllLines(Paths.get("input11.txt"));
		length = lines.get(0).length();
		size = lines.size();
		seatingSystem(lines, true);
		seatingSystem(lines, false);
	}

	private static void seatingSystem(List<String> lines, boolean isFirstPart) {
		boolean isChanged;
		List<String> seats;

		do {
			isChanged = false;
			seats = new ArrayList<>();
			for (int i = 0; i < size; i++) {
				String line = lines.get(i);
				StringBuilder sb = new StringBuilder();
				for (int j = 0; j < length; j++) {
					if (line.charAt(j) == '.') {
						sb.append('.');
						continue;
					}
					if (line.charAt(j) == '#') {
						if (shouldLeave(lines, i, j, isFirstPart))
							sb.append('L');
						else
							sb.append('#');
					}
					if (line.charAt(j) == 'L') {
						if (shouldCome(lines, i, j, isFirstPart))
							sb.append('#');
						else
							sb.append('L');
					}
				}
				seats.add(sb.toString());
				if (!sb.toString().equals(line)) isChanged = true;
			}
			lines = seats;
		} while (isChanged);
		System.out.println(countSeats(seats));
	}

	private static int countSeats(List<String> seats) {
		int occ = 0;
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < length; j++) {
				if (seats.get(i).charAt(j) == '#') ++occ;
			}
		}
		return occ;
	}

	private static boolean shouldCome(List<String> lines, int row, int column, boolean isFirstPart) {
		if (!isFirstPart) return shouldCome2(lines, row, column);
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				if (i == 0 && j == 0) continue;
				try {
					if (lines.get(row + i).charAt(column + j) == '#') return false;
				} catch (Exception ex) {}
			}
		}
		return true;
	}

	private static boolean shouldCome2(List<String> lines, int row, int column) {
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				if (i == 0 && j == 0) continue;
				try {
					if (isDirFree(lines, row, column, i, j) == false) return false;
				} catch (Exception ex) {}
			}
		}
		return true;
	}

	private static boolean isDirFree(List<String> lines, int row, int column, int i, int j) {
		int dir_i = i;
		int dir_j = j;
		while (true) {
			try {
				if(lines.get(row + i).charAt(column + j) == '.') {
					i+=dir_i;
					j+=dir_j;
				} else if(lines.get(row + i).charAt(column + j) == 'L') return true;
				else return false;
			} catch (Exception ex) {
				return true;
			}
		}
	}

	private static boolean shouldLeave(List<String> lines, int row, int column, boolean isFirstPart) {
		if (!isFirstPart) return shouldLeave2(lines, row, column);
		int occupied = 0;
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				if (i == 0 && j == 0) continue;
				try {
					if (lines.get(row + i).charAt(column + j) == '#') ++occupied;
				} catch (Exception ex) {}
			}
		}
		if (isFirstPart && occupied >= 4) return true;
		return false;
	}

	private static boolean shouldLeave2(List<String> lines, int row, int column) {
		int dirs_occupied = 0;
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				if (i == 0 && j == 0) continue;
				try {
					if (isDirFree(lines, row, column, i, j) == false) ++dirs_occupied;
				} catch (Exception ex) {}
			}
		}
		if(dirs_occupied >= 5) return true;
		return false;
	}
}
