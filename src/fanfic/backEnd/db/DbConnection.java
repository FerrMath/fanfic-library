package fanfic.backEnd.db;

import fanfic.backEnd.logic.Fanfiction;
import fanfic.backEnd.logic.Tag;
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
    private String USER_ENV_VARIABLE;
    private String PSWD_ENV_VARIABLE;

    
    public DbConnection() {
        USER_ENV_VARIABLE = System.getenv("USER");
        PSWD_ENV_VARIABLE = System.getenv("PSWD");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/fic_library",USER_ENV_VARIABLE,PSWD_ENV_VARIABLE); // CHANGE TO YOUR USER AND PASSWORD
            this.stt = conn.createStatement();
        } catch (ClassNotFoundException ex){
            System.out.println("Diver não disponivel");
        } catch (SQLException ex){
            System.out.println("Sintaxe de comando invalida: " + ex.getMessage());
        }
    }
    
    public ArrayList<Fanfiction> getFics(){
        /**
         * Gera lista com as fics salvas na BD
         */
        
        ArrayList<Fanfiction> list = new ArrayList<>();
        Tag[] tags;
        Fanfiction f;
        try {
            ResultSet ficResult = this.stt.executeQuery("SELECT name, id FROM fanfictions ORDER BY id ASC");
            
            while(ficResult.next()){
                String name = ficResult.getString("name");
                int id = ficResult.getInt("id");
                tags = getRelatedTags(id);
                f = new Fanfiction(id, name, tags);
                list.add(f);
            }
        } catch (SQLException e) {
        }
        
        return list;
    }    

    public Tag[] getRelatedTags(int ficId){
        ArrayList<Tag> tempList = new ArrayList<>();
        ResultSet result;
        int id;
        String name;
        String sql = "SELECT tags.id AS id, tags.name AS name FROM fanfictions_has_tags "+
                     "JOIN tags ON fanfictions_has_tags.tag = tags.id "+
                     "WHERE fanfictions_has_tags.fic = ?";
        try {
            pstt = conn.prepareStatement(sql);
            pstt.setInt(1, ficId);
            result = pstt.executeQuery();
            while (result.next()){
                id = result.getInt("id");
                name = result.getString("name");
                tempList.add(new Tag(id, name));
            }
        } catch (SQLException ex) {
            
        }
        
        
        return tempList.toArray(new Tag[0]);
    }
    
    public void addFicEntry(String name, String[] tags){
        int ficId;
        ficId = addFicName(name);
        addNewTagsToDb(tags);
        setRelatedTags(ficId, tags);
    }

    private int addFicName(String name) {
        int id = -1;
        ResultSet result;
        try {
            pstt = conn.prepareStatement("INSERT IGNORE INTO fanfictions (name) VALUES (?)");

            pstt.setString(1, name);
            if (pstt.executeUpdate() > 0){
                result = stt.executeQuery("SELECT last_insert_id() as id");
                if (result.next()){ 
                    id = result.getInt("id");
                }
                JOptionPane.showMessageDialog(null, "Fanfic adicionada na lista");
                
            } else {
                System.out.println("Fic já existe no sistema");
            }
        } catch (SQLException ex) {
            System.out.println("Erro sql: " + ex.getMessage());
        }
        return id;
    }
    
    private void addNewTagsToDb(String[] tags) {
        try {
            pstt = conn.prepareStatement("INSERT IGNORE INTO tags (name) VALUES (?)");
            stt = conn.createStatement();
            for (String t:tags){
                pstt.setString(1, t);
                pstt.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Deu ruim adicionando as tags: "+ e.getMessage());
        }   
    }
    
    private void setRelatedTags(int id, String[] tags){
        ArrayList<Integer> tagIds = new ArrayList<>();
        ResultSet result;
        try {
            pstt = conn.prepareStatement("SELECT id FROM tags WHERE name = ?");
            for (String t:tags){
                pstt.setString(1, t);
                result = pstt.executeQuery();
                if (result.next()){
                    tagIds.add(result.getInt("id"));
                }
            }
            
            pstt = conn.prepareStatement("INSERT IGNORE INTO fanfictions_has_tags (fic,tag) VALUES (?, ?)");
            for (int tagId:tagIds){
                pstt.setInt(1, id);
                pstt.setInt(2, tagId);
                if (pstt.executeUpdate() > 0){
                    
                }
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
//            addNewTagsToDb(f.getTags()); TEMP 
            if (rows > 0){
                System.out.println("Deu certo");
            } else {
                System.out.println("Não alterou");
            }
        } catch (SQLException e) {
            System.out.println("Deu erro: " + e.getMessage());
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
