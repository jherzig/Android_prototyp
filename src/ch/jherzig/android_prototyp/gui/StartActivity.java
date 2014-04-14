package ch.jherzig.android_prototyp.gui;

import ch.jherzig.android_prototyp.R;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class StartActivity extends Activity {

	private Button btnServiceActivity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		
		
		btnServiceActivity = (Button) findViewById(R.id.sf_service_starten);

		btnServiceActivity.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(final View sfView) {
				Intent i = new Intent();
				i.setClass(getApplicationContext(), ServiceActivity.class);
				startActivity(i);
			}

		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.start, menu);
		return true;
	}

}
