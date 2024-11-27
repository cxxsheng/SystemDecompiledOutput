package android.content.pm;

/* loaded from: classes.dex */
public class ApexStagedEvent implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.content.pm.ApexStagedEvent> CREATOR = new android.os.Parcelable.Creator<android.content.pm.ApexStagedEvent>() { // from class: android.content.pm.ApexStagedEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.pm.ApexStagedEvent createFromParcel(android.os.Parcel parcel) {
            android.content.pm.ApexStagedEvent apexStagedEvent = new android.content.pm.ApexStagedEvent();
            apexStagedEvent.readFromParcel(parcel);
            return apexStagedEvent;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.pm.ApexStagedEvent[] newArray(int i) {
            return new android.content.pm.ApexStagedEvent[i];
        }
    };
    public java.lang.String[] stagedApexModuleNames;

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeStringArray(this.stagedApexModuleNames);
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
            } else {
                this.stagedApexModuleNames = parcel.createStringArray();
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
