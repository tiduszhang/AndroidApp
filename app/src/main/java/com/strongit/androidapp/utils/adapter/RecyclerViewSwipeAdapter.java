package com.strongit.androidapp.utils.adapter;

import android.content.Context;
import android.databinding.ObservableList;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.implments.SwipeItemRecyclerMangerImpl;
import com.daimajia.swipe.interfaces.SwipeAdapterInterface;
import com.daimajia.swipe.interfaces.SwipeItemMangerInterface;
import com.daimajia.swipe.util.Attributes;
import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter;

import java.util.List;

public class RecyclerViewSwipeAdapter<T> extends BindingRecyclerViewAdapter<RecyclerView.ViewHolder> implements SwipeItemMangerInterface, SwipeAdapterInterface {


    //展现控制接口
    public SwipeItemRecyclerMangerImpl mItemManger = new SwipeItemRecyclerMangerImpl(this);

        /**上下文*/
    private Context myContext;
    /**集合*/
    private ObservableList<T> listitemList;
    private int swipeLayoutid;
    private int news_list_itemid;
    private int bottom_wrapperid;

    /**
     * 构造函数
     */
    public RecyclerViewSwipeAdapter(Context context, ObservableList<T> itemlist, int swipeLayoutid, int news_list_itemid, int bottom_wrapperid) {
        this.myContext = context;
       this. listitemList = itemlist;
        this.swipeLayoutid = swipeLayoutid;
        this.news_list_itemid = news_list_itemid;
        this.bottom_wrapperid =bottom_wrapperid;

        //添加数据变化监听
        listitemList.addOnListChangedCallback(new ObservableList.OnListChangedCallback<ObservableList<T>>() {
            @Override
            public void onChanged(ObservableList<T> sender) {
                //onChanged(sender);
            }

            @Override
            public void onItemRangeChanged(ObservableList<T> sender, int positionStart, int itemCount) {
                notifyItemChanged(positionStart);
            }

            @Override
            public void onItemRangeInserted(ObservableList<T> sender, int positionStart, int itemCount) {
                notifyItemInserted(positionStart);
            }

            @Override
            public void onItemRangeMoved(ObservableList<T> sender, int fromPosition, int toPosition, int itemCount) {
                notifyItemMoved(fromPosition,toPosition);
            }

            @Override
            public void onItemRangeRemoved(ObservableList<T> sender, int positionStart, int itemCount) {
                notifyItemRemoved(positionStart);
            }
        });
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, int position, List<Object> payloads) {

        View view = viewHolder.itemView;
        final SwipeLayout swipeLayout = view.findViewById(swipeLayoutid);

        swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
//        swipeLayout.close();

//        swipeLayout.addSwipeListener(new SimpleSwipeListener() {
//            @Override
//            public void onOpen(SwipeLayout layout) {
//                //实现动画效果展现隐藏层
//                YoYo.with(Techniques.Tada).duration(500).delay(100).playOn(layout.findViewById(bottom_wrapperid));
//            }
//        });
//        swipeLayout.getSurfaceView().setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                swipeLayout.close();//隐藏侧滑菜单区域
//
//            }
//        });
//        //长按事件
//        swipeLayout.getSurfaceView().setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View view) {
//                swipeLayout.close();//隐藏侧滑菜单区域
//                return false;
//            }
//        });



        mItemManger.bindView(viewHolder.itemView, position);//实现只展现一条列表项的侧滑区域

        super.onBindViewHolder(viewHolder, position, payloads);
    }

    /**
     * 获取总的条目数
     */
    @Override
    public int getItemCount() {
        return listitemList.size();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        //return 0;
        return swipeLayoutid;//实现只展现一条列表项的侧滑区域
    }

    public void openItem(int position) {
        mItemManger.openItem(position);
    }

    public void closeItem(int position) {
        mItemManger.closeItem(position);
    }

    public void closeAllExcept(SwipeLayout layout) {
        mItemManger.closeAllExcept(layout);
    }

    public void closeAllItems() {
        mItemManger.closeAllItems();
    }

    public List<Integer> getOpenItems() {
        return mItemManger.getOpenItems();
    }

    public List<SwipeLayout> getOpenLayouts() {
        return mItemManger.getOpenLayouts();
    }

    public void removeShownLayouts(SwipeLayout layout) {
        mItemManger.removeShownLayouts(layout);
    }

    public boolean isOpen(int position) {
        return mItemManger.isOpen(position);
    }

    public Attributes.Mode getMode() {
        return mItemManger.getMode();
    }

    public void setMode(Attributes.Mode mode) {
        mItemManger.setMode(mode);
    }
}
