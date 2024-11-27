package android.service.autofill;

/* loaded from: classes3.dex */
public final class TextValueSanitizer extends android.service.autofill.InternalSanitizer implements android.service.autofill.Sanitizer, android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.service.autofill.TextValueSanitizer> CREATOR = new android.os.Parcelable.Creator<android.service.autofill.TextValueSanitizer>() { // from class: android.service.autofill.TextValueSanitizer.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.autofill.TextValueSanitizer createFromParcel(android.os.Parcel parcel) {
            return new android.service.autofill.TextValueSanitizer((java.util.regex.Pattern) parcel.readSerializable(java.util.regex.Pattern.class.getClassLoader(), java.util.regex.Pattern.class), parcel.readString());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.autofill.TextValueSanitizer[] newArray(int i) {
            return new android.service.autofill.TextValueSanitizer[i];
        }
    };
    private static final java.lang.String TAG = "TextValueSanitizer";
    private final java.util.regex.Pattern mRegex;
    private final java.lang.String mSubst;

    public TextValueSanitizer(java.util.regex.Pattern pattern, java.lang.String str) {
        this.mRegex = (java.util.regex.Pattern) java.util.Objects.requireNonNull(pattern);
        this.mSubst = (java.lang.String) java.util.Objects.requireNonNull(str);
    }

    @Override // android.service.autofill.InternalSanitizer
    public android.view.autofill.AutofillValue sanitize(android.view.autofill.AutofillValue autofillValue) {
        if (autofillValue == null) {
            android.util.Slog.w(TAG, "sanitize() called with null value");
            return null;
        }
        if (!autofillValue.isText()) {
            if (android.view.autofill.Helper.sDebug) {
                android.util.Slog.d(TAG, "sanitize() called with non-text value: " + autofillValue);
            }
            return null;
        }
        try {
            java.util.regex.Matcher matcher = this.mRegex.matcher(autofillValue.getTextValue());
            if (!matcher.matches()) {
                if (android.view.autofill.Helper.sDebug) {
                    android.util.Slog.d(TAG, "sanitize(): " + this.mRegex + " failed for " + autofillValue);
                }
                return null;
            }
            return android.view.autofill.AutofillValue.forText(matcher.replaceAll(this.mSubst));
        } catch (java.lang.Exception e) {
            android.util.Slog.w(TAG, "Exception evaluating " + this.mRegex + "/" + this.mSubst + ": " + e);
            return null;
        }
    }

    public java.lang.String toString() {
        return !android.view.autofill.Helper.sDebug ? super.toString() : "TextValueSanitizer: [regex=" + this.mRegex + ", subst=" + this.mSubst + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeSerializable(this.mRegex);
        parcel.writeString(this.mSubst);
    }
}
