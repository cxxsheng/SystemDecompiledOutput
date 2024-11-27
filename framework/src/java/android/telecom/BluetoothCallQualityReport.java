package android.telecom;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class BluetoothCallQualityReport implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.telecom.BluetoothCallQualityReport> CREATOR = new android.os.Parcelable.Creator<android.telecom.BluetoothCallQualityReport>() { // from class: android.telecom.BluetoothCallQualityReport.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telecom.BluetoothCallQualityReport createFromParcel(android.os.Parcel parcel) {
            return new android.telecom.BluetoothCallQualityReport(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telecom.BluetoothCallQualityReport[] newArray(int i) {
            return new android.telecom.BluetoothCallQualityReport[i];
        }
    };
    public static final java.lang.String EVENT_BLUETOOTH_CALL_QUALITY_REPORT = "android.telecom.event.BLUETOOTH_CALL_QUALITY_REPORT";
    public static final java.lang.String EXTRA_BLUETOOTH_CALL_QUALITY_REPORT = "android.telecom.extra.BLUETOOTH_CALL_QUALITY_REPORT";
    private final boolean mChoppyVoice;
    private final int mNegativeAcknowledgementCount;
    private final int mPacketsNotReceivedCount;
    private final int mRetransmittedPacketsCount;
    private final int mRssiDbm;
    private final long mSentTimestampMillis;
    private final int mSnrDb;

    public long getSentTimestampMillis() {
        return this.mSentTimestampMillis;
    }

    public boolean isChoppyVoice() {
        return this.mChoppyVoice;
    }

    public int getRssiDbm() {
        return this.mRssiDbm;
    }

    public int getSnrDb() {
        return this.mSnrDb;
    }

    public int getRetransmittedPacketsCount() {
        return this.mRetransmittedPacketsCount;
    }

    public int getPacketsNotReceivedCount() {
        return this.mPacketsNotReceivedCount;
    }

    public int getNegativeAcknowledgementCount() {
        return this.mNegativeAcknowledgementCount;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeLong(this.mSentTimestampMillis);
        parcel.writeBoolean(this.mChoppyVoice);
        parcel.writeInt(this.mRssiDbm);
        parcel.writeInt(this.mSnrDb);
        parcel.writeInt(this.mRetransmittedPacketsCount);
        parcel.writeInt(this.mPacketsNotReceivedCount);
        parcel.writeInt(this.mNegativeAcknowledgementCount);
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.telecom.BluetoothCallQualityReport bluetoothCallQualityReport = (android.telecom.BluetoothCallQualityReport) obj;
        if (this.mSentTimestampMillis == bluetoothCallQualityReport.mSentTimestampMillis && this.mChoppyVoice == bluetoothCallQualityReport.mChoppyVoice && this.mRssiDbm == bluetoothCallQualityReport.mRssiDbm && this.mSnrDb == bluetoothCallQualityReport.mSnrDb && this.mRetransmittedPacketsCount == bluetoothCallQualityReport.mRetransmittedPacketsCount && this.mPacketsNotReceivedCount == bluetoothCallQualityReport.mPacketsNotReceivedCount && this.mNegativeAcknowledgementCount == bluetoothCallQualityReport.mNegativeAcknowledgementCount) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Long.valueOf(this.mSentTimestampMillis), java.lang.Boolean.valueOf(this.mChoppyVoice), java.lang.Integer.valueOf(this.mRssiDbm), java.lang.Integer.valueOf(this.mSnrDb), java.lang.Integer.valueOf(this.mRetransmittedPacketsCount), java.lang.Integer.valueOf(this.mPacketsNotReceivedCount), java.lang.Integer.valueOf(this.mNegativeAcknowledgementCount));
    }

    public static final class Builder {
        private boolean mChoppyVoice;
        private int mNegativeAcknowledgementCount;
        private int mPacketsNotReceivedCount;
        private int mRetransmittedPacketsCount;
        private int mRssiDbm;
        private long mSentTimestampMillis;
        private int mSnrDb;

        public android.telecom.BluetoothCallQualityReport.Builder setSentTimestampMillis(long j) {
            this.mSentTimestampMillis = j;
            return this;
        }

        public android.telecom.BluetoothCallQualityReport.Builder setChoppyVoice(boolean z) {
            this.mChoppyVoice = z;
            return this;
        }

        public android.telecom.BluetoothCallQualityReport.Builder setRssiDbm(int i) {
            this.mRssiDbm = i;
            return this;
        }

        public android.telecom.BluetoothCallQualityReport.Builder setSnrDb(int i) {
            this.mSnrDb = i;
            return this;
        }

        public android.telecom.BluetoothCallQualityReport.Builder setRetransmittedPacketsCount(int i) {
            this.mRetransmittedPacketsCount = i;
            return this;
        }

        public android.telecom.BluetoothCallQualityReport.Builder setPacketsNotReceivedCount(int i) {
            this.mPacketsNotReceivedCount = i;
            return this;
        }

        public android.telecom.BluetoothCallQualityReport.Builder setNegativeAcknowledgementCount(int i) {
            this.mNegativeAcknowledgementCount = i;
            return this;
        }

        public android.telecom.BluetoothCallQualityReport build() {
            return new android.telecom.BluetoothCallQualityReport(this);
        }
    }

    private BluetoothCallQualityReport(android.os.Parcel parcel) {
        this.mSentTimestampMillis = parcel.readLong();
        this.mChoppyVoice = parcel.readBoolean();
        this.mRssiDbm = parcel.readInt();
        this.mSnrDb = parcel.readInt();
        this.mRetransmittedPacketsCount = parcel.readInt();
        this.mPacketsNotReceivedCount = parcel.readInt();
        this.mNegativeAcknowledgementCount = parcel.readInt();
    }

    private BluetoothCallQualityReport(android.telecom.BluetoothCallQualityReport.Builder builder) {
        this.mSentTimestampMillis = builder.mSentTimestampMillis;
        this.mChoppyVoice = builder.mChoppyVoice;
        this.mRssiDbm = builder.mRssiDbm;
        this.mSnrDb = builder.mSnrDb;
        this.mRetransmittedPacketsCount = builder.mRetransmittedPacketsCount;
        this.mPacketsNotReceivedCount = builder.mPacketsNotReceivedCount;
        this.mNegativeAcknowledgementCount = builder.mNegativeAcknowledgementCount;
    }
}
