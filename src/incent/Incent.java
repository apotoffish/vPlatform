package incent;
import platform.userdata;

import java.util.ArrayList;
import java.util.Date;

//声誉值初始为0.6,取值区间(0，1)
public class Incent {
    public static userdata mechanism(userdata udata){
        userdata newudata= new userdata();
        newudata = udata;

        Incent incent=new Incent();
        incent.addPositive(new Date());
        incent.addNegative(new Date());
        incent.addUncertain(new Date());
        newudata.reputation=incent.calculateReputation(newudata);
        newudata.behave.pay =(int)(newudata.reputation*100);
        //incent.addPositive(new Date());
        //incent.addNegative(new Date());
        System.out.println("id: "+newudata.id+" | reputation:"+newudata.reputation+" | positive: ");
        return newudata;
    }

    private final ArrayList<Date> positive=new ArrayList<Date>();
    private final ArrayList<Date> negative=new ArrayList<Date>();
    private final ArrayList<Date> uncertain=new ArrayList<Date>();

    public static double positiveWeight =0.2;//0.4
    public static double negativeWeight =0.8;//0.6
    public static double uncertainWeight =0.5;
    public static double fadeWeight =0.8;//0.8

    public Incent(){
    }

    //判断声誉值是否满足最低要求
    /*public boolean repuIsValid(){
        if(calculateReputation()>=REPU_LIMIT)
            return true;
        else
            return false;
    }*/

    //声誉得分
    //声誉值取值范围 (0,1), 初始值为0.75
    public double calculateReputation(userdata ud){
        if(positive.size()==0&&negative.size()==0&&uncertain.size()==0)
            return ud.reputation;

        double positiveEffect=0;
        double negitiveEffect=0;
        double uncertainEffect=0;
        double belief;
        double uncertainty;


        for (Date date : positive) {
            positiveEffect += timeEffect(ud,date);
        }
        for (Date date : negative) {
            negitiveEffect += timeEffect(ud,date);
        }
        for (Date date : uncertain) {
            uncertainEffect += timeEffect(ud,date);
        }

        //belief 值
        belief=successProbability()*positiveWeight*positiveEffect/(positiveWeight*positiveEffect+negativeWeight*negitiveEffect);
        //uncertainty 值
        uncertainty=(1-successProbability())*uncertainEffect;
        //用户信誉值
        return belief+uncertainWeight*uncertainty;
    }

    private double successProbability(){
        return (double) (positive.size()+negative.size())/(double) (positive.size()+negative.size()+uncertain.size());
    }

    private double timeEffect(userdata ud, Date date){
        double diff=(date.getTime()-ud.startTime.getTime())/(1000.0*60);
        return Math.pow(fadeWeight,diff);
    }

    public void addNegative(Date date) {
        negative.add(date);
        //System.out.println(date);
    }

    public void addPositive(Date date) {
        positive.add(date);
        //System.out.println(date);
    }

    public void addUncertain(Date date) {
        uncertain.add(date);
        //System.out.println(date);
    }
}