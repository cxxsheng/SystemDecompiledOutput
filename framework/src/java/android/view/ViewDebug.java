package android.view;

/* loaded from: classes4.dex */
public class ViewDebug {
    private static final int CAPTURE_TIMEOUT = 6000;
    public static final boolean DEBUG_DRAG = false;
    public static final boolean DEBUG_POSITIONING = false;
    private static final java.lang.String REMOTE_COMMAND_CAPTURE = "CAPTURE";
    private static final java.lang.String REMOTE_COMMAND_CAPTURE_LAYERS = "CAPTURE_LAYERS";
    private static final java.lang.String REMOTE_COMMAND_DUMP = "DUMP";
    public static final java.lang.String REMOTE_COMMAND_DUMP_ENCODED = "DUMP_ENCODED";
    private static final java.lang.String REMOTE_COMMAND_DUMP_THEME = "DUMP_THEME";
    private static final java.lang.String REMOTE_COMMAND_INVALIDATE = "INVALIDATE";
    private static final java.lang.String REMOTE_COMMAND_INVOKE_METHOD = "INVOKE_METHOD";
    private static final java.lang.String REMOTE_COMMAND_OUTPUT_DISPLAYLIST = "OUTPUT_DISPLAYLIST";
    private static final java.lang.String REMOTE_COMMAND_REQUEST_LAYOUT = "REQUEST_LAYOUT";
    private static final java.lang.String REMOTE_PROFILE = "PROFILE";
    private static final char SIG_ARRAY = '[';
    private static final char SIG_BOOLEAN = 'Z';
    private static final char SIG_BYTE = 'B';
    private static final char SIG_CHAR = 'C';
    private static final char SIG_DOUBLE = 'D';
    private static final char SIG_FLOAT = 'F';
    private static final char SIG_INT = 'I';
    private static final char SIG_LONG = 'J';
    private static final char SIG_SHORT = 'S';
    private static final char SIG_STRING = 'R';
    private static final char SIG_VOID = 'V';
    private static final java.lang.String TAG = "ViewDebug";

    @java.lang.Deprecated
    public static final boolean TRACE_HIERARCHY = false;

    @java.lang.Deprecated
    public static final boolean TRACE_RECYCLER = false;
    private static java.util.HashMap<java.lang.Class<?>, android.view.ViewDebug.PropertyInfo<android.view.ViewDebug.CapturedViewProperty, ?>[]> sCapturedViewProperties;
    private static java.util.HashMap<java.lang.Class<?>, android.view.ViewDebug.PropertyInfo<android.view.ViewDebug.ExportedProperty, ?>[]> sExportProperties;

    public interface CanvasProvider {
        android.graphics.Bitmap createBitmap();

        android.graphics.Canvas getCanvas(android.view.View view, int i, int i2);
    }

    @java.lang.annotation.Target({java.lang.annotation.ElementType.FIELD, java.lang.annotation.ElementType.METHOD})
    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
    public @interface CapturedViewProperty {
        boolean retrieveReturn() default false;
    }

    @java.lang.annotation.Target({java.lang.annotation.ElementType.FIELD, java.lang.annotation.ElementType.METHOD})
    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
    public @interface ExportedProperty {
        java.lang.String category() default "";

        boolean deepExport() default false;

        android.view.ViewDebug.FlagToString[] flagMapping() default {};

        boolean formatToHexString() default false;

        boolean hasAdjacentMapping() default false;

        android.view.ViewDebug.IntToString[] indexMapping() default {};

        android.view.ViewDebug.IntToString[] mapping() default {};

        java.lang.String prefix() default "";

        boolean resolveId() default false;
    }

    @java.lang.annotation.Target({java.lang.annotation.ElementType.TYPE})
    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
    public @interface FlagToString {
        int equals();

        int mask();

        java.lang.String name();

        boolean outputIf() default true;
    }

    public interface HierarchyHandler {
        void dumpViewHierarchyWithProperties(java.io.BufferedWriter bufferedWriter, int i);

        android.view.View findHierarchyView(java.lang.String str, int i);
    }

    @java.lang.Deprecated
    public enum HierarchyTraceType {
        INVALIDATE,
        INVALIDATE_CHILD,
        INVALIDATE_CHILD_IN_PARENT,
        REQUEST_LAYOUT,
        ON_LAYOUT,
        ON_MEASURE,
        DRAW,
        BUILD_CACHE
    }

    @java.lang.annotation.Target({java.lang.annotation.ElementType.TYPE})
    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
    public @interface IntToString {
        int from();

        java.lang.String to();
    }

    @java.lang.Deprecated
    public enum RecyclerTraceType {
        NEW_VIEW,
        BIND_VIEW,
        RECYCLE_FROM_ACTIVE_HEAP,
        RECYCLE_FROM_SCRAP_HEAP,
        MOVE_TO_SCRAP_HEAP,
        MOVE_FROM_ACTIVE_TO_SCRAP_HEAP
    }

    /* JADX INFO: Access modifiers changed from: private */
    static abstract class PropertyInfo<T extends java.lang.annotation.Annotation, R extends java.lang.reflect.AccessibleObject & java.lang.reflect.Member> {
        public final R member;
        public final java.lang.String name;
        public final T property;
        public final java.lang.Class<?> returnType;
        public java.lang.String entrySuffix = "";
        public java.lang.String valueSuffix = "";

        public abstract java.lang.Object invoke(java.lang.Object obj) throws java.lang.Exception;

        PropertyInfo(java.lang.Class<T> cls, R r, java.lang.Class<?> cls2) {
            this.member = r;
            this.name = r.getName();
            this.property = (T) r.getAnnotation(cls);
            this.returnType = cls2;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public static <T extends java.lang.annotation.Annotation> android.view.ViewDebug.PropertyInfo<T, ?> forMethod(java.lang.reflect.Method method, java.lang.Class<T> cls) {
            try {
                if (method.getReturnType() != java.lang.Void.class) {
                    if (method.getParameterTypes().length != 0 || !method.isAnnotationPresent(cls)) {
                        return null;
                    }
                    method.setAccessible(true);
                    android.view.ViewDebug.MethodPI methodPI = new android.view.ViewDebug.MethodPI(method, cls);
                    methodPI.entrySuffix = "()";
                    methodPI.valueSuffix = android.inputmethodservice.navigationbar.NavigationBarInflaterView.GRAVITY_SEPARATOR;
                    return methodPI;
                }
                return null;
            } catch (java.lang.NoClassDefFoundError e) {
                return null;
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public static <T extends java.lang.annotation.Annotation> android.view.ViewDebug.PropertyInfo<T, ?> forField(java.lang.reflect.Field field, java.lang.Class<T> cls) {
            if (!field.isAnnotationPresent(cls)) {
                return null;
            }
            field.setAccessible(true);
            return new android.view.ViewDebug.FieldPI(field, cls);
        }
    }

    private static class MethodPI<T extends java.lang.annotation.Annotation> extends android.view.ViewDebug.PropertyInfo<T, java.lang.reflect.Method> {
        MethodPI(java.lang.reflect.Method method, java.lang.Class<T> cls) {
            super(cls, method, method.getReturnType());
        }

        @Override // android.view.ViewDebug.PropertyInfo
        public java.lang.Object invoke(java.lang.Object obj) throws java.lang.Exception {
            return ((java.lang.reflect.Method) this.member).invoke(obj, new java.lang.Object[0]);
        }
    }

    private static class FieldPI<T extends java.lang.annotation.Annotation> extends android.view.ViewDebug.PropertyInfo<T, java.lang.reflect.Field> {
        FieldPI(java.lang.reflect.Field field, java.lang.Class<T> cls) {
            super(cls, field, field.getType());
        }

        @Override // android.view.ViewDebug.PropertyInfo
        public java.lang.Object invoke(java.lang.Object obj) throws java.lang.Exception {
            return ((java.lang.reflect.Field) this.member).get(obj);
        }
    }

    public static long getViewInstanceCount() {
        return android.os.Debug.countInstancesOfClass(android.view.View.class);
    }

    public static long getViewRootImplCount() {
        return android.os.Debug.countInstancesOfClass(android.view.ViewRootImpl.class);
    }

    @java.lang.Deprecated
    public static void trace(android.view.View view, android.view.ViewDebug.RecyclerTraceType recyclerTraceType, int... iArr) {
    }

    @java.lang.Deprecated
    public static void startRecyclerTracing(java.lang.String str, android.view.View view) {
    }

    @java.lang.Deprecated
    public static void stopRecyclerTracing() {
    }

    @java.lang.Deprecated
    public static void trace(android.view.View view, android.view.ViewDebug.HierarchyTraceType hierarchyTraceType) {
    }

    @java.lang.Deprecated
    public static void startHierarchyTracing(java.lang.String str, android.view.View view) {
    }

    @java.lang.Deprecated
    public static void stopHierarchyTracing() {
    }

    static void dispatchCommand(android.view.View view, java.lang.String str, java.lang.String str2, java.io.OutputStream outputStream) throws java.io.IOException {
        android.view.View rootView = view.getRootView();
        if (REMOTE_COMMAND_DUMP.equalsIgnoreCase(str)) {
            dump(rootView, false, true, outputStream);
            return;
        }
        if (REMOTE_COMMAND_DUMP_THEME.equalsIgnoreCase(str)) {
            dumpTheme(rootView, outputStream);
            return;
        }
        if (REMOTE_COMMAND_DUMP_ENCODED.equalsIgnoreCase(str)) {
            dumpEncoded(rootView, outputStream);
            return;
        }
        if (REMOTE_COMMAND_CAPTURE_LAYERS.equalsIgnoreCase(str)) {
            captureLayers(rootView, new java.io.DataOutputStream(outputStream));
            return;
        }
        java.lang.String[] split = str2.split(" ");
        if (REMOTE_COMMAND_CAPTURE.equalsIgnoreCase(str)) {
            capture(rootView, outputStream, split[0]);
            return;
        }
        if (REMOTE_COMMAND_OUTPUT_DISPLAYLIST.equalsIgnoreCase(str)) {
            outputDisplayList(rootView, split[0]);
            return;
        }
        if (REMOTE_COMMAND_INVALIDATE.equalsIgnoreCase(str)) {
            invalidate(rootView, split[0]);
            return;
        }
        if (REMOTE_COMMAND_REQUEST_LAYOUT.equalsIgnoreCase(str)) {
            requestLayout(rootView, split[0]);
        } else if (REMOTE_PROFILE.equalsIgnoreCase(str)) {
            profile(rootView, outputStream, split[0]);
        } else if (REMOTE_COMMAND_INVOKE_METHOD.equals(str)) {
            invokeViewMethod(rootView, outputStream, split);
        }
    }

    public static android.view.View findView(android.view.View view, java.lang.String str) {
        if (str.indexOf(64) != -1) {
            java.lang.String[] split = str.split("@");
            java.lang.String str2 = split[0];
            int parseLong = (int) java.lang.Long.parseLong(split[1], 16);
            android.view.View rootView = view.getRootView();
            if (rootView instanceof android.view.ViewGroup) {
                return findView((android.view.ViewGroup) rootView, str2, parseLong);
            }
            return null;
        }
        return view.getRootView().findViewById(view.getResources().getIdentifier(str, null, null));
    }

    private static void invalidate(android.view.View view, java.lang.String str) {
        android.view.View findView = findView(view, str);
        if (findView != null) {
            findView.postInvalidate();
        }
    }

    private static void requestLayout(android.view.View view, java.lang.String str) {
        final android.view.View findView = findView(view, str);
        if (findView != null) {
            view.post(new java.lang.Runnable() { // from class: android.view.ViewDebug.1
                @Override // java.lang.Runnable
                public void run() {
                    android.view.View.this.requestLayout();
                }
            });
        }
    }

    private static void profile(android.view.View view, java.io.OutputStream outputStream, java.lang.String str) throws java.io.IOException {
        android.view.View findView = findView(view, str);
        java.io.BufferedWriter bufferedWriter = null;
        try {
            try {
                java.io.BufferedWriter bufferedWriter2 = new java.io.BufferedWriter(new java.io.OutputStreamWriter(outputStream), 32768);
                try {
                    if (findView != null) {
                        profileViewAndChildren(findView, bufferedWriter2);
                    } else {
                        bufferedWriter2.write("-1 -1 -1");
                        bufferedWriter2.newLine();
                    }
                    bufferedWriter2.write("DONE.");
                    bufferedWriter2.newLine();
                    bufferedWriter2.close();
                } catch (java.lang.Exception e) {
                    e = e;
                    bufferedWriter = bufferedWriter2;
                    android.util.Log.w("View", "Problem profiling the view:", e);
                    if (bufferedWriter != null) {
                        bufferedWriter.close();
                    }
                } catch (java.lang.Throwable th) {
                    th = th;
                    bufferedWriter = bufferedWriter2;
                    if (bufferedWriter != null) {
                        bufferedWriter.close();
                    }
                    throw th;
                }
            } catch (java.lang.Exception e2) {
                e = e2;
            }
        } catch (java.lang.Throwable th2) {
            th = th2;
        }
    }

    public static void profileViewAndChildren(android.view.View view, java.io.BufferedWriter bufferedWriter) throws java.io.IOException {
        profileViewAndChildren(view, android.graphics.RenderNode.create(TAG, null), bufferedWriter, true);
    }

    private static void profileViewAndChildren(android.view.View view, android.graphics.RenderNode renderNode, java.io.BufferedWriter bufferedWriter, boolean z) throws java.io.IOException {
        long profileViewMeasure = (z || (view.mPrivateFlags & 2048) != 0) ? profileViewMeasure(view) : 0L;
        long profileViewLayout = (z || (view.mPrivateFlags & 8192) != 0) ? profileViewLayout(view) : 0L;
        long profileViewDraw = (!z && view.willNotDraw() && (view.mPrivateFlags & 32) == 0) ? 0L : profileViewDraw(view, renderNode);
        bufferedWriter.write(java.lang.String.valueOf(profileViewMeasure));
        bufferedWriter.write(32);
        bufferedWriter.write(java.lang.String.valueOf(profileViewLayout));
        bufferedWriter.write(32);
        bufferedWriter.write(java.lang.String.valueOf(profileViewDraw));
        bufferedWriter.newLine();
        if (view instanceof android.view.ViewGroup) {
            android.view.ViewGroup viewGroup = (android.view.ViewGroup) view;
            int childCount = viewGroup.getChildCount();
            for (int i = 0; i < childCount; i++) {
                profileViewAndChildren(viewGroup.getChildAt(i), renderNode, bufferedWriter, false);
            }
        }
    }

    private static long profileViewMeasure(final android.view.View view) {
        return profileViewOperation(view, new android.view.ViewDebug.ViewOperation() { // from class: android.view.ViewDebug.2
            @Override // android.view.ViewDebug.ViewOperation
            public void pre() {
                forceLayout(android.view.View.this);
            }

            private void forceLayout(android.view.View view2) {
                view2.forceLayout();
                if (view2 instanceof android.view.ViewGroup) {
                    android.view.ViewGroup viewGroup = (android.view.ViewGroup) view2;
                    int childCount = viewGroup.getChildCount();
                    for (int i = 0; i < childCount; i++) {
                        forceLayout(viewGroup.getChildAt(i));
                    }
                }
            }

            @Override // android.view.ViewDebug.ViewOperation
            public void run() {
                android.view.View.this.measure(android.view.View.this.mOldWidthMeasureSpec, android.view.View.this.mOldHeightMeasureSpec);
            }
        });
    }

    private static long profileViewLayout(final android.view.View view) {
        return profileViewOperation(view, new android.view.ViewDebug.ViewOperation() { // from class: android.view.ViewDebug$$ExternalSyntheticLambda0
            @Override // android.view.ViewDebug.ViewOperation
            public final void run() {
                r0.layout(r0.mLeft, r0.mTop, r0.mRight, android.view.View.this.mBottom);
            }
        });
    }

    private static long profileViewDraw(final android.view.View view, android.graphics.RenderNode renderNode) {
        android.util.DisplayMetrics displayMetrics = view.getResources().getDisplayMetrics();
        if (displayMetrics == null) {
            return 0L;
        }
        if (view.isHardwareAccelerated()) {
            final android.graphics.RecordingCanvas beginRecording = renderNode.beginRecording(displayMetrics.widthPixels, displayMetrics.heightPixels);
            try {
                return profileViewOperation(view, new android.view.ViewDebug.ViewOperation() { // from class: android.view.ViewDebug$$ExternalSyntheticLambda1
                    @Override // android.view.ViewDebug.ViewOperation
                    public final void run() {
                        android.view.View.this.draw(beginRecording);
                    }
                });
            } finally {
                renderNode.endRecording();
            }
        }
        android.graphics.Bitmap createBitmap = android.graphics.Bitmap.createBitmap(displayMetrics, displayMetrics.widthPixels, displayMetrics.heightPixels, android.graphics.Bitmap.Config.RGB_565);
        final android.graphics.Canvas canvas = new android.graphics.Canvas(createBitmap);
        try {
            return profileViewOperation(view, new android.view.ViewDebug.ViewOperation() { // from class: android.view.ViewDebug$$ExternalSyntheticLambda2
                @Override // android.view.ViewDebug.ViewOperation
                public final void run() {
                    android.view.View.this.draw(canvas);
                }
            });
        } finally {
            canvas.setBitmap(null);
            createBitmap.recycle();
        }
    }

    interface ViewOperation {
        void run();

        default void pre() {
        }
    }

    private static long profileViewOperation(android.view.View view, final android.view.ViewDebug.ViewOperation viewOperation) {
        final java.util.concurrent.CountDownLatch countDownLatch = new java.util.concurrent.CountDownLatch(1);
        final long[] jArr = new long[1];
        view.post(new java.lang.Runnable() { // from class: android.view.ViewDebug$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() {
                android.view.ViewDebug.lambda$profileViewOperation$3(android.view.ViewDebug.ViewOperation.this, jArr, countDownLatch);
            }
        });
        try {
            if (!countDownLatch.await(6000L, java.util.concurrent.TimeUnit.MILLISECONDS)) {
                android.util.Log.w("View", "Could not complete the profiling of the view " + view);
                return -1L;
            }
            return jArr[0];
        } catch (java.lang.InterruptedException e) {
            android.util.Log.w("View", "Could not complete the profiling of the view " + view);
            java.lang.Thread.currentThread().interrupt();
            return -1L;
        }
    }

    static /* synthetic */ void lambda$profileViewOperation$3(android.view.ViewDebug.ViewOperation viewOperation, long[] jArr, java.util.concurrent.CountDownLatch countDownLatch) {
        try {
            viewOperation.pre();
            long threadCpuTimeNanos = android.os.Debug.threadCpuTimeNanos();
            viewOperation.run();
            jArr[0] = android.os.Debug.threadCpuTimeNanos() - threadCpuTimeNanos;
        } finally {
            countDownLatch.countDown();
        }
    }

    public static void captureLayers(android.view.View view, java.io.DataOutputStream dataOutputStream) throws java.io.IOException {
        try {
            android.graphics.Rect rect = new android.graphics.Rect();
            view.mAttachInfo.mViewRootImpl.getDisplayFrame(rect);
            dataOutputStream.writeInt(rect.width());
            dataOutputStream.writeInt(rect.height());
            captureViewLayer(view, dataOutputStream, true);
            dataOutputStream.write(2);
        } finally {
            dataOutputStream.close();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r8v1 */
    /* JADX WARN: Type inference failed for: r8v2, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r8v3 */
    private static void captureViewLayer(android.view.View view, java.io.DataOutputStream dataOutputStream, boolean z) throws java.io.IOException {
        ?? r8 = (view.getVisibility() == 0 && z) ? 1 : 0;
        if ((view.mPrivateFlags & 128) != 128) {
            int id = view.getId();
            java.lang.String simpleName = view.getClass().getSimpleName();
            if (id != -1) {
                simpleName = resolveId(view.getContext(), id).toString();
            }
            dataOutputStream.write(1);
            dataOutputStream.writeUTF(simpleName);
            dataOutputStream.writeByte(r8);
            int[] iArr = new int[2];
            view.getLocationInWindow(iArr);
            dataOutputStream.writeInt(iArr[0]);
            dataOutputStream.writeInt(iArr[1]);
            dataOutputStream.flush();
            android.graphics.Bitmap performViewCapture = performViewCapture(view, true);
            if (performViewCapture != null) {
                java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream(performViewCapture.getWidth() * performViewCapture.getHeight() * 2);
                performViewCapture.compress(android.graphics.Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                dataOutputStream.writeInt(byteArrayOutputStream.size());
                byteArrayOutputStream.writeTo(dataOutputStream);
            }
            dataOutputStream.flush();
        }
        if (view instanceof android.view.ViewGroup) {
            android.view.ViewGroup viewGroup = (android.view.ViewGroup) view;
            int childCount = viewGroup.getChildCount();
            for (int i = 0; i < childCount; i++) {
                captureViewLayer(viewGroup.getChildAt(i), dataOutputStream, r8);
            }
        }
        if (view.mOverlay != null) {
            captureViewLayer(view.getOverlay().mOverlayViewGroup, dataOutputStream, r8);
        }
    }

    private static void outputDisplayList(android.view.View view, java.lang.String str) throws java.io.IOException {
        android.view.View findView = findView(view, str);
        findView.getViewRootImpl().outputDisplayList(findView);
    }

    public static void outputDisplayList(android.view.View view, android.view.View view2) {
        view.getViewRootImpl().outputDisplayList(view2);
    }

    private static class PictureCallbackHandler implements java.lang.AutoCloseable, android.graphics.HardwareRenderer.PictureCapturedCallback, java.lang.Runnable {
        private final java.util.function.Function<android.graphics.Picture, java.lang.Boolean> mCallback;
        private final java.util.concurrent.Executor mExecutor;
        private final java.util.concurrent.locks.ReentrantLock mLock;
        private final java.util.ArrayDeque<android.graphics.Picture> mQueue;
        private java.lang.Thread mRenderThread;
        private final android.graphics.HardwareRenderer mRenderer;
        private boolean mStopListening;

        private PictureCallbackHandler(android.graphics.HardwareRenderer hardwareRenderer, java.util.function.Function<android.graphics.Picture, java.lang.Boolean> function, java.util.concurrent.Executor executor) {
            this.mLock = new java.util.concurrent.locks.ReentrantLock(false);
            this.mQueue = new java.util.ArrayDeque<>(3);
            this.mRenderer = hardwareRenderer;
            this.mCallback = function;
            this.mExecutor = executor;
            this.mRenderer.setPictureCaptureCallback(this);
        }

        @Override // java.lang.AutoCloseable
        public void close() {
            this.mLock.lock();
            this.mStopListening = true;
            this.mLock.unlock();
            this.mRenderer.setPictureCaptureCallback(null);
        }

        @Override // android.graphics.HardwareRenderer.PictureCapturedCallback
        public void onPictureCaptured(android.graphics.Picture picture) {
            this.mLock.lock();
            if (this.mStopListening) {
                this.mLock.unlock();
                this.mRenderer.setPictureCaptureCallback(null);
                return;
            }
            if (this.mRenderThread == null) {
                this.mRenderThread = java.lang.Thread.currentThread();
            }
            android.graphics.Picture removeLast = this.mQueue.size() == 3 ? this.mQueue.removeLast() : null;
            this.mQueue.add(picture);
            this.mLock.unlock();
            if (removeLast == null) {
                this.mExecutor.execute(this);
            } else {
                removeLast.close();
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            this.mLock.lock();
            android.graphics.Picture poll = this.mQueue.poll();
            boolean z = this.mStopListening;
            this.mLock.unlock();
            if (java.lang.Thread.currentThread() == this.mRenderThread) {
                close();
                throw new java.lang.IllegalStateException("ViewDebug#startRenderingCommandsCapture must be given an executor that invokes asynchronously");
            }
            if (z) {
                poll.close();
            } else if (!this.mCallback.apply(poll).booleanValue()) {
                close();
            }
        }
    }

    @java.lang.Deprecated
    public static java.lang.AutoCloseable startRenderingCommandsCapture(android.view.View view, java.util.concurrent.Executor executor, java.util.function.Function<android.graphics.Picture, java.lang.Boolean> function) {
        android.view.View.AttachInfo attachInfo = view.mAttachInfo;
        if (attachInfo == null) {
            throw new java.lang.IllegalArgumentException("Given view isn't attached");
        }
        if (attachInfo.mHandler.getLooper() != android.os.Looper.myLooper()) {
            throw new java.lang.IllegalStateException("Called on the wrong thread. Must be called on the thread that owns the given View");
        }
        android.view.ThreadedRenderer threadedRenderer = attachInfo.mThreadedRenderer;
        if (threadedRenderer == null) {
            return null;
        }
        return new android.view.ViewDebug.PictureCallbackHandler(threadedRenderer, function, executor);
    }

    private static class StreamingPictureCallbackHandler implements java.lang.AutoCloseable, android.graphics.HardwareRenderer.PictureCapturedCallback, java.lang.Runnable {
        private final java.util.concurrent.Callable<java.io.OutputStream> mCallback;
        private final java.util.concurrent.Executor mExecutor;
        private final java.util.concurrent.locks.ReentrantLock mLock;
        private final java.util.ArrayDeque<android.graphics.Picture> mQueue;
        private java.lang.Thread mRenderThread;
        private final android.graphics.HardwareRenderer mRenderer;
        private boolean mStopListening;

        private StreamingPictureCallbackHandler(android.graphics.HardwareRenderer hardwareRenderer, java.util.concurrent.Callable<java.io.OutputStream> callable, java.util.concurrent.Executor executor) {
            this.mLock = new java.util.concurrent.locks.ReentrantLock(false);
            this.mQueue = new java.util.ArrayDeque<>(3);
            this.mRenderer = hardwareRenderer;
            this.mCallback = callable;
            this.mExecutor = executor;
            this.mRenderer.setPictureCaptureCallback(this);
        }

        @Override // java.lang.AutoCloseable
        public void close() {
            this.mLock.lock();
            this.mStopListening = true;
            this.mLock.unlock();
            this.mRenderer.setPictureCaptureCallback(null);
        }

        @Override // android.graphics.HardwareRenderer.PictureCapturedCallback
        public void onPictureCaptured(android.graphics.Picture picture) {
            boolean z;
            this.mLock.lock();
            if (this.mStopListening) {
                this.mLock.unlock();
                this.mRenderer.setPictureCaptureCallback(null);
                return;
            }
            if (this.mRenderThread == null) {
                this.mRenderThread = java.lang.Thread.currentThread();
            }
            if (this.mQueue.size() != 3) {
                z = true;
            } else {
                this.mQueue.removeLast();
                z = false;
            }
            this.mQueue.add(picture);
            this.mLock.unlock();
            if (z) {
                this.mExecutor.execute(this);
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            java.io.OutputStream outputStream;
            this.mLock.lock();
            android.graphics.Picture poll = this.mQueue.poll();
            boolean z = this.mStopListening;
            this.mLock.unlock();
            if (java.lang.Thread.currentThread() == this.mRenderThread) {
                close();
                throw new java.lang.IllegalStateException("ViewDebug#startRenderingCommandsCapture must be given an executor that invokes asynchronously");
            }
            if (z) {
                return;
            }
            try {
                outputStream = this.mCallback.call();
            } catch (java.lang.Exception e) {
                android.util.Log.w(android.view.ViewDebug.TAG, "Aborting rendering commands capture because callback threw exception", e);
                outputStream = null;
            }
            if (outputStream != null) {
                try {
                    poll.writeToStream(outputStream);
                    outputStream.flush();
                    return;
                } catch (java.io.IOException e2) {
                    android.util.Log.w(android.view.ViewDebug.TAG, "Aborting rendering commands capture due to IOException writing to output stream", e2);
                    return;
                }
            }
            close();
        }
    }

    public static java.lang.AutoCloseable startRenderingCommandsCapture(android.view.View view, java.util.concurrent.Executor executor, java.util.concurrent.Callable<java.io.OutputStream> callable) {
        android.view.View.AttachInfo attachInfo = view.mAttachInfo;
        if (attachInfo == null) {
            throw new java.lang.IllegalArgumentException("Given view isn't attached");
        }
        if (attachInfo.mHandler.getLooper() != android.os.Looper.myLooper()) {
            throw new java.lang.IllegalStateException("Called on the wrong thread. Must be called on the thread that owns the given View");
        }
        android.view.ThreadedRenderer threadedRenderer = attachInfo.mThreadedRenderer;
        if (threadedRenderer == null) {
            return null;
        }
        return new android.view.ViewDebug.StreamingPictureCallbackHandler(threadedRenderer, callable, executor);
    }

    private static void capture(android.view.View view, java.io.OutputStream outputStream, java.lang.String str) throws java.io.IOException {
        capture(view, outputStream, findView(view, str));
    }

    public static void capture(android.view.View view, java.io.OutputStream outputStream, android.view.View view2) throws java.io.IOException {
        java.io.BufferedOutputStream bufferedOutputStream;
        java.lang.Throwable th;
        android.graphics.Bitmap performViewCapture = performViewCapture(view2, false);
        if (performViewCapture == null) {
            android.util.Log.w("View", "Failed to create capture bitmap!");
            performViewCapture = android.graphics.Bitmap.createBitmap(view.getResources().getDisplayMetrics(), 1, 1, android.graphics.Bitmap.Config.ARGB_8888);
        }
        try {
            bufferedOutputStream = new java.io.BufferedOutputStream(outputStream, 32768);
        } catch (java.lang.Throwable th2) {
            bufferedOutputStream = null;
            th = th2;
        }
        try {
            performViewCapture.compress(android.graphics.Bitmap.CompressFormat.PNG, 100, bufferedOutputStream);
            bufferedOutputStream.flush();
            bufferedOutputStream.close();
            performViewCapture.recycle();
        } catch (java.lang.Throwable th3) {
            th = th3;
            if (bufferedOutputStream != null) {
                bufferedOutputStream.close();
            }
            performViewCapture.recycle();
            throw th;
        }
    }

    private static android.graphics.Bitmap performViewCapture(final android.view.View view, final boolean z) {
        if (view != null) {
            final java.util.concurrent.CountDownLatch countDownLatch = new java.util.concurrent.CountDownLatch(1);
            final android.graphics.Bitmap[] bitmapArr = new android.graphics.Bitmap[1];
            view.post(new java.lang.Runnable() { // from class: android.view.ViewDebug$$ExternalSyntheticLambda10
                @Override // java.lang.Runnable
                public final void run() {
                    android.view.ViewDebug.lambda$performViewCapture$4(android.view.View.this, bitmapArr, z, countDownLatch);
                }
            });
            try {
                countDownLatch.await(6000L, java.util.concurrent.TimeUnit.MILLISECONDS);
                return bitmapArr[0];
            } catch (java.lang.InterruptedException e) {
                android.util.Log.w("View", "Could not complete the capture of the view " + view);
                java.lang.Thread.currentThread().interrupt();
                return null;
            }
        }
        return null;
    }

    static /* synthetic */ void lambda$performViewCapture$4(android.view.View view, android.graphics.Bitmap[] bitmapArr, boolean z, java.util.concurrent.CountDownLatch countDownLatch) {
        try {
            try {
                bitmapArr[0] = view.createSnapshot(view.isHardwareAccelerated() ? new android.view.ViewDebug.HardwareCanvasProvider() : new android.view.ViewDebug.SoftwareCanvasProvider(), z);
            } catch (java.lang.OutOfMemoryError e) {
                android.util.Log.w("View", "Out of memory for bitmap");
            }
        } finally {
            countDownLatch.countDown();
        }
    }

    @java.lang.Deprecated
    public static void dump(android.view.View view, boolean z, boolean z2, java.io.OutputStream outputStream) throws java.io.IOException {
        java.io.BufferedWriter bufferedWriter = null;
        try {
            try {
                java.io.BufferedWriter bufferedWriter2 = new java.io.BufferedWriter(new java.io.OutputStreamWriter(outputStream, "utf-8"), 32768);
                try {
                    android.view.View rootView = view.getRootView();
                    if (rootView instanceof android.view.ViewGroup) {
                        android.view.ViewGroup viewGroup = (android.view.ViewGroup) rootView;
                        dumpViewHierarchy(viewGroup.getContext(), viewGroup, bufferedWriter2, 0, z, z2);
                    }
                    bufferedWriter2.write("DONE.");
                    bufferedWriter2.newLine();
                    bufferedWriter2.close();
                } catch (java.lang.Exception e) {
                    e = e;
                    bufferedWriter = bufferedWriter2;
                    android.util.Log.w("View", "Problem dumping the view:", e);
                    if (bufferedWriter != null) {
                        bufferedWriter.close();
                    }
                } catch (java.lang.Throwable th) {
                    th = th;
                    bufferedWriter = bufferedWriter2;
                    if (bufferedWriter != null) {
                        bufferedWriter.close();
                    }
                    throw th;
                }
            } catch (java.lang.Throwable th2) {
                th = th2;
            }
        } catch (java.lang.Exception e2) {
            e = e2;
        }
    }

    public static void dumpv2(final android.view.View view, java.io.ByteArrayOutputStream byteArrayOutputStream) throws java.lang.InterruptedException {
        final android.view.ViewHierarchyEncoder viewHierarchyEncoder = new android.view.ViewHierarchyEncoder(byteArrayOutputStream);
        final java.util.concurrent.CountDownLatch countDownLatch = new java.util.concurrent.CountDownLatch(1);
        view.post(new java.lang.Runnable() { // from class: android.view.ViewDebug.3
            @Override // java.lang.Runnable
            public void run() {
                android.view.ViewHierarchyEncoder.this.addProperty("window:left", view.mAttachInfo.mWindowLeft);
                android.view.ViewHierarchyEncoder.this.addProperty("window:top", view.mAttachInfo.mWindowTop);
                view.encode(android.view.ViewHierarchyEncoder.this);
                countDownLatch.countDown();
            }
        });
        countDownLatch.await(2L, java.util.concurrent.TimeUnit.SECONDS);
        viewHierarchyEncoder.endStream();
    }

    private static void dumpEncoded(android.view.View view, java.io.OutputStream outputStream) throws java.io.IOException {
        java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream();
        android.view.ViewHierarchyEncoder viewHierarchyEncoder = new android.view.ViewHierarchyEncoder(byteArrayOutputStream);
        viewHierarchyEncoder.setUserPropertiesEnabled(false);
        viewHierarchyEncoder.addProperty("window:left", view.mAttachInfo.mWindowLeft);
        viewHierarchyEncoder.addProperty("window:top", view.mAttachInfo.mWindowTop);
        view.encode(viewHierarchyEncoder);
        viewHierarchyEncoder.endStream();
        outputStream.write(byteArrayOutputStream.toByteArray());
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v7 */
    public static void dumpTheme(android.view.View view, java.io.OutputStream outputStream) throws java.io.IOException {
        java.io.BufferedWriter bufferedWriter = null;
        int i = 0;
        java.io.BufferedWriter bufferedWriter2 = null;
        try {
            try {
                java.io.BufferedWriter bufferedWriter3 = new java.io.BufferedWriter(new java.io.OutputStreamWriter(outputStream, "utf-8"), 32768);
                try {
                    java.lang.String[] styleAttributesDump = getStyleAttributesDump(view.getContext().getResources(), view.getContext().getTheme());
                    if (styleAttributesDump != null) {
                        int i2 = 0;
                        while (true) {
                            i = styleAttributesDump.length;
                            if (i2 >= i) {
                                break;
                            }
                            if (styleAttributesDump[i2] != null) {
                                bufferedWriter3.write(styleAttributesDump[i2] + "\n");
                                bufferedWriter3.write(styleAttributesDump[i2 + 1] + "\n");
                            }
                            i2 += 2;
                        }
                    }
                    bufferedWriter3.write("DONE.");
                    bufferedWriter3.newLine();
                    bufferedWriter3.close();
                    bufferedWriter = i;
                } catch (java.lang.Exception e) {
                    e = e;
                    bufferedWriter2 = bufferedWriter3;
                    android.util.Log.w("View", "Problem dumping View Theme:", e);
                    bufferedWriter = bufferedWriter2;
                    if (bufferedWriter2 != null) {
                        bufferedWriter2.close();
                        bufferedWriter = bufferedWriter2;
                    }
                } catch (java.lang.Throwable th) {
                    th = th;
                    bufferedWriter = bufferedWriter3;
                    if (bufferedWriter != null) {
                        bufferedWriter.close();
                    }
                    throw th;
                }
            } catch (java.lang.Exception e2) {
                e = e2;
            }
        } catch (java.lang.Throwable th2) {
            th = th2;
        }
    }

    private static java.lang.String[] getStyleAttributesDump(android.content.res.Resources resources, android.content.res.Resources.Theme theme) {
        android.util.TypedValue typedValue = new android.util.TypedValue();
        int[] allAttributes = theme.getAllAttributes();
        java.lang.String[] strArr = new java.lang.String[allAttributes.length * 2];
        int i = 0;
        for (int i2 : allAttributes) {
            try {
                strArr[i] = resources.getResourceName(i2);
                strArr[i + 1] = theme.resolveAttribute(i2, typedValue, true) ? typedValue.coerceToString().toString() : "null";
                i += 2;
                if (typedValue.type == 1) {
                    strArr[i - 1] = resources.getResourceName(typedValue.resourceId);
                }
            } catch (android.content.res.Resources.NotFoundException e) {
            }
        }
        return strArr;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static android.view.View findView(android.view.ViewGroup viewGroup, java.lang.String str, int i) {
        android.view.View findHierarchyView;
        android.view.View findView;
        if (isRequestedView(viewGroup, str, i)) {
            return viewGroup;
        }
        int childCount = viewGroup.getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            android.view.View childAt = viewGroup.getChildAt(i2);
            if (childAt instanceof android.view.ViewGroup) {
                android.view.View findView2 = findView((android.view.ViewGroup) childAt, str, i);
                if (findView2 != null) {
                    return findView2;
                }
            } else if (isRequestedView(childAt, str, i)) {
                return childAt;
            }
            if (childAt.mOverlay != null && (findView = findView(childAt.mOverlay.mOverlayViewGroup, str, i)) != null) {
                return findView;
            }
            if ((childAt instanceof android.view.ViewDebug.HierarchyHandler) && (findHierarchyView = ((android.view.ViewDebug.HierarchyHandler) childAt).findHierarchyView(str, i)) != null) {
                return findHierarchyView;
            }
        }
        return null;
    }

    private static boolean isRequestedView(android.view.View view, java.lang.String str, int i) {
        if (view.hashCode() == i) {
            java.lang.String name = view.getClass().getName();
            if (str.equals("ViewOverlay")) {
                return name.equals("android.view.ViewOverlay$OverlayViewGroup");
            }
            return str.equals(name);
        }
        return false;
    }

    private static void dumpViewHierarchy(final android.content.Context context, final android.view.ViewGroup viewGroup, final java.io.BufferedWriter bufferedWriter, final int i, final boolean z, final boolean z2) {
        cacheExportedProperties(viewGroup.getClass());
        if (!z) {
            cacheExportedPropertiesForChildren(viewGroup);
        }
        android.os.Handler handler = viewGroup.getHandler();
        if (handler == null) {
            handler = new android.os.Handler(android.os.Looper.getMainLooper());
        }
        if (handler.getLooper() == android.os.Looper.myLooper()) {
            dumpViewHierarchyOnUIThread(context, viewGroup, bufferedWriter, i, z, z2);
            return;
        }
        java.util.concurrent.FutureTask futureTask = new java.util.concurrent.FutureTask(new java.lang.Runnable() { // from class: android.view.ViewDebug$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                android.view.ViewDebug.dumpViewHierarchyOnUIThread(android.content.Context.this, viewGroup, bufferedWriter, i, z, z2);
            }
        }, null);
        android.os.Message obtain = android.os.Message.obtain(handler, futureTask);
        obtain.setAsynchronous(true);
        handler.sendMessage(obtain);
        while (true) {
            try {
                futureTask.get(6000L, java.util.concurrent.TimeUnit.MILLISECONDS);
                return;
            } catch (java.lang.InterruptedException e) {
            } catch (java.util.concurrent.ExecutionException | java.util.concurrent.TimeoutException e2) {
                throw new java.lang.RuntimeException(e2);
            }
        }
    }

    private static void cacheExportedPropertiesForChildren(android.view.ViewGroup viewGroup) {
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            android.view.View childAt = viewGroup.getChildAt(i);
            cacheExportedProperties(childAt.getClass());
            if (childAt instanceof android.view.ViewGroup) {
                cacheExportedPropertiesForChildren((android.view.ViewGroup) childAt);
            }
        }
    }

    private static void cacheExportedProperties(java.lang.Class<?> cls) {
        if (sExportProperties != null && sExportProperties.containsKey(cls)) {
            return;
        }
        do {
            for (android.view.ViewDebug.PropertyInfo<android.view.ViewDebug.ExportedProperty, ?> propertyInfo : getExportedProperties(cls)) {
                if (!propertyInfo.returnType.isPrimitive() && propertyInfo.property.deepExport()) {
                    cacheExportedProperties(propertyInfo.returnType);
                }
            }
            cls = cls.getSuperclass();
        } while (cls != java.lang.Object.class);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public static void dumpViewHierarchyOnUIThread(android.content.Context context, android.view.ViewGroup viewGroup, java.io.BufferedWriter bufferedWriter, int i, boolean z, boolean z2) {
        if (!dumpView(context, viewGroup, bufferedWriter, i, z2) || z) {
            return;
        }
        int childCount = viewGroup.getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            android.view.View childAt = viewGroup.getChildAt(i2);
            if (childAt instanceof android.view.ViewGroup) {
                dumpViewHierarchyOnUIThread(context, (android.view.ViewGroup) childAt, bufferedWriter, i + 1, z, z2);
            } else {
                dumpView(context, childAt, bufferedWriter, i + 1, z2);
            }
            if (childAt.mOverlay != null) {
                dumpViewHierarchyOnUIThread(context, childAt.getOverlay().mOverlayViewGroup, bufferedWriter, i + 2, z, z2);
            }
        }
        if (viewGroup instanceof android.view.ViewDebug.HierarchyHandler) {
            ((android.view.ViewDebug.HierarchyHandler) viewGroup).dumpViewHierarchyWithProperties(bufferedWriter, i + 1);
        }
    }

    private static boolean dumpView(android.content.Context context, android.view.View view, java.io.BufferedWriter bufferedWriter, int i, boolean z) {
        for (int i2 = 0; i2 < i; i2++) {
            try {
                bufferedWriter.write(32);
            } catch (java.io.IOException e) {
                android.util.Log.w("View", "Error while dumping hierarchy tree");
                return false;
            }
        }
        java.lang.String name = view.getClass().getName();
        if (name.equals("android.view.ViewOverlay$OverlayViewGroup")) {
            name = "ViewOverlay";
        }
        bufferedWriter.write(name);
        bufferedWriter.write(64);
        bufferedWriter.write(java.lang.Integer.toHexString(view.hashCode()));
        bufferedWriter.write(32);
        if (z) {
            dumpViewProperties(context, view, bufferedWriter);
        }
        bufferedWriter.newLine();
        return true;
    }

    private static <T extends java.lang.annotation.Annotation> android.view.ViewDebug.PropertyInfo<T, ?>[] convertToPropertyInfos(java.lang.reflect.Method[] methodArr, java.lang.reflect.Field[] fieldArr, final java.lang.Class<T> cls) {
        return (android.view.ViewDebug.PropertyInfo[]) java.util.stream.Stream.of((java.lang.Object[]) new java.util.stream.Stream[]{java.util.Arrays.stream(methodArr).map(new java.util.function.Function() { // from class: android.view.ViewDebug$$ExternalSyntheticLambda4
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                android.view.ViewDebug.PropertyInfo forMethod;
                forMethod = android.view.ViewDebug.PropertyInfo.forMethod((java.lang.reflect.Method) obj, cls);
                return forMethod;
            }
        }), java.util.Arrays.stream(fieldArr).map(new java.util.function.Function() { // from class: android.view.ViewDebug$$ExternalSyntheticLambda5
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                android.view.ViewDebug.PropertyInfo forField;
                forField = android.view.ViewDebug.PropertyInfo.forField((java.lang.reflect.Field) obj, cls);
                return forField;
            }
        })}).flatMap(java.util.function.Function.identity()).filter(new java.util.function.Predicate() { // from class: android.view.ViewDebug$$ExternalSyntheticLambda6
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                return android.view.ViewDebug.lambda$convertToPropertyInfos$8(obj);
            }
        }).toArray(new java.util.function.IntFunction() { // from class: android.view.ViewDebug$$ExternalSyntheticLambda7
            @Override // java.util.function.IntFunction
            public final java.lang.Object apply(int i) {
                return android.view.ViewDebug.lambda$convertToPropertyInfos$9(i);
            }
        });
    }

    static /* synthetic */ boolean lambda$convertToPropertyInfos$8(java.lang.Object obj) {
        return obj != null;
    }

    static /* synthetic */ android.view.ViewDebug.PropertyInfo[] lambda$convertToPropertyInfos$9(int i) {
        return new android.view.ViewDebug.PropertyInfo[i];
    }

    private static android.view.ViewDebug.PropertyInfo<android.view.ViewDebug.ExportedProperty, ?>[] getExportedProperties(java.lang.Class<?> cls) {
        if (sExportProperties == null) {
            sExportProperties = new java.util.HashMap<>();
        }
        java.util.HashMap<java.lang.Class<?>, android.view.ViewDebug.PropertyInfo<android.view.ViewDebug.ExportedProperty, ?>[]> hashMap = sExportProperties;
        android.view.ViewDebug.PropertyInfo<android.view.ViewDebug.ExportedProperty, ?>[] propertyInfoArr = sExportProperties.get(cls);
        if (propertyInfoArr == null) {
            android.view.ViewDebug.PropertyInfo<android.view.ViewDebug.ExportedProperty, ?>[] convertToPropertyInfos = convertToPropertyInfos(cls.getDeclaredMethods(), cls.getDeclaredFields(), android.view.ViewDebug.ExportedProperty.class);
            hashMap.put(cls, convertToPropertyInfos);
            return convertToPropertyInfos;
        }
        return propertyInfoArr;
    }

    private static void dumpViewProperties(android.content.Context context, java.lang.Object obj, java.io.BufferedWriter bufferedWriter) throws java.io.IOException {
        dumpViewProperties(context, obj, bufferedWriter, "");
    }

    private static void dumpViewProperties(android.content.Context context, java.lang.Object obj, java.io.BufferedWriter bufferedWriter, java.lang.String str) throws java.io.IOException {
        if (obj == null) {
            bufferedWriter.write(str + "=4,null ");
            return;
        }
        java.lang.Class<?> cls = obj.getClass();
        do {
            writeExportedProperties(context, obj, bufferedWriter, cls, str);
            cls = cls.getSuperclass();
        } while (cls != java.lang.Object.class);
    }

    private static java.lang.String formatIntToHexString(int i) {
        return "0x" + java.lang.Integer.toHexString(i).toUpperCase();
    }

    private static void writeExportedProperties(android.content.Context context, java.lang.Object obj, java.io.BufferedWriter bufferedWriter, java.lang.Class<?> cls, java.lang.String str) throws java.io.IOException {
        java.lang.Object invoke;
        java.lang.String str2;
        boolean z;
        for (android.view.ViewDebug.PropertyInfo<android.view.ViewDebug.ExportedProperty, ?> propertyInfo : getExportedProperties(cls)) {
            try {
                invoke = propertyInfo.invoke(obj);
                str2 = propertyInfo.property.category().length() != 0 ? propertyInfo.property.category() + ":" : "";
            } catch (java.lang.Exception e) {
            }
            if (propertyInfo.returnType == java.lang.Integer.TYPE || propertyInfo.returnType == java.lang.Byte.TYPE) {
                if (propertyInfo.property.resolveId() && context != null) {
                    invoke = resolveId(context, ((java.lang.Integer) invoke).intValue());
                } else if (propertyInfo.property.formatToHexString()) {
                    if (propertyInfo.returnType == java.lang.Integer.TYPE) {
                        invoke = formatIntToHexString(((java.lang.Integer) invoke).intValue());
                    } else if (propertyInfo.returnType == java.lang.Byte.TYPE) {
                        invoke = "0x" + libcore.util.HexEncoding.encodeToString(((java.lang.Byte) invoke).byteValue(), true);
                    }
                } else {
                    android.view.ViewDebug.FlagToString[] flagMapping = propertyInfo.property.flagMapping();
                    if (flagMapping.length > 0) {
                        exportUnrolledFlags(bufferedWriter, flagMapping, ((java.lang.Integer) invoke).intValue(), str2 + str + propertyInfo.name + '_');
                    }
                    android.view.ViewDebug.IntToString[] mapping = propertyInfo.property.mapping();
                    if (mapping.length > 0) {
                        int intValue = ((java.lang.Integer) invoke).intValue();
                        int length = mapping.length;
                        int i = 0;
                        while (true) {
                            if (i >= length) {
                                z = false;
                                break;
                            }
                            android.view.ViewDebug.IntToString intToString = mapping[i];
                            if (intToString.from() != intValue) {
                                i++;
                            } else {
                                invoke = intToString.to();
                                z = true;
                                break;
                            }
                        }
                        if (!z) {
                            invoke = java.lang.Integer.valueOf(intValue);
                        }
                    }
                }
            } else {
                if (propertyInfo.returnType == int[].class) {
                    exportUnrolledArray(context, bufferedWriter, propertyInfo.property, (int[]) invoke, str2 + str + propertyInfo.name + '_', propertyInfo.entrySuffix);
                } else if (propertyInfo.returnType == java.lang.String[].class) {
                    java.lang.String[] strArr = (java.lang.String[]) invoke;
                    if (propertyInfo.property.hasAdjacentMapping() && strArr != null) {
                        for (int i2 = 0; i2 < strArr.length; i2 += 2) {
                            if (strArr[i2] != null) {
                                int i3 = i2 + 1;
                                writeEntry(bufferedWriter, str2 + str, strArr[i2], propertyInfo.entrySuffix, strArr[i3] == null ? "null" : strArr[i3]);
                            }
                        }
                    }
                } else if (!propertyInfo.returnType.isPrimitive() && propertyInfo.property.deepExport()) {
                    dumpViewProperties(context, invoke, bufferedWriter, str + propertyInfo.property.prefix());
                }
            }
            writeEntry(bufferedWriter, str2 + str, propertyInfo.name, propertyInfo.entrySuffix, invoke);
        }
    }

    private static void writeEntry(java.io.BufferedWriter bufferedWriter, java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.Object obj) throws java.io.IOException {
        bufferedWriter.write(str);
        bufferedWriter.write(str2);
        bufferedWriter.write(str3);
        bufferedWriter.write("=");
        writeValue(bufferedWriter, obj);
        bufferedWriter.write(32);
    }

    private static void exportUnrolledFlags(java.io.BufferedWriter bufferedWriter, android.view.ViewDebug.FlagToString[] flagToStringArr, int i, java.lang.String str) throws java.io.IOException {
        for (android.view.ViewDebug.FlagToString flagToString : flagToStringArr) {
            boolean outputIf = flagToString.outputIf();
            int mask = flagToString.mask() & i;
            boolean z = mask == flagToString.equals();
            if ((z && outputIf) || (!z && !outputIf)) {
                writeEntry(bufferedWriter, str, flagToString.name(), "", formatIntToHexString(mask));
            }
        }
    }

    public static java.lang.String intToString(java.lang.Class<?> cls, java.lang.String str, int i) {
        android.view.ViewDebug.IntToString[] mapping = getMapping(cls, str);
        if (mapping == null) {
            return java.lang.Integer.toString(i);
        }
        for (android.view.ViewDebug.IntToString intToString : mapping) {
            if (intToString.from() == i) {
                return intToString.to();
            }
        }
        return java.lang.Integer.toString(i);
    }

    public static java.lang.String flagsToString(java.lang.Class<?> cls, java.lang.String str, int i) {
        android.view.ViewDebug.FlagToString[] flagMapping = getFlagMapping(cls, str);
        if (flagMapping == null) {
            return java.lang.Integer.toHexString(i);
        }
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        int length = flagMapping.length;
        int i2 = 0;
        while (true) {
            if (i2 >= length) {
                break;
            }
            android.view.ViewDebug.FlagToString flagToString = flagMapping[i2];
            boolean outputIf = flagToString.outputIf();
            if (((flagToString.mask() & i) == flagToString.equals()) && outputIf) {
                sb.append(flagToString.name()).append(' ');
            }
            i2++;
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    private static android.view.ViewDebug.FlagToString[] getFlagMapping(java.lang.Class<?> cls, java.lang.String str) {
        try {
            return ((android.view.ViewDebug.ExportedProperty) cls.getDeclaredField(str).getAnnotation(android.view.ViewDebug.ExportedProperty.class)).flagMapping();
        } catch (java.lang.NoSuchFieldException e) {
            return null;
        }
    }

    private static android.view.ViewDebug.IntToString[] getMapping(java.lang.Class<?> cls, java.lang.String str) {
        try {
            return ((android.view.ViewDebug.ExportedProperty) cls.getDeclaredField(str).getAnnotation(android.view.ViewDebug.ExportedProperty.class)).mapping();
        } catch (java.lang.NoSuchFieldException e) {
            return null;
        }
    }

    private static void exportUnrolledArray(android.content.Context context, java.io.BufferedWriter bufferedWriter, android.view.ViewDebug.ExportedProperty exportedProperty, int[] iArr, java.lang.String str, java.lang.String str2) throws java.io.IOException {
        java.lang.String str3;
        android.view.ViewDebug.IntToString[] indexMapping = exportedProperty.indexMapping();
        boolean z = indexMapping.length > 0;
        android.view.ViewDebug.IntToString[] mapping = exportedProperty.mapping();
        boolean z2 = mapping.length > 0;
        boolean z3 = exportedProperty.resolveId() && context != null;
        int length = iArr.length;
        for (int i = 0; i < length; i++) {
            int i2 = iArr[i];
            java.lang.String valueOf = java.lang.String.valueOf(i);
            if (z) {
                int length2 = indexMapping.length;
                int i3 = 0;
                while (true) {
                    if (i3 >= length2) {
                        break;
                    }
                    android.view.ViewDebug.IntToString intToString = indexMapping[i3];
                    if (intToString.from() != i) {
                        i3++;
                    } else {
                        valueOf = intToString.to();
                        break;
                    }
                }
            }
            if (z2) {
                for (android.view.ViewDebug.IntToString intToString2 : mapping) {
                    if (intToString2.from() == i2) {
                        str3 = intToString2.to();
                        break;
                    }
                }
            }
            str3 = null;
            if (z3) {
                if (str3 == null) {
                    str3 = (java.lang.String) resolveId(context, i2);
                }
            } else {
                str3 = java.lang.String.valueOf(i2);
            }
            writeEntry(bufferedWriter, str, valueOf, str2, str3);
        }
    }

    static java.lang.Object resolveId(android.content.Context context, int i) {
        android.content.res.Resources resources = context.getResources();
        if (i >= 0) {
            try {
                return resources.getResourceTypeName(i) + '/' + resources.getResourceEntryName(i);
            } catch (android.content.res.Resources.NotFoundException e) {
                return "id/" + formatIntToHexString(i);
            }
        }
        return "NO_ID";
    }

    private static void writeValue(java.io.BufferedWriter bufferedWriter, java.lang.Object obj) throws java.io.IOException {
        if (obj != null) {
            try {
                java.lang.String replace = obj.toString().replace("\n", "\\n");
                bufferedWriter.write(java.lang.String.valueOf(replace.length()));
                bufferedWriter.write(",");
                bufferedWriter.write(replace);
                return;
            } catch (java.lang.Throwable th) {
                bufferedWriter.write(java.lang.String.valueOf("[EXCEPTION]".length()));
                bufferedWriter.write(",");
                bufferedWriter.write("[EXCEPTION]");
                throw th;
            }
        }
        bufferedWriter.write("4,null");
    }

    private static android.view.ViewDebug.PropertyInfo<android.view.ViewDebug.CapturedViewProperty, ?>[] getCapturedViewProperties(java.lang.Class<?> cls) {
        if (sCapturedViewProperties == null) {
            sCapturedViewProperties = new java.util.HashMap<>();
        }
        java.util.HashMap<java.lang.Class<?>, android.view.ViewDebug.PropertyInfo<android.view.ViewDebug.CapturedViewProperty, ?>[]> hashMap = sCapturedViewProperties;
        android.view.ViewDebug.PropertyInfo<android.view.ViewDebug.CapturedViewProperty, ?>[] propertyInfoArr = hashMap.get(cls);
        if (propertyInfoArr == null) {
            android.view.ViewDebug.PropertyInfo<android.view.ViewDebug.CapturedViewProperty, ?>[] convertToPropertyInfos = convertToPropertyInfos(cls.getMethods(), cls.getFields(), android.view.ViewDebug.CapturedViewProperty.class);
            hashMap.put(cls, convertToPropertyInfos);
            return convertToPropertyInfos;
        }
        return propertyInfoArr;
    }

    private static java.lang.String exportCapturedViewProperties(java.lang.Object obj, java.lang.Class<?> cls, java.lang.String str) {
        if (obj == null) {
            return "null";
        }
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        for (android.view.ViewDebug.PropertyInfo<android.view.ViewDebug.CapturedViewProperty, ?> propertyInfo : getCapturedViewProperties(cls)) {
            try {
                java.lang.Object invoke = propertyInfo.invoke(obj);
                if (propertyInfo.property.retrieveReturn()) {
                    sb.append(exportCapturedViewProperties(invoke, propertyInfo.returnType, propertyInfo.name + "#"));
                } else {
                    sb.append(str).append(propertyInfo.name).append(propertyInfo.entrySuffix).append("=");
                    if (invoke != null) {
                        sb.append(invoke.toString().replace("\n", "\\n"));
                    } else {
                        sb.append("null");
                    }
                    sb.append(propertyInfo.valueSuffix).append(" ");
                }
            } catch (java.lang.Exception e) {
            }
        }
        return sb.toString();
    }

    public static void dumpCapturedView(java.lang.String str, java.lang.Object obj) {
        java.lang.Class<?> cls = obj.getClass();
        android.util.Log.d(str, (cls.getName() + ": ") + exportCapturedViewProperties(obj, cls, ""));
    }

    private static void invokeViewMethod(android.view.View view, java.io.OutputStream outputStream, java.lang.String[] strArr) throws java.io.IOException {
        byte[] decode;
        java.io.BufferedWriter bufferedWriter = new java.io.BufferedWriter(new java.io.OutputStreamWriter(outputStream), 32768);
        try {
            try {
            } catch (java.lang.Exception e) {
                bufferedWriter.write("-1");
                bufferedWriter.newLine();
                bufferedWriter.write(e.getMessage());
                bufferedWriter.newLine();
            }
            if (strArr.length < 2) {
                throw new java.lang.IllegalArgumentException("Missing parameter");
            }
            android.view.View findView = findView(view, strArr[0]);
            if (findView == null) {
                throw new java.lang.IllegalArgumentException("View not found: " + strArr[0]);
            }
            java.lang.String str = strArr[1];
            if (strArr.length < 2) {
                decode = new byte[0];
            } else {
                decode = android.util.Base64.decode(strArr[2], 2);
            }
            byte[] invokeViewMethod = invokeViewMethod(findView, str, java.nio.ByteBuffer.wrap(decode));
            bufferedWriter.write("1");
            bufferedWriter.newLine();
            bufferedWriter.write(android.util.Base64.encodeToString(invokeViewMethod, 2));
            bufferedWriter.newLine();
        } finally {
            bufferedWriter.close();
        }
    }

    public static byte[] invokeViewMethod(final android.view.View view, java.lang.String str, java.nio.ByteBuffer byteBuffer) throws android.view.ViewDebug.ViewMethodInvocationSerializationException {
        final java.lang.Object[] objArr;
        java.lang.Class<?>[] clsArr;
        if (!byteBuffer.hasRemaining()) {
            clsArr = new java.lang.Class[0];
            objArr = new java.lang.Object[0];
        } else {
            int i = byteBuffer.getInt();
            java.lang.Class<?>[] clsArr2 = new java.lang.Class[i];
            java.lang.Object[] objArr2 = new java.lang.Object[i];
            deserializeMethodParameters(objArr2, clsArr2, byteBuffer);
            objArr = objArr2;
            clsArr = clsArr2;
        }
        try {
            final java.lang.reflect.Method method = view.getClass().getMethod(str, clsArr);
            try {
                java.util.concurrent.FutureTask futureTask = new java.util.concurrent.FutureTask(new java.util.concurrent.Callable() { // from class: android.view.ViewDebug$$ExternalSyntheticLambda3
                    @Override // java.util.concurrent.Callable
                    public final java.lang.Object call() {
                        java.lang.Object invoke;
                        invoke = method.invoke(view, objArr);
                        return invoke;
                    }
                });
                view.post(futureTask);
                java.lang.Object obj = futureTask.get();
                java.lang.Class<?> returnType = method.getReturnType();
                return serializeReturnValue(returnType, returnType.cast(obj));
            } catch (java.lang.Exception e) {
                android.util.Log.e(TAG, "Exception while invoking method: " + e.getCause().getMessage());
                java.lang.String message = e.getCause().getMessage();
                if (message == null) {
                    message = e.getCause().toString();
                }
                throw new java.lang.RuntimeException(message);
            }
        } catch (java.lang.NoSuchMethodException e2) {
            android.util.Log.e(TAG, "No such method: " + e2.getMessage());
            throw new android.view.ViewDebug.ViewMethodInvocationSerializationException("No such method: " + e2.getMessage());
        }
    }

    public static void setLayoutParameter(final android.view.View view, java.lang.String str, int i) throws java.lang.NoSuchFieldException, java.lang.IllegalAccessException {
        final android.view.ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        java.lang.reflect.Field field = layoutParams.getClass().getField(str);
        if (field.getType() != java.lang.Integer.TYPE) {
            throw new java.lang.RuntimeException("Only integer layout parameters can be set. Field " + str + " is of type " + field.getType().getSimpleName());
        }
        field.set(layoutParams, java.lang.Integer.valueOf(i));
        view.post(new java.lang.Runnable() { // from class: android.view.ViewDebug.4
            @Override // java.lang.Runnable
            public void run() {
                android.view.View.this.setLayoutParams(layoutParams);
            }
        });
    }

    public static class SoftwareCanvasProvider implements android.view.ViewDebug.CanvasProvider {
        private android.graphics.Bitmap mBitmap;
        private android.graphics.Canvas mCanvas;
        private boolean mEnabledHwFeaturesInSwMode;

        @Override // android.view.ViewDebug.CanvasProvider
        public android.graphics.Canvas getCanvas(android.view.View view, int i, int i2) {
            this.mBitmap = android.graphics.Bitmap.createBitmap(view.getResources().getDisplayMetrics(), i, i2, android.graphics.Bitmap.Config.ARGB_8888);
            if (this.mBitmap == null) {
                throw new java.lang.OutOfMemoryError();
            }
            this.mBitmap.setDensity(view.getResources().getDisplayMetrics().densityDpi);
            if (view.mAttachInfo != null) {
                this.mCanvas = view.mAttachInfo.mCanvas;
            }
            if (this.mCanvas == null) {
                this.mCanvas = new android.graphics.Canvas();
            }
            this.mEnabledHwFeaturesInSwMode = this.mCanvas.isHwFeaturesInSwModeEnabled();
            this.mCanvas.setBitmap(this.mBitmap);
            return this.mCanvas;
        }

        @Override // android.view.ViewDebug.CanvasProvider
        public android.graphics.Bitmap createBitmap() {
            this.mCanvas.setBitmap(null);
            this.mCanvas.setHwFeaturesInSwModeEnabled(this.mEnabledHwFeaturesInSwMode);
            return this.mBitmap;
        }
    }

    public static class HardwareCanvasProvider implements android.view.ViewDebug.CanvasProvider {
        private android.graphics.Picture mPicture;

        @Override // android.view.ViewDebug.CanvasProvider
        public android.graphics.Canvas getCanvas(android.view.View view, int i, int i2) {
            this.mPicture = new android.graphics.Picture();
            return this.mPicture.beginRecording(i, i2);
        }

        @Override // android.view.ViewDebug.CanvasProvider
        public android.graphics.Bitmap createBitmap() {
            this.mPicture.endRecording();
            return android.graphics.Bitmap.createBitmap(this.mPicture);
        }
    }

    public static void deserializeMethodParameters(java.lang.Object[] objArr, java.lang.Class<?>[] clsArr, java.nio.ByteBuffer byteBuffer) throws android.view.ViewDebug.ViewMethodInvocationSerializationException {
        com.android.internal.util.Preconditions.checkArgument(objArr.length == clsArr.length);
        for (int i = 0; i < objArr.length; i++) {
            char c = byteBuffer.getChar();
            if (c == '[') {
                if (byteBuffer.getChar() != 'B') {
                    throw new android.view.ViewDebug.ViewMethodInvocationSerializationException("Unsupported array parameter type (" + c + ") to invoke view method @argument " + i);
                }
                int i2 = byteBuffer.getInt();
                if (i2 > byteBuffer.remaining()) {
                    throw new java.nio.BufferUnderflowException();
                }
                byte[] bArr = new byte[i2];
                byteBuffer.get(bArr);
                clsArr[i] = byte[].class;
                objArr[i] = bArr;
            } else {
                switch (c) {
                    case 'B':
                        clsArr[i] = java.lang.Byte.TYPE;
                        objArr[i] = java.lang.Byte.valueOf(byteBuffer.get());
                        break;
                    case 'C':
                        clsArr[i] = java.lang.Character.TYPE;
                        objArr[i] = java.lang.Character.valueOf(byteBuffer.getChar());
                        break;
                    case 'D':
                        clsArr[i] = java.lang.Double.TYPE;
                        objArr[i] = java.lang.Double.valueOf(byteBuffer.getDouble());
                        break;
                    case 'F':
                        clsArr[i] = java.lang.Float.TYPE;
                        objArr[i] = java.lang.Float.valueOf(byteBuffer.getFloat());
                        break;
                    case 'I':
                        clsArr[i] = java.lang.Integer.TYPE;
                        objArr[i] = java.lang.Integer.valueOf(byteBuffer.getInt());
                        break;
                    case 'J':
                        clsArr[i] = java.lang.Long.TYPE;
                        objArr[i] = java.lang.Long.valueOf(byteBuffer.getLong());
                        break;
                    case 'R':
                        clsArr[i] = java.lang.String.class;
                        byte[] bArr2 = new byte[java.lang.Short.toUnsignedInt(byteBuffer.getShort())];
                        byteBuffer.get(bArr2);
                        objArr[i] = new java.lang.String(bArr2, java.nio.charset.StandardCharsets.UTF_8);
                        break;
                    case 'S':
                        clsArr[i] = java.lang.Short.TYPE;
                        objArr[i] = java.lang.Short.valueOf(byteBuffer.getShort());
                        break;
                    case 'Z':
                        clsArr[i] = java.lang.Boolean.TYPE;
                        objArr[i] = java.lang.Boolean.valueOf(byteBuffer.get() != 0);
                        break;
                    default:
                        android.util.Log.e(TAG, "arg " + i + ", unrecognized type: " + c);
                        throw new android.view.ViewDebug.ViewMethodInvocationSerializationException("Unsupported parameter type (" + c + ") to invoke view method.");
                }
            }
        }
    }

    public static byte[] serializeReturnValue(java.lang.Class<?> cls, java.lang.Object obj) throws android.view.ViewDebug.ViewMethodInvocationSerializationException, java.io.IOException {
        java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream(1024);
        java.io.DataOutputStream dataOutputStream = new java.io.DataOutputStream(byteArrayOutputStream);
        if (cls.isArray()) {
            if (!cls.equals(byte[].class)) {
                throw new android.view.ViewDebug.ViewMethodInvocationSerializationException("Unsupported array return type (" + cls + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
            }
            byte[] bArr = (byte[]) obj;
            dataOutputStream.writeChar(91);
            dataOutputStream.writeChar(66);
            dataOutputStream.writeInt(bArr.length);
            dataOutputStream.write(bArr);
        } else if (java.lang.Boolean.TYPE.equals(cls)) {
            dataOutputStream.writeChar(90);
            dataOutputStream.write(((java.lang.Boolean) obj).booleanValue() ? 1 : 0);
        } else if (java.lang.Byte.TYPE.equals(cls)) {
            dataOutputStream.writeChar(66);
            dataOutputStream.writeByte(((java.lang.Byte) obj).byteValue());
        } else if (java.lang.Character.TYPE.equals(cls)) {
            dataOutputStream.writeChar(67);
            dataOutputStream.writeChar(((java.lang.Character) obj).charValue());
        } else if (java.lang.Short.TYPE.equals(cls)) {
            dataOutputStream.writeChar(83);
            dataOutputStream.writeShort(((java.lang.Short) obj).shortValue());
        } else if (java.lang.Integer.TYPE.equals(cls)) {
            dataOutputStream.writeChar(73);
            dataOutputStream.writeInt(((java.lang.Integer) obj).intValue());
        } else if (java.lang.Long.TYPE.equals(cls)) {
            dataOutputStream.writeChar(74);
            dataOutputStream.writeLong(((java.lang.Long) obj).longValue());
        } else if (java.lang.Double.TYPE.equals(cls)) {
            dataOutputStream.writeChar(68);
            dataOutputStream.writeDouble(((java.lang.Double) obj).doubleValue());
        } else if (java.lang.Float.TYPE.equals(cls)) {
            dataOutputStream.writeChar(70);
            dataOutputStream.writeFloat(((java.lang.Float) obj).floatValue());
        } else if (java.lang.String.class.equals(cls)) {
            dataOutputStream.writeChar(82);
            dataOutputStream.writeUTF(obj != null ? (java.lang.String) obj : "");
        } else {
            dataOutputStream.writeChar(86);
        }
        return byteArrayOutputStream.toByteArray();
    }

    public static class ViewMethodInvocationSerializationException extends java.lang.Exception {
        ViewMethodInvocationSerializationException(java.lang.String str) {
            super(str);
        }
    }
}
