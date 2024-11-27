package com.android.internal.util;

/* loaded from: classes5.dex */
public class WakeupMessage implements android.app.AlarmManager.OnAlarmListener {
    private final android.app.AlarmManager mAlarmManager;
    protected final int mArg1;
    protected final int mArg2;
    protected final int mCmd;
    protected final java.lang.String mCmdName;
    protected final android.os.Handler mHandler;
    protected final java.lang.Object mObj;
    private final java.lang.Runnable mRunnable;
    private boolean mScheduled;

    public WakeupMessage(android.content.Context context, android.os.Handler handler, java.lang.String str, int i, int i2, int i3, java.lang.Object obj) {
        this.mAlarmManager = getAlarmManager(context);
        this.mHandler = handler;
        this.mCmdName = str;
        this.mCmd = i;
        this.mArg1 = i2;
        this.mArg2 = i3;
        this.mObj = obj;
        this.mRunnable = null;
    }

    public WakeupMessage(android.content.Context context, android.os.Handler handler, java.lang.String str, int i, int i2) {
        this(context, handler, str, i, i2, 0, null);
    }

    public WakeupMessage(android.content.Context context, android.os.Handler handler, java.lang.String str, int i, int i2, int i3) {
        this(context, handler, str, i, i2, i3, null);
    }

    public WakeupMessage(android.content.Context context, android.os.Handler handler, java.lang.String str, int i) {
        this(context, handler, str, i, 0, 0, null);
    }

    public WakeupMessage(android.content.Context context, android.os.Handler handler, java.lang.String str, java.lang.Runnable runnable) {
        this.mAlarmManager = getAlarmManager(context);
        this.mHandler = handler;
        this.mCmdName = str;
        this.mCmd = 0;
        this.mArg1 = 0;
        this.mArg2 = 0;
        this.mObj = null;
        this.mRunnable = runnable;
    }

    private static android.app.AlarmManager getAlarmManager(android.content.Context context) {
        return (android.app.AlarmManager) context.getSystemService("alarm");
    }

    public synchronized void schedule(long j) {
        this.mAlarmManager.setExact(2, j, this.mCmdName, this, this.mHandler);
        this.mScheduled = true;
    }

    public synchronized void cancel() {
        if (this.mScheduled) {
            this.mAlarmManager.cancel(this);
            this.mScheduled = false;
        }
    }

    @Override // android.app.AlarmManager.OnAlarmListener
    public void onAlarm() {
        boolean z;
        android.os.Message obtain;
        synchronized (this) {
            z = this.mScheduled;
            this.mScheduled = false;
        }
        if (z) {
            if (this.mRunnable == null) {
                obtain = this.mHandler.obtainMessage(this.mCmd, this.mArg1, this.mArg2, this.mObj);
            } else {
                obtain = android.os.Message.obtain(this.mHandler, this.mRunnable);
            }
            this.mHandler.dispatchMessage(obtain);
            obtain.recycle();
        }
    }
}
