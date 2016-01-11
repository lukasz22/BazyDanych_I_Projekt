package com.mycompany.bazydanych_projekt;

import javax.swing.JComboBox;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//http://edu.pjwstk.edu.pl/wyklady/poj/scb/TabSort/TabSort.html

/**
 *
 * @author lukasz
 */
public  class ComboIT {
    private String val;
    private String lab;
    public ComboIT(){
        val=new String();
        lab=new String();
    }
    public ComboIT(String v,String l){
        val=v;
        lab=l;
    }
    public void addItems(JComboBox box,Object[][] arg){
        box.removeAllItems();
        if(arg.length==0)   return;
        for (Object[] arg1 : arg) {
            box.addItem(new ComboIT(arg1[0].toString(), arg1[1].toString()));
            
        }
        box.setSelectedItem(new ComboIT(arg[0][0].toString(), arg[0][1].toString()));
    }
    public String getIndex(){
        return val;
    }
    @Override
    public boolean equals(Object obj) {
    if (obj == null) {
        return false;
    }
    if (getClass() != obj.getClass()) {
        return false;
    }
    final ComboIT other = (ComboIT) obj;
    if ((this.lab == null) ? (other.lab != null) : !this.lab.equals(other.lab)) {
        return false;
    }
    if ((this.val == null) ? (other.val != null) : !this.val.equals(other.val)) {
        return false;
    }
    return true;
    }
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + (this.val != null ? this.val.hashCode() : 0);
        hash = 53 * hash + (this.lab != null ? this.lab.hashCode() : 0);
        return hash;
    }
    @Override
    public String toString(){
        return this.lab.toString();
    }
}
