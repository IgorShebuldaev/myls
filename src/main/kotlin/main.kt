import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.attribute.PosixFileAttributeView

val file = File(System.getProperty("user.dir"))
val listNames: Array<String> = file.list()

fun main(args: Array<String>) {
    if (args.isEmpty())
        shortListingFormat()
    else if(args[0] == "-l")
        longListingFormat()
}

fun shortListingFormat() {
    println(listNames.joinToString(" "))
}

fun longListingFormat() {
    var filesWithLongListingFormat: ArrayList<FileTemplate> = ArrayList()

    for (item in listNames) {
        filesWithLongListingFormat.add(getStats(item))
    }

    filesWithLongListingFormat = fixFormatting(filesWithLongListingFormat)

    for (item in filesWithLongListingFormat) {
        println(item.toString())
    }
}

fun getStats(fileName: String): FileTemplate {
    val file = Paths.get(fileName)
    val fileAttributes = Files.getFileAttributeView(file, PosixFileAttributeView::class.java).readAttributes()

    return ParserTerminal(fileAttributes, fileName).getStats()
}

fun fixFormatting(filesWithLongListingFormat: ArrayList<FileTemplate>): ArrayList<FileTemplate>{
    var max = 0;
    for (item in filesWithLongListingFormat) {
        if (max < item.size.length) {
            max = item.size.length
        }
    }

    for (item in filesWithLongListingFormat) {
        if (max > item.size.length) {
            val a = max - item.size.length
            item.size = item.size.padEnd(a + item.size.length)
        }
    }

    return filesWithLongListingFormat
}
