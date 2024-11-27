package android.service.settings.suggestions;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class Suggestion implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.service.settings.suggestions.Suggestion> CREATOR = new android.os.Parcelable.Creator<android.service.settings.suggestions.Suggestion>() { // from class: android.service.settings.suggestions.Suggestion.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.settings.suggestions.Suggestion createFromParcel(android.os.Parcel parcel) {
            return new android.service.settings.suggestions.Suggestion(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.settings.suggestions.Suggestion[] newArray(int i) {
            return new android.service.settings.suggestions.Suggestion[i];
        }
    };
    public static final int FLAG_HAS_BUTTON = 1;
    public static final int FLAG_ICON_TINTABLE = 2;
    private final int mFlags;
    private final android.graphics.drawable.Icon mIcon;
    private final java.lang.String mId;
    private final android.app.PendingIntent mPendingIntent;
    private final java.lang.CharSequence mSummary;
    private final java.lang.CharSequence mTitle;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Flags {
    }

    public java.lang.String getId() {
        return this.mId;
    }

    public java.lang.CharSequence getTitle() {
        return this.mTitle;
    }

    public java.lang.CharSequence getSummary() {
        return this.mSummary;
    }

    public android.graphics.drawable.Icon getIcon() {
        return this.mIcon;
    }

    public int getFlags() {
        return this.mFlags;
    }

    public android.app.PendingIntent getPendingIntent() {
        return this.mPendingIntent;
    }

    private Suggestion(android.service.settings.suggestions.Suggestion.Builder builder) {
        this.mId = builder.mId;
        this.mTitle = builder.mTitle;
        this.mSummary = builder.mSummary;
        this.mIcon = builder.mIcon;
        this.mFlags = builder.mFlags;
        this.mPendingIntent = builder.mPendingIntent;
    }

    private Suggestion(android.os.Parcel parcel) {
        this.mId = parcel.readString();
        this.mTitle = parcel.readCharSequence();
        this.mSummary = parcel.readCharSequence();
        this.mIcon = (android.graphics.drawable.Icon) parcel.readParcelable(android.graphics.drawable.Icon.class.getClassLoader(), android.graphics.drawable.Icon.class);
        this.mFlags = parcel.readInt();
        this.mPendingIntent = (android.app.PendingIntent) parcel.readParcelable(android.app.PendingIntent.class.getClassLoader(), android.app.PendingIntent.class);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.mId);
        parcel.writeCharSequence(this.mTitle);
        parcel.writeCharSequence(this.mSummary);
        parcel.writeParcelable(this.mIcon, i);
        parcel.writeInt(this.mFlags);
        parcel.writeParcelable(this.mPendingIntent, i);
    }

    public static class Builder {
        private int mFlags;
        private android.graphics.drawable.Icon mIcon;
        private final java.lang.String mId;
        private android.app.PendingIntent mPendingIntent;
        private java.lang.CharSequence mSummary;
        private java.lang.CharSequence mTitle;

        public Builder(java.lang.String str) {
            if (android.text.TextUtils.isEmpty(str)) {
                throw new java.lang.IllegalArgumentException("Suggestion id cannot be empty");
            }
            this.mId = str;
        }

        public android.service.settings.suggestions.Suggestion.Builder setTitle(java.lang.CharSequence charSequence) {
            this.mTitle = charSequence;
            return this;
        }

        public android.service.settings.suggestions.Suggestion.Builder setSummary(java.lang.CharSequence charSequence) {
            this.mSummary = charSequence;
            return this;
        }

        public android.service.settings.suggestions.Suggestion.Builder setIcon(android.graphics.drawable.Icon icon) {
            this.mIcon = icon;
            return this;
        }

        public android.service.settings.suggestions.Suggestion.Builder setFlags(int i) {
            this.mFlags = i;
            return this;
        }

        public android.service.settings.suggestions.Suggestion.Builder setPendingIntent(android.app.PendingIntent pendingIntent) {
            this.mPendingIntent = pendingIntent;
            return this;
        }

        public android.service.settings.suggestions.Suggestion build() {
            return new android.service.settings.suggestions.Suggestion(this);
        }
    }
}
