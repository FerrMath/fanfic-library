/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fanfic;

import java.util.ArrayList;
import fanfic.backEnd.logic.Fanfic;
import java.util.Arrays;

/**
 *
 * @author ma_fe
 */
public class GenerateFanficsTEMP {
    // //////////////////////// //
    // THIS IS A TEMPORARY FILE //
    // //////////////////////// //
    
    private static final Fanfic[] ficArray = {
      new Fanfic("The One Time Poison Is Good For You", new String[]{"Love", "Love Confessions","Idiots in Love","Friendship/Love","Friends to Lovers","Fluff"}),  
      new Fanfic("Quantum Entanglement", new String[]{"Enemies to friends to lovers","Murder","Panic Attacks","Smut"}),
      new Fanfic("Whatever it takes", new String[]{"Romance","Angst","Stalking","Murder","Fluff","Hurt and confort"})
    };
    
    
    public static ArrayList<Fanfic> generateFics(){
        ArrayList<Fanfic> list = new ArrayList<>();
         
        for (Fanfic fic:ficArray){
            list.add(fic);
        }
        
        return list;
    }
    
    public static String[] getNames(){
        ArrayList<String> li = new ArrayList<>();
        for (Fanfic fic: ficArray){
            li.add(fic.getName());
        }
        
        String[] la = li.toArray(new String[0]);
        return la;
    }
}
