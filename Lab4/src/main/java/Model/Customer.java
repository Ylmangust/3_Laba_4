/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Регина
 */
public class Customer {

    private int id;
    private String name;
    private String address;

    public Customer(int id, String name, String addres) {
        this.id = id;
        this.name = name;
        this.address = addres;
    }
    
    public int getID(){
        return id;
    }
    
    @Override
    public String toString(){
        return name;
    }

}
