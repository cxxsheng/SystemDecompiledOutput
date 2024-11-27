package android.app.admin;

/* loaded from: classes.dex */
public class ParcelableGranteeMap implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.admin.ParcelableGranteeMap> CREATOR = new android.os.Parcelable.Creator<android.app.admin.ParcelableGranteeMap>() { // from class: android.app.admin.ParcelableGranteeMap.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.admin.ParcelableGranteeMap createFromParcel(android.os.Parcel parcel) {
            android.util.ArrayMap arrayMap = new android.util.ArrayMap();
            int readInt = parcel.readInt();
            for (int i = 0; i < readInt; i++) {
                arrayMap.put(java.lang.Integer.valueOf(parcel.readInt()), new android.util.ArraySet(parcel.readStringArray()));
            }
            return new android.app.admin.ParcelableGranteeMap(arrayMap);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.admin.ParcelableGranteeMap[] newArray(int i) {
            return new android.app.admin.ParcelableGranteeMap[i];
        }
    };
    private final java.util.Map<java.lang.Integer, java.util.Set<java.lang.String>> mPackagesByUid;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mPackagesByUid.size());
        for (java.util.Map.Entry<java.lang.Integer, java.util.Set<java.lang.String>> entry : this.mPackagesByUid.entrySet()) {
            parcel.writeInt(entry.getKey().intValue());
            parcel.writeStringArray((java.lang.String[]) entry.getValue().toArray(new java.lang.String[0]));
        }
    }

    public ParcelableGranteeMap(java.util.Map<java.lang.Integer, java.util.Set<java.lang.String>> map) {
        this.mPackagesByUid = map;
    }

    public java.util.Map<java.lang.Integer, java.util.Set<java.lang.String>> getPackagesByUid() {
        return this.mPackagesByUid;
    }
}
