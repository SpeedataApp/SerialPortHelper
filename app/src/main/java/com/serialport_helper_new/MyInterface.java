package com.serialport_helper_new;

interface MyInterface {
	public void setSerialPort(String path);

	public void setBaurdrate(int baurdrate);

	public void setBaurdrateSpinner(int position);

	public void setSerialPortSpinner(int position);

	public void setPower(String poweron, String poweroff);

	public void setPowerSpinner(int position);
}