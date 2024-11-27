package android.content.pm;

/* loaded from: classes.dex */
public class LabeledIntent extends android.content.Intent {
    public static final android.os.Parcelable.Creator<android.content.pm.LabeledIntent> CREATOR = new android.os.Parcelable.Creator<android.content.pm.LabeledIntent>() { // from class: android.content.pm.LabeledIntent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.pm.LabeledIntent createFromParcel(android.os.Parcel parcel) {
            return new android.content.pm.LabeledIntent(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.pm.LabeledIntent[] newArray(int i) {
            return new android.content.pm.LabeledIntent[i];
        }
    };
    private int mIcon;
    private int mLabelRes;
    private java.lang.CharSequence mNonLocalizedLabel;
    private java.lang.String mSourcePackage;

    public LabeledIntent(android.content.Intent intent, java.lang.String str, int i, int i2) {
        super(intent);
        this.mSourcePackage = str;
        this.mLabelRes = i;
        this.mNonLocalizedLabel = null;
        this.mIcon = i2;
    }

    public LabeledIntent(android.content.Intent intent, java.lang.String str, java.lang.CharSequence charSequence, int i) {
        super(intent);
        this.mSourcePackage = str;
        this.mLabelRes = 0;
        this.mNonLocalizedLabel = charSequence;
        this.mIcon = i;
    }

    public LabeledIntent(java.lang.String str, int i, int i2) {
        this.mSourcePackage = str;
        this.mLabelRes = i;
        this.mNonLocalizedLabel = null;
        this.mIcon = i2;
    }

    public LabeledIntent(java.lang.String str, java.lang.CharSequence charSequence, int i) {
        this.mSourcePackage = str;
        this.mLabelRes = 0;
        this.mNonLocalizedLabel = charSequence;
        this.mIcon = i;
    }

    public java.lang.String getSourcePackage() {
        return this.mSourcePackage;
    }

    public int getLabelResource() {
        return this.mLabelRes;
    }

    public java.lang.CharSequence getNonLocalizedLabel() {
        return this.mNonLocalizedLabel;
    }

    public int getIconResource() {
        return this.mIcon;
    }

    public java.lang.CharSequence loadLabel(android.content.pm.PackageManager packageManager) {
        java.lang.CharSequence text;
        if (this.mNonLocalizedLabel != null) {
            return this.mNonLocalizedLabel;
        }
        if (this.mLabelRes == 0 || this.mSourcePackage == null || (text = packageManager.getText(this.mSourcePackage, this.mLabelRes, null)) == null) {
            return null;
        }
        return text;
    }

    public android.graphics.drawable.Drawable loadIcon(android.content.pm.PackageManager packageManager) {
        android.graphics.drawable.Drawable drawable;
        if (this.mIcon == 0 || this.mSourcePackage == null || (drawable = packageManager.getDrawable(this.mSourcePackage, this.mIcon, null)) == null) {
            return null;
        }
        return drawable;
    }

    @Override // android.content.Intent, android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString(this.mSourcePackage);
        parcel.writeInt(this.mLabelRes);
        android.text.TextUtils.writeToParcel(this.mNonLocalizedLabel, parcel, i);
        parcel.writeInt(this.mIcon);
    }

    protected LabeledIntent(android.os.Parcel parcel) {
        readFromParcel(parcel);
    }

    @Override // android.content.Intent
    public void readFromParcel(android.os.Parcel parcel) {
        super.readFromParcel(parcel);
        this.mSourcePackage = parcel.readString();
        this.mLabelRes = parcel.readInt();
        this.mNonLocalizedLabel = android.text.TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.mIcon = parcel.readInt();
    }
}
