import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Day14 {
	public static void main(String[] args) throws IOException {
		List<String> lines = Files.readAllLines(Paths.get("input14.txt"));
		Map<String, Long> map = new HashMap<>();

		Map<Long, Long> map2 = new HashMap<>();

		String curr_mask = null;
		for (String line : lines) {
			if (line.contains("mask")) {
				curr_mask = line.substring(line.indexOf("=") + 2);

			} else {
				String mem = line.substring(line.indexOf("[") + 1, line.indexOf("]"));
				long value = Long.parseLong(line.substring(line.indexOf("=") + 2));
				long value_m = applyMask(value, curr_mask);
				map.put(mem, value_m);

				for (long m : applyOnMem(mem, curr_mask))
					map2.put(m, value);
			}
		}
		System.out.println(map.entrySet().stream().map(e -> e.getValue()).mapToLong(Long::longValue).sum());
		System.out.println(map2.entrySet().stream().map(e -> e.getValue()).mapToLong(Long::longValue).sum());
	}

	private static long applyMask(long value, String curr_mask) {
		long changed = 0L;
		for (int i = 0, j = curr_mask.length() - 1; j >= 0; j--, i++) {
			if (curr_mask.charAt(j) == '1') {
				changed += Math.pow(2, i);
			} else if (curr_mask.charAt(j) == '0') {
				continue;
			} else {
				changed += (long) Math.pow(2, i) & value;
			}
		}
		return changed;
	}

	private static List<Long> applyOnMem(String mem, String curr_mask) {
		long m = Long.parseLong(mem);
		List<Long> mem_locations = new ArrayList<>();
		mem_locations.add(0L);

		for (int i = 0, j = curr_mask.length() - 1; j >= 0; j--, i++) {
			if (curr_mask.charAt(j) == '1') {
				mem_locations = updateLocations(mem_locations, i, false);
		
			} else if (curr_mask.charAt(j) == '0') {
				if (((long) Math.pow(2, i) & m) == 0) continue;
				mem_locations = updateLocations(mem_locations, i, false);
			
			} else { 
				mem_locations = updateLocations(mem_locations, i, true);
			}
		}
		return mem_locations;
	}

	private static List<Long> updateLocations(List<Long> mem_locations, int i, boolean addZeros) {
		List<Long> updated = new LinkedList<>();
		long powerOfTwo = (long) Math.pow(2, i);
		for (long m : mem_locations) {
			updated.add(m + powerOfTwo);
		}
		if (addZeros) mem_locations.addAll(updated);
		else mem_locations = updated;
		return mem_locations;
	}
}
