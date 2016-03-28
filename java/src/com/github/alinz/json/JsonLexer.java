package com.github.alinz.json;

public class JsonLexer extends Lexer {
    public JsonLexer(String input) {
        super(input);
    }

    private Token lexString() {
        acceptRunUntil("\"");
        String value = value();
        //we need to ignore the " at the end.
        next();
        ignore();
        return new Token(Token.Type.STRING, value);
    }

    private Token lexNumber() {
        accept("+-");
        String digits = "0123456789";
        if (accept("0") && accept("xX")) {
            digits = "0123456789abcdefABCDEF";
        }

        acceptRun(digits);

        if (accept(".")) {
            acceptRun(digits);
        }

        if (accept("eE")) {
            accept("+-");
            acceptRun("0123456789");
        }

        return new Token(Token.Type.NUMBER, value());
    }

    private Token lexNull() {
        if (acceptInSequence("null")) {
            return new Token(Token.Type.NULL, value());
        }

        return null;
    }

    private Token lexBoolean() {
        if (acceptInSequence("false") || acceptInSequence("true")) {
            return new Token(Token.Type.BOOLEAN, value());
        }
        return null;
    }

    public Token nextToken() {
        if (peek() == null) return null;

        //remove white spaces
        this.acceptRun(" \t\r\n");
        this.ignore();

        String n = next();

        switch (n) {
            case "\"":
                ignore();
                return lexString();
            case "f":
            case "t":
                backup();
                return lexBoolean();
            case "n":
                backup();
                return lexNull();
            case "{":
                return new Token(Token.Type.OPEN_MAP, value());
            case "}":
                return new Token(Token.Type.CLOSE_MAP, value());
            case "[":
                return new Token(Token.Type.OPEN_ARRAY, value());
            case "]":
                return new Token(Token.Type.CLOSE_ARRAY, value());
            case ":":
                return new Token(Token.Type.COLON, value());
            case ",":
                return new Token(Token.Type.COMMA, value());
            default:
                if ("+-1234567890".contains(n)) {
                    backup();
                    return lexNumber();
                }
        }


        return null;
    }
}
