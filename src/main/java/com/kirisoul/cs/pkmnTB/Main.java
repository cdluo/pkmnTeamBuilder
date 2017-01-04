package com.kirisoul.cs.pkmnTB;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.kirisoul.cs.pkmnTB.database.PKMNDB;
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

  public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException {
    new Main(args).run();
  }

  private String[] args;

  private Main(String[] args) {
    this.args = args;
  }

  private void run() throws IOException, ClassNotFoundException, SQLException {
    System.out.println("Ready\n");
    TypeCalculator tc = new TypeCalculator();
    TeamChart teamC = new TeamChart();
    ChartAnalyzer ca = new ChartAnalyzer(teamC);
    PKMNDB db = new PKMNDB();
    
    //Kirisoul's Team
    
    Pokemon a = new Pokemon("", "Psychic", "Fairy");
    Pokemon b = new Pokemon("", "Fire", null);
    Pokemon c = new Pokemon("", "Water", null);
    Pokemon d = new Pokemon("", "Dragon", null);
    Pokemon e = new Pokemon("", "Dark", "Flying");
    
//    Pokemon f = new Pokemon("", "Grass", "Steel");
    
//    //pkmnTeamBuilder's Team
//    
//    Pokemon a = new Pokemon("", "Psychic", "Fairy");    //Lele
//    Pokemon b = new Pokemon("", "Normal", null);        //P2
//    Pokemon c = new Pokemon("", "Poison", "Dark");      //Muk
//    Pokemon d = new Pokemon("", "Water", "Ground");     //Gastrodon
//    Pokemon e = new Pokemon("", "Water", "Flying");      //Gyarados
    
    //Perfectly Balanced Type Chart!
    
    teamC.addPokemon(a);
    teamC.addPokemon(b);
    teamC.addPokemon(c);
    teamC.addPokemon(d);
    teamC.addPokemon(e);
    
//    teamC.addPokemon(f);
    
    teamC.printChart();
    
    System.out.println("-----------Team Weaknesses---");
    
    for(int i: teamC.getTeamWeak()){
      System.out.println("Weak to " + tc.convertTypeNum(i));
    }
    
    System.out.println("---------Recommended Types---");
    
    List<String> recTypes = ca.recommendTypes();
    
    System.out.println("----------Recommended PKMN---");
    List<Pokemon> candidates = new ArrayList<Pokemon>();
    
    for(String s:recTypes){
      for(Pokemon p: db.getPkmnOfType(s)){
        if(!candidates.contains(p)){
          candidates.add(p);
        }
      }
    }
    
    List<String> recPKMN = ca.recommendPokemon(candidates);
    //Top recommendations printed.
    
//    System.out.println(db.getPkmnByName("Kartana").getWeak().toString());
    
    runSparkServer();
    
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
