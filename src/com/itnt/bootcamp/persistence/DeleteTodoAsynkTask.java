package com.itnt.bootcamp.persistence;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.Toast;

import com.itnt.bootcamp.TabsActivity;
import com.itnt.bootcamp.model.RemoteTodoItem;
import com.itnt.bootcamp.resources.Utils;

/**
 * 
 * @author GabiRadu
 * 
 */
public class DeleteTodoAsynkTask extends AsyncTask<String, String, JSONObject> {

	public String URL = null;
	public RemoteTodoItem item;
	public Activity context;
	private boolean success = false;
	public long id = -1;
	public ListView todoListView;

	@Override
	protected void onPostExecute(JSONObject result) {
		super.onPostExecute(result);
		if (success) {
			Toast.makeText(context, "Item deleted successfully!",
					Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(context, TabsActivity.class);
			intent.putExtra("tab", Utils.TAB_REMOTE_TODO);
			context.startActivity(intent);
			context.finish();
		}
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected JSONObject doInBackground(String... params) {
		HttpClient httpclient = new DefaultHttpClient();
		HttpDelete httpdelete = null;
		try {
			httpdelete = new HttpDelete(new URI(URL + id + ".json"));
			httpdelete.setHeader("Content-type", "application/json");
			httpdelete.setHeader("Accept", "application/json");

			// Execute HTTP Post Request
			HttpResponse response = httpclient.execute(httpdelete);

			// TODO repair this
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_CREATED
					|| response.getStatusLine().getStatusCode() == HttpStatus.SC_OK
					|| response.getStatusLine().getStatusCode() == HttpStatus.SC_NO_CONTENT) {
				success = true;
			}

		} catch (URISyntaxException e1) {
			e1.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}