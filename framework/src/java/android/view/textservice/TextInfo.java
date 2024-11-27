package android.view.textservice;

/* loaded from: classes4.dex */
public final class TextInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.view.textservice.TextInfo> CREATOR = new android.os.Parcelable.Creator<android.view.textservice.TextInfo>() { // from class: android.view.textservice.TextInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.textservice.TextInfo createFromParcel(android.os.Parcel parcel) {
            return new android.view.textservice.TextInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.textservice.TextInfo[] newArray(int i) {
            return new android.view.textservice.TextInfo[i];
        }
    };
    private static final int DEFAULT_COOKIE = 0;
    private static final int DEFAULT_SEQUENCE_NUMBER = 0;
    private final java.lang.CharSequence mCharSequence;
    private final int mCookie;
    private final int mSequenceNumber;

    public TextInfo(java.lang.String str) {
        this(str, 0, getStringLengthOrZero(str), 0, 0);
    }

    public TextInfo(java.lang.String str, int i, int i2) {
        this(str, 0, getStringLengthOrZero(str), i, i2);
    }

    private static int getStringLengthOrZero(java.lang.String str) {
        if (android.text.TextUtils.isEmpty(str)) {
            return 0;
        }
        return str.length();
    }

    public TextInfo(java.lang.CharSequence charSequence, int i, int i2, int i3, int i4) {
        if (android.text.TextUtils.isEmpty(charSequence)) {
            throw new java.lang.IllegalArgumentException("charSequence is empty");
        }
        android.text.SpannableStringBuilder spannableStringBuilder = new android.text.SpannableStringBuilder(charSequence, i, i2);
        for (android.text.style.SpellCheckSpan spellCheckSpan : (android.text.style.SpellCheckSpan[]) spannableStringBuilder.getSpans(0, spannableStringBuilder.length(), android.text.style.SpellCheckSpan.class)) {
            spannableStringBuilder.removeSpan(spellCheckSpan);
        }
        this.mCharSequence = spannableStringBuilder;
        this.mCookie = i3;
        this.mSequenceNumber = i4;
    }

    public TextInfo(android.os.Parcel parcel) {
        this.mCharSequence = android.text.TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.mCookie = parcel.readInt();
        this.mSequenceNumber = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        android.text.TextUtils.writeToParcel(this.mCharSequence, parcel, i);
        parcel.writeInt(this.mCookie);
        parcel.writeInt(this.mSequenceNumber);
    }

    public java.lang.String getText() {
        if (this.mCharSequence == null) {
            return null;
        }
        return this.mCharSequence.toString();
    }

    public java.lang.CharSequence getCharSequence() {
        return this.mCharSequence;
    }

    public int getCookie() {
        return this.mCookie;
    }

    public int getSequence() {
        return this.mSequenceNumber;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
