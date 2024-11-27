package android.service.autofill;

/* loaded from: classes3.dex */
public final class UserData implements android.service.autofill.FieldClassificationUserData, android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.service.autofill.UserData> CREATOR = new android.os.Parcelable.Creator<android.service.autofill.UserData>() { // from class: android.service.autofill.UserData.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.autofill.UserData createFromParcel(android.os.Parcel parcel) {
            java.lang.String readString = parcel.readString();
            java.lang.String[] readStringArray = parcel.readStringArray();
            java.lang.String[] readStringArray2 = parcel.readStringArray();
            java.lang.String readString2 = parcel.readString();
            android.os.Bundle readBundle = parcel.readBundle();
            android.util.ArrayMap arrayMap = new android.util.ArrayMap();
            parcel.readMap(arrayMap, java.lang.String.class.getClassLoader());
            java.util.Map arrayMap2 = new android.util.ArrayMap();
            parcel.readMap(arrayMap2, android.os.Bundle.class.getClassLoader());
            android.service.autofill.UserData.Builder fieldClassificationAlgorithm = new android.service.autofill.UserData.Builder(readString, readStringArray2[0], readStringArray[0]).setFieldClassificationAlgorithm(readString2, readBundle);
            for (int i = 1; i < readStringArray.length; i++) {
                fieldClassificationAlgorithm.add(readStringArray2[i], readStringArray[i]);
            }
            int size = arrayMap.size();
            if (size > 0) {
                for (int i2 = 0; i2 < size; i2++) {
                    java.lang.String str = (java.lang.String) arrayMap.keyAt(i2);
                    fieldClassificationAlgorithm.setFieldClassificationAlgorithmForCategory(str, (java.lang.String) arrayMap.valueAt(i2), (android.os.Bundle) arrayMap2.get(str));
                }
            }
            return fieldClassificationAlgorithm.build();
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.autofill.UserData[] newArray(int i) {
            return new android.service.autofill.UserData[i];
        }
    };
    private static final int DEFAULT_MAX_CATEGORY_COUNT = 10;
    private static final int DEFAULT_MAX_FIELD_CLASSIFICATION_IDS_SIZE = 10;
    private static final int DEFAULT_MAX_USER_DATA_SIZE = 50;
    private static final int DEFAULT_MAX_VALUE_LENGTH = 100;
    private static final int DEFAULT_MIN_VALUE_LENGTH = 3;
    private static final java.lang.String TAG = "UserData";
    private final android.util.ArrayMap<java.lang.String, java.lang.String> mCategoryAlgorithms;
    private final android.util.ArrayMap<java.lang.String, android.os.Bundle> mCategoryArgs;
    private final java.lang.String[] mCategoryIds;
    private final java.lang.String mDefaultAlgorithm;
    private final android.os.Bundle mDefaultArgs;
    private final java.lang.String mId;
    private final java.lang.String[] mValues;

    private UserData(android.service.autofill.UserData.Builder builder) {
        this.mId = builder.mId;
        this.mCategoryIds = new java.lang.String[builder.mCategoryIds.size()];
        builder.mCategoryIds.toArray(this.mCategoryIds);
        this.mValues = new java.lang.String[builder.mValues.size()];
        builder.mValues.toArray(this.mValues);
        builder.mValues.toArray(this.mValues);
        this.mDefaultAlgorithm = builder.mDefaultAlgorithm;
        this.mDefaultArgs = builder.mDefaultArgs;
        this.mCategoryAlgorithms = builder.mCategoryAlgorithms;
        this.mCategoryArgs = builder.mCategoryArgs;
    }

    @Override // android.service.autofill.FieldClassificationUserData
    public java.lang.String getFieldClassificationAlgorithm() {
        return this.mDefaultAlgorithm;
    }

    @Override // android.service.autofill.FieldClassificationUserData
    public android.os.Bundle getDefaultFieldClassificationArgs() {
        return this.mDefaultArgs;
    }

    @Override // android.service.autofill.FieldClassificationUserData
    public java.lang.String getFieldClassificationAlgorithmForCategory(java.lang.String str) {
        java.util.Objects.requireNonNull(str);
        if (this.mCategoryAlgorithms == null || !this.mCategoryAlgorithms.containsKey(str)) {
            return null;
        }
        return this.mCategoryAlgorithms.get(str);
    }

    public java.lang.String getId() {
        return this.mId;
    }

    @Override // android.service.autofill.FieldClassificationUserData
    public java.lang.String[] getCategoryIds() {
        return this.mCategoryIds;
    }

    @Override // android.service.autofill.FieldClassificationUserData
    public java.lang.String[] getValues() {
        return this.mValues;
    }

    @Override // android.service.autofill.FieldClassificationUserData
    public android.util.ArrayMap<java.lang.String, java.lang.String> getFieldClassificationAlgorithms() {
        return this.mCategoryAlgorithms;
    }

    @Override // android.service.autofill.FieldClassificationUserData
    public android.util.ArrayMap<java.lang.String, android.os.Bundle> getFieldClassificationArgs() {
        return this.mCategoryArgs;
    }

    public void dump(java.lang.String str, java.io.PrintWriter printWriter) {
        printWriter.print(str);
        printWriter.print("id: ");
        printWriter.print(this.mId);
        printWriter.print(str);
        printWriter.print("Default Algorithm: ");
        printWriter.print(this.mDefaultAlgorithm);
        printWriter.print(str);
        printWriter.print("Default Args");
        printWriter.print(this.mDefaultArgs);
        if (this.mCategoryAlgorithms != null && this.mCategoryAlgorithms.size() > 0) {
            printWriter.print(str);
            printWriter.print("Algorithms per category: ");
            for (int i = 0; i < this.mCategoryAlgorithms.size(); i++) {
                printWriter.print(str);
                printWriter.print(str);
                printWriter.print(this.mCategoryAlgorithms.keyAt(i));
                printWriter.print(": ");
                printWriter.println(android.view.autofill.Helper.getRedacted(this.mCategoryAlgorithms.valueAt(i)));
                printWriter.print("args=");
                printWriter.print(this.mCategoryArgs.get(this.mCategoryAlgorithms.keyAt(i)));
            }
        }
        printWriter.print(str);
        printWriter.print("Field ids size: ");
        printWriter.println(this.mCategoryIds.length);
        for (int i2 = 0; i2 < this.mCategoryIds.length; i2++) {
            printWriter.print(str);
            printWriter.print(str);
            printWriter.print(i2);
            printWriter.print(": ");
            printWriter.println(android.view.autofill.Helper.getRedacted(this.mCategoryIds[i2]));
        }
        printWriter.print(str);
        printWriter.print("Values size: ");
        printWriter.println(this.mValues.length);
        for (int i3 = 0; i3 < this.mValues.length; i3++) {
            printWriter.print(str);
            printWriter.print(str);
            printWriter.print(i3);
            printWriter.print(": ");
            printWriter.println(android.view.autofill.Helper.getRedacted(this.mValues[i3]));
        }
    }

    public static void dumpConstraints(java.lang.String str, java.io.PrintWriter printWriter) {
        printWriter.print(str);
        printWriter.print("maxUserDataSize: ");
        printWriter.println(getMaxUserDataSize());
        printWriter.print(str);
        printWriter.print("maxFieldClassificationIdsSize: ");
        printWriter.println(getMaxFieldClassificationIdsSize());
        printWriter.print(str);
        printWriter.print("maxCategoryCount: ");
        printWriter.println(getMaxCategoryCount());
        printWriter.print(str);
        printWriter.print("minValueLength: ");
        printWriter.println(getMinValueLength());
        printWriter.print(str);
        printWriter.print("maxValueLength: ");
        printWriter.println(getMaxValueLength());
    }

    public static final class Builder {
        private android.util.ArrayMap<java.lang.String, java.lang.String> mCategoryAlgorithms;
        private android.util.ArrayMap<java.lang.String, android.os.Bundle> mCategoryArgs;
        private final java.util.ArrayList<java.lang.String> mCategoryIds;
        private java.lang.String mDefaultAlgorithm;
        private android.os.Bundle mDefaultArgs;
        private boolean mDestroyed;
        private final java.lang.String mId;
        private final android.util.ArraySet<java.lang.String> mUniqueCategoryIds;
        private final android.util.ArraySet<java.lang.String> mUniqueValueCategoryPairs;
        private final java.util.ArrayList<java.lang.String> mValues;

        public Builder(java.lang.String str, java.lang.String str2, java.lang.String str3) {
            this.mId = checkNotEmpty("id", str);
            checkNotEmpty("categoryId", str3);
            checkValidValue(str2);
            int maxUserDataSize = android.service.autofill.UserData.getMaxUserDataSize();
            this.mCategoryIds = new java.util.ArrayList<>(maxUserDataSize);
            this.mValues = new java.util.ArrayList<>(maxUserDataSize);
            this.mUniqueValueCategoryPairs = new android.util.ArraySet<>(maxUserDataSize);
            this.mUniqueCategoryIds = new android.util.ArraySet<>(android.service.autofill.UserData.getMaxCategoryCount());
            addMapping(str2, str3);
        }

        public android.service.autofill.UserData.Builder setFieldClassificationAlgorithm(java.lang.String str, android.os.Bundle bundle) {
            throwIfDestroyed();
            this.mDefaultAlgorithm = str;
            this.mDefaultArgs = bundle;
            return this;
        }

        public android.service.autofill.UserData.Builder setFieldClassificationAlgorithmForCategory(java.lang.String str, java.lang.String str2, android.os.Bundle bundle) {
            throwIfDestroyed();
            java.util.Objects.requireNonNull(str);
            if (this.mCategoryAlgorithms == null) {
                this.mCategoryAlgorithms = new android.util.ArrayMap<>(android.service.autofill.UserData.getMaxCategoryCount());
            }
            if (this.mCategoryArgs == null) {
                this.mCategoryArgs = new android.util.ArrayMap<>(android.service.autofill.UserData.getMaxCategoryCount());
            }
            this.mCategoryAlgorithms.put(str, str2);
            this.mCategoryArgs.put(str, bundle);
            return this;
        }

        public android.service.autofill.UserData.Builder add(java.lang.String str, java.lang.String str2) {
            throwIfDestroyed();
            checkNotEmpty("categoryId", str2);
            checkValidValue(str);
            if (!this.mUniqueCategoryIds.contains(str2)) {
                com.android.internal.util.Preconditions.checkState(this.mUniqueCategoryIds.size() < android.service.autofill.UserData.getMaxCategoryCount(), "already added %d unique category ids", java.lang.Integer.valueOf(this.mUniqueCategoryIds.size()));
            }
            com.android.internal.util.Preconditions.checkState(this.mValues.size() < android.service.autofill.UserData.getMaxUserDataSize(), "already added %d elements", java.lang.Integer.valueOf(this.mValues.size()));
            addMapping(str, str2);
            return this;
        }

        private void addMapping(java.lang.String str, java.lang.String str2) {
            java.lang.String str3 = str + ":" + str2;
            if (this.mUniqueValueCategoryPairs.contains(str3)) {
                android.util.Log.w(android.service.autofill.UserData.TAG, "Ignoring entry with same value / category");
                return;
            }
            this.mCategoryIds.add(str2);
            this.mValues.add(str);
            this.mUniqueCategoryIds.add(str2);
            this.mUniqueValueCategoryPairs.add(str3);
        }

        private java.lang.String checkNotEmpty(java.lang.String str, java.lang.String str2) {
            java.util.Objects.requireNonNull(str2);
            com.android.internal.util.Preconditions.checkArgument(!android.text.TextUtils.isEmpty(str2), "%s cannot be empty", str);
            return str2;
        }

        private void checkValidValue(java.lang.String str) {
            java.util.Objects.requireNonNull(str);
            int length = str.length();
            com.android.internal.util.Preconditions.checkArgumentInRange(length, android.service.autofill.UserData.getMinValueLength(), android.service.autofill.UserData.getMaxValueLength(), "value length (" + length + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        }

        public android.service.autofill.UserData build() {
            throwIfDestroyed();
            this.mDestroyed = true;
            return new android.service.autofill.UserData(this);
        }

        private void throwIfDestroyed() {
            if (this.mDestroyed) {
                throw new java.lang.IllegalStateException("Already called #build()");
            }
        }
    }

    public java.lang.String toString() {
        if (!android.view.autofill.Helper.sDebug) {
            return super.toString();
        }
        java.lang.StringBuilder append = new java.lang.StringBuilder("UserData: [id=").append(this.mId);
        append.append(", categoryIds=");
        android.view.autofill.Helper.appendRedacted(append, this.mCategoryIds);
        append.append(", values=");
        android.view.autofill.Helper.appendRedacted(append, this.mValues);
        return append.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END).toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.mId);
        parcel.writeStringArray(this.mCategoryIds);
        parcel.writeStringArray(this.mValues);
        parcel.writeString(this.mDefaultAlgorithm);
        parcel.writeBundle(this.mDefaultArgs);
        parcel.writeMap(this.mCategoryAlgorithms);
        parcel.writeMap(this.mCategoryArgs);
    }

    public static int getMaxUserDataSize() {
        return getInt(android.provider.Settings.Secure.AUTOFILL_USER_DATA_MAX_USER_DATA_SIZE, 50);
    }

    public static int getMaxFieldClassificationIdsSize() {
        return getInt(android.provider.Settings.Secure.AUTOFILL_USER_DATA_MAX_FIELD_CLASSIFICATION_IDS_SIZE, 10);
    }

    public static int getMaxCategoryCount() {
        return getInt(android.provider.Settings.Secure.AUTOFILL_USER_DATA_MAX_CATEGORY_COUNT, 10);
    }

    public static int getMinValueLength() {
        return getInt(android.provider.Settings.Secure.AUTOFILL_USER_DATA_MIN_VALUE_LENGTH, 3);
    }

    public static int getMaxValueLength() {
        return getInt(android.provider.Settings.Secure.AUTOFILL_USER_DATA_MAX_VALUE_LENGTH, 100);
    }

    private static int getInt(java.lang.String str, int i) {
        android.content.ContentResolver contentResolver;
        android.app.ActivityThread currentActivityThread = android.app.ActivityThread.currentActivityThread();
        if (currentActivityThread == null) {
            contentResolver = null;
        } else {
            contentResolver = currentActivityThread.getApplication().getContentResolver();
        }
        if (contentResolver == null) {
            android.util.Log.w(TAG, "Could not read from " + str + "; hardcoding " + i);
            return i;
        }
        return android.provider.Settings.Secure.getInt(contentResolver, str, i);
    }
}
