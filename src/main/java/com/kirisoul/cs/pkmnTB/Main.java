package com.kirisoul.cs.pkmnTB;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.kirisoul.cs.pkmnTB.entities.Pokemon;
import com.kirisoul.cs.pkmnTB.logic.TypeCalculator;
import com.kirisoul.cs.pkmnTB.structures.TeamChart;
import com.kirisoul.cs.pkmnTB.logic.ChartAnalyzer;

import freemarker.template.Configuration;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Spark;
import spark.TemplateViewRoute;
import spark.template.freemarker.FreeMarkerEngine;

/**
 * Main method, which executes all the functionality of the program.
 * @author cdluo
 */

public final class Main {

  public static void main(String[] args) throws IOException {
    new Main(args).run();
  }

  private String[] args;

  private Main(String[] args) {
    this.args = args;
  }

  private void run() throws IOException {
    System.out.println("Ready");
    TypeCalculator tc = new TypeCalculator();
    TeamChart teamC = new TeamChart();
    ChartAnalyzer ca = new ChartAnalyzer(teamC);
    
    Pokemon x = new Pokemon("", "Psychic", "Fairy");
    Pokemon y = new Pokemon("", "Fire", null);
    Pokemon z = new Pokemon("", "Water", null);
    Pokemon a = new Pokemon("", "Dragon", null);
    Pokemon b = new Pokemon("", "Dark", "Flying");
    
    teamC.addPokemon(x);
    teamC.addPokemon(y);
    teamC.addPokemon(z);
    teamC.addPokemon(a);
    teamC.addPokemon(b);
    
    teamC.printChart();
    
    System.out.println("-----------------------------");
    
    for(int i: teamC.getTeamWeak()){
      System.out.println("Weak to " + tc.convertTypeNum(i));
    }
    
    System.out.println("-----------------------------");
    
//    teamC.checkCreateWeak("Grass");
    for(int i: ca.types1()){
      System.out.println(tc.convertTypeNum(i) + " resists at least 1 weakness!");
    }
    
    System.out.println("-----------------------------");
    
    System.out.println("Recommend: "+ ca.recommendTypes());
    
    System.out.println("-----------------------------");
    //Now start finding pokemon based on these types. Score each pkmn as well.
    
  }

  ///////////////
  // Front End //
  ///////////////
  
  private static FreeMarkerEngine createEngine() {
    Configuration config = new Configuration();
    File templates = new File("src/main/resources/spark/template/freemarker");
    try {
      config.setDirectoryForTemplateLoading(templates);
    } catch (IOException ioe) {
      System.out.printf("ERROR: Unable use %s for template loading.\n",
          templates);
      System.exit(1);
    }
    return new FreeMarkerEngine(config);
  }

  /**
   * Runs the spark Server with 3 paths: 1 homepage, and 2 results pages.
   */
  private void runSparkServer() {
    Spark.externalStaticFileLocation("src/main/resources/static");
    FreeMarkerEngine freeMarker = createEngine();

    // Setup Spark Routes
    Spark.get("/home", new FrontHandler(), freeMarker);
  }

  /**
   * Handles the homepage.
   * @return a modelandview object representing the homepage.
   * @author cdluo
   */
  private class FrontHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {
      Map<String, Object> variables = ImmutableMap.of("title",
          "Template");
      return new ModelAndView(variables, "query.ftl");
    }
  }
}
