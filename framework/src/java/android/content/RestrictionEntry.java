package android.content;

/* loaded from: classes.dex */
public class RestrictionEntry implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.content.RestrictionEntry> CREATOR = new android.os.Parcelable.Creator<android.content.RestrictionEntry>() { // from class: android.content.RestrictionEntry.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.RestrictionEntry createFromParcel(android.os.Parcel parcel) {
            return new android.content.RestrictionEntry(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.RestrictionEntry[] newArray(int i) {
            return new android.content.RestrictionEntry[i];
        }
    };
    public static final int TYPE_BOOLEAN = 1;
    public static final int TYPE_BUNDLE = 7;
    public static final int TYPE_BUNDLE_ARRAY = 8;
    public static final int TYPE_CHOICE = 2;
    public static final int TYPE_CHOICE_LEVEL = 3;
    public static final int TYPE_INTEGER = 5;
    public static final int TYPE_MULTI_SELECT = 4;
    public static final int TYPE_NULL = 0;
    public static final int TYPE_STRING = 6;
    private java.lang.String[] mChoiceEntries;
    private java.lang.String[] mChoiceValues;
    private java.lang.String mCurrentValue;
    private java.lang.String[] mCurrentValues;
    private java.lang.String mDescription;
    private java.lang.String mKey;
    private android.content.RestrictionEntry[] mRestrictions;
    private java.lang.String mTitle;
    private int mType;

    public RestrictionEntry(int i, java.lang.String str) {
        this.mType = i;
        this.mKey = str;
    }

    public RestrictionEntry(java.lang.String str, java.lang.String str2) {
        this.mKey = str;
        this.mType = 2;
        this.mCurrentValue = str2;
    }

    public RestrictionEntry(java.lang.String str, boolean z) {
        this.mKey = str;
        this.mType = 1;
        setSelectedState(z);
    }

    public RestrictionEntry(java.lang.String str, java.lang.String[] strArr) {
        this.mKey = str;
        this.mType = 4;
        this.mCurrentValues = strArr;
    }

    public RestrictionEntry(java.lang.String str, int i) {
        this.mKey = str;
        this.mType = 5;
        setIntValue(i);
    }

    private RestrictionEntry(java.lang.String str, android.content.RestrictionEntry[] restrictionEntryArr, boolean z) {
        this.mKey = str;
        if (z) {
            this.mType = 8;
            if (restrictionEntryArr != null) {
                for (android.content.RestrictionEntry restrictionEntry : restrictionEntryArr) {
                    if (restrictionEntry.getType() != 7) {
                        throw new java.lang.IllegalArgumentException("bundle_array restriction can only have nested restriction entries of type bundle");
                    }
                }
            }
        } else {
            this.mType = 7;
        }
        setRestrictions(restrictionEntryArr);
    }

    public static android.content.RestrictionEntry createBundleEntry(java.lang.String str, android.content.RestrictionEntry[] restrictionEntryArr) {
        return new android.content.RestrictionEntry(str, restrictionEntryArr, false);
    }

    public static android.content.RestrictionEntry createBundleArrayEntry(java.lang.String str, android.content.RestrictionEntry[] restrictionEntryArr) {
        return new android.content.RestrictionEntry(str, restrictionEntryArr, true);
    }

    public void setType(int i) {
        this.mType = i;
    }

    public int getType() {
        return this.mType;
    }

    public java.lang.String getSelectedString() {
        return this.mCurrentValue;
    }

    public java.lang.String[] getAllSelectedStrings() {
        return this.mCurrentValues;
    }

    public boolean getSelectedState() {
        return java.lang.Boolean.parseBoolean(this.mCurrentValue);
    }

    public int getIntValue() {
        return java.lang.Integer.parseInt(this.mCurrentValue);
    }

    public void setIntValue(int i) {
        this.mCurrentValue = java.lang.Integer.toString(i);
    }

    public void setSelectedString(java.lang.String str) {
        this.mCurrentValue = str;
    }

    public void setSelectedState(boolean z) {
        this.mCurrentValue = java.lang.Boolean.toString(z);
    }

    public void setAllSelectedStrings(java.lang.String[] strArr) {
        this.mCurrentValues = strArr;
    }

    public void setChoiceValues(java.lang.String[] strArr) {
        this.mChoiceValues = strArr;
    }

    public void setChoiceValues(android.content.Context context, int i) {
        this.mChoiceValues = context.getResources().getStringArray(i);
    }

    public android.content.RestrictionEntry[] getRestrictions() {
        return this.mRestrictions;
    }

    public void setRestrictions(android.content.RestrictionEntry[] restrictionEntryArr) {
        this.mRestrictions = restrictionEntryArr;
    }

    public java.lang.String[] getChoiceValues() {
        return this.mChoiceValues;
    }

    public void setChoiceEntries(java.lang.String[] strArr) {
        this.mChoiceEntries = strArr;
    }

    public void setChoiceEntries(android.content.Context context, int i) {
        this.mChoiceEntries = context.getResources().getStringArray(i);
    }

    public java.lang.String[] getChoiceEntries() {
        return this.mChoiceEntries;
    }

    public java.lang.String getDescription() {
        return this.mDescription;
    }

    public void setDescription(java.lang.String str) {
        this.mDescription = str;
    }

    public java.lang.String getKey() {
        return this.mKey;
    }

    public java.lang.String getTitle() {
        return this.mTitle;
    }

    public void setTitle(java.lang.String str) {
        this.mTitle = str;
    }

    public boolean equals(java.lang.Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof android.content.RestrictionEntry)) {
            return false;
        }
        android.content.RestrictionEntry restrictionEntry = (android.content.RestrictionEntry) obj;
        if (this.mType != restrictionEntry.mType || !this.mKey.equals(restrictionEntry.mKey)) {
            return false;
        }
        if (this.mCurrentValues == null && restrictionEntry.mCurrentValues == null && this.mRestrictions == null && restrictionEntry.mRestrictions == null && java.util.Objects.equals(this.mCurrentValue, restrictionEntry.mCurrentValue)) {
            return true;
        }
        if (this.mCurrentValue == null && restrictionEntry.mCurrentValue == null && this.mRestrictions == null && restrictionEntry.mRestrictions == null && java.util.Arrays.equals(this.mCurrentValues, restrictionEntry.mCurrentValues)) {
            return true;
        }
        return this.mCurrentValue == null && restrictionEntry.mCurrentValue == null && this.mCurrentValue == null && restrictionEntry.mCurrentValue == null && java.util.Arrays.equals(this.mRestrictions, restrictionEntry.mRestrictions);
    }

    public int hashCode() {
        int hashCode = 527 + this.mKey.hashCode();
        if (this.mCurrentValue != null) {
            return (hashCode * 31) + this.mCurrentValue.hashCode();
        }
        if (this.mCurrentValues != null) {
            for (java.lang.String str : this.mCurrentValues) {
                if (str != null) {
                    hashCode = (hashCode * 31) + str.hashCode();
                }
            }
            return hashCode;
        }
        if (this.mRestrictions != null) {
            return (hashCode * 31) + java.util.Arrays.hashCode(this.mRestrictions);
        }
        return hashCode;
    }

    public RestrictionEntry(android.os.Parcel parcel) {
        this.mType = parcel.readInt();
        this.mKey = parcel.readString();
        this.mTitle = parcel.readString();
        this.mDescription = parcel.readString();
        this.mChoiceEntries = parcel.readStringArray();
        this.mChoiceValues = parcel.readStringArray();
        this.mCurrentValue = parcel.readString();
        this.mCurrentValues = parcel.readStringArray();
        android.os.Parcelable[] parcelableArr = (android.os.Parcelable[]) parcel.readParcelableArray(null, android.content.RestrictionEntry.class);
        if (parcelableArr != null) {
            this.mRestrictions = new android.content.RestrictionEntry[parcelableArr.length];
            for (int i = 0; i < parcelableArr.length; i++) {
                this.mRestrictions[i] = (android.content.RestrictionEntry) parcelableArr[i];
            }
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mType);
        parcel.writeString(this.mKey);
        parcel.writeString(this.mTitle);
        parcel.writeString(this.mDescription);
        parcel.writeStringArray(this.mChoiceEntries);
        parcel.writeStringArray(this.mChoiceValues);
        parcel.writeString(this.mCurrentValue);
        parcel.writeStringArray(this.mCurrentValues);
        parcel.writeParcelableArray(this.mRestrictions, 0);
    }

    public java.lang.String toString() {
        return "RestrictionEntry{mType=" + this.mType + ", mKey='" + this.mKey + android.text.format.DateFormat.QUOTE + ", mTitle='" + this.mTitle + android.text.format.DateFormat.QUOTE + ", mDescription='" + this.mDescription + android.text.format.DateFormat.QUOTE + ", mChoiceEntries=" + java.util.Arrays.toString(this.mChoiceEntries) + ", mChoiceValues=" + java.util.Arrays.toString(this.mChoiceValues) + ", mCurrentValue='" + this.mCurrentValue + android.text.format.DateFormat.QUOTE + ", mCurrentValues=" + java.util.Arrays.toString(this.mCurrentValues) + ", mRestrictions=" + java.util.Arrays.toString(this.mRestrictions) + '}';
    }
}
