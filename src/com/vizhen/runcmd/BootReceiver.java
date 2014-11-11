package com.vizhen.runcmd;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * 接收开机广播,完成开机之后需要完成的必须操作
 * 
 * @author vizhen
 * 
 */
public class BootReceiver extends BroadcastReceiver
{
    
    @Override
    public void onReceive(Context context, Intent intent)
    {
        //开机自动打开调试功能
        Log.d("[RunCMD]BootReceiver", "BootReceiver!!");
    }
    
}
