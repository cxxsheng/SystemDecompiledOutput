package com.android.server;

/* loaded from: classes.dex */
public abstract class IntentResolver<F, R> {
    private static final boolean DEBUG = false;
    private static final java.lang.String TAG = "IntentResolver";
    private static final boolean localLOGV = false;
    private static final boolean localVerificationLOGV = false;
    private static final java.util.Comparator mResolvePrioritySorter = new java.util.Comparator() { // from class: com.android.server.IntentResolver.1
        @Override // java.util.Comparator
        public int compare(java.lang.Object obj, java.lang.Object obj2) {
            int priority = ((android.content.IntentFilter) obj).getPriority();
            int priority2 = ((android.content.IntentFilter) obj2).getPriority();
            if (priority > priority2) {
                return -1;
            }
            return priority < priority2 ? 1 : 0;
        }
    };
    protected final android.util.ArraySet<F> mFilters = new android.util.ArraySet<>();
    private final android.util.ArrayMap<java.lang.String, F[]> mTypeToFilter = new android.util.ArrayMap<>();
    private final android.util.ArrayMap<java.lang.String, F[]> mBaseTypeToFilter = new android.util.ArrayMap<>();
    private final android.util.ArrayMap<java.lang.String, F[]> mWildTypeToFilter = new android.util.ArrayMap<>();
    private final android.util.ArrayMap<java.lang.String, F[]> mSchemeToFilter = new android.util.ArrayMap<>();
    private final android.util.ArrayMap<java.lang.String, F[]> mActionToFilter = new android.util.ArrayMap<>();
    private final android.util.ArrayMap<java.lang.String, F[]> mTypedActionToFilter = new android.util.ArrayMap<>();

    protected abstract android.content.IntentFilter getIntentFilter(@android.annotation.NonNull F f);

    protected abstract boolean isPackageForFilter(java.lang.String str, F f);

    protected abstract F[] newArray(int i);

    public void addFilter(@android.annotation.Nullable com.android.server.pm.snapshot.PackageDataSnapshot packageDataSnapshot, F f) {
        android.content.IntentFilter intentFilter = getIntentFilter(f);
        this.mFilters.add(f);
        int register_intent_filter = register_intent_filter(f, intentFilter.schemesIterator(), this.mSchemeToFilter, "      Scheme: ");
        int register_mime_types = register_mime_types(f, "      Type: ");
        if (register_intent_filter == 0 && register_mime_types == 0) {
            register_intent_filter(f, intentFilter.actionsIterator(), this.mActionToFilter, "      Action: ");
        }
        if (register_mime_types != 0) {
            register_intent_filter(f, intentFilter.actionsIterator(), this.mTypedActionToFilter, "      TypedAction: ");
        }
    }

    public static boolean intentMatchesFilter(android.content.IntentFilter intentFilter, android.content.Intent intent, java.lang.String str) {
        java.lang.String str2;
        boolean z = (intent.getFlags() & 8) != 0;
        android.util.LogPrinter logPrinter = z ? new android.util.LogPrinter(2, TAG, 3) : null;
        if (z) {
            android.util.Slog.v(TAG, "Intent: " + intent);
            android.util.Slog.v(TAG, "Matching against filter: " + intentFilter);
            intentFilter.dump(logPrinter, "  ");
        }
        int match = intentFilter.match(intent.getAction(), str, intent.getScheme(), intent.getData(), intent.getCategories(), TAG);
        if (match >= 0) {
            if (z) {
                android.util.Slog.v(TAG, "Filter matched!  match=0x" + java.lang.Integer.toHexString(match));
            }
            return true;
        }
        if (z) {
            switch (match) {
                case -4:
                    str2 = "category";
                    break;
                case -3:
                    str2 = com.android.server.pm.verify.domain.DomainVerificationPersistence.ATTR_ACTION;
                    break;
                case -2:
                    str2 = "data";
                    break;
                case -1:
                    str2 = com.android.server.voiceinteraction.DatabaseHelper.SoundModelContract.KEY_TYPE;
                    break;
                default:
                    str2 = "unknown reason";
                    break;
            }
            android.util.Slog.v(TAG, "Filter did not match: " + str2);
        }
        return false;
    }

    private java.util.ArrayList<F> collectFilters(F[] fArr, android.content.IntentFilter intentFilter) {
        F f;
        java.util.ArrayList<F> arrayList = null;
        if (fArr != null) {
            for (int i = 0; i < fArr.length && (f = fArr[i]) != null; i++) {
                if (android.content.IntentFilter.filterEquals(getIntentFilter(f), intentFilter)) {
                    if (arrayList == null) {
                        arrayList = new java.util.ArrayList<>();
                    }
                    arrayList.add(f);
                }
            }
        }
        return arrayList;
    }

    public java.util.ArrayList<F> findFilters(android.content.IntentFilter intentFilter) {
        if (intentFilter.countDataSchemes() == 1) {
            return collectFilters(this.mSchemeToFilter.get(intentFilter.getDataScheme(0)), intentFilter);
        }
        if (intentFilter.countDataTypes() != 0 && intentFilter.countActions() == 1) {
            return collectFilters(this.mTypedActionToFilter.get(intentFilter.getAction(0)), intentFilter);
        }
        if (intentFilter.countDataTypes() == 0 && intentFilter.countDataSchemes() == 0 && intentFilter.countActions() == 1) {
            return collectFilters(this.mActionToFilter.get(intentFilter.getAction(0)), intentFilter);
        }
        java.util.Iterator<F> it = this.mFilters.iterator();
        java.util.ArrayList<F> arrayList = null;
        while (it.hasNext()) {
            F next = it.next();
            if (android.content.IntentFilter.filterEquals(getIntentFilter(next), intentFilter)) {
                if (arrayList == null) {
                    arrayList = new java.util.ArrayList<>();
                }
                arrayList.add(next);
            }
        }
        return arrayList;
    }

    public void removeFilter(F f) {
        removeFilterInternal(f);
        this.mFilters.remove(f);
    }

    protected void removeFilterInternal(F f) {
        android.content.IntentFilter intentFilter = getIntentFilter(f);
        int unregister_intent_filter = unregister_intent_filter(f, intentFilter.schemesIterator(), this.mSchemeToFilter, "      Scheme: ");
        int unregister_mime_types = unregister_mime_types(f, "      Type: ");
        if (unregister_intent_filter == 0 && unregister_mime_types == 0) {
            unregister_intent_filter(f, intentFilter.actionsIterator(), this.mActionToFilter, "      Action: ");
        }
        if (unregister_mime_types != 0) {
            unregister_intent_filter(f, intentFilter.actionsIterator(), this.mTypedActionToFilter, "      TypedAction: ");
        }
    }

    boolean dumpMap(java.io.PrintWriter printWriter, java.lang.String str, java.lang.String str2, java.lang.String str3, android.util.ArrayMap<java.lang.String, F[]> arrayMap, java.lang.String str4, boolean z, boolean z2) {
        boolean z3;
        java.lang.String str5;
        android.util.PrintWriterPrinter printWriterPrinter;
        boolean z4;
        android.util.ArrayMap<java.lang.String, F[]> arrayMap2 = arrayMap;
        java.lang.String str6 = str3 + "  ";
        java.lang.String str7 = str3 + "    ";
        android.util.ArrayMap arrayMap3 = new android.util.ArrayMap();
        java.lang.String str8 = str2;
        int i = 0;
        boolean z5 = false;
        android.util.PrintWriterPrinter printWriterPrinter2 = null;
        while (i < arrayMap.size()) {
            F[] valueAt = arrayMap2.valueAt(i);
            int length = valueAt.length;
            if (z2 && !z) {
                arrayMap3.clear();
                int i2 = 0;
                while (true) {
                    if (i2 >= length) {
                        str5 = str8;
                        break;
                    }
                    str5 = str8;
                    F f = valueAt[i2];
                    if (f == null) {
                        break;
                    }
                    if (str4 != null && !isPackageForFilter(str4, f)) {
                        z4 = z5;
                        printWriterPrinter = printWriterPrinter2;
                    } else {
                        java.lang.Object filterToLabel = filterToLabel(f);
                        printWriterPrinter = printWriterPrinter2;
                        int indexOfKey = arrayMap3.indexOfKey(filterToLabel);
                        if (indexOfKey < 0) {
                            z4 = z5;
                            arrayMap3.put(filterToLabel, new android.util.MutableInt(1));
                        } else {
                            z4 = z5;
                            ((android.util.MutableInt) arrayMap3.valueAt(indexOfKey)).value++;
                        }
                    }
                    i2++;
                    str8 = str5;
                    printWriterPrinter2 = printWriterPrinter;
                    z5 = z4;
                }
                z3 = z5;
                android.util.PrintWriterPrinter printWriterPrinter3 = printWriterPrinter2;
                str8 = str5;
                int i3 = 0;
                boolean z6 = false;
                while (i3 < arrayMap3.size()) {
                    if (str8 != null) {
                        printWriter.print(str);
                        printWriter.println(str8);
                        str8 = null;
                    }
                    if (!z6) {
                        printWriter.print(str6);
                        printWriter.print(arrayMap2.keyAt(i));
                        printWriter.println(":");
                        z6 = true;
                    }
                    dumpFilterLabel(printWriter, str7, arrayMap3.keyAt(i3), ((android.util.MutableInt) arrayMap3.valueAt(i3)).value);
                    i3++;
                    z3 = true;
                }
                printWriterPrinter2 = printWriterPrinter3;
            } else {
                z3 = z5;
                str8 = str8;
                printWriterPrinter2 = printWriterPrinter2;
                int i4 = 0;
                boolean z7 = false;
                while (i4 < length) {
                    F f2 = valueAt[i4];
                    if (f2 != null) {
                        if (str4 == null || isPackageForFilter(str4, f2)) {
                            if (str8 != null) {
                                printWriter.print(str);
                                printWriter.println(str8);
                                str8 = null;
                            }
                            if (!z7) {
                                printWriter.print(str6);
                                printWriter.print(arrayMap2.keyAt(i));
                                printWriter.println(":");
                                z7 = true;
                            }
                            dumpFilter(printWriter, str7, f2);
                            if (!z) {
                                z3 = true;
                            } else {
                                if (printWriterPrinter2 == null) {
                                    printWriterPrinter2 = new android.util.PrintWriterPrinter(printWriter);
                                }
                                getIntentFilter(f2).dump(printWriterPrinter2, str7 + "  ");
                                z3 = true;
                            }
                        }
                        i4++;
                        arrayMap2 = arrayMap;
                    }
                }
            }
            z5 = z3;
            i++;
            arrayMap2 = arrayMap;
        }
        return z5;
    }

    void writeProtoMap(android.util.proto.ProtoOutputStream protoOutputStream, long j, android.util.ArrayMap<java.lang.String, F[]> arrayMap) {
        int size = arrayMap.size();
        for (int i = 0; i < size; i++) {
            long start = protoOutputStream.start(j);
            protoOutputStream.write(1138166333441L, arrayMap.keyAt(i));
            for (F f : arrayMap.valueAt(i)) {
                if (f != null) {
                    protoOutputStream.write(2237677961218L, f.toString());
                }
            }
            protoOutputStream.end(start);
        }
    }

    public void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        writeProtoMap(protoOutputStream, 2246267895809L, this.mTypeToFilter);
        writeProtoMap(protoOutputStream, 2246267895810L, this.mBaseTypeToFilter);
        writeProtoMap(protoOutputStream, 2246267895811L, this.mWildTypeToFilter);
        writeProtoMap(protoOutputStream, 2246267895812L, this.mSchemeToFilter);
        writeProtoMap(protoOutputStream, 2246267895813L, this.mActionToFilter);
        writeProtoMap(protoOutputStream, 2246267895814L, this.mTypedActionToFilter);
        protoOutputStream.end(start);
    }

    public boolean dump(java.io.PrintWriter printWriter, java.lang.String str, java.lang.String str2, java.lang.String str3, boolean z, boolean z2) {
        java.lang.String str4 = str2 + "  ";
        java.lang.String str5 = "\n" + str2;
        java.lang.String str6 = str + "\n" + str2;
        if (dumpMap(printWriter, str6, "Full MIME Types:", str4, this.mTypeToFilter, str3, z, z2)) {
            str6 = str5;
        }
        if (dumpMap(printWriter, str6, "Base MIME Types:", str4, this.mBaseTypeToFilter, str3, z, z2)) {
            str6 = str5;
        }
        if (dumpMap(printWriter, str6, "Wild MIME Types:", str4, this.mWildTypeToFilter, str3, z, z2)) {
            str6 = str5;
        }
        if (dumpMap(printWriter, str6, "Schemes:", str4, this.mSchemeToFilter, str3, z, z2)) {
            str6 = str5;
        }
        if (dumpMap(printWriter, str6, "Non-Data Actions:", str4, this.mActionToFilter, str3, z, z2)) {
            str6 = str5;
        }
        if (dumpMap(printWriter, str6, "MIME Typed Actions:", str4, this.mTypedActionToFilter, str3, z, z2)) {
            str6 = str5;
        }
        return str6 == str5;
    }

    private class IteratorWrapper implements java.util.Iterator<F> {
        private F mCur;
        private final java.util.Iterator<F> mI;

        IteratorWrapper(java.util.Iterator<F> it) {
            this.mI = it;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.mI.hasNext();
        }

        @Override // java.util.Iterator
        public F next() {
            F next = this.mI.next();
            this.mCur = next;
            return next;
        }

        @Override // java.util.Iterator
        public void remove() {
            if (this.mCur != null) {
                com.android.server.IntentResolver.this.removeFilterInternal(this.mCur);
            }
            this.mI.remove();
        }
    }

    public java.util.Iterator<F> filterIterator() {
        return new com.android.server.IntentResolver.IteratorWrapper(this.mFilters.iterator());
    }

    public java.util.Set<F> filterSet() {
        return java.util.Collections.unmodifiableSet(this.mFilters);
    }

    public java.util.List<R> queryIntentFromList(@android.annotation.NonNull com.android.server.pm.Computer computer, android.content.Intent intent, java.lang.String str, boolean z, java.util.ArrayList<F[]> arrayList, int i, long j) {
        if ("android.intent.action.PROCESS_TEXT".equals(intent.getAction()) && android.permission.flags.Flags.ignoreProcessText()) {
            return java.util.Collections.emptyList();
        }
        java.util.ArrayList arrayList2 = new java.util.ArrayList();
        boolean z2 = (intent.getFlags() & 8) != 0;
        android.util.FastImmutableArraySet<java.lang.String> fastIntentCategories = getFastIntentCategories(intent);
        java.lang.String scheme = intent.getScheme();
        int i2 = 0;
        for (int size = arrayList.size(); i2 < size; size = size) {
            buildResolveList(computer, intent, fastIntentCategories, z2, z, str, scheme, arrayList.get(i2), arrayList2, i, j);
            i2++;
        }
        filterResults(arrayList2);
        sortResults(arrayList2);
        return arrayList2;
    }

    public java.util.List<R> queryIntent(@android.annotation.NonNull com.android.server.pm.snapshot.PackageDataSnapshot packageDataSnapshot, android.content.Intent intent, java.lang.String str, boolean z, int i) {
        return queryIntent(packageDataSnapshot, intent, str, z, i, 0L);
    }

    /* JADX WARN: Removed duplicated region for block: B:34:0x018c  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x01b5 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:48:0x01f0  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x0216  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x0232  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x024e  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x0272  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x020f  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x01b1  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    protected final java.util.List<R> queryIntent(@android.annotation.NonNull com.android.server.pm.snapshot.PackageDataSnapshot packageDataSnapshot, android.content.Intent intent, java.lang.String str, boolean z, int i, long j) {
        F[] fArr;
        F[] fArr2;
        F[] fArr3;
        F[] fArr4;
        F[] fArr5;
        java.lang.String str2;
        java.util.ArrayList arrayList;
        int i2;
        int indexOf;
        F[] fArr6;
        if ("android.intent.action.PROCESS_TEXT".equals(intent.getAction()) && android.permission.flags.Flags.ignoreProcessText()) {
            return java.util.Collections.emptyList();
        }
        java.lang.String scheme = intent.getScheme();
        java.util.ArrayList arrayList2 = new java.util.ArrayList();
        boolean z2 = (intent.getFlags() & 8) != 0;
        if (z2) {
            android.util.Slog.v(TAG, "Resolving type=" + str + " scheme=" + scheme + " defaultOnly=" + z + " userId=" + i + " of " + intent);
        }
        if (str != null && (indexOf = str.indexOf(47)) > 0) {
            java.lang.String substring = str.substring(0, indexOf);
            if (!substring.equals(com.android.server.am.SettingsToPropertiesMapper.NAMESPACE_REBOOT_STAGING_DELIMITER)) {
                if (str.length() != indexOf + 2 || str.charAt(indexOf + 1) != '*') {
                    fArr = this.mTypeToFilter.get(str);
                    if (z2) {
                        android.util.Slog.v(TAG, "First type cut: " + java.util.Arrays.toString(fArr));
                    }
                    fArr6 = this.mWildTypeToFilter.get(substring);
                    if (z2) {
                        android.util.Slog.v(TAG, "Second type cut: " + java.util.Arrays.toString(fArr6));
                    }
                } else {
                    fArr = this.mBaseTypeToFilter.get(substring);
                    if (z2) {
                        android.util.Slog.v(TAG, "First type cut: " + java.util.Arrays.toString(fArr));
                    }
                    fArr6 = this.mWildTypeToFilter.get(substring);
                    if (z2) {
                        android.util.Slog.v(TAG, "Second type cut: " + java.util.Arrays.toString(fArr6));
                    }
                }
                F[] fArr7 = this.mWildTypeToFilter.get(com.android.server.am.SettingsToPropertiesMapper.NAMESPACE_REBOOT_STAGING_DELIMITER);
                if (z2) {
                    android.util.Slog.v(TAG, "Third type cut: " + java.util.Arrays.toString(fArr7));
                }
                fArr3 = fArr7;
                fArr2 = fArr6;
                if (scheme == null) {
                    fArr4 = null;
                } else {
                    F[] fArr8 = this.mSchemeToFilter.get(scheme);
                    if (z2) {
                        android.util.Slog.v(TAG, "Scheme list: " + java.util.Arrays.toString(fArr8));
                    }
                    fArr4 = fArr8;
                }
                if (str == null || scheme != null || intent.getAction() == null) {
                    fArr5 = fArr;
                } else {
                    F[] fArr9 = this.mActionToFilter.get(intent.getAction());
                    if (z2) {
                        android.util.Slog.v(TAG, "Action list: " + java.util.Arrays.toString(fArr9));
                    }
                    fArr5 = fArr9;
                }
                android.util.FastImmutableArraySet<java.lang.String> fastIntentCategories = getFastIntentCategories(intent);
                com.android.server.pm.Computer computer = (com.android.server.pm.Computer) packageDataSnapshot;
                if (fArr5 != null) {
                    arrayList = arrayList2;
                    str2 = TAG;
                    i2 = 0;
                    buildResolveList(computer, intent, fastIntentCategories, z2, z, str, scheme, fArr5, arrayList2, i, j);
                } else {
                    str2 = TAG;
                    arrayList = arrayList2;
                    i2 = 0;
                }
                if (fArr2 != null) {
                    buildResolveList(computer, intent, fastIntentCategories, z2, z, str, scheme, fArr2, arrayList, i, j);
                }
                if (fArr3 != null) {
                    buildResolveList(computer, intent, fastIntentCategories, z2, z, str, scheme, fArr3, arrayList, i, j);
                }
                if (fArr4 != null) {
                    buildResolveList(computer, intent, fastIntentCategories, z2, z, str, scheme, fArr4, arrayList, i, j);
                }
                java.util.ArrayList arrayList3 = arrayList;
                filterResults(arrayList3);
                sortResults(arrayList3);
                if (z2) {
                    android.util.Slog.v(str2, "Final result list:");
                    for (int i3 = i2; i3 < arrayList3.size(); i3++) {
                        android.util.Slog.v(str2, "  " + arrayList3.get(i3));
                    }
                }
                return arrayList3;
            }
            if (intent.getAction() != null) {
                fArr = this.mTypedActionToFilter.get(intent.getAction());
                if (z2) {
                    android.util.Slog.v(TAG, "Typed Action list: " + java.util.Arrays.toString(fArr));
                }
                fArr2 = null;
                fArr3 = null;
                if (scheme == null) {
                }
                if (str == null) {
                }
                fArr5 = fArr;
                android.util.FastImmutableArraySet<java.lang.String> fastIntentCategories2 = getFastIntentCategories(intent);
                com.android.server.pm.Computer computer2 = (com.android.server.pm.Computer) packageDataSnapshot;
                if (fArr5 != null) {
                }
                if (fArr2 != null) {
                }
                if (fArr3 != null) {
                }
                if (fArr4 != null) {
                }
                java.util.ArrayList arrayList32 = arrayList;
                filterResults(arrayList32);
                sortResults(arrayList32);
                if (z2) {
                }
                return arrayList32;
            }
        }
        fArr = null;
        fArr2 = null;
        fArr3 = null;
        if (scheme == null) {
        }
        if (str == null) {
        }
        fArr5 = fArr;
        android.util.FastImmutableArraySet<java.lang.String> fastIntentCategories22 = getFastIntentCategories(intent);
        com.android.server.pm.Computer computer22 = (com.android.server.pm.Computer) packageDataSnapshot;
        if (fArr5 != null) {
        }
        if (fArr2 != null) {
        }
        if (fArr3 != null) {
        }
        if (fArr4 != null) {
        }
        java.util.ArrayList arrayList322 = arrayList;
        filterResults(arrayList322);
        sortResults(arrayList322);
        if (z2) {
        }
        return arrayList322;
    }

    protected boolean allowFilterResult(F f, java.util.List<R> list) {
        return true;
    }

    protected boolean isFilterStopped(@android.annotation.NonNull com.android.server.pm.Computer computer, F f, int i) {
        return false;
    }

    protected boolean isFilterVerified(F f) {
        return getIntentFilter(f).isVerified();
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected R newResult(@android.annotation.NonNull com.android.server.pm.Computer computer, F f, int i, int i2, long j) {
        return f;
    }

    protected void sortResults(java.util.List<R> list) {
        java.util.Collections.sort(list, mResolvePrioritySorter);
    }

    protected void filterResults(java.util.List<R> list) {
    }

    protected void dumpFilter(java.io.PrintWriter printWriter, java.lang.String str, F f) {
        printWriter.print(str);
        printWriter.println(f);
    }

    protected java.lang.Object filterToLabel(F f) {
        return "IntentFilter";
    }

    protected void dumpFilterLabel(java.io.PrintWriter printWriter, java.lang.String str, java.lang.Object obj, int i) {
        printWriter.print(str);
        printWriter.print(obj);
        printWriter.print(": ");
        printWriter.println(i);
    }

    private final void addFilter(android.util.ArrayMap<java.lang.String, F[]> arrayMap, java.lang.String str, F f) {
        F[] fArr = arrayMap.get(str);
        if (fArr == null) {
            F[] newArray = newArray(2);
            arrayMap.put(str, newArray);
            newArray[0] = f;
            return;
        }
        int length = fArr.length;
        int i = length;
        while (i > 0 && fArr[i - 1] == null) {
            i--;
        }
        if (i >= length) {
            F[] newArray2 = newArray((length * 3) / 2);
            java.lang.System.arraycopy(fArr, 0, newArray2, 0, length);
            newArray2[length] = f;
            arrayMap.put(str, newArray2);
            return;
        }
        fArr[i] = f;
    }

    private final int register_mime_types(F f, java.lang.String str) {
        java.lang.String str2;
        java.util.Iterator<java.lang.String> typesIterator = getIntentFilter(f).typesIterator();
        if (typesIterator == null) {
            return 0;
        }
        int i = 0;
        while (typesIterator.hasNext()) {
            java.lang.String next = typesIterator.next();
            i++;
            int indexOf = next.indexOf(47);
            if (indexOf > 0) {
                str2 = next.substring(0, indexOf).intern();
            } else {
                str2 = next;
                next = next + "/*";
            }
            addFilter(this.mTypeToFilter, next, f);
            if (indexOf > 0) {
                addFilter(this.mBaseTypeToFilter, str2, f);
            } else {
                addFilter(this.mWildTypeToFilter, str2, f);
            }
        }
        return i;
    }

    private final int unregister_mime_types(F f, java.lang.String str) {
        java.lang.String str2;
        java.util.Iterator<java.lang.String> typesIterator = getIntentFilter(f).typesIterator();
        if (typesIterator == null) {
            return 0;
        }
        int i = 0;
        while (typesIterator.hasNext()) {
            java.lang.String next = typesIterator.next();
            i++;
            int indexOf = next.indexOf(47);
            if (indexOf > 0) {
                str2 = next.substring(0, indexOf).intern();
            } else {
                str2 = next;
                next = next + "/*";
            }
            remove_all_objects(this.mTypeToFilter, next, f);
            if (indexOf > 0) {
                remove_all_objects(this.mBaseTypeToFilter, str2, f);
            } else {
                remove_all_objects(this.mWildTypeToFilter, str2, f);
            }
        }
        return i;
    }

    protected final int register_intent_filter(F f, java.util.Iterator<java.lang.String> it, android.util.ArrayMap<java.lang.String, F[]> arrayMap, java.lang.String str) {
        int i = 0;
        if (it == null) {
            return 0;
        }
        while (it.hasNext()) {
            i++;
            addFilter(arrayMap, it.next(), f);
        }
        return i;
    }

    protected final int unregister_intent_filter(F f, java.util.Iterator<java.lang.String> it, android.util.ArrayMap<java.lang.String, F[]> arrayMap, java.lang.String str) {
        int i = 0;
        if (it == null) {
            return 0;
        }
        while (it.hasNext()) {
            i++;
            remove_all_objects(arrayMap, it.next(), f);
        }
        return i;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final void remove_all_objects(android.util.ArrayMap<java.lang.String, F[]> arrayMap, java.lang.String str, F f) {
        F[] fArr = arrayMap.get(str);
        if (fArr != null) {
            int length = fArr.length - 1;
            while (length >= 0 && fArr[length] == null) {
                length--;
            }
            int i = length;
            while (length >= 0) {
                java.lang.Object obj = fArr[length];
                if (obj != null && getIntentFilter(obj) == getIntentFilter(f)) {
                    int i2 = i - length;
                    if (i2 > 0) {
                        java.lang.System.arraycopy(fArr, length + 1, fArr, length, i2);
                    }
                    fArr[i] = null;
                    i--;
                }
                length--;
            }
            if (i < 0) {
                arrayMap.remove(str);
            } else if (i < fArr.length / 2) {
                F[] newArray = newArray(i + 2);
                java.lang.System.arraycopy(fArr, 0, newArray, 0, i + 1);
                arrayMap.put(str, newArray);
            }
        }
    }

    private static android.util.FastImmutableArraySet<java.lang.String> getFastIntentCategories(android.content.Intent intent) {
        java.util.Set<java.lang.String> categories = intent.getCategories();
        if (categories == null) {
            return null;
        }
        return new android.util.FastImmutableArraySet<>((java.lang.String[]) categories.toArray(new java.lang.String[categories.size()]));
    }

    /* JADX WARN: Code restructure failed: missing block: B:24:0x0086, code lost:
    
        if (isPackageForFilter(r12, r2) != false) goto L29;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0088, code lost:
    
        if (r26 == false) goto L28;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x008a, code lost:
    
        android.util.Slog.v(com.android.server.IntentResolver.TAG, "  Filter is not from package " + r12 + "; skipping");
        r20 = r4;
        r21 = r5;
        r19 = r10;
        r10 = r6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x00ac, code lost:
    
        r20 = r4;
        r21 = r5;
        r19 = r10;
        r10 = r6;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void buildResolveList(@android.annotation.NonNull com.android.server.pm.Computer computer, android.content.Intent intent, android.util.FastImmutableArraySet<java.lang.String> fastImmutableArraySet, boolean z, boolean z2, java.lang.String str, java.lang.String str2, F[] fArr, java.util.List<R> list, int i, long j) {
        com.android.internal.util.FastPrintWriter fastPrintWriter;
        android.util.LogPrinter logPrinter;
        java.lang.String str3;
        int i2;
        int i3;
        com.android.internal.util.FastPrintWriter fastPrintWriter2;
        java.lang.String str4;
        F[] fArr2 = fArr;
        java.lang.String action = intent.getAction();
        android.net.Uri data = intent.getData();
        java.lang.String str5 = intent.getPackage();
        boolean isExcludingStopped = intent.isExcludingStopped();
        if (z) {
            android.util.LogPrinter logPrinter2 = new android.util.LogPrinter(2, TAG, 3);
            logPrinter = logPrinter2;
            fastPrintWriter = new com.android.internal.util.FastPrintWriter(logPrinter2);
        } else {
            fastPrintWriter = null;
            logPrinter = null;
        }
        int length = fArr2 != null ? fArr2.length : 0;
        int i4 = 0;
        boolean z3 = false;
        while (i4 < length) {
            F f = fArr2[i4];
            if (f != null) {
                if (z) {
                    android.util.Slog.v(TAG, "Matching against filter " + f);
                }
                if (isExcludingStopped && isFilterStopped(computer, f, i)) {
                    if (z) {
                        android.util.Slog.v(TAG, "  Filter's target is stopped; skipping");
                        i2 = i4;
                        i3 = length;
                        str3 = action;
                        fastPrintWriter2 = fastPrintWriter;
                    } else {
                        i2 = i4;
                        i3 = length;
                        str3 = action;
                        fastPrintWriter2 = fastPrintWriter;
                    }
                    i4 = i2 + 1;
                    fArr2 = fArr;
                    fastPrintWriter = fastPrintWriter2;
                    action = str3;
                    length = i3;
                }
                android.content.IntentFilter intentFilter = getIntentFilter(f);
                if (intentFilter.getAutoVerify() && z) {
                    android.util.Slog.v(TAG, "  Filter verified: " + isFilterVerified(f));
                    int i5 = 0;
                    for (int countDataAuthorities = intentFilter.countDataAuthorities(); i5 < countDataAuthorities; countDataAuthorities = countDataAuthorities) {
                        android.util.Slog.v(TAG, "   " + intentFilter.getDataAuthority(i5).getHost());
                        i5++;
                    }
                }
                if (!allowFilterResult(f, list)) {
                    if (z) {
                        android.util.Slog.v(TAG, "  Filter's target already added");
                        i2 = i4;
                        i3 = length;
                        str3 = action;
                        fastPrintWriter2 = fastPrintWriter;
                    } else {
                        i2 = i4;
                        i3 = length;
                        str3 = action;
                        fastPrintWriter2 = fastPrintWriter;
                    }
                } else {
                    java.lang.String str6 = action;
                    str3 = action;
                    i2 = i4;
                    i3 = length;
                    fastPrintWriter2 = fastPrintWriter;
                    int match = intentFilter.match(str6, str, str2, data, fastImmutableArraySet, TAG);
                    if (match >= 0) {
                        if (z) {
                            android.util.Slog.v(TAG, "  Filter matched!  match=0x" + java.lang.Integer.toHexString(match) + " hasDefault=" + intentFilter.hasCategory("android.intent.category.DEFAULT"));
                        }
                        if (!z2 || intentFilter.hasCategory("android.intent.category.DEFAULT")) {
                            R newResult = newResult(computer, f, match, i, j);
                            if (z) {
                                android.util.Slog.v(TAG, "    Created result: " + newResult);
                            }
                            if (newResult != null) {
                                list.add(newResult);
                                if (z) {
                                    dumpFilter(fastPrintWriter2, "    ", f);
                                    fastPrintWriter2.flush();
                                    intentFilter.dump(logPrinter, "    ");
                                }
                            }
                        } else {
                            z3 = true;
                        }
                    } else if (z) {
                        switch (match) {
                            case -4:
                                str4 = "category";
                                break;
                            case -3:
                                str4 = com.android.server.pm.verify.domain.DomainVerificationPersistence.ATTR_ACTION;
                                break;
                            case -2:
                                str4 = "data";
                                break;
                            case -1:
                                str4 = com.android.server.voiceinteraction.DatabaseHelper.SoundModelContract.KEY_TYPE;
                                break;
                            default:
                                str4 = "unknown reason";
                                break;
                        }
                        android.util.Slog.v(TAG, "  Filter did not match: " + str4);
                    }
                }
                i4 = i2 + 1;
                fArr2 = fArr;
                fastPrintWriter = fastPrintWriter2;
                action = str3;
                length = i3;
            } else if (!z && z3) {
                if (list.size() == 0) {
                    android.util.Slog.v(TAG, "resolveIntent failed: found match, but none with CATEGORY_DEFAULT");
                    return;
                } else {
                    if (list.size() > 1) {
                        android.util.Slog.v(TAG, "resolveIntent: multiple matches, only some with CATEGORY_DEFAULT");
                        return;
                    }
                    return;
                }
            }
        }
        if (!z) {
        }
    }

    protected F snapshot(F f) {
        return f;
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected void copyInto(android.util.ArrayMap<java.lang.String, F[]> arrayMap, android.util.ArrayMap<java.lang.String, F[]> arrayMap2) {
        int size = arrayMap2.size();
        arrayMap.clear();
        arrayMap.ensureCapacity(size);
        for (int i = 0; i < size; i++) {
            F[] valueAt = arrayMap2.valueAt(i);
            java.lang.String keyAt = arrayMap2.keyAt(i);
            java.lang.Object[] copyOf = java.util.Arrays.copyOf(valueAt, valueAt.length);
            for (int i2 = 0; i2 < copyOf.length; i2++) {
                copyOf[i2] = snapshot(copyOf[i2]);
            }
            arrayMap.put(keyAt, copyOf);
        }
    }

    protected void copyInto(android.util.ArraySet<F> arraySet, android.util.ArraySet<F> arraySet2) {
        arraySet.clear();
        int size = arraySet2.size();
        arraySet.ensureCapacity(size);
        for (int i = 0; i < size; i++) {
            arraySet.append(snapshot(arraySet2.valueAt(i)));
        }
    }

    protected void copyFrom(com.android.server.IntentResolver intentResolver) {
        copyInto(this.mFilters, intentResolver.mFilters);
        copyInto(this.mTypeToFilter, intentResolver.mTypeToFilter);
        copyInto(this.mBaseTypeToFilter, intentResolver.mBaseTypeToFilter);
        copyInto(this.mWildTypeToFilter, intentResolver.mWildTypeToFilter);
        copyInto(this.mSchemeToFilter, intentResolver.mSchemeToFilter);
        copyInto(this.mActionToFilter, intentResolver.mActionToFilter);
        copyInto(this.mTypedActionToFilter, intentResolver.mTypedActionToFilter);
    }
}
