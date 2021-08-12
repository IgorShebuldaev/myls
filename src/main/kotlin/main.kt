import java.io.File

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
    fixFormatting(listNames.map { name -> getFileStat(name) } as ArrayList<FileStat>).forEach { line -> println(line)}
}

fun getFileStat(fileName: String): FileStat {
    return FileStat(fileName);
}

fun fixFormatting(filesWithLongListingFormat: ArrayList<FileStat>): ArrayList<FileStat>{
    var max = 0;
    for (item in filesWithLongListingFormat) {
        if (max < item.size.length) {
            max = item.size.length
        }
    }

    for (item in filesWithLongListingFormat) {
        if (max > item.size.length) {
            val a = max - item.size.length
            item.size = item.size.padStart(a + item.size.length)
        }
    }

    return filesWithLongListingFormat
}
