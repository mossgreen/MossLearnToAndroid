package com.example.avjindersinghsekhon.minimaltodo;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;

/*
@moss
CoordinatorLayout 实现了多种Material Design中提到的滚动效果。目前这个框架提供了几种不用写动画代码就能工作的方法。
只要使用CoordinatorLayout作为基本布局，将自动产生向上移动的动画。
浮动操作按钮有一个 默认的 behavior来检测Snackbar的添加并让按钮在Snackbar之上呈现上移与Snackbar等高的动画

CoordinatorLayout的工作原理是搜索定义了CoordinatorLayout Behavior 的子view，
不管是通过在xml中使用app:layout_behavior标签还是通过在代码中对view类使用@DefaultBehavior修饰符来添加注解。
当滚动发生的时候，CoordinatorLayout会尝试触发那些声明了依赖的子view。

要自己定义CoordinatorLayout Behavior，需要实现layoutDependsOn() 和onDependentViewChanged()两个方法
它们用于跟踪CoordinatorLayout中其他view的变化
 */
public class ScrollingFABBehaviour extends CoordinatorLayout.Behavior<FloatingActionButton> {
    private int toolbarHeight;
    private static boolean scrolledUp = false;
    private static boolean scrolledDown = false;

    public  ScrollingFABBehaviour(Context context, AttributeSet attributeSet){
        super(context, attributeSet);
        this.toolbarHeight = Utils.getToolbarHeight(context);
    }


    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, FloatingActionButton child, View dependency) {
        return (dependency instanceof Snackbar.SnackbarLayout) || (dependency instanceof Toolbar);
//        return (dependency instanceof Snackbar.SnackbarLayout);
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, final FloatingActionButton child, View dependency) {
        if(dependency instanceof Snackbar.SnackbarLayout){
            float finalVal = (float)parent.getHeight() - dependency.getY();
            child.setTranslationY(-finalVal);
        }
//
//        if(dependency instanceof RecyclerView){
//            RecyclerView view = (RecyclerView)dependency;
//            CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams)child.getLayoutParams();
//            int fabBottomMargin = lp.bottomMargin;
//            final int distanceToScroll = child.getHeight() + fabBottomMargin;
//
//            final RecyclerView.LayoutManager rlp = (RecyclerView.LayoutManager)view.getLayoutManager();
//            Log.d("OskarSchindler", "Height: "+rlp.getHeight());
//
//
//
//        }
        if(dependency instanceof Toolbar){
            CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams)child.getLayoutParams();
            int fabBottomMargin = lp.bottomMargin;
            int distanceToScroll = child.getHeight() + fabBottomMargin;
            float finalVal = dependency.getY()/(float)toolbarHeight;
            float distFinal = -distanceToScroll * finalVal;
            child.setTranslationY(distFinal);
        }


        return true;
    }

}
