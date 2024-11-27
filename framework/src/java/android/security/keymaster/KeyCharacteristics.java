package android.security.keymaster;

/* loaded from: classes3.dex */
public class KeyCharacteristics implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.security.keymaster.KeyCharacteristics> CREATOR = new android.os.Parcelable.Creator<android.security.keymaster.KeyCharacteristics>() { // from class: android.security.keymaster.KeyCharacteristics.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.security.keymaster.KeyCharacteristics createFromParcel(android.os.Parcel parcel) {
            return new android.security.keymaster.KeyCharacteristics(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.security.keymaster.KeyCharacteristics[] newArray(int i) {
            return new android.security.keymaster.KeyCharacteristics[i];
        }
    };
    public android.security.keymaster.KeymasterArguments hwEnforced;
    public android.security.keymaster.KeymasterArguments swEnforced;

    public KeyCharacteristics() {
    }

    protected KeyCharacteristics(android.os.Parcel parcel) {
        readFromParcel(parcel);
    }

    public void shallowCopyFrom(android.security.keymaster.KeyCharacteristics keyCharacteristics) {
        this.swEnforced = keyCharacteristics.swEnforced;
        this.hwEnforced = keyCharacteristics.hwEnforced;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        this.swEnforced.writeToParcel(parcel, i);
        this.hwEnforced.writeToParcel(parcel, i);
    }

    public void readFromParcel(android.os.Parcel parcel) {
        this.swEnforced = android.security.keymaster.KeymasterArguments.CREATOR.createFromParcel(parcel);
        this.hwEnforced = android.security.keymaster.KeymasterArguments.CREATOR.createFromParcel(parcel);
    }

    public java.lang.Integer getEnum(int i) {
        if (this.hwEnforced.containsTag(i)) {
            return java.lang.Integer.valueOf(this.hwEnforced.getEnum(i, -1));
        }
        if (this.swEnforced.containsTag(i)) {
            return java.lang.Integer.valueOf(this.swEnforced.getEnum(i, -1));
        }
        return null;
    }

    public java.util.List<java.lang.Integer> getEnums(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        arrayList.addAll(this.hwEnforced.getEnums(i));
        arrayList.addAll(this.swEnforced.getEnums(i));
        return arrayList;
    }

    public long getUnsignedInt(int i, long j) {
        if (this.hwEnforced.containsTag(i)) {
            return this.hwEnforced.getUnsignedInt(i, j);
        }
        return this.swEnforced.getUnsignedInt(i, j);
    }

    public java.util.List<java.math.BigInteger> getUnsignedLongs(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        arrayList.addAll(this.hwEnforced.getUnsignedLongs(i));
        arrayList.addAll(this.swEnforced.getUnsignedLongs(i));
        return arrayList;
    }

    public java.util.Date getDate(int i) {
        java.util.Date date = this.swEnforced.getDate(i, null);
        if (date != null) {
            return date;
        }
        return this.hwEnforced.getDate(i, null);
    }

    public boolean getBoolean(int i) {
        if (this.hwEnforced.containsTag(i)) {
            return this.hwEnforced.getBoolean(i);
        }
        return this.swEnforced.getBoolean(i);
    }
}
