package com.android.server.wm;

@com.android.internal.annotations.Keep
/* loaded from: classes3.dex */
class DisplayAreaGroup extends com.android.server.wm.RootDisplayArea {
    DisplayAreaGroup(com.android.server.wm.WindowManagerService windowManagerService, java.lang.String str, int i) {
        super(windowManagerService, str, i);
    }

    @Override // com.android.server.wm.RootDisplayArea
    boolean isOrientationDifferentFromDisplay() {
        return isOrientationDifferentFromDisplay(getBounds());
    }

    private boolean isOrientationDifferentFromDisplay(android.graphics.Rect rect) {
        if (this.mDisplayContent == null) {
            return false;
        }
        android.graphics.Rect bounds = this.mDisplayContent.getBounds();
        return (rect.width() < rect.height()) != (bounds.width() < bounds.height());
    }

    @Override // com.android.server.wm.DisplayArea, com.android.server.wm.WindowContainer
    int getOrientation(int i) {
        int orientation = super.getOrientation(i);
        return isOrientationDifferentFromDisplay() ? android.content.pm.ActivityInfo.reverseOrientation(orientation) : orientation;
    }

    @Override // com.android.server.wm.DisplayArea, com.android.server.wm.ConfigurationContainer
    void resolveOverrideConfiguration(android.content.res.Configuration configuration) {
        super.resolveOverrideConfiguration(configuration);
        android.content.res.Configuration resolvedOverrideConfiguration = getResolvedOverrideConfiguration();
        if (resolvedOverrideConfiguration.orientation != 0) {
            return;
        }
        android.graphics.Rect bounds = resolvedOverrideConfiguration.windowConfiguration.getBounds();
        if (bounds.isEmpty()) {
            bounds = configuration.windowConfiguration.getBounds();
        }
        if (isOrientationDifferentFromDisplay(bounds)) {
            if (configuration.orientation == 1) {
                resolvedOverrideConfiguration.orientation = 2;
            } else if (configuration.orientation == 2) {
                resolvedOverrideConfiguration.orientation = 1;
            }
        }
    }
}
