package org.hw1;

public final class Token {
    public final TokenType type;
    public final String data;
    public Token(TokenType type, String data) {
        this.type = type;
        this.data = data;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Token)) {
            return false;
        }
        var o = (Token)obj;
        return this.type.equals(o.type) && this.data.equals(o.data);
    }

    @Override
    public String toString() {
        return type.toString() + " " + data;
    }
}
