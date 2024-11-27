package android.service.chooser;

@java.lang.Deprecated
/* loaded from: classes3.dex */
public final class ChooserTarget implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.service.chooser.ChooserTarget> CREATOR = new android.os.Parcelable.Creator<android.service.chooser.ChooserTarget>() { // from class: android.service.chooser.ChooserTarget.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.chooser.ChooserTarget createFromParcel(android.os.Parcel parcel) {
            return new android.service.chooser.ChooserTarget(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.chooser.ChooserTarget[] newArray(int i) {
            return new android.service.chooser.ChooserTarget[i];
        }
    };
    private static final java.lang.String TAG = "ChooserTarget";
    private android.content.ComponentName mComponentName;
    private android.graphics.drawable.Icon mIcon;
    private android.os.Bundle mIntentExtras;
    private float mScore;
    private java.lang.CharSequence mTitle;

    public ChooserTarget(java.lang.CharSequence charSequence, android.graphics.drawable.Icon icon, float f, android.content.ComponentName componentName, android.os.Bundle bundle) {
        this.mTitle = charSequence;
        this.mIcon = icon;
        if (f > 1.0f || f < 0.0f) {
            throw new java.lang.IllegalArgumentException("Score " + f + " out of range; must be between 0.0f and 1.0f");
        }
        this.mScore = f;
        this.mComponentName = componentName;
        this.mIntentExtras = bundle;
    }

    ChooserTarget(android.os.Parcel parcel) {
        this.mTitle = parcel.readCharSequence();
        if (parcel.readInt() != 0) {
            this.mIcon = android.graphics.drawable.Icon.CREATOR.createFromParcel(parcel);
        } else {
            this.mIcon = null;
        }
        this.mScore = parcel.readFloat();
        this.mComponentName = android.content.ComponentName.readFromParcel(parcel);
        this.mIntentExtras = parcel.readBundle();
    }

    public java.lang.CharSequence getTitle() {
        return this.mTitle;
    }

    public android.graphics.drawable.Icon getIcon() {
        return this.mIcon;
    }

    public float getScore() {
        return this.mScore;
    }

    public android.content.ComponentName getComponentName() {
        return this.mComponentName;
    }

    public android.os.Bundle getIntentExtras() {
        return this.mIntentExtras;
    }

    public java.lang.String toString() {
        return "ChooserTarget{" + this.mComponentName + ", " + this.mIntentExtras + ", '" + ((java.lang.Object) this.mTitle) + "', " + this.mScore + "}";
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeCharSequence(this.mTitle);
        if (this.mIcon != null) {
            parcel.writeInt(1);
            this.mIcon.writeToParcel(parcel, 0);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeFloat(this.mScore);
        android.content.ComponentName.writeToParcel(this.mComponentName, parcel);
        parcel.writeBundle(this.mIntentExtras);
    }
}
