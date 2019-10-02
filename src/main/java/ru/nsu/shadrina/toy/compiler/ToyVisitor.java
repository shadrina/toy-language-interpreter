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
        ctx.children.forEach(child -> child.accept(this));
        methodVisitor.visitInsn(RETURN);
        methodVisitor.visitMaxs(maxStack, maxLocals);
        methodVisitor.visitEnd();
        return methodVisitor;
    }

    @Override
    public MethodVisitor visitStatement(ToyParser.StatementContext ctx) {
        return super.visitStatement(ctx);
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
        return super.visitPrintExpression(ctx);
    }

    @Override
    public MethodVisitor visitExpression(ToyParser.ExpressionContext ctx) {
        return super.visitExpression(ctx);
    }

    @Override
    public MethodVisitor visitOperation(ToyParser.OperationContext ctx) {
        return super.visitOperation(ctx);
    }

    @Override
    public MethodVisitor visitAtomic(ToyParser.AtomicContext ctx) {
        return super.visitAtomic(ctx);
    }

    @Override
    public MethodVisitor visitParenthesizedExpression(ToyParser.ParenthesizedExpressionContext ctx) {
        return super.visitParenthesizedExpression(ctx);
    }

    @Override
    public MethodVisitor visitLiteralConstant(ToyParser.LiteralConstantContext ctx) {
        return super.visitLiteralConstant(ctx);
    }

    @Override
    public MethodVisitor visitLineStringLiteral(ToyParser.LineStringLiteralContext ctx) {
        return super.visitLineStringLiteral(ctx);
    }
}
