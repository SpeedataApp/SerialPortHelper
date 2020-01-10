package com.serialport_helper_new;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.serialport_helper_new.utils.ToastUtils;

class SetOtherSerialPort extends Dialog implements android.view.View.OnClickListener {

	/**
	 * 
	 */
	private final MyInterface interface1;
	private EditText eport;
	private EditText baudrate;
	private Button ok;
	private Button cancel;
	private Context mContext;

	public SetOtherSerialPort(MyInterface interface1, Context context) {
		super(context);
		this.interface1 = interface1;
		mContext = context;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.set);
		eport = findViewById(R.id.port);
		ok = findViewById(R.id.ok);
		cancel = findViewById(R.id.cancel);
		ok.setOnClickListener(this);
		cancel.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v == ok) {
			String ss = eport.getText().toString();
			if (!"".equals(ss)) {
				interface1.setSerialPort(ss);
				dismiss();
			} else {
				ToastUtils.showShortToastSafe("can't be empty");
			}
		} else if (v == cancel) {
			dismiss();
			// SetDialog.p_Spinner.setAdapter(SetDialog.p_adapter);
			interface1.setBaurdrateSpinner(0);
		}

	}

}