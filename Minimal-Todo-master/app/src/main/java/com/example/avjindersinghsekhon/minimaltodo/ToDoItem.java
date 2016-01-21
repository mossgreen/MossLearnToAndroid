package com.example.avjindersinghsekhon.minimaltodo;

import android.graphics.Color;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/*
@moss
Android中Intent中如何传递对象,目前所知道的有两种方法，
一种是Bundle.putSerializable(Key,Object);
另一种是Bundle.putParcelable(Key, Object);
当然这些Object是有一定的条件的，前者是实现了Serializable接口，而后者是实现了Parcelable接口


Java的对象序列化是指将那些实现了Serializable接口的对象转换成一个字符序列，
并能够在以后将这个字节序列完全恢复为原来的对象。
这一过程甚至可通过网络进行，这意味着序列化机制能自动弥补不同操作系统之间的差异。
只要对象实现了Serializable接口
这个接口只是一个标记接口，不包含任何的方法
 */
public class ToDoItem implements Serializable{
    private String mToDoText;
    private boolean mHasReminder;
//    private Date mLastEdited;
    private int mTodoColor;
    private Date mToDoDate;
    /*
    @moss
    add this variable to record the time left to the due time
     */
    private UUID mTodoIdentifier;
    private static final String TODOTEXT = "todotext";
    private static final String TODOREMINDER = "todoreminder";
//    private static final String TODOLASTEDITED = "todolastedited";
    private static final String TODOCOLOR = "todocolor";
    private static final String TODODATE = "tododate";
    private static final String TODOIDENTIFIER = "todoidentifier";

    private static final String TODOCOUNTING = "todocounting";


    public ToDoItem(String todoBody, boolean hasReminder, Date toDoDate){
        mToDoText = todoBody;
        mHasReminder = hasReminder;
        mToDoDate = toDoDate;
//        mCountingDate =
        mTodoColor = Color.LTGRAY;
        mTodoIdentifier = UUID.randomUUID();
    }

    /*
    @moss
    anther constructor of this class
    it will get string from json object
    and get information like whether this object has a reminder
    and the color of the text
    check whether the object  has a to do data
     */
    public ToDoItem(JSONObject jsonObject) throws JSONException{
        mToDoText = jsonObject.getString(TODOTEXT);
        mHasReminder = jsonObject.getBoolean(TODOREMINDER);
        mTodoColor = jsonObject.getInt(TODOCOLOR);
        mTodoIdentifier = UUID.fromString(jsonObject.getString(TODOIDENTIFIER));

//        if(jsonObject.has(TODOLASTEDITED)){
//            mLastEdited = new Date(jsonObject.getLong(TODOLASTEDITED));
//        }
        if(jsonObject.has(TODODATE)){
            mToDoDate = new Date(jsonObject.getLong(TODODATE));
        }
    }

    /*
    @moss this is public method
    this method is to put content to json object
     */
    public JSONObject toJSON() throws JSONException{
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(TODOTEXT, mToDoText);
        jsonObject.put(TODOREMINDER, mHasReminder);
//        jsonObject.put(TODOLASTEDITED, mLastEdited.getTime());
        if(mToDoDate!=null){
            jsonObject.put(TODODATE, mToDoDate.getTime());
        }
        jsonObject.put(TODOCOLOR, mTodoColor);
        jsonObject.put(TODOIDENTIFIER, mTodoIdentifier.toString());

        return jsonObject;
    }

    /*
    @moss
    setters and getters
     */
    public ToDoItem(){
        this("Clean my room", true, new Date());
    }

    public String getToDoText() {
        return mToDoText;
    }

    public void setToDoText(String mToDoText) {
        this.mToDoText = mToDoText;
    }

    public boolean hasDueTime() {
        return mHasReminder;
    }

    public void setHasReminder(boolean mHasReminder) {
        this.mHasReminder = mHasReminder;
    }

    public Date getToDoDate() {
        return mToDoDate;
    }


/*
@moss
this method a getter of todoColor
if
 */
     public int getTodoColor() {

        if (mToDoDate != null) {

            Date curDate = Calendar.getInstance().getTime();
            Long timeSpan = this.mToDoDate.getTime() - curDate.getTime();


            if(timeSpan >= 1000 * 60 * 60 * 24 * 3){
                this.mTodoColor = MainActivity.ICON_GREEN;
            }else if(timeSpan > 1){
                this.mTodoColor = MainActivity.ICON_RED;
            }else{
                this.mTodoColor = Color.GRAY;
            }

        }

        return mTodoColor;
    }

    public void setTodoColor(int mTodoColor) {

        this.mTodoColor = mTodoColor;


    }

    public void setToDoDate(Date mToDoDate) {
        this.mToDoDate = mToDoDate;
    }




    public UUID getIdentifier(){
        return mTodoIdentifier;
    }
}

