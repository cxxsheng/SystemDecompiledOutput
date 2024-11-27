package android.app.smartspace.uitemplatedata;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class TapAction implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.smartspace.uitemplatedata.TapAction> CREATOR = new android.os.Parcelable.Creator<android.app.smartspace.uitemplatedata.TapAction>() { // from class: android.app.smartspace.uitemplatedata.TapAction.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.smartspace.uitemplatedata.TapAction createFromParcel(android.os.Parcel parcel) {
            return new android.app.smartspace.uitemplatedata.TapAction(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.smartspace.uitemplatedata.TapAction[] newArray(int i) {
            return new android.app.smartspace.uitemplatedata.TapAction[i];
        }
    };
    private final android.os.Bundle mExtras;
    private final java.lang.CharSequence mId;
    private final android.content.Intent mIntent;
    private final android.app.PendingIntent mPendingIntent;
    private final boolean mShouldShowOnLockscreen;
    private final android.os.UserHandle mUserHandle;

    TapAction(android.os.Parcel parcel) {
        this.mId = android.text.TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.mIntent = (android.content.Intent) parcel.readTypedObject(android.content.Intent.CREATOR);
        this.mPendingIntent = (android.app.PendingIntent) parcel.readTypedObject(android.app.PendingIntent.CREATOR);
        this.mUserHandle = (android.os.UserHandle) parcel.readTypedObject(android.os.UserHandle.CREATOR);
        this.mExtras = parcel.readBundle();
        this.mShouldShowOnLockscreen = parcel.readBoolean();
    }

    private TapAction(java.lang.CharSequence charSequence, android.content.Intent intent, android.app.PendingIntent pendingIntent, android.os.UserHandle userHandle, android.os.Bundle bundle, boolean z) {
        this.mId = charSequence;
        this.mIntent = intent;
        this.mPendingIntent = pendingIntent;
        this.mUserHandle = userHandle;
        this.mExtras = bundle;
        this.mShouldShowOnLockscreen = z;
    }

    public java.lang.CharSequence getId() {
        return this.mId;
    }

    public android.content.Intent getIntent() {
        return this.mIntent;
    }

    public android.app.PendingIntent getPendingIntent() {
        return this.mPendingIntent;
    }

    public android.os.UserHandle getUserHandle() {
        return this.mUserHandle;
    }

    public android.os.Bundle getExtras() {
        return this.mExtras;
    }

    public boolean shouldShowOnLockscreen() {
        return this.mShouldShowOnLockscreen;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        android.text.TextUtils.writeToParcel(this.mId, parcel, i);
        parcel.writeTypedObject(this.mIntent, i);
        parcel.writeTypedObject(this.mPendingIntent, i);
        parcel.writeTypedObject(this.mUserHandle, i);
        parcel.writeBundle(this.mExtras);
        parcel.writeBoolean(this.mShouldShowOnLockscreen);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof android.app.smartspace.uitemplatedata.TapAction) {
            return android.app.smartspace.SmartspaceUtils.isEqual(this.mId, ((android.app.smartspace.uitemplatedata.TapAction) obj).mId);
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(this.mId);
    }

    public java.lang.String toString() {
        return "SmartspaceTapAction{mId=" + ((java.lang.Object) this.mId) + "mIntent=" + this.mIntent + ", mPendingIntent=" + this.mPendingIntent + ", mUserHandle=" + this.mUserHandle + ", mExtras=" + this.mExtras + ", mShouldShowOnLockscreen=" + this.mShouldShowOnLockscreen + '}';
    }

    @android.annotation.SystemApi
    public static final class Builder {
        private android.os.Bundle mExtras;
        private java.lang.CharSequence mId;
        private android.content.Intent mIntent;
        private android.app.PendingIntent mPendingIntent;
        private boolean mShouldShowOnLockScreen = false;
        private android.os.UserHandle mUserHandle;

        public Builder(java.lang.CharSequence charSequence) {
            this.mId = (java.lang.CharSequence) java.util.Objects.requireNonNull(charSequence);
        }

        public android.app.smartspace.uitemplatedata.TapAction.Builder setIntent(android.content.Intent intent) {
            this.mIntent = intent;
            return this;
        }

        public android.app.smartspace.uitemplatedata.TapAction.Builder setPendingIntent(android.app.PendingIntent pendingIntent) {
            this.mPendingIntent = pendingIntent;
            return this;
        }

        public android.app.smartspace.uitemplatedata.TapAction.Builder setUserHandle(android.os.UserHandle userHandle) {
            this.mUserHandle = userHandle;
            return this;
        }

        public android.app.smartspace.uitemplatedata.TapAction.Builder setExtras(android.os.Bundle bundle) {
            this.mExtras = bundle;
            return this;
        }

        public android.app.smartspace.uitemplatedata.TapAction.Builder setShouldShowOnLockscreen(boolean z) {
            this.mShouldShowOnLockScreen = z;
            return this;
        }

        public android.app.smartspace.uitemplatedata.TapAction build() {
            if (this.mIntent == null && this.mPendingIntent == null && this.mExtras == null) {
                throw new java.lang.IllegalStateException("Please assign at least 1 valid tap field");
            }
            return new android.app.smartspace.uitemplatedata.TapAction(this.mId, this.mIntent, this.mPendingIntent, this.mUserHandle, this.mExtras, this.mShouldShowOnLockScreen);
        }
    }
}
