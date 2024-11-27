package android.app.job;

/* loaded from: classes.dex */
public class UserVisibleJobSummary implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.job.UserVisibleJobSummary> CREATOR = new android.os.Parcelable.Creator<android.app.job.UserVisibleJobSummary>() { // from class: android.app.job.UserVisibleJobSummary.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.job.UserVisibleJobSummary createFromParcel(android.os.Parcel parcel) {
            return new android.app.job.UserVisibleJobSummary(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.job.UserVisibleJobSummary[] newArray(int i) {
            return new android.app.job.UserVisibleJobSummary[i];
        }
    };
    private final java.lang.String mCallingPackageName;
    private final int mCallingUid;
    private final int mJobId;
    private final java.lang.String mNamespace;
    private final java.lang.String mSourcePackageName;
    private final int mSourceUserId;

    public UserVisibleJobSummary(int i, java.lang.String str, int i2, java.lang.String str2, java.lang.String str3, int i3) {
        this.mCallingUid = i;
        this.mCallingPackageName = str;
        this.mSourceUserId = i2;
        this.mSourcePackageName = str2;
        this.mNamespace = str3;
        this.mJobId = i3;
    }

    protected UserVisibleJobSummary(android.os.Parcel parcel) {
        this.mCallingUid = parcel.readInt();
        this.mCallingPackageName = parcel.readString();
        this.mSourceUserId = parcel.readInt();
        this.mSourcePackageName = parcel.readString();
        this.mNamespace = parcel.readString();
        this.mJobId = parcel.readInt();
    }

    public java.lang.String getCallingPackageName() {
        return this.mCallingPackageName;
    }

    public int getCallingUid() {
        return this.mCallingUid;
    }

    public int getJobId() {
        return this.mJobId;
    }

    public java.lang.String getNamespace() {
        return this.mNamespace;
    }

    public int getSourceUserId() {
        return this.mSourceUserId;
    }

    public java.lang.String getSourcePackageName() {
        return this.mSourcePackageName;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof android.app.job.UserVisibleJobSummary)) {
            return false;
        }
        android.app.job.UserVisibleJobSummary userVisibleJobSummary = (android.app.job.UserVisibleJobSummary) obj;
        return this.mCallingUid == userVisibleJobSummary.mCallingUid && this.mCallingPackageName.equals(userVisibleJobSummary.mCallingPackageName) && this.mSourceUserId == userVisibleJobSummary.mSourceUserId && this.mSourcePackageName.equals(userVisibleJobSummary.mSourcePackageName) && java.util.Objects.equals(this.mNamespace, userVisibleJobSummary.mNamespace) && this.mJobId == userVisibleJobSummary.mJobId;
    }

    public int hashCode() {
        int hashCode = ((((((0 + this.mCallingUid) * 31) + this.mCallingPackageName.hashCode()) * 31) + this.mSourceUserId) * 31) + this.mSourcePackageName.hashCode();
        if (this.mNamespace != null) {
            hashCode = (hashCode * 31) + this.mNamespace.hashCode();
        }
        return (hashCode * 31) + this.mJobId;
    }

    public java.lang.String toString() {
        return "UserVisibleJobSummary{callingUid=" + this.mCallingUid + ", callingPackageName='" + this.mCallingPackageName + "', sourceUserId=" + this.mSourceUserId + ", sourcePackageName='" + this.mSourcePackageName + "', namespace=" + this.mNamespace + ", jobId=" + this.mJobId + "}";
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mCallingUid);
        parcel.writeString(this.mCallingPackageName);
        parcel.writeInt(this.mSourceUserId);
        parcel.writeString(this.mSourcePackageName);
        parcel.writeString(this.mNamespace);
        parcel.writeInt(this.mJobId);
    }
}
