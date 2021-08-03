class FileTemplate {
    var accessPrivileges: String = ""
    var hardLinks: String = ""
    var uid: String = ""
    var gid: String = ""
    var size: String = ""
    var lastModify: String = ""
    var fileName: String = ""

    override fun toString(): String {
        return "$accessPrivileges $hardLinks $uid $gid $size $lastModify $fileName"
    }
}