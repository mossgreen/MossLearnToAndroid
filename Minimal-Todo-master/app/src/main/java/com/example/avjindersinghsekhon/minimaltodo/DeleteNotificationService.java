package com.example.avjindersinghsekhon.minimaltodo;

import android.app.IntentService;
import android.content.Intent;

import java.util.ArrayList;

/*

IntentService is a base class for Services that handle asynchronous requests (expressed as Intents) on demand.
Clients send requests through startService(Intent) calls;
the service is started as needed, handles each Intent in turn using a worker thread,
and stops itself when it runs out of work.

This "work queue processor" pattern is commonly used to offload tasks from an application's main thread.
The IntentService class exists to simplify this pattern and take care of the mechanics.
To use it, extend IntentService and implement onHandleIntent(Intent).
IntentService will receive the Intents, launch a worker thread, and stop the service as appropriate.

All requests are handled on a single worker thread --
they may take as long as necessary (and will not block the application's main loop),
 but only one request will be processed at a time.
 */
public class DeleteNotificationService extends IntentService {

    private StoreRetrieveData storeRetrieveData;
    private ArrayList<ToDoItem> mToDoItems;
    private ToDoItem mItem;

    /*
    constructor
     */
    public DeleteNotificationService(){
        super("DeleteNotificationService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }


    private void saveData(){
        try{
            storeRetrieveData.saveToFile(mToDoItems);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    @moss
    save the data when onDestroy()
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        saveData();
    }

    private ArrayList<ToDoItem> loadData(){
        try{
            return storeRetrieveData.loadFromFile();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return null;

    }
}
