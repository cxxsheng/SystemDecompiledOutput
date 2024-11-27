package android.window;

/* loaded from: classes4.dex */
public class AddToSurfaceSyncGroupResult implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.window.AddToSurfaceSyncGroupResult> CREATOR = new android.os.Parcelable.Creator<android.window.AddToSurfaceSyncGroupResult>() { // from class: android.window.AddToSurfaceSyncGroupResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.window.AddToSurfaceSyncGroupResult createFromParcel(android.os.Parcel parcel) {
            android.window.AddToSurfaceSyncGroupResult addToSurfaceSyncGroupResult = new android.window.AddToSurfaceSyncGroupResult();
            addToSurfaceSyncGroupResult.readFromParcel(parcel);
            return addToSurfaceSyncGroupResult;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.window.AddToSurfaceSyncGroupResult[] newArray(int i) {
            return new android.window.AddToSurfaceSyncGroupResult[i];
        }
    };
    public android.window.ISurfaceSyncGroup mParentSyncGroup;
    public android.window.ITransactionReadyCallback mTransactionReadyCallback;

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeStrongInterface(this.mParentSyncGroup);
        parcel.writeStrongInterface(this.mTransactionReadyCallback);
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
            this.mParentSyncGroup = android.window.ISurfaceSyncGroup.Stub.asInterface(parcel.readStrongBinder());
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.mTransactionReadyCallback = android.window.ITransactionReadyCallback.Stub.asInterface(parcel.readStrongBinder());
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
