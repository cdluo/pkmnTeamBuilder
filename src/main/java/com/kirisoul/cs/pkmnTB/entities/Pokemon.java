package com.kirisoul.cs.pkmnTB.entities;

import java.util.ArrayList;

import com.kirisoul.cs.pkmnTB.logic.TypeCalculator;

public class Pokemon {

  private String name;
  private String type1;
  private String type2;
  private TypeCalculator tc;
  private ArrayList<double[]> weak;
  private ArrayList<double[]> strong;
  
  public Pokemon(String name1, String t1, String t2){
    name = name1;
    type1 = t1;
    type2 = t2;
    tc = new TypeCalculator();
    weak = new ArrayList<double[]>();
    strong = new ArrayList<double[]>();
    calculateEffectiveness();
  }
  
  public String getName(){
    return name;
  }
  
  public String type1(){
    return type1;
  }
  
  public String type2(){
    return type2;
  }
  
  public void calculateEffectiveness(){
    
    //attackers typings on this pkmn
    double[] typeEffective = new double[18];
    
    for(int i = 0; i < 18; i++){
      typeEffective[i] = tc.getEffectiveNumber(i, tc.convertType(type1));
    }
    
    if(type2 != null){
      for(int i = 0; i < 18; i++){
        typeEffective[i] *= tc.getEffectiveNumber(i, tc.convertType(type2));
      }
    }
    
    for(int i = 0; i < 18; i++){
      if(typeEffective[i] != 1){
        if(typeEffective[i] > 1){
          double[] newWeak = {i,typeEffective[i]};
          weak.add(newWeak);
        }else{
          double[] newStrong = {i,typeEffective[i]};
          strong.add(newStrong);
        }
      }
    }
  }
  
  public ArrayList<double[]> getWeak(){
    return weak;
  }
  
  public ArrayList<double[]> getStrong(){
    return strong;
  }
}
