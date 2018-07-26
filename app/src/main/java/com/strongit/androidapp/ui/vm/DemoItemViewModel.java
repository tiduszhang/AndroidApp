package com.strongit.androidapp.ui.vm;

import android.content.Context;
import com.strongit.androidapp.entity.DemoEntity;
import me.goldze.mvvmhabit.base.BaseViewModel;

public class DemoItemViewModel extends BaseViewModel {

    public  DemoEntity.ItemsEntity  entity;

    public DemoItemViewModel(Context context,  DemoEntity.ItemsEntity entity) {
        super(context);

        this.entity = entity;
    }



}
