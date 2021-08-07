import java.nio.file.attribute.PosixFileAttributeView
import java.nio.file.attribute.PosixFileAttributes
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class ParserTerminal constructor(private val fileAttributes: PosixFileAttributes, private val fileName: String) {
    private val fileTemplate = FileTemplate()

    enum class FilePermissions(private var shortName: String) {
        OWNER_READ("r"),
        OWNER_WRITE("w"),
        OWNER_EXECUTE("x"),
        GROUP_READ("r"),
        GROUP_WRITE("w"),
        GROUP_EXECUTE("x"),
        OTHERS_READ("r"),
        OTHERS_WRITE("w"),
        OTHERS_EXECUTE("x");

        fun getShortName(): String {
            return shortName
        }
    }

    private fun parse() {
        fileTemplate.accessPrivileges = formattingPermissions()
        fileTemplate.uid = fileAttributes.owner().name
        fileTemplate.gid = fileAttributes.group().name
        fileTemplate.size = fileAttributes.size().toString()
        fileTemplate.lastModify = formattingTime()
        fileTemplate.fileName = fileName
    }

    private fun formattingPermissions(): String {
        val filePermissions: StringBuilder = StringBuilder()

        if (fileAttributes.isDirectory)
            filePermissions.append("d")
        else
            filePermissions.append("-")

        var checked: Boolean
        for (permission in FilePermissions.values()) {
            checked = false
            for (item in fileAttributes.permissions()) {
                if (permission.toString() == item.toString()) {
                    filePermissions.append(permission.getShortName())
                    checked = true
                    continue
                }
            }
            if (!checked)
                filePermissions.append("-")
        }

        return filePermissions.toString()
    }

    private fun formattingTime(): String {
       var time = fileAttributes.
        lastModifiedTime().
        toInstant().
        atZone(ZoneId.
        systemDefault()).
        format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM))

        if (time[1] == ' '){
            time = time.padStart(time.length + 1)
        }

        return time
    }

    fun getStats(): FileTemplate {
        parse()

        return fileTemplate
    }
}