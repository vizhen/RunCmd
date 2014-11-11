package com.vizhen.runcmd;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener{

	Button openWifiADB;
	Button exit;
	Button showInfo;
	TextView text;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        openWifiADB = (Button)findViewById(R.id.btn_open);
        openWifiADB.setOnClickListener(this);
        exit = (Button)findViewById(R.id.btn_exit);
        exit.setOnClickListener(this);
        showInfo = (Button)findViewById(R.id.btn_info);
        showInfo.setOnClickListener(this);
        text = (TextView) findViewById(R.id.tv_info);
        
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
		case R.id.btn_info:
			showInfo();
			break;
		default:
			break;
		}
	}

	private void showInfo() {
		int height = getResources().getDisplayMetrics().heightPixels;
		int width = getResources().getDisplayMetrics().widthPixels;
		int density = getResources().getDisplayMetrics().densityDpi;
		
		text.setText("DisplayMetrics\n\twidth:" + width + " height:" + height + " density:" + density);
	}

	
}
