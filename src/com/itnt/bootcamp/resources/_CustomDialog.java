package com.itnt.bootcamp.resources;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.itnt.bootcamp.R;
import com.itnt.bootcamp.R.id;
import com.itnt.bootcamp.R.layout;
import com.itnt.bootcamp.model.Priority;

public class _CustomDialog extends Dialog {
	// UI ELEMENTS
	protected final TextView dialogTitle;
	protected final EditText titleBox;
	protected final EditText descriptionBox;
	protected final Button okButton;
	protected final Button cancelButton;
	protected final Spinner prioritySpinner;

	// current selected priority
	private Priority curPriority;

	public _CustomDialog(Context context, String title, String okButtonText) {
		super(context);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.custom_dialog);

		dialogTitle = (TextView) findViewById(R.id.customDialogTitle);
		dialogTitle.setText(title);

		titleBox = (EditText) findViewById(R.id.thingTitle);
		descriptionBox = (EditText) findViewById(R.id.thingDescription);

		okButton = (Button) findViewById(R.id.okButton);
		okButton.setText(okButtonText);

		cancelButton = (Button) findViewById(R.id.cancelButton);
		cancelButton.setOnClickListener(new android.view.View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
			}
		});

		curPriority = Priority.LOW;
		prioritySpinner = (Spinner) findViewById(R.id.prioritySpinner);
		prioritySpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				switch (position) {
				case 0:
					curPriority = Priority.LOW;
					break;
				case 1:
					curPriority = Priority.MEDIUM;
					break;
				default:
					curPriority = Priority.HIGH;
					break;
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// DO NOTHING
			}
		});
	}

	protected void setPrioritySpinner(Priority p) {
		this.curPriority = p;
		prioritySpinner.setSelection(p.ordinal());
	}
	
	protected Priority selectedPriority() {
		return curPriority;
	}
}
