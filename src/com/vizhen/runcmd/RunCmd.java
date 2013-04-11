package com.vizhen.runcmd;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.NetworkInfo.State;
import android.net.wifi.WifiManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.widget.Toast;

public final class RunCmd
{
    private static final String SET_PORT = "setprop service.adb.tcp.port 5555";
    
    private static final String STOP_ADBD = "stop adbd";
    
    private static final String START_ADBD = "start adbd";
    
    private static final String EXIT = "exit";
    
    private static final int NO_NETWORK_CONNECT = -1;
    
    private static final int MOBLE_NETWORK_CONNECT = 0;
    
    private static final int WIFI_NETWORK_CONNECT = 1;
    
    private static final int ETH_NETWORK_CONNECT = 2;
    
    
    
    
    /**
     * 打开wifi调试
     * @param context
     */
    public static void runOpenWifiAdb(Context context)
    {
        // TODO Auto-generated method stub
        try
        {
            
            Process process = Runtime.getRuntime().exec("su");
            DataOutputStream dos = new DataOutputStream(process.getOutputStream());
            
            // Open wifi adb
            dos.writeBytes(SET_PORT + "\n");
            dos.flush();
            dos.writeBytes(STOP_ADBD + "\n");
            dos.flush();
            dos.writeBytes(START_ADBD + "\n");
            dos.flush();
            
            dos.writeBytes(EXIT + "\n");
            dos.flush();
            
            dos.close();
            
            String ipAddress = getLocalIpAddress();
            
            if (ipAddress != null)
            {
                notifUserInfo(context, "OpenWifiADB", "You can use\n \" adb connect " + ipAddress + ":5555\" " + "\nto debug");
            }
            else
            {
                notifUserInfo(context, "OpenWifiADB", "You can use adb connect");
            }
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Toast.makeText(context.getApplicationContext(),
                "Can not get system runtime! Please Root First!",
                Toast.LENGTH_LONG).show();
            
            notifUserInfo(context, "OpenWifiADB", "Please Root You Device First!");
        }
    }
    
    /**
     * 通知栏通知用户
     * 
     * @param context
     * @param title
     * @param message
     */
    public static void notifUserInfo(Context context, String title, String message)
    {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
        mBuilder.setSmallIcon(R.drawable.ic_launcher);
        mBuilder.setContentTitle(title);
        mBuilder.setContentText(message);
        
        Intent resultIntent = new Intent(context.getApplicationContext(), MainActivity.class);
        
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);
        
        NotificationManager notificationManager =
            (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, mBuilder.build());
    }
    
    /**
     * 检查是否有Eth，Wifi，Mobile网络连接，优先返回Eth
     * @param context
     * @return 没有网络连接，则返回-1
     */
    public static int checkNetworkInfo(Context context)
    {
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        
        State mobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
        State wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
        State eth = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_ETHERNET).getState();
        
        if(eth == State.CONNECTED)
        {
            return ETH_NETWORK_CONNECT;
        }
        else if(wifi == State.CONNECTED)
        {
            return WIFI_NETWORK_CONNECT;
        }
        else if(mobile == State.CONNECTED)
        {
            return MOBLE_NETWORK_CONNECT;
        }
        
        return -1;
    }
    
    public static String getWifiIP(Context context)
    {
        String result = null;
        WifiManager wifiMgr = (WifiManager)context.getSystemService( Context.WIFI_SERVICE );
        DhcpInfo info = wifiMgr.getDhcpInfo();
        String[] temp = info.toString().split( " " );
        result = temp[1];
        return result;
    }
    
    /*public static String getEthIP(Context context)
    {
        String result = null;

        return result;
    }*/
    
    /*public static String getMobileIP(Context context)
    {
        String result = null;
        
        return result;
    }*/
    
    public static String getLocalIpAddress() {     
        try {     
            for (Enumeration<NetworkInterface> en = NetworkInterface     
                    .getNetworkInterfaces(); en.hasMoreElements();) {     
                NetworkInterface intf = en.nextElement();     
                for (Enumeration<InetAddress> enumIpAddr = intf     
                        .getInetAddresses(); enumIpAddr.hasMoreElements();) {     
                    InetAddress inetAddress = enumIpAddr.nextElement();     
                    if (!inetAddress.isLoopbackAddress() && !inetAddress.isLinkLocalAddress()
                        && inetAddress.isSiteLocalAddress()) {     
                        return inetAddress.getHostAddress().toLowerCase();     
                    }     
                }     
            }     
        } catch (SocketException ex) {     
            Log.e("IpAddress", ex.toString());     
        }     
        return null;     
    }
}
