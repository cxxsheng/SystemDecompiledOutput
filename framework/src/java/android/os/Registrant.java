package android.os;

/* loaded from: classes3.dex */
public class Registrant {
    java.lang.ref.WeakReference refH;
    java.lang.Object userObj;
    int what;

    public Registrant(android.os.Handler handler, int i, java.lang.Object obj) {
        this.refH = new java.lang.ref.WeakReference(handler);
        this.what = i;
        this.userObj = obj;
    }

    public void clear() {
        this.refH = null;
        this.userObj = null;
    }

    public void notifyRegistrant() {
        internalNotifyRegistrant(null, null);
    }

    public void notifyResult(java.lang.Object obj) {
        internalNotifyRegistrant(obj, null);
    }

    public void notifyException(java.lang.Throwable th) {
        internalNotifyRegistrant(null, th);
    }

    public void notifyRegistrant(android.os.AsyncResult asyncResult) {
        internalNotifyRegistrant(asyncResult.result, asyncResult.exception);
    }

    void internalNotifyRegistrant(java.lang.Object obj, java.lang.Throwable th) {
        android.os.Handler handler = getHandler();
        if (handler == null) {
            clear();
            return;
        }
        android.os.Message obtain = android.os.Message.obtain();
        obtain.what = this.what;
        obtain.obj = new android.os.AsyncResult(this.userObj, obj, th);
        handler.sendMessage(obtain);
    }

    public android.os.Message messageForRegistrant() {
        android.os.Handler handler = getHandler();
        if (handler == null) {
            clear();
            return null;
        }
        android.os.Message obtainMessage = handler.obtainMessage();
        obtainMessage.what = this.what;
        obtainMessage.obj = this.userObj;
        return obtainMessage;
    }

    public android.os.Handler getHandler() {
        if (this.refH == null) {
            return null;
        }
        return (android.os.Handler) this.refH.get();
    }
}
