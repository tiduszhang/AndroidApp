package com.strongit.androidapp.ui.vm;

import android.content.Context;
import com.strongit.androidapp.entity.DemoEntity;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class DemoItemViewModel extends BaseViewModel {

    public  DemoEntity.ItemsEntity  entity;

    public DemoItemViewModel(Context context,  DemoEntity.ItemsEntity entity) {
        super(context);

        this.entity = entity;
    }

    //条目的点击事件
    public BindingCommand itemClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            ToastUtils.showShort("itemClick");
        }
    });
    //条目的长按事件
    public BindingCommand itemLongClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            ToastUtils.showShort("itemLongClick");
        }
    });

    //删除
    public  BindingCommand itemDelectClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            ToastUtils.showShort("itemDelectClick");
        }
    });

    //置顶
    public  BindingCommand itemTopClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            ToastUtils.showShort("itemTopClick");
        }
    });
}
