package android.view.translation;

@android.annotation.SystemApi
/* loaded from: classes4.dex */
public final class UiTranslationSpec implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.view.translation.UiTranslationSpec> CREATOR = new android.os.Parcelable.Creator<android.view.translation.UiTranslationSpec>() { // from class: android.view.translation.UiTranslationSpec.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.translation.UiTranslationSpec[] newArray(int i) {
            return new android.view.translation.UiTranslationSpec[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.translation.UiTranslationSpec createFromParcel(android.os.Parcel parcel) {
            return new android.view.translation.UiTranslationSpec(parcel);
        }
    };
    private boolean mShouldPadContentForCompat;

    public boolean shouldPadContentForCompat() {
        return this.mShouldPadContentForCompat;
    }

    UiTranslationSpec(boolean z) {
        this.mShouldPadContentForCompat = false;
        this.mShouldPadContentForCompat = z;
    }

    public java.lang.String toString() {
        return "UiTranslationSpec { shouldPadContentForCompat = " + this.mShouldPadContentForCompat + " }";
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass() && this.mShouldPadContentForCompat == ((android.view.translation.UiTranslationSpec) obj).mShouldPadContentForCompat) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return 31 + java.lang.Boolean.hashCode(this.mShouldPadContentForCompat);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeByte(this.mShouldPadContentForCompat ? (byte) 1 : (byte) 0);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    UiTranslationSpec(android.os.Parcel parcel) {
        this.mShouldPadContentForCompat = false;
        this.mShouldPadContentForCompat = (parcel.readByte() & 1) != 0;
    }

    public static final class Builder {
        private long mBuilderFieldsSet = 0;
        private boolean mShouldPadContentForCompat;

        public android.view.translation.UiTranslationSpec.Builder setShouldPadContentForCompat(boolean z) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 1;
            this.mShouldPadContentForCompat = z;
            return this;
        }

        public android.view.translation.UiTranslationSpec build() {
            checkNotUsed();
            this.mBuilderFieldsSet |= 2;
            if ((this.mBuilderFieldsSet & 1) == 0) {
                this.mShouldPadContentForCompat = false;
            }
            return new android.view.translation.UiTranslationSpec(this.mShouldPadContentForCompat);
        }

        private void checkNotUsed() {
            if ((this.mBuilderFieldsSet & 2) != 0) {
                throw new java.lang.IllegalStateException("This Builder should not be reused. Use a new Builder instance instead");
            }
        }
    }

    @java.lang.Deprecated
    private void __metadata() {
    }
}
