package com.itnt.bootcamp;

import java.util.Calendar;
import java.util.Properties;

import com.itnt.bootcamp.model.RemoteTodoItem;
import com.itnt.bootcamp.persistence.AddTodoAsynkTask;
import com.itnt.bootcamp.persistence.UpdateTodoAsynkTask;
import com.itnt.bootcamp.resources.LoadAssetProperties;
import com.itnt.bootcamp.resources.Utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * 
 * @author GabiRadu
 * 
 */
public class UpdateRemoteTodoItemActivity extends Activity implements
		OnClickListener {

	private RemoteTodoItem item = null;
	private Properties properties;
	private Button doneBtn;
	private String url;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.remote_additem);
		Typeface customFont = Typeface.createFromAsset(
				UpdateRemoteTodoItemActivity.this.getAssets(),
				"fonts/AgendaLight.otf");

		// read properties file
		Resources resources = UpdateRemoteTodoItemActivity.this.getResources();
		LoadAssetProperties load = new LoadAssetProperties();
		properties = load.loadRESTApiFile(resources, "utils.properties",
				UpdateRemoteTodoItemActivity.this);
		
		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			item = (RemoteTodoItem) bundle.get("selectedItem");
		}
		// activate the keyboard on loading screen
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

		final EditText titleBox = (EditText) findViewById(R.id.remoteItemTitle);
		titleBox.setText(item.getTitle());

		TextView itemTitle = (TextView) findViewById(R.id.remoteItemTitletxt);
		itemTitle.setTypeface(customFont);

		doneBtn = (Button) findViewById(R.id.doneBtn);
		doneBtn.setOnClickListener(this);
		Button updateBtn = (Button) findViewById(R.id.createNewBtn);
		updateBtn.setText(getString(R.string.edit_dialog_ok_button));
		
		url = properties.getProperty("UPDATE_ITEM") + item.getId() + ".json";

		if (Utils.isNetworkAvailable(UpdateRemoteTodoItemActivity.this)) {
			doneBtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {

					System.out.println("update url: " + url);
					item.setTitle(titleBox.getText().toString());
					item.setDone(true);

					UpdateTodoAsynkTask updateTask = new UpdateTodoAsynkTask();
					updateTask.URL = url;
					updateTask.item = item;
					updateTask.context = UpdateRemoteTodoItemActivity.this;
					updateTask.execute();
				}
			});
			updateBtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					item.setTitle(titleBox.getText().toString());
					item.setDone(false);

					UpdateTodoAsynkTask updateTask = new UpdateTodoAsynkTask();
					updateTask.URL = url;
					updateTask.item = new RemoteTodoItem(Calendar.getInstance()
							.getTime().toString(), false, titleBox.getText()
							.toString(), Calendar.getInstance().getTime()
							.toString());
					updateTask.context = UpdateRemoteTodoItemActivity.this;
					updateTask.execute();
				}
			});
		}
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent intent = new Intent(UpdateRemoteTodoItemActivity.this,
					TabsActivity.class);
			intent.putExtra("tab", Utils.TAB_REMOTE_TODO);
			startActivity(intent);
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}
}
