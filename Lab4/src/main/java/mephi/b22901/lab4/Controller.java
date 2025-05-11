/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mephi.b22901.lab4;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Регина
 */
public class Controller {
    private DatabaseOperation operation;
    private GUI gui;
    
    public Controller(){
        this.operation = new DatabaseOperation();
        this.gui = new GUI(this);
    }
    
    public void addNewPerson(String name, String address){
        operation.addPerson(name, address);
    }
    
    public void addNewWand(){
        
    }
    
    public void addNewSupply(LocalDate date, List <Integer> id){
        operation.addNewSupply(date, id);
    }
    
    public void clear() {
        operation.clearData();
    }
    
    public List<Wand> getWands(){
        return operation.getWands();
    }
    
    public List<Customer> getCustomers(){
        return operation.getCustomers();
    }
    
    public List<Component> getStorage(){
        return operation.getStorage();
    }
    
    public List<Wand> getWandsAvaliable(){
        return operation.getWandsAvaliable();
    }
}
