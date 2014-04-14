package ch.jherzig.android_prototyp.service;


import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

public class TestServiceMessage extends Service {
	private Handler mCallbackHandler;

	private final IBinder mBinder = new TestBinder();

	public class TestBinder extends Binder {

		public void setActivityCallbackHandler(final Handler callback) {
			mCallbackHandler = callback;
		}


		public void berechneErgebnis() {
			new Thread() {
				public void run() {
					long ergebnis = _berechneErgebnis();
					
					final Message msg = new Message();
					final Bundle bundle = new Bundle();
					bundle.putLong("ergebnis", ergebnis);
					msg.setData(bundle);
					
					mCallbackHandler.sendMessage(msg);
				}
			}.start();
		}
	}

	private long _berechneErgebnis() {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 42;
	}

	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}

}
