package com.android.server.wm.utils;

/* loaded from: classes3.dex */
public class DisplayInfoOverrides {
    public static final com.android.server.wm.utils.DisplayInfoOverrides.DisplayInfoFieldsUpdater WM_OVERRIDE_FIELDS = new com.android.server.wm.utils.DisplayInfoOverrides.DisplayInfoFieldsUpdater() { // from class: com.android.server.wm.utils.DisplayInfoOverrides$$ExternalSyntheticLambda0
        @Override // com.android.server.wm.utils.DisplayInfoOverrides.DisplayInfoFieldsUpdater
        public final void setFields(android.view.DisplayInfo displayInfo, android.view.DisplayInfo displayInfo2) {
            com.android.server.wm.utils.DisplayInfoOverrides.lambda$static$0(displayInfo, displayInfo2);
        }
    };

    public interface DisplayInfoFieldsUpdater {
        void setFields(@android.annotation.NonNull android.view.DisplayInfo displayInfo, @android.annotation.NonNull android.view.DisplayInfo displayInfo2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$static$0(android.view.DisplayInfo displayInfo, android.view.DisplayInfo displayInfo2) {
        displayInfo.appWidth = displayInfo2.appWidth;
        displayInfo.appHeight = displayInfo2.appHeight;
        displayInfo.smallestNominalAppWidth = displayInfo2.smallestNominalAppWidth;
        displayInfo.smallestNominalAppHeight = displayInfo2.smallestNominalAppHeight;
        displayInfo.largestNominalAppWidth = displayInfo2.largestNominalAppWidth;
        displayInfo.largestNominalAppHeight = displayInfo2.largestNominalAppHeight;
        displayInfo.logicalWidth = displayInfo2.logicalWidth;
        displayInfo.logicalHeight = displayInfo2.logicalHeight;
        displayInfo.physicalXDpi = displayInfo2.physicalXDpi;
        displayInfo.physicalYDpi = displayInfo2.physicalYDpi;
        displayInfo.rotation = displayInfo2.rotation;
        displayInfo.displayCutout = displayInfo2.displayCutout;
        displayInfo.logicalDensityDpi = displayInfo2.logicalDensityDpi;
        displayInfo.roundedCorners = displayInfo2.roundedCorners;
        displayInfo.displayShape = displayInfo2.displayShape;
    }

    public static void copyDisplayInfoFields(@android.annotation.NonNull android.view.DisplayInfo displayInfo, @android.annotation.NonNull android.view.DisplayInfo displayInfo2, @android.annotation.Nullable android.view.DisplayInfo displayInfo3, @android.annotation.NonNull com.android.server.wm.utils.DisplayInfoOverrides.DisplayInfoFieldsUpdater displayInfoFieldsUpdater) {
        displayInfo.copyFrom(displayInfo2);
        if (displayInfo3 != null) {
            displayInfoFieldsUpdater.setFields(displayInfo, displayInfo3);
        }
    }
}
