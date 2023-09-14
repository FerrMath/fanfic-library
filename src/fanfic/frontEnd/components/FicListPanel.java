/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package fanfic.frontEnd.components;

import fanfic.backEnd.logic.Fanfic;
import fanfic.frontEnd.MainView;
import java.awt.CardLayout;
import javax.swing.JPanel;

/**
 *
 * @author ma_fe
 */
public class FicListPanel extends javax.swing.JPanel {
    
    private MainView mf;
    
    public FicListPanel(MainView mainFrame) {
        this.mf = mainFrame;
        initComponents();
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        lista = new javax.swing.JList<>();

        lista.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "FIC 1", "FIC 2", "Fic 3", "FIC 1", "FIC 2", "Fic 3", "FIC 1", "FIC 2", "Fic 3" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
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
            Fanfic fic = new Fanfic("Isso aqui"); // Temp
            mf.showFicDeatails(fic);
        }
    }//GEN-LAST:event_listaMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList<String> lista;
    // End of variables declaration//GEN-END:variables
}
