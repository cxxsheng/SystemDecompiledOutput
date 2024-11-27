package android.os;

/* loaded from: classes3.dex */
public class ResultReceiver implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.os.ResultReceiver> CREATOR = new android.os.Parcelable.Creator<android.os.ResultReceiver>() { // from class: android.os.ResultReceiver.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.os.ResultReceiver createFromParcel(android.os.Parcel parcel) {
            return new android.os.ResultReceiver(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.os.ResultReceiver[] newArray(int i) {
            return new android.os.ResultReceiver[i];
        }
    };
    final android.os.Handler mHandler;
    final boolean mLocal;
    com.android.internal.os.IResultReceiver mReceiver;

    class MyRunnable implements java.lang.Runnable {
        final int mResultCode;
        final android.os.Bundle mResultData;

        MyRunnable(int i, android.os.Bundle bundle) {
            this.mResultCode = i;
            this.mResultData = bundle;
        }

        @Override // java.lang.Runnable
        public void run() {
            android.os.ResultReceiver.this.onReceiveResult(this.mResultCode, this.mResultData);
        }
    }

    class MyResultReceiver extends com.android.internal.os.IResultReceiver.Stub {
        MyResultReceiver() {
        }

        @Override // com.android.internal.os.IResultReceiver
        public void send(int i, android.os.Bundle bundle) {
            if (android.os.ResultReceiver.this.mHandler != null) {
                android.os.ResultReceiver.this.mHandler.post(android.os.ResultReceiver.this.new MyRunnable(i, bundle));
            } else {
                android.os.ResultReceiver.this.onReceiveResult(i, bundle);
            }
        }
    }

    public ResultReceiver(android.os.Handler handler) {
        this.mLocal = true;
        this.mHandler = handler;
    }

    public void send(int i, android.os.Bundle bundle) {
        if (this.mLocal) {
            if (this.mHandler != null) {
                this.mHandler.post(new android.os.ResultReceiver.MyRunnable(i, bundle));
                return;
            } else {
                onReceiveResult(i, bundle);
                return;
            }
        }
        if (this.mReceiver != null) {
            try {
                this.mReceiver.send(i, bundle);
            } catch (android.os.RemoteException e) {
            }
        }
    }

    protected void onReceiveResult(int i, android.os.Bundle bundle) {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        synchronized (this) {
            if (this.mReceiver == null) {
                this.mReceiver = new android.os.ResultReceiver.MyResultReceiver();
            }
            parcel.writeStrongBinder(this.mReceiver.asBinder());
        }
    }

    ResultReceiver(android.os.Parcel parcel) {
        this.mLocal = false;
        this.mHandler = null;
        this.mReceiver = com.android.internal.os.IResultReceiver.Stub.asInterface(parcel.readStrongBinder());
    }
}
