package fanfic.backEnd.db;

import fanfic.backEnd.logic.Fanfiction;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class DbConnection {
    private Connection conn;
    private Statement stt;
    private PreparedStatement pstt;

    
    // TODO create logic to add new fics;
    public DbConnection() {
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/fic_library","USER","PASSWORD"); // CHANGE TO YOUR USER AND PASSWORD
            this.stt = conn.createStatement();
            
        // TODO pegar id da fic escrita na db, passar para a classe, e da lista de Fanfictions posso acessar o id pra pegar os dados na db depois;
        } catch (ClassNotFoundException ex){
            System.out.println("Diver não disponivel");
        } catch (SQLException ex){
            System.out.println("Sintaxe de comando invalida: " + ex.getMessage());
        }
    }
    
    public ArrayList<Fanfiction> getFics(){
        ArrayList list = new ArrayList();
        Fanfiction f;
        try {
            ResultSet ficResult = this.stt.executeQuery("SELECT name, id FROM fanfictions ORDER BY id ASC");
            ResultSet tagResult;
            while(ficResult.next()){
                String name = ficResult.getString("name");
                int id = ficResult.getInt("id");
                f = new Fanfiction(id, name, new String[]{"Teste","Teste 2"});
                list.add(f);
            }
        } catch (SQLException e) {
        }
        
        return list;
    }
    
    // TODO criar as tags na bd se não existirem
    // TODO relacionar as tags a fic que as possuem na db
    // TODO Pegar todas as informações de fic e sua tags relacionadas
    
    public void addFic(String name, String[] tags){
        try {
            pstt = conn.prepareStatement("INSERT IGNORE INTO fanfictions (name) VALUES (?)");
            pstt.setString(1, name);
            if (pstt.executeUpdate() > 0){
                JOptionPane.showMessageDialog(null, "Fanfic adicionada na lista");
            } else {
                System.out.println("Fic já existe no sistema");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DbConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void removeFic(Fanfiction f) {
        try {
            pstt = conn.prepareStatement("DELETE FROM fanfictions WHERE id=?");
            pstt.setInt(1, f.getId());
            int rows = pstt.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(null, "Fanfic removida da lista");
            }
        } catch (SQLException e) {
            System.out.println("Erro" + e.getMessage());
        }
    }
    
    public void updateFic(Fanfiction f) {
        try {
            pstt = conn.prepareStatement("UPDATE fanfictions SET name = ? WHERE id = ?");
            pstt.setString(1, f.getName());
            pstt.setInt(2, f.getId());
            int rows = pstt.executeUpdate();
            addNewTagsToDb(f.getTags());
            if (rows > 0){
                System.out.println("Deu certo");
            } else {
                System.out.println("Não alterou");
            }
        } catch (SQLException e) {
            System.out.println("Deu erro: " + e.getMessage());
        }
    }
    
    private void addNewTagsToDb(String[] tags) {
        try {
            pstt = conn.prepareStatement("INSERT IGNORE INTO tags (name) VALUES (?)");

            for (String t:tags){
            pstt.setString(1, t);
            pstt.executeUpdate();
            }

            System.out.println(pstt.toString());
        } catch (SQLException e) {
            System.out.println("Deu ruim adicionando as tags: "+ e.getMessage());
        }   
    }
    
    
    public void finishConnection(){
        try {
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(DbConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
