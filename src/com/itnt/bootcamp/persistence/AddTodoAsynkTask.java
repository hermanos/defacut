package com.itnt.bootcamp.persistence;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.itnt.bootcamp.TabsActivity;
import com.itnt.bootcamp.model.RemoteTodoItem;
import com.itnt.bootcamp.resources.Utils;

/**
 * O
 * 
 * @author GabiRadu
 * 
 */
public class AddTodoAsynkTask extends AsyncTask<String, String, JSONObject> {

	public String URL = null;
	public RemoteTodoItem item;
	public Activity context;
	private boolean success = false;

	@Override
	protected void onPostExecute(JSONObject result) {
		super.onPostExecute(result);
		if (success) {
			Toast.makeText(context, "We created a new item", Toast.LENGTH_SHORT)
					.show();
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
		HttpPost httppost = null;
		try {
			httppost = new HttpPost(new URI(URL));
			httppost.setHeader("Content-type", "application/json");
			httppost.setHeader("Accept", "application/json");
			JSONObject obj = new JSONObject();
			obj.put("created_at", item.getCreated());
			obj.put("updated_at", item.getUpdated());
			obj.put("done", item.isDone());
			obj.put("title", item.getTitle());
			httppost.setEntity(new StringEntity(obj.toString(), "UTF-8"));

			// Execute HTTP Post Request
			HttpResponse response = httpclient.execute(httppost);

			// TODO repair this
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_CREATED
					|| response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				success = true;
			}

		} catch (URISyntaxException e1) {
			e1.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}