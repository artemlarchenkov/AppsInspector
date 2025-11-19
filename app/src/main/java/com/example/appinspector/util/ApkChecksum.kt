import java.io.File
import java.security.MessageDigest

object ApkChecksum {

    fun calculateSHA1(path: String?): String {
        val file = File(path)
        val digest = MessageDigest.getInstance("SHA-1")

        file.inputStream().use { fis ->
            val buffer = ByteArray(8192)
            var read: Int

            while (fis.read(buffer).also { read = it } != -1) {
                digest.update(buffer, 0, read)
            }
        }

        return digest.digest().joinToString("") { "%02x".format(it) }
    }
}
