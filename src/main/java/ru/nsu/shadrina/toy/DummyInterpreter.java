package ru.nsu.shadrina.toy;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import ru.nsu.shadrina.toy.antlr.ToyLexer;
import ru.nsu.shadrina.toy.antlr.ToyParser;
import ru.nsu.shadrina.toy.antlr.ToyParserBaseVisitor;

public class DummyInterpreter extends ToyParserBaseVisitor<Integer> {
    // TODO

    public static void main(String[] args) {
        var expression = "print(\"Hello, world!\");";
        var lexer = new ToyLexer(new ANTLRInputStream(expression));
        var parser = new ToyParser(new CommonTokenStream(lexer));
        var tree = parser.file();
        var answer = new DummyInterpreter().visit(tree);
        System.out.printf("%s = %s\n", expression, answer);
    }
}

