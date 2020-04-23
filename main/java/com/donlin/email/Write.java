package com.donlin.email;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Write extends AppCompatActivity {

	ButtonClick bc;
	EditText[] allEditText;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_write);
		initVariable();
		initButtons();
	}
	//when the activity is pause, all data will be preserved
	@Override
	public void onPause() {
		super.onPause();
		new FileOperation(getFilesDir().toString()).saveFile(bc.get());
		Toast.makeText(getApplicationContext(), "data preserved", Toast.LENGTH_SHORT).show();
	}
	//when te activity is resume, data will be loaded again
	public void onResume() {
		super.onResume();
		String[] textInfo;
		textInfo=new FileOperation(getFilesDir().toString()).readFile();
		outPutLoadingInfo(textInfo);
		for(int i=0;i<6;i++) {
			allEditText[i].setText(textInfo[i]);
		}
	}
	/*
	@param a string array that need to show the debug information
	output the number of data that loaded from the fire
	 */
	public void outPutLoadingInfo(String[] text){
		int count=0;
		for(int i=0;i<6;i++) {
			if(text[i]==null)
				continue;
			if(text[i].length()>0)
				count++;
		}
		Toast.makeText(getApplicationContext(), count+" data has loaded ", Toast.LENGTH_SHORT).show();
	}

	//find all edit text and store them into an array
	private void initVariable() {
		allEditText=new EditText[6];
		
		allEditText[0]=(EditText)findViewById(R.id.wFrom);
		allEditText[1]=(EditText)findViewById(R.id.wTo);
		allEditText[2]=(EditText)findViewById(R.id.wBody);
		allEditText[3]=(EditText)findViewById(R.id.wCC);
		allEditText[4]=(EditText)findViewById(R.id.wBCC);
		allEditText[5]=(EditText)findViewById(R.id.wSub);
		
		bc=new ButtonClick(allEditText);
	}

	//initialize all three buttons
	private void initButtons() {
		//when the real-send button clicked, the content inside the email will be sent
		Button realSend = (Button) findViewById(R.id.buton_realsend);
		realSend.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				String[] allEmailInfo = bc.get();
				String emailToString = allEmailInfo[1];
				String subjectString = allEmailInfo[5];
				String bodyString = allEmailInfo[2];
				String[] ccString = {allEmailInfo[3]};
				String[] bccString = {allEmailInfo[4]};

				Intent data = new Intent(Intent.ACTION_SENDTO);
				data.setData(Uri.parse("mailto:" + emailToString));
				data.putExtra(Intent.EXTRA_CC, ccString);
				data.putExtra(Intent.EXTRA_BCC, bccString);
				data.putExtra(Intent.EXTRA_SUBJECT, subjectString);
				data.putExtra(Intent.EXTRA_TEXT, bodyString);
				startActivity(data);
			}
		});

		//when the send button clicked, the read activity will start
		Button send = (Button) findViewById(R.id.button_send);

		send.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(Write.this, Read.class);
				i.putExtra("mail", bc.get());
				startActivity(i);
			}
		});

		//when the clear button clicked, all data preserved inside the edit text will be deleted
		Button clear = (Button) findViewById(R.id.buton_clear);
		clear.setOnClickListener(bc);
	}
}
