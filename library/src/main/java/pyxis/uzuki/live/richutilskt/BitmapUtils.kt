package pyxis.uzuki.live.richutilskt

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.Log
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.util.*

/**
 * Created by pyxis on 2017. 5. 31..
 */


fun Context.saveBitmapToFileName(bitmap: Bitmap): String? {
    try {
        val file = saveBitmapToFile(this, bitmap)
        return file?.absolutePath
    } catch (e: Exception) {
    }

    return null
}

fun saveBitmapToFile(context: Context, bitmap: Bitmap): File? {
    try {
        val photo = getOutputMediaFile(context)
        val out = FileOutputStream(photo)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
        out.close()

        return photo
    } catch (exception: FileNotFoundException) {
        Log.e("FileNotFoundException", exception.message)
    } catch (exception: IOException) {
        Log.e("IOException", exception.message)
    }

    return null
}

fun getOutputMediaFile(context: Context): File {
    val picName = UUID.randomUUID().toString().replace("-".toRegex(), "") + ".jpg"

    val folder = context.getExternalFilesDir(null)
    if (!folder.isDirectory) {
        folder.mkdirs()
    }

    return File(folder, picName)
}

fun drawableToBitmap(drawable: Drawable): Bitmap {
    if (drawable is BitmapDrawable) {
        return drawable.bitmap
    }

    var width = drawable.intrinsicWidth
    width = if (width > 0) width else 1
    var height = drawable.intrinsicHeight
    height = if (height > 0) height else 1

    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    drawable.setBounds(0, 0, canvas.width, canvas.height)
    drawable.draw(canvas)

    return bitmap
}