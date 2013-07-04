package com.itnt.bootcamp.persistence;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.itnt.bootcamp.R;
import com.itnt.bootcamp.model.RemoteTodoItem;
import com.itnt.bootcamp.model.TodoItem;

public class RemoteAdapter extends ArrayAdapter<RemoteTodoItem> {

	private List<RemoteTodoItem> listOfItems;

	public RemoteAdapter(Context context, int textViewResourceId,
			List<RemoteTodoItem> listOfItems) {
		super(context, textViewResourceId, listOfItems);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return getViewOptimize(position, convertView, parent);
	}

	@SuppressLint("NewApi")
	private View getViewOptimize(final int position, View convertView,
			ViewGroup parent) {
		ViewHolder viewHolder = null;

		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.remote_list_row, null);

			viewHolder = new ViewHolder();
			viewHolder.title = (TextView) convertView
					.findViewById(R.id.remoteItemTitle);
			viewHolder.finishedTask = (ImageView) convertView
					.findViewById(R.id.remoteItemImg);
			viewHolder.updatedAt = (TextView) convertView
					.findViewById(R.id.remoteItemDate);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		RemoteTodoItem item = getItem(position);
		System.out.println("item : " + item);

		viewHolder.title.setText(item.getTitle() != null ? item.getTitle() : " ");
		viewHolder.finishedTask.setImageResource(item.isDone() ? R.drawable.done : R.drawable.priority_high);
		viewHolder.updatedAt.setText("Last updated: " + item.getUpdated() != null ?  item.getUpdated() : "-");

		return convertView;
	}

	private class ViewHolder {
		public TextView title;
		public ImageView finishedTask;
		public TextView updatedAt;
	}

}
