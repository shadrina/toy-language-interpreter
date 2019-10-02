package ru.nsu.shadrina.toy;

import org.apache.commons.cli.*;
import ru.nsu.shadrina.toy.compiler.ToyCompiler;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;

public class Main {
    public static void main(String[] args) {
        var options = new Options();

        var input = new Option("f", "file", true, "File to compile");
        input.setRequired(true);
        options.addOption(input);

        var parser = new DefaultParser();
        try {
            var cmd = parser.parse(options, args);
            var fileName = cmd.getOptionValue("f");
            var classLoader = Main.class.getClassLoader();
            var file = new File(Objects.requireNonNull(classLoader.getResource(fileName)).getFile());
            var content = new String(Files.readAllBytes(file.toPath()));
            new ToyCompiler().compile(content);

        } catch (ParseException | IOException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }
}
