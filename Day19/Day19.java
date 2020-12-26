import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day19 {
	public static void main(String[] args) throws IOException {
		countMatches(false);
		countMatches(true);
	}

	private static void countMatches(boolean isSecond) throws IOException {
		List<String> lines = Files.readAllLines(Paths.get("input19.txt"));
		Map<String, List<List<String>>> rules = new HashMap<>();
		Map<String, String> singles = new HashMap<>();

		int matching = 0;
		for (String line : lines) {
			if (line.isEmpty()) continue;

			if (isSecond && line.startsWith("8:")) {
				line = "8: 42 | 42 8";
			
			} else if (isSecond && line.startsWith("11:")) {
				line = "11: 42 31 | 42 11 31";
			}

			if (line.contains(": ")) {
				String parts[] = line.split(": ");
				if (parts[1].contains("|")) {
					List<List<String>> list = getRules(parts[1], true);
					rules.put(parts[0], list);

				} else if (parts[1].contains("\"")) {
					singles.put(parts[0], parts[1].substring(parts[1].indexOf("\"") + 1, parts[1].length() - 1));

				} else {
					List<List<String>> list = getRules(parts[1], false);
					rules.put(parts[0], list);
				}

			} else {
				if (isMatch(line, 0, line.length(), "0", rules, singles, new HashMap<>())) {
					++matching;
				}
			}
		}
		System.out.println(matching);
	}

	private static boolean isMatch(String line, int pos, int end, String currRule,
			Map<String, List<List<String>>> rules, Map<String, String> singles,
			HashMap<Triple<Integer, Integer, String>, Boolean> states) {
		Triple<Integer, Integer, String> state = new Triple<>(pos, end, currRule);
		if (states.containsKey(state))
			return states.get(state);

		boolean isMatch = false;
		if (singles.containsKey(currRule)) {
			isMatch = singles.get(currRule).equals(line.substring(pos, end));

		} else {
			List<List<String>> ruls = rules.get(currRule);
			for (List<String> rule : ruls) {
				if (isMatchingRules(line, pos, end, rule, rules, singles, states))
					isMatch = true;
			}
		}

		states.put(state, isMatch);
		return isMatch;
	}

	private static boolean isMatchingRules(String line, int pos, int end, List<String> rule,
			Map<String, List<List<String>>> rules, Map<String, String> singles,
			HashMap<Triple<Integer, Integer, String>, Boolean> states) {

		if (pos == end && rule.isEmpty())
			return true;
		if (pos == end)
			return false;
		if (rule.isEmpty())
			return false;

		boolean isMatch = false;
		for (int i = pos + 1; i < end + 1; i++) {
			if (isMatch(line, pos, i, rule.get(0), rules, singles, states)) {
				if (rule.size() == 1) {
					if (isMatchingRules(line, i, end, new ArrayList<>(), rules, singles, states)) {
						isMatch = true;
					}
				} else {
					List<String> newRules = new ArrayList<>(rule);
					newRules.remove(0);
					if (isMatchingRules(line, i, end, newRules, rules, singles, states)) {
						isMatch = true;
					}
				}
			}
		}
		return isMatch;
	}

	private static List<List<String>> getRules(String string, boolean haveTwo) {
		List<List<String>> list = new ArrayList<>();
		if (haveTwo) {
			String[] rules = string.split(" \\| ");
			if (rules[1].contains(" ")) {
				List<String> first = fillList(rules[0]);
				List<String> second = fillList(rules[1]);

				list.add(first);
				list.add(second);

			} else {
				List<String> first = new ArrayList<>();
				first.add(rules[0].trim());

				List<String> second = new ArrayList<>();
				second.add(rules[1].trim());

				list.add(first);
				list.add(second);
			}

		} else {
			List<String> first = new ArrayList<>();
			if (string.contains(" ")) {
				first = fillList(string);
			} else {
				first.add(string.trim());
			}
			list.add(first);
		}
		return list;
	}

	private static List<String> fillList(String string) {
		List<String> list = new ArrayList<>();
		String[] parts = string.split(" ");
		for (String p : parts) {
			p = p.trim();
			list.add(p);
		}
		return list;
	}
}
