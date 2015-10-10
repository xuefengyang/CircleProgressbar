package com.yxf.circleprogressbardemo;

import com.yxf.circleprogressbar.CircleProgressBar;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends Activity {
    private CircleProgressBar mProgressBar;
    private CircleProgressBar mProgressBar2;
    private CircleProgressBar mProgressBar3;
    private CircleProgressBar mProgerssBar4;
    private CircleProgressBar mProgressBar5;
    private boolean flag=true;
    Handler mHandler =new Handler(){   
        public void handleMessage(android.os.Message msg) {
            int progress = mProgressBar.getProgress();
            int progress2 =mProgressBar2.getProgress();
            int progress3 =mProgressBar3.getProgress();
            int progress4=mProgressBar3.getProgress();
            int progress5=mProgressBar5.getProgress(); 
            mProgressBar.setProgress(++progress);  
            mProgressBar2.setProgress(++progress2);
            mProgressBar3.setProgress(++progress3);
            mProgerssBar4.setProgress(++progress4);    
            mProgressBar5.setProgress(++progress5);
        }  
    };
   @Override
   protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_main);
       mProgressBar=(CircleProgressBar)findViewById(R.id.progressbar1);
       mProgressBar2=(CircleProgressBar)findViewById(R.id.progressbar2);
       mProgressBar3=(CircleProgressBar)findViewById(R.id.progressbar3);
       mProgerssBar4=(CircleProgressBar)findViewById(R.id.progressbar4);
       mProgressBar5=(CircleProgressBar)findViewById(R.id.progressbar5);
       new Thread(new Runnable() {
           @Override
           public void run() {
               while (flag) {
                   try {   
                       Thread.sleep(100);
                       mHandler.sendEmptyMessage(1);
                   } catch (InterruptedException e) {
                       e.printStackTrace();	
                   }
               }
              
           }
       }).start();
   }   
   @Override
   protected void onResume() {
       super.onResume();
       flag=true;
   }
   @Override
   protected void onPause() {
       // TODO Auto-generated method stub
       super.onPause();
       flag=false;
   }}
