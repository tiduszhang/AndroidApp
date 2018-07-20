package com.strongit.androidapp.ui.vm;

import android.content.Context;
import android.databinding.ObservableField;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.binding.command.BindingConsumer;
import me.goldze.mvvmhabit.bus.Messenger;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class TestViewModel extends BaseViewModel {
    public static final String SHOW_MESSAGE = "SHOW_MESSAGE";
    public static final String WRITE_STRING = "WRITE_STRING";

    public TestViewModel(Context context) {
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

        Messenger.getDefault().register(this, SHOW_MESSAGE, String.class, new BindingConsumer<String>() {
                    @Override
                    public void call(String s) {
                        Message msg = new Message();
                        msg.what = 1;
                        msg.obj = s;
                        handlerToastUtils.sendMessage(msg);
                    }
                }
        );

        Messenger.getDefault().register(this, WRITE_STRING, String.class, new BindingConsumer<String>() {
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


    @Override
    public void onDestroy() {
        super.onDestroy();
        isClose = true;
    }

    String strMessage = "这是一段Android程序生成的字符串！";
    int i = 0;

    public void showToastMessage(String message) {
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


        if(!task.getStatus().equals(AsyncTask.Status.RUNNING)) {

            task.execute(new Void[]{});
        }
//
//        if (!thread.isAlive()) {
//            thread.start();
//        }
//
        if (!isWriting) {
            isWriting = true;
        }
    }

    Handler handlerToastUtils = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                ToastUtils.showShort(msg.obj.toString());
            }
            super.handleMessage(msg);
        }
    };

    boolean isWriting = false;

    Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
//            while (true) {
//                try {
//                    if (!isWriting) {
//                        Thread.sleep(1000);
//                        continue;
//                    }
//                    showToastMessage("开始打字！");
//                    for (i = 0; i < strMessage.length(); i++) {
//                        Thread.sleep(250);
//                        //userName.set(userName.get() + strMessage.charAt(i));
//                        Messenger.getDefault().send(strMessage.charAt(i) + "", WRITE_STRING);
//                    }
//                    showToastMessage("完成打字！");
//                    isWriting = false;
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
        }
    });

    boolean isClose = false;

    AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {

        @Override
        protected Void doInBackground(Void... strings) {

            while (true) {
                try {
                    if(isClose){
                        break;
                    }
                    if (!isWriting) {
                        Thread.sleep(1000);
                        continue;
                    }

                    showToastMessage("开始打字！");
                    for (i = 0; i < strMessage.length(); i++) {
                        Thread.sleep(250);
                        //userName.set(userName.get() + strMessage.charAt(i));
                        Messenger.getDefault().send(strMessage.charAt(i) + "", WRITE_STRING);
                    }
                    showToastMessage("完成打字！");
                    isWriting = false;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    };
}
