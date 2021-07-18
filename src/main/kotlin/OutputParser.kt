class OutputParser(private val listStats: ArrayList<String?>){
    private val fileTemplate = FileTemplate()
    private val list: ArrayList<String> = ArrayList()

    private fun parse() {
        for (item in listStats) {
            if (item != null) {
                for (value in item.split(" "))
                    list.add(value)
            }
        }

        for (item in list.indices) {
            if ("File:" == list[item]) {
                fileTemplate.fileName = list[item + 1]
                break
            }
        }

        for (item in list.indices) {
            if ("Size:" == list[item]) {
                fileTemplate.size = list[item + 1].toInt()
                break
            }
        }

        for (item in list.indices) {
            if ("Links:" == list[item]) {
                fileTemplate.hardLinks = list[item + 1]
                break
            }
        }

        for (item in list.indices) {
            if ("Access:" == list[item]) {
                fileTemplate.accessPrivileges = list[item + 1]
                break
            }
        }

        for (item in list.indices) {
            if ("Uid:" == list[item]) {
                fileTemplate.uid = list[item + 2]
                break
            }
        }

        for (item in list.indices) {
            if ("Gid:" == list[item]) {
                fileTemplate.gid = list[item + 2]
                break
            }
        }

        for (item in list.indices) {
            if ("Modify:" == list[item]) {
                fileTemplate.lastModify = list[item + 1] + " " + list[item + 2]
                break
            }
        }
    }

    fun getStats(): FileTemplate {
        parse()
        return fileTemplate
    }

    //    fun getAccessFilePrivileges() {
//        val string = listStats[3].split(" ")
//        fileTemplate.accessPrivileges = string[1]
//    }
//
//    fun getHardLinks() {
//        val string = listStats[2].split(" ")
//        fileTemplate.hardLinks = string[5]
//    }
//
//    fun getUid() {
//        val string = listStats[2].split(" ")
//        fileTemplate.uid = string[4]
//    }
//
//    fun getGid() {
//        val string = listStats[2].split(" ")
//        fileTemplate.gid = string[7]
//    }
//
//    fun getFileSize() {
//        val string = listStats[1].split(" ")
//        fileTemplate.size = string[1].toInt()
//    }
//
//    fun getLastModifyFile() {
//        val string = listStats[5].split(" ")
//        fileTemplate.lastModify = string[1] + " " + string[2]
//    }
//
//    fun getFileName() {
//        val string = listStats[0].split(" ")
//        fileTemplate.fileName = string[1]
//    }
}