package ch.jherzig.android_prototyp.gui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import ch.jherzig.android_prototyp.R;
import ch.jherzig.android_prototyp.service.TestService;
import ch.jherzig.android_prototyp.service.TestServiceMessage;

public class ServiceActivity extends Activity {

	private TestService.TestBinder mBinder;
	private TestServiceMessage.TestBinder mBinderServiceMesageHandler;
	private Button btnErgebnis;
	private Button btnServiceMesageHandler;
	private Button btnDelete;

	
	//Callback mittels Message Objekt
	private final Handler RunnableMessageHandler = new Handler();
	@SuppressLint("HandlerLeak")
	private final Handler mesageHandler = new Handler() {
		// Diese Methode wird aufgerufen wenn der Servce ein Message Objekt als
		// Callback in die Messag Queu gestellt hat.
		public void handleMessage(Message msg) {
			final Bundle bundel = msg.getData();
			final long ergebnis = bundel.getLong("ergebnis");
			final TextView fldErgenisMsg = (TextView) findViewById(R.id.tx_ergebnis_Msg);
			fldErgenisMsg.setText(String.valueOf(ergebnis));
			super.handleMessage(msg);
		}
	};

	private ServiceConnection mServiceMesageHandler = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			mBinderServiceMesageHandler = (TestServiceMessage.TestBinder) service;
			mBinderServiceMesageHandler
					.setActivityCallbackHandler(mesageHandler);
		}
	};

	
	// Callback mittels Runnable Objekt
	public class MeinRunnable implements Runnable {
		public long ergebnis;

		@Override
		public void run() {
			final TextView fldErgebnis = (TextView) findViewById(R.id.tx_ergebnis);
			fldErgebnis.setText(String.valueOf(ergebnis));
		}
	}

	// Erstellt eine verbindung zum RunnableMessageHandler Service der über
	// mBinder angesprochenw erden kann
	private ServiceConnection mServiceConnection = new ServiceConnection() {
		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			mBinder = (TestService.TestBinder) service;
			mBinder.setActivityCallbackHandler(RunnableMessageHandler);
			mBinder.setRunnable(new MeinRunnable());
			// mBinder.berechneErgebnis();
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_runnable_objekt);

		//Runabel Service starten
		final Intent intent = new Intent(this, TestService.class);
		bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
		
		//Message Servce Starten
		final Intent intentMSG = new Intent(this, TestServiceMessage.class);
		bindService(intentMSG, mServiceMesageHandler, Context.BIND_AUTO_CREATE);
		

		btnErgebnis = (Button) findViewById(R.id.sf_ergebnis);
		btnServiceMesageHandler = (Button) findViewById(R.id.sf_ergebnis_msg_service);
		btnDelete = (Button) findViewById(R.id.sf_delete);

		btnErgebnis.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				shortToast("mBinder.berechneErgebnis() Läft");
				mBinder.berechneErgebnis();
			}
		});

		btnServiceMesageHandler.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				shortToast("ServiceMesageHandler Läft");
				mBinderServiceMesageHandler.berechneErgebnis();
			}
		});

		btnDelete.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				final TextView fldErgebnis = (TextView) findViewById(R.id.tx_ergebnis);
				fldErgebnis.setText("");
				final TextView fldErgebnisMsg = (TextView) findViewById(R.id.tx_ergebnis_Msg);
				fldErgebnisMsg.setText("");
			}
		});

	}

	private void shortToast(String text) {
		Context context = getApplicationContext();
		int duration = Toast.LENGTH_SHORT;

		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
	}

}
