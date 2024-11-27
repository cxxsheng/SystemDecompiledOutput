package android.app.search;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class SearchAction implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.search.SearchAction> CREATOR = new android.os.Parcelable.Creator<android.app.search.SearchAction>() { // from class: android.app.search.SearchAction.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.search.SearchAction createFromParcel(android.os.Parcel parcel) {
            return new android.app.search.SearchAction(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.search.SearchAction[] newArray(int i) {
            return new android.app.search.SearchAction[i];
        }
    };
    private static final java.lang.String TAG = "SearchAction";
    private final java.lang.CharSequence mContentDescription;
    private final android.os.Bundle mExtras;
    private final android.graphics.drawable.Icon mIcon;
    private java.lang.String mId;
    private final android.content.Intent mIntent;
    private final android.app.PendingIntent mPendingIntent;
    private final java.lang.CharSequence mSubtitle;
    private final java.lang.CharSequence mTitle;
    private final android.os.UserHandle mUserHandle;

    SearchAction(android.os.Parcel parcel) {
        this.mId = parcel.readString();
        this.mTitle = android.text.TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.mIcon = (android.graphics.drawable.Icon) parcel.readTypedObject(android.graphics.drawable.Icon.CREATOR);
        this.mSubtitle = android.text.TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.mContentDescription = android.text.TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.mPendingIntent = (android.app.PendingIntent) parcel.readTypedObject(android.app.PendingIntent.CREATOR);
        this.mIntent = (android.content.Intent) parcel.readTypedObject(android.content.Intent.CREATOR);
        this.mUserHandle = (android.os.UserHandle) parcel.readTypedObject(android.os.UserHandle.CREATOR);
        this.mExtras = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
    }

    private SearchAction(java.lang.String str, java.lang.CharSequence charSequence, android.graphics.drawable.Icon icon, java.lang.CharSequence charSequence2, java.lang.CharSequence charSequence3, android.app.PendingIntent pendingIntent, android.content.Intent intent, android.os.UserHandle userHandle, android.os.Bundle bundle) {
        this.mId = (java.lang.String) java.util.Objects.requireNonNull(str);
        this.mTitle = (java.lang.CharSequence) java.util.Objects.requireNonNull(charSequence);
        this.mIcon = icon;
        this.mSubtitle = charSequence2;
        this.mContentDescription = charSequence3;
        this.mPendingIntent = pendingIntent;
        this.mIntent = intent;
        this.mUserHandle = userHandle;
        this.mExtras = bundle == null ? new android.os.Bundle() : bundle;
        if (this.mPendingIntent == null && this.mIntent == null) {
            throw new java.lang.IllegalStateException("At least one type of intent should be available.");
        }
        if (this.mPendingIntent != null && this.mIntent != null) {
            throw new java.lang.IllegalStateException("Only one type of intent should be available.");
        }
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
        if (!(obj instanceof android.app.search.SearchAction)) {
            return false;
        }
        android.app.search.SearchAction searchAction = (android.app.search.SearchAction) obj;
        return this.mId.equals(searchAction.mId) && this.mTitle.equals(searchAction.mTitle);
    }

    public int hashCode() {
        return java.util.Objects.hash(this.mId, this.mTitle);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.mId);
        android.text.TextUtils.writeToParcel(this.mTitle, parcel, i);
        parcel.writeTypedObject(this.mIcon, i);
        android.text.TextUtils.writeToParcel(this.mSubtitle, parcel, i);
        android.text.TextUtils.writeToParcel(this.mContentDescription, parcel, i);
        parcel.writeTypedObject(this.mPendingIntent, i);
        parcel.writeTypedObject(this.mIntent, i);
        parcel.writeTypedObject(this.mUserHandle, i);
        parcel.writeTypedObject(this.mExtras, i);
    }

    public java.lang.String toString() {
        return "id=" + this.mId + " title=" + ((java.lang.Object) this.mTitle) + " contentDescription=" + ((java.lang.Object) this.mContentDescription) + " subtitle=" + ((java.lang.Object) this.mSubtitle) + " icon=" + this.mIcon + " pendingIntent=" + (this.mPendingIntent == null ? "" : this.mPendingIntent.getIntent()) + " intent=" + this.mIntent + " userHandle=" + this.mUserHandle;
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

        public android.app.search.SearchAction.Builder setIcon(android.graphics.drawable.Icon icon) {
            this.mIcon = icon;
            return this;
        }

        public android.app.search.SearchAction.Builder setSubtitle(java.lang.CharSequence charSequence) {
            this.mSubtitle = charSequence;
            return this;
        }

        public android.app.search.SearchAction.Builder setContentDescription(java.lang.CharSequence charSequence) {
            this.mContentDescription = charSequence;
            return this;
        }

        public android.app.search.SearchAction.Builder setPendingIntent(android.app.PendingIntent pendingIntent) {
            this.mPendingIntent = pendingIntent;
            return this;
        }

        public android.app.search.SearchAction.Builder setUserHandle(android.os.UserHandle userHandle) {
            this.mUserHandle = userHandle;
            return this;
        }

        public android.app.search.SearchAction.Builder setIntent(android.content.Intent intent) {
            this.mIntent = intent;
            return this;
        }

        public android.app.search.SearchAction.Builder setExtras(android.os.Bundle bundle) {
            this.mExtras = bundle;
            return this;
        }

        public android.app.search.SearchAction build() {
            return new android.app.search.SearchAction(this.mId, this.mTitle, this.mIcon, this.mSubtitle, this.mContentDescription, this.mPendingIntent, this.mIntent, this.mUserHandle, this.mExtras);
        }
    }
}
