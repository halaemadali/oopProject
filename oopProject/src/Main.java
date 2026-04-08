

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        RoomType t1 =new RoomType("single",1,200);
        System.out.println(  t1.getNO3());
        Amenity wifi = new Amenity("WiFi");
        Room r1=new Room();
        r1.addAmenity(wifi);
        System.out.println( r1.getAmenities().get(0));
        r1.removeAmenity(wifi);


        RoomType RT=new RoomType();

        Room r=new Room();
        r.settype(RT);
        r.gettype().setBasePrice(2000.650);
        r.gettype().setCapacity(2);
        r.gettype().setNO3("single");

        Amenity TV=new Amenity("TV");
        Amenity bar=new Amenity("bar");
        r.setroomnumber(102);
        r.addAmenity(wifi);
        r.addAmenity(TV);
        r.setisavailable(true);
        r.checkavailability(r.getisavailable());
        r.getAmenities();
        r.getroomnumber();
        r.getisavailable();
        r.removeAmenity(wifi);
        System.out.println (r.gettype().getBasePrice());
        System.out.println (r.gettype().getCapacity());
        System.out.println (r.gettype().getNO3());

    }
}
