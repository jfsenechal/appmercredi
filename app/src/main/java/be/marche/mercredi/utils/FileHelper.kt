package be.marche.mercredi.utils

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.FileNotFoundException
import java.io.InputStream

class FileHelper(val context: Context) {


    fun getRealPathFromURI(contentURI: Uri): String {
        val result: String
        val cursor = context.getContentResolver().query(contentURI, null, null, null, null)
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath()
        } else {
            cursor!!.moveToFirst()
            val idx = cursor!!.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
            result = cursor!!.getString(idx)
            cursor!!.close()
        }
        return result
    }

    fun uploadToServer(filePath: String) {
        // val retrofit = NetworkClient.getRetrofitClient(this)
        //  val uploadAPIs = retrofit.create(UploadAPIs::class.java!!)
        //Create a file object using file path
        val file = File(filePath)
        // Create a request body with file and image media type
        val fileReqBody = RequestBody.create(MediaType.parse("image/*"), file)
        // Create MultipartBody.Part using file request-body,file name and part name
        val part = MultipartBody.Part.createFormData("upload", file.name, fileReqBody)
        //Create request body with text description and text media type
        val description = RequestBody.create(MediaType.parse("text/plain"), "image-type")
        //

        //mercrediViewModel.uploadImage2(part, description)
    }

    fun createPart(filePath: String): MultipartBody.Part {
        val mimeType = "jpg"//FileUtil.getMimeType(filePath)
        val mediaType = MediaType.parse(mimeType ?: filePath)
        val file = File(filePath)
        val requestBody: RequestBody = RequestBody.create(mediaType, file)
        val data = MultipartBody.Part.createFormData("file", file.name, requestBody)

        return data
    }

    fun getInputStreamForVirtualFile(uri: Uri, mimeTypeFilter: String): InputStream? {

        val openableMimeTypes: Array<String>? = context.contentResolver?.getStreamTypes(uri, mimeTypeFilter)

        return if (openableMimeTypes?.isNotEmpty() == true) {
            context.contentResolver?.openTypedAssetFileDescriptor(uri, openableMimeTypes[0], null)?.createInputStream()
        } else {
            throw FileNotFoundException()
        }
    }

    fun postServer(contentURI: Uri) {

        val MEDIA_TYPE_IMAGE: MediaType = MediaType.parse("image/*")!!
        val file = File(contentURI.path)

        val requestBody: RequestBody = RequestBody.create(MEDIA_TYPE_IMAGE, file)
        val part: MultipartBody.Part = MultipartBody.Part.createFormData("image", file.getName(), requestBody)

        //mercrediViewModel.uploadImage(enfant, requestBody)
    }

}