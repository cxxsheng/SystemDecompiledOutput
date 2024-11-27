package com.android.server.policy;

/* loaded from: classes2.dex */
final class GlobalKeyManager {
    private static final java.lang.String ATTR_COMPONENT = "component";
    private static final java.lang.String ATTR_DISPATCH_WHEN_NON_INTERACTIVE = "dispatchWhenNonInteractive";
    private static final java.lang.String ATTR_KEY_CODE = "keyCode";
    private static final java.lang.String ATTR_VERSION = "version";
    private static final int GLOBAL_KEY_FILE_VERSION = 1;
    private static final java.lang.String TAG = "GlobalKeyManager";
    private static final java.lang.String TAG_GLOBAL_KEYS = "global_keys";
    private static final java.lang.String TAG_KEY = "key";
    private final android.util.SparseArray<com.android.server.policy.GlobalKeyManager.GlobalKeyAction> mKeyMapping = new android.util.SparseArray<>();
    private boolean mBeganFromNonInteractive = false;

    public GlobalKeyManager(android.content.Context context) {
        loadGlobalKeys(context);
    }

    boolean handleGlobalKey(android.content.Context context, int i, android.view.KeyEvent keyEvent) {
        com.android.server.policy.GlobalKeyManager.GlobalKeyAction globalKeyAction;
        if (this.mKeyMapping.size() <= 0 || (globalKeyAction = this.mKeyMapping.get(i)) == null) {
            return false;
        }
        context.sendBroadcastAsUser(new com.android.server.policy.GlobalKeyIntent(globalKeyAction.mComponentName, keyEvent, this.mBeganFromNonInteractive).getIntent(), android.os.UserHandle.CURRENT, null);
        if (keyEvent.getAction() == 1) {
            this.mBeganFromNonInteractive = false;
        }
        return true;
    }

    boolean shouldHandleGlobalKey(int i) {
        return this.mKeyMapping.get(i) != null;
    }

    boolean shouldDispatchFromNonInteractive(int i) {
        com.android.server.policy.GlobalKeyManager.GlobalKeyAction globalKeyAction = this.mKeyMapping.get(i);
        if (globalKeyAction == null) {
            return false;
        }
        return globalKeyAction.mDispatchWhenNonInteractive;
    }

    void setBeganFromNonInteractive() {
        this.mBeganFromNonInteractive = true;
    }

    class GlobalKeyAction {
        private final android.content.ComponentName mComponentName;
        private final boolean mDispatchWhenNonInteractive;

        GlobalKeyAction(java.lang.String str, java.lang.String str2) {
            this.mComponentName = android.content.ComponentName.unflattenFromString(str);
            this.mDispatchWhenNonInteractive = java.lang.Boolean.parseBoolean(str2);
        }
    }

    private void loadGlobalKeys(android.content.Context context) {
        try {
            android.content.res.XmlResourceParser xml = context.getResources().getXml(android.R.xml.global_keys);
            try {
                com.android.internal.util.XmlUtils.beginDocument(xml, TAG_GLOBAL_KEYS);
                if (1 == xml.getAttributeIntValue(null, ATTR_VERSION, 0)) {
                    while (true) {
                        com.android.internal.util.XmlUtils.nextElement(xml);
                        java.lang.String name = xml.getName();
                        if (name == null) {
                            break;
                        }
                        if (TAG_KEY.equals(name)) {
                            java.lang.String attributeValue = xml.getAttributeValue(null, ATTR_KEY_CODE);
                            java.lang.String attributeValue2 = xml.getAttributeValue(null, ATTR_COMPONENT);
                            java.lang.String attributeValue3 = xml.getAttributeValue(null, ATTR_DISPATCH_WHEN_NON_INTERACTIVE);
                            if (attributeValue == null || attributeValue2 == null) {
                                android.util.Log.wtf(TAG, "Failed to parse global keys entry: " + xml.getText());
                            } else {
                                int keyCodeFromString = android.view.KeyEvent.keyCodeFromString(attributeValue);
                                if (keyCodeFromString != 0) {
                                    this.mKeyMapping.put(keyCodeFromString, new com.android.server.policy.GlobalKeyManager.GlobalKeyAction(attributeValue2, attributeValue3));
                                } else {
                                    android.util.Log.wtf(TAG, "Global keys entry does not map to a valid key code: " + attributeValue);
                                }
                            }
                        }
                    }
                }
                xml.close();
            } finally {
            }
        } catch (android.content.res.Resources.NotFoundException e) {
            android.util.Log.wtf(TAG, "global keys file not found", e);
        } catch (java.io.IOException e2) {
            android.util.Log.e(TAG, "I/O exception reading global keys file", e2);
        } catch (org.xmlpull.v1.XmlPullParserException e3) {
            android.util.Log.wtf(TAG, "XML parser exception reading global keys file", e3);
        }
    }

    public void dump(java.lang.String str, java.io.PrintWriter printWriter) {
        int size = this.mKeyMapping.size();
        if (size == 0) {
            printWriter.print(str);
            printWriter.println("mKeyMapping.size=0");
            return;
        }
        printWriter.print(str);
        printWriter.println("mKeyMapping={");
        for (int i = 0; i < size; i++) {
            printWriter.print("  ");
            printWriter.print(str);
            printWriter.print(android.view.KeyEvent.keyCodeToString(this.mKeyMapping.keyAt(i)));
            printWriter.print("=");
            printWriter.print(this.mKeyMapping.valueAt(i).mComponentName.flattenToString());
            printWriter.print(",dispatchWhenNonInteractive=");
            printWriter.println(this.mKeyMapping.valueAt(i).mDispatchWhenNonInteractive);
        }
        printWriter.print(str);
        printWriter.println("}");
    }
}
