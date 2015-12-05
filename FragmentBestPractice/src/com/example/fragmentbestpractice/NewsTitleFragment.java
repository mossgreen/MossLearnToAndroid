package com.example.fragmentbestpractice;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.ListView;

public class NewsTitleFragment extends Fragment implements OnItemClickListener {
	
	private ListView newsTitleListView;
	private List<News>newsList;
	private NewsAdapter adapter;
	private boolean isTwoPane;
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		newsList = getNews();
		adapter = new NewsAdapter(activity, R.layout.news_item, newsList);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View view = inflater.inflate(R.layout.news_title_frag, container,false);
		newsTitleListView = (ListView) view.findViewById(R.id.news_title_list_view);
		newsTitleListView.setAdapter(adapter);
		newsTitleListView.setOnItemClickListener(this);
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		if(getActivity().findViewById(R.id.news_content_layout) != null){
			isTwoPane = true;
		}else {
			isTwoPane = false;
		}
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id){
		News news = newsList.get(position);
		if(isTwoPane){
			NewsContentFragment newsContentFragment = (NewsContentFragment) getFragmentManager().findFragmentById(R.id.news_content_fragment);
			newsContentFragment.refresh(news.getTitle(), news.getContent());
		}else{
			NewsContentActivity.actionStart(getActivity(), news.getTitle(), news.getContent());
		}
	}
	
	private List<News> getNews(){
		List<News> newsList = new ArrayList<News>();
		News news1 = new News();
		news1.setTitle("succeed in college as a  Learning Disable student");
		news1.setContent("college frashmen will soon learn to live with a roommate, adjust to a new social scene and survive less than stellar dining hall food. Students with learning disabilitiesw will face these translations while also grappling with a few more hurdles.");
		newsList.add(news1);
		News news2 = new News();
		news2.setTitle("google android exec poached by China's Xiaomi");
		news2.setContent("china's xiaomi has poached a key google executive involved in teh tech giant's android phones, in a move seen as a coup for the repidly growing chines smartphone maker.");
		newsList.add(news2);
		return newsList;
	}

}
