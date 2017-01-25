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

public class OldPKMNDB {

  private Connection conn;
  private TypeCalculator tc;
  
  public OldPKMNDB() throws ClassNotFoundException, URISyntaxException{
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
  
  /**
   * Gets the connection to the heroku-PostGreSQL DB.
   * 
   * @return a DB Connection
   * @throws URISyntaxException exception
   * @throws SQLException exception
   */
  private static Connection getConnection() throws URISyntaxException, SQLException {
    String dbUrl = "postgres://awwcplfmatfrdy:a8437de3ec3bb7c6a7df9a10f28c544bd92e2e521a8f"
        + "337b2ce78914eb624316@ec2-54-235-168-152.compute-1.amazonaws.com:5432/d60j980okbo60a";

    return DriverManager.getConnection(dbUrl);
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
  
  public Pokemon getPkmnByName(String name) throws SQLException{
    String query = "SELECT * FROM PKMN WHERE name = ?;";
    
    PreparedStatement prep = conn.prepareStatement(query);
    prep.setString(1, name);
    
    ResultSet rs = prep.executeQuery();
    
    rs.next();
    Pokemon toReturn = new Pokemon(rs.getString(1), rs.getString(2), rs.getString(3));
    
    return toReturn;
  }
  
  public List<String> getAllPkmnNames() throws SQLException{
    List<String> names = new ArrayList<String>();
    
    String query = "SELECT Name FROM PKMN;";
    
    PreparedStatement prep = conn.prepareStatement(query);

    ResultSet rs = prep.executeQuery();
    
    while (rs.next()) {
      String toAdd = rs.getString(1);
      names.add(toAdd);
    }
    
    return names;
  }
  
  public List<Pokemon> getAllPkmn() throws SQLException{
    List<Pokemon> names = new ArrayList<Pokemon>();
    
    String query = "SELECT * FROM PKMN;";
    
    PreparedStatement prep = conn.prepareStatement(query);

    ResultSet rs = prep.executeQuery();
    
    while (rs.next()) {
      String name = rs.getString(1);
      String t1 = rs.getString(2);
      String t2 = rs.getString(3);
      
      Pokemon p = new Pokemon(name, t1, t2);
      
      names.add(p);
    }
    
    return names;
  }
  
  public void insertPokemon(Pokemon p) throws SQLException{
    String query = "INSERT INTO PKMN VALUES(? ? ?);";
    PreparedStatement ps = conn.prepareStatement(query);
    
    ps.setString(1, p.getName());
    ps.setString(2, p.type1());
    ps.setString(3, p.type2());
    
    ps.addBatch();
    ps.executeBatch();
    ps.close();
  }
}
