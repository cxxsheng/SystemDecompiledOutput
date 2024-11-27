package com.android.server.autofill;

/* loaded from: classes.dex */
public final class Helper {
    private static final java.lang.String TAG = "AutofillHelper";
    public static boolean sDebug = false;
    public static boolean sVerbose = false;
    public static java.lang.Boolean sFullScreenMode = null;

    /* JADX INFO: Access modifiers changed from: private */
    interface ViewNodeFilter {
        boolean matches(android.app.assist.AssistStructure.ViewNode viewNode);
    }

    private Helper() {
        throw new java.lang.UnsupportedOperationException("contains static members only");
    }

    private static boolean checkRemoteViewUriPermissions(final int i, @android.annotation.NonNull android.widget.RemoteViews remoteViews) {
        final java.util.concurrent.atomic.AtomicBoolean atomicBoolean = new java.util.concurrent.atomic.AtomicBoolean(true);
        remoteViews.visitUris(new java.util.function.Consumer() { // from class: com.android.server.autofill.Helper$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.autofill.Helper.lambda$checkRemoteViewUriPermissions$0(i, atomicBoolean, (android.net.Uri) obj);
            }
        });
        return atomicBoolean.get();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$checkRemoteViewUriPermissions$0(int i, java.util.concurrent.atomic.AtomicBoolean atomicBoolean, android.net.Uri uri) {
        atomicBoolean.set((android.content.ContentProvider.getUserIdFromUri(uri) == i) & atomicBoolean.get());
    }

    @android.annotation.Nullable
    public static android.widget.RemoteViews sanitizeRemoteView(android.widget.RemoteViews remoteViews) {
        if (remoteViews == null) {
            return null;
        }
        int currentUser = android.app.ActivityManager.getCurrentUser();
        boolean checkRemoteViewUriPermissions = checkRemoteViewUriPermissions(currentUser, remoteViews);
        if (!checkRemoteViewUriPermissions) {
            android.util.Slog.w(TAG, "sanitizeRemoteView() user: " + currentUser + " tried accessing resource that does not belong to them");
        }
        if (checkRemoteViewUriPermissions) {
            return remoteViews;
        }
        return null;
    }

    @android.annotation.Nullable
    static android.view.autofill.AutofillId[] toArray(@android.annotation.Nullable android.util.ArraySet<android.view.autofill.AutofillId> arraySet) {
        if (arraySet == null) {
            return null;
        }
        android.view.autofill.AutofillId[] autofillIdArr = new android.view.autofill.AutofillId[arraySet.size()];
        for (int i = 0; i < arraySet.size(); i++) {
            autofillIdArr[i] = arraySet.valueAt(i);
        }
        return autofillIdArr;
    }

    @android.annotation.NonNull
    public static java.lang.String paramsToString(@android.annotation.NonNull android.view.WindowManager.LayoutParams layoutParams) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder(25);
        layoutParams.dumpDimensions(sb);
        return sb.toString();
    }

    @android.annotation.NonNull
    static android.util.ArrayMap<android.view.autofill.AutofillId, android.view.autofill.AutofillValue> getFields(@android.annotation.NonNull android.service.autofill.Dataset dataset) {
        java.util.ArrayList fieldIds = dataset.getFieldIds();
        java.util.ArrayList fieldValues = dataset.getFieldValues();
        int size = fieldIds == null ? 0 : fieldIds.size();
        android.util.ArrayMap<android.view.autofill.AutofillId, android.view.autofill.AutofillValue> arrayMap = new android.util.ArrayMap<>(size);
        for (int i = 0; i < size; i++) {
            arrayMap.put((android.view.autofill.AutofillId) fieldIds.get(i), (android.view.autofill.AutofillValue) fieldValues.get(i));
        }
        return arrayMap;
    }

    @android.annotation.NonNull
    private static android.metrics.LogMaker newLogMaker(int i, @android.annotation.NonNull java.lang.String str, int i2, boolean z) {
        android.metrics.LogMaker addTaggedData = new android.metrics.LogMaker(i).addTaggedData(908, str).addTaggedData(1456, java.lang.Integer.toString(i2));
        if (z) {
            addTaggedData.addTaggedData(1414, 1);
        }
        return addTaggedData;
    }

    @android.annotation.NonNull
    public static android.metrics.LogMaker newLogMaker(int i, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2, int i2, boolean z) {
        return newLogMaker(i, str2, i2, z).setPackageName(str);
    }

    @android.annotation.NonNull
    public static android.metrics.LogMaker newLogMaker(int i, @android.annotation.NonNull android.content.ComponentName componentName, @android.annotation.NonNull java.lang.String str, int i2, boolean z) {
        return newLogMaker(i, str, i2, z).setComponentName(new android.content.ComponentName(componentName.getPackageName(), ""));
    }

    public static void printlnRedactedText(@android.annotation.NonNull java.io.PrintWriter printWriter, @android.annotation.Nullable java.lang.CharSequence charSequence) {
        if (charSequence == null) {
            printWriter.println("null");
        } else {
            printWriter.print(charSequence.length());
            printWriter.println("_chars");
        }
    }

    @android.annotation.Nullable
    public static android.app.assist.AssistStructure.ViewNode findViewNodeByAutofillId(@android.annotation.NonNull android.app.assist.AssistStructure assistStructure, @android.annotation.NonNull final android.view.autofill.AutofillId autofillId) {
        return findViewNode(assistStructure, new com.android.server.autofill.Helper.ViewNodeFilter() { // from class: com.android.server.autofill.Helper$$ExternalSyntheticLambda0
            @Override // com.android.server.autofill.Helper.ViewNodeFilter
            public final boolean matches(android.app.assist.AssistStructure.ViewNode viewNode) {
                boolean lambda$findViewNodeByAutofillId$1;
                lambda$findViewNodeByAutofillId$1 = com.android.server.autofill.Helper.lambda$findViewNodeByAutofillId$1(autofillId, viewNode);
                return lambda$findViewNodeByAutofillId$1;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$findViewNodeByAutofillId$1(android.view.autofill.AutofillId autofillId, android.app.assist.AssistStructure.ViewNode viewNode) {
        return autofillId.equals(viewNode.getAutofillId());
    }

    private static android.app.assist.AssistStructure.ViewNode findViewNode(@android.annotation.NonNull android.app.assist.AssistStructure assistStructure, @android.annotation.NonNull com.android.server.autofill.Helper.ViewNodeFilter viewNodeFilter) {
        java.util.ArrayDeque arrayDeque = new java.util.ArrayDeque();
        int windowNodeCount = assistStructure.getWindowNodeCount();
        for (int i = 0; i < windowNodeCount; i++) {
            arrayDeque.add(assistStructure.getWindowNodeAt(i).getRootViewNode());
        }
        while (!arrayDeque.isEmpty()) {
            android.app.assist.AssistStructure.ViewNode viewNode = (android.app.assist.AssistStructure.ViewNode) arrayDeque.removeFirst();
            if (viewNodeFilter.matches(viewNode)) {
                return viewNode;
            }
            for (int i2 = 0; i2 < viewNode.getChildCount(); i2++) {
                arrayDeque.addLast(viewNode.getChildAt(i2));
            }
        }
        return null;
    }

    @android.annotation.Nullable
    public static android.app.assist.AssistStructure.ViewNode sanitizeUrlBar(@android.annotation.NonNull android.app.assist.AssistStructure assistStructure, @android.annotation.NonNull final java.lang.String[] strArr) {
        android.app.assist.AssistStructure.ViewNode findViewNode = findViewNode(assistStructure, new com.android.server.autofill.Helper.ViewNodeFilter() { // from class: com.android.server.autofill.Helper$$ExternalSyntheticLambda2
            @Override // com.android.server.autofill.Helper.ViewNodeFilter
            public final boolean matches(android.app.assist.AssistStructure.ViewNode viewNode) {
                boolean lambda$sanitizeUrlBar$2;
                lambda$sanitizeUrlBar$2 = com.android.server.autofill.Helper.lambda$sanitizeUrlBar$2(strArr, viewNode);
                return lambda$sanitizeUrlBar$2;
            }
        });
        if (findViewNode != null) {
            java.lang.String charSequence = findViewNode.getText().toString();
            if (charSequence.isEmpty()) {
                if (sDebug) {
                    android.util.Slog.d(TAG, "sanitizeUrlBar(): empty on " + findViewNode.getIdEntry());
                    return null;
                }
                return null;
            }
            findViewNode.setWebDomain(charSequence);
            if (sDebug) {
                android.util.Slog.d(TAG, "sanitizeUrlBar(): id=" + findViewNode.getIdEntry() + ", domain=" + findViewNode.getWebDomain());
            }
        }
        return findViewNode;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$sanitizeUrlBar$2(java.lang.String[] strArr, android.app.assist.AssistStructure.ViewNode viewNode) {
        return com.android.internal.util.ArrayUtils.contains(strArr, viewNode.getIdEntry());
    }

    static int getNumericValue(@android.annotation.NonNull android.metrics.LogMaker logMaker, int i) {
        java.lang.Object taggedData = logMaker.getTaggedData(i);
        if (!(taggedData instanceof java.lang.Number)) {
            return 0;
        }
        return ((java.lang.Number) taggedData).intValue();
    }

    @android.annotation.NonNull
    static java.util.ArrayList<android.view.autofill.AutofillId> getAutofillIds(@android.annotation.NonNull android.app.assist.AssistStructure assistStructure, boolean z) {
        java.util.ArrayList<android.view.autofill.AutofillId> arrayList = new java.util.ArrayList<>();
        int windowNodeCount = assistStructure.getWindowNodeCount();
        for (int i = 0; i < windowNodeCount; i++) {
            addAutofillableIds(assistStructure.getWindowNodeAt(i).getRootViewNode(), arrayList, z);
        }
        return arrayList;
    }

    private static void addAutofillableIds(@android.annotation.NonNull android.app.assist.AssistStructure.ViewNode viewNode, @android.annotation.NonNull java.util.ArrayList<android.view.autofill.AutofillId> arrayList, boolean z) {
        if (!z || viewNode.getAutofillType() != 0) {
            arrayList.add(viewNode.getAutofillId());
        }
        int childCount = viewNode.getChildCount();
        for (int i = 0; i < childCount; i++) {
            addAutofillableIds(viewNode.getChildAt(i), arrayList, z);
        }
    }

    @android.annotation.Nullable
    static android.util.ArrayMap<android.view.autofill.AutofillId, android.service.autofill.InternalSanitizer> createSanitizers(@android.annotation.Nullable android.service.autofill.SaveInfo saveInfo) {
        android.service.autofill.InternalSanitizer[] sanitizerKeys;
        if (saveInfo == null || (sanitizerKeys = saveInfo.getSanitizerKeys()) == null) {
            return null;
        }
        int length = sanitizerKeys.length;
        android.util.ArrayMap<android.view.autofill.AutofillId, android.service.autofill.InternalSanitizer> arrayMap = new android.util.ArrayMap<>(length);
        if (sDebug) {
            android.util.Slog.d(TAG, "Service provided " + length + " sanitizers");
        }
        android.view.autofill.AutofillId[][] sanitizerValues = saveInfo.getSanitizerValues();
        for (int i = 0; i < length; i++) {
            android.service.autofill.InternalSanitizer internalSanitizer = sanitizerKeys[i];
            android.view.autofill.AutofillId[] autofillIdArr = sanitizerValues[i];
            if (sDebug) {
                android.util.Slog.d(TAG, "sanitizer #" + i + " (" + internalSanitizer + ") for ids " + java.util.Arrays.toString(autofillIdArr));
            }
            for (android.view.autofill.AutofillId autofillId : autofillIdArr) {
                arrayMap.put(autofillId, internalSanitizer);
            }
        }
        return arrayMap;
    }

    static boolean containsCharsInOrder(java.lang.String str, java.lang.String str2) {
        int i = -1;
        for (char c : str2.toCharArray()) {
            i = android.text.TextUtils.indexOf(str, c, i + 1);
            if (i == -1) {
                return false;
            }
        }
        return true;
    }

    static android.content.Context getDisplayContext(android.content.Context context, int i) {
        if (!android.os.UserManager.isVisibleBackgroundUsersEnabled()) {
            return context;
        }
        if (context.getDisplayId() == i) {
            if (sDebug) {
                com.android.server.utils.Slogf.d(TAG, "getDisplayContext(): context %s already has displayId %d", context, java.lang.Integer.valueOf(i));
            }
            return context;
        }
        if (sDebug) {
            com.android.server.utils.Slogf.d(TAG, "Creating context for display %d", java.lang.Integer.valueOf(i));
        }
        android.view.Display display = ((android.hardware.display.DisplayManager) context.getSystemService(android.hardware.display.DisplayManager.class)).getDisplay(i);
        if (display == null) {
            com.android.server.utils.Slogf.wtf(TAG, "Could not get context with displayId %d, Autofill operations will probably fail)", java.lang.Integer.valueOf(i));
            return context;
        }
        return context.createDisplayContext(display);
    }

    @android.annotation.Nullable
    static <T> T weakDeref(java.lang.ref.WeakReference<T> weakReference, java.lang.String str, java.lang.String str2) {
        T t = weakReference.get();
        if (t == null) {
            android.util.Slog.wtf(str, str2 + "fail to deref " + weakReference);
        }
        return t;
    }
}
