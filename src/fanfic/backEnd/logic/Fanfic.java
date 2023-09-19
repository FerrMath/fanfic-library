/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fanfic.backEnd.logic;

/**
 *
 * @author ma_fe
 */
public class Fanfic {
    private int id;
    private String name;
    private String[] tags; // Talvez criar uma classe para tag
    private String[] comments; // Talvez criar uma classe para comentario
    
    public Fanfic (int id, String n, String[] t){
        this.name = n;
        this.tags = t;
    }

    public String getName() {
        return name;
    }

    public String[] getTags() {
        return tags;
    }
        
}
