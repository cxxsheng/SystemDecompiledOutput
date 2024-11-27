package android.media;

/* loaded from: classes2.dex */
class CallbackUtil {
    private static final java.lang.String TAG = "CallbackUtil";

    interface CallbackMethod<T> {
        void callbackMethod(T t);
    }

    interface DispatcherStub {
        void register(boolean z);
    }

    CallbackUtil() {
    }

    static class ListenerInfo<T> {
        final java.util.concurrent.Executor mExecutor;
        final T mListener;

        ListenerInfo(T t, java.util.concurrent.Executor executor) {
            this.mListener = t;
            this.mExecutor = executor;
        }
    }

    static <T> android.media.CallbackUtil.ListenerInfo<T> getListenerInfo(T t, java.util.ArrayList<android.media.CallbackUtil.ListenerInfo<T>> arrayList) {
        if (arrayList == null) {
            return null;
        }
        java.util.Iterator<android.media.CallbackUtil.ListenerInfo<T>> it = arrayList.iterator();
        while (it.hasNext()) {
            android.media.CallbackUtil.ListenerInfo<T> next = it.next();
            if (next.mListener == t) {
                return next;
            }
        }
        return null;
    }

    static <T> boolean hasListener(T t, java.util.ArrayList<android.media.CallbackUtil.ListenerInfo<T>> arrayList) {
        return getListenerInfo(t, arrayList) != null;
    }

    static <T> boolean removeListener(T t, java.util.ArrayList<android.media.CallbackUtil.ListenerInfo<T>> arrayList) {
        android.media.CallbackUtil.ListenerInfo listenerInfo = getListenerInfo(t, arrayList);
        if (listenerInfo != null) {
            arrayList.remove(listenerInfo);
            return true;
        }
        return false;
    }

    static <T, S> android.util.Pair<java.util.ArrayList<android.media.CallbackUtil.ListenerInfo<T>>, S> addListener(java.lang.String str, java.util.concurrent.Executor executor, T t, java.util.ArrayList<android.media.CallbackUtil.ListenerInfo<T>> arrayList, S s, java.util.function.Supplier<S> supplier, java.util.function.Consumer<S> consumer) {
        java.util.Objects.requireNonNull(executor);
        java.util.Objects.requireNonNull(t);
        if (hasListener(t, arrayList)) {
            throw new java.lang.IllegalArgumentException("attempt to call " + str + "on a previously registered listener");
        }
        if (arrayList == null) {
            arrayList = new java.util.ArrayList<>();
        }
        if (arrayList.size() == 0) {
            if (s == null) {
                try {
                    s = supplier.get();
                } catch (java.lang.Exception e) {
                    android.util.Log.e(TAG, "Exception while creating stub in " + str, e);
                    return new android.util.Pair<>(null, null);
                }
            }
            consumer.accept(s);
        }
        arrayList.add(new android.media.CallbackUtil.ListenerInfo<>(t, executor));
        return new android.util.Pair<>(arrayList, s);
    }

    static <T, S> android.util.Pair<java.util.ArrayList<android.media.CallbackUtil.ListenerInfo<T>>, S> removeListener(java.lang.String str, T t, java.util.ArrayList<android.media.CallbackUtil.ListenerInfo<T>> arrayList, S s, java.util.function.Consumer<S> consumer) {
        java.util.Objects.requireNonNull(t);
        if (!removeListener(t, arrayList)) {
            throw new java.lang.IllegalArgumentException("attempt to call " + str + " on an unregistered listener");
        }
        if (arrayList.size() == 0) {
            consumer.accept(s);
            return new android.util.Pair<>(null, null);
        }
        return new android.util.Pair<>(arrayList, s);
    }

    static <T> void callListeners(java.util.ArrayList<android.media.CallbackUtil.ListenerInfo<T>> arrayList, java.lang.Object obj, final android.media.CallbackUtil.CallbackMethod<T> callbackMethod) {
        java.util.Objects.requireNonNull(obj);
        synchronized (obj) {
            if (arrayList != null) {
                if (arrayList.size() != 0) {
                    java.util.ArrayList arrayList2 = (java.util.ArrayList) arrayList.clone();
                    android.media.permission.SafeCloseable create = android.media.permission.ClearCallingIdentityContext.create();
                    try {
                        java.util.Iterator it = arrayList2.iterator();
                        while (it.hasNext()) {
                            final android.media.CallbackUtil.ListenerInfo listenerInfo = (android.media.CallbackUtil.ListenerInfo) it.next();
                            listenerInfo.mExecutor.execute(new java.lang.Runnable() { // from class: android.media.CallbackUtil$$ExternalSyntheticLambda0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    android.media.CallbackUtil.CallbackMethod.this.callbackMethod(listenerInfo.mListener);
                                }
                            });
                        }
                        if (create != null) {
                            create.close();
                        }
                    } catch (java.lang.Throwable th) {
                        if (create != null) {
                            try {
                                create.close();
                            } catch (java.lang.Throwable th2) {
                                th.addSuppressed(th2);
                            }
                        }
                        throw th;
                    }
                }
            }
        }
    }

    static class LazyListenerManager<T> {
        private android.media.CallbackUtil.DispatcherStub mDispatcherStub;
        private final java.lang.Object mListenerLock = new java.lang.Object();
        private java.util.ArrayList<android.media.CallbackUtil.ListenerInfo<T>> mListeners;

        LazyListenerManager() {
        }

        /* JADX WARN: Multi-variable type inference failed */
        void addListener(java.util.concurrent.Executor executor, T t, java.lang.String str, java.util.function.Supplier<android.media.CallbackUtil.DispatcherStub> supplier) {
            synchronized (this.mListenerLock) {
                android.util.Pair addListener = android.media.CallbackUtil.addListener(str, executor, t, this.mListeners, this.mDispatcherStub, supplier, new java.util.function.Consumer() { // from class: android.media.CallbackUtil$LazyListenerManager$$ExternalSyntheticLambda1
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        ((android.media.CallbackUtil.DispatcherStub) obj).register(true);
                    }
                });
                this.mListeners = (java.util.ArrayList) addListener.first;
                this.mDispatcherStub = (android.media.CallbackUtil.DispatcherStub) addListener.second;
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        void removeListener(T t, java.lang.String str) {
            synchronized (this.mListenerLock) {
                android.util.Pair removeListener = android.media.CallbackUtil.removeListener(str, t, this.mListeners, this.mDispatcherStub, new java.util.function.Consumer() { // from class: android.media.CallbackUtil$LazyListenerManager$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        ((android.media.CallbackUtil.DispatcherStub) obj).register(false);
                    }
                });
                this.mListeners = (java.util.ArrayList) removeListener.first;
                this.mDispatcherStub = (android.media.CallbackUtil.DispatcherStub) removeListener.second;
            }
        }

        void callListeners(android.media.CallbackUtil.CallbackMethod<T> callbackMethod) {
            android.media.CallbackUtil.callListeners(this.mListeners, this.mListenerLock, callbackMethod);
        }
    }
}
