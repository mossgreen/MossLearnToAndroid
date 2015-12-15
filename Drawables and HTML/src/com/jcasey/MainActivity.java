package com.jcasey;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity {

	/**
	 * @author John Casey (john.casey@gmail.com)
	 * 
	 * Example code to illustrate how HTML can be embedded into a TextView 
	 * widget to add styles and links to Android TextView widgets
	 * 
	 */
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        TextView textView = (TextView) findViewById(R.id.textView1);
        
//        Spanned test = Html.fromHtml("The quick brown fox, jumps over the <i>lazy</i> dog <table><tr><td>test</td></tr></table>");
//        textView.setText(test);
        
        Spanned fromHtml = Html.fromHtml("<img width = \"30\" height = \"30\" src=\"http://static.stuff.co.nz/1335468190/486/6815486.gif\">Maecenas a augue <b>tortor</b>. Donec interdum hendrerit facilisis. Integer at dictum dui. Pellentesque <a href=\"http://www.unitec.ac.nz\">habitant</a> morbi tristique senectus et netus et malesuada fames ac turpis egestas. Cras bibendum rhoncus venenatis. Aenean viverra dapibus urna posuere <font color=\"white\">vehicula</font>. Maecenas feugiat eros eu odio posuere dignissim ullamcorper lacus interdum. Vivamus blandit <b>ipsum</b> mauris, sed aliquet elit. Duis <b><i>scelerisque</i></b>, mi eget consectetur eleifend, neque ipsum sodales risus, at imperdiet felis orci id purus. Aliquam nec quam ut dolor porta mollis. Cras sodales condimentum est vel elementum. Integer nec massa sit amet libero tincidunt aliquet et at tortor. Maecenas ut justo id odio accumsan feugiat ut id urna. Proin sed sapien sed mi euismod consequat ut non turpis.",new ImageGetter() {
			public Drawable getDrawable(String source) {
				// note the image src is not used here

				int id = R.drawable.ic_launcher;
				  
	            // we instead use a drawable resource (already available) instead  
				
				Drawable d = getResources().getDrawable(id);
				d.setBounds(0,0,d.getIntrinsicWidth(),d.getIntrinsicHeight());
				return d;
			}
		},null);
        
        
		textView.setText(fromHtml);
        
        Linkify.addLinks(textView, Linkify.WEB_URLS);
        
        textView.setLinksClickable(true);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
