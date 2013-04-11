package com.vizhen.runcmd;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener{

	Button openWifiADB;
	Button exit;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        openWifiADB = (Button)findViewById(R.id.btn_open);
        openWifiADB.setOnClickListener(this);
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
			RunCmd.runOpenWifiAdb(MainActivity.this);
			break;
		case R.id.btn_exit:
			finish();
			break;
		default:
			break;
		}
	}

	
}
