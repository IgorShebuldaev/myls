package parser

import FileTemplate

class MacOsParserTerminal constructor(private val listStats: ArrayList<String?>): ParserTerminal() {
    private val fileTemplate = FileTemplate()
    private val list: ArrayList<String> = ArrayList()

    override fun parse() {
        for (item in listStats) {
            if (item != null) {
                for (value in item.split(" "))
                    list.add(value)
            }
        }

        fileTemplate.accessPrivileges = list[2]
        fileTemplate.fileName = list.last()
        fileTemplate.uid = list[4]
        fileTemplate.gid = list[5]
        fileTemplate.hardLinks = list[3]
        fileTemplate.size = list[list.size - 4]
        fileTemplate.lastModify = list[16] + " " + list[17] + " " + list[18] + " " + list[19]
    }

    override fun getStats(): FileTemplate {
        parse()

        return fileTemplate
    }
}


