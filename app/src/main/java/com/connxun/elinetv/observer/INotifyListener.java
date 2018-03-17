package com.connxun.elinetv.observer;

/**
 * 观察者使用,，用于更新数据
 * Created by zr on 2016/10/14.
 */

public interface INotifyListener {
    void notifyUpdate(NotifyObject obj);
    void registerListener();
    void unRegisterListener();
}
