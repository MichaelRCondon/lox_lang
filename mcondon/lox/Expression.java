package mcondon.lox;
import java.util.List;

abstract class Expression {
    interface Visitor<R> {
    R visitBinaryExpression(Binary expression);
    R visitGroupingExpression(Grouping expression);
    R visitLiteralExpression(Literal expression);
    R visitUnaryExpression(Unary expression);
    }
 static class Binary extends Expression {
//Contructor
    Binary(Expression left, Token Operator, Expression right) {
    this.left = left;
    this.Operator = Operator;
    this.right = right;
    }
// Accept Visitor
    @Override
    <R> R accept(Visitor<R> visitor) {
        return visitor.visitBinaryExpression(this);
    }

// Fields
    final Expression left;
    final Token Operator;
    final Expression right;
    }
 static class Grouping extends Expression {
//Contructor
    Grouping(Expression expression) {
    this.expression = expression;
    }
// Accept Visitor
    @Override
    <R> R accept(Visitor<R> visitor) {
        return visitor.visitGroupingExpression(this);
    }

// Fields
    final Expression expression;
    }
 static class Literal extends Expression {
//Contructor
    Literal(Object value) {
    this.value = value;
    }
// Accept Visitor
    @Override
    <R> R accept(Visitor<R> visitor) {
        return visitor.visitLiteralExpression(this);
    }

// Fields
    final Object value;
    }
 static class Unary extends Expression {
//Contructor
    Unary(Token operator, Expression right) {
    this.operator = operator;
    this.right = right;
    }
// Accept Visitor
    @Override
    <R> R accept(Visitor<R> visitor) {
        return visitor.visitUnaryExpression(this);
    }

// Fields
    final Token operator;
    final Expression right;
    }

  abstract <R> R accept(Visitor<R> visitor);
}
