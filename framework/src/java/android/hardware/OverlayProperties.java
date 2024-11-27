package android.hardware;

/* loaded from: classes.dex */
public final class OverlayProperties implements android.os.Parcelable {
    private static android.hardware.OverlayProperties sDefaultOverlayProperties;
    private java.lang.Runnable mCloser;
    private long mNativeObject;
    private static final libcore.util.NativeAllocationRegistry sRegistry = libcore.util.NativeAllocationRegistry.createMalloced(android.hardware.OverlayProperties.class.getClassLoader(), nGetDestructor());
    public static final android.os.Parcelable.Creator<android.hardware.OverlayProperties> CREATOR = new android.os.Parcelable.Creator<android.hardware.OverlayProperties>() { // from class: android.hardware.OverlayProperties.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.OverlayProperties createFromParcel(android.os.Parcel parcel) {
            if (parcel.readInt() != 0) {
                return new android.hardware.OverlayProperties(android.hardware.OverlayProperties.nReadOverlayPropertiesFromParcel(parcel));
            }
            return null;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.OverlayProperties[] newArray(int i) {
            return new android.hardware.OverlayProperties[i];
        }
    };

    private static native long nCreateDefault();

    private static native long nGetDestructor();

    private static native boolean nIsCombinationSupported(long j, int i, int i2);

    /* JADX INFO: Access modifiers changed from: private */
    public static native long nReadOverlayPropertiesFromParcel(android.os.Parcel parcel);

    private static native boolean nSupportFp16ForHdr(long j);

    private static native boolean nSupportMixedColorSpaces(long j);

    private static native void nWriteOverlayPropertiesToParcel(long j, android.os.Parcel parcel);

    private OverlayProperties(long j) {
        if (j != 0) {
            this.mCloser = sRegistry.registerNativeAllocation(this, j);
        }
        this.mNativeObject = j;
    }

    public static android.hardware.OverlayProperties getDefault() {
        if (sDefaultOverlayProperties == null) {
            sDefaultOverlayProperties = new android.hardware.OverlayProperties(nCreateDefault());
        }
        return sDefaultOverlayProperties;
    }

    public boolean isFp16SupportedForHdr() {
        if (this.mNativeObject == 0) {
            return false;
        }
        return nIsCombinationSupported(this.mNativeObject, 411107328, 22);
    }

    public boolean isCombinationSupported(int i, int i2) {
        if (this.mNativeObject == 0) {
            return false;
        }
        return nIsCombinationSupported(this.mNativeObject, i, i2);
    }

    public boolean isMixedColorSpacesSupported() {
        if (this.mNativeObject == 0) {
            return false;
        }
        return nSupportMixedColorSpaces(this.mNativeObject);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        if (this.mNativeObject == 0) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(1);
            nWriteOverlayPropertiesToParcel(this.mNativeObject, parcel);
        }
    }
}
