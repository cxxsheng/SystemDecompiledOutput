package android.telephony.mbms;

/* loaded from: classes3.dex */
public final class StreamingServiceInfo extends android.telephony.mbms.ServiceInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.telephony.mbms.StreamingServiceInfo> CREATOR = new android.os.Parcelable.Creator<android.telephony.mbms.StreamingServiceInfo>() { // from class: android.telephony.mbms.StreamingServiceInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.mbms.StreamingServiceInfo createFromParcel(android.os.Parcel parcel) {
            return new android.telephony.mbms.StreamingServiceInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.mbms.StreamingServiceInfo[] newArray(int i) {
            return new android.telephony.mbms.StreamingServiceInfo[i];
        }
    };

    @android.annotation.SystemApi
    public StreamingServiceInfo(java.util.Map<java.util.Locale, java.lang.String> map, java.lang.String str, java.util.List<java.util.Locale> list, java.lang.String str2, java.util.Date date, java.util.Date date2) {
        super(map, str, list, str2, date, date2);
    }

    private StreamingServiceInfo(android.os.Parcel parcel) {
        super(parcel);
    }

    @Override // android.telephony.mbms.ServiceInfo, android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
