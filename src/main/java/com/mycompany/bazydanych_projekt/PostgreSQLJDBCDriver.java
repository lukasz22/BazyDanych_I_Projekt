/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bazydanych_projekt;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.*;
import javax.swing.JOptionPane;
/**
 * Klasa jest pośrednikiem między bazą danych a interfejsem użytkownika. 
 * Pozwala połączyc sie z baza danych, rozłączyć się z baza danych, potrafi wykonywac kod SQLowy.
 * @author lukasz
 */


public class PostgreSQLJDBCDriver {
    /**
     * Łączy aplikację z bazą danych 
     * @throws InstantiationException
     * @throws IllegalAccessException 
     */
    public void connect() throws InstantiationException, IllegalAccessException {
        conn = null;
        try {
            Class.forName("org.postgresql.Driver").newInstance();
            System.out.println("postgresql JDBC Driver Registered!");
 
            conn = DriverManager.getConnection(
/*                    "jdbc:postgresql://localhost:5432/BazaPGModeler_2", "postgres",
                    "postgres");
  */            "jdbc:postgresql://pascal:5432/u3bartocha", "u3bartocha",
                    "3bartocha");  
            //Or use this way
           // conn = DriverManager.getConnection("jdbc:postgresql://localhost/localhost:5432?" +
           // "user=asjava&password=123");
            //Do something with the Connection
        } catch (ClassNotFoundException e) {
            //Cannot register postgresql MySQL driver
            System.out.println("This is something you have not add in postgresql library to classpath!");
            e.printStackTrace();
        }catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }finally{
            //After using connection, release the postgresql resource.
                System.out.println("Połączyliśmy się z bazą");
            
        }
    }
    /**
     * Rozłącza aplikację z bazą danych
     */
    public void disconnect(){
        try {
            conn.close();
            System.out.println("Rozłączylismy się z bazą");
        } 
        catch (SQLException e) {
            System.out.println("Nie udalo się rozłączyć");
        }
    }
    public void viewTable()
    throws SQLException {

    Statement stmt = null;
    String query = "select Imię from Osoba";
    try {
        stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            String coffeeName = rs.getString("Imię");
            /*int supplierID = rs.getInt("SUP_ID");
            float price = rs.getFloat("PRICE");
            int sales = rs.getInt("SALES");
            int total = rs.getInt("TOTAL");
            */System.out.println(coffeeName);
        }
    } catch (SQLException e ) {
        System.out.println("Cos poszlo nie tak");
    } finally {
        if (stmt != null) { stmt.close(); }
    }
}
    /**
     * Wstawia dane do tabeli osoby
     * @param imie  wartość dla atrubutu imie
     * @param nazwisko wartośc dla atrybutu nazwisko
     * @param nick wartośc dla atrybutu nick
     */
    public void dodajUzytkownika(String imie, String nazwisko, String nick){
        String nazwaTabeli="osoby";
        if(imie != null && !imie.isEmpty()){
            imie="\'"+imie+"\'";
        }
        else{
            imie=null;
        }
        if(nazwisko != null && !nazwisko.isEmpty()){
            nazwisko="\'"+nazwisko+"\'";
        }
        else{
            nazwisko=null;
        }
         if(nick != null && !nick.isEmpty()){
            nick="\'"+nick+"\'";
        }
        else{
            nick=null;
        }
        try {
  //Connection conn = DriverManager.getConnection(url+baza, login, password);
  Statement st = conn.createStatement();
  try {
   st.executeUpdate("INSERT INTO "+nazwaTabeli+"(imie,haslo,nick) VALUES ("+imie+" ,"+nazwisko+", "+nick+")");
  System.out.println("Rekord został utworzony");
  JOptionPane.showMessageDialog(null,
    "Dodano użytkownika");
  } catch (SQLException e) {
   System.out.println(e.getMessage());
   JOptionPane.showMessageDialog(null, "Nieodpowiedni zestaw danych wejściowych."
           + "Dokonaj korekty.","Błąd", JOptionPane.ERROR_MESSAGE);
   
   
  }
 } catch (SQLException e) {
  System.out.println("Uwaga! Mamy problemy z połączeniem!");
 }
    }
    /**
     * Pobiera wiersze z bazy danych
     * @param tabela nazwa tabeli
     * @param atrybuty nazwy atrybutow tabeli, jakie chcemy dostac
     * @return Object[n][k], gdzie n-liczba wierszy tabeli,k-liczba atrybutów w wierszu zwróconym
     * @throws SQLException 
     */
    public Object[][] getRows(String tabela, String[] atrybuty) throws SQLException{
        Object[][] tabobj=null;
        String atr=new String("");
        int ileatr=0;
        while(ileatr<atrybuty.length){
            if(ileatr!=0) atr=atr+",";
            atr=atr+atrybuty[ileatr];
            ileatr++;
        }
        try {
            Statement st=conn.createStatement();
            ResultSet rs=st.executeQuery("Select "+atr+" from "+tabela);
            ArrayList<String[]> data=new ArrayList<String[]>();
            //Vector<String[2]> data=new Vector<String[2]>();
            //Vector<Object> data2=new Vector<Object>();
            int ileUzytkownikow;
            while(rs.next()){
                //data.add(rs.getString("nick"));
                //String dane[]={rs.getString(oneAtrybut),rs.getString(twoAtrybut)};
                String[] dane=new String[atrybuty.length];
                ileatr=0;
                while(ileatr<atrybuty.length){
                    dane[ileatr]=rs.getString(atrybuty[ileatr]);
                    ileatr++;
                }
                data.add(dane);
            }
            tabobj= data.toArray(new Object[data.size()][atrybuty.length]);
            System.out.println(tabobj.toString());
        } catch (SQLException ex) {
            //Logger.getLogger(PostgreSQLJDBCDriverTest.class.getName()).log(Level.SEVERE, null, ex);
            throw new SQLException(ex);
        }
        return tabobj;
        
    }
    /**
     * Pobiera jedną kolumnę z bazy danych
     * @param tabela nazwa tabeli
     * @param atrybut nazwa atrubutu
     * @param warunek warunek (na przykład "id_osoba=4")
     * @return kolumna atrybutów o nazwie=atrybut
     */
    public Object[] getElementOfTable(String tabela, String atrybut, String warunek){
        Object[] tabobj=null;
        try {
            Statement st=conn.createStatement();
            String askSQL=null;
            if(warunek.isEmpty()){
                askSQL="Select "+atrybut+" from "+tabela;
            } 
            else{
                askSQL="Select "+atrybut+" from "+tabela+" where "+warunek;
            }
            ResultSet rs=st.executeQuery(askSQL);
            ArrayList<String> data=new ArrayList<String>();
            while(rs.next()){
                //data.add(rs.getString("nick"));
                //String dane[]={rs.getString("id_osoba"),rs.getString("nick")};
                data.add(rs.getString(atrybut));
            }
            tabobj= data.toArray(new Object[data.size()]);
            System.out.println(tabobj.toString());
            return tabobj;
        } catch (SQLException ex) {
            Logger.getLogger(PostgreSQLJDBCDriver.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tabobj;
        
    }
    /**
     * Dodaje wiersz do bazy danych
     * @param nazwaTabeli   nazwa tabeli
     * @param atrybuty nazwy atrybotow, do ktorych chcemy dodac dane
     * @param wartosci wartosci, ktore chcemy dodac do bazy danych. wartosci[i] trafia do pola o nazwie atryvutu atrybuty[i]
     * @throws Exception 
     */
    public void dodajDane(String nazwaTabeli,String[] atrybuty,String[] wartosci) throws Exception{
        try {
  //Connection conn = DriverManager.getConnection(url+baza, login, password);
  Statement st = conn.createStatement();
  String atr=new String("");
  int ileatr=0;
  while(ileatr<atrybuty.length){
      if(ileatr!=0) atr=atr+",";
      atr=atr+atrybuty[ileatr];
      ileatr++;
  }
  String val=new String("");
  int ileval=0;
  while(ileval<wartosci.length){
      if(ileval!=0) val=val+",";
      
      if(wartosci[ileval] != null && !wartosci[ileval].isEmpty()){
            val=val+"\'"+wartosci[ileval]+"\'";
        }
        else{
            wartosci[ileval]=null;
        }
      
      ileval++;
  }
  
  try {
   st.executeUpdate("INSERT INTO "+nazwaTabeli+"("+atr+") VALUES ("+val+")");
  //System.out.println("Rekord został utworzony");
  } catch (SQLException e) {
   System.out.println(e.getMessage());
   throw new Exception("Nie dodalismy danych. Zmień dane.");
  }
 } catch (SQLException e) {
  System.out.println("Uwaga! Mamy problemy z połączeniem!");
 }
        throw new Exception("Rekord został dodany do bazy danych");
    }
    
/**
 * Wykonuje zapytanie SQLowe do bazy 
 * Select answer from nazwafunkcji(...) as answer 
 * gdzie ... tu mogą być parametry
 * @param nazwafunkcji  nazwa funkcji
 * @param parametry parametry funkcji,
 * @return to co będzie się kryć jako pierwszy przy atrybucie answer
 * @throws Exception 
 */
    public String dodajDaneZfunkcji(String nazwafunkcji,String[] parametry) throws Exception{
    try {
  //Connection conn = DriverManager.getConnection(url+baza, login, password);
        Statement st = conn.createStatement();
        String val=new String("");
        int ileval=0;
        while(ileval<parametry.length){
            if(ileval!=0) val=val+",";
            
            if(parametry[ileval] != null && !parametry[ileval].isEmpty()){
            val=val+"\'"+parametry[ileval]+"\'";
        }
        else{
            parametry[ileval]=null;
        }
            
            ileval++;
        }
        String str=null;
        try {
            ResultSet rs=st.executeQuery("Select answer from "+nazwafunkcji+"("+val+") as answer");
            rs.next();
            str=rs.getString("answer");
            System.out.println("DODANO REKORD ,ID MAMY?");
            //throw new Exception("Coś poszło nie tak");
            return str;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new Exception("Nie dodano rekordu do bazy danych");
        }
        //throw new Exception("Rekord został dodany");
    } catch (SQLException e) {
        System.out.println("Uwaga! Mamy problemy z połączeniem!");
    }
    throw new Exception("Rekord został dodany do bazy danych");
    
}
    /**
     * Funkcja usuwająca dane z bazy danych
     * @param str nazwa tabeli wraz z warunkiem
     * @throws Exception 
     */
    public void usunRekord(String str) throws Exception{
        try {
  //Connection conn = DriverManager.getConnection(url+baza, login, password);
  Statement st = conn.createStatement();
  
  
  try {
   st.executeUpdate("DELETE FROM "+str);
  //System.out.println("Rekord został utworzony");
  } catch (SQLException e) {
   System.out.println(e.getMessage());
   throw new Exception("Nie usunelismy danych.");
  }
 } catch (SQLException e) {
  System.out.println("Uwaga! Mamy problemy z połączeniem!");
 }
        throw new Exception("Usunięto z bazy danych");
    }
    /**
     * zmienna typu Connection
     */
    private Connection conn;
}
