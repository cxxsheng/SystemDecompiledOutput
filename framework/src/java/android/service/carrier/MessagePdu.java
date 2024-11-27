package android.service.carrier;

/* loaded from: classes3.dex */
public final class MessagePdu implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.service.carrier.MessagePdu> CREATOR = new android.os.Parcelable.Creator<android.service.carrier.MessagePdu>() { // from class: android.service.carrier.MessagePdu.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.carrier.MessagePdu createFromParcel(android.os.Parcel parcel) {
            java.util.ArrayList arrayList;
            int readInt = parcel.readInt();
            if (readInt == -1) {
                arrayList = null;
            } else {
                java.util.ArrayList arrayList2 = new java.util.ArrayList(readInt);
                for (int i = 0; i < readInt; i++) {
                    arrayList2.add(parcel.createByteArray());
                }
                arrayList = arrayList2;
            }
            return new android.service.carrier.MessagePdu(arrayList);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.carrier.MessagePdu[] newArray(int i) {
            return new android.service.carrier.MessagePdu[i];
        }
    };
    private static final int NULL_LENGTH = -1;
    private final java.util.List<byte[]> mPduList;

    public MessagePdu(java.util.List<byte[]> list) {
        if (list == null || list.contains(null)) {
            throw new java.lang.IllegalArgumentException("pduList must not be null or contain nulls");
        }
        this.mPduList = list;
    }

    public java.util.List<byte[]> getPdus() {
        return this.mPduList;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        if (this.mPduList == null) {
            parcel.writeInt(-1);
            return;
        }
        parcel.writeInt(this.mPduList.size());
        java.util.Iterator<byte[]> it = this.mPduList.iterator();
        while (it.hasNext()) {
            parcel.writeByteArray(it.next());
        }
    }
}
