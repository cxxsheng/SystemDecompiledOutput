package android.hardware.tv.tuner;

/* loaded from: classes2.dex */
public class DemuxMmtpFilterSettings implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.tv.tuner.DemuxMmtpFilterSettings> CREATOR = new android.os.Parcelable.Creator<android.hardware.tv.tuner.DemuxMmtpFilterSettings>() { // from class: android.hardware.tv.tuner.DemuxMmtpFilterSettings.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.tv.tuner.DemuxMmtpFilterSettings createFromParcel(android.os.Parcel parcel) {
            android.hardware.tv.tuner.DemuxMmtpFilterSettings demuxMmtpFilterSettings = new android.hardware.tv.tuner.DemuxMmtpFilterSettings();
            demuxMmtpFilterSettings.readFromParcel(parcel);
            return demuxMmtpFilterSettings;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.tv.tuner.DemuxMmtpFilterSettings[] newArray(int i) {
            return new android.hardware.tv.tuner.DemuxMmtpFilterSettings[i];
        }
    };
    public android.hardware.tv.tuner.DemuxMmtpFilterSettingsFilterSettings filterSettings;
    public int mmtpPid = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.mmtpPid);
        parcel.writeTypedObject(this.filterSettings, i);
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
            this.mmtpPid = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.filterSettings = (android.hardware.tv.tuner.DemuxMmtpFilterSettingsFilterSettings) parcel.readTypedObject(android.hardware.tv.tuner.DemuxMmtpFilterSettingsFilterSettings.CREATOR);
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
        return describeContents(this.filterSettings) | 0;
    }

    private int describeContents(java.lang.Object obj) {
        if (obj == null || !(obj instanceof android.os.Parcelable)) {
            return 0;
        }
        return ((android.os.Parcelable) obj).describeContents();
    }
}