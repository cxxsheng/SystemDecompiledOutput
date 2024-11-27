package com.android.internal.inputmethod;

/* loaded from: classes4.dex */
public final class InputMethodSubtypeHandle implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<com.android.internal.inputmethod.InputMethodSubtypeHandle> CREATOR = new android.os.Parcelable.Creator<com.android.internal.inputmethod.InputMethodSubtypeHandle>() { // from class: com.android.internal.inputmethod.InputMethodSubtypeHandle.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.internal.inputmethod.InputMethodSubtypeHandle createFromParcel(android.os.Parcel parcel) {
            return com.android.internal.inputmethod.InputMethodSubtypeHandle.of(parcel.readString8());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.internal.inputmethod.InputMethodSubtypeHandle[] newArray(int i) {
            return new com.android.internal.inputmethod.InputMethodSubtypeHandle[i];
        }
    };
    private static final char DATA_SEPARATOR = ':';
    private static final java.lang.String SUBTYPE_TAG = "subtype";
    private final java.lang.String mHandle;

    @java.lang.annotation.Target({java.lang.annotation.ElementType.METHOD, java.lang.annotation.ElementType.FIELD, java.lang.annotation.ElementType.LOCAL_VARIABLE, java.lang.annotation.ElementType.PARAMETER})
    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface RawHandle {
    }

    private static java.lang.String encodeHandle(java.lang.String str, int i) {
        return str + ':' + SUBTYPE_TAG + ':' + i;
    }

    private InputMethodSubtypeHandle(java.lang.String str) {
        this.mHandle = str;
    }

    public static com.android.internal.inputmethod.InputMethodSubtypeHandle of(android.view.inputmethod.InputMethodInfo inputMethodInfo, android.view.inputmethod.InputMethodSubtype inputMethodSubtype) {
        return new com.android.internal.inputmethod.InputMethodSubtypeHandle(encodeHandle(inputMethodInfo.getId(), inputMethodSubtype != null ? inputMethodSubtype.hashCode() : 0));
    }

    public static com.android.internal.inputmethod.InputMethodSubtypeHandle of(java.lang.String str) {
        android.text.TextUtils.SimpleStringSplitter simpleStringSplitter = new android.text.TextUtils.SimpleStringSplitter(':');
        simpleStringSplitter.setString((java.lang.String) java.util.Objects.requireNonNull(str));
        if (!simpleStringSplitter.hasNext()) {
            throw new java.security.InvalidParameterException("Invalid handle=" + str);
        }
        java.lang.String next = simpleStringSplitter.next();
        android.content.ComponentName unflattenFromString = android.content.ComponentName.unflattenFromString(next);
        if (unflattenFromString == null) {
            throw new java.security.InvalidParameterException("Invalid handle=" + str);
        }
        if (!java.util.Objects.equals(unflattenFromString.flattenToShortString(), next)) {
            throw new java.security.InvalidParameterException("Invalid handle=" + str);
        }
        if (!simpleStringSplitter.hasNext()) {
            throw new java.security.InvalidParameterException("Invalid handle=" + str);
        }
        if (!java.util.Objects.equals(simpleStringSplitter.next(), SUBTYPE_TAG)) {
            throw new java.security.InvalidParameterException("Invalid handle=" + str);
        }
        if (!simpleStringSplitter.hasNext()) {
            throw new java.security.InvalidParameterException("Invalid handle=" + str);
        }
        java.lang.String next2 = simpleStringSplitter.next();
        if (simpleStringSplitter.hasNext()) {
            throw new java.security.InvalidParameterException("Invalid handle=" + str);
        }
        try {
            if (!java.util.Objects.equals(encodeHandle(next, java.lang.Integer.parseInt(next2)), str)) {
                throw new java.security.InvalidParameterException("Invalid handle=" + str);
            }
            return new com.android.internal.inputmethod.InputMethodSubtypeHandle(str);
        } catch (java.lang.NumberFormatException e) {
            throw new java.security.InvalidParameterException("Invalid handle=" + str);
        }
    }

    public android.content.ComponentName getComponentName() {
        return android.content.ComponentName.unflattenFromString(getImeId());
    }

    public java.lang.String getImeId() {
        return this.mHandle.substring(0, this.mHandle.indexOf(58));
    }

    public java.lang.String toStringHandle() {
        return this.mHandle;
    }

    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof com.android.internal.inputmethod.InputMethodSubtypeHandle)) {
            return false;
        }
        return java.util.Objects.equals(this.mHandle, ((com.android.internal.inputmethod.InputMethodSubtypeHandle) obj).mHandle);
    }

    public int hashCode() {
        return java.util.Objects.hashCode(this.mHandle);
    }

    public java.lang.String toString() {
        return "InputMethodSubtypeHandle{mHandle=" + this.mHandle + "}";
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString8(toStringHandle());
    }
}
