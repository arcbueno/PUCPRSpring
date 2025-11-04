package br.pucpr.authserver.users

import org.springframework.mock.web.MockMultipartFile
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.multipart.MultipartFile
import java.security.MessageDigest

@Service
class AvatarHelperService(
    private val restTemplate: RestTemplate = RestTemplate()
) {
    fun fetchAvatarImage(email: String, userName: String): MultipartFile? {
        val gravatarResult = fetchGravatarImages(email)
        if (gravatarResult.byteArray != null) {
            return byteArrayToMultipartFile(gravatarResult.byteArray)
        }
        val uiAvatarResult = fetchUIAvatar(userName)
        if (uiAvatarResult.byteArray != null) {
            return byteArrayToMultipartFile(uiAvatarResult.byteArray)
        }
        return null;
    }

    private fun byteArrayToMultipartFile(byteArray: ByteArray): MultipartFile {
        return MockMultipartFile("avatar.png", "avatar.png", "image/png", byteArray)
    }

    private fun fetchGravatarImages(email: String): ExternalImageResult {
        val hashedEmail = generateEmailHash(email)
        val url = "https://gravatar.com/avatar/${hashedEmail}?d=404"
        try{
            val result = restTemplate.getForObject<ByteArray>(url, ByteArray::class.java)
            if(result != null) {
                return ExternalImageResult(result, null);
            }
            return ExternalImageResult(null, "Failed to fetch Gravatar images");
        }catch (e: Exception){
            return ExternalImageResult(null, "Failed to fetch Gravatar images");
        }
    }

    private fun fetchUIAvatar(userName: String): ExternalImageResult {
        val url = "https://ui-avatars.com/api/?name=${userName.replace(" ", "+")}&background=random"
        try{
            val result = restTemplate.getForObject<ByteArray>(url, ByteArray::class.java)
            if(result != null) {
                return ExternalImageResult(result, null);
            }
            return ExternalImageResult(null, "Failed to fetch UI Avatar image");
        }catch (e: Exception){
            return ExternalImageResult(null, "Failed to fetch UI Avatar image");
        }
    }

    private fun generateEmailHash(email: String): String {
        val bytes = MessageDigest.getInstance("SHA-256").digest(email.toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }
}