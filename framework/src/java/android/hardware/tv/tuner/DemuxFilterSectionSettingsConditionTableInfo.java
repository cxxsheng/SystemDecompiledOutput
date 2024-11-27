package android.hardware.tv.tuner;

/* loaded from: classes2.dex */
public class DemuxFilterSectionSettingsConditionTableInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.tv.tuner.DemuxFilterSectionSettingsConditionTableInfo> CREATOR = new android.os.Parcelable.Creator<android.hardware.tv.tuner.DemuxFilterSectionSettingsConditionTableInfo>() { // from class: android.hardware.tv.tuner.DemuxFilterSectionSettingsConditionTableInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.tv.tuner.DemuxFilterSectionSettingsConditionTableInfo createFromParcel(android.os.Parcel parcel) {
            android.hardware.tv.tuner.DemuxFilterSectionSettingsConditionTableInfo demuxFilterSectionSettingsConditionTableInfo = new android.hardware.tv.tuner.DemuxFilterSectionSettingsConditionTableInfo();
            demuxFilterSectionSettingsConditionTableInfo.readFromParcel(parcel);
            return demuxFilterSectionSettingsConditionTableInfo;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.tv.tuner.DemuxFilterSectionSettingsConditionTableInfo[] newArray(int i) {
            return new android.hardware.tv.tuner.DemuxFilterSectionSettingsConditionTableInfo[i];
        }
    };
    public int tableId = 0;
    public int version = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.tableId);
        parcel.writeInt(this.version);
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
            this.tableId = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.version = parcel.readInt();
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
