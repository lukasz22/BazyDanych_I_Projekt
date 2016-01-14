/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bazydanych_projekt;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author lukasz
 */
public class SkladnikiJednostkaTableModel extends AbstractTableModel implements TableModel {
    SkladnikiJednostkaTableModel() {
        data=null;
        columnnames=null;
        
    }
    SkladnikiJednostkaTableModel(Object[][] dataparam,String[] columnnamesparam) {
        data=dataparam;
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
    
    public void addRow(Object[] obj){
        if(obj.length==this.getColumnCount()){
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
        }
    } 
    public boolean isInColumn(int indexOfColumn, Object val){
        for(int i=0;i< this.getRowCount();i++){
                if(data[i][indexOfColumn].equals(val)){
                    return true;
                }
            }
        return false;
    }
    Object[][] data;
    String[] columnnames;
}
