package com.aze51.bidbid_client.Network;

import android.util.Log;
import android.widget.TextView;

import com.aze51.bidbid_client.ApplicationController;

import java.util.List;

/**
 * Created by Leekh on 2016-07-05.
 */
public class TimeThread extends Thread {
    boolean running = true;
    int rSec, rMin, rHour;
    //ViewHolder viewHolder;
    TextView text1, text2, text3;
    List<Product> products = ApplicationController.getInstance().getProducts(0);
    int time;
   /*public TimeThread(ViewHolder holder){
        this.viewHolder = holder;
    }*/
    public void run(){
        while (running){
            try {
                Thread.sleep(1000);
                text1 = ApplicationController.getInstance().getTextView();
                text2 = ApplicationController.getInstance().getTextView2();
                text3 = ApplicationController.getInstance().getTextView3();
                for(int i=0;i<products.size();i++){
                    time = products.get(i).rtime;
                    rHour = (time/3600);
                    double tmp2 = ((time/3600.0)-rHour)*60.0;
                    rMin = ((int)tmp2);
                    double tmp3 = (tmp2-rMin)*60;
                    rSec = ((int)tmp3);
                }
                rSec--;
                if(rSec == 0){
                    rMin--;
                    rSec = 59;
                }
                if(rMin == 0){
                    rHour--;
                    rMin = 59;
                }
                text1.setText(Integer.toString(rHour));
                text2.setText(Integer.toString(rMin));
                text3.setText(Integer.toString(rSec));

            }
            catch (InterruptedException ex){
                Log.e("SampleJavaThread", "Exception in thread");
            }
        }
    }

}
