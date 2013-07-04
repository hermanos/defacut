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
import com.itnt.bootcamp.model.TodoItem;

public class CustomAdapter extends ArrayAdapter<TodoItem> {
	
	private List<TodoItem> listOfItems;
	
	public CustomAdapter(Context context, int textViewResourceId,
			List<TodoItem> listOfItems) {
		super(context, textViewResourceId, listOfItems);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return getViewOptimize(position, convertView, parent);
	}

	@SuppressLint("NewApi")
	private View getViewOptimize(final int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.list_row, null);
			
			viewHolder = new ViewHolder();
			viewHolder.title = (TextView) convertView.findViewById(R.id.listItemTitle);
			viewHolder.description = (TextView) convertView.findViewById(R.id.listItemDescription);
			viewHolder.priority = (ImageView) convertView.findViewById(R.id.priorityImage);
			
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		TodoItem item = getItem(position);
		
		int priorityImageId;
		switch (item.getPriority()) {
		case DONE:
			priorityImageId = R.drawable.done;
			break;
		case LOW:
			priorityImageId = R.drawable.priority_low;
			break;
		case MEDIUM:
			priorityImageId = R.drawable.priority_medium;
			break;
		case HIGH:
			priorityImageId = R.drawable.priority_high;
			break;
		default:
			priorityImageId = R.drawable.priority_medium;
			break;
		}

		viewHolder.title.setText(item.getTitle());
		viewHolder.description.setText(item.getDescription());
		viewHolder.priority.setImageResource(priorityImageId);

		return convertView;
	}

	private class ViewHolder {
		public TextView title;
		public TextView description;
		public ImageView priority;
	}
	
}
