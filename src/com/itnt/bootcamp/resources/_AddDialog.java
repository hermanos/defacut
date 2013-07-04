package com.itnt.bootcamp.resources;

import android.content.Context;
import android.view.View;

import com.itnt.bootcamp.model.TodoItem;

public class _AddDialog extends _CustomDialog {

	public _AddDialog(Context context, String title, String okButtonText,
			final TodoItem t) {
		super(context, "Add a new TODO item", "Add");

		// defines what to do when the positive button (okButton here) is clicked
		okButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// sets the new values and "dismiss" the dialog
				t.setTitle(titleBox.getText().toString());
				t.setDescription(descriptionBox.getText().toString());
				t.setPriority(selectedPriority());

				dismiss();
			}

		});
	}

}
