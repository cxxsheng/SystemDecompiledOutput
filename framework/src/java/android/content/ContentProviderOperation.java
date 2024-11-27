package android.content;

/* loaded from: classes.dex */
public class ContentProviderOperation implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.content.ContentProviderOperation> CREATOR = new android.os.Parcelable.Creator<android.content.ContentProviderOperation>() { // from class: android.content.ContentProviderOperation.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.ContentProviderOperation createFromParcel(android.os.Parcel parcel) {
            return new android.content.ContentProviderOperation(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.ContentProviderOperation[] newArray(int i) {
            return new android.content.ContentProviderOperation[i];
        }
    };
    private static final java.lang.String TAG = "ContentProviderOperation";
    public static final int TYPE_ASSERT = 4;
    public static final int TYPE_CALL = 5;
    public static final int TYPE_DELETE = 3;
    public static final int TYPE_INSERT = 1;
    public static final int TYPE_UPDATE = 2;
    private final java.lang.String mArg;
    private final boolean mExceptionAllowed;
    private final java.lang.Integer mExpectedCount;
    private final android.util.ArrayMap<java.lang.String, java.lang.Object> mExtras;
    private final java.lang.String mMethod;
    private final java.lang.String mSelection;
    private final android.util.SparseArray<java.lang.Object> mSelectionArgs;
    private final int mType;
    private final android.net.Uri mUri;
    private final android.util.ArrayMap<java.lang.String, java.lang.Object> mValues;
    private final boolean mYieldAllowed;

    private ContentProviderOperation(android.content.ContentProviderOperation.Builder builder) {
        this.mType = builder.mType;
        this.mUri = builder.mUri;
        this.mMethod = builder.mMethod;
        this.mArg = builder.mArg;
        this.mValues = builder.mValues;
        this.mExtras = builder.mExtras;
        this.mSelection = builder.mSelection;
        this.mSelectionArgs = builder.mSelectionArgs;
        this.mExpectedCount = builder.mExpectedCount;
        this.mYieldAllowed = builder.mYieldAllowed;
        this.mExceptionAllowed = builder.mExceptionAllowed;
    }

    private ContentProviderOperation(android.os.Parcel parcel) {
        this.mType = parcel.readInt();
        this.mUri = android.net.Uri.CREATOR.createFromParcel(parcel);
        this.mMethod = parcel.readInt() != 0 ? parcel.readString8() : null;
        this.mArg = parcel.readInt() != 0 ? parcel.readString8() : null;
        int readInt = parcel.readInt();
        if (readInt != -1) {
            this.mValues = new android.util.ArrayMap<>(readInt);
            parcel.readArrayMap(this.mValues, null);
        } else {
            this.mValues = null;
        }
        int readInt2 = parcel.readInt();
        if (readInt2 != -1) {
            this.mExtras = new android.util.ArrayMap<>(readInt2);
            parcel.readArrayMap(this.mExtras, null);
        } else {
            this.mExtras = null;
        }
        this.mSelection = parcel.readInt() != 0 ? parcel.readString8() : null;
        this.mSelectionArgs = parcel.readSparseArray(null, java.lang.Object.class);
        this.mExpectedCount = parcel.readInt() != 0 ? java.lang.Integer.valueOf(parcel.readInt()) : null;
        this.mYieldAllowed = parcel.readInt() != 0;
        this.mExceptionAllowed = parcel.readInt() != 0;
    }

    public ContentProviderOperation(android.content.ContentProviderOperation contentProviderOperation, android.net.Uri uri) {
        this.mType = contentProviderOperation.mType;
        this.mUri = uri;
        this.mMethod = contentProviderOperation.mMethod;
        this.mArg = contentProviderOperation.mArg;
        this.mValues = contentProviderOperation.mValues;
        this.mExtras = contentProviderOperation.mExtras;
        this.mSelection = contentProviderOperation.mSelection;
        this.mSelectionArgs = contentProviderOperation.mSelectionArgs;
        this.mExpectedCount = contentProviderOperation.mExpectedCount;
        this.mYieldAllowed = contentProviderOperation.mYieldAllowed;
        this.mExceptionAllowed = contentProviderOperation.mExceptionAllowed;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mType);
        android.net.Uri.writeToParcel(parcel, this.mUri);
        if (this.mMethod != null) {
            parcel.writeInt(1);
            parcel.writeString8(this.mMethod);
        } else {
            parcel.writeInt(0);
        }
        if (this.mArg != null) {
            parcel.writeInt(1);
            parcel.writeString8(this.mArg);
        } else {
            parcel.writeInt(0);
        }
        if (this.mValues != null) {
            parcel.writeInt(this.mValues.size());
            parcel.writeArrayMap(this.mValues);
        } else {
            parcel.writeInt(-1);
        }
        if (this.mExtras != null) {
            parcel.writeInt(this.mExtras.size());
            parcel.writeArrayMap(this.mExtras);
        } else {
            parcel.writeInt(-1);
        }
        if (this.mSelection != null) {
            parcel.writeInt(1);
            parcel.writeString8(this.mSelection);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeSparseArray(this.mSelectionArgs);
        if (this.mExpectedCount != null) {
            parcel.writeInt(1);
            parcel.writeInt(this.mExpectedCount.intValue());
        } else {
            parcel.writeInt(0);
        }
        parcel.writeInt(this.mYieldAllowed ? 1 : 0);
        parcel.writeInt(this.mExceptionAllowed ? 1 : 0);
    }

    public static android.content.ContentProviderOperation.Builder newInsert(android.net.Uri uri) {
        return new android.content.ContentProviderOperation.Builder(1, uri);
    }

    public static android.content.ContentProviderOperation.Builder newUpdate(android.net.Uri uri) {
        return new android.content.ContentProviderOperation.Builder(2, uri);
    }

    public static android.content.ContentProviderOperation.Builder newDelete(android.net.Uri uri) {
        return new android.content.ContentProviderOperation.Builder(3, uri);
    }

    public static android.content.ContentProviderOperation.Builder newAssertQuery(android.net.Uri uri) {
        return new android.content.ContentProviderOperation.Builder(4, uri);
    }

    public static android.content.ContentProviderOperation.Builder newCall(android.net.Uri uri, java.lang.String str, java.lang.String str2) {
        return new android.content.ContentProviderOperation.Builder(5, uri, str, str2);
    }

    public android.net.Uri getUri() {
        return this.mUri;
    }

    public boolean isYieldAllowed() {
        return this.mYieldAllowed;
    }

    public boolean isExceptionAllowed() {
        return this.mExceptionAllowed;
    }

    public int getType() {
        return this.mType;
    }

    public boolean isInsert() {
        return this.mType == 1;
    }

    public boolean isDelete() {
        return this.mType == 3;
    }

    public boolean isUpdate() {
        return this.mType == 2;
    }

    public boolean isAssertQuery() {
        return this.mType == 4;
    }

    public boolean isCall() {
        return this.mType == 5;
    }

    public boolean isWriteOperation() {
        return this.mType == 3 || this.mType == 1 || this.mType == 2;
    }

    public boolean isReadOperation() {
        return this.mType == 4;
    }

    public android.content.ContentProviderResult apply(android.content.ContentProvider contentProvider, android.content.ContentProviderResult[] contentProviderResultArr, int i) throws android.content.OperationApplicationException {
        if (this.mExceptionAllowed) {
            try {
                return applyInternal(contentProvider, contentProviderResultArr, i);
            } catch (java.lang.Exception e) {
                return new android.content.ContentProviderResult(e);
            }
        }
        return applyInternal(contentProvider, contentProviderResultArr, i);
    }

    private android.content.ContentProviderResult applyInternal(android.content.ContentProvider contentProvider, android.content.ContentProviderResult[] contentProviderResultArr, int i) throws android.content.OperationApplicationException {
        java.lang.String[] strArr;
        int i2;
        android.content.ContentValues resolveValueBackReferences = resolveValueBackReferences(contentProviderResultArr, i);
        android.os.Bundle resolveExtrasBackReferences = resolveExtrasBackReferences(contentProviderResultArr, i);
        if (this.mSelection != null) {
            if (resolveExtrasBackReferences == null) {
                resolveExtrasBackReferences = new android.os.Bundle();
            }
            resolveExtrasBackReferences.putString(android.content.ContentResolver.QUERY_ARG_SQL_SELECTION, this.mSelection);
        }
        if (this.mSelectionArgs != null) {
            if (resolveExtrasBackReferences == null) {
                resolveExtrasBackReferences = new android.os.Bundle();
            }
            resolveExtrasBackReferences.putStringArray(android.content.ContentResolver.QUERY_ARG_SQL_SELECTION_ARGS, resolveSelectionArgsBackReferences(contentProviderResultArr, i));
        }
        if (this.mType == 1) {
            android.net.Uri insert = contentProvider.insert(this.mUri, resolveValueBackReferences, resolveExtrasBackReferences);
            if (insert != null) {
                return new android.content.ContentProviderResult(insert);
            }
            throw new android.content.OperationApplicationException("Insert into " + this.mUri + " returned no result");
        }
        if (this.mType == 5) {
            return new android.content.ContentProviderResult(contentProvider.call(this.mUri.getAuthority(), this.mMethod, this.mArg, resolveExtrasBackReferences));
        }
        if (this.mType == 3) {
            i2 = contentProvider.delete(this.mUri, resolveExtrasBackReferences);
        } else if (this.mType == 2) {
            i2 = contentProvider.update(this.mUri, resolveValueBackReferences, resolveExtrasBackReferences);
        } else if (this.mType == 4) {
            if (resolveValueBackReferences == null) {
                strArr = null;
            } else {
                java.util.ArrayList arrayList = new java.util.ArrayList();
                java.util.Iterator<java.util.Map.Entry<java.lang.String, java.lang.Object>> it = resolveValueBackReferences.valueSet().iterator();
                while (it.hasNext()) {
                    arrayList.add(it.next().getKey());
                }
                strArr = (java.lang.String[]) arrayList.toArray(new java.lang.String[arrayList.size()]);
            }
            android.database.Cursor query = contentProvider.query(this.mUri, strArr, resolveExtrasBackReferences, null);
            try {
                int count = query.getCount();
                if (strArr != null) {
                    while (query.moveToNext()) {
                        for (int i3 = 0; i3 < strArr.length; i3++) {
                            java.lang.String string = query.getString(i3);
                            java.lang.String asString = resolveValueBackReferences.getAsString(strArr[i3]);
                            if (!android.text.TextUtils.equals(string, asString)) {
                                throw new android.content.OperationApplicationException("Found value " + string + " when expected " + asString + " for column " + strArr[i3]);
                            }
                        }
                    }
                }
                query.close();
                i2 = count;
            } catch (java.lang.Throwable th) {
                query.close();
                throw th;
            }
        } else {
            throw new java.lang.IllegalStateException("bad type, " + this.mType);
        }
        if (this.mExpectedCount != null && this.mExpectedCount.intValue() != i2) {
            throw new android.content.OperationApplicationException("Expected " + this.mExpectedCount + " rows but actual " + i2);
        }
        return new android.content.ContentProviderResult(i2);
    }

    public android.content.ContentValues resolveValueBackReferences(android.content.ContentProviderResult[] contentProviderResultArr, int i) {
        if (this.mValues != null) {
            android.content.ContentValues contentValues = new android.content.ContentValues();
            for (int i2 = 0; i2 < this.mValues.size(); i2++) {
                java.lang.Object valueAt = this.mValues.valueAt(i2);
                if (valueAt instanceof android.content.ContentProviderOperation.BackReference) {
                    valueAt = ((android.content.ContentProviderOperation.BackReference) valueAt).resolve(contentProviderResultArr, i);
                }
                contentValues.putObject(this.mValues.keyAt(i2), valueAt);
            }
            return contentValues;
        }
        return null;
    }

    public android.os.Bundle resolveExtrasBackReferences(android.content.ContentProviderResult[] contentProviderResultArr, int i) {
        if (this.mExtras != null) {
            android.os.Bundle bundle = new android.os.Bundle();
            for (int i2 = 0; i2 < this.mExtras.size(); i2++) {
                java.lang.Object valueAt = this.mExtras.valueAt(i2);
                if (valueAt instanceof android.content.ContentProviderOperation.BackReference) {
                    valueAt = ((android.content.ContentProviderOperation.BackReference) valueAt).resolve(contentProviderResultArr, i);
                }
                bundle.putObject(this.mExtras.keyAt(i2), valueAt);
            }
            return bundle;
        }
        return null;
    }

    public java.lang.String[] resolveSelectionArgsBackReferences(android.content.ContentProviderResult[] contentProviderResultArr, int i) {
        if (this.mSelectionArgs != null) {
            int i2 = -1;
            for (int i3 = 0; i3 < this.mSelectionArgs.size(); i3++) {
                i2 = java.lang.Math.max(i2, this.mSelectionArgs.keyAt(i3));
            }
            java.lang.String[] strArr = new java.lang.String[i2 + 1];
            for (int i4 = 0; i4 < this.mSelectionArgs.size(); i4++) {
                java.lang.Object valueAt = this.mSelectionArgs.valueAt(i4);
                if (valueAt instanceof android.content.ContentProviderOperation.BackReference) {
                    valueAt = ((android.content.ContentProviderOperation.BackReference) valueAt).resolve(contentProviderResultArr, i);
                }
                strArr[this.mSelectionArgs.keyAt(i4)] = java.lang.String.valueOf(valueAt);
            }
            return strArr;
        }
        return null;
    }

    public static java.lang.String typeToString(int i) {
        switch (i) {
            case 1:
                return "insert";
            case 2:
                return "update";
            case 3:
                return "delete";
            case 4:
                return "assert";
            case 5:
                return "call";
            default:
                return java.lang.Integer.toString(i);
        }
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder("ContentProviderOperation(");
        sb.append("type=").append(typeToString(this.mType)).append(' ');
        if (this.mUri != null) {
            sb.append("uri=").append(this.mUri).append(' ');
        }
        if (this.mValues != null) {
            sb.append("values=").append(this.mValues).append(' ');
        }
        if (this.mSelection != null) {
            sb.append("selection=").append(this.mSelection).append(' ');
        }
        if (this.mSelectionArgs != null) {
            sb.append("selectionArgs=").append(this.mSelectionArgs).append(' ');
        }
        if (this.mExpectedCount != null) {
            sb.append("expectedCount=").append(this.mExpectedCount).append(' ');
        }
        if (this.mYieldAllowed) {
            sb.append("yieldAllowed ");
        }
        if (this.mExceptionAllowed) {
            sb.append("exceptionAllowed ");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        return sb.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static class BackReference implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.content.ContentProviderOperation.BackReference> CREATOR = new android.os.Parcelable.Creator<android.content.ContentProviderOperation.BackReference>() { // from class: android.content.ContentProviderOperation.BackReference.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.content.ContentProviderOperation.BackReference createFromParcel(android.os.Parcel parcel) {
                return new android.content.ContentProviderOperation.BackReference(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.content.ContentProviderOperation.BackReference[] newArray(int i) {
                return new android.content.ContentProviderOperation.BackReference[i];
            }
        };
        private final int fromIndex;
        private final java.lang.String fromKey;

        private BackReference(int i, java.lang.String str) {
            this.fromIndex = i;
            this.fromKey = str;
        }

        public BackReference(android.os.Parcel parcel) {
            this.fromIndex = parcel.readInt();
            if (parcel.readInt() != 0) {
                this.fromKey = parcel.readString8();
            } else {
                this.fromKey = null;
            }
        }

        public java.lang.Object resolve(android.content.ContentProviderResult[] contentProviderResultArr, int i) {
            if (this.fromIndex >= i) {
                android.util.Log.e(android.content.ContentProviderOperation.TAG, toString());
                throw new java.lang.ArrayIndexOutOfBoundsException("asked for back ref " + this.fromIndex + " but there are only " + i + " back refs");
            }
            android.content.ContentProviderResult contentProviderResult = contentProviderResultArr[this.fromIndex];
            if (contentProviderResult.extras != null) {
                return contentProviderResult.extras.get(this.fromKey);
            }
            if (contentProviderResult.uri != null) {
                return java.lang.Long.valueOf(android.content.ContentUris.parseId(contentProviderResult.uri));
            }
            return java.lang.Long.valueOf(contentProviderResult.count.intValue());
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeInt(this.fromIndex);
            if (this.fromKey != null) {
                parcel.writeInt(1);
                parcel.writeString8(this.fromKey);
            } else {
                parcel.writeInt(0);
            }
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }
    }

    public static class Builder {
        private final java.lang.String mArg;
        private boolean mExceptionAllowed;
        private java.lang.Integer mExpectedCount;
        private android.util.ArrayMap<java.lang.String, java.lang.Object> mExtras;
        private final java.lang.String mMethod;
        private java.lang.String mSelection;
        private android.util.SparseArray<java.lang.Object> mSelectionArgs;
        private final int mType;
        private final android.net.Uri mUri;
        private android.util.ArrayMap<java.lang.String, java.lang.Object> mValues;
        private boolean mYieldAllowed;

        private Builder(int i, android.net.Uri uri) {
            this(i, uri, null, null);
        }

        private Builder(int i, android.net.Uri uri, java.lang.String str, java.lang.String str2) {
            this.mType = i;
            this.mUri = (android.net.Uri) java.util.Objects.requireNonNull(uri);
            this.mMethod = str;
            this.mArg = str2;
        }

        public android.content.ContentProviderOperation build() {
            if (this.mType == 2 && (this.mValues == null || this.mValues.isEmpty())) {
                throw new java.lang.IllegalArgumentException("Empty values");
            }
            if (this.mType == 4 && ((this.mValues == null || this.mValues.isEmpty()) && this.mExpectedCount == null)) {
                throw new java.lang.IllegalArgumentException("Empty values");
            }
            return new android.content.ContentProviderOperation(this);
        }

        private void ensureValues() {
            if (this.mValues == null) {
                this.mValues = new android.util.ArrayMap<>();
            }
        }

        private void ensureExtras() {
            if (this.mExtras == null) {
                this.mExtras = new android.util.ArrayMap<>();
            }
        }

        private void ensureSelectionArgs() {
            if (this.mSelectionArgs == null) {
                this.mSelectionArgs = new android.util.SparseArray<>();
            }
        }

        private void setValue(java.lang.String str, java.lang.Object obj) {
            ensureValues();
            boolean z = obj instanceof android.content.ContentProviderOperation.BackReference;
            if (!(this.mValues.get(str) instanceof android.content.ContentProviderOperation.BackReference) || z) {
                this.mValues.put(str, obj);
            }
        }

        private void setExtra(java.lang.String str, java.lang.Object obj) {
            ensureExtras();
            boolean z = obj instanceof android.content.ContentProviderOperation.BackReference;
            if (!(this.mExtras.get(str) instanceof android.content.ContentProviderOperation.BackReference) || z) {
                this.mExtras.put(str, obj);
            }
        }

        private void setSelectionArg(int i, java.lang.Object obj) {
            ensureSelectionArgs();
            boolean z = obj instanceof android.content.ContentProviderOperation.BackReference;
            if (!(this.mSelectionArgs.get(i) instanceof android.content.ContentProviderOperation.BackReference) || z) {
                this.mSelectionArgs.put(i, obj);
            }
        }

        public android.content.ContentProviderOperation.Builder withValues(android.content.ContentValues contentValues) {
            assertValuesAllowed();
            ensureValues();
            android.util.ArrayMap<java.lang.String, java.lang.Object> values = contentValues.getValues();
            for (int i = 0; i < values.size(); i++) {
                setValue(values.keyAt(i), values.valueAt(i));
            }
            return this;
        }

        public android.content.ContentProviderOperation.Builder withValue(java.lang.String str, java.lang.Object obj) {
            assertValuesAllowed();
            if (!android.content.ContentValues.isSupportedValue(obj)) {
                throw new java.lang.IllegalArgumentException("bad value type: " + obj.getClass().getName());
            }
            setValue(str, obj);
            return this;
        }

        public android.content.ContentProviderOperation.Builder withValueBackReferences(android.content.ContentValues contentValues) {
            assertValuesAllowed();
            android.util.ArrayMap<java.lang.String, java.lang.Object> values = contentValues.getValues();
            for (int i = 0; i < values.size(); i++) {
                setValue(values.keyAt(i), new android.content.ContentProviderOperation.BackReference(((java.lang.Integer) values.valueAt(i)).intValue(), null));
            }
            return this;
        }

        public android.content.ContentProviderOperation.Builder withValueBackReference(java.lang.String str, int i) {
            assertValuesAllowed();
            setValue(str, new android.content.ContentProviderOperation.BackReference(i, null));
            return this;
        }

        public android.content.ContentProviderOperation.Builder withValueBackReference(java.lang.String str, int i, java.lang.String str2) {
            assertValuesAllowed();
            setValue(str, new android.content.ContentProviderOperation.BackReference(i, str2));
            return this;
        }

        public android.content.ContentProviderOperation.Builder withExtras(android.os.Bundle bundle) {
            assertExtrasAllowed();
            ensureExtras();
            for (java.lang.String str : bundle.keySet()) {
                setExtra(str, bundle.get(str));
            }
            return this;
        }

        public android.content.ContentProviderOperation.Builder withExtra(java.lang.String str, java.lang.Object obj) {
            assertExtrasAllowed();
            setExtra(str, obj);
            return this;
        }

        public android.content.ContentProviderOperation.Builder withExtraBackReference(java.lang.String str, int i) {
            assertExtrasAllowed();
            setExtra(str, new android.content.ContentProviderOperation.BackReference(i, null));
            return this;
        }

        public android.content.ContentProviderOperation.Builder withExtraBackReference(java.lang.String str, int i, java.lang.String str2) {
            assertExtrasAllowed();
            setExtra(str, new android.content.ContentProviderOperation.BackReference(i, str2));
            return this;
        }

        public android.content.ContentProviderOperation.Builder withSelection(java.lang.String str, java.lang.String[] strArr) {
            assertSelectionAllowed();
            this.mSelection = str;
            if (strArr != null) {
                ensureSelectionArgs();
                for (int i = 0; i < strArr.length; i++) {
                    setSelectionArg(i, strArr[i]);
                }
            }
            return this;
        }

        public android.content.ContentProviderOperation.Builder withSelectionBackReference(int i, int i2) {
            assertSelectionAllowed();
            setSelectionArg(i, new android.content.ContentProviderOperation.BackReference(i2, null));
            return this;
        }

        public android.content.ContentProviderOperation.Builder withSelectionBackReference(int i, int i2, java.lang.String str) {
            assertSelectionAllowed();
            setSelectionArg(i, new android.content.ContentProviderOperation.BackReference(i2, str));
            return this;
        }

        public android.content.ContentProviderOperation.Builder withExpectedCount(int i) {
            if (this.mType != 2 && this.mType != 3 && this.mType != 4) {
                throw new java.lang.IllegalArgumentException("only updates, deletes, and asserts can have expected counts");
            }
            this.mExpectedCount = java.lang.Integer.valueOf(i);
            return this;
        }

        public android.content.ContentProviderOperation.Builder withYieldAllowed(boolean z) {
            this.mYieldAllowed = z;
            return this;
        }

        public android.content.ContentProviderOperation.Builder withExceptionAllowed(boolean z) {
            this.mExceptionAllowed = z;
            return this;
        }

        public android.content.ContentProviderOperation.Builder withFailureAllowed(boolean z) {
            return withExceptionAllowed(z);
        }

        private void assertValuesAllowed() {
            switch (this.mType) {
                case 1:
                case 2:
                case 4:
                    return;
                case 3:
                default:
                    throw new java.lang.IllegalArgumentException("Values not supported for " + android.content.ContentProviderOperation.typeToString(this.mType));
            }
        }

        private void assertSelectionAllowed() {
            switch (this.mType) {
                case 2:
                case 3:
                case 4:
                    return;
                default:
                    throw new java.lang.IllegalArgumentException("Selection not supported for " + android.content.ContentProviderOperation.typeToString(this.mType));
            }
        }

        private void assertExtrasAllowed() {
            switch (this.mType) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                    return;
                default:
                    throw new java.lang.IllegalArgumentException("Extras not supported for " + android.content.ContentProviderOperation.typeToString(this.mType));
            }
        }
    }
}
