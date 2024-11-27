package android.app.smartspace.uitemplatedata;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class Icon implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.smartspace.uitemplatedata.Icon> CREATOR = new android.os.Parcelable.Creator<android.app.smartspace.uitemplatedata.Icon>() { // from class: android.app.smartspace.uitemplatedata.Icon.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.smartspace.uitemplatedata.Icon createFromParcel(android.os.Parcel parcel) {
            return new android.app.smartspace.uitemplatedata.Icon(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.smartspace.uitemplatedata.Icon[] newArray(int i) {
            return new android.app.smartspace.uitemplatedata.Icon[i];
        }
    };
    private final java.lang.CharSequence mContentDescription;
    private final android.graphics.drawable.Icon mIcon;
    private final boolean mShouldTint;

    Icon(android.os.Parcel parcel) {
        this.mIcon = (android.graphics.drawable.Icon) parcel.readTypedObject(android.graphics.drawable.Icon.CREATOR);
        this.mContentDescription = android.text.TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.mShouldTint = parcel.readBoolean();
    }

    private Icon(android.graphics.drawable.Icon icon, java.lang.CharSequence charSequence, boolean z) {
        this.mIcon = icon;
        this.mContentDescription = charSequence;
        this.mShouldTint = z;
    }

    public android.graphics.drawable.Icon getIcon() {
        return this.mIcon;
    }

    public java.lang.CharSequence getContentDescription() {
        return this.mContentDescription;
    }

    public boolean shouldTint() {
        return this.mShouldTint;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof android.app.smartspace.uitemplatedata.Icon)) {
            return false;
        }
        android.app.smartspace.uitemplatedata.Icon icon = (android.app.smartspace.uitemplatedata.Icon) obj;
        return this.mIcon.toString().equals(icon.mIcon.toString()) && android.app.smartspace.SmartspaceUtils.isEqual(this.mContentDescription, icon.mContentDescription) && this.mShouldTint == icon.mShouldTint;
    }

    public int hashCode() {
        return java.util.Objects.hash(this.mIcon.toString(), this.mContentDescription, java.lang.Boolean.valueOf(this.mShouldTint));
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeTypedObject(this.mIcon, i);
        android.text.TextUtils.writeToParcel(this.mContentDescription, parcel, i);
        parcel.writeBoolean(this.mShouldTint);
    }

    public java.lang.String toString() {
        return "SmartspaceIcon{mIcon=" + this.mIcon + ", mContentDescription=" + ((java.lang.Object) this.mContentDescription) + ", mShouldTint=" + this.mShouldTint + '}';
    }

    @android.annotation.SystemApi
    public static final class Builder {
        private java.lang.CharSequence mContentDescription;
        private android.graphics.drawable.Icon mIcon;
        private boolean mShouldTint = true;

        public Builder(android.graphics.drawable.Icon icon) {
            this.mIcon = (android.graphics.drawable.Icon) java.util.Objects.requireNonNull(icon);
        }

        public android.app.smartspace.uitemplatedata.Icon.Builder setContentDescription(java.lang.CharSequence charSequence) {
            this.mContentDescription = charSequence;
            return this;
        }

        public android.app.smartspace.uitemplatedata.Icon.Builder setShouldTint(boolean z) {
            this.mShouldTint = z;
            return this;
        }

        public android.app.smartspace.uitemplatedata.Icon build() {
            this.mIcon.convertToAshmem();
            return new android.app.smartspace.uitemplatedata.Icon(this.mIcon, this.mContentDescription, this.mShouldTint);
        }
    }
}
