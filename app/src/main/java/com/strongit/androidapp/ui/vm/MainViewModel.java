package com.strongit.androidapp.ui.vm;

import android.content.Context;
import android.databinding.ObservableField;
import android.text.TextUtils;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class MainViewModel extends BaseViewModel {

    public MainViewModel(Context context) {
        super(context);
    }

    //用户名的绑定
    public ObservableField<String> userName = new ObservableField<>("");


    //登录按钮的点击事件
    public BindingCommand mainOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            showMessage();
        }
    });


    private  void  showMessage(){
        if (TextUtils.isEmpty(userName.get())) {
            ToastUtils.showShort("当前文本框没有输入内容！");
        }else {
            ToastUtils.showShort("当前文本框输入内容是“" + userName.get() + "”！");
        }
    }
}
