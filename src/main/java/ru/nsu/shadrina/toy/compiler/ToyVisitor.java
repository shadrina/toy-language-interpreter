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
        methodVisitor.visitVarInsn(ASTORE, maxLocals);
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
        methodVisitor.visitVarInsn(ASTORE, index);
        return methodVisitor;
    }

    @Override
    public MethodVisitor visitPrintExpression(ToyParser.PrintExpressionContext ctx) {
        methodVisitor.visitFieldInsn(GETSTATIC,"java/lang/System", "out", "Ljava/io/PrintStream;");
        ctx.expression().accept(this);
        methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/Object;)V", false);
        return methodVisitor;
    }

    @Override
    public MethodVisitor visitIfExpression(ToyParser.IfExpressionContext ctx) {
        checkConditionExpression(ctx.expression());
        ctx.expression().accept(this);
        unbox();
        methodVisitor.visitInsn(ICONST_0);
        var elseLabel = new Label();
        methodVisitor.visitJumpInsn(IF_ICMPEQ, elseLabel);
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
    public MethodVisitor visitWhileDoExpression(ToyParser.WhileDoExpressionContext ctx) {
        checkConditionExpression(ctx.expression());
        var conditionLabel = new Label();
        var nextLabel = new Label();
        methodVisitor.visitLabel(conditionLabel);
        ctx.expression().accept(this);
        unbox();
        methodVisitor.visitInsn(ICONST_0);
        methodVisitor.visitJumpInsn(IF_ICMPEQ, nextLabel);
        ctx.statements().accept(this);
        methodVisitor.visitJumpInsn(GOTO, conditionLabel);
        methodVisitor.visitLabel(nextLabel);
        return methodVisitor;
    }

    @Override
    public MethodVisitor visitDoWhileExpression(ToyParser.DoWhileExpressionContext ctx) {
        checkConditionExpression(ctx.expression());
        var bodyLabel = new Label();
        methodVisitor.visitLabel(bodyLabel);
        ctx.statements().accept(this);
        ctx.expression().accept(this);
        unbox();
        methodVisitor.visitInsn(ICONST_0);
        methodVisitor.visitJumpInsn(IF_ICMPNE, bodyLabel);
        return methodVisitor;
    }

    @Override
    public MethodVisitor visitExpression(ToyParser.ExpressionContext ctx) {
        ctx.additiveExpression(0).accept(this);
        if (ctx.additiveExpression().size() == 1) return methodVisitor;
        unbox();
        if (ctx.comparisonOperator() != null) {
            ctx.additiveExpression(1).accept(this);
            unbox();
            var elseLabel = new Label();
            int ifInstruction;
            switch (ctx.comparisonOperator().start.getType()) {
                case ToyParser.LANGLE:
                    ifInstruction = IF_ICMPGE;
                    break;
                case ToyParser.RANGLE:
                    ifInstruction = IF_ICMPLE;
                    break;
                case ToyParser.LE:
                    ifInstruction = IF_ICMPGT;
                    break;
                case ToyParser.GE:
                    ifInstruction = IF_ICMPLT;
                    break;
                case ToyParser.EXCL_EQ:
                    ifInstruction = IF_ICMPEQ;
                    break;
                case ToyParser.EQEQ:
                    ifInstruction = IF_ICMPNE;
                    break;
                default:
                    throw new RuntimeException("Operator " + ctx.comparisonOperator().getText() + " not supported yet");
            }
            methodVisitor.visitJumpInsn(ifInstruction, elseLabel);
            ctx.comparisonOperator().accept(this);
            methodVisitor.visitInsn(ICONST_1);
            var nextLabel = new Label();
            methodVisitor.visitJumpInsn(GOTO, nextLabel);
            methodVisitor.visitLabel(elseLabel);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitLabel(nextLabel);
        }
        box();
        return methodVisitor;
    }

    @Override
    public MethodVisitor visitAdditiveExpression(ToyParser.AdditiveExpressionContext ctx) {
        ctx.multiplicativeExpression(0).accept(this);
        if (ctx.multiplicativeExpression().size() == 1) return methodVisitor;
        unbox();
        var i = 0;
        while (i + 1 < ctx.multiplicativeExpression().size()) {
            ctx.multiplicativeExpression(i + 1).accept(this);
            unbox();
            ctx.additiveOperator(i).accept(this);
            i++;
        }
        box();
        return methodVisitor;
    }

    @Override
    public MethodVisitor visitMultiplicativeExpression(ToyParser.MultiplicativeExpressionContext ctx) {
        ctx.atomic(0).accept(this);
        if (ctx.atomic().size() == 1) return methodVisitor;
        unbox();
        var i = 0;
        while (i + 1 < ctx.atomic().size()) {
            ctx.atomic(i + 1).accept(this);
            unbox();
            ctx.multiplicativeOperator(i).accept(this);
            i++;
        }
        box();
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
        methodVisitor.visitVarInsn(ALOAD, index);
        return methodVisitor;
    }

    @Override
    public MethodVisitor visitLineStringLiteral(ToyParser.LineStringLiteralContext ctx) {
        var content = ctx.LineStringText().getText();
        methodVisitor.visitLdcInsn(content);
        return methodVisitor;
    }

    @Override
    public MethodVisitor visitLiteralConstant(ToyParser.LiteralConstantContext ctx) {
        var constant = Integer.parseInt(ctx.getText());
        switch (constant) {
            case 0:
                methodVisitor.visitInsn(ICONST_0);
                break;
            case 1:
                methodVisitor.visitInsn(ICONST_1);
                break;
            case 2:
                methodVisitor.visitInsn(ICONST_2);
                break;
            case 3:
                methodVisitor.visitInsn(ICONST_3);
                break;
            case 4:
                methodVisitor.visitInsn(ICONST_4);
                break;
            case 5:
                methodVisitor.visitInsn(ICONST_5);
                break;
            default:
                if (constant >= -BIPUSH_MAX && constant < BIPUSH_MAX) {
                    methodVisitor.visitIntInsn(BIPUSH, constant);
                } else if (constant >= -SIPUSH_MAX && constant < SIPUSH_MAX) {
                    methodVisitor.visitIntInsn(SIPUSH, constant);
                } else {
                    methodVisitor.visitLdcInsn(constant);
                }
                break;
        }
        box();
        return methodVisitor;
    }

    private void checkConditionExpression(ToyParser.ExpressionContext ctx) {
        if (ctx.comparisonOperator() == null) {
            throw new RuntimeException("Condition has to be comparison expression");
        }
    }

    private void box() {
        methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;", false);
    }

    private void unbox() {
        methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Integer", "intValue", "()I", false);
    }
}
