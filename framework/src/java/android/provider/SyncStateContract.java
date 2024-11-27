package android.provider;

/* loaded from: classes3.dex */
public class SyncStateContract {

    public interface Columns extends android.provider.BaseColumns {
        public static final java.lang.String ACCOUNT_NAME = "account_name";
        public static final java.lang.String ACCOUNT_TYPE = "account_type";
        public static final java.lang.String DATA = "data";
    }

    public static class Constants implements android.provider.SyncStateContract.Columns {
        public static final java.lang.String CONTENT_DIRECTORY = "syncstate";
    }

    public static final class Helpers {
        private static final java.lang.String[] DATA_PROJECTION = {"data", "_id"};
        private static final java.lang.String SELECT_BY_ACCOUNT = "account_name=? AND account_type=?";

        public static byte[] get(android.content.ContentProviderClient contentProviderClient, android.net.Uri uri, android.accounts.Account account) throws android.os.RemoteException {
            android.database.Cursor query = contentProviderClient.query(uri, DATA_PROJECTION, SELECT_BY_ACCOUNT, new java.lang.String[]{account.name, account.type}, null);
            if (query == null) {
                throw new android.os.RemoteException();
            }
            try {
                if (query.moveToNext()) {
                    return query.getBlob(query.getColumnIndexOrThrow("data"));
                }
                query.close();
                return null;
            } finally {
                query.close();
            }
        }

        public static void set(android.content.ContentProviderClient contentProviderClient, android.net.Uri uri, android.accounts.Account account, byte[] bArr) throws android.os.RemoteException {
            android.content.ContentValues contentValues = new android.content.ContentValues();
            contentValues.put("data", bArr);
            contentValues.put("account_name", account.name);
            contentValues.put("account_type", account.type);
            contentProviderClient.insert(uri, contentValues);
        }

        public static android.net.Uri insert(android.content.ContentProviderClient contentProviderClient, android.net.Uri uri, android.accounts.Account account, byte[] bArr) throws android.os.RemoteException {
            android.content.ContentValues contentValues = new android.content.ContentValues();
            contentValues.put("data", bArr);
            contentValues.put("account_name", account.name);
            contentValues.put("account_type", account.type);
            return contentProviderClient.insert(uri, contentValues);
        }

        public static void update(android.content.ContentProviderClient contentProviderClient, android.net.Uri uri, byte[] bArr) throws android.os.RemoteException {
            android.content.ContentValues contentValues = new android.content.ContentValues();
            contentValues.put("data", bArr);
            contentProviderClient.update(uri, contentValues, null, null);
        }

        public static android.util.Pair<android.net.Uri, byte[]> getWithUri(android.content.ContentProviderClient contentProviderClient, android.net.Uri uri, android.accounts.Account account) throws android.os.RemoteException {
            android.database.Cursor query = contentProviderClient.query(uri, DATA_PROJECTION, SELECT_BY_ACCOUNT, new java.lang.String[]{account.name, account.type}, null);
            if (query == null) {
                throw new android.os.RemoteException();
            }
            try {
                if (query.moveToNext()) {
                    long j = query.getLong(1);
                    return android.util.Pair.create(android.content.ContentUris.withAppendedId(uri, j), query.getBlob(query.getColumnIndexOrThrow("data")));
                }
                query.close();
                return null;
            } finally {
                query.close();
            }
        }

        public static android.content.ContentProviderOperation newSetOperation(android.net.Uri uri, android.accounts.Account account, byte[] bArr) {
            android.content.ContentValues contentValues = new android.content.ContentValues();
            contentValues.put("data", bArr);
            return android.content.ContentProviderOperation.newInsert(uri).withValue("account_name", account.name).withValue("account_type", account.type).withValues(contentValues).build();
        }

        public static android.content.ContentProviderOperation newUpdateOperation(android.net.Uri uri, byte[] bArr) {
            android.content.ContentValues contentValues = new android.content.ContentValues();
            contentValues.put("data", bArr);
            return android.content.ContentProviderOperation.newUpdate(uri).withValues(contentValues).build();
        }
    }
}
