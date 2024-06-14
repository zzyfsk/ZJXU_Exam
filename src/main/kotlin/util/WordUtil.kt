package util

import org.apache.poi.hwpf.HWPFDocument
import org.apache.poi.hwpf.extractor.WordExtractor
import org.apache.poi.xwpf.extractor.XWPFWordExtractor
import org.apache.poi.xwpf.usermodel.XWPFDocument
import java.io.File
import java.io.FileInputStream

class WordUtil {

    fun readWord(location:String):String{
        if (location.endsWith(".doc")||location.endsWith(".wps")){
            val file = File(location)
            val doc = HWPFDocument(file.inputStream())
            val extractor = WordExtractor(doc)

            val content = extractor.text

            doc.close()
            extractor.close()
            return content
        } else{
            val file = FileInputStream(location)
            val xdoc = XWPFDocument(file)
            val extractor = XWPFWordExtractor(xdoc)
            val content = extractor.text
            file.close()
            return content
        }

    }

    companion object{
        val wordUtil = WordUtil()
    }
}