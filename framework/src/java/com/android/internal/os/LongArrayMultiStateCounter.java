package com.android.internal.os;

/* loaded from: classes4.dex */
public final class LongArrayMultiStateCounter implements android.os.Parcelable {
    private static volatile libcore.util.NativeAllocationRegistry sRegistry;
    private final int mLength;
    final long mNativeObject;
    private final int mStateCount;
    private static final java.util.concurrent.atomic.AtomicReference<com.android.internal.os.LongArrayMultiStateCounter.LongArrayContainer> sTmpArrayContainer = new java.util.concurrent.atomic.AtomicReference<>();
    public static final android.os.Parcelable.Creator<com.android.internal.os.LongArrayMultiStateCounter> CREATOR = new android.os.Parcelable.Creator<com.android.internal.os.LongArrayMultiStateCounter>() { // from class: com.android.internal.os.LongArrayMultiStateCounter.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.internal.os.LongArrayMultiStateCounter createFromParcel(android.os.Parcel parcel) {
            return new com.android.internal.os.LongArrayMultiStateCounter(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.internal.os.LongArrayMultiStateCounter[] newArray(int i) {
            return new com.android.internal.os.LongArrayMultiStateCounter[i];
        }
    };

    @dalvik.annotation.optimization.CriticalNative
    private static native void native_addCounts(long j, long j2);

    @dalvik.annotation.optimization.CriticalNative
    private static native int native_getArrayLength(long j);

    @dalvik.annotation.optimization.CriticalNative
    private static native void native_getCounts(long j, long j2, int i);

    @dalvik.annotation.optimization.CriticalNative
    private static native long native_getReleaseFunc();

    @dalvik.annotation.optimization.CriticalNative
    private static native int native_getStateCount(long j);

    @dalvik.annotation.optimization.CriticalNative
    private static native void native_incrementValues(long j, long j2, long j3);

    @dalvik.annotation.optimization.CriticalNative
    private static native long native_init(int i, int i2);

    @dalvik.annotation.optimization.FastNative
    private static native long native_initFromParcel(android.os.Parcel parcel);

    @dalvik.annotation.optimization.CriticalNative
    private static native void native_reset(long j);

    @dalvik.annotation.optimization.CriticalNative
    private static native void native_setEnabled(long j, boolean z, long j2);

    @dalvik.annotation.optimization.CriticalNative
    private static native void native_setState(long j, int i, long j2);

    @dalvik.annotation.optimization.CriticalNative
    private static native void native_setValues(long j, int i, long j2);

    @dalvik.annotation.optimization.FastNative
    private static native java.lang.String native_toString(long j);

    @dalvik.annotation.optimization.CriticalNative
    private static native void native_updateValues(long j, long j2, long j3);

    @dalvik.annotation.optimization.FastNative
    private static native void native_writeToParcel(long j, android.os.Parcel parcel, int i);

    public static class LongArrayContainer {
        private static libcore.util.NativeAllocationRegistry sRegistry;
        private final int mLength;
        final long mNativeObject;

        @dalvik.annotation.optimization.FastNative
        private static native boolean native_combineValues(long j, long[] jArr, int[] iArr);

        @dalvik.annotation.optimization.CriticalNative
        private static native long native_getReleaseFunc();

        @dalvik.annotation.optimization.FastNative
        private static native void native_getValues(long j, long[] jArr);

        @dalvik.annotation.optimization.CriticalNative
        private static native long native_init(int i);

        @dalvik.annotation.optimization.FastNative
        private static native void native_setValues(long j, long[] jArr);

        public LongArrayContainer(int i) {
            this.mLength = i;
            this.mNativeObject = native_init(i);
            registerNativeAllocation();
        }

        private void registerNativeAllocation() {
            if (sRegistry == null) {
                synchronized (com.android.internal.os.LongArrayMultiStateCounter.class) {
                    if (sRegistry == null) {
                        sRegistry = libcore.util.NativeAllocationRegistry.createMalloced(com.android.internal.os.LongArrayMultiStateCounter.LongArrayContainer.class.getClassLoader(), native_getReleaseFunc());
                    }
                }
            }
            sRegistry.registerNativeAllocation(this, this.mNativeObject);
        }

        private void registerNativeAllocation$ravenwood() {
        }

        public void setValues(long[] jArr) {
            if (jArr.length != this.mLength) {
                throw new java.lang.IllegalArgumentException("Invalid array length: " + this.mLength + ", expected: " + this.mLength);
            }
            native_setValues(this.mNativeObject, jArr);
        }

        public void getValues(long[] jArr) {
            if (jArr.length != this.mLength) {
                throw new java.lang.IllegalArgumentException("Invalid array length: " + this.mLength + ", expected: " + this.mLength);
            }
            native_getValues(this.mNativeObject, jArr);
        }

        public boolean combineValues(long[] jArr, int[] iArr) {
            if (iArr.length != this.mLength) {
                throw new java.lang.IllegalArgumentException("Wrong index map size " + iArr.length + ", expected " + this.mLength);
            }
            return native_combineValues(this.mNativeObject, jArr, iArr);
        }

        public java.lang.String toString() {
            long[] jArr = new long[this.mLength];
            getValues(jArr);
            return java.util.Arrays.toString(jArr);
        }
    }

    public LongArrayMultiStateCounter(int i, int i2) {
        com.android.internal.util.Preconditions.checkArgumentPositive(i, "stateCount must be greater than 0");
        this.mStateCount = i;
        this.mLength = i2;
        this.mNativeObject = native_init(i, i2);
        registerNativeAllocation();
    }

    private void registerNativeAllocation() {
        if (sRegistry == null) {
            synchronized (com.android.internal.os.LongArrayMultiStateCounter.class) {
                if (sRegistry == null) {
                    sRegistry = libcore.util.NativeAllocationRegistry.createMalloced(com.android.internal.os.LongArrayMultiStateCounter.class.getClassLoader(), native_getReleaseFunc());
                }
            }
        }
        sRegistry.registerNativeAllocation(this, this.mNativeObject);
    }

    private void registerNativeAllocation$ravenwood() {
    }

    private LongArrayMultiStateCounter(android.os.Parcel parcel) {
        this.mNativeObject = native_initFromParcel(parcel);
        registerNativeAllocation();
        this.mStateCount = native_getStateCount(this.mNativeObject);
        this.mLength = native_getArrayLength(this.mNativeObject);
    }

    public int getStateCount() {
        return this.mStateCount;
    }

    public int getArrayLength() {
        return this.mLength;
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

    public void setValues(int i, long[] jArr) {
        if (i < 0 || i >= this.mStateCount) {
            throw new java.lang.IllegalArgumentException("State: " + i + ", outside the range: [0-" + (this.mStateCount - 1) + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END);
        }
        if (jArr.length != this.mLength) {
            throw new java.lang.IllegalArgumentException("Invalid array length: " + jArr.length + ", expected: " + this.mLength);
        }
        com.android.internal.os.LongArrayMultiStateCounter.LongArrayContainer andSet = sTmpArrayContainer.getAndSet(null);
        if (andSet == null || andSet.mLength != jArr.length) {
            andSet = new com.android.internal.os.LongArrayMultiStateCounter.LongArrayContainer(jArr.length);
        }
        andSet.setValues(jArr);
        native_setValues(this.mNativeObject, i, andSet.mNativeObject);
        sTmpArrayContainer.set(andSet);
    }

    public void updateValues(long[] jArr, long j) {
        com.android.internal.os.LongArrayMultiStateCounter.LongArrayContainer andSet = sTmpArrayContainer.getAndSet(null);
        if (andSet == null || andSet.mLength != jArr.length) {
            andSet = new com.android.internal.os.LongArrayMultiStateCounter.LongArrayContainer(jArr.length);
        }
        andSet.setValues(jArr);
        updateValues(andSet, j);
        sTmpArrayContainer.set(andSet);
    }

    public void incrementValues(long[] jArr, long j) {
        com.android.internal.os.LongArrayMultiStateCounter.LongArrayContainer andSet = sTmpArrayContainer.getAndSet(null);
        if (andSet == null || andSet.mLength != jArr.length) {
            andSet = new com.android.internal.os.LongArrayMultiStateCounter.LongArrayContainer(jArr.length);
        }
        andSet.setValues(jArr);
        native_incrementValues(this.mNativeObject, andSet.mNativeObject, j);
        sTmpArrayContainer.set(andSet);
    }

    public void updateValues(com.android.internal.os.LongArrayMultiStateCounter.LongArrayContainer longArrayContainer, long j) {
        if (longArrayContainer.mLength != this.mLength) {
            throw new java.lang.IllegalArgumentException("Invalid array length: " + longArrayContainer.mLength + ", expected: " + this.mLength);
        }
        native_updateValues(this.mNativeObject, longArrayContainer.mNativeObject, j);
    }

    public void addCounts(com.android.internal.os.LongArrayMultiStateCounter.LongArrayContainer longArrayContainer) {
        if (longArrayContainer.mLength != this.mLength) {
            throw new java.lang.IllegalArgumentException("Invalid array length: " + longArrayContainer.mLength + ", expected: " + this.mLength);
        }
        native_addCounts(this.mNativeObject, longArrayContainer.mNativeObject);
    }

    public void reset() {
        native_reset(this.mNativeObject);
    }

    public void getCounts(long[] jArr, int i) {
        com.android.internal.os.LongArrayMultiStateCounter.LongArrayContainer andSet = sTmpArrayContainer.getAndSet(null);
        if (andSet == null || andSet.mLength != jArr.length) {
            andSet = new com.android.internal.os.LongArrayMultiStateCounter.LongArrayContainer(jArr.length);
        }
        getCounts(andSet, i);
        andSet.getValues(jArr);
        sTmpArrayContainer.set(andSet);
    }

    public void getCounts(com.android.internal.os.LongArrayMultiStateCounter.LongArrayContainer longArrayContainer, int i) {
        if (i < 0 || i >= this.mStateCount) {
            throw new java.lang.IllegalArgumentException("State: " + i + ", outside the range: [0-" + this.mStateCount + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END);
        }
        native_getCounts(this.mNativeObject, longArrayContainer.mNativeObject, i);
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
