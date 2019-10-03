package ru.nsu.shadrina.toy.compiler;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import ru.nsu.shadrina.toy.antlr.ToyParser;
import ru.nsu.shadrina.toy.antlr.ToyParserBaseVisitor;

import java.util.HashMap;
import java.util.Map;

import static org.objectweb.asm.Opcodes.*;

public class ToyVisitor extends ToyParserBaseVisitor<MethodVisitor> {
    private static final int BIPUSH_MAX = 128;
    private static final int SIPUSH_MAX = 32768;

    private MethodVisitor methodVisitor;
    private Map<String, Integer> variableToIndexMapping = new HashMap<>();

    private int maxLocals = 1;
    private Label lastLabel;

    ToyVisitor(ClassVisitor classVisitor) {
        methodVisitor = classVisitor.visitMethod(ACC_PUBLIC + ACC_STATIC, "main", "([Ljava/lang/String;)V", null, null);
    }

    @Override
    public MethodVisitor visitFile(ToyParser.FileContext ctx) {
        methodVisitor.visitCode();
        visitChildren(ctx);
        methodVisitor.visitInsn(RETURN);
        methodVisitor.visitMaxs(0, 0);
        methodVisitor.visitEnd();
        return methodVisitor;
    }

    @Override
    public MethodVisitor visitDeclaration(ToyParser.DeclarationContext ctx) {
        var identifier = ctx.Identifier().getText();
        if (variableToIndexMapping.containsKey(identifier)) {
            throw new RuntimeException("Variable with name " + identifier + " already exists");
        }
        ctx.expression().accept(this);
        methodVisitor.visitVarInsn(ISTORE, maxLocals);
        variableToIndexMapping.put(identifier, maxLocals);
        maxLocals++;
        return methodVisitor;
    }

    @Override
    public MethodVisitor visitAssignment(ToyParser.AssignmentContext ctx) {
        var identifier = ctx.Identifier().getText();
        var index = variableToIndexMapping.getOrDefault(identifier, null);
        if (index == null) {
            throw new RuntimeException("Unknown variable " + identifier);
        }
        ctx.expression().accept(this);
        methodVisitor.visitVarInsn(ISTORE, index);
        return methodVisitor;
    }

    @Override
    public MethodVisitor visitPrintExpression(ToyParser.PrintExpressionContext ctx) {
        methodVisitor.visitFieldInsn(GETSTATIC,"java/lang/System", "out", "Ljava/io/PrintStream;");
        ctx.expression().accept(this);
        methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(I)V", false);
        return methodVisitor;
    }

    @Override
    public MethodVisitor visitIfExpression(ToyParser.IfExpressionContext ctx) {
        if (ctx.expression().comparisonOperator() == null) {
            throw new RuntimeException("Condition has to be comparison expression");
        }
        ctx.expression().accept(this);
        methodVisitor.visitInsn(ICONST_1);
        var elseLabel = new Label();
        methodVisitor.visitJumpInsn(IF_ICMPNE, elseLabel);
        ctx.statements(0).accept(this);
        var nextLabel = new Label();
        methodVisitor.visitJumpInsn(GOTO, nextLabel);
        methodVisitor.visitLabel(elseLabel);
        if (ctx.statements().size() > 1) {
            ctx.statements(1).accept(this);
        }
        methodVisitor.visitLabel(nextLabel);
        return methodVisitor;
    }

    @Override
    public MethodVisitor visitExpression(ToyParser.ExpressionContext ctx) {
        ctx.additiveExpression(0).accept(this);
        if (ctx.comparisonOperator() != null) {
            ctx.additiveExpression(1).accept(this);
            var elseLabel = new Label();
            lastLabel = elseLabel;
            ctx.comparisonOperator().accept(this);
            methodVisitor.visitInsn(ICONST_1);
            var nextLabel = new Label();
            methodVisitor.visitJumpInsn(GOTO, nextLabel);
            methodVisitor.visitLabel(elseLabel);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitLabel(nextLabel);
        }
        return methodVisitor;
    }

    @Override
    public MethodVisitor visitAdditiveExpression(ToyParser.AdditiveExpressionContext ctx) {
        ctx.multiplicativeExpression(0).accept(this);
        var i = 0;
        while (i + 1 < ctx.multiplicativeExpression().size()) {
            ctx.multiplicativeExpression(i + 1).accept(this);
            ctx.additiveOperator(i).accept(this);
            i++;
        }
        return methodVisitor;
    }

    @Override
    public MethodVisitor visitMultiplicativeExpression(ToyParser.MultiplicativeExpressionContext ctx) {
        ctx.atomic(0).accept(this);
        var i = 0;
        while (i + 1 < ctx.atomic().size()) {
            ctx.atomic(i + 1).accept(this);
            ctx.multiplicativeOperator(i).accept(this);
            i++;
        }
        return methodVisitor;
    }

    @Override
    public MethodVisitor visitComparisonOperator(ToyParser.ComparisonOperatorContext ctx) {
        switch (ctx.start.getType()) {
            case ToyParser.LANGLE:
                methodVisitor.visitJumpInsn(IF_ICMPGT, lastLabel);
                break;
            case ToyParser.RANGLE:
                methodVisitor.visitJumpInsn(IF_ICMPLT, lastLabel);
                break;
            case ToyParser.LE:
                methodVisitor.visitJumpInsn(IF_ICMPGE, lastLabel);
                break;
            case ToyParser.GE:
                methodVisitor.visitJumpInsn(IF_ICMPLE, lastLabel);
                break;
            case ToyParser.EXCL_EQ:
                methodVisitor.visitJumpInsn(IF_ICMPEQ, lastLabel);
                break;
            case ToyParser.EQEQ:
                methodVisitor.visitJumpInsn(IF_ICMPNE, lastLabel);
                break;
        }
        return methodVisitor;
    }

    @Override
    public MethodVisitor visitAdditiveOperator(ToyParser.AdditiveOperatorContext ctx) {
        switch (ctx.start.getType()) {
            case ToyParser.ADD:
                methodVisitor.visitInsn(IADD);
                break;
            case ToyParser.SUB:
                methodVisitor.visitInsn(ISUB);
                break;
        }
        return methodVisitor;
    }

    @Override
    public MethodVisitor visitMultiplicativeOperator(ToyParser.MultiplicativeOperatorContext ctx) {
        switch (ctx.start.getType()) {
            case ToyParser.MULT:
                methodVisitor.visitInsn(IMUL);
                break;
            case ToyParser.DIV:
                methodVisitor.visitInsn(IDIV);
                break;
            case ToyParser.MOD:
                methodVisitor.visitInsn(IREM);
                break;
        }
        return methodVisitor;
    }

    @Override
    public MethodVisitor visitIdentifier(ToyParser.IdentifierContext ctx) {
        var identifier = ctx.Identifier().getText();
        var index = variableToIndexMapping.getOrDefault(identifier, null);
        if (index == null) {
            throw new RuntimeException("Unknown variable " + identifier);
        }
        methodVisitor.visitVarInsn(ILOAD, index);
        return methodVisitor;
    }

    @Override
    public MethodVisitor visitLiteralConstant(ToyParser.LiteralConstantContext ctx) {
        var constant = Integer.parseInt(ctx.getText());
        // TODO: Use iconst_* for small numbers
        if (constant >= -BIPUSH_MAX && constant < BIPUSH_MAX) {
            methodVisitor.visitIntInsn(BIPUSH, constant);
        } else if (constant >= -SIPUSH_MAX && constant < SIPUSH_MAX) {
            methodVisitor.visitIntInsn(SIPUSH, constant);
        } else {
            methodVisitor.visitLdcInsn(constant);
        }
        return methodVisitor;
    }
}
