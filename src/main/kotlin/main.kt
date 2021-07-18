import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader

fun main(args: Array<String>) {
    if (args.isEmpty())
        shortListingFormat()
    else if(args[0] == "-l")
        longListingFormat()
}

fun shortListingFormat() {
    val file = File(System.getProperty("user.dir"))
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
    val file = File(System.getProperty("user.dir"))
    val listFiles: Array<File>? = file.listFiles()
    val filesWithLongListingFormat: ArrayList<FileTemplate> = ArrayList()

    if (listFiles != null) {
        for (item in listFiles) {
            filesWithLongListingFormat.add(getStats(item))
        }
    }

    if (listFiles != null) {
        println("total ${listFiles.size}")
    }

    for (item in filesWithLongListingFormat) {
        println(item.toString())
    }
}

fun getStats(file: File): FileTemplate {
    val process: Process = Runtime.getRuntime().exec("stat ${file.name}")
    val listStats: ArrayList<String?> = ArrayList()

    val fileStat = BufferedReader(InputStreamReader(process.inputStream))

    var line: String?
    while (fileStat.readLine().also { line = it } != null) {
        listStats.add(line)
    }

    val outputParser = OutputParser(listStats)

    return outputParser.getStats()
}