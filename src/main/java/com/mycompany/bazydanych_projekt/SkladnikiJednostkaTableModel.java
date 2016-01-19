/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bazydanych_projekt;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

/**
 *  Model danych
 * @author lukasz
 */
public class SkladnikiJednostkaTableModel extends AbstractTableModel implements TableModel {
    /**
     * Konstruktor
     */
    SkladnikiJednostkaTableModel() {
        Object[][]obj ={{}};
            data=obj;
        String[]str ={""};
            columnnames=str;
        
    }
    /**
     * 
     * @param dataparam obiekt z danymi
     * @param columnnamesparam zawiera nazwy kolumn
     */
    SkladnikiJednostkaTableModel(Object[][] dataparam,String[] columnnamesparam) {
        if(dataparam!=null) data=dataparam;
        else{
            Object[][]obj ={{}};
            data=obj;
        }
        columnnames=columnnamesparam;
        
    }

    @Override
    public int getRowCount() {
        return data.length;
    }

    @Override
    public int getColumnCount() {
        return columnnames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data[rowIndex][columnIndex];
    }
    @Override
    public String getColumnName(int column) {
        return columnnames[column];
      }
    @Override
    public Class getColumnClass(int column) {
        return (getValueAt(0, column).getClass());
      }
    /**
     * Dodaje wiersz do obiektu klasy SkladnikiJednostkaTableModel
     * @param obj wiersz, który ma byc dodany
     */
    public void addRow(Object[] obj){
       // if(obj.length==this.getColumnCount()){
            Object[][] dane=new Object[this.getRowCount()+1][this.getColumnCount()];
            for(int i=0;i< this.getRowCount();i++){
                for(int j=0;j<this.getColumnCount();j++){
                    dane[i][j]=this.data[i][j];
                }
            }
            for(int j=0;j<this.getColumnCount();j++){
                dane[this.getRowCount()][j]=obj[j];
            }
            this.data=dane;
       // }
    } 
    /**
     * Usuwa wiersz z obiektu klasy SkladnikiJednostkaTableModel
     * @param index numer wiersza
     */
    public void removeRow(int index){
        
            Object[][] dane=new Object[this.getRowCount()-1][this.getColumnCount()];
            for(int i=0;i< index;i++){
                for(int j=0;j<this.getColumnCount();j++){
                    dane[i][j]=this.data[i][j];
                }
            }
            for(int i=index+1;i< this.getRowCount();i++){
                for(int j=0;j<this.getColumnCount();j++){
                    dane[i][j]=this.data[i][j];
                }
            }
            this.data=dane;
        
    } 
    /**
     * Bada czy wartość val jest podana w columnie o numerze indexy indexOfColumn
     * @param indexOfColumn numer indeksu kolumny
     * @param val wartość
     * @return true, jeśli obiekt o wartości val istnieje; w przeciwnym wypadku zwraca false
     */
    public boolean isInColumn(int indexOfColumn, Object val){
        for(int i=0;i< this.getRowCount();i++){
                if(data[i][indexOfColumn].equals(val)){
                    return true;
                }
            }
        return false;
    }
    /**
     * Zawiera dane
     */
    Object[][] data;
    /**
     * Zawiera nazwy kolumn
     */
    String[] columnnames;
}
