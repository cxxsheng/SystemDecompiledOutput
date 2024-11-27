package android.view;

/* loaded from: classes4.dex */
public class PrivacyIndicatorBounds implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.view.PrivacyIndicatorBounds> CREATOR = new android.os.Parcelable.Creator<android.view.PrivacyIndicatorBounds>() { // from class: android.view.PrivacyIndicatorBounds.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.PrivacyIndicatorBounds[] newArray(int i) {
            return new android.view.PrivacyIndicatorBounds[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.PrivacyIndicatorBounds createFromParcel(android.os.Parcel parcel) {
            return new android.view.PrivacyIndicatorBounds(parcel);
        }
    };
    private final int mRotation;
    private final android.graphics.Rect[] mStaticBounds;

    public PrivacyIndicatorBounds() {
        this.mStaticBounds = new android.graphics.Rect[4];
        this.mRotation = 0;
    }

    public PrivacyIndicatorBounds(android.graphics.Rect[] rectArr, int i) {
        this.mStaticBounds = rectArr;
        this.mRotation = i;
    }

    public android.view.PrivacyIndicatorBounds updateStaticBounds(android.graphics.Rect[] rectArr) {
        return new android.view.PrivacyIndicatorBounds(rectArr, this.mRotation);
    }

    public android.view.PrivacyIndicatorBounds updateBoundsForRotation(android.graphics.Rect rect, int i) {
        if (i >= this.mStaticBounds.length || i < 0) {
            return this;
        }
        android.graphics.Rect[] rectArr = (android.graphics.Rect[]) com.android.internal.util.ArrayUtils.cloneOrNull(this.mStaticBounds);
        rectArr[i] = rect;
        return updateStaticBounds(rectArr);
    }

    public android.view.PrivacyIndicatorBounds inset(int i, int i2, int i3, int i4) {
        if (i == 0 && i2 == 0 && i3 == 0 && i4 == 0) {
            return this;
        }
        android.graphics.Rect[] rectArr = new android.graphics.Rect[this.mStaticBounds.length];
        for (int i5 = 0; i5 < this.mStaticBounds.length; i5++) {
            rectArr[i5] = insetRect(this.mStaticBounds[i5], i, i2, i3, i4);
        }
        return updateStaticBounds(rectArr);
    }

    private static android.graphics.Rect insetRect(android.graphics.Rect rect, int i, int i2, int i3, int i4) {
        if (rect == null) {
            return null;
        }
        int max = java.lang.Math.max(0, rect.left - i);
        int max2 = java.lang.Math.max(0, rect.top - i2);
        return new android.graphics.Rect(max, max2, java.lang.Math.max(max, rect.right - i3), java.lang.Math.max(max2, rect.bottom - i4));
    }

    public android.view.PrivacyIndicatorBounds rotate(int i) {
        if (i == 0) {
            return this;
        }
        return new android.view.PrivacyIndicatorBounds(this.mStaticBounds, i);
    }

    public android.view.PrivacyIndicatorBounds scale(float f) {
        if (f == 1.0f) {
            return this;
        }
        android.graphics.Rect[] rectArr = new android.graphics.Rect[this.mStaticBounds.length];
        for (int i = 0; i < this.mStaticBounds.length; i++) {
            rectArr[i] = scaleRect(this.mStaticBounds[i], f);
        }
        return new android.view.PrivacyIndicatorBounds(rectArr, this.mRotation);
    }

    private static android.graphics.Rect scaleRect(android.graphics.Rect rect, float f) {
        if (rect == null) {
            return null;
        }
        android.graphics.Rect rect2 = new android.graphics.Rect(rect);
        rect2.scale(f);
        return rect2;
    }

    public android.graphics.Rect getStaticPrivacyIndicatorBounds() {
        return this.mStaticBounds[this.mRotation];
    }

    public java.lang.String toString() {
        return "PrivacyIndicatorBounds {static bounds=" + getStaticPrivacyIndicatorBounds() + " rotation=" + this.mRotation + "}";
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.view.PrivacyIndicatorBounds privacyIndicatorBounds = (android.view.PrivacyIndicatorBounds) obj;
        if (java.util.Arrays.equals(this.mStaticBounds, privacyIndicatorBounds.mStaticBounds) && this.mRotation == privacyIndicatorBounds.mRotation) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return ((java.util.Arrays.hashCode(this.mStaticBounds) + 31) * 31) + this.mRotation;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeTypedArray(this.mStaticBounds, i);
        parcel.writeInt(this.mRotation);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    protected PrivacyIndicatorBounds(android.os.Parcel parcel) {
        android.graphics.Rect[] rectArr = (android.graphics.Rect[]) parcel.createTypedArray(android.graphics.Rect.CREATOR);
        int readInt = parcel.readInt();
        this.mStaticBounds = rectArr;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mStaticBounds);
        this.mRotation = readInt;
    }

    @java.lang.Deprecated
    private void __metadata() {
    }
}
