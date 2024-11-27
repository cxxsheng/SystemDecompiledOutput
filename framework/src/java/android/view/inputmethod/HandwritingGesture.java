package android.view.inputmethod;

/* loaded from: classes4.dex */
public abstract class HandwritingGesture {
    public static final int GESTURE_TYPE_DELETE = 4;
    public static final int GESTURE_TYPE_DELETE_RANGE = 64;
    public static final int GESTURE_TYPE_INSERT = 2;
    public static final int GESTURE_TYPE_INSERT_MODE = 128;
    public static final int GESTURE_TYPE_JOIN_OR_SPLIT = 16;
    public static final int GESTURE_TYPE_NONE = 0;
    public static final int GESTURE_TYPE_REMOVE_SPACE = 8;
    public static final int GESTURE_TYPE_SELECT = 1;
    public static final int GESTURE_TYPE_SELECT_RANGE = 32;
    public static final int GRANULARITY_CHARACTER = 2;
    static final int GRANULARITY_UNDEFINED = 0;
    public static final int GRANULARITY_WORD = 1;
    java.lang.String mFallbackText;
    int mType = 0;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    @interface GestureType {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface GestureTypeFlags {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    @interface Granularity {
    }

    HandwritingGesture() {
    }

    public final int getGestureType() {
        return this.mType;
    }

    public final java.lang.String getFallbackText() {
        return this.mFallbackText;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final byte[] toByteArray() {
        android.os.Parcel parcel;
        if (!(this instanceof android.os.Parcelable)) {
            throw new java.lang.UnsupportedOperationException(getClass() + " is not Parcelable");
        }
        if ((((android.os.Parcelable) this).describeContents() & 1) != 0) {
            throw new java.lang.UnsupportedOperationException("Gesture that contains FD is not supported");
        }
        try {
            parcel = android.os.Parcel.obtain();
        } catch (java.lang.Throwable th) {
            th = th;
            parcel = null;
        }
        try {
            android.view.inputmethod.ParcelableHandwritingGesture.of(this).writeToParcel(parcel, 0);
            byte[] marshall = parcel.marshall();
            if (parcel != null) {
                parcel.recycle();
            }
            return marshall;
        } catch (java.lang.Throwable th2) {
            th = th2;
            if (parcel != null) {
                parcel.recycle();
            }
            throw th;
        }
    }

    public static android.view.inputmethod.HandwritingGesture fromByteArray(byte[] bArr) {
        android.os.Parcel parcel;
        try {
            parcel = android.os.Parcel.obtain();
            try {
                parcel.unmarshall(bArr, 0, bArr.length);
                parcel.setDataPosition(0);
                android.view.inputmethod.HandwritingGesture handwritingGesture = android.view.inputmethod.ParcelableHandwritingGesture.CREATOR.createFromParcel(parcel).get();
                if (parcel != null) {
                    parcel.recycle();
                }
                return handwritingGesture;
            } catch (java.lang.Throwable th) {
                th = th;
                if (parcel != null) {
                    parcel.recycle();
                }
                throw th;
            }
        } catch (java.lang.Throwable th2) {
            th = th2;
            parcel = null;
        }
    }
}
