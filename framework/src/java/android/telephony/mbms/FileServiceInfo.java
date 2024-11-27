package android.telephony.mbms;

/* loaded from: classes3.dex */
public final class FileServiceInfo extends android.telephony.mbms.ServiceInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.telephony.mbms.FileServiceInfo> CREATOR = new android.os.Parcelable.Creator<android.telephony.mbms.FileServiceInfo>() { // from class: android.telephony.mbms.FileServiceInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.mbms.FileServiceInfo createFromParcel(android.os.Parcel parcel) {
            return new android.telephony.mbms.FileServiceInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.mbms.FileServiceInfo[] newArray(int i) {
            return new android.telephony.mbms.FileServiceInfo[i];
        }
    };
    private final java.util.List<android.telephony.mbms.FileInfo> files;

    @android.annotation.SystemApi
    public FileServiceInfo(java.util.Map<java.util.Locale, java.lang.String> map, java.lang.String str, java.util.List<java.util.Locale> list, java.lang.String str2, java.util.Date date, java.util.Date date2, java.util.List<android.telephony.mbms.FileInfo> list2) {
        super(map, str, list, str2, date, date2);
        this.files = new java.util.ArrayList(list2);
    }

    FileServiceInfo(android.os.Parcel parcel) {
        super(parcel);
        this.files = new java.util.ArrayList();
        parcel.readList(this.files, android.telephony.mbms.FileInfo.class.getClassLoader(), android.telephony.mbms.FileInfo.class);
    }

    @Override // android.telephony.mbms.ServiceInfo, android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeList(this.files);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public java.util.List<android.telephony.mbms.FileInfo> getFiles() {
        return this.files;
    }
}
