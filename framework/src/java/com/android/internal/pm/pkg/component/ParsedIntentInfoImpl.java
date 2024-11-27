package com.android.internal.pm.pkg.component;

/* loaded from: classes5.dex */
public class ParsedIntentInfoImpl implements com.android.internal.pm.pkg.component.ParsedIntentInfo, android.os.Parcelable {
    public static final android.os.Parcelable.Creator<com.android.internal.pm.pkg.component.ParsedIntentInfoImpl> CREATOR = new android.os.Parcelable.Creator<com.android.internal.pm.pkg.component.ParsedIntentInfoImpl>() { // from class: com.android.internal.pm.pkg.component.ParsedIntentInfoImpl.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.internal.pm.pkg.component.ParsedIntentInfoImpl[] newArray(int i) {
            return new com.android.internal.pm.pkg.component.ParsedIntentInfoImpl[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.internal.pm.pkg.component.ParsedIntentInfoImpl createFromParcel(android.os.Parcel parcel) {
            return new com.android.internal.pm.pkg.component.ParsedIntentInfoImpl(parcel);
        }
    };
    private boolean mHasDefault;
    private int mIcon;
    private android.content.IntentFilter mIntentFilter;
    private int mLabelRes;
    private java.lang.CharSequence mNonLocalizedLabel;

    public ParsedIntentInfoImpl() {
        this.mIntentFilter = new android.content.IntentFilter();
    }

    @Override // com.android.internal.pm.pkg.component.ParsedIntentInfo
    public boolean isHasDefault() {
        return this.mHasDefault;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedIntentInfo
    public int getLabelRes() {
        return this.mLabelRes;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedIntentInfo
    public java.lang.CharSequence getNonLocalizedLabel() {
        return this.mNonLocalizedLabel;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedIntentInfo
    public int getIcon() {
        return this.mIcon;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedIntentInfo
    public android.content.IntentFilter getIntentFilter() {
        return this.mIntentFilter;
    }

    public com.android.internal.pm.pkg.component.ParsedIntentInfoImpl setHasDefault(boolean z) {
        this.mHasDefault = z;
        return this;
    }

    public com.android.internal.pm.pkg.component.ParsedIntentInfoImpl setLabelRes(int i) {
        this.mLabelRes = i;
        return this;
    }

    public com.android.internal.pm.pkg.component.ParsedIntentInfoImpl setNonLocalizedLabel(java.lang.CharSequence charSequence) {
        this.mNonLocalizedLabel = charSequence;
        return this;
    }

    public com.android.internal.pm.pkg.component.ParsedIntentInfoImpl setIcon(int i) {
        this.mIcon = i;
        return this;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        byte b = this.mHasDefault ? (byte) 1 : (byte) 0;
        if (this.mNonLocalizedLabel != null) {
            b = (byte) (b | 4);
        }
        parcel.writeByte(b);
        parcel.writeInt(this.mLabelRes);
        if (this.mNonLocalizedLabel != null) {
            parcel.writeCharSequence(this.mNonLocalizedLabel);
        }
        parcel.writeInt(this.mIcon);
        parcel.writeTypedObject(this.mIntentFilter, i);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    protected ParsedIntentInfoImpl(android.os.Parcel parcel) {
        this.mIntentFilter = new android.content.IntentFilter();
        byte readByte = parcel.readByte();
        boolean z = (readByte & 1) != 0;
        int readInt = parcel.readInt();
        java.lang.CharSequence readCharSequence = (readByte & 4) == 0 ? null : parcel.readCharSequence();
        int readInt2 = parcel.readInt();
        android.content.IntentFilter intentFilter = (android.content.IntentFilter) parcel.readTypedObject(android.content.IntentFilter.CREATOR);
        this.mHasDefault = z;
        this.mLabelRes = readInt;
        this.mNonLocalizedLabel = readCharSequence;
        this.mIcon = readInt2;
        this.mIntentFilter = intentFilter;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mIntentFilter);
    }

    @java.lang.Deprecated
    private void __metadata() {
    }
}
