package com.kirisoul.cs.pkmnTB.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.kirisoul.cs.pkmnTB.entities.Pokemon;
import com.kirisoul.cs.pkmnTB.structures.TeamChart;

public class ChartAnalyzer {

  private TeamChart teamC;
  private TypeCalculator tc;
  private String formattedRecTypes;
  private String formattedRecPKMN;
  
  
  public ChartAnalyzer(TeamChart teamC1){
    teamC = teamC1;
    tc = new TypeCalculator();
  }
  
  public List<String> recommendTypes(){

    ReverseDoubleComparator rdc = new ReverseDoubleComparator();
    Map<Double, ArrayList<String>> scores = new TreeMap<Double, ArrayList<String>>(rdc);
    List<String> recommend = new ArrayList<String>();
    
    for(int i: types1()){
      double [] score = scoreType(i);
      
      if(scores.containsKey(score[1])){
        scores.get(score[1]).add(tc.convertTypeNum((int)score[0]));
      }else{
        ArrayList<String> newTypeContainer = new ArrayList<String>();
        newTypeContainer.add(tc.convertTypeNum((int)score[0]));
        scores.put(score[1], newTypeContainer);
      }
    }
    
    StringBuilder sb = new StringBuilder();
    for(double d: scores.keySet()){
      if(d >= 0){  //Do d >= 0 to lower standards, or d > 0 for higher standards
        sb.append(d + ": " + scores.get(d).toString() +"\n");
        for(String s: scores.get(d)){
          if(recommend.size() < 6){
            recommend.add(s); 
          }else{
            break;
          }
        }
      }
    }
    
    formattedRecTypes = sb.toString();
    
    return recommend;
  }
  
  /**
   * Gets types that resist at least 1 of the team's weaknesses.
   * @return
   */
  public List<Integer> types1(){
    ArrayList<Integer> types1 = new ArrayList<Integer>();
    ArrayList<Integer> teamWeak = teamC.getTeamWeak();
    
    for(int i = 0; i < 18; i++){
      for(int w: teamWeak){
        if(tc.getEffectiveNumber(w, i) < 1 && !types1.contains(i)){
          types1.add(i);
          break;
        }
      }
    }
    
    return types1;
  }
  
  /**
   * If:
   *  -type resists a weakness (+1)
   *  -type is super effective on weakness (+.5)
   *  -type creates new weakness (-1)
   * 
   * @param type
   * @return
   */
  public double[] scoreType(int type){
    double [] typeScore = new double[2];
    typeScore[0] = type;
    double score = 0;
    
    ArrayList<Integer> teamWeak = teamC.getTeamWeak();
    
    //Adding
    for(int w: teamWeak){
      if(tc.getEffectiveNumber(w, type) < 1){
        score ++;
      }
      if(tc.getEffectiveNumber(type, w) > 1){
        score += .5;
      }
    }
    
    //Subtracting
    score -= teamC.checkCreateWeak(tc.convertTypeNum(type));
    
    typeScore[1] = score;
    
    return typeScore;
  }

  public List<String> recommendPokemon(List<Pokemon> pkmnList){
    
    List<String> recommend = new ArrayList<String>();
    ReverseDoubleComparator rdc = new ReverseDoubleComparator();
    Map<Double, ArrayList<String>> scores = new TreeMap<Double, ArrayList<String>>(rdc);
    
    for(Pokemon p: pkmnList){
      double score = scorePKMN(p);
      
      if(scores.containsKey(score)){
        scores.get(score).add(p.getName());
      }else{
        ArrayList<String> newPKMNContainer = new ArrayList<String>();
        newPKMNContainer.add(p.getName());
        scores.put(score, newPKMNContainer);
      }
    }
    
    StringBuilder sb = new StringBuilder();
    
    for(double d: scores.keySet()){
      if(d >= 0 && recommend.size() < 10){ //Do d >= 0 to lower standards, or d > 0 for higher standards
        sb.append(d + ": " + scores.get(d).toString() + "\n");
        for(String s: scores.get(d)){
          recommend.add(s);
        }
      }
    }
    
    formattedRecPKMN = sb.toString();
    
    return recommend;
    
  }
  
  /**
   * If:
   *  -PKMN resists a weakness (+1)
   *  -PKMN STABS super effective on weakness (+.5)
   *  -PKMN creates new weakness (-1)
   * 
   * @param type
   * @return
   */
  public double scorePKMN(Pokemon p){
   
    ArrayList<Integer> teamWeak = teamC.getTeamWeak();
    double score = 0;
    
    //Step 1
    for(double[] d : p.getStrong()){
      if(teamWeak.contains((int)d[0])){
        score += 1;
      }
    }
    
    //Step 2 (Has at least 1 STAB that hits that team weakness super effective)
    for(int i: teamWeak){
      if(tc.getEffectiveNumber(tc.convertType(p.type1()), i) > 1){
        score += .5;
      }else if(p.type2() != null){
        if(tc.getEffectiveNumber(tc.convertType(p.type2()), i) > 1){
          score += .5;
        }
      }
    }
      
    //Step 3 (Adds Weaknesses?)
    List<Integer> newWeak = new ArrayList<Integer>();
    for(double[] d: p.getWeak()){
      newWeak.add((int) d[0]);
    }
    score -= teamC.checkCreateWeakList(newWeak);
    
    return score;
  }
  
  public String getRecTypesString(){
    return formattedRecTypes;
  }
  
  public String getRecPKMNString(){
    return formattedRecPKMN;
  }
}
