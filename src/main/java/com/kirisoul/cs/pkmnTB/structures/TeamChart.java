package com.kirisoul.cs.pkmnTB.structures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.kirisoul.cs.pkmnTB.entities.Pokemon;
import com.kirisoul.cs.pkmnTB.logic.TypeCalculator;

public class TeamChart {
  
  private ArrayList<Pokemon> pkmn;
  private HashMap<String, double[]> teamChart;
  private TypeCalculator tc;

  public TeamChart(){
    pkmn = new ArrayList<Pokemon>();
    teamChart = new HashMap<String, double[]>();
    tc = new TypeCalculator();
  }
  
  public void addPokemon(Pokemon p){
    pkmn.add(p);
    
    //Adding Weaknesses to Chart
    for(double[] d: p.getWeak()){
      String type = tc.convertTypeNum((int)d[0]);
      
      //if the chart currently doesn't contain this type
      if(!teamChart.containsKey(type)){
        double[] newRow = {0,0,0,0,0};  //0, .25, .5, 2, 4
        if(d[1] == 2){
          newRow[3]++;
        }else{
          newRow[4]++;  //4x weakness
        }
        teamChart.put(type, newRow);
      }else{
        //Chart contains this type
        if(d[1] == 2){
          teamChart.get(type)[3]++;
        }else{
          teamChart.get(type)[4]++;  //4x weakness
        }
      }
    }
    
  //Adding Resistances to Chart
    for(double[] d: p.getStrong()){
      String type = tc.convertTypeNum((int)d[0]);
      
      //if the chart currently doesn't contain this type
      if(!teamChart.containsKey(type)){
        double[] newRow = {0,0,0,0,0};  //0, .25, .5, 2, 4
        if(d[1] == 0){
          newRow[0]++;
        }else if(d[1] == .25){
          newRow[1]++;
        }else{
          newRow[2]++; 
        }
        teamChart.put(type, newRow);
      }else{
        //Chart contains this type
        if(d[1] == 0){
          teamChart.get(type)[0]++;
        }else if(d[1] == .25){
          teamChart.get(type)[1]++;
        }else{
          teamChart.get(type)[2]++; 
        }
      }
    }
  }
  
  public void removePokemon(Pokemon p){
    if(!pkmn.remove(p)){
     System.out.println("ERROR: Pokemon " + p.getName() + " not in the chart!"); 
    }
    
    //Removing Weaknesses from Chart
    for(double[] d: p.getWeak()){
      String type = tc.convertTypeNum((int)d[0]);
      
      //Chart must contain this type
      if(d[1] == 2){
        teamChart.get(type)[3]--;
      }else{
        teamChart.get(type)[4]--;  //4x weakness
      }
      
      for(int i = 0; i < 5; i++){
        if(teamChart.get(type)[i] != 0){
          break;
        }
        
        //If this happens, the row is empty, so remove it
        if(i == 4){
          teamChart.remove(type);
        }
      }
    }
    
    //Removing Resistances from Chart
    for(double[] d: p.getStrong()){
      String type = tc.convertTypeNum((int)d[0]);
      
      //Chart must contain this type
      if(d[1] == 0){
        teamChart.get(type)[0]--;
      }else if(d[1] == .25){
        teamChart.get(type)[1]--; 
      }else{
        teamChart.get(type)[2]--;
      }
      
      for(int i = 0; i < 5; i++){
        if(teamChart.get(type)[i] != 0){
          break;
        }
        
        //If this happens, the row is empty, so remove it
        if(i == 4){
          teamChart.remove(type);
        }
      }
    }
  }
  
  public ArrayList<Integer> getTeamWeak(){
    
    ArrayList<Integer> weak = new ArrayList<Integer>();
    
    for(String s : teamChart.keySet()){
      double[] row = teamChart.get(s);
      
      if((row[3] + row[4]) > (row[0] + row[1] + row[2])){
        weak.add(tc.convertType(s));
      }
    }
    
    return weak;
  }
  
  /**
   * Checks if adding a monotype pokemon of the given type adds weaknesses 
   * to the overall team.
   * @param type
   * @return
   */
  public int checkCreateWeak(String type){
    
    int newWeak = 0;
    
    for(int s: tc.getWeak(type)){
      double[] row = teamChart.get(tc.convertTypeNum(s));
      
      //This means that adding this new weakness unbalances the chart or
      //stacks on an existing weakness.
      if(row == null){
        //Row doesn't exist in table yet, so all 0. Then adding a weakness unbalances it.
//        System.out.println(type + " would create new weakness in " + tc.convertTypeNum(s));
        newWeak++;
      }
      else if(row[3] + row[4] + 1 > row[0] + row[1] + row[2]){
//        System.out.println(type + " would create new weakness in " + tc.convertTypeNum(s));
        newWeak++;
      }
    }
    
    return newWeak;
  }
  
  public int checkCreateWeakList(List<Integer> types){
    int newWeak = 0;
    
    //types is the new weaknesses to test
    for(int i: types){
      double[] row = teamChart.get(tc.convertTypeNum(i));
      
      if(row == null){
        //Row doesn't exist in table yet, so all 0. Then adding a weakness unbalances it.
//        System.out.println("New Weak to " + i);
        newWeak++;
      }
      else if(row[3] + row[4] + 1 > row[0] + row[1] + row[2]){
//        System.out.println("New Weak to " + i);
        newWeak++;
      }
    }
    
    return newWeak;
  }
  
  public void clearChart(){
    Iterator<Pokemon> iter = pkmn.iterator();
    List<Pokemon> toRemove = new ArrayList<Pokemon>();
    while(iter.hasNext()){
      toRemove.add(iter.next());
    }
    
    for(Pokemon p: toRemove){
      removePokemon(p);
    }
  }
  
  public ArrayList<String[]> toJSONArray(){
    ArrayList<String[]> chart = new ArrayList<String[]>();
    
    for(String s: teamChart.keySet()){
      double[] values = teamChart.get(s);
      String[] toAdd = new String[6];
      toAdd[0] = s;
      toAdd[1] = Integer.toString((int)values[0]);
      toAdd[2] = Integer.toString((int)values[1]);
      toAdd[3] = Integer.toString((int)values[2]);
      toAdd[4] = Integer.toString((int)values[3]);
      toAdd[5] = Integer.toString((int)values[4]);
      
      chart.add(toAdd);
    }
    
    return chart;
  }
  
  public String toString(){
    StringBuilder sb = new StringBuilder();
    sb.append("Type    0    .25   .5    2   4\n");
    sb.append("----Weaknesses/Resistances---\n");
    for(String s : teamChart.keySet()){
      sb.append(s + ": " + teamChart.get(s)[0] + ", " 
          + teamChart.get(s)[1] + ", "
          + teamChart.get(s)[2] + ", "
          + teamChart.get(s)[3] + ", "
          + teamChart.get(s)[4] + "\n");
    }
    
    return sb.toString();
  }
}
