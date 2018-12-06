package com.serialport_helper_new;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.serialport.SerialPort;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;

class SettingsDialog extends Dialog implements
        android.view.View.OnClickListener, MyInterface {
    private final MainActivity mainActivity;
    private Context mContext;
    private SetOtherSerialPort setDialog;
    private SetOtherPowerPath setPowerDialog;
    private Setbaudrate setBaudrate;
    private SerialPort mSerialPort;
    private int fd;

    public SettingsDialog(MainActivity mainActivity, Context context) {
        super(context);
        this.mainActivity = mainActivity;
        mContext = context;
        setDialog = new SetOtherSerialPort(this, context);
        setPowerDialog = new SetOtherPowerPath(this, context);
        setBaudrate = new Setbaudrate(this, context);
        mSerialPort = mainActivity.getmSerialPort();
        DevCtrl = mainActivity.getDevCtrl();
        ArrayBaudrate = mContext.getResources()
                .getStringArray(R.array.baudrate);
        ArraySerialPort = mContext.getResources().getStringArray(
                R.array.serial_port);
        ArrayPowerPath = mContext.getResources().getStringArray(
                R.array.power_path);
        ArrayStopBit = mContext.getResources().getStringArray(R.array.stopbit);
        Arraydatabit = mContext.getResources().getStringArray(R.array.databit);
        ArrayCrc = mContext.getResources().getStringArray(R.array.crc);
    }

    // private Button ok;
    private Button goback;
    private Button poweron;
    private Button poweroff;
    private Button openSerial;
    private Button closeSerial;
    private String[] ArrayBaudrate;
    private String[] ArraySerialPort;
    private final String[] ArrayPowerPath;
    private final String[] ArrayStopBit;
    private final String[] ArrayCrc;
    private final String[] Arraydatabit;
    private Spinner b_Spinner;
    private Spinner p_Spinner;
    private Spinner s_Spinner;
    private Spinner crc_Spinner;
    private Spinner databitSpinner;

    private int baudrate = 0;
    private String serial_path = "";
    private String power_path;

    private Spinner path_Spinner;
    private ArrayAdapter<String> path_adapter;

    private int stopbitt;
    private ArrayAdapter<String> b_adapter;
    private ArrayAdapter<String> p_adapter;
    private ArrayAdapter<String> s_adapter;
    private ArrayAdapter<String> crc_adapter;
    private ArrayAdapter<String> databit_adapter;
    private int crc_num = 0;
    private int dataBit = 8;
    private int powercount = 0;
    private int serial = 0;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second);
        // ok = (Button) findViewById(R.id.pathok);
        // ok.setOnClickListener(this);
        goback = this.findViewById(R.id.backto);
        poweron = this.findViewById(R.id.poweron);
        openSerial = this.findViewById(R.id.openss);
        closeSerial = this.findViewById(R.id.closess);
        poweroff = this.findViewById(R.id.poweroff);
        b_Spinner = this.findViewById(R.id.spinner2);
        p_Spinner = this.findViewById(R.id.spinner1);
        path_Spinner = this.findViewById(R.id.pathspinner);
        s_Spinner = this.findViewById(R.id.spinner3);
        crc_Spinner = this.findViewById(R.id.spinner4);
        databitSpinner = this.findViewById(R.id.spinner_data);
        goback.setOnClickListener(this);
        poweron.setOnClickListener(this);
        poweroff.setOnClickListener(this);
        openSerial.setOnClickListener(this);
        closeSerial.setOnClickListener(this);
        poweroff.setEnabled(false);
        closeSerial.setEnabled(false);
        // sendstring = EditTextsend.getText().toString();
        // sp = new SerialPort();
        b_adapter = new ArrayAdapter<>(mainActivity,
                android.R.layout.simple_spinner_item, ArrayBaudrate);
        // Log.w(TAG,"WARN");
        b_adapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        b_Spinner.setAdapter(b_adapter);
        b_Spinner
                .setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View arg1,
                                               int position, long id) {
                        arg0.setVisibility(View.VISIBLE);
                        // "1200","9600","38400","57600","1152000","other"
                        if (b_Spinner.getSelectedItem().toString()
                                .equals("other")) {
                            SettingsDialog.this.setBaudrate
                                    .setTitle(R.string.sure);
                            SettingsDialog.this.setBaudrate.show();
                        } else {
                            baudrate = Integer.parseInt(b_Spinner
                                    .getSelectedItem().toString());
                        }

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {

                    }

                });
        p_adapter = new ArrayAdapter<>(mainActivity,
                android.R.layout.simple_spinner_item, ArraySerialPort);
        p_adapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        p_Spinner.setAdapter(p_adapter);
        p_Spinner
                .setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View arg1,
                                               int position, long id) {
                        arg0.setVisibility(View.VISIBLE);
                        serial_path = SettingsDialog.this.p_Spinner
                                .getSelectedItem().toString();
                        if (serial_path.equals("other")) {
                            setDialog.setTitle(R.string.sure);
                            setDialog.show();
                        } else {
                            serial_path = "/dev/"
                                    + SettingsDialog.this.p_Spinner
                                    .getSelectedItem().toString();
                        }

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                    }

                });

        path_adapter = new ArrayAdapter<>(mainActivity,
                android.R.layout.simple_spinner_item, ArrayPowerPath);
        path_adapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        path_Spinner.setAdapter(path_adapter);
        path_Spinner
                .setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View arg1,
                                               int position, long id) {
                        // "/proc/driver/scan","/proc/driver/as3992","/proc/driver/tda3992","/proc/driver/sirf"
                        arg0.setVisibility(View.VISIBLE);
                        power_path = path_Spinner.getSelectedItem().toString();
                        int select = path_Spinner.getSelectedItemPosition();
                        switch (select) {
                            case 0:
                                actual_path = "/sys/class/misc/mtgpio/pin";
                                power_on_write = "-wdout64 1";
                                power_off_write = "-wdout64 0";
                                break;
                            case 1:
                                actual_path = "/sys/class/misc/mtgpio/pin";
                                power_on_write = "-wdout106 1";
                                power_off_write = "-wdout106 0";
                                break;

                            default:
                                actual_path = power_path;
                                power_on_write = "on";
                                power_off_write = "off";
                                break;
                        }

                        if (power_path.equals("other")) {
                            setPowerDialog.setTitle(R.string.sure);
                            setPowerDialog.show();
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {

                    }

                });


        s_adapter = new ArrayAdapter<>(mainActivity,
                android.R.layout.simple_spinner_item, ArrayStopBit);
        // Log.w(TAG,"WARN");
        s_adapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s_Spinner.setAdapter(s_adapter);
        s_Spinner
                .setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View arg1,
                                               int position, long id) {
                        arg0.setVisibility(View.VISIBLE);
                        if (position == 0 && id == 0) {
                            stopbitt = 1;
                        }
                        if (position == 1 && id == 1) {
                            stopbitt = 2;
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                    }

                });
        crc_adapter = new ArrayAdapter<>(mainActivity,
                android.R.layout.simple_spinner_item, ArrayCrc);
        // Log.w(TAG,"WARN");
        crc_adapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        crc_Spinner.setAdapter(crc_adapter);
        crc_Spinner
                .setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View arg1,
                                               int position, long id) {
                        arg0.setVisibility(View.VISIBLE);
                        if (position == 0 && id == 0) {
                            crc_num = 0;
                        }
                        if (position == 1 && id == 1) {
                            crc_num = 2; // odd ��У��
                        }
                        if (position == 2 && id == 2) {
                            crc_num = 1; // even żУ��
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                    }

                });


        p_Spinner.setSelection(0);
        b_Spinner.setSelection(0);
        openSerial.requestFocus();
        databit_adapter = new ArrayAdapter<>(mainActivity, android.R.layout.simple_spinner_item, Arraydatabit);
        databit_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        databitSpinner.setAdapter(databit_adapter);
        databitSpinner.setSelection(0);
        databitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    dataBit = Integer.parseInt(databitSpinner
                            .getSelectedItem().toString());
                } else if (position == 1) {
                    showInputDialog();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void showInputDialog() {
        /*@setView 装入一个EditView
         */
        final EditText editText = new EditText(mainActivity);
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        AlertDialog.Builder inputDialog =
                new AlertDialog.Builder(mainActivity);
        inputDialog.setTitle(R.string.dialog_databit).setView(editText);
        inputDialog.setPositiveButton(R.string.ok,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dataBit = Integer.parseInt(editText.getText().toString());
                    }
                }).show();
    }


    private DeviceControl DevCtrl;
    // DeviceControl DevCtrl2;

    private String power_off_write = "";
    private String power_on_write = "";
    private String actual_path = "";

    @Override
    public void onClick(View v) {
        if (v == poweron) {

            try {
                // if (power_path.equals("MT GPIO")) {
                // System.out.println("MT GPIO on");
                // DevCtrl = new DeviceControl("/sys/class/misc/mtgpio/pin",
                // mainActivity);
                // DevCtrl.MTGpioOn();
                // // DisplayToast("open driver success  " + pathss);
                // } else {
                System.out.println(actual_path);
                DevCtrl = new DeviceControl(actual_path, mainActivity);
                DevCtrl.PowerOnDevice(power_on_write);
                DisplayToast("open driver success  " + actual_path);
                // }
                poweroff.setEnabled(true);
                poweron.setEnabled(false);
                path_Spinner.setEnabled(false);
                powercount = 1;
                Log.d(MainActivity.TAG, "open driver success");
            } catch (IOException e) {
                e.printStackTrace();
                DisplayToast("open driver " + actual_path + "failed");
                Log.e(MainActivity.TAG, "open driver failed" + power_path);
                // return;
            }
            // dismiss();
        }
        if (v == openSerial) {
            try {
                System.out.println("open_port:" + serial_path);
                mainActivity.getmSerialPort().OpenSerial(serial_path, baudrate,
                        dataBit, stopbitt, crc_num);
                // System.out.println(Getstopbit() + "!!!!!!!ceshi");
                DisplayToast("open " + serial_path + " by  " + baudrate
                        + " baurate success");
                Log.d(MainActivity.TAG, "openSerialPort");
                fd = mSerialPort.getFd();
                Log.d(MainActivity.TAG, "open fd=" + fd);
                // 使能发送按键
                mainActivity.sendButton.setEnabled(true);
                mainActivity.setReadSerialTask();
                // 开始读串口
                // setReadSerialTask();
                closeSerial.setEnabled(true);
                openSerial.setEnabled(false);
                b_Spinner.setEnabled(false);
                p_Spinner.setEnabled(false);
                s_Spinner.setEnabled(false);
                crc_Spinner.setEnabled(false);
                serial = 1;
            } catch (SecurityException e) {
                DisplayToast("open " + serial_path + " by  " + baudrate
                        + " baurate failed");

                e.printStackTrace();
            } catch (IOException e) {
                DisplayToast("open " + serial_path + " by  " + baudrate
                        + " baurate failed");
                e.printStackTrace();
            }
        }
        if (v == poweroff) {
            power_path = path_Spinner.getSelectedItem().toString();
            if (power_path.equals("MT GPIO")) {
                System.out.println("MT GPIO off");
                DevCtrl.MTGpioOff();
            } else {
                try {
                    DevCtrl.PowerOffDevice(power_off_write);
                    Toast.makeText(mContext, "close success",
                            Toast.LENGTH_SHORT).show();
                    // if (DevCtrl2 != null) {
                    // DevCtrl2.PowerOffDevice();
                    // }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            poweron.setEnabled(true);
            poweroff.setEnabled(false);
            path_Spinner.setEnabled(true);
            powercount = 0;
        }

        if (v == closeSerial) {
            mSerialPort.CloseSerial(fd);
            openSerial.setEnabled(true);
            closeSerial.setEnabled(false);
            b_Spinner.setEnabled(true);
            p_Spinner.setEnabled(true);
            s_Spinner.setEnabled(true);
            crc_Spinner.setEnabled(true);
            serial = 0;
            mainActivity.sendButton.setEnabled(false);

        }
        if (v == goback) {
            dismiss();
            mainActivity.ShowState();
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_LEFT:
                sendBroadcastKey("down");
                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                sendBroadcastKey("up");
                break;
            default:
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void sendBroadcastKey(String action) {
        Intent intent = new Intent("wrist.action." + action);
        mContext.sendBroadcast(intent);
    }

    public void DisplayToast(String str) {
        Toast toast = Toast.makeText(mContext, str, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, 0, 220);
        toast.show();
    }

    public void closeDev() {
        if (powercount > 0) {
            try {
                DevCtrl.PowerOffDevice(power_off_write);
                System.out.println("power_off_write" + power_off_write);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void setSerialPort(String path) {
        serial_path = "/dev/" + path;
    }

    @Override
    public void setBaurdrate(int baurdrate) {
        baudrate = baurdrate;
    }

    @Override
    public void setBaurdrateSpinner(int position) {
        p_Spinner.setSelection(position);
    }

    @Override
    public void setSerialPortSpinner(int position) {
        p_Spinner.setSelection(position);
    }

    public void closeSerial() {
        if (serial != 0) {
            mSerialPort.CloseSerial(fd);
        }
    }

    public int getSerial() {
        return serial;
    }

    public void setSerial(int serial) {
        this.serial = serial;
    }

    public int getPowercount() {
        return powercount;
    }

    public void setPowercount(int powercount) {
        this.powercount = powercount;
    }

    public int getBaudrate() {
        return baudrate;
    }

    public void setBaudrate(int baudrate) {
        this.baudrate = baudrate;
    }

    String getSerial_path() {
        return serial_path;
    }

    public void setSerial_path(String serial_path) {
        this.serial_path = serial_path;
    }

    String getPower_path() {
        return actual_path + " " + power_on_write;
    }

    public void setPower_path(String power_path) {
        this.power_path = power_path;
    }

    @Override
    public void setPower(String poweron, String poweroff) {
        this.power_off_write = poweroff;
        this.power_on_write = poweron;
        this.actual_path = "/sys/class/misc/mtgpio/pin";
    }

    @Override
    public void setPowerSpinner(int position) {
        path_Spinner.setSelection(position);
    }

}