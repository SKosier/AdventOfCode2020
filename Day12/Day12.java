import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day12 {
	public static void main(String[] args) throws IOException {
		List<String> lines = Files.readAllLines(Paths.get("input12.txt"));
		Map<Character, Integer> compas = new HashMap<>();
		String directionR = "NESW";
		String directionL = "NWSE";
		compas.put('N', 0);
		compas.put('E', 0);
		compas.put('S', 0);
		compas.put('W', 0);
		char facing = 'E';

		for (String line : lines) {
			int no = Integer.parseInt(line.substring(1));
			char d = line.charAt(0);
			if (d == 'F') {
				compas.put(facing, compas.get(facing) + no);

			} else if (d == 'R') {
				int index = (directionR.indexOf(facing) + no / 90) % 4;
				facing = directionR.charAt(index);
			
			} else if (d == 'L') {
				int index = (directionL.indexOf(facing) + no / 90) % 4;
				facing = directionL.charAt(index);
			
			} else {
				compas.put(d, compas.get(d) + no);
			}
		}
		int sum1 = Math.abs(compas.get('N') - compas.get('S'));
		int sum2 = Math.abs(compas.get('E') - compas.get('W'));
		System.out.println(sum1 + sum2);

		secondPart(lines);
	}

	private static void secondPart(List<String> lines) {
		Map<Character, Integer> compas = new HashMap<>();
		int[] viewpoint = { 1, 10 };
		int[] ship = { 0, 0 }; // N-S, E-W

		for (String line : lines) {
			int no = Integer.parseInt(line.substring(1));
			char d = line.charAt(0);
			if (d == 'F') {
				ship[0] = ship[0] + no * viewpoint[0];
				ship[1] = ship[1] + no * viewpoint[1];

			} else if (d == 'R') {
				int index = no / 90 % 4;
				if (index == 0) {
				} else if (index == 2) {
					viewpoint[0] *= -1;
					viewpoint[1] *= -1;
				} else if (index == 1) {
					int help = viewpoint[0];
					viewpoint[0] = viewpoint[1] * -1;
					viewpoint[1] = help;
				} else if (index == 3) {
					int help = viewpoint[0];
					viewpoint[0] = viewpoint[1];
					viewpoint[1] = help * -1;
				}

			} else if (d == 'L') {
				int index = no / 90 % 4;
				if (index == 0) {
				} else if (index == 2) {
					viewpoint[0] *= -1;
					viewpoint[1] *= -1;
				} else if (index == 3) {
					int help = viewpoint[0];
					viewpoint[0] = viewpoint[1] * -1;
					viewpoint[1] = help;
				} else if (index == 1) {
					int help = viewpoint[0];
					viewpoint[0] = viewpoint[1];
					viewpoint[1] = help * -1;
				}
	
			} else {
				if (d == 'N' || d == 'S') {
					if (d == 'S') viewpoint[0] -= no;
					else viewpoint[0] += no;
				} else // E-W
				if (d == 'W') viewpoint[1] -= no;
				else viewpoint[1] += no;
			}
		}
		System.out.println(Math.abs(ship[0]) + Math.abs(ship[1]));
	}
}
