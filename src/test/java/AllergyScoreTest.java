import java.util.ArrayList;
import java.util.HashMap;
import org.fluentlenium.adapter.FluentTest;
import org.junit.ClassRule;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.*;
import static org.junit.Assert.*;

public class AllergyScoreTest extends FluentTest {
  public WebDriver webDriver = new HtmlUnitDriver();
  public WebDriver getDefaultDriver() {
      return webDriver;
  }

  @ClassRule
  public static ServerRule server = new ServerRule();

  @Test
  public void rootTest() {
      goTo("http://localhost:4567/");
      assertThat(pageSource()).contains("Input your allergy score to find out what you're allergic to!");
  }

  @Test
  public void checkScore3() {
    goTo("http://localhost:4567");
    fill("#score").with("3");
    submit(".btn");
    assertThat(pageSource()).contains("[eggs, peanuts]");
  }

  @Test
  public void checkScore148() {
    goTo("http://localhost:4567");
    fill("#score").with("148");
    submit(".btn");
    assertThat(pageSource()).contains("[cats, tomatoes, shellfish]");
  }
  @Test
  public void allergyConvert_ScoreIsZero_NoAllergies() {
    AllergyScore allergyTypes = new AllergyScore();
    ArrayList<String> userAllergies = new ArrayList<String>();
    userAllergies.add("You have no allergies!");

    assertEquals(userAllergies, allergyTypes.convertAllergyScore(0));
  }

  @Test
  public void allergyConvert_ScoreIsOne_Eggs() {
    AllergyScore allergyTypes = new AllergyScore();
    ArrayList<String> userAllergies = new ArrayList<String>();
    userAllergies.add("eggs");

    assertEquals(userAllergies, allergyTypes.convertAllergyScore(1));
  }

  @Test
  public void allergyConvert_ScoreIs32_Chocolate() {
    AllergyScore allergyTypes = new AllergyScore();
    ArrayList<String> userAllergies = new ArrayList<String>();
    userAllergies.add("chocolate");

    assertEquals(userAllergies, allergyTypes.convertAllergyScore(32));
  }

  @Test
  public void allergyConvert_ScoreIs3_EggsPeanuts() {
    AllergyScore allergyTypes = new AllergyScore();
    ArrayList<String> userAllergies = new ArrayList<String>();
    userAllergies.add("eggs");
    userAllergies.add("peanuts");

    assertEquals(userAllergies, allergyTypes.convertAllergyScore(3));
  }

  @Test
  public void allergyConvert_ScoreIs148_CatsTomatoesShellfish() {
    AllergyScore allergyTypes = new AllergyScore();
    ArrayList<String> userAllergies = new ArrayList<String>();
    userAllergies.add("cats");
    userAllergies.add("tomatoes");
    userAllergies.add("shellfish");

    assertEquals(userAllergies, allergyTypes.convertAllergyScore(148));
  }

}
