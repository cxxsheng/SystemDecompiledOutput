package android.telephony;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class PhoneNumberRange implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.telephony.PhoneNumberRange> CREATOR = new android.os.Parcelable.Creator<android.telephony.PhoneNumberRange>() { // from class: android.telephony.PhoneNumberRange.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.PhoneNumberRange createFromParcel(android.os.Parcel parcel) {
            return new android.telephony.PhoneNumberRange(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.PhoneNumberRange[] newArray(int i) {
            return new android.telephony.PhoneNumberRange[i];
        }
    };
    private final java.lang.String mCountryCode;
    private final java.lang.String mLowerBound;
    private final java.lang.String mPrefix;
    private final java.lang.String mUpperBound;

    public PhoneNumberRange(java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4) {
        validateLowerAndUpperBounds(str3, str4);
        if (!java.util.regex.Pattern.matches("[0-9]*", str)) {
            throw new java.lang.IllegalArgumentException("Country code must be all numeric");
        }
        if (!java.util.regex.Pattern.matches("[0-9]*", str2)) {
            throw new java.lang.IllegalArgumentException("Prefix must be all numeric");
        }
        this.mCountryCode = str;
        this.mPrefix = str2;
        this.mLowerBound = str3;
        this.mUpperBound = str4;
    }

    private PhoneNumberRange(android.os.Parcel parcel) {
        this.mCountryCode = parcel.readString();
        this.mPrefix = parcel.readString();
        this.mLowerBound = parcel.readString();
        this.mUpperBound = parcel.readString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.mCountryCode);
        parcel.writeString(this.mPrefix);
        parcel.writeString(this.mLowerBound);
        parcel.writeString(this.mUpperBound);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.telephony.PhoneNumberRange phoneNumberRange = (android.telephony.PhoneNumberRange) obj;
        if (java.util.Objects.equals(this.mCountryCode, phoneNumberRange.mCountryCode) && java.util.Objects.equals(this.mPrefix, phoneNumberRange.mPrefix) && java.util.Objects.equals(this.mLowerBound, phoneNumberRange.mLowerBound) && java.util.Objects.equals(this.mUpperBound, phoneNumberRange.mUpperBound)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(this.mCountryCode, this.mPrefix, this.mLowerBound, this.mUpperBound);
    }

    public java.lang.String toString() {
        return "PhoneNumberRange{mCountryCode='" + this.mCountryCode + android.text.format.DateFormat.QUOTE + ", mPrefix='" + this.mPrefix + android.text.format.DateFormat.QUOTE + ", mLowerBound='" + this.mLowerBound + android.text.format.DateFormat.QUOTE + ", mUpperBound='" + this.mUpperBound + android.text.format.DateFormat.QUOTE + '}';
    }

    private void validateLowerAndUpperBounds(java.lang.String str, java.lang.String str2) {
        if (str.length() != str2.length()) {
            throw new java.lang.IllegalArgumentException("Lower and upper bounds must have the same length");
        }
        if (!java.util.regex.Pattern.matches("[0-9]*", str)) {
            throw new java.lang.IllegalArgumentException("Lower bound must be all numeric");
        }
        if (!java.util.regex.Pattern.matches("[0-9]*", str2)) {
            throw new java.lang.IllegalArgumentException("Upper bound must be all numeric");
        }
        if (java.lang.Integer.parseInt(str) > java.lang.Integer.parseInt(str2)) {
            throw new java.lang.IllegalArgumentException("Lower bound must be lower than upper bound");
        }
    }

    public boolean matches(java.lang.String str) {
        java.lang.String substring;
        java.lang.String replaceAll = str.replaceAll("[^0-9]", "");
        java.lang.String str2 = this.mCountryCode + this.mPrefix;
        if (replaceAll.startsWith(str2)) {
            substring = replaceAll.substring(str2.length());
        } else {
            if (!replaceAll.startsWith(this.mPrefix)) {
                return false;
            }
            substring = replaceAll.substring(this.mPrefix.length());
        }
        try {
            int parseInt = java.lang.Integer.parseInt(this.mLowerBound);
            int parseInt2 = java.lang.Integer.parseInt(this.mUpperBound);
            int parseInt3 = java.lang.Integer.parseInt(substring);
            return parseInt3 <= parseInt2 && parseInt3 >= parseInt;
        } catch (java.lang.NumberFormatException e) {
            android.util.Log.e(android.telephony.PhoneNumberRange.class.getSimpleName(), "Invalid bounds or number.", e);
            return false;
        }
    }
}
