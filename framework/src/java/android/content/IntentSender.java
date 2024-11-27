package android.content;

/* loaded from: classes.dex */
public class IntentSender implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.content.IntentSender> CREATOR = new android.os.Parcelable.Creator<android.content.IntentSender>() { // from class: android.content.IntentSender.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.IntentSender createFromParcel(android.os.Parcel parcel) {
            android.os.IBinder readStrongBinder = parcel.readStrongBinder();
            if (readStrongBinder != null) {
                return new android.content.IntentSender(readStrongBinder);
            }
            return null;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.IntentSender[] newArray(int i) {
            return new android.content.IntentSender[i];
        }
    };
    private android.app.ActivityManager.PendingIntentInfo mCachedInfo;
    private final android.content.IIntentSender mTarget;
    android.os.IBinder mWhitelistToken;

    public interface OnFinished {
        void onSendFinished(android.content.IntentSender intentSender, android.content.Intent intent, int i, java.lang.String str, android.os.Bundle bundle);
    }

    public static class SendIntentException extends android.util.AndroidException {
        public SendIntentException() {
        }

        public SendIntentException(java.lang.String str) {
            super(str);
        }

        public SendIntentException(java.lang.Exception exc) {
            super(exc);
        }
    }

    private static class FinishedDispatcher extends android.content.IIntentReceiver.Stub implements java.lang.Runnable {
        private final android.os.Handler mHandler;
        private android.content.Intent mIntent;
        private final android.content.IntentSender mIntentSender;
        private int mResultCode;
        private java.lang.String mResultData;
        private android.os.Bundle mResultExtras;
        private final android.content.IntentSender.OnFinished mWho;

        FinishedDispatcher(android.content.IntentSender intentSender, android.content.IntentSender.OnFinished onFinished, android.os.Handler handler) {
            this.mIntentSender = intentSender;
            this.mWho = onFinished;
            this.mHandler = handler;
        }

        @Override // android.content.IIntentReceiver
        public void performReceive(android.content.Intent intent, int i, java.lang.String str, android.os.Bundle bundle, boolean z, boolean z2, int i2) {
            this.mIntent = intent;
            this.mResultCode = i;
            this.mResultData = str;
            this.mResultExtras = bundle;
            if (this.mHandler == null) {
                run();
            } else {
                this.mHandler.post(this);
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            this.mWho.onSendFinished(this.mIntentSender, this.mIntent, this.mResultCode, this.mResultData, this.mResultExtras);
        }
    }

    public void sendIntent(android.content.Context context, int i, android.content.Intent intent, android.content.IntentSender.OnFinished onFinished, android.os.Handler handler) throws android.content.IntentSender.SendIntentException {
        sendIntent(context, i, intent, onFinished, handler, null, null);
    }

    public void sendIntent(android.content.Context context, int i, android.content.Intent intent, android.content.IntentSender.OnFinished onFinished, android.os.Handler handler, java.lang.String str) throws android.content.IntentSender.SendIntentException {
        sendIntent(context, i, intent, onFinished, handler, str, null);
    }

    public void sendIntent(android.content.Context context, int i, android.content.Intent intent, android.content.IntentSender.OnFinished onFinished, android.os.Handler handler, java.lang.String str, android.os.Bundle bundle) throws android.content.IntentSender.SendIntentException {
        java.lang.String resolveTypeIfNeeded;
        android.content.IntentSender.FinishedDispatcher finishedDispatcher;
        if (intent != null) {
            try {
                resolveTypeIfNeeded = intent.resolveTypeIfNeeded(context.getContentResolver());
            } catch (android.os.RemoteException e) {
                throw new android.content.IntentSender.SendIntentException();
            }
        } else {
            resolveTypeIfNeeded = null;
        }
        android.app.ActivityThread.ApplicationThread applicationThread = android.app.ActivityThread.currentActivityThread().getApplicationThread();
        android.app.IActivityManager service = android.app.ActivityManager.getService();
        android.content.IIntentSender iIntentSender = this.mTarget;
        android.os.IBinder iBinder = this.mWhitelistToken;
        if (onFinished != null) {
            finishedDispatcher = new android.content.IntentSender.FinishedDispatcher(this, onFinished, handler);
        } else {
            finishedDispatcher = null;
        }
        if (service.sendIntentSender(applicationThread, iIntentSender, iBinder, i, intent, resolveTypeIfNeeded, finishedDispatcher, str, bundle) < 0) {
            throw new android.content.IntentSender.SendIntentException();
        }
    }

    @java.lang.Deprecated
    public java.lang.String getTargetPackage() {
        return getCreatorPackage();
    }

    public java.lang.String getCreatorPackage() {
        return getCachedInfo().getCreatorPackage();
    }

    public int getCreatorUid() {
        return getCachedInfo().getCreatorUid();
    }

    public android.os.UserHandle getCreatorUserHandle() {
        int creatorUid = getCachedInfo().getCreatorUid();
        if (creatorUid > 0) {
            return new android.os.UserHandle(android.os.UserHandle.getUserId(creatorUid));
        }
        return null;
    }

    public boolean equals(java.lang.Object obj) {
        if (obj instanceof android.content.IntentSender) {
            return this.mTarget.asBinder().equals(((android.content.IntentSender) obj).mTarget.asBinder());
        }
        return false;
    }

    public int hashCode() {
        return this.mTarget.asBinder().hashCode();
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder(128);
        sb.append("IntentSender{");
        sb.append(java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)));
        sb.append(": ");
        sb.append(this.mTarget != null ? this.mTarget.asBinder() : null);
        sb.append('}');
        return sb.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeStrongBinder(this.mTarget.asBinder());
    }

    public static void writeIntentSenderOrNullToParcel(android.content.IntentSender intentSender, android.os.Parcel parcel) {
        parcel.writeStrongBinder(intentSender != null ? intentSender.mTarget.asBinder() : null);
    }

    public static android.content.IntentSender readIntentSenderOrNullFromParcel(android.os.Parcel parcel) {
        android.os.IBinder readStrongBinder = parcel.readStrongBinder();
        if (readStrongBinder != null) {
            return new android.content.IntentSender(readStrongBinder);
        }
        return null;
    }

    public android.content.IIntentSender getTarget() {
        return this.mTarget;
    }

    public android.os.IBinder getWhitelistToken() {
        return this.mWhitelistToken;
    }

    public IntentSender(android.content.IIntentSender iIntentSender) {
        this.mTarget = iIntentSender;
    }

    public IntentSender(android.content.IIntentSender iIntentSender, android.os.IBinder iBinder) {
        this.mTarget = iIntentSender;
        this.mWhitelistToken = iBinder;
    }

    public IntentSender(android.os.IBinder iBinder) {
        this.mTarget = android.content.IIntentSender.Stub.asInterface(iBinder);
    }

    private android.app.ActivityManager.PendingIntentInfo getCachedInfo() {
        if (this.mCachedInfo == null) {
            try {
                this.mCachedInfo = android.app.ActivityManager.getService().getInfoForIntentSender(this.mTarget);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return this.mCachedInfo;
    }

    public boolean isImmutable() {
        return getCachedInfo().isImmutable();
    }
}
