package com.kirisoul.cs.pkmnTB.logic;

import java.util.ArrayList;
import java.util.List;

public class TypeCalculator {

  private static final int NORMAL = 0;
  private static final int FIRE = 1;
  private static final int WATER = 2;
  private static final int ELECTRIC = 3;
  private static final int GRASS = 4;
  private static final int ICE = 5;
  private static final int FIGHTING = 6;
  private static final int POISON = 7;
  private static final int GROUND = 8;
  private static final int FLYING = 9;
  private static final int PSYCHIC = 10;
  private static final int BUG = 11;
  private static final int ROCK = 12;
  private static final int GHOST = 13;
  private static final int DRAGON = 14;
  private static final int DARK = 15;
  private static final int STEEL = 16;
  private static final int FAIRY = 17;
  
  private static final double[][] typeChart = 
    //  N,  F,  W,  E, Gs,  I, Fi,  P, Gd, Fl, Py,  B,  R, Gh, Dr, Dk,  S,  F
    {{  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1, .5,  0,  1,  1, .5,  1},  //Normal
     {  1, .5, .5,  1,  2,  2,  1,  1,  1,  1,  1,  2, .5,  1, .5,  1,  2,  1},  //Fire
     {  1,  2, .5,  1, .5,  1,  1,  1,  2,  1,  1,  1,  2,  1, .5,  1,  1,  1},  //Water
     {  1,  1,  2, .5, .5,  1,  1,  1,  0,  2,  1,  1,  1,  1, .5,  1,  1,  1},  //Electric
     {  1, .5,  2,  1, .5,  1,  1, .5,  2, .5,  1, .5,  2,  1, .5,  1, .5,  1},  //Grass
     {  1, .5, .5,  1,  2, .5,  1,  1,  2,  2,  1,  1,  1,  1,  2,  1, .5,  1},  //Ice
     {  2,  1,  1,  1,  1,  2,  1, .5,  1, .5, .5, .5,  2,  0,  1,  2,  2, .5},  //Fighting
     {  1,  1,  1,  1,  2,  1,  1, .5, .5,  1,  1,  1, .5, .5,  1,  1,  0,  2},  //Poison
     {  1,  2,  1,  2, .5,  1,  1,  2,  1,  0,  1, .5,  2,  1,  1,  1,  2,  1},  //Ground
     {  1,  1,  1, .5,  2,  1,  2,  1,  1,  1,  1,  2, .5,  1,  1,  1, .5,  1},  //Flying
     {  1,  1,  1,  1,  1,  1,  2,  2,  1,  1, .5,  1,  1,  1,  1,  0, .5,  1},  //Psychic
     {  1, .5,  1,  1,  2,  1, .5, .5,  1, .5,  2,  1,  1, .5,  1,  2, .5, .5},  //Bug
     {  1,  2,  1,  1,  1,  2, .5,  1, .5,  2,  1,  2,  1,  1,  1,  1, .5,  1},  //Rock
     {  0,  1,  1,  1,  1,  1,  1,  1,  1,  1,  2,  1,  1,  2,  1, .5,  1,  1},  //Ghost
     {  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  2,  1, .5,  0},  //Dragon
     {  1,  1,  1,  1,  1,  1, .5,  1,  1,  1,  2,  1,  1,  2,  1, .5,  1, .5},  //Dark
     {  1, .5, .5, .5,  1,  2,  1,  1,  1,  1,  1,  1,  2,  1,  1,  1, .5,  2},  //Steel
     {  1, .5,  1,  1,  1,  1,  2, .5,  1,  1,  1,  1,  1,  1,  2,  2, .5,  1},  //Fairy
    };
  
  public TypeCalculator(){
    //Constructor
  }
  
  public int convertType(String type){
    
    int typeInt;
    
    switch(type){
      case "Normal":  typeInt = NORMAL;
      break;
      case "Fire":  typeInt = FIRE;
      break;
      case "Water":  typeInt = WATER;
      break;
      case "Electric":  typeInt = ELECTRIC;
      break;
      case "Grass":  typeInt = GRASS;
      break;
      case "Ice":  typeInt = ICE;
      break;
      case "Fighting":  typeInt = FIGHTING;
      break;
      case "Poison":  typeInt = POISON;
      break;
      case "Ground":  typeInt = GROUND;
      break;
      case "Flying":  typeInt = FLYING;
      break;
      case "Psychic":  typeInt = PSYCHIC;
      break;
      case "Bug":  typeInt = BUG;
      break;
      case "Rock":  typeInt = ROCK;
      break;
      case "Ghost":  typeInt = GHOST;
      break;
      case "Dragon":  typeInt = DRAGON;
      break;
      case "Dark":  typeInt = DARK;
      break;
      case "Steel":  typeInt = STEEL;
      break;
      case "Fairy":  typeInt = FAIRY;
      break;
      default: typeInt = -1;
      break;
    }
    
    return typeInt;
  }
  
  public String convertTypeNum(int type){
    
    String typeString;
    
    switch(type){
      case NORMAL:  typeString = "Normal";
      break;
      case FIRE:  typeString = "Fire";
      break;
      case WATER:  typeString = "Water";
      break;
      case ELECTRIC:  typeString = "Electric";
      break;
      case GRASS:  typeString = "Grass";
      break;
      case ICE:  typeString = "Ice";
      break;
      case FIGHTING:  typeString = "Fighting";
      break;
      case POISON:  typeString = "Poison";
      break;
      case GROUND:  typeString = "Ground";
      break;
      case FLYING:  typeString = "Flying";
      break;
      case PSYCHIC:  typeString = "Psychic";
      break;
      case BUG:  typeString = "Bug";
      break;
      case ROCK:  typeString = "Rock";
      break;
      case GHOST:  typeString = "Ghost";
      break;
      case DRAGON:  typeString = "Dragon";
      break;
      case DARK:  typeString = "Dark";
      break;
      case STEEL:  typeString = "Steel";
      break;
      case FAIRY:  typeString = "Fairy";
      break;
      default: typeString = "ERROR";
      break;
    }
    
    return typeString;
  }
  
  public double getEffectiveString(String attack, String defend){
    int type1 = convertType(attack);
    if(type1 == -1){
      System.out.println("ERROR: Type '" + attack + "' not recognized.");
      return -1;
    }
    
    int type2 = convertType(defend);
    if(type2 == -1){
      System.out.println("ERROR: Type '" + defend + "' not recognized.");
      return -1;
    }
    
    return typeChart[type1][type2];
  }
  
  public double getEffectiveNumber(int attack, int defend){
    int type1 = attack;
    if(type1 > 17 || type1 < 0){
      System.out.println("ERROR: Type1 integer out of bounds");
      return -1;
    }
    
    int type2 = defend;
    if(type1 > 17 || type1 < 0){
      System.out.println("ERROR: Type2 integer out of bounds");
      return -1;
    }
    
    return typeChart[type1][type2];
  }
  
  public List<String> getWeak(String type){
    List<String> weaknesses = new ArrayList<String>();
    int typeNum = convertType(type);
    
    for(int i = 0; i < 18; i++){
      if(typeChart[i][typeNum] > 1){
        weaknesses.add(convertTypeNum(i));
      }
    }
    
    return weaknesses;
  }
}
