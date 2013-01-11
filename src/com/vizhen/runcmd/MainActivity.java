package com.vizhen.runcmd;

import java.io.DataOutputStream;
import java.io.IOException;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener{

	public static final String SET_PORT="setprop service.adb.tcp.port 5555";
	public static final String STOP_ADBD = "stop adbd";
	public static final String START_ADBD = "start adbd";
	public static final String EXIT = "exit";
	
	Button open;
	Button exit;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        open = (Button)findViewById(R.id.btn_open);
        open.setOnClickListener(this);
        exit = (Button)findViewById(R.id.btn_exit);
        exit.setOnClickListener(this);
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.btn_open:
			runOpenWifiAdb();
			break;
		case R.id.btn_exit:
			finish();
			break;
		default:
			break;
		}
	}

	private void runOpenWifiAdb() {
		// TODO Auto-generated method stub
		try {
			
			Process process = Runtime.getRuntime().exec("su");
			DataOutputStream dos = new DataOutputStream(process.getOutputStream());
			
			//Open wifi adb
			dos.writeBytes(SET_PORT + "\n");
			dos.flush();
			dos.writeBytes(STOP_ADBD + "\n");
			dos.flush();
			dos.writeBytes(START_ADBD + "\n");
			dos.flush();
			
			dos.writeBytes(EXIT + "\n");
			dos.flush();			
			
			dos.close();
			
			Toast.makeText(getApplicationContext(), "OK", Toast.LENGTH_LONG).show();
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(getApplicationContext(),
					"Can not get system runtime! Please Root First!",
					Toast.LENGTH_LONG).show();
		}
	}
}
