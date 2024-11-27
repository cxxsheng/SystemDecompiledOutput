package android.telephony;

/* loaded from: classes3.dex */
public final class VisualVoicemailSms implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.telephony.VisualVoicemailSms> CREATOR = new android.os.Parcelable.Creator<android.telephony.VisualVoicemailSms>() { // from class: android.telephony.VisualVoicemailSms.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.VisualVoicemailSms createFromParcel(android.os.Parcel parcel) {
            return new android.telephony.VisualVoicemailSms.Builder().setPhoneAccountHandle((android.telecom.PhoneAccountHandle) parcel.readParcelable(null, android.telecom.PhoneAccountHandle.class)).setPrefix(parcel.readString()).setFields(parcel.readBundle()).setMessageBody(parcel.readString()).build();
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.VisualVoicemailSms[] newArray(int i) {
            return new android.telephony.VisualVoicemailSms[i];
        }
    };
    private final android.os.Bundle mFields;
    private final java.lang.String mMessageBody;
    private final android.telecom.PhoneAccountHandle mPhoneAccountHandle;
    private final java.lang.String mPrefix;

    VisualVoicemailSms(android.telephony.VisualVoicemailSms.Builder builder) {
        this.mPhoneAccountHandle = builder.mPhoneAccountHandle;
        this.mPrefix = builder.mPrefix;
        this.mFields = builder.mFields;
        this.mMessageBody = builder.mMessageBody;
    }

    public android.telecom.PhoneAccountHandle getPhoneAccountHandle() {
        return this.mPhoneAccountHandle;
    }

    public java.lang.String getPrefix() {
        return this.mPrefix;
    }

    public android.os.Bundle getFields() {
        return this.mFields;
    }

    public java.lang.String getMessageBody() {
        return this.mMessageBody;
    }

    public static class Builder {
        private android.os.Bundle mFields;
        private java.lang.String mMessageBody;
        private android.telecom.PhoneAccountHandle mPhoneAccountHandle;
        private java.lang.String mPrefix;

        public android.telephony.VisualVoicemailSms build() {
            return new android.telephony.VisualVoicemailSms(this);
        }

        public android.telephony.VisualVoicemailSms.Builder setPhoneAccountHandle(android.telecom.PhoneAccountHandle phoneAccountHandle) {
            this.mPhoneAccountHandle = phoneAccountHandle;
            return this;
        }

        public android.telephony.VisualVoicemailSms.Builder setPrefix(java.lang.String str) {
            this.mPrefix = str;
            return this;
        }

        public android.telephony.VisualVoicemailSms.Builder setFields(android.os.Bundle bundle) {
            this.mFields = bundle;
            return this;
        }

        public android.telephony.VisualVoicemailSms.Builder setMessageBody(java.lang.String str) {
            this.mMessageBody = str;
            return this;
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeParcelable(getPhoneAccountHandle(), i);
        parcel.writeString(getPrefix());
        parcel.writeBundle(getFields());
        parcel.writeString(getMessageBody());
    }
}
