package android.security.keymaster;

/* loaded from: classes3.dex */
public class OperationResult implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.security.keymaster.OperationResult> CREATOR = new android.os.Parcelable.Creator<android.security.keymaster.OperationResult>() { // from class: android.security.keymaster.OperationResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.security.keymaster.OperationResult createFromParcel(android.os.Parcel parcel) {
            return new android.security.keymaster.OperationResult(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.security.keymaster.OperationResult[] newArray(int i) {
            return new android.security.keymaster.OperationResult[i];
        }
    };
    public final int inputConsumed;
    public final long operationHandle;
    public final android.security.keymaster.KeymasterArguments outParams;
    public final byte[] output;
    public final int resultCode;
    public final android.os.IBinder token;

    public OperationResult(int i, android.os.IBinder iBinder, long j, int i2, byte[] bArr, android.security.keymaster.KeymasterArguments keymasterArguments) {
        this.resultCode = i;
        this.token = iBinder;
        this.operationHandle = j;
        this.inputConsumed = i2;
        this.output = bArr;
        this.outParams = keymasterArguments;
    }

    public OperationResult(int i) {
        this(i, null, 0L, 0, null, null);
    }

    protected OperationResult(android.os.Parcel parcel) {
        this.resultCode = parcel.readInt();
        this.token = parcel.readStrongBinder();
        this.operationHandle = parcel.readLong();
        this.inputConsumed = parcel.readInt();
        this.output = parcel.createByteArray();
        this.outParams = android.security.keymaster.KeymasterArguments.CREATOR.createFromParcel(parcel);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.resultCode);
        parcel.writeStrongBinder(this.token);
        parcel.writeLong(this.operationHandle);
        parcel.writeInt(this.inputConsumed);
        parcel.writeByteArray(this.output);
        this.outParams.writeToParcel(parcel, i);
    }
}
