package com.itnt.bootcamp.persistence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.itnt.bootcamp.R;
import com.itnt.bootcamp.model.RemoteTodoItem;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

/**
 * 
 * @author GabiRadu
 * 
 */
public class ViewAllTodoItemsAsynkTask extends
		AsyncTask<String, String, JSONObject> {

	public String URL = null;
	public Activity context;
	static InputStream is = null;
	static JSONArray jObjList = null;
	static String json = "";
	public RemoteAdapter adapter;
	public ListView todoListView;
	private List<RemoteTodoItem> listOfItems = new ArrayList<RemoteTodoItem>();

	@SuppressLint("NewApi")
	@Override
	protected JSONObject doInBackground(String... params) {
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(URL);
		try {
			HttpResponse response = client.execute(request);

			HttpEntity entity = response.getEntity();
			if (entity != null) {
				System.out.println("entity: " + entity);
				InputStream in = entity.getContent();
				json = readStream(in);
				System.out.println("json: " + json);

				jObjList = new JSONArray(json);
				System.out.println("json list: " + jObjList);
			}
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPostExecute(JSONObject result) {
		super.onPostExecute(result);

		System.out.println("on post loading...");
		if (jObjList != null) {
			for (int i = 0; i < jObjList.length(); i++) {
				try {
					System.out.println("objlist " + jObjList.get(i));
					RemoteTodoItem todoItem = new Gson().fromJson(
							jObjList.get(i).toString(), RemoteTodoItem.class);
					System.out.println("todoItem : " + todoItem);
					listOfItems.add(todoItem);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			// creates the custom adapter
			adapter = new RemoteAdapter(context, R.id.itemTitle, listOfItems);
			System.out.println("adapter: " + adapter);

			// attaches the custom adapter to the list
			todoListView.setAdapter(adapter);
		}

	}

	protected void onPreExecute() {
		super.onPreExecute();
	}

	private String readStream(InputStream is) {
		try {
			Log.i("Logging out *is* before beffered reader", is.toString());
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "utf-8"), 8);
			Log.i("Logging out *is* after beffered reader", is.toString());
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			json = sb.toString();
			return json;
		} catch (Exception e) {
			Log.e("Buffer Error", "Error converting result " + e.toString());
		}
		return null;
	}
}