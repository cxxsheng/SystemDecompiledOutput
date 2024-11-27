package android.provider;

/* loaded from: classes3.dex */
public class ContactsInternal {
    private static final int CONTACTS_URI_LOOKUP = 1001;
    private static final int CONTACTS_URI_LOOKUP_ID = 1000;
    private static final android.content.UriMatcher sContactsUriMatcher = new android.content.UriMatcher(-1);

    private ContactsInternal() {
    }

    static {
        android.content.UriMatcher uriMatcher = sContactsUriMatcher;
        uriMatcher.addURI(android.provider.ContactsContract.AUTHORITY, "contacts/lookup/*", 1001);
        uriMatcher.addURI(android.provider.ContactsContract.AUTHORITY, "contacts/lookup/*/#", 1000);
    }

    public static void startQuickContactWithErrorToast(android.content.Context context, android.content.Intent intent) {
        switch (sContactsUriMatcher.match(intent.getData())) {
            case 1000:
            case 1001:
                if (maybeStartManagedQuickContact(context, intent)) {
                    return;
                }
                break;
        }
        startQuickContactWithErrorToastForUser(context, intent, context.getUser());
    }

    public static void startQuickContactWithErrorToastForUser(android.content.Context context, android.content.Intent intent, android.os.UserHandle userHandle) {
        try {
            context.startActivityAsUser(intent, userHandle);
        } catch (android.content.ActivityNotFoundException e) {
            android.widget.Toast.makeText(context, com.android.internal.R.string.quick_contacts_not_available, 0).show();
        }
    }

    private static boolean maybeStartManagedQuickContact(android.content.Context context, android.content.Intent intent) {
        long parseId;
        long parseLong;
        android.net.Uri data = intent.getData();
        java.util.List<java.lang.String> pathSegments = data.getPathSegments();
        boolean z = pathSegments.size() < 4;
        if (z) {
            parseId = android.provider.ContactsContract.Contacts.ENTERPRISE_CONTACT_ID_BASE;
        } else {
            parseId = android.content.ContentUris.parseId(data);
        }
        java.lang.String str = pathSegments.get(2);
        java.lang.String queryParameter = data.getQueryParameter("directory");
        if (queryParameter == null) {
            parseLong = 1000000000;
        } else {
            parseLong = java.lang.Long.parseLong(queryParameter);
        }
        if (android.text.TextUtils.isEmpty(str) || !str.startsWith(android.provider.ContactsContract.Contacts.ENTERPRISE_CONTACT_LOOKUP_PREFIX)) {
            return false;
        }
        if (!android.provider.ContactsContract.Contacts.isEnterpriseContactId(parseId)) {
            throw new java.lang.IllegalArgumentException("Invalid enterprise contact id: " + parseId);
        }
        if (!android.provider.ContactsContract.Directory.isEnterpriseDirectoryId(parseLong)) {
            throw new java.lang.IllegalArgumentException("Invalid enterprise directory id: " + parseLong);
        }
        ((android.app.admin.DevicePolicyManager) context.getSystemService(android.app.admin.DevicePolicyManager.class)).startManagedQuickContact(str.substring(android.provider.ContactsContract.Contacts.ENTERPRISE_CONTACT_LOOKUP_PREFIX.length()), parseId - android.provider.ContactsContract.Contacts.ENTERPRISE_CONTACT_ID_BASE, z, parseLong - 1000000000, intent);
        return true;
    }
}
