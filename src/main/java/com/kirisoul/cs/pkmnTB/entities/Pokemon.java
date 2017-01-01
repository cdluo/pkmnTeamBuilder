package com.kirisoul.cs.pkmnTB.entities;

public class Pokemon {

  private String name;
  private String type1;
  private String type2;
  
  public Pokemon(String name1, String t1, String t2){
    name = name1;
    type1 = t1;
    type2 = t2;
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
}
