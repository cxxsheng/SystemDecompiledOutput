package android.app.search;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class SearchTargetEvent implements android.os.Parcelable {
    public static final int ACTION_DELETE = 9;
    public static final int ACTION_DISMISS = 10;
    public static final int ACTION_DRAGNDROP = 7;
    public static final int ACTION_LAUNCH_KEYBOARD_FOCUS = 6;
    public static final int ACTION_LAUNCH_TOUCH = 5;
    public static final int ACTION_LONGPRESS = 4;
    public static final int ACTION_SURFACE_INVISIBLE = 8;
    public static final int ACTION_SURFACE_VISIBLE = 1;
    public static final int ACTION_TAP = 3;
    public static final android.os.Parcelable.Creator<android.app.search.SearchTargetEvent> CREATOR = new android.os.Parcelable.Creator<android.app.search.SearchTargetEvent>() { // from class: android.app.search.SearchTargetEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.search.SearchTargetEvent createFromParcel(android.os.Parcel parcel) {
            return new android.app.search.SearchTargetEvent(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.search.SearchTargetEvent[] newArray(int i) {
            return new android.app.search.SearchTargetEvent[i];
        }
    };
    public static final int FLAG_IME_SHOWN = 1;
    private final int mAction;
    private int mFlags;
    private final java.lang.String mLocation;
    private final java.util.List<java.lang.String> mTargetIds;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ActionType {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface FlagType {
    }

    private SearchTargetEvent(java.util.List<java.lang.String> list, java.lang.String str, int i, int i2) {
        this.mTargetIds = (java.util.List) java.util.Objects.requireNonNull(list);
        this.mLocation = str;
        this.mAction = i;
        this.mFlags = i2;
    }

    private SearchTargetEvent(android.os.Parcel parcel) {
        this.mTargetIds = new java.util.ArrayList();
        parcel.readStringList(this.mTargetIds);
        this.mLocation = parcel.readString();
        this.mAction = parcel.readInt();
        this.mFlags = parcel.readInt();
    }

    public java.lang.String getTargetId() {
        return this.mTargetIds.get(0);
    }

    public java.util.List<java.lang.String> getTargetIds() {
        return this.mTargetIds;
    }

    public java.lang.String getLaunchLocation() {
        return this.mLocation;
    }

    public int getAction() {
        return this.mAction;
    }

    public int getFlags() {
        return this.mFlags;
    }

    public int hashCode() {
        return this.mTargetIds.get(0).hashCode() + this.mAction;
    }

    public boolean equals(java.lang.Object obj) {
        if (!getClass().equals(obj != null ? obj.getClass() : null)) {
            return false;
        }
        android.app.search.SearchTargetEvent searchTargetEvent = (android.app.search.SearchTargetEvent) obj;
        return (this.mTargetIds.equals(searchTargetEvent.mTargetIds) && this.mAction == searchTargetEvent.mAction && this.mFlags == searchTargetEvent.mFlags && this.mLocation == null) ? searchTargetEvent.mLocation == null : this.mLocation.equals(searchTargetEvent.mLocation);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeStringList(this.mTargetIds);
        parcel.writeString(this.mLocation);
        parcel.writeInt(this.mAction);
        parcel.writeInt(this.mFlags);
    }

    @android.annotation.SystemApi
    public static final class Builder {
        private int mAction;
        private int mFlags;
        private java.lang.String mLocation;
        private java.util.List<java.lang.String> mTargetIds;

        public Builder(java.lang.String str, int i) {
            this.mTargetIds = new java.util.ArrayList();
            this.mTargetIds.add(str);
            this.mAction = i;
        }

        public Builder(java.util.List<java.lang.String> list, int i) {
            this.mTargetIds = list;
            this.mAction = i;
        }

        public android.app.search.SearchTargetEvent.Builder setLaunchLocation(java.lang.String str) {
            this.mLocation = str;
            return this;
        }

        public android.app.search.SearchTargetEvent.Builder setFlags(int i) {
            this.mFlags = i;
            return this;
        }

        public android.app.search.SearchTargetEvent build() {
            return new android.app.search.SearchTargetEvent(this.mTargetIds, this.mLocation, this.mAction, this.mFlags);
        }
    }
}
