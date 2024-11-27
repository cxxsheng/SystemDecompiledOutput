package android.view.inputmethod;

/* loaded from: classes4.dex */
public final class InputBinding implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.view.inputmethod.InputBinding> CREATOR = new android.os.Parcelable.Creator<android.view.inputmethod.InputBinding>() { // from class: android.view.inputmethod.InputBinding.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.inputmethod.InputBinding createFromParcel(android.os.Parcel parcel) {
            return new android.view.inputmethod.InputBinding(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.inputmethod.InputBinding[] newArray(int i) {
            return new android.view.inputmethod.InputBinding[i];
        }
    };
    static final java.lang.String TAG = "InputBinding";
    final android.view.inputmethod.InputConnection mConnection;
    final android.os.IBinder mConnectionToken;
    final int mPid;
    final int mUid;

    public InputBinding(android.view.inputmethod.InputConnection inputConnection, android.os.IBinder iBinder, int i, int i2) {
        this.mConnection = inputConnection;
        this.mConnectionToken = iBinder;
        this.mUid = i;
        this.mPid = i2;
    }

    public InputBinding(android.view.inputmethod.InputConnection inputConnection, android.view.inputmethod.InputBinding inputBinding) {
        this.mConnection = inputConnection;
        this.mConnectionToken = inputBinding.getConnectionToken();
        this.mUid = inputBinding.getUid();
        this.mPid = inputBinding.getPid();
    }

    InputBinding(android.os.Parcel parcel) {
        this.mConnection = null;
        this.mConnectionToken = parcel.readStrongBinder();
        this.mUid = parcel.readInt();
        this.mPid = parcel.readInt();
    }

    public android.view.inputmethod.InputConnection getConnection() {
        return this.mConnection;
    }

    public android.os.IBinder getConnectionToken() {
        return this.mConnectionToken;
    }

    public int getUid() {
        return this.mUid;
    }

    public int getPid() {
        return this.mPid;
    }

    public java.lang.String toString() {
        return "InputBinding{" + this.mConnectionToken + " / uid " + this.mUid + " / pid " + this.mPid + "}";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeStrongBinder(this.mConnectionToken);
        parcel.writeInt(this.mUid);
        parcel.writeInt(this.mPid);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
