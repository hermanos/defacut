package com.itnt.bootcamp.persistence;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
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
 * 
 * @author GabiRadu
 * 
 */
public class UpdateTodoAsynkTask extends AsyncTask<String, String, JSONObject> {

	public String URL = null;
	public RemoteTodoItem item = null;
	public Activity context;
	private boolean success = false;

	@Override
	protected void onPostExecute(JSONObject result) {
		super.onPostExecute(result);
		if (success) {
			Toast.makeText(context, "We updated your item", Toast.LENGTH_SHORT)
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
		if (item == null) {
			Toast.makeText(context, "Error showing selected item",
					Toast.LENGTH_SHORT).show();
			context.finish();
		} else {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPut httpput = null;
			try {
				httpput = new HttpPut(new URI(URL));
				httpput.setHeader("Content-type", "application/json");
				httpput.setHeader("Accept", "application/json");
				JSONObject obj = new JSONObject();
				obj.put("created_at", item.getCreated());
				obj.put("updated_at", item.getUpdated());
				obj.put("done", item.isDone());
				obj.put("id", item.getId());
				obj.put("title", item.getTitle());
				httpput.setEntity(new StringEntity(obj.toString(), "UTF-8"));

				// Execute HTTP Post Request
				HttpResponse response = httpclient.execute(httpput);

				// TODO repair this
				if (response.getStatusLine().getStatusCode() == HttpStatus.SC_CREATED
						|| response.getStatusLine().getStatusCode() == HttpStatus.SC_OK
						|| response.getStatusLine().getStatusCode() == HttpStatus.SC_NO_CONTENT) {
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
		}
		return null;
	}
}