package com.serialport_helper_new;

import java.io.IOException;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

class Setbaudrate extends Dialog implements android.view.View.OnClickListener {
	/**
	 * 
	 */
	private final MyInterface interface1;
	private EditText baudrate;
	private Button ok;
	private Button cancel;
	private Context mContext;

	public Setbaudrate(MyInterface mainActivity, Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		interface1 = mainActivity;
		mContext = context;
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setbaudrate);
		baudrate = (EditText) findViewById(R.id.bb);
		ok = (Button) findViewById(R.id.ok1);
		cancel = (Button) findViewById(R.id.cancle1);
		ok.setOnClickListener(this);
		cancel.setOnClickListener(this);
		// baudrate.setInputType()
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == ok) {
			String temp_b = baudrate.getText().toString();
			if (!temp_b.equals("")) {
				interface1.setBaurdrate(Integer.parseInt(temp_b));
				dismiss();
			} else
				Toast.makeText(mContext, "can't be empty", 1000).show();
		}
		if (v == cancel) {
			dismiss();
			interface1.setSerialPortSpinner(0);
		}

	}

}