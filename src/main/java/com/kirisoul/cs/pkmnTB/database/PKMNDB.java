package com.kirisoul.cs.pkmnTB.database;

import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.kirisoul.cs.pkmnTB.entities.Pokemon;
import com.kirisoul.cs.pkmnTB.logic.TypeCalculator;

public class PKMNDB {

  private Connection conn;
  private TypeCalculator tc;
  
  public PKMNDB() throws ClassNotFoundException{
    Class.forName("org.sqlite.JDBC");
    
    try {
      conn = DriverManager.getConnection("jdbc:sqlite:Alola.db");
    } catch (SQLException e) {
      System.out.println("ERROR: Connection to PKMNDB failed!");
      e.printStackTrace();
      return;
    }
    
    tc = new TypeCalculator();
  }
  
  public List<Pokemon> getPkmnOfType(String type) throws SQLException{
    List<Pokemon> pkmn = new ArrayList<Pokemon>();
    
    String query = "SELECT * FROM PKMN WHERE type1 = ? OR type2 = ?;";
    
    PreparedStatement prep = conn.prepareStatement(query);
    prep.setString(1, type);
    prep.setString(2, type);
    
    ResultSet rs = prep.executeQuery();
    
    while (rs.next()) {
      Pokemon toAdd = new Pokemon(rs.getString(1), rs.getString(2), rs.getString(3));
      pkmn.add(toAdd);
    }
    
    return pkmn;
  }
}
