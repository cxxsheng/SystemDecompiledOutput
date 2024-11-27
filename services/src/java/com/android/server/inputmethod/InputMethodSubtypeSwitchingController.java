package com.android.server.inputmethod;

/* loaded from: classes2.dex */
final class InputMethodSubtypeSwitchingController {
    private static final boolean DEBUG = false;
    private static final int NOT_A_SUBTYPE_ID = -1;
    private static final java.lang.String TAG = com.android.server.inputmethod.InputMethodSubtypeSwitchingController.class.getSimpleName();
    private final android.content.Context mContext;
    private com.android.server.inputmethod.InputMethodSubtypeSwitchingController.ControllerImpl mController;
    private final int mUserId;

    public static class ImeSubtypeListItem implements java.lang.Comparable<com.android.server.inputmethod.InputMethodSubtypeSwitchingController.ImeSubtypeListItem> {
        public final java.lang.CharSequence mImeName;
        public final android.view.inputmethod.InputMethodInfo mImi;
        public final boolean mIsSystemLanguage;
        public final boolean mIsSystemLocale;
        public final int mSubtypeId;
        public final java.lang.CharSequence mSubtypeName;

        ImeSubtypeListItem(java.lang.CharSequence charSequence, java.lang.CharSequence charSequence2, android.view.inputmethod.InputMethodInfo inputMethodInfo, int i, java.lang.String str, java.lang.String str2) {
            this.mImeName = charSequence;
            this.mSubtypeName = charSequence2;
            this.mImi = inputMethodInfo;
            this.mSubtypeId = i;
            boolean z = false;
            if (android.text.TextUtils.isEmpty(str)) {
                this.mIsSystemLocale = false;
                this.mIsSystemLanguage = false;
                return;
            }
            this.mIsSystemLocale = str.equals(str2);
            if (this.mIsSystemLocale) {
                this.mIsSystemLanguage = true;
                return;
            }
            java.lang.String languageFromLocaleString = com.android.server.inputmethod.LocaleUtils.getLanguageFromLocaleString(str2);
            java.lang.String languageFromLocaleString2 = com.android.server.inputmethod.LocaleUtils.getLanguageFromLocaleString(str);
            if (languageFromLocaleString.length() >= 2 && languageFromLocaleString.equals(languageFromLocaleString2)) {
                z = true;
            }
            this.mIsSystemLanguage = z;
        }

        private static int compareNullableCharSequences(@android.annotation.Nullable java.lang.CharSequence charSequence, @android.annotation.Nullable java.lang.CharSequence charSequence2) {
            boolean isEmpty = android.text.TextUtils.isEmpty(charSequence);
            boolean isEmpty2 = android.text.TextUtils.isEmpty(charSequence2);
            if (isEmpty || isEmpty2) {
                return (isEmpty ? 1 : 0) - (isEmpty2 ? 1 : 0);
            }
            return charSequence.toString().compareTo(charSequence2.toString());
        }

        @Override // java.lang.Comparable
        public int compareTo(com.android.server.inputmethod.InputMethodSubtypeSwitchingController.ImeSubtypeListItem imeSubtypeListItem) {
            int compareNullableCharSequences = compareNullableCharSequences(this.mImeName, imeSubtypeListItem.mImeName);
            if (compareNullableCharSequences != 0) {
                return compareNullableCharSequences;
            }
            int i = (this.mIsSystemLocale ? -1 : 0) - (imeSubtypeListItem.mIsSystemLocale ? -1 : 0);
            if (i != 0) {
                return i;
            }
            int i2 = (this.mIsSystemLanguage ? -1 : 0) - (imeSubtypeListItem.mIsSystemLanguage ? -1 : 0);
            if (i2 != 0) {
                return i2;
            }
            int compareNullableCharSequences2 = compareNullableCharSequences(this.mSubtypeName, imeSubtypeListItem.mSubtypeName);
            if (compareNullableCharSequences2 != 0) {
                return compareNullableCharSequences2;
            }
            return this.mImi.getId().compareTo(imeSubtypeListItem.mImi.getId());
        }

        public java.lang.String toString() {
            return "ImeSubtypeListItem{mImeName=" + ((java.lang.Object) this.mImeName) + " mSubtypeName=" + ((java.lang.Object) this.mSubtypeName) + " mSubtypeId=" + this.mSubtypeId + " mIsSystemLocale=" + this.mIsSystemLocale + " mIsSystemLanguage=" + this.mIsSystemLanguage + "}";
        }

        public boolean equals(java.lang.Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof com.android.server.inputmethod.InputMethodSubtypeSwitchingController.ImeSubtypeListItem)) {
                return false;
            }
            com.android.server.inputmethod.InputMethodSubtypeSwitchingController.ImeSubtypeListItem imeSubtypeListItem = (com.android.server.inputmethod.InputMethodSubtypeSwitchingController.ImeSubtypeListItem) obj;
            return java.util.Objects.equals(this.mImi, imeSubtypeListItem.mImi) && this.mSubtypeId == imeSubtypeListItem.mSubtypeId;
        }
    }

    static java.util.List<com.android.server.inputmethod.InputMethodSubtypeSwitchingController.ImeSubtypeListItem> getSortedInputMethodAndSubtypeList(boolean z, boolean z2, boolean z3, @android.annotation.NonNull android.content.Context context, @android.annotation.NonNull com.android.server.inputmethod.InputMethodMap inputMethodMap, int i) {
        android.content.Context createContextAsUser;
        boolean z4;
        android.content.Context context2;
        com.android.server.inputmethod.InputMethodSettings inputMethodSettings;
        java.util.ArrayList<android.view.inputmethod.InputMethodInfo> arrayList;
        android.content.Context context3;
        com.android.server.inputmethod.InputMethodSettings inputMethodSettings2;
        int i2;
        int i3;
        android.view.inputmethod.InputMethodInfo inputMethodInfo;
        java.util.ArrayList<android.view.inputmethod.InputMethodInfo> arrayList2;
        android.util.ArraySet arraySet;
        int i4 = 0;
        if (context.getUserId() == i) {
            createContextAsUser = context;
        } else {
            createContextAsUser = context.createContextAsUser(android.os.UserHandle.of(i), 0);
        }
        java.lang.String languageTag = com.android.server.inputmethod.SystemLocaleWrapper.get(i).get(0).toLanguageTag();
        com.android.server.inputmethod.InputMethodSettings create = com.android.server.inputmethod.InputMethodSettings.create(inputMethodMap, i);
        java.util.ArrayList<android.view.inputmethod.InputMethodInfo> enabledInputMethodList = create.getEnabledInputMethodList();
        if (enabledInputMethodList.isEmpty()) {
            return new java.util.ArrayList();
        }
        if (z2 && z) {
            z4 = false;
        } else {
            z4 = z;
        }
        java.util.ArrayList arrayList3 = new java.util.ArrayList();
        int size = enabledInputMethodList.size();
        int i5 = 0;
        while (i5 < size) {
            android.view.inputmethod.InputMethodInfo inputMethodInfo2 = enabledInputMethodList.get(i5);
            if (z3 && !inputMethodInfo2.shouldShowInInputMethodPicker()) {
                context2 = createContextAsUser;
                inputMethodSettings = create;
                arrayList = enabledInputMethodList;
            } else {
                java.util.List<android.view.inputmethod.InputMethodSubtype> enabledInputMethodSubtypeList = create.getEnabledInputMethodSubtypeList(inputMethodInfo2, true);
                android.util.ArraySet arraySet2 = new android.util.ArraySet();
                java.util.Iterator<android.view.inputmethod.InputMethodSubtype> it = enabledInputMethodSubtypeList.iterator();
                while (it.hasNext()) {
                    arraySet2.add(java.lang.String.valueOf(it.next().hashCode()));
                }
                java.lang.CharSequence loadLabel = inputMethodInfo2.loadLabel(createContextAsUser.getPackageManager());
                if (arraySet2.size() > 0) {
                    int subtypeCount = inputMethodInfo2.getSubtypeCount();
                    int i6 = i4;
                    while (i6 < subtypeCount) {
                        android.view.inputmethod.InputMethodSubtype subtypeAt = inputMethodInfo2.getSubtypeAt(i6);
                        java.lang.String valueOf = java.lang.String.valueOf(subtypeAt.hashCode());
                        if (!arraySet2.contains(valueOf)) {
                            context3 = createContextAsUser;
                            inputMethodSettings2 = create;
                            i2 = i6;
                            i3 = subtypeCount;
                            inputMethodInfo = inputMethodInfo2;
                            arrayList2 = enabledInputMethodList;
                            arraySet = arraySet2;
                        } else if (z4 || !subtypeAt.isAuxiliary()) {
                            context3 = createContextAsUser;
                            inputMethodSettings2 = create;
                            i2 = i6;
                            i3 = subtypeCount;
                            arrayList2 = enabledInputMethodList;
                            arraySet = arraySet2;
                            inputMethodInfo = inputMethodInfo2;
                            arrayList3.add(new com.android.server.inputmethod.InputMethodSubtypeSwitchingController.ImeSubtypeListItem(loadLabel, subtypeAt.overridesImplicitlyEnabledSubtype() ? null : subtypeAt.getDisplayName(createContextAsUser, inputMethodInfo2.getPackageName(), inputMethodInfo2.getServiceInfo().applicationInfo), inputMethodInfo2, i2, subtypeAt.getLocale(), languageTag));
                            arraySet.remove(valueOf);
                        } else {
                            context3 = createContextAsUser;
                            inputMethodSettings2 = create;
                            i2 = i6;
                            i3 = subtypeCount;
                            inputMethodInfo = inputMethodInfo2;
                            arrayList2 = enabledInputMethodList;
                            arraySet = arraySet2;
                        }
                        i6 = i2 + 1;
                        createContextAsUser = context3;
                        create = inputMethodSettings2;
                        arraySet2 = arraySet;
                        inputMethodInfo2 = inputMethodInfo;
                        subtypeCount = i3;
                        enabledInputMethodList = arrayList2;
                    }
                    context2 = createContextAsUser;
                    inputMethodSettings = create;
                    arrayList = enabledInputMethodList;
                } else {
                    context2 = createContextAsUser;
                    inputMethodSettings = create;
                    arrayList = enabledInputMethodList;
                    arrayList3.add(new com.android.server.inputmethod.InputMethodSubtypeSwitchingController.ImeSubtypeListItem(loadLabel, null, inputMethodInfo2, -1, null, languageTag));
                }
            }
            i5++;
            createContextAsUser = context2;
            create = inputMethodSettings;
            enabledInputMethodList = arrayList;
            i4 = 0;
        }
        java.util.Collections.sort(arrayList3);
        return arrayList3;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int calculateSubtypeId(android.view.inputmethod.InputMethodInfo inputMethodInfo, android.view.inputmethod.InputMethodSubtype inputMethodSubtype) {
        if (inputMethodSubtype != null) {
            return com.android.server.inputmethod.SubtypeUtils.getSubtypeIdFromHashCode(inputMethodInfo, inputMethodSubtype.hashCode());
        }
        return -1;
    }

    private static class StaticRotationList {
        private final java.util.List<com.android.server.inputmethod.InputMethodSubtypeSwitchingController.ImeSubtypeListItem> mImeSubtypeList;

        StaticRotationList(java.util.List<com.android.server.inputmethod.InputMethodSubtypeSwitchingController.ImeSubtypeListItem> list) {
            this.mImeSubtypeList = list;
        }

        private int getIndex(android.view.inputmethod.InputMethodInfo inputMethodInfo, android.view.inputmethod.InputMethodSubtype inputMethodSubtype) {
            int calculateSubtypeId = com.android.server.inputmethod.InputMethodSubtypeSwitchingController.calculateSubtypeId(inputMethodInfo, inputMethodSubtype);
            int size = this.mImeSubtypeList.size();
            for (int i = 0; i < size; i++) {
                com.android.server.inputmethod.InputMethodSubtypeSwitchingController.ImeSubtypeListItem imeSubtypeListItem = this.mImeSubtypeList.get(i);
                if (inputMethodInfo.equals(imeSubtypeListItem.mImi) && imeSubtypeListItem.mSubtypeId == calculateSubtypeId) {
                    return i;
                }
            }
            return -1;
        }

        public com.android.server.inputmethod.InputMethodSubtypeSwitchingController.ImeSubtypeListItem getNextInputMethodLocked(boolean z, android.view.inputmethod.InputMethodInfo inputMethodInfo, android.view.inputmethod.InputMethodSubtype inputMethodSubtype) {
            int index;
            if (inputMethodInfo == null) {
                return null;
            }
            if (this.mImeSubtypeList.size() <= 1 || (index = getIndex(inputMethodInfo, inputMethodSubtype)) < 0) {
                return null;
            }
            int size = this.mImeSubtypeList.size();
            for (int i = 1; i < size; i++) {
                com.android.server.inputmethod.InputMethodSubtypeSwitchingController.ImeSubtypeListItem imeSubtypeListItem = this.mImeSubtypeList.get((index + i) % size);
                if (!z || inputMethodInfo.equals(imeSubtypeListItem.mImi)) {
                    return imeSubtypeListItem;
                }
            }
            return null;
        }

        protected void dump(android.util.Printer printer, java.lang.String str) {
            int size = this.mImeSubtypeList.size();
            for (int i = 0; i < size; i++) {
                printer.println(str + "rank=" + i + " item=" + this.mImeSubtypeList.get(i));
            }
        }
    }

    private static class DynamicRotationList {
        private static final java.lang.String TAG = com.android.server.inputmethod.InputMethodSubtypeSwitchingController.DynamicRotationList.class.getSimpleName();
        private final java.util.List<com.android.server.inputmethod.InputMethodSubtypeSwitchingController.ImeSubtypeListItem> mImeSubtypeList;
        private final int[] mUsageHistoryOfSubtypeListItemIndex;

        private DynamicRotationList(java.util.List<com.android.server.inputmethod.InputMethodSubtypeSwitchingController.ImeSubtypeListItem> list) {
            this.mImeSubtypeList = list;
            this.mUsageHistoryOfSubtypeListItemIndex = new int[this.mImeSubtypeList.size()];
            int size = this.mImeSubtypeList.size();
            for (int i = 0; i < size; i++) {
                this.mUsageHistoryOfSubtypeListItemIndex[i] = i;
            }
        }

        private int getUsageRank(android.view.inputmethod.InputMethodInfo inputMethodInfo, android.view.inputmethod.InputMethodSubtype inputMethodSubtype) {
            int calculateSubtypeId = com.android.server.inputmethod.InputMethodSubtypeSwitchingController.calculateSubtypeId(inputMethodInfo, inputMethodSubtype);
            int length = this.mUsageHistoryOfSubtypeListItemIndex.length;
            for (int i = 0; i < length; i++) {
                com.android.server.inputmethod.InputMethodSubtypeSwitchingController.ImeSubtypeListItem imeSubtypeListItem = this.mImeSubtypeList.get(this.mUsageHistoryOfSubtypeListItemIndex[i]);
                if (imeSubtypeListItem.mImi.equals(inputMethodInfo) && imeSubtypeListItem.mSubtypeId == calculateSubtypeId) {
                    return i;
                }
            }
            return -1;
        }

        public void onUserAction(android.view.inputmethod.InputMethodInfo inputMethodInfo, android.view.inputmethod.InputMethodSubtype inputMethodSubtype) {
            int usageRank = getUsageRank(inputMethodInfo, inputMethodSubtype);
            if (usageRank <= 0) {
                return;
            }
            int i = this.mUsageHistoryOfSubtypeListItemIndex[usageRank];
            java.lang.System.arraycopy(this.mUsageHistoryOfSubtypeListItemIndex, 0, this.mUsageHistoryOfSubtypeListItemIndex, 1, usageRank);
            this.mUsageHistoryOfSubtypeListItemIndex[0] = i;
        }

        public com.android.server.inputmethod.InputMethodSubtypeSwitchingController.ImeSubtypeListItem getNextInputMethodLocked(boolean z, android.view.inputmethod.InputMethodInfo inputMethodInfo, android.view.inputmethod.InputMethodSubtype inputMethodSubtype) {
            int usageRank = getUsageRank(inputMethodInfo, inputMethodSubtype);
            if (usageRank < 0) {
                return null;
            }
            int length = this.mUsageHistoryOfSubtypeListItemIndex.length;
            for (int i = 1; i < length; i++) {
                com.android.server.inputmethod.InputMethodSubtypeSwitchingController.ImeSubtypeListItem imeSubtypeListItem = this.mImeSubtypeList.get(this.mUsageHistoryOfSubtypeListItemIndex[(usageRank + i) % length]);
                if (!z || inputMethodInfo.equals(imeSubtypeListItem.mImi)) {
                    return imeSubtypeListItem;
                }
            }
            return null;
        }

        protected void dump(android.util.Printer printer, java.lang.String str) {
            for (int i = 0; i < this.mUsageHistoryOfSubtypeListItemIndex.length; i++) {
                printer.println(str + "rank=" + this.mUsageHistoryOfSubtypeListItemIndex[i] + " item=" + this.mImeSubtypeList.get(i));
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    public static class ControllerImpl {
        private final com.android.server.inputmethod.InputMethodSubtypeSwitchingController.DynamicRotationList mSwitchingAwareRotationList;
        private final com.android.server.inputmethod.InputMethodSubtypeSwitchingController.StaticRotationList mSwitchingUnawareRotationList;

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r1v5 */
        /* JADX WARN: Type inference failed for: r1v6 */
        /* JADX WARN: Type inference failed for: r1v7 */
        public static com.android.server.inputmethod.InputMethodSubtypeSwitchingController.ControllerImpl createFrom(com.android.server.inputmethod.InputMethodSubtypeSwitchingController.ControllerImpl controllerImpl, java.util.List<com.android.server.inputmethod.InputMethodSubtypeSwitchingController.ImeSubtypeListItem> list) {
            com.android.server.inputmethod.InputMethodSubtypeSwitchingController.DynamicRotationList dynamicRotationList;
            java.util.List<com.android.server.inputmethod.InputMethodSubtypeSwitchingController.ImeSubtypeListItem> filterImeSubtypeList = filterImeSubtypeList(list, true);
            com.android.server.inputmethod.InputMethodSubtypeSwitchingController.StaticRotationList staticRotationList = 0;
            staticRotationList = 0;
            staticRotationList = 0;
            if (controllerImpl != null && controllerImpl.mSwitchingAwareRotationList != null && java.util.Objects.equals(controllerImpl.mSwitchingAwareRotationList.mImeSubtypeList, filterImeSubtypeList)) {
                dynamicRotationList = controllerImpl.mSwitchingAwareRotationList;
            } else {
                dynamicRotationList = null;
            }
            if (dynamicRotationList == null) {
                dynamicRotationList = new com.android.server.inputmethod.InputMethodSubtypeSwitchingController.DynamicRotationList(filterImeSubtypeList);
            }
            java.util.List<com.android.server.inputmethod.InputMethodSubtypeSwitchingController.ImeSubtypeListItem> filterImeSubtypeList2 = filterImeSubtypeList(list, false);
            if (controllerImpl != null && controllerImpl.mSwitchingUnawareRotationList != null && java.util.Objects.equals(controllerImpl.mSwitchingUnawareRotationList.mImeSubtypeList, filterImeSubtypeList2)) {
                staticRotationList = controllerImpl.mSwitchingUnawareRotationList;
            }
            if (staticRotationList == 0) {
                staticRotationList = new com.android.server.inputmethod.InputMethodSubtypeSwitchingController.StaticRotationList(filterImeSubtypeList2);
            }
            return new com.android.server.inputmethod.InputMethodSubtypeSwitchingController.ControllerImpl(dynamicRotationList, staticRotationList);
        }

        private ControllerImpl(com.android.server.inputmethod.InputMethodSubtypeSwitchingController.DynamicRotationList dynamicRotationList, com.android.server.inputmethod.InputMethodSubtypeSwitchingController.StaticRotationList staticRotationList) {
            this.mSwitchingAwareRotationList = dynamicRotationList;
            this.mSwitchingUnawareRotationList = staticRotationList;
        }

        public com.android.server.inputmethod.InputMethodSubtypeSwitchingController.ImeSubtypeListItem getNextInputMethod(boolean z, android.view.inputmethod.InputMethodInfo inputMethodInfo, android.view.inputmethod.InputMethodSubtype inputMethodSubtype) {
            if (inputMethodInfo == null) {
                return null;
            }
            if (inputMethodInfo.supportsSwitchingToNextInputMethod()) {
                return this.mSwitchingAwareRotationList.getNextInputMethodLocked(z, inputMethodInfo, inputMethodSubtype);
            }
            return this.mSwitchingUnawareRotationList.getNextInputMethodLocked(z, inputMethodInfo, inputMethodSubtype);
        }

        public void onUserActionLocked(android.view.inputmethod.InputMethodInfo inputMethodInfo, android.view.inputmethod.InputMethodSubtype inputMethodSubtype) {
            if (inputMethodInfo != null && inputMethodInfo.supportsSwitchingToNextInputMethod()) {
                this.mSwitchingAwareRotationList.onUserAction(inputMethodInfo, inputMethodSubtype);
            }
        }

        private static java.util.List<com.android.server.inputmethod.InputMethodSubtypeSwitchingController.ImeSubtypeListItem> filterImeSubtypeList(java.util.List<com.android.server.inputmethod.InputMethodSubtypeSwitchingController.ImeSubtypeListItem> list, boolean z) {
            java.util.ArrayList arrayList = new java.util.ArrayList();
            int size = list.size();
            for (int i = 0; i < size; i++) {
                com.android.server.inputmethod.InputMethodSubtypeSwitchingController.ImeSubtypeListItem imeSubtypeListItem = list.get(i);
                if (imeSubtypeListItem.mImi.supportsSwitchingToNextInputMethod() == z) {
                    arrayList.add(imeSubtypeListItem);
                }
            }
            return arrayList;
        }

        protected void dump(android.util.Printer printer) {
            printer.println("    mSwitchingAwareRotationList:");
            this.mSwitchingAwareRotationList.dump(printer, "      ");
            printer.println("    mSwitchingUnawareRotationList:");
            this.mSwitchingUnawareRotationList.dump(printer, "      ");
        }
    }

    private InputMethodSubtypeSwitchingController(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull com.android.server.inputmethod.InputMethodMap inputMethodMap, int i) {
        this.mContext = context;
        this.mUserId = i;
        this.mController = com.android.server.inputmethod.InputMethodSubtypeSwitchingController.ControllerImpl.createFrom(null, getSortedInputMethodAndSubtypeList(false, false, false, context, inputMethodMap, i));
    }

    @android.annotation.NonNull
    public static com.android.server.inputmethod.InputMethodSubtypeSwitchingController createInstanceLocked(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull com.android.server.inputmethod.InputMethodMap inputMethodMap, int i) {
        return new com.android.server.inputmethod.InputMethodSubtypeSwitchingController(context, inputMethodMap, i);
    }

    int getUserId() {
        return this.mUserId;
    }

    public void onUserActionLocked(android.view.inputmethod.InputMethodInfo inputMethodInfo, android.view.inputmethod.InputMethodSubtype inputMethodSubtype) {
        if (this.mController == null) {
            return;
        }
        this.mController.onUserActionLocked(inputMethodInfo, inputMethodSubtype);
    }

    public void resetCircularListLocked(@android.annotation.NonNull com.android.server.inputmethod.InputMethodMap inputMethodMap) {
        this.mController = com.android.server.inputmethod.InputMethodSubtypeSwitchingController.ControllerImpl.createFrom(this.mController, getSortedInputMethodAndSubtypeList(false, false, false, this.mContext, inputMethodMap, this.mUserId));
    }

    public com.android.server.inputmethod.InputMethodSubtypeSwitchingController.ImeSubtypeListItem getNextInputMethodLocked(boolean z, android.view.inputmethod.InputMethodInfo inputMethodInfo, android.view.inputmethod.InputMethodSubtype inputMethodSubtype) {
        if (this.mController == null) {
            return null;
        }
        return this.mController.getNextInputMethod(z, inputMethodInfo, inputMethodSubtype);
    }

    public void dump(android.util.Printer printer) {
        if (this.mController != null) {
            this.mController.dump(printer);
        } else {
            printer.println("    mController=null");
        }
    }
}
