package android.service.autofill;

/* loaded from: classes3.dex */
public final class DateTransformation extends android.service.autofill.InternalTransformation implements android.service.autofill.Transformation, android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.service.autofill.DateTransformation> CREATOR = new android.os.Parcelable.Creator<android.service.autofill.DateTransformation>() { // from class: android.service.autofill.DateTransformation.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.autofill.DateTransformation createFromParcel(android.os.Parcel parcel) {
            return new android.service.autofill.DateTransformation((android.view.autofill.AutofillId) parcel.readParcelable(null, android.view.autofill.AutofillId.class), (android.icu.text.DateFormat) parcel.readSerializable(android.icu.text.DateFormat.class.getClassLoader(), android.icu.text.DateFormat.class));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.autofill.DateTransformation[] newArray(int i) {
            return new android.service.autofill.DateTransformation[i];
        }
    };
    private static final java.lang.String TAG = "DateTransformation";
    private final android.icu.text.DateFormat mDateFormat;
    private final android.view.autofill.AutofillId mFieldId;

    public DateTransformation(android.view.autofill.AutofillId autofillId, android.icu.text.DateFormat dateFormat) {
        this.mFieldId = (android.view.autofill.AutofillId) java.util.Objects.requireNonNull(autofillId);
        this.mDateFormat = (android.icu.text.DateFormat) java.util.Objects.requireNonNull(dateFormat);
    }

    @Override // android.service.autofill.InternalTransformation
    public void apply(android.service.autofill.ValueFinder valueFinder, android.widget.RemoteViews remoteViews, int i) throws java.lang.Exception {
        android.view.autofill.AutofillValue findRawValueByAutofillId = valueFinder.findRawValueByAutofillId(this.mFieldId);
        if (findRawValueByAutofillId == null) {
            android.util.Log.w(TAG, "No value for id " + this.mFieldId);
            return;
        }
        if (!findRawValueByAutofillId.isDate()) {
            android.util.Log.w(TAG, "Value for " + this.mFieldId + " is not date: " + findRawValueByAutofillId);
            return;
        }
        try {
            java.util.Date date = new java.util.Date(findRawValueByAutofillId.getDateValue());
            java.lang.String format = this.mDateFormat.format(date);
            if (android.view.autofill.Helper.sDebug) {
                android.util.Log.d(TAG, "Transformed " + date + " to " + format);
            }
            remoteViews.setCharSequence(i, "setText", format);
        } catch (java.lang.Exception e) {
            android.util.Log.w(TAG, "Could not apply " + this.mDateFormat + " to " + findRawValueByAutofillId + ": " + e);
        }
    }

    public java.lang.String toString() {
        return !android.view.autofill.Helper.sDebug ? super.toString() : "DateTransformation: [id=" + this.mFieldId + ", format=" + this.mDateFormat + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeParcelable(this.mFieldId, i);
        parcel.writeSerializable(this.mDateFormat);
    }
}
