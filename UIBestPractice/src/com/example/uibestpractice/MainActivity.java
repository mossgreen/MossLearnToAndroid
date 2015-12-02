package com.example.uibestpractice;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends Activity {
	
	private ListView msgListView;
	private EditText InputText;
	private Button send;
	private MsgAdapter adapter;
	private List<Msg> msgList = new ArrayList<Msg>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		initMsgs();
		
		adapter = new MsgAdapter(MainActivity.this, R.layout.msg_item, msgList);
		InputText = (EditText) findViewById(R.id.input_text);
		send = (Button) findViewById(R.id.sent);
		msgListView = (ListView) findViewById(R.id.msg_list_view);
		msgListView.setAdapter(adapter);
		send.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String content = InputText.getText().toString();
				if(!"".equals(content)){
					Msg msg = new Msg(content, Msg.TYPE_SENT);
					msgList.add(msg);
					adapter.notifyDataSetChanged(); // when new info comes, refresh the listview
					msgListView.setSelection(msgList.size());// go to the last line of listview
					InputText.setText(""); //clear the content of input frame
				}
			}
		});
	}
	
	private void initMsgs(){
		Msg msg1 = new Msg("Hello, this is moss",  Msg.TYPE_RECEIVED);
		msgList.add(msg1);
		Msg msg2 = new Msg("Hello, this is huahua", Msg.TYPE_SENT);
		msgList.add(msg2);
		Msg msg3 = new Msg("miss you much", Msg.TYPE_RECEIVED);
		msgList.add(msg3);
		Msg msg4 = new Msg(": ) will call you when I'm home.", Msg.TYPE_SENT);
		msgList.add(msg4);
	}
//
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.main, menu);
//		return true;
//	}
//
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		// Handle action bar item clicks here. The action bar will
//		// automatically handle clicks on the Home/Up button, so long
//		// as you specify a parent activity in AndroidManifest.xml.
//		int id = item.getItemId();
//		if (id == R.id.action_settings) {
//			return true;
//		}
//		return super.onOptionsItemSelected(item);
//	}
}
