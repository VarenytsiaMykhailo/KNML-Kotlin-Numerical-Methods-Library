package com.github.varenytsiamykhailo.knml.util.analyzer

import java.io.File
import java.nio.file.Paths
import kotlin.io.path.Path
import kotlin.io.path.createFile


class InputAnalyzer {
    fun analyzeAndSolve(str: String): Any? {
        try {
            val output = getDataFromAnalyzer(dirPath, str)
            writeOutputToKotlinClass(output, kotlinClassPath)
            createJarWithOutput(kotlinClassPath, jarPath, libPath).run {
                return loadOutputClassAndInvokeMethod()
            }
        } catch (e: Exception) {
            deleteBuildFiles()
        }
        return null
    }

    companion object {
        val absPath = Paths.get("").toAbsolutePath().toString()
        val dirPath = "$absPath/analyzer"
        val inputFile = "$dirPath/input.txt"
        val libPath = "$absPath/build/libs/knml-1.0-SNAPSHOT.jar"
        val kotlinClassPath = "$dirPath/Output.kt"
        val jarPath = "$dirPath/output.jar"
        val outputClassPath = "$dirPath/output/com/github/varenytsiamykhailo/knml/util/analyzer/util/Output.class"
        val outputClassName = "com.github.varenytsiamykhailo.knml.util.analyzer.util.Output"
    }

    private fun loadOutputClassAndInvokeMethod(): Any {
        val ccl = CCLoader(InputAnalyzer::class.java.classLoader, dirPath, jarPath, outputClassPath)
        val outputClass = ccl.loadClass(outputClassName)
        val outputClassInstance = outputClass?.getDeclaredConstructor()?.newInstance()
        val callMethod = outputClass!!.getMethod("call")
        deleteBuildFiles()
        val res = callMethod.invoke(outputClassInstance)
        return res
    }

    private fun deleteBuildFiles() {
        File(kotlinClassPath).delete()
        File(outputClassPath).delete()
        File(jarPath).delete()
        File(inputFile).delete()
    }

    private fun createJarWithOutput(kotlinClassPath: String, jarPath: String, libPath: String) {
        Runtime.getRuntime()
            .exec("kotlinc $kotlinClassPath -include-runtime -d $jarPath -cp $libPath")
            .waitFor()
    }

    private fun writeOutputToKotlinClass(output: String, pathToKotlinClass: String) {
        File(pathToKotlinClass).writeText(output)
    }

    private fun getDataFromAnalyzer(dirPath: String, str: String): String {
        Runtime.getRuntime().exec("chmod +x $dirPath/knml_analyzer")

        Path(inputFile).createFile()
        File(inputFile).writeText(str)
        var output = ""

        Runtime.getRuntime()
            .exec("$dirPath/knml_analyzer $dirPath/input.txt")
            .inputStream
            .reader(Charsets.UTF_8)
            .use { output += it.readText() }

        if (output.indexOf("package") != -1 && output.indexOf("package") < output.length) {
            output = output.substring(output.indexOf("package"), output.length)
        }

        val cleanOutput = output.filter {
            it.isLetterOrDigit() || it == '(' || it == ')'
                    || it == ' ' || it == '{' || it == '}'
                    || it == '.' || it == ':' || it == ','
                    || it == '-' || it == '=' || it == '+'
                    || it == '*' || it == '/' || it == '\n'
                    || it == '\t' || it == '>'
        }

        println("Output: $output")
        return cleanOutput
    }
}