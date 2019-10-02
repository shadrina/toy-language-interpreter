package ru.nsu.shadrina.toy.compiler;

import lombok.Getter;
import lombok.Setter;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.objectweb.asm.ClassWriter;
import ru.nsu.shadrina.toy.antlr.ToyLexer;
import ru.nsu.shadrina.toy.antlr.ToyParser;

import java.io.FileOutputStream;
import java.io.IOException;

import static org.objectweb.asm.Opcodes.*;

public class ToyCompiler {
    @Getter @Setter
    private String generatedClassPackage = "ru/nsu/shadrina/toy";
    @Getter @Setter
    private String generatedClassName = "ToyGenerated";

    private String generatedClassFullName() {
        return generatedClassPackage + "/" + generatedClassName;
    }

    public void compile(String code) {
        var classWriter = new ClassWriter(0);
        classWriter.visit(V11, ACC_PUBLIC | ACC_SUPER, generatedClassFullName(), null, "java/lang/Object", null);
        createInit(classWriter);

        var lexer = new ToyLexer(new ANTLRInputStream(code));
        var parser = new ToyParser(new CommonTokenStream(lexer));
        var tree = parser.file();
        new ToyVisitor(classWriter).visit(tree);

        classWriter.visitEnd();

        try (var fos = new FileOutputStream(generatedClassName + ".class")) {
            fos.write(classWriter.toByteArray());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void createInit(ClassWriter classWriter) {
        var methodVisitor = classWriter.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
        methodVisitor.visitCode();
        methodVisitor.visitVarInsn(ALOAD, 0); //load the first local variable: this
        methodVisitor.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V");
        methodVisitor.visitInsn(RETURN);
        methodVisitor.visitMaxs(1,1);
        methodVisitor.visitEnd();
    }
}
