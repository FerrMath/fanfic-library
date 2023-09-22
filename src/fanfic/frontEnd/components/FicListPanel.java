/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package fanfic.frontEnd.components;

import fanfic.backEnd.db.DbConnection;
import fanfic.backEnd.logic.Fanfiction;
import fanfic.backEnd.logic.Tag;
import fanfic.frontEnd.MainView;
import java.awt.CardLayout;
import java.util.ArrayList;
import javax.swing.JList;
import javax.swing.JPanel;

/**
 *
 * @author ma_fe
 */
public class FicListPanel extends javax.swing.JPanel {
    
    private MainView mf;
    private ArrayList<Fanfiction> fics;
    private DbConnection db;
    
    public FicListPanel(MainView mainFrame) {
        this.mf = mainFrame;
        this.db = new DbConnection();
        this.fics = db.getFics();
        initComponents();
    }
    
    public JList getLista(){
        return this.lista;
    }
    
    private String[] getNames(){
        ArrayList<String> li = new ArrayList<>();
        for (Fanfiction fic: fics){
            li.add(fic.getName());
        }
        
        String[] la = li.toArray(new String[0]);
        return la;
        
    }
    
    public void updateFic(String title, Tag[] tags){
        Fanfiction f = fics.get(lista.getSelectedIndex());
        f.setName(title);
        f.setTags(tags);
        db.updateFic(f);
        updateList();
    }
    
    public void updateList(){
        lista.setModel(
            new javax.swing.AbstractListModel<String>() {
                String[] strings = getNames();
                @Override
                public int getSize() {return strings.length;}
                @Override
                public String getElementAt(int i) {return strings[i];}
            }
        );
    }
    
    public void addFicToList(String title, String[] tags){
        db.addFicEntry(title, tags); // TEMP
        this.fics = db.getFics();
        updateList();
    }
    
    public void removeFic() {
        int row = lista.getSelectedIndex();
        if (row > -1){
            Fanfiction f = fics.get(row);
            db.removeFic(f);
            fics.remove(row);
            updateList();
        }
    }
    
    public Fanfiction getSelectedFic() {
        int index = lista.getSelectedIndex();
        if (index > -1){
            return this.fics.get(index);
        } else {
            return null;
        }
    }
    
    public void close() {
        this.db.finishConnection();
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        lista = new javax.swing.JList<>();

        lista.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = getNames();
            public int getSize() {return strings.length;}
            public String getElementAt(int i) {return strings[i];}
        }

    );
    lista.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
    lista.setFixedCellHeight(48);
    lista.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            listaMouseClicked(evt);
        }
    });
    jScrollPane1.setViewportView(lista);

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
    );
    layout.setVerticalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
    );
    }// </editor-fold>//GEN-END:initComponents

    private void listaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listaMouseClicked
        // Pegando double click na lista
        if (evt.getClickCount() == 2){
            int row = lista.getSelectedIndex();
            JPanel parentPanel = (JPanel) getParent();
            CardLayout cl = (CardLayout) parentPanel.getLayout();
            
            // TODO pegar as informações da fic selecionada
            Fanfiction fic = fics.get(row);
            mf.showFicDeatails(fic);
        }
    }//GEN-LAST:event_listaMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList<String> lista;
    // End of variables declaration//GEN-END:variables
}
