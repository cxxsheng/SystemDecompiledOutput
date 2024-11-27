package android.app.search;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class SearchTarget implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.search.SearchTarget> CREATOR = new android.os.Parcelable.Creator<android.app.search.SearchTarget>() { // from class: android.app.search.SearchTarget.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.search.SearchTarget createFromParcel(android.os.Parcel parcel) {
            return new android.app.search.SearchTarget(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.search.SearchTarget[] newArray(int i) {
            return new android.app.search.SearchTarget[i];
        }
    };
    public static final java.lang.String LAYOUT_TYPE_ICON = "icon";
    public static final java.lang.String LAYOUT_TYPE_ICON_ROW = "icon_row";
    public static final java.lang.String LAYOUT_TYPE_SHORT_ICON_ROW = "short_icon_row";
    public static final int RESULT_TYPE_APPLICATION = 1;
    public static final int RESULT_TYPE_SHORTCUT = 2;
    public static final int RESULT_TYPE_SLICE = 4;
    public static final int RESULT_TYPE_WIDGETS = 8;
    private final android.appwidget.AppWidgetProviderInfo mAppWidgetProviderInfo;
    private final android.os.Bundle mExtras;
    private final boolean mHidden;
    private final java.lang.String mId;
    private final java.lang.String mLayoutType;
    private final java.lang.String mPackageName;
    private java.lang.String mParentId;
    private final int mResultType;
    private final float mScore;
    private final android.app.search.SearchAction mSearchAction;
    private final android.content.pm.ShortcutInfo mShortcutInfo;
    private final android.net.Uri mSliceUri;
    private final android.os.UserHandle mUserHandle;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface SearchLayoutType {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface SearchResultType {
    }

    private SearchTarget(android.os.Parcel parcel) {
        this.mResultType = parcel.readInt();
        this.mLayoutType = parcel.readString();
        this.mId = parcel.readString();
        this.mParentId = parcel.readString();
        this.mScore = parcel.readFloat();
        this.mHidden = parcel.readBoolean();
        this.mPackageName = parcel.readString();
        this.mUserHandle = android.os.UserHandle.of(parcel.readInt());
        this.mSearchAction = (android.app.search.SearchAction) parcel.readTypedObject(android.app.search.SearchAction.CREATOR);
        this.mShortcutInfo = (android.content.pm.ShortcutInfo) parcel.readTypedObject(android.content.pm.ShortcutInfo.CREATOR);
        this.mAppWidgetProviderInfo = (android.appwidget.AppWidgetProviderInfo) parcel.readTypedObject(android.appwidget.AppWidgetProviderInfo.CREATOR);
        this.mSliceUri = (android.net.Uri) parcel.readTypedObject(android.net.Uri.CREATOR);
        this.mExtras = parcel.readBundle(getClass().getClassLoader());
    }

    private SearchTarget(int i, java.lang.String str, java.lang.String str2, java.lang.String str3, float f, boolean z, java.lang.String str4, android.os.UserHandle userHandle, android.app.search.SearchAction searchAction, android.content.pm.ShortcutInfo shortcutInfo, android.net.Uri uri, android.appwidget.AppWidgetProviderInfo appWidgetProviderInfo, android.os.Bundle bundle) {
        this.mResultType = i;
        this.mLayoutType = (java.lang.String) java.util.Objects.requireNonNull(str);
        this.mId = (java.lang.String) java.util.Objects.requireNonNull(str2);
        this.mParentId = str3;
        this.mScore = f;
        this.mHidden = z;
        this.mPackageName = (java.lang.String) java.util.Objects.requireNonNull(str4);
        this.mUserHandle = (android.os.UserHandle) java.util.Objects.requireNonNull(userHandle);
        this.mSearchAction = searchAction;
        this.mShortcutInfo = shortcutInfo;
        this.mAppWidgetProviderInfo = appWidgetProviderInfo;
        this.mSliceUri = uri;
        this.mExtras = bundle == null ? new android.os.Bundle() : bundle;
    }

    public int getResultType() {
        return this.mResultType;
    }

    public java.lang.String getLayoutType() {
        return this.mLayoutType;
    }

    public java.lang.String getId() {
        return this.mId;
    }

    public java.lang.String getParentId() {
        return this.mParentId;
    }

    public float getScore() {
        return this.mScore;
    }

    @java.lang.Deprecated
    public boolean shouldHide() {
        return this.mHidden;
    }

    public boolean isHidden() {
        return this.mHidden;
    }

    public java.lang.String getPackageName() {
        return this.mPackageName;
    }

    public android.os.UserHandle getUserHandle() {
        return this.mUserHandle;
    }

    public android.content.pm.ShortcutInfo getShortcutInfo() {
        return this.mShortcutInfo;
    }

    public android.appwidget.AppWidgetProviderInfo getAppWidgetProviderInfo() {
        return this.mAppWidgetProviderInfo;
    }

    public android.net.Uri getSliceUri() {
        return this.mSliceUri;
    }

    public android.app.search.SearchAction getSearchAction() {
        return this.mSearchAction;
    }

    public android.os.Bundle getExtras() {
        return this.mExtras;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mResultType);
        parcel.writeString(this.mLayoutType);
        parcel.writeString(this.mId);
        parcel.writeString(this.mParentId);
        parcel.writeFloat(this.mScore);
        parcel.writeBoolean(this.mHidden);
        parcel.writeString(this.mPackageName);
        parcel.writeInt(this.mUserHandle.getIdentifier());
        parcel.writeTypedObject(this.mSearchAction, i);
        parcel.writeTypedObject(this.mShortcutInfo, i);
        parcel.writeTypedObject(this.mAppWidgetProviderInfo, i);
        parcel.writeTypedObject(this.mSliceUri, i);
        parcel.writeBundle(this.mExtras);
    }

    @android.annotation.SystemApi
    public static final class Builder {
        private android.appwidget.AppWidgetProviderInfo mAppWidgetProviderInfo;
        private android.os.Bundle mExtras;
        private java.lang.String mId;
        private java.lang.String mLayoutType;
        private java.lang.String mPackageName;
        private java.lang.String mParentId;
        private int mResultType;
        private android.app.search.SearchAction mSearchAction;
        private android.content.pm.ShortcutInfo mShortcutInfo;
        private android.net.Uri mSliceUri;
        private android.os.UserHandle mUserHandle;
        private float mScore = 1.0f;
        private boolean mHidden = false;

        public Builder(int i, java.lang.String str, java.lang.String str2) {
            this.mId = str2;
            this.mLayoutType = (java.lang.String) java.util.Objects.requireNonNull(str);
            this.mResultType = i;
        }

        public android.app.search.SearchTarget.Builder setParentId(java.lang.String str) {
            this.mParentId = (java.lang.String) java.util.Objects.requireNonNull(str);
            return this;
        }

        public android.app.search.SearchTarget.Builder setPackageName(java.lang.String str) {
            this.mPackageName = (java.lang.String) java.util.Objects.requireNonNull(str);
            return this;
        }

        public android.app.search.SearchTarget.Builder setUserHandle(android.os.UserHandle userHandle) {
            this.mUserHandle = (android.os.UserHandle) java.util.Objects.requireNonNull(userHandle);
            return this;
        }

        public android.app.search.SearchTarget.Builder setShortcutInfo(android.content.pm.ShortcutInfo shortcutInfo) {
            this.mShortcutInfo = (android.content.pm.ShortcutInfo) java.util.Objects.requireNonNull(shortcutInfo);
            if (this.mPackageName != null && !this.mPackageName.equals(shortcutInfo.getPackage())) {
                throw new java.lang.IllegalStateException("SearchTarget packageName is different from shortcut's packageName");
            }
            this.mPackageName = shortcutInfo.getPackage();
            return this;
        }

        public android.app.search.SearchTarget.Builder setAppWidgetProviderInfo(android.appwidget.AppWidgetProviderInfo appWidgetProviderInfo) {
            this.mAppWidgetProviderInfo = (android.appwidget.AppWidgetProviderInfo) java.util.Objects.requireNonNull(appWidgetProviderInfo);
            if (this.mPackageName != null && !this.mPackageName.equals(appWidgetProviderInfo.provider.getPackageName())) {
                throw new java.lang.IllegalStateException("SearchTarget packageName is different from appWidgetProviderInfo's packageName");
            }
            return this;
        }

        public android.app.search.SearchTarget.Builder setSliceUri(android.net.Uri uri) {
            this.mSliceUri = uri;
            return this;
        }

        public android.app.search.SearchTarget.Builder setSearchAction(android.app.search.SearchAction searchAction) {
            this.mSearchAction = searchAction;
            return this;
        }

        public android.app.search.SearchTarget.Builder setExtras(android.os.Bundle bundle) {
            this.mExtras = (android.os.Bundle) java.util.Objects.requireNonNull(bundle);
            return this;
        }

        public android.app.search.SearchTarget.Builder setScore(float f) {
            this.mScore = f;
            return this;
        }

        public android.app.search.SearchTarget.Builder setHidden(boolean z) {
            this.mHidden = z;
            return this;
        }

        @java.lang.Deprecated
        public android.app.search.SearchTarget.Builder setShouldHide(boolean z) {
            this.mHidden = z;
            return this;
        }

        public android.app.search.SearchTarget build() {
            return new android.app.search.SearchTarget(this.mResultType, this.mLayoutType, this.mId, this.mParentId, this.mScore, this.mHidden, this.mPackageName, this.mUserHandle, this.mSearchAction, this.mShortcutInfo, this.mSliceUri, this.mAppWidgetProviderInfo, this.mExtras);
        }
    }
}
