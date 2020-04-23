package com.donlin.email;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class ButtonClick implements OnClickListener{
	public EditText[] allEditText;

	public ButtonClick(EditText[] e) {
		allEditText=e;
	}
	
	public void onClick(View v) {
		for(int i=0;i<allEditText.length;i++)
			allEditText[i].setText(null);
	}

	//get all information from files
	public String[] get() {
		String[] result=new String[6];
		for(int i=0;i<result.length;i++)
			result[i]=allEditText[i].getText().toString();
		return result;
	}
	
}
