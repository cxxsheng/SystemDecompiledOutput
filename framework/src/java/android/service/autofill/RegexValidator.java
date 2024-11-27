package android.service.autofill;

/* loaded from: classes3.dex */
public final class RegexValidator extends android.service.autofill.InternalValidator implements android.service.autofill.Validator, android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.service.autofill.RegexValidator> CREATOR = new android.os.Parcelable.Creator<android.service.autofill.RegexValidator>() { // from class: android.service.autofill.RegexValidator.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.autofill.RegexValidator createFromParcel(android.os.Parcel parcel) {
            return new android.service.autofill.RegexValidator((android.view.autofill.AutofillId) parcel.readParcelable(null, android.view.autofill.AutofillId.class), (java.util.regex.Pattern) parcel.readSerializable(java.util.regex.Pattern.class.getClassLoader(), java.util.regex.Pattern.class));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.autofill.RegexValidator[] newArray(int i) {
            return new android.service.autofill.RegexValidator[i];
        }
    };
    private static final java.lang.String TAG = "RegexValidator";
    private final android.view.autofill.AutofillId mId;
    private final java.util.regex.Pattern mRegex;

    public RegexValidator(android.view.autofill.AutofillId autofillId, java.util.regex.Pattern pattern) {
        this.mId = (android.view.autofill.AutofillId) java.util.Objects.requireNonNull(autofillId);
        this.mRegex = (java.util.regex.Pattern) java.util.Objects.requireNonNull(pattern);
    }

    @Override // android.service.autofill.InternalValidator
    public boolean isValid(android.service.autofill.ValueFinder valueFinder) {
        java.lang.String findByAutofillId = valueFinder.findByAutofillId(this.mId);
        if (findByAutofillId == null) {
            android.util.Log.w(TAG, "No view for id " + this.mId);
            return false;
        }
        boolean matches = this.mRegex.matcher(findByAutofillId).matches();
        if (android.view.autofill.Helper.sDebug) {
            android.util.Log.d(TAG, "isValid(): " + matches);
        }
        return matches;
    }

    public java.lang.String toString() {
        return !android.view.autofill.Helper.sDebug ? super.toString() : "RegexValidator: [id=" + this.mId + ", regex=" + this.mRegex + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeParcelable(this.mId, i);
        parcel.writeSerializable(this.mRegex);
    }
}
