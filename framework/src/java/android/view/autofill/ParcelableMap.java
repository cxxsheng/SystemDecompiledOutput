package android.view.autofill;

/* loaded from: classes4.dex */
class ParcelableMap extends java.util.HashMap<android.view.autofill.AutofillId, android.view.autofill.AutofillValue> implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.view.autofill.ParcelableMap> CREATOR = new android.os.Parcelable.Creator<android.view.autofill.ParcelableMap>() { // from class: android.view.autofill.ParcelableMap.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.autofill.ParcelableMap createFromParcel(android.os.Parcel parcel) {
            int readInt = parcel.readInt();
            android.view.autofill.ParcelableMap parcelableMap = new android.view.autofill.ParcelableMap(readInt);
            for (int i = 0; i < readInt; i++) {
                parcelableMap.put((android.view.autofill.AutofillId) parcel.readParcelable(null, android.view.autofill.AutofillId.class), (android.view.autofill.AutofillValue) parcel.readParcelable(null, android.view.autofill.AutofillValue.class));
            }
            return parcelableMap;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.autofill.ParcelableMap[] newArray(int i) {
            return new android.view.autofill.ParcelableMap[i];
        }
    };

    ParcelableMap(int i) {
        super(i);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(size());
        for (java.util.Map.Entry<android.view.autofill.AutofillId, android.view.autofill.AutofillValue> entry : entrySet()) {
            parcel.writeParcelable(entry.getKey(), 0);
            parcel.writeParcelable(entry.getValue(), 0);
        }
    }
}
