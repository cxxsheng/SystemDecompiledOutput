package android.window;

/* loaded from: classes4.dex */
public class RemoteTransition implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.window.RemoteTransition> CREATOR = new android.os.Parcelable.Creator<android.window.RemoteTransition>() { // from class: android.window.RemoteTransition.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.window.RemoteTransition[] newArray(int i) {
            return new android.window.RemoteTransition[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.window.RemoteTransition createFromParcel(android.os.Parcel parcel) {
            return new android.window.RemoteTransition(parcel);
        }
    };
    private android.app.IApplicationThread mAppThread;
    private java.lang.String mDebugName;
    private android.window.IRemoteTransition mRemoteTransition;

    public RemoteTransition(android.window.IRemoteTransition iRemoteTransition) {
        this(iRemoteTransition, null, null);
    }

    public RemoteTransition(android.window.IRemoteTransition iRemoteTransition, java.lang.String str) {
        this(iRemoteTransition, null, str);
    }

    public android.os.IBinder asBinder() {
        return this.mRemoteTransition.asBinder();
    }

    public RemoteTransition(android.window.IRemoteTransition iRemoteTransition, android.app.IApplicationThread iApplicationThread, java.lang.String str) {
        this.mRemoteTransition = iRemoteTransition;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mRemoteTransition);
        this.mAppThread = iApplicationThread;
        this.mDebugName = str;
    }

    public android.window.IRemoteTransition getRemoteTransition() {
        return this.mRemoteTransition;
    }

    public android.app.IApplicationThread getAppThread() {
        return this.mAppThread;
    }

    public java.lang.String getDebugName() {
        return this.mDebugName;
    }

    public android.window.RemoteTransition setRemoteTransition(android.window.IRemoteTransition iRemoteTransition) {
        this.mRemoteTransition = iRemoteTransition;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mRemoteTransition);
        return this;
    }

    public android.window.RemoteTransition setAppThread(android.app.IApplicationThread iApplicationThread) {
        this.mAppThread = iApplicationThread;
        return this;
    }

    public android.window.RemoteTransition setDebugName(java.lang.String str) {
        this.mDebugName = str;
        return this;
    }

    public java.lang.String toString() {
        return "RemoteTransition { remoteTransition = " + this.mRemoteTransition + ", appThread = " + this.mAppThread + ", debugName = " + this.mDebugName + " }";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        byte b = this.mAppThread != null ? (byte) 2 : (byte) 0;
        if (this.mDebugName != null) {
            b = (byte) (b | 4);
        }
        parcel.writeByte(b);
        parcel.writeStrongInterface(this.mRemoteTransition);
        if (this.mAppThread != null) {
            parcel.writeStrongInterface(this.mAppThread);
        }
        if (this.mDebugName != null) {
            parcel.writeString(this.mDebugName);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    protected RemoteTransition(android.os.Parcel parcel) {
        byte readByte = parcel.readByte();
        android.window.IRemoteTransition asInterface = android.window.IRemoteTransition.Stub.asInterface(parcel.readStrongBinder());
        android.app.IApplicationThread asInterface2 = (readByte & 2) == 0 ? null : android.app.IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
        java.lang.String readString = (readByte & 4) == 0 ? null : parcel.readString();
        this.mRemoteTransition = asInterface;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mRemoteTransition);
        this.mAppThread = asInterface2;
        this.mDebugName = readString;
    }

    @java.lang.Deprecated
    private void __metadata() {
    }
}
