package com.example.avjindersinghsekhon.minimaltodo;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

/*this class is for store and retrieve data*/
public class StoreRetrieveData {
    private Context mContext;
    private String mFileName;

    /**
     * constructer
     * @param context   the context of one activity
     * @param filename  file name
     */
    public StoreRetrieveData(Context context, String filename){
        mContext = context;
        mFileName = filename;
    }

    /*
     * static method
     * write date to a Json Array
     */
    public static JSONArray toJSONArray(ArrayList<ToDoItem> items) throws JSONException{
        /*
        JSONArray class has a dense indexed sequence of values.
        JSONArray() Creates a JSONArray with no values.
         */
        JSONArray jsonArray = new JSONArray();

        /* first, create am empty array, the type is Json,
        then, to iterate over the dataset and
        and transfer each of items to a json object
        finally, put this object to the json Array that crated above
        return this json array*/
        for(ToDoItem item : items){
            JSONObject jsonObject = item.toJSON();
            jsonArray.put(jsonObject);
        }
        return  jsonArray;
    }
    /*
    void method
    used to write a json array to file
     */

    public void saveToFile(ArrayList<ToDoItem> items) throws JSONException, IOException{
        /*
        since will write json doc to file
        first, it initializes a file output stream and output steam writer
        then, open the file out put steam, with the name of file
        write the "item", which is the param of this method, to the file, as a json Array, in string

         */
        FileOutputStream fileOutputStream;
        OutputStreamWriter outputStreamWriter;
        fileOutputStream = mContext.openFileOutput(mFileName, Context.MODE_PRIVATE);
        outputStreamWriter = new OutputStreamWriter(fileOutputStream);
        outputStreamWriter.write(toJSONArray(items).toString());
        outputStreamWriter.close();
        fileOutputStream.close();

        /*
        由于SharedPreferences使用到Context.MODE_PRIVATE，记录如下：

        Context.MODE_PRIVATE：为默认操作模式，代表该文件是私有数据，只能被应用本身访问.
            在该模式下，写入的内容会覆盖原文件的内容，如果想把新写入的内容追加到原文件中。可以使用Context.MODE_APPEND
        Context.MODE_APPEND：模式会检查文件是否存在，存在就往文件追加内容，否则就创建新文件。
        Context.MODE_WORLD_READABLE和Context.MODE_WORLD_WRITEABLE用来控制其他应用是否有权限读写该文件。
        MODE_WORLD_READABLE：表示当前文件可以被其他应用读取；
        MODE_WORLD_WRITEABLE：表示当前文件可以被其他应用写入。
         */
    }


    /*
    this method is to load data from a file,
    return an ArrayList, type of ToDoItem
     */
    public ArrayList<ToDoItem> loadFromFile() throws IOException, JSONException{
        ArrayList<ToDoItem> items = new ArrayList<>();
        BufferedReader bufferedReader = null;
        FileInputStream fileInputStream = null;
        try {

            /*
            to read file contend from a file by using a string builder
            append the string that read to the string builder, while retrieving data from the file
            we get json array by using json tokener, which has a nextValue() method can retrieve josn object
            iterate the json array, get json object from each item of the json array
            generate the ToDoitem using the json object
            add this item to the ArrayList of <ToDoItem> named items


             */
            fileInputStream =  mContext.openFileInput(mFileName);
            StringBuilder builder = new StringBuilder();
            String line;
            bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));

            while((line = bufferedReader.readLine())!=null){
                builder.append(line);
            }

            //通过JSONTokener的nextValue()来获得JSONObject对象，然后再通过JSONObject对象来做进一步的解析。
            JSONArray jsonArray = (JSONArray)new JSONTokener(builder.toString()).nextValue();
            for(int i =0; i<jsonArray.length();i++){
                ToDoItem item = new ToDoItem(jsonArray.getJSONObject(i));
                items.add(item);
            }


        } catch (FileNotFoundException fnfe) {
            //do nothing about it
            //file won't exist first time app is run
        }
        finally {
            if(bufferedReader!=null){
                bufferedReader.close();
            }
            if(fileInputStream!=null){
                fileInputStream.close();
            }

        }
        return items;
    }

}
