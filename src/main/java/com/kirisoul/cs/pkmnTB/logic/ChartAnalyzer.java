package com.kirisoul.cs.pkmnTB.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.kirisoul.cs.pkmnTB.structures.TeamChart;

public class ChartAnalyzer {

  TeamChart teamC;
  TypeCalculator tc;
  
  public ChartAnalyzer(TeamChart teamC1){
    teamC = teamC1;
    tc = new TypeCalculator();
  }
  
  public List<String> recommendTypes(){
    //Double is the score, the list is the types with that score.
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
    
    for(double d: scores.keySet()){
      System.out.println(d + ": " + scores.get(d).toString());
    }
    
    for(double d: scores.keySet()){
      if(d >= 0){
        for(String s: scores.get(d)){
          recommend.add(s);
        }
      }
    }
    
    
    
    
    return recommend;
  }
  
  //Gets types that resist at least 1 of the team's weaknesses.
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
  
  //If:
  //  -type resists a weakness (+1)
  //  -type is super effective on weakness (+.5)
  //  -type creates new weakness (-1)
  //  -type not very effective on a weakness (-.5)
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
      //Subtracting
      if(tc.getEffectiveNumber(type, w) < 1){
        score -= .5;
      }
    }
    
    score -= teamC.checkCreateWeak(tc.convertTypeNum(type));
    
    typeScore[1] = score;
    
    return typeScore;
  }
}
