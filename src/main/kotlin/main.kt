import parser.LinuxParserTerminal
import parser.MacOsParserTerminal
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import kotlin.system.exitProcess

const val LINUX = "Linux"
const val MACOS = "Mac OS X"
val typeOS: String = System.getProperty("os.name")
val file = File(System.getProperty("user.dir"))

fun main(args: Array<String>) {
    if (typeOS != LINUX && typeOS != MACOS) {
        println("Utility is not supported on this operating system.")
        exitProcess(0);
        } else
            run(args)
    }

fun run(args: Array<String>) {
    if (args.isEmpty())
        shortListingFormat()
    else if(args[0] == "-l")
        longListingFormat()
}

fun shortListingFormat() {
    val listNames: Array<out String>? = file.list()

    if (listNames != null) {
        println("total ${listNames.size}")

        for (name in listNames)
            print("$name ")
    } else
        print("total 0")

    println()
}

fun longListingFormat() {
    val listFiles: Array<File>? = file.listFiles()
    var filesWithLongListingFormat: ArrayList<FileTemplate> = ArrayList()

    if (listFiles != null) {
        for (item in listFiles) {
            filesWithLongListingFormat.add(getStats(item))
        }
    }

    if (listFiles != null) {
        println("total ${listFiles.size}")
    }

    filesWithLongListingFormat = fixTable(filesWithLongListingFormat)


    for (item in filesWithLongListingFormat) {
        println(item.toString())
    }
}

fun getStats(file: File): FileTemplate {
    val process = Runtime.getRuntime().exec("stat ${file.name}")
    val listStats: ArrayList<String?> = ArrayList()

    val fileStat = BufferedReader(InputStreamReader(process.inputStream))

    var line: String?
    while (fileStat.readLine().also { line = it } != null) {
        listStats.add(line)
    }

    if (typeOS == LINUX)
        return LinuxParserTerminal(listStats).getStats()
    else
        return MacOsParserTerminal(listStats).getStats()
}

fun fixTable(filesWithLongListingFormat: ArrayList<FileTemplate>): ArrayList<FileTemplate>{
    var max = 0;
    for (item in filesWithLongListingFormat) {
        if (max < item.hardLinks.length) {
            max = item.hardLinks.length
        }
    }

    for (item in filesWithLongListingFormat) {
        if (max > item.hardLinks.length) {
            val a = max - item.hardLinks.length
            item.hardLinks + addSpaces(a)
        }
    }

    return filesWithLongListingFormat
}

fun addSpaces(numberSpaces: Int): String {
    val spaceLine: StringBuilder = StringBuilder()
    for (i in 1..numberSpaces) {
        spaceLine.append(" ")
    }

    return spaceLine.toString()
}
