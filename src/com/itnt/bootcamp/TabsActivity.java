package com.itnt.bootcamp;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

import com.itnt.bootcamp.resources.Utils;

/**
 * 
 * @author GabiRadu
 * 
 */
public class TabsActivity extends TabActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tabs);

		TabHost tabHost = getTabHost();

		// Tab for LocalStorage TODOs
		TabSpec localStorage = tabHost.newTabSpec("LocalStorage");
		// setting Title and Icon for the Tab
		localStorage.setIndicator(getString(R.string.localtodo), getResources()
				.getDrawable(R.drawable.add));
		Intent intent = new Intent(TabsActivity.this, LocalTasksActivity.class);
		localStorage.setContent(intent);

		// Tab for remote TODOs
		TabSpec remote = tabHost.newTabSpec("Remote");
		remote.setIndicator(getString(R.string.remotetodo), getResources()
				.getDrawable(R.drawable.server));
		Intent remoteIntent = new Intent(TabsActivity.this,
				RemoteTasksActivity.class);
		remote.setContent(remoteIntent);

		// Adding all TabSpec to TabHost
		tabHost.addTab(localStorage);
		tabHost.addTab(remote);

		// check to see if there is a selected tab preferred from a previous activity
		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			int tab = bundle.getInt("tab");
			if (tab == Utils.TAB_LOCAL_TODO) {
				tabHost.setCurrentTab(0);
			} else if (tab == Utils.TAB_REMOTE_TODO) {
				tabHost.setCurrentTab(1);
			}
		}

	}
}