package mcondon.lox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import mcondon.lox.TokenType.*;

public class Scanner {
    private final String source;
    private final List<Token> tokens = new ArrayList<>();

    private int start = 0;
    private int current = 0;
    private int line = 1;

    Scanner(String source) {
        this.source = source;
    }

    List<Token> scanTokens() {
        while (!isAtEnd()) {
            start = current;
            scanToken();
        }

        tokens.add(new Token(TokenType.EOF, "", null, line));
        return tokens;
    }

    private boolean isAtEnd() {
        return current >= source.length();
    }

    private boolean isDigit(char c) {
        return c >= 0 && c <= 9;
    }

    // Number lexing
    // Todo - Add int vs double/float handling
    private void number() {
        while (isDigit(peek())) {
            advance();
        }
        if (peek() == '.') {
            advance();
            while (isDigit(peek())) {
                advance();
            }
        }
        addToken(TokenType.NUMBER, Double.parseDouble(source.substring(start, current)));

    }

    // String lexing - peek until find ending quote
    // Consider adding string escaping
    private void string() {
        while (peek() != '"' && !isAtEnd()) {
            if (peek() == '\n') {
                line++;
            }
            advance();
        }
        if (isAtEnd()) {
            Lox.error(line, "Unterminated String");
            return;
        }
        // Advance one more to capture closing "
        advance();
        String value = source.substring(start + 1, current - 1);
        addToken(TokenType.STRING, value);
    }

    // When checking may-be-two characters, the second character is implied by the
    // first
    // TODO - how to handle pairs which may have multiple followers - eg "+=" vs
    // "++"
    // If the following character matches, consume it and advance pointers.
    // IF not, do not advance
    private boolean match(char expected) {
        if (isAtEnd()) {
            return false;
        }
        if (source.charAt(current) != expected) {
            return false;
        }

        current++;
        return true;
    }

    // Check the next character without ever consuming
    private char peek() {
        if (isAtEnd()) {
            return '\0';
        }
        return source.charAt(current);
    }

    // Increment current char pointer, return next character
    private char advance() {
        return source.charAt(current++);
    }

    private void addToken(TokenType type) {
        addToken(type, null);
    }

    // Append a token to our list of tokens
    private void addToken(TokenType type, Object literal) {
        String text = source.substring(start, current);
        tokens.add(new Token(type, text, literal, line));
    }

    private void scanToken() {
        char c = advance();
        // skip whitespace and newlines
        // TODO - add line-split handling a-la .NET _
        switch (c) {
            case '(':
                addToken(TokenType.LEFT_PAREN);
                break;
            case ')':
                addToken(TokenType.RIGHT_PAREN);
                break;
            case '{':
                addToken(TokenType.LEFT_BRACE);
                break;
            case '}':
                addToken(TokenType.RIGHT_BRACE);
                break;
            case ',':
                addToken(TokenType.COMMA);
                break;
            case '.':
                addToken(TokenType.DOT);
                break;
            case ';':
                addToken(TokenType.SEMICOLON);
                break;
            case '*':
                addToken(TokenType.STAR);
                break;
            case '+':
                if (match('=')) {
                    addToken(TokenType.PLUS_EQUAL);
                } else if (match('+')) {
                    addToken(TokenType.PLUS_PLUS);

                } else {
                    addToken(TokenType.PLUS);
                }
                break;
            case '-':
                if (match('=')) {
                    addToken(TokenType.MINUS_EQUAL);
                } else if (match('-')) {
                    addToken(TokenType.MINUS_MINUS);
                } else {
                    addToken(TokenType.MINUS);
                }
                break;
            case '!':
                addToken(match('=') ? TokenType.BANG_EQUAL : TokenType.BANG);
                break;
            case '=':
                if (match('-')) {
                    addToken(TokenType.EQUAL_MINUS);
                } else if (match('+')) {
                    addToken(TokenType.EQUAL_PLUS);
                } else if (match('=')) {
                    addToken(TokenType.EQUAL_EQUAL);
                } else {
                    addToken(TokenType.EQUAL);
                }
                break;
            case '<':
                addToken(match('=') ? TokenType.LESS_EQUAL : TokenType.LESS);
                break;
            case '>':
                addToken(match('=') ? TokenType.GREATER_EQUAL : TokenType.GREATER);
                break;
            case '/':
                if (match('/')) {
                    while (peek() != '\n' && !isAtEnd()) {
                        advance();
                    }
                } else {
                    addToken(TokenType.SLASH);
                }
                break;
            case '\r':
            case ' ':
            case '\t':
                break;
            case '\n':
                line++;
                break;
            case '"':
                string();
                break;
            default:
                if (isDigit(c)) {
                    number();
                } else {
                    Lox.error(line, "Unexpected Character: " + c);
                    break;
                }
        }

    }

}
