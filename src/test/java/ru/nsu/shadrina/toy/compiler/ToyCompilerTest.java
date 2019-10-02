package ru.nsu.shadrina.toy.compiler;

import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Paths;

public class ToyCompilerTest {
    @Test
    public void compile() {
        var compiler = new ToyCompiler();
        var args = new String[] {"-f", "example.toy"};
        compiler.run(args);

        try {
            var currentRelativePath = Paths.get(compiler.getOutputPath());
            var urls = new URL[] {currentRelativePath.toUri().toURL()};
            var classLoader = new URLClassLoader(urls);
            var clazz = classLoader.loadClass(compiler.getGeneratedClassName());
            var mainMethod = clazz.getMethod("main", String[].class);
            String[] params = {};
            mainMethod.invoke(null, (Object) params);

        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | IOException e) {
            e.printStackTrace();
        }
    }
}
