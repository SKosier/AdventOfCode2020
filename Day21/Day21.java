import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Day21 {
	public static void main(String[] args) throws IOException {
		List<String> lines = Files.readAllLines(Paths.get("input21.txt"));
		Map<String, Set<String>> ingredientAllergens = new HashMap<>();
		Map<String, List<Integer>> recipesForAllergen = new HashMap<>();
		List<Set<String>> recipes = new ArrayList<>();
		
		int r = 0;
		for(String line : lines) {
			String[] parts = line.split("\\(contains");
			String[] ingredients = parts[0].split(" ");
			String[] allergens = parts[1].substring(1, parts[1].length()-1).split(", ");
			
			Set<String> recipe = new HashSet<>(Arrays.asList(ingredients));
			recipes.add(recipe);
		
			for(String al : allergens) {
				if(recipesForAllergen.containsKey(al) == false) {
					List<Integer> recipesIndices = new ArrayList<>();
					recipesForAllergen.put(al, recipesIndices);
				} 
				
				recipesForAllergen.get(al).add(r);
			}
			
			for(String ingr : ingredients) {
				if(ingredientAllergens.containsKey(ingr) == false) {
					ingredientAllergens.put(ingr, new HashSet<>());
				}
				ingredientAllergens.get(ingr).addAll(Arrays.asList(allergens));
			}
			r++;
		}
		
		Set<String> neutralIngr = new HashSet<>();
		for(String ingr : ingredientAllergens.keySet()) {
			Set<String> myAllergens = ingredientAllergens.get(ingr);
			Set<String> notAllergenForIngr = new HashSet<>();
			
			for(String allergen : myAllergens) {
				List<Integer> containingRecipes = recipesForAllergen.get(allergen);
				for(int index : containingRecipes) {
					if(recipes.get(index).contains(ingr) == false) {
						notAllergenForIngr.add(allergen);
					}
				}
			}
			myAllergens.removeAll(notAllergenForIngr);
			if(myAllergens.isEmpty()) {
				neutralIngr.add(ingr);
			}
		}
		
		int sum = 0;
		for(Set<String> recipe : recipes) {
			for(String ingr : recipe) {
				if(neutralIngr.contains(ingr)) ++sum;
			}
		}
		System.out.println(sum);
		
		ingredientAllergens.entrySet().removeIf(entry -> entry.getValue().isEmpty());
		
		Map<String, String> matched = new HashMap<>();
		while(!ingredientAllergens.isEmpty()) {
			String allergen = null, ingrid = null;
			
			for(String ingr : ingredientAllergens.keySet()) {
				if(ingredientAllergens.get(ingr).size() == 1) {
					allergen = ingredientAllergens.get(ingr).iterator().next();
					ingrid = ingr;
					matched.put(ingr, allergen);
					break;
				}
			}
			ingredientAllergens.remove(ingrid);
			
			for(String ingr : ingredientAllergens.keySet()) {
				if(ingredientAllergens.get(ingr).contains(allergen)) {
					ingredientAllergens.get(ingr).remove(allergen);
				}
			}
		}
	    matched.entrySet().stream().sorted(Map.Entry.comparingByValue()).forEach(e -> System.out.print(e.getKey()+","));
	}
}