package android.telecom;

/* loaded from: classes3.dex */
public class ParcelableRttCall implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.telecom.ParcelableRttCall> CREATOR = new android.os.Parcelable.Creator<android.telecom.ParcelableRttCall>() { // from class: android.telecom.ParcelableRttCall.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telecom.ParcelableRttCall createFromParcel(android.os.Parcel parcel) {
            return new android.telecom.ParcelableRttCall(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telecom.ParcelableRttCall[] newArray(int i) {
            return new android.telecom.ParcelableRttCall[i];
        }
    };
    private final android.os.ParcelFileDescriptor mReceiveStream;
    private final int mRttMode;
    private final android.os.ParcelFileDescriptor mTransmitStream;

    public ParcelableRttCall(int i, android.os.ParcelFileDescriptor parcelFileDescriptor, android.os.ParcelFileDescriptor parcelFileDescriptor2) {
        this.mRttMode = i;
        this.mTransmitStream = parcelFileDescriptor;
        this.mReceiveStream = parcelFileDescriptor2;
    }

    protected ParcelableRttCall(android.os.Parcel parcel) {
        this.mRttMode = parcel.readInt();
        this.mTransmitStream = (android.os.ParcelFileDescriptor) parcel.readParcelable(android.os.ParcelFileDescriptor.class.getClassLoader(), android.os.ParcelFileDescriptor.class);
        this.mReceiveStream = (android.os.ParcelFileDescriptor) parcel.readParcelable(android.os.ParcelFileDescriptor.class.getClassLoader(), android.os.ParcelFileDescriptor.class);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mRttMode);
        parcel.writeParcelable(this.mTransmitStream, i);
        parcel.writeParcelable(this.mReceiveStream, i);
    }

    public int getRttMode() {
        return this.mRttMode;
    }

    public android.os.ParcelFileDescriptor getReceiveStream() {
        return this.mReceiveStream;
    }

    public android.os.ParcelFileDescriptor getTransmitStream() {
        return this.mTransmitStream;
    }
}
