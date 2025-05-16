/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import java.time.LocalDate;
import java.util.List;
import Model.Component;
import Model.Customer;
import Model.DatabaseOperation;
import View.GUI;
import Model.Sale;
import Model.Wand;

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
    
    public void addNewWand(Component wood, Component core){
        operation.addNewWand(wood, core);
    }
    
    public void addNewSupply(LocalDate date, List <Integer> id){
        operation.addNewSupply(date, id);
    }
    
    public void addNewSale(int customerID, int wandID, LocalDate date){
        operation.addNewSale(customerID, wandID, date);
    }
    
    public void clear() {
        operation.clearData();
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
    
    public List<Component> getCompsAvaliable(){
        return operation.getComponentsAvaliable();
    }
    
    public List <Sale> getSalesHistory(){
        return operation.getSales();
    }
}
