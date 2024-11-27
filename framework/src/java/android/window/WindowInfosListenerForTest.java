package android.window;

/* loaded from: classes4.dex */
public class WindowInfosListenerForTest {
    private static final java.lang.String TAG = "WindowInfosListenerForTest";
    private android.util.ArrayMap<java.util.function.Consumer<java.util.List<android.window.WindowInfosListenerForTest.WindowInfo>>, android.window.WindowInfosListener> mListeners = new android.util.ArrayMap<>();

    public static class WindowInfo {
        public final android.graphics.Rect bounds;
        public final int displayId;
        public final boolean isDuplicateTouchToWallpaper;
        public final boolean isFocusable;
        public final boolean isPreventSplitting;
        public final boolean isTouchable;
        public final boolean isTrustedOverlay;
        public final boolean isVisible;
        public final boolean isWatchOutsideTouch;
        public final java.lang.String name;
        public final android.graphics.Matrix transform;
        public final android.os.IBinder windowToken;

        WindowInfo(android.os.IBinder iBinder, java.lang.String str, int i, android.graphics.Rect rect, int i2, android.graphics.Matrix matrix) {
            this.windowToken = iBinder;
            this.name = str;
            this.displayId = i;
            this.bounds = rect;
            this.isTrustedOverlay = (i2 & 256) != 0;
            this.isVisible = (i2 & 2) == 0;
            this.transform = matrix;
            this.isTouchable = (i2 & 8) == 0;
            this.isFocusable = (i2 & 4) == 0;
            this.isPreventSplitting = (i2 & 16) != 0;
            this.isDuplicateTouchToWallpaper = (i2 & 32) != 0;
            this.isWatchOutsideTouch = (i2 & 512) != 0;
        }

        public java.lang.String toString() {
            return this.name + ", displayId=" + this.displayId + ", frame=" + this.bounds + ", isVisible=" + this.isVisible + ", isTrustedOverlay=" + this.isTrustedOverlay + ", token=" + this.windowToken + ", transform=" + this.transform;
        }
    }

    public void addWindowInfosListener(final java.util.function.Consumer<java.util.List<android.window.WindowInfosListenerForTest.WindowInfo>> consumer) {
        final java.util.concurrent.CountDownLatch countDownLatch = new java.util.concurrent.CountDownLatch(1);
        android.window.WindowInfosListener windowInfosListener = new android.window.WindowInfosListener() { // from class: android.window.WindowInfosListenerForTest.1
            @Override // android.window.WindowInfosListener
            public void onWindowInfosChanged(android.view.InputWindowHandle[] inputWindowHandleArr, android.window.WindowInfosListener.DisplayInfo[] displayInfoArr) {
                try {
                    countDownLatch.await();
                } catch (java.lang.InterruptedException e) {
                    android.util.Log.e(android.window.WindowInfosListenerForTest.TAG, "Exception thrown while waiting for listener to be called with initial state");
                }
                consumer.accept(android.window.WindowInfosListenerForTest.buildWindowInfos(inputWindowHandleArr, displayInfoArr));
            }
        };
        this.mListeners.put(consumer, windowInfosListener);
        android.util.Pair<android.view.InputWindowHandle[], android.window.WindowInfosListener.DisplayInfo[]> register = windowInfosListener.register();
        consumer.accept(buildWindowInfos(register.first, register.second));
        countDownLatch.countDown();
    }

    public void removeWindowInfosListener(java.util.function.Consumer<java.util.List<android.window.WindowInfosListenerForTest.WindowInfo>> consumer) {
        android.window.WindowInfosListener remove = this.mListeners.remove(consumer);
        if (remove == null) {
            return;
        }
        remove.unregister();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static java.util.List<android.window.WindowInfosListenerForTest.WindowInfo> buildWindowInfos(android.view.InputWindowHandle[] inputWindowHandleArr, android.window.WindowInfosListener.DisplayInfo[] displayInfoArr) {
        java.util.ArrayList arrayList = new java.util.ArrayList(inputWindowHandleArr.length);
        android.util.SparseArray sparseArray = new android.util.SparseArray(displayInfoArr.length);
        for (android.window.WindowInfosListener.DisplayInfo displayInfo : displayInfoArr) {
            sparseArray.put(displayInfo.mDisplayId, displayInfo);
        }
        android.graphics.RectF rectF = new android.graphics.RectF();
        for (android.view.InputWindowHandle inputWindowHandle : inputWindowHandleArr) {
            android.graphics.Rect rect = new android.graphics.Rect(inputWindowHandle.frame);
            android.window.WindowInfosListener.DisplayInfo displayInfo2 = (android.window.WindowInfosListener.DisplayInfo) sparseArray.get(inputWindowHandle.displayId);
            if (displayInfo2 != null) {
                rectF.set(rect);
                displayInfo2.mTransform.mapRect(rectF);
                rectF.round(rect);
            }
            arrayList.add(new android.window.WindowInfosListenerForTest.WindowInfo(inputWindowHandle.getWindowToken(), inputWindowHandle.name, inputWindowHandle.displayId, rect, inputWindowHandle.inputConfig, inputWindowHandle.transform));
        }
        return arrayList;
    }
}
