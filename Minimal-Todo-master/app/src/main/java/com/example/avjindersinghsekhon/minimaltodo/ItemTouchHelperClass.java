package com.example.avjindersinghsekhon.minimaltodo;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

// ItemTouchHelper是一个强大的工具，它处理好了关于在RecyclerView上添加拖动排序与滑动删除的所有事情。它是RecyclerView.ItemDecoration的子类，
// 也就是说它可以轻易的添加到几乎所有的LayoutManager和Adapter中。它还可以和现有的item动画一起工作，提供受类型限制的拖放动画等等，
public class ItemTouchHelperClass extends ItemTouchHelper.Callback{
    private ItemTouchHelperAdapter adapter;
    public interface ItemTouchHelperAdapter{
        void onItemMoved(int fromPosition, int toPosition);
        void onItemRemoved(int position);
    }

    public ItemTouchHelperClass(ItemTouchHelperAdapter ad){
        adapter = ad;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int upFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;

        return makeMovementFlags(upFlags, swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        adapter.onItemMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

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
