package com.example.avjindersinghsekhon.minimaltodo;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

/**
 * @author: Feifeigu and Qingyuanli  based on com.example.avjindersinghsekhon.minimaltodo
 * @version v1.2
 */

public class MainActivity extends AppCompatActivity {

    private RecyclerViewEmptySupport mRecyclerView;

    private FloatingActionButton mAddToDoItemFAB;

    private ArrayList<ToDoItem> mToDoItemsArrayList;

    private CoordinatorLayout mCoordLayout;

    public static final String TODOITEM = "com.example.approaching";


    private BasicListAdapter adapter;
    private static final int REQUEST_ID_TODO_ITEM = 100;
    private ToDoItem mJustDeletedToDoItem;
    private int mIndexOfDeletedToDoItem;
    public static final String DATE_TIME_FORMAT_12_HOUR = "MMM d, yyyy  h:mm a";
    public static final String DATE_TIME_FORMAT_24_HOUR = "MMM d, yyyy  k:mm";
    public static final String FILENAME = "todoitems.json";
    public static final int ICON_RED = 0xffe91e63;
    public static final int ICON_GREEN = 0xff00bfa5;
    public int fab_color;
    private StoreRetrieveData storeRetrieveData;


    public ItemTouchHelper itemTouchHelper;
    private CustomRecyclerScrollViewListener customRecyclerScrollViewListener;
    public static final String SHARED_PREF_DATA_SET_CHANGED = "com.example.approaching.datasetchanged";
    public static final String CHANGE_OCCURED = "com.example.approaching.changeoccured";
    private int mTheme = -1;
    private String theme = "name_of_the_theme";
    public static final String THEME_PREFERENCES = "com.example.approaching.themepref";
    public static final String RECREATE_ACTIVITY = "com.example.approaching.recreateactivity";
    public static final String THEME_SAVED = "com.example.approaching.savedtheme";
    public static final String DARKTHEME = "com.example.approaching.darktheme";
    public static final String LIGHTTHEME = "com.example.approaching.lighttheme";
    private String[] testStrings = {"Clean my room",
            "Water the plants",
            "Get car washed",
            "Get my dry cleaning"
    };




    /*
    @moss
    this is a static method
    so the method used in this method is static method too
    return an ArrayList, type is todoitem
    one param, which is a object reference to a class named StoreRetrieveData
     */
    public static ArrayList<ToDoItem> getLocallyStoredData(StoreRetrieveData storeRetrieveData){

        /*
        @moss
        first, initialize a ArrayList, type of ToDoItem
        then, load data from json file, by using loadFromFile() method, provided by storeRetrieveData object
        if there is no data, create a new ArrayList and return this empty ArrayList,
        otherwise, return the ArrayList that with data that we got from json file
         */
        ArrayList<ToDoItem> items = null;

        try {
            items  = storeRetrieveData.loadFromFile();

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        if(items == null){
            items = new ArrayList<>();
        }
        return items;

    }

    @Override
    protected void onResume() {
        super.onResume();


        /*
        @author
        We need to do this, as this activity's onCreate won't be called when coming back from SettingsActivity,
        thus our changes to dark/light mode won't take place, as the setContentView() is not called again.
        So, inside our SettingsFragment, whenever the checkbox's value is changed, in our shared preferences,
        we mark our recreate_activity key as true.

        Note: the recreate_key's value is changed to false before calling recreate(), or we would have ended up in an infinite loop,
        as onResume() will be called on recreation, which will again call recreate() and so on....

         */
        if(getSharedPreferences(THEME_PREFERENCES, MODE_PRIVATE).getBoolean(RECREATE_ACTIVITY, false)){
            SharedPreferences.Editor editor = getSharedPreferences(THEME_PREFERENCES, MODE_PRIVATE).edit();
            editor.putBoolean(RECREATE_ACTIVITY, false);
            editor.apply();
            recreate();
        }


    }

    @Override
    protected void onStart() {

        super.onStart();
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_DATA_SET_CHANGED, MODE_PRIVATE);
        if(sharedPreferences.getBoolean(CHANGE_OCCURED, false)){

            /*
            @moss
            getLocallyStoredData() is the method used to fetch data from the jason file
            it returns an ArrayList, type is todoitem
             */
            mToDoItemsArrayList = getLocallyStoredData(storeRetrieveData);

            /*
            @moss
            如果一个项目当中需要定义多个Adapter，那么重复的编写一些相同的代码无意是繁琐和浪费时间的，
            所以BaseListAdapter就产生了，
            当需要自定义Adapter的时候，让这个Adapter继承BaseListAdapter，
            只需覆写bindView(int position, View convertView, ViewGroup parent)方法即可，
            另外，可以通过setOnInViewClickListener(Integer key,onInternalClickListener onClickListener)方法
            来为Adapter里的view添加点击事件，
            当然，也可以直接通过setOnClickListener（listener）为view设置点击事件。
             */
            adapter = new BasicListAdapter(mToDoItemsArrayList);
            mRecyclerView.setAdapter(adapter);
//            setAlarms();

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(CHANGE_OCCURED, false);
//            editor.commit();
            editor.apply();


        }
    }


    protected void onCreate(Bundle savedInstanceState) {

        /*@moss
        in the onCreate phase,
        it first calls a instance of Analytics Class to this app
        second, set the them to this app
        third, set content view from R file
        fourth, find json file and get the data stored in it to a ArrayList
        fifth, set adapter and alarm to items of this ArrayList
        sixth, find fab and set onclick listener on it.
        click the fab, wil trigger a new intent, to start a AddToDoActivity.class
        the returned data will be getherred by this activity
        last, set CustomRecyclerScrollViewListener to the recycle list view

        */

        //We recover the theme we've set and setTheme accordingly
        theme = getSharedPreferences(THEME_PREFERENCES, MODE_PRIVATE).getString(THEME_SAVED, LIGHTTHEME);

        if(theme.equals(LIGHTTHEME)){
            mTheme = R.style.CustomStyle_LightTheme;
        }
        else{
            mTheme = R.style.CustomStyle_DarkTheme;
        }
        this.setTheme(mTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_DATA_SET_CHANGED, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(CHANGE_OCCURED, false);
        editor.apply();

        /*
        @moss
        FILENAME = "todoitems.json"
        use this file and this activity to initialize a StoreRetrieveData object
        we already have a getLocallyStoredData() method in this class
        put the this activity as param to get the ArrayList data from the FileName.json
        then, set List Adapter to this ArrayList
        and set Alarm to this list.
        about set Alarm method, just see the code above
        it iterates the ArrayList to check each item
        if an item has a to do date, just set alarm to this item
         */
        storeRetrieveData = new StoreRetrieveData(this, FILENAME);
        mToDoItemsArrayList =  getLocallyStoredData(storeRetrieveData);
        adapter = new BasicListAdapter(mToDoItemsArrayList);

        final android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        /*
        @moss
        CoordinatorLayout 实现了多种Material Design中提到的滚动效果
        目前这个框架提供了几种不用写动画代码就能工作的方法
        CoordinatorLayout可以用来配合浮动操作按钮的 layout_anchor 和 layout_gravity属性创造出浮动效果
        根据官方的谷歌文档，AppBarLayout目前必须是第一个嵌套在CoordinatorLayout里面的子view

        CoordinatorLayout的工作原理是搜索定义了CoordinatorLayout Behavior 的子view，
        不管是通过在xml中使用app:layout_behavior标签还是通过在代码中对view类使用@DefaultBehavior修饰符来添加注解。
        当滚动发生的时候，CoordinatorLayout会尝试触发那些声明了依赖的子view。
         */

        /*
        @moss
        get the layout and fab from R file
        then,set onClickListener to the fab
         */
        mCoordLayout = (CoordinatorLayout)findViewById(R.id.myCoordinatorLayout);
        mAddToDoItemFAB = (FloatingActionButton)findViewById(R.id.addToDoItemFAB);

        fab_color = mAddToDoItemFAB.getDrawingCacheBackgroundColor();
        mAddToDoItemFAB.setOnClickListener(new View.OnClickListener() {

            @SuppressWarnings("deprecation")
            @Override
            public void onClick(View v) {
                /*
                @moss
                if the fab is clicked, an intend will be triggered, to start the AddToDoActivity
                and click the FAB will generate a new default TodoItem
                 */
                Intent newTodo = new Intent(MainActivity.this, AddToDoActivity.class);
                ToDoItem item = new ToDoItem("", false, null);

                //@moss set the color to gray when the item is just generated
                item.setTodoColor(Color.LTGRAY);
                newTodo.putExtra(TODOITEM, item);
//
                /*
                @moss
                if this activity jump to another activity and need some data come sback
                then use startActivityForResult (Intent intent, int requestCode)
                */
                startActivityForResult(newTodo, REQUEST_ID_TODO_ITEM);
            }
        });


        mRecyclerView = (RecyclerViewEmptySupport)findViewById(R.id.toDoRecyclerView);
        if(theme.equals(LIGHTTHEME)){
            mRecyclerView.setBackgroundColor(getResources().getColor(R.color.primary_lightest));
        }
        mRecyclerView.setEmptyView(findViewById(R.id.toDoEmptyView));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));



        customRecyclerScrollViewListener = new CustomRecyclerScrollViewListener() {
            @Override
            public void show() {

                /*
                @moss set the animation to show
                 */
                mAddToDoItemFAB.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
            }

            @Override
            public void hide() {
                CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams)mAddToDoItemFAB.getLayoutParams();
                int fabMargin = lp.bottomMargin;
                mAddToDoItemFAB.animate().translationY(mAddToDoItemFAB.getHeight()+fabMargin).setInterpolator(new AccelerateInterpolator(2.0f)).start();
            }
        };
        mRecyclerView.addOnScrollListener(customRecyclerScrollViewListener);

        ItemTouchHelper.Callback callback = new ItemTouchHelperClass(adapter);
        itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);

        mRecyclerView.setAdapter(adapter);
    }


    /*@moss
    will create a options menu when this app just initialized
    the layout of this menu is from R file
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /*
    @moss
    in the options menu, there are two items: about and setting
    click each of them can trigger an intent and jump to the corresponding activity
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.aboutMeMenuItem:
                Intent i = new Intent(this, AboutActivity.class);
                startActivity(i);
                return true;
            case R.id.preferences:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /*
    in the onCreate method, there was a method named "startActivityForResult"
    which indicates that some data have to be returned to the mainActivity
    so this "onActivityResult()" is the return data method
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /*
        @moss
        when user click the back button, return data
         */
        if(resultCode!= RESULT_CANCELED && requestCode == REQUEST_ID_TODO_ITEM){
            ToDoItem item =(ToDoItem) data.getSerializableExtra(TODOITEM);
            if(item.getToDoText().length()<=0){
                return;
            }
            boolean existed = false;


            /*
            @moss
            getIdentifier()方法可以方便的获各应用包下的指定资源ID。
             */
            for(int i = 0; i<mToDoItemsArrayList.size();i++){
                if(item.getIdentifier().equals(mToDoItemsArrayList.get(i).getIdentifier())){
                    mToDoItemsArrayList.set(i, item);
                    existed = true;
                    adapter.notifyDataSetChanged();
                    break;
                }
            }
            if(!existed) {
                addToDataStore(item);
            }


        }
    }


    private boolean doesPendingIntentExist(Intent i, int requestCode){
        PendingIntent pi = PendingIntent.getService(this,requestCode, i, PendingIntent.FLAG_NO_CREATE);
        return pi!=null;
    }


    /*
    @moss
    the animation when a new item insert into a list
     */
    private void addToDataStore(ToDoItem item){
        mToDoItemsArrayList.add(item);
        adapter.notifyItemInserted(mToDoItemsArrayList.size() - 1);

    }


    public class BasicListAdapter extends RecyclerView.Adapter<BasicListAdapter.ViewHolder> implements ItemTouchHelperClass.ItemTouchHelperAdapter{
        private ArrayList<ToDoItem> items;

        /*
        @moss
        this method is for moving items from one position to another
         */
        @Override
        public void onItemMoved(int fromPosition, int toPosition) {

           if(fromPosition<toPosition){

               for(int i=fromPosition; i<toPosition; i++){
                   Collections.swap(items, i, i+1);
               }
           }
            else{

               for(int i=fromPosition; i > toPosition; i--){
                   Collections.swap(items, i, i-1);
               }
           }
            notifyItemMoved(fromPosition, toPosition);
        }

        @Override
        public void onItemRemoved(final int position) {


            /*
            @moss
            if we delete a to do item
            will trigger a intent and delete the system alarm
            also there will be a pop out message " deleted XXX item"
            and user can have a chance to UNDO this action by clicking on the UNDO text
            because this click will trigger a inter to add alarm again to system alarm
             */
            mJustDeletedToDoItem =  items.remove(position);
            mIndexOfDeletedToDoItem = position;
            Intent i = new Intent(MainActivity.this,TodoNotificationService.class);

            notifyItemRemoved(position);


            String toShow = "Todo";
            Snackbar.make(mCoordLayout, "Deleted "+toShow,Snackbar.LENGTH_SHORT)
                    .setAction("UNDO", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            items.add(mIndexOfDeletedToDoItem, mJustDeletedToDoItem);

                            /*
                            @moss comments it out
                            if the item that just deleted is not null or has a reminder
                            trigger an intent and set alarm for this item
                             */
                            if(mJustDeletedToDoItem.getToDoDate()!=null && mJustDeletedToDoItem.hasDueTime()){
                                Intent i = new Intent(MainActivity.this, TodoNotificationService.class);
                                i.putExtra(TodoNotificationService.TODOTEXT, mJustDeletedToDoItem.getToDoText());
                                i.putExtra(TodoNotificationService.TODOUUID, mJustDeletedToDoItem.getIdentifier());
//                                createAlarm(i, mJustDeletedToDoItem.getIdentifier().hashCode(), mJustDeletedToDoItem.getToDoDate().getTime());
                            }
                            notifyItemInserted(mIndexOfDeletedToDoItem);
                        }
                    }).show();
        }

        /*
        @moss
        A ViewHolder describes an item view and metadata about its place within the RecyclerView.
        RecyclerView.Adapter implementations should subclass ViewHolder
        and add fields for caching potentially expensive findViewById(int) results.
         */
        @Override
        public BasicListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_circle_try, parent, false);
            return new ViewHolder(v);
        }

        /*
        @moss
        this method Called by RecyclerView to display the data at the specified position.
         */
        @Override
        public void onBindViewHolder(final BasicListAdapter.ViewHolder holder, final int position) {
            ToDoItem item = items.get(position);
//            if(item.getToDoDate()!=null && item.getToDoDate().before(new Date())){
//                item.setToDoDate(null);
//            }

            //todo: change the todo color to the color of FAB

            /*
            @moss
            if this app is using Light theme, then set the background color to white and text color to white
            otherwise, set teh background color to dark gray and text color to white
             */
            SharedPreferences sharedPreferences = getSharedPreferences(THEME_PREFERENCES, MODE_PRIVATE);
            //Background color for each to-do item. Necessary for night/day mode
            int bgColor;
            //color of title text in our to-do item. White for night mode, dark gray for day mode
            int todoTextColor;
            if(sharedPreferences.getString(THEME_SAVED, LIGHTTHEME).equals(LIGHTTHEME)){
                bgColor = Color.WHITE;
//                todoTextColor = fab_color;
                todoTextColor = getResources().getColor(R.color.secondary_text);
            }
            else{
                bgColor = Color.DKGRAY;
                todoTextColor = Color.WHITE;
            }
            holder.linearLayout.setBackgroundColor(bgColor);


            if(item.hasDueTime() && item.getToDoDate()!=null){
                holder.mToDoTextview.setMaxLines(1);
                holder.mTimeTextView.setVisibility(View.VISIBLE);
                holder.mCountingTextView.setVisibility(View.VISIBLE);
            }
            else{
                holder.mCountingTextView.setVisibility(View.GONE);
                holder.mTimeTextView.setVisibility(View.GONE);
                holder.mToDoTextview.setMaxLines(1);
            }
            holder.mToDoTextview.setText(item.getToDoText());
            holder.mToDoTextview.setTextColor(todoTextColor);

            /*
            @moss
            TextDrawable = TextView+ImageView的实现
            简单的说，TextDrawable的目的，是将一个普通的文本变形为一个“文本”的drawable，
             */

            TextDrawable myDrawable = TextDrawable.builder().beginConfig()
                    .textColor(Color.WHITE)
                    .useFont(Typeface.DEFAULT)
                    .toUpperCase()
                    .endConfig()
                    .buildRound(item.getToDoText().substring(0,1),item.getTodoColor());

            holder.mColorImageView.setImageDrawable(myDrawable);

            if(item.getToDoDate()!=null){

                Date curDate = Calendar.getInstance().getTime();
                Long timeSpan = item.getToDoDate().getTime() - curDate.getTime();

                long days = timeSpan / (1000 * 60 * 60 * 24);
                long hours = (timeSpan - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
                long minutes = (timeSpan - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60);

//                holder.mCountingTextView.setTextColor(Color.BLACK);


                String dueTimeToShow;
                String countingTimeToShow= days+" d, "+hours+" h, " +minutes+ " m";


                if(android.text.format.DateFormat.is24HourFormat(MainActivity.this)){
                    dueTimeToShow = AddToDoActivity.formatDate(MainActivity.DATE_TIME_FORMAT_24_HOUR, item.getToDoDate());
                }
                else{
                    dueTimeToShow = AddToDoActivity.formatDate(MainActivity.DATE_TIME_FORMAT_12_HOUR, item.getToDoDate());
                }

                //set text to
                holder.mTimeTextView.setText(dueTimeToShow);
                holder.mCountingTextView.setText(countingTimeToShow);


                holder.mCountingTextView.setTextColor(item.getTodoColor());
                holder.mTimeTextView.setTextColor(todoTextColor);
            }


        }

        /*
        @moss
        this method is a getter, to get the size of the todoArrayList
         */
        @Override
        public int getItemCount() {
            return items.size();
        }

        /*
        @moss
        this method is the constructor for BasicListAdapter class
        it uses the ArrayList as param
         */
        BasicListAdapter(ArrayList<ToDoItem> items){

            this.items = items;
        }

        @SuppressWarnings("deprecation")
        public class ViewHolder extends RecyclerView.ViewHolder{

            View mView;
            LinearLayout linearLayout;
            TextView mToDoTextview;
//            TextView mColorTextView;
            ImageView mColorImageView;
            TextView mTimeTextView;
            TextView mCountingTextView;
//            int color = -1;

            public ViewHolder(View v){
                super(v);
                mView = v;

                v.setOnClickListener(new View.OnClickListener() {

                    /*
                    @moss
                    if this item is clicked, the class will get this item by the position
                    trigger a intent, from mainActivity to "AddToDoActivity"
                    and ask for a result
                     */
                    @Override
                    public void onClick(View v) {
                        ToDoItem item = items.get(ViewHolder.this.getAdapterPosition());
                        Intent i = new Intent(MainActivity.this, AddToDoActivity.class);
                        i.putExtra(TODOITEM, item);
                        startActivityForResult(i, REQUEST_ID_TODO_ITEM);
                    }
                });
                mToDoTextview = (TextView)v.findViewById(R.id.toDoListItemTextview);
                mTimeTextView = (TextView)v.findViewById(R.id.todoListItemTimeTextView);

                /*
                @moss add this text view to show  counting times
                 */
                mCountingTextView = (TextView)v.findViewById(R.id.toDoListCountingTextView);
//                mColorTextView = (TextView)v.findViewById(R.id.toDoColorTextView);
                mColorImageView = (ImageView)v.findViewById(R.id.toDoListItemColorImageView);
                linearLayout = (LinearLayout)v.findViewById(R.id.listItemLinearLayout);
            }


        }
    }

    //Used when using custom fonts
//    @Override
//    protected void attachBaseContext(Context newBase) {
//        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
//    }

    /*
    this method is to save data to file
    have to use a instance from the StoreRetrieveData class
     */
    private void saveDate(){
        try {
            storeRetrieveData.saveToFile(mToDoItemsArrayList);
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }

    }

    /*
    save data to file when this app is onPause
    or, it may lost data
     */
    @Override
    protected void onPause() {
        super.onPause();
        try {
            storeRetrieveData.saveToFile(mToDoItemsArrayList);
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }


    /*
    on Destroy method,
     */
    @Override
    protected void onDestroy() {

        super.onDestroy();
        mRecyclerView.removeOnScrollListener(customRecyclerScrollViewListener);
    }


//    public void setUpTransitions(){
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
//            Transition enterT = new Slide(Gravity.RIGHT);
//            enterT.setDuration(500);
//
//            Transition exitT = new Slide(Gravity.LEFT);
//            exitT.setDuration(300);
//
//            Fade fade = new Fade();
//            fade.setDuration(500);
//
//            getWindow().setExitTransition(fade);
//            getWindow().setReenterTransition(fade);
//
//        }
//    }

}


