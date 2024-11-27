package android.view;

/* loaded from: classes4.dex */
public final class ContentRecordingSession implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.view.ContentRecordingSession> CREATOR = new android.os.Parcelable.Creator<android.view.ContentRecordingSession>() { // from class: android.view.ContentRecordingSession.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.ContentRecordingSession[] newArray(int i) {
            return new android.view.ContentRecordingSession[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.ContentRecordingSession createFromParcel(android.os.Parcel parcel) {
            return new android.view.ContentRecordingSession(parcel);
        }
    };
    public static final int RECORD_CONTENT_DISPLAY = 0;
    public static final int RECORD_CONTENT_TASK = 1;
    public static final int TARGET_UID_FULL_SCREEN = -1;
    public static final int TARGET_UID_UNKNOWN = -2;
    private int mContentToRecord;
    private int mDisplayToRecord;
    private int mTargetUid;
    private android.os.IBinder mTokenToRecord;
    private int mVirtualDisplayId;
    private boolean mWaitingForConsent;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface RecordContent {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface TargetUid {
    }

    private ContentRecordingSession() {
        this.mVirtualDisplayId = -1;
        this.mContentToRecord = 0;
        this.mDisplayToRecord = -1;
        this.mTokenToRecord = null;
        this.mWaitingForConsent = false;
        this.mTargetUid = -2;
    }

    public static android.view.ContentRecordingSession createDisplaySession(int i) {
        return new android.view.ContentRecordingSession().setDisplayToRecord(i).setContentToRecord(0).setTargetUid(-1);
    }

    public static android.view.ContentRecordingSession createTaskSession(android.os.IBinder iBinder) {
        return createTaskSession(iBinder, -2);
    }

    public static android.view.ContentRecordingSession createTaskSession(android.os.IBinder iBinder, int i) {
        return new android.view.ContentRecordingSession().setContentToRecord(1).setTokenToRecord(iBinder).setTargetUid(i);
    }

    public static boolean isValid(android.view.ContentRecordingSession contentRecordingSession) {
        if (contentRecordingSession == null) {
            return false;
        }
        boolean z = contentRecordingSession.getContentToRecord() == 1 && contentRecordingSession.getTokenToRecord() != null;
        boolean z2 = contentRecordingSession.getContentToRecord() == 0 && contentRecordingSession.getDisplayToRecord() > -1;
        if (contentRecordingSession.getVirtualDisplayId() > -1) {
            return z || z2;
        }
        return false;
    }

    public static boolean isProjectionOnSameDisplay(android.view.ContentRecordingSession contentRecordingSession, android.view.ContentRecordingSession contentRecordingSession2) {
        return (contentRecordingSession == null || contentRecordingSession2 == null || contentRecordingSession.getVirtualDisplayId() != contentRecordingSession2.getVirtualDisplayId()) ? false : true;
    }

    public static java.lang.String recordContentToString(int i) {
        switch (i) {
            case 0:
                return "RECORD_CONTENT_DISPLAY";
            case 1:
                return "RECORD_CONTENT_TASK";
            default:
                return java.lang.Integer.toHexString(i);
        }
    }

    public static java.lang.String targetUidToString(int i) {
        switch (i) {
            case -2:
                return "TARGET_UID_UNKNOWN";
            case -1:
                return "TARGET_UID_FULL_SCREEN";
            default:
                return java.lang.Integer.toHexString(i);
        }
    }

    ContentRecordingSession(int i, int i2, int i3, android.os.IBinder iBinder, boolean z, int i4) {
        this.mVirtualDisplayId = -1;
        this.mContentToRecord = 0;
        this.mDisplayToRecord = -1;
        this.mTokenToRecord = null;
        this.mWaitingForConsent = false;
        this.mTargetUid = -2;
        this.mVirtualDisplayId = i;
        this.mContentToRecord = i2;
        if (this.mContentToRecord != 0 && this.mContentToRecord != 1) {
            throw new java.lang.IllegalArgumentException("contentToRecord was " + this.mContentToRecord + " but must be one of: RECORD_CONTENT_DISPLAY(0), RECORD_CONTENT_TASK(1" + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        }
        this.mDisplayToRecord = i3;
        this.mTokenToRecord = iBinder;
        this.mWaitingForConsent = z;
        this.mTargetUid = i4;
    }

    public int getVirtualDisplayId() {
        return this.mVirtualDisplayId;
    }

    public int getContentToRecord() {
        return this.mContentToRecord;
    }

    public int getDisplayToRecord() {
        return this.mDisplayToRecord;
    }

    public android.os.IBinder getTokenToRecord() {
        return this.mTokenToRecord;
    }

    public boolean isWaitingForConsent() {
        return this.mWaitingForConsent;
    }

    public int getTargetUid() {
        return this.mTargetUid;
    }

    public android.view.ContentRecordingSession setVirtualDisplayId(int i) {
        this.mVirtualDisplayId = i;
        return this;
    }

    public android.view.ContentRecordingSession setContentToRecord(int i) {
        this.mContentToRecord = i;
        if (this.mContentToRecord != 0 && this.mContentToRecord != 1) {
            throw new java.lang.IllegalArgumentException("contentToRecord was " + this.mContentToRecord + " but must be one of: RECORD_CONTENT_DISPLAY(0), RECORD_CONTENT_TASK(1" + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        }
        return this;
    }

    public android.view.ContentRecordingSession setDisplayToRecord(int i) {
        this.mDisplayToRecord = i;
        return this;
    }

    public android.view.ContentRecordingSession setTokenToRecord(android.os.IBinder iBinder) {
        this.mTokenToRecord = iBinder;
        return this;
    }

    public android.view.ContentRecordingSession setWaitingForConsent(boolean z) {
        this.mWaitingForConsent = z;
        return this;
    }

    public android.view.ContentRecordingSession setTargetUid(int i) {
        this.mTargetUid = i;
        return this;
    }

    public java.lang.String toString() {
        return "ContentRecordingSession { virtualDisplayId = " + this.mVirtualDisplayId + ", contentToRecord = " + recordContentToString(this.mContentToRecord) + ", displayToRecord = " + this.mDisplayToRecord + ", tokenToRecord = " + this.mTokenToRecord + ", waitingForConsent = " + this.mWaitingForConsent + ", targetUid = " + this.mTargetUid + " }";
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.view.ContentRecordingSession contentRecordingSession = (android.view.ContentRecordingSession) obj;
        if (this.mVirtualDisplayId == contentRecordingSession.mVirtualDisplayId && this.mContentToRecord == contentRecordingSession.mContentToRecord && this.mDisplayToRecord == contentRecordingSession.mDisplayToRecord && java.util.Objects.equals(this.mTokenToRecord, contentRecordingSession.mTokenToRecord) && this.mWaitingForConsent == contentRecordingSession.mWaitingForConsent && this.mTargetUid == contentRecordingSession.mTargetUid) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return ((((((((((this.mVirtualDisplayId + 31) * 31) + this.mContentToRecord) * 31) + this.mDisplayToRecord) * 31) + java.util.Objects.hashCode(this.mTokenToRecord)) * 31) + java.lang.Boolean.hashCode(this.mWaitingForConsent)) * 31) + this.mTargetUid;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        byte b = this.mWaitingForConsent ? (byte) 16 : (byte) 0;
        if (this.mTokenToRecord != null) {
            b = (byte) (b | 8);
        }
        parcel.writeByte(b);
        parcel.writeInt(this.mVirtualDisplayId);
        parcel.writeInt(this.mContentToRecord);
        parcel.writeInt(this.mDisplayToRecord);
        if (this.mTokenToRecord != null) {
            parcel.writeStrongBinder(this.mTokenToRecord);
        }
        parcel.writeInt(this.mTargetUid);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    ContentRecordingSession(android.os.Parcel parcel) {
        this.mVirtualDisplayId = -1;
        this.mContentToRecord = 0;
        this.mDisplayToRecord = -1;
        this.mTokenToRecord = null;
        this.mWaitingForConsent = false;
        this.mTargetUid = -2;
        byte readByte = parcel.readByte();
        boolean z = (readByte & 16) != 0;
        int readInt = parcel.readInt();
        int readInt2 = parcel.readInt();
        int readInt3 = parcel.readInt();
        android.os.IBinder readStrongBinder = (readByte & 8) != 0 ? parcel.readStrongBinder() : null;
        int readInt4 = parcel.readInt();
        this.mVirtualDisplayId = readInt;
        this.mContentToRecord = readInt2;
        if (this.mContentToRecord != 0 && this.mContentToRecord != 1) {
            throw new java.lang.IllegalArgumentException("contentToRecord was " + this.mContentToRecord + " but must be one of: RECORD_CONTENT_DISPLAY(0), RECORD_CONTENT_TASK(1" + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        }
        this.mDisplayToRecord = readInt3;
        this.mTokenToRecord = readStrongBinder;
        this.mWaitingForConsent = z;
        this.mTargetUid = readInt4;
    }

    public static final class Builder {
        private long mBuilderFieldsSet = 0;
        private int mContentToRecord;
        private int mDisplayToRecord;
        private int mTargetUid;
        private android.os.IBinder mTokenToRecord;
        private int mVirtualDisplayId;
        private boolean mWaitingForConsent;

        public android.view.ContentRecordingSession.Builder setVirtualDisplayId(int i) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 1;
            this.mVirtualDisplayId = i;
            return this;
        }

        public android.view.ContentRecordingSession.Builder setContentToRecord(int i) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 2;
            this.mContentToRecord = i;
            return this;
        }

        public android.view.ContentRecordingSession.Builder setDisplayToRecord(int i) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 4;
            this.mDisplayToRecord = i;
            return this;
        }

        public android.view.ContentRecordingSession.Builder setTokenToRecord(android.os.IBinder iBinder) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 8;
            this.mTokenToRecord = iBinder;
            return this;
        }

        public android.view.ContentRecordingSession.Builder setWaitingForConsent(boolean z) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 16;
            this.mWaitingForConsent = z;
            return this;
        }

        public android.view.ContentRecordingSession.Builder setTargetUid(int i) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 32;
            this.mTargetUid = i;
            return this;
        }

        public android.view.ContentRecordingSession build() {
            checkNotUsed();
            this.mBuilderFieldsSet |= 64;
            if ((this.mBuilderFieldsSet & 1) == 0) {
                this.mVirtualDisplayId = -1;
            }
            if ((this.mBuilderFieldsSet & 2) == 0) {
                this.mContentToRecord = 0;
            }
            if ((this.mBuilderFieldsSet & 4) == 0) {
                this.mDisplayToRecord = -1;
            }
            if ((this.mBuilderFieldsSet & 8) == 0) {
                this.mTokenToRecord = null;
            }
            if ((this.mBuilderFieldsSet & 16) == 0) {
                this.mWaitingForConsent = false;
            }
            if ((this.mBuilderFieldsSet & 32) == 0) {
                this.mTargetUid = -2;
            }
            return new android.view.ContentRecordingSession(this.mVirtualDisplayId, this.mContentToRecord, this.mDisplayToRecord, this.mTokenToRecord, this.mWaitingForConsent, this.mTargetUid);
        }

        private void checkNotUsed() {
            if ((this.mBuilderFieldsSet & 64) != 0) {
                throw new java.lang.IllegalStateException("This Builder should not be reused. Use a new Builder instance instead");
            }
        }
    }

    @java.lang.Deprecated
    private void __metadata() {
    }
}
