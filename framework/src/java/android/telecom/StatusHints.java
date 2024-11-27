package android.telecom;

/* loaded from: classes3.dex */
public final class StatusHints implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.telecom.StatusHints> CREATOR = new android.os.Parcelable.Creator<android.telecom.StatusHints>() { // from class: android.telecom.StatusHints.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telecom.StatusHints createFromParcel(android.os.Parcel parcel) {
            return new android.telecom.StatusHints(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telecom.StatusHints[] newArray(int i) {
            return new android.telecom.StatusHints[i];
        }
    };
    private final android.os.Bundle mExtras;
    private android.graphics.drawable.Icon mIcon;
    private final java.lang.CharSequence mLabel;

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public StatusHints(android.content.ComponentName componentName, java.lang.CharSequence charSequence, int i, android.os.Bundle bundle) {
        this(charSequence, i == 0 ? null : android.graphics.drawable.Icon.createWithResource(componentName.getPackageName(), i), bundle);
    }

    public StatusHints(java.lang.CharSequence charSequence, android.graphics.drawable.Icon icon, android.os.Bundle bundle) {
        this.mLabel = charSequence;
        this.mIcon = validateAccountIconUserBoundary(icon, android.os.Binder.getCallingUserHandle());
        this.mExtras = bundle;
    }

    public StatusHints(android.graphics.drawable.Icon icon) {
        this.mLabel = null;
        this.mExtras = null;
        this.mIcon = icon;
    }

    public void setIcon(android.graphics.drawable.Icon icon) {
        this.mIcon = icon;
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public android.content.ComponentName getPackageName() {
        return new android.content.ComponentName("", "");
    }

    public java.lang.CharSequence getLabel() {
        return this.mLabel;
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public int getIconResId() {
        return 0;
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public android.graphics.drawable.Drawable getIcon(android.content.Context context) {
        return this.mIcon.loadDrawable(context);
    }

    public android.graphics.drawable.Icon getIcon() {
        return this.mIcon;
    }

    public android.os.Bundle getExtras() {
        return this.mExtras;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static android.graphics.drawable.Icon validateAccountIconUserBoundary(android.graphics.drawable.Icon icon, android.os.UserHandle userHandle) {
        java.lang.String encodedUserInfo;
        if (icon == null || (!(icon.getType() == 4 || icon.getType() == 6) || (encodedUserInfo = icon.getUri().getEncodedUserInfo()) == null || java.lang.Integer.parseInt(encodedUserInfo) == userHandle.getIdentifier())) {
            return icon;
        }
        return null;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeCharSequence(this.mLabel);
        parcel.writeParcelable(this.mIcon, 0);
        parcel.writeParcelable(this.mExtras, 0);
    }

    private StatusHints(android.os.Parcel parcel) {
        this.mLabel = parcel.readCharSequence();
        this.mIcon = (android.graphics.drawable.Icon) parcel.readParcelable(getClass().getClassLoader(), android.graphics.drawable.Icon.class);
        this.mExtras = (android.os.Bundle) parcel.readParcelable(getClass().getClassLoader(), android.os.Bundle.class);
    }

    public boolean equals(java.lang.Object obj) {
        if (obj == null || !(obj instanceof android.telecom.StatusHints)) {
            return false;
        }
        android.telecom.StatusHints statusHints = (android.telecom.StatusHints) obj;
        return java.util.Objects.equals(statusHints.getLabel(), getLabel()) && java.util.Objects.equals(statusHints.getIcon(), getIcon()) && java.util.Objects.equals(statusHints.getExtras(), getExtras());
    }

    public int hashCode() {
        return java.util.Objects.hashCode(this.mLabel) + java.util.Objects.hashCode(this.mIcon) + java.util.Objects.hashCode(this.mExtras);
    }
}
