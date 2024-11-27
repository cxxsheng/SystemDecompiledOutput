package android.os.incremental;

/* loaded from: classes3.dex */
public class StorageHealthCheckParams implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.os.incremental.StorageHealthCheckParams> CREATOR = new android.os.Parcelable.Creator<android.os.incremental.StorageHealthCheckParams>() { // from class: android.os.incremental.StorageHealthCheckParams.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.os.incremental.StorageHealthCheckParams createFromParcel(android.os.Parcel parcel) {
            android.os.incremental.StorageHealthCheckParams storageHealthCheckParams = new android.os.incremental.StorageHealthCheckParams();
            storageHealthCheckParams.readFromParcel(parcel);
            return storageHealthCheckParams;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.os.incremental.StorageHealthCheckParams[] newArray(int i) {
            return new android.os.incremental.StorageHealthCheckParams[i];
        }
    };
    public int blockedTimeoutMs = 0;
    public int unhealthyTimeoutMs = 0;
    public int unhealthyMonitoringMs = 0;

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.blockedTimeoutMs);
        parcel.writeInt(this.unhealthyTimeoutMs);
        parcel.writeInt(this.unhealthyMonitoringMs);
        int dataPosition2 = parcel.dataPosition();
        parcel.setDataPosition(dataPosition);
        parcel.writeInt(dataPosition2 - dataPosition);
        parcel.setDataPosition(dataPosition2);
    }

    public final void readFromParcel(android.os.Parcel parcel) {
        int dataPosition = parcel.dataPosition();
        int readInt = parcel.readInt();
        try {
            if (readInt < 4) {
                throw new android.os.BadParcelableException("Parcelable too small");
            }
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.blockedTimeoutMs = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.unhealthyTimeoutMs = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.unhealthyMonitoringMs = parcel.readInt();
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            }
        } catch (java.lang.Throwable th) {
            if (dataPosition > Integer.MAX_VALUE - readInt) {
                throw new android.os.BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(dataPosition + readInt);
            throw th;
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
