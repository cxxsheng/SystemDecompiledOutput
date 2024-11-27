package com.android.internal.os;

@java.lang.Deprecated
/* loaded from: classes4.dex */
public class HandlerCaller {
    final com.android.internal.os.HandlerCaller.Callback mCallback;
    final android.os.Handler mH;
    final android.os.Looper mMainLooper;

    public interface Callback {
        void executeMessage(android.os.Message message);
    }

    class MyHandler extends android.os.Handler {
        MyHandler(android.os.Looper looper, boolean z) {
            super(looper, null, z);
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            com.android.internal.os.HandlerCaller.this.mCallback.executeMessage(message);
        }
    }

    public HandlerCaller(android.content.Context context, android.os.Looper looper, com.android.internal.os.HandlerCaller.Callback callback, boolean z) {
        this.mMainLooper = looper == null ? context.getMainLooper() : looper;
        this.mH = new com.android.internal.os.HandlerCaller.MyHandler(this.mMainLooper, z);
        this.mCallback = callback;
    }

    public android.os.Handler getHandler() {
        return this.mH;
    }

    public void executeOrSendMessage(android.os.Message message) {
        if (android.os.Looper.myLooper() == this.mMainLooper) {
            this.mCallback.executeMessage(message);
            message.recycle();
        } else {
            this.mH.sendMessage(message);
        }
    }

    public void sendMessageDelayed(android.os.Message message, long j) {
        this.mH.sendMessageDelayed(message, j);
    }

    public boolean hasMessages(int i) {
        return this.mH.hasMessages(i);
    }

    public void removeMessages(int i) {
        this.mH.removeMessages(i);
    }

    public void removeMessages(int i, java.lang.Object obj) {
        this.mH.removeMessages(i, obj);
    }

    public void sendMessage(android.os.Message message) {
        this.mH.sendMessage(message);
    }

    public com.android.internal.os.SomeArgs sendMessageAndWait(android.os.Message message) {
        if (android.os.Looper.myLooper() == this.mH.getLooper()) {
            throw new java.lang.IllegalStateException("Can't wait on same thread as looper");
        }
        com.android.internal.os.SomeArgs someArgs = (com.android.internal.os.SomeArgs) message.obj;
        someArgs.mWaitState = 1;
        this.mH.sendMessage(message);
        synchronized (someArgs) {
            while (someArgs.mWaitState == 1) {
                try {
                    someArgs.wait();
                } catch (java.lang.InterruptedException e) {
                    return null;
                }
            }
        }
        someArgs.mWaitState = 0;
        return someArgs;
    }

    public android.os.Message obtainMessage(int i) {
        return this.mH.obtainMessage(i);
    }

    public android.os.Message obtainMessageBO(int i, boolean z, java.lang.Object obj) {
        return this.mH.obtainMessage(i, z ? 1 : 0, 0, obj);
    }

    public android.os.Message obtainMessageBOO(int i, boolean z, java.lang.Object obj, java.lang.Object obj2) {
        com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
        obtain.arg1 = obj;
        obtain.arg2 = obj2;
        return this.mH.obtainMessage(i, z ? 1 : 0, 0, obtain);
    }

    public android.os.Message obtainMessageO(int i, java.lang.Object obj) {
        return this.mH.obtainMessage(i, 0, 0, obj);
    }

    public android.os.Message obtainMessageI(int i, int i2) {
        return this.mH.obtainMessage(i, i2, 0);
    }

    public android.os.Message obtainMessageII(int i, int i2, int i3) {
        return this.mH.obtainMessage(i, i2, i3);
    }

    public android.os.Message obtainMessageIO(int i, int i2, java.lang.Object obj) {
        return this.mH.obtainMessage(i, i2, 0, obj);
    }

    public android.os.Message obtainMessageIIO(int i, int i2, int i3, java.lang.Object obj) {
        return this.mH.obtainMessage(i, i2, i3, obj);
    }

    public android.os.Message obtainMessageIIOO(int i, int i2, int i3, java.lang.Object obj, java.lang.Object obj2) {
        com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
        obtain.arg1 = obj;
        obtain.arg2 = obj2;
        return this.mH.obtainMessage(i, i2, i3, obtain);
    }

    public android.os.Message obtainMessageIOO(int i, int i2, java.lang.Object obj, java.lang.Object obj2) {
        com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
        obtain.arg1 = obj;
        obtain.arg2 = obj2;
        return this.mH.obtainMessage(i, i2, 0, obtain);
    }

    public android.os.Message obtainMessageIOOO(int i, int i2, java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3) {
        com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
        obtain.arg1 = obj;
        obtain.arg2 = obj2;
        obtain.arg3 = obj3;
        return this.mH.obtainMessage(i, i2, 0, obtain);
    }

    public android.os.Message obtainMessageIIOOO(int i, int i2, int i3, java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3) {
        com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
        obtain.arg1 = obj;
        obtain.arg2 = obj2;
        obtain.arg3 = obj3;
        return this.mH.obtainMessage(i, i2, i3, obtain);
    }

    public android.os.Message obtainMessageIIOOOO(int i, int i2, int i3, java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3, java.lang.Object obj4) {
        com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
        obtain.arg1 = obj;
        obtain.arg2 = obj2;
        obtain.arg3 = obj3;
        obtain.arg4 = obj4;
        return this.mH.obtainMessage(i, i2, i3, obtain);
    }

    public android.os.Message obtainMessageOO(int i, java.lang.Object obj, java.lang.Object obj2) {
        com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
        obtain.arg1 = obj;
        obtain.arg2 = obj2;
        return this.mH.obtainMessage(i, 0, 0, obtain);
    }

    public android.os.Message obtainMessageOOO(int i, java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3) {
        com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
        obtain.arg1 = obj;
        obtain.arg2 = obj2;
        obtain.arg3 = obj3;
        return this.mH.obtainMessage(i, 0, 0, obtain);
    }

    public android.os.Message obtainMessageOOOO(int i, java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3, java.lang.Object obj4) {
        com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
        obtain.arg1 = obj;
        obtain.arg2 = obj2;
        obtain.arg3 = obj3;
        obtain.arg4 = obj4;
        return this.mH.obtainMessage(i, 0, 0, obtain);
    }

    public android.os.Message obtainMessageOOOOO(int i, java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3, java.lang.Object obj4, java.lang.Object obj5) {
        com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
        obtain.arg1 = obj;
        obtain.arg2 = obj2;
        obtain.arg3 = obj3;
        obtain.arg4 = obj4;
        obtain.arg5 = obj5;
        return this.mH.obtainMessage(i, 0, 0, obtain);
    }

    public android.os.Message obtainMessageOOOOII(int i, java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3, java.lang.Object obj4, int i2, int i3) {
        com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
        obtain.arg1 = obj;
        obtain.arg2 = obj2;
        obtain.arg3 = obj3;
        obtain.arg4 = obj4;
        obtain.argi5 = i2;
        obtain.argi6 = i3;
        return this.mH.obtainMessage(i, 0, 0, obtain);
    }

    public android.os.Message obtainMessageIIII(int i, int i2, int i3, int i4, int i5) {
        com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
        obtain.argi1 = i2;
        obtain.argi2 = i3;
        obtain.argi3 = i4;
        obtain.argi4 = i5;
        return this.mH.obtainMessage(i, 0, 0, obtain);
    }

    public android.os.Message obtainMessageIIIIII(int i, int i2, int i3, int i4, int i5, int i6, int i7) {
        com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
        obtain.argi1 = i2;
        obtain.argi2 = i3;
        obtain.argi3 = i4;
        obtain.argi4 = i5;
        obtain.argi5 = i6;
        obtain.argi6 = i7;
        return this.mH.obtainMessage(i, 0, 0, obtain);
    }

    public android.os.Message obtainMessageIIIIO(int i, int i2, int i3, int i4, int i5, java.lang.Object obj) {
        com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
        obtain.arg1 = obj;
        obtain.argi1 = i2;
        obtain.argi2 = i3;
        obtain.argi3 = i4;
        obtain.argi4 = i5;
        return this.mH.obtainMessage(i, 0, 0, obtain);
    }
}
