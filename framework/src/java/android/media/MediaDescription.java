package android.media;

/* loaded from: classes2.dex */
public class MediaDescription implements android.os.Parcelable {
    public static final long BT_FOLDER_TYPE_ALBUMS = 2;
    public static final long BT_FOLDER_TYPE_ARTISTS = 3;
    public static final long BT_FOLDER_TYPE_GENRES = 4;
    public static final long BT_FOLDER_TYPE_MIXED = 0;
    public static final long BT_FOLDER_TYPE_PLAYLISTS = 5;
    public static final long BT_FOLDER_TYPE_TITLES = 1;
    public static final long BT_FOLDER_TYPE_YEARS = 6;
    public static final android.os.Parcelable.Creator<android.media.MediaDescription> CREATOR = new android.os.Parcelable.Creator<android.media.MediaDescription>() { // from class: android.media.MediaDescription.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.MediaDescription createFromParcel(android.os.Parcel parcel) {
            return new android.media.MediaDescription(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.MediaDescription[] newArray(int i) {
            return new android.media.MediaDescription[i];
        }
    };
    public static final java.lang.String EXTRA_BT_FOLDER_TYPE = "android.media.extra.BT_FOLDER_TYPE";
    private final java.lang.CharSequence mDescription;
    private final android.os.Bundle mExtras;
    private final android.graphics.Bitmap mIcon;
    private final android.net.Uri mIconUri;
    private final java.lang.String mMediaId;
    private final android.net.Uri mMediaUri;
    private final java.lang.CharSequence mSubtitle;
    private final java.lang.CharSequence mTitle;

    private MediaDescription(java.lang.String str, java.lang.CharSequence charSequence, java.lang.CharSequence charSequence2, java.lang.CharSequence charSequence3, android.graphics.Bitmap bitmap, android.net.Uri uri, android.os.Bundle bundle, android.net.Uri uri2) {
        this.mMediaId = str;
        this.mTitle = charSequence;
        this.mSubtitle = charSequence2;
        this.mDescription = charSequence3;
        this.mIcon = bitmap;
        this.mIconUri = uri;
        this.mExtras = bundle;
        this.mMediaUri = uri2;
    }

    private MediaDescription(android.os.Parcel parcel) {
        this.mMediaId = parcel.readString();
        this.mTitle = android.text.TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.mSubtitle = android.text.TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.mDescription = android.text.TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.mIcon = (android.graphics.Bitmap) parcel.readParcelable(null, android.graphics.Bitmap.class);
        this.mIconUri = (android.net.Uri) parcel.readParcelable(null, android.net.Uri.class);
        this.mExtras = parcel.readBundle();
        this.mMediaUri = (android.net.Uri) parcel.readParcelable(null, android.net.Uri.class);
    }

    public java.lang.String getMediaId() {
        return this.mMediaId;
    }

    public java.lang.CharSequence getTitle() {
        return this.mTitle;
    }

    public java.lang.CharSequence getSubtitle() {
        return this.mSubtitle;
    }

    public java.lang.CharSequence getDescription() {
        return this.mDescription;
    }

    public android.graphics.Bitmap getIconBitmap() {
        return this.mIcon;
    }

    public android.net.Uri getIconUri() {
        return this.mIconUri;
    }

    public android.os.Bundle getExtras() {
        return this.mExtras;
    }

    public android.net.Uri getMediaUri() {
        return this.mMediaUri;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.mMediaId);
        android.text.TextUtils.writeToParcel(this.mTitle, parcel, 0);
        android.text.TextUtils.writeToParcel(this.mSubtitle, parcel, 0);
        android.text.TextUtils.writeToParcel(this.mDescription, parcel, 0);
        parcel.writeParcelable(this.mIcon, i);
        parcel.writeParcelable(this.mIconUri, i);
        parcel.writeBundle(this.mExtras);
        parcel.writeParcelable(this.mMediaUri, i);
    }

    public boolean equals(java.lang.Object obj) {
        if (obj == null || !(obj instanceof android.media.MediaDescription)) {
            return false;
        }
        android.media.MediaDescription mediaDescription = (android.media.MediaDescription) obj;
        if (!java.lang.String.valueOf(this.mTitle).equals(java.lang.String.valueOf(mediaDescription.mTitle)) || !java.lang.String.valueOf(this.mSubtitle).equals(java.lang.String.valueOf(mediaDescription.mSubtitle)) || !java.lang.String.valueOf(this.mDescription).equals(java.lang.String.valueOf(mediaDescription.mDescription))) {
            return false;
        }
        return true;
    }

    public java.lang.String toString() {
        return ((java.lang.Object) this.mTitle) + ", " + ((java.lang.Object) this.mSubtitle) + ", " + ((java.lang.Object) this.mDescription);
    }

    public static class Builder {
        private java.lang.CharSequence mDescription;
        private android.os.Bundle mExtras;
        private android.graphics.Bitmap mIcon;
        private android.net.Uri mIconUri;
        private java.lang.String mMediaId;
        private android.net.Uri mMediaUri;
        private java.lang.CharSequence mSubtitle;
        private java.lang.CharSequence mTitle;

        public android.media.MediaDescription.Builder setMediaId(java.lang.String str) {
            this.mMediaId = str;
            return this;
        }

        public android.media.MediaDescription.Builder setTitle(java.lang.CharSequence charSequence) {
            this.mTitle = charSequence;
            return this;
        }

        public android.media.MediaDescription.Builder setSubtitle(java.lang.CharSequence charSequence) {
            this.mSubtitle = charSequence;
            return this;
        }

        public android.media.MediaDescription.Builder setDescription(java.lang.CharSequence charSequence) {
            this.mDescription = charSequence;
            return this;
        }

        public android.media.MediaDescription.Builder setIconBitmap(android.graphics.Bitmap bitmap) {
            this.mIcon = bitmap;
            return this;
        }

        public android.media.MediaDescription.Builder setIconUri(android.net.Uri uri) {
            this.mIconUri = uri;
            return this;
        }

        public android.media.MediaDescription.Builder setExtras(android.os.Bundle bundle) {
            this.mExtras = bundle;
            return this;
        }

        public android.media.MediaDescription.Builder setMediaUri(android.net.Uri uri) {
            this.mMediaUri = uri;
            return this;
        }

        public android.media.MediaDescription build() {
            return new android.media.MediaDescription(this.mMediaId, this.mTitle, this.mSubtitle, this.mDescription, this.mIcon, this.mIconUri, this.mExtras, this.mMediaUri);
        }
    }
}
