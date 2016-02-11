import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;



public class AllergyScore {
  public static void main( String[] args ) {
    // front-end
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/home.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/results", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/results.vtl");

      Integer score = Integer.parseInt(request.queryParams("score"));
      ArrayList<String> result = convertAllergyScore(score);

      model.put("result", result);
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
  }
  // Back-end

  public static ArrayList<String> convertAllergyScore(int score) {
    ArrayList<String> userAllergies = new ArrayList<String>();
    String[] typesOfAllergens = new String[]
    {"cats", "pollen", "chocolate", "tomatoes", "strawberries", "shellfish", "peanuts"};
    int[] allergenScores = new int[] {128,64,32,16,8,4,2};

    if (score == 0) {
      userAllergies.add("You have no allergies!");
    }
    if (score % 2 == 1) {
      userAllergies.add("eggs");
      score -= 1;
    }

    int index = 0;
    while (index < allergenScores.length) {
      if (score >= allergenScores[index]) {
        userAllergies.add(typesOfAllergens[index]);
        score -= allergenScores[index];
      }
      index++;
    }
    return userAllergies;
  }
}




// HashMap<Integer,String> allergyDictionary = new HashMap<Integer,String>();
// allergyDictionary.put(2,"peanuts");
// allergyDictionary.put(4,"shellfish");
// allergyDictionary.put(8,"strawberries");
// allergyDictionary.put(16,"tomatoes");
// allergyDictionary.put(32,"chocolate");
// allergyDictionary.put(64,"pollen");
// allergyDictionary.put(128,"cats");

// String allergen = allergyDictionary.get(score);
// if (allergen != null) {
//   userAllergies.add(allergen);
//   return userAllergies;
// }
