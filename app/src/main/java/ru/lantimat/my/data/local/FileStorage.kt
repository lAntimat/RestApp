package ru.lantimat.my.data.local

import ru.cometrica.arbook.util.sinkAll
import timber.log.Timber
import java.io.*


open class FileStorage(
    private val storageFolder: File
) {

    fun addFile(
        source: InputStream,
        destinationFile: String,
        destinationFolder: String = DEFAULT_FOLDER
    ): File {
        try {
            val destination = makeFilePath(destinationFolder, destinationFile)
            Timber.d(
                "saveFile file: %s/%s destination: %s",
                destinationFolder, destinationFile, destination
            )
            if (destination.exists()) {
                destination.delete()
            }
            destination.parentFile?.mkdirs()
            destination.sinkAll(source)
            return destination
        } catch (e: IOException) {
            Timber.e(
                e, "Can't save file to: %s/%s",
                destinationFolder, destinationFile
            )
            throw e
        }
    }

    fun find(fileName: String, folderName: String = DEFAULT_FOLDER): File? {
        val file = makeFilePath(folderName, fileName)
        return if (file.exists()) {
            file
        } else {
            null
        }
    }

    fun open(fileName: String, folderName: String = DEFAULT_FOLDER): InputStream? =
        find(fileName, folderName)?.let { file ->
            BufferedInputStream(FileInputStream(file))
        }

    fun deleteFolder(folderName: String) {
        try {
            makeFolderPath(folderName).deleteRecursively()
        } catch (e: Exception) {
            Timber.e(e, "Can't delete folder: $folderName.")
            throw e
        }
    }

    fun deleteFile(fileName: String, folderName: String = DEFAULT_FOLDER): Boolean {
        val file = makeFilePath(folderName, fileName)
        return if (file.delete()) {
            Timber.d("File: %s deleted.", file)
            true
        } else {
            Timber.w("Can't delete file: %s", file)
            false
        }
    }

    fun clear() {
        try {
            storageFolder.deleteRecursively()
        } catch (e: Exception) {
            Timber.e(e, "Can't clear storage: $storageFolder.")
            throw e
        }
    }

    fun makeFilePath(folderName: String, filename: String) =
        File(makeFolderPath(folderName), filename)
            .apply {
                parentFile?.mkdirs()
            }

    fun makeFolderPath(folderName: String) =
        File(storageFolder, folderName)
            .apply {
                mkdirs()
            }

    companion object {

        private const val DEFAULT_FOLDER = ""
    }
}
