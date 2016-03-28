package com.github.alinz.json;

public abstract class State {
    private Token token;
    public abstract State action(Lexer lex);
    protected final void setToken(final Token token) { this.token = token; }
    public final Token getToken() { return token; }
}
