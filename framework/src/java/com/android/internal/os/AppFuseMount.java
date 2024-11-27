package com.android.internal.os;

/* loaded from: classes4.dex */
public class AppFuseMount implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<com.android.internal.os.AppFuseMount> CREATOR = new android.os.Parcelable.Creator<com.android.internal.os.AppFuseMount>() { // from class: com.android.internal.os.AppFuseMount.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.internal.os.AppFuseMount createFromParcel(android.os.Parcel parcel) {
            return new com.android.internal.os.AppFuseMount(parcel.readInt(), (android.os.ParcelFileDescriptor) parcel.readParcelable(null, android.os.ParcelFileDescriptor.class));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.internal.os.AppFuseMount[] newArray(int i) {
            return new com.android.internal.os.AppFuseMount[i];
        }
    };
    public final android.os.ParcelFileDescriptor fd;
    public final int mountPointId;

    public AppFuseMount(int i, android.os.ParcelFileDescriptor parcelFileDescriptor) {
        com.android.internal.util.Preconditions.checkNotNull(parcelFileDescriptor);
        this.mountPointId = i;
        this.fd = parcelFileDescriptor;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mountPointId);
        parcel.writeParcelable(this.fd, i);
    }
}
