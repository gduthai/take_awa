package com.why.take_away.common;
/*
* */
public class BaseContext {
    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();
    public static void setCurrentId(long id) {
        threadLocal.set(id);
    }
    public static long getCurrentId() {
        return threadLocal.get();
    }


}
