package com.itnt.bootcamp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * 
 * @author GabiRadu
 * 
 */
public class AddTodoItemActivity extends Activity {

	private Typeface customFont;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_additem);

		customFont = Typeface.createFromAsset(
				AddTodoItemActivity.this.getAssets(), "fonts/AgendaLight.otf");

		// activate the keyboard on loading screen
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

		
		// get action of this intent
		final String action = getIntent().getAction();
		
		// load GUI
		final Button okButton = (Button) findViewById(R.id.okButton);
		final EditText titleBox = (EditText) findViewById(R.id.addItemTitleTxt);
		final EditText descrBox = (EditText) findViewById(R.id.addItemDescriptionTxt);
		final Spinner prioritySpinner = (Spinner) findViewById(R.id.prioritySpinner);

		TextView itemTitle = (TextView) findViewById(R.id.addItemTitle);
		itemTitle.setTypeface(customFont);
		TextView itemDescription = (TextView) findViewById(R.id.addItemDescription);
		itemDescription.setTypeface(customFont);
		TextView itemPriority = (TextView) findViewById(R.id.addItemPriority);
		itemPriority.setTypeface(customFont);

		okButton.setTypeface(customFont);

		if (action.equals("add")) {
			setTitle(R.string.add_dialog_title);
			okButton.setText(R.string.add_dialog_ok_button);
		} else if (action.equals("edit")) {
			setTitle(R.string.edit_dialog_title);
			okButton.setText(R.string.edit_dialog_ok_button);

			titleBox.setText(getIntent().getExtras().getString("title"));
			descrBox.setText(getIntent().getExtras().getString("description"));
			prioritySpinner.setSelection(getIntent().getExtras().getInt(
					"priority"));
		} else if (action.equals("addremote")) {
			setTitle(R.string.add_dialog_title);
			okButton.setText(R.string.add_dialog_ok_button);
		} else {
			setResult(RESULT_CANCELED);
			finish();
		}

		okButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent res = AddTodoItemActivity.this.getIntent();

				if (action.equals("edit")) {
					res.putExtra("pos", getIntent().getExtras().getInt("pos"));
				}

				res.putExtra("title", titleBox.getText().toString());
				res.putExtra("description", descrBox.getText().toString());
				res.putExtra("priority",
						prioritySpinner.getSelectedItemPosition());

				setResult(RESULT_OK, res);
				finish();
			}
		});

	}
}