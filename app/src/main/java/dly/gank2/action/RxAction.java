package dly.gank2.action;

import android.support.v4.util.ArrayMap;

/**
 * Object class that hold the type of action and the data we want to attach to it
 */
public class RxAction {
    private final String type;
    private final ArrayMap<String, Object> data;

    RxAction(String type, ArrayMap<String, Object> data) {
        this.type = type;
        this.data = data;
    }

    public static Builder type(String type) {
        return new Builder().with(type);
    }

    public String getType() {
        return type;
    }

    public ArrayMap<String, Object> getData() {
        return data;
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String tag) {
        return (T) data.get(tag);
    }


    //使用静态内部类的方式来构造对象
    public static class Builder {

        private String type;
        private ArrayMap<String, Object> data;

        Builder with(String type) {
            if (type == null) {
                throw new IllegalArgumentException("Type may not be null.");
            }
            this.type = type;
            this.data = new ArrayMap<>();
            return this;
        }


        public RxAction build() {
            if (type == null || type.isEmpty()) {
                throw new IllegalArgumentException("At least one key is required.");
            }
            return new RxAction(type, data);
        }
    }
}