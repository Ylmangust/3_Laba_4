/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package mephi.b22901.lab4;

import com.github.javafaker.Faker;
import java.util.Locale;
/**
 *
 * @author Регина
 */
public class Lab4 {

    public static void main(String[] args) {
        
        
     
        Controller CTRL = new Controller();
        //DatabaseOperation data = new DatabaseOperation();
        Faker faker = new Faker(Locale.UK);
        for (int i = 0; i < 10; i++) {
           // System.out.println(faker.name().fullName());
        }
        
    }
}
