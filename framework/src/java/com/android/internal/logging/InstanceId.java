package com.android.internal.logging;

/* loaded from: classes4.dex */
public final class InstanceId implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<com.android.internal.logging.InstanceId> CREATOR = new android.os.Parcelable.Creator<com.android.internal.logging.InstanceId>() { // from class: com.android.internal.logging.InstanceId.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.internal.logging.InstanceId createFromParcel(android.os.Parcel parcel) {
            return new com.android.internal.logging.InstanceId(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.internal.logging.InstanceId[] newArray(int i) {
            return new com.android.internal.logging.InstanceId[i];
        }
    };
    static final int INSTANCE_ID_MAX = 1048576;
    private final int mId;

    InstanceId(int i) {
        this.mId = java.lang.Math.min(java.lang.Math.max(0, i), 1048576);
    }

    private InstanceId(android.os.Parcel parcel) {
        this(parcel.readInt());
    }

    public int getId() {
        return this.mId;
    }

    public static com.android.internal.logging.InstanceId fakeInstanceId(int i) {
        return new com.android.internal.logging.InstanceId(i);
    }

    public int hashCode() {
        return this.mId;
    }

    public boolean equals(java.lang.Object obj) {
        return (obj instanceof com.android.internal.logging.InstanceId) && this.mId == ((com.android.internal.logging.InstanceId) obj).mId;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mId);
    }
}
