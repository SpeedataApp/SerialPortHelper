package com.serialport_helper_new;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.serialport_helper_new.utils.ToastUtils;

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
		interface1 = mainActivity;
		mContext = context;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setbaudrate);
		baudrate = findViewById(R.id.bb);
		ok = findViewById(R.id.ok1);
		cancel = findViewById(R.id.cancle1);
		ok.setOnClickListener(this);
		cancel.setOnClickListener(this);
		// baudrate.setInputType()
	}

	@Override
	public void onClick(View v) {
		if (v == ok) {
			String temp_b = baudrate.getText().toString();
			if (!"".equals(temp_b)) {
				interface1.setBaurdrate(Integer.parseInt(temp_b));
				dismiss();
			} else {
				ToastUtils.showShortToastSafe("can't be empty");
			}
		}
		if (v == cancel) {
			dismiss();
			interface1.setSerialPortSpinner(0);
		}

	}

}