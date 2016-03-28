package com.github.alinz.json;

import java.util.ArrayList;
import java.util.Map;

public class JsonNode<T> {
    public enum Type {
        STRING,
        NUMBER,
        MAP,
        ARRAY,
        BOOLEAN,
        NULL
    }

    final public Type type;
    final public T value;

    public JsonNode(final Type type, final T value) {
        this.type = type;
        this.value = value;
    }

    static public class StringNode extends JsonNode<String> {
        public StringNode(final String value) {
            super(Type.STRING, value);
        }
        public String toString() {
            return "\"" + value + "\"";
        }
    }

    static public class NumberNode extends JsonNode<Double> {
        public NumberNode(final Double value) {
            super(Type.NUMBER, value);
        }
        public String toString() {
            return "" + value;
        }
    }

    static public class MapNode extends JsonNode<Map<String, JsonNode>> {
        public MapNode(final Map<String, JsonNode> value) {
            super(Type.MAP, value);
        }
        public String toString() {
            String map = "{";

            boolean first = true;
            for (String key : value.keySet()) {
                if (first) {
                    map += "\"" + key + "\": " + value.get(key);
                    first = false;
                } else {
                    map += " ,\"" + key + "\": " + value.get(key);
                }
            }

            return map + "}";
        }
    }

    static public class ArrayNode extends JsonNode<ArrayList<JsonNode>> {
        public ArrayNode(final ArrayList<JsonNode> value) {
            super(Type.ARRAY, value);
        }
        public String toString() {
            String array = "[";

            boolean first = true;
            for (JsonNode val : value) {
                if (first) {
                    array += val;
                    first = false;
                } else {
                    array += " ," + val;
                }
            }

            array += "]";

            return array;
        }
    }

    static public class BooleanNode extends JsonNode<Boolean> {
        public BooleanNode(final Boolean value) {
            super(Type.BOOLEAN, value);
        }
        public String toString() {
            return value? "true" : "false";
        }
    }

    static public class NullNode extends JsonNode {
        public NullNode() {
            super(Type.NULL, null);
        }
        public String toString() {
            return "null";
        }
    }
}



