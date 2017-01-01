package com.kirisoul.cs.pkmnTB;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.kirisoul.cs.pkmnTB.entities.Pokemon;
import com.kirisoul.cs.pkmnTB.logic.TypeCalculator;
import com.kirisoul.cs.pkmnTB.structures.TeamChart;

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
    System.out.println("Ready\n");
    TypeCalculator tc = new TypeCalculator();
    TeamChart teamC = new TeamChart();
    
    Pokemon x = new Pokemon("", "Dragon", "Dark");
    Pokemon y = new Pokemon("", "Steel", "Ghost");
    
    teamC.addPokemon(x);
    teamC.addPokemon(y);
    teamC.removePokemon(y);
    teamC.printChart();
    
//    System.out.println("X weaknesses:");
//    for(double[]d : x.getWeak()){
//      System.out.println(tc.convertTypeNum((int)d[0]) + ": " + d[1]);
//    }
//    
//    System.out.println("");
//    
//    System.out.println("X resistances:");
//    for(double[]d : x.getStrong()){
//      System.out.println(tc.convertTypeNum((int)d[0]) + ": " + d[1]);
//    }
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
