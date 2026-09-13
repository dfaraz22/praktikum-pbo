/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pertemuan1;

/**
 *
 * @author ACER
 */
public class Smartphone {
    private String merk;
    private String model;
    private int size;
    private int battery;
    private String color;
    private int price;
    
    public void setMerk (String merkSmartphone){
        merk = merkSmartphone;
    }
    
    public String getMerk (){
        return merk;
    }
    
    public void setModel (String modelSmartphone){
        model = modelSmartphone;
    }
    
    public String getModel (){
        return model;
    }
    
    public void setSize (int sizeSmartphone){
        size = sizeSmartphone;
    }
    
    public int getSize (){
        return size;
    }
    
    public void setBattery (int BatterySmartphone){
        battery = BatterySmartphone;
    }
    
    public int getBattery (){
        return battery;
    }
    
     public void setColor (String ColorSmartphone){
        color = ColorSmartphone;
    }
    
    public String getColor (){
        return color;
    }
    
    public void setPrice (int PriceSmartphone){
        price = PriceSmartphone;
    }
    
    public int getPrice (){
        return price;
    }
}
