package android.view;

/* loaded from: classes4.dex */
public final class MotionEvent extends android.view.InputEvent implements android.os.Parcelable {
    public static final int ACTION_BUTTON_PRESS = 11;
    public static final int ACTION_BUTTON_RELEASE = 12;
    public static final int ACTION_CANCEL = 3;
    public static final int ACTION_DOWN = 0;
    public static final int ACTION_HOVER_ENTER = 9;
    public static final int ACTION_HOVER_EXIT = 10;
    public static final int ACTION_HOVER_MOVE = 7;
    public static final int ACTION_MASK = 255;
    public static final int ACTION_MOVE = 2;
    public static final int ACTION_OUTSIDE = 4;

    @java.lang.Deprecated
    public static final int ACTION_POINTER_1_DOWN = 5;

    @java.lang.Deprecated
    public static final int ACTION_POINTER_1_UP = 6;

    @java.lang.Deprecated
    public static final int ACTION_POINTER_2_DOWN = 261;

    @java.lang.Deprecated
    public static final int ACTION_POINTER_2_UP = 262;

    @java.lang.Deprecated
    public static final int ACTION_POINTER_3_DOWN = 517;

    @java.lang.Deprecated
    public static final int ACTION_POINTER_3_UP = 518;
    public static final int ACTION_POINTER_DOWN = 5;

    @java.lang.Deprecated
    public static final int ACTION_POINTER_ID_MASK = 65280;

    @java.lang.Deprecated
    public static final int ACTION_POINTER_ID_SHIFT = 8;
    public static final int ACTION_POINTER_INDEX_MASK = 65280;
    public static final int ACTION_POINTER_INDEX_SHIFT = 8;
    public static final int ACTION_POINTER_UP = 6;
    public static final int ACTION_SCROLL = 8;
    public static final int ACTION_UP = 1;
    public static final int AXIS_BRAKE = 23;
    public static final int AXIS_DISTANCE = 24;
    public static final int AXIS_GAS = 22;
    public static final int AXIS_GENERIC_1 = 32;
    public static final int AXIS_GENERIC_10 = 41;
    public static final int AXIS_GENERIC_11 = 42;
    public static final int AXIS_GENERIC_12 = 43;
    public static final int AXIS_GENERIC_13 = 44;
    public static final int AXIS_GENERIC_14 = 45;
    public static final int AXIS_GENERIC_15 = 46;
    public static final int AXIS_GENERIC_16 = 47;
    public static final int AXIS_GENERIC_2 = 33;
    public static final int AXIS_GENERIC_3 = 34;
    public static final int AXIS_GENERIC_4 = 35;
    public static final int AXIS_GENERIC_5 = 36;
    public static final int AXIS_GENERIC_6 = 37;
    public static final int AXIS_GENERIC_7 = 38;
    public static final int AXIS_GENERIC_8 = 39;
    public static final int AXIS_GENERIC_9 = 40;
    public static final int AXIS_GESTURE_PINCH_SCALE_FACTOR = 52;
    public static final int AXIS_GESTURE_SCROLL_X_DISTANCE = 50;
    public static final int AXIS_GESTURE_SCROLL_Y_DISTANCE = 51;
    public static final int AXIS_GESTURE_SWIPE_FINGER_COUNT = 53;
    public static final int AXIS_GESTURE_X_OFFSET = 48;
    public static final int AXIS_GESTURE_Y_OFFSET = 49;
    public static final int AXIS_HAT_X = 15;
    public static final int AXIS_HAT_Y = 16;
    public static final int AXIS_HSCROLL = 10;
    public static final int AXIS_LTRIGGER = 17;
    public static final int AXIS_ORIENTATION = 8;
    public static final int AXIS_PRESSURE = 2;
    public static final int AXIS_RELATIVE_X = 27;
    public static final int AXIS_RELATIVE_Y = 28;
    public static final int AXIS_RTRIGGER = 18;
    public static final int AXIS_RUDDER = 20;
    public static final int AXIS_RX = 12;
    public static final int AXIS_RY = 13;
    public static final int AXIS_RZ = 14;
    public static final int AXIS_SCROLL = 26;
    public static final int AXIS_SIZE = 3;
    private static final android.util.SparseArray<java.lang.String> AXIS_SYMBOLIC_NAMES = new android.util.SparseArray<>();
    public static final int AXIS_THROTTLE = 19;
    public static final int AXIS_TILT = 25;
    public static final int AXIS_TOOL_MAJOR = 6;
    public static final int AXIS_TOOL_MINOR = 7;
    public static final int AXIS_TOUCH_MAJOR = 4;
    public static final int AXIS_TOUCH_MINOR = 5;
    public static final int AXIS_VSCROLL = 9;
    public static final int AXIS_WHEEL = 21;
    public static final int AXIS_X = 0;
    public static final int AXIS_Y = 1;
    public static final int AXIS_Z = 11;
    public static final int BUTTON_BACK = 8;
    public static final int BUTTON_FORWARD = 16;
    public static final int BUTTON_PRIMARY = 1;
    public static final int BUTTON_SECONDARY = 2;
    public static final int BUTTON_STYLUS_PRIMARY = 32;
    public static final int BUTTON_STYLUS_SECONDARY = 64;
    private static final java.lang.String[] BUTTON_SYMBOLIC_NAMES;
    public static final int BUTTON_TERTIARY = 4;
    public static final int CLASSIFICATION_AMBIGUOUS_GESTURE = 1;
    public static final int CLASSIFICATION_DEEP_PRESS = 2;
    public static final int CLASSIFICATION_MULTI_FINGER_SWIPE = 4;
    public static final int CLASSIFICATION_NONE = 0;
    public static final int CLASSIFICATION_PINCH = 5;
    public static final int CLASSIFICATION_TWO_FINGER_SWIPE = 3;
    public static final android.os.Parcelable.Creator<android.view.MotionEvent> CREATOR;
    private static final boolean DEBUG_CONCISE_TOSTRING = false;
    public static final int EDGE_BOTTOM = 2;
    public static final int EDGE_LEFT = 4;
    public static final int EDGE_RIGHT = 8;
    public static final int EDGE_TOP = 1;
    public static final int FLAG_CANCELED = 32;
    public static final int FLAG_HOVER_EXIT_PENDING = 4;
    public static final int FLAG_IS_ACCESSIBILITY_EVENT = 2048;
    public static final int FLAG_IS_GENERATED_GESTURE = 8;
    public static final int FLAG_NO_FOCUS_CHANGE = 64;
    public static final int FLAG_TAINTED = Integer.MIN_VALUE;
    public static final int FLAG_TARGET_ACCESSIBILITY_FOCUS = 1073741824;
    public static final int FLAG_WINDOW_IS_OBSCURED = 1;
    public static final int FLAG_WINDOW_IS_PARTIALLY_OBSCURED = 2;
    private static final int HISTORY_CURRENT = Integer.MIN_VALUE;
    private static final float INVALID_CURSOR_POSITION = Float.NaN;
    public static final int INVALID_POINTER_ID = -1;
    private static final java.lang.String LABEL_PREFIX = "AXIS_";
    private static final int MAX_RECYCLED = 10;
    private static final long NS_PER_MS = 1000000;
    private static final java.lang.String TAG = "MotionEvent";
    public static final int TOOL_TYPE_ERASER = 4;
    public static final int TOOL_TYPE_FINGER = 1;
    public static final int TOOL_TYPE_MOUSE = 3;
    public static final int TOOL_TYPE_PALM = 5;
    public static final int TOOL_TYPE_STYLUS = 2;
    private static final android.util.SparseArray<java.lang.String> TOOL_TYPE_SYMBOLIC_NAMES;
    public static final int TOOL_TYPE_UNKNOWN = 0;
    private static final java.lang.Object gRecyclerLock;
    private static android.view.MotionEvent gRecyclerTop;
    private static int gRecyclerUsed;
    private static final java.lang.Object gSharedTempLock;
    private static android.view.MotionEvent.PointerCoords[] gSharedTempPointerCoords;
    private static int[] gSharedTempPointerIndexMap;
    private static android.view.MotionEvent.PointerProperties[] gSharedTempPointerProperties;
    private long mNativePtr;
    private android.view.MotionEvent mNext;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Classification {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ToolType {
    }

    private static native void nativeAddBatch(long j, long j2, android.view.MotionEvent.PointerCoords[] pointerCoordsArr, int i);

    @dalvik.annotation.optimization.FastNative
    private static native void nativeApplyTransform(long j, android.graphics.Matrix matrix);

    private static native int nativeAxisFromString(java.lang.String str);

    private static native java.lang.String nativeAxisToString(int i);

    @dalvik.annotation.optimization.CriticalNative
    private static native long nativeCopy(long j, long j2, boolean z);

    private static native void nativeDispose(long j);

    @dalvik.annotation.optimization.CriticalNative
    private static native int nativeFindPointerIndex(long j, int i);

    @dalvik.annotation.optimization.CriticalNative
    private static native int nativeGetAction(long j);

    @dalvik.annotation.optimization.CriticalNative
    private static native int nativeGetActionButton(long j);

    @dalvik.annotation.optimization.FastNative
    private static native float nativeGetAxisValue(long j, int i, int i2, int i3);

    @dalvik.annotation.optimization.CriticalNative
    private static native int nativeGetButtonState(long j);

    @dalvik.annotation.optimization.CriticalNative
    private static native int nativeGetClassification(long j);

    @dalvik.annotation.optimization.CriticalNative
    private static native int nativeGetDeviceId(long j);

    @dalvik.annotation.optimization.CriticalNative
    private static native int nativeGetDisplayId(long j);

    @dalvik.annotation.optimization.CriticalNative
    private static native long nativeGetDownTimeNanos(long j);

    @dalvik.annotation.optimization.CriticalNative
    private static native int nativeGetEdgeFlags(long j);

    @dalvik.annotation.optimization.FastNative
    private static native long nativeGetEventTimeNanos(long j, int i);

    @dalvik.annotation.optimization.CriticalNative
    private static native int nativeGetFlags(long j);

    @dalvik.annotation.optimization.CriticalNative
    private static native int nativeGetHistorySize(long j);

    @dalvik.annotation.optimization.CriticalNative
    private static native int nativeGetId(long j);

    @dalvik.annotation.optimization.CriticalNative
    private static native int nativeGetMetaState(long j);

    private static native void nativeGetPointerCoords(long j, int i, int i2, android.view.MotionEvent.PointerCoords pointerCoords);

    @dalvik.annotation.optimization.CriticalNative
    private static native int nativeGetPointerCount(long j);

    @dalvik.annotation.optimization.FastNative
    private static native int nativeGetPointerId(long j, int i);

    private static native void nativeGetPointerProperties(long j, int i, android.view.MotionEvent.PointerProperties pointerProperties);

    @dalvik.annotation.optimization.FastNative
    private static native float nativeGetRawAxisValue(long j, int i, int i2, int i3);

    @dalvik.annotation.optimization.CriticalNative
    private static native int nativeGetSource(long j);

    @dalvik.annotation.optimization.CriticalNative
    private static native int nativeGetSurfaceRotation(long j);

    @dalvik.annotation.optimization.FastNative
    private static native int nativeGetToolType(long j, int i);

    @dalvik.annotation.optimization.CriticalNative
    private static native float nativeGetXCursorPosition(long j);

    @dalvik.annotation.optimization.CriticalNative
    private static native float nativeGetXOffset(long j);

    @dalvik.annotation.optimization.CriticalNative
    private static native float nativeGetXPrecision(long j);

    @dalvik.annotation.optimization.CriticalNative
    private static native float nativeGetYCursorPosition(long j);

    @dalvik.annotation.optimization.CriticalNative
    private static native float nativeGetYOffset(long j);

    @dalvik.annotation.optimization.CriticalNative
    private static native float nativeGetYPrecision(long j);

    private static native long nativeInitialize(long j, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, float f, float f2, float f3, float f4, long j2, long j3, int i10, android.view.MotionEvent.PointerProperties[] pointerPropertiesArr, android.view.MotionEvent.PointerCoords[] pointerCoordsArr);

    @dalvik.annotation.optimization.CriticalNative
    private static native boolean nativeIsTouchEvent(long j);

    @dalvik.annotation.optimization.CriticalNative
    private static native void nativeOffsetLocation(long j, float f, float f2);

    private static native long nativeReadFromParcel(long j, android.os.Parcel parcel);

    @dalvik.annotation.optimization.CriticalNative
    private static native void nativeScale(long j, float f);

    @dalvik.annotation.optimization.CriticalNative
    private static native void nativeSetAction(long j, int i);

    @dalvik.annotation.optimization.CriticalNative
    private static native void nativeSetActionButton(long j, int i);

    @dalvik.annotation.optimization.CriticalNative
    private static native void nativeSetButtonState(long j, int i);

    @dalvik.annotation.optimization.CriticalNative
    private static native void nativeSetCursorPosition(long j, float f, float f2);

    @dalvik.annotation.optimization.CriticalNative
    private static native void nativeSetDisplayId(long j, int i);

    @dalvik.annotation.optimization.CriticalNative
    private static native void nativeSetDownTimeNanos(long j, long j2);

    @dalvik.annotation.optimization.CriticalNative
    private static native void nativeSetEdgeFlags(long j, int i);

    @dalvik.annotation.optimization.CriticalNative
    private static native void nativeSetFlags(long j, int i);

    @dalvik.annotation.optimization.CriticalNative
    private static native void nativeSetSource(long j, int i);

    @dalvik.annotation.optimization.FastNative
    private static native void nativeTransform(long j, android.graphics.Matrix matrix);

    private static native void nativeWriteToParcel(long j, android.os.Parcel parcel);

    static {
        android.util.SparseArray<java.lang.String> sparseArray = AXIS_SYMBOLIC_NAMES;
        sparseArray.append(0, "AXIS_X");
        sparseArray.append(1, "AXIS_Y");
        sparseArray.append(2, "AXIS_PRESSURE");
        sparseArray.append(3, "AXIS_SIZE");
        sparseArray.append(4, "AXIS_TOUCH_MAJOR");
        sparseArray.append(5, "AXIS_TOUCH_MINOR");
        sparseArray.append(6, "AXIS_TOOL_MAJOR");
        sparseArray.append(7, "AXIS_TOOL_MINOR");
        sparseArray.append(8, "AXIS_ORIENTATION");
        sparseArray.append(9, "AXIS_VSCROLL");
        sparseArray.append(10, "AXIS_HSCROLL");
        sparseArray.append(11, "AXIS_Z");
        sparseArray.append(12, "AXIS_RX");
        sparseArray.append(13, "AXIS_RY");
        sparseArray.append(14, "AXIS_RZ");
        sparseArray.append(15, "AXIS_HAT_X");
        sparseArray.append(16, "AXIS_HAT_Y");
        sparseArray.append(17, "AXIS_LTRIGGER");
        sparseArray.append(18, "AXIS_RTRIGGER");
        sparseArray.append(19, "AXIS_THROTTLE");
        sparseArray.append(20, "AXIS_RUDDER");
        sparseArray.append(21, "AXIS_WHEEL");
        sparseArray.append(22, "AXIS_GAS");
        sparseArray.append(23, "AXIS_BRAKE");
        sparseArray.append(24, "AXIS_DISTANCE");
        sparseArray.append(25, "AXIS_TILT");
        sparseArray.append(26, "AXIS_SCROLL");
        sparseArray.append(27, "AXIS_REALTIVE_X");
        sparseArray.append(28, "AXIS_REALTIVE_Y");
        sparseArray.append(32, "AXIS_GENERIC_1");
        sparseArray.append(33, "AXIS_GENERIC_2");
        sparseArray.append(34, "AXIS_GENERIC_3");
        sparseArray.append(35, "AXIS_GENERIC_4");
        sparseArray.append(36, "AXIS_GENERIC_5");
        sparseArray.append(37, "AXIS_GENERIC_6");
        sparseArray.append(38, "AXIS_GENERIC_7");
        sparseArray.append(39, "AXIS_GENERIC_8");
        sparseArray.append(40, "AXIS_GENERIC_9");
        sparseArray.append(41, "AXIS_GENERIC_10");
        sparseArray.append(42, "AXIS_GENERIC_11");
        sparseArray.append(43, "AXIS_GENERIC_12");
        sparseArray.append(44, "AXIS_GENERIC_13");
        sparseArray.append(45, "AXIS_GENERIC_14");
        sparseArray.append(46, "AXIS_GENERIC_15");
        sparseArray.append(47, "AXIS_GENERIC_16");
        sparseArray.append(48, "AXIS_GESTURE_X_OFFSET");
        sparseArray.append(49, "AXIS_GESTURE_Y_OFFSET");
        sparseArray.append(50, "AXIS_GESTURE_SCROLL_X_DISTANCE");
        sparseArray.append(51, "AXIS_GESTURE_SCROLL_Y_DISTANCE");
        sparseArray.append(52, "AXIS_GESTURE_PINCH_SCALE_FACTOR");
        sparseArray.append(53, "AXIS_GESTURE_SWIPE_FINGER_COUNT");
        BUTTON_SYMBOLIC_NAMES = new java.lang.String[]{"BUTTON_PRIMARY", "BUTTON_SECONDARY", "BUTTON_TERTIARY", "BUTTON_BACK", "BUTTON_FORWARD", "BUTTON_STYLUS_PRIMARY", "BUTTON_STYLUS_SECONDARY", "0x00000080", "0x00000100", "0x00000200", "0x00000400", "0x00000800", "0x00001000", "0x00002000", "0x00004000", "0x00008000", "0x00010000", "0x00020000", "0x00040000", "0x00080000", "0x00100000", "0x00200000", "0x00400000", "0x00800000", "0x01000000", "0x02000000", "0x04000000", "0x08000000", "0x10000000", "0x20000000", "0x40000000", "0x80000000"};
        TOOL_TYPE_SYMBOLIC_NAMES = new android.util.SparseArray<>();
        android.util.SparseArray<java.lang.String> sparseArray2 = TOOL_TYPE_SYMBOLIC_NAMES;
        sparseArray2.append(0, "TOOL_TYPE_UNKNOWN");
        sparseArray2.append(1, "TOOL_TYPE_FINGER");
        sparseArray2.append(2, "TOOL_TYPE_STYLUS");
        sparseArray2.append(3, "TOOL_TYPE_MOUSE");
        sparseArray2.append(4, "TOOL_TYPE_ERASER");
        gRecyclerLock = new java.lang.Object();
        gSharedTempLock = new java.lang.Object();
        CREATOR = new android.os.Parcelable.Creator<android.view.MotionEvent>() { // from class: android.view.MotionEvent.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.view.MotionEvent createFromParcel(android.os.Parcel parcel) {
                parcel.readInt();
                return android.view.MotionEvent.createFromParcelBody(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.view.MotionEvent[] newArray(int i) {
                return new android.view.MotionEvent[i];
            }
        };
    }

    private static final void ensureSharedTempPointerCapacity(int i) {
        if (gSharedTempPointerCoords == null || gSharedTempPointerCoords.length < i) {
            int length = gSharedTempPointerCoords != null ? gSharedTempPointerCoords.length : 8;
            while (length < i) {
                length *= 2;
            }
            gSharedTempPointerCoords = android.view.MotionEvent.PointerCoords.createArray(length);
            gSharedTempPointerProperties = android.view.MotionEvent.PointerProperties.createArray(length);
            gSharedTempPointerIndexMap = new int[length];
        }
    }

    private MotionEvent() {
    }

    protected void finalize() throws java.lang.Throwable {
        try {
            if (this.mNativePtr != 0) {
                nativeDispose(this.mNativePtr);
                this.mNativePtr = 0L;
            }
        } finally {
            super.finalize();
        }
    }

    private static android.view.MotionEvent obtain() {
        synchronized (gRecyclerLock) {
            android.view.MotionEvent motionEvent = gRecyclerTop;
            if (motionEvent == null) {
                return new android.view.MotionEvent();
            }
            gRecyclerTop = motionEvent.mNext;
            gRecyclerUsed--;
            motionEvent.mNext = null;
            motionEvent.prepareForReuse();
            return motionEvent;
        }
    }

    public static android.view.MotionEvent obtain(long j, long j2, int i, int i2, android.view.MotionEvent.PointerProperties[] pointerPropertiesArr, android.view.MotionEvent.PointerCoords[] pointerCoordsArr, int i3, int i4, float f, float f2, int i5, int i6, int i7, int i8, int i9, int i10) {
        android.view.MotionEvent obtain = obtain();
        if (!obtain.initialize(i5, i7, i8, i, i9, i6, i3, i4, i10, 0.0f, 0.0f, f, f2, j * 1000000, j2 * 1000000, i2, pointerPropertiesArr, pointerCoordsArr)) {
            android.util.Log.e(TAG, "Could not initialize MotionEvent");
            obtain.recycle();
            return null;
        }
        return obtain;
    }

    public static android.view.MotionEvent obtain(long j, long j2, int i, int i2, android.view.MotionEvent.PointerProperties[] pointerPropertiesArr, android.view.MotionEvent.PointerCoords[] pointerCoordsArr, int i3, int i4, float f, float f2, int i5, int i6, int i7, int i8, int i9) {
        return obtain(j, j2, i, i2, pointerPropertiesArr, pointerCoordsArr, i3, i4, f, f2, i5, i6, i7, i8, i9, 0);
    }

    public static android.view.MotionEvent obtain(long j, long j2, int i, int i2, android.view.MotionEvent.PointerProperties[] pointerPropertiesArr, android.view.MotionEvent.PointerCoords[] pointerCoordsArr, int i3, int i4, float f, float f2, int i5, int i6, int i7, int i8) {
        return obtain(j, j2, i, i2, pointerPropertiesArr, pointerCoordsArr, i3, i4, f, f2, i5, i6, i7, 0, i8);
    }

    @java.lang.Deprecated
    public static android.view.MotionEvent obtain(long j, long j2, int i, int i2, int[] iArr, android.view.MotionEvent.PointerCoords[] pointerCoordsArr, int i3, float f, float f2, int i4, int i5, int i6, int i7) {
        android.view.MotionEvent obtain;
        synchronized (gSharedTempLock) {
            ensureSharedTempPointerCapacity(i2);
            android.view.MotionEvent.PointerProperties[] pointerPropertiesArr = gSharedTempPointerProperties;
            for (int i8 = 0; i8 < i2; i8++) {
                pointerPropertiesArr[i8].clear();
                pointerPropertiesArr[i8].id = iArr[i8];
            }
            obtain = obtain(j, j2, i, i2, pointerPropertiesArr, pointerCoordsArr, i3, 0, f, f2, i4, i5, i6, i7);
        }
        return obtain;
    }

    public static android.view.MotionEvent obtain(long j, long j2, int i, float f, float f2, float f3, float f4, int i2, float f5, float f6, int i3, int i4) {
        return obtain(j, j2, i, f, f2, f3, f4, i2, f5, f6, i3, i4, 2, 0);
    }

    public static android.view.MotionEvent obtain(long j, long j2, int i, float f, float f2, float f3, float f4, int i2, float f5, float f6, int i3, int i4, int i5, int i6) {
        android.view.MotionEvent obtain = obtain();
        synchronized (gSharedTempLock) {
            ensureSharedTempPointerCapacity(1);
            android.view.MotionEvent.PointerProperties[] pointerPropertiesArr = gSharedTempPointerProperties;
            pointerPropertiesArr[0].clear();
            pointerPropertiesArr[0].id = 0;
            android.view.MotionEvent.PointerCoords[] pointerCoordsArr = gSharedTempPointerCoords;
            pointerCoordsArr[0].clear();
            pointerCoordsArr[0].x = f;
            pointerCoordsArr[0].y = f2;
            pointerCoordsArr[0].pressure = f3;
            pointerCoordsArr[0].size = f4;
            obtain.initialize(i3, i5, i6, i, 0, i4, i2, 0, 0, 0.0f, 0.0f, f5, f6, j * 1000000, j2 * 1000000, 1, pointerPropertiesArr, pointerCoordsArr);
        }
        return obtain;
    }

    @java.lang.Deprecated
    public static android.view.MotionEvent obtain(long j, long j2, int i, int i2, float f, float f2, float f3, float f4, int i3, float f5, float f6, int i4, int i5) {
        return obtain(j, j2, i, f, f2, f3, f4, i3, f5, f6, i4, i5);
    }

    public static android.view.MotionEvent obtain(long j, long j2, int i, float f, float f2, int i2) {
        return obtain(j, j2, i, f, f2, 1.0f, 1.0f, i2, 1.0f, 1.0f, 0, 0);
    }

    public static android.view.MotionEvent obtain(android.view.MotionEvent motionEvent) {
        if (motionEvent == null) {
            throw new java.lang.IllegalArgumentException("other motion event must not be null");
        }
        android.view.MotionEvent obtain = obtain();
        obtain.mNativePtr = nativeCopy(obtain.mNativePtr, motionEvent.mNativePtr, true);
        return obtain;
    }

    public static android.view.MotionEvent obtainNoHistory(android.view.MotionEvent motionEvent) {
        if (motionEvent == null) {
            throw new java.lang.IllegalArgumentException("other motion event must not be null");
        }
        android.view.MotionEvent obtain = obtain();
        obtain.mNativePtr = nativeCopy(obtain.mNativePtr, motionEvent.mNativePtr, false);
        return obtain;
    }

    private boolean initialize(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, float f, float f2, float f3, float f4, long j, long j2, int i10, android.view.MotionEvent.PointerProperties[] pointerPropertiesArr, android.view.MotionEvent.PointerCoords[] pointerCoordsArr) {
        int i11;
        if (i4 != 3) {
            i11 = i5;
        } else {
            i11 = i5 | 32;
        }
        this.mNativePtr = nativeInitialize(this.mNativePtr, i, i2, i3, i4, i11, i6, i7, i8, i9, f, f2, f3, f4, j, j2, i10, pointerPropertiesArr, pointerCoordsArr);
        if (this.mNativePtr == 0) {
            return false;
        }
        updateCursorPosition();
        return true;
    }

    @Override // android.view.InputEvent
    public android.view.MotionEvent copy() {
        return obtain(this);
    }

    @Override // android.view.InputEvent
    public final void recycle() {
        super.recycle();
        synchronized (gRecyclerLock) {
            if (gRecyclerUsed < 10) {
                gRecyclerUsed++;
                this.mNext = gRecyclerTop;
                gRecyclerTop = this;
            }
        }
    }

    public final void scale(float f) {
        if (f != 1.0f) {
            nativeScale(this.mNativePtr, f);
        }
    }

    @Override // android.view.InputEvent
    public int getId() {
        return nativeGetId(this.mNativePtr);
    }

    @Override // android.view.InputEvent
    public final int getDeviceId() {
        return nativeGetDeviceId(this.mNativePtr);
    }

    @Override // android.view.InputEvent
    public final int getSource() {
        return nativeGetSource(this.mNativePtr);
    }

    @Override // android.view.InputEvent
    public final void setSource(int i) {
        if (i == getSource()) {
            return;
        }
        nativeSetSource(this.mNativePtr, i);
        updateCursorPosition();
    }

    @Override // android.view.InputEvent
    public int getDisplayId() {
        return nativeGetDisplayId(this.mNativePtr);
    }

    @Override // android.view.InputEvent
    public void setDisplayId(int i) {
        nativeSetDisplayId(this.mNativePtr, i);
    }

    public final int getAction() {
        return nativeGetAction(this.mNativePtr);
    }

    public final int getActionMasked() {
        return nativeGetAction(this.mNativePtr) & 255;
    }

    public final int getActionIndex() {
        return (nativeGetAction(this.mNativePtr) & 65280) >> 8;
    }

    public final boolean isTouchEvent() {
        return nativeIsTouchEvent(this.mNativePtr);
    }

    public boolean isStylusPointer() {
        int actionIndex = getActionIndex();
        return isFromSource(16386) && (getToolType(actionIndex) == 2 || getToolType(actionIndex) == 4);
    }

    public boolean isHoverEvent() {
        return getActionMasked() == 9 || getActionMasked() == 10 || getActionMasked() == 7;
    }

    public final int getFlags() {
        return nativeGetFlags(this.mNativePtr);
    }

    @Override // android.view.InputEvent
    public final boolean isTainted() {
        return (getFlags() & Integer.MIN_VALUE) != 0;
    }

    @Override // android.view.InputEvent
    public final void setTainted(boolean z) {
        int flags = getFlags();
        nativeSetFlags(this.mNativePtr, z ? Integer.MIN_VALUE | flags : Integer.MAX_VALUE & flags);
    }

    private void setCanceled(boolean z) {
        int flags = getFlags();
        nativeSetFlags(this.mNativePtr, z ? flags | 32 : flags & (-33));
    }

    public boolean isTargetAccessibilityFocus() {
        return (getFlags() & 1073741824) != 0;
    }

    public void setTargetAccessibilityFocus(boolean z) {
        int i;
        int flags = getFlags();
        long j = this.mNativePtr;
        if (z) {
            i = 1073741824 | flags;
        } else {
            i = (-1073741825) & flags;
        }
        nativeSetFlags(j, i);
    }

    public final boolean isHoverExitPending() {
        return (getFlags() & 4) != 0;
    }

    public void setHoverExitPending(boolean z) {
        int i;
        int flags = getFlags();
        long j = this.mNativePtr;
        if (z) {
            i = flags | 4;
        } else {
            i = flags & (-5);
        }
        nativeSetFlags(j, i);
    }

    public final long getDownTime() {
        return nativeGetDownTimeNanos(this.mNativePtr) / 1000000;
    }

    public final void setDownTime(long j) {
        nativeSetDownTimeNanos(this.mNativePtr, j * 1000000);
    }

    @Override // android.view.InputEvent
    public final long getEventTime() {
        return nativeGetEventTimeNanos(this.mNativePtr, Integer.MIN_VALUE) / 1000000;
    }

    @Override // android.view.InputEvent
    public long getEventTimeNanos() {
        return nativeGetEventTimeNanos(this.mNativePtr, Integer.MIN_VALUE);
    }

    public final float getX() {
        return nativeGetAxisValue(this.mNativePtr, 0, 0, Integer.MIN_VALUE);
    }

    public final float getY() {
        return nativeGetAxisValue(this.mNativePtr, 1, 0, Integer.MIN_VALUE);
    }

    public final float getPressure() {
        return nativeGetAxisValue(this.mNativePtr, 2, 0, Integer.MIN_VALUE);
    }

    public final float getSize() {
        return nativeGetAxisValue(this.mNativePtr, 3, 0, Integer.MIN_VALUE);
    }

    public final float getTouchMajor() {
        return nativeGetAxisValue(this.mNativePtr, 4, 0, Integer.MIN_VALUE);
    }

    public final float getTouchMinor() {
        return nativeGetAxisValue(this.mNativePtr, 5, 0, Integer.MIN_VALUE);
    }

    public final float getToolMajor() {
        return nativeGetAxisValue(this.mNativePtr, 6, 0, Integer.MIN_VALUE);
    }

    public final float getToolMinor() {
        return nativeGetAxisValue(this.mNativePtr, 7, 0, Integer.MIN_VALUE);
    }

    public final float getOrientation() {
        return nativeGetAxisValue(this.mNativePtr, 8, 0, Integer.MIN_VALUE);
    }

    public final float getAxisValue(int i) {
        return nativeGetAxisValue(this.mNativePtr, i, 0, Integer.MIN_VALUE);
    }

    public final int getPointerCount() {
        return nativeGetPointerCount(this.mNativePtr);
    }

    public final int getPointerId(int i) {
        return nativeGetPointerId(this.mNativePtr, i);
    }

    public int getToolType(int i) {
        return nativeGetToolType(this.mNativePtr, i);
    }

    public final int findPointerIndex(int i) {
        return nativeFindPointerIndex(this.mNativePtr, i);
    }

    public final float getX(int i) {
        return nativeGetAxisValue(this.mNativePtr, 0, i, Integer.MIN_VALUE);
    }

    public final float getY(int i) {
        return nativeGetAxisValue(this.mNativePtr, 1, i, Integer.MIN_VALUE);
    }

    public final float getPressure(int i) {
        return nativeGetAxisValue(this.mNativePtr, 2, i, Integer.MIN_VALUE);
    }

    public final float getSize(int i) {
        return nativeGetAxisValue(this.mNativePtr, 3, i, Integer.MIN_VALUE);
    }

    public final float getTouchMajor(int i) {
        return nativeGetAxisValue(this.mNativePtr, 4, i, Integer.MIN_VALUE);
    }

    public final float getTouchMinor(int i) {
        return nativeGetAxisValue(this.mNativePtr, 5, i, Integer.MIN_VALUE);
    }

    public final float getToolMajor(int i) {
        return nativeGetAxisValue(this.mNativePtr, 6, i, Integer.MIN_VALUE);
    }

    public final float getToolMinor(int i) {
        return nativeGetAxisValue(this.mNativePtr, 7, i, Integer.MIN_VALUE);
    }

    public final float getOrientation(int i) {
        return nativeGetAxisValue(this.mNativePtr, 8, i, Integer.MIN_VALUE);
    }

    public final float getAxisValue(int i, int i2) {
        return nativeGetAxisValue(this.mNativePtr, i, i2, Integer.MIN_VALUE);
    }

    public final void getPointerCoords(int i, android.view.MotionEvent.PointerCoords pointerCoords) {
        nativeGetPointerCoords(this.mNativePtr, i, Integer.MIN_VALUE, pointerCoords);
    }

    public final void getPointerProperties(int i, android.view.MotionEvent.PointerProperties pointerProperties) {
        nativeGetPointerProperties(this.mNativePtr, i, pointerProperties);
    }

    public final int getMetaState() {
        return nativeGetMetaState(this.mNativePtr);
    }

    public final int getButtonState() {
        return nativeGetButtonState(this.mNativePtr);
    }

    public final void setButtonState(int i) {
        nativeSetButtonState(this.mNativePtr, i);
    }

    public int getClassification() {
        return nativeGetClassification(this.mNativePtr);
    }

    public final int getActionButton() {
        return nativeGetActionButton(this.mNativePtr);
    }

    public final void setActionButton(int i) {
        nativeSetActionButton(this.mNativePtr, i);
    }

    public final float getRawX() {
        return nativeGetRawAxisValue(this.mNativePtr, 0, 0, Integer.MIN_VALUE);
    }

    public final float getRawY() {
        return nativeGetRawAxisValue(this.mNativePtr, 1, 0, Integer.MIN_VALUE);
    }

    public float getRawX(int i) {
        return nativeGetRawAxisValue(this.mNativePtr, 0, i, Integer.MIN_VALUE);
    }

    public float getRawY(int i) {
        return nativeGetRawAxisValue(this.mNativePtr, 1, i, Integer.MIN_VALUE);
    }

    public final float getXPrecision() {
        return nativeGetXPrecision(this.mNativePtr);
    }

    public final float getYPrecision() {
        return nativeGetYPrecision(this.mNativePtr);
    }

    public float getXCursorPosition() {
        return nativeGetXCursorPosition(this.mNativePtr);
    }

    public float getYCursorPosition() {
        return nativeGetYCursorPosition(this.mNativePtr);
    }

    private void setCursorPosition(float f, float f2) {
        nativeSetCursorPosition(this.mNativePtr, f, f2);
    }

    public final int getHistorySize() {
        return nativeGetHistorySize(this.mNativePtr);
    }

    public final long getHistoricalEventTime(int i) {
        return nativeGetEventTimeNanos(this.mNativePtr, i) / 1000000;
    }

    public long getHistoricalEventTimeNanos(int i) {
        return nativeGetEventTimeNanos(this.mNativePtr, i);
    }

    public final float getHistoricalX(int i) {
        return nativeGetAxisValue(this.mNativePtr, 0, 0, i);
    }

    public final float getHistoricalY(int i) {
        return nativeGetAxisValue(this.mNativePtr, 1, 0, i);
    }

    public final float getHistoricalPressure(int i) {
        return nativeGetAxisValue(this.mNativePtr, 2, 0, i);
    }

    public final float getHistoricalSize(int i) {
        return nativeGetAxisValue(this.mNativePtr, 3, 0, i);
    }

    public final float getHistoricalTouchMajor(int i) {
        return nativeGetAxisValue(this.mNativePtr, 4, 0, i);
    }

    public final float getHistoricalTouchMinor(int i) {
        return nativeGetAxisValue(this.mNativePtr, 5, 0, i);
    }

    public final float getHistoricalToolMajor(int i) {
        return nativeGetAxisValue(this.mNativePtr, 6, 0, i);
    }

    public final float getHistoricalToolMinor(int i) {
        return nativeGetAxisValue(this.mNativePtr, 7, 0, i);
    }

    public final float getHistoricalOrientation(int i) {
        return nativeGetAxisValue(this.mNativePtr, 8, 0, i);
    }

    public final float getHistoricalAxisValue(int i, int i2) {
        return nativeGetAxisValue(this.mNativePtr, i, 0, i2);
    }

    public final float getHistoricalX(int i, int i2) {
        return nativeGetAxisValue(this.mNativePtr, 0, i, i2);
    }

    public final float getHistoricalY(int i, int i2) {
        return nativeGetAxisValue(this.mNativePtr, 1, i, i2);
    }

    public final float getHistoricalPressure(int i, int i2) {
        return nativeGetAxisValue(this.mNativePtr, 2, i, i2);
    }

    public final float getHistoricalSize(int i, int i2) {
        return nativeGetAxisValue(this.mNativePtr, 3, i, i2);
    }

    public final float getHistoricalTouchMajor(int i, int i2) {
        return nativeGetAxisValue(this.mNativePtr, 4, i, i2);
    }

    public final float getHistoricalTouchMinor(int i, int i2) {
        return nativeGetAxisValue(this.mNativePtr, 5, i, i2);
    }

    public final float getHistoricalToolMajor(int i, int i2) {
        return nativeGetAxisValue(this.mNativePtr, 6, i, i2);
    }

    public final float getHistoricalToolMinor(int i, int i2) {
        return nativeGetAxisValue(this.mNativePtr, 7, i, i2);
    }

    public final float getHistoricalOrientation(int i, int i2) {
        return nativeGetAxisValue(this.mNativePtr, 8, i, i2);
    }

    public final float getHistoricalAxisValue(int i, int i2, int i3) {
        return nativeGetAxisValue(this.mNativePtr, i, i2, i3);
    }

    public final void getHistoricalPointerCoords(int i, int i2, android.view.MotionEvent.PointerCoords pointerCoords) {
        nativeGetPointerCoords(this.mNativePtr, i, i2, pointerCoords);
    }

    public final int getEdgeFlags() {
        return nativeGetEdgeFlags(this.mNativePtr);
    }

    public final void setEdgeFlags(int i) {
        nativeSetEdgeFlags(this.mNativePtr, i);
    }

    public final void setAction(int i) {
        int i2 = i & 255;
        if (i2 == 3) {
            setCanceled(true);
        } else if (i2 != 6) {
            setCanceled(false);
        }
        nativeSetAction(this.mNativePtr, i);
    }

    public final void offsetLocation(float f, float f2) {
        if (f != 0.0f || f2 != 0.0f) {
            nativeOffsetLocation(this.mNativePtr, f, f2);
        }
    }

    public final void setLocation(float f, float f2) {
        offsetLocation(f - getX(), f2 - getY());
    }

    public final void transform(android.graphics.Matrix matrix) {
        if (matrix == null) {
            throw new java.lang.IllegalArgumentException("matrix must not be null");
        }
        nativeTransform(this.mNativePtr, matrix);
    }

    public void applyTransform(android.graphics.Matrix matrix) {
        if (matrix == null) {
            throw new java.lang.IllegalArgumentException("matrix must not be null");
        }
        nativeApplyTransform(this.mNativePtr, matrix);
    }

    public final void addBatch(long j, float f, float f2, float f3, float f4, int i) {
        synchronized (gSharedTempLock) {
            ensureSharedTempPointerCapacity(1);
            android.view.MotionEvent.PointerCoords[] pointerCoordsArr = gSharedTempPointerCoords;
            pointerCoordsArr[0].clear();
            pointerCoordsArr[0].x = f;
            pointerCoordsArr[0].y = f2;
            pointerCoordsArr[0].pressure = f3;
            pointerCoordsArr[0].size = f4;
            nativeAddBatch(this.mNativePtr, 1000000 * j, pointerCoordsArr, i);
        }
    }

    public final void addBatch(long j, android.view.MotionEvent.PointerCoords[] pointerCoordsArr, int i) {
        nativeAddBatch(this.mNativePtr, 1000000 * j, pointerCoordsArr, i);
    }

    public final boolean addBatch(android.view.MotionEvent motionEvent) {
        int nativeGetPointerCount;
        int nativeGetAction = nativeGetAction(this.mNativePtr);
        if ((nativeGetAction != 2 && nativeGetAction != 7) || nativeGetAction != nativeGetAction(motionEvent.mNativePtr) || nativeGetDeviceId(this.mNativePtr) != nativeGetDeviceId(motionEvent.mNativePtr) || nativeGetSource(this.mNativePtr) != nativeGetSource(motionEvent.mNativePtr) || nativeGetDisplayId(this.mNativePtr) != nativeGetDisplayId(motionEvent.mNativePtr) || nativeGetFlags(this.mNativePtr) != nativeGetFlags(motionEvent.mNativePtr) || nativeGetClassification(this.mNativePtr) != nativeGetClassification(motionEvent.mNativePtr) || (nativeGetPointerCount = nativeGetPointerCount(this.mNativePtr)) != nativeGetPointerCount(motionEvent.mNativePtr)) {
            return false;
        }
        synchronized (gSharedTempLock) {
            ensureSharedTempPointerCapacity(java.lang.Math.max(nativeGetPointerCount, 2));
            android.view.MotionEvent.PointerProperties[] pointerPropertiesArr = gSharedTempPointerProperties;
            android.view.MotionEvent.PointerCoords[] pointerCoordsArr = gSharedTempPointerCoords;
            for (int i = 0; i < nativeGetPointerCount; i++) {
                nativeGetPointerProperties(this.mNativePtr, i, pointerPropertiesArr[0]);
                nativeGetPointerProperties(motionEvent.mNativePtr, i, pointerPropertiesArr[1]);
                if (!pointerPropertiesArr[0].equals(pointerPropertiesArr[1])) {
                    return false;
                }
            }
            int nativeGetMetaState = nativeGetMetaState(motionEvent.mNativePtr);
            int nativeGetHistorySize = nativeGetHistorySize(motionEvent.mNativePtr);
            int i2 = 0;
            while (i2 <= nativeGetHistorySize) {
                int i3 = i2 == nativeGetHistorySize ? Integer.MIN_VALUE : i2;
                for (int i4 = 0; i4 < nativeGetPointerCount; i4++) {
                    nativeGetPointerCoords(motionEvent.mNativePtr, i4, i3, pointerCoordsArr[i4]);
                }
                nativeAddBatch(this.mNativePtr, nativeGetEventTimeNanos(motionEvent.mNativePtr, i3), pointerCoordsArr, nativeGetMetaState);
                i2++;
            }
            return true;
        }
    }

    public final boolean isWithinBoundsNoHistory(float f, float f2, float f3, float f4) {
        int nativeGetPointerCount = nativeGetPointerCount(this.mNativePtr);
        for (int i = 0; i < nativeGetPointerCount; i++) {
            float nativeGetAxisValue = nativeGetAxisValue(this.mNativePtr, 0, i, Integer.MIN_VALUE);
            float nativeGetAxisValue2 = nativeGetAxisValue(this.mNativePtr, 1, i, Integer.MIN_VALUE);
            if (nativeGetAxisValue < f || nativeGetAxisValue > f3 || nativeGetAxisValue2 < f2 || nativeGetAxisValue2 > f4) {
                return false;
            }
        }
        return true;
    }

    private static final float clamp(float f, float f2, float f3) {
        if (f < f2) {
            return f2;
        }
        if (f > f3) {
            return f3;
        }
        return f;
    }

    public final android.view.MotionEvent clampNoHistory(float f, float f2, float f3, float f4) {
        android.view.MotionEvent obtain = obtain();
        synchronized (gSharedTempLock) {
            int nativeGetPointerCount = nativeGetPointerCount(this.mNativePtr);
            ensureSharedTempPointerCapacity(nativeGetPointerCount);
            android.view.MotionEvent.PointerProperties[] pointerPropertiesArr = gSharedTempPointerProperties;
            android.view.MotionEvent.PointerCoords[] pointerCoordsArr = gSharedTempPointerCoords;
            for (int i = 0; i < nativeGetPointerCount; i++) {
                nativeGetPointerProperties(this.mNativePtr, i, pointerPropertiesArr[i]);
                nativeGetPointerCoords(this.mNativePtr, i, Integer.MIN_VALUE, pointerCoordsArr[i]);
                pointerCoordsArr[i].x = clamp(pointerCoordsArr[i].x, f, f3);
                pointerCoordsArr[i].y = clamp(pointerCoordsArr[i].y, f2, f4);
            }
            obtain.initialize(nativeGetDeviceId(this.mNativePtr), nativeGetSource(this.mNativePtr), nativeGetDisplayId(this.mNativePtr), nativeGetAction(this.mNativePtr), nativeGetFlags(this.mNativePtr), nativeGetEdgeFlags(this.mNativePtr), nativeGetMetaState(this.mNativePtr), nativeGetButtonState(this.mNativePtr), nativeGetClassification(this.mNativePtr), nativeGetXOffset(this.mNativePtr), nativeGetYOffset(this.mNativePtr), nativeGetXPrecision(this.mNativePtr), nativeGetYPrecision(this.mNativePtr), nativeGetDownTimeNanos(this.mNativePtr), nativeGetEventTimeNanos(this.mNativePtr, Integer.MIN_VALUE), nativeGetPointerCount, pointerPropertiesArr, pointerCoordsArr);
        }
        return obtain;
    }

    public final int getPointerIdBits() {
        int nativeGetPointerCount = nativeGetPointerCount(this.mNativePtr);
        int i = 0;
        for (int i2 = 0; i2 < nativeGetPointerCount; i2++) {
            i |= 1 << nativeGetPointerId(this.mNativePtr, i2);
        }
        return i;
    }

    /* JADX WARN: Code restructure failed: missing block: B:45:0x0059, code lost:
    
        r27 = 2;
     */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0083  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final android.view.MotionEvent split(int i) {
        int i2;
        int i3;
        int nativeGetHistorySize;
        int i4;
        android.view.MotionEvent motionEvent;
        int i5;
        int i6;
        int i7;
        android.view.MotionEvent motionEvent2;
        android.view.MotionEvent motionEvent3 = this;
        android.view.MotionEvent obtain = obtain();
        synchronized (gSharedTempLock) {
            int nativeGetPointerCount = nativeGetPointerCount(motionEvent3.mNativePtr);
            ensureSharedTempPointerCapacity(nativeGetPointerCount);
            android.view.MotionEvent.PointerProperties[] pointerPropertiesArr = gSharedTempPointerProperties;
            android.view.MotionEvent.PointerCoords[] pointerCoordsArr = gSharedTempPointerCoords;
            int[] iArr = gSharedTempPointerIndexMap;
            int nativeGetAction = nativeGetAction(motionEvent3.mNativePtr);
            int i8 = nativeGetAction & 255;
            int i9 = (65280 & nativeGetAction) >> 8;
            int i10 = -1;
            int i11 = 0;
            for (int i12 = 0; i12 < nativeGetPointerCount; i12++) {
                nativeGetPointerProperties(motionEvent3.mNativePtr, i12, pointerPropertiesArr[i11]);
                if (((1 << pointerPropertiesArr[i11].id) & i) != 0) {
                    if (i12 == i9) {
                        i10 = i11;
                    }
                    iArr[i11] = i12;
                    i11++;
                }
            }
            if (i11 == 0) {
                throw new java.lang.IllegalArgumentException("idBits did not match any ids in the event");
            }
            if (i8 != 5 && i8 != 6) {
                i2 = nativeGetAction;
                nativeGetHistorySize = nativeGetHistorySize(motionEvent3.mNativePtr);
                i4 = 0;
                while (i4 <= nativeGetHistorySize) {
                    int i13 = i4 == nativeGetHistorySize ? Integer.MIN_VALUE : i4;
                    for (int i14 = 0; i14 < i11; i14++) {
                        nativeGetPointerCoords(motionEvent3.mNativePtr, iArr[i14], i13, pointerCoordsArr[i14]);
                    }
                    long nativeGetEventTimeNanos = nativeGetEventTimeNanos(motionEvent3.mNativePtr, i13);
                    if (i4 == 0) {
                        int nativeGetDeviceId = nativeGetDeviceId(motionEvent3.mNativePtr);
                        int nativeGetSource = nativeGetSource(motionEvent3.mNativePtr);
                        int nativeGetDisplayId = nativeGetDisplayId(motionEvent3.mNativePtr);
                        int nativeGetFlags = nativeGetFlags(motionEvent3.mNativePtr);
                        int nativeGetEdgeFlags = nativeGetEdgeFlags(motionEvent3.mNativePtr);
                        int nativeGetMetaState = nativeGetMetaState(motionEvent3.mNativePtr);
                        int nativeGetButtonState = nativeGetButtonState(motionEvent3.mNativePtr);
                        int nativeGetClassification = nativeGetClassification(motionEvent3.mNativePtr);
                        int i15 = i4;
                        float nativeGetXOffset = nativeGetXOffset(motionEvent3.mNativePtr);
                        int i16 = nativeGetHistorySize;
                        float nativeGetYOffset = nativeGetYOffset(motionEvent3.mNativePtr);
                        int i17 = i11;
                        float nativeGetXPrecision = nativeGetXPrecision(motionEvent3.mNativePtr);
                        float nativeGetYPrecision = nativeGetYPrecision(motionEvent3.mNativePtr);
                        long nativeGetDownTimeNanos = nativeGetDownTimeNanos(motionEvent3.mNativePtr);
                        i5 = i15;
                        i6 = i16;
                        i7 = i17;
                        motionEvent2 = obtain;
                        obtain.initialize(nativeGetDeviceId, nativeGetSource, nativeGetDisplayId, i2, nativeGetFlags, nativeGetEdgeFlags, nativeGetMetaState, nativeGetButtonState, nativeGetClassification, nativeGetXOffset, nativeGetYOffset, nativeGetXPrecision, nativeGetYPrecision, nativeGetDownTimeNanos, nativeGetEventTimeNanos, i17, pointerPropertiesArr, pointerCoordsArr);
                    } else {
                        i5 = i4;
                        i6 = nativeGetHistorySize;
                        i7 = i11;
                        motionEvent2 = obtain;
                        nativeAddBatch(motionEvent2.mNativePtr, nativeGetEventTimeNanos, pointerCoordsArr, 0);
                    }
                    i4 = i5 + 1;
                    obtain = motionEvent2;
                    nativeGetHistorySize = i6;
                    i11 = i7;
                    motionEvent3 = this;
                }
                motionEvent = obtain;
            }
            if (i11 == 1) {
                if (i8 == 5) {
                    i3 = 0;
                } else {
                    i3 = (getFlags() & 32) == 0 ? 1 : 3;
                }
                i2 = i3;
            } else {
                i2 = i8 | (i10 << 8);
            }
            nativeGetHistorySize = nativeGetHistorySize(motionEvent3.mNativePtr);
            i4 = 0;
            while (i4 <= nativeGetHistorySize) {
            }
            motionEvent = obtain;
        }
        return motionEvent;
    }

    private void updateCursorPosition() {
        if (getSource() != 8194) {
            setCursorPosition(Float.NaN, Float.NaN);
            return;
        }
        int pointerCount = getPointerCount();
        float f = 0.0f;
        float f2 = 0.0f;
        for (int i = 0; i < pointerCount; i++) {
            f += getX(i);
            f2 += getY(i);
        }
        float f3 = pointerCount;
        setCursorPosition(f / f3, f2 / f3);
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("MotionEvent { action=").append(actionToString(getAction()));
        appendUnless(android.media.AudioSystem.LEGACY_REMOTE_SUBMIX_ADDRESS, sb, ", actionButton=", buttonStateToString(getActionButton()));
        int pointerCount = getPointerCount();
        for (int i = 0; i < pointerCount; i++) {
            appendUnless(java.lang.Integer.valueOf(i), sb, ", id[" + i + "]=", java.lang.Integer.valueOf(getPointerId(i)));
            float x = getX(i);
            float y = getY(i);
            sb.append(", x[").append(i).append("]=").append(x);
            sb.append(", y[").append(i).append("]=").append(y);
            appendUnless(TOOL_TYPE_SYMBOLIC_NAMES.get(1), sb, ", toolType[" + i + "]=", toolTypeToString(getToolType(i)));
        }
        appendUnless(android.media.AudioSystem.LEGACY_REMOTE_SUBMIX_ADDRESS, sb, ", buttonState=", buttonStateToString(getButtonState()));
        appendUnless(classificationToString(0), sb, ", classification=", classificationToString(getClassification()));
        appendUnless(android.media.AudioSystem.LEGACY_REMOTE_SUBMIX_ADDRESS, sb, ", metaState=", android.view.KeyEvent.metaStateToString(getMetaState()));
        appendUnless(android.media.AudioSystem.LEGACY_REMOTE_SUBMIX_ADDRESS, sb, ", flags=0x", java.lang.Integer.toHexString(getFlags()));
        appendUnless(android.media.AudioSystem.LEGACY_REMOTE_SUBMIX_ADDRESS, sb, ", edgeFlags=0x", java.lang.Integer.toHexString(getEdgeFlags()));
        appendUnless(1, sb, ", pointerCount=", java.lang.Integer.valueOf(pointerCount));
        appendUnless(0, sb, ", historySize=", java.lang.Integer.valueOf(getHistorySize()));
        sb.append(", eventTime=").append(getEventTime());
        sb.append(", downTime=").append(getDownTime());
        sb.append(", deviceId=").append(getDeviceId());
        sb.append(", source=0x").append(java.lang.Integer.toHexString(getSource()));
        sb.append(", displayId=").append(getDisplayId());
        sb.append(", eventId=").append(getId());
        sb.append(" }");
        return sb.toString();
    }

    private static <T> void appendUnless(T t, java.lang.StringBuilder sb, java.lang.String str, T t2) {
        sb.append(str).append(t2);
    }

    public static java.lang.String actionToString(int i) {
        switch (i) {
            case 0:
                return "ACTION_DOWN";
            case 1:
                return "ACTION_UP";
            case 2:
                return "ACTION_MOVE";
            case 3:
                return "ACTION_CANCEL";
            case 4:
                return "ACTION_OUTSIDE";
            case 5:
            case 6:
            default:
                int i2 = (65280 & i) >> 8;
                switch (i & 255) {
                    case 5:
                        return "ACTION_POINTER_DOWN(" + i2 + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
                    case 6:
                        return "ACTION_POINTER_UP(" + i2 + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
                    default:
                        return java.lang.Integer.toString(i);
                }
            case 7:
                return "ACTION_HOVER_MOVE";
            case 8:
                return "ACTION_SCROLL";
            case 9:
                return "ACTION_HOVER_ENTER";
            case 10:
                return "ACTION_HOVER_EXIT";
            case 11:
                return "ACTION_BUTTON_PRESS";
            case 12:
                return "ACTION_BUTTON_RELEASE";
        }
    }

    public static java.lang.String axisToString(int i) {
        java.lang.String nativeAxisToString = nativeAxisToString(i);
        return nativeAxisToString != null ? LABEL_PREFIX + nativeAxisToString : java.lang.Integer.toString(i);
    }

    public static int axisFromString(java.lang.String str) {
        int nativeAxisFromString;
        if (str.startsWith(LABEL_PREFIX) && (nativeAxisFromString = nativeAxisFromString((str = str.substring(LABEL_PREFIX.length())))) >= 0) {
            return nativeAxisFromString;
        }
        try {
            return java.lang.Integer.parseInt(str, 10);
        } catch (java.lang.NumberFormatException e) {
            return -1;
        }
    }

    public static java.lang.String buttonStateToString(int i) {
        if (i == 0) {
            return android.media.AudioSystem.LEGACY_REMOTE_SUBMIX_ADDRESS;
        }
        java.lang.StringBuilder sb = null;
        int i2 = 0;
        while (i != 0) {
            boolean z = (i & 1) != 0;
            i >>>= 1;
            if (z) {
                java.lang.String str = BUTTON_SYMBOLIC_NAMES[i2];
                if (sb == null) {
                    if (i == 0) {
                        return str;
                    }
                    sb = new java.lang.StringBuilder(str);
                } else {
                    sb.append('|');
                    sb.append(str);
                }
            }
            i2++;
        }
        return sb.toString();
    }

    public static java.lang.String classificationToString(int i) {
        switch (i) {
            case 0:
                return android.security.keystore.KeyProperties.DIGEST_NONE;
            case 1:
                return "AMBIGUOUS_GESTURE";
            case 2:
                return "DEEP_PRESS";
            case 3:
                return "TWO_FINGER_SWIPE";
            case 4:
                return "MULTI_FINGER_SWIPE";
            default:
                return "UNKNOWN";
        }
    }

    public static java.lang.String toolTypeToString(int i) {
        java.lang.String str = TOOL_TYPE_SYMBOLIC_NAMES.get(i);
        return str != null ? str : java.lang.Integer.toString(i);
    }

    public final boolean isButtonPressed(int i) {
        return i != 0 && (getButtonState() & i) == i;
    }

    public int getSurfaceRotation() {
        return nativeGetSurfaceRotation(this.mNativePtr);
    }

    public static android.graphics.Matrix createRotateMatrix(int i, int i2, int i3) {
        float[] fArr;
        if (i == 0) {
            return new android.graphics.Matrix(android.graphics.Matrix.IDENTITY_MATRIX);
        }
        if (i == 1) {
            fArr = new float[]{0.0f, 1.0f, 0.0f, -1.0f, 0.0f, i3, 0.0f, 0.0f, 1.0f};
        } else if (i == 2) {
            fArr = new float[]{-1.0f, 0.0f, i2, 0.0f, -1.0f, i3, 0.0f, 0.0f, 1.0f};
        } else if (i != 3) {
            fArr = null;
        } else {
            fArr = new float[]{0.0f, -1.0f, i2, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f};
        }
        android.graphics.Matrix matrix = new android.graphics.Matrix();
        matrix.setValues(fArr);
        return matrix;
    }

    public static android.view.MotionEvent createFromParcelBody(android.os.Parcel parcel) {
        android.view.MotionEvent obtain = obtain();
        obtain.mNativePtr = nativeReadFromParcel(obtain.mNativePtr, parcel);
        return obtain;
    }

    @Override // android.view.InputEvent
    public final void cancel() {
        setCanceled(true);
        setAction(3);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(1);
        nativeWriteToParcel(this.mNativePtr, parcel);
    }

    public float getXDispatchLocation(int i) {
        if (isFromSource(8194)) {
            float xCursorPosition = getXCursorPosition();
            if (xCursorPosition != Float.NaN) {
                return xCursorPosition;
            }
        }
        return getX(i);
    }

    public float getYDispatchLocation(int i) {
        if (isFromSource(8194)) {
            float yCursorPosition = getYCursorPosition();
            if (yCursorPosition != Float.NaN) {
                return yCursorPosition;
            }
        }
        return getY(i);
    }

    public static final class PointerCoords {
        private static final int INITIAL_PACKED_AXIS_VALUES = 8;
        public boolean isResampled;
        private long mPackedAxisBits;
        private float[] mPackedAxisValues;
        public float orientation;
        public float pressure;
        public float relativeX;
        public float relativeY;
        public float size;
        public float toolMajor;
        public float toolMinor;
        public float touchMajor;
        public float touchMinor;
        public float x;
        public float y;

        public PointerCoords() {
        }

        public PointerCoords(android.view.MotionEvent.PointerCoords pointerCoords) {
            copyFrom(pointerCoords);
        }

        public static android.view.MotionEvent.PointerCoords[] createArray(int i) {
            android.view.MotionEvent.PointerCoords[] pointerCoordsArr = new android.view.MotionEvent.PointerCoords[i];
            for (int i2 = 0; i2 < i; i2++) {
                pointerCoordsArr[i2] = new android.view.MotionEvent.PointerCoords();
            }
            return pointerCoordsArr;
        }

        public boolean isResampled() {
            return this.isResampled;
        }

        public void clear() {
            this.mPackedAxisBits = 0L;
            this.x = 0.0f;
            this.y = 0.0f;
            this.pressure = 0.0f;
            this.size = 0.0f;
            this.touchMajor = 0.0f;
            this.touchMinor = 0.0f;
            this.toolMajor = 0.0f;
            this.toolMinor = 0.0f;
            this.orientation = 0.0f;
            this.relativeX = 0.0f;
            this.relativeY = 0.0f;
            this.isResampled = false;
        }

        public void copyFrom(android.view.MotionEvent.PointerCoords pointerCoords) {
            long j = pointerCoords.mPackedAxisBits;
            this.mPackedAxisBits = j;
            if (j != 0) {
                float[] fArr = pointerCoords.mPackedAxisValues;
                int bitCount = java.lang.Long.bitCount(j);
                float[] fArr2 = this.mPackedAxisValues;
                if (fArr2 == null || bitCount > fArr2.length) {
                    fArr2 = new float[fArr.length];
                    this.mPackedAxisValues = fArr2;
                }
                java.lang.System.arraycopy(fArr, 0, fArr2, 0, bitCount);
            }
            this.x = pointerCoords.x;
            this.y = pointerCoords.y;
            this.pressure = pointerCoords.pressure;
            this.size = pointerCoords.size;
            this.touchMajor = pointerCoords.touchMajor;
            this.touchMinor = pointerCoords.touchMinor;
            this.toolMajor = pointerCoords.toolMajor;
            this.toolMinor = pointerCoords.toolMinor;
            this.orientation = pointerCoords.orientation;
            this.relativeX = pointerCoords.relativeX;
            this.relativeY = pointerCoords.relativeY;
            this.isResampled = pointerCoords.isResampled;
        }

        public float getAxisValue(int i) {
            switch (i) {
                case 0:
                    return this.x;
                case 1:
                    return this.y;
                case 2:
                    return this.pressure;
                case 3:
                    return this.size;
                case 4:
                    return this.touchMajor;
                case 5:
                    return this.touchMinor;
                case 6:
                    return this.toolMajor;
                case 7:
                    return this.toolMinor;
                case 8:
                    return this.orientation;
                case 27:
                    return this.relativeX;
                case 28:
                    return this.relativeY;
                default:
                    if (i < 0 || i > 63) {
                        throw new java.lang.IllegalArgumentException("Axis out of range.");
                    }
                    long j = this.mPackedAxisBits;
                    if ((((-9223372036854775808) >>> i) & j) == 0) {
                        return 0.0f;
                    }
                    return this.mPackedAxisValues[java.lang.Long.bitCount(j & (~((-1) >>> i)))];
            }
        }

        public void setAxisValue(int i, float f) {
            switch (i) {
                case 0:
                    this.x = f;
                    return;
                case 1:
                    this.y = f;
                    return;
                case 2:
                    this.pressure = f;
                    return;
                case 3:
                    this.size = f;
                    return;
                case 4:
                    this.touchMajor = f;
                    return;
                case 5:
                    this.touchMinor = f;
                    return;
                case 6:
                    this.toolMajor = f;
                    return;
                case 7:
                    this.toolMinor = f;
                    return;
                case 8:
                    this.orientation = f;
                    return;
                case 27:
                    this.relativeX = f;
                    return;
                case 28:
                    this.relativeY = f;
                    return;
                default:
                    if (i < 0 || i > 63) {
                        throw new java.lang.IllegalArgumentException("Axis out of range.");
                    }
                    long j = this.mPackedAxisBits;
                    long j2 = (-9223372036854775808) >>> i;
                    int bitCount = java.lang.Long.bitCount((~((-1) >>> i)) & j);
                    float[] fArr = this.mPackedAxisValues;
                    if ((j & j2) == 0) {
                        if (fArr == null) {
                            fArr = new float[8];
                            this.mPackedAxisValues = fArr;
                        } else {
                            int bitCount2 = java.lang.Long.bitCount(j);
                            if (bitCount2 < fArr.length) {
                                if (bitCount != bitCount2) {
                                    java.lang.System.arraycopy(fArr, bitCount, fArr, bitCount + 1, bitCount2 - bitCount);
                                }
                            } else {
                                float[] fArr2 = new float[bitCount2 * 2];
                                java.lang.System.arraycopy(fArr, 0, fArr2, 0, bitCount);
                                java.lang.System.arraycopy(fArr, bitCount, fArr2, bitCount + 1, bitCount2 - bitCount);
                                this.mPackedAxisValues = fArr2;
                                fArr = fArr2;
                            }
                        }
                        this.mPackedAxisBits = j | j2;
                    }
                    fArr[bitCount] = f;
                    return;
            }
        }
    }

    public static final class PointerProperties {
        public int id;
        public int toolType;

        public PointerProperties() {
            clear();
        }

        public PointerProperties(android.view.MotionEvent.PointerProperties pointerProperties) {
            copyFrom(pointerProperties);
        }

        public static android.view.MotionEvent.PointerProperties[] createArray(int i) {
            android.view.MotionEvent.PointerProperties[] pointerPropertiesArr = new android.view.MotionEvent.PointerProperties[i];
            for (int i2 = 0; i2 < i; i2++) {
                pointerPropertiesArr[i2] = new android.view.MotionEvent.PointerProperties();
            }
            return pointerPropertiesArr;
        }

        public void clear() {
            this.id = -1;
            this.toolType = 0;
        }

        public void copyFrom(android.view.MotionEvent.PointerProperties pointerProperties) {
            this.id = pointerProperties.id;
            this.toolType = pointerProperties.toolType;
        }

        public boolean equals(java.lang.Object obj) {
            if (obj instanceof android.view.MotionEvent.PointerProperties) {
                return equals((android.view.MotionEvent.PointerProperties) obj);
            }
            return false;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean equals(android.view.MotionEvent.PointerProperties pointerProperties) {
            return pointerProperties != null && this.id == pointerProperties.id && this.toolType == pointerProperties.toolType;
        }

        public int hashCode() {
            return this.id | (this.toolType << 8);
        }
    }
}
