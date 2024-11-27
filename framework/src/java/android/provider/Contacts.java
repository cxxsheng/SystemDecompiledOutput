package android.provider;

@java.lang.Deprecated
/* loaded from: classes3.dex */
public class Contacts {

    @java.lang.Deprecated
    public static final java.lang.String AUTHORITY = "contacts";

    @java.lang.Deprecated
    public static final android.net.Uri CONTENT_URI = android.net.Uri.parse("content://contacts");

    @java.lang.Deprecated
    public static final int KIND_EMAIL = 1;

    @java.lang.Deprecated
    public static final int KIND_IM = 3;

    @java.lang.Deprecated
    public static final int KIND_ORGANIZATION = 4;

    @java.lang.Deprecated
    public static final int KIND_PHONE = 5;

    @java.lang.Deprecated
    public static final int KIND_POSTAL = 2;
    private static final java.lang.String TAG = "Contacts";

    @java.lang.Deprecated
    public interface ContactMethodsColumns {

        @java.lang.Deprecated
        public static final java.lang.String AUX_DATA = "aux_data";

        @java.lang.Deprecated
        public static final java.lang.String DATA = "data";

        @java.lang.Deprecated
        public static final java.lang.String ISPRIMARY = "isprimary";

        @java.lang.Deprecated
        public static final java.lang.String KIND = "kind";

        @java.lang.Deprecated
        public static final java.lang.String LABEL = "label";

        @java.lang.Deprecated
        public static final int MOBILE_EMAIL_TYPE_INDEX = 2;

        @java.lang.Deprecated
        public static final java.lang.String MOBILE_EMAIL_TYPE_NAME = "_AUTO_CELL";

        @java.lang.Deprecated
        public static final java.lang.String TYPE = "type";

        @java.lang.Deprecated
        public static final int TYPE_CUSTOM = 0;

        @java.lang.Deprecated
        public static final int TYPE_HOME = 1;

        @java.lang.Deprecated
        public static final int TYPE_OTHER = 3;

        @java.lang.Deprecated
        public static final int TYPE_WORK = 2;
    }

    @java.lang.Deprecated
    public interface ExtensionsColumns {

        @java.lang.Deprecated
        public static final java.lang.String NAME = "name";

        @java.lang.Deprecated
        public static final java.lang.String VALUE = "value";
    }

    @java.lang.Deprecated
    public interface GroupsColumns {

        @java.lang.Deprecated
        public static final java.lang.String NAME = "name";

        @java.lang.Deprecated
        public static final java.lang.String NOTES = "notes";

        @java.lang.Deprecated
        public static final java.lang.String SHOULD_SYNC = "should_sync";

        @java.lang.Deprecated
        public static final java.lang.String SYSTEM_ID = "system_id";
    }

    @java.lang.Deprecated
    public interface OrganizationColumns {

        @java.lang.Deprecated
        public static final java.lang.String COMPANY = "company";

        @java.lang.Deprecated
        public static final java.lang.String ISPRIMARY = "isprimary";

        @java.lang.Deprecated
        public static final java.lang.String LABEL = "label";

        @java.lang.Deprecated
        public static final java.lang.String PERSON_ID = "person";

        @java.lang.Deprecated
        public static final java.lang.String TITLE = "title";

        @java.lang.Deprecated
        public static final java.lang.String TYPE = "type";

        @java.lang.Deprecated
        public static final int TYPE_CUSTOM = 0;

        @java.lang.Deprecated
        public static final int TYPE_OTHER = 2;

        @java.lang.Deprecated
        public static final int TYPE_WORK = 1;
    }

    @java.lang.Deprecated
    public interface PeopleColumns {

        @java.lang.Deprecated
        public static final java.lang.String CUSTOM_RINGTONE = "custom_ringtone";

        @java.lang.Deprecated
        public static final java.lang.String DISPLAY_NAME = "display_name";

        @java.lang.Deprecated
        public static final java.lang.String LAST_TIME_CONTACTED = "last_time_contacted";

        @java.lang.Deprecated
        public static final java.lang.String NAME = "name";

        @java.lang.Deprecated
        public static final java.lang.String NOTES = "notes";

        @java.lang.Deprecated
        public static final java.lang.String PHONETIC_NAME = "phonetic_name";

        @java.lang.Deprecated
        public static final java.lang.String PHOTO_VERSION = "photo_version";

        @java.lang.Deprecated
        public static final java.lang.String SEND_TO_VOICEMAIL = "send_to_voicemail";

        @java.lang.Deprecated
        public static final java.lang.String SORT_STRING = "sort_string";

        @java.lang.Deprecated
        public static final java.lang.String STARRED = "starred";

        @java.lang.Deprecated
        public static final java.lang.String TIMES_CONTACTED = "times_contacted";
    }

    @java.lang.Deprecated
    public interface PhonesColumns {

        @java.lang.Deprecated
        public static final java.lang.String ISPRIMARY = "isprimary";

        @java.lang.Deprecated
        public static final java.lang.String LABEL = "label";

        @java.lang.Deprecated
        public static final java.lang.String NUMBER = "number";

        @java.lang.Deprecated
        public static final java.lang.String NUMBER_KEY = "number_key";

        @java.lang.Deprecated
        public static final java.lang.String TYPE = "type";

        @java.lang.Deprecated
        public static final int TYPE_CUSTOM = 0;

        @java.lang.Deprecated
        public static final int TYPE_FAX_HOME = 5;

        @java.lang.Deprecated
        public static final int TYPE_FAX_WORK = 4;

        @java.lang.Deprecated
        public static final int TYPE_HOME = 1;

        @java.lang.Deprecated
        public static final int TYPE_MOBILE = 2;

        @java.lang.Deprecated
        public static final int TYPE_OTHER = 7;

        @java.lang.Deprecated
        public static final int TYPE_PAGER = 6;

        @java.lang.Deprecated
        public static final int TYPE_WORK = 3;
    }

    @java.lang.Deprecated
    public interface PhotosColumns {

        @java.lang.Deprecated
        public static final java.lang.String DATA = "data";

        @java.lang.Deprecated
        public static final java.lang.String DOWNLOAD_REQUIRED = "download_required";

        @java.lang.Deprecated
        public static final java.lang.String EXISTS_ON_SERVER = "exists_on_server";

        @java.lang.Deprecated
        public static final java.lang.String LOCAL_VERSION = "local_version";

        @java.lang.Deprecated
        public static final java.lang.String PERSON_ID = "person";

        @java.lang.Deprecated
        public static final java.lang.String SYNC_ERROR = "sync_error";
    }

    @java.lang.Deprecated
    public interface PresenceColumns {
        public static final int AVAILABLE = 5;
        public static final int AWAY = 2;
        public static final int DO_NOT_DISTURB = 4;
        public static final int IDLE = 3;

        @java.lang.Deprecated
        public static final java.lang.String IM_ACCOUNT = "im_account";

        @java.lang.Deprecated
        public static final java.lang.String IM_HANDLE = "im_handle";

        @java.lang.Deprecated
        public static final java.lang.String IM_PROTOCOL = "im_protocol";
        public static final int INVISIBLE = 1;
        public static final int OFFLINE = 0;
        public static final java.lang.String PRESENCE_CUSTOM_STATUS = "status";
        public static final java.lang.String PRESENCE_STATUS = "mode";
        public static final java.lang.String PRIORITY = "priority";
    }

    @java.lang.Deprecated
    public interface SettingsColumns {

        @java.lang.Deprecated
        public static final java.lang.String KEY = "key";

        @java.lang.Deprecated
        public static final java.lang.String VALUE = "value";

        @java.lang.Deprecated
        public static final java.lang.String _SYNC_ACCOUNT = "_sync_account";

        @java.lang.Deprecated
        public static final java.lang.String _SYNC_ACCOUNT_TYPE = "_sync_account_type";
    }

    private Contacts() {
    }

    @java.lang.Deprecated
    public static final class Settings implements android.provider.BaseColumns, android.provider.Contacts.SettingsColumns {

        @java.lang.Deprecated
        public static final java.lang.String CONTENT_DIRECTORY = "settings";

        @java.lang.Deprecated
        public static final android.net.Uri CONTENT_URI = android.net.Uri.parse("content://contacts/settings");

        @java.lang.Deprecated
        public static final java.lang.String DEFAULT_SORT_ORDER = "key ASC";

        @java.lang.Deprecated
        public static final java.lang.String SYNC_EVERYTHING = "syncEverything";

        private Settings() {
        }

        @java.lang.Deprecated
        public static java.lang.String getSetting(android.content.ContentResolver contentResolver, java.lang.String str, java.lang.String str2) {
            android.database.Cursor query = contentResolver.query(CONTENT_URI, new java.lang.String[]{"value"}, "key=?", new java.lang.String[]{str2}, null);
            try {
                if (query.moveToNext()) {
                    return query.getString(0);
                }
                query.close();
                return null;
            } finally {
                query.close();
            }
        }

        @java.lang.Deprecated
        public static void setSetting(android.content.ContentResolver contentResolver, java.lang.String str, java.lang.String str2, java.lang.String str3) {
            android.content.ContentValues contentValues = new android.content.ContentValues();
            contentValues.put("key", str2);
            contentValues.put("value", str3);
            contentResolver.update(CONTENT_URI, contentValues, null, null);
        }
    }

    @java.lang.Deprecated
    public static final class People implements android.provider.BaseColumns, android.provider.Contacts.PeopleColumns, android.provider.Contacts.PhonesColumns, android.provider.Contacts.PresenceColumns {

        @java.lang.Deprecated
        public static final java.lang.String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/person";

        @java.lang.Deprecated
        public static final java.lang.String CONTENT_TYPE = "vnd.android.cursor.dir/person";

        @java.lang.Deprecated
        public static final java.lang.String DEFAULT_SORT_ORDER = "name ASC";

        @java.lang.Deprecated
        public static final java.lang.String PRIMARY_EMAIL_ID = "primary_email";

        @java.lang.Deprecated
        public static final java.lang.String PRIMARY_ORGANIZATION_ID = "primary_organization";

        @java.lang.Deprecated
        public static final java.lang.String PRIMARY_PHONE_ID = "primary_phone";

        @java.lang.Deprecated
        public static final android.net.Uri CONTENT_URI = android.net.Uri.parse("content://contacts/people");

        @java.lang.Deprecated
        public static final android.net.Uri CONTENT_FILTER_URI = android.net.Uri.parse("content://contacts/people/filter");

        @java.lang.Deprecated
        public static final android.net.Uri DELETED_CONTENT_URI = android.net.Uri.parse("content://contacts/deleted_people");

        @java.lang.Deprecated
        public static final android.net.Uri WITH_EMAIL_OR_IM_FILTER_URI = android.net.Uri.parse("content://contacts/people/with_email_or_im_filter");
        private static final java.lang.String[] GROUPS_PROJECTION = {"_id"};

        @java.lang.Deprecated
        private People() {
        }

        @java.lang.Deprecated
        public static void markAsContacted(android.content.ContentResolver contentResolver, long j) {
        }

        @java.lang.Deprecated
        public static long tryGetMyContactsGroupId(android.content.ContentResolver contentResolver) {
            android.database.Cursor query = contentResolver.query(android.provider.Contacts.Groups.CONTENT_URI, GROUPS_PROJECTION, "system_id='Contacts'", null, null);
            if (query != null) {
                try {
                    if (query.moveToFirst()) {
                        return query.getLong(0);
                    }
                    return 0L;
                } finally {
                    query.close();
                }
            }
            return 0L;
        }

        @java.lang.Deprecated
        public static android.net.Uri addToMyContactsGroup(android.content.ContentResolver contentResolver, long j) {
            long tryGetMyContactsGroupId = tryGetMyContactsGroupId(contentResolver);
            if (tryGetMyContactsGroupId == 0) {
                throw new java.lang.IllegalStateException("Failed to find the My Contacts group");
            }
            return addToGroup(contentResolver, j, tryGetMyContactsGroupId);
        }

        @java.lang.Deprecated
        public static android.net.Uri addToGroup(android.content.ContentResolver contentResolver, long j, java.lang.String str) {
            long j2;
            android.database.Cursor query = contentResolver.query(android.provider.Contacts.Groups.CONTENT_URI, GROUPS_PROJECTION, "name=?", new java.lang.String[]{str}, null);
            if (query == null) {
                j2 = 0;
            } else {
                try {
                    if (!query.moveToFirst()) {
                        j2 = 0;
                    } else {
                        j2 = query.getLong(0);
                    }
                } finally {
                    query.close();
                }
            }
            if (j2 == 0) {
                throw new java.lang.IllegalStateException("Failed to find the My Contacts group");
            }
            return addToGroup(contentResolver, j, j2);
        }

        @java.lang.Deprecated
        public static android.net.Uri addToGroup(android.content.ContentResolver contentResolver, long j, long j2) {
            android.content.ContentValues contentValues = new android.content.ContentValues();
            contentValues.put("person", java.lang.Long.valueOf(j));
            contentValues.put(android.provider.Contacts.GroupMembership.GROUP_ID, java.lang.Long.valueOf(j2));
            return contentResolver.insert(android.provider.Contacts.GroupMembership.CONTENT_URI, contentValues);
        }

        @java.lang.Deprecated
        public static android.net.Uri createPersonInMyContactsGroup(android.content.ContentResolver contentResolver, android.content.ContentValues contentValues) {
            android.net.Uri insert = contentResolver.insert(CONTENT_URI, contentValues);
            if (insert == null) {
                android.util.Log.e("Contacts", "Failed to create the contact");
                return null;
            }
            if (addToMyContactsGroup(contentResolver, android.content.ContentUris.parseId(insert)) == null) {
                contentResolver.delete(insert, null, null);
                return null;
            }
            return insert;
        }

        @java.lang.Deprecated
        public static android.database.Cursor queryGroups(android.content.ContentResolver contentResolver, long j) {
            return contentResolver.query(android.provider.Contacts.GroupMembership.CONTENT_URI, null, "person=?", new java.lang.String[]{java.lang.String.valueOf(j)}, "name ASC");
        }

        @java.lang.Deprecated
        public static void setPhotoData(android.content.ContentResolver contentResolver, android.net.Uri uri, byte[] bArr) {
            android.net.Uri withAppendedPath = android.net.Uri.withAppendedPath(uri, "photo");
            android.content.ContentValues contentValues = new android.content.ContentValues();
            contentValues.put("data", bArr);
            contentResolver.update(withAppendedPath, contentValues, null, null);
        }

        @java.lang.Deprecated
        public static java.io.InputStream openContactPhotoInputStream(android.content.ContentResolver contentResolver, android.net.Uri uri) {
            android.database.Cursor query = contentResolver.query(android.net.Uri.withAppendedPath(uri, "photo"), new java.lang.String[]{"data"}, null, null, null);
            if (query != null) {
                try {
                    if (query.moveToNext()) {
                        byte[] blob = query.getBlob(0);
                        if (blob == null) {
                            return null;
                        }
                        java.io.ByteArrayInputStream byteArrayInputStream = new java.io.ByteArrayInputStream(blob);
                        if (query != null) {
                            query.close();
                        }
                        return byteArrayInputStream;
                    }
                } finally {
                    if (query != null) {
                        query.close();
                    }
                }
            }
            if (query != null) {
                query.close();
            }
            return null;
        }

        @java.lang.Deprecated
        public static android.graphics.Bitmap loadContactPhoto(android.content.Context context, android.net.Uri uri, int i, android.graphics.BitmapFactory.Options options) {
            if (uri == null) {
                return loadPlaceholderPhoto(i, context, options);
            }
            java.io.InputStream openContactPhotoInputStream = openContactPhotoInputStream(context.getContentResolver(), uri);
            android.graphics.Bitmap decodeStream = openContactPhotoInputStream != null ? android.graphics.BitmapFactory.decodeStream(openContactPhotoInputStream, null, options) : null;
            if (decodeStream == null) {
                return loadPlaceholderPhoto(i, context, options);
            }
            return decodeStream;
        }

        private static android.graphics.Bitmap loadPlaceholderPhoto(int i, android.content.Context context, android.graphics.BitmapFactory.Options options) {
            if (i == 0) {
                return null;
            }
            return android.graphics.BitmapFactory.decodeResource(context.getResources(), i, options);
        }

        @java.lang.Deprecated
        public static final class Phones implements android.provider.BaseColumns, android.provider.Contacts.PhonesColumns, android.provider.Contacts.PeopleColumns {

            @java.lang.Deprecated
            public static final java.lang.String CONTENT_DIRECTORY = "phones";

            @java.lang.Deprecated
            public static final java.lang.String DEFAULT_SORT_ORDER = "number ASC";

            private Phones() {
            }
        }

        @java.lang.Deprecated
        public static final class ContactMethods implements android.provider.BaseColumns, android.provider.Contacts.ContactMethodsColumns, android.provider.Contacts.PeopleColumns {

            @java.lang.Deprecated
            public static final java.lang.String CONTENT_DIRECTORY = "contact_methods";

            @java.lang.Deprecated
            public static final java.lang.String DEFAULT_SORT_ORDER = "data ASC";

            private ContactMethods() {
            }
        }

        @java.lang.Deprecated
        public static class Extensions implements android.provider.BaseColumns, android.provider.Contacts.ExtensionsColumns {

            @java.lang.Deprecated
            public static final java.lang.String CONTENT_DIRECTORY = "extensions";

            @java.lang.Deprecated
            public static final java.lang.String DEFAULT_SORT_ORDER = "name ASC";

            @java.lang.Deprecated
            public static final java.lang.String PERSON_ID = "person";

            @java.lang.Deprecated
            private Extensions() {
            }
        }
    }

    @java.lang.Deprecated
    public static final class Groups implements android.provider.BaseColumns, android.provider.Contacts.GroupsColumns {

        @java.lang.Deprecated
        public static final java.lang.String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/contactsgroup";

        @java.lang.Deprecated
        public static final java.lang.String CONTENT_TYPE = "vnd.android.cursor.dir/contactsgroup";

        @java.lang.Deprecated
        public static final java.lang.String DEFAULT_SORT_ORDER = "name ASC";

        @java.lang.Deprecated
        public static final java.lang.String GROUP_ANDROID_STARRED = "Starred in Android";

        @java.lang.Deprecated
        public static final java.lang.String GROUP_MY_CONTACTS = "Contacts";

        @java.lang.Deprecated
        public static final android.net.Uri CONTENT_URI = android.net.Uri.parse("content://contacts/groups");

        @java.lang.Deprecated
        public static final android.net.Uri DELETED_CONTENT_URI = android.net.Uri.parse("content://contacts/deleted_groups");

        private Groups() {
        }
    }

    @java.lang.Deprecated
    public static final class Phones implements android.provider.BaseColumns, android.provider.Contacts.PhonesColumns, android.provider.Contacts.PeopleColumns {

        @java.lang.Deprecated
        public static final java.lang.String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/phone";

        @java.lang.Deprecated
        public static final java.lang.String CONTENT_TYPE = "vnd.android.cursor.dir/phone";

        @java.lang.Deprecated
        public static final java.lang.String DEFAULT_SORT_ORDER = "name ASC";

        @java.lang.Deprecated
        public static final java.lang.String PERSON_ID = "person";

        @java.lang.Deprecated
        public static final android.net.Uri CONTENT_URI = android.net.Uri.parse("content://contacts/phones");

        @java.lang.Deprecated
        public static final android.net.Uri CONTENT_FILTER_URL = android.net.Uri.parse("content://contacts/phones/filter");

        private Phones() {
        }

        @java.lang.Deprecated
        public static final java.lang.CharSequence getDisplayLabel(android.content.Context context, int i, java.lang.CharSequence charSequence, java.lang.CharSequence[] charSequenceArr) {
            if (i != 0) {
                if (charSequenceArr == null) {
                    charSequenceArr = context.getResources().getTextArray(17235971);
                }
                try {
                    return charSequenceArr[i - 1];
                } catch (java.lang.ArrayIndexOutOfBoundsException e) {
                    return charSequenceArr[0];
                }
            }
            if (android.text.TextUtils.isEmpty(charSequence)) {
                return "";
            }
            return charSequence;
        }

        @java.lang.Deprecated
        public static final java.lang.CharSequence getDisplayLabel(android.content.Context context, int i, java.lang.CharSequence charSequence) {
            return getDisplayLabel(context, i, charSequence, null);
        }
    }

    @java.lang.Deprecated
    public static final class GroupMembership implements android.provider.BaseColumns, android.provider.Contacts.GroupsColumns {

        @java.lang.Deprecated
        public static final java.lang.String CONTENT_DIRECTORY = "groupmembership";

        @java.lang.Deprecated
        public static final java.lang.String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/contactsgroupmembership";

        @java.lang.Deprecated
        public static final java.lang.String CONTENT_TYPE = "vnd.android.cursor.dir/contactsgroupmembership";

        @java.lang.Deprecated
        public static final java.lang.String DEFAULT_SORT_ORDER = "group_id ASC";

        @java.lang.Deprecated
        public static final java.lang.String GROUP_ID = "group_id";

        @java.lang.Deprecated
        public static final java.lang.String GROUP_SYNC_ACCOUNT = "group_sync_account";

        @java.lang.Deprecated
        public static final java.lang.String GROUP_SYNC_ACCOUNT_TYPE = "group_sync_account_type";

        @java.lang.Deprecated
        public static final java.lang.String GROUP_SYNC_ID = "group_sync_id";

        @java.lang.Deprecated
        public static final java.lang.String PERSON_ID = "person";

        @java.lang.Deprecated
        public static final android.net.Uri CONTENT_URI = android.net.Uri.parse("content://contacts/groupmembership");

        @java.lang.Deprecated
        public static final android.net.Uri RAW_CONTENT_URI = android.net.Uri.parse("content://contacts/groupmembershipraw");

        private GroupMembership() {
        }
    }

    @java.lang.Deprecated
    public static final class ContactMethods implements android.provider.BaseColumns, android.provider.Contacts.ContactMethodsColumns, android.provider.Contacts.PeopleColumns {

        @java.lang.Deprecated
        public static final java.lang.String CONTENT_EMAIL_ITEM_TYPE = "vnd.android.cursor.item/email";

        @java.lang.Deprecated
        public static final java.lang.String CONTENT_EMAIL_TYPE = "vnd.android.cursor.dir/email";

        @java.lang.Deprecated
        public static final java.lang.String CONTENT_IM_ITEM_TYPE = "vnd.android.cursor.item/jabber-im";

        @java.lang.Deprecated
        public static final java.lang.String CONTENT_POSTAL_ITEM_TYPE = "vnd.android.cursor.item/postal-address";

        @java.lang.Deprecated
        public static final java.lang.String CONTENT_POSTAL_TYPE = "vnd.android.cursor.dir/postal-address";

        @java.lang.Deprecated
        public static final java.lang.String CONTENT_TYPE = "vnd.android.cursor.dir/contact-methods";

        @java.lang.Deprecated
        public static final java.lang.String DEFAULT_SORT_ORDER = "name ASC";

        @java.lang.Deprecated
        public static final java.lang.String PERSON_ID = "person";

        @java.lang.Deprecated
        public static final java.lang.String POSTAL_LOCATION_LATITUDE = "data";

        @java.lang.Deprecated
        public static final java.lang.String POSTAL_LOCATION_LONGITUDE = "aux_data";

        @java.lang.Deprecated
        public static final int PROTOCOL_AIM = 0;

        @java.lang.Deprecated
        public static final int PROTOCOL_GOOGLE_TALK = 5;

        @java.lang.Deprecated
        public static final int PROTOCOL_ICQ = 6;

        @java.lang.Deprecated
        public static final int PROTOCOL_JABBER = 7;

        @java.lang.Deprecated
        public static final int PROTOCOL_MSN = 1;

        @java.lang.Deprecated
        public static final int PROTOCOL_QQ = 4;

        @java.lang.Deprecated
        public static final int PROTOCOL_SKYPE = 3;

        @java.lang.Deprecated
        public static final int PROTOCOL_YAHOO = 2;

        @java.lang.Deprecated
        public static final android.net.Uri CONTENT_URI = android.net.Uri.parse("content://contacts/contact_methods");

        @java.lang.Deprecated
        public static final android.net.Uri CONTENT_EMAIL_URI = android.net.Uri.parse("content://contacts/contact_methods/email");

        interface ProviderNames {
            public static final java.lang.String AIM = "AIM";
            public static final java.lang.String GTALK = "GTalk";
            public static final java.lang.String ICQ = "ICQ";
            public static final java.lang.String JABBER = "JABBER";
            public static final java.lang.String MSN = "MSN";
            public static final java.lang.String QQ = "QQ";
            public static final java.lang.String SKYPE = "SKYPE";
            public static final java.lang.String XMPP = "XMPP";
            public static final java.lang.String YAHOO = "Yahoo";
        }

        @java.lang.Deprecated
        public static java.lang.String encodePredefinedImProtocol(int i) {
            return "pre:" + i;
        }

        @java.lang.Deprecated
        public static java.lang.String encodeCustomImProtocol(java.lang.String str) {
            return "custom:" + str;
        }

        @java.lang.Deprecated
        public static java.lang.Object decodeImProtocol(java.lang.String str) {
            if (str == null) {
                return null;
            }
            if (str.startsWith("pre:")) {
                return java.lang.Integer.valueOf(java.lang.Integer.parseInt(str.substring(4)));
            }
            if (str.startsWith("custom:")) {
                return str.substring(7);
            }
            throw new java.lang.IllegalArgumentException("the value is not a valid encoded protocol, " + str);
        }

        @java.lang.Deprecated
        public static java.lang.String lookupProviderNameFromId(int i) {
            switch (i) {
                case 0:
                    return android.provider.Contacts.ContactMethods.ProviderNames.AIM;
                case 1:
                    return android.provider.Contacts.ContactMethods.ProviderNames.MSN;
                case 2:
                    return android.provider.Contacts.ContactMethods.ProviderNames.YAHOO;
                case 3:
                    return android.provider.Contacts.ContactMethods.ProviderNames.SKYPE;
                case 4:
                    return android.provider.Contacts.ContactMethods.ProviderNames.QQ;
                case 5:
                    return android.provider.Contacts.ContactMethods.ProviderNames.GTALK;
                case 6:
                    return android.provider.Contacts.ContactMethods.ProviderNames.ICQ;
                case 7:
                    return android.provider.Contacts.ContactMethods.ProviderNames.JABBER;
                default:
                    return null;
            }
        }

        private ContactMethods() {
        }

        /* JADX WARN: Removed duplicated region for block: B:18:0x004a A[ORIG_RETURN, RETURN] */
        @java.lang.Deprecated
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public static final java.lang.CharSequence getDisplayLabel(android.content.Context context, int i, int i2, java.lang.CharSequence charSequence) {
            switch (i) {
                case 1:
                    if (i2 == 0) {
                        return !android.text.TextUtils.isEmpty(charSequence) ? charSequence : "";
                    }
                    java.lang.CharSequence[] textArray = context.getResources().getTextArray(17235968);
                    try {
                        return textArray[i2 - 1];
                    } catch (java.lang.ArrayIndexOutOfBoundsException e) {
                        return textArray[0];
                    }
                case 2:
                    if (i2 != 0) {
                        java.lang.CharSequence[] textArray2 = context.getResources().getTextArray(17235972);
                        try {
                            return textArray2[i2 - 1];
                        } catch (java.lang.ArrayIndexOutOfBoundsException e2) {
                            return textArray2[0];
                        }
                    }
                    if (!android.text.TextUtils.isEmpty(charSequence)) {
                        return charSequence;
                    }
                    break;
                default:
                    return context.getString(17039375);
            }
        }

        @java.lang.Deprecated
        public void addPostalLocation(android.content.Context context, long j, double d, double d2) {
            android.content.ContentResolver contentResolver = context.getContentResolver();
            android.content.ContentValues contentValues = new android.content.ContentValues(2);
            contentValues.put("data", java.lang.Double.valueOf(d));
            contentValues.put("aux_data", java.lang.Double.valueOf(d2));
            long parseId = android.content.ContentUris.parseId(contentResolver.insert(CONTENT_URI, contentValues));
            contentValues.clear();
            contentValues.put("aux_data", java.lang.Long.valueOf(parseId));
            contentResolver.update(android.content.ContentUris.withAppendedId(CONTENT_URI, j), contentValues, null, null);
        }
    }

    @java.lang.Deprecated
    public static final class Presence implements android.provider.BaseColumns, android.provider.Contacts.PresenceColumns, android.provider.Contacts.PeopleColumns {

        @java.lang.Deprecated
        public static final android.net.Uri CONTENT_URI = android.net.Uri.parse("content://contacts/presence");

        @java.lang.Deprecated
        public static final java.lang.String PERSON_ID = "person";

        @java.lang.Deprecated
        public static final int getPresenceIconResourceId(int i) {
            switch (i) {
                case 1:
                    return 17301609;
                case 2:
                case 3:
                    return 17301607;
                case 4:
                    return 17301608;
                case 5:
                    return 17301611;
                default:
                    return 17301610;
            }
        }

        @java.lang.Deprecated
        public static final void setPresenceIcon(android.widget.ImageView imageView, int i) {
            imageView.setImageResource(getPresenceIconResourceId(i));
        }
    }

    @java.lang.Deprecated
    public static final class Organizations implements android.provider.BaseColumns, android.provider.Contacts.OrganizationColumns {

        @java.lang.Deprecated
        public static final java.lang.String CONTENT_DIRECTORY = "organizations";

        @java.lang.Deprecated
        public static final android.net.Uri CONTENT_URI = android.net.Uri.parse("content://contacts/organizations");

        @java.lang.Deprecated
        public static final java.lang.String DEFAULT_SORT_ORDER = "company, title, isprimary ASC";

        private Organizations() {
        }

        @java.lang.Deprecated
        public static final java.lang.CharSequence getDisplayLabel(android.content.Context context, int i, java.lang.CharSequence charSequence) {
            if (i != 0) {
                java.lang.CharSequence[] textArray = context.getResources().getTextArray(17235970);
                try {
                    return textArray[i - 1];
                } catch (java.lang.ArrayIndexOutOfBoundsException e) {
                    return textArray[0];
                }
            }
            if (android.text.TextUtils.isEmpty(charSequence)) {
                return "";
            }
            return charSequence;
        }
    }

    @java.lang.Deprecated
    public static final class Photos implements android.provider.BaseColumns, android.provider.Contacts.PhotosColumns {

        @java.lang.Deprecated
        public static final java.lang.String CONTENT_DIRECTORY = "photo";

        @java.lang.Deprecated
        public static final android.net.Uri CONTENT_URI = android.net.Uri.parse("content://contacts/photos");

        @java.lang.Deprecated
        public static final java.lang.String DEFAULT_SORT_ORDER = "person ASC";

        private Photos() {
        }
    }

    @java.lang.Deprecated
    public static final class Extensions implements android.provider.BaseColumns, android.provider.Contacts.ExtensionsColumns {

        @java.lang.Deprecated
        public static final java.lang.String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/contact_extensions";

        @java.lang.Deprecated
        public static final java.lang.String CONTENT_TYPE = "vnd.android.cursor.dir/contact_extensions";

        @java.lang.Deprecated
        public static final android.net.Uri CONTENT_URI = android.net.Uri.parse("content://contacts/extensions");

        @java.lang.Deprecated
        public static final java.lang.String DEFAULT_SORT_ORDER = "person, name ASC";

        @java.lang.Deprecated
        public static final java.lang.String PERSON_ID = "person";

        private Extensions() {
        }
    }

    @java.lang.Deprecated
    public static final class Intents {

        @java.lang.Deprecated
        public static final java.lang.String ATTACH_IMAGE = "com.android.contacts.action.ATTACH_IMAGE";

        @java.lang.Deprecated
        public static final java.lang.String EXTRA_CREATE_DESCRIPTION = "com.android.contacts.action.CREATE_DESCRIPTION";

        @java.lang.Deprecated
        public static final java.lang.String EXTRA_FORCE_CREATE = "com.android.contacts.action.FORCE_CREATE";

        @java.lang.Deprecated
        public static final java.lang.String EXTRA_TARGET_RECT = "target_rect";

        @java.lang.Deprecated
        public static final java.lang.String SEARCH_SUGGESTION_CLICKED = "android.provider.Contacts.SEARCH_SUGGESTION_CLICKED";

        @java.lang.Deprecated
        public static final java.lang.String SEARCH_SUGGESTION_CREATE_CONTACT_CLICKED = "android.provider.Contacts.SEARCH_SUGGESTION_CREATE_CONTACT_CLICKED";

        @java.lang.Deprecated
        public static final java.lang.String SEARCH_SUGGESTION_DIAL_NUMBER_CLICKED = "android.provider.Contacts.SEARCH_SUGGESTION_DIAL_NUMBER_CLICKED";

        @java.lang.Deprecated
        public static final java.lang.String SHOW_OR_CREATE_CONTACT = "com.android.contacts.action.SHOW_OR_CREATE_CONTACT";

        @java.lang.Deprecated
        public Intents() {
        }

        @java.lang.Deprecated
        public static final class UI {

            @java.lang.Deprecated
            public static final java.lang.String FILTER_CONTACTS_ACTION = "com.android.contacts.action.FILTER_CONTACTS";

            @java.lang.Deprecated
            public static final java.lang.String FILTER_TEXT_EXTRA_KEY = "com.android.contacts.extra.FILTER_TEXT";

            @java.lang.Deprecated
            public static final java.lang.String GROUP_NAME_EXTRA_KEY = "com.android.contacts.extra.GROUP";

            @java.lang.Deprecated
            public static final java.lang.String LIST_ALL_CONTACTS_ACTION = "com.android.contacts.action.LIST_ALL_CONTACTS";

            @java.lang.Deprecated
            public static final java.lang.String LIST_CONTACTS_WITH_PHONES_ACTION = "com.android.contacts.action.LIST_CONTACTS_WITH_PHONES";

            @java.lang.Deprecated
            public static final java.lang.String LIST_DEFAULT = "com.android.contacts.action.LIST_DEFAULT";

            @java.lang.Deprecated
            public static final java.lang.String LIST_FREQUENT_ACTION = "com.android.contacts.action.LIST_FREQUENT";

            @java.lang.Deprecated
            public static final java.lang.String LIST_GROUP_ACTION = "com.android.contacts.action.LIST_GROUP";

            @java.lang.Deprecated
            public static final java.lang.String LIST_STARRED_ACTION = "com.android.contacts.action.LIST_STARRED";

            @java.lang.Deprecated
            public static final java.lang.String LIST_STREQUENT_ACTION = "com.android.contacts.action.LIST_STREQUENT";

            @java.lang.Deprecated
            public static final java.lang.String TITLE_EXTRA_KEY = "com.android.contacts.extra.TITLE_EXTRA";

            @java.lang.Deprecated
            public UI() {
            }
        }

        @java.lang.Deprecated
        public static final class Insert {

            @java.lang.Deprecated
            public static final java.lang.String ACTION = "android.intent.action.INSERT";

            @java.lang.Deprecated
            public static final java.lang.String COMPANY = "company";

            @java.lang.Deprecated
            public static final java.lang.String EMAIL = "email";

            @java.lang.Deprecated
            public static final java.lang.String EMAIL_ISPRIMARY = "email_isprimary";

            @java.lang.Deprecated
            public static final java.lang.String EMAIL_TYPE = "email_type";

            @java.lang.Deprecated
            public static final java.lang.String FULL_MODE = "full_mode";

            @java.lang.Deprecated
            public static final java.lang.String IM_HANDLE = "im_handle";

            @java.lang.Deprecated
            public static final java.lang.String IM_ISPRIMARY = "im_isprimary";

            @java.lang.Deprecated
            public static final java.lang.String IM_PROTOCOL = "im_protocol";

            @java.lang.Deprecated
            public static final java.lang.String JOB_TITLE = "job_title";

            @java.lang.Deprecated
            public static final java.lang.String NAME = "name";

            @java.lang.Deprecated
            public static final java.lang.String NOTES = "notes";

            @java.lang.Deprecated
            public static final java.lang.String PHONE = "phone";

            @java.lang.Deprecated
            public static final java.lang.String PHONETIC_NAME = "phonetic_name";

            @java.lang.Deprecated
            public static final java.lang.String PHONE_ISPRIMARY = "phone_isprimary";

            @java.lang.Deprecated
            public static final java.lang.String PHONE_TYPE = "phone_type";

            @java.lang.Deprecated
            public static final java.lang.String POSTAL = "postal";

            @java.lang.Deprecated
            public static final java.lang.String POSTAL_ISPRIMARY = "postal_isprimary";

            @java.lang.Deprecated
            public static final java.lang.String POSTAL_TYPE = "postal_type";

            @java.lang.Deprecated
            public static final java.lang.String SECONDARY_EMAIL = "secondary_email";

            @java.lang.Deprecated
            public static final java.lang.String SECONDARY_EMAIL_TYPE = "secondary_email_type";

            @java.lang.Deprecated
            public static final java.lang.String SECONDARY_PHONE = "secondary_phone";

            @java.lang.Deprecated
            public static final java.lang.String SECONDARY_PHONE_TYPE = "secondary_phone_type";

            @java.lang.Deprecated
            public static final java.lang.String TERTIARY_EMAIL = "tertiary_email";

            @java.lang.Deprecated
            public static final java.lang.String TERTIARY_EMAIL_TYPE = "tertiary_email_type";

            @java.lang.Deprecated
            public static final java.lang.String TERTIARY_PHONE = "tertiary_phone";

            @java.lang.Deprecated
            public static final java.lang.String TERTIARY_PHONE_TYPE = "tertiary_phone_type";

            @java.lang.Deprecated
            public Insert() {
            }
        }
    }
}
