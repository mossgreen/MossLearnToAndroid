package com.example.listviewtext;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class FruitAdapter extends  ArrayAdapter<Fruit>{
	private int resourceId;
	public FruitAdapter(Context context, int textViewResourceId, List<Fruit> objects ){
		super(context, textViewResourceId,objects);
		resourceId = textViewResourceId;
	}
	
//	@Override
//	public View getView(int position, View convertView, ViewGroup parent){
//		Fruit fruit= getItem(position); // the instance of fruit
//		View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
//		ImageView fruitImage = (ImageView) view.findViewById(R.id.fruit_image);
//		TextView fruitName = (TextView) view.findViewById(R.id.fruit_name);
//		fruitImage.setImageResource(fruit.getImageId());
//		fruitName.setText(fruit.getName());
//		return view;
//	}
	
	// optimize the code 
//	@Override
//	public View getView(int position, View convertView, ViewGroup parent){
//		Fruit fruit = getItem(position);
//		View view;
//		if(convertView == null){
//			view = LayoutInflater.from(getContext()).inflate(resourceId, null);
//		}else{
//			view = convertView;
//		}
//		
//		ImageView fruitImage = (ImageView) view.findViewById(R.id.fruit_image);
//		TextView fruitName = (TextView) view.findViewById(R.id.fruit_name);
//		fruitImage.setImageResource(fruit.getImageId());
//		fruitName.setText(fruit.getName());
//		return view;
//	}
	
	//further to optimize the code
	
	class ViewHolder{
		ImageView fruitImage;
		TextView fruitName;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		Fruit fruit = getItem(position);
		View view;
		ViewHolder viewHolder;
		if(convertView == null){
			view = LayoutInflater.from(getContext()).inflate(resourceId, null);
			viewHolder = new ViewHolder();
			viewHolder.fruitImage = (ImageView) view.findViewById(R.id.fruit_image);
			viewHolder.fruitName = (TextView) view.findViewById(R.id.fruit_name);
			view.setTag(viewHolder); // to store the View to viewHolder
		}else{
			view = convertView;
			viewHolder = (ViewHolder) view.getTag(); // the get the viewHolder again
		}
		viewHolder.fruitImage.setImageResource(fruit.getImageId());
		viewHolder.fruitName.setText(fruit.getName());
		return view;
	}

}
