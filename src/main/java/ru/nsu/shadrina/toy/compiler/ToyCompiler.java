package ru.nsu.shadrina.toy.compiler;

import lombok.Getter;
import lombok.Setter;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.apache.commons.cli.*;
import org.objectweb.asm.ClassWriter;
import ru.nsu.shadrina.toy.Main;
import ru.nsu.shadrina.toy.antlr.ToyLexer;
import ru.nsu.shadrina.toy.antlr.ToyParser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;

import static org.objectweb.asm.Opcodes.*;

public class ToyCompiler {
    @Getter @Setter
    private String generatedClassName = "ToyGenerated";
    @Getter @Setter
    private String outputPath = "";

    public void run(String[] args) {
        var options = new Options();
        configureOptions(options);
        try {
            var parser = new DefaultParser();
            var cmd = parser.parse(options, args);
            var fileName = cmd.getOptionValue("f");
            var classLoader = Main.class.getClassLoader();
            var filePath = Objects.requireNonNull(classLoader.getResource(fileName));
            var file = new File(filePath.getFile());
            var content = new String(Files.readAllBytes(file.toPath()));
            compile(content);

        } catch (ParseException | IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private void configureOptions(Options options) {
        var input = new Option("f", "file", true, "File to compile");
        input.setRequired(true);
        options.addOption(input);
    }

    private void compile(String code) {
        var classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
        classWriter.visit(V11, ACC_PUBLIC | ACC_SUPER, generatedClassName, null, "java/lang/Object", null);

        var lexer = new ToyLexer(new ANTLRInputStream(code));
        var parser = new ToyParser(new CommonTokenStream(lexer));
        var tree = parser.file();
        new ToyVisitor(classWriter).visit(tree);

        classWriter.visitEnd();
        try (var fos = new FileOutputStream(outputPath + generatedClassName + ".class")) {
            fos.write(classWriter.toByteArray());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
