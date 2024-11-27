package android.view.autofill;

/* loaded from: classes4.dex */
public final class AutofillValue implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.view.autofill.AutofillValue> CREATOR = new android.os.Parcelable.Creator<android.view.autofill.AutofillValue>() { // from class: android.view.autofill.AutofillValue.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.autofill.AutofillValue createFromParcel(android.os.Parcel parcel) {
            return new android.view.autofill.AutofillValue(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.autofill.AutofillValue[] newArray(int i) {
            return new android.view.autofill.AutofillValue[i];
        }
    };
    private static final java.lang.String TAG = "AutofillValue";
    private final int mType;
    private final java.lang.Object mValue;

    private AutofillValue(int i, java.lang.Object obj) {
        this.mType = i;
        this.mValue = obj;
    }

    public java.lang.CharSequence getTextValue() {
        com.android.internal.util.Preconditions.checkState(isText(), "value must be a text value, not type=%d", java.lang.Integer.valueOf(this.mType));
        return (java.lang.CharSequence) this.mValue;
    }

    public boolean isText() {
        return this.mType == 1;
    }

    public boolean getToggleValue() {
        com.android.internal.util.Preconditions.checkState(isToggle(), "value must be a toggle value, not type=%d", java.lang.Integer.valueOf(this.mType));
        return ((java.lang.Boolean) this.mValue).booleanValue();
    }

    public boolean isToggle() {
        return this.mType == 2;
    }

    public int getListValue() {
        com.android.internal.util.Preconditions.checkState(isList(), "value must be a list value, not type=%d", java.lang.Integer.valueOf(this.mType));
        return ((java.lang.Integer) this.mValue).intValue();
    }

    public boolean isList() {
        return this.mType == 3;
    }

    public long getDateValue() {
        com.android.internal.util.Preconditions.checkState(isDate(), "value must be a date value, not type=%d", java.lang.Integer.valueOf(this.mType));
        return ((java.lang.Long) this.mValue).longValue();
    }

    public boolean isDate() {
        return this.mType == 4;
    }

    public boolean isEmpty() {
        return isText() && ((java.lang.CharSequence) this.mValue).length() == 0;
    }

    public int hashCode() {
        return this.mType + this.mValue.hashCode();
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.view.autofill.AutofillValue autofillValue = (android.view.autofill.AutofillValue) obj;
        if (this.mType != autofillValue.mType) {
            return false;
        }
        if (isText()) {
            return this.mValue.toString().equals(autofillValue.mValue.toString());
        }
        return java.util.Objects.equals(this.mValue, autofillValue.mValue);
    }

    public java.lang.String toString() {
        if (!android.view.autofill.Helper.sDebug) {
            return super.toString();
        }
        java.lang.StringBuilder append = new java.lang.StringBuilder().append("[type=").append(this.mType).append(", value=");
        if (isText()) {
            android.view.autofill.Helper.appendRedacted(append, (java.lang.CharSequence) this.mValue);
        } else {
            append.append(this.mValue);
        }
        return append.append(']').toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mType);
        switch (this.mType) {
            case 1:
                parcel.writeCharSequence((java.lang.CharSequence) this.mValue);
                break;
            case 2:
                parcel.writeInt(((java.lang.Boolean) this.mValue).booleanValue() ? 1 : 0);
                break;
            case 3:
                parcel.writeInt(((java.lang.Integer) this.mValue).intValue());
                break;
            case 4:
                parcel.writeLong(((java.lang.Long) this.mValue).longValue());
                break;
        }
    }

    private AutofillValue(android.os.Parcel parcel) {
        this.mType = parcel.readInt();
        switch (this.mType) {
            case 1:
                this.mValue = parcel.readCharSequence();
                return;
            case 2:
                this.mValue = java.lang.Boolean.valueOf(parcel.readInt() != 0);
                return;
            case 3:
                this.mValue = java.lang.Integer.valueOf(parcel.readInt());
                return;
            case 4:
                this.mValue = java.lang.Long.valueOf(parcel.readLong());
                return;
            default:
                throw new java.lang.IllegalArgumentException("type=" + this.mType + " not valid");
        }
    }

    public static android.view.autofill.AutofillValue forText(java.lang.CharSequence charSequence) {
        if (android.view.autofill.Helper.sVerbose && !android.os.Looper.getMainLooper().isCurrentThread()) {
            android.util.Log.v(TAG, "forText() not called on main thread: " + java.lang.Thread.currentThread());
        }
        if (charSequence == null) {
            return null;
        }
        return new android.view.autofill.AutofillValue(1, android.text.TextUtils.trimNoCopySpans(charSequence));
    }

    public static android.view.autofill.AutofillValue forToggle(boolean z) {
        return new android.view.autofill.AutofillValue(2, java.lang.Boolean.valueOf(z));
    }

    public static android.view.autofill.AutofillValue forList(int i) {
        return new android.view.autofill.AutofillValue(3, java.lang.Integer.valueOf(i));
    }

    public static android.view.autofill.AutofillValue forDate(long j) {
        return new android.view.autofill.AutofillValue(4, java.lang.Long.valueOf(j));
    }
}
