package util

import java.io.File
import java.io.IOException
import javax.swing.JFileChooser

class SaveUtil {

    private val relativePathFile = "save.txt"
    private val currentPath = File("").absolutePath


    fun checkFile(){

        val newFilePath = "$currentPath/$relativePathFile"
        val newFile = File(newFilePath)

        if(!newFile.exists()){
            newFile.createNewFile()
        }
    }

    fun readFile():List<String>{
        val filepath = "$currentPath/$relativePathFile"

        val file = File(filepath)

        val list = mutableListOf<String>()
        try {
            val reader = file.bufferedReader()
            reader.useLines { lines->
                for (line in lines){
                    list.add(line)
                }
            }
            reader.close()
        } catch (e: IOException) {
            println(e.printStackTrace())
        }

        return list
    }

    fun addFile(filepath:String){
        val filePath = "$currentPath/$relativePathFile"

        val file = File(filePath)

        try {
            val lines = file.readLines().toMutableList()

            val newLine = filepath // 新的内容

            // 追加新行到列表末尾
            lines.add(newLine)

            // 将更新后的内容写回文件
            file.writeText(lines.joinToString("\n"))
        }catch (e: IOException){
            println(e.printStackTrace())
        }
    }

    fun removeFile(index:Int){
        val filepath = "$currentPath/$relativePathFile"
        val file = File(filepath)
        try {
            val lines = file.readLines().toMutableList()
            lines.removeAt(index)
            file.writeText(lines.joinToString("\n"))
        }catch (e:IOException){
            println(e.printStackTrace())
        }
    }

    fun getPath():String{
        val fileChooser = JFileChooser()
        val result = fileChooser.showOpenDialog(null)
        if (result == JFileChooser.APPROVE_OPTION) {
            val selectedFile = fileChooser.selectedFile
            val filePath = selectedFile.absolutePath
            println("Selected file path: $filePath")
            return filePath
        }
        return ""
    }

    companion object{
        val saveUtil = SaveUtil()
    }
}