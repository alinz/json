package com.github.alinz.json;

public class Token {
    public enum Type {
        STRING,
        NUMBER,
        OPEN_MAP,
        CLOSE_MAP,
        OPEN_ARRAY,
        CLOSE_ARRAY,
        NULL,
        COLON,
        COMMA,
        BOOLEAN
    }

    public final Type type;
    public final String value;

    public Token(final Type type, final String value) {
        this.type = type;
        this.value = value;
    }

    private String typeStringValue(Type type) {
        switch(type) {
            case STRING:
                return "STRING";
            case NUMBER:
                return "NUMBER";
            case OPEN_MAP:
                return "OPEN_MAP";
            case CLOSE_MAP:
                return "CLOSE_MAP";
            case OPEN_ARRAY:
                return "OPEN_ARRAY";
            case CLOSE_ARRAY:
                return "CLOSE_ARRAY";
            case NULL:
                return "NULL";
            case COLON:
                return "COLON";
            case COMMA:
                return "COMMA";
            case BOOLEAN:
                return "BOOLEAN";
            default:
                return "UNKNOWN";
        }
    }

    public String toString() {
        return String.format("Type: %s, Value: %s", typeStringValue(type), value);
    }
}