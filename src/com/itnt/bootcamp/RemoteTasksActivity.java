package com.itnt.bootcamp;

import java.util.Properties;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.itnt.bootcamp.model.RemoteTodoItem;
import com.itnt.bootcamp.persistence.DeleteTodoAsynkTask;
import com.itnt.bootcamp.persistence.RemoteAdapter;
import com.itnt.bootcamp.persistence.ViewAllTodoItemsAsynkTask;
import com.itnt.bootcamp.resources.LoadAssetProperties;
import com.itnt.bootcamp.resources.Utils;

/**
 * 
 * @author GabiRadu
 * 
 */
public class RemoteTasksActivity extends Activity implements OnClickListener {

	private Properties properties;

	// UI Elements
	private ListView todoListView;
	public RemoteAdapter listAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.remote_main);

		// read properties file
		Resources resources = RemoteTasksActivity.this.getResources();
		LoadAssetProperties load = new LoadAssetProperties();
		properties = load.loadRESTApiFile(resources, "utils.properties",
				RemoteTasksActivity.this);

		// attaches the custom adapter to the list
		todoListView = (ListView) findViewById(R.id.todoList);

		ImageView addBtn = (ImageView) findViewById(R.id.addRemoteItemButton);
		addBtn.setOnClickListener(this);

		if (Utils.isNetworkAvailable(RemoteTasksActivity.this)) {
			String url = properties.getProperty("SHOW_ALL_ITEMS");
			System.out.println("show url: " + url);
			ViewAllTodoItemsAsynkTask viewAllTask = new ViewAllTodoItemsAsynkTask();
			viewAllTask.adapter = listAdapter;
			viewAllTask.context = RemoteTasksActivity.this;
			viewAllTask.todoListView = todoListView;
			viewAllTask.URL = url;
			viewAllTask.execute();

			// sets the edit action on the list click
			todoListView.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> p, View v, int pos,
						long id) {
					RemoteTodoItem remoteItem = (RemoteTodoItem) p.getItemAtPosition(pos);
					System.out.println("selected remote item: " + remoteItem);
					Intent intent = new Intent(RemoteTasksActivity.this, UpdateRemoteTodoItemActivity.class);
					intent.putExtra("selectedItem", remoteItem);
					startActivity(intent);
					finish();
				}
			});
			
			// open a menu on long click
			todoListView.setOnItemLongClickListener(new OnItemLongClickListener() {
				@Override
				public boolean onItemLongClick(AdapterView<?> p, View v, int pos,
						long id) {
					(new ChooseBox((RemoteTodoItem) p.getItemAtPosition(pos))).show();
					return true;
				}
			});

		} else {
			Toast.makeText(RemoteTasksActivity.this,
					getString(R.string.noconnectivity), Toast.LENGTH_SHORT)
					.show();
		}

	}

	/**
	 * Represents a dialog in which the user can choose if edit or delete the
	 * current item.
	 */
	private class ChooseBox extends AlertDialog {

		public ChooseBox(final RemoteTodoItem item) {
			super(RemoteTasksActivity.this);

			// set the box title's
			setTitle(R.string.choose_box_title);

			// sets what to do when the POSITIVE button ("Edit") is clicked
			setButton(BUTTON_POSITIVE,
					getString(R.string.choose_box_positive_button),
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog,
								int whichButton) {
							Intent intent = new Intent(RemoteTasksActivity.this, UpdateRemoteTodoItemActivity.class);
							intent.putExtra("selectedItem", item);
							startActivity(intent);
							finish();
						}
					});

			// sets what to do when the NEGATIVE button ("Delete") is clicked
			setButton(BUTTON_NEGATIVE,
					getString(R.string.choose_box_negative_button),
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog,
								int whichButton) {
							DeleteTodoAsynkTask deleteTask = new DeleteTodoAsynkTask();
							deleteTask.URL =  properties.getProperty("DELETE_ITEM");
							deleteTask.id = item.getId();
							deleteTask.todoListView = todoListView;
							deleteTask.context = RemoteTasksActivity.this;
							deleteTask.execute();
						}
					});
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.addRemoteItemButton:
			Intent addIntent = new Intent(RemoteTasksActivity.this,
					AddRemoteTodoItemActivity.class);
			addIntent.setAction("addremote");
			startActivity(addIntent);
			finish();
			break;
		default:
			break;
		}

	}
}