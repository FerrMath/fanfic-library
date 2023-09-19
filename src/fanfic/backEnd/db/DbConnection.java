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

public class DbConnection {
    private Connection conn;
    private Statement stt;
    private PreparedStatement pstt;

    
    // TODO create logic to add new fics;
    public DbConnection() {
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/fic_library","teste","root"); // CHANGE TO YOUR USER AND PASSWORD
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
            ResultSet result = this.stt.executeQuery("SELECT name, id FROM fanfictions ORDER BY id ASC");
            while(result.next()){
                String name = result.getString("name");
                int id = result.getInt("id");
                f = new Fanfiction(id, name, new String[]{"Teste","Teste 2"});
                list.add(f);
            }
        } catch (SQLException e) {
        }
        
        return list;
    }
    
    public void addFic(String name, String[] tags){
        try {
            pstt = conn.prepareStatement("INSERT IGNORE INTO fanfictions (name) VALUES (?)");
            pstt.setString(1, name);
            if (pstt.executeUpdate() > 0){
                System.out.println("Sucesso");
            } else {
                System.out.println("Fic já existe no sistema");
            }
            
            System.out.println("Foi");
        } catch (SQLException ex) {
            Logger.getLogger(DbConnection.class.getName()).log(Level.SEVERE, null, ex);
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
