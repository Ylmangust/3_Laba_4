/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Регина
 */
public class Wand {
    private int id;
    private int woodId;
    private int coreId;
    private boolean isSold;
    private String woodName;
    private String coreName;


    public Wand(int id, int woodId, int coreId, boolean isSold, String wood, String core) {
        this.id = id;
        this.woodId = woodId;
        this.coreId = coreId;
        this.isSold = isSold;
        this.woodName = wood;
        this.coreName = core;
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
    
    @Override
    public String toString(){
        return woodName + "+" + coreName;
    }
    
    
}
