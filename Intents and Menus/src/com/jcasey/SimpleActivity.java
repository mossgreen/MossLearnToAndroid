package com.jcasey;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class SimpleActivity extends Activity {
    
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        
        if(item.getItemId() == R.id.mnuConnectivity)
        {
            Log.e("mnuConnectivity","...");
        }
        
        return super.onOptionsItemSelected(item);
    }

    StringBuilder data = new StringBuilder();
    
    @Override
    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(R.layout.simple_activity);
        
        Bundle extras = getIntent().getExtras();
        
        if(extras != null)
        {
        	data = new StringBuilder();
        	
	        String clientId = extras.getString("clientId");
	        String firstName = extras.getString("firstName");
	        String lastName = extras.getString("lastName");
	
	        Log.e("clientId", clientId);
	        Log.e("firstName", firstName);
	        Log.e("lastName", lastName);
	        
	        TextView dataBundle = (TextView) findViewById(R.id.txtDataBundle);
	        
	        data.append("clientId:" + clientId + "\n" + "firstName:" + firstName + "\n"+ "lastName:" + lastName);
	        
	        dataBundle.setText(data);
        }
        
        Button btnPrevious = (Button)findViewById(R.id.btnPrevious);
        btnPrevious.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(); 
                intent.putExtra("RESULT", "Return message"); 
                intent.putExtra("DATA", data.toString());
                setResult(Activity.RESULT_OK, intent); 
                
                finish();                
            }});
    }    
}
