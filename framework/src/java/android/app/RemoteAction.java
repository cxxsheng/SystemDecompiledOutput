package android.app;

/* loaded from: classes.dex */
public final class RemoteAction implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.RemoteAction> CREATOR = new android.os.Parcelable.Creator<android.app.RemoteAction>() { // from class: android.app.RemoteAction.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.RemoteAction createFromParcel(android.os.Parcel parcel) {
            return new android.app.RemoteAction(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.RemoteAction[] newArray(int i) {
            return new android.app.RemoteAction[i];
        }
    };
    private static final java.lang.String TAG = "RemoteAction";
    private final android.app.PendingIntent mActionIntent;
    private final java.lang.CharSequence mContentDescription;
    private boolean mEnabled;
    private final android.graphics.drawable.Icon mIcon;
    private boolean mShouldShowIcon;
    private final java.lang.CharSequence mTitle;

    RemoteAction(android.os.Parcel parcel) {
        this.mIcon = android.graphics.drawable.Icon.CREATOR.createFromParcel(parcel);
        this.mTitle = android.text.TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.mContentDescription = android.text.TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.mActionIntent = android.app.PendingIntent.CREATOR.createFromParcel(parcel);
        this.mEnabled = parcel.readBoolean();
        this.mShouldShowIcon = parcel.readBoolean();
    }

    public RemoteAction(android.graphics.drawable.Icon icon, java.lang.CharSequence charSequence, java.lang.CharSequence charSequence2, android.app.PendingIntent pendingIntent) {
        if (icon == null || charSequence == null || charSequence2 == null || pendingIntent == null) {
            throw new java.lang.IllegalArgumentException("Expected icon, title, content description and action callback");
        }
        this.mIcon = icon;
        this.mTitle = charSequence;
        this.mContentDescription = charSequence2;
        this.mActionIntent = pendingIntent;
        this.mEnabled = true;
        this.mShouldShowIcon = true;
    }

    public void setEnabled(boolean z) {
        this.mEnabled = z;
    }

    public boolean isEnabled() {
        return this.mEnabled;
    }

    public void setShouldShowIcon(boolean z) {
        this.mShouldShowIcon = z;
    }

    public boolean shouldShowIcon() {
        return this.mShouldShowIcon;
    }

    public android.graphics.drawable.Icon getIcon() {
        return this.mIcon;
    }

    public java.lang.CharSequence getTitle() {
        return this.mTitle;
    }

    public java.lang.CharSequence getContentDescription() {
        return this.mContentDescription;
    }

    public android.app.PendingIntent getActionIntent() {
        return this.mActionIntent;
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public android.app.RemoteAction m492clone() {
        android.app.RemoteAction remoteAction = new android.app.RemoteAction(this.mIcon, this.mTitle, this.mContentDescription, this.mActionIntent);
        remoteAction.setEnabled(this.mEnabled);
        remoteAction.setShouldShowIcon(this.mShouldShowIcon);
        return remoteAction;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof android.app.RemoteAction)) {
            return false;
        }
        android.app.RemoteAction remoteAction = (android.app.RemoteAction) obj;
        return this.mEnabled == remoteAction.mEnabled && this.mShouldShowIcon == remoteAction.mShouldShowIcon && this.mIcon.equals(remoteAction.mIcon) && this.mTitle.equals(remoteAction.mTitle) && this.mContentDescription.equals(remoteAction.mContentDescription) && this.mActionIntent.equals(remoteAction.mActionIntent);
    }

    public int hashCode() {
        return java.util.Objects.hash(this.mIcon, this.mTitle, this.mContentDescription, this.mActionIntent, java.lang.Boolean.valueOf(this.mEnabled), java.lang.Boolean.valueOf(this.mShouldShowIcon));
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        this.mIcon.writeToParcel(parcel, 0);
        android.text.TextUtils.writeToParcel(this.mTitle, parcel, i);
        android.text.TextUtils.writeToParcel(this.mContentDescription, parcel, i);
        this.mActionIntent.writeToParcel(parcel, i);
        parcel.writeBoolean(this.mEnabled);
        parcel.writeBoolean(this.mShouldShowIcon);
    }

    public void dump(java.lang.String str, java.io.PrintWriter printWriter) {
        printWriter.print(str);
        printWriter.print("title=" + ((java.lang.Object) this.mTitle));
        printWriter.print(" enabled=" + this.mEnabled);
        printWriter.print(" contentDescription=" + ((java.lang.Object) this.mContentDescription));
        printWriter.print(" icon=" + this.mIcon);
        printWriter.print(" action=" + this.mActionIntent.getIntent());
        printWriter.print(" shouldShowIcon=" + this.mShouldShowIcon);
        printWriter.println();
    }
}
