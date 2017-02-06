package com.example.magicuhf;

import android.app.Application;
import android.hardware.uhf.magic.DevBeep;
import android.hardware.uhf.magic.reader;
import android.util.Log;

public class App extends Application {
	static String C5U = "/dev/ttyMT1";
	static String C7DU = "/dev/ttyMT2";
	static String CM550 = "/dev/ttyMT2";
	static String CM398M = "/dev/ttyMSM0";
	@Override
	public void onCreate(){
		super.onCreate();
		InitUHF();
	}
	public void InitUHF(){
		android.hardware.uhf.magic.reader.init(C5U);
		android.hardware.uhf.magic.reader.Open(C5U);
		if (reader.SetTransmissionPower(1950) != 0x11) {
			if (reader.SetTransmissionPower(1950) !=0x11) {
				reader.SetTransmissionPower(1950);
			}
		}
		DevBeep.init(App.this);

	}

}
