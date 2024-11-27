package android.telephony.ims;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class RcsContactPresenceTuple implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.telephony.ims.RcsContactPresenceTuple> CREATOR = new android.os.Parcelable.Creator<android.telephony.ims.RcsContactPresenceTuple>() { // from class: android.telephony.ims.RcsContactPresenceTuple.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.ims.RcsContactPresenceTuple createFromParcel(android.os.Parcel parcel) {
            return new android.telephony.ims.RcsContactPresenceTuple(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.ims.RcsContactPresenceTuple[] newArray(int i) {
            return new android.telephony.ims.RcsContactPresenceTuple[i];
        }
    };
    private static final java.lang.String LOG_TAG = "RcsContactPresenceTuple";
    public static final java.lang.String SERVICE_ID_CALL_COMPOSER = "org.3gpp.urn:urn-7:3gpp-service.ims.icsi.gsma.callcomposer";
    public static final java.lang.String SERVICE_ID_CHATBOT = "org.3gpp.urn:urn-7:3gpp-application.ims.iari.rcs.chatbot";
    public static final java.lang.String SERVICE_ID_CHATBOT_ROLE = "org.gsma.rcs.isbot";
    public static final java.lang.String SERVICE_ID_CHATBOT_STANDALONE = " org.3gpp.urn:urn-7:3gpp-application.ims.iari.rcs.chatbot.sa";
    public static final java.lang.String SERVICE_ID_CHAT_V1 = "org.openmobilealliance:IM-session";
    public static final java.lang.String SERVICE_ID_CHAT_V2 = "org.openmobilealliance:ChatSession";
    public static final java.lang.String SERVICE_ID_FT = "org.openmobilealliance:File-Transfer-HTTP";
    public static final java.lang.String SERVICE_ID_FT_OVER_SMS = "org.3gpp.urn:urn-7:3gpp-application.ims.iari.rcs.ftsms";
    public static final java.lang.String SERVICE_ID_GEO_PUSH = "org.3gpp.urn:urn-7:3gpp-application.ims.iari.rcs.geopush";
    public static final java.lang.String SERVICE_ID_GEO_PUSH_VIA_SMS = "org.3gpp.urn:urn-7:3gpp-application.ims.iari.rcs.geosms";
    public static final java.lang.String SERVICE_ID_MMTEL = "org.3gpp.urn:urn-7:3gpp-service.ims.icsi.mmtel";
    public static final java.lang.String SERVICE_ID_POST_CALL = "org.3gpp.urn:urn-7:3gpp-service.ims.icsi.gsma.callunanswered";
    public static final java.lang.String SERVICE_ID_PRESENCE = "org.3gpp.urn:urn-7:3gpp-application.ims.iari.rcse.dp";
    public static final java.lang.String SERVICE_ID_SHARED_MAP = "org.3gpp.urn:urn-7:3gpp-service.ims.icsi.gsma.sharedmap";
    public static final java.lang.String SERVICE_ID_SHARED_SKETCH = "org.3gpp.urn:urn-7:3gpp-service.ims.icsi.gsma.sharedsketch";
    public static final java.lang.String SERVICE_ID_SLM = "org.openmobilealliance:StandaloneMsg";
    public static final java.lang.String TUPLE_BASIC_STATUS_CLOSED = "closed";
    public static final java.lang.String TUPLE_BASIC_STATUS_OPEN = "open";
    private android.net.Uri mContactUri;
    private android.telephony.ims.RcsContactPresenceTuple.ServiceCapabilities mServiceCapabilities;
    private java.lang.String mServiceDescription;
    private java.lang.String mServiceId;
    private java.lang.String mServiceVersion;
    private java.lang.String mStatus;
    private java.time.Instant mTimestamp;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface BasicStatus {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ServiceId {
    }

    public static final class ServiceCapabilities implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.telephony.ims.RcsContactPresenceTuple.ServiceCapabilities> CREATOR = new android.os.Parcelable.Creator<android.telephony.ims.RcsContactPresenceTuple.ServiceCapabilities>() { // from class: android.telephony.ims.RcsContactPresenceTuple.ServiceCapabilities.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.telephony.ims.RcsContactPresenceTuple.ServiceCapabilities createFromParcel(android.os.Parcel parcel) {
                return new android.telephony.ims.RcsContactPresenceTuple.ServiceCapabilities(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.telephony.ims.RcsContactPresenceTuple.ServiceCapabilities[] newArray(int i) {
                return new android.telephony.ims.RcsContactPresenceTuple.ServiceCapabilities[i];
            }
        };
        public static final java.lang.String DUPLEX_MODE_FULL = "full";
        public static final java.lang.String DUPLEX_MODE_HALF = "half";
        public static final java.lang.String DUPLEX_MODE_RECEIVE_ONLY = "receive-only";
        public static final java.lang.String DUPLEX_MODE_SEND_ONLY = "send-only";
        private final boolean mIsAudioCapable;
        private final boolean mIsVideoCapable;
        private final java.util.List<java.lang.String> mSupportedDuplexModeList;
        private final java.util.List<java.lang.String> mUnsupportedDuplexModeList;

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface DuplexMode {
        }

        public static final class Builder {
            private android.telephony.ims.RcsContactPresenceTuple.ServiceCapabilities mCapabilities;

            public Builder(boolean z, boolean z2) {
                this.mCapabilities = new android.telephony.ims.RcsContactPresenceTuple.ServiceCapabilities(z, z2);
            }

            public android.telephony.ims.RcsContactPresenceTuple.ServiceCapabilities.Builder addSupportedDuplexMode(java.lang.String str) {
                this.mCapabilities.mSupportedDuplexModeList.add(str);
                return this;
            }

            public android.telephony.ims.RcsContactPresenceTuple.ServiceCapabilities.Builder addUnsupportedDuplexMode(java.lang.String str) {
                this.mCapabilities.mUnsupportedDuplexModeList.add(str);
                return this;
            }

            public android.telephony.ims.RcsContactPresenceTuple.ServiceCapabilities build() {
                return this.mCapabilities;
            }
        }

        ServiceCapabilities(boolean z, boolean z2) {
            this.mSupportedDuplexModeList = new java.util.ArrayList();
            this.mUnsupportedDuplexModeList = new java.util.ArrayList();
            this.mIsAudioCapable = z;
            this.mIsVideoCapable = z2;
        }

        private ServiceCapabilities(android.os.Parcel parcel) {
            this.mSupportedDuplexModeList = new java.util.ArrayList();
            this.mUnsupportedDuplexModeList = new java.util.ArrayList();
            this.mIsAudioCapable = parcel.readBoolean();
            this.mIsVideoCapable = parcel.readBoolean();
            parcel.readStringList(this.mSupportedDuplexModeList);
            parcel.readStringList(this.mUnsupportedDuplexModeList);
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeBoolean(this.mIsAudioCapable);
            parcel.writeBoolean(this.mIsVideoCapable);
            parcel.writeStringList(this.mSupportedDuplexModeList);
            parcel.writeStringList(this.mUnsupportedDuplexModeList);
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public boolean isAudioCapable() {
            return this.mIsAudioCapable;
        }

        public boolean isVideoCapable() {
            return this.mIsVideoCapable;
        }

        public java.util.List<java.lang.String> getSupportedDuplexModes() {
            return java.util.Collections.unmodifiableList(this.mSupportedDuplexModeList);
        }

        public java.util.List<java.lang.String> getUnsupportedDuplexModes() {
            return java.util.Collections.unmodifiableList(this.mUnsupportedDuplexModeList);
        }

        public java.lang.String toString() {
            return "servCaps{a=" + this.mIsAudioCapable + ", v=" + this.mIsVideoCapable + ", supported=" + this.mSupportedDuplexModeList + ", unsupported=" + this.mUnsupportedDuplexModeList + '}';
        }
    }

    public static final class Builder {
        private final android.telephony.ims.RcsContactPresenceTuple mPresenceTuple;

        public Builder(java.lang.String str, java.lang.String str2, java.lang.String str3) {
            this.mPresenceTuple = new android.telephony.ims.RcsContactPresenceTuple(str, str2, str3);
        }

        public android.telephony.ims.RcsContactPresenceTuple.Builder setContactUri(android.net.Uri uri) {
            this.mPresenceTuple.mContactUri = uri;
            return this;
        }

        public android.telephony.ims.RcsContactPresenceTuple.Builder setTime(java.time.Instant instant) {
            this.mPresenceTuple.mTimestamp = instant;
            return this;
        }

        public android.telephony.ims.RcsContactPresenceTuple.Builder setServiceDescription(java.lang.String str) {
            this.mPresenceTuple.mServiceDescription = str;
            return this;
        }

        public android.telephony.ims.RcsContactPresenceTuple.Builder setServiceCapabilities(android.telephony.ims.RcsContactPresenceTuple.ServiceCapabilities serviceCapabilities) {
            this.mPresenceTuple.mServiceCapabilities = serviceCapabilities;
            return this;
        }

        public android.telephony.ims.RcsContactPresenceTuple build() {
            return this.mPresenceTuple;
        }
    }

    private RcsContactPresenceTuple(java.lang.String str, java.lang.String str2, java.lang.String str3) {
        this.mStatus = str;
        this.mServiceId = str2;
        this.mServiceVersion = str3;
    }

    private RcsContactPresenceTuple(android.os.Parcel parcel) {
        this.mContactUri = (android.net.Uri) parcel.readParcelable(android.net.Uri.class.getClassLoader(), android.net.Uri.class);
        this.mTimestamp = convertStringFormatTimeToInstant(parcel.readString());
        this.mStatus = parcel.readString();
        this.mServiceId = parcel.readString();
        this.mServiceVersion = parcel.readString();
        this.mServiceDescription = parcel.readString();
        this.mServiceCapabilities = (android.telephony.ims.RcsContactPresenceTuple.ServiceCapabilities) parcel.readParcelable(android.telephony.ims.RcsContactPresenceTuple.ServiceCapabilities.class.getClassLoader(), android.telephony.ims.RcsContactPresenceTuple.ServiceCapabilities.class);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeParcelable(this.mContactUri, i);
        parcel.writeString(convertInstantToStringFormat(this.mTimestamp));
        parcel.writeString(this.mStatus);
        parcel.writeString(this.mServiceId);
        parcel.writeString(this.mServiceVersion);
        parcel.writeString(this.mServiceDescription);
        parcel.writeParcelable(this.mServiceCapabilities, i);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private java.lang.String convertInstantToStringFormat(java.time.Instant instant) {
        if (instant == null) {
            return "";
        }
        return instant.toString();
    }

    private java.time.Instant convertStringFormatTimeToInstant(java.lang.String str) {
        if (android.text.TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            return (java.time.Instant) java.time.format.DateTimeFormatter.ISO_OFFSET_DATE_TIME.parse(str, new java.time.temporal.TemporalQuery() { // from class: android.telephony.ims.RcsContactPresenceTuple$$ExternalSyntheticLambda0
                @Override // java.time.temporal.TemporalQuery
                public final java.lang.Object queryFrom(java.time.temporal.TemporalAccessor temporalAccessor) {
                    return java.time.Instant.from(temporalAccessor);
                }
            });
        } catch (java.time.format.DateTimeParseException e) {
            return null;
        }
    }

    public java.lang.String getStatus() {
        return this.mStatus;
    }

    public java.lang.String getServiceId() {
        return this.mServiceId;
    }

    public java.lang.String getServiceVersion() {
        return this.mServiceVersion;
    }

    public android.net.Uri getContactUri() {
        return this.mContactUri;
    }

    public java.time.Instant getTime() {
        return this.mTimestamp;
    }

    public java.lang.String getServiceDescription() {
        return this.mServiceDescription;
    }

    public android.telephony.ims.RcsContactPresenceTuple.ServiceCapabilities getServiceCapabilities() {
        return this.mServiceCapabilities;
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder("{");
        if (android.os.Build.IS_ENG) {
            sb.append("u=");
            sb.append(this.mContactUri);
        } else {
            sb.append("u=");
            sb.append(this.mContactUri != null ? "XXX" : "null");
        }
        sb.append(", id=");
        sb.append(this.mServiceId);
        sb.append(", v=");
        sb.append(this.mServiceVersion);
        sb.append(", s=");
        sb.append(this.mStatus);
        if (this.mTimestamp != null) {
            sb.append(", timestamp=");
            sb.append(this.mTimestamp);
        }
        if (this.mServiceDescription != null) {
            sb.append(", servDesc=");
            sb.append(this.mServiceDescription);
        }
        if (this.mServiceCapabilities != null) {
            sb.append(", servCaps=");
            sb.append(this.mServiceCapabilities);
        }
        sb.append("}");
        return sb.toString();
    }
}
