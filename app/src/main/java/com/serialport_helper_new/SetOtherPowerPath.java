package com.serialport_helper_new;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

class SetOtherPowerPath extends Dialog implements
		android.view.View.OnClickListener {

	/**
	 * 
	 */
	private final MyInterface interface1;
	private EditText eport;
	private EditText baudrate;
	private Button ok;
	private Button cancel;
	private Context mContext;

	public SetOtherPowerPath(MyInterface interface1, Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.interface1 = interface1;
		mContext = context;
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.set_other_power_path);
		eport = (EditText) findViewById(R.id.ev_gpio);
		ok = (Button) findViewById(R.id.ok);
		cancel = (Button) findViewById(R.id.cancel);
		ok.setOnClickListener(this);
		cancel.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == ok) {
			String ss = eport.getText().toString();
			if (!ss.equals("")) {
				// -wdout64 1"
				interface1.setPower("-wdout" + ss + " 1", "-wdout" + ss + " 0");
				dismiss();
			} else
				Toast.makeText(mContext, "can't be empty", 1000).show();
		} else if (v == cancel) {
			// SetDialog.p_Spinner.setAdapter(SetDialog.p_adapter);
			interface1.setPowerSpinner(0);
			dismiss();
		}

	}

}