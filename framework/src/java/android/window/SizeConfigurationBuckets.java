package android.window;

/* loaded from: classes4.dex */
public final class SizeConfigurationBuckets implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.window.SizeConfigurationBuckets> CREATOR = new android.os.Parcelable.Creator<android.window.SizeConfigurationBuckets>() { // from class: android.window.SizeConfigurationBuckets.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.window.SizeConfigurationBuckets[] newArray(int i) {
            return new android.window.SizeConfigurationBuckets[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.window.SizeConfigurationBuckets createFromParcel(android.os.Parcel parcel) {
            return new android.window.SizeConfigurationBuckets(parcel);
        }
    };
    private final int[] mHorizontal;
    private final boolean mScreenLayoutLongSet;
    private final int[] mScreenLayoutSize;
    private final int[] mSmallest;
    private final int[] mVertical;

    public SizeConfigurationBuckets(android.content.res.Configuration[] configurationArr) {
        android.util.SparseIntArray sparseIntArray = new android.util.SparseIntArray();
        android.util.SparseIntArray sparseIntArray2 = new android.util.SparseIntArray();
        android.util.SparseIntArray sparseIntArray3 = new android.util.SparseIntArray();
        android.util.SparseIntArray sparseIntArray4 = new android.util.SparseIntArray();
        boolean z = false;
        for (int length = configurationArr.length - 1; length >= 0; length--) {
            android.content.res.Configuration configuration = configurationArr[length];
            if (configuration.screenHeightDp != 0) {
                sparseIntArray2.put(configuration.screenHeightDp, 0);
            }
            if (configuration.screenWidthDp != 0) {
                sparseIntArray.put(configuration.screenWidthDp, 0);
            }
            if (configuration.smallestScreenWidthDp != 0) {
                sparseIntArray3.put(configuration.smallestScreenWidthDp, 0);
            }
            int i = configuration.screenLayout & 15;
            if (i != 0) {
                sparseIntArray4.put(i, 0);
            }
            if (!z && (configuration.screenLayout & 48) != 0) {
                z = true;
            }
        }
        this.mHorizontal = sparseIntArray.copyKeys();
        this.mVertical = sparseIntArray2.copyKeys();
        this.mSmallest = sparseIntArray3.copyKeys();
        this.mScreenLayoutSize = sparseIntArray4.copyKeys();
        this.mScreenLayoutLongSet = z;
    }

    public static int filterDiff(int i, android.content.res.Configuration configuration, android.content.res.Configuration configuration2, android.window.SizeConfigurationBuckets sizeConfigurationBuckets) {
        if (sizeConfigurationBuckets == null) {
            return i;
        }
        boolean areNonSizeLayoutFieldsUnchanged = areNonSizeLayoutFieldsUnchanged(configuration.screenLayout, configuration2.screenLayout);
        if ((i & 1024) != 0) {
            if (!(sizeConfigurationBuckets.crossesHorizontalSizeThreshold(configuration.screenWidthDp, configuration2.screenWidthDp) || sizeConfigurationBuckets.crossesVerticalSizeThreshold(configuration.screenHeightDp, configuration2.screenHeightDp))) {
                i &= -1025;
            }
        }
        if ((i & 2048) != 0 && !sizeConfigurationBuckets.crossesSmallestSizeThreshold(configuration.smallestScreenWidthDp, configuration2.smallestScreenWidthDp)) {
            i &= -2049;
        }
        if ((i & 256) != 0 && areNonSizeLayoutFieldsUnchanged && !sizeConfigurationBuckets.crossesScreenLayoutSizeThreshold(configuration, configuration2) && !sizeConfigurationBuckets.crossesScreenLayoutLongThreshold(configuration.screenLayout, configuration2.screenLayout)) {
            return i & (-257);
        }
        return i;
    }

    private boolean crossesHorizontalSizeThreshold(int i, int i2) {
        return crossesSizeThreshold(this.mHorizontal, i, i2);
    }

    private boolean crossesVerticalSizeThreshold(int i, int i2) {
        return crossesSizeThreshold(this.mVertical, i, i2);
    }

    private boolean crossesSmallestSizeThreshold(int i, int i2) {
        return crossesSizeThreshold(this.mSmallest, i, i2);
    }

    public boolean crossesScreenLayoutSizeThreshold(android.content.res.Configuration configuration, android.content.res.Configuration configuration2) {
        if ((configuration.screenLayout & 15) == (configuration2.screenLayout & 15)) {
            return false;
        }
        if (!configuration2.isLayoutSizeAtLeast(configuration.screenLayout & 15)) {
            return true;
        }
        if (this.mScreenLayoutSize != null) {
            for (int i : this.mScreenLayoutSize) {
                if (configuration.isLayoutSizeAtLeast(i) != configuration2.isLayoutSizeAtLeast(i)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean crossesScreenLayoutLongThreshold(int i, int i2) {
        return this.mScreenLayoutLongSet && (i & 48) != (i2 & 48);
    }

    public static boolean areNonSizeLayoutFieldsUnchanged(int i, int i2) {
        return (i & 268436416) == (i2 & 268436416);
    }

    public static boolean crossesSizeThreshold(int[] iArr, int i, int i2) {
        if (iArr == null) {
            return false;
        }
        for (int length = iArr.length - 1; length >= 0; length--) {
            int i3 = iArr[length];
            if ((i < i3 && i2 >= i3) || (i >= i3 && i2 < i3)) {
                return true;
            }
        }
        return false;
    }

    public java.lang.String toString() {
        return java.util.Arrays.toString(this.mHorizontal) + " " + java.util.Arrays.toString(this.mVertical) + " " + java.util.Arrays.toString(this.mSmallest) + " " + java.util.Arrays.toString(this.mScreenLayoutSize) + " " + this.mScreenLayoutLongSet;
    }

    public SizeConfigurationBuckets(int[] iArr, int[] iArr2, int[] iArr3, int[] iArr4, boolean z) {
        this.mHorizontal = iArr;
        this.mVertical = iArr2;
        this.mSmallest = iArr3;
        this.mScreenLayoutSize = iArr4;
        this.mScreenLayoutLongSet = z;
    }

    public int[] getHorizontal() {
        return this.mHorizontal;
    }

    public int[] getVertical() {
        return this.mVertical;
    }

    public int[] getSmallest() {
        return this.mSmallest;
    }

    public int[] getScreenLayoutSize() {
        return this.mScreenLayoutSize;
    }

    public boolean isScreenLayoutLongSet() {
        return this.mScreenLayoutLongSet;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        byte b = this.mScreenLayoutLongSet ? (byte) 16 : (byte) 0;
        if (this.mHorizontal != null) {
            b = (byte) (b | 1);
        }
        if (this.mVertical != null) {
            b = (byte) (b | 2);
        }
        if (this.mSmallest != null) {
            b = (byte) (b | 4);
        }
        if (this.mScreenLayoutSize != null) {
            b = (byte) (b | 8);
        }
        parcel.writeByte(b);
        if (this.mHorizontal != null) {
            parcel.writeIntArray(this.mHorizontal);
        }
        if (this.mVertical != null) {
            parcel.writeIntArray(this.mVertical);
        }
        if (this.mSmallest != null) {
            parcel.writeIntArray(this.mSmallest);
        }
        if (this.mScreenLayoutSize != null) {
            parcel.writeIntArray(this.mScreenLayoutSize);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    SizeConfigurationBuckets(android.os.Parcel parcel) {
        byte readByte = parcel.readByte();
        boolean z = (readByte & 16) != 0;
        int[] createIntArray = (readByte & 1) == 0 ? null : parcel.createIntArray();
        int[] createIntArray2 = (readByte & 2) == 0 ? null : parcel.createIntArray();
        int[] createIntArray3 = (readByte & 4) == 0 ? null : parcel.createIntArray();
        int[] createIntArray4 = (readByte & 8) != 0 ? parcel.createIntArray() : null;
        this.mHorizontal = createIntArray;
        this.mVertical = createIntArray2;
        this.mSmallest = createIntArray3;
        this.mScreenLayoutSize = createIntArray4;
        this.mScreenLayoutLongSet = z;
    }

    @java.lang.Deprecated
    private void __metadata() {
    }
}
