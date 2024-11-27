package android.database.sqlite;

/* loaded from: classes.dex */
public class SQLiteQueryBuilder {
    private static final int STRICT_COLUMNS = 2;
    private static final int STRICT_GRAMMAR = 4;
    private static final int STRICT_PARENTHESES = 1;
    private static final java.lang.String TAG = "SQLiteQueryBuilder";
    private static final java.util.regex.Pattern sAggregationPattern = java.util.regex.Pattern.compile("(?i)(AVG|COUNT|MAX|MIN|SUM|TOTAL|GROUP_CONCAT)\\((.+)\\)");
    private int mStrictFlags;
    private java.util.Map<java.lang.String, java.lang.String> mProjectionMap = null;
    private java.util.Collection<java.util.regex.Pattern> mProjectionGreylist = null;
    private java.lang.String mTables = "";
    private java.lang.StringBuilder mWhereClause = null;
    private boolean mDistinct = false;
    private android.database.sqlite.SQLiteDatabase.CursorFactory mFactory = null;

    public void setDistinct(boolean z) {
        this.mDistinct = z;
    }

    public boolean isDistinct() {
        return this.mDistinct;
    }

    public java.lang.String getTables() {
        return this.mTables;
    }

    public void setTables(java.lang.String str) {
        this.mTables = str;
    }

    public void appendWhere(java.lang.CharSequence charSequence) {
        if (this.mWhereClause == null) {
            this.mWhereClause = new java.lang.StringBuilder(charSequence.length() + 16);
        }
        this.mWhereClause.append(charSequence);
    }

    public void appendWhereEscapeString(java.lang.String str) {
        if (this.mWhereClause == null) {
            this.mWhereClause = new java.lang.StringBuilder(str.length() + 16);
        }
        android.database.DatabaseUtils.appendEscapedSQLString(this.mWhereClause, str);
    }

    public void appendWhereStandalone(java.lang.CharSequence charSequence) {
        if (this.mWhereClause == null) {
            this.mWhereClause = new java.lang.StringBuilder(charSequence.length() + 16);
        }
        if (this.mWhereClause.length() > 0) {
            this.mWhereClause.append(" AND ");
        }
        this.mWhereClause.append('(').append(charSequence).append(')');
    }

    public void setProjectionMap(java.util.Map<java.lang.String, java.lang.String> map) {
        this.mProjectionMap = map;
    }

    public java.util.Map<java.lang.String, java.lang.String> getProjectionMap() {
        return this.mProjectionMap;
    }

    public void setProjectionGreylist(java.util.Collection<java.util.regex.Pattern> collection) {
        this.mProjectionGreylist = collection;
    }

    public java.util.Collection<java.util.regex.Pattern> getProjectionGreylist() {
        return this.mProjectionGreylist;
    }

    @java.lang.Deprecated
    public void setProjectionAggregationAllowed(boolean z) {
    }

    @java.lang.Deprecated
    public boolean isProjectionAggregationAllowed() {
        return true;
    }

    public void setCursorFactory(android.database.sqlite.SQLiteDatabase.CursorFactory cursorFactory) {
        this.mFactory = cursorFactory;
    }

    public android.database.sqlite.SQLiteDatabase.CursorFactory getCursorFactory() {
        return this.mFactory;
    }

    public void setStrict(boolean z) {
        if (z) {
            this.mStrictFlags |= 1;
        } else {
            this.mStrictFlags &= -2;
        }
    }

    public boolean isStrict() {
        return (this.mStrictFlags & 1) != 0;
    }

    public void setStrictColumns(boolean z) {
        if (z) {
            this.mStrictFlags |= 2;
        } else {
            this.mStrictFlags &= -3;
        }
    }

    public boolean isStrictColumns() {
        return (this.mStrictFlags & 2) != 0;
    }

    public void setStrictGrammar(boolean z) {
        if (z) {
            this.mStrictFlags |= 4;
        } else {
            this.mStrictFlags &= -5;
        }
    }

    public boolean isStrictGrammar() {
        return (this.mStrictFlags & 4) != 0;
    }

    public static java.lang.String buildQueryString(boolean z, java.lang.String str, java.lang.String[] strArr, java.lang.String str2, java.lang.String str3, java.lang.String str4, java.lang.String str5, java.lang.String str6) {
        if (android.text.TextUtils.isEmpty(str3) && !android.text.TextUtils.isEmpty(str4)) {
            throw new java.lang.IllegalArgumentException("HAVING clauses are only permitted when using a groupBy clause");
        }
        java.lang.StringBuilder sb = new java.lang.StringBuilder(120);
        sb.append("SELECT ");
        if (z) {
            sb.append("DISTINCT ");
        }
        if (strArr != null && strArr.length != 0) {
            appendColumns(sb, strArr);
        } else {
            sb.append("* ");
        }
        sb.append("FROM ");
        sb.append(str);
        appendClause(sb, " WHERE ", str2);
        appendClause(sb, " GROUP BY ", str3);
        appendClause(sb, " HAVING ", str4);
        appendClause(sb, " ORDER BY ", str5);
        appendClause(sb, " LIMIT ", str6);
        return sb.toString();
    }

    private static void appendClause(java.lang.StringBuilder sb, java.lang.String str, java.lang.String str2) {
        if (!android.text.TextUtils.isEmpty(str2)) {
            sb.append(str);
            sb.append(str2);
        }
    }

    public static void appendColumns(java.lang.StringBuilder sb, java.lang.String[] strArr) {
        int length = strArr.length;
        for (int i = 0; i < length; i++) {
            java.lang.String str = strArr[i];
            if (str != null) {
                if (i > 0) {
                    sb.append(", ");
                }
                sb.append(str);
            }
        }
        sb.append(' ');
    }

    public android.database.Cursor query(android.database.sqlite.SQLiteDatabase sQLiteDatabase, java.lang.String[] strArr, java.lang.String str, java.lang.String[] strArr2, java.lang.String str2, java.lang.String str3, java.lang.String str4) {
        return query(sQLiteDatabase, strArr, str, strArr2, str2, str3, str4, null, null);
    }

    public android.database.Cursor query(android.database.sqlite.SQLiteDatabase sQLiteDatabase, java.lang.String[] strArr, java.lang.String str, java.lang.String[] strArr2, java.lang.String str2, java.lang.String str3, java.lang.String str4, java.lang.String str5) {
        return query(sQLiteDatabase, strArr, str, strArr2, str2, str3, str4, str5, null);
    }

    public android.database.Cursor query(android.database.sqlite.SQLiteDatabase sQLiteDatabase, java.lang.String[] strArr, java.lang.String str, java.lang.String[] strArr2, java.lang.String str2, java.lang.String str3, java.lang.String str4, java.lang.String str5, android.os.CancellationSignal cancellationSignal) {
        if (this.mTables == null) {
            return null;
        }
        java.lang.String buildQuery = buildQuery(strArr, str, str2, str3, str4, str5);
        if (isStrictColumns()) {
            enforceStrictColumns(strArr);
        }
        if (isStrictGrammar()) {
            enforceStrictGrammar(str, str2, str3, str4, str5);
        }
        if (isStrict()) {
            sQLiteDatabase.validateSql(buildQuery, cancellationSignal);
            buildQuery = buildQuery(strArr, wrap(str), str2, wrap(str3), str4, str5);
        }
        if (android.util.Log.isLoggable(TAG, 3)) {
            if (android.os.Build.IS_DEBUGGABLE) {
                android.util.Log.d(TAG, buildQuery + " with args " + java.util.Arrays.toString(strArr2));
            } else {
                android.util.Log.d(TAG, buildQuery);
            }
        }
        return sQLiteDatabase.rawQueryWithFactory(this.mFactory, buildQuery, strArr2, android.database.sqlite.SQLiteDatabase.findEditTable(this.mTables), cancellationSignal);
    }

    public long insert(android.database.sqlite.SQLiteDatabase sQLiteDatabase, android.content.ContentValues contentValues) {
        java.util.Objects.requireNonNull(this.mTables, "No tables defined");
        java.util.Objects.requireNonNull(sQLiteDatabase, "No database defined");
        java.util.Objects.requireNonNull(contentValues, "No values defined");
        if (isStrictColumns()) {
            enforceStrictColumns(contentValues);
        }
        java.lang.String buildInsert = buildInsert(contentValues);
        android.util.ArrayMap<java.lang.String, java.lang.Object> values = contentValues.getValues();
        int size = values.size();
        java.lang.Object[] objArr = new java.lang.Object[size];
        for (int i = 0; i < size; i++) {
            objArr[i] = values.valueAt(i);
        }
        if (android.util.Log.isLoggable(TAG, 3)) {
            if (android.os.Build.IS_DEBUGGABLE) {
                android.util.Log.d(TAG, buildInsert + " with args " + java.util.Arrays.toString(objArr));
            } else {
                android.util.Log.d(TAG, buildInsert);
            }
        }
        return android.database.DatabaseUtils.executeInsert(sQLiteDatabase, buildInsert, objArr);
    }

    public int update(android.database.sqlite.SQLiteDatabase sQLiteDatabase, android.content.ContentValues contentValues, java.lang.String str, java.lang.String[] strArr) {
        java.util.Objects.requireNonNull(this.mTables, "No tables defined");
        java.util.Objects.requireNonNull(sQLiteDatabase, "No database defined");
        java.util.Objects.requireNonNull(contentValues, "No values defined");
        java.lang.String buildUpdate = buildUpdate(contentValues, str);
        if (isStrictColumns()) {
            enforceStrictColumns(contentValues);
        }
        if (isStrictGrammar()) {
            enforceStrictGrammar(str, null, null, null, null);
        }
        if (isStrict()) {
            sQLiteDatabase.validateSql(buildUpdate, null);
            buildUpdate = buildUpdate(contentValues, wrap(str));
        }
        if (strArr == null) {
            strArr = libcore.util.EmptyArray.STRING;
        }
        android.util.ArrayMap<java.lang.String, java.lang.Object> values = contentValues.getValues();
        int size = values.size();
        int length = strArr.length + size;
        java.lang.Object[] objArr = new java.lang.Object[length];
        for (int i = 0; i < length; i++) {
            if (i < size) {
                objArr[i] = values.valueAt(i);
            } else {
                objArr[i] = strArr[i - size];
            }
        }
        if (android.util.Log.isLoggable(TAG, 3)) {
            if (android.os.Build.IS_DEBUGGABLE) {
                android.util.Log.d(TAG, buildUpdate + " with args " + java.util.Arrays.toString(objArr));
            } else {
                android.util.Log.d(TAG, buildUpdate);
            }
        }
        return android.database.DatabaseUtils.executeUpdateDelete(sQLiteDatabase, buildUpdate, objArr);
    }

    public int delete(android.database.sqlite.SQLiteDatabase sQLiteDatabase, java.lang.String str, java.lang.String[] strArr) {
        java.util.Objects.requireNonNull(this.mTables, "No tables defined");
        java.util.Objects.requireNonNull(sQLiteDatabase, "No database defined");
        java.lang.String buildDelete = buildDelete(str);
        if (isStrictGrammar()) {
            enforceStrictGrammar(str, null, null, null, null);
        }
        if (isStrict()) {
            sQLiteDatabase.validateSql(buildDelete, null);
            buildDelete = buildDelete(wrap(str));
        }
        if (android.util.Log.isLoggable(TAG, 3)) {
            if (android.os.Build.IS_DEBUGGABLE) {
                android.util.Log.d(TAG, buildDelete + " with args " + java.util.Arrays.toString(strArr));
            } else {
                android.util.Log.d(TAG, buildDelete);
            }
        }
        return android.database.DatabaseUtils.executeUpdateDelete(sQLiteDatabase, buildDelete, strArr);
    }

    private void enforceStrictColumns(java.lang.String[] strArr) {
        java.util.Objects.requireNonNull(this.mProjectionMap, "No projection map defined");
        computeProjection(strArr);
    }

    private void enforceStrictColumns(android.content.ContentValues contentValues) {
        java.util.Objects.requireNonNull(this.mProjectionMap, "No projection map defined");
        android.util.ArrayMap<java.lang.String, java.lang.Object> values = contentValues.getValues();
        for (int i = 0; i < values.size(); i++) {
            java.lang.String keyAt = values.keyAt(i);
            if (!this.mProjectionMap.containsKey(keyAt)) {
                throw new java.lang.IllegalArgumentException("Invalid column " + keyAt);
            }
        }
    }

    private void enforceStrictGrammar(java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4, java.lang.String str5) {
        android.database.sqlite.SQLiteTokenizer.tokenize(str, 0, new java.util.function.Consumer() { // from class: android.database.sqlite.SQLiteQueryBuilder$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                android.database.sqlite.SQLiteQueryBuilder.this.enforceStrictToken((java.lang.String) obj);
            }
        });
        android.database.sqlite.SQLiteTokenizer.tokenize(str2, 0, new java.util.function.Consumer() { // from class: android.database.sqlite.SQLiteQueryBuilder$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                android.database.sqlite.SQLiteQueryBuilder.this.enforceStrictToken((java.lang.String) obj);
            }
        });
        android.database.sqlite.SQLiteTokenizer.tokenize(str3, 0, new java.util.function.Consumer() { // from class: android.database.sqlite.SQLiteQueryBuilder$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                android.database.sqlite.SQLiteQueryBuilder.this.enforceStrictToken((java.lang.String) obj);
            }
        });
        android.database.sqlite.SQLiteTokenizer.tokenize(str4, 0, new java.util.function.Consumer() { // from class: android.database.sqlite.SQLiteQueryBuilder$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                android.database.sqlite.SQLiteQueryBuilder.this.enforceStrictToken((java.lang.String) obj);
            }
        });
        android.database.sqlite.SQLiteTokenizer.tokenize(str5, 0, new java.util.function.Consumer() { // from class: android.database.sqlite.SQLiteQueryBuilder$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                android.database.sqlite.SQLiteQueryBuilder.this.enforceStrictToken((java.lang.String) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public void enforceStrictToken(java.lang.String str) {
        char c;
        if (android.text.TextUtils.isEmpty(str) || isTableOrColumn(str) || android.database.sqlite.SQLiteTokenizer.isFunction(str) || android.database.sqlite.SQLiteTokenizer.isType(str)) {
            return;
        }
        boolean isKeyword = android.database.sqlite.SQLiteTokenizer.isKeyword(str);
        java.lang.String upperCase = str.toUpperCase(java.util.Locale.US);
        switch (upperCase.hashCode()) {
            case -1852692228:
                if (upperCase.equals("SELECT")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -1770483422:
                if (upperCase.equals("VALUES")) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case -1734422544:
                if (upperCase.equals("WINDOW")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case 2166698:
                if (upperCase.equals("FROM")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 68091487:
                if (upperCase.equals("GROUP")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 72438683:
                if (upperCase.equals("LIMIT")) {
                    c = '\b';
                    break;
                }
                c = 65535;
                break;
            case 75468590:
                if (upperCase.equals("ORDER")) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case 82560199:
                if (upperCase.equals("WHERE")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 2123962405:
                if (upperCase.equals("HAVING")) {
                    c = 4;
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
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case '\b':
                isKeyword = false;
                break;
        }
        if (!isKeyword) {
            throw new java.lang.IllegalArgumentException("Invalid token " + str);
        }
    }

    public java.lang.String buildQuery(java.lang.String[] strArr, java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4, java.lang.String str5) {
        return buildQueryString(this.mDistinct, this.mTables, computeProjection(strArr), computeWhere(str), str2, str3, str4, str5);
    }

    @java.lang.Deprecated
    public java.lang.String buildQuery(java.lang.String[] strArr, java.lang.String str, java.lang.String[] strArr2, java.lang.String str2, java.lang.String str3, java.lang.String str4, java.lang.String str5) {
        return buildQuery(strArr, str, str2, str3, str4, str5);
    }

    public java.lang.String buildInsert(android.content.ContentValues contentValues) {
        if (contentValues == null || contentValues.isEmpty()) {
            throw new java.lang.IllegalArgumentException("Empty values");
        }
        java.lang.StringBuilder sb = new java.lang.StringBuilder(120);
        sb.append("INSERT INTO ");
        sb.append(android.database.sqlite.SQLiteDatabase.findEditTable(this.mTables));
        sb.append(" (");
        android.util.ArrayMap<java.lang.String, java.lang.Object> values = contentValues.getValues();
        for (int i = 0; i < values.size(); i++) {
            if (i > 0) {
                sb.append(',');
            }
            sb.append(values.keyAt(i));
        }
        sb.append(") VALUES (");
        for (int i2 = 0; i2 < values.size(); i2++) {
            if (i2 > 0) {
                sb.append(',');
            }
            sb.append('?');
        }
        sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        return sb.toString();
    }

    public java.lang.String buildUpdate(android.content.ContentValues contentValues, java.lang.String str) {
        if (contentValues == null || contentValues.isEmpty()) {
            throw new java.lang.IllegalArgumentException("Empty values");
        }
        java.lang.StringBuilder sb = new java.lang.StringBuilder(120);
        sb.append("UPDATE ");
        sb.append(android.database.sqlite.SQLiteDatabase.findEditTable(this.mTables));
        sb.append(" SET ");
        android.util.ArrayMap<java.lang.String, java.lang.Object> values = contentValues.getValues();
        for (int i = 0; i < values.size(); i++) {
            if (i > 0) {
                sb.append(',');
            }
            sb.append(values.keyAt(i));
            sb.append("=?");
        }
        appendClause(sb, " WHERE ", computeWhere(str));
        return sb.toString();
    }

    public java.lang.String buildDelete(java.lang.String str) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder(120);
        sb.append("DELETE FROM ");
        sb.append(android.database.sqlite.SQLiteDatabase.findEditTable(this.mTables));
        appendClause(sb, " WHERE ", computeWhere(str));
        return sb.toString();
    }

    public java.lang.String buildUnionSubQuery(java.lang.String str, java.lang.String[] strArr, java.util.Set<java.lang.String> set, int i, java.lang.String str2, java.lang.String str3, java.lang.String str4, java.lang.String str5) {
        int length = strArr.length;
        java.lang.String[] strArr2 = new java.lang.String[length];
        for (int i2 = 0; i2 < length; i2++) {
            java.lang.String str6 = strArr[i2];
            if (str6.equals(str)) {
                strArr2[i2] = "'" + str2 + "' AS " + str;
            } else {
                if (i2 > i && !set.contains(str6)) {
                    strArr2[i2] = "NULL AS " + str6;
                }
                strArr2[i2] = str6;
            }
        }
        return buildQuery(strArr2, str3, str4, str5, null, null);
    }

    @java.lang.Deprecated
    public java.lang.String buildUnionSubQuery(java.lang.String str, java.lang.String[] strArr, java.util.Set<java.lang.String> set, int i, java.lang.String str2, java.lang.String str3, java.lang.String[] strArr2, java.lang.String str4, java.lang.String str5) {
        return buildUnionSubQuery(str, strArr, set, i, str2, str3, str4, str5);
    }

    public java.lang.String buildUnionQuery(java.lang.String[] strArr, java.lang.String str, java.lang.String str2) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder(128);
        int length = strArr.length;
        java.lang.String str3 = this.mDistinct ? " UNION " : " UNION ALL ";
        for (int i = 0; i < length; i++) {
            if (i > 0) {
                sb.append(str3);
            }
            sb.append(strArr[i]);
        }
        appendClause(sb, " ORDER BY ", str);
        appendClause(sb, " LIMIT ", str2);
        return sb.toString();
    }

    private static java.lang.String maybeWithOperator(java.lang.String str, java.lang.String str2) {
        if (str != null) {
            return str + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_START + str2 + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
        }
        return str2;
    }

    public java.lang.String[] computeProjection(java.lang.String[] strArr) {
        int i = 0;
        if (!com.android.internal.util.ArrayUtils.isEmpty(strArr)) {
            java.lang.String[] strArr2 = new java.lang.String[strArr.length];
            while (i < strArr.length) {
                strArr2[i] = computeSingleProjectionOrThrow(strArr[i]);
                i++;
            }
            return strArr2;
        }
        if (this.mProjectionMap != null) {
            java.util.Set<java.util.Map.Entry<java.lang.String, java.lang.String>> entrySet = this.mProjectionMap.entrySet();
            java.lang.String[] strArr3 = new java.lang.String[entrySet.size()];
            for (java.util.Map.Entry<java.lang.String, java.lang.String> entry : entrySet) {
                if (!entry.getKey().equals(android.provider.BaseColumns._COUNT)) {
                    strArr3[i] = entry.getValue();
                    i++;
                }
            }
            return strArr3;
        }
        return null;
    }

    private java.lang.String computeSingleProjectionOrThrow(java.lang.String str) {
        java.lang.String computeSingleProjection = computeSingleProjection(str);
        if (computeSingleProjection != null) {
            return computeSingleProjection;
        }
        throw new java.lang.IllegalArgumentException("Invalid column " + str);
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0035  */
    /* JADX WARN: Removed duplicated region for block: B:13:0x003a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private java.lang.String computeSingleProjection(java.lang.String str) {
        java.lang.String str2;
        java.lang.String str3;
        java.lang.String str4;
        if (this.mProjectionMap == null) {
            return str;
        }
        java.lang.String str5 = this.mProjectionMap.get(str);
        boolean z = true;
        if (str5 == null) {
            java.util.regex.Matcher matcher = sAggregationPattern.matcher(str);
            if (matcher.matches()) {
                str4 = matcher.group(1);
                str3 = matcher.group(2);
                str2 = this.mProjectionMap.get(str3);
                if (str2 == null) {
                    return maybeWithOperator(str4, str2);
                }
                if (this.mStrictFlags == 0 && (str3.contains(" AS ") || str3.contains(" as "))) {
                    return maybeWithOperator(str4, str3);
                }
                if (this.mProjectionGreylist != null) {
                    java.util.Iterator<java.util.regex.Pattern> it = this.mProjectionGreylist.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            z = false;
                            break;
                        }
                        if (it.next().matcher(str3).matches()) {
                            break;
                        }
                    }
                    if (z) {
                        android.util.Log.w(TAG, "Allowing abusive custom column: " + str3);
                        return maybeWithOperator(str4, str3);
                    }
                }
                return null;
            }
        }
        str2 = str5;
        str3 = str;
        str4 = null;
        if (str2 == null) {
        }
    }

    private boolean isTableOrColumn(java.lang.String str) {
        return this.mTables.equals(str) || computeSingleProjection(str) != null;
    }

    public java.lang.String computeWhere(java.lang.String str) {
        boolean z = !android.text.TextUtils.isEmpty(this.mWhereClause);
        boolean z2 = !android.text.TextUtils.isEmpty(str);
        if (z || z2) {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            if (z) {
                sb.append('(').append((java.lang.CharSequence) this.mWhereClause).append(')');
            }
            if (z && z2) {
                sb.append(" AND ");
            }
            if (z2) {
                sb.append('(').append(str).append(')');
            }
            return sb.toString();
        }
        return null;
    }

    private java.lang.String wrap(java.lang.String str) {
        if (android.text.TextUtils.isEmpty(str)) {
            return str;
        }
        return android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_START + str + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
    }
}
