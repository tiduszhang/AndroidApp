package com.strongit.androidapp.ui.vm;

import android.content.Context;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableList;
import android.os.Handler;
import com.strongit.androidapp.R;
import com.strongit.androidapp.BR;
import com.strongit.androidapp.entity.DemoEntity;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.utils.ToastUtils;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class DemoViewModel extends BaseViewModel {
       public DemoViewModel(Context context) {
        super(context);
    }

    //给RecyclerView添加ObservableList
    public ObservableList<DemoItemViewModel> observableList = new ObservableArrayList<>();
    //给RecyclerView添加ItemView
    public ItemBinding<DemoItemViewModel> itemBinding = ItemBinding.of(BR.demoItemViewModel, R.layout.activity_demo_item);

    //封装一个界面发生改变的观察者
    public UIChangeObservable uc = new UIChangeObservable();

    public class UIChangeObservable {
        //下拉刷新完成的观察者
        public ObservableBoolean isFinishRefreshing = new ObservableBoolean(false);
        //上拉加载完成的观察者
        public ObservableBoolean isFinishLoadmore = new ObservableBoolean(false);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        //模拟网络请求完成后收回
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                //模拟一部分假数据
                for (int i = 0; i < 40; i++) {
                    DemoEntity.ItemsEntity item = new DemoEntity.ItemsEntity();
                    item.setName("模拟条目" + i);
                    //动态添加Item
                    observableList.add(new DemoItemViewModel(context, item));
                }
            }
        }, 1000);
    }


    //下拉刷新
    public BindingCommand onRefreshCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            ToastUtils.showShort("下拉刷新");
            //重新请求

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    //刷新完成收回
                    uc.isFinishRefreshing.set(!uc.isFinishRefreshing.get());
                }
            }, 1000);

        }
    });

    //上拉加载
    public BindingCommand onLoadMoreCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            ToastUtils.showShort("上拉加载");
            //重新请求

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    //刷新完成收回
                    uc.isFinishLoadmore.set(!uc.isFinishLoadmore.get());
                }
            }, 1000);
        }
    });




}
