package com.strongit.androidapp.ui.vm;

import android.content.Context;
import android.databinding.ObservableField;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.binding.command.BindingConsumer;
import me.goldze.mvvmhabit.bus.Messenger;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class MainViewModel extends BaseViewModel {
    public static final String SHOW_MESSAGE = "SHOW_MESSAGE";
    public static final String WRITE_STRING = "WRITE_STRING";
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

    @Override
    public void registerRxBus() {
        super.registerRxBus();

        Messenger.getDefault().register(this, SHOW_MESSAGE, String.class,new BindingConsumer<String>(){
                @Override
                public void call(String s) {
                    Message msg = new Message();
                    msg.what = 1;
                    msg.obj = s;
                    handlerToastUtils.sendMessage(msg);
                }
            }
        );

        Messenger.getDefault().register(this, WRITE_STRING, String.class,new BindingConsumer<String>() {
                    @Override
                    public void call(String s) {
                        userName.set(userName.get() + s);
                    }
                }
        );
    }

    @Override
    public void removeRxBus() {
        super.removeRxBus();
        Messenger.getDefault().unregister(this, SHOW_MESSAGE);
        Messenger.getDefault().unregister(this, WRITE_STRING);
    }

    String strMessage = "这是一段Android程序生成的字符串！";
    int i = 0;

    public  void showToastMessage(String message){
        Messenger.getDefault().send(message, SHOW_MESSAGE);
    }


    private void showMessage() {
        if (TextUtils.isEmpty(userName.get())) {
            showToastMessage("当前文本框没有输入内容！");
        } else {
            showToastMessage("当前文本框输入内容是“" + userName.get() + "”！");
        }
        userName.set("");
        i = 0;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    showToastMessage("开始打字！");
                    for (i = 0; i < strMessage.length(); i++) {

                        Messenger.getDefault().send(strMessage.charAt(i) + "", WRITE_STRING);

                        Thread.sleep(200);
                    }
                    showToastMessage("完成打字！");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    Handler handlerToastUtils = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1){
                ToastUtils.showShort(msg.obj.toString());
            }
            super.handleMessage(msg);
        }
    };
}
