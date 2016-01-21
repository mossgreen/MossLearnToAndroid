package com.example.avjindersinghsekhon.minimaltodo;

import android.app.IntentService;
import android.content.Intent;


public class TodoNotificationService extends IntentService {
    public static final String TODOTEXT = "com.example.approaching.todonotificationservicetext";
    public static final String TODOUUID = "com.example.approaching.todonotificationserviceuuid";


    public TodoNotificationService(){
        super("TodoNotificationService");
    }
    @Override
    protected void onHandleIntent(Intent intent) {}


    //todo may add function to notify user

}
