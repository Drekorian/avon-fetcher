package cz.drekorian.avonfetcher

import java.io.File
import java.io.OutputStream
import java.io.PrintStream
import java.nio.charset.Charset

const val CSV_SEPARATOR = ";"

object CsvPrinter {

    fun print(filePath: String, data: Iterable<String>, vararg header: String) {
        val headerSize = header.size

        File(filePath).outputStream().use { outputStream ->
            outputStream.writeBom()



            val stream = PrintStream(outputStream, true, Charsets.UTF_8.name())
            stream.println(header.joinToString(separator = CSV_SEPARATOR))

            data.forEach { line ->
                val lineSize = line.split(CSV_SEPARATOR).size
                check(lineSize == headerSize) { "CSV mismatch, current line ($line) has $lineSize columns while header has" +
            " $headerSize columns." }

                stream.println(line)
            }
        }
    }

    private fun OutputStream.writeBom() {
        val bom = byteArrayOf(0xef.toByte(), 0xbb.toByte(), 0xbf.toByte())
        write(bom)
    }
}
