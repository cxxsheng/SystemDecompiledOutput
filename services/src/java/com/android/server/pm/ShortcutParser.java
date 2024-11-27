package com.android.server.pm;

/* loaded from: classes2.dex */
public class ShortcutParser {
    private static final boolean DEBUG = false;

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String METADATA_KEY = "android.app.shortcuts";
    private static final java.lang.String TAG = "ShortcutService";
    private static final java.lang.String TAG_CATEGORIES = "categories";
    private static final java.lang.String TAG_CATEGORY = "category";
    private static final java.lang.String TAG_DATA = "data";
    private static final java.lang.String TAG_INTENT = "intent";
    private static final java.lang.String TAG_SHARE_TARGET = "share-target";
    private static final java.lang.String TAG_SHORTCUT = "shortcut";
    private static final java.lang.String TAG_SHORTCUTS = "shortcuts";

    @android.annotation.Nullable
    public static java.util.List<android.content.pm.ShortcutInfo> parseShortcuts(com.android.server.pm.ShortcutService shortcutService, java.lang.String str, int i, @android.annotation.NonNull java.util.List<com.android.server.pm.ShareTargetInfo> list) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        android.content.pm.ActivityInfo activityInfoWithMetadata;
        java.util.List<android.content.pm.ResolveInfo> injectGetMainActivities = shortcutService.injectGetMainActivities(str, i);
        if (injectGetMainActivities == null || injectGetMainActivities.size() == 0) {
            return null;
        }
        list.clear();
        try {
            int size = injectGetMainActivities.size();
            java.util.List<android.content.pm.ShortcutInfo> list2 = null;
            for (int i2 = 0; i2 < size; i2++) {
                android.content.pm.ActivityInfo activityInfo = injectGetMainActivities.get(i2).activityInfo;
                if (activityInfo != null && (activityInfoWithMetadata = shortcutService.getActivityInfoWithMetadata(activityInfo.getComponentName(), i)) != null) {
                    list2 = parseShortcutsOneFile(shortcutService, activityInfoWithMetadata, str, i, list2, list);
                }
            }
            return list2;
        } catch (java.lang.RuntimeException e) {
            shortcutService.wtf("Exception caught while parsing shortcut XML for package=" + str, e);
            return null;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x03d0, code lost:
    
        r10.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x03d4, code lost:
    
        return r12;
     */
    /* JADX WARN: Removed duplicated region for block: B:168:0x01c2 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:47:0x01cc  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static java.util.List<android.content.pm.ShortcutInfo> parseShortcutsOneFile(com.android.server.pm.ShortcutService shortcutService, android.content.pm.ActivityInfo activityInfo, java.lang.String str, int i, java.util.List<android.content.pm.ShortcutInfo> list, @android.annotation.NonNull java.util.List<com.android.server.pm.ShareTargetInfo> list2) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        android.content.res.XmlResourceParser xmlResourceParser;
        android.content.res.XmlResourceParser injectXmlMetaData;
        android.content.ComponentName componentName;
        android.util.AttributeSet asAttributeSet;
        int maxActivityShortcuts;
        java.util.ArrayList arrayList;
        java.util.ArrayList arrayList2;
        java.util.List<android.content.pm.ShortcutInfo> list3;
        android.content.pm.ShortcutInfo shortcutInfo;
        com.android.server.pm.ShareTargetInfo shareTargetInfo;
        android.util.ArraySet arraySet;
        int i2;
        int i3;
        java.util.List<android.content.pm.ShortcutInfo> list4;
        int i4;
        java.util.List<android.content.pm.ShortcutInfo> list5;
        int i5;
        com.android.server.pm.ShareTargetInfo shareTargetInfo2;
        android.util.ArraySet arraySet2;
        com.android.server.pm.ShortcutService shortcutService2;
        java.util.ArrayList arrayList3;
        android.content.ComponentName componentName2;
        java.util.List<android.content.pm.ShortcutInfo> list6;
        java.util.List<android.content.pm.ShortcutInfo> list7;
        int i6;
        try {
            injectXmlMetaData = shortcutService.injectXmlMetaData(activityInfo, METADATA_KEY);
        } catch (java.lang.Throwable th) {
            th = th;
            xmlResourceParser = null;
        }
        if (injectXmlMetaData == null) {
            if (injectXmlMetaData != null) {
                injectXmlMetaData.close();
            }
            return list;
        }
        try {
            componentName = new android.content.ComponentName(str, activityInfo.name);
            asAttributeSet = android.util.Xml.asAttributeSet(injectXmlMetaData);
            maxActivityShortcuts = shortcutService.getMaxActivityShortcuts();
            arrayList = new java.util.ArrayList();
            arrayList2 = new java.util.ArrayList();
            list3 = list;
            shortcutInfo = null;
            shareTargetInfo = null;
            arraySet = null;
            i2 = 0;
            i3 = 0;
        } catch (java.lang.Throwable th2) {
            th = th2;
            xmlResourceParser = injectXmlMetaData;
        }
        while (true) {
            int next = injectXmlMetaData.next();
            if (next == 1) {
                list4 = list3;
                break;
            }
            if (next == 3 && injectXmlMetaData.getDepth() <= 0) {
                list4 = list3;
                break;
            }
            int depth = injectXmlMetaData.getDepth();
            java.lang.String name = injectXmlMetaData.getName();
            android.content.ComponentName componentName3 = componentName;
            if (next != 3 || depth != 2 || !TAG_SHORTCUT.equals(name)) {
                i4 = i2;
                if (next != 3 || depth != 2) {
                    list5 = list3;
                    i5 = maxActivityShortcuts;
                } else if (!TAG_SHARE_TARGET.equals(name)) {
                    list5 = list3;
                    i5 = maxActivityShortcuts;
                } else if (shareTargetInfo == null) {
                    componentName = componentName3;
                    i2 = i4;
                } else {
                    if (arraySet == null || arraySet.isEmpty()) {
                        list7 = list3;
                        i6 = maxActivityShortcuts;
                    } else if (!arrayList2.isEmpty()) {
                        list5 = list3;
                        i5 = maxActivityShortcuts;
                        list2.add(new com.android.server.pm.ShareTargetInfo((com.android.server.pm.ShareTargetInfo.TargetData[]) arrayList2.toArray(new com.android.server.pm.ShareTargetInfo.TargetData[arrayList2.size()]), shareTargetInfo.mTargetClass, (java.lang.String[]) arraySet.toArray(new java.lang.String[arraySet.size()])));
                        arrayList2.clear();
                        shareTargetInfo2 = null;
                        arraySet2 = null;
                        if (next == 2) {
                            shortcutService2 = shortcutService;
                            arrayList3 = arrayList2;
                            componentName2 = componentName3;
                            list6 = list5;
                        } else if (depth == 1 && TAG_SHORTCUTS.equals(name)) {
                            shortcutService2 = shortcutService;
                            arrayList3 = arrayList2;
                            componentName2 = componentName3;
                            list6 = list5;
                        } else if (depth == 2 && TAG_SHORTCUT.equals(name)) {
                            list6 = list5;
                            java.util.ArrayList arrayList4 = arrayList2;
                            android.content.pm.ShortcutInfo parseShortcutAttributes = parseShortcutAttributes(shortcutService, asAttributeSet, str, componentName3, i, i3);
                            if (parseShortcutAttributes == null) {
                                shortcutService2 = shortcutService;
                                arrayList3 = arrayList4;
                                componentName2 = componentName3;
                            } else {
                                if (list6 != null) {
                                    for (int size = list6.size() - 1; size >= 0; size--) {
                                        if (parseShortcutAttributes.getId().equals(list6.get(size).getId())) {
                                            android.util.Log.e(TAG, "Duplicate shortcut ID detected. Skipping it.");
                                            shortcutService2 = shortcutService;
                                            arrayList3 = arrayList4;
                                            componentName2 = componentName3;
                                        }
                                    }
                                }
                                shortcutInfo = parseShortcutAttributes;
                                arrayList2 = arrayList4;
                                list3 = list6;
                                componentName = componentName3;
                                i2 = i4;
                                maxActivityShortcuts = i5;
                                shareTargetInfo = shareTargetInfo2;
                                arraySet = null;
                            }
                        } else {
                            arrayList3 = arrayList2;
                            list6 = list5;
                            if (depth == 2 && TAG_SHARE_TARGET.equals(name)) {
                                shortcutService2 = shortcutService;
                                com.android.server.pm.ShareTargetInfo parseShareTargetAttributes = parseShareTargetAttributes(shortcutService2, asAttributeSet);
                                if (parseShareTargetAttributes == null) {
                                    componentName2 = componentName3;
                                } else {
                                    arrayList3.clear();
                                    arrayList2 = arrayList3;
                                    shareTargetInfo = parseShareTargetAttributes;
                                    list3 = list6;
                                    componentName = componentName3;
                                    i2 = i4;
                                    maxActivityShortcuts = i5;
                                    arraySet = null;
                                }
                            } else {
                                shortcutService2 = shortcutService;
                                if (depth == 3 && TAG_INTENT.equals(name)) {
                                    if (shortcutInfo == null) {
                                        componentName2 = componentName3;
                                    } else if (shortcutInfo.isEnabled()) {
                                        android.content.Intent parseIntent = android.content.Intent.parseIntent(shortcutService2.mContext.getResources(), injectXmlMetaData, asAttributeSet);
                                        if (android.text.TextUtils.isEmpty(parseIntent.getAction())) {
                                            android.util.Log.e(TAG, "Shortcut intent action must be provided. activity=" + componentName3);
                                            arrayList2 = arrayList3;
                                            componentName = componentName3;
                                            list3 = list6;
                                            i2 = i4;
                                            maxActivityShortcuts = i5;
                                            shareTargetInfo = shareTargetInfo2;
                                            arraySet = arraySet2;
                                            shortcutInfo = null;
                                        } else {
                                            componentName2 = componentName3;
                                            arrayList.add(parseIntent);
                                        }
                                    } else {
                                        componentName2 = componentName3;
                                    }
                                    android.util.Log.e(TAG, "Ignoring excessive intent tag.");
                                } else {
                                    componentName2 = componentName3;
                                    if (depth == 3 && TAG_CATEGORIES.equals(name)) {
                                        if (shortcutInfo != null && shortcutInfo.getCategories() == null) {
                                            java.lang.String parseCategories = parseCategories(shortcutService2, asAttributeSet);
                                            if (android.text.TextUtils.isEmpty(parseCategories)) {
                                                android.util.Log.e(TAG, "Empty category found. activity=" + componentName2);
                                            } else {
                                                android.util.ArraySet arraySet3 = arraySet2 == null ? new android.util.ArraySet() : arraySet2;
                                                arraySet3.add(parseCategories);
                                                arrayList2 = arrayList3;
                                                arraySet = arraySet3;
                                                componentName = componentName2;
                                                list3 = list6;
                                                i2 = i4;
                                                maxActivityShortcuts = i5;
                                                shareTargetInfo = shareTargetInfo2;
                                            }
                                        }
                                    } else if (depth == 3 && TAG_CATEGORY.equals(name)) {
                                        if (shareTargetInfo2 != null) {
                                            java.lang.String parseCategory = parseCategory(shortcutService2, asAttributeSet);
                                            if (android.text.TextUtils.isEmpty(parseCategory)) {
                                                android.util.Log.e(TAG, "Empty category found. activity=" + componentName2);
                                            } else {
                                                android.util.ArraySet arraySet4 = arraySet2 == null ? new android.util.ArraySet() : arraySet2;
                                                arraySet4.add(parseCategory);
                                                arrayList2 = arrayList3;
                                                arraySet = arraySet4;
                                                componentName = componentName2;
                                                list3 = list6;
                                                i2 = i4;
                                                maxActivityShortcuts = i5;
                                                shareTargetInfo = shareTargetInfo2;
                                            }
                                        }
                                    } else if (depth != 3 || !"data".equals(name)) {
                                        android.util.Log.w(TAG, java.lang.String.format("Invalid tag '%s' found at depth %d", name, java.lang.Integer.valueOf(depth)));
                                    } else if (shareTargetInfo2 != null) {
                                        com.android.server.pm.ShareTargetInfo.TargetData parseShareTargetData = parseShareTargetData(shortcutService2, asAttributeSet);
                                        if (parseShareTargetData == null) {
                                            android.util.Log.e(TAG, "Invalid data tag found. activity=" + componentName2);
                                        } else {
                                            arrayList3.add(parseShareTargetData);
                                        }
                                    }
                                }
                            }
                        }
                        arrayList2 = arrayList3;
                        componentName = componentName2;
                        list3 = list6;
                        i2 = i4;
                        maxActivityShortcuts = i5;
                        shareTargetInfo = shareTargetInfo2;
                        arraySet = arraySet2;
                        break;
                    } else {
                        list7 = list3;
                        i6 = maxActivityShortcuts;
                    }
                    componentName = componentName3;
                    i2 = i4;
                    list3 = list7;
                    maxActivityShortcuts = i6;
                    shareTargetInfo = null;
                }
                shareTargetInfo2 = shareTargetInfo;
                arraySet2 = arraySet;
                if (next == 2) {
                }
                arrayList2 = arrayList3;
                componentName = componentName2;
                list3 = list6;
                i2 = i4;
                maxActivityShortcuts = i5;
                shareTargetInfo = shareTargetInfo2;
                arraySet = arraySet2;
                break;
                break;
            } else {
                if (shortcutInfo != null) {
                    if (!shortcutInfo.isEnabled()) {
                        arrayList.clear();
                        arrayList.add(new android.content.Intent("android.intent.action.VIEW"));
                    } else if (arrayList.size() == 0) {
                        android.util.Log.e(TAG, "Shortcut " + shortcutInfo.getId() + " has no intent. Skipping it.");
                        componentName = componentName3;
                        shortcutInfo = null;
                    }
                    if (i2 >= maxActivityShortcuts) {
                        android.util.Log.e(TAG, "More than " + maxActivityShortcuts + " shortcuts found for " + activityInfo.getComponentName() + ". Skipping the rest.");
                        injectXmlMetaData.close();
                        return list3;
                    }
                    ((android.content.Intent) arrayList.get(0)).addFlags(268484608);
                    try {
                        shortcutInfo.setIntents((android.content.Intent[]) arrayList.toArray(new android.content.Intent[arrayList.size()]));
                        arrayList.clear();
                        if (arraySet != null) {
                            shortcutInfo.setCategories(arraySet);
                            arraySet = null;
                        }
                        if (list3 == null) {
                            list3 = new java.util.ArrayList<>();
                        }
                        list3.add(shortcutInfo);
                        i2++;
                        i3++;
                        componentName = componentName3;
                        shortcutInfo = null;
                    } catch (java.lang.RuntimeException e) {
                        android.util.Log.e(TAG, "Shortcut's extras contain un-persistable values. Skipping it.");
                    }
                    th = th2;
                    xmlResourceParser = injectXmlMetaData;
                    if (xmlResourceParser != null) {
                        xmlResourceParser.close();
                    }
                    throw th;
                }
                i4 = i2;
                componentName = componentName3;
                i2 = i4;
            }
        }
    }

    private static java.lang.String parseCategories(com.android.server.pm.ShortcutService shortcutService, android.util.AttributeSet attributeSet) {
        android.content.res.TypedArray obtainAttributes = shortcutService.mContext.getResources().obtainAttributes(attributeSet, com.android.internal.R.styleable.ShortcutCategories);
        try {
            if (obtainAttributes.getType(0) == 3) {
                return obtainAttributes.getNonResourceString(0);
            }
            android.util.Log.w(TAG, "android:name for shortcut category must be string literal.");
            obtainAttributes.recycle();
            return null;
        } finally {
            obtainAttributes.recycle();
        }
    }

    private static android.content.pm.ShortcutInfo parseShortcutAttributes(com.android.server.pm.ShortcutService shortcutService, android.util.AttributeSet attributeSet, java.lang.String str, android.content.ComponentName componentName, int i, int i2) {
        android.content.res.TypedArray obtainAttributes = shortcutService.mContext.getResources().obtainAttributes(attributeSet, com.android.internal.R.styleable.Shortcut);
        try {
            if (obtainAttributes.getType(2) != 3) {
                android.util.Log.w(TAG, "android:shortcutId must be string literal. activity=" + componentName);
                return null;
            }
            java.lang.String nonResourceString = obtainAttributes.getNonResourceString(2);
            boolean z = obtainAttributes.getBoolean(1, true);
            int resourceId = obtainAttributes.getResourceId(0, 0);
            int resourceId2 = obtainAttributes.getResourceId(3, 0);
            int resourceId3 = obtainAttributes.getResourceId(4, 0);
            int resourceId4 = obtainAttributes.getResourceId(5, 0);
            int resourceId5 = obtainAttributes.getResourceId(6, 0);
            java.lang.String resourceName = resourceId5 != 0 ? shortcutService.mContext.getResources().getResourceName(resourceId5) : null;
            if (android.text.TextUtils.isEmpty(nonResourceString)) {
                android.util.Log.w(TAG, "android:shortcutId must be provided. activity=" + componentName);
                return null;
            }
            if (resourceId2 != 0) {
                return createShortcutFromManifest(shortcutService, i, nonResourceString, str, componentName, resourceId2, resourceId3, resourceId4, i2, resourceId, z, resourceName);
            }
            android.util.Log.w(TAG, "android:shortcutShortLabel must be provided. activity=" + componentName);
            return null;
        } finally {
            obtainAttributes.recycle();
        }
    }

    private static android.content.pm.ShortcutInfo createShortcutFromManifest(com.android.server.pm.ShortcutService shortcutService, int i, java.lang.String str, java.lang.String str2, android.content.ComponentName componentName, int i2, int i3, int i4, int i5, int i6, boolean z, @android.annotation.Nullable java.lang.String str3) {
        int i7;
        int i8 = (z ? 32 : 64) | 256 | (i6 != 0 ? 4 : 0);
        if (z) {
            i7 = 0;
        } else {
            i7 = 1;
        }
        return new android.content.pm.ShortcutInfo(i, str, str2, componentName, null, null, i2, null, null, i3, null, null, i4, null, null, null, i5, null, shortcutService.injectCurrentTimeMillis(), i8, i6, null, null, null, i7, null, null, str3, null);
    }

    private static java.lang.String parseCategory(com.android.server.pm.ShortcutService shortcutService, android.util.AttributeSet attributeSet) {
        android.content.res.TypedArray obtainAttributes = shortcutService.mContext.getResources().obtainAttributes(attributeSet, com.android.internal.R.styleable.IntentCategory);
        try {
            if (obtainAttributes.getType(0) == 3) {
                return obtainAttributes.getString(0);
            }
            android.util.Log.w(TAG, "android:name must be string literal.");
            obtainAttributes.recycle();
            return null;
        } finally {
            obtainAttributes.recycle();
        }
    }

    private static com.android.server.pm.ShareTargetInfo parseShareTargetAttributes(com.android.server.pm.ShortcutService shortcutService, android.util.AttributeSet attributeSet) {
        android.content.res.TypedArray obtainAttributes = shortcutService.mContext.getResources().obtainAttributes(attributeSet, com.android.internal.R.styleable.Intent);
        try {
            java.lang.String string = obtainAttributes.getString(4);
            if (!android.text.TextUtils.isEmpty(string)) {
                return new com.android.server.pm.ShareTargetInfo(null, string, null);
            }
            android.util.Log.w(TAG, "android:targetClass must be provided.");
            return null;
        } finally {
            obtainAttributes.recycle();
        }
    }

    private static com.android.server.pm.ShareTargetInfo.TargetData parseShareTargetData(com.android.server.pm.ShortcutService shortcutService, android.util.AttributeSet attributeSet) {
        android.content.res.TypedArray obtainAttributes = shortcutService.mContext.getResources().obtainAttributes(attributeSet, com.android.internal.R.styleable.AndroidManifestData);
        try {
            if (obtainAttributes.getType(0) == 3) {
                return new com.android.server.pm.ShareTargetInfo.TargetData(obtainAttributes.getString(1), obtainAttributes.getString(2), obtainAttributes.getString(3), obtainAttributes.getString(4), obtainAttributes.getString(6), obtainAttributes.getString(5), obtainAttributes.getString(0));
            }
            android.util.Log.w(TAG, "android:mimeType must be string literal.");
            obtainAttributes.recycle();
            return null;
        } finally {
            obtainAttributes.recycle();
        }
    }
}
