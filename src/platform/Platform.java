package platform;
import java.util.Date;
import java.util.Scanner;
import java.util.Random;
import static java.lang.String.format;
import incent.Incent;

public class Platform {
    static userdata initworker(userdata userdata,int id){
        userdata = new userdata();
        userdata.id=id;
        Random r= new Random();
        userdata.device_score = r.nextInt(100);
        userdata.joined=true;
        userdata.behave=new userbehave();
        userdata.reputation=0.5;
        return userdata;
    }


    public static void main(String[] args){
        final int MAX_WORKER=20;
        final int MAX_SLCT=5;
        int round=3;
        Random random = new Random();
        userdata [] udata=new userdata[MAX_WORKER];//用户表
        userdata [] newudata=new userdata[MAX_WORKER];
        System.out.println("initworker: ");
        for (int i=0;i<MAX_WORKER;i++){
            udata[i]= new userdata();
            udata[i] = initworker(udata[i],i);//初始化用户数据
            System.out.print(udata[i].id+" ");
        }
        System.out.println();

        for(int r=0;r<round ; r++){
            int [] selected=new int[MAX_SLCT];
            selected=Selections.select(udata,MAX_SLCT);//每一轮开始前进行选择

            //每轮开始前用户随机行为
            for(int i=0;i<MAX_SLCT;i++){
                int join=random.nextInt(100);
                if(join%8==0){//在参与和不参与之间选择
                    udata[selected[i]].joined = false;
                }
                udata[selected[i]].behave.finished=random.nextBoolean();
                udata[selected[i]].behave.quit=random.nextBoolean();
                udata[selected[i]].behave.accuarcy=random.nextDouble();
            }

            for(int i=0;i<MAX_SLCT;i++){
                newudata[selected[i]]= new userdata();
                udata[i].startTime = new Date();
                newudata[selected[i]] = Incent.mechanism(udata[i]);//调用机制算法
                System.out.println("r:"+r+" id:"+newudata[selected[i]].id);//输出每一轮更新信息
                System.out.println("reputation: "+newudata[selected[i]].reputation);
                System.out.println("pay: "+newudata[selected[i]].behave.pay);
            }
        }
    }
}
