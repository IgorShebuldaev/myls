package parser

import FileTemplate

abstract class ParserTerminal {
    protected abstract fun parse()
    abstract fun getStats(): FileTemplate
}