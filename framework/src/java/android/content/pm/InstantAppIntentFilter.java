package android.content.pm;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class InstantAppIntentFilter implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.content.pm.InstantAppIntentFilter> CREATOR = new android.os.Parcelable.Creator<android.content.pm.InstantAppIntentFilter>() { // from class: android.content.pm.InstantAppIntentFilter.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.pm.InstantAppIntentFilter createFromParcel(android.os.Parcel parcel) {
            return new android.content.pm.InstantAppIntentFilter(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.pm.InstantAppIntentFilter[] newArray(int i) {
            return new android.content.pm.InstantAppIntentFilter[i];
        }
    };
    private final java.util.List<android.content.IntentFilter> mFilters = new java.util.ArrayList();
    private final java.lang.String mSplitName;

    public InstantAppIntentFilter(java.lang.String str, java.util.List<android.content.IntentFilter> list) {
        if (list == null || list.size() == 0) {
            throw new java.lang.IllegalArgumentException();
        }
        this.mSplitName = str;
        this.mFilters.addAll(list);
    }

    InstantAppIntentFilter(android.os.Parcel parcel) {
        this.mSplitName = parcel.readString();
        parcel.readList(this.mFilters, getClass().getClassLoader(), android.content.IntentFilter.class);
    }

    public java.lang.String getSplitName() {
        return this.mSplitName;
    }

    public java.util.List<android.content.IntentFilter> getFilters() {
        return this.mFilters;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.mSplitName);
        parcel.writeList(this.mFilters);
    }
}
