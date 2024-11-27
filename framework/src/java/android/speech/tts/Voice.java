package android.speech.tts;

/* loaded from: classes3.dex */
public class Voice implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.speech.tts.Voice> CREATOR = new android.os.Parcelable.Creator<android.speech.tts.Voice>() { // from class: android.speech.tts.Voice.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.speech.tts.Voice createFromParcel(android.os.Parcel parcel) {
            return new android.speech.tts.Voice(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.speech.tts.Voice[] newArray(int i) {
            return new android.speech.tts.Voice[i];
        }
    };
    public static final int LATENCY_HIGH = 400;
    public static final int LATENCY_LOW = 200;
    public static final int LATENCY_NORMAL = 300;
    public static final int LATENCY_VERY_HIGH = 500;
    public static final int LATENCY_VERY_LOW = 100;
    public static final int QUALITY_HIGH = 400;
    public static final int QUALITY_LOW = 200;
    public static final int QUALITY_NORMAL = 300;
    public static final int QUALITY_VERY_HIGH = 500;
    public static final int QUALITY_VERY_LOW = 100;
    private final java.util.Set<java.lang.String> mFeatures;
    private final int mLatency;
    private final java.util.Locale mLocale;
    private final java.lang.String mName;
    private final int mQuality;
    private final boolean mRequiresNetworkConnection;

    public Voice(java.lang.String str, java.util.Locale locale, int i, int i2, boolean z, java.util.Set<java.lang.String> set) {
        this.mName = str;
        this.mLocale = locale;
        this.mQuality = i;
        this.mLatency = i2;
        this.mRequiresNetworkConnection = z;
        this.mFeatures = set;
    }

    private Voice(android.os.Parcel parcel) {
        this.mName = parcel.readString();
        this.mLocale = (java.util.Locale) parcel.readSerializable(java.util.Locale.class.getClassLoader(), java.util.Locale.class);
        this.mQuality = parcel.readInt();
        this.mLatency = parcel.readInt();
        this.mRequiresNetworkConnection = parcel.readByte() == 1;
        this.mFeatures = new java.util.HashSet();
        java.util.Collections.addAll(this.mFeatures, parcel.readStringArray());
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.mName);
        parcel.writeSerializable(this.mLocale);
        parcel.writeInt(this.mQuality);
        parcel.writeInt(this.mLatency);
        parcel.writeByte(this.mRequiresNetworkConnection ? (byte) 1 : (byte) 0);
        parcel.writeStringList(new java.util.ArrayList(this.mFeatures));
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public java.util.Locale getLocale() {
        return this.mLocale;
    }

    public int getQuality() {
        return this.mQuality;
    }

    public int getLatency() {
        return this.mLatency;
    }

    public boolean isNetworkConnectionRequired() {
        return this.mRequiresNetworkConnection;
    }

    public java.lang.String getName() {
        return this.mName;
    }

    public java.util.Set<java.lang.String> getFeatures() {
        return this.mFeatures;
    }

    public java.lang.String toString() {
        return new java.lang.StringBuilder(64).append("Voice[Name: ").append(this.mName).append(", locale: ").append(this.mLocale).append(", quality: ").append(this.mQuality).append(", latency: ").append(this.mLatency).append(", requiresNetwork: ").append(this.mRequiresNetworkConnection).append(", features: ").append(this.mFeatures.toString()).append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END).toString();
    }

    public int hashCode() {
        return (((((((((((this.mFeatures == null ? 0 : this.mFeatures.hashCode()) + 31) * 31) + this.mLatency) * 31) + (this.mLocale == null ? 0 : this.mLocale.hashCode())) * 31) + (this.mName != null ? this.mName.hashCode() : 0)) * 31) + this.mQuality) * 31) + (this.mRequiresNetworkConnection ? com.android.internal.logging.nano.MetricsProto.MetricsEvent.AUTOFILL_SERVICE_DISABLED_APP : com.android.internal.logging.nano.MetricsProto.MetricsEvent.ANOMALY_TYPE_UNOPTIMIZED_BT);
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.speech.tts.Voice voice = (android.speech.tts.Voice) obj;
        if (this.mFeatures == null) {
            if (voice.mFeatures != null) {
                return false;
            }
        } else if (!this.mFeatures.equals(voice.mFeatures)) {
            return false;
        }
        if (this.mLatency != voice.mLatency) {
            return false;
        }
        if (this.mLocale == null) {
            if (voice.mLocale != null) {
                return false;
            }
        } else if (!this.mLocale.equals(voice.mLocale)) {
            return false;
        }
        if (this.mName == null) {
            if (voice.mName != null) {
                return false;
            }
        } else if (!this.mName.equals(voice.mName)) {
            return false;
        }
        if (this.mQuality == voice.mQuality && this.mRequiresNetworkConnection == voice.mRequiresNetworkConnection) {
            return true;
        }
        return false;
    }
}
