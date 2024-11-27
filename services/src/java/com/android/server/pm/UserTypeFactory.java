package com.android.server.pm;

/* loaded from: classes2.dex */
public final class UserTypeFactory {
    private static final java.lang.String LOG_TAG = "UserTypeFactory";

    private UserTypeFactory() {
    }

    public static android.util.ArrayMap<java.lang.String, com.android.server.pm.UserTypeDetails> getUserTypes() {
        android.util.ArrayMap<java.lang.String, com.android.server.pm.UserTypeDetails.Builder> defaultBuilders = getDefaultBuilders();
        android.content.res.XmlResourceParser xml = android.content.res.Resources.getSystem().getXml(android.R.xml.config_user_types);
        try {
            customizeBuilders(defaultBuilders, xml);
            if (xml != null) {
                xml.close();
            }
            android.util.ArrayMap<java.lang.String, com.android.server.pm.UserTypeDetails> arrayMap = new android.util.ArrayMap<>(defaultBuilders.size());
            for (int i = 0; i < defaultBuilders.size(); i++) {
                arrayMap.put(defaultBuilders.keyAt(i), defaultBuilders.valueAt(i).createUserTypeDetails());
            }
            return arrayMap;
        } catch (java.lang.Throwable th) {
            if (xml != null) {
                try {
                    xml.close();
                } catch (java.lang.Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    private static android.util.ArrayMap<java.lang.String, com.android.server.pm.UserTypeDetails.Builder> getDefaultBuilders() {
        android.util.ArrayMap<java.lang.String, com.android.server.pm.UserTypeDetails.Builder> arrayMap = new android.util.ArrayMap<>();
        arrayMap.put("android.os.usertype.profile.MANAGED", getDefaultTypeProfileManaged());
        arrayMap.put("android.os.usertype.full.SYSTEM", getDefaultTypeFullSystem());
        arrayMap.put("android.os.usertype.full.SECONDARY", getDefaultTypeFullSecondary());
        arrayMap.put("android.os.usertype.full.GUEST", getDefaultTypeFullGuest());
        arrayMap.put("android.os.usertype.full.DEMO", getDefaultTypeFullDemo());
        arrayMap.put("android.os.usertype.full.RESTRICTED", getDefaultTypeFullRestricted());
        arrayMap.put("android.os.usertype.system.HEADLESS", getDefaultTypeSystemHeadless());
        arrayMap.put("android.os.usertype.profile.CLONE", getDefaultTypeProfileClone());
        arrayMap.put("android.os.usertype.profile.COMMUNAL", getDefaultTypeProfileCommunal());
        arrayMap.put("android.os.usertype.profile.PRIVATE", getDefaultTypeProfilePrivate());
        if (android.os.Build.IS_DEBUGGABLE) {
            arrayMap.put("android.os.usertype.profile.TEST", getDefaultTypeProfileTest());
        }
        return arrayMap;
    }

    private static com.android.server.pm.UserTypeDetails.Builder getDefaultTypeProfileClone() {
        return new com.android.server.pm.UserTypeDetails.Builder().setName("android.os.usertype.profile.CLONE").setBaseType(4096).setMaxAllowedPerParent(1).setLabels(android.R.string.policylab_expirePassword).setIconBadge(android.R.drawable.ic_clear_search_api_holo_light).setBadgePlain(android.R.drawable.ic_clear_search_api_holo_dark).setBadgeNoBackground(android.R.drawable.ic_clear_search_api_holo_dark).setStatusBarIcon(0).setBadgeLabels(android.R.string.checked).setBadgeColors(android.R.color.system_neutral2_800).setDarkThemeBadgeColors(android.R.color.system_neutral2_900).setDefaultRestrictions(null).setDefaultCrossProfileIntentFilters(getDefaultCloneCrossProfileIntentFilter()).setDefaultSecureSettings(getDefaultNonManagedProfileSecureSettings()).setDefaultUserProperties(new android.content.pm.UserProperties.Builder().setStartWithParent(true).setShowInLauncher(0).setShowInSettings(0).setInheritDevicePolicy(1).setUseParentsContacts(true).setUpdateCrossProfileIntentFiltersOnOTA(true).setCrossProfileIntentFilterAccessControl(10).setCrossProfileIntentResolutionStrategy(1).setShowInQuietMode(2).setShowInSharingSurfaces(0).setMediaSharedWithParent(true).setCredentialShareableWithParent(true).setDeleteAppWithParent(true).setCrossProfileContentSharingStrategy(1));
    }

    private static com.android.server.pm.UserTypeDetails.Builder getDefaultTypeProfileManaged() {
        return new com.android.server.pm.UserTypeDetails.Builder().setName("android.os.usertype.profile.MANAGED").setBaseType(4096).setDefaultUserInfoPropertyFlags(32).setMaxAllowedPerParent(1).setLabels(android.R.string.policylab_setGlobalProxy, android.R.string.policylab_watchLogin, android.R.string.policylab_wipeData).setIconBadge(android.R.drawable.ic_corp_badge_off).setBadgePlain(android.R.drawable.ic_contact_picture_holo_light).setBadgeNoBackground(android.R.drawable.ic_corp_badge_case).setStatusBarIcon(android.R.drawable.stat_sys_download_anim5).setBadgeLabels(android.R.string.lockscreen_sim_locked_message, android.R.string.lockscreen_sim_puk_locked_instructions, android.R.string.lockscreen_sim_puk_locked_message).setBadgeColors(android.R.color.primary_text_disable_only_holo_dark, android.R.color.primary_text_disable_only_material_dark, android.R.color.primary_text_focused_holo_dark).setDarkThemeBadgeColors(android.R.color.primary_text_disable_only_holo_light, android.R.color.primary_text_disable_only_material_light, android.R.color.primary_text_holo_dark).setDefaultRestrictions(getDefaultProfileRestrictions()).setDefaultSecureSettings(getDefaultManagedProfileSecureSettings()).setDefaultCrossProfileIntentFilters(getDefaultManagedCrossProfileIntentFilter()).setDefaultUserProperties(new android.content.pm.UserProperties.Builder().setStartWithParent(true).setShowInLauncher(1).setShowInSettings(1).setShowInQuietMode(0).setShowInSharingSurfaces(1).setAuthAlwaysRequiredToDisableQuietMode(false).setCredentialShareableWithParent(true));
    }

    private static com.android.server.pm.UserTypeDetails.Builder getDefaultTypeProfileTest() {
        android.os.Bundle defaultProfileRestrictions = getDefaultProfileRestrictions();
        defaultProfileRestrictions.putBoolean("no_fun", true);
        return new com.android.server.pm.UserTypeDetails.Builder().setName("android.os.usertype.profile.TEST").setBaseType(4096).setMaxAllowedPerParent(2).setLabels(android.R.string.policylab_resetPassword, android.R.string.policylab_resetPassword, android.R.string.policylab_resetPassword).setIconBadge(android.R.drawable.ic_test_badge_experiment).setBadgePlain(android.R.drawable.ic_swap_horiz).setBadgeNoBackground(android.R.drawable.ic_sysbar_quicksettings).setStatusBarIcon(android.R.drawable.ic_swap_horiz).setBadgeLabels(android.R.string.lockscreen_sim_locked_message, android.R.string.lockscreen_sim_puk_locked_instructions, android.R.string.lockscreen_sim_puk_locked_message).setBadgeColors(android.R.color.primary_text_disable_only_holo_dark, android.R.color.primary_text_disable_only_material_dark, android.R.color.primary_text_focused_holo_dark).setDarkThemeBadgeColors(android.R.color.primary_text_disable_only_holo_light, android.R.color.primary_text_disable_only_material_light, android.R.color.primary_text_holo_dark).setDefaultRestrictions(defaultProfileRestrictions).setDefaultSecureSettings(getDefaultNonManagedProfileSecureSettings());
    }

    private static com.android.server.pm.UserTypeDetails.Builder getDefaultTypeProfileCommunal() {
        return new com.android.server.pm.UserTypeDetails.Builder().setName("android.os.usertype.profile.COMMUNAL").setBaseType(4096).setMaxAllowed(1).setEnabled(android.os.UserManager.isCommunalProfileEnabled() ? 1 : 0).setLabels(android.R.string.policylab_forceLock).setIconBadge(android.R.drawable.ic_test_badge_experiment).setBadgePlain(android.R.drawable.ic_swap_horiz).setBadgeNoBackground(android.R.drawable.ic_sysbar_quicksettings).setStatusBarIcon(android.R.drawable.ic_swap_horiz).setBadgeLabels(android.R.string.lockscreen_sim_locked_message, android.R.string.lockscreen_sim_puk_locked_instructions, android.R.string.lockscreen_sim_puk_locked_message).setBadgeColors(android.R.color.primary_text_disable_only_holo_dark, android.R.color.primary_text_disable_only_material_dark, android.R.color.primary_text_focused_holo_dark).setDarkThemeBadgeColors(android.R.color.primary_text_disable_only_holo_light, android.R.color.primary_text_disable_only_material_light, android.R.color.primary_text_holo_dark).setDefaultRestrictions(getDefaultProfileRestrictions()).setDefaultSecureSettings(getDefaultNonManagedProfileSecureSettings()).setDefaultUserProperties(new android.content.pm.UserProperties.Builder().setStartWithParent(false).setShowInLauncher(1).setShowInSettings(1).setCredentialShareableWithParent(false).setAlwaysVisible(true));
    }

    private static com.android.server.pm.UserTypeDetails.Builder getDefaultTypeProfilePrivate() {
        return new com.android.server.pm.UserTypeDetails.Builder().setName("android.os.usertype.profile.PRIVATE").setBaseType(4096).setMaxAllowedPerParent(1).setLabels(android.R.string.policylab_limitPassword).setIconBadge(android.R.drawable.ic_print_error).setBadgePlain(android.R.drawable.ic_print).setBadgeNoBackground(android.R.drawable.ic_print).setStatusBarIcon(android.R.drawable.stat_sys_download_done_static).setBadgeLabels(android.R.string.policydesc_wipeData).setBadgeColors(android.R.color.black).setDarkThemeBadgeColors(android.R.color.white).setDefaultRestrictions(getDefaultProfileRestrictions()).setDefaultCrossProfileIntentFilters(getDefaultPrivateCrossProfileIntentFilter()).setDefaultUserProperties(new android.content.pm.UserProperties.Builder().setStartWithParent(true).setCredentialShareableWithParent(true).setAuthAlwaysRequiredToDisableQuietMode(true).setAllowStoppingUserWithDelayedLocking(true).setMediaSharedWithParent(false).setShowInLauncher(1).setShowInSettings(1).setShowInQuietMode(1).setShowInSharingSurfaces(1).setCrossProfileIntentFilterAccessControl(10).setInheritDevicePolicy(1).setCrossProfileContentSharingStrategy(1).setProfileApiVisibility(1).setItemsRestrictedOnHomeScreen(true));
    }

    private static com.android.server.pm.UserTypeDetails.Builder getDefaultTypeFullSecondary() {
        return new com.android.server.pm.UserTypeDetails.Builder().setName("android.os.usertype.full.SECONDARY").setBaseType(1024).setMaxAllowed(-1).setDefaultRestrictions(getDefaultSecondaryUserRestrictions());
    }

    private static com.android.server.pm.UserTypeDetails.Builder getDefaultTypeFullGuest() {
        return new com.android.server.pm.UserTypeDetails.Builder().setName("android.os.usertype.full.GUEST").setBaseType(1024).setDefaultUserInfoPropertyFlags((android.content.res.Resources.getSystem().getBoolean(android.R.bool.config_freeformWindowManagement) ? 256 : 0) | 4).setMaxAllowed(1).setDefaultRestrictions(getDefaultGuestUserRestrictions());
    }

    private static com.android.server.pm.UserTypeDetails.Builder getDefaultTypeFullDemo() {
        return new com.android.server.pm.UserTypeDetails.Builder().setName("android.os.usertype.full.DEMO").setBaseType(1024).setDefaultUserInfoPropertyFlags(512).setMaxAllowed(-1).setDefaultRestrictions(null);
    }

    private static com.android.server.pm.UserTypeDetails.Builder getDefaultTypeFullRestricted() {
        return new com.android.server.pm.UserTypeDetails.Builder().setName("android.os.usertype.full.RESTRICTED").setBaseType(1024).setDefaultUserInfoPropertyFlags(8).setMaxAllowed(-1).setDefaultRestrictions(null);
    }

    private static com.android.server.pm.UserTypeDetails.Builder getDefaultTypeFullSystem() {
        return new com.android.server.pm.UserTypeDetails.Builder().setName("android.os.usertype.full.SYSTEM").setBaseType(3072).setDefaultUserInfoPropertyFlags(16387).setMaxAllowed(1);
    }

    private static com.android.server.pm.UserTypeDetails.Builder getDefaultTypeSystemHeadless() {
        return new com.android.server.pm.UserTypeDetails.Builder().setName("android.os.usertype.system.HEADLESS").setBaseType(2048).setDefaultUserInfoPropertyFlags(3).setMaxAllowed(1);
    }

    private static android.os.Bundle getDefaultSecondaryUserRestrictions() {
        android.os.Bundle bundle = new android.os.Bundle();
        bundle.putBoolean("no_outgoing_calls", true);
        bundle.putBoolean("no_sms", true);
        return bundle;
    }

    private static android.os.Bundle getDefaultGuestUserRestrictions() {
        android.os.Bundle defaultSecondaryUserRestrictions = getDefaultSecondaryUserRestrictions();
        defaultSecondaryUserRestrictions.putBoolean("no_config_wifi", true);
        defaultSecondaryUserRestrictions.putBoolean("no_install_unknown_sources", true);
        defaultSecondaryUserRestrictions.putBoolean("no_config_credentials", true);
        return defaultSecondaryUserRestrictions;
    }

    private static android.os.Bundle getDefaultProfileRestrictions() {
        android.os.Bundle bundle = new android.os.Bundle();
        bundle.putBoolean("no_wallpaper", true);
        return bundle;
    }

    private static android.os.Bundle getDefaultManagedProfileSecureSettings() {
        android.os.Bundle bundle = new android.os.Bundle();
        bundle.putString("managed_profile_contact_remote_search", "1");
        bundle.putString("cross_profile_calendar_enabled", "1");
        return bundle;
    }

    private static java.util.List<com.android.server.pm.DefaultCrossProfileIntentFilter> getDefaultManagedCrossProfileIntentFilter() {
        return com.android.server.pm.DefaultCrossProfileIntentFiltersUtils.getDefaultManagedProfileFilters();
    }

    private static java.util.List<com.android.server.pm.DefaultCrossProfileIntentFilter> getDefaultCloneCrossProfileIntentFilter() {
        return com.android.server.pm.DefaultCrossProfileIntentFiltersUtils.getDefaultCloneProfileFilters();
    }

    private static java.util.List<com.android.server.pm.DefaultCrossProfileIntentFilter> getDefaultPrivateCrossProfileIntentFilter() {
        return com.android.server.pm.DefaultCrossProfileIntentFiltersUtils.getDefaultPrivateProfileFilters();
    }

    private static android.os.Bundle getDefaultNonManagedProfileSecureSettings() {
        android.os.Bundle bundle = new android.os.Bundle();
        bundle.putString("user_setup_complete", "1");
        return bundle;
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x0085  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x0086 A[SYNTHETIC] */
    @com.android.internal.annotations.VisibleForTesting
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    static void customizeBuilders(android.util.ArrayMap<java.lang.String, com.android.server.pm.UserTypeDetails.Builder> arrayMap, android.content.res.XmlResourceParser xmlResourceParser) {
        boolean z;
        final com.android.server.pm.UserTypeDetails.Builder builder;
        try {
            com.android.internal.util.XmlUtils.beginDocument(xmlResourceParser, "user-types");
            com.android.internal.util.XmlUtils.nextElement(xmlResourceParser);
            while (true) {
                boolean z2 = true;
                if (xmlResourceParser.getEventType() != 1) {
                    java.lang.String name = xmlResourceParser.getName();
                    if ("profile-type".equals(name)) {
                        z = true;
                    } else if ("full-type".equals(name)) {
                        z = false;
                    } else {
                        if ("change-user-type".equals(name)) {
                            com.android.internal.util.XmlUtils.skipCurrentTag(xmlResourceParser);
                        } else {
                            android.util.Slog.w(LOG_TAG, "Skipping unknown element " + name + " in " + xmlResourceParser.getPositionDescription());
                            com.android.internal.util.XmlUtils.skipCurrentTag(xmlResourceParser);
                        }
                        com.android.internal.util.XmlUtils.nextElement(xmlResourceParser);
                    }
                    java.lang.String attributeValue = xmlResourceParser.getAttributeValue(null, "name");
                    if (attributeValue == null || attributeValue.equals("")) {
                        android.util.Slog.w(LOG_TAG, "Skipping user type with no name in " + xmlResourceParser.getPositionDescription());
                        com.android.internal.util.XmlUtils.skipCurrentTag(xmlResourceParser);
                    } else {
                        java.lang.String intern = attributeValue.intern();
                        if (intern.startsWith("android.")) {
                            android.util.Slog.i(LOG_TAG, "Customizing user type " + intern);
                            builder = arrayMap.get(intern);
                            if (builder == null) {
                                throw new java.lang.IllegalArgumentException("Illegal custom user type name " + intern + ": Non-AOSP user types cannot start with 'android.'");
                            }
                            if (z) {
                                if (builder.getBaseType() != 4096) {
                                }
                                if (!z2) {
                                    throw new java.lang.IllegalArgumentException("Wrong base type to customize user type (" + intern + "), which is type " + android.content.pm.UserInfo.flagsToString(builder.getBaseType()));
                                }
                            }
                            if (z || builder.getBaseType() != 1024) {
                                z2 = false;
                                if (!z2) {
                                }
                            }
                            if (!z2) {
                            }
                        } else if (z) {
                            android.util.Slog.i(LOG_TAG, "Creating custom user type " + intern);
                            builder = new com.android.server.pm.UserTypeDetails.Builder();
                            builder.setName(intern);
                            builder.setBaseType(4096);
                            arrayMap.put(intern, builder);
                        } else {
                            throw new java.lang.IllegalArgumentException("Creation of non-profile user type (" + intern + ") is not currently supported.");
                        }
                        if (z) {
                            java.util.Objects.requireNonNull(builder);
                            setIntAttribute(xmlResourceParser, "max-allowed-per-parent", new java.util.function.Consumer() { // from class: com.android.server.pm.UserTypeFactory$$ExternalSyntheticLambda0
                                @Override // java.util.function.Consumer
                                public final void accept(java.lang.Object obj) {
                                    com.android.server.pm.UserTypeDetails.Builder.this.setMaxAllowedPerParent(((java.lang.Integer) obj).intValue());
                                }
                            });
                            java.util.Objects.requireNonNull(builder);
                            setResAttribute(xmlResourceParser, "icon-badge", new java.util.function.Consumer() { // from class: com.android.server.pm.UserTypeFactory$$ExternalSyntheticLambda1
                                @Override // java.util.function.Consumer
                                public final void accept(java.lang.Object obj) {
                                    com.android.server.pm.UserTypeDetails.Builder.this.setIconBadge(((java.lang.Integer) obj).intValue());
                                }
                            });
                            java.util.Objects.requireNonNull(builder);
                            setResAttribute(xmlResourceParser, "badge-plain", new java.util.function.Consumer() { // from class: com.android.server.pm.UserTypeFactory$$ExternalSyntheticLambda2
                                @Override // java.util.function.Consumer
                                public final void accept(java.lang.Object obj) {
                                    com.android.server.pm.UserTypeDetails.Builder.this.setBadgePlain(((java.lang.Integer) obj).intValue());
                                }
                            });
                            java.util.Objects.requireNonNull(builder);
                            setResAttribute(xmlResourceParser, "badge-no-background", new java.util.function.Consumer() { // from class: com.android.server.pm.UserTypeFactory$$ExternalSyntheticLambda3
                                @Override // java.util.function.Consumer
                                public final void accept(java.lang.Object obj) {
                                    com.android.server.pm.UserTypeDetails.Builder.this.setBadgeNoBackground(((java.lang.Integer) obj).intValue());
                                }
                            });
                            java.util.Objects.requireNonNull(builder);
                            setResAttribute(xmlResourceParser, "status-bar-icon", new java.util.function.Consumer() { // from class: com.android.server.pm.UserTypeFactory$$ExternalSyntheticLambda4
                                @Override // java.util.function.Consumer
                                public final void accept(java.lang.Object obj) {
                                    com.android.server.pm.UserTypeDetails.Builder.this.setStatusBarIcon(((java.lang.Integer) obj).intValue());
                                }
                            });
                        }
                        java.util.Objects.requireNonNull(builder);
                        setIntAttribute(xmlResourceParser, com.android.server.timezonedetector.ServiceConfigAccessor.PROVIDER_MODE_ENABLED, new java.util.function.Consumer() { // from class: com.android.server.pm.UserTypeFactory$$ExternalSyntheticLambda5
                            @Override // java.util.function.Consumer
                            public final void accept(java.lang.Object obj) {
                                com.android.server.pm.UserTypeDetails.Builder.this.setEnabled(((java.lang.Integer) obj).intValue());
                            }
                        });
                        java.util.Objects.requireNonNull(builder);
                        setIntAttribute(xmlResourceParser, "max-allowed", new java.util.function.Consumer() { // from class: com.android.server.pm.UserTypeFactory$$ExternalSyntheticLambda6
                            @Override // java.util.function.Consumer
                            public final void accept(java.lang.Object obj) {
                                com.android.server.pm.UserTypeDetails.Builder.this.setMaxAllowed(((java.lang.Integer) obj).intValue());
                            }
                        });
                        int depth = xmlResourceParser.getDepth();
                        while (com.android.internal.util.XmlUtils.nextElementWithin(xmlResourceParser, depth)) {
                            java.lang.String name2 = xmlResourceParser.getName();
                            if ("default-restrictions".equals(name2)) {
                                builder.setDefaultRestrictions(com.android.server.pm.UserRestrictionsUtils.readRestrictions(com.android.internal.util.XmlUtils.makeTyped(xmlResourceParser)));
                            } else if (z && "badge-labels".equals(name2)) {
                                java.util.Objects.requireNonNull(builder);
                                setResAttributeArray(xmlResourceParser, new java.util.function.Consumer() { // from class: com.android.server.pm.UserTypeFactory$$ExternalSyntheticLambda7
                                    @Override // java.util.function.Consumer
                                    public final void accept(java.lang.Object obj) {
                                        com.android.server.pm.UserTypeDetails.Builder.this.setBadgeLabels((int[]) obj);
                                    }
                                });
                            } else if (z && "badge-colors".equals(name2)) {
                                java.util.Objects.requireNonNull(builder);
                                setResAttributeArray(xmlResourceParser, new java.util.function.Consumer() { // from class: com.android.server.pm.UserTypeFactory$$ExternalSyntheticLambda8
                                    @Override // java.util.function.Consumer
                                    public final void accept(java.lang.Object obj) {
                                        com.android.server.pm.UserTypeDetails.Builder.this.setBadgeColors((int[]) obj);
                                    }
                                });
                            } else if (z && "badge-colors-dark".equals(name2)) {
                                java.util.Objects.requireNonNull(builder);
                                setResAttributeArray(xmlResourceParser, new java.util.function.Consumer() { // from class: com.android.server.pm.UserTypeFactory$$ExternalSyntheticLambda9
                                    @Override // java.util.function.Consumer
                                    public final void accept(java.lang.Object obj) {
                                        com.android.server.pm.UserTypeDetails.Builder.this.setDarkThemeBadgeColors((int[]) obj);
                                    }
                                });
                            } else if ("user-properties".equals(name2)) {
                                builder.getDefaultUserProperties().updateFromXml(com.android.internal.util.XmlUtils.makeTyped(xmlResourceParser));
                            } else {
                                android.util.Slog.w(LOG_TAG, "Unrecognized tag " + name2 + " in " + xmlResourceParser.getPositionDescription());
                            }
                        }
                    }
                    com.android.internal.util.XmlUtils.nextElement(xmlResourceParser);
                } else {
                    return;
                }
            }
        } catch (java.io.IOException | org.xmlpull.v1.XmlPullParserException e) {
            android.util.Slog.w(LOG_TAG, "Cannot read user type configuration file.", e);
        }
    }

    private static void setIntAttribute(android.content.res.XmlResourceParser xmlResourceParser, java.lang.String str, java.util.function.Consumer<java.lang.Integer> consumer) {
        java.lang.String attributeValue = xmlResourceParser.getAttributeValue(null, str);
        if (attributeValue == null) {
            return;
        }
        try {
            consumer.accept(java.lang.Integer.valueOf(java.lang.Integer.parseInt(attributeValue)));
        } catch (java.lang.NumberFormatException e) {
            android.util.Slog.e(LOG_TAG, "Cannot parse value of '" + attributeValue + "' for " + str + " in " + xmlResourceParser.getPositionDescription(), e);
            throw e;
        }
    }

    private static void setResAttribute(android.content.res.XmlResourceParser xmlResourceParser, java.lang.String str, java.util.function.Consumer<java.lang.Integer> consumer) {
        if (xmlResourceParser.getAttributeValue(null, str) == null) {
            return;
        }
        consumer.accept(java.lang.Integer.valueOf(xmlResourceParser.getAttributeResourceValue(null, str, 0)));
    }

    private static void setResAttributeArray(android.content.res.XmlResourceParser xmlResourceParser, java.util.function.Consumer<int[]> consumer) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        int depth = xmlResourceParser.getDepth();
        while (com.android.internal.util.XmlUtils.nextElementWithin(xmlResourceParser, depth)) {
            java.lang.String name = xmlResourceParser.getName();
            if (!com.android.server.pm.Settings.TAG_ITEM.equals(name)) {
                android.util.Slog.w(LOG_TAG, "Skipping unknown child element " + name + " in " + xmlResourceParser.getPositionDescription());
                com.android.internal.util.XmlUtils.skipCurrentTag(xmlResourceParser);
            } else {
                int attributeResourceValue = xmlResourceParser.getAttributeResourceValue(null, "res", -1);
                if (attributeResourceValue != -1) {
                    arrayList.add(java.lang.Integer.valueOf(attributeResourceValue));
                }
            }
        }
        int[] iArr = new int[arrayList.size()];
        for (int i = 0; i < arrayList.size(); i++) {
            iArr[i] = ((java.lang.Integer) arrayList.get(i)).intValue();
        }
        consumer.accept(iArr);
    }

    public static int getUserTypeVersion() {
        android.content.res.XmlResourceParser xml = android.content.res.Resources.getSystem().getXml(android.R.xml.config_user_types);
        try {
            int userTypeVersion = getUserTypeVersion(xml);
            if (xml != null) {
                xml.close();
            }
            return userTypeVersion;
        } catch (java.lang.Throwable th) {
            if (xml != null) {
                try {
                    xml.close();
                } catch (java.lang.Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    static int getUserTypeVersion(android.content.res.XmlResourceParser xmlResourceParser) {
        try {
            com.android.internal.util.XmlUtils.beginDocument(xmlResourceParser, "user-types");
            java.lang.String attributeValue = xmlResourceParser.getAttributeValue(null, "version");
            if (attributeValue == null) {
                return 0;
            }
            try {
                return java.lang.Integer.parseInt(attributeValue);
            } catch (java.lang.NumberFormatException e) {
                android.util.Slog.e(LOG_TAG, "Cannot parse value of '" + attributeValue + "' for version in " + xmlResourceParser.getPositionDescription(), e);
                throw e;
            }
        } catch (java.io.IOException | org.xmlpull.v1.XmlPullParserException e2) {
            android.util.Slog.w(LOG_TAG, "Cannot read user type configuration file.", e2);
            return 0;
        }
    }

    public static java.util.List<com.android.server.pm.UserTypeFactory.UserTypeUpgrade> getUserTypeUpgrades() {
        android.content.res.XmlResourceParser xml = android.content.res.Resources.getSystem().getXml(android.R.xml.config_user_types);
        try {
            java.util.List<com.android.server.pm.UserTypeFactory.UserTypeUpgrade> parseUserUpgrades = parseUserUpgrades(getDefaultBuilders(), xml);
            if (xml != null) {
                xml.close();
            }
            return parseUserUpgrades;
        } catch (java.lang.Throwable th) {
            if (xml != null) {
                try {
                    xml.close();
                } catch (java.lang.Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    static java.util.List<com.android.server.pm.UserTypeFactory.UserTypeUpgrade> parseUserUpgrades(android.util.ArrayMap<java.lang.String, com.android.server.pm.UserTypeDetails.Builder> arrayMap, android.content.res.XmlResourceParser xmlResourceParser) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        try {
            com.android.internal.util.XmlUtils.beginDocument(xmlResourceParser, "user-types");
            com.android.internal.util.XmlUtils.nextElement(xmlResourceParser);
            while (xmlResourceParser.getEventType() != 1) {
                if ("change-user-type".equals(xmlResourceParser.getName())) {
                    java.lang.String attributeValue = xmlResourceParser.getAttributeValue(null, "from");
                    java.lang.String attributeValue2 = xmlResourceParser.getAttributeValue(null, "to");
                    validateUserTypeIsProfile(attributeValue, arrayMap);
                    validateUserTypeIsProfile(attributeValue2, arrayMap);
                    try {
                        arrayList.add(new com.android.server.pm.UserTypeFactory.UserTypeUpgrade(attributeValue, attributeValue2, java.lang.Integer.parseInt(xmlResourceParser.getAttributeValue(null, "whenVersionLeq"))));
                    } catch (java.lang.NumberFormatException e) {
                        android.util.Slog.e(LOG_TAG, "Cannot parse value of whenVersionLeq in " + xmlResourceParser.getPositionDescription(), e);
                        throw e;
                    }
                } else {
                    com.android.internal.util.XmlUtils.skipCurrentTag(xmlResourceParser);
                }
                com.android.internal.util.XmlUtils.nextElement(xmlResourceParser);
            }
        } catch (java.io.IOException | org.xmlpull.v1.XmlPullParserException e2) {
            android.util.Slog.w(LOG_TAG, "Cannot read user type configuration file.", e2);
        }
        return arrayList;
    }

    private static void validateUserTypeIsProfile(java.lang.String str, android.util.ArrayMap<java.lang.String, com.android.server.pm.UserTypeDetails.Builder> arrayMap) {
        com.android.server.pm.UserTypeDetails.Builder builder = arrayMap.get(str);
        if (builder != null && builder.getBaseType() != 4096) {
            throw new java.lang.IllegalArgumentException("Illegal upgrade of user type " + str + " : Can only upgrade profiles user types");
        }
    }

    public static class UserTypeUpgrade {
        private final java.lang.String mFromType;
        private final java.lang.String mToType;
        private final int mUpToVersion;

        public UserTypeUpgrade(java.lang.String str, java.lang.String str2, int i) {
            this.mFromType = str;
            this.mToType = str2;
            this.mUpToVersion = i;
        }

        public java.lang.String getFromType() {
            return this.mFromType;
        }

        public java.lang.String getToType() {
            return this.mToType;
        }

        public int getUpToVersion() {
            return this.mUpToVersion;
        }
    }
}
