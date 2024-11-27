package android.media;

@java.lang.Deprecated
/* loaded from: classes2.dex */
public abstract class MediaMetadataEditor {
    public static final int BITMAP_KEY_ARTWORK = 100;
    public static final int KEY_EDITABLE_MASK = 536870911;
    protected static final android.util.SparseIntArray METADATA_KEYS_TYPE = new android.util.SparseIntArray(17);
    protected static final int METADATA_TYPE_BITMAP = 2;
    protected static final int METADATA_TYPE_INVALID = -1;
    protected static final int METADATA_TYPE_LONG = 0;
    protected static final int METADATA_TYPE_RATING = 3;
    protected static final int METADATA_TYPE_STRING = 1;
    public static final int RATING_KEY_BY_OTHERS = 101;
    public static final int RATING_KEY_BY_USER = 268435457;
    private static final java.lang.String TAG = "MediaMetadataEditor";
    protected long mEditableKeys;
    protected android.graphics.Bitmap mEditorArtwork;
    protected android.os.Bundle mEditorMetadata;
    protected android.media.MediaMetadata.Builder mMetadataBuilder;
    protected boolean mMetadataChanged = false;
    protected boolean mApplied = false;
    protected boolean mArtworkChanged = false;

    public abstract void apply();

    protected MediaMetadataEditor() {
    }

    public synchronized void clear() {
        if (this.mApplied) {
            android.util.Log.e(TAG, "Can't clear a previously applied MediaMetadataEditor");
            return;
        }
        this.mEditorMetadata.clear();
        this.mEditorArtwork = null;
        this.mMetadataBuilder = new android.media.MediaMetadata.Builder();
    }

    public synchronized void addEditableKey(int i) {
        if (this.mApplied) {
            android.util.Log.e(TAG, "Can't change editable keys of a previously applied MetadataEditor");
            return;
        }
        if (i == 268435457) {
            this.mEditableKeys |= i & KEY_EDITABLE_MASK;
            this.mMetadataChanged = true;
        } else {
            android.util.Log.e(TAG, "Metadata key " + i + " cannot be edited");
        }
    }

    public synchronized void removeEditableKeys() {
        if (this.mApplied) {
            android.util.Log.e(TAG, "Can't remove all editable keys of a previously applied MetadataEditor");
            return;
        }
        if (this.mEditableKeys != 0) {
            this.mEditableKeys = 0L;
            this.mMetadataChanged = true;
        }
    }

    public synchronized int[] getEditableKeys() {
        if (this.mEditableKeys != 268435457) {
            return null;
        }
        return new int[]{RATING_KEY_BY_USER};
    }

    public synchronized android.media.MediaMetadataEditor putString(int i, java.lang.String str) throws java.lang.IllegalArgumentException {
        if (this.mApplied) {
            android.util.Log.e(TAG, "Can't edit a previously applied MediaMetadataEditor");
            return this;
        }
        if (METADATA_KEYS_TYPE.get(i, -1) != 1) {
            throw new java.lang.IllegalArgumentException("Invalid type 'String' for key " + i);
        }
        this.mEditorMetadata.putString(java.lang.String.valueOf(i), str);
        this.mMetadataChanged = true;
        return this;
    }

    public synchronized android.media.MediaMetadataEditor putLong(int i, long j) throws java.lang.IllegalArgumentException {
        if (this.mApplied) {
            android.util.Log.e(TAG, "Can't edit a previously applied MediaMetadataEditor");
            return this;
        }
        if (METADATA_KEYS_TYPE.get(i, -1) != 0) {
            throw new java.lang.IllegalArgumentException("Invalid type 'long' for key " + i);
        }
        this.mEditorMetadata.putLong(java.lang.String.valueOf(i), j);
        this.mMetadataChanged = true;
        return this;
    }

    public synchronized android.media.MediaMetadataEditor putBitmap(int i, android.graphics.Bitmap bitmap) throws java.lang.IllegalArgumentException {
        if (this.mApplied) {
            android.util.Log.e(TAG, "Can't edit a previously applied MediaMetadataEditor");
            return this;
        }
        if (i != 100) {
            throw new java.lang.IllegalArgumentException("Invalid type 'Bitmap' for key " + i);
        }
        this.mEditorArtwork = bitmap;
        this.mArtworkChanged = true;
        return this;
    }

    public synchronized android.media.MediaMetadataEditor putObject(int i, java.lang.Object obj) throws java.lang.IllegalArgumentException {
        if (this.mApplied) {
            android.util.Log.e(TAG, "Can't edit a previously applied MediaMetadataEditor");
            return this;
        }
        switch (METADATA_KEYS_TYPE.get(i, -1)) {
            case 0:
                if (obj instanceof java.lang.Long) {
                    return putLong(i, ((java.lang.Long) obj).longValue());
                }
                throw new java.lang.IllegalArgumentException("Not a non-null Long for key " + i);
            case 1:
                if (obj != null && !(obj instanceof java.lang.String)) {
                    throw new java.lang.IllegalArgumentException("Not a String for key " + i);
                }
                return putString(i, (java.lang.String) obj);
            case 2:
                if (obj != null && !(obj instanceof android.graphics.Bitmap)) {
                    throw new java.lang.IllegalArgumentException("Not a Bitmap for key " + i);
                }
                return putBitmap(i, (android.graphics.Bitmap) obj);
            case 3:
                this.mEditorMetadata.putParcelable(java.lang.String.valueOf(i), (android.os.Parcelable) obj);
                this.mMetadataChanged = true;
                return this;
            default:
                throw new java.lang.IllegalArgumentException("Invalid key " + i);
        }
    }

    public synchronized long getLong(int i, long j) throws java.lang.IllegalArgumentException {
        if (METADATA_KEYS_TYPE.get(i, -1) != 0) {
            throw new java.lang.IllegalArgumentException("Invalid type 'long' for key " + i);
        }
        return this.mEditorMetadata.getLong(java.lang.String.valueOf(i), j);
    }

    public synchronized java.lang.String getString(int i, java.lang.String str) throws java.lang.IllegalArgumentException {
        if (METADATA_KEYS_TYPE.get(i, -1) != 1) {
            throw new java.lang.IllegalArgumentException("Invalid type 'String' for key " + i);
        }
        return this.mEditorMetadata.getString(java.lang.String.valueOf(i), str);
    }

    public synchronized android.graphics.Bitmap getBitmap(int i, android.graphics.Bitmap bitmap) throws java.lang.IllegalArgumentException {
        if (i != 100) {
            throw new java.lang.IllegalArgumentException("Invalid type 'Bitmap' for key " + i);
        }
        if (this.mEditorArtwork != null) {
            bitmap = this.mEditorArtwork;
        }
        return bitmap;
    }

    public synchronized java.lang.Object getObject(int i, java.lang.Object obj) throws java.lang.IllegalArgumentException {
        switch (METADATA_KEYS_TYPE.get(i, -1)) {
            case 0:
                if (!this.mEditorMetadata.containsKey(java.lang.String.valueOf(i))) {
                    return obj;
                }
                return java.lang.Long.valueOf(this.mEditorMetadata.getLong(java.lang.String.valueOf(i)));
            case 1:
                if (!this.mEditorMetadata.containsKey(java.lang.String.valueOf(i))) {
                    return obj;
                }
                return this.mEditorMetadata.getString(java.lang.String.valueOf(i));
            case 2:
                if (i == 100) {
                    if (this.mEditorArtwork != null) {
                        obj = this.mEditorArtwork;
                    }
                    return obj;
                }
                break;
            case 3:
                if (!this.mEditorMetadata.containsKey(java.lang.String.valueOf(i))) {
                    return obj;
                }
                return this.mEditorMetadata.getParcelable(java.lang.String.valueOf(i));
        }
        throw new java.lang.IllegalArgumentException("Invalid key " + i);
    }

    static {
        METADATA_KEYS_TYPE.put(0, 0);
        METADATA_KEYS_TYPE.put(14, 0);
        METADATA_KEYS_TYPE.put(9, 0);
        METADATA_KEYS_TYPE.put(8, 0);
        METADATA_KEYS_TYPE.put(1, 1);
        METADATA_KEYS_TYPE.put(13, 1);
        METADATA_KEYS_TYPE.put(7, 1);
        METADATA_KEYS_TYPE.put(2, 1);
        METADATA_KEYS_TYPE.put(3, 1);
        METADATA_KEYS_TYPE.put(15, 1);
        METADATA_KEYS_TYPE.put(4, 1);
        METADATA_KEYS_TYPE.put(5, 1);
        METADATA_KEYS_TYPE.put(6, 1);
        METADATA_KEYS_TYPE.put(11, 1);
        METADATA_KEYS_TYPE.put(100, 2);
        METADATA_KEYS_TYPE.put(101, 3);
        METADATA_KEYS_TYPE.put(RATING_KEY_BY_USER, 3);
    }
}
