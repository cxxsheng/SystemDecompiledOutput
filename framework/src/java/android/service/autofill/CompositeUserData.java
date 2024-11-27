package android.service.autofill;

/* loaded from: classes3.dex */
public final class CompositeUserData implements android.service.autofill.FieldClassificationUserData, android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.service.autofill.CompositeUserData> CREATOR = new android.os.Parcelable.Creator<android.service.autofill.CompositeUserData>() { // from class: android.service.autofill.CompositeUserData.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.autofill.CompositeUserData createFromParcel(android.os.Parcel parcel) {
            return new android.service.autofill.CompositeUserData((android.service.autofill.UserData) parcel.readParcelable(null, android.service.autofill.UserData.class), (android.service.autofill.UserData) parcel.readParcelable(null, android.service.autofill.UserData.class));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.autofill.CompositeUserData[] newArray(int i) {
            return new android.service.autofill.CompositeUserData[i];
        }
    };
    private final java.lang.String[] mCategories;
    private final android.service.autofill.UserData mGenericUserData;
    private final android.service.autofill.UserData mPackageUserData;
    private final java.lang.String[] mValues;

    public CompositeUserData(android.service.autofill.UserData userData, android.service.autofill.UserData userData2) {
        this.mGenericUserData = userData;
        this.mPackageUserData = userData2;
        java.lang.String[] categoryIds = this.mPackageUserData.getCategoryIds();
        java.lang.String[] values = this.mPackageUserData.getValues();
        java.util.ArrayList arrayList = new java.util.ArrayList(categoryIds.length);
        java.util.ArrayList arrayList2 = new java.util.ArrayList(values.length);
        java.util.Collections.addAll(arrayList, categoryIds);
        java.util.Collections.addAll(arrayList2, values);
        if (this.mGenericUserData != null) {
            java.lang.String[] categoryIds2 = this.mGenericUserData.getCategoryIds();
            java.lang.String[] values2 = this.mGenericUserData.getValues();
            int length = this.mGenericUserData.getCategoryIds().length;
            for (int i = 0; i < length; i++) {
                if (!arrayList.contains(categoryIds2[i])) {
                    arrayList.add(categoryIds2[i]);
                    arrayList2.add(values2[i]);
                }
            }
        }
        this.mCategories = new java.lang.String[arrayList.size()];
        arrayList.toArray(this.mCategories);
        this.mValues = new java.lang.String[arrayList2.size()];
        arrayList2.toArray(this.mValues);
    }

    @Override // android.service.autofill.FieldClassificationUserData
    public java.lang.String getFieldClassificationAlgorithm() {
        java.lang.String fieldClassificationAlgorithm = this.mPackageUserData.getFieldClassificationAlgorithm();
        if (fieldClassificationAlgorithm != null) {
            return fieldClassificationAlgorithm;
        }
        if (this.mGenericUserData == null) {
            return null;
        }
        return this.mGenericUserData.getFieldClassificationAlgorithm();
    }

    @Override // android.service.autofill.FieldClassificationUserData
    public android.os.Bundle getDefaultFieldClassificationArgs() {
        android.os.Bundle defaultFieldClassificationArgs = this.mPackageUserData.getDefaultFieldClassificationArgs();
        if (defaultFieldClassificationArgs != null) {
            return defaultFieldClassificationArgs;
        }
        if (this.mGenericUserData == null) {
            return null;
        }
        return this.mGenericUserData.getDefaultFieldClassificationArgs();
    }

    @Override // android.service.autofill.FieldClassificationUserData
    public java.lang.String getFieldClassificationAlgorithmForCategory(java.lang.String str) {
        java.util.Objects.requireNonNull(str);
        android.util.ArrayMap<java.lang.String, java.lang.String> fieldClassificationAlgorithms = getFieldClassificationAlgorithms();
        if (fieldClassificationAlgorithms == null || !fieldClassificationAlgorithms.containsKey(str)) {
            return null;
        }
        return fieldClassificationAlgorithms.get(str);
    }

    @Override // android.service.autofill.FieldClassificationUserData
    public android.util.ArrayMap<java.lang.String, java.lang.String> getFieldClassificationAlgorithms() {
        android.util.ArrayMap<java.lang.String, java.lang.String> fieldClassificationAlgorithms = this.mPackageUserData.getFieldClassificationAlgorithms();
        android.util.ArrayMap<java.lang.String, java.lang.String> arrayMap = null;
        android.util.ArrayMap<java.lang.String, java.lang.String> fieldClassificationAlgorithms2 = this.mGenericUserData == null ? null : this.mGenericUserData.getFieldClassificationAlgorithms();
        if (fieldClassificationAlgorithms != null || fieldClassificationAlgorithms2 != null) {
            arrayMap = new android.util.ArrayMap<>();
            if (fieldClassificationAlgorithms2 != null) {
                arrayMap.putAll((android.util.ArrayMap<? extends java.lang.String, ? extends java.lang.String>) fieldClassificationAlgorithms2);
            }
            if (fieldClassificationAlgorithms != null) {
                arrayMap.putAll((android.util.ArrayMap<? extends java.lang.String, ? extends java.lang.String>) fieldClassificationAlgorithms);
            }
        }
        return arrayMap;
    }

    @Override // android.service.autofill.FieldClassificationUserData
    public android.util.ArrayMap<java.lang.String, android.os.Bundle> getFieldClassificationArgs() {
        android.util.ArrayMap<java.lang.String, android.os.Bundle> fieldClassificationArgs = this.mPackageUserData.getFieldClassificationArgs();
        android.util.ArrayMap<java.lang.String, android.os.Bundle> arrayMap = null;
        android.util.ArrayMap<java.lang.String, android.os.Bundle> fieldClassificationArgs2 = this.mGenericUserData == null ? null : this.mGenericUserData.getFieldClassificationArgs();
        if (fieldClassificationArgs != null || fieldClassificationArgs2 != null) {
            arrayMap = new android.util.ArrayMap<>();
            if (fieldClassificationArgs2 != null) {
                arrayMap.putAll((android.util.ArrayMap<? extends java.lang.String, ? extends android.os.Bundle>) fieldClassificationArgs2);
            }
            if (fieldClassificationArgs != null) {
                arrayMap.putAll((android.util.ArrayMap<? extends java.lang.String, ? extends android.os.Bundle>) fieldClassificationArgs);
            }
        }
        return arrayMap;
    }

    @Override // android.service.autofill.FieldClassificationUserData
    public java.lang.String[] getCategoryIds() {
        return this.mCategories;
    }

    @Override // android.service.autofill.FieldClassificationUserData
    public java.lang.String[] getValues() {
        return this.mValues;
    }

    public java.lang.String toString() {
        return !android.view.autofill.Helper.sDebug ? super.toString() : "genericUserData=" + this.mGenericUserData + ", packageUserData=" + this.mPackageUserData;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeParcelable(this.mGenericUserData, 0);
        parcel.writeParcelable(this.mPackageUserData, 0);
    }
}
