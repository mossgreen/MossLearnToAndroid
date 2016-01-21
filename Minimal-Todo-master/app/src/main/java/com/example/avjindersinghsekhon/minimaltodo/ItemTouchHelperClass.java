package com.example.avjindersinghsekhon.minimaltodo;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/*
@moss
ItemTouchHelper是一个强大的工具，它处理好了关于在RecyclerView上添加拖动排序与滑动删除的所有事情。
它是RecyclerView.ItemDecoration的子类，
也就是说它可以轻易的添加到几乎所有的LayoutManager和Adapter中。
它还可以和现有的item动画一起工作，提供受类型限制的拖放动画等等.
要使用ItemTouchHelper，你需要创建一个ItemTouchHelper.Callback。
这个接口可以让你监听“move”与 “swipe”事件。
 */
public class ItemTouchHelperClass extends ItemTouchHelper.Callback {

    /*
    @moss
     这是一个引用adapter的成员变量
     */
    private ItemTouchHelperAdapter adapter;

    /*
    @moss
    this is an interface, which means who implements this interface, has to rewrite these methods
    first, when moved an item
    second, deleted an item
     */

    /*
    @moss
    本类有onMove()和onSwiped()连个方法，用于通知底层数据的更新。
    首先我们创建一个可以将这些回调方法传递出去的接口。
     */
    public interface ItemTouchHelperAdapter {
        void onItemMoved(int fromPosition, int toPosition);

        void onItemRemoved(int position);
    }

    public ItemTouchHelperClass(ItemTouchHelperAdapter ad) {
        adapter = ad;
    }

    /*
    @moss
    ItemTouchHelper可以用于没有滑动的拖动操作（或者反过来），你必须指明你到底要支持哪一种。
    要支持长按RecyclerView item进入拖动操作，你必须在isLongPressDragEnabled()方法中返回true。
    或者，也可以调用ItemTouchHelper.startDrag(RecyclerView.ViewHolder) 方法来开始一个拖动。
     */
    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    /*
    要在view任意位置触摸事件发生时启用滑动操作，则直接在sItemViewSwipeEnabled()中返回true就可以了。
    或者，你也主动调用ItemTouchHelper.startSwipe(RecyclerView.ViewHolder) 来开始滑动操作。
     */
    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    /*
    @moss
    ItemTouchHelper可以让你轻易得到一个事件的方向。
    你需要重写getMovementFlags()方法来指定可以支持的拖放和滑动的方向。
    使用helperItemTouchHelper.makeMovementFlags(int, int)来构造返回的flag。
    这里我们启用了上下左右两种方向。注：上下为拖动（drag），左右为滑动（swipe）。

     */
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int upFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;

        return makeMovementFlags(upFlags, swipeFlags);
    }

    /*
    @moss
    onMove()拖動item和onSwiped()向右或向左刷掉，用于通知底层数据的更新。
     */
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        adapter.onItemMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }
    /*
    @moss
    onMove()拖動item和onSwiped()向右或向左刷掉，用于通知底层数据的更新。
     */
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        adapter.onItemRemoved(viewHolder.getAdapterPosition());

    }

//    @SuppressWarnings("deprecation")
//    @Override
//    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
//        if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE){
//            View itemView = viewHolder.itemView;
//            Paint p = new Paint();
//
//
//            MainActivity.BasicListAdapter.ViewHolder vh= (MainActivity.BasicListAdapter.ViewHolder)viewHolder;
//            p.setColor(recyclerView.getResources().getColor(R.color.primary_light));
//
//            if(dX > 0){
//                c.drawRect((float)itemView.getLeft(), (float)itemView.getTop(), dX, (float)itemView.getBottom(), p);
//                String toWrite = "Left"+itemView.getLeft()+" Top "+itemView.getTop()+" Right "+dX+" Bottom "+itemView.getBottom();
////                Log.d("OskarSchindler", toWrite);
//            }
//            else{
//                String toWrite = "Left"+(itemView.getLeft()+dX)+" Top "+itemView.getTop()+" Right "+dX+" Bottom "+itemView.getBottom();
////                Log.d("OskarSchindler", toWrite);
//                c.drawRect((float) itemView.getRight() + dX, (float) itemView.getTop(), (float) itemView.getRight(), (float) itemView.getBottom(), p);
//            }
//            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
//        }
//    }
}
