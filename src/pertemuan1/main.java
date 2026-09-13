/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pertemuan1;

/**
 *
 * @author ACER
 */
public class main {
    public static void main(String[] args) {
        Smartphone hp = new Smartphone ();
        
        hp.setMerk("Redmi");
        hp.setModel("Note 12");
        hp.setBattery(5000);
        hp.setColor("Pink");
        hp.setSize(11);
        hp.setPrice(3000000);
        
        System.out.println("Merk hp: "+hp.getMerk());
        System.out.println("dengan model: "+hp.getModel());
        System.out.println("dengan kapasistas baterai "+ hp.getBattery()+"mAh");
        System.out.println("warna "+hp.getColor());
        System.out.println("ukuran sebesar "+hp.getSize());
        System.out.println("hanya seharga Rp."+hp.getPrice());
    }
}
