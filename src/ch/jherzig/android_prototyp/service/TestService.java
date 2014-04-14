package ch.jherzig.android_prototyp.service;

import ch.jherzig.android_prototyp.gui.ServiceActivity;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;

public class TestService extends Service {
	private Handler mCallbackHandler;
	private ServiceActivity.MeinRunnable mRunnable;

	private final IBinder mBinder = new TestBinder();

	public class TestBinder extends Binder {

		public void setActivityCallbackHandler(final Handler callback) {
			mCallbackHandler = callback;
		}

		public void setRunnable(
				final ServiceActivity.MeinRunnable runnable) {
			mRunnable = runnable;
		}

		public void berechneErgebnis() {
			new Thread() {
				public void run() {
					mRunnable.ergebnis = _berechneErgebnis();
					mCallbackHandler.post(mRunnable);
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
