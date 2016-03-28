package com.github.alinz.json;

public class Lexer {
    private int start;
    private int pos;
    private int width;

    private String input;

    public Lexer(String input) {
        this.input = input;
    }

    private boolean contains(String a, String b) {
        if (a != null) {
            return b.contains(a);
        }
        return false;
    }

    public void ignore() {
        start = pos;
    }

    public void backup() {
        pos -= width;
        width = 0;
    }

    public String next() {
        if (pos >= input.length()) {
            width = 0;
            return null;
        }

        String ch = String.valueOf(input.charAt(pos));
        width = 1;
        pos += width;

        return ch;
    }

    public String peek() {
        String ch = next();
        backup();
        return ch;
    }

    public String current() {
        backup();
        return next();
    }

    public String value() {
        return input.substring(start, pos);
    }

    public boolean accept(String valid) {
        if (contains(next(), valid)) {
            return true;
        }
        backup();
        return false;
    }

    public void acceptRun(String valid) {
        while(contains(next(), valid));
        backup();
    }

    public boolean acceptInSequence(String seq) {
        int len = seq.length();
        int forward = 0;
        boolean result = false;

        for (; forward < len; forward++) {
            if (!accept(seq.substring(forward, forward + 1))) {
                result = false;
                break;
            }
            result = true;
        }

        if (!result) {
            for (int i = forward - 1; i > 0; i--) {
                backup();
            }
        }

        return result;
    }

    public void acceptRunUntil(String notValid) {
        while(!contains(next(), notValid));
        backup();
    }

    @Override
    public String toString() {
        return String.format("start: %d\n pos: %d\n width: %d\n", start, pos, width);
    }
}