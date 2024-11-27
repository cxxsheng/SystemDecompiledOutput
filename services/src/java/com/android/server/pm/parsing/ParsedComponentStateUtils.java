package com.android.server.pm.parsing;

/* loaded from: classes2.dex */
public class ParsedComponentStateUtils {
    @android.annotation.NonNull
    public static android.util.Pair<java.lang.CharSequence, java.lang.Integer> getNonLocalizedLabelAndIcon(com.android.internal.pm.pkg.component.ParsedComponent parsedComponent, @android.annotation.Nullable com.android.server.pm.pkg.PackageStateInternal packageStateInternal, int i) {
        java.lang.CharSequence nonLocalizedLabel = parsedComponent.getNonLocalizedLabel();
        int icon = parsedComponent.getIcon();
        android.util.Pair<java.lang.String, java.lang.Integer> overrideLabelIconForComponent = packageStateInternal == null ? null : packageStateInternal.getUserStateOrDefault(i).getOverrideLabelIconForComponent(parsedComponent.getComponentName());
        if (overrideLabelIconForComponent != null) {
            if (overrideLabelIconForComponent.first != null) {
                nonLocalizedLabel = (java.lang.CharSequence) overrideLabelIconForComponent.first;
            }
            if (overrideLabelIconForComponent.second != null) {
                icon = ((java.lang.Integer) overrideLabelIconForComponent.second).intValue();
            }
        }
        return android.util.Pair.create(nonLocalizedLabel, java.lang.Integer.valueOf(icon));
    }
}
