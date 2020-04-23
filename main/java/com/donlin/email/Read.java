package com.donlin.email;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Read extends AppCompatActivity {
	//all textViews are stored in one array
	public static TextView[] allText;

	//find all TextView and store them in to an array
	//put the information into the textview
	private void initData() {
		allText=new TextView[6];

		allText[0]=(TextView)findViewById(R.id.rFrom);
		allText[1]=(TextView)findViewById(R.id.rTo);
		allText[2]=(TextView)findViewById(R.id.rBody);
		allText[3]=(TextView)findViewById(R.id.rCC);
		allText[4]=(TextView)findViewById(R.id.rBCC);
		allText[5]=(TextView)findViewById(R.id.rSub);

		Bundle extras=getIntent().getExtras();
		String[] data=extras.getStringArray("mail");

		for(int i=0;i<allText.length;i++)
			allText[i].setText(data[i]);
	}
	//when the activity is resume, all stored data will be loaded again
	@Override
	public void onResume() {
		super.onResume();
		String[] textInfo;
		textInfo=new FileOperation(getFilesDir().toString()).readFile();
		outPutLoadingInfo(textInfo);
		for(int i=0;i<6;i++) {
			allText[i].setText(textInfo[i]);
		}
	}
	/*
	@param the string array that has already loaded
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
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//load the layout
		setContentView(R.layout.activity_read);
		initData();
		Button back = (Button) findViewById(R.id.button_back);

		back.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(Read.this, Write.class);
				startActivity(i);
			}

		});
		/*
		When click the send button, all user input data will be put into an intent for the purpose of sending.
		 */
		Button send=(Button)findViewById(R.id.button_send2);
		send.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Bundle extras=getIntent().getExtras();
				String[] allEmailInfo=extras.getStringArray("mail");

				String emailToString=allEmailInfo[1];
				String subjectString=allEmailInfo[5];
				String bodyString=allEmailInfo[2];
				String ccString=allEmailInfo[3];
				String bccString=allEmailInfo[4];
				//create an intent to send the email
				Intent mailSendData=new Intent(Intent.ACTION_SENDTO);
				mailSendData.setData(Uri.parse("mailto:"+emailToString));
				mailSendData.putExtra(Intent.EXTRA_CC,subjectString);
				mailSendData.putExtra(Intent.EXTRA_BCC,bccString);
				mailSendData.putExtra(Intent.EXTRA_SUBJECT, subjectString);
				mailSendData.putExtra(Intent.EXTRA_TEXT, bodyString);
				startActivity(mailSendData);
			}
		});
	}
}
