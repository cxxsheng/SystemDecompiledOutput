package com.android.internal.os;

/* loaded from: classes4.dex */
public final class LongMultiStateCounter implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<com.android.internal.os.LongMultiStateCounter> CREATOR = new android.os.Parcelable.Creator<com.android.internal.os.LongMultiStateCounter>() { // from class: com.android.internal.os.LongMultiStateCounter.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.internal.os.LongMultiStateCounter createFromParcel(android.os.Parcel parcel) {
            return new com.android.internal.os.LongMultiStateCounter(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.internal.os.LongMultiStateCounter[] newArray(int i) {
            return new com.android.internal.os.LongMultiStateCounter[i];
        }
    };
    private static libcore.util.NativeAllocationRegistry sRegistry;
    final long mNativeObject;
    private final int mStateCount;

    @dalvik.annotation.optimization.CriticalNative
    private static native void native_addCount(long j, long j2);

    @dalvik.annotation.optimization.CriticalNative
    private static native long native_getCount(long j, int i);

    @dalvik.annotation.optimization.CriticalNative
    private static native long native_getReleaseFunc();

    @dalvik.annotation.optimization.CriticalNative
    private static native int native_getStateCount(long j);

    @dalvik.annotation.optimization.CriticalNative
    private static native void native_incrementValue(long j, long j2, long j3);

    @dalvik.annotation.optimization.CriticalNative
    private static native long native_init(int i);

    @dalvik.annotation.optimization.FastNative
    private static native long native_initFromParcel(android.os.Parcel parcel);

    @dalvik.annotation.optimization.CriticalNative
    private static native void native_reset(long j);

    @dalvik.annotation.optimization.CriticalNative
    private static native void native_setEnabled(long j, boolean z, long j2);

    @dalvik.annotation.optimization.CriticalNative
    private static native void native_setState(long j, int i, long j2);

    @dalvik.annotation.optimization.FastNative
    private static native java.lang.String native_toString(long j);

    @dalvik.annotation.optimization.CriticalNative
    private static native long native_updateValue(long j, long j2, long j3);

    @dalvik.annotation.optimization.FastNative
    private static native void native_writeToParcel(long j, android.os.Parcel parcel, int i);

    public LongMultiStateCounter(int i) {
        com.android.internal.util.Preconditions.checkArgumentPositive(i, "stateCount must be greater than 0");
        this.mStateCount = i;
        this.mNativeObject = native_init(i);
        registerNativeAllocation();
    }

    private LongMultiStateCounter(android.os.Parcel parcel) {
        this.mNativeObject = native_initFromParcel(parcel);
        registerNativeAllocation();
        this.mStateCount = native_getStateCount(this.mNativeObject);
    }

    private void registerNativeAllocation() {
        if (sRegistry == null) {
            synchronized (com.android.internal.os.LongMultiStateCounter.class) {
                if (sRegistry == null) {
                    sRegistry = libcore.util.NativeAllocationRegistry.createMalloced(com.android.internal.os.LongMultiStateCounter.class.getClassLoader(), native_getReleaseFunc());
                }
            }
        }
        sRegistry.registerNativeAllocation(this, this.mNativeObject);
    }

    private void registerNativeAllocation$ravenwood() {
    }

    public int getStateCount() {
        return this.mStateCount;
    }

    public void setEnabled(boolean z, long j) {
        native_setEnabled(this.mNativeObject, z, j);
    }

    public void setState(int i, long j) {
        if (i < 0 || i >= this.mStateCount) {
            throw new java.lang.IllegalArgumentException("State: " + i + ", outside the range: [0-" + (this.mStateCount - 1) + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END);
        }
        native_setState(this.mNativeObject, i, j);
    }

    public long updateValue(long j, long j2) {
        return native_updateValue(this.mNativeObject, j, j2);
    }

    public void incrementValue(long j, long j2) {
        native_incrementValue(this.mNativeObject, j, j2);
    }

    public void addCount(long j) {
        native_addCount(this.mNativeObject, j);
    }

    public void reset() {
        native_reset(this.mNativeObject);
    }

    public long getCount(int i) {
        if (i < 0 || i >= this.mStateCount) {
            throw new java.lang.IllegalArgumentException("State: " + i + ", outside the range: [0-" + this.mStateCount + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END);
        }
        return native_getCount(this.mNativeObject, i);
    }

    public long getTotalCount() {
        long j = 0;
        for (int i = 0; i < this.mStateCount; i++) {
            j += native_getCount(this.mNativeObject, i);
        }
        return j;
    }

    public java.lang.String toString() {
        return native_toString(this.mNativeObject);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        native_writeToParcel(this.mNativeObject, parcel, i);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
