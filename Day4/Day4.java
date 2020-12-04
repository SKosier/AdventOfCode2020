import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day4 {
	public static void main(String[] args) throws IOException {
		List<String> list = Files.readAllLines(Paths.get("input4.txt"));
		List<Map<String, String>> passports = new ArrayList<>();
		Map<String, String> current = new HashMap<>();

		for (String line : list) {
			if (line.length() == 0) {
				if (checkFields(current)) {
					passports.add(current);
				}
				current = new HashMap<>();
				continue;
			}

			String[] fields = line.split(" ");
			for (String field : fields) {
				String[] pair = field.split(":");
				current.put(pair[0], pair[1]);
			}
		}

		if (checkFields(current)) passports.add(current);
		System.out.println(passports.size());
		
		int validPass = 0;
		Set<String> colors = Stream.of("amb", "blu", "brn", "gry", "grn", "hzl", "oth").collect(Collectors.toCollection(HashSet::new));
		
		for (Map<String, String> pass : passports) {
			int valid = 0;
			for (Map.Entry<String, String> entry : pass.entrySet()) {
				if (entry.getKey().equals("byr")) {
					int year = Integer.parseInt(entry.getValue());
					if (year >= 1920 && year <= 2002) ++valid;
				}

				if (entry.getKey().equals("iyr")) {
					int year = Integer.parseInt(entry.getValue());
					if (year >= 2010 && year <= 2020) ++valid;
				}

				if (entry.getKey().equals("eyr")) {
					int year = Integer.parseInt(entry.getValue());
					if (year >= 2020 && year <= 2030) ++valid;
				}

				if (entry.getKey().equals("hgt")) {
					if (entry.getValue().endsWith("cm")) {
						int hgt = Integer.parseInt(entry.getValue().substring(0, entry.getValue().indexOf("cm")));
						if (hgt >= 150 && hgt <= 193) ++valid;
					}
					if (entry.getValue().endsWith("in")) {
						int hgt = Integer.parseInt(entry.getValue().substring(0, entry.getValue().indexOf("in")));
						if (hgt >= 59 && hgt <= 76)	++valid;
					}
				}
			
				if (entry.getKey().equals("hcl")) {
					if (Pattern.matches("#[0-9a-z]+", entry.getValue()) == true && entry.getValue().length() == 7) ++valid;
				}
				
				if (entry.getKey().equals("ecl")) {
					if (colors.contains(entry.getValue())) ++valid;
				}
				
				if (entry.getKey().equals("pid")) {
					if (Pattern.matches("[0-9]+", entry.getValue()) == true && entry.getValue().length() == 9) ++valid;
				}
			}
			if(valid == 7) ++validPass;
		}
		System.out.println(validPass);
	}

	private static boolean checkFields(Map<String, String> current) {
		boolean flag = current.containsKey("byr") && current.containsKey("iyr") && current.containsKey("eyr")
				&& current.containsKey("hgt") && current.containsKey("hcl") && current.containsKey("ecl")
				&& current.containsKey("pid");
		return (flag && current.size() == 7) || (flag && current.size() == 8 && current.containsKey("cid"));
	}
}
