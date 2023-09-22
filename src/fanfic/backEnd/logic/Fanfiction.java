/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fanfic.backEnd.logic;

/**
 *
 * @author ma_fe
 */
public class Fanfiction {
    private int id;
    private String name;
    private Tag[] tags; // TODO alterar toda a logica para class de tag
    private String[] comments; // Talvez criar uma classe para comentario
    
    public Fanfiction (int id, String n, Tag[] t){
        this.name = n;
        this.tags = t;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Tag[] getTags() {
        return tags;
    }
    
    public String getStrTags() {
        String str = "";
        for (Tag s:tags){
            if (s.getName().equals(tags[tags.length - 1])){
                str += s;
            } else {
                str += s + ", ";
            }
        }
        return str;
    }
    
    public int getId(){
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTags(Tag[] tags) {
        this.tags = tags;
    }
            
}
