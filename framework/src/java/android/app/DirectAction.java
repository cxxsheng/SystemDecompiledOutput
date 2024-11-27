package android.app;

/* loaded from: classes.dex */
public final class DirectAction implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.DirectAction> CREATOR = new android.os.Parcelable.Creator<android.app.DirectAction>() { // from class: android.app.DirectAction.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.DirectAction createFromParcel(android.os.Parcel parcel) {
            return new android.app.DirectAction(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.DirectAction[] newArray(int i) {
            return new android.app.DirectAction[i];
        }
    };
    public static final java.lang.String KEY_ACTIONS_LIST = "actions_list";
    private android.os.IBinder mActivityId;
    private final android.os.Bundle mExtras;
    private final java.lang.String mID;
    private final android.content.LocusId mLocusId;
    private int mTaskId;

    public DirectAction(java.lang.String str, android.os.Bundle bundle, android.content.LocusId locusId) {
        this.mID = (java.lang.String) com.android.internal.util.Preconditions.checkStringNotEmpty(str);
        this.mExtras = bundle;
        this.mLocusId = locusId;
    }

    public void setSource(int i, android.os.IBinder iBinder) {
        this.mTaskId = i;
        this.mActivityId = iBinder;
    }

    public DirectAction(android.app.DirectAction directAction) {
        this.mTaskId = directAction.mTaskId;
        this.mActivityId = directAction.mActivityId;
        this.mID = directAction.mID;
        this.mExtras = directAction.mExtras;
        this.mLocusId = directAction.mLocusId;
    }

    private DirectAction(android.os.Parcel parcel) {
        this.mTaskId = parcel.readInt();
        this.mActivityId = parcel.readStrongBinder();
        this.mID = parcel.readString();
        this.mExtras = parcel.readBundle();
        java.lang.String readString = parcel.readString();
        this.mLocusId = readString != null ? new android.content.LocusId(readString) : null;
    }

    public int getTaskId() {
        return this.mTaskId;
    }

    public android.os.IBinder getActivityId() {
        return this.mActivityId;
    }

    public java.lang.String getId() {
        return this.mID;
    }

    public android.os.Bundle getExtras() {
        return this.mExtras;
    }

    public android.content.LocusId getLocusId() {
        return this.mLocusId;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public int hashCode() {
        return this.mID.hashCode();
    }

    public boolean equals(java.lang.Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        return this.mID.equals(((android.app.DirectAction) obj).mID);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mTaskId);
        parcel.writeStrongBinder(this.mActivityId);
        parcel.writeString(this.mID);
        parcel.writeBundle(this.mExtras);
        parcel.writeString(this.mLocusId.getId());
    }

    public static final class Builder {
        private android.os.Bundle mExtras;
        private java.lang.String mId;
        private android.content.LocusId mLocusId;

        public Builder(java.lang.String str) {
            java.util.Objects.requireNonNull(str);
            this.mId = str;
        }

        public android.app.DirectAction.Builder setExtras(android.os.Bundle bundle) {
            this.mExtras = bundle;
            return this;
        }

        public android.app.DirectAction.Builder setLocusId(android.content.LocusId locusId) {
            this.mLocusId = locusId;
            return this;
        }

        public android.app.DirectAction build() {
            return new android.app.DirectAction(this.mId, this.mExtras, this.mLocusId);
        }
    }
}
