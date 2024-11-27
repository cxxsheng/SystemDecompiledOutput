package android.ddm;

/* loaded from: classes.dex */
public class DdmHandleViewDebug extends android.ddm.DdmHandle {
    private static final int ERR_EXCEPTION = -3;
    private static final int ERR_INVALID_OP = -1;
    private static final int ERR_INVALID_PARAM = -2;
    private static final java.lang.String TAG = "DdmViewDebug";
    private static final int VUOP_CAPTURE_VIEW = 1;
    private static final int VUOP_DUMP_DISPLAYLIST = 2;
    private static final int VUOP_INVOKE_VIEW_METHOD = 4;
    private static final int VUOP_PROFILE_VIEW = 3;
    private static final int VUOP_SET_LAYOUT_PARAMETER = 5;
    private static final int VURT_CAPTURE_LAYERS = 2;
    private static final int VURT_DUMP_HIERARCHY = 1;
    private static final int VURT_DUMP_THEME = 3;
    private static final int CHUNK_VULW = org.apache.harmony.dalvik.ddmc.ChunkHandler.type("VULW");
    private static final int CHUNK_VURT = org.apache.harmony.dalvik.ddmc.ChunkHandler.type("VURT");
    private static final int CHUNK_VUOP = org.apache.harmony.dalvik.ddmc.ChunkHandler.type("VUOP");
    private static final android.ddm.DdmHandleViewDebug sInstance = new android.ddm.DdmHandleViewDebug();

    private DdmHandleViewDebug() {
    }

    public static void register() {
        org.apache.harmony.dalvik.ddmc.DdmServer.registerHandler(CHUNK_VULW, sInstance);
        org.apache.harmony.dalvik.ddmc.DdmServer.registerHandler(CHUNK_VURT, sInstance);
        org.apache.harmony.dalvik.ddmc.DdmServer.registerHandler(CHUNK_VUOP, sInstance);
    }

    public void onConnected() {
    }

    public void onDisconnected() {
    }

    public org.apache.harmony.dalvik.ddmc.Chunk handleChunk(org.apache.harmony.dalvik.ddmc.Chunk chunk) {
        int i = chunk.type;
        if (i == CHUNK_VULW) {
            return listWindows();
        }
        java.nio.ByteBuffer wrapChunk = wrapChunk(chunk);
        int i2 = wrapChunk.getInt();
        android.view.View rootView = getRootView(wrapChunk);
        if (rootView == null) {
            return createFailChunk(-2, "Invalid View Root");
        }
        if (i == CHUNK_VURT) {
            if (i2 == 1) {
                return dumpHierarchy(rootView, wrapChunk);
            }
            if (i2 == 2) {
                return captureLayers(rootView);
            }
            if (i2 == 3) {
                return dumpTheme(rootView);
            }
            return createFailChunk(-1, "Unknown view root operation: " + i2);
        }
        android.view.View targetView = getTargetView(rootView, wrapChunk);
        if (targetView == null) {
            return createFailChunk(-2, "Invalid target view");
        }
        if (i == CHUNK_VUOP) {
            switch (i2) {
                case 1:
                    return captureView(rootView, targetView);
                case 2:
                    return dumpDisplayLists(rootView, targetView);
                case 3:
                    return profileView(rootView, targetView);
                case 4:
                    return invokeViewMethod(rootView, targetView, wrapChunk);
                case 5:
                    return setLayoutParameter(rootView, targetView, wrapChunk);
                default:
                    return createFailChunk(-1, "Unknown view operation: " + i2);
            }
        }
        throw new java.lang.RuntimeException("Unknown packet " + name(i));
    }

    private org.apache.harmony.dalvik.ddmc.Chunk listWindows() {
        java.lang.String[] viewRootNames = android.view.WindowManagerGlobal.getInstance().getViewRootNames();
        int i = 4;
        for (java.lang.String str : viewRootNames) {
            i = i + 4 + (str.length() * 2);
        }
        java.nio.ByteBuffer allocate = java.nio.ByteBuffer.allocate(i);
        allocate.order(org.apache.harmony.dalvik.ddmc.ChunkHandler.CHUNK_ORDER);
        allocate.putInt(viewRootNames.length);
        for (java.lang.String str2 : viewRootNames) {
            allocate.putInt(str2.length());
            putString(allocate, str2);
        }
        return new org.apache.harmony.dalvik.ddmc.Chunk(CHUNK_VULW, allocate);
    }

    private android.view.View getRootView(java.nio.ByteBuffer byteBuffer) {
        try {
            return android.view.WindowManagerGlobal.getInstance().getRootView(getString(byteBuffer, byteBuffer.getInt()));
        } catch (java.nio.BufferUnderflowException e) {
            return null;
        }
    }

    private android.view.View getTargetView(android.view.View view, java.nio.ByteBuffer byteBuffer) {
        try {
            return android.view.ViewDebug.findView(view, getString(byteBuffer, byteBuffer.getInt()));
        } catch (java.nio.BufferUnderflowException e) {
            return null;
        }
    }

    private org.apache.harmony.dalvik.ddmc.Chunk dumpHierarchy(android.view.View view, java.nio.ByteBuffer byteBuffer) {
        int i = 1;
        boolean z = byteBuffer.getInt() > 0;
        boolean z2 = byteBuffer.getInt() > 0;
        boolean z3 = byteBuffer.hasRemaining() && byteBuffer.getInt() > 0;
        long currentTimeMillis = java.lang.System.currentTimeMillis();
        java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream(2097152);
        try {
            if (z3) {
                android.view.ViewDebug.dumpv2(view, byteArrayOutputStream);
            } else {
                android.view.ViewDebug.dump(view, z, z2, byteArrayOutputStream);
            }
            android.util.Log.d(TAG, "Time to obtain view hierarchy (ms): " + (java.lang.System.currentTimeMillis() - currentTimeMillis));
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            int i2 = CHUNK_VURT;
            i = byteArray.length;
            return new org.apache.harmony.dalvik.ddmc.Chunk(i2, byteArray, 0, i);
        } catch (java.io.IOException | java.lang.InterruptedException e) {
            return createFailChunk(i, "Unexpected error while obtaining view hierarchy: " + e.getMessage());
        }
    }

    private org.apache.harmony.dalvik.ddmc.Chunk captureLayers(android.view.View view) {
        java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream(1024);
        java.io.DataOutputStream dataOutputStream = new java.io.DataOutputStream(byteArrayOutputStream);
        try {
            try {
                android.view.ViewDebug.captureLayers(view, dataOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                return new org.apache.harmony.dalvik.ddmc.Chunk(CHUNK_VURT, byteArray, 0, byteArray.length);
            } catch (java.io.IOException e) {
                org.apache.harmony.dalvik.ddmc.Chunk createFailChunk = createFailChunk(1, "Unexpected error while obtaining view hierarchy: " + e.getMessage());
                try {
                    dataOutputStream.close();
                } catch (java.io.IOException e2) {
                }
                return createFailChunk;
            }
        } finally {
            try {
                dataOutputStream.close();
            } catch (java.io.IOException e3) {
            }
        }
    }

    private org.apache.harmony.dalvik.ddmc.Chunk dumpTheme(android.view.View view) {
        java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream(1024);
        try {
            android.view.ViewDebug.dumpTheme(view, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            return new org.apache.harmony.dalvik.ddmc.Chunk(CHUNK_VURT, byteArray, 0, byteArray.length);
        } catch (java.io.IOException e) {
            return createFailChunk(1, "Unexpected error while dumping the theme: " + e.getMessage());
        }
    }

    private org.apache.harmony.dalvik.ddmc.Chunk captureView(android.view.View view, android.view.View view2) {
        java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream(1024);
        try {
            android.view.ViewDebug.capture(view, byteArrayOutputStream, view2);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            return new org.apache.harmony.dalvik.ddmc.Chunk(CHUNK_VUOP, byteArray, 0, byteArray.length);
        } catch (java.io.IOException e) {
            return createFailChunk(1, "Unexpected error while capturing view: " + e.getMessage());
        }
    }

    private org.apache.harmony.dalvik.ddmc.Chunk dumpDisplayLists(final android.view.View view, final android.view.View view2) {
        view.post(new java.lang.Runnable() { // from class: android.ddm.DdmHandleViewDebug.1
            @Override // java.lang.Runnable
            public void run() {
                android.view.ViewDebug.outputDisplayList(view, view2);
            }
        });
        return null;
    }

    private org.apache.harmony.dalvik.ddmc.Chunk invokeViewMethod(android.view.View view, android.view.View view2, java.nio.ByteBuffer byteBuffer) {
        try {
            byte[] invokeViewMethod = android.view.ViewDebug.invokeViewMethod(view2, getString(byteBuffer, byteBuffer.getInt()), byteBuffer);
            return new org.apache.harmony.dalvik.ddmc.Chunk(CHUNK_VUOP, invokeViewMethod, 0, invokeViewMethod.length);
        } catch (android.view.ViewDebug.ViewMethodInvocationSerializationException e) {
            return createFailChunk(-2, e.getMessage());
        } catch (java.lang.Exception e2) {
            return createFailChunk(-3, e2.getMessage());
        }
    }

    private org.apache.harmony.dalvik.ddmc.Chunk setLayoutParameter(android.view.View view, android.view.View view2, java.nio.ByteBuffer byteBuffer) {
        java.lang.String string = getString(byteBuffer, byteBuffer.getInt());
        try {
            android.view.ViewDebug.setLayoutParameter(view2, string, byteBuffer.getInt());
            return null;
        } catch (java.lang.Exception e) {
            android.util.Log.e(TAG, "Exception setting layout parameter: " + e);
            return createFailChunk(-3, "Error accessing field " + string + ":" + e.getMessage());
        }
    }

    private org.apache.harmony.dalvik.ddmc.Chunk profileView(android.view.View view, android.view.View view2) {
        java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream(32768);
        java.io.BufferedWriter bufferedWriter = new java.io.BufferedWriter(new java.io.OutputStreamWriter(byteArrayOutputStream), 32768);
        try {
            try {
                android.view.ViewDebug.profileViewAndChildren(view2, bufferedWriter);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                return new org.apache.harmony.dalvik.ddmc.Chunk(CHUNK_VUOP, byteArray, 0, byteArray.length);
            } catch (java.io.IOException e) {
                org.apache.harmony.dalvik.ddmc.Chunk createFailChunk = createFailChunk(1, "Unexpected error while profiling view: " + e.getMessage());
                try {
                    bufferedWriter.close();
                } catch (java.io.IOException e2) {
                }
                return createFailChunk;
            }
        } finally {
            try {
                bufferedWriter.close();
            } catch (java.io.IOException e3) {
            }
        }
    }
}
