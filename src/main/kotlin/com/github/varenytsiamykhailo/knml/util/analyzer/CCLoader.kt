package com.github.varenytsiamykhailo.knml.util.analyzer

import java.io.*
import java.util.jar.JarFile


class CCLoader(
    parent: ClassLoader?,
    val dirPath: String,
    val jarPath: String,
    val classPath: String,
) : ClassLoader(parent) {
    @Throws(ClassNotFoundException::class)
    private fun getClass(className: String): Class<*>? {
        val b: ByteArray?
        return try {
            b = loadClassFileData(classPath)
            val c = defineClass(className, b, 0, b.size)
            resolveClass(c)
            c
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    @Throws(ClassNotFoundException::class)
    override fun loadClass(className: String): Class<*>? {
        if (className.contains("Output")) {
            extractOutputJAR()
            return getClass(className)
        }
        return super.loadClass(className)
    }

    @Throws(IOException::class)
    private fun loadClassFileData(classPath: String): ByteArray {
        val stream = FileInputStream(classPath)
        val size = stream.available()
        val buff = ByteArray(size)
        val input = DataInputStream(stream)
        buff.let { input.readFully(it) }
        input.close()
        return buff
    }

    private fun extractOutputJAR() {
        val jarfile = JarFile(File(jarPath))
        val enu = jarfile.entries()
        while (enu.hasMoreElements()) {
            val destDir = "$dirPath/output/"
            val je = enu.nextElement()
            var fl = File(destDir, je.name)
            if (!fl.exists()) {
                fl.parentFile.mkdirs()
                fl = File(destDir, je.name)
            }
            if (je.isDirectory) {
                continue
            }
            val inputStream = jarfile.getInputStream(je)
            val outputStream = FileOutputStream(fl)
            try {
                while (inputStream.available() > 0) {
                    outputStream.write(inputStream.read())
                }
            } catch (_: EOFException) {
            }
            outputStream.close()
            inputStream.close()
        }
    }
}