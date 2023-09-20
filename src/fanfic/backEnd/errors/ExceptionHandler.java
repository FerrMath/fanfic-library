/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fanfic.backEnd.errors;

/**
 *
 * @author ma_fe
 */
public class ExceptionHandler {
    public static boolean haveEmptyFields(String... fields){
        for (String f:fields){
            if(f.isBlank() || f.isEmpty()){
                return true;
            }
        }
        return false;
    }
}
