package com.itnt.bootcamp.resources;

import android.content.Context;
import android.view.View;

import com.itnt.bootcamp.model.TodoItem;

public class _EditDialog extends _CustomDialog {

	public _EditDialog(Context context, String title, String okButtonText,
			final TodoItem t) {
		super(context, "Edit", "Update");

		titleBox.setText(t.getTitle());
		descriptionBox.setText(t.getDescription());
		setPrioritySpinner(t.getPriority());
		
		okButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				t.setTitle(titleBox.getText().toString());
				t.setDescription(descriptionBox.getText().toString());
				t.setPriority(selectedPriority());
				
				dismiss();
			}
			
		});
	}

}
