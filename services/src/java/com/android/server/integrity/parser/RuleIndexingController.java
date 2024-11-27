package com.android.server.integrity.parser;

/* loaded from: classes2.dex */
public class RuleIndexingController {
    private static java.util.LinkedHashMap<java.lang.String, java.lang.Integer> sAppCertificateBasedIndexes;
    private static java.util.LinkedHashMap<java.lang.String, java.lang.Integer> sPackageNameBasedIndexes;
    private static java.util.LinkedHashMap<java.lang.String, java.lang.Integer> sUnindexedRuleIndexes;

    public RuleIndexingController(java.io.InputStream inputStream) throws java.io.IOException {
        com.android.server.integrity.model.BitInputStream bitInputStream = new com.android.server.integrity.model.BitInputStream(inputStream);
        sPackageNameBasedIndexes = getNextIndexGroup(bitInputStream);
        sAppCertificateBasedIndexes = getNextIndexGroup(bitInputStream);
        sUnindexedRuleIndexes = getNextIndexGroup(bitInputStream);
    }

    public java.util.List<com.android.server.integrity.parser.RuleIndexRange> identifyRulesToEvaluate(android.content.integrity.AppInstallMetadata appInstallMetadata) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        arrayList.add(searchIndexingKeysRangeContainingKey(sPackageNameBasedIndexes, appInstallMetadata.getPackageName()));
        java.util.Iterator it = appInstallMetadata.getAppCertificates().iterator();
        while (it.hasNext()) {
            arrayList.add(searchIndexingKeysRangeContainingKey(sAppCertificateBasedIndexes, (java.lang.String) it.next()));
        }
        arrayList.add(new com.android.server.integrity.parser.RuleIndexRange(sUnindexedRuleIndexes.get(com.android.server.integrity.model.IndexingFileConstants.START_INDEXING_KEY).intValue(), sUnindexedRuleIndexes.get(com.android.server.integrity.model.IndexingFileConstants.END_INDEXING_KEY).intValue()));
        return arrayList;
    }

    private java.util.LinkedHashMap<java.lang.String, java.lang.Integer> getNextIndexGroup(com.android.server.integrity.model.BitInputStream bitInputStream) throws java.io.IOException {
        java.util.LinkedHashMap<java.lang.String, java.lang.Integer> linkedHashMap = new java.util.LinkedHashMap<>();
        while (bitInputStream.hasNext()) {
            java.lang.String stringValue = com.android.server.integrity.parser.BinaryFileOperations.getStringValue(bitInputStream);
            linkedHashMap.put(stringValue, java.lang.Integer.valueOf(com.android.server.integrity.parser.BinaryFileOperations.getIntValue(bitInputStream)));
            if (stringValue.matches(com.android.server.integrity.model.IndexingFileConstants.END_INDEXING_KEY)) {
                break;
            }
        }
        if (linkedHashMap.size() < 2) {
            throw new java.lang.IllegalStateException("Indexing file is corrupt.");
        }
        return linkedHashMap;
    }

    private static com.android.server.integrity.parser.RuleIndexRange searchIndexingKeysRangeContainingKey(java.util.LinkedHashMap<java.lang.String, java.lang.Integer> linkedHashMap, java.lang.String str) {
        java.util.List list = (java.util.List) linkedHashMap.keySet().stream().collect(java.util.stream.Collectors.toList());
        java.util.List<java.lang.String> searchKeysRangeContainingKey = searchKeysRangeContainingKey(list, str, 0, list.size() - 1);
        return new com.android.server.integrity.parser.RuleIndexRange(linkedHashMap.get(searchKeysRangeContainingKey.get(0)).intValue(), linkedHashMap.get(searchKeysRangeContainingKey.get(1)).intValue());
    }

    private static java.util.List<java.lang.String> searchKeysRangeContainingKey(java.util.List<java.lang.String> list, java.lang.String str, int i, int i2) {
        if (i2 <= i) {
            throw new java.lang.IllegalStateException("Indexing file is corrupt.");
        }
        int i3 = i2 - i;
        if (i3 == 1) {
            return java.util.Arrays.asList(list.get(i), list.get(i2));
        }
        int i4 = (i3 / 2) + i;
        if (str.compareTo(list.get(i4)) >= 0) {
            return searchKeysRangeContainingKey(list, str, i4, i2);
        }
        return searchKeysRangeContainingKey(list, str, i, i4);
    }
}
