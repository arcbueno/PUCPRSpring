package br.pucpr.authserver.users

data class ExternalImageResult(val byteArray: ByteArray?, val error: String?) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ExternalImageResult

        if (byteArray != null) {
            if (other.byteArray == null) return false
            if (!byteArray.contentEquals(other.byteArray)) return false
        } else if (other.byteArray != null) return false
        if (error != other.error) return false

        return true
    }

    override fun hashCode(): Int {
        var result = byteArray?.contentHashCode() ?: 0
        result = 31 * result + (error?.hashCode() ?: 0)
        return result
    }
}