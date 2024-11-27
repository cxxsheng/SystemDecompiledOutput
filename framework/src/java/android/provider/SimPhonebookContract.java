package android.provider;

/* loaded from: classes3.dex */
public final class SimPhonebookContract {
    public static final java.lang.String AUTHORITY = "com.android.simphonebook";
    public static final android.net.Uri AUTHORITY_URI = android.net.Uri.parse("content://com.android.simphonebook");
    public static final java.lang.String SUBSCRIPTION_ID_PATH_SEGMENT = "subid";

    private SimPhonebookContract() {
    }

    public static java.lang.String getEfUriPath(int i) {
        switch (i) {
            case 1:
                return android.provider.SimPhonebookContract.ElementaryFiles.PATH_SEGMENT_EF_ADN;
            case 2:
                return android.provider.SimPhonebookContract.ElementaryFiles.PATH_SEGMENT_EF_FDN;
            case 3:
                return android.provider.SimPhonebookContract.ElementaryFiles.PATH_SEGMENT_EF_SDN;
            default:
                throw new java.lang.IllegalArgumentException("Unsupported EfType " + i);
        }
    }

    public static final class SimRecords {
        public static final java.lang.String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/sim-contact_v2";
        public static final java.lang.String CONTENT_TYPE = "vnd.android.cursor.dir/sim-contact_v2";
        public static final java.lang.String ELEMENTARY_FILE_TYPE = "elementary_file_type";
        public static final int ERROR_NAME_UNSUPPORTED = -1;
        public static final java.lang.String EXTRA_ENCODED_NAME_LENGTH = "android.provider.extra.ENCODED_NAME_LENGTH";
        public static final java.lang.String GET_ENCODED_NAME_LENGTH_METHOD_NAME = "get_encoded_name_length";
        public static final java.lang.String NAME = "name";
        public static final java.lang.String PHONE_NUMBER = "phone_number";

        @android.annotation.SystemApi
        public static final java.lang.String QUERY_ARG_PIN2 = "android:query-arg-pin2";
        public static final java.lang.String RECORD_NUMBER = "record_number";
        public static final java.lang.String SUBSCRIPTION_ID = "subscription_id";

        private SimRecords() {
        }

        public static android.net.Uri getContentUri(int i, int i2) {
            return buildContentUri(i, i2).build();
        }

        public static android.net.Uri getItemUri(int i, int i2, int i3) {
            com.android.internal.util.Preconditions.checkArgument(i3 > 0, "Invalid recordNumber");
            return buildContentUri(i, i2).appendPath(java.lang.String.valueOf(i3)).build();
        }

        public static int getEncodedNameLength(android.content.ContentResolver contentResolver, java.lang.String str) {
            java.util.Objects.requireNonNull(str);
            android.os.Bundle call = contentResolver.call(android.provider.SimPhonebookContract.AUTHORITY, GET_ENCODED_NAME_LENGTH_METHOD_NAME, str, (android.os.Bundle) null);
            if (call == null || !call.containsKey(EXTRA_ENCODED_NAME_LENGTH)) {
                throw new java.lang.IllegalStateException("Provider malfunction: no length was returned.");
            }
            int i = call.getInt(EXTRA_ENCODED_NAME_LENGTH, -1);
            if (i < 0 && i != -1) {
                throw new java.lang.IllegalStateException("Provider malfunction: invalid length was returned.");
            }
            return i;
        }

        private static android.net.Uri.Builder buildContentUri(int i, int i2) {
            return new android.net.Uri.Builder().scheme("content").authority(android.provider.SimPhonebookContract.AUTHORITY).appendPath(android.provider.SimPhonebookContract.SUBSCRIPTION_ID_PATH_SEGMENT).appendPath(java.lang.String.valueOf(i)).appendPath(android.provider.SimPhonebookContract.getEfUriPath(i2));
        }
    }

    public static final class ElementaryFiles {
        public static final java.lang.String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/sim-elementary-file";
        public static final java.lang.String CONTENT_TYPE = "vnd.android.cursor.dir/sim-elementary-file";
        public static final int EF_ADN = 1;
        public static final int EF_FDN = 2;
        public static final int EF_SDN = 3;
        public static final java.lang.String EF_TYPE = "ef_type";
        public static final int EF_UNKNOWN = 0;
        public static final java.lang.String MAX_RECORDS = "max_records";
        public static final java.lang.String NAME_MAX_LENGTH = "name_max_length";
        public static final java.lang.String PATH_SEGMENT_EF_ADN = "adn";
        public static final java.lang.String PATH_SEGMENT_EF_FDN = "fdn";
        public static final java.lang.String PATH_SEGMENT_EF_SDN = "sdn";
        public static final java.lang.String PHONE_NUMBER_MAX_LENGTH = "phone_number_max_length";
        public static final java.lang.String RECORD_COUNT = "record_count";
        public static final java.lang.String SLOT_INDEX = "slot_index";
        public static final java.lang.String SUBSCRIPTION_ID = "subscription_id";
        public static final java.lang.String ELEMENTARY_FILES_PATH_SEGMENT = "elementary_files";
        public static final android.net.Uri CONTENT_URI = android.provider.SimPhonebookContract.AUTHORITY_URI.buildUpon().appendPath(ELEMENTARY_FILES_PATH_SEGMENT).build();

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface EfType {
        }

        private ElementaryFiles() {
        }

        public static android.net.Uri getItemUri(int i, int i2) {
            return CONTENT_URI.buildUpon().appendPath(android.provider.SimPhonebookContract.SUBSCRIPTION_ID_PATH_SEGMENT).appendPath(java.lang.String.valueOf(i)).appendPath(android.provider.SimPhonebookContract.getEfUriPath(i2)).build();
        }
    }
}
