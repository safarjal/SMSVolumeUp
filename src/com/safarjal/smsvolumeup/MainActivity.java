package com.safarjal.smsvolumeup;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {
	EditText smsEditText;
	SharedPreferences settings;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		smsEditText = (EditText) findViewById(R.id.smsEditText);

		settings = PreferenceManager.getDefaultSharedPreferences(this);

		putValueInEditText();
	}
	public void onClickSaveButton(View view){
		if(smsEditText.getText().toString().equals("")){
			toast("Please do not leave the value empty");
		}else{
			SharedPreferences.Editor editor = settings.edit();
			editor.putString("password", smsEditText.getText().toString());
			editor.commit();
			toast("Save successful");
		}
	}


	private void putValueInEditText() {
		String password;
		password = settings.getString("password", "VolumeUp");
		smsEditText.setText(password);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	public void toast(String toasttext){
		Context context;
		context = getApplicationContext();
		CharSequence text = toasttext;
		int duration = Toast.LENGTH_SHORT;
		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
	}

}
