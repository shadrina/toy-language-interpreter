package ru.nsu.shadrina.toy.compiler;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import ru.nsu.shadrina.toy.antlr.ToyParser;
import ru.nsu.shadrina.toy.antlr.ToyParserBaseVisitor;

import static org.objectweb.asm.Opcodes.*;

public class ToyVisitor extends ToyParserBaseVisitor<MethodVisitor> {
    private MethodVisitor methodVisitor;

    private int maxStack = 0;
    private int maxLocals = 1;

    ToyVisitor(ClassVisitor classVisitor) {
        methodVisitor = classVisitor.visitMethod(ACC_PUBLIC + ACC_STATIC, "main", "([Ljava/lang/String;)V", null, null);
    }

    @Override
    public MethodVisitor visitFile(ToyParser.FileContext ctx) {
        methodVisitor.visitCode();
        visitChildren(ctx);
        methodVisitor.visitInsn(RETURN);
        methodVisitor.visitMaxs(maxStack, maxLocals);
        methodVisitor.visitEnd();
        return methodVisitor;
    }

    @Override
    public MethodVisitor visitStatement(ToyParser.StatementContext ctx) {
        visitChildren(ctx);
        return methodVisitor;
    }

    @Override
    public MethodVisitor visitDeclaration(ToyParser.DeclarationContext ctx) {
        return super.visitDeclaration(ctx);
    }

    @Override
    public MethodVisitor visitAssignment(ToyParser.AssignmentContext ctx) {
        return super.visitAssignment(ctx);
    }

    @Override
    public MethodVisitor visitPrintExpression(ToyParser.PrintExpressionContext ctx) {
        methodVisitor.visitFieldInsn(GETSTATIC,"java/lang/System", "out", "Ljava/io/PrintStream;");
        maxStack++;
        ctx.expression().accept(this);
        methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(I)V", false);
        return methodVisitor;
    }

    @Override
    public MethodVisitor visitExpression(ToyParser.ExpressionContext ctx) {
        ctx.atomic().accept(this);
        var operation = ctx.operation();
        var expression = ctx.expression();
        if (operation != null && expression != null) {
            expression.accept(this);
            operation.accept(this);
        }
        return methodVisitor;
    }

    @Override
    public MethodVisitor visitOperation(ToyParser.OperationContext ctx) {
        switch (ctx.start.getType()) {
            case ToyParser.ADD:
                methodVisitor.visitInsn(IADD);
                break;
            case ToyParser.SUB:
                methodVisitor.visitInsn(ISUB);
                break;
            default:
                throw new RuntimeException("Unknown operation");
        }
        return super.visitOperation(ctx);
    }

    @Override
    public MethodVisitor visitAtomic(ToyParser.AtomicContext ctx) {
        visitChildren(ctx);
        return methodVisitor;
    }

    @Override
    public MethodVisitor visitLiteralConstant(ToyParser.LiteralConstantContext ctx) {
        var constant = Integer.parseInt(ctx.getText());
        methodVisitor.visitLdcInsn(constant);
        maxStack++;
        return super.visitLiteralConstant(ctx);
    }
}
