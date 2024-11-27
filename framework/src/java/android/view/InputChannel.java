package android.view;

/* loaded from: classes4.dex */
public final class InputChannel implements android.os.Parcelable {
    private static final boolean DEBUG = false;
    private static final java.lang.String TAG = "InputChannel";
    private long mPtr;
    private static final libcore.util.NativeAllocationRegistry sRegistry = libcore.util.NativeAllocationRegistry.createMalloced(android.view.InputChannel.class.getClassLoader(), nativeGetFinalizer());
    public static final android.os.Parcelable.Creator<android.view.InputChannel> CREATOR = new android.os.Parcelable.Creator<android.view.InputChannel>() { // from class: android.view.InputChannel.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.InputChannel createFromParcel(android.os.Parcel parcel) {
            android.view.InputChannel inputChannel = new android.view.InputChannel();
            inputChannel.readFromParcel(parcel);
            return inputChannel;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.InputChannel[] newArray(int i) {
            return new android.view.InputChannel[i];
        }
    };

    private native void nativeDispose(long j);

    private native long nativeDup(long j);

    private static native long nativeGetFinalizer();

    private native java.lang.String nativeGetName(long j);

    private native android.os.IBinder nativeGetToken(long j);

    private static native long[] nativeOpenInputChannelPair(java.lang.String str);

    private native long nativeReadFromParcel(android.os.Parcel parcel);

    private native void nativeWriteToParcel(android.os.Parcel parcel, long j);

    private void setNativeInputChannel(long j) {
        if (j == 0) {
            throw new java.lang.IllegalArgumentException("Attempting to set native input channel to null.");
        }
        if (this.mPtr != 0) {
            throw new java.lang.IllegalArgumentException("Already has native input channel.");
        }
        sRegistry.registerNativeAllocation(this, j);
        this.mPtr = j;
    }

    public static android.view.InputChannel[] openInputChannelPair(java.lang.String str) {
        if (str == null) {
            throw new java.lang.IllegalArgumentException("name must not be null");
        }
        android.view.InputChannel[] inputChannelArr = new android.view.InputChannel[2];
        long[] nativeOpenInputChannelPair = nativeOpenInputChannelPair(str);
        for (int i = 0; i < 2; i++) {
            inputChannelArr[i] = new android.view.InputChannel();
            inputChannelArr[i].setNativeInputChannel(nativeOpenInputChannelPair[i]);
        }
        return inputChannelArr;
    }

    public java.lang.String getName() {
        java.lang.String nativeGetName = nativeGetName(this.mPtr);
        return nativeGetName != null ? nativeGetName : "uninitialized";
    }

    public void dispose() {
        nativeDispose(this.mPtr);
    }

    public void release() {
    }

    public void copyTo(android.view.InputChannel inputChannel) {
        if (inputChannel == null) {
            throw new java.lang.IllegalArgumentException("outParameter must not be null");
        }
        if (inputChannel.mPtr != 0) {
            throw new java.lang.IllegalArgumentException("Other object already has a native input channel.");
        }
        inputChannel.setNativeInputChannel(nativeDup(this.mPtr));
    }

    public android.view.InputChannel dup() {
        android.view.InputChannel inputChannel = new android.view.InputChannel();
        inputChannel.setNativeInputChannel(nativeDup(this.mPtr));
        return inputChannel;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 1;
    }

    public void readFromParcel(android.os.Parcel parcel) {
        if (parcel == null) {
            throw new java.lang.IllegalArgumentException("in must not be null");
        }
        long nativeReadFromParcel = nativeReadFromParcel(parcel);
        if (nativeReadFromParcel != 0) {
            setNativeInputChannel(nativeReadFromParcel);
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        if (parcel == null) {
            throw new java.lang.IllegalArgumentException("out must not be null");
        }
        nativeWriteToParcel(parcel, this.mPtr);
        if ((i & 1) != 0) {
            dispose();
        }
    }

    public java.lang.String toString() {
        return getName();
    }

    public android.os.IBinder getToken() {
        return nativeGetToken(this.mPtr);
    }
}
