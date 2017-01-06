package com.kirisoul.cs.pkmnTB;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.kirisoul.cs.pkmnTB.database.PKMNDB;
import com.kirisoul.cs.pkmnTB.entities.Pokemon;
import com.kirisoul.cs.pkmnTB.logic.TypeCalculator;
import com.kirisoul.cs.pkmnTB.structures.TeamChart;
import com.kirisoul.cs.pkmnTB.logic.ChartAnalyzer;
import com.kirisoul.cs.pkmnTB.autoCorrect.AutoCorrecter;

import freemarker.template.Configuration;
import spark.ModelAndView;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Route;
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
  private static AutoCorrecter ac;
  private static PKMNDB db;
  private static TeamChart teamC;
  private static TypeCalculator tc;
  private static ChartAnalyzer ca;
  private static boolean teamLoaded = false;
  
  private static final Gson GSON = new Gson();

  private Main(String[] args) {
    this.args = args;
  }

  /////////////////////////
  // * Main Run Method * //
  /////////////////////////
  
  private void run() throws IOException, ClassNotFoundException, SQLException {
    System.out.println("Ready\n");
    
    teamC = new TeamChart();
    db = new PKMNDB();
    ac = new AutoCorrecter(db);
    tc = new TypeCalculator();
    ca = new ChartAnalyzer(teamC);
    
    //Loading the Team
    
    //Kirisoul's Team
//    Pokemon a = new Pokemon("", "Psychic", "Fairy");
//    Pokemon b = new Pokemon("", "Fire", null);
//    Pokemon c = new Pokemon("", "Water", null);
//    Pokemon d = new Pokemon("", "Dragon", null);
//    Pokemon e = new Pokemon("", "Dark", "Flying");
//    Pokemon f = new Pokemon("", "Grass", "Steel");
    
//    //pkmnTeamBuilder's Team  
//    Pokemon a = new Pokemon("", "Dragon", "Grass");    //Exxegutor
//    Pokemon b = new Pokemon("", "Steel", "Fairy");        //Klefki
//    Pokemon c = new Pokemon("", "Water", "Ground");      //Gastrodon
//    Pokemon d = new Pokemon("", "Water", "Ground");     //Gastrodon
//    Pokemon e = new Pokemon("", "Water", "Flying");      //Gyarados
    
//    teamC.addPokemon(a);
//    teamC.addPokemon(b);
//    teamC.addPokemon(c);
//    teamC.addPokemon(d);
//    teamC.addPokemon(e);
    
//    teamC.addPokemon(f);
    
//    teamC.printChart();
//    
//    System.out.println("-----------Team Weaknesses---");
//    
//    for(int i: teamC.getTeamWeak()){
//      System.out.println("Weak to " + tc.convertTypeNum(i));
//    }
    
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
    
    //Auto correct format must be: capital letter, lower case letters. 
    //Check this in a new args parser.
    
    runSparkServer();
    
  }

  ///////////////////
  // * Front End * //
  ///////////////////
  
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

  private void runSparkServer() {
    Spark.externalStaticFileLocation("src/main/resources/public");
    FreeMarkerEngine freeMarker = createEngine();

    // Setup Spark Routes
    Spark.get("/home", new FrontHandler(), freeMarker);
    Spark.post("/autocorrect", new AutoHandler());
    Spark.post("/findPkmn", new FindPkmnHandler());
    Spark.post("/loadTeam", new LoadTeamHandler());
    Spark.post("/teamChart", new TeamChartHandler());
    Spark.post("/teamWeak", new TeamWeakHandler());
    Spark.post("/recTypes", new RecTypesHandler());
    Spark.post("/recPKMN", new RecPKMNHandler());
  }
  
  //////////////////
  // * Handlers * //
  //////////////////

  /**
   * Handles the homepage.
   * @return a modelandview object representing the homepage.
   * @author cdluo
   */
  private class FrontHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {
      Map<String, Object> variables = ImmutableMap.of();
      return new ModelAndView(variables, "query.ftl");
    }
  }
  
  private static class AutoHandler implements Route {
    @Override
    public Object handle(final Request req, final Response res) {
      QueryParamsMap qm = req.queryMap();
      String input = qm.value("input"); // Got the input here
      List<String> result = ac.generateSuggestions(input);

      Map<String, Object> variables = new ImmutableMap.Builder().put("result",
          result).build();

      return GSON.toJson(variables);
    }
  }
  
  private static class FindPkmnHandler implements Route {
    @Override
    public Object handle(final Request req, final Response res) {
      QueryParamsMap qm = req.queryMap();
      String input = qm.value("input"); // Got the input here
      Pokemon result;
      
      try {
        result = db.getPkmnByName(input);
      } catch (SQLException e) {
        // TODO Auto-generated catch block
        System.out.println("ERROR: Invalid Pokemon");
        e.printStackTrace();
        result = new Pokemon("ERROR", "Invalid", "Pokemon");
      }

      Map<String, Object> variables = new ImmutableMap.Builder().put("result",
          result).build();

      return GSON.toJson(variables);
    }
  }
  
  private static class LoadTeamHandler implements Route {
    @Override
    public Object handle(final Request req, final Response res) {
      teamC.clearChart();
      
      QueryParamsMap qm = req.queryMap();
      
      for(int i=0; i < 6; i++){
        if(qm.value("p" + i + "Name") != "" && qm.value("p" + i + "Name") != null){
          String name = qm.value("p"+ i + "Name");
          System.out.println("Adding " + name);
          String type1 = qm.value("p"+ i + "t1");
          String type2 = null;
          if(qm.value("p"+ i + "t2") != "" && qm.value("p"+ i + "t2") != null){
            type2 = qm.value("p"+ i + "t2");
          }
          Pokemon toAdd = new Pokemon(name, type1, type2);
          teamC.addPokemon(toAdd);
        }
      }
      
      System.out.println(teamC.toString());

      Map<String, Object> variables = new ImmutableMap.Builder().put("result",
          "true").build();
      
      teamLoaded = true;

      return GSON.toJson(variables);
    }
  }
  
  private static class TeamChartHandler implements Route {
    @Override
    public Object handle(final Request req, final Response res) {
      while(!teamLoaded){
        //Wait for the team to load
      }

      Map<String, Object> variables = new ImmutableMap.Builder().put("result",
          teamC.toString()).build();

      return GSON.toJson(variables);
    }
  }
  
  private static class TeamWeakHandler implements Route {
    @Override
    public Object handle(final Request req, final Response res) {
      List<String> weak = new ArrayList<String>();
      
      while(!teamLoaded){
        //Wait for the team to load
      }
      
      for(int i: teamC.getTeamWeak()){
        weak.add(tc.convertTypeNum(i));
      }

      Map<String, Object> variables = new ImmutableMap.Builder().put("result",
          weak).build();

      return GSON.toJson(variables);
    }
  }
  
  private static class RecTypesHandler implements Route {
    @Override
    public Object handle(final Request req, final Response res) {
      while(!teamLoaded){
        //Wait for the team to load
      }

      Map<String, Object> variables = new ImmutableMap.Builder().put("result",
          ca.recommendTypes()).build();

      return GSON.toJson(variables);
    }
  }
  
  private static class RecPKMNHandler implements Route {
    @Override
    public Object handle(final Request req, final Response res) {
      while(!teamLoaded){
        //Wait for the team to load
      }
      
      List<String> recTypes = ca.recommendTypes();
      
      System.out.println("----------Recommended PKMN---");
      List<Pokemon> candidates = new ArrayList<Pokemon>();
      
      for(String s:recTypes){
        try {
          for(Pokemon p: db.getPkmnOfType(s)){
            if(!candidates.contains(p)){
              candidates.add(p);
            }
          }
        } catch (SQLException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
      
      List<String> recPKMN = ca.recommendPokemon(candidates);

      Map<String, Object> variables = new ImmutableMap.Builder().put("result",
          recPKMN).build();

      return GSON.toJson(variables);
    }
  }
}
