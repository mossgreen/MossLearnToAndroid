package com.example.avjindersinghsekhon.minimaltodo;

import android.app.AlarmManager;
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
import android.util.Log;
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
import com.amulyakhare.textdrawable.util.ColorGenerator;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    //实现 下拉刷新喝自动加载
    private RecyclerViewEmptySupport mRecyclerView;

    // 悬浮的“＋” 按钮，称为fab，是material design 的一个特色
    private FloatingActionButton mAddToDoItemFAB;

    //todolist
    private ArrayList<ToDoItem> mToDoItemsArrayList;

    //CoordinatorLayout 实现了多种Material Design中提到的滚动效果。
    private CoordinatorLayout mCoordLayout;

    //
    public static final String TODOITEM = "com.avjindersinghsekhon.com.avjindersinghsekhon.minimaltodo.MainActivity";


    private BasicListAdapter adapter;
    private static final int REQUEST_ID_TODO_ITEM = 100;
    private ToDoItem mJustDeletedToDoItem;
    private int mIndexOfDeletedToDoItem;
    public static final String DATE_TIME_FORMAT_12_HOUR = "MMM d, yyyy  h:mm a";
    public static final String DATE_TIME_FORMAT_24_HOUR = "MMM d, yyyy  k:mm";
    public static final String FILENAME = "todoitems.json";
    private StoreRetrieveData storeRetrieveData;

    /*ItemTouchHelper是一个强大的工具，它处理好了关于在RecyclerView上添加拖动排序与滑动删除的所有事情。
    它是RecyclerView.ItemDecoration的子类，也就是说它可以轻易的添加到几乎所有的LayoutManager和Adapter中。
    它还可以和现有的item动画一起工作，提供受类型限制的拖放动画等等，
     */
    public ItemTouchHelper itemTouchHelper;
    private CustomRecyclerScrollViewListener customRecyclerScrollViewListener;
    public static final String SHARED_PREF_DATA_SET_CHANGED = "com.avjindersekhon.datasetchanged";
    public static final String CHANGE_OCCURED = "com.avjinder.changeoccured";
    private int mTheme = -1;
    private String theme = "name_of_the_theme";
    public static final String THEME_PREFERENCES = "com.avjindersekhon.themepref";
    public static final String RECREATE_ACTIVITY = "com.avjindersekhon.recreateactivity";
    public static final String THEME_SAVED = "com.avjindersekhon.savedtheme";
    public static final String DARKTHEME = "com.avjindersekon.darktheme";
    public static final String LIGHTTHEME = "com.avjindersekon.lighttheme";
    private AnalyticsApplication app;
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
        //the author is tracking this app
        app.send(this);

        /*
        @moss
        SharedPreferences的使用非常简单，能够轻松的存放数据和读取数据。
        SharedPreferences只能保存简单类型的数据，例如，String、int等。
        一般会将复杂类型的数据转换成Base64编码，
        然后将转换后的数据以字符串的形式保存在 XML文件中，再用SharedPreferences保存。

        使用SharedPreferences保存key-value对的步骤如下：
         */

        /*
        （1）使用Activity类的getSharedPreferences方法获得SharedPreferences对象，
            其中存储key-value的文件的名称由getSharedPreferences方法的第一个参数指定。
         */
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_DATA_SET_CHANGED, MODE_PRIVATE);

        if(sharedPreferences.getBoolean(ReminderActivity.EXIT, false)){

            /*
            （2）使用SharedPreferences接口的edit获得SharedPreferences.Editor对象。
             */
            SharedPreferences.Editor editor = sharedPreferences.edit();
            /*
            （3）通过SharedPreferences.Editor接口的putXxx方法保存key-value对。
            其中Xxx表示不同的数据类型。例如：字符串类型的value需要用putString方法。
             */
            editor.putBoolean(ReminderActivity.EXIT,false);
            /*
            (4)通过SharedPreferences.Editor接口的commit方法保存key-value对。commit方法相当于数据库事务中的提交（commit）操作。
             */
            editor.apply();
            finish();
        }
        /*
        @author
        We need to do this, as this activity's onCreate won't be called when coming back from SettingsActivity,
        thus our changes to dark/light mode won't take place, as the setContentView() is not called again.
        So, inside our SettingsFragment, whenever the checkbox's value is changed, in our shared preferences,
        we mark our recreate_activity key as true.

        Note: the recreate_key's value is changed to false before calling recreate(), or we would have ended up in an infinite loop,
        as onResume() will be called on recreation, which will again call recreate() and so on....
        and get an ANR

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

        //this code is for author to track this app using google analysis
        app = (AnalyticsApplication)getApplication();
        super.onStart();
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_DATA_SET_CHANGED, MODE_PRIVATE);
        if(sharedPreferences.getBoolean(CHANGE_OCCURED, false)){

            /*
            @moss
            getLocallyStoredData() is the method that created above,
            used to fetch data from the jason file
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
            setAlarms();

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(CHANGE_OCCURED, false);
//            editor.commit();
            editor.apply();


        }
    }

    /*
    set Alarms to a to do item
     */
    private void setAlarms(){
        /*@moss
        first, check the to do item ArrayList, if it's not null
        iterate this ArrayList,
        if any item has a reminder or item has a to do date
        check the to do date
        if the date has passed, do nothing and continue
        otherwise:
        new an intent, to set the alarm
         */
        if(mToDoItemsArrayList!=null){
            for(ToDoItem item : mToDoItemsArrayList){
                if(item.hasReminder() && item.getToDoDate()!=null){
                    if(item.getToDoDate().before(new Date())){
                        item.setToDoDate(null);
                        continue;
                    }
                    Intent i = new Intent(this, TodoNotificationService.class);
                    i.putExtra(TodoNotificationService.TODOUUID, item.getIdentifier());
                    i.putExtra(TodoNotificationService.TODOTEXT, item.getToDoText());
                    createAlarm(i, item.getIdentifier().hashCode(), item.getToDoDate().getTime());
                }
            }
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
        app = (AnalyticsApplication)getApplication();
//        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
//                .setDefaultFontPath("fonts/Aller_Regular.tff").setFontAttrId(R.attr.fontPath).build());

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
        FILENAME is from: public static final String FILENAME = "todoitems.json"
        us this name and this activity to initialize a StoreRetrieveData object
        we already have a getLocallyStoredData() method in this class
        put the object as param to get the ArrayList data from the FileName.json
        then, set List Adapter to this ArrayList
        and set Alarm to this list.
        about set Alarm method, just see the code above
        it iterates the ArrayList to check each item
        if an item has a to do date, just set alarm to this item
         */
        storeRetrieveData = new StoreRetrieveData(this, FILENAME);
        mToDoItemsArrayList =  getLocallyStoredData(storeRetrieveData);
        adapter = new BasicListAdapter(mToDoItemsArrayList);
        setAlarms();


//        adapter.notifyDataSetChanged();
//        storeRetrieveData = new StoreRetrieveData(this, FILENAME);
//
//        try {
//            mToDoItemsArrayList = storeRetrieveData.loadFromFile();
////            Log.d("OskarSchindler", "Arraylist Length: "+mToDoItemsArrayList.size());
//        } catch (IOException | JSONException e) {
////            Log.d("OskarSchindler", "IOException received");
//            e.printStackTrace();
//        }
//
//        if(mToDoItemsArrayList==null){
//            mToDoItemsArrayList = new ArrayList<>();
//        }
//

//        mToDoItemsArrayList = new ArrayList<>();
//        makeUpItems(mToDoItemsArrayList, testStrings.length);

        /*
        @moss
        Toolbar其实是一个ActionBar的变体，大大扩展了Actionbar。
        我们可以像对待一个独立控件一样去使用ToolBar，
        可以将它放到屏幕的任何位置，不必拘泥于顶部，
        还可以将它改变高度或者是在ToolBar上使用动画。
         */
        final android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        /*
        CoordinatorLayout 实现了多种Material Design中提到的滚动效果
        目前这个框架提供了几种不用写动画代码就能工作的方法
        CoordinatorLayout可以用来配合浮动操作按钮的 layout_anchor 和 layout_gravity属性创造出浮动效果
        根据官方的谷歌文档，AppBarLayout目前必须是第一个嵌套在CoordinatorLayout里面的子view

        CoordinatorLayout的工作原理是搜索定义了CoordinatorLayout Behavior 的子view，
        不管是通过在xml中使用app:layout_behavior标签还是通过在代码中对view类使用@DefaultBehavior修饰符来添加注解。
        当滚动发生的时候，CoordinatorLayout会尝试触发那些声明了依赖的子view。
         */
        mCoordLayout = (CoordinatorLayout)findViewById(R.id.myCoordinatorLayout);
        mAddToDoItemFAB = (FloatingActionButton)findViewById(R.id.addToDoItemFAB);

        /*
        set on click listener to the fab
         */
        mAddToDoItemFAB.setOnClickListener(new View.OnClickListener() {

            @SuppressWarnings("deprecation")
            @Override
            public void onClick(View v) {
                app.send(this, "Action", "FAB pressed");
                Intent newTodo = new Intent(MainActivity.this, AddToDoActivity.class);
                ToDoItem item = new ToDoItem("", false, null);

                /*
                !!!!!!!!!will set random color to the list view!!!!!--------
                 */
                int color = ColorGenerator.MATERIAL.getRandomColor();
                item.setTodoColor(color);
                //noinspection ResourceType
//                String color = getResources().getString(R.color.primary_ligher);
                newTodo.putExtra(TODOITEM, item);
//                View decorView = getWindow().getDecorView();
//                View navView= decorView.findViewById(android.R.id.navigationBarBackground);
//                View statusView = decorView.findViewById(android.R.id.statusBarBackground);
//                Pair<View, String> navBar ;
//                if(navView!=null){
//                    navBar = Pair.create(navView, navView.getTransitionName());
//                }
//                else{
//                    navBar = null;
//                }
//                Pair<View, String> statusBar= Pair.create(statusView, statusView.getTransitionName());
//                ActivityOptions options;
//                if(navBar!=null){
//                    options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, navBar, statusBar);
//                }
//                else{
//                    options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, statusBar);
//                }

//                startActivity(new Intent(MainActivity.this, TestLayout.class), options.toBundle());
//                startActivityForResult(newTodo, REQUEST_ID_TODO_ITEM, options.toBundle());

                /*
                @moss
                Activity跳转,需要返回数据或结果的，则使用startActivityForResult (Intent intent, int requestCode)
                requestCode的值是自定义的，用于识别跳转的目标Activity。
                跳转的目标Activity所要做的就是返回数据/结果
                setResult(int resultCode)只返回结果不带数据，或者setResult(int resultCode, Intent data)两者都返回！
                而接收返回的数据/结果的处理函数是onActivityResult(int requestCode, int resultCode, Intent data)，
                这里的requestCode就是startActivityForResult的requestCode，
                resultCode就是setResult里面的resultCode，返回的数据在data里面。
                */
                startActivityForResult(newTodo, REQUEST_ID_TODO_ITEM);
            }
        });


//        mRecyclerView = (RecyclerView)findViewById(R.id.toDoRecyclerView);
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
                Decelerate Interpolator 减速， 插入内容
                 */
                mAddToDoItemFAB.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
//                mAddToDoItemFAB.animate().translationY(0).setInterpolator(new AccelerateInterpolator(2.0f)).start();
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
//        setUpTransitions();



    }

    /*
    @moss
    this is setter method
    it sets the them to the system xml file
    by put string to the them_key "THEME_SAVED"
     */
    public void addThemeToSharedPreferences(String theme){
        SharedPreferences sharedPreferences = getSharedPreferences(THEME_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(THEME_SAVED, theme);
        editor.apply();
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
    if clicks the about item, a new intent will be triggered
    and start an AboutActivity.class

    if clicked the setting item, a new intent will be triggered
    and start an SettingsActivity.class
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.aboutMeMenuItem:
                Intent i = new Intent(this, AboutActivity.class);
                startActivity(i);
                return true;
//            case R.id.switch_themes:
//                if(mTheme == R.style.CustomStyle_DarkTheme){
//                    addThemeToSharedPreferences(LIGHTTHEME);
//                }
//                else{
//                    addThemeToSharedPreferences(DARKTHEME);
//                }
//
////                if(mTheme == R.style.CustomStyle_DarkTheme){
////                    mTheme = R.style.CustomStyle_LightTheme;
////                }
////                else{
////                    mTheme = R.style.CustomStyle_DarkTheme;
////                }
//                this.recreate();
//                return true;
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
        在运行过程中，发现按Back键后，是可以返回RESULT_CANCELED的，而且不带有数据的。
        这意味着，如果你设想在返回RESULT_CANCELED时并返回数据，
        那么需要截获Back键的事件处理，把原来返回RESULT_CANCELED的核心逻辑copy到事件处理里面。

        REQUEST_ID_TODO_ITEM 是上面已经定义好的int，在onclick 的时候发给下一个class 的int
         */
        if(resultCode!= RESULT_CANCELED && requestCode == REQUEST_ID_TODO_ITEM){
            ToDoItem item =(ToDoItem) data.getSerializableExtra(TODOITEM);
            if(item.getToDoText().length()<=0){
                return;
            }
            boolean existed = false;


            /*
            @moss
            if the data that returned from the "AddToDoActivity.class" has a reminder or data
            will trigger a alarm system service
             */
            if(item.hasReminder() && item.getToDoDate()!=null){
                Intent i = new Intent(this, TodoNotificationService.class);
                i.putExtra(TodoNotificationService.TODOTEXT, item.getToDoText());
                i.putExtra(TodoNotificationService.TODOUUID, item.getIdentifier());
                createAlarm(i, item.getIdentifier().hashCode(), item.getToDoDate().getTime());
//                Log.d("OskarSchindler", "Alarm Created: "+item.getToDoText()+" at "+item.getToDoDate());
            }

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

    /*
    @moss
    在特定的时刻为我们广播一个指定的Intent。
    简单的说就是我们设定一个时间，然后在该时间到来时，AlarmManager为我们广播一个我们设定的Intent。
     */
    private AlarmManager getAlarmManager(){
        return (AlarmManager)getSystemService(ALARM_SERVICE);
    }

    private boolean doesPendingIntentExist(Intent i, int requestCode){
        PendingIntent pi = PendingIntent.getService(this,requestCode, i, PendingIntent.FLAG_NO_CREATE);
        return pi!=null;
    }

    private void createAlarm(Intent i, int requestCode, long timeInMillis){
        AlarmManager alarmManager = getAlarmManager();
        PendingIntent pendingIntent = PendingIntent.getService(this, requestCode, i, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.set(AlarmManager.RTC_WAKEUP, timeInMillis, pendingIntent);
//        Log.d("OskarSchindler", "createAlarm "+requestCode+" time: "+timeInMillis+" PI "+pendingIntent.toString());
    }
    private void deleteAlarm(Intent i, int requestCode){
        if(doesPendingIntentExist(i, requestCode)){
            PendingIntent pendingIntent = PendingIntent.getService(this, requestCode,i, PendingIntent.FLAG_NO_CREATE);
            pendingIntent.cancel();
            getAlarmManager().cancel(pendingIntent);
            Log.d("OskarSchindler", "PI Cancelled " + doesPendingIntentExist(i, requestCode));
        }
    }


    /*
    @moss
    RecyclerView 还带来了一个新特性：列表项动画（Item Animation）。
    同时自带了一个动画器是 DefaultItemAnimator，大致上的效果就是挤开前后的列表项然后淡入（反之亦然）。
    notifyItemInserted(position) - 有一个新项插入到了 position 位置
    notifyItemRangeInserted(position, count) - 在 position 位置插入了 count 个新项目
    notifyItemRemoved(position)
    notifyItemRangeRemoved(position, count)
    notifyItemChanged(position)
    notifyItemMoved(from, to)
     */
    private void addToDataStore(ToDoItem item){
        mToDoItemsArrayList.add(item);
        adapter.notifyItemInserted(mToDoItemsArrayList.size() - 1);

    }


    public void makeUpItems(ArrayList<ToDoItem> items, int len){
        for (String testString : testStrings) {
            ToDoItem item = new ToDoItem(testString, false, new Date());
            //noinspection ResourceType
//            item.setTodoColor(getResources().getString(R.color.red_secondary));
            items.add(item);
        }

    }

    public class BasicListAdapter extends RecyclerView.Adapter<BasicListAdapter.ViewHolder> implements ItemTouchHelperClass.ItemTouchHelperAdapter{
        private ArrayList<ToDoItem> items;


        @Override
        public void onItemMoved(int fromPosition, int toPosition) {
           if(fromPosition<toPosition){

               /*
               this is the swap from left to right
                */
               for(int i=fromPosition; i<toPosition; i++){
                   Collections.swap(items, i, i+1);
               }
           }
            else{

               /*
               this is the swap from right to left
                */
               for(int i=fromPosition; i > toPosition; i--){
                   Collections.swap(items, i, i-1);
               }
           }
            notifyItemMoved(fromPosition, toPosition);
        }

        @Override
        public void onItemRemoved(final int position) {
            //Remove this line if not using Google Analytics
            app.send(this, "Action", "Swiped Todo Away");

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
            deleteAlarm(i, mJustDeletedToDoItem.getIdentifier().hashCode());
            notifyItemRemoved(position);

//            String toShow = (mJustDeletedToDoItem.getToDoText().length()>20)?mJustDeletedToDoItem.getToDoText().substring(0, 20)+"...":mJustDeletedToDoItem.getToDoText();
            String toShow = "Todo";
            Snackbar.make(mCoordLayout, "Deleted "+toShow,Snackbar.LENGTH_SHORT)
                    .setAction("UNDO", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            //Comment the line below if not using Google Analytics
                            app.send(this, "Action", "UNDO Pressed");
                            items.add(mIndexOfDeletedToDoItem, mJustDeletedToDoItem);
                            if(mJustDeletedToDoItem.getToDoDate()!=null && mJustDeletedToDoItem.hasReminder()){
                                Intent i = new Intent(MainActivity.this, TodoNotificationService.class);
                                i.putExtra(TodoNotificationService.TODOTEXT, mJustDeletedToDoItem.getToDoText());
                                i.putExtra(TodoNotificationService.TODOUUID, mJustDeletedToDoItem.getIdentifier());
                                createAlarm(i, mJustDeletedToDoItem.getIdentifier().hashCode(), mJustDeletedToDoItem.getToDoDate().getTime());
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
                todoTextColor = getResources().getColor(R.color.secondary_text);
            }
            else{
                bgColor = Color.DKGRAY;
                todoTextColor = Color.WHITE;
            }
            holder.linearLayout.setBackgroundColor(bgColor);

            /*
            @moss
            if this item has a reminder of to do date
            set the to do list to just one line, because the time is the another line
            otherwise, set the to do list to two lines and hence no space left for data information
            text of items are from: item.getToDoText()
            text color is from todoTextColor
             */
            if(item.hasReminder() && item.getToDoDate()!=null){
                holder.mToDoTextview.setMaxLines(1);
                holder.mTimeTextView.setVisibility(View.VISIBLE);
//                holder.mToDoTextview.setVisibility(View.GONE);
            }
            else{
                holder.mTimeTextView.setVisibility(View.GONE);
                holder.mToDoTextview.setMaxLines(2);
            }
            holder.mToDoTextview.setText(item.getToDoText());
            holder.mToDoTextview.setTextColor(todoTextColor);
//            holder.mColorTextView.setBackgroundColor(Color.parseColor(item.getTodoColor()));

//            TextDrawable myDrawable = TextDrawable.builder().buildRoundRect(item.getToDoText().substring(0,1),Color.RED, 10);
            //We check if holder.color is set or not
//            if(item.getTodoColor() == null){
//                ColorGenerator generator = ColorGenerator.MATERIAL;
//                int color = generator.getRandomColor();
//                item.setTodoColor(color+"");
//            }
//            Log.d("OskarSchindler", "Color: "+item.getTodoColor());

            /*
            @moss
            在实际的Android开发中，很多时候，需要用TextView表现和展示view的内容和标题、标签之类。
            但是Android本身提供的TextView只提供了基础的text表现，比较单调乏味，
            如果要实现丰富多彩的和ImageView那样的样式和表现能力，则需要自己动手实现或者使用第三方开源库。
            TextDrawable 就是这样的TextView+ImageView的实现
            简单的说，TextDrawable的目的，是将一个普通的文本变形为一个“文本”的drawable，
            一旦成为drawable，那么接下来开发者可以自由使用的空间就很大了，
            比如可以随意的将此drawable作为源设置到ImageView里面等

            使用TextDrawable之前首先需要到TextDrawable在github上的主页上把该项目的库文件拖下来，
            然后作为一个Android的lib，在自己的项目中引用。
             */
            /*
            @moss
            this drawable is the image that in front from every list withe a Letter from list
            we set this letter color to white
            use default font
            set it to upper case
            and use the first letter of the item string
             */
            TextDrawable myDrawable = TextDrawable.builder().beginConfig()
                    .textColor(Color.WHITE)
                    .useFont(Typeface.DEFAULT)
                    .toUpperCase()
                    .endConfig()
                    .buildRound(item.getToDoText().substring(0,1),item.getTodoColor());

//            TextDrawable myDrawable = TextDrawable.builder().buildRound(item.getToDoText().substring(0,1),holder.color);
            holder.mColorImageView.setImageDrawable(myDrawable);

            /*
            @moss
            if any item has a data, make its format to 24hour
            otherwise, make it to 12hour format
             */
            if(item.getToDoDate()!=null){
                String timeToShow;
                if(android.text.format.DateFormat.is24HourFormat(MainActivity.this)){
                    timeToShow = AddToDoActivity.formatDate(MainActivity.DATE_TIME_FORMAT_24_HOUR, item.getToDoDate());
                }
                else{
                    timeToShow = AddToDoActivity.formatDate(MainActivity.DATE_TIME_FORMAT_12_HOUR, item.getToDoDate());
                }
                holder.mTimeTextView.setText(timeToShow);
            }


        }

        /*
        @moss
        this method is a getter, to get the size of the items
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

        /*
        @moss
        这个世界的事物总是成对出现。即然有使编译器产生警告信息的，那么就有抑制编译器产生警告信息的。
        SuppressWarnings注释就是为了这样一个目的而存在的。
         */
        @SuppressWarnings("deprecation")
        public class ViewHolder extends RecyclerView.ViewHolder{

            View mView;
            LinearLayout linearLayout;
            TextView mToDoTextview;
//            TextView mColorTextView;
            ImageView mColorImageView;
            TextView mTimeTextView;
//            int color = -1;

            public ViewHolder(View v){
                super(v);
                mView = v;

                /*
                效果就是： 点击任何一个list， 可以修改。我觉得我们可以去掉这个效果，
                因为user 可能会误触这个list 而触发修改
                我们预计的效果是，从右往左滑动，才是修改。
                 */
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


