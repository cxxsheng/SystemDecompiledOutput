package com.android.internal.accessibility.dialog;

/* loaded from: classes4.dex */
public final class AccessibilityTargetHelper {
    private AccessibilityTargetHelper() {
    }

    public static java.util.List<com.android.internal.accessibility.dialog.AccessibilityTarget> getTargets(android.content.Context context, int i) {
        java.util.List<com.android.internal.accessibility.dialog.AccessibilityTarget> installedTargets = getInstalledTargets(context, i);
        java.util.List<java.lang.String> accessibilityShortcutTargets = ((android.view.accessibility.AccessibilityManager) context.getSystemService(android.content.Context.ACCESSIBILITY_SERVICE)).getAccessibilityShortcutTargets(i);
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (java.lang.String str : accessibilityShortcutTargets) {
            for (com.android.internal.accessibility.dialog.AccessibilityTarget accessibilityTarget : installedTargets) {
                if (!"com.android.server.accessibility.MagnificationController".contentEquals(str) && android.content.ComponentName.unflattenFromString(str).equals(android.content.ComponentName.unflattenFromString(accessibilityTarget.getId()))) {
                    arrayList.add(accessibilityTarget);
                } else if (str.contentEquals(accessibilityTarget.getId())) {
                    arrayList.add(accessibilityTarget);
                }
            }
        }
        return arrayList;
    }

    public static java.util.List<com.android.internal.accessibility.dialog.AccessibilityTarget> getInstalledTargets(android.content.Context context, int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        arrayList.addAll(getAccessibilityFilteredTargets(context, i));
        arrayList.addAll(getAllowListingFeatureTargets(context, i));
        return arrayList;
    }

    private static java.util.List<com.android.internal.accessibility.dialog.AccessibilityTarget> getAccessibilityFilteredTargets(android.content.Context context, int i) {
        java.util.List<com.android.internal.accessibility.dialog.AccessibilityTarget> accessibilityServiceTargets = getAccessibilityServiceTargets(context, i);
        java.util.List<com.android.internal.accessibility.dialog.AccessibilityTarget> accessibilityActivityTargets = getAccessibilityActivityTargets(context, i);
        for (final com.android.internal.accessibility.dialog.AccessibilityTarget accessibilityTarget : accessibilityActivityTargets) {
            accessibilityServiceTargets.removeIf(new java.util.function.Predicate() { // from class: com.android.internal.accessibility.dialog.AccessibilityTargetHelper$$ExternalSyntheticLambda2
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean arePackageNameAndLabelTheSame;
                    arePackageNameAndLabelTheSame = com.android.internal.accessibility.dialog.AccessibilityTargetHelper.arePackageNameAndLabelTheSame((com.android.internal.accessibility.dialog.AccessibilityTarget) obj, com.android.internal.accessibility.dialog.AccessibilityTarget.this);
                    return arePackageNameAndLabelTheSame;
                }
            });
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        arrayList.addAll(accessibilityServiceTargets);
        arrayList.addAll(accessibilityActivityTargets);
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean arePackageNameAndLabelTheSame(com.android.internal.accessibility.dialog.AccessibilityTarget accessibilityTarget, com.android.internal.accessibility.dialog.AccessibilityTarget accessibilityTarget2) {
        return android.content.ComponentName.unflattenFromString(accessibilityTarget2.getId()).getPackageName().equals(android.content.ComponentName.unflattenFromString(accessibilityTarget.getId()).getPackageName()) && accessibilityTarget2.getLabel().equals(accessibilityTarget.getLabel());
    }

    private static java.util.List<com.android.internal.accessibility.dialog.AccessibilityTarget> getAccessibilityServiceTargets(android.content.Context context, int i) {
        java.util.List<android.accessibilityservice.AccessibilityServiceInfo> installedAccessibilityServiceList = ((android.view.accessibility.AccessibilityManager) context.getSystemService(android.content.Context.ACCESSIBILITY_SERVICE)).getInstalledAccessibilityServiceList();
        if (installedAccessibilityServiceList == null) {
            return java.util.Collections.emptyList();
        }
        java.util.ArrayList arrayList = new java.util.ArrayList(installedAccessibilityServiceList.size());
        for (android.accessibilityservice.AccessibilityServiceInfo accessibilityServiceInfo : installedAccessibilityServiceList) {
            int i2 = accessibilityServiceInfo.getResolveInfo().serviceInfo.applicationInfo.targetSdkVersion;
            boolean z = (accessibilityServiceInfo.flags & 256) != 0;
            if (i2 > 29 || z || i != 0) {
                arrayList.add(createAccessibilityServiceTarget(context, i, accessibilityServiceInfo));
            }
        }
        return arrayList;
    }

    private static java.util.List<com.android.internal.accessibility.dialog.AccessibilityTarget> getAccessibilityActivityTargets(android.content.Context context, int i) {
        java.util.List<android.accessibilityservice.AccessibilityShortcutInfo> installedAccessibilityShortcutListAsUser = ((android.view.accessibility.AccessibilityManager) context.getSystemService(android.content.Context.ACCESSIBILITY_SERVICE)).getInstalledAccessibilityShortcutListAsUser(context, android.app.ActivityManager.getCurrentUser());
        if (installedAccessibilityShortcutListAsUser == null) {
            return java.util.Collections.emptyList();
        }
        java.util.ArrayList arrayList = new java.util.ArrayList(installedAccessibilityShortcutListAsUser.size());
        java.util.Iterator<android.accessibilityservice.AccessibilityShortcutInfo> it = installedAccessibilityShortcutListAsUser.iterator();
        while (it.hasNext()) {
            arrayList.add(new com.android.internal.accessibility.dialog.AccessibilityActivityTarget(context, i, it.next()));
        }
        return arrayList;
    }

    private static java.util.List<com.android.internal.accessibility.dialog.AccessibilityTarget> getAllowListingFeatureTargets(android.content.Context context, int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        int i2 = context.getApplicationInfo().uid;
        arrayList.add(new com.android.internal.accessibility.dialog.InvisibleToggleAllowListingFeatureTarget(context, i, com.android.internal.accessibility.util.ShortcutUtils.isShortcutContained(context, i, "com.android.server.accessibility.MagnificationController"), "com.android.server.accessibility.MagnificationController", i2, context.getString(com.android.internal.R.string.accessibility_magnification_chooser_text), context.getDrawable(com.android.internal.R.drawable.ic_accessibility_magnification), android.provider.Settings.Secure.ACCESSIBILITY_DISPLAY_MAGNIFICATION_NAVBAR_ENABLED));
        arrayList.add(new com.android.internal.accessibility.dialog.ToggleAllowListingFeatureTarget(context, i, com.android.internal.accessibility.util.ShortcutUtils.isShortcutContained(context, i, com.android.internal.accessibility.AccessibilityShortcutController.DALTONIZER_COMPONENT_NAME.flattenToString()), com.android.internal.accessibility.AccessibilityShortcutController.DALTONIZER_COMPONENT_NAME.flattenToString(), i2, context.getString(com.android.internal.R.string.color_correction_feature_name), context.getDrawable(com.android.internal.R.drawable.ic_accessibility_color_correction), android.provider.Settings.Secure.ACCESSIBILITY_DISPLAY_DALTONIZER_ENABLED));
        arrayList.add(new com.android.internal.accessibility.dialog.ToggleAllowListingFeatureTarget(context, i, com.android.internal.accessibility.util.ShortcutUtils.isShortcutContained(context, i, com.android.internal.accessibility.AccessibilityShortcutController.COLOR_INVERSION_COMPONENT_NAME.flattenToString()), com.android.internal.accessibility.AccessibilityShortcutController.COLOR_INVERSION_COMPONENT_NAME.flattenToString(), i2, context.getString(com.android.internal.R.string.color_inversion_feature_name), context.getDrawable(com.android.internal.R.drawable.ic_accessibility_color_inversion), android.provider.Settings.Secure.ACCESSIBILITY_DISPLAY_INVERSION_ENABLED));
        if (com.android.internal.os.RoSystemProperties.SUPPORT_ONE_HANDED_MODE) {
            arrayList.add(new com.android.internal.accessibility.dialog.ToggleAllowListingFeatureTarget(context, i, com.android.internal.accessibility.util.ShortcutUtils.isShortcutContained(context, i, com.android.internal.accessibility.AccessibilityShortcutController.ONE_HANDED_COMPONENT_NAME.flattenToString()), com.android.internal.accessibility.AccessibilityShortcutController.ONE_HANDED_COMPONENT_NAME.flattenToString(), i2, context.getString(com.android.internal.R.string.one_handed_mode_feature_name), context.getDrawable(com.android.internal.R.drawable.ic_accessibility_one_handed), android.provider.Settings.Secure.ONE_HANDED_MODE_ACTIVATED));
        }
        arrayList.add(new com.android.internal.accessibility.dialog.ToggleAllowListingFeatureTarget(context, i, com.android.internal.accessibility.util.ShortcutUtils.isShortcutContained(context, i, com.android.internal.accessibility.AccessibilityShortcutController.REDUCE_BRIGHT_COLORS_COMPONENT_NAME.flattenToString()), com.android.internal.accessibility.AccessibilityShortcutController.REDUCE_BRIGHT_COLORS_COMPONENT_NAME.flattenToString(), i2, context.getString(com.android.internal.R.string.reduce_bright_colors_feature_name), context.getDrawable(com.android.internal.R.drawable.ic_accessibility_reduce_bright_colors), android.provider.Settings.Secure.REDUCE_BRIGHT_COLORS_ACTIVATED));
        arrayList.add(new com.android.internal.accessibility.dialog.InvisibleToggleAllowListingFeatureTarget(context, i, com.android.internal.accessibility.util.ShortcutUtils.isShortcutContained(context, i, com.android.internal.accessibility.AccessibilityShortcutController.ACCESSIBILITY_HEARING_AIDS_COMPONENT_NAME.flattenToString()), com.android.internal.accessibility.AccessibilityShortcutController.ACCESSIBILITY_HEARING_AIDS_COMPONENT_NAME.flattenToString(), i2, context.getString(com.android.internal.R.string.hearing_aids_feature_name), context.getDrawable(com.android.internal.R.drawable.ic_accessibility_hearing_aid), null));
        return arrayList;
    }

    private static com.android.internal.accessibility.dialog.AccessibilityTarget createAccessibilityServiceTarget(android.content.Context context, int i, android.accessibilityservice.AccessibilityServiceInfo accessibilityServiceInfo) {
        switch (com.android.internal.accessibility.util.AccessibilityUtils.getAccessibilityServiceFragmentType(accessibilityServiceInfo)) {
            case 0:
                return new com.android.internal.accessibility.dialog.VolumeShortcutToggleAccessibilityServiceTarget(context, i, accessibilityServiceInfo);
            case 1:
                return new com.android.internal.accessibility.dialog.InvisibleToggleAccessibilityServiceTarget(context, i, accessibilityServiceInfo);
            case 2:
                return new com.android.internal.accessibility.dialog.ToggleAccessibilityServiceTarget(context, i, accessibilityServiceInfo);
            default:
                throw new java.lang.IllegalStateException("Unexpected fragment type");
        }
    }

    @java.lang.Deprecated
    static android.view.View createEnableDialogContentView(android.content.Context context, final com.android.internal.accessibility.dialog.AccessibilityServiceTarget accessibilityServiceTarget, final android.view.View.OnClickListener onClickListener, final android.view.View.OnClickListener onClickListener2) {
        android.view.View inflate = ((android.view.LayoutInflater) context.getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE)).inflate(com.android.internal.R.layout.accessibility_enable_service_warning, (android.view.ViewGroup) null);
        ((android.widget.ImageView) inflate.findViewById(com.android.internal.R.id.accessibility_permissionDialog_icon)).lambda$setImageURIAsync$2(accessibilityServiceTarget.getIcon());
        ((android.widget.TextView) inflate.findViewById(com.android.internal.R.id.accessibility_permissionDialog_title)).lambda$setTextAsync$0(context.getString(com.android.internal.R.string.accessibility_enable_service_title, getServiceName(context, accessibilityServiceTarget.getLabel())));
        android.widget.Button button = (android.widget.Button) inflate.findViewById(com.android.internal.R.id.accessibility_permission_enable_allow_button);
        android.widget.Button button2 = (android.widget.Button) inflate.findViewById(com.android.internal.R.id.accessibility_permission_enable_deny_button);
        button.setOnClickListener(new android.view.View.OnClickListener() { // from class: com.android.internal.accessibility.dialog.AccessibilityTargetHelper$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(android.view.View view) {
                com.android.internal.accessibility.dialog.AccessibilityTargetHelper.lambda$createEnableDialogContentView$1(com.android.internal.accessibility.dialog.AccessibilityServiceTarget.this, onClickListener, view);
            }
        });
        button2.setOnClickListener(new android.view.View.OnClickListener() { // from class: com.android.internal.accessibility.dialog.AccessibilityTargetHelper$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(android.view.View view) {
                com.android.internal.accessibility.dialog.AccessibilityTargetHelper.lambda$createEnableDialogContentView$2(com.android.internal.accessibility.dialog.AccessibilityServiceTarget.this, onClickListener2, view);
            }
        });
        return inflate;
    }

    static /* synthetic */ void lambda$createEnableDialogContentView$1(com.android.internal.accessibility.dialog.AccessibilityServiceTarget accessibilityServiceTarget, android.view.View.OnClickListener onClickListener, android.view.View view) {
        accessibilityServiceTarget.onCheckedChanged(true);
        onClickListener.onClick(view);
    }

    static /* synthetic */ void lambda$createEnableDialogContentView$2(com.android.internal.accessibility.dialog.AccessibilityServiceTarget accessibilityServiceTarget, android.view.View.OnClickListener onClickListener, android.view.View view) {
        accessibilityServiceTarget.onCheckedChanged(false);
        onClickListener.onClick(view);
    }

    private static java.lang.CharSequence getServiceName(android.content.Context context, java.lang.CharSequence charSequence) {
        return android.text.BidiFormatter.getInstance(context.getResources().getConfiguration().getLocales().get(0)).unicodeWrap(charSequence);
    }

    public static boolean isAccessibilityTargetAllowed(android.content.Context context, java.lang.String str, int i) {
        return ((android.view.accessibility.AccessibilityManager) context.getSystemService(android.view.accessibility.AccessibilityManager.class)).isAccessibilityTargetAllowed(str, i, android.os.UserHandle.myUserId());
    }

    public static boolean sendRestrictedDialogIntent(android.content.Context context, java.lang.String str, int i) {
        return ((android.view.accessibility.AccessibilityManager) context.getSystemService(android.view.accessibility.AccessibilityManager.class)).sendRestrictedDialogIntent(str, i, android.os.UserHandle.myUserId());
    }
}
