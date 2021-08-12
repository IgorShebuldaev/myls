import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.attribute.PosixFileAttributeView
import java.nio.file.attribute.PosixFileAttributes
import java.nio.file.attribute.PosixFilePermission
import java.text.SimpleDateFormat
import java.util.*


val PERMISSION_LIST = listOf(
    PosixFilePermission.OWNER_READ,
    PosixFilePermission.OWNER_WRITE,
    PosixFilePermission.OWNER_EXECUTE,
    PosixFilePermission.GROUP_READ,
    PosixFilePermission.GROUP_WRITE,
    PosixFilePermission.GROUP_EXECUTE,
    PosixFilePermission.OTHERS_READ,
    PosixFilePermission.OTHERS_WRITE,
    PosixFilePermission.OTHERS_EXECUTE
)

class FileStat constructor(private val name: String) {
    var permissions: String = ""
    var owner: String = ""
    var group: String = ""
    var size: String = ""
    var lastModified: String = ""

    init {
        val file = Paths.get(name)
        val fileAttributes = Files.getFileAttributeView(file, PosixFileAttributeView::class.java).readAttributes()

        permissions = formattingPermissions(fileAttributes)
        owner = fileAttributes.owner().name
        group = fileAttributes.group().name
        size = fileAttributes.size().toString()
        lastModified = formattedTime(fileAttributes)
    }

    private fun formattingPermissions(fileAttributes: PosixFileAttributes): String {
        val filePermissions: StringBuilder = StringBuilder()

        when {
            fileAttributes.isDirectory -> filePermissions.append("d")
            fileAttributes.isSymbolicLink -> filePermissions.append("l")
            else -> filePermissions.append("-")
        }

        val rwx = PERMISSION_LIST.joinToString("") { permission ->
            if (fileAttributes.permissions().contains(permission)) {
                val permissionName = permission.name

                when {
                    permissionName.contains("READ") -> 'r'.toString()
                    permissionName.contains("WRITE") -> 'w'.toString()
                    else -> 'x'.toString()
                }
            } else "-"
        }

        return filePermissions.append(rwx).toString()
    }

    private fun formattedTime(fileAttributes: PosixFileAttributes): String {
        val time = Date(fileAttributes.lastModifiedTime().toMillis())
        val simpleDateFormat = SimpleDateFormat("d MMM HH:mm")

        return simpleDateFormat.format(time)
    }

    override fun toString(): String {
        return "$permissions $owner $group $size $lastModified $name"
    }
}
