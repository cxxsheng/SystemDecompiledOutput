package android.content.pm;

/* loaded from: classes.dex */
public final class ProviderInfoList implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.content.pm.ProviderInfoList> CREATOR = new android.os.Parcelable.Creator<android.content.pm.ProviderInfoList>() { // from class: android.content.pm.ProviderInfoList.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.pm.ProviderInfoList createFromParcel(android.os.Parcel parcel) {
            return new android.content.pm.ProviderInfoList(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.pm.ProviderInfoList[] newArray(int i) {
            return new android.content.pm.ProviderInfoList[i];
        }
    };
    private final java.util.List<android.content.pm.ProviderInfo> mList;

    private ProviderInfoList(android.os.Parcel parcel) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        parcel.readTypedList(arrayList, android.content.pm.ProviderInfo.CREATOR);
        this.mList = arrayList;
    }

    private ProviderInfoList(java.util.List<android.content.pm.ProviderInfo> list) {
        this.mList = list;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        boolean allowSquashing = parcel.allowSquashing();
        parcel.writeTypedList(this.mList, i);
        parcel.restoreAllowSquashing(allowSquashing);
    }

    public java.util.List<android.content.pm.ProviderInfo> getList() {
        return this.mList;
    }

    public static android.content.pm.ProviderInfoList fromList(java.util.List<android.content.pm.ProviderInfo> list) {
        return new android.content.pm.ProviderInfoList(list);
    }
}
