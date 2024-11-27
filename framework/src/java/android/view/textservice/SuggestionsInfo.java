package android.view.textservice;

/* loaded from: classes4.dex */
public final class SuggestionsInfo implements android.os.Parcelable {
    public static final int RESULT_ATTR_DONT_SHOW_UI_FOR_SUGGESTIONS = 16;
    public static final int RESULT_ATTR_HAS_RECOMMENDED_SUGGESTIONS = 4;
    public static final int RESULT_ATTR_IN_THE_DICTIONARY = 1;
    public static final int RESULT_ATTR_LOOKS_LIKE_GRAMMAR_ERROR = 8;
    public static final int RESULT_ATTR_LOOKS_LIKE_TYPO = 2;
    private int mCookie;
    private int mSequence;
    private final java.lang.String[] mSuggestions;
    private final int mSuggestionsAttributes;
    private final boolean mSuggestionsAvailable;
    private static final java.lang.String[] EMPTY = (java.lang.String[]) com.android.internal.util.ArrayUtils.emptyArray(java.lang.String.class);
    public static final android.os.Parcelable.Creator<android.view.textservice.SuggestionsInfo> CREATOR = new android.os.Parcelable.Creator<android.view.textservice.SuggestionsInfo>() { // from class: android.view.textservice.SuggestionsInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.textservice.SuggestionsInfo createFromParcel(android.os.Parcel parcel) {
            return new android.view.textservice.SuggestionsInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.textservice.SuggestionsInfo[] newArray(int i) {
            return new android.view.textservice.SuggestionsInfo[i];
        }
    };

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ResultAttrs {
    }

    public SuggestionsInfo(int i, java.lang.String[] strArr) {
        this(i, strArr, 0, 0);
    }

    public SuggestionsInfo(int i, java.lang.String[] strArr, int i2, int i3) {
        if (strArr == null) {
            this.mSuggestions = EMPTY;
            this.mSuggestionsAvailable = false;
        } else {
            this.mSuggestions = strArr;
            this.mSuggestionsAvailable = true;
        }
        this.mSuggestionsAttributes = i;
        this.mCookie = i2;
        this.mSequence = i3;
    }

    public SuggestionsInfo(android.os.Parcel parcel) {
        this.mSuggestionsAttributes = parcel.readInt();
        this.mSuggestions = parcel.readStringArray();
        this.mCookie = parcel.readInt();
        this.mSequence = parcel.readInt();
        this.mSuggestionsAvailable = parcel.readInt() == 1;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mSuggestionsAttributes);
        parcel.writeStringArray(this.mSuggestions);
        parcel.writeInt(this.mCookie);
        parcel.writeInt(this.mSequence);
        parcel.writeInt(this.mSuggestionsAvailable ? 1 : 0);
    }

    public void setCookieAndSequence(int i, int i2) {
        this.mCookie = i;
        this.mSequence = i2;
    }

    public int getCookie() {
        return this.mCookie;
    }

    public int getSequence() {
        return this.mSequence;
    }

    public int getSuggestionsAttributes() {
        return this.mSuggestionsAttributes;
    }

    public int getSuggestionsCount() {
        if (!this.mSuggestionsAvailable) {
            return -1;
        }
        return this.mSuggestions.length;
    }

    public java.lang.String getSuggestionAt(int i) {
        return this.mSuggestions[i];
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
