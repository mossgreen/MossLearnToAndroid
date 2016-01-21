package com.example.avjindersinghsekhon.minimaltodo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

/*
@moss
RecyclerView是 Android 兼容包V21中新推出的列表类，
它的自定义化强的优点足以让它能够取代GridView和ListView
他可以通过设置LayoutManager来快速实现listview、gridview、瀑布流的效果，
而且还可以设置横向和纵向显示，添加动画效果也非常简单
(自带了ItemAnimation，可以设置加载和移除时的动画，方便做出各种动态浏览的效果),是官方推荐使用的

首先要用这个控件，你需要在gradle文件中添加包的引用（配合官方CardView使用）
然后是在XML文件用使用它,
接着在Activity中设置它
然后是适配器代码, (这个类大概就是适配器）
*/
public class RecyclerViewEmptySupport extends RecyclerView {
    private View emptyView;

    /*
    @moss
    Observer base class for watching changes to an RecyclerView.Adapter.
     */
    private AdapterDataObserver observer = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            showEmptyView();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            super.onItemRangeInserted(positionStart, itemCount);
            showEmptyView();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            super.onItemRangeRemoved(positionStart, itemCount);
            showEmptyView();
        }
    };


    public RecyclerViewEmptySupport(Context context) {
        super(context);
    }

    public void showEmptyView() {

        Adapter<?> adapter = getAdapter();
        if (adapter != null && emptyView != null) {
            if (adapter.getItemCount() == 0) {
                emptyView.setVisibility(VISIBLE);
                RecyclerViewEmptySupport.this.setVisibility(GONE);
            } else {
                /*
                @moss
                VISIBLE：设置控件可见; INVISIBLE：设置控件不可见; GONE：设置控件隐藏
                而INVISIBLE和GONE的主要区别是：
                当控件visibility属性为INVISIBLE时，界面保留了view控件所占有的空间；
                而控件属性为GONE时，界面则不保留view控件所占有的空间。
                 */
                emptyView.setVisibility(GONE);
                RecyclerViewEmptySupport.this.setVisibility(VISIBLE);
            }
        }

    }

    public RecyclerViewEmptySupport(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RecyclerViewEmptySupport(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
        if (adapter != null) {

            /*
            @moss
             the only callbacks that get triggered inside the RecyclerView.AdapterDataObserver
             are a result of which notify methods you call.
             So for instance if you call notifyItemInserted() after you add an item to your adapter
             then onItemRangeInserted() will get called.
             */
            adapter.registerAdapterDataObserver(observer);
            observer.onChanged();
        }
    }

    public void setEmptyView(View v) {
        emptyView = v;
    }
}
