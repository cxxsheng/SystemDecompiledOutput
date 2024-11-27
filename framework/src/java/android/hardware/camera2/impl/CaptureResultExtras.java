package android.hardware.camera2.impl;

/* loaded from: classes.dex */
public class CaptureResultExtras implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.camera2.impl.CaptureResultExtras> CREATOR = new android.os.Parcelable.Creator<android.hardware.camera2.impl.CaptureResultExtras>() { // from class: android.hardware.camera2.impl.CaptureResultExtras.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.camera2.impl.CaptureResultExtras createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.camera2.impl.CaptureResultExtras(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.camera2.impl.CaptureResultExtras[] newArray(int i) {
            return new android.hardware.camera2.impl.CaptureResultExtras[i];
        }
    };
    private int afTriggerId;
    private java.lang.String errorPhysicalCameraId;
    private int errorStreamId;
    private long frameNumber;
    private boolean hasReadoutTimestamp;
    private long lastCompletedRegularFrameNumber;
    private long lastCompletedReprocessFrameNumber;
    private long lastCompletedZslFrameNumber;
    private int partialResultCount;
    private int precaptureTriggerId;
    private long readoutTimestamp;
    private int requestId;
    private int subsequenceId;

    private CaptureResultExtras(android.os.Parcel parcel) {
        readFromParcel(parcel);
    }

    public CaptureResultExtras(int i, int i2, int i3, int i4, long j, int i5, int i6, java.lang.String str, long j2, long j3, long j4, boolean z, long j5) {
        this.requestId = i;
        this.subsequenceId = i2;
        this.afTriggerId = i3;
        this.precaptureTriggerId = i4;
        this.frameNumber = j;
        this.partialResultCount = i5;
        this.errorStreamId = i6;
        this.errorPhysicalCameraId = str;
        this.lastCompletedRegularFrameNumber = j2;
        this.lastCompletedReprocessFrameNumber = j3;
        this.lastCompletedZslFrameNumber = j4;
        this.hasReadoutTimestamp = z;
        this.readoutTimestamp = j5;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.requestId);
        parcel.writeInt(this.subsequenceId);
        parcel.writeInt(this.afTriggerId);
        parcel.writeInt(this.precaptureTriggerId);
        parcel.writeLong(this.frameNumber);
        parcel.writeInt(this.partialResultCount);
        parcel.writeInt(this.errorStreamId);
        if (this.errorPhysicalCameraId != null && !this.errorPhysicalCameraId.isEmpty()) {
            parcel.writeBoolean(true);
            parcel.writeString(this.errorPhysicalCameraId);
        } else {
            parcel.writeBoolean(false);
        }
        parcel.writeLong(this.lastCompletedRegularFrameNumber);
        parcel.writeLong(this.lastCompletedReprocessFrameNumber);
        parcel.writeLong(this.lastCompletedZslFrameNumber);
        parcel.writeBoolean(this.hasReadoutTimestamp);
        if (this.hasReadoutTimestamp) {
            parcel.writeLong(this.readoutTimestamp);
        }
    }

    public void readFromParcel(android.os.Parcel parcel) {
        this.requestId = parcel.readInt();
        this.subsequenceId = parcel.readInt();
        this.afTriggerId = parcel.readInt();
        this.precaptureTriggerId = parcel.readInt();
        this.frameNumber = parcel.readLong();
        this.partialResultCount = parcel.readInt();
        this.errorStreamId = parcel.readInt();
        if (parcel.readBoolean()) {
            this.errorPhysicalCameraId = parcel.readString();
        }
        this.lastCompletedRegularFrameNumber = parcel.readLong();
        this.lastCompletedReprocessFrameNumber = parcel.readLong();
        this.lastCompletedZslFrameNumber = parcel.readLong();
        this.hasReadoutTimestamp = parcel.readBoolean();
        if (this.hasReadoutTimestamp) {
            this.readoutTimestamp = parcel.readLong();
        }
    }

    public java.lang.String getErrorPhysicalCameraId() {
        return this.errorPhysicalCameraId;
    }

    public int getRequestId() {
        return this.requestId;
    }

    public int getSubsequenceId() {
        return this.subsequenceId;
    }

    public int getAfTriggerId() {
        return this.afTriggerId;
    }

    public int getPrecaptureTriggerId() {
        return this.precaptureTriggerId;
    }

    public long getFrameNumber() {
        return this.frameNumber;
    }

    public int getPartialResultCount() {
        return this.partialResultCount;
    }

    public int getErrorStreamId() {
        return this.errorStreamId;
    }

    public long getLastCompletedRegularFrameNumber() {
        return this.lastCompletedRegularFrameNumber;
    }

    public long getLastCompletedReprocessFrameNumber() {
        return this.lastCompletedReprocessFrameNumber;
    }

    public long getLastCompletedZslFrameNumber() {
        return this.lastCompletedZslFrameNumber;
    }

    public boolean hasReadoutTimestamp() {
        return this.hasReadoutTimestamp;
    }

    public long getReadoutTimestamp() {
        return this.readoutTimestamp;
    }
}
