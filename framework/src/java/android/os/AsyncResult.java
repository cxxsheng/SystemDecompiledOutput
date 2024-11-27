package android.os;

/* loaded from: classes3.dex */
public class AsyncResult {
    public java.lang.Throwable exception;
    public java.lang.Object result;
    public java.lang.Object userObj;

    public static android.os.AsyncResult forMessage(android.os.Message message, java.lang.Object obj, java.lang.Throwable th) {
        android.os.AsyncResult asyncResult = new android.os.AsyncResult(message.obj, obj, th);
        message.obj = asyncResult;
        return asyncResult;
    }

    public static android.os.AsyncResult forMessage(android.os.Message message) {
        android.os.AsyncResult asyncResult = new android.os.AsyncResult(message.obj, null, null);
        message.obj = asyncResult;
        return asyncResult;
    }

    public AsyncResult(java.lang.Object obj, java.lang.Object obj2, java.lang.Throwable th) {
        this.userObj = obj;
        this.result = obj2;
        this.exception = th;
    }
}
