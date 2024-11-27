package android.media;

/* loaded from: classes2.dex */
public final class MediaMetadata implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.media.MediaMetadata> CREATOR;
    private static final android.util.SparseArray<java.lang.String> EDITOR_KEY_MAPPING;
    public static final java.lang.String METADATA_KEY_BT_FOLDER_TYPE = "android.media.metadata.BT_FOLDER_TYPE";
    public static final java.lang.String METADATA_KEY_COMPILATION = "android.media.metadata.COMPILATION";
    public static final java.lang.String METADATA_KEY_DATE = "android.media.metadata.DATE";
    public static final java.lang.String METADATA_KEY_DISC_NUMBER = "android.media.metadata.DISC_NUMBER";
    public static final java.lang.String METADATA_KEY_DISPLAY_DESCRIPTION = "android.media.metadata.DISPLAY_DESCRIPTION";
    public static final java.lang.String METADATA_KEY_DISPLAY_SUBTITLE = "android.media.metadata.DISPLAY_SUBTITLE";
    public static final java.lang.String METADATA_KEY_DISPLAY_TITLE = "android.media.metadata.DISPLAY_TITLE";
    public static final java.lang.String METADATA_KEY_DURATION = "android.media.metadata.DURATION";
    public static final java.lang.String METADATA_KEY_GENRE = "android.media.metadata.GENRE";
    public static final java.lang.String METADATA_KEY_MEDIA_ID = "android.media.metadata.MEDIA_ID";
    public static final java.lang.String METADATA_KEY_MEDIA_URI = "android.media.metadata.MEDIA_URI";
    public static final java.lang.String METADATA_KEY_NUM_TRACKS = "android.media.metadata.NUM_TRACKS";
    public static final java.lang.String METADATA_KEY_RATING = "android.media.metadata.RATING";
    public static final java.lang.String METADATA_KEY_TRACK_NUMBER = "android.media.metadata.TRACK_NUMBER";
    public static final java.lang.String METADATA_KEY_USER_RATING = "android.media.metadata.USER_RATING";
    public static final java.lang.String METADATA_KEY_YEAR = "android.media.metadata.YEAR";
    private static final int METADATA_TYPE_BITMAP = 2;
    private static final int METADATA_TYPE_INVALID = -1;
    private static final int METADATA_TYPE_LONG = 0;
    private static final int METADATA_TYPE_RATING = 3;
    private static final int METADATA_TYPE_TEXT = 1;
    private static final java.lang.String TAG = "MediaMetadata";
    private final int mBitmapDimensionLimit;
    private final android.os.Bundle mBundle;
    private android.media.MediaDescription mDescription;
    public static final java.lang.String METADATA_KEY_TITLE = "android.media.metadata.TITLE";
    public static final java.lang.String METADATA_KEY_ARTIST = "android.media.metadata.ARTIST";
    public static final java.lang.String METADATA_KEY_ALBUM = "android.media.metadata.ALBUM";
    public static final java.lang.String METADATA_KEY_ALBUM_ARTIST = "android.media.metadata.ALBUM_ARTIST";
    public static final java.lang.String METADATA_KEY_WRITER = "android.media.metadata.WRITER";
    public static final java.lang.String METADATA_KEY_AUTHOR = "android.media.metadata.AUTHOR";
    public static final java.lang.String METADATA_KEY_COMPOSER = "android.media.metadata.COMPOSER";
    private static final java.lang.String[] PREFERRED_DESCRIPTION_ORDER = {METADATA_KEY_TITLE, METADATA_KEY_ARTIST, METADATA_KEY_ALBUM, METADATA_KEY_ALBUM_ARTIST, METADATA_KEY_WRITER, METADATA_KEY_AUTHOR, METADATA_KEY_COMPOSER};
    public static final java.lang.String METADATA_KEY_DISPLAY_ICON = "android.media.metadata.DISPLAY_ICON";
    public static final java.lang.String METADATA_KEY_ART = "android.media.metadata.ART";
    public static final java.lang.String METADATA_KEY_ALBUM_ART = "android.media.metadata.ALBUM_ART";
    private static final java.lang.String[] PREFERRED_BITMAP_ORDER = {METADATA_KEY_DISPLAY_ICON, METADATA_KEY_ART, METADATA_KEY_ALBUM_ART};
    public static final java.lang.String METADATA_KEY_DISPLAY_ICON_URI = "android.media.metadata.DISPLAY_ICON_URI";
    public static final java.lang.String METADATA_KEY_ART_URI = "android.media.metadata.ART_URI";
    public static final java.lang.String METADATA_KEY_ALBUM_ART_URI = "android.media.metadata.ALBUM_ART_URI";
    private static final java.lang.String[] PREFERRED_URI_ORDER = {METADATA_KEY_DISPLAY_ICON_URI, METADATA_KEY_ART_URI, METADATA_KEY_ALBUM_ART_URI};
    private static final android.util.ArrayMap<java.lang.String, java.lang.Integer> METADATA_KEYS_TYPE = new android.util.ArrayMap<>();

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface BitmapKey {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface LongKey {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface RatingKey {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface TextKey {
    }

    static {
        METADATA_KEYS_TYPE.put(METADATA_KEY_TITLE, 1);
        METADATA_KEYS_TYPE.put(METADATA_KEY_ARTIST, 1);
        METADATA_KEYS_TYPE.put(METADATA_KEY_DURATION, 0);
        METADATA_KEYS_TYPE.put(METADATA_KEY_ALBUM, 1);
        METADATA_KEYS_TYPE.put(METADATA_KEY_AUTHOR, 1);
        METADATA_KEYS_TYPE.put(METADATA_KEY_WRITER, 1);
        METADATA_KEYS_TYPE.put(METADATA_KEY_COMPOSER, 1);
        METADATA_KEYS_TYPE.put(METADATA_KEY_COMPILATION, 1);
        METADATA_KEYS_TYPE.put(METADATA_KEY_DATE, 1);
        METADATA_KEYS_TYPE.put(METADATA_KEY_YEAR, 0);
        METADATA_KEYS_TYPE.put(METADATA_KEY_GENRE, 1);
        METADATA_KEYS_TYPE.put(METADATA_KEY_TRACK_NUMBER, 0);
        METADATA_KEYS_TYPE.put(METADATA_KEY_NUM_TRACKS, 0);
        METADATA_KEYS_TYPE.put(METADATA_KEY_DISC_NUMBER, 0);
        METADATA_KEYS_TYPE.put(METADATA_KEY_ALBUM_ARTIST, 1);
        METADATA_KEYS_TYPE.put(METADATA_KEY_ART, 2);
        METADATA_KEYS_TYPE.put(METADATA_KEY_ART_URI, 1);
        METADATA_KEYS_TYPE.put(METADATA_KEY_ALBUM_ART, 2);
        METADATA_KEYS_TYPE.put(METADATA_KEY_ALBUM_ART_URI, 1);
        METADATA_KEYS_TYPE.put(METADATA_KEY_USER_RATING, 3);
        METADATA_KEYS_TYPE.put(METADATA_KEY_RATING, 3);
        METADATA_KEYS_TYPE.put(METADATA_KEY_DISPLAY_TITLE, 1);
        METADATA_KEYS_TYPE.put(METADATA_KEY_DISPLAY_SUBTITLE, 1);
        METADATA_KEYS_TYPE.put(METADATA_KEY_DISPLAY_DESCRIPTION, 1);
        METADATA_KEYS_TYPE.put(METADATA_KEY_DISPLAY_ICON, 2);
        METADATA_KEYS_TYPE.put(METADATA_KEY_DISPLAY_ICON_URI, 1);
        METADATA_KEYS_TYPE.put(METADATA_KEY_BT_FOLDER_TYPE, 0);
        METADATA_KEYS_TYPE.put(METADATA_KEY_MEDIA_ID, 1);
        METADATA_KEYS_TYPE.put(METADATA_KEY_MEDIA_URI, 1);
        EDITOR_KEY_MAPPING = new android.util.SparseArray<>();
        EDITOR_KEY_MAPPING.put(100, METADATA_KEY_ART);
        EDITOR_KEY_MAPPING.put(101, METADATA_KEY_RATING);
        EDITOR_KEY_MAPPING.put(android.media.MediaMetadataEditor.RATING_KEY_BY_USER, METADATA_KEY_USER_RATING);
        EDITOR_KEY_MAPPING.put(1, METADATA_KEY_ALBUM);
        EDITOR_KEY_MAPPING.put(13, METADATA_KEY_ALBUM_ARTIST);
        EDITOR_KEY_MAPPING.put(2, METADATA_KEY_ARTIST);
        EDITOR_KEY_MAPPING.put(3, METADATA_KEY_AUTHOR);
        EDITOR_KEY_MAPPING.put(0, METADATA_KEY_TRACK_NUMBER);
        EDITOR_KEY_MAPPING.put(4, METADATA_KEY_COMPOSER);
        EDITOR_KEY_MAPPING.put(15, METADATA_KEY_COMPILATION);
        EDITOR_KEY_MAPPING.put(5, METADATA_KEY_DATE);
        EDITOR_KEY_MAPPING.put(14, METADATA_KEY_DISC_NUMBER);
        EDITOR_KEY_MAPPING.put(9, METADATA_KEY_DURATION);
        EDITOR_KEY_MAPPING.put(6, METADATA_KEY_GENRE);
        EDITOR_KEY_MAPPING.put(10, METADATA_KEY_NUM_TRACKS);
        EDITOR_KEY_MAPPING.put(7, METADATA_KEY_TITLE);
        EDITOR_KEY_MAPPING.put(11, METADATA_KEY_WRITER);
        EDITOR_KEY_MAPPING.put(8, METADATA_KEY_YEAR);
        CREATOR = new android.os.Parcelable.Creator<android.media.MediaMetadata>() { // from class: android.media.MediaMetadata.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.media.MediaMetadata createFromParcel(android.os.Parcel parcel) {
                return new android.media.MediaMetadata(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.media.MediaMetadata[] newArray(int i) {
                return new android.media.MediaMetadata[i];
            }
        };
    }

    private MediaMetadata(android.os.Bundle bundle, int i) {
        this.mBundle = new android.os.Bundle(bundle);
        this.mBitmapDimensionLimit = i;
    }

    private MediaMetadata(android.os.Parcel parcel) {
        this.mBundle = parcel.readBundle();
        this.mBitmapDimensionLimit = java.lang.Math.max(parcel.readInt(), 1);
        getBitmap(METADATA_KEY_ART);
        getBitmap(METADATA_KEY_ALBUM_ART);
        getBitmap(METADATA_KEY_DISPLAY_ICON);
    }

    public boolean containsKey(java.lang.String str) {
        return this.mBundle.containsKey(str);
    }

    public java.lang.CharSequence getText(java.lang.String str) {
        return this.mBundle.getCharSequence(str);
    }

    public java.lang.String getString(java.lang.String str) {
        java.lang.CharSequence text = getText(str);
        if (text != null) {
            return text.toString();
        }
        return null;
    }

    public long getLong(java.lang.String str) {
        return this.mBundle.getLong(str, 0L);
    }

    public android.media.Rating getRating(java.lang.String str) {
        try {
            return (android.media.Rating) this.mBundle.getParcelable(str, android.media.Rating.class);
        } catch (java.lang.Exception e) {
            android.util.Log.w(TAG, "Failed to retrieve a key as Rating.", e);
            return null;
        }
    }

    public android.graphics.Bitmap getBitmap(java.lang.String str) {
        try {
            return (android.graphics.Bitmap) this.mBundle.getParcelable(str, android.graphics.Bitmap.class);
        } catch (java.lang.Exception e) {
            android.util.Log.w(TAG, "Failed to retrieve a key as Bitmap.", e);
            return null;
        }
    }

    public int getBitmapDimensionLimit() {
        return this.mBitmapDimensionLimit;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeBundle(this.mBundle);
        parcel.writeInt(this.mBitmapDimensionLimit);
    }

    public int size() {
        return this.mBundle.size();
    }

    public java.util.Set<java.lang.String> keySet() {
        return this.mBundle.keySet();
    }

    public android.media.MediaDescription getDescription() {
        android.graphics.Bitmap bitmap;
        android.net.Uri uri;
        if (this.mDescription != null) {
            return this.mDescription;
        }
        java.lang.String string = getString(METADATA_KEY_MEDIA_ID);
        java.lang.CharSequence[] charSequenceArr = new java.lang.CharSequence[3];
        java.lang.CharSequence text = getText(METADATA_KEY_DISPLAY_TITLE);
        if (!android.text.TextUtils.isEmpty(text)) {
            charSequenceArr[0] = text;
            charSequenceArr[1] = getText(METADATA_KEY_DISPLAY_SUBTITLE);
            charSequenceArr[2] = getText(METADATA_KEY_DISPLAY_DESCRIPTION);
        } else {
            int i = 0;
            int i2 = 0;
            while (i < 3 && i2 < PREFERRED_DESCRIPTION_ORDER.length) {
                int i3 = i2 + 1;
                java.lang.CharSequence text2 = getText(PREFERRED_DESCRIPTION_ORDER[i2]);
                if (!android.text.TextUtils.isEmpty(text2)) {
                    charSequenceArr[i] = text2;
                    i++;
                }
                i2 = i3;
            }
        }
        int i4 = 0;
        while (true) {
            if (i4 >= PREFERRED_BITMAP_ORDER.length) {
                bitmap = null;
                break;
            }
            bitmap = getBitmap(PREFERRED_BITMAP_ORDER[i4]);
            if (bitmap != null) {
                break;
            }
            i4++;
        }
        int i5 = 0;
        while (true) {
            if (i5 >= PREFERRED_URI_ORDER.length) {
                uri = null;
                break;
            }
            java.lang.String string2 = getString(PREFERRED_URI_ORDER[i5]);
            if (android.text.TextUtils.isEmpty(string2)) {
                i5++;
            } else {
                uri = android.net.Uri.parse(string2);
                break;
            }
        }
        java.lang.String string3 = getString(METADATA_KEY_MEDIA_URI);
        android.net.Uri parse = android.text.TextUtils.isEmpty(string3) ? null : android.net.Uri.parse(string3);
        android.media.MediaDescription.Builder builder = new android.media.MediaDescription.Builder();
        builder.setMediaId(string);
        builder.setTitle(charSequenceArr[0]);
        builder.setSubtitle(charSequenceArr[1]);
        builder.setDescription(charSequenceArr[2]);
        builder.setIconBitmap(bitmap);
        builder.setIconUri(uri);
        builder.setMediaUri(parse);
        if (this.mBundle.containsKey(METADATA_KEY_BT_FOLDER_TYPE)) {
            android.os.Bundle bundle = new android.os.Bundle();
            bundle.putLong(android.media.MediaDescription.EXTRA_BT_FOLDER_TYPE, getLong(METADATA_KEY_BT_FOLDER_TYPE));
            builder.setExtras(bundle);
        }
        this.mDescription = builder.build();
        return this.mDescription;
    }

    public static java.lang.String getKeyFromMetadataEditorKey(int i) {
        return EDITOR_KEY_MAPPING.get(i, null);
    }

    public boolean equals(java.lang.Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof android.media.MediaMetadata)) {
            return false;
        }
        android.media.MediaMetadata mediaMetadata = (android.media.MediaMetadata) obj;
        for (int i = 0; i < METADATA_KEYS_TYPE.size(); i++) {
            java.lang.String keyAt = METADATA_KEYS_TYPE.keyAt(i);
            switch (METADATA_KEYS_TYPE.valueAt(i).intValue()) {
                case 0:
                    if (getLong(keyAt) != mediaMetadata.getLong(keyAt)) {
                        return false;
                    }
                    break;
                case 1:
                    if (!java.util.Objects.equals(getString(keyAt), mediaMetadata.getString(keyAt))) {
                        return false;
                    }
                    break;
            }
        }
        return true;
    }

    public int hashCode() {
        int i = 17;
        for (int i2 = 0; i2 < METADATA_KEYS_TYPE.size(); i2++) {
            java.lang.String keyAt = METADATA_KEYS_TYPE.keyAt(i2);
            switch (METADATA_KEYS_TYPE.valueAt(i2).intValue()) {
                case 0:
                    i = (i * 31) + java.lang.Long.hashCode(getLong(keyAt));
                    break;
                case 1:
                    i = (i * 31) + java.util.Objects.hash(getString(keyAt));
                    break;
            }
        }
        return i;
    }

    public static final class Builder {
        private int mBitmapDimensionLimit;
        private final android.os.Bundle mBundle;

        public Builder() {
            this.mBitmapDimensionLimit = Integer.MAX_VALUE;
            this.mBundle = new android.os.Bundle();
        }

        public Builder(android.media.MediaMetadata mediaMetadata) {
            this.mBitmapDimensionLimit = Integer.MAX_VALUE;
            this.mBundle = new android.os.Bundle(mediaMetadata.mBundle);
            this.mBitmapDimensionLimit = mediaMetadata.mBitmapDimensionLimit;
        }

        public android.media.MediaMetadata.Builder putText(java.lang.String str, java.lang.CharSequence charSequence) {
            if (android.media.MediaMetadata.METADATA_KEYS_TYPE.containsKey(str) && ((java.lang.Integer) android.media.MediaMetadata.METADATA_KEYS_TYPE.get(str)).intValue() != 1) {
                throw new java.lang.IllegalArgumentException("The " + str + " key cannot be used to put a CharSequence");
            }
            this.mBundle.putCharSequence(str, charSequence);
            return this;
        }

        public android.media.MediaMetadata.Builder putString(java.lang.String str, java.lang.String str2) {
            if (android.media.MediaMetadata.METADATA_KEYS_TYPE.containsKey(str) && ((java.lang.Integer) android.media.MediaMetadata.METADATA_KEYS_TYPE.get(str)).intValue() != 1) {
                throw new java.lang.IllegalArgumentException("The " + str + " key cannot be used to put a String");
            }
            this.mBundle.putCharSequence(str, str2);
            return this;
        }

        public android.media.MediaMetadata.Builder putLong(java.lang.String str, long j) {
            if (android.media.MediaMetadata.METADATA_KEYS_TYPE.containsKey(str) && ((java.lang.Integer) android.media.MediaMetadata.METADATA_KEYS_TYPE.get(str)).intValue() != 0) {
                throw new java.lang.IllegalArgumentException("The " + str + " key cannot be used to put a long");
            }
            this.mBundle.putLong(str, j);
            return this;
        }

        public android.media.MediaMetadata.Builder putRating(java.lang.String str, android.media.Rating rating) {
            if (android.media.MediaMetadata.METADATA_KEYS_TYPE.containsKey(str) && ((java.lang.Integer) android.media.MediaMetadata.METADATA_KEYS_TYPE.get(str)).intValue() != 3) {
                throw new java.lang.IllegalArgumentException("The " + str + " key cannot be used to put a Rating");
            }
            this.mBundle.putParcelable(str, rating);
            return this;
        }

        public android.media.MediaMetadata.Builder putBitmap(java.lang.String str, android.graphics.Bitmap bitmap) {
            if (android.media.MediaMetadata.METADATA_KEYS_TYPE.containsKey(str) && ((java.lang.Integer) android.media.MediaMetadata.METADATA_KEYS_TYPE.get(str)).intValue() != 2) {
                throw new java.lang.IllegalArgumentException("The " + str + " key cannot be used to put a Bitmap");
            }
            this.mBundle.putParcelable(str, bitmap);
            return this;
        }

        public android.media.MediaMetadata.Builder setBitmapDimensionLimit(int i) {
            if (i > 0) {
                this.mBitmapDimensionLimit = i;
            } else {
                android.util.Log.w(android.media.MediaMetadata.TAG, "setBitmapDimensionLimit(): Ignoring non-positive bitmapDimensionLimit: " + i);
            }
            return this;
        }

        public android.media.MediaMetadata build() {
            if (this.mBitmapDimensionLimit != Integer.MAX_VALUE) {
                for (java.lang.String str : this.mBundle.keySet()) {
                    java.lang.Object obj = this.mBundle.get(str);
                    if (obj instanceof android.graphics.Bitmap) {
                        android.graphics.Bitmap bitmap = (android.graphics.Bitmap) obj;
                        if (bitmap.getHeight() > this.mBitmapDimensionLimit || bitmap.getWidth() > this.mBitmapDimensionLimit) {
                            putBitmap(str, scaleBitmap(bitmap, this.mBitmapDimensionLimit));
                        }
                    }
                }
            }
            return new android.media.MediaMetadata(this.mBundle, this.mBitmapDimensionLimit);
        }

        private android.graphics.Bitmap scaleBitmap(android.graphics.Bitmap bitmap, int i) {
            float f = i;
            float min = java.lang.Math.min(f / bitmap.getWidth(), f / bitmap.getHeight());
            return android.graphics.Bitmap.createScaledBitmap(bitmap, (int) (bitmap.getWidth() * min), (int) (bitmap.getHeight() * min), true);
        }
    }
}
