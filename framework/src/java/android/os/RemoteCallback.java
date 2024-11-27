package android.os;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class RemoteCallback implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.os.RemoteCallback> CREATOR = new android.os.Parcelable.Creator<android.os.RemoteCallback>() { // from class: android.os.RemoteCallback.3
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.os.RemoteCallback createFromParcel(android.os.Parcel parcel) {
            return new android.os.RemoteCallback(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.os.RemoteCallback[] newArray(int i) {
            return new android.os.RemoteCallback[i];
        }
    };
    private final android.os.IRemoteCallback mCallback;
    private final android.os.Handler mHandler;
    private final android.os.RemoteCallback.OnResultListener mListener;

    public interface OnResultListener {
        void onResult(android.os.Bundle bundle);
    }

    public RemoteCallback(android.os.RemoteCallback.OnResultListener onResultListener) {
        this(onResultListener, null);
    }

    public RemoteCallback(android.os.RemoteCallback.OnResultListener onResultListener, android.os.Handler handler) {
        if (onResultListener == null) {
            throw new java.lang.NullPointerException("listener cannot be null");
        }
        this.mListener = onResultListener;
        this.mHandler = handler;
        this.mCallback = new android.os.IRemoteCallback.Stub() { // from class: android.os.RemoteCallback.1
            @Override // android.os.IRemoteCallback
            public void sendResult(android.os.Bundle bundle) {
                android.os.RemoteCallback.this.sendResult(bundle);
            }
        };
    }

    RemoteCallback(android.os.Parcel parcel) {
        this.mListener = null;
        this.mHandler = null;
        this.mCallback = android.os.IRemoteCallback.Stub.asInterface(parcel.readStrongBinder());
    }

    public void sendResult(final android.os.Bundle bundle) {
        if (this.mListener != null) {
            if (this.mHandler != null) {
                this.mHandler.post(new java.lang.Runnable() { // from class: android.os.RemoteCallback.2
                    @Override // java.lang.Runnable
                    public void run() {
                        android.os.RemoteCallback.this.mListener.onResult(bundle);
                    }
                });
                return;
            } else {
                this.mListener.onResult(bundle);
                return;
            }
        }
        try {
            this.mCallback.sendResult(bundle);
        } catch (android.os.RemoteException e) {
        }
    }

    public android.os.IRemoteCallback getInterface() {
        return this.mCallback;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeStrongBinder(this.mCallback.asBinder());
    }
}
