import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Day3 {
	public static void main(String[] args) throws IOException {
		List<String> lines = Files.readAllLines(Paths.get("input3.txt"));
		System.out.println(checkTrees(1, 3, lines));

		Long trees = checkTrees(1, 1, lines) * checkTrees(1, 3, lines) * checkTrees(1, 5, lines)
				* checkTrees(1, 7, lines) * checkTrees(2, 1, lines);
		System.out.println(trees);

	}

	private static long checkTrees(int step_d, int step_r, List<String> lines) {
		int i = 0, j = 0, trees = 0;
		while (i + step_d < lines.size()) {
			i = i + step_d;
			j = (j + step_r) % lines.get(i).length();
			if (lines.get(i).charAt(j) != '.')
				++trees;
		}
		return trees;
	}
}
