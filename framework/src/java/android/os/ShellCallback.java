package android.os;

/* loaded from: classes3.dex */
public class ShellCallback implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.os.ShellCallback> CREATOR = new android.os.Parcelable.Creator<android.os.ShellCallback>() { // from class: android.os.ShellCallback.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.os.ShellCallback createFromParcel(android.os.Parcel parcel) {
            return new android.os.ShellCallback(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.os.ShellCallback[] newArray(int i) {
            return new android.os.ShellCallback[i];
        }
    };
    static final boolean DEBUG = false;
    static final java.lang.String TAG = "ShellCallback";
    final boolean mLocal = true;
    com.android.internal.os.IShellCallback mShellCallback;

    class MyShellCallback extends com.android.internal.os.IShellCallback.Stub {
        MyShellCallback() {
        }

        @Override // com.android.internal.os.IShellCallback
        public android.os.ParcelFileDescriptor openFile(java.lang.String str, java.lang.String str2, java.lang.String str3) {
            return android.os.ShellCallback.this.onOpenFile(str, str2, str3);
        }
    }

    public ShellCallback() {
    }

    public android.os.ParcelFileDescriptor openFile(java.lang.String str, java.lang.String str2, java.lang.String str3) {
        if (this.mLocal) {
            return onOpenFile(str, str2, str3);
        }
        if (this.mShellCallback != null) {
            try {
                return this.mShellCallback.openFile(str, str2, str3);
            } catch (android.os.RemoteException e) {
                android.util.Log.w(TAG, "Failure opening " + str, e);
                return null;
            }
        }
        return null;
    }

    public android.os.ParcelFileDescriptor onOpenFile(java.lang.String str, java.lang.String str2, java.lang.String str3) {
        return null;
    }

    public static void writeToParcel(android.os.ShellCallback shellCallback, android.os.Parcel parcel) {
        if (shellCallback == null) {
            parcel.writeStrongBinder(null);
        } else {
            shellCallback.writeToParcel(parcel, 0);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        synchronized (this) {
            if (this.mShellCallback == null) {
                this.mShellCallback = new android.os.ShellCallback.MyShellCallback();
            }
            parcel.writeStrongBinder(this.mShellCallback.asBinder());
        }
    }

    public android.os.IBinder getShellCallbackBinder() {
        return this.mShellCallback.asBinder();
    }

    ShellCallback(android.os.Parcel parcel) {
        this.mShellCallback = com.android.internal.os.IShellCallback.Stub.asInterface(parcel.readStrongBinder());
        if (this.mShellCallback != null) {
            android.os.Binder.allowBlocking(this.mShellCallback.asBinder());
        }
    }
}
