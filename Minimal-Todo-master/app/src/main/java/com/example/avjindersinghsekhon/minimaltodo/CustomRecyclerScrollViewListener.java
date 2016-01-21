package com.example.avjindersinghsekhon.minimaltodo;

import android.support.v7.widget.RecyclerView;

/*
@moss this class is a abstract class, which means it can not have any object
however, any class wants to have a customRecycleScrollview have to extend this class
 */
public abstract class CustomRecyclerScrollViewListener extends RecyclerView.OnScrollListener {

    int scrollDist = 0;
    boolean isVisible = true;
    static final float MINIMUM = 20;

    /*
    @moss
    this method is to control the distance that user scrolls on the screen can hide the fab
     */
    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        if(isVisible && scrollDist>MINIMUM){
            hide();
            scrollDist = 0;
            isVisible = false;
        }
        else if(!isVisible && scrollDist < -MINIMUM){
            show();
            scrollDist = 0;
            isVisible =true;
        }
        if((isVisible && dy>0) || (!isVisible && dy<0)){
            scrollDist += dy;
        }
    }
    public abstract void show();
    public abstract void hide();
}
