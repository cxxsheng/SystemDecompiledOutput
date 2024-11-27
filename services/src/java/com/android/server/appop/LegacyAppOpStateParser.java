package com.android.server.appop;

/* loaded from: classes.dex */
class LegacyAppOpStateParser {
    private static final int NO_FILE_VERSION = -2;
    private static final int NO_VERSION = -1;
    static final java.lang.String TAG = com.android.server.appop.LegacyAppOpStateParser.class.getSimpleName();

    LegacyAppOpStateParser() {
    }

    /* JADX WARN: Code restructure failed: missing block: B:27:0x003a, code lost:
    
        if (r5 != 4) goto L69;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x003d, code lost:
    
        r5 = r1.getName();
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x0048, code lost:
    
        if (r5.equals("pkg") == false) goto L70;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x0055, code lost:
    
        if (r5.equals(com.android.server.net.watchlist.WatchlistLoggingHandler.WatchlistEventKeys.UID) == false) goto L73;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x0062, code lost:
    
        if (r5.equals("user") == false) goto L74;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x0068, code lost:
    
        android.util.Slog.w(com.android.server.appop.LegacyAppOpStateParser.TAG, "Unknown element under <app-ops>: " + r1.getName());
        com.android.internal.util.XmlUtils.skipCurrentTag(r1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x0064, code lost:
    
        readUser(r1, r11);
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x0057, code lost:
    
        readUidOps(r1, r10);
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x004a, code lost:
    
        readPackage(r1, r11);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public int readState(android.util.AtomicFile atomicFile, android.util.SparseArray<android.util.SparseIntArray> sparseArray, android.util.SparseArray<android.util.ArrayMap<java.lang.String, android.util.SparseIntArray>> sparseArray2) {
        int next;
        try {
            java.io.FileInputStream openRead = atomicFile.openRead();
            try {
                com.android.modules.utils.TypedXmlPullParser resolvePullParser = android.util.Xml.resolvePullParser(openRead);
                do {
                    next = resolvePullParser.next();
                    if (next == 2) {
                        break;
                    }
                } while (next != 1);
                if (next != 2) {
                    throw new java.lang.IllegalStateException("no start tag found");
                }
                int attributeInt = resolvePullParser.getAttributeInt((java.lang.String) null, "v", -1);
                int depth = resolvePullParser.getDepth();
                while (true) {
                    int next2 = resolvePullParser.next();
                    if (next2 == 1 || (next2 == 3 && resolvePullParser.getDepth() <= depth)) {
                        break;
                    }
                }
                if (openRead != null) {
                    openRead.close();
                }
                return attributeInt;
            } catch (java.lang.Throwable th) {
                if (openRead != null) {
                    try {
                        openRead.close();
                    } catch (java.lang.Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        } catch (java.io.FileNotFoundException e) {
            android.util.Slog.i(TAG, "No existing app ops " + atomicFile.getBaseFile() + "; starting empty");
            return -2;
        } catch (java.io.IOException e2) {
            throw new java.lang.RuntimeException(e2);
        } catch (org.xmlpull.v1.XmlPullParserException e3) {
            throw new java.lang.RuntimeException(e3);
        }
    }

    private void readPackage(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, android.util.SparseArray<android.util.ArrayMap<java.lang.String, android.util.SparseIntArray>> sparseArray) throws java.lang.NumberFormatException, org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        java.lang.String attributeValue = typedXmlPullParser.getAttributeValue((java.lang.String) null, "n");
        int depth = typedXmlPullParser.getDepth();
        while (true) {
            int next = typedXmlPullParser.next();
            if (next != 1) {
                if (next == 3 && typedXmlPullParser.getDepth() <= depth) {
                    return;
                }
                if (next != 3 && next != 4) {
                    if (typedXmlPullParser.getName().equals(com.android.server.net.watchlist.WatchlistLoggingHandler.WatchlistEventKeys.UID)) {
                        readPackageUid(typedXmlPullParser, attributeValue, sparseArray);
                    } else {
                        android.util.Slog.w(TAG, "Unknown element under <pkg>: " + typedXmlPullParser.getName());
                        com.android.internal.util.XmlUtils.skipCurrentTag(typedXmlPullParser);
                    }
                }
            } else {
                return;
            }
        }
    }

    private void readPackageUid(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, java.lang.String str, android.util.SparseArray<android.util.ArrayMap<java.lang.String, android.util.SparseIntArray>> sparseArray) throws java.lang.NumberFormatException, org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        int userId = android.os.UserHandle.getUserId(typedXmlPullParser.getAttributeInt((java.lang.String) null, "n"));
        int depth = typedXmlPullParser.getDepth();
        while (true) {
            int next = typedXmlPullParser.next();
            if (next != 1) {
                if (next == 3 && typedXmlPullParser.getDepth() <= depth) {
                    return;
                }
                if (next != 3 && next != 4) {
                    if (typedXmlPullParser.getName().equals("op")) {
                        readOp(typedXmlPullParser, userId, str, sparseArray);
                    } else {
                        android.util.Slog.w(TAG, "Unknown element under <pkg>: " + typedXmlPullParser.getName());
                        com.android.internal.util.XmlUtils.skipCurrentTag(typedXmlPullParser);
                    }
                }
            } else {
                return;
            }
        }
    }

    private void readUidOps(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, android.util.SparseArray<android.util.SparseIntArray> sparseArray) throws java.lang.NumberFormatException, org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        int attributeInt = typedXmlPullParser.getAttributeInt((java.lang.String) null, "n");
        android.util.SparseIntArray sparseIntArray = sparseArray.get(attributeInt);
        if (sparseIntArray == null) {
            sparseIntArray = new android.util.SparseIntArray();
            sparseArray.put(attributeInt, sparseIntArray);
        }
        int depth = typedXmlPullParser.getDepth();
        while (true) {
            int next = typedXmlPullParser.next();
            if (next != 1) {
                if (next == 3 && typedXmlPullParser.getDepth() <= depth) {
                    return;
                }
                if (next != 3 && next != 4) {
                    if (typedXmlPullParser.getName().equals("op")) {
                        int attributeInt2 = typedXmlPullParser.getAttributeInt((java.lang.String) null, "n");
                        int attributeInt3 = typedXmlPullParser.getAttributeInt((java.lang.String) null, "m");
                        if (attributeInt3 != android.app.AppOpsManager.opToDefaultMode(attributeInt2)) {
                            sparseIntArray.put(attributeInt2, attributeInt3);
                        }
                    } else {
                        android.util.Slog.w(TAG, "Unknown element under <uid>: " + typedXmlPullParser.getName());
                        com.android.internal.util.XmlUtils.skipCurrentTag(typedXmlPullParser);
                    }
                }
            } else {
                return;
            }
        }
    }

    private void readUser(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, android.util.SparseArray<android.util.ArrayMap<java.lang.String, android.util.SparseIntArray>> sparseArray) throws java.lang.NumberFormatException, org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        int attributeInt = typedXmlPullParser.getAttributeInt((java.lang.String) null, "n");
        int depth = typedXmlPullParser.getDepth();
        while (true) {
            int next = typedXmlPullParser.next();
            if (next != 1) {
                if (next == 3 && typedXmlPullParser.getDepth() <= depth) {
                    return;
                }
                if (next != 3 && next != 4) {
                    if (typedXmlPullParser.getName().equals("pkg")) {
                        readPackageOp(typedXmlPullParser, attributeInt, sparseArray);
                    } else {
                        android.util.Slog.w(TAG, "Unknown element under <user>: " + typedXmlPullParser.getName());
                        com.android.internal.util.XmlUtils.skipCurrentTag(typedXmlPullParser);
                    }
                }
            } else {
                return;
            }
        }
    }

    private void readPackageOp(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, int i, android.util.SparseArray<android.util.ArrayMap<java.lang.String, android.util.SparseIntArray>> sparseArray) throws java.lang.NumberFormatException, org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        java.lang.String attributeValue = typedXmlPullParser.getAttributeValue((java.lang.String) null, "n");
        int depth = typedXmlPullParser.getDepth();
        while (true) {
            int next = typedXmlPullParser.next();
            if (next != 1) {
                if (next == 3 && typedXmlPullParser.getDepth() <= depth) {
                    return;
                }
                if (next != 3 && next != 4) {
                    if (typedXmlPullParser.getName().equals("op")) {
                        readOp(typedXmlPullParser, i, attributeValue, sparseArray);
                    } else {
                        android.util.Slog.w(TAG, "Unknown element under <pkg>: " + typedXmlPullParser.getName());
                        com.android.internal.util.XmlUtils.skipCurrentTag(typedXmlPullParser);
                    }
                }
            } else {
                return;
            }
        }
    }

    private void readOp(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, int i, @android.annotation.NonNull java.lang.String str, android.util.SparseArray<android.util.ArrayMap<java.lang.String, android.util.SparseIntArray>> sparseArray) throws java.lang.NumberFormatException, org.xmlpull.v1.XmlPullParserException {
        int attributeInt = typedXmlPullParser.getAttributeInt((java.lang.String) null, "n");
        int opToDefaultMode = android.app.AppOpsManager.opToDefaultMode(attributeInt);
        int attributeInt2 = typedXmlPullParser.getAttributeInt((java.lang.String) null, "m", opToDefaultMode);
        if (attributeInt2 != opToDefaultMode) {
            android.util.ArrayMap<java.lang.String, android.util.SparseIntArray> arrayMap = sparseArray.get(i);
            if (arrayMap == null) {
                arrayMap = new android.util.ArrayMap<>();
                sparseArray.put(i, arrayMap);
            }
            android.util.SparseIntArray sparseIntArray = arrayMap.get(str);
            if (sparseIntArray == null) {
                sparseIntArray = new android.util.SparseIntArray();
                arrayMap.put(str, sparseIntArray);
            }
            sparseIntArray.put(attributeInt, attributeInt2);
        }
    }
}
