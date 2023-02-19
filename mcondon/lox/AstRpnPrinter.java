package mcondon.lox;

public class AstRpnPrinter implements Expression.Visitor<String> {
    String print(Expression expression) {
        return expression.accept(this);
    }

    @Override
    public String visitBinaryExpression(Expression.Binary expr) {
        return rpnthesize(expr.Operator.lexeme, expr.left, expr.right);
    }

    @Override
    public String visitGroupingExpression(Expression.Grouping expr) {
        return rpnthesize("group", expr.expression);
    }

    @Override
    public String visitLiteralExpression(Expression.Literal expr) {
        if (expr.value == null)
            return "nil";
        return expr.value.toString();
    }

    @Override
    public String visitUnaryExpression(Expression.Unary expr) {
        return rpnthesize(expr.operator.lexeme, expr.right);
    }

    private String rpnthesize(String name, Expression... exprs) {
        StringBuilder builder = new StringBuilder();
        for (Expression expr : exprs) {
            builder.append(" ");
            builder.append(expr.accept(this));
        }
        builder.append(" ");
        builder.append(name);
        return builder.toString();
    }

    public static void main(String[] args) {
        Expression twoPlusOne = new Expression.Binary(
                new Expression.Literal(2),
                new Token(TokenType.PLUS, "+", null, 1),
                new Expression.Literal(1));

        Expression fourMinusThree = new Expression.Binary(
                new Expression.Literal(4),
                new Token(TokenType.MINUS, "-", null, 1),
                new Expression.Literal(3));
        Expression expr = new Expression.Binary(
                twoPlusOne,
                new Token(TokenType.STAR, "*", null, 1),
                fourMinusThree);

        System.out.println(new AstRpnPrinter().print(expr));
    }
}
