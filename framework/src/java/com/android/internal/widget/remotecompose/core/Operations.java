package com.android.internal.widget.remotecompose.core;

/* loaded from: classes5.dex */
public class Operations {
    public static final int CLICK_AREA = 64;
    public static final int DATA_BITMAP = 101;
    public static final int DATA_TEXT = 102;
    public static final int DRAW_BITMAP = 44;
    public static final int DRAW_BITMAP_INT = 66;
    public static final int HEADER = 0;
    public static final int LOAD_BITMAP = 4;
    public static final int ROOT_CONTENT_BEHAVIOR = 65;
    public static final int ROOT_CONTENT_DESCRIPTION = 103;
    public static final int THEME = 63;
    public static com.android.internal.widget.remotecompose.core.operations.utilities.IntMap<com.android.internal.widget.remotecompose.core.CompanionOperation> map = new com.android.internal.widget.remotecompose.core.operations.utilities.IntMap<>();

    static {
        map.put(0, com.android.internal.widget.remotecompose.core.operations.Header.COMPANION);
        map.put(66, com.android.internal.widget.remotecompose.core.operations.DrawBitmapInt.COMPANION);
        map.put(101, com.android.internal.widget.remotecompose.core.operations.BitmapData.COMPANION);
        map.put(102, com.android.internal.widget.remotecompose.core.operations.TextData.COMPANION);
        map.put(63, com.android.internal.widget.remotecompose.core.operations.Theme.COMPANION);
        map.put(64, com.android.internal.widget.remotecompose.core.operations.ClickArea.COMPANION);
        map.put(65, com.android.internal.widget.remotecompose.core.operations.RootContentBehavior.COMPANION);
        map.put(103, com.android.internal.widget.remotecompose.core.operations.RootContentDescription.COMPANION);
    }
}
