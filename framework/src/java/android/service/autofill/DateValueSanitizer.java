package android.service.autofill;

/* loaded from: classes3.dex */
public final class DateValueSanitizer extends android.service.autofill.InternalSanitizer implements android.service.autofill.Sanitizer, android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.service.autofill.DateValueSanitizer> CREATOR = new android.os.Parcelable.Creator<android.service.autofill.DateValueSanitizer>() { // from class: android.service.autofill.DateValueSanitizer.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.autofill.DateValueSanitizer createFromParcel(android.os.Parcel parcel) {
            return new android.service.autofill.DateValueSanitizer((android.icu.text.DateFormat) parcel.readSerializable(android.icu.text.DateFormat.class.getClassLoader(), android.icu.text.DateFormat.class));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.autofill.DateValueSanitizer[] newArray(int i) {
            return new android.service.autofill.DateValueSanitizer[i];
        }
    };
    private static final java.lang.String TAG = "DateValueSanitizer";
    private final android.icu.text.DateFormat mDateFormat;

    public DateValueSanitizer(android.icu.text.DateFormat dateFormat) {
        this.mDateFormat = (android.icu.text.DateFormat) java.util.Objects.requireNonNull(dateFormat);
    }

    @Override // android.service.autofill.InternalSanitizer
    public android.view.autofill.AutofillValue sanitize(android.view.autofill.AutofillValue autofillValue) {
        if (autofillValue == null) {
            android.util.Log.w(TAG, "sanitize() called with null value");
            return null;
        }
        if (!autofillValue.isDate()) {
            if (android.view.autofill.Helper.sDebug) {
                android.util.Log.d(TAG, autofillValue + " is not a date");
            }
            return null;
        }
        try {
            java.util.Date date = new java.util.Date(autofillValue.getDateValue());
            java.lang.String format = this.mDateFormat.format(date);
            if (android.view.autofill.Helper.sDebug) {
                android.util.Log.d(TAG, "Transformed " + date + " to " + format);
            }
            java.util.Date parse = this.mDateFormat.parse(format);
            if (android.view.autofill.Helper.sDebug) {
                android.util.Log.d(TAG, "Sanitized to " + parse);
            }
            return android.view.autofill.AutofillValue.forDate(parse.getTime());
        } catch (java.lang.Exception e) {
            android.util.Log.w(TAG, "Could not apply " + this.mDateFormat + " to " + autofillValue + ": " + e);
            return null;
        }
    }

    public java.lang.String toString() {
        return !android.view.autofill.Helper.sDebug ? super.toString() : "DateValueSanitizer: [dateFormat=" + this.mDateFormat + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeSerializable(this.mDateFormat);
    }
}
