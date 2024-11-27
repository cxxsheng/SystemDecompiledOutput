package android.app.smartspace;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class SmartspaceAction implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.smartspace.SmartspaceAction> CREATOR = new android.os.Parcelable.Creator<android.app.smartspace.SmartspaceAction>() { // from class: android.app.smartspace.SmartspaceAction.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.smartspace.SmartspaceAction createFromParcel(android.os.Parcel parcel) {
            return new android.app.smartspace.SmartspaceAction(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.smartspace.SmartspaceAction[] newArray(int i) {
            return new android.app.smartspace.SmartspaceAction[i];
        }
    };
    private static final java.lang.String TAG = "SmartspaceAction";
    private final java.lang.CharSequence mContentDescription;
    private android.os.Bundle mExtras;
    private final android.graphics.drawable.Icon mIcon;
    private final java.lang.String mId;
    private final android.content.Intent mIntent;
    private final android.app.PendingIntent mPendingIntent;
    private final java.lang.CharSequence mSubtitle;
    private final java.lang.CharSequence mTitle;
    private final android.os.UserHandle mUserHandle;

    SmartspaceAction(android.os.Parcel parcel) {
        this.mId = parcel.readString();
        this.mIcon = (android.graphics.drawable.Icon) parcel.readTypedObject(android.graphics.drawable.Icon.CREATOR);
        this.mTitle = android.text.TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.mSubtitle = android.text.TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.mContentDescription = android.text.TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.mPendingIntent = (android.app.PendingIntent) parcel.readTypedObject(android.app.PendingIntent.CREATOR);
        this.mIntent = (android.content.Intent) parcel.readTypedObject(android.content.Intent.CREATOR);
        this.mUserHandle = (android.os.UserHandle) parcel.readTypedObject(android.os.UserHandle.CREATOR);
        this.mExtras = parcel.readBundle();
    }

    private SmartspaceAction(java.lang.String str, android.graphics.drawable.Icon icon, java.lang.CharSequence charSequence, java.lang.CharSequence charSequence2, java.lang.CharSequence charSequence3, android.app.PendingIntent pendingIntent, android.content.Intent intent, android.os.UserHandle userHandle, android.os.Bundle bundle) {
        this.mId = (java.lang.String) java.util.Objects.requireNonNull(str);
        this.mIcon = icon;
        this.mTitle = (java.lang.CharSequence) java.util.Objects.requireNonNull(charSequence);
        this.mSubtitle = charSequence2;
        this.mContentDescription = charSequence3;
        this.mPendingIntent = pendingIntent;
        this.mIntent = intent;
        this.mUserHandle = userHandle;
        this.mExtras = bundle;
    }

    public java.lang.String getId() {
        return this.mId;
    }

    public android.graphics.drawable.Icon getIcon() {
        return this.mIcon;
    }

    public java.lang.CharSequence getTitle() {
        return this.mTitle;
    }

    public java.lang.CharSequence getSubtitle() {
        return this.mSubtitle;
    }

    public java.lang.CharSequence getContentDescription() {
        return this.mContentDescription;
    }

    public android.app.PendingIntent getPendingIntent() {
        return this.mPendingIntent;
    }

    public android.content.Intent getIntent() {
        return this.mIntent;
    }

    public android.os.UserHandle getUserHandle() {
        return this.mUserHandle;
    }

    public android.os.Bundle getExtras() {
        return this.mExtras;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof android.app.smartspace.SmartspaceAction) {
            return this.mId.equals(((android.app.smartspace.SmartspaceAction) obj).mId);
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(this.mId);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.mId);
        parcel.writeTypedObject(this.mIcon, i);
        android.text.TextUtils.writeToParcel(this.mTitle, parcel, i);
        android.text.TextUtils.writeToParcel(this.mSubtitle, parcel, i);
        android.text.TextUtils.writeToParcel(this.mContentDescription, parcel, i);
        parcel.writeTypedObject(this.mPendingIntent, i);
        parcel.writeTypedObject(this.mIntent, i);
        parcel.writeTypedObject(this.mUserHandle, i);
        parcel.writeBundle(this.mExtras);
    }

    public java.lang.String toString() {
        return "SmartspaceAction{mId='" + this.mId + android.text.format.DateFormat.QUOTE + ", mIcon=" + this.mIcon + ", mTitle=" + ((java.lang.Object) this.mTitle) + ", mSubtitle=" + ((java.lang.Object) this.mSubtitle) + ", mContentDescription=" + ((java.lang.Object) this.mContentDescription) + ", mPendingIntent=" + this.mPendingIntent + ", mIntent=" + this.mIntent + ", mUserHandle=" + this.mUserHandle + ", mExtras=" + this.mExtras + '}';
    }

    @android.annotation.SystemApi
    public static final class Builder {
        private java.lang.CharSequence mContentDescription;
        private android.os.Bundle mExtras;
        private android.graphics.drawable.Icon mIcon;
        private java.lang.String mId;
        private android.content.Intent mIntent;
        private android.app.PendingIntent mPendingIntent;
        private java.lang.CharSequence mSubtitle;
        private java.lang.CharSequence mTitle;
        private android.os.UserHandle mUserHandle;

        public Builder(java.lang.String str, java.lang.String str2) {
            this.mId = (java.lang.String) java.util.Objects.requireNonNull(str);
            this.mTitle = (java.lang.CharSequence) java.util.Objects.requireNonNull(str2);
        }

        public android.app.smartspace.SmartspaceAction.Builder setIcon(android.graphics.drawable.Icon icon) {
            this.mIcon = icon;
            return this;
        }

        public android.app.smartspace.SmartspaceAction.Builder setSubtitle(java.lang.CharSequence charSequence) {
            this.mSubtitle = charSequence;
            return this;
        }

        public android.app.smartspace.SmartspaceAction.Builder setContentDescription(java.lang.CharSequence charSequence) {
            this.mContentDescription = charSequence;
            return this;
        }

        public android.app.smartspace.SmartspaceAction.Builder setPendingIntent(android.app.PendingIntent pendingIntent) {
            this.mPendingIntent = pendingIntent;
            return this;
        }

        public android.app.smartspace.SmartspaceAction.Builder setUserHandle(android.os.UserHandle userHandle) {
            this.mUserHandle = userHandle;
            return this;
        }

        public android.app.smartspace.SmartspaceAction.Builder setIntent(android.content.Intent intent) {
            this.mIntent = intent;
            return this;
        }

        public android.app.smartspace.SmartspaceAction.Builder setExtras(android.os.Bundle bundle) {
            this.mExtras = bundle;
            return this;
        }

        public android.app.smartspace.SmartspaceAction build() {
            if (this.mIcon != null) {
                this.mIcon.convertToAshmem();
            }
            return new android.app.smartspace.SmartspaceAction(this.mId, this.mIcon, this.mTitle, this.mSubtitle, this.mContentDescription, this.mPendingIntent, this.mIntent, this.mUserHandle, this.mExtras);
        }
    }
}
