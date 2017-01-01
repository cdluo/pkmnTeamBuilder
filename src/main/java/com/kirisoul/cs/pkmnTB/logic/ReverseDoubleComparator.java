package com.kirisoul.cs.pkmnTB.logic;

import java.util.Comparator;

public class ReverseDoubleComparator implements Comparator<Double>{

  @Override
  public int compare(Double o1, Double o2) {
    if(o1 < o2){
      return 1;
    }if(o1.equals(o2)){
      return 0;
    }else{
      return -1;
    }
  }
}
