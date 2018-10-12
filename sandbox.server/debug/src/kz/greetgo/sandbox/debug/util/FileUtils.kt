///MODIFY replace sandbox {PROJECT_NAME}
package kz.greetgo.sandbox.debug.util

import java.io.File

object FileUtils {
  fun isParent(parentDir: File, childFile: File): Boolean {

    val parentUri = parentDir.toURI()

    val childUri = childFile.toURI()

    return !parentUri.relativize(childUri).isAbsolute
  }
}
