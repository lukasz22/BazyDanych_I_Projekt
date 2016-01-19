/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bazydanych_projekt;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author lukasz
 */
//http://www.asjava.com/jdbc/using-jdbc-with-postgresql-tutorial-and-example/
public class NewApplication extends javax.swing.JFrame {
    private Object ComboIT;

    /**
     * Creates new form NewApplication
     */
    public NewApplication() {
        initComponents();
        id_user=null;
        rekwizytyOsoby.setEnabled(false);
        yourproducts.setEnabled(false);
        this.setTitle("Książka kucharska - Profil ogólny");
        psql = new PostgreSQLJDBCDriver();
        try {
            psql.connect();
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(NewApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
        String dane[]={"id_produkt","jednostka_miary","nazwa_jedn","nazwa_prod","liczba_kcal_w_1_jednostce","sugerowana_cena_za_1_jednostke"};
        TableModel model = null;
        try {
            model = new SkladnikiJednostkaTableModel(psql.getRows("pelny_produkt_jednostka", dane),dane);
        } catch (SQLException ex) {
            Logger.getLogger(NewApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
        //jTable1=null;
        //jTable1=new JTable(model);
        jTable1.setModel(model);
       // jTable1=new JTable(psql.getRows("rekwizyt", dane),dane);
        jTable1.setSelectionModel(new ClassListSelectionModel());
        jTable1.getColumnModel().getColumn(0).setMinWidth(0);
        jTable1.getColumnModel().getColumn(0).setMaxWidth(0);
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(0);
        jTable1.getColumnModel().getColumn(1).setMinWidth(0);
        jTable1.getColumnModel().getColumn(1).setMaxWidth(0);
        jTable1.getColumnModel().getColumn(1).setPreferredWidth(0);
        jTable1.repaint();
        
        String dane2[]={"id_przepis","id_produkt","jednostka_miary","Nazwa","Ilość","Jednostka","liczba_kcal_w_1_jednostce","sugerowana_cena_za_1_jednostke"};
        TableModel model2 = null;
        Object[][] obj=new Object[0][8];
        model2 = new SkladnikiJednostkaTableModel(obj,dane2);
        this.dodajPrzepisdodajSkladnikiTable.setModel(model2);
        this.dodajPrzepisdodajSkladnikiTable.setSelectionModel(new ClassListSelectionModel());
        this.dodajPrzepisdodajSkladnikiTable.repaint();
        dodajPrzepisdodajSkladnikiTable.getColumnModel().getColumn(0).setMinWidth(0);
        dodajPrzepisdodajSkladnikiTable.getColumnModel().getColumn(0).setMaxWidth(0);
        dodajPrzepisdodajSkladnikiTable.getColumnModel().getColumn(0).setPreferredWidth(0);
        dodajPrzepisdodajSkladnikiTable.getColumnModel().getColumn(1).setMinWidth(0);
        dodajPrzepisdodajSkladnikiTable.getColumnModel().getColumn(1).setMaxWidth(0);
        dodajPrzepisdodajSkladnikiTable.getColumnModel().getColumn(1).setPreferredWidth(0);
        dodajPrzepisdodajSkladnikiTable.getColumnModel().getColumn(2).setMinWidth(0);
        dodajPrzepisdodajSkladnikiTable.getColumnModel().getColumn(2).setMaxWidth(0);
        dodajPrzepisdodajSkladnikiTable.getColumnModel().getColumn(2).setPreferredWidth(0);
        
        String dane3[]={"id_rekwizyt","Nazwa","Ilosc"};
        TableModel model3 = null;
        Object[][] obj3=new Object[0][3];
        model3 = new SkladnikiJednostkaTableModel(obj3,dane3);
        this.dodajPrzepisDodajRekwizytTable.setModel(model3);
        this.dodajPrzepisDodajRekwizytTable.setSelectionModel(new ClassListSelectionModel());
        this.dodajPrzepisDodajRekwizytTable.repaint();
        SkladnikiJednostkaTableModel mod= (SkladnikiJednostkaTableModel)this.dodajPrzepisDodajRekwizytTable.getModel();
       // Object[] dan={"111","Krzeslo","5"};
       // mod.addRow(dan);
        mod.fireTableDataChanged();
        this.dodajPrzepisDodajRekwizytTable.setModel(mod);
        this.dodajPrzepisDodajRekwizytTable.repaint();
        dodajPrzepisDodajRekwizytTable.getColumnModel().getColumn(0).setMinWidth(0);
        dodajPrzepisDodajRekwizytTable.getColumnModel().getColumn(0).setMaxWidth(0);
        dodajPrzepisDodajRekwizytTable.getColumnModel().getColumn(0).setPreferredWidth(0);
        
        String dane4[]={"id_przepis","Nazwa","Autor"};
        TableModel model4 = null;
        Object[][] obj4=new Object[0][3];
        model4 = new SkladnikiJednostkaTableModel(obj4,dane4);
        this.wyswietlPrzepisyTable.setModel(model4);
        wyswietlPrzepisyTable.getColumnModel().getColumn(0).setMinWidth(0);
        wyswietlPrzepisyTable.getColumnModel().getColumn(0).setMaxWidth(0);
        wyswietlPrzepisyTable.getColumnModel().getColumn(0).setPreferredWidth(0);
        this.wyswietlPrzepisyTable.setSelectionModel(new ClassListSelectionModel());
        wyswietlPrzepisyTable.addMouseListener(new java.awt.event.MouseAdapter() {
        @Override
        public void mouseClicked(java.awt.event.MouseEvent evt) {
        int row = wyswietlPrzepisyTable.rowAtPoint(evt.getPoint());
       //int col = wyswietlPrzepisyTable.columnAtPoint(evt.getPoint());
        if (row >= 0) {
            System.out.println("Kliknieto");
            
            String odp=null;
            String[] parametry={id_user,wyswietlPrzepisyTable.getModel().getValueAt(row,0).toString()};
            try {
                odp=psql.dodajDaneZfunkcji("czy_osoba_lubi_przepis",parametry);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
            if(odp.equals("1")){
                wyswietlPrzepisyDodajUlubione.setText("Dislike");
                Object [] obj=psql.getElementOfTable("ulubione","priorytet","id_osoba="+id_user+" and "
                        +"id_przepis="+wyswietlPrzepisyTable.getModel().getValueAt(row,0).toString());
                priorytetLabel.setText("Priorytet zaznaczonego przepisu: "+obj[0].toString());
                
            }
                else{
                wyswietlPrzepisyDodajUlubione.setText("Like");
                priorytetLabel.setText("---");
            }

        }
        }
}       );
        wyswietlPrzepisyTable.getColumnModel().getColumn(0).setMinWidth(0);
        wyswietlPrzepisyTable.getColumnModel().getColumn(0).setMaxWidth(0);
        wyswietlPrzepisyTable.getColumnModel().getColumn(0).setPreferredWidth(0);
        this.wyswietlPrzepisyTable.repaint();
        
        String dane5[]={"id_aktywnosc","zdarzenie","id_autor"};
        TableModel model5 = null;
        Object[][] obj5=new Object[0][3];
        model5 = new SkladnikiJednostkaTableModel(obj5,dane5);
        this.rankingTable.setModel(model5);
        this.rankingTable.setSelectionModel(new ClassListSelectionModel());
        this.rankingTable.repaint();
        rankingTable.getColumnModel().getColumn(0).setMinWidth(0);
        rankingTable.getColumnModel().getColumn(0).setMaxWidth(0);
        rankingTable.getColumnModel().getColumn(0).setPreferredWidth(0);
        
        String dane6[]={"nick","liczba_przepisow"};
        TableModel model6 = null;
        Object[][] obj6=new Object[0][2];
        model6 = new SkladnikiJednostkaTableModel(obj6,dane6);
        this.rankingPrawdziwyTable.setModel(model6);
        this.rankingPrawdziwyTable.setSelectionModel(new ClassListSelectionModel());
        this.rankingPrawdziwyTable.repaint();
        
        this.ustawDostep(false);
        this.wylogujMenu.setEnabled(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        addtUserJFrame = new javax.swing.JFrame();
        jButton3 = new javax.swing.JButton();
        PodajImie = new javax.swing.JTextField();
        PodajNazwisko = new javax.swing.JTextField();
        PodajNick = new javax.swing.JTextField();
        AddUser = new javax.swing.JButton();
        addUserJFramePodajImieLabel = new javax.swing.JLabel();
        addUserJFramePodajNazwiskoLabel = new javax.swing.JLabel();
        addUserJFramePodajNickLabel = new javax.swing.JLabel();
        addRekwizytFrame = new javax.swing.JFrame();
        wybieranieRekwizytu = new javax.swing.JComboBox();
        ilosc = new javax.swing.JTextField();
        checkRekOpt = new javax.swing.JCheckBox();
        NazwaRekwizytu = new javax.swing.JTextField();
        addRekwizytButton = new javax.swing.JButton();
        dodajRekwizytDoPrzepisuIlosc = new javax.swing.JTextField();
        dodajRekwizytDoPrzepisuButton = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        nazwaRekwizytuLabel = new javax.swing.JLabel();
        iloscRekwizytowLabel = new javax.swing.JLabel();
        addProductOsAddPrzepisSkladnikFrame = new javax.swing.JFrame();
        addProductOsDataWaznosci = new javax.swing.JTextField();
        addProductOsIlosc = new javax.swing.JTextField();
        addProduktButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        dodawanieProduktJednostka = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        dodajPrzepisdodajSkladnikIlosc = new javax.swing.JTextField();
        dodajPrzepisDodajSkladnikButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        podajDateWaznosciLabel = new javax.swing.JLabel();
        addProductJednostkaFrame = new javax.swing.JFrame();
        wybieranieProduktu2 = new javax.swing.JComboBox();
        wybieranieJednostki = new javax.swing.JComboBox();
        sugerowanaCena = new javax.swing.JTextField();
        liczbaKalorii = new javax.swing.JTextField();
        dodawanieProduktu = new javax.swing.JButton();
        dodawanieJednostki = new javax.swing.JButton();
        dodawanieProduktuJedDoBazy = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        wyswietlTwojeRekwizytyFrame = new javax.swing.JFrame();
        zamknijWyswietlTwojeRekwizytyFrame = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        wyswietlTwojeRekwizytyTable = new javax.swing.JTable();
        wyswietlTwojeProduktyFrame = new javax.swing.JFrame();
        jButton6 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        wyswietlTwojeProduktyTable = new javax.swing.JTable();
        dodajPrzepisFrame = new javax.swing.JFrame();
        nazwaPrzepisu = new javax.swing.JTextField();
        czasWykonania = new javax.swing.JTextField();
        liczbaPorcji = new javax.swing.JTextField();
        trudnoscWykonania = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        opisWykonania = new javax.swing.JTextPane();
        dodajPrzepisButton = new javax.swing.JButton();
        zamknijDodajPrzepisFrame = new javax.swing.JButton();
        dodajSkladnikiDoPrzepisuButton = new javax.swing.JButton();
        dodajRekwizytyDoPrzepisuButton = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        dodajPrzepisdodajSkladnikiTable = new javax.swing.JTable();
        jScrollPane6 = new javax.swing.JScrollPane();
        dodajPrzepisDodajRekwizytTable = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        dataDodaniaField = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        wyswietlPrzepisyFrame = new javax.swing.JFrame();
        jScrollPane7 = new javax.swing.JScrollPane();
        wyswietlPrzepisyTable = new javax.swing.JTable();
        wyswietlPrzepisyZamknij = new javax.swing.JButton();
        wyswietlPrzepisyobejrzyjPrzepisButton = new javax.swing.JButton();
        wyswietlPrzepisyUsunPrzepisButton = new javax.swing.JButton();
        wyswietlMojePrzepisyCheckButton = new javax.swing.JCheckBox();
        wyswietlPrzepisyDodajUlubione = new javax.swing.JButton();
        wyswietlPrzepisyPokazUlubione = new javax.swing.JCheckBox();
        wyswietlPrzepisyCenaKalorie = new javax.swing.JButton();
        priorytetLabel = new javax.swing.JLabel();
        aktywnoscPokazFrame = new javax.swing.JFrame();
        jScrollPane8 = new javax.swing.JScrollPane();
        rankingTable = new javax.swing.JTable();
        rankingZamknij = new javax.swing.JButton();
        rankingPrawdziwy = new javax.swing.JFrame();
        jScrollPane9 = new javax.swing.JScrollPane();
        rankingPrawdziwyTable = new javax.swing.JTable();
        rankingPrawdziwyZamknij = new javax.swing.JButton();
        ifZalogowanyLabel = new javax.swing.JLabel();
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        openMenuItem = new javax.swing.JMenuItem();
        zalogujMenu = new javax.swing.JMenuItem();
        wylogujMenu = new javax.swing.JMenuItem();
        exitMenuItem = new javax.swing.JMenuItem();
        rekwizytyOsoby = new javax.swing.JMenu();
        dodajRekwizyt = new javax.swing.JMenuItem();
        wyswietlTwojeRekwizytyOpen = new javax.swing.JMenuItem();
        yourproducts = new javax.swing.JMenu();
        dodajProdukt = new javax.swing.JMenuItem();
        wyswietlTwojeProduktyOpen = new javax.swing.JMenuItem();
        przepisykulinarneMenu = new javax.swing.JMenu();
        dodajPrzepisOpen = new javax.swing.JMenuItem();
        wyswietlPrzepisy = new javax.swing.JMenuItem();
        aktywnosciMenu = new javax.swing.JMenu();
        aktywnoscWyswietl = new javax.swing.JMenuItem();
        rankingWyswietl = new javax.swing.JMenuItem();

        addtUserJFrame.setTitle("Dodaj nowego użytkownika");
        addtUserJFrame.setMaximizedBounds(new java.awt.Rectangle(0, 0, 0, 0));
        addtUserJFrame.setMinimumSize(new java.awt.Dimension(276, 245));
        addtUserJFrame.setResizable(false);
        this.addtUserJFrame.setLocationRelativeTo(null);

        jButton3.setText("Zamknij okno");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        AddUser.setText("Dodaj Usera");
        AddUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddUserActionPerformed(evt);
            }
        });

        addUserJFramePodajImieLabel.setText("Podaj imię");

        addUserJFramePodajNazwiskoLabel.setText("Podaj hasło");

        addUserJFramePodajNickLabel.setText("Podaj nick");

        javax.swing.GroupLayout addtUserJFrameLayout = new javax.swing.GroupLayout(addtUserJFrame.getContentPane());
        addtUserJFrame.getContentPane().setLayout(addtUserJFrameLayout);
        addtUserJFrameLayout.setHorizontalGroup(
            addtUserJFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addtUserJFrameLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(addtUserJFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(addtUserJFrameLayout.createSequentialGroup()
                        .addComponent(PodajImie, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(addUserJFramePodajImieLabel))
                    .addGroup(addtUserJFrameLayout.createSequentialGroup()
                        .addGroup(addtUserJFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(PodajNazwisko, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(PodajNick, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(AddUser, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(addtUserJFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton3)
                            .addComponent(addUserJFramePodajNazwiskoLabel)
                            .addComponent(addUserJFramePodajNickLabel))))
                .addGap(23, 23, 23))
        );
        addtUserJFrameLayout.setVerticalGroup(
            addtUserJFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addtUserJFrameLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(addtUserJFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(addtUserJFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(PodajImie, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(addUserJFramePodajImieLabel))
                    .addGroup(addtUserJFrameLayout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addGroup(addtUserJFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(PodajNazwisko, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(addUserJFramePodajNazwiskoLabel))))
                .addGap(20, 20, 20)
                .addGroup(addtUserJFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(PodajNick, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addUserJFramePodajNickLabel))
                .addGap(18, 18, 18)
                .addGroup(addtUserJFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AddUser)
                    .addComponent(jButton3))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        addRekwizytFrame.setTitle("Dodaj rekwizyt");
        addRekwizytFrame.setMinimumSize(new java.awt.Dimension(600, 200));
        addRekwizytFrame.setResizable(false);
        this.addRekwizytFrame.setLocationRelativeTo(null);

        wybieranieRekwizytu.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        wybieranieRekwizytu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wybieranieRekwizytuActionPerformed(evt);
            }
        });

        checkRekOpt.setText("Wybierz rekwizyt z listy dostępnych rekwizytów");
        checkRekOpt.setToolTipText("This is a message");
        checkRekOpt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkRekOptActionPerformed(evt);
            }
        });

        NazwaRekwizytu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NazwaRekwizytuActionPerformed(evt);
            }
        });

        addRekwizytButton.setText("Dodaj");
        addRekwizytButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addRekwizytButtonActionPerformed(evt);
            }
        });

        dodajRekwizytDoPrzepisuButton.setText("Dodaj");
        dodajRekwizytDoPrzepisuButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dodajRekwizytDoPrzepisuButtonActionPerformed(evt);
            }
        });

        jButton7.setText("Zamknij");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        nazwaRekwizytuLabel.setText("Nazwa");

        iloscRekwizytowLabel.setText("Ilość");
        this.iloscRekwizytowLabel.setToolTipText("Liczba całkowita nieujemna");

        javax.swing.GroupLayout addRekwizytFrameLayout = new javax.swing.GroupLayout(addRekwizytFrame.getContentPane());
        addRekwizytFrame.getContentPane().setLayout(addRekwizytFrameLayout);
        addRekwizytFrameLayout.setHorizontalGroup(
            addRekwizytFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addRekwizytFrameLayout.createSequentialGroup()
                .addGroup(addRekwizytFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(addRekwizytFrameLayout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addGroup(addRekwizytFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(checkRekOpt)
                            .addGroup(addRekwizytFrameLayout.createSequentialGroup()
                                .addComponent(wybieranieRekwizytu, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(NazwaRekwizytu, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(addRekwizytFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jButton7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(addRekwizytButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(ilosc, javax.swing.GroupLayout.DEFAULT_SIZE, 86, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(addRekwizytFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(dodajRekwizytDoPrzepisuIlosc, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(dodajRekwizytDoPrzepisuButton, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(addRekwizytFrameLayout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addComponent(nazwaRekwizytuLabel)
                        .addGap(58, 58, 58)
                        .addComponent(iloscRekwizytowLabel)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        addRekwizytFrameLayout.setVerticalGroup(
            addRekwizytFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addRekwizytFrameLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(checkRekOpt)
                .addGap(18, 18, 18)
                .addGroup(addRekwizytFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nazwaRekwizytuLabel)
                    .addComponent(iloscRekwizytowLabel))
                .addGap(3, 3, 3)
                .addGroup(addRekwizytFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(NazwaRekwizytu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ilosc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(wybieranieRekwizytu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dodajRekwizytDoPrzepisuIlosc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(addRekwizytFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addRekwizytButton)
                    .addComponent(dodajRekwizytDoPrzepisuButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton7)
                .addGap(17, 17, 17))
        );

        addProductOsAddPrzepisSkladnikFrame.setTitle("Dodaj produkt spożywczy");
        addProductOsAddPrzepisSkladnikFrame.setMinimumSize(new java.awt.Dimension(500, 423));
        addProductOsAddPrzepisSkladnikFrame.setResizable(false);
        this.addProductOsAddPrzepisSkladnikFrame.setLocationRelativeTo(null);

        addProductOsDataWaznosci.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addProductOsDataWaznosciActionPerformed(evt);
            }
        });

        addProductOsIlosc.setMinimumSize(new java.awt.Dimension(110, 27));
        addProductOsIlosc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addProductOsIloscActionPerformed(evt);
            }
        });

        addProduktButton1.setText("Dodaj");
        addProduktButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addProduktButton1ActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        dodawanieProduktJednostka.setText("Przejdź do kreatora dodawania produktu z jednostką");
        dodawanieProduktJednostka.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dodawanieProduktJednostkaActionPerformed(evt);
            }
        });

        jButton5.setText("Zamknij");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        dodajPrzepisDodajSkladnikButton.setText("Dodaj Skł");
        dodajPrzepisDodajSkladnikButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dodajPrzepisDodajSkladnikButtonActionPerformed(evt);
            }
        });

        jLabel1.setText("Podaj ilość");

        podajDateWaznosciLabel.setText("Podaj datę ważności [rrrr-mm-dd]");

        javax.swing.GroupLayout addProductOsAddPrzepisSkladnikFrameLayout = new javax.swing.GroupLayout(addProductOsAddPrzepisSkladnikFrame.getContentPane());
        addProductOsAddPrzepisSkladnikFrame.getContentPane().setLayout(addProductOsAddPrzepisSkladnikFrameLayout);
        addProductOsAddPrzepisSkladnikFrameLayout.setHorizontalGroup(
            addProductOsAddPrzepisSkladnikFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addProductOsAddPrzepisSkladnikFrameLayout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(addProductOsAddPrzepisSkladnikFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, addProductOsAddPrzepisSkladnikFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(addProductOsAddPrzepisSkladnikFrameLayout.createSequentialGroup()
                            .addGroup(addProductOsAddPrzepisSkladnikFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(addProductOsAddPrzepisSkladnikFrameLayout.createSequentialGroup()
                                    .addGap(23, 23, 23)
                                    .addComponent(jLabel1))
                                .addComponent(dodajPrzepisdodajSkladnikIlosc, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
                                .addComponent(addProductOsIlosc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(addProductOsAddPrzepisSkladnikFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(addProductOsAddPrzepisSkladnikFrameLayout.createSequentialGroup()
                                    .addComponent(podajDateWaznosciLabel)
                                    .addGap(0, 140, Short.MAX_VALUE))
                                .addGroup(addProductOsAddPrzepisSkladnikFrameLayout.createSequentialGroup()
                                    .addGap(6, 6, 6)
                                    .addComponent(addProductOsDataWaznosci, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(addProductOsAddPrzepisSkladnikFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(dodajPrzepisDodajSkladnikButton)
                                        .addComponent(addProduktButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addContainerGap())))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, addProductOsAddPrzepisSkladnikFrameLayout.createSequentialGroup()
                            .addGap(0, 0, Short.MAX_VALUE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, addProductOsAddPrzepisSkladnikFrameLayout.createSequentialGroup()
                        .addComponent(jButton5)
                        .addGap(29, 29, 29))))
            .addGroup(addProductOsAddPrzepisSkladnikFrameLayout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addComponent(dodawanieProduktJednostka, javax.swing.GroupLayout.PREFERRED_SIZE, 379, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        addProductOsAddPrzepisSkladnikFrameLayout.setVerticalGroup(
            addProductOsAddPrzepisSkladnikFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addProductOsAddPrzepisSkladnikFrameLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(dodawanieProduktJednostka)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(addProductOsAddPrzepisSkladnikFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(podajDateWaznosciLabel))
                .addGap(3, 3, 3)
                .addGroup(addProductOsAddPrzepisSkladnikFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addProductOsIlosc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addProductOsDataWaznosci, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addProduktButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(addProductOsAddPrzepisSkladnikFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dodajPrzepisDodajSkladnikButton)
                    .addComponent(dodajPrzepisdodajSkladnikIlosc, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton5))
        );

        addProductJednostkaFrame.setTitle("Kreator dodawania nowego produktu z jednostką");
        addProductJednostkaFrame.setMinimumSize(new java.awt.Dimension(500, 280));
        addProductJednostkaFrame.setResizable(false);
        this.addProductJednostkaFrame.setLocationRelativeTo(null);

        wybieranieProduktu2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        wybieranieJednostki.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        sugerowanaCena.setMinimumSize(new java.awt.Dimension(70, 27));
        sugerowanaCena.setPreferredSize(new java.awt.Dimension(70, 27));

        liczbaKalorii.setMinimumSize(new java.awt.Dimension(70, 27));
        liczbaKalorii.setPreferredSize(new java.awt.Dimension(70, 27));

        dodawanieProduktu.setText("Dodaj nowy produkt");
        dodawanieProduktu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dodawanieProduktuActionPerformed(evt);
            }
        });

        dodawanieJednostki.setText("Dodaj nową jednostkę");
        dodawanieJednostki.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dodawanieJednostkiActionPerformed(evt);
            }
        });

        dodawanieProduktuJedDoBazy.setText("Dodaj");
        dodawanieProduktuJedDoBazy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dodawanieProduktuJedDoBazyActionPerformed(evt);
            }
        });

        jButton4.setText("Zamknij kreator");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel2.setText("Cena");

        jLabel3.setText("Liczba kcal");

        jLabel4.setText("Dla jednej jednostki:");

        jLabel5.setText("Produkt");

        jLabel6.setText("Jednostka");

        javax.swing.GroupLayout addProductJednostkaFrameLayout = new javax.swing.GroupLayout(addProductJednostkaFrame.getContentPane());
        addProductJednostkaFrame.getContentPane().setLayout(addProductJednostkaFrameLayout);
        addProductJednostkaFrameLayout.setHorizontalGroup(
            addProductJednostkaFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addProductJednostkaFrameLayout.createSequentialGroup()
                .addContainerGap(40, Short.MAX_VALUE)
                .addGroup(addProductJednostkaFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, addProductJednostkaFrameLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(dodawanieProduktuJedDoBazy)
                        .addGap(18, 18, 18)
                        .addComponent(jButton4)
                        .addGap(23, 23, 23))
                    .addGroup(addProductJednostkaFrameLayout.createSequentialGroup()
                        .addGroup(addProductJednostkaFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(addProductJednostkaFrameLayout.createSequentialGroup()
                                .addGroup(addProductJednostkaFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(wybieranieProduktu2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(addProductJednostkaFrameLayout.createSequentialGroup()
                                        .addGap(10, 10, 10)
                                        .addComponent(jLabel5)))
                                .addGap(18, 18, 18)
                                .addGroup(addProductJednostkaFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(wybieranieJednostki, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6))
                                .addGap(22, 22, 22)
                                .addGroup(addProductJednostkaFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, addProductJednostkaFrameLayout.createSequentialGroup()
                                        .addGroup(addProductJednostkaFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(sugerowanaCena, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, addProductJednostkaFrameLayout.createSequentialGroup()
                                                .addGap(25, 25, 25)
                                                .addComponent(jLabel2)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(addProductJednostkaFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(liczbaKalorii, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel3)))
                                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)))
                            .addGroup(addProductJednostkaFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(dodawanieProduktu, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(dodawanieJednostki, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addContainerGap(78, Short.MAX_VALUE))))
        );
        addProductJednostkaFrameLayout.setVerticalGroup(
            addProductJednostkaFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addProductJednostkaFrameLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(addProductJednostkaFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(addProductJednostkaFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(wybieranieProduktu2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(wybieranieJednostki, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sugerowanaCena, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(liczbaKalorii, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dodawanieProduktu)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dodawanieJednostki)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(addProductJednostkaFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dodawanieProduktuJedDoBazy)
                    .addComponent(jButton4))
                .addContainerGap())
        );

        wyswietlTwojeRekwizytyFrame.setTitle("Twoje rekwizyty");
        wyswietlTwojeRekwizytyFrame.setMinimumSize(new java.awt.Dimension(400, 360));
        this.wyswietlTwojeRekwizytyFrame.setLocationRelativeTo(null);

        zamknijWyswietlTwojeRekwizytyFrame.setText("OK");
        zamknijWyswietlTwojeRekwizytyFrame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zamknijWyswietlTwojeRekwizytyFrameActionPerformed(evt);
            }
        });

        wyswietlTwojeRekwizytyTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(wyswietlTwojeRekwizytyTable);

        javax.swing.GroupLayout wyswietlTwojeRekwizytyFrameLayout = new javax.swing.GroupLayout(wyswietlTwojeRekwizytyFrame.getContentPane());
        wyswietlTwojeRekwizytyFrame.getContentPane().setLayout(wyswietlTwojeRekwizytyFrameLayout);
        wyswietlTwojeRekwizytyFrameLayout.setHorizontalGroup(
            wyswietlTwojeRekwizytyFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(wyswietlTwojeRekwizytyFrameLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(12, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, wyswietlTwojeRekwizytyFrameLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(zamknijWyswietlTwojeRekwizytyFrame, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38))
        );
        wyswietlTwojeRekwizytyFrameLayout.setVerticalGroup(
            wyswietlTwojeRekwizytyFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(wyswietlTwojeRekwizytyFrameLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(zamknijWyswietlTwojeRekwizytyFrame))
        );

        wyswietlTwojeProduktyFrame.setTitle("Wyświetl Twoje produkty");
        wyswietlTwojeProduktyFrame.setMinimumSize(new java.awt.Dimension(560, 360));
        wyswietlTwojeProduktyFrame.setResizable(false);
        this.wyswietlTwojeProduktyFrame.setLocationRelativeTo(null);

        jButton6.setText("OK");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        wyswietlTwojeProduktyTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane3.setViewportView(wyswietlTwojeProduktyTable);

        javax.swing.GroupLayout wyswietlTwojeProduktyFrameLayout = new javax.swing.GroupLayout(wyswietlTwojeProduktyFrame.getContentPane());
        wyswietlTwojeProduktyFrame.getContentPane().setLayout(wyswietlTwojeProduktyFrameLayout);
        wyswietlTwojeProduktyFrameLayout.setHorizontalGroup(
            wyswietlTwojeProduktyFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, wyswietlTwojeProduktyFrameLayout.createSequentialGroup()
                .addContainerGap(474, Short.MAX_VALUE)
                .addComponent(jButton6)
                .addGap(34, 34, 34))
            .addGroup(wyswietlTwojeProduktyFrameLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3)
                .addContainerGap())
        );
        wyswietlTwojeProduktyFrameLayout.setVerticalGroup(
            wyswietlTwojeProduktyFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(wyswietlTwojeProduktyFrameLayout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton6)
                .addContainerGap())
        );

        dodajPrzepisFrame.setTitle("Dodaj nowy przepis");
        dodajPrzepisFrame.setMinimumSize(new java.awt.Dimension(900, 350));
        dodajPrzepisFrame.setPreferredSize(new java.awt.Dimension(900, 300));
        dodajPrzepisFrame.setResizable(false);
        this.dodajPrzepisFrame.setLocationRelativeTo(null);

        liczbaPorcji.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                liczbaPorcjiActionPerformed(evt);
            }
        });

        trudnoscWykonania.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                trudnoscWykonaniaActionPerformed(evt);
            }
        });

        opisWykonania.setText("Wykonanie");
        jScrollPane4.setViewportView(opisWykonania);

        dodajPrzepisButton.setText("Dodaj");
        dodajPrzepisButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dodajPrzepisButtonActionPerformed(evt);
            }
        });

        zamknijDodajPrzepisFrame.setText("Zamknij");
        zamknijDodajPrzepisFrame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zamknijDodajPrzepisFrameActionPerformed(evt);
            }
        });

        dodajSkladnikiDoPrzepisuButton.setText("Dodaj składniki do przepisu");
        dodajSkladnikiDoPrzepisuButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dodajSkladnikiDoPrzepisuButtonActionPerformed(evt);
            }
        });

        dodajRekwizytyDoPrzepisuButton.setText("Dodaj rekwizyty do przepisu");
        dodajRekwizytyDoPrzepisuButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dodajRekwizytyDoPrzepisuButtonActionPerformed(evt);
            }
        });

        dodajPrzepisdodajSkladnikiTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane5.setViewportView(dodajPrzepisdodajSkladnikiTable);

        dodajPrzepisDodajRekwizytTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane6.setViewportView(dodajPrzepisDodajRekwizytTable);

        jLabel7.setText("Nazwa przepisu");

        jLabel8.setText("Czas wykonania [min]");

        jLabel9.setText("L. porcji");

        jLabel10.setText("Trudność wykonania[od 1 do 10]");

        jLabel11.setText("Wykonanie");

        dataDodaniaField.setEnabled(false);

        jLabel12.setText("Data dodania");

        javax.swing.GroupLayout dodajPrzepisFrameLayout = new javax.swing.GroupLayout(dodajPrzepisFrame.getContentPane());
        dodajPrzepisFrame.getContentPane().setLayout(dodajPrzepisFrameLayout);
        dodajPrzepisFrameLayout.setHorizontalGroup(
            dodajPrzepisFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dodajPrzepisFrameLayout.createSequentialGroup()
                .addGroup(dodajPrzepisFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dodajPrzepisFrameLayout.createSequentialGroup()
                        .addGroup(dodajPrzepisFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(dodajPrzepisFrameLayout.createSequentialGroup()
                                .addGap(13, 13, 13)
                                .addComponent(nazwaPrzepisu, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(dodajPrzepisFrameLayout.createSequentialGroup()
                                .addGap(37, 37, 37)
                                .addComponent(jLabel7)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(dodajPrzepisFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(czasWykonania)
                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(dodajPrzepisFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(dodajPrzepisFrameLayout.createSequentialGroup()
                                .addGap(46, 46, 46)
                                .addComponent(jLabel9)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel10))
                            .addGroup(dodajPrzepisFrameLayout.createSequentialGroup()
                                .addGap(28, 28, 28)
                                .addComponent(liczbaPorcji, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(53, 53, 53)
                                .addComponent(trudnoscWykonania, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(34, 34, 34)
                        .addGroup(dodajPrzepisFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dataDodaniaField, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12)))
                    .addGroup(dodajPrzepisFrameLayout.createSequentialGroup()
                        .addGap(90, 90, 90)
                        .addComponent(dodajSkladnikiDoPrzepisuButton)
                        .addGap(180, 180, 180)
                        .addComponent(dodajRekwizytyDoPrzepisuButton, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(dodajPrzepisFrameLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 415, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(1, 1, 1)
                .addGroup(dodajPrzepisFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dodajPrzepisFrameLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                        .addGroup(dodajPrzepisFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(dodajPrzepisFrameLayout.createSequentialGroup()
                                .addComponent(dodajPrzepisButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(zamknijDodajPrzepisFrame))
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dodajPrzepisFrameLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel11)
                        .addGap(58, 58, 58))))
        );
        dodajPrzepisFrameLayout.setVerticalGroup(
            dodajPrzepisFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dodajPrzepisFrameLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(dodajPrzepisFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10)
                    .addComponent(jLabel8)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(dodajPrzepisFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nazwaPrzepisu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(czasWykonania, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(liczbaPorcji, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(trudnoscWykonania, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dataDodaniaField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(dodajPrzepisFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(dodajPrzepisFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dodajRekwizytyDoPrzepisuButton)
                    .addComponent(dodajSkladnikiDoPrzepisuButton)
                    .addComponent(dodajPrzepisButton)
                    .addComponent(zamknijDodajPrzepisFrame))
                .addContainerGap())
        );

        wyswietlPrzepisyFrame.setTitle("Przepisy kulinarne");
        wyswietlPrzepisyFrame.setMinimumSize(new java.awt.Dimension(500, 360));

        wyswietlPrzepisyTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane7.setViewportView(wyswietlPrzepisyTable);

        wyswietlPrzepisyZamknij.setText("Zamknij");
        wyswietlPrzepisyZamknij.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wyswietlPrzepisyZamknijActionPerformed(evt);
            }
        });

        wyswietlPrzepisyobejrzyjPrzepisButton.setText("Obejrzyj");
        wyswietlPrzepisyobejrzyjPrzepisButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wyswietlPrzepisyobejrzyjPrzepisButtonActionPerformed(evt);
            }
        });

        wyswietlPrzepisyUsunPrzepisButton.setText("Usuń");
        wyswietlPrzepisyUsunPrzepisButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wyswietlPrzepisyUsunPrzepisButtonActionPerformed(evt);
            }
        });

        wyswietlMojePrzepisyCheckButton.setText("Pokaż moje przepisy");
        wyswietlMojePrzepisyCheckButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wyswietlMojePrzepisyCheckButtonActionPerformed(evt);
            }
        });

        wyswietlPrzepisyDodajUlubione.setText("Like");
        wyswietlPrzepisyDodajUlubione.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wyswietlPrzepisyDodajUlubioneActionPerformed(evt);
            }
        });

        wyswietlPrzepisyPokazUlubione.setText("Pokaż ulubione");
        wyswietlPrzepisyPokazUlubione.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wyswietlPrzepisyPokazUlubioneActionPerformed(evt);
            }
        });

        wyswietlPrzepisyCenaKalorie.setText("Kcal i cena");
        wyswietlPrzepisyCenaKalorie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wyswietlPrzepisyCenaKalorieActionPerformed(evt);
            }
        });

        priorytetLabel.setText("---");

        javax.swing.GroupLayout wyswietlPrzepisyFrameLayout = new javax.swing.GroupLayout(wyswietlPrzepisyFrame.getContentPane());
        wyswietlPrzepisyFrame.getContentPane().setLayout(wyswietlPrzepisyFrameLayout);
        wyswietlPrzepisyFrameLayout.setHorizontalGroup(
            wyswietlPrzepisyFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(wyswietlPrzepisyFrameLayout.createSequentialGroup()
                .addGroup(wyswietlPrzepisyFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(wyswietlPrzepisyFrameLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(wyswietlPrzepisyFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(wyswietlPrzepisyZamknij)
                            .addComponent(wyswietlPrzepisyCenaKalorie)
                            .addGroup(wyswietlPrzepisyFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(wyswietlPrzepisyDodajUlubione, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(wyswietlPrzepisyUsunPrzepisButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(wyswietlPrzepisyobejrzyjPrzepisButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(wyswietlPrzepisyFrameLayout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addComponent(wyswietlMojePrzepisyCheckButton)
                        .addGap(18, 18, 18)
                        .addGroup(wyswietlPrzepisyFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(wyswietlPrzepisyFrameLayout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addComponent(priorytetLabel))
                            .addComponent(wyswietlPrzepisyPokazUlubione))))
                .addContainerGap(8, Short.MAX_VALUE))
        );
        wyswietlPrzepisyFrameLayout.setVerticalGroup(
            wyswietlPrzepisyFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, wyswietlPrzepisyFrameLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(wyswietlPrzepisyFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(wyswietlMojePrzepisyCheckButton)
                    .addComponent(wyswietlPrzepisyPokazUlubione))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(priorytetLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, wyswietlPrzepisyFrameLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(wyswietlPrzepisyCenaKalorie)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(wyswietlPrzepisyDodajUlubione)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(wyswietlPrzepisyUsunPrzepisButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(wyswietlPrzepisyobejrzyjPrzepisButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(wyswietlPrzepisyZamknij)
                .addContainerGap())
        );

        aktywnoscPokazFrame.setTitle("Aktywności");
        aktywnoscPokazFrame.setMinimumSize(new java.awt.Dimension(560, 380));

        rankingTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane8.setViewportView(rankingTable);

        rankingZamknij.setText("Zamknij");
        rankingZamknij.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rankingZamknijActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout aktywnoscPokazFrameLayout = new javax.swing.GroupLayout(aktywnoscPokazFrame.getContentPane());
        aktywnoscPokazFrame.getContentPane().setLayout(aktywnoscPokazFrameLayout);
        aktywnoscPokazFrameLayout.setHorizontalGroup(
            aktywnoscPokazFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(aktywnoscPokazFrameLayout.createSequentialGroup()
                .addGap(304, 304, 304)
                .addComponent(rankingZamknij)
                .addContainerGap(141, Short.MAX_VALUE))
            .addGroup(aktywnoscPokazFrameLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane8)
                .addContainerGap())
        );
        aktywnoscPokazFrameLayout.setVerticalGroup(
            aktywnoscPokazFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(aktywnoscPokazFrameLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                .addComponent(rankingZamknij)
                .addContainerGap())
        );

        rankingPrawdziwy.setTitle("Ranking");
        rankingPrawdziwy.setMinimumSize(new java.awt.Dimension(500, 300));

        rankingPrawdziwyTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane9.setViewportView(rankingPrawdziwyTable);

        rankingPrawdziwyZamknij.setText("Zamknij");
        rankingPrawdziwyZamknij.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rankingPrawdziwyZamknijActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout rankingPrawdziwyLayout = new javax.swing.GroupLayout(rankingPrawdziwy.getContentPane());
        rankingPrawdziwy.getContentPane().setLayout(rankingPrawdziwyLayout);
        rankingPrawdziwyLayout.setHorizontalGroup(
            rankingPrawdziwyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rankingPrawdziwyLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rankingPrawdziwyZamknij)
                .addContainerGap(18, Short.MAX_VALUE))
        );
        rankingPrawdziwyLayout.setVerticalGroup(
            rankingPrawdziwyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rankingPrawdziwyLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(rankingPrawdziwyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rankingPrawdziwyLayout.createSequentialGroup()
                        .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rankingPrawdziwyLayout.createSequentialGroup()
                        .addComponent(rankingPrawdziwyZamknij)
                        .addGap(57, 57, 57))))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Książka kucharska");

        ifZalogowanyLabel.setText("Niezalogowany");

        fileMenu.setMnemonic('f');
        fileMenu.setText("Użytkownik");

        openMenuItem.setMnemonic('o');
        openMenuItem.setText("Dodaj użytkownika");
        openMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(openMenuItem);

        zalogujMenu.setMnemonic('s');
        zalogujMenu.setText("Zaloguj");
        zalogujMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zalogujMenuActionPerformed(evt);
            }
        });
        fileMenu.add(zalogujMenu);

        wylogujMenu.setMnemonic('a');
        wylogujMenu.setText("Wyloguj");
        wylogujMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wylogujMenuActionPerformed(evt);
            }
        });
        fileMenu.add(wylogujMenu);

        exitMenuItem.setMnemonic('x');
        exitMenuItem.setText("Exit");
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        rekwizytyOsoby.setMnemonic('e');
        rekwizytyOsoby.setText("Twoje Rekwizyty");

        dodajRekwizyt.setMnemonic('t');
        dodajRekwizyt.setText("Dodaj nowy");
        dodajRekwizyt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dodajRekwizytActionPerformed(evt);
            }
        });
        rekwizytyOsoby.add(dodajRekwizyt);

        wyswietlTwojeRekwizytyOpen.setMnemonic('y');
        wyswietlTwojeRekwizytyOpen.setText("Wyświetl Twoje rekwizyty");
        wyswietlTwojeRekwizytyOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wyswietlTwojeRekwizytyOpenActionPerformed(evt);
            }
        });
        rekwizytyOsoby.add(wyswietlTwojeRekwizytyOpen);

        menuBar.add(rekwizytyOsoby);

        yourproducts.setMnemonic('h');
        yourproducts.setText("Twoje produkty");

        dodajProdukt.setMnemonic('c');
        dodajProdukt.setText("Dodaj");
        dodajProdukt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dodajProduktActionPerformed(evt);
            }
        });
        yourproducts.add(dodajProdukt);

        wyswietlTwojeProduktyOpen.setMnemonic('a');
        wyswietlTwojeProduktyOpen.setText("Wyświetl Twoje produkty");
        wyswietlTwojeProduktyOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wyswietlTwojeProduktyOpenActionPerformed(evt);
            }
        });
        yourproducts.add(wyswietlTwojeProduktyOpen);

        menuBar.add(yourproducts);

        przepisykulinarneMenu.setText("Przepisy kulinarne");

        dodajPrzepisOpen.setText("Dodaj nowy przepis");
        dodajPrzepisOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dodajPrzepisOpenActionPerformed(evt);
            }
        });
        przepisykulinarneMenu.add(dodajPrzepisOpen);

        wyswietlPrzepisy.setText("Wyświetl przepisy");
        wyswietlPrzepisy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wyswietlPrzepisyActionPerformed(evt);
            }
        });
        przepisykulinarneMenu.add(wyswietlPrzepisy);

        menuBar.add(przepisykulinarneMenu);

        aktywnosciMenu.setText("Aktywności");

        aktywnoscWyswietl.setText("Pokaż aktywności");
        aktywnoscWyswietl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aktywnoscWyswietlActionPerformed(evt);
            }
        });
        aktywnosciMenu.add(aktywnoscWyswietl);

        rankingWyswietl.setText("Ranking");
        rankingWyswietl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rankingWyswietlActionPerformed(evt);
            }
        });
        aktywnosciMenu.add(rankingWyswietl);

        menuBar.add(aktywnosciMenu);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(350, Short.MAX_VALUE)
                .addComponent(ifZalogowanyLabel)
                .addGap(52, 52, 52))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(196, Short.MAX_VALUE)
                .addComponent(ifZalogowanyLabel)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
        psql.disconnect();
        System.exit(0);
    }//GEN-LAST:event_exitMenuItemActionPerformed

    private void openMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openMenuItemActionPerformed
        addtUserJFrame.show();
    }//GEN-LAST:event_openMenuItemActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        addtUserJFrame.dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void AddUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddUserActionPerformed
        // TODO add your handling code here:
        psql.dodajUzytkownika(PodajImie.getText(),PodajNazwisko.getText(),PodajNick.getText());
    }//GEN-LAST:event_AddUserActionPerformed

    private void zalogujMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zalogujMenuActionPerformed
        JDialog.setDefaultLookAndFeelDecorated(true);
    //Object[][] selectionValues=psql.getUzytkownicy();
        Object[] selectionValues=psql.getElementOfTable("osoby", "nick","");
    String initialSelection = selectionValues[0].toString();
    Object selection = JOptionPane.showInputDialog(null, "Wybierz użytkownika",
        "Wybierz użytkownika", JOptionPane.QUESTION_MESSAGE, null, selectionValues, initialSelection);
    System.out.println(selection);
    String id_user2=psql.getElementOfTable("osoby", "id_osoba","nick=\'"+selection.toString()+"\'")[0].toString();
    if(id_user2!=null) {
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Enter a password:");
        JPasswordField pass = new JPasswordField(40);
        panel.add(label);
        panel.add(pass);
        String[] options = new String[]{"OK", "Cancel"};
        int option = JOptionPane.showOptionDialog(null, panel, "Podaj hasło",
                                 JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                                 null, options, options[1]);
        if(option == 0) // pressing OK button
        {
            char[] password = pass.getPassword();
            String pas=password.toString();
            String[] atr={"haslo"};
            Object[][] obj=null;
            try {
                obj=psql.getRows("osoby where id_osoba="+id_user2,atr);
                System.out.println(obj[0][0].toString());
                if(obj[0][0].toString().equals(new String(password))){
                    rekwizytyOsoby.setEnabled(true);
                    yourproducts.setEnabled(true);
                    this.setTitle("Książka kucharska - Profil "+psql.getElementOfTable("osoby", "nick","id_osoba=\'"+id_user2+"\'")[0].toString());
                    this.ustawDostep(true);
                    id_user=id_user2;
                    this.zalogujMenu.setEnabled(false);
                    this.wylogujMenu.setEnabled(true);
                }
            } catch (SQLException ex) {
                Logger.getLogger(NewApplication.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        
        
        
        
        
    }  
    }//GEN-LAST:event_zalogujMenuActionPerformed

    private void dodajRekwizytActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dodajRekwizytActionPerformed
        this.checkRekOpt.setSelected(false);
        this.dodajRekwizytDoPrzepisuIlosc.hide();
       this.dodajRekwizytDoPrzepisuButton.hide();
       //this.NazwaRekwizytu.show();
       this.ilosc.show();
       this.addRekwizytButton.show();
        addRekwizytFrame.show();
       //Object[] selectionValues=psql.getElementOfTable("rekwizyt", "nazwa","");
       String dane[]={"id_rekwizyt","nazwa"};
       Object[][] selectionValues = null;
        try {
            selectionValues = psql.getRows("rekwizyt",dane);
            //wybieranieRekwizytu.setActionCommand(selectionValues.toString());
        } catch (SQLException ex) {
            Logger.getLogger(NewApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
       
       ComboIT com=new ComboIT();
       com.addItems(wybieranieRekwizytu, selectionValues);
       //wybieranieRekwizytu.setModel(new DefaultComboBoxModel(selectionValues));
       wybieranieRekwizytu.setVisible(false);
       NazwaRekwizytu.setVisible(true);
    }//GEN-LAST:event_dodajRekwizytActionPerformed

    private void wybieranieRekwizytuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wybieranieRekwizytuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_wybieranieRekwizytuActionPerformed

    private void checkRekOptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkRekOptActionPerformed
        if(checkRekOpt.isSelected()){
            wybieranieRekwizytu.setVisible(true);
            NazwaRekwizytu.setVisible(false);
            System.out.println("JestemTutaj");
        }
        else{
            
            System.out.println("JestemWDrugim");
            wybieranieRekwizytu.setVisible(false);
            NazwaRekwizytu.setVisible(true);
        }
        
    }//GEN-LAST:event_checkRekOptActionPerformed

    private void NazwaRekwizytuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NazwaRekwizytuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NazwaRekwizytuActionPerformed

    private void addRekwizytButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addRekwizytButtonActionPerformed
        String info=new String();
        if(wybieranieRekwizytu.isVisible()){
            
            try {
                String atr[]={"wlasciciel","id_rekwizyt","ilosc"};
                ComboIT item=new ComboIT();
                item=(ComboIT)wybieranieRekwizytu.getSelectedItem();
                String val[]={id_user,item.getIndex(),ilosc.getText()};
                psql.dodajDane("rekwizyt_osoby", atr, val);
            } catch (Exception ex) {
                System.out.println(ex);

                info=info+"rekwizyt_osoby:"+ex.getMessage()+"\n";
            }
        }
        else{
            String atr[]={"nazwa"};
            String val[]={NazwaRekwizytu.getText()};
            String atr2[]={"wlasciciel","id_rekwizyt","ilosc"};
            try {
                psql.dodajDane("rekwizyt", atr, val);
            
            
            ComboIT item=new ComboIT();
            String dane[]={"id_rekwizyt","nazwa"};
            try {
                item.addItems(wybieranieRekwizytu,psql.getRows("rekwizyt", dane));
            } catch (SQLException ex) {
                Logger.getLogger(NewApplication.class.getName()).log(Level.SEVERE, null, ex);
            }
            } catch (Exception ex) {
                System.out.println(ex);
                info=info+"rekwizyt: "+ex.getMessage()+"\n";
            }
            Object[] id_rek=null;
            String val2[]=null;
            try {
            id_rek=psql.getElementOfTable("rekwizyt","id_rekwizyt","nazwa=\'"+NazwaRekwizytu.getText()+"\'");
            String val3[]={id_user,id_rek[0].toString(),ilosc.getText()};
            val2=val3;
            }catch(Exception ex){System.out.println("Trafilem");}
            try {
                psql.dodajDane("rekwizyt_osoby", atr2, val2);
            }
            catch(NullPointerException ex){
                System.out.println(ex);
            } 
            catch (Exception ex) {
                System.out.println(ex);
                info=info+"rekwizyt_osoby:"+ex.getMessage()+"\n";
            }
            
        }
        //psql.dodajDane(id_user, atrybuty, wartosci);
        String dane[]={"nazwa","ilosc"};
        TableModel model = null;
        try {
            model = new SkladnikiJednostkaTableModel(psql.getRows("get_rekwizyty_jednej_osoby(\'"+id_user+"\')", dane),dane);
        } catch (SQLException ex) {
            Logger.getLogger(NewApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.wyswietlTwojeRekwizytyTable.setModel(model);
        this.wyswietlTwojeRekwizytyTable.setSelectionModel(new ClassListSelectionModel());
        this.wyswietlTwojeRekwizytyTable.repaint();
        if(!info.isEmpty()){
                JOptionPane.showMessageDialog(null, info,"Informacja",
                        JOptionPane.INFORMATION_MESSAGE);
                
        }
    }//GEN-LAST:event_addRekwizytButtonActionPerformed

    private void dodajProduktActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dodajProduktActionPerformed
        
        this.addProductOsIlosc.show();
        this.podajDateWaznosciLabel.show();
        this.addProductOsDataWaznosci.show();
        this.addProduktButton1.show();
        this.dodajPrzepisdodajSkladnikIlosc.hide();
        this.dodajPrzepisDodajSkladnikButton.hide();
        String dane[]={"id_produkt","jednostka_miary","nazwa_jedn","nazwa_prod","liczba_kcal_w_1_jednostce","sugerowana_cena_za_1_jednostke"};
        TableModel model = null;
        try {
            model = new SkladnikiJednostkaTableModel(psql.getRows("pelny_produkt_jednostka", dane),dane);
        } catch (SQLException ex) {
            Logger.getLogger(NewApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
        jTable1.setModel(model);
        jTable1.setSelectionModel(new ClassListSelectionModel());
        jTable1.getColumnModel().getColumn(0).setMinWidth(0);
        jTable1.getColumnModel().getColumn(0).setMaxWidth(0);
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(0);
        jTable1.getColumnModel().getColumn(1).setMinWidth(0);
        jTable1.getColumnModel().getColumn(1).setMaxWidth(0);
        jTable1.getColumnModel().getColumn(1).setPreferredWidth(0);
        jTable1.repaint();
        addProductOsAddPrzepisSkladnikFrame.show();
       
    }//GEN-LAST:event_dodajProduktActionPerformed

    private void dodawanieProduktuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dodawanieProduktuActionPerformed
       String info=new String("");
        String input = JOptionPane.showInputDialog(null, "Podaj nazwę nowego produktu:", "Dodawanie nowego produktu",
        JOptionPane.INFORMATION_MESSAGE);
       if(true){
        
           try {
               String[] dane={input};
               String[] atrubut={"nazwa_prod"};
               psql.dodajDane("produkt_spozywczy",atrubut,dane);
           } catch (Exception ex) {
               System.out.println(ex);
               info=info+"produkt_spozywczy: "+ex.getMessage()+"\n";
           }
       }
       else{
           System.out.println("String pusty. Nie dodano produktu");
           info=info+"Puste pole. Nie dodano.";
       }
       String dane[]={"id_produkt","nazwa_prod"};
       refreshJComboBox(wybieranieProduktu2,"produkt_spozywczy",dane);
       if(!info.isEmpty()){
                JOptionPane.showMessageDialog(null, info,"Informacja",
                        JOptionPane.INFORMATION_MESSAGE);
                
        }
    }//GEN-LAST:event_dodawanieProduktuActionPerformed

    private void dodawanieJednostkiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dodawanieJednostkiActionPerformed
        String info=new String();
        String input = JOptionPane.showInputDialog(null, "Podaj nazwę nowej jednostki:", "Dodawanie nowej jednostki",
        JOptionPane.INFORMATION_MESSAGE);
       if(!true){
           try {
               String[] dane={input};
               String[] atrubut={"nazwa_jedn"};
               psql.dodajDane("jednostki_miary",atrubut,dane);
           } catch (Exception ex) {
               System.out.println(ex);
               info=info+"produkt_spozywczy: "+ex.getMessage()+"\n";
           }
       }
       else{
           System.out.println("String pusty. Nie dodano jednostki");
           info=info+"Puste pole. Nie dodano.";
       }
       String dane[]={"id_jednsotki","nazwa_jedn"};
        refreshJComboBox(wybieranieJednostki,"jednostki_miary",dane);
        if(!info.isEmpty()){
                JOptionPane.showMessageDialog(null, info,"Informacja",
                        JOptionPane.INFORMATION_MESSAGE);
                
        }
    }//GEN-LAST:event_dodawanieJednostkiActionPerformed

    private void dodawanieProduktuJedDoBazyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dodawanieProduktuJedDoBazyActionPerformed
        String info=new String("");
        String atr[]={"id_produkt","jednostka_miary","sugerowana_cena_za_1_jednostke","liczba_kcal_w_1_jednostce"};
        ComboIT item=new ComboIT();
        ComboIT item2=new ComboIT();
        item2=(ComboIT)wybieranieJednostki.getSelectedItem();
        item=(ComboIT)wybieranieProduktu2.getSelectedItem();
        String val[]={item.getIndex(),item2.getIndex(),sugerowanaCena.getText(),liczbaKalorii.getText()};
        try {
            psql.dodajDane("produkty_spozywcze_jednostka", atr, val);
        } catch (Exception ex) {
            System.out.println(ex);
            info=info+"produkty_spozywcze_jednostka: "+ex.getMessage()+"\n";
        }
        String dane[]={"id_produkt","jednostka_miary","nazwa_jedn","nazwa_prod","liczba_kcal_w_1_jednostce","sugerowana_cena_za_1_jednostke"};
        TableModel model = null;
        try {
            model = new SkladnikiJednostkaTableModel(psql.getRows("pelny_produkt_jednostka", dane),dane);
        } catch (SQLException ex) {
            Logger.getLogger(NewApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
        jTable1.setModel(model);
        jTable1.setSelectionModel(new ClassListSelectionModel());
        jTable1.getColumnModel().getColumn(0).setMinWidth(0);
        jTable1.getColumnModel().getColumn(0).setMaxWidth(0);
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(0);
        jTable1.getColumnModel().getColumn(1).setMinWidth(0);
        jTable1.getColumnModel().getColumn(1).setMaxWidth(0);
        jTable1.getColumnModel().getColumn(1).setPreferredWidth(0);
        jTable1.repaint();
        if(!info.isEmpty()){
                JOptionPane.showMessageDialog(null, info,"Informacja",
                        JOptionPane.INFORMATION_MESSAGE);
                
        }
    }//GEN-LAST:event_dodawanieProduktuJedDoBazyActionPerformed

    private void dodawanieProduktJednostkaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dodawanieProduktJednostkaActionPerformed
        String dane[]={"id_produkt","nazwa_prod"};
        String dane2[]={"id_jednsotki","nazwa_jedn"};
        refreshJComboBox(wybieranieProduktu2,"produkt_spozywczy",dane);
        refreshJComboBox(wybieranieJednostki,"jednostki_miary",dane2);
        addProductJednostkaFrame.show();
    }//GEN-LAST:event_dodawanieProduktJednostkaActionPerformed

    private void addProduktButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addProduktButton1ActionPerformed
        String info=new String("");
        if(this.jTable1.getSelectedRowCount()==1){
            String atr[]={"ilosc","wlasciciel","id_produkt","id_jednostka","termin_waznosci"};
            String val[]={addProductOsIlosc.getText(),
                id_user,
                jTable1.getModel().getValueAt(jTable1.getSelectedRow(),0).toString(),
                jTable1.getModel().getValueAt(jTable1.getSelectedRow(),1).toString(),
                this.addProductOsDataWaznosci.getText()};
            try {
                psql.dodajDane("przedmiot_osoby", atr, val);
            } catch (Exception ex) {
                System.out.println(ex);
                info=info+"przedmiot_osoby: "+ex.getMessage()+"\n";
            }
            String dane[]={"wlasciciel", "nazwa_prod", "ilosc","nazwa_jedn", "sugerowana_cena_za_1_jednostke","liczba_kcal_w_1_jednostce","termin_waznosci"};
            TableModel model = null;
            try {
                model = new SkladnikiJednostkaTableModel(psql.getRows("get_przedmioty_jednej_osoby(\'"+id_user+"\')", dane),dane);
            } catch (SQLException ex) {
                Logger.getLogger(NewApplication.class.getName()).log(Level.SEVERE, null, ex);
            }
            this.wyswietlTwojeProduktyTable.setModel(model);
            this.wyswietlTwojeProduktyTable.setSelectionModel(new ClassListSelectionModel());
            wyswietlTwojeProduktyTable.getColumnModel().getColumn(0).setMinWidth(0);
            wyswietlTwojeProduktyTable.getColumnModel().getColumn(0).setMaxWidth(0);
            wyswietlTwojeProduktyTable.getColumnModel().getColumn(0).setPreferredWidth(0);
            this.wyswietlTwojeProduktyTable.repaint();
            
        }
        else{
            info=info+"Nieodpowiednia liczba zaznaczonych wierszy. "
                    + "Nie dodano rekordu do bazy danych\n";
        }
        if(!info.isEmpty()){
                JOptionPane.showMessageDialog(null, info,"Informacja",
                        JOptionPane.INFORMATION_MESSAGE);
                
        }
        
    }//GEN-LAST:event_addProduktButton1ActionPerformed

    private void addProductOsIloscActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addProductOsIloscActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_addProductOsIloscActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        addProductJednostkaFrame.dispose();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        addProductOsAddPrzepisSkladnikFrame.dispose();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void zamknijWyswietlTwojeRekwizytyFrameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zamknijWyswietlTwojeRekwizytyFrameActionPerformed
        this.wyswietlTwojeRekwizytyFrame.dispose();
    }//GEN-LAST:event_zamknijWyswietlTwojeRekwizytyFrameActionPerformed

    private void wyswietlTwojeRekwizytyOpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wyswietlTwojeRekwizytyOpenActionPerformed
        this.wyswietlTwojeRekwizytyFrame.show();
        String dane[]={"nazwa","ilosc"};
        TableModel model = null;
        try {
            model = new SkladnikiJednostkaTableModel(psql.getRows("get_rekwizyty_jednej_osoby(\'"+id_user+"\')", dane),dane);
        } catch (SQLException ex) {
            Logger.getLogger(NewApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.wyswietlTwojeRekwizytyTable.setModel(model);
        this.wyswietlTwojeRekwizytyTable.setSelectionModel(new ClassListSelectionModel());
        this.wyswietlTwojeRekwizytyTable.repaint();
    }//GEN-LAST:event_wyswietlTwojeRekwizytyOpenActionPerformed

    private void addProductOsDataWaznosciActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addProductOsDataWaznosciActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_addProductOsDataWaznosciActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        this.wyswietlTwojeProduktyFrame.dispose();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void wyswietlTwojeProduktyOpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wyswietlTwojeProduktyOpenActionPerformed
        this.wyswietlTwojeProduktyFrame.show();
        String dane[]={"wlasciciel", "nazwa_prod", "ilosc","nazwa_jedn", "sugerowana_cena_za_1_jednostke","liczba_kcal_w_1_jednostce","termin_waznosci"};
        TableModel model = null;
        try {
            model = new SkladnikiJednostkaTableModel(psql.getRows("get_przedmioty_jednej_osoby(\'"+id_user+"\')", dane),dane);
        } catch (SQLException ex) {
            Logger.getLogger(NewApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.wyswietlTwojeProduktyTable.setModel(model);
        this.wyswietlTwojeProduktyTable.setSelectionModel(new ClassListSelectionModel());
        wyswietlTwojeProduktyTable.getColumnModel().getColumn(0).setMinWidth(0);
        wyswietlTwojeProduktyTable.getColumnModel().getColumn(0).setMaxWidth(0);
        wyswietlTwojeProduktyTable.getColumnModel().getColumn(0).setPreferredWidth(0);
        this.wyswietlTwojeProduktyTable.repaint();
    }//GEN-LAST:event_wyswietlTwojeProduktyOpenActionPerformed

    private void zamknijDodajPrzepisFrameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zamknijDodajPrzepisFrameActionPerformed
        this.dodajPrzepisFrame.dispose();
    }//GEN-LAST:event_zamknijDodajPrzepisFrameActionPerformed

    private void dodajPrzepisOpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dodajPrzepisOpenActionPerformed
        //!!!!
        
        this.dodajPrzepisButton.setVisible(true);
        this.dodajPrzepisFrameClear();
        this.dodajPrzepisFrameDisableOrEnable(true);
        this.dodajPrzepisFrame.show();
    }//GEN-LAST:event_dodajPrzepisOpenActionPerformed

    private void dodajSkladnikiDoPrzepisuButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dodajSkladnikiDoPrzepisuButtonActionPerformed
        this.addProductOsIlosc.hide();
        this.podajDateWaznosciLabel.hide();
        this.addProductOsDataWaznosci.hide();
        this.addProduktButton1.hide();
        this.dodajPrzepisdodajSkladnikIlosc.show();
        this.dodajPrzepisDodajSkladnikButton.show();
        this.addProductOsAddPrzepisSkladnikFrame.show();
    }//GEN-LAST:event_dodajSkladnikiDoPrzepisuButtonActionPerformed

    private void dodajRekwizytyDoPrzepisuButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dodajRekwizytyDoPrzepisuButtonActionPerformed
        this.checkRekOpt.setSelected(false);
        if(this.checkRekOpt.isSelected()){
            this.wybieranieRekwizytu.show();
            this.NazwaRekwizytu.hide();
            this.nazwaRekwizytuLabel.hide();
        }
        else{
            this.wybieranieRekwizytu.hide();
            this.NazwaRekwizytu.show();
            this.nazwaRekwizytuLabel.show();
        }
        this.dodajRekwizytDoPrzepisuIlosc.show();
       this.dodajRekwizytDoPrzepisuButton.show();
       //this.NazwaRekwizytu.hide();
       this.ilosc.hide();
       this.addRekwizytButton.hide();
        this.addRekwizytFrame.show();
        String data[]={"id_rekwizyt","nazwa"};
            refreshJComboBox(wybieranieRekwizytu,"rekwizyt",data);
    }//GEN-LAST:event_dodajRekwizytyDoPrzepisuButtonActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        this.addRekwizytFrame.dispose();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void dodajRekwizytDoPrzepisuButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dodajRekwizytDoPrzepisuButtonActionPerformed
        String info=new String();
        SkladnikiJednostkaTableModel mod= (SkladnikiJednostkaTableModel)this.dodajPrzepisDodajRekwizytTable.getModel();;
        if(wybieranieRekwizytu.isVisible()){
            try {
                ComboIT item=new ComboIT();
                item=(ComboIT)wybieranieRekwizytu.getSelectedItem();
                Object dan[]={item.getIndex(),item.toString(),this.dodajRekwizytDoPrzepisuIlosc.getText()};
                if(!mod.isInColumn(0,dan[0])){
                    mod.addRow(dan);
                mod.fireTableDataChanged();
                 this.dodajPrzepisDodajRekwizytTable.setModel(mod);
                this.dodajPrzepisDodajRekwizytTable.repaint();
                info=info+"Dodano do przechowalni zbioru rekwizytów.";
                }
                else{
                    info=info+"W przechowalni zbioru rekwizytów juz jest taka nazwa rekwizytu. Zmień dane.\n";
                }
            } catch (Exception ex) {
                System.out.println(ex);
                info=info+"Nie dodano do przechowalni zbioru rekwizytów. Zmień dane\n";
            }
        }
        else{
            String atr[]={"nazwa"};
            String val[]={NazwaRekwizytu.getText()};
            try {
                psql.dodajDane("rekwizyt", atr, val);
            } catch (Exception ex) {
                System.out.println(ex);
                info=info+" Rekwizyt: "+ex.getMessage()+"\n";
            }
            String atr2[]={"wlasciciel","id_rekwizyt","ilosc"};
            ComboIT item=new ComboIT();
            String dane[]={"id_rekwizyt","nazwa"};
            try {
                item.addItems(wybieranieRekwizytu,psql.getRows("rekwizyt", dane));
            } catch (SQLException ex) {
                Logger.getLogger(NewApplication.class.getName()).log(Level.SEVERE, null, ex);
            }
            String danee[]={"id_rekwizyt","nazwa"};
            Object[][] id_rek=null;
            try {
                id_rek = psql.getRows("rekwizyt where nazwa=\'"+NazwaRekwizytu.getText()+"\'",danee);
            } catch (SQLException ex) {
                Logger.getLogger(NewApplication.class.getName()).log(Level.SEVERE, null, ex);
            }
            //String val2[]={id_user,id_rek[0].toString(),ilosc.getText()};            
            try {
                Object dan[]={id_rek[0][0],id_rek[0][1],this.dodajRekwizytDoPrzepisuIlosc.getText()};
                if(!mod.isInColumn(0,dan[0])){
                    mod.addRow(dan);
                    mod.fireTableDataChanged();
                     this.dodajPrzepisDodajRekwizytTable.setModel(mod);
                     this.dodajPrzepisDodajRekwizytTable.repaint();
                     info=info+"Dodano do zbioru rekwizytów.\n";
                }
                else{
                    info=info+"Nie dodano do przechowalni zbioru rekwizytów. Zmień dane\n";
                }
                
            } catch (Exception ex) {
                System.out.println(ex);
                info=info+"Nie dodano do przechowalni zbioru rekwizytów. Zmień dane\n";
            }
            String data[]={"id_rekwizyt","nazwa"};
            refreshJComboBox(wybieranieRekwizytu,"rekwizyt",data);
            
        }
        if(!info.isEmpty()){
                JOptionPane.showMessageDialog(null, info,"Informacja",
                        JOptionPane.INFORMATION_MESSAGE);
                
        }
    }//GEN-LAST:event_dodajRekwizytDoPrzepisuButtonActionPerformed

    private void dodajPrzepisDodajSkladnikButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dodajPrzepisDodajSkladnikButtonActionPerformed
        String info=new String("");
        if(this.jTable1.getSelectedRowCount()==1){
            SkladnikiJednostkaTableModel mod= (SkladnikiJednostkaTableModel)this.dodajPrzepisdodajSkladnikiTable.getModel();
            Object dan[]={0,
                this.jTable1.getValueAt(this.jTable1.getSelectedRow(),0),
                this.jTable1.getValueAt(this.jTable1.getSelectedRow(),1),
                this.jTable1.getValueAt(this.jTable1.getSelectedRow(),3),
                this.dodajPrzepisdodajSkladnikIlosc.getText(),
                this.jTable1.getValueAt(this.jTable1.getSelectedRow(),2),
                this.jTable1.getValueAt(this.jTable1.getSelectedRow(),4),
                this.jTable1.getValueAt(this.jTable1.getSelectedRow(),5)
                };
                    if(mod.isInColumn(1, this.jTable1.getValueAt(this.jTable1.getSelectedRow(),1)) &&
                            mod.isInColumn(2, this.jTable1.getValueAt(this.jTable1.getSelectedRow(),2))){
                        info=info+"Mamy juz pozycję o takiej nazwie składnika i nazwie jednostki. "
                    + "Nie dodano danych do przechowalni składników przepisu\n";
                    }
                    else{
                        mod.addRow(dan);
                        mod.fireTableDataChanged();
                        this.dodajPrzepisdodajSkladnikiTable.setModel(mod);
                        this.dodajPrzepisdodajSkladnikiTable.repaint();
                        info=info+"Dodano dane do przechowalni składników przepisu\n";
                    }
                    
        }
        else{
            info=info+"Nieodpowiednia liczba zaznaczonych wierszy. "
                    + "Nie dodano danych do przechowalni składników przepisu\n";
        }
        if(!info.isEmpty()){
                JOptionPane.showMessageDialog(null, info,"Informacja",
                        JOptionPane.INFORMATION_MESSAGE);
                
        }
    }//GEN-LAST:event_dodajPrzepisDodajSkladnikButtonActionPerformed

    private void dodajPrzepisButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dodajPrzepisButtonActionPerformed
        String info = new String();
        try {
            //najpierw dodajemu przepis
            String atrybuty[]={nazwaPrzepisu.getText(),this.opisWykonania.getText(),czasWykonania.getText(), this.id_user,
                this.liczbaPorcji.getText(),
                this.trudnoscWykonania.getText()};
            String id_przepisu=null;
            try{
                id_przepisu=psql.dodajDaneZfunkcji("przepisy_kulinarne_wstaw_function",atrybuty);
                Integer.parseInt(id_przepisu);
                throw new Exception("Dodano rekord do bazy danych");
            }
            catch(Exception ex){
                info=info+"przepisy_kulinarne: "+ex.getMessage()+"\n";
            }
            for(int i=0;i<this.dodajPrzepisdodajSkladnikiTable.getModel().getRowCount();i++){
                String[] atr={"id_produkt","id_jednostka","id_przepis","ilosc"};
                String[] values={
                this.dodajPrzepisdodajSkladnikiTable.getModel().getValueAt(i, 1).toString(),
                this.dodajPrzepisdodajSkladnikiTable.getModel().getValueAt(i, 2).toString(),
                id_przepisu,
                this.dodajPrzepisdodajSkladnikiTable.getModel().getValueAt(i, 4).toString()
                };
                System.out.println("id_przepisu: "+ id_przepisu);
                try{
                    psql.dodajDane("skladniki_przepisu", atr, values);
                }catch(Exception ex){
                    System.out.println(ex.getMessage());
                    info=info+"skladniki_przepisu: "+ex.getMessage()+"nr."+i+"\n";
                }
                
                
            }
            for(int i=0;i<this.dodajPrzepisDodajRekwizytTable.getModel().getRowCount();i++){
                String[] atr={"id_przepis","id_rekwizyt","ilosc"};
                String[] values={
                id_przepisu,
                this.dodajPrzepisDodajRekwizytTable.getModel().getValueAt(i, 0).toString(),
                this.dodajPrzepisDodajRekwizytTable.getModel().getValueAt(i, 2).toString()
                };
                try{
                    psql.dodajDane("zbiory_rekwizytow", atr, values);
                }catch(Exception ex){
                    refreshWyswietlPrzepisyTable("przepis_autor");
                    System.out.println(ex.getMessage());
                    info=info+"zbiory_rekwizytow: "+ex.getMessage()+"nr."+i+"\n";
                }
            }
            // funkcja składowana :-) która zwraca serial id
            //najpierw dodajemy do tabel skladniki przepisu i rekwizyty przepisu
            //to_char timestamp
        } catch (Exception ex) {
            Logger.getLogger(NewApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(!info.isEmpty()){
                JOptionPane.showMessageDialog(null, info,"Informacja",
                        JOptionPane.INFORMATION_MESSAGE);
                
        }
        refreshRankingTable();
        refreshRankingTablePrawdziwy();
    }//GEN-LAST:event_dodajPrzepisButtonActionPerformed

    private void trudnoscWykonaniaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_trudnoscWykonaniaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_trudnoscWykonaniaActionPerformed

    private void liczbaPorcjiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_liczbaPorcjiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_liczbaPorcjiActionPerformed

    private void wyswietlPrzepisyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wyswietlPrzepisyActionPerformed
        this.wyswietlMojePrzepisyCheckButton.setSelected(false);
        this.wyswietlPrzepisyPokazUlubione.setSelected(false);
        this.wyswietlPrzepisyUsunPrzepisButton.setEnabled(false);
        this.wyswietlPrzepisyUsunPrzepisButton.setEnabled(false);
        refreshWyswietlPrzepisyTable2("przepis_autor");
        wyswietlPrzepisyFrame.show();
        
    }//GEN-LAST:event_wyswietlPrzepisyActionPerformed

    private void wyswietlPrzepisyZamknijActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wyswietlPrzepisyZamknijActionPerformed
        wyswietlPrzepisyFrame.hide();
    }//GEN-LAST:event_wyswietlPrzepisyZamknijActionPerformed

    private void wyswietlMojePrzepisyCheckButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wyswietlMojePrzepisyCheckButtonActionPerformed
        refreshWyswietlPrzepisyTable2("przepis_autor");
        if(this.wyswietlMojePrzepisyCheckButton.isSelected()){
            this.wyswietlPrzepisyUsunPrzepisButton.setEnabled(true);
            this.wyswietlPrzepisyUsunPrzepisButton.setEnabled(true);
        }
        else{
            this.wyswietlPrzepisyUsunPrzepisButton.setEnabled(false);
            this.wyswietlPrzepisyUsunPrzepisButton.setEnabled(false);
        }
    }//GEN-LAST:event_wyswietlMojePrzepisyCheckButtonActionPerformed

    private void wyswietlPrzepisyUsunPrzepisButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wyswietlPrzepisyUsunPrzepisButtonActionPerformed
    String info=new String();
        if(this.wyswietlPrzepisyTable.getSelectedRowCount()>0){
        try {
        psql.usunRekord("przepisy_kulinarne "
        + "where id_przepis="+this.wyswietlPrzepisyTable.getModel()
        .getValueAt(this.wyswietlPrzepisyTable.getSelectedRow(), 0));
        refreshWyswietlPrzepisyTable("przepis_autor");
        } catch (Exception ex) {
        System.out.println(ex.getMessage());
        info=info+"Przepis wraz z ewentualnymi składnikami przepisu oraz zbioru rekwizytów: "+ex.getMessage()+"\n";
        }
        }
        else{
            info=info+"Nieodpowiednia liczba zaznaczonych wierszy.\n"; 
        }
        this.refreshWyswietlPrzepisyTable("przepis_autor");
        if(!info.isEmpty()){
                JOptionPane.showMessageDialog(null, info,"Informacja",
                        JOptionPane.INFORMATION_MESSAGE);
                
        }
        refreshRankingTable();
        refreshRankingTablePrawdziwy();
    }//GEN-LAST:event_wyswietlPrzepisyUsunPrzepisButtonActionPerformed

    private void wyswietlPrzepisyobejrzyjPrzepisButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wyswietlPrzepisyobejrzyjPrzepisButtonActionPerformed
        String info=new String();
        if(this.wyswietlPrzepisyTable.getSelectedRowCount()==1){
           String zaznaczone_id=this.wyswietlPrzepisyTable.getModel()
                .getValueAt(this.wyswietlPrzepisyTable.getSelectedRow(), 0).toString();
           this.dodajPrzepisFrame.show();
           this.dodajPrzepisButton.setVisible(false);
           this.dodajPrzepisFrameClear();
           this.dodajPrzepisFrameDisableOrEnable(false);
           String[] dane={"nazwa","wykonanie","czas_wykonania","data_dodania","liczba_porcji","trudnosc_wykonania"};
           Object[][] selectionValues = null;
           try {
                selectionValues = psql.getRows("przepisy_kulinarne where id_przepis="+zaznaczone_id,dane);
                //wybieranieRekwizytu.setActionCommand(selectionValues.toString());
           } catch (SQLException ex) {
           //Logger.getLogger(NewApplication.class.getName()).log(Level.SEVERE, null, ex);
           }
            this.nazwaPrzepisu.setText(selectionValues[0][0].toString());
            this.opisWykonania.setText(selectionValues[0][1].toString());
            this.liczbaPorcji.setText(selectionValues[0][4].toString());
            this.trudnoscWykonania.setText(selectionValues[0][5].toString());
            this.czasWykonania.setText(selectionValues[0][2].toString());
            this.dataDodaniaField.setText(selectionValues[0][3].toString());
            //pelny_produkt_jednostka
            String[] dane2={
                "id_przepis",
                "id_produkt",
                "jednostka_miary",
                "nazwa_prod",
                "ilosc",
                "nazwa_jedn",
                "sugerowana_cena_za_1_jednostke",
                "liczba_kcal_w_1_jednostce"
            };
           selectionValues = null;
           try {
                selectionValues = psql.getRows("pelny_skladnik_przepisu where id_przepis="+zaznaczone_id,dane2);
           //wybieranieRekwizytu.setActionCommand(selectionValues.toString());
           } catch (SQLException ex) {
           //Logger.getLogger(NewApplication.class.getName()).log(Level.SEVERE, null, ex);
           }
           //this.dodajPrzepisdodajSkladnikiTable.setModel();
            SkladnikiJednostkaTableModel mod =new SkladnikiJednostkaTableModel(selectionValues,dane2);
            mod.fireTableDataChanged();
           this.dodajPrzepisdodajSkladnikiTable.setModel(mod);
           String[] dane3={
           "id_przepis",
           "nazwa",
           "ilosc"};
           selectionValues = null;
           try {
           selectionValues = psql.getRows("pelny_rekwizyt_przepisu where id_przepis="+zaznaczone_id,dane3);
           //wybieranieRekwizytu.setActionCommand(selectionValues.toString());
           } catch (SQLException ex) {
           //Logger.getLogger(NewApplication.class.getName()).log(Level.SEVERE, null, ex);
           }
           //this.dodajPrzepisdodajSkladnikiTable.setModel();
           mod =new SkladnikiJednostkaTableModel(selectionValues,dane3);
           mod.fireTableDataChanged();
           this.dodajPrzepisDodajRekwizytTable.setModel(mod);
        }
        else{
            info=info+"Nieodpowiednia liczba zaznaczonych wierszy. "
                    + "\n";
        }
        if(!info.isEmpty()){
                JOptionPane.showMessageDialog(null, info,"Informacja",
                        JOptionPane.INFORMATION_MESSAGE);
                
        }
        
    }//GEN-LAST:event_wyswietlPrzepisyobejrzyjPrzepisButtonActionPerformed

    private void wyswietlPrzepisyDodajUlubioneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wyswietlPrzepisyDodajUlubioneActionPerformed
        if(wyswietlPrzepisyTable.getSelectedRowCount()<1){
            JOptionPane.showMessageDialog(null, "Nie wybrałeś przepisu","Informacja",
                        JOptionPane.INFORMATION_MESSAGE);
        }
        else{
            String info=new String();
            if(wyswietlPrzepisyDodajUlubione.getText().equals("Like")){
                System.out.println("Mozna go dodac do ulubionych");
                String[] atrybuty={"id_osoba","id_przepis","priorytet"};
                Object[] selectionValues = { "1", "2", "3","4","5","6","7","8","9","10" };
                String initialSelection = "1";
                Object selection = JOptionPane.showInputDialog(null, "Wybierz priorytet",
        "Wybierz priorytet", JOptionPane.QUESTION_MESSAGE, null, selectionValues, initialSelection);
                if(selection!=null){
                    String[] wartosci={id_user,
                wyswietlPrzepisyTable.getModel().getValueAt(wyswietlPrzepisyTable.getSelectedRow(),0).toString(),
                "5"};
                try {
                    psql.dodajDane("ulubione",atrybuty,wartosci);
                } catch (Exception ex) {
                    info=info+"Ulubione"+ex.getMessage();
                    wyswietlPrzepisyDodajUlubione.setText("Dislike");
                }
                }
                else{
                    info=info+"Nie wybrales priorytetu\n";
                }
                
                
                
            }
            else{
                System.out.println("Nie dodajemy go do ulubionych");
                String[] atrybuty={"id_osoba","id_przepis","priorytet"};
                String[] wartosci={id_user,
                wyswietlPrzepisyTable.getModel().getValueAt(wyswietlPrzepisyTable.getSelectedRow(),0).toString(),
                "5"};
                try {
                    psql.usunRekord("ulubione where "+atrybuty[0]+"="+id_user+
                            " and "+atrybuty[1]+"="+wyswietlPrzepisyTable.getModel().getValueAt(wyswietlPrzepisyTable.getSelectedRow(),0).toString());
                } catch (Exception ex) {
                    info=info+"Ulubione"+ex.getMessage();
                    wyswietlPrzepisyDodajUlubione.setText("Like");
                }
            }
            if(!info.isEmpty()){
                JOptionPane.showMessageDialog(null, info,"Informacja",
                        JOptionPane.INFORMATION_MESSAGE);
                
            }
            refreshWyswietlPrzepisyTable2("przepis_autor");
            refreshRankingTable();
            refreshRankingTablePrawdziwy();
        }
    }//GEN-LAST:event_wyswietlPrzepisyDodajUlubioneActionPerformed

    private void wyswietlPrzepisyPokazUlubioneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wyswietlPrzepisyPokazUlubioneActionPerformed
        refreshWyswietlPrzepisyTable2("przepis_autor");
        if(this.wyswietlMojePrzepisyCheckButton.isSelected()){
            this.wyswietlPrzepisyUsunPrzepisButton.setEnabled(true);
            this.wyswietlPrzepisyUsunPrzepisButton.setEnabled(true);
        }
        else{
            this.wyswietlPrzepisyUsunPrzepisButton.setEnabled(false);
            this.wyswietlPrzepisyUsunPrzepisButton.setEnabled(false);
        }
    }//GEN-LAST:event_wyswietlPrzepisyPokazUlubioneActionPerformed

    private void wyswietlPrzepisyCenaKalorieActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wyswietlPrzepisyCenaKalorieActionPerformed
        if(wyswietlPrzepisyTable.getSelectedRowCount()<1){
            JOptionPane.showMessageDialog(null, "Nie wybrałeś przepisu","Informacja",
                        JOptionPane.INFORMATION_MESSAGE);
        }
            else{
                String info=new String();
            info="Siemka";
            String[] atrybuty={"answer"};
            Object[][] val=null;
            Object[][] val2=null;
            try {
                 val=psql.getRows("licz_cene_przepisu("+this.wyswietlPrzepisyTable
                        .getModel().getValueAt(this.wyswietlPrzepisyTable.getSelectedRow(),0)+") as answer", atrybuty);
            } catch (SQLException ex) {
                Logger.getLogger(NewApplication.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
               val2=psql.getRows("licz_kalorie_przepisu("+this.wyswietlPrzepisyTable
                        .getModel().getValueAt(this.wyswietlPrzepisyTable.getSelectedRow(),0)+") as answer", atrybuty);
            } catch (SQLException ex) {
                Logger.getLogger(NewApplication.class.getName()).log(Level.SEVERE, null, ex);
            }
            if(!info.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Cena jednej porcji[zł]: "+val[0][0].toString()
                            +"\n"+"Liczba kalorii w jednej porcji[kcal]: "+val2[0][0].toString(),"Informacja",
                            JOptionPane.INFORMATION_MESSAGE);

                }
        }
        
    }//GEN-LAST:event_wyswietlPrzepisyCenaKalorieActionPerformed

    private void rankingWyswietlActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rankingWyswietlActionPerformed
        this.rankingPrawdziwy.setVisible(true);
        refreshRankingTablePrawdziwy();
    }//GEN-LAST:event_rankingWyswietlActionPerformed

    private void rankingZamknijActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rankingZamknijActionPerformed
        this.aktywnoscPokazFrame.setVisible(false);
    }//GEN-LAST:event_rankingZamknijActionPerformed

    private void aktywnoscWyswietlActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aktywnoscWyswietlActionPerformed
        refreshRankingTable();
        refreshRankingTablePrawdziwy();
        this.aktywnoscPokazFrame.setVisible(true);
    }//GEN-LAST:event_aktywnoscWyswietlActionPerformed

    private void wylogujMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wylogujMenuActionPerformed
        rekwizytyOsoby.setEnabled(false);
                    yourproducts.setEnabled(false);
                    this.setTitle("Książka kucharska - Profil ogólny");
                    this.ustawDostep(false);
                    id_user=null;
                    this.zalogujMenu.setEnabled(true);
                    this.wylogujMenu.setEnabled(false);
    }//GEN-LAST:event_wylogujMenuActionPerformed

    private void rankingPrawdziwyZamknijActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rankingPrawdziwyZamknijActionPerformed
        this.rankingPrawdziwy.setVisible(false);
    }//GEN-LAST:event_rankingPrawdziwyZamknijActionPerformed
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(NewApplication.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NewApplication.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NewApplication.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NewApplication.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new NewApplication().setVisible(true);
            }
        });
    }
    private PostgreSQLJDBCDriver psql;
    private String id_user;
    private String imieUser;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddUser;
    private javax.swing.JTextField NazwaRekwizytu;
    private javax.swing.JTextField PodajImie;
    private javax.swing.JTextField PodajNazwisko;
    private javax.swing.JTextField PodajNick;
    private javax.swing.JFrame addProductJednostkaFrame;
    private javax.swing.JFrame addProductOsAddPrzepisSkladnikFrame;
    private javax.swing.JTextField addProductOsDataWaznosci;
    private javax.swing.JTextField addProductOsIlosc;
    private javax.swing.JButton addProduktButton1;
    private javax.swing.JButton addRekwizytButton;
    private javax.swing.JFrame addRekwizytFrame;
    private javax.swing.JLabel addUserJFramePodajImieLabel;
    private javax.swing.JLabel addUserJFramePodajNazwiskoLabel;
    private javax.swing.JLabel addUserJFramePodajNickLabel;
    private javax.swing.JFrame addtUserJFrame;
    private javax.swing.JFrame aktywnoscPokazFrame;
    private javax.swing.JMenuItem aktywnoscWyswietl;
    private javax.swing.JMenu aktywnosciMenu;
    private javax.swing.JCheckBox checkRekOpt;
    private javax.swing.JTextField czasWykonania;
    private javax.swing.JTextField dataDodaniaField;
    private javax.swing.JMenuItem dodajProdukt;
    private javax.swing.JButton dodajPrzepisButton;
    private javax.swing.JTable dodajPrzepisDodajRekwizytTable;
    private javax.swing.JButton dodajPrzepisDodajSkladnikButton;
    private javax.swing.JFrame dodajPrzepisFrame;
    private javax.swing.JMenuItem dodajPrzepisOpen;
    private javax.swing.JTextField dodajPrzepisdodajSkladnikIlosc;
    private javax.swing.JTable dodajPrzepisdodajSkladnikiTable;
    private javax.swing.JMenuItem dodajRekwizyt;
    private javax.swing.JButton dodajRekwizytDoPrzepisuButton;
    private javax.swing.JTextField dodajRekwizytDoPrzepisuIlosc;
    private javax.swing.JButton dodajRekwizytyDoPrzepisuButton;
    private javax.swing.JButton dodajSkladnikiDoPrzepisuButton;
    private javax.swing.JButton dodawanieJednostki;
    private javax.swing.JButton dodawanieProduktJednostka;
    private javax.swing.JButton dodawanieProduktu;
    private javax.swing.JButton dodawanieProduktuJedDoBazy;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JLabel ifZalogowanyLabel;
    private javax.swing.JTextField ilosc;
    private javax.swing.JLabel iloscRekwizytowLabel;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField liczbaKalorii;
    private javax.swing.JTextField liczbaPorcji;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JTextField nazwaPrzepisu;
    private javax.swing.JLabel nazwaRekwizytuLabel;
    private javax.swing.JMenuItem openMenuItem;
    private javax.swing.JTextPane opisWykonania;
    private javax.swing.JLabel podajDateWaznosciLabel;
    private javax.swing.JLabel priorytetLabel;
    private javax.swing.JMenu przepisykulinarneMenu;
    private javax.swing.JFrame rankingPrawdziwy;
    private javax.swing.JTable rankingPrawdziwyTable;
    private javax.swing.JButton rankingPrawdziwyZamknij;
    private javax.swing.JTable rankingTable;
    private javax.swing.JMenuItem rankingWyswietl;
    private javax.swing.JButton rankingZamknij;
    private javax.swing.JMenu rekwizytyOsoby;
    private javax.swing.JTextField sugerowanaCena;
    private javax.swing.JTextField trudnoscWykonania;
    private javax.swing.JComboBox wybieranieJednostki;
    private javax.swing.JComboBox wybieranieProduktu2;
    private javax.swing.JComboBox wybieranieRekwizytu;
    private javax.swing.JMenuItem wylogujMenu;
    private javax.swing.JCheckBox wyswietlMojePrzepisyCheckButton;
    private javax.swing.JMenuItem wyswietlPrzepisy;
    private javax.swing.JButton wyswietlPrzepisyCenaKalorie;
    private javax.swing.JButton wyswietlPrzepisyDodajUlubione;
    private javax.swing.JFrame wyswietlPrzepisyFrame;
    private javax.swing.JCheckBox wyswietlPrzepisyPokazUlubione;
    private javax.swing.JTable wyswietlPrzepisyTable;
    private javax.swing.JButton wyswietlPrzepisyUsunPrzepisButton;
    private javax.swing.JButton wyswietlPrzepisyZamknij;
    private javax.swing.JButton wyswietlPrzepisyobejrzyjPrzepisButton;
    private javax.swing.JFrame wyswietlTwojeProduktyFrame;
    private javax.swing.JMenuItem wyswietlTwojeProduktyOpen;
    private javax.swing.JTable wyswietlTwojeProduktyTable;
    private javax.swing.JFrame wyswietlTwojeRekwizytyFrame;
    private javax.swing.JMenuItem wyswietlTwojeRekwizytyOpen;
    private javax.swing.JTable wyswietlTwojeRekwizytyTable;
    private javax.swing.JMenu yourproducts;
    private javax.swing.JMenuItem zalogujMenu;
    private javax.swing.JButton zamknijDodajPrzepisFrame;
    private javax.swing.JButton zamknijWyswietlTwojeRekwizytyFrame;
    // End of variables declaration//GEN-END:variables

    private void refreshJComboBox(JComboBox box,String tabela,String[] dane){
       // String dane[]={"id_rekwizyt","nazwa"};
       Object[][] selectionValues = null;
        try {
            selectionValues = psql.getRows(tabela,dane);
            //wybieranieRekwizytu.setActionCommand(selectionValues.toString());
        } catch (SQLException ex) {
            //Logger.getLogger(NewApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
       
       ComboIT com=new ComboIT();
       com.addItems(box, selectionValues);
       //wybieranieRekwizytu.setModel(new DefaultComboBoxModel(selectionValues));
       //wybieranieRekwizytu.setVisible(false);
       box.repaint();
    } 
    private void refreshWyswietlPrzepisyTable(String tabelaWar){
        String arg=new String("");
        if(this.wyswietlMojePrzepisyCheckButton.isSelected())   arg+=" where "+tabelaWar+".id_osoba_dodajaca="+this.id_user;
        
        String dane[]={"id_przepis","nazwa","nick"};
        SkladnikiJednostkaTableModel model = null;
        try {
            model = new SkladnikiJednostkaTableModel(psql.getRows(tabelaWar+arg, dane),dane);
        } catch (SQLException ex) {
            Logger.getLogger(NewApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
        model.fireTableDataChanged();
        this.wyswietlPrzepisyTable.setModel(model);
        this.wyswietlPrzepisyTable.setSelectionModel(new ClassListSelectionModel());
        wyswietlPrzepisyTable.getColumnModel().getColumn(0).setMinWidth(0);
        wyswietlPrzepisyTable.getColumnModel().getColumn(0).setMaxWidth(0);
        wyswietlPrzepisyTable.getColumnModel().getColumn(0).setPreferredWidth(0);
        
    }
    private void refreshWyswietlPrzepisyTable2(String tabelaWar){
        String arg=new String("");
        if(this.wyswietlPrzepisyPokazUlubione.isSelected()) {
            if(wyswietlMojePrzepisyCheckButton.isSelected()){
            tabelaWar="przepis_autor_ulubione";
                arg=" where "+tabelaWar+".id_osoba_dodajaca="+this.id_user+" and "+tabelaWar+".kto_lubi="+this.id_user;
            //wyswietlMojePrzepisyCheckButton.setSelected(true);
            }
            else{
                tabelaWar="przepis_autor_ulubione";
            arg=" where "+tabelaWar+".kto_lubi="+this.id_user;
            //wyswietlMojePrzepisyCheckButton.setSelected(true);
            }
            
            
        }
        else{
            if(wyswietlMojePrzepisyCheckButton.isSelected()){
                tabelaWar="przepis_autor";
            arg=" where "+tabelaWar+".id_osoba_dodajaca="+this.id_user;
            //wyswietlMojePrzepisyCheckButton.setSelected(true);
            }
            else{
                tabelaWar="przepis_autor";
            arg="";
            //wyswietlMojePrzepisyCheckButton.setSelected(true);
            }
        }
        String dane[]={"id_przepis","nazwa","nick"};
        SkladnikiJednostkaTableModel model = null;
        try {
            model = new SkladnikiJednostkaTableModel(psql.getRows(tabelaWar+arg, dane),dane);
        } catch (SQLException ex) {
            Logger.getLogger(NewApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
        model.fireTableDataChanged();
        this.wyswietlPrzepisyTable.setModel(model);
        this.wyswietlPrzepisyTable.setSelectionModel(new ClassListSelectionModel());
        wyswietlPrzepisyTable.getColumnModel().getColumn(0).setMinWidth(0);
        wyswietlPrzepisyTable.getColumnModel().getColumn(0).setMaxWidth(0);
        wyswietlPrzepisyTable.getColumnModel().getColumn(0).setPreferredWidth(0);
        
    }
    /**
     * Pobiera dane z aktywnosc_osoba i odświeża tabelę rankingTable
     */
    private void refreshRankingTable(){
        String arg=new String("");
        String tabelaWar="aktywnosc_osoba";
        String dane[]={"id_aktywnosc","zdarzenie","nick"};
        SkladnikiJednostkaTableModel model = null;
        try {
            model = new SkladnikiJednostkaTableModel(psql.getRows(tabelaWar+arg, dane),dane);
        } catch (SQLException ex) {
            Logger.getLogger(NewApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
        model.fireTableDataChanged();
        this.rankingTable.setModel(model);
        this.rankingTable.setSelectionModel(new ClassListSelectionModel());
        rankingTable.getColumnModel().getColumn(0).setMinWidth(0);
        rankingTable.getColumnModel().getColumn(0).setMaxWidth(0);
        rankingTable.getColumnModel().getColumn(0).setPreferredWidth(0);
        this.rankingTable.repaint();
        
    }
    /**
     * Odświeża tabelę rankingPrawdziwyTable
     */
    private void refreshRankingTablePrawdziwy(){
        String arg=new String("");
        String tabelaWar="nick_liczba_przepisow_function()";
        String dane[]={"nick","liczba_przepisow"};
        SkladnikiJednostkaTableModel model = null;
        try {
            model = new SkladnikiJednostkaTableModel(psql.getRows(tabelaWar+arg, dane),dane);
        } catch (SQLException ex) {
            Logger.getLogger(NewApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
        model.fireTableDataChanged();
        this.rankingPrawdziwyTable.setModel(model);
        this.rankingPrawdziwyTable.setSelectionModel(new ClassListSelectionModel());
        this.rankingPrawdziwyTable.repaint();
        
    }
    /**
     * Czyści m.in pole formularza
     */
    public void dodajPrzepisFrameClear(){
        this.nazwaPrzepisu.setText("");
        this.opisWykonania.setText("");
        this.liczbaPorcji.setText("");
        this.trudnoscWykonania.setText("");
        this.czasWykonania.setText("");
        SkladnikiJednostkaTableModel dm = 
                (SkladnikiJednostkaTableModel) this.dodajPrzepisdodajSkladnikiTable.getModel();
            int rowCount = dodajPrzepisdodajSkladnikiTable.getRowCount();
            //Remove rows one by one from the end of the table
            for (int i = rowCount - 1; i >= 0; i--) {
                dm.removeRow(i);
                
            }
            dm.fireTableDataChanged();
        this.dodajPrzepisdodajSkladnikiTable.setModel(dm);
        //dodajPrzepisdodajSkladnikiTable.repaint();
        SkladnikiJednostkaTableModel dm2 = 
                (SkladnikiJednostkaTableModel) this.dodajPrzepisDodajRekwizytTable.getModel();
            int rowCount2 = this.dodajPrzepisDodajRekwizytTable.getRowCount();
            //Remove rows one by one from the end of the table
            for (int i = rowCount2 - 1; i >= 0; i--) {
                dm2.removeRow(i);
                
            }
            dm2.fireTableDataChanged();
        this.dodajPrzepisDodajRekwizytTable.setModel(dm2);
        //dodajPrzepisDodajRekwizytTable.repaint();
        
        
            
        
    }
    /**
     * Ustawia, czy pewne elementy mają byc dostepne, czy nie. Ustawia, czy pewne elementy mają byc widoczne, czy nie.
     * @param flag flaga
     */
    public void dodajPrzepisFrameDisableOrEnable(boolean flag){
        this.nazwaPrzepisu.setEnabled(flag);
        this.opisWykonania.setEnabled(flag);
        this.liczbaPorcji.setEnabled(flag);
        this.trudnoscWykonania.setEnabled(flag);
        this.czasWykonania.setEnabled(flag);
        this.dodajSkladnikiDoPrzepisuButton.setVisible(flag);
        this.dodajRekwizytyDoPrzepisuButton.setVisible(flag);
    }
    /**
     * Ustawia, czy pewne elementy mają byc dostepne, czy nie. Ustawia napis na "Zalogowany" lub "Niezalogowany"
     * @param flaga flaga 
     */
    public void ustawDostep(boolean flaga){
        this.rekwizytyOsoby.setEnabled(flaga);
        this.yourproducts.setEnabled(flaga);
        this.dodajPrzepisOpen.setEnabled(flaga);
        this.aktywnoscWyswietl.setEnabled(flaga);
        this.wyswietlMojePrzepisyCheckButton.setEnabled(flaga);
        this.wyswietlPrzepisyPokazUlubione.setEnabled(flaga);
        this.wyswietlPrzepisyDodajUlubione.setEnabled(flaga);
        if(flaga){
            this.ifZalogowanyLabel.setText("Zalogowany");
        }
        else{
            this.ifZalogowanyLabel.setText("Niezalogowany");
        }
        
        
    }
} 
