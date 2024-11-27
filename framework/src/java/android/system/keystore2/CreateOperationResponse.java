package android.system.keystore2;

/* loaded from: classes3.dex */
public class CreateOperationResponse implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.system.keystore2.CreateOperationResponse> CREATOR = new android.os.Parcelable.Creator<android.system.keystore2.CreateOperationResponse>() { // from class: android.system.keystore2.CreateOperationResponse.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.system.keystore2.CreateOperationResponse createFromParcel(android.os.Parcel parcel) {
            android.system.keystore2.CreateOperationResponse createOperationResponse = new android.system.keystore2.CreateOperationResponse();
            createOperationResponse.readFromParcel(parcel);
            return createOperationResponse;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.system.keystore2.CreateOperationResponse[] newArray(int i) {
            return new android.system.keystore2.CreateOperationResponse[i];
        }
    };
    public android.system.keystore2.IKeystoreOperation iOperation;
    public android.system.keystore2.OperationChallenge operationChallenge;
    public android.system.keystore2.KeyParameters parameters;
    public byte[] upgradedBlob;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeStrongInterface(this.iOperation);
        parcel.writeTypedObject(this.operationChallenge, i);
        parcel.writeTypedObject(this.parameters, i);
        parcel.writeByteArray(this.upgradedBlob);
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
            this.iOperation = android.system.keystore2.IKeystoreOperation.Stub.asInterface(parcel.readStrongBinder());
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.operationChallenge = (android.system.keystore2.OperationChallenge) parcel.readTypedObject(android.system.keystore2.OperationChallenge.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.parameters = (android.system.keystore2.KeyParameters) parcel.readTypedObject(android.system.keystore2.KeyParameters.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.upgradedBlob = parcel.createByteArray();
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
        return describeContents(this.operationChallenge) | 0 | describeContents(this.parameters);
    }

    private int describeContents(java.lang.Object obj) {
        if (obj == null || !(obj instanceof android.os.Parcelable)) {
            return 0;
        }
        return ((android.os.Parcelable) obj).describeContents();
    }
}
