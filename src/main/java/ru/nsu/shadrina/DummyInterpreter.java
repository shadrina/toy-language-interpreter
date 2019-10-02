package ru.nsu.shadrina;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import ru.nsu.shadrina.antlr.ToyBaseVisitor;
import ru.nsu.shadrina.antlr.ToyLexer;
import ru.nsu.shadrina.antlr.ToyParser;

public class DummyInterpreter extends ToyBaseVisitor<Integer> {
    @Override
    public Integer visitOpExpr(ToyParser.OpExprContext ctx) {
        var left = visit(ctx.left);
        var right = visit(ctx.right);
        var op = ctx.op.getText();
        switch (op.charAt(0)) {
            case '*': return left * right;
            case '/': return left / right;
            case '+': return left + right;
            case '-': return left - right;
            default: throw new IllegalArgumentException("Unknown operator " + op);
        }
    }

    @Override
    public Integer visitAtomExpr(ToyParser.AtomExprContext ctx) {
        return Integer.valueOf(ctx.getText());
    }

    @Override
    public Integer visitParenExpr(ToyParser.ParenExprContext ctx) {
        return this.visit(ctx.expr());
    }

    public static void main(String[] args) {
        var expression = "2*(3+4)";
        var lexer = new ToyLexer(new ANTLRInputStream(expression));
        var parser = new ToyParser(new CommonTokenStream(lexer));
        var tree = parser.expr();
        var answer = new DummyInterpreter().visit(tree);
        System.out.printf("%s = %s\n", expression, answer);
    }
}

