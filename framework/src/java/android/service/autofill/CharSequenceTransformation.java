package android.service.autofill;

/* loaded from: classes3.dex */
public final class CharSequenceTransformation extends android.service.autofill.InternalTransformation implements android.service.autofill.Transformation, android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.service.autofill.CharSequenceTransformation> CREATOR = new android.os.Parcelable.Creator<android.service.autofill.CharSequenceTransformation>() { // from class: android.service.autofill.CharSequenceTransformation.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.autofill.CharSequenceTransformation createFromParcel(android.os.Parcel parcel) {
            android.view.autofill.AutofillId[] autofillIdArr = (android.view.autofill.AutofillId[]) parcel.readParcelableArray(null, android.view.autofill.AutofillId.class);
            java.util.regex.Pattern[] patternArr = (java.util.regex.Pattern[]) parcel.readSerializable();
            java.lang.String[] createStringArray = parcel.createStringArray();
            android.service.autofill.CharSequenceTransformation.Builder builder = new android.service.autofill.CharSequenceTransformation.Builder(autofillIdArr[0], patternArr[0], createStringArray[0]);
            int length = autofillIdArr.length;
            for (int i = 1; i < length; i++) {
                builder.addField(autofillIdArr[i], patternArr[i], createStringArray[i]);
            }
            return builder.build();
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.autofill.CharSequenceTransformation[] newArray(int i) {
            return new android.service.autofill.CharSequenceTransformation[i];
        }
    };
    private static final java.lang.String TAG = "CharSequenceTransformation";
    private final java.util.LinkedHashMap<android.view.autofill.AutofillId, android.util.Pair<java.util.regex.Pattern, java.lang.String>> mFields;

    private CharSequenceTransformation(android.service.autofill.CharSequenceTransformation.Builder builder) {
        this.mFields = builder.mFields;
    }

    @Override // android.service.autofill.InternalTransformation
    public void apply(android.service.autofill.ValueFinder valueFinder, android.widget.RemoteViews remoteViews, int i) throws java.lang.Exception {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        int size = this.mFields.size();
        if (android.view.autofill.Helper.sDebug) {
            android.util.Log.d(TAG, size + " fields on id " + i);
        }
        for (java.util.Map.Entry<android.view.autofill.AutofillId, android.util.Pair<java.util.regex.Pattern, java.lang.String>> entry : this.mFields.entrySet()) {
            android.view.autofill.AutofillId key = entry.getKey();
            android.util.Pair<java.util.regex.Pattern, java.lang.String> value = entry.getValue();
            java.lang.String findByAutofillId = valueFinder.findByAutofillId(key);
            if (findByAutofillId == null) {
                android.util.Log.w(TAG, "No value for id " + key);
                return;
            }
            try {
                java.util.regex.Matcher matcher = value.first.matcher(findByAutofillId);
                if (!matcher.find()) {
                    if (android.view.autofill.Helper.sDebug) {
                        android.util.Log.d(TAG, "Match for " + value.first + " failed on id " + key);
                        return;
                    }
                    return;
                }
                sb.append(matcher.replaceAll(value.second));
            } catch (java.lang.Exception e) {
                android.util.Log.w(TAG, "Cannot apply " + value.first.pattern() + android.telecom.Logging.Session.SUBSESSION_SEPARATION_CHAR + value.second + " to field with autofill id" + key + ": " + e.getClass());
                throw e;
            }
        }
        android.util.Log.d(TAG, "Converting text on child " + i + " to " + sb.length() + "_chars");
        remoteViews.setCharSequence(i, "setText", sb);
    }

    public static class Builder {
        private boolean mDestroyed;
        private final java.util.LinkedHashMap<android.view.autofill.AutofillId, android.util.Pair<java.util.regex.Pattern, java.lang.String>> mFields = new java.util.LinkedHashMap<>();

        public Builder(android.view.autofill.AutofillId autofillId, java.util.regex.Pattern pattern, java.lang.String str) {
            addField(autofillId, pattern, str);
        }

        public android.service.autofill.CharSequenceTransformation.Builder addField(android.view.autofill.AutofillId autofillId, java.util.regex.Pattern pattern, java.lang.String str) {
            throwIfDestroyed();
            java.util.Objects.requireNonNull(autofillId);
            java.util.Objects.requireNonNull(pattern);
            java.util.Objects.requireNonNull(str);
            this.mFields.put(autofillId, new android.util.Pair<>(pattern, str));
            return this;
        }

        public android.service.autofill.CharSequenceTransformation build() {
            throwIfDestroyed();
            this.mDestroyed = true;
            return new android.service.autofill.CharSequenceTransformation(this);
        }

        private void throwIfDestroyed() {
            com.android.internal.util.Preconditions.checkState(!this.mDestroyed, "Already called build()");
        }
    }

    public java.lang.String toString() {
        return !android.view.autofill.Helper.sDebug ? super.toString() : "MultipleViewsCharSequenceTransformation: [fields=" + this.mFields + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v0, types: [java.io.Serializable, java.util.regex.Pattern[]] */
    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        int size = this.mFields.size();
        android.view.autofill.AutofillId[] autofillIdArr = new android.view.autofill.AutofillId[size];
        ?? r2 = new java.util.regex.Pattern[size];
        java.lang.String[] strArr = new java.lang.String[size];
        int i2 = 0;
        for (java.util.Map.Entry<android.view.autofill.AutofillId, android.util.Pair<java.util.regex.Pattern, java.lang.String>> entry : this.mFields.entrySet()) {
            autofillIdArr[i2] = entry.getKey();
            android.util.Pair<java.util.regex.Pattern, java.lang.String> value = entry.getValue();
            r2[i2] = value.first;
            strArr[i2] = value.second;
            i2++;
        }
        parcel.writeParcelableArray(autofillIdArr, i);
        parcel.writeSerializable(r2);
        parcel.writeStringArray(strArr);
    }
}
