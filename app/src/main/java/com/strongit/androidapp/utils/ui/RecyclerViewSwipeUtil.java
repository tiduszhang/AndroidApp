package com.strongit.androidapp.utils.ui;

import android.content.Context;
import android.databinding.ObservableList;
import android.support.v7.widget.LinearLayoutManager;
import com.daimajia.swipe.util.Attributes;
import com.strongit.androidapp.utils.adapter.RecyclerViewSwipeAdapter;

/**
 * 侧滑菜单工具类
 */
public class RecyclerViewSwipeUtil {

    public static void setRecyclerViewAdapter(Context context, android.support.v7.widget.RecyclerView recyclerView, ObservableList observableList, int swipeLayoutId, RecyclerViewSwipeAdapter mNewsAdapter) {
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);

        //设置适配器
        if(mNewsAdapter == null){
            //设置适配器
            mNewsAdapter = new RecyclerViewSwipeAdapter(context, observableList, swipeLayoutId);
            mNewsAdapter.setMode(Attributes.Mode.Single);//设置只有一个拖拽打开的时候，其他的关闭
            recyclerView.setAdapter(mNewsAdapter);
            //添加分割线
            //设置添加删除动画
            //调用ListView的setSelected(!ListView.isSelected())方法，这样就能及时刷新布局
            recyclerView.setSelected(true);
        }else{
            mNewsAdapter.notifyDataSetChanged();
        }
    }
}
