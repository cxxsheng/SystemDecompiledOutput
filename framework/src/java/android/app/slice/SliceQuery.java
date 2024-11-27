package android.app.slice;

/* loaded from: classes.dex */
public class SliceQuery {
    private static final java.lang.String TAG = "SliceQuery";

    public static android.app.slice.SliceItem getPrimaryIcon(android.app.slice.Slice slice) {
        android.app.slice.SliceItem find;
        for (android.app.slice.SliceItem sliceItem : slice.getItems()) {
            if (java.util.Objects.equals(sliceItem.getFormat(), android.app.slice.SliceItem.FORMAT_IMAGE)) {
                return sliceItem;
            }
            if (!compareTypes(sliceItem, "slice") || !sliceItem.hasHint(android.app.slice.Slice.HINT_LIST)) {
                if (!sliceItem.hasHint(android.app.slice.Slice.HINT_ACTIONS) && !sliceItem.hasHint(android.app.slice.Slice.HINT_LIST_ITEM) && !compareTypes(sliceItem, "action") && (find = find(sliceItem, android.app.slice.SliceItem.FORMAT_IMAGE)) != null) {
                    return find;
                }
            }
        }
        return null;
    }

    public static android.app.slice.SliceItem findNotContaining(android.app.slice.SliceItem sliceItem, java.util.List<android.app.slice.SliceItem> list) {
        android.app.slice.SliceItem sliceItem2 = null;
        while (sliceItem2 == null && list.size() != 0) {
            android.app.slice.SliceItem remove = list.remove(0);
            if (!contains(sliceItem, remove)) {
                sliceItem2 = remove;
            }
        }
        return sliceItem2;
    }

    private static boolean contains(android.app.slice.SliceItem sliceItem, final android.app.slice.SliceItem sliceItem2) {
        if (sliceItem == null || sliceItem2 == null) {
            return false;
        }
        return stream(sliceItem).filter(new java.util.function.Predicate() { // from class: android.app.slice.SliceQuery$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                return android.app.slice.SliceQuery.lambda$contains$0(android.app.slice.SliceItem.this, (android.app.slice.SliceItem) obj);
            }
        }).findAny().isPresent();
    }

    static /* synthetic */ boolean lambda$contains$0(android.app.slice.SliceItem sliceItem, android.app.slice.SliceItem sliceItem2) {
        return sliceItem2 == sliceItem;
    }

    public static java.util.List<android.app.slice.SliceItem> findAll(android.app.slice.SliceItem sliceItem, java.lang.String str) {
        return findAll(sliceItem, str, (java.lang.String[]) null, (java.lang.String[]) null);
    }

    public static java.util.List<android.app.slice.SliceItem> findAll(android.app.slice.SliceItem sliceItem, java.lang.String str, java.lang.String str2, java.lang.String str3) {
        return findAll(sliceItem, str, new java.lang.String[]{str2}, new java.lang.String[]{str3});
    }

    public static java.util.List<android.app.slice.SliceItem> findAll(android.app.slice.SliceItem sliceItem, final java.lang.String str, final java.lang.String[] strArr, final java.lang.String[] strArr2) {
        return (java.util.List) stream(sliceItem).filter(new java.util.function.Predicate() { // from class: android.app.slice.SliceQuery$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                return android.app.slice.SliceQuery.lambda$findAll$1(str, strArr, strArr2, (android.app.slice.SliceItem) obj);
            }
        }).collect(java.util.stream.Collectors.toList());
    }

    static /* synthetic */ boolean lambda$findAll$1(java.lang.String str, java.lang.String[] strArr, java.lang.String[] strArr2, android.app.slice.SliceItem sliceItem) {
        return compareTypes(sliceItem, str) && sliceItem.hasHints(strArr) && !sliceItem.hasAnyHints(strArr2);
    }

    public static android.app.slice.SliceItem find(android.app.slice.Slice slice, java.lang.String str, java.lang.String str2, java.lang.String str3) {
        return find(slice, str, new java.lang.String[]{str2}, new java.lang.String[]{str3});
    }

    public static android.app.slice.SliceItem find(android.app.slice.Slice slice, java.lang.String str) {
        return find(slice, str, (java.lang.String[]) null, (java.lang.String[]) null);
    }

    public static android.app.slice.SliceItem find(android.app.slice.SliceItem sliceItem, java.lang.String str) {
        return find(sliceItem, str, (java.lang.String[]) null, (java.lang.String[]) null);
    }

    public static android.app.slice.SliceItem find(android.app.slice.SliceItem sliceItem, java.lang.String str, java.lang.String str2, java.lang.String str3) {
        return find(sliceItem, str, new java.lang.String[]{str2}, new java.lang.String[]{str3});
    }

    public static android.app.slice.SliceItem find(android.app.slice.Slice slice, java.lang.String str, java.lang.String[] strArr, java.lang.String[] strArr2) {
        java.util.List<java.lang.String> hints = slice.getHints();
        return find(new android.app.slice.SliceItem(slice, "slice", (java.lang.String) null, (java.lang.String[]) hints.toArray(new java.lang.String[hints.size()])), str, strArr, strArr2);
    }

    public static android.app.slice.SliceItem find(android.app.slice.SliceItem sliceItem, final java.lang.String str, final java.lang.String[] strArr, final java.lang.String[] strArr2) {
        return stream(sliceItem).filter(new java.util.function.Predicate() { // from class: android.app.slice.SliceQuery$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                return android.app.slice.SliceQuery.lambda$find$2(str, strArr, strArr2, (android.app.slice.SliceItem) obj);
            }
        }).findFirst().orElse(null);
    }

    static /* synthetic */ boolean lambda$find$2(java.lang.String str, java.lang.String[] strArr, java.lang.String[] strArr2, android.app.slice.SliceItem sliceItem) {
        return compareTypes(sliceItem, str) && sliceItem.hasHints(strArr) && !sliceItem.hasAnyHints(strArr2);
    }

    public static java.util.stream.Stream<android.app.slice.SliceItem> stream(android.app.slice.SliceItem sliceItem) {
        final java.util.LinkedList linkedList = new java.util.LinkedList();
        linkedList.add(sliceItem);
        return java.util.stream.StreamSupport.stream(java.util.Spliterators.spliteratorUnknownSize(new java.util.Iterator<android.app.slice.SliceItem>() { // from class: android.app.slice.SliceQuery.1
            @Override // java.util.Iterator
            public boolean hasNext() {
                return linkedList.size() != 0;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // java.util.Iterator
            public android.app.slice.SliceItem next() {
                android.app.slice.SliceItem sliceItem2 = (android.app.slice.SliceItem) linkedList.poll();
                if (android.app.slice.SliceQuery.compareTypes(sliceItem2, "slice") || android.app.slice.SliceQuery.compareTypes(sliceItem2, "action")) {
                    linkedList.addAll(sliceItem2.getSlice().getItems());
                }
                return sliceItem2;
            }
        }, 0), false);
    }

    public static boolean compareTypes(android.app.slice.SliceItem sliceItem, java.lang.String str) {
        if (str.length() == 3 && str.equals("*/*")) {
            return true;
        }
        if (sliceItem.getSubType() == null && str.indexOf(47) < 0) {
            return sliceItem.getFormat().equals(str);
        }
        return (sliceItem.getFormat() + "/" + sliceItem.getSubType()).matches(str.replaceAll("\\*", ".*"));
    }
}
