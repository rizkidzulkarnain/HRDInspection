package id.co.ultrajaya.hrdinspection.clsumum

class Config {
    companion object {
        var myUser : Cuser ?=null;

        var UrlDefault = "http://b2b.ultrajaya.co.id/ws_middle3/service.asmx"
        var Url = "http://b2b.ultrajaya.co.id/ws_middle3/service.asmx"
        var Url5 = "http://b2b.ultrajaya.co.id/ws_middle5/service.asmx"
        var ServerLog = "1000SAFELOG91"
        var ServerOracleQA = "QA3300ORC"

        val AM: Char = 254.toChar()
        val VM: Char = 253.toChar()
        val VN: Char = 250.toChar()

        val serverDB = "1000PRODUKSI"

        //hanya default, modifier static tandanya bisa di ubah di tempat manapun
        var VERSION = "1.0"
    }
}