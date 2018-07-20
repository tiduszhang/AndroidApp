package com.strongit.androidapp.ui.activity;

import android.os.Bundle;
import com.strongit.androidapp.BR;
import com.strongit.androidapp.R;
import com.strongit.androidapp.databinding.ActivityTestBinding;
import com.strongit.androidapp.ui.vm.TestViewModel;
import me.goldze.mvvmhabit.base.BaseActivity;

public class TestActivity extends BaseActivity<ActivityTestBinding, TestViewModel> {

    @Override
    public int initContentView(Bundle bundle) {
        return R.layout.activity_test;
    }

    @Override
    public int initVariableId() {
        return BR.testViewModel;
    }

    @Override
    public TestViewModel initViewModel() {
        //View持有ViewModel的引用 (考虑到框架适用性，这里暂时没有用Dagger2解耦)
        return new TestViewModel(this);
    }

}