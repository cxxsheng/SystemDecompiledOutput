package android.database;

/* loaded from: classes.dex */
public class DatabaseUtils {
    private static final boolean DEBUG = false;
    private static final int PREFIX_GROUP_NUM = 2;
    public static final int STATEMENT_ABORT = 6;
    public static final int STATEMENT_ATTACH = 3;
    public static final int STATEMENT_BEGIN = 4;
    public static final int STATEMENT_COMMENT = 102;
    public static final int STATEMENT_COMMIT = 5;
    public static final int STATEMENT_CREATE = 101;
    public static final int STATEMENT_DDL = 8;
    public static final int STATEMENT_OTHER = 99;
    public static final int STATEMENT_PRAGMA = 7;
    public static final int STATEMENT_SELECT = 1;
    public static final int STATEMENT_UNPREPARED = 9;
    public static final int STATEMENT_UPDATE = 2;
    public static final int STATEMENT_WITH = 100;
    private static final java.lang.String TAG = "DatabaseUtils";
    private static final char[] DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', android.text.format.DateFormat.AM_PM, 'b', 'c', android.text.format.DateFormat.DATE, 'e', 'f'};
    private static java.text.Collator mColl = null;
    private static final java.lang.String PREFIX_REGEX = "(\\s+|--.*?\n|/\\*[\\w\\W]*?\\*/)*(\\w\\w\\w)";
    private static final java.util.regex.Pattern sPrefixPattern = java.util.regex.Pattern.compile(PREFIX_REGEX);

    public static final void writeExceptionToParcel(android.os.Parcel parcel, java.lang.Exception exc) {
        boolean z = false;
        int i = 1;
        if (!(exc instanceof java.io.FileNotFoundException)) {
            if (exc instanceof java.lang.IllegalArgumentException) {
                z = true;
                i = 2;
            } else if (exc instanceof java.lang.UnsupportedOperationException) {
                z = true;
                i = 3;
            } else if (exc instanceof android.database.sqlite.SQLiteAbortException) {
                z = true;
                i = 4;
            } else if (exc instanceof android.database.sqlite.SQLiteConstraintException) {
                z = true;
                i = 5;
            } else if (exc instanceof android.database.sqlite.SQLiteDatabaseCorruptException) {
                z = true;
                i = 6;
            } else if (exc instanceof android.database.sqlite.SQLiteFullException) {
                z = true;
                i = 7;
            } else if (exc instanceof android.database.sqlite.SQLiteDiskIOException) {
                z = true;
                i = 8;
            } else if (exc instanceof android.database.sqlite.SQLiteException) {
                z = true;
                i = 9;
            } else if (exc instanceof android.content.OperationApplicationException) {
                z = true;
                i = 10;
            } else if (exc instanceof android.os.OperationCanceledException) {
                i = 11;
            } else {
                parcel.writeException(exc);
                android.util.Log.e(TAG, "Writing exception to parcel", exc);
                return;
            }
        }
        parcel.writeInt(i);
        parcel.writeString(exc.getMessage());
        if (z) {
            android.util.Log.e(TAG, "Writing exception to parcel", exc);
        }
    }

    public static final void readExceptionFromParcel(android.os.Parcel parcel) {
        int readExceptionCode = parcel.readExceptionCode();
        if (readExceptionCode == 0) {
            return;
        }
        readExceptionFromParcel(parcel, parcel.readString(), readExceptionCode);
    }

    public static void readExceptionWithFileNotFoundExceptionFromParcel(android.os.Parcel parcel) throws java.io.FileNotFoundException {
        int readExceptionCode = parcel.readExceptionCode();
        if (readExceptionCode == 0) {
            return;
        }
        java.lang.String readString = parcel.readString();
        if (readExceptionCode == 1) {
            throw new java.io.FileNotFoundException(readString);
        }
        readExceptionFromParcel(parcel, readString, readExceptionCode);
    }

    public static void readExceptionWithOperationApplicationExceptionFromParcel(android.os.Parcel parcel) throws android.content.OperationApplicationException {
        int readExceptionCode = parcel.readExceptionCode();
        if (readExceptionCode == 0) {
            return;
        }
        java.lang.String readString = parcel.readString();
        if (readExceptionCode == 10) {
            throw new android.content.OperationApplicationException(readString);
        }
        readExceptionFromParcel(parcel, readString, readExceptionCode);
    }

    private static final void readExceptionFromParcel(android.os.Parcel parcel, java.lang.String str, int i) {
        switch (i) {
            case 2:
                throw new java.lang.IllegalArgumentException(str);
            case 3:
                throw new java.lang.UnsupportedOperationException(str);
            case 4:
                throw new android.database.sqlite.SQLiteAbortException(str);
            case 5:
                throw new android.database.sqlite.SQLiteConstraintException(str);
            case 6:
                throw new android.database.sqlite.SQLiteDatabaseCorruptException(str);
            case 7:
                throw new android.database.sqlite.SQLiteFullException(str);
            case 8:
                throw new android.database.sqlite.SQLiteDiskIOException(str);
            case 9:
                throw new android.database.sqlite.SQLiteException(str);
            case 10:
            default:
                parcel.readException(i, str);
                return;
            case 11:
                throw new android.os.OperationCanceledException(str);
        }
    }

    public static long executeInsert(android.database.sqlite.SQLiteDatabase sQLiteDatabase, java.lang.String str, java.lang.Object[] objArr) throws android.database.SQLException {
        android.database.sqlite.SQLiteStatement compileStatement = sQLiteDatabase.compileStatement(str);
        try {
            bindArgs(compileStatement, objArr);
            long executeInsert = compileStatement.executeInsert();
            if (compileStatement != null) {
                compileStatement.close();
            }
            return executeInsert;
        } catch (java.lang.Throwable th) {
            if (compileStatement != null) {
                try {
                    compileStatement.close();
                } catch (java.lang.Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    public static int executeUpdateDelete(android.database.sqlite.SQLiteDatabase sQLiteDatabase, java.lang.String str, java.lang.Object[] objArr) throws android.database.SQLException {
        android.database.sqlite.SQLiteStatement compileStatement = sQLiteDatabase.compileStatement(str);
        try {
            bindArgs(compileStatement, objArr);
            int executeUpdateDelete = compileStatement.executeUpdateDelete();
            if (compileStatement != null) {
                compileStatement.close();
            }
            return executeUpdateDelete;
        } catch (java.lang.Throwable th) {
            if (compileStatement != null) {
                try {
                    compileStatement.close();
                } catch (java.lang.Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    private static void bindArgs(android.database.sqlite.SQLiteStatement sQLiteStatement, java.lang.Object[] objArr) {
        if (objArr == null) {
            return;
        }
        for (int i = 0; i < objArr.length; i++) {
            java.lang.Object obj = objArr[i];
            switch (getTypeOfObject(obj)) {
                case 0:
                    sQLiteStatement.bindNull(i + 1);
                    break;
                case 1:
                    sQLiteStatement.bindLong(i + 1, ((java.lang.Number) obj).longValue());
                    break;
                case 2:
                    sQLiteStatement.bindDouble(i + 1, ((java.lang.Number) obj).doubleValue());
                    break;
                case 3:
                default:
                    if (obj instanceof java.lang.Boolean) {
                        sQLiteStatement.bindLong(i + 1, ((java.lang.Boolean) obj).booleanValue() ? 1L : 0L);
                        break;
                    } else {
                        sQLiteStatement.bindString(i + 1, obj.toString());
                        break;
                    }
                case 4:
                    sQLiteStatement.bindBlob(i + 1, (byte[]) obj);
                    break;
            }
        }
    }

    public static void bindObjectToProgram(android.database.sqlite.SQLiteProgram sQLiteProgram, int i, java.lang.Object obj) {
        if (obj == null) {
            sQLiteProgram.bindNull(i);
            return;
        }
        if ((obj instanceof java.lang.Double) || (obj instanceof java.lang.Float)) {
            sQLiteProgram.bindDouble(i, ((java.lang.Number) obj).doubleValue());
            return;
        }
        if (obj instanceof java.lang.Number) {
            sQLiteProgram.bindLong(i, ((java.lang.Number) obj).longValue());
            return;
        }
        if (obj instanceof java.lang.Boolean) {
            if (((java.lang.Boolean) obj).booleanValue()) {
                sQLiteProgram.bindLong(i, 1L);
                return;
            } else {
                sQLiteProgram.bindLong(i, 0L);
                return;
            }
        }
        if (obj instanceof byte[]) {
            sQLiteProgram.bindBlob(i, (byte[]) obj);
        } else {
            sQLiteProgram.bindString(i, obj.toString());
        }
    }

    public static java.lang.String bindSelection(java.lang.String str, java.lang.Object... objArr) {
        char c;
        if (str == null) {
            return null;
        }
        if (com.android.internal.util.ArrayUtils.isEmpty(objArr) || str.indexOf(63) == -1) {
            return str;
        }
        int length = str.length();
        java.lang.StringBuilder sb = new java.lang.StringBuilder(length);
        int i = 0;
        int i2 = 0;
        char c2 = ' ';
        while (i < length) {
            int i3 = i + 1;
            char charAt = str.charAt(i);
            if (charAt == '?') {
                i = i3;
                while (true) {
                    if (i >= length) {
                        c = ' ';
                    } else {
                        c = str.charAt(i);
                        if (c >= '0' && c <= '9') {
                            i++;
                        }
                    }
                }
                if (i3 != i) {
                    i2 = java.lang.Integer.parseInt(str.substring(i3, i)) - 1;
                }
                int i4 = i2 + 1;
                java.lang.Object obj = objArr[i2];
                if (c2 != ' ' && c2 != '=') {
                    sb.append(' ');
                }
                switch (getTypeOfObject(obj)) {
                    case 0:
                        sb.append("NULL");
                        break;
                    case 1:
                        sb.append(((java.lang.Number) obj).longValue());
                        break;
                    case 2:
                        sb.append(((java.lang.Number) obj).doubleValue());
                        break;
                    case 3:
                    default:
                        if (obj instanceof java.lang.Boolean) {
                            sb.append(((java.lang.Boolean) obj).booleanValue() ? 1 : 0);
                            break;
                        } else {
                            sb.append(android.text.format.DateFormat.QUOTE);
                            sb.append(obj.toString());
                            sb.append(android.text.format.DateFormat.QUOTE);
                            break;
                        }
                    case 4:
                        throw new java.lang.IllegalArgumentException("Blobs not supported");
                }
                if (c != ' ') {
                    sb.append(' ');
                }
                i2 = i4;
            } else {
                sb.append(charAt);
                c2 = charAt;
                i = i3;
            }
        }
        return sb.toString();
    }

    public static java.lang.Object[] deepCopyOf(java.lang.Object[] objArr) {
        if (objArr == null) {
            return null;
        }
        java.lang.Object[] objArr2 = new java.lang.Object[objArr.length];
        for (int i = 0; i < objArr.length; i++) {
            java.lang.Object obj = objArr[i];
            if (obj == null || (obj instanceof java.lang.Number) || (obj instanceof java.lang.String)) {
                objArr2[i] = obj;
            } else if (obj instanceof byte[]) {
                byte[] bArr = (byte[]) obj;
                objArr2[i] = java.util.Arrays.copyOf(bArr, bArr.length);
            } else {
                objArr2[i] = java.lang.String.valueOf(obj);
            }
        }
        return objArr2;
    }

    public static int getTypeOfObject(java.lang.Object obj) {
        if (obj == null) {
            return 0;
        }
        if (obj instanceof byte[]) {
            return 4;
        }
        if ((obj instanceof java.lang.Float) || (obj instanceof java.lang.Double)) {
            return 2;
        }
        if ((obj instanceof java.lang.Long) || (obj instanceof java.lang.Integer) || (obj instanceof java.lang.Short) || (obj instanceof java.lang.Byte)) {
            return 1;
        }
        return 3;
    }

    public static void cursorFillWindow(android.database.Cursor cursor, int i, android.database.CursorWindow cursorWindow) {
        boolean putNull;
        if (i < 0 || i >= cursor.getCount()) {
            return;
        }
        int position = cursor.getPosition();
        int columnCount = cursor.getColumnCount();
        cursorWindow.clear();
        cursorWindow.setStartPosition(i);
        cursorWindow.setNumColumns(columnCount);
        if (cursor.moveToPosition(i)) {
            while (true) {
                if (cursorWindow.allocRow()) {
                    int i2 = 0;
                    while (true) {
                        if (i2 < columnCount) {
                            switch (cursor.getType(i2)) {
                                case 0:
                                    putNull = cursorWindow.putNull(i, i2);
                                    break;
                                case 1:
                                    putNull = cursorWindow.putLong(cursor.getLong(i2), i, i2);
                                    break;
                                case 2:
                                    putNull = cursorWindow.putDouble(cursor.getDouble(i2), i, i2);
                                    break;
                                case 3:
                                default:
                                    java.lang.String string = cursor.getString(i2);
                                    if (string == null) {
                                        putNull = cursorWindow.putNull(i, i2);
                                        break;
                                    } else {
                                        putNull = cursorWindow.putString(string, i, i2);
                                        break;
                                    }
                                case 4:
                                    byte[] blob = cursor.getBlob(i2);
                                    if (blob == null) {
                                        putNull = cursorWindow.putNull(i, i2);
                                        break;
                                    } else {
                                        putNull = cursorWindow.putBlob(blob, i, i2);
                                        break;
                                    }
                            }
                            if (putNull) {
                                i2++;
                            } else {
                                cursorWindow.freeLastRow();
                            }
                        } else {
                            i++;
                            if (!cursor.moveToNext()) {
                            }
                        }
                    }
                }
            }
        }
        cursor.moveToPosition(position);
    }

    public static void appendEscapedSQLString(java.lang.StringBuilder sb, java.lang.String str) {
        sb.append(android.text.format.DateFormat.QUOTE);
        int length = str.length();
        for (int i = 0; i < length; i++) {
            char charAt = str.charAt(i);
            if (java.lang.Character.isHighSurrogate(charAt)) {
                if (i != length - 1) {
                    int i2 = i + 1;
                    if (java.lang.Character.isLowSurrogate(str.charAt(i2))) {
                        sb.append(charAt);
                        sb.append(str.charAt(i2));
                    }
                }
            } else if (!java.lang.Character.isLowSurrogate(charAt)) {
                if (charAt == '\'') {
                    sb.append(android.text.format.DateFormat.QUOTE);
                }
                sb.append(charAt);
            }
        }
        sb.append(android.text.format.DateFormat.QUOTE);
    }

    public static java.lang.String sqlEscapeString(java.lang.String str) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        appendEscapedSQLString(sb, str);
        return sb.toString();
    }

    public static final void appendValueToSql(java.lang.StringBuilder sb, java.lang.Object obj) {
        if (obj == null) {
            sb.append("NULL");
            return;
        }
        if (obj instanceof java.lang.Boolean) {
            if (((java.lang.Boolean) obj).booleanValue()) {
                sb.append('1');
                return;
            } else {
                sb.append('0');
                return;
            }
        }
        appendEscapedSQLString(sb, obj.toString());
    }

    public static java.lang.String concatenateWhere(java.lang.String str, java.lang.String str2) {
        if (android.text.TextUtils.isEmpty(str)) {
            return str2;
        }
        if (android.text.TextUtils.isEmpty(str2)) {
            return str;
        }
        return android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_START + str + ") AND (" + str2 + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
    }

    public static java.lang.String getCollationKey(java.lang.String str) {
        byte[] collationKeyInBytes = getCollationKeyInBytes(str);
        try {
            return new java.lang.String(collationKeyInBytes, 0, getKeyLen(collationKeyInBytes), "ISO8859_1");
        } catch (java.lang.Exception e) {
            return "";
        }
    }

    public static java.lang.String getHexCollationKey(java.lang.String str) {
        byte[] collationKeyInBytes = getCollationKeyInBytes(str);
        return new java.lang.String(encodeHex(collationKeyInBytes), 0, getKeyLen(collationKeyInBytes) * 2);
    }

    private static char[] encodeHex(byte[] bArr) {
        int length = bArr.length;
        char[] cArr = new char[length << 1];
        int i = 0;
        for (int i2 = 0; i2 < length; i2++) {
            int i3 = i + 1;
            cArr[i] = DIGITS[(bArr[i2] & 240) >>> 4];
            i = i3 + 1;
            cArr[i3] = DIGITS[bArr[i2] & 15];
        }
        return cArr;
    }

    private static int getKeyLen(byte[] bArr) {
        if (bArr[bArr.length - 1] != 0) {
            return bArr.length;
        }
        return bArr.length - 1;
    }

    private static byte[] getCollationKeyInBytes(java.lang.String str) {
        if (mColl == null) {
            mColl = java.text.Collator.getInstance();
            mColl.setStrength(0);
        }
        return mColl.getCollationKey(str).toByteArray();
    }

    public static void dumpCursor(android.database.Cursor cursor) {
        dumpCursor(cursor, java.lang.System.out);
    }

    public static void dumpCursor(android.database.Cursor cursor, java.io.PrintStream printStream) {
        printStream.println(">>>>> Dumping cursor " + cursor);
        if (cursor != null) {
            int position = cursor.getPosition();
            cursor.moveToPosition(-1);
            while (cursor.moveToNext()) {
                dumpCurrentRow(cursor, printStream);
            }
            cursor.moveToPosition(position);
        }
        printStream.println("<<<<<");
    }

    public static void dumpCursor(android.database.Cursor cursor, java.lang.StringBuilder sb) {
        sb.append(">>>>> Dumping cursor ").append(cursor).append('\n');
        if (cursor != null) {
            int position = cursor.getPosition();
            cursor.moveToPosition(-1);
            while (cursor.moveToNext()) {
                dumpCurrentRow(cursor, sb);
            }
            cursor.moveToPosition(position);
        }
        sb.append("<<<<<\n");
    }

    public static java.lang.String dumpCursorToString(android.database.Cursor cursor) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        dumpCursor(cursor, sb);
        return sb.toString();
    }

    public static void dumpCurrentRow(android.database.Cursor cursor) {
        dumpCurrentRow(cursor, java.lang.System.out);
    }

    public static void dumpCurrentRow(android.database.Cursor cursor, java.io.PrintStream printStream) {
        java.lang.String str;
        java.lang.String[] columnNames = cursor.getColumnNames();
        printStream.println("" + cursor.getPosition() + " {");
        int length = columnNames.length;
        for (int i = 0; i < length; i++) {
            try {
                str = cursor.getString(i);
            } catch (android.database.sqlite.SQLiteException e) {
                str = "<unprintable>";
            }
            printStream.println("   " + columnNames[i] + '=' + str);
        }
        printStream.println("}");
    }

    public static void dumpCurrentRow(android.database.Cursor cursor, java.lang.StringBuilder sb) {
        java.lang.String str;
        java.lang.String[] columnNames = cursor.getColumnNames();
        sb.append(cursor.getPosition()).append(" {\n");
        int length = columnNames.length;
        for (int i = 0; i < length; i++) {
            try {
                str = cursor.getString(i);
            } catch (android.database.sqlite.SQLiteException e) {
                str = "<unprintable>";
            }
            sb.append("   ").append(columnNames[i]).append('=').append(str).append('\n');
        }
        sb.append("}\n");
    }

    public static java.lang.String dumpCurrentRowToString(android.database.Cursor cursor) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        dumpCurrentRow(cursor, sb);
        return sb.toString();
    }

    public static void cursorStringToContentValues(android.database.Cursor cursor, java.lang.String str, android.content.ContentValues contentValues) {
        cursorStringToContentValues(cursor, str, contentValues, str);
    }

    public static void cursorStringToInsertHelper(android.database.Cursor cursor, java.lang.String str, android.database.DatabaseUtils.InsertHelper insertHelper, int i) {
        insertHelper.bind(i, cursor.getString(cursor.getColumnIndexOrThrow(str)));
    }

    public static void cursorStringToContentValues(android.database.Cursor cursor, java.lang.String str, android.content.ContentValues contentValues, java.lang.String str2) {
        contentValues.put(str2, cursor.getString(cursor.getColumnIndexOrThrow(str)));
    }

    public static void cursorIntToContentValues(android.database.Cursor cursor, java.lang.String str, android.content.ContentValues contentValues) {
        cursorIntToContentValues(cursor, str, contentValues, str);
    }

    public static void cursorIntToContentValues(android.database.Cursor cursor, java.lang.String str, android.content.ContentValues contentValues, java.lang.String str2) {
        int columnIndex = cursor.getColumnIndex(str);
        if (!cursor.isNull(columnIndex)) {
            contentValues.put(str2, java.lang.Integer.valueOf(cursor.getInt(columnIndex)));
        } else {
            contentValues.put(str2, (java.lang.Integer) null);
        }
    }

    public static void cursorLongToContentValues(android.database.Cursor cursor, java.lang.String str, android.content.ContentValues contentValues) {
        cursorLongToContentValues(cursor, str, contentValues, str);
    }

    public static void cursorLongToContentValues(android.database.Cursor cursor, java.lang.String str, android.content.ContentValues contentValues, java.lang.String str2) {
        int columnIndex = cursor.getColumnIndex(str);
        if (!cursor.isNull(columnIndex)) {
            contentValues.put(str2, java.lang.Long.valueOf(cursor.getLong(columnIndex)));
        } else {
            contentValues.put(str2, (java.lang.Long) null);
        }
    }

    public static void cursorDoubleToCursorValues(android.database.Cursor cursor, java.lang.String str, android.content.ContentValues contentValues) {
        cursorDoubleToContentValues(cursor, str, contentValues, str);
    }

    public static void cursorDoubleToContentValues(android.database.Cursor cursor, java.lang.String str, android.content.ContentValues contentValues, java.lang.String str2) {
        int columnIndex = cursor.getColumnIndex(str);
        if (!cursor.isNull(columnIndex)) {
            contentValues.put(str2, java.lang.Double.valueOf(cursor.getDouble(columnIndex)));
        } else {
            contentValues.put(str2, (java.lang.Double) null);
        }
    }

    public static void cursorRowToContentValues(android.database.Cursor cursor, android.content.ContentValues contentValues) {
        java.lang.String[] columnNames = cursor.getColumnNames();
        int length = columnNames.length;
        for (int i = 0; i < length; i++) {
            if (cursor.getType(i) == 4) {
                contentValues.put(columnNames[i], cursor.getBlob(i));
            } else {
                contentValues.put(columnNames[i], cursor.getString(i));
            }
        }
    }

    public static int cursorPickFillWindowStartPosition(int i, int i2) {
        return java.lang.Math.max(i - (i2 / 3), 0);
    }

    public static long queryNumEntries(android.database.sqlite.SQLiteDatabase sQLiteDatabase, java.lang.String str) {
        return queryNumEntries(sQLiteDatabase, str, null, null);
    }

    public static long queryNumEntries(android.database.sqlite.SQLiteDatabase sQLiteDatabase, java.lang.String str, java.lang.String str2) {
        return queryNumEntries(sQLiteDatabase, str, str2, null);
    }

    public static long queryNumEntries(android.database.sqlite.SQLiteDatabase sQLiteDatabase, java.lang.String str, java.lang.String str2, java.lang.String[] strArr) {
        return longForQuery(sQLiteDatabase, "select count(*) from " + str + (!android.text.TextUtils.isEmpty(str2) ? " where " + str2 : ""), strArr);
    }

    public static boolean queryIsEmpty(android.database.sqlite.SQLiteDatabase sQLiteDatabase, java.lang.String str) {
        return longForQuery(sQLiteDatabase, new java.lang.StringBuilder().append("select exists(select 1 from ").append(str).append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END).toString(), null) == 0;
    }

    public static long longForQuery(android.database.sqlite.SQLiteDatabase sQLiteDatabase, java.lang.String str, java.lang.String[] strArr) {
        android.database.sqlite.SQLiteStatement compileStatement = sQLiteDatabase.compileStatement(str);
        try {
            return longForQuery(compileStatement, strArr);
        } finally {
            compileStatement.close();
        }
    }

    public static long longForQuery(android.database.sqlite.SQLiteStatement sQLiteStatement, java.lang.String[] strArr) {
        sQLiteStatement.bindAllArgsAsStrings(strArr);
        return sQLiteStatement.simpleQueryForLong();
    }

    public static java.lang.String stringForQuery(android.database.sqlite.SQLiteDatabase sQLiteDatabase, java.lang.String str, java.lang.String[] strArr) {
        android.database.sqlite.SQLiteStatement compileStatement = sQLiteDatabase.compileStatement(str);
        try {
            return stringForQuery(compileStatement, strArr);
        } finally {
            compileStatement.close();
        }
    }

    public static java.lang.String stringForQuery(android.database.sqlite.SQLiteStatement sQLiteStatement, java.lang.String[] strArr) {
        sQLiteStatement.bindAllArgsAsStrings(strArr);
        return sQLiteStatement.simpleQueryForString();
    }

    public static android.os.ParcelFileDescriptor blobFileDescriptorForQuery(android.database.sqlite.SQLiteDatabase sQLiteDatabase, java.lang.String str, java.lang.String[] strArr) {
        android.database.sqlite.SQLiteStatement compileStatement = sQLiteDatabase.compileStatement(str);
        try {
            return blobFileDescriptorForQuery(compileStatement, strArr);
        } finally {
            compileStatement.close();
        }
    }

    public static android.os.ParcelFileDescriptor blobFileDescriptorForQuery(android.database.sqlite.SQLiteStatement sQLiteStatement, java.lang.String[] strArr) {
        sQLiteStatement.bindAllArgsAsStrings(strArr);
        return sQLiteStatement.simpleQueryForBlobFileDescriptor();
    }

    public static void cursorStringToContentValuesIfPresent(android.database.Cursor cursor, android.content.ContentValues contentValues, java.lang.String str) {
        int columnIndex = cursor.getColumnIndex(str);
        if (columnIndex != -1 && !cursor.isNull(columnIndex)) {
            contentValues.put(str, cursor.getString(columnIndex));
        }
    }

    public static void cursorLongToContentValuesIfPresent(android.database.Cursor cursor, android.content.ContentValues contentValues, java.lang.String str) {
        int columnIndex = cursor.getColumnIndex(str);
        if (columnIndex != -1 && !cursor.isNull(columnIndex)) {
            contentValues.put(str, java.lang.Long.valueOf(cursor.getLong(columnIndex)));
        }
    }

    public static void cursorShortToContentValuesIfPresent(android.database.Cursor cursor, android.content.ContentValues contentValues, java.lang.String str) {
        int columnIndex = cursor.getColumnIndex(str);
        if (columnIndex != -1 && !cursor.isNull(columnIndex)) {
            contentValues.put(str, java.lang.Short.valueOf(cursor.getShort(columnIndex)));
        }
    }

    public static void cursorIntToContentValuesIfPresent(android.database.Cursor cursor, android.content.ContentValues contentValues, java.lang.String str) {
        int columnIndex = cursor.getColumnIndex(str);
        if (columnIndex != -1 && !cursor.isNull(columnIndex)) {
            contentValues.put(str, java.lang.Integer.valueOf(cursor.getInt(columnIndex)));
        }
    }

    public static void cursorFloatToContentValuesIfPresent(android.database.Cursor cursor, android.content.ContentValues contentValues, java.lang.String str) {
        int columnIndex = cursor.getColumnIndex(str);
        if (columnIndex != -1 && !cursor.isNull(columnIndex)) {
            contentValues.put(str, java.lang.Float.valueOf(cursor.getFloat(columnIndex)));
        }
    }

    public static void cursorDoubleToContentValuesIfPresent(android.database.Cursor cursor, android.content.ContentValues contentValues, java.lang.String str) {
        int columnIndex = cursor.getColumnIndex(str);
        if (columnIndex != -1 && !cursor.isNull(columnIndex)) {
            contentValues.put(str, java.lang.Double.valueOf(cursor.getDouble(columnIndex)));
        }
    }

    @java.lang.Deprecated
    public static class InsertHelper {
        public static final int TABLE_INFO_PRAGMA_COLUMNNAME_INDEX = 1;
        public static final int TABLE_INFO_PRAGMA_DEFAULT_INDEX = 4;
        private java.util.HashMap<java.lang.String, java.lang.Integer> mColumns;
        private final android.database.sqlite.SQLiteDatabase mDb;
        private final java.lang.String mTableName;
        private java.lang.String mInsertSQL = null;
        private android.database.sqlite.SQLiteStatement mInsertStatement = null;
        private android.database.sqlite.SQLiteStatement mReplaceStatement = null;
        private android.database.sqlite.SQLiteStatement mPreparedStatement = null;

        public InsertHelper(android.database.sqlite.SQLiteDatabase sQLiteDatabase, java.lang.String str) {
            this.mDb = sQLiteDatabase;
            this.mTableName = str;
        }

        private void buildSQL() throws android.database.SQLException {
            java.lang.StringBuilder sb = new java.lang.StringBuilder(128);
            sb.append("INSERT INTO ");
            sb.append(this.mTableName);
            sb.append(" (");
            java.lang.StringBuilder sb2 = new java.lang.StringBuilder(128);
            sb2.append("VALUES (");
            android.database.Cursor cursor = null;
            try {
                cursor = this.mDb.rawQuery("PRAGMA table_info(" + this.mTableName + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END, null);
                this.mColumns = new java.util.HashMap<>(cursor.getCount());
                int i = 1;
                while (cursor.moveToNext()) {
                    java.lang.String string = cursor.getString(1);
                    java.lang.String string2 = cursor.getString(4);
                    this.mColumns.put(string, java.lang.Integer.valueOf(i));
                    sb.append("'");
                    sb.append(string);
                    sb.append("'");
                    if (string2 == null) {
                        sb2.append("?");
                    } else {
                        sb2.append("COALESCE(?, ");
                        sb2.append(string2);
                        sb2.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
                    }
                    sb.append(i == cursor.getCount() ? ") " : ", ");
                    sb2.append(i == cursor.getCount() ? ");" : ", ");
                    i++;
                }
                sb.append((java.lang.CharSequence) sb2);
                this.mInsertSQL = sb.toString();
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }

        private android.database.sqlite.SQLiteStatement getStatement(boolean z) throws android.database.SQLException {
            if (z) {
                if (this.mReplaceStatement == null) {
                    if (this.mInsertSQL == null) {
                        buildSQL();
                    }
                    this.mReplaceStatement = this.mDb.compileStatement("INSERT OR REPLACE" + this.mInsertSQL.substring(6));
                }
                return this.mReplaceStatement;
            }
            if (this.mInsertStatement == null) {
                if (this.mInsertSQL == null) {
                    buildSQL();
                }
                this.mInsertStatement = this.mDb.compileStatement(this.mInsertSQL);
            }
            return this.mInsertStatement;
        }

        private long insertInternal(android.content.ContentValues contentValues, boolean z) {
            this.mDb.beginTransactionNonExclusive();
            try {
                try {
                    android.database.sqlite.SQLiteStatement statement = getStatement(z);
                    statement.clearBindings();
                    for (java.util.Map.Entry<java.lang.String, java.lang.Object> entry : contentValues.valueSet()) {
                        android.database.DatabaseUtils.bindObjectToProgram(statement, getColumnIndex(entry.getKey()), entry.getValue());
                    }
                    long executeInsert = statement.executeInsert();
                    this.mDb.setTransactionSuccessful();
                    return executeInsert;
                } catch (android.database.SQLException e) {
                    android.util.Log.e(android.database.DatabaseUtils.TAG, "Error inserting " + contentValues + " into table  " + this.mTableName, e);
                    this.mDb.endTransaction();
                    return -1L;
                }
            } finally {
                this.mDb.endTransaction();
            }
        }

        public int getColumnIndex(java.lang.String str) {
            getStatement(false);
            java.lang.Integer num = this.mColumns.get(str);
            if (num == null) {
                throw new java.lang.IllegalArgumentException("column '" + str + "' is invalid");
            }
            return num.intValue();
        }

        public void bind(int i, double d) {
            this.mPreparedStatement.bindDouble(i, d);
        }

        public void bind(int i, float f) {
            this.mPreparedStatement.bindDouble(i, f);
        }

        public void bind(int i, long j) {
            this.mPreparedStatement.bindLong(i, j);
        }

        public void bind(int i, int i2) {
            this.mPreparedStatement.bindLong(i, i2);
        }

        public void bind(int i, boolean z) {
            this.mPreparedStatement.bindLong(i, z ? 1L : 0L);
        }

        public void bindNull(int i) {
            this.mPreparedStatement.bindNull(i);
        }

        public void bind(int i, byte[] bArr) {
            if (bArr == null) {
                this.mPreparedStatement.bindNull(i);
            } else {
                this.mPreparedStatement.bindBlob(i, bArr);
            }
        }

        public void bind(int i, java.lang.String str) {
            if (str == null) {
                this.mPreparedStatement.bindNull(i);
            } else {
                this.mPreparedStatement.bindString(i, str);
            }
        }

        public long insert(android.content.ContentValues contentValues) {
            return insertInternal(contentValues, false);
        }

        public long execute() {
            if (this.mPreparedStatement == null) {
                throw new java.lang.IllegalStateException("you must prepare this inserter before calling execute");
            }
            try {
                try {
                    return this.mPreparedStatement.executeInsert();
                } catch (android.database.SQLException e) {
                    android.util.Log.e(android.database.DatabaseUtils.TAG, "Error executing InsertHelper with table " + this.mTableName, e);
                    this.mPreparedStatement = null;
                    return -1L;
                }
            } finally {
                this.mPreparedStatement = null;
            }
        }

        public void prepareForInsert() {
            this.mPreparedStatement = getStatement(false);
            this.mPreparedStatement.clearBindings();
        }

        public void prepareForReplace() {
            this.mPreparedStatement = getStatement(true);
            this.mPreparedStatement.clearBindings();
        }

        public long replace(android.content.ContentValues contentValues) {
            return insertInternal(contentValues, true);
        }

        public void close() {
            if (this.mInsertStatement != null) {
                this.mInsertStatement.close();
                this.mInsertStatement = null;
            }
            if (this.mReplaceStatement != null) {
                this.mReplaceStatement.close();
                this.mReplaceStatement = null;
            }
            this.mInsertSQL = null;
            this.mColumns = null;
        }
    }

    public static void createDbFromSqlStatements(android.content.Context context, java.lang.String str, int i, java.lang.String str2) {
        android.database.sqlite.SQLiteDatabase openOrCreateDatabase = context.openOrCreateDatabase(str, 0, null);
        for (java.lang.String str3 : android.text.TextUtils.split(str2, ";\n")) {
            if (!android.text.TextUtils.isEmpty(str3)) {
                openOrCreateDatabase.execSQL(str3);
            }
        }
        openOrCreateDatabase.setVersion(i);
        openOrCreateDatabase.close();
    }

    private static java.lang.String getSqlStatementPrefixSimple(java.lang.String str) {
        java.lang.String trim = str.trim();
        if (trim.length() < 3) {
            return null;
        }
        return trim.substring(0, 3).toUpperCase(java.util.Locale.ROOT);
    }

    private static java.lang.String getSqlStatementPrefixExtended(java.lang.String str) {
        java.util.regex.Matcher matcher = sPrefixPattern.matcher(str);
        if (matcher.lookingAt()) {
            return matcher.group(2).toUpperCase(java.util.Locale.ROOT);
        }
        return null;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private static int categorizeStatement(java.lang.String str, java.lang.String str2) {
        char c;
        if (str == null) {
            return 99;
        }
        switch (str.hashCode()) {
            case 64905:
                if (str.equals("ALT")) {
                    c = '\r';
                    break;
                }
                c = 65535;
                break;
            case 64948:
                if (str.equals("ANA")) {
                    c = 14;
                    break;
                }
                c = 65535;
                break;
            case 65153:
                if (str.equals("ATT")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case 65636:
                if (str.equals("BEG")) {
                    c = '\t';
                    break;
                }
                c = 65535;
                break;
            case 66913:
                if (str.equals("COM")) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case 66998:
                if (str.equals("CRE")) {
                    c = 11;
                    break;
                }
                c = 65535;
                break;
            case 67563:
                if (str.equals("DEL")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 67571:
                if (str.equals("DET")) {
                    c = 15;
                    break;
                }
                c = 65535;
                break;
            case 67969:
                if (str.equals("DRO")) {
                    c = '\f';
                    break;
                }
                c = 65535;
                break;
            case 68795:
                if (str.equals("END")) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case 72654:
                if (str.equals("INS")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 79487:
                if (str.equals("PRA")) {
                    c = '\n';
                    break;
                }
                c = 65535;
                break;
            case 81021:
                if (str.equals("REP")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 81327:
                if (str.equals("ROL")) {
                    c = '\b';
                    break;
                }
                c = 65535;
                break;
            case 81978:
                if (str.equals("SEL")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 84233:
                if (str.equals("UPD")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 85954:
                if (str.equals("WIT")) {
                    c = 16;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                break;
            case 1:
            case 2:
            case 3:
            case 4:
                break;
            case 5:
                break;
            case 6:
            case 7:
                break;
            case '\b':
                if (str2.toUpperCase(java.util.Locale.ROOT).contains(" TO ")) {
                }
                break;
            case '\t':
                break;
            case '\n':
                break;
            case 11:
                break;
            case '\f':
            case '\r':
                break;
            case 14:
            case 15:
                break;
            case 16:
                break;
            default:
                if (str.startsWith("--") || str.startsWith(android.content.IntentFilter.WILDCARD_PATH)) {
                }
                break;
        }
        return 99;
    }

    public static int getSqlStatementTypeExtended(java.lang.String str) {
        int categorizeStatement = categorizeStatement(getSqlStatementPrefixSimple(str), str);
        if (categorizeStatement == 102) {
            return categorizeStatement(getSqlStatementPrefixExtended(str), str);
        }
        return categorizeStatement;
    }

    public static int getSqlStatementType(int i) {
        switch (i) {
            case 100:
                return 99;
            case 101:
                return 8;
            case 102:
                return 99;
            default:
                return i;
        }
    }

    public static int getSqlStatementType(java.lang.String str) {
        return getSqlStatementType(getSqlStatementTypeExtended(str));
    }

    public static java.lang.String[] appendSelectionArgs(java.lang.String[] strArr, java.lang.String[] strArr2) {
        if (strArr == null || strArr.length == 0) {
            return strArr2;
        }
        java.lang.String[] strArr3 = new java.lang.String[strArr.length + strArr2.length];
        java.lang.System.arraycopy(strArr, 0, strArr3, 0, strArr.length);
        java.lang.System.arraycopy(strArr2, 0, strArr3, strArr.length, strArr2.length);
        return strArr3;
    }

    public static int findRowIdColumnIndex(java.lang.String[] strArr) {
        int length = strArr.length;
        for (int i = 0; i < length; i++) {
            if (strArr[i].equals("_id")) {
                return i;
            }
        }
        return -1;
    }

    public static java.lang.String escapeForLike(java.lang.String str) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char charAt = str.charAt(i);
            switch (charAt) {
                case '%':
                    sb.append('\\');
                    break;
                case '_':
                    sb.append('\\');
                    break;
            }
            sb.append(charAt);
        }
        return sb.toString();
    }
}
