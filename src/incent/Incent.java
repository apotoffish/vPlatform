package incent;
import java.util.Random;
import platform.userdata;

public class Incent {
    public static userdata mechanism(userdata udata){
        userdata newudata= new userdata();
        newudata = udata;
        Random r=new Random();

        newudata.reputation=r.nextDouble();
        newudata.behave.pay=(int)(newudata.reputation*100);
        return newudata;
    }
}
