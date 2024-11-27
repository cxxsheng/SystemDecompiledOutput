package android.app.prediction;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class AppTarget implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.prediction.AppTarget> CREATOR = new android.os.Parcelable.Creator<android.app.prediction.AppTarget>() { // from class: android.app.prediction.AppTarget.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.prediction.AppTarget createFromParcel(android.os.Parcel parcel) {
            return new android.app.prediction.AppTarget(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.prediction.AppTarget[] newArray(int i) {
            return new android.app.prediction.AppTarget[i];
        }
    };
    private final java.lang.String mClassName;
    private final android.app.prediction.AppTargetId mId;
    private final java.lang.String mPackageName;
    private final int mRank;
    private final android.content.pm.ShortcutInfo mShortcutInfo;
    private final android.os.UserHandle mUser;

    @java.lang.Deprecated
    public AppTarget(android.app.prediction.AppTargetId appTargetId, java.lang.String str, java.lang.String str2, android.os.UserHandle userHandle) {
        this.mId = appTargetId;
        this.mShortcutInfo = null;
        this.mPackageName = (java.lang.String) java.util.Objects.requireNonNull(str);
        this.mClassName = str2;
        this.mUser = (android.os.UserHandle) java.util.Objects.requireNonNull(userHandle);
        this.mRank = 0;
    }

    @java.lang.Deprecated
    public AppTarget(android.app.prediction.AppTargetId appTargetId, android.content.pm.ShortcutInfo shortcutInfo, java.lang.String str) {
        this.mId = appTargetId;
        this.mShortcutInfo = (android.content.pm.ShortcutInfo) java.util.Objects.requireNonNull(shortcutInfo);
        this.mPackageName = this.mShortcutInfo.getPackage();
        this.mUser = this.mShortcutInfo.getUserHandle();
        this.mClassName = str;
        this.mRank = 0;
    }

    private AppTarget(android.app.prediction.AppTargetId appTargetId, java.lang.String str, android.os.UserHandle userHandle, android.content.pm.ShortcutInfo shortcutInfo, java.lang.String str2, int i) {
        this.mId = appTargetId;
        this.mShortcutInfo = shortcutInfo;
        this.mPackageName = str;
        this.mClassName = str2;
        this.mUser = userHandle;
        this.mRank = i;
    }

    private AppTarget(android.os.Parcel parcel) {
        this.mId = (android.app.prediction.AppTargetId) parcel.readTypedObject(android.app.prediction.AppTargetId.CREATOR);
        this.mShortcutInfo = (android.content.pm.ShortcutInfo) parcel.readTypedObject(android.content.pm.ShortcutInfo.CREATOR);
        if (this.mShortcutInfo == null) {
            this.mPackageName = parcel.readString();
            this.mUser = android.os.UserHandle.of(parcel.readInt());
        } else {
            this.mPackageName = this.mShortcutInfo.getPackage();
            this.mUser = this.mShortcutInfo.getUserHandle();
        }
        this.mClassName = parcel.readString();
        this.mRank = parcel.readInt();
    }

    public android.app.prediction.AppTargetId getId() {
        return this.mId;
    }

    public java.lang.String getClassName() {
        return this.mClassName;
    }

    public java.lang.String getPackageName() {
        return this.mPackageName;
    }

    public android.os.UserHandle getUser() {
        return this.mUser;
    }

    public android.content.pm.ShortcutInfo getShortcutInfo() {
        return this.mShortcutInfo;
    }

    public int getRank() {
        return this.mRank;
    }

    public boolean equals(java.lang.Object obj) {
        if (!getClass().equals(obj != null ? obj.getClass() : null)) {
            return false;
        }
        android.app.prediction.AppTarget appTarget = (android.app.prediction.AppTarget) obj;
        return this.mId.equals(appTarget.mId) && this.mPackageName.equals(appTarget.mPackageName) && ((this.mClassName == null && appTarget.mClassName == null) || (this.mClassName != null && this.mClassName.equals(appTarget.mClassName))) && this.mUser.equals(appTarget.mUser) && ((this.mShortcutInfo == null && appTarget.mShortcutInfo == null) || (this.mShortcutInfo != null && appTarget.mShortcutInfo != null && this.mShortcutInfo.getId() == appTarget.mShortcutInfo.getId())) && this.mRank == appTarget.mRank;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeTypedObject(this.mId, i);
        parcel.writeTypedObject(this.mShortcutInfo, i);
        if (this.mShortcutInfo == null) {
            parcel.writeString(this.mPackageName);
            parcel.writeInt(this.mUser.getIdentifier());
        }
        parcel.writeString(this.mClassName);
        parcel.writeInt(this.mRank);
    }

    @android.annotation.SystemApi
    public static final class Builder {
        private java.lang.String mClassName;
        private final android.app.prediction.AppTargetId mId;
        private java.lang.String mPackageName;
        private int mRank;
        private android.content.pm.ShortcutInfo mShortcutInfo;
        private android.os.UserHandle mUser;

        @android.annotation.SystemApi
        @java.lang.Deprecated
        public Builder(android.app.prediction.AppTargetId appTargetId) {
            this.mId = appTargetId;
        }

        @android.annotation.SystemApi
        public Builder(android.app.prediction.AppTargetId appTargetId, java.lang.String str, android.os.UserHandle userHandle) {
            this.mId = (android.app.prediction.AppTargetId) java.util.Objects.requireNonNull(appTargetId);
            this.mPackageName = (java.lang.String) java.util.Objects.requireNonNull(str);
            this.mUser = (android.os.UserHandle) java.util.Objects.requireNonNull(userHandle);
        }

        @android.annotation.SystemApi
        public Builder(android.app.prediction.AppTargetId appTargetId, android.content.pm.ShortcutInfo shortcutInfo) {
            this.mId = (android.app.prediction.AppTargetId) java.util.Objects.requireNonNull(appTargetId);
            this.mShortcutInfo = (android.content.pm.ShortcutInfo) java.util.Objects.requireNonNull(shortcutInfo);
            this.mPackageName = shortcutInfo.getPackage();
            this.mUser = shortcutInfo.getUserHandle();
        }

        @java.lang.Deprecated
        public android.app.prediction.AppTarget.Builder setTarget(java.lang.String str, android.os.UserHandle userHandle) {
            if (this.mPackageName != null) {
                throw new java.lang.IllegalArgumentException("Target is already set");
            }
            this.mPackageName = (java.lang.String) java.util.Objects.requireNonNull(str);
            this.mUser = (android.os.UserHandle) java.util.Objects.requireNonNull(userHandle);
            return this;
        }

        @java.lang.Deprecated
        public android.app.prediction.AppTarget.Builder setTarget(android.content.pm.ShortcutInfo shortcutInfo) {
            setTarget(shortcutInfo.getPackage(), shortcutInfo.getUserHandle());
            this.mShortcutInfo = (android.content.pm.ShortcutInfo) java.util.Objects.requireNonNull(shortcutInfo);
            return this;
        }

        public android.app.prediction.AppTarget.Builder setClassName(java.lang.String str) {
            this.mClassName = (java.lang.String) java.util.Objects.requireNonNull(str);
            return this;
        }

        public android.app.prediction.AppTarget.Builder setRank(int i) {
            if (i < 0) {
                throw new java.lang.IllegalArgumentException("rank cannot be a negative value");
            }
            this.mRank = i;
            return this;
        }

        public android.app.prediction.AppTarget build() {
            if (this.mPackageName == null) {
                throw new java.lang.IllegalStateException("No target is set");
            }
            return new android.app.prediction.AppTarget(this.mId, this.mPackageName, this.mUser, this.mShortcutInfo, this.mClassName, this.mRank);
        }
    }
}
