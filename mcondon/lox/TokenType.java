package mcondon.lox;

// TODO: Implement additional operators to make more useful
// * '%' as mod
// * '^' as exponent
// '/*  */' block commenting
// * (maybe) ref and val to implement explicit arg passing 
public enum TokenType {
    // One-Character tokens
    LEFT_PAREN,
    RIGHT_PAREN,
    LEFT_BRACE,
    RIGHT_BRACE,
    COMMA,
    DOT,
    MINUS,
    PLUS,
    SEMICOLON,
    SLASH,
    STAR,
    // May-be-two character tokens
    BANG,
    BANG_EQUAL,
    EQUAL,
    EQUAL_EQUAL,
    GREATER,
    GREATER_EQUAL,
    LESS,
    LESS_EQUAL,
    PLUS_EQUAL,
    EQUAL_PLUS,
    MINUS_EQUAL,
    EQUAL_MINUS,
    PLUS_PLUS,
    MINUS_MINUS,
    // Literals
    IDENTIFIER,
    STRING,
    NUMBER,
    // Keywords
    AND,
    CLASS,
    ELSE,
    FALSE,
    FUN,
    FOR,
    IF,
    NIL,
    OR,
    PRINT,
    RETURN,
    SUPER,
    THIS,
    TRUE,
    VAR,
    WHILE,
    EOF
}
