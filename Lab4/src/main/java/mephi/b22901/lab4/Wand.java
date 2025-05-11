/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mephi.b22901.lab4;

/**
 *
 * @author Регина
 */
public class Wand {
    private int id;
    private int woodId;
    private int coreId;
    private boolean isSold;


    public Wand(int id, int woodId, int coreId, boolean isSold) {
        this.id = id;
        this.woodId = woodId;
        this.coreId = coreId;
        this.isSold = isSold;
    }

    public int getId() {
        return id;
    }

    public int getWoodId() {
        return woodId;
    }

    public int getCoreId() {
        return coreId;
    }

    public boolean getIsSold() {
        return isSold;
    }
    
    
}
