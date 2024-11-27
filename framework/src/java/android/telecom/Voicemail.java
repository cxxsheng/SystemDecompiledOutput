package android.telecom;

/* loaded from: classes3.dex */
public class Voicemail implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.telecom.Voicemail> CREATOR = new android.os.Parcelable.Creator<android.telecom.Voicemail>() { // from class: android.telecom.Voicemail.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telecom.Voicemail createFromParcel(android.os.Parcel parcel) {
            return new android.telecom.Voicemail(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telecom.Voicemail[] newArray(int i) {
            return new android.telecom.Voicemail[i];
        }
    };
    private final java.lang.Long mDuration;
    private final java.lang.Boolean mHasContent;
    private final java.lang.Long mId;
    private final java.lang.Boolean mIsRead;
    private final java.lang.String mNumber;
    private final android.telecom.PhoneAccountHandle mPhoneAccount;
    private final java.lang.String mProviderData;
    private final java.lang.String mSource;
    private final java.lang.Long mTimestamp;
    private final java.lang.String mTranscription;
    private final android.net.Uri mUri;

    private Voicemail(java.lang.Long l, java.lang.String str, android.telecom.PhoneAccountHandle phoneAccountHandle, java.lang.Long l2, java.lang.Long l3, java.lang.String str2, java.lang.String str3, android.net.Uri uri, java.lang.Boolean bool, java.lang.Boolean bool2, java.lang.String str4) {
        this.mTimestamp = l;
        this.mNumber = str;
        this.mPhoneAccount = phoneAccountHandle;
        this.mId = l2;
        this.mDuration = l3;
        this.mSource = str2;
        this.mProviderData = str3;
        this.mUri = uri;
        this.mIsRead = bool;
        this.mHasContent = bool2;
        this.mTranscription = str4;
    }

    public static android.telecom.Voicemail.Builder createForInsertion(long j, java.lang.String str) {
        return new android.telecom.Voicemail.Builder().setNumber(str).setTimestamp(j);
    }

    public static android.telecom.Voicemail.Builder createForUpdate(long j, java.lang.String str) {
        return new android.telecom.Voicemail.Builder().setId(j).setSourceData(str);
    }

    public static class Builder {
        private java.lang.Long mBuilderDuration;
        private boolean mBuilderHasContent;
        private java.lang.Long mBuilderId;
        private java.lang.Boolean mBuilderIsRead;
        private java.lang.String mBuilderNumber;
        private android.telecom.PhoneAccountHandle mBuilderPhoneAccount;
        private java.lang.String mBuilderSourceData;
        private java.lang.String mBuilderSourcePackage;
        private java.lang.Long mBuilderTimestamp;
        private java.lang.String mBuilderTranscription;
        private android.net.Uri mBuilderUri;

        private Builder() {
        }

        public android.telecom.Voicemail.Builder setNumber(java.lang.String str) {
            this.mBuilderNumber = str;
            return this;
        }

        public android.telecom.Voicemail.Builder setTimestamp(long j) {
            this.mBuilderTimestamp = java.lang.Long.valueOf(j);
            return this;
        }

        public android.telecom.Voicemail.Builder setPhoneAccount(android.telecom.PhoneAccountHandle phoneAccountHandle) {
            this.mBuilderPhoneAccount = phoneAccountHandle;
            return this;
        }

        public android.telecom.Voicemail.Builder setId(long j) {
            this.mBuilderId = java.lang.Long.valueOf(j);
            return this;
        }

        public android.telecom.Voicemail.Builder setDuration(long j) {
            this.mBuilderDuration = java.lang.Long.valueOf(j);
            return this;
        }

        public android.telecom.Voicemail.Builder setSourcePackage(java.lang.String str) {
            this.mBuilderSourcePackage = str;
            return this;
        }

        public android.telecom.Voicemail.Builder setSourceData(java.lang.String str) {
            this.mBuilderSourceData = str;
            return this;
        }

        public android.telecom.Voicemail.Builder setUri(android.net.Uri uri) {
            this.mBuilderUri = uri;
            return this;
        }

        public android.telecom.Voicemail.Builder setIsRead(boolean z) {
            this.mBuilderIsRead = java.lang.Boolean.valueOf(z);
            return this;
        }

        public android.telecom.Voicemail.Builder setHasContent(boolean z) {
            this.mBuilderHasContent = z;
            return this;
        }

        public android.telecom.Voicemail.Builder setTranscription(java.lang.String str) {
            this.mBuilderTranscription = str;
            return this;
        }

        public android.telecom.Voicemail build() {
            this.mBuilderId = java.lang.Long.valueOf(this.mBuilderId == null ? -1L : this.mBuilderId.longValue());
            this.mBuilderTimestamp = java.lang.Long.valueOf(this.mBuilderTimestamp == null ? 0L : this.mBuilderTimestamp.longValue());
            this.mBuilderDuration = java.lang.Long.valueOf(this.mBuilderDuration != null ? this.mBuilderDuration.longValue() : 0L);
            this.mBuilderIsRead = java.lang.Boolean.valueOf(this.mBuilderIsRead == null ? false : this.mBuilderIsRead.booleanValue());
            return new android.telecom.Voicemail(this.mBuilderTimestamp, this.mBuilderNumber, this.mBuilderPhoneAccount, this.mBuilderId, this.mBuilderDuration, this.mBuilderSourcePackage, this.mBuilderSourceData, this.mBuilderUri, this.mBuilderIsRead, java.lang.Boolean.valueOf(this.mBuilderHasContent), this.mBuilderTranscription);
        }
    }

    public long getId() {
        return this.mId.longValue();
    }

    public java.lang.String getNumber() {
        return this.mNumber;
    }

    public android.telecom.PhoneAccountHandle getPhoneAccount() {
        return this.mPhoneAccount;
    }

    public long getTimestampMillis() {
        return this.mTimestamp.longValue();
    }

    public long getDuration() {
        return this.mDuration.longValue();
    }

    public java.lang.String getSourcePackage() {
        return this.mSource;
    }

    public java.lang.String getSourceData() {
        return this.mProviderData;
    }

    public android.net.Uri getUri() {
        return this.mUri;
    }

    public boolean isRead() {
        return this.mIsRead.booleanValue();
    }

    public boolean hasContent() {
        return this.mHasContent.booleanValue();
    }

    public java.lang.String getTranscription() {
        return this.mTranscription;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeLong(this.mTimestamp.longValue());
        parcel.writeCharSequence(this.mNumber);
        if (this.mPhoneAccount == null) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(1);
            this.mPhoneAccount.writeToParcel(parcel, i);
        }
        parcel.writeLong(this.mId.longValue());
        parcel.writeLong(this.mDuration.longValue());
        parcel.writeCharSequence(this.mSource);
        parcel.writeCharSequence(this.mProviderData);
        if (this.mUri == null) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(1);
            this.mUri.writeToParcel(parcel, i);
        }
        if (this.mIsRead.booleanValue()) {
            parcel.writeInt(1);
        } else {
            parcel.writeInt(0);
        }
        if (this.mHasContent.booleanValue()) {
            parcel.writeInt(1);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeCharSequence(this.mTranscription);
    }

    private Voicemail(android.os.Parcel parcel) {
        this.mTimestamp = java.lang.Long.valueOf(parcel.readLong());
        this.mNumber = (java.lang.String) parcel.readCharSequence();
        if (parcel.readInt() > 0) {
            this.mPhoneAccount = android.telecom.PhoneAccountHandle.CREATOR.createFromParcel(parcel);
        } else {
            this.mPhoneAccount = null;
        }
        this.mId = java.lang.Long.valueOf(parcel.readLong());
        this.mDuration = java.lang.Long.valueOf(parcel.readLong());
        this.mSource = (java.lang.String) parcel.readCharSequence();
        this.mProviderData = (java.lang.String) parcel.readCharSequence();
        if (parcel.readInt() > 0) {
            this.mUri = android.net.Uri.CREATOR.createFromParcel(parcel);
        } else {
            this.mUri = null;
        }
        this.mIsRead = java.lang.Boolean.valueOf(parcel.readInt() > 0);
        this.mHasContent = java.lang.Boolean.valueOf(parcel.readInt() > 0);
        this.mTranscription = (java.lang.String) parcel.readCharSequence();
    }
}
