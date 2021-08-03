package parser

import FileTemplate

class LinuxParserTerminal(private val listStats: ArrayList<String?>): ParserTerminal() {
    private val fileTemplate = FileTemplate()
    private val list: ArrayList<String> = ArrayList()

    override fun parse() {
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
                fileTemplate.size = list[item + 1]
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

    override fun getStats(): FileTemplate {
        parse()

        return fileTemplate
    }
}
