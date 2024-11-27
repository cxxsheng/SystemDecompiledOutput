package android.telephony;

/* loaded from: classes3.dex */
public final class VisualVoicemailSmsFilterSettings implements android.os.Parcelable {
    public static final java.lang.String DEFAULT_CLIENT_PREFIX = "//VVM";
    public static final int DEFAULT_DESTINATION_PORT = -1;
    public static final int DESTINATION_PORT_ANY = -1;
    public static final int DESTINATION_PORT_DATA_SMS = -2;
    public final java.lang.String clientPrefix;
    public final int destinationPort;
    public final java.util.List<java.lang.String> originatingNumbers;
    public final java.lang.String packageName;
    public static final java.util.List<java.lang.String> DEFAULT_ORIGINATING_NUMBERS = java.util.Collections.emptyList();
    public static final android.os.Parcelable.Creator<android.telephony.VisualVoicemailSmsFilterSettings> CREATOR = new android.os.Parcelable.Creator<android.telephony.VisualVoicemailSmsFilterSettings>() { // from class: android.telephony.VisualVoicemailSmsFilterSettings.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.VisualVoicemailSmsFilterSettings createFromParcel(android.os.Parcel parcel) {
            android.telephony.VisualVoicemailSmsFilterSettings.Builder builder = new android.telephony.VisualVoicemailSmsFilterSettings.Builder();
            builder.setClientPrefix(parcel.readString());
            builder.setOriginatingNumbers(parcel.createStringArrayList());
            builder.setDestinationPort(parcel.readInt());
            builder.setPackageName(parcel.readString());
            return builder.build();
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.VisualVoicemailSmsFilterSettings[] newArray(int i) {
            return new android.telephony.VisualVoicemailSmsFilterSettings[i];
        }
    };

    public static class Builder {
        private java.lang.String mPackageName;
        private java.lang.String mClientPrefix = android.telephony.VisualVoicemailSmsFilterSettings.DEFAULT_CLIENT_PREFIX;
        private java.util.List<java.lang.String> mOriginatingNumbers = android.telephony.VisualVoicemailSmsFilterSettings.DEFAULT_ORIGINATING_NUMBERS;
        private int mDestinationPort = -1;

        public android.telephony.VisualVoicemailSmsFilterSettings build() {
            return new android.telephony.VisualVoicemailSmsFilterSettings(this);
        }

        public android.telephony.VisualVoicemailSmsFilterSettings.Builder setClientPrefix(java.lang.String str) {
            if (str == null) {
                throw new java.lang.IllegalArgumentException("Client prefix cannot be null");
            }
            this.mClientPrefix = str;
            return this;
        }

        public android.telephony.VisualVoicemailSmsFilterSettings.Builder setOriginatingNumbers(java.util.List<java.lang.String> list) {
            if (list == null) {
                throw new java.lang.IllegalArgumentException("Originating numbers cannot be null");
            }
            this.mOriginatingNumbers = list;
            return this;
        }

        public android.telephony.VisualVoicemailSmsFilterSettings.Builder setDestinationPort(int i) {
            this.mDestinationPort = i;
            return this;
        }

        public android.telephony.VisualVoicemailSmsFilterSettings.Builder setPackageName(java.lang.String str) {
            this.mPackageName = str;
            return this;
        }
    }

    private VisualVoicemailSmsFilterSettings(android.telephony.VisualVoicemailSmsFilterSettings.Builder builder) {
        this.clientPrefix = builder.mClientPrefix;
        this.originatingNumbers = builder.mOriginatingNumbers;
        this.destinationPort = builder.mDestinationPort;
        this.packageName = builder.mPackageName;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.clientPrefix);
        parcel.writeStringList(this.originatingNumbers);
        parcel.writeInt(this.destinationPort);
        parcel.writeString(this.packageName);
    }

    public java.lang.String toString() {
        return "[VisualVoicemailSmsFilterSettings clientPrefix=" + this.clientPrefix + ", originatingNumbers=" + this.originatingNumbers + ", destinationPort=" + this.destinationPort + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END;
    }
}
