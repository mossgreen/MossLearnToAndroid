package com.example.avjindersinghsekhon.minimaltodo;

import android.animation.Animator;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddToDoActivity extends AppCompatActivity implements  DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{
    private Date mLastEdited;
    private EditText mToDoTextBodyEditText;
    private SwitchCompat mToDoDateSwitch;
//    private TextView mLastSeenTextView;
    private LinearLayout mUserDateSpinnerContainingLinearLayout;
    private TextView mReminderTextView;

    private EditText mDateEditText;
    private EditText mTimeEditText;
    private String mDefaultTimeOptions12H[];
    private String mDefaultTimeOptions24H[];

    private Button mChooseDateButton;
    private Button mChooseTimeButton;
    private ToDoItem mUserToDoItem;

    /*
    this is the floating action button in the middle of the screen
    */
    private FloatingActionButton mToDoSendFloatingActionButton;
    public static final String DATE_FORMAT = "MMM d, yyyy";
    public static final String DATE_FORMAT_MONTH_DAY = "MMM d";
    public static final String DATE_FORMAT_TIME = "H:m";

    private String mUserEnteredText;
    private boolean mUserHasReminder;
    private Toolbar mToolbar;
    private Date mUserReminderDate;
    private Date mUserCountingDate;
    private int mUserColor;
    private boolean setDateButtonClickedOnce = false;
    private boolean setTimeButtonClickedOnce = false;
    private LinearLayout mContainerLayout;
    private String theme;

    @Override
    protected void onResume() {
        super.onResume();
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        setContentView(R.layout.new_to_do_layout);
        //Need references to these to change them during light/dark mode
        ImageButton reminderIconImageButton;
        TextView reminderRemindMeTextView;

        //@lv light theme or dark theme
        theme = getSharedPreferences(MainActivity.THEME_PREFERENCES, MODE_PRIVATE).getString(MainActivity.THEME_SAVED, MainActivity.LIGHTTHEME);
        if(theme.equals(MainActivity.LIGHTTHEME)){
            setTheme(R.style.CustomStyle_LightTheme);
            Log.d("OskarSchindler", "Light Theme");
        }
        else{
            setTheme(R.style.CustomStyle_DarkTheme);
        }

        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_add_to_do);
        //Testing out a new layout
        setContentView(R.layout.activity_todo_test);

        //Show an X in place of <-
        //@lv init cross
        final Drawable cross = getResources().getDrawable(R.drawable.ic_clear_white_24dp);
        if(cross !=null){
            //@lv PorterDuff.Mode.SRC_ATOP,取下层非交集部分与上层交集部分
            cross.setColorFilter(getResources().getColor(R.color.icons), PorterDuff.Mode.SRC_ATOP);
        }

        /*
        @moss
        很多的Android应用左上角都有返回按钮, 在默认的情况下 ADT会默认给一个返回图标
        而作为开发需求 很多都要求定制一个新的图标
        以下的代码，是一个定制的toolbar，上面有一个X，点击X 则返回HOME
        这个toobar 被设置成 和页面在同一水平，不显示标题，
        */

        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        if(getSupportActionBar()!=null){
            /*
            @moss
            The action bar's elevation is the distance it is placed from its parent surface. Higher values are closer to the user.
            Set the Z-axis elevation of the action bar in pixels.
            this code indicates that, the X button is not floating
            */

            //@lv action bar和页面在一个平面?
            getSupportActionBar().setElevation(0);
            //@lv 不显示activity title/subtitle(boolean)
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            //@lv Set home should not be displayed as an "up" affordance.
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            //@lv Set an alternate drawable to display next to the icon/logo/title when DISPLAY_HOME_AS_UP is enabled.
            getSupportActionBar().setHomeAsUpIndicator(cross );

        }


        mUserToDoItem = (ToDoItem)getIntent().getSerializableExtra(MainActivity.TODOITEM);
        mUserEnteredText = mUserToDoItem.getToDoText();
        mUserHasReminder = mUserToDoItem.hasDueTime();
        mUserReminderDate = mUserToDoItem.getToDoDate();
        mUserColor = mUserToDoItem.getTodoColor();



        //@lv add reminder页面上的闹钟图标
        reminderIconImageButton = (ImageButton)findViewById(R.id.userToDoReminderIconImageButton);
        //@lv add reminder,text view,闹钟之后的文字
        /*
        i change the text from "Remind me" to "Due time"
        the xml file name is in res/layout/activity_add_to_do.xml
        the string is in res/values/string
         */
        reminderRemindMeTextView = (TextView)findViewById(R.id.userToDoRemindMeTextView);
        //@lv app theme不同时,ImageButton不同
        if(theme.equals(MainActivity.DARKTHEME)){
            reminderIconImageButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_alarm_add_white_24dp));
            reminderRemindMeTextView.setTextColor(Color.WHITE);
        }

        //@lv add reminder页面的layout
        mContainerLayout = (LinearLayout)findViewById(R.id.todoReminderAndDateContainerLayout);
        //@lv add reminder页面下半部分的layout
        mUserDateSpinnerContainingLinearLayout = (LinearLayout)findViewById(R.id.toDoEnterDateLinearLayout);
        //@lv text view for input title
        /*
        @moss
        this is the edit text, not a textView
        EditText 与 TextView共享大部分XML属性, 但是EditText可以接受用户输入;
        EditText最重要的属性是android:inputType, 该属性用来定义输入的数据类型;
        AutoCompletetextView, 该组件是带自动完成功能的组件, 通常与Adapter一起使用;
        ExtractEditText, EditText的底层服务类, 负责提供全屏输入法;
         */
        mToDoTextBodyEditText = (EditText)findViewById(R.id.userToDoEditText);
        //@lv A Switch is a two-state toggle switch widget that can select between two options.(SwitchCompat)
        mToDoDateSwitch = (SwitchCompat)findViewById(R.id.toDoHasDateSwitchCompat);
//        mLastSeenTextView = (TextView)findViewById(R.id.toDoLastEditedTextView);
        //action button
        mToDoSendFloatingActionButton = (FloatingActionButton)findViewById(R.id.makeToDoFloatingActionButton);
        //text view reminder set for ***
        mReminderTextView = (TextView)findViewById(R.id.newToDoDateTimeReminderTextView);

        //@lv 点击屏幕其他位置的时候隐藏键盘
        mContainerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard(mToDoTextBodyEditText);
            }
        });

        //@lv if有reminder&reminder date
        if(mUserHasReminder && (mUserReminderDate!=null)){
//            mUserDateSpinnerContainingLinearLayout.setVisibility(View.VISIBLE);
            //@lv setReminderTextView is a method
            setReminderTextView();
            setEnterDateLayoutVisibleWithAnimations(true);
        }
        if(mUserReminderDate==null){
            //@lv SwitchCompat
            /*
            @moss
            如果user 没有输入 reminder data， set “remind me” 为没有标记上
            因此，在界面底部也不会显示 “remind me ..”
             */
            mToDoDateSwitch.setChecked(false);
            mReminderTextView.setVisibility(View.INVISIBLE);
        }

//        TextInputLayout til = (TextInputLayout)findViewById(R.id.toDoCustomTextInput);
//        til.requestFocus();
        mToDoTextBodyEditText.requestFocus();

        /*
        @moss
        把edit text 里的文字设定为 这个文字
        原因是，如果这不是新加的todo的话， 可能是modify 已有的todo，所以，需要显示原有的文字。
         */
        mToDoTextBodyEditText.setText(mUserEnteredText);

        /*
        @moss
        InputMethodManager是一个用于控制显示或隐藏输入法面板的类（当然还有其他作用）。
         */
        InputMethodManager imm = (InputMethodManager)this.getSystemService(INPUT_METHOD_SERVICE);
//        imm.showSoftInput(mToDoTextBodyEditText, InputMethodManager.SHOW_IMPLICIT);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        mToDoTextBodyEditText.setSelection(mToDoTextBodyEditText.length());

        /*
        @moss
        if the content of the edit text is changed, it will trigger a text Changed listener
         */
        mToDoTextBodyEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mUserEnteredText = s.toString();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


//        String lastSeen = formatDate(DATE_FORMAT, mLastEdited);
//        mLastSeenTextView.setText(String.format(getResources().getString(R.string.last_edited), lastSeen));

        setEnterDateLayoutVisible(mToDoDateSwitch.isChecked());

        //@lv check reminder me的按钮是否划开
        /*@moss
        just set the following code to true is not right
        tried, failed.
         */
        mToDoDateSwitch.setChecked(mUserHasReminder && (mUserReminderDate != null));
        mToDoDateSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {



                if (!isChecked) {
                    mUserReminderDate = null;
                }
                mUserHasReminder = isChecked;
                setDateAndTimeEditText();
                setEnterDateLayoutVisibleWithAnimations(isChecked);
                hideKeyboard(mToDoTextBodyEditText);
            }
        });

        /*
        @moss
         set a on click listener to the fab
         if the reminder date is not null or the date is already passed, cancel the reminder
         otherwise, run the makeResult() method
         then, hide the key board, and finish this activity.
         */
        mToDoSendFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mUserReminderDate!=null && mUserReminderDate.before(new Date())){

                    makeResult(RESULT_CANCELED);
                }
                else{

                    makeResult(RESULT_OK);
                }
                hideKeyboard(mToDoTextBodyEditText);
                finish();
            }
        });

        /*
        @moss
        find the data EditText and time EditText view from R file
        and set on click listener to each of them separately
        newTodoDateEditText is from /res/layout/activity_to_test.xml
         */
        mDateEditText = (EditText)findViewById(R.id.newTodoDateEditText);
        mTimeEditText = (EditText)findViewById(R.id.newTodoTimeEditText);

        /*
        @moss

        if user clicks the data EditText, by default, it shows today
        if user chooses another date, then it changes to anther date
         */
        mDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Date date;
                hideKeyboard(mToDoTextBodyEditText);
                if(mUserToDoItem.getToDoDate()!=null){
//                    date = mUserToDoItem.getToDoDate();
                    date = mUserReminderDate;
                }
                else{
                    date = new Date();
                }
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(AddToDoActivity.this, year, month, day);
                if(theme.equals(MainActivity.DARKTHEME)){
                    datePickerDialog.setThemeDark(true);
                }
                datePickerDialog.show(getFragmentManager(), "DateFragment");

            }
        });


        mTimeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //@lv set reminder date
                Date date;
                hideKeyboard(mToDoTextBodyEditText);
                if(mUserToDoItem.getToDoDate()!=null){
//                    date = mUserToDoItem.getToDoDate();
                    date = mUserReminderDate;
                }
                else{
                    date = new Date();
                }
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(AddToDoActivity.this, hour, minute, DateFormat.is24HourFormat(AddToDoActivity.this));
                if(theme.equals(MainActivity.DARKTHEME)){
                    timePickerDialog.setThemeDark(true);
                }
                timePickerDialog.show(getFragmentManager(), "TimeFragment");
            }
        });

        setDateAndTimeEditText();

    }
    /*
    @moss
    if the item is already has a reminder, the time and date EditText is set to the default one
    otherwise, set it to today and next hour
     */
    private void setDateAndTimeEditText(){

        if(mUserToDoItem.hasDueTime() && mUserReminderDate!=null){
            String userDate = formatDate("d MMM, yyyy", mUserReminderDate);
            String formatToUse;
            if(DateFormat.is24HourFormat(this)){
                formatToUse = "k:mm";
            }
            else{
                formatToUse = "h:mm a";

            }
            String userTime = formatDate(formatToUse, mUserReminderDate);
            mTimeEditText.setText(userTime);
            mDateEditText.setText(userDate);

        }
        else{
            mDateEditText.setText(getString(R.string.date_reminder_default));
//            mUserReminderDate = new Date();
            boolean time24 = DateFormat.is24HourFormat(this);
            Calendar cal = Calendar.getInstance();
            if(time24){
                cal.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY)+1);
            }
            else{
                cal.set(Calendar.HOUR, cal.get(Calendar.HOUR)+1);
            }
            cal.set(Calendar.MINUTE, 0);
            mUserReminderDate = cal.getTime();
            Log.d("OskarSchindler", "Imagined Date: "+mUserReminderDate);
            String timeString;
            if(time24){
                timeString = formatDate("k:mm", mUserReminderDate);
            }
            else{
                timeString = formatDate("h:mm a", mUserReminderDate);
            }
            mTimeEditText.setText(timeString);
        }
    }

    private String getThemeSet(){
        return getSharedPreferences(MainActivity.THEME_PREFERENCES, MODE_PRIVATE).getString(MainActivity.THEME_SAVED, MainActivity.LIGHTTHEME);
    }
    public void hideKeyboard(EditText et){

        InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
    }


    /*
    @moss
    setDate,
    if user set a date the earlier to today
     a message will show
     */
    public void setDate(int year, int month, int day){
        Calendar calendar = Calendar.getInstance();
        int hour, minute;

        Calendar reminderCalendar = Calendar.getInstance();
        reminderCalendar.set(year, month, day);
        
        if(reminderCalendar.before(calendar)){
            Toast.makeText(this, "My time-machine is a bit rusty", Toast.LENGTH_SHORT).show();
            return;
        }

        if(mUserReminderDate!=null){
            calendar.setTime(mUserReminderDate);
        }

        if(DateFormat.is24HourFormat(this)){
            hour = calendar.get(Calendar.HOUR_OF_DAY);
        }
        else{

            hour = calendar.get(Calendar.HOUR);
        }
        minute = calendar.get(Calendar.MINUTE);

        calendar.set(year, month, day, hour, minute);
        mUserReminderDate = calendar.getTime();
        //@lv setReminderTextView is a method
        setReminderTextView();
//        setDateAndTimeEditText();
        setDateEditText();
    }

    public void setTime(int hour, int minute){
        Calendar calendar = Calendar.getInstance();
        if(mUserReminderDate!=null){
            calendar.setTime(mUserReminderDate);
        }


        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        Log.d("OskarSchindler", "Time set: "+hour);
        calendar.set(year, month, day, hour, minute, 0);
        mUserReminderDate = calendar.getTime();

        //@lv setReminderTextView is a method
        setReminderTextView();
//        setDateAndTimeEditText();
        setTimeEditText();
    }

    public void  setDateEditText(){
        String dateFormat = "d MMM, yyyy";
        mDateEditText.setText(formatDate(dateFormat, mUserReminderDate));
    }

    public void  setTimeEditText(){
        String dateFormat;
        if(DateFormat.is24HourFormat(this)){
            dateFormat = "k:mm";
        }
        else{
            dateFormat = "h:mm a";

        }
        mTimeEditText.setText(formatDate(dateFormat, mUserReminderDate));
    }

    //@lv 判断页面下方reminder set for *** 是否显示
    public void setReminderTextView(){
        if(mUserReminderDate!=null){
            //@lv 如果reminder date不为空,则reminder set for ***显示
            mReminderTextView.setVisibility(View.VISIBLE);
            //@lv reminder date 必须在today之后
            if(mUserReminderDate.before(new Date())){
                Log.d("OskarSchindler", "DATE is "+mUserReminderDate);
                mReminderTextView.setText(getString(R.string.date_error_check_again));
                mReminderTextView.setTextColor(Color.RED);
                return;
            }
            Date date = mUserReminderDate;
            //@lv 规定reminder date格式
            String dateString = formatDate("d MMM, yyyy", date);
            String timeString;
            String amPmString = "";

            //@lv reminder date使用24小时格式
            if(DateFormat.is24HourFormat(this)){
                timeString = formatDate("k:mm", date);
            }
            else{
                //@lv reminder date使用12小时格式
                timeString = formatDate("h:mm", date);
                amPmString = formatDate("a", date);
            }
            //@lv add to do页面最下方的reminder set for格式(string),颜色

            String finalString = String.format(getResources().getString(R.string.remind_date_and_time), dateString, timeString, amPmString);
            mReminderTextView.setTextColor(getResources().getColor(R.color.secondary_text));
            mReminderTextView.setText(finalString);
        }
        else{
            //@lv 如果reminder date为空,reminder set for ***不显示
            mReminderTextView.setVisibility(View.INVISIBLE);

        }
    }

    /*
    @moss
    set information for ToDoItems
     */
    public void makeResult(int result){
        Intent i = new Intent();
        if(mUserEnteredText.length()>0){

            String capitalizedString = Character.toUpperCase(mUserEnteredText.charAt(0))+mUserEnteredText.substring(1);
            mUserToDoItem.setToDoText(capitalizedString);
        }
        else{
            mUserToDoItem.setToDoText(mUserEnteredText);
        }

        /*
        @moss
        i guess this a method to set reminder
        todo: will try to comment it out to check whether it's a reminder
         */
//        if(mUserReminderDate!=null){
//            Calendar calendar = Calendar.getInstance();
//            calendar.setTime(mUserReminderDate);
//            calendar.set(Calendar.SECOND, 0);
//            mUserReminderDate = calendar.getTime();
//        }


        mUserToDoItem.setHasReminder(mUserHasReminder);
        mUserToDoItem.setToDoDate(mUserReminderDate);
        mUserToDoItem.setTodoColor(mUserColor);
        i.putExtra(MainActivity.TODOITEM, mUserToDoItem);
        setResult(result, i);
    }

    /*
    @moss

    override method if user press back button during this activity
     */
    @Override
    public void onBackPressed() {
        if(mUserReminderDate.before(new Date())){
            mUserToDoItem.setToDoDate(null);
        }
        makeResult(RESULT_OK);
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                if(NavUtils.getParentActivityName(this)!=null){

                    makeResult(RESULT_CANCELED);
                    NavUtils.navigateUpFromSameTask(this);
                }
                hideKeyboard(mToDoTextBodyEditText);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static String formatDate(String formatString, Date dateToFormat){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatString);
        return simpleDateFormat.format(dateToFormat);
    }

    @Override
    public void onTimeSet(RadialPickerLayout radialPickerLayout, int hour, int minute) {
        setTime(hour, minute);
    }

    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {
        setDate(year, month, day);
    }


    public void setEnterDateLayoutVisible(boolean checked){
        if(checked){
            //@lv mUserDateSpinnerContainingLinearLayout is linear layout
            mUserDateSpinnerContainingLinearLayout.setVisibility(View.VISIBLE);
        }
        else{
            mUserDateSpinnerContainingLinearLayout.setVisibility(View.INVISIBLE);
        }
    }

    public void setEnterDateLayoutVisibleWithAnimations(boolean checked){
        if(checked){
            //@lv setReminderTextView is a method
            setReminderTextView();
            //@lv linear layout is linear layout
            mUserDateSpinnerContainingLinearLayout.animate().alpha(1.0f).setDuration(500).setListener(
                    new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            //@lv linear layout 可见
                            mUserDateSpinnerContainingLinearLayout.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {
                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {
                        }
                    }
            );
        }
        else{
            mUserDateSpinnerContainingLinearLayout.animate().alpha(0.0f).setDuration(500).setListener(
                    new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            mUserDateSpinnerContainingLinearLayout.setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    }
            );
        }

    }
}

