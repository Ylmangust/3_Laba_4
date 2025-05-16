/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Регина
 */
public class Sale {

    private int saleId;
    private String custName;
    private String wandName;
    private String date;

    public Sale(int saleId, String custName, String wandName, String date) {
        this.saleId = saleId;
        this.custName = custName;
        this.wandName = wandName;
        this.date = date;
    }

    public int getSaleId() {
        return saleId;
    }

    public String getCustName() {
        return custName;
    }

    public String getWandName() {
        return wandName;
    }

    public String getDate() {
        return date;
    }
    
    

}
