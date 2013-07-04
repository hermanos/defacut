package com.itnt.bootcamp;

import java.util.Calendar;
import java.util.Properties;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.itnt.bootcamp.model.RemoteTodoItem;
import com.itnt.bootcamp.persistence.AddTodoAsynkTask;
import com.itnt.bootcamp.resources.LoadAssetProperties;
import com.itnt.bootcamp.resources.Utils;

public class AddRemoteTodoItemActivity extends Activity {

	private Typeface customFont;
	public String URL;
	private Properties properties;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.remote_additem);

		customFont = Typeface.createFromAsset(
				AddRemoteTodoItemActivity.this.getAssets(),
				"fonts/AgendaLight.otf");

		// read properties file
		Resources resources = AddRemoteTodoItemActivity.this.getResources();
		LoadAssetProperties load = new LoadAssetProperties();
		properties = load.loadRESTApiFile(resources, "utils.properties",
				AddRemoteTodoItemActivity.this);

		// activate the keyboard on loading screen
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

		final EditText titleBox = (EditText) findViewById(R.id.remoteItemTitle);

		TextView itemTitle = (TextView) findViewById(R.id.remoteItemTitletxt);
		itemTitle.setTypeface(customFont);

		Button doneBtn = (Button) findViewById(R.id.doneBtn);
		Button createNewBtn = (Button) findViewById(R.id.createNewBtn);

		if (Utils.isNetworkAvailable(AddRemoteTodoItemActivity.this)) {
			doneBtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (titleBox.getText().toString().length() > 0) {
						String url = properties.getProperty("ADD_NEW_ITEM");
						System.out.println("add new url: " + url);

						AddTodoAsynkTask addTask = new AddTodoAsynkTask();
						addTask.URL = url;
						addTask.item = new RemoteTodoItem(Calendar
								.getInstance().getTime().toString(), true,
								titleBox.getText().toString(), Calendar
										.getInstance().getTime().toString());
						addTask.context = AddRemoteTodoItemActivity.this;
						addTask.execute();
					} else {
						Toast.makeText(AddRemoteTodoItemActivity.this,
								getString(R.string.addcontent),
								Toast.LENGTH_SHORT).show();
						Intent intent = new Intent(
								AddRemoteTodoItemActivity.this,
								TabsActivity.class);
						intent.putExtra("tab", Utils.TAB_REMOTE_TODO);
						startActivity(intent);
						finish();
					}
				}
			});
			createNewBtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (titleBox.getText().toString().length() > 0) {
						String url = properties.getProperty("ADD_NEW_ITEM");
						System.out.println("add new url: " + url);

						AddTodoAsynkTask addTask = new AddTodoAsynkTask();
						addTask.URL = url;
						addTask.item = new RemoteTodoItem(Calendar
								.getInstance().getTime().toString(), false,
								titleBox.getText().toString(), Calendar
										.getInstance().getTime().toString());
						addTask.context = AddRemoteTodoItemActivity.this;
						addTask.execute();

					} else {
						Toast.makeText(AddRemoteTodoItemActivity.this,
								getString(R.string.addcontent),
								Toast.LENGTH_SHORT).show();
						Intent intent = new Intent(
								AddRemoteTodoItemActivity.this,
								TabsActivity.class);
						intent.putExtra("tab", Utils.TAB_REMOTE_TODO);
						startActivity(intent);
						finish();
					}
				}
			});
		}
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			startActivity(new Intent(AddRemoteTodoItemActivity.this,
					TabsActivity.class));
			finish();
		}
		return false;
	}

}