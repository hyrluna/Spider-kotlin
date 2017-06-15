package lunax.spider.data;

import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by Bamboo on 6/15/2017.
 */

public class Repository {

    private static Builder INSTANCE;

    public static Builder builder() {
        if (INSTANCE == null) {
            INSTANCE = new Builder();
        }
        return INSTANCE;
    }

    public static class Builder<T> {

        private SpiderDataSource local;
        private SpiderDataSource remote;
        private Method method;
        private Object[] objects;
        private boolean loadFromLocal = false;

        public Builder local(SpiderDataSource l) {
            local = l;
            return this;
        }
        public Builder remote(SpiderDataSource r) {
            remote = r;
            return this;
        }

        public Builder methodArguments(Object... args) {
            objects = args;
            return this;
        }

        public Builder method(String m, Class<?>... parameterTypes) {
            try {
                method = SpiderDataSource.class.getDeclaredMethod(m, parameterTypes);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            return this;
        }
        public Builder loadFromLocal(boolean isLocal) {
            loadFromLocal = isLocal;
            return this;
        }

        @SuppressWarnings("unchecked")
        public Observable<T> build() {
            try {
                Observable<T> remoteTask =  ((Observable<T>) method.invoke(remote, objects));
                Observable<T> localTask =  ((Observable<T>) method.invoke(local, objects));
                if (loadFromLocal) {
                    return localTask;
                } else {
                    return remoteTask;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
