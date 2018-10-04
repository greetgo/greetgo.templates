///MODIFY replace sandbox {PROJECT_NAME}
package kz.greetgo.sandbox.controller.util

import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

object Modules {
  fun parentDir(): File {
///MODIFY replace sandbox {PROJECT_NAME}
    if (File("sandbox.client").isDirectory) {
      return File(".")
    }

///MODIFY replace sandbox {PROJECT_NAME}
    if (File("../sandbox.client").isDirectory) {
      return File("..")
    }

///MODIFY replace sandbox {PROJECT_NAME}
    throw RuntimeException("Cannot find sandbox.root dir")
  }

  private fun findGreetgoProjectNamePath(): Path {
    val markerPath = Paths.get(".greetgo", "project-name.txt")

    val point = Paths.get(".")
    if (Files.exists(point.resolve(markerPath))) {
      return point
    }

    var points = Paths.get("..")
    if (Files.exists(points.resolve(markerPath))) {
      return points
    }

    for (i in 0..6) {
      points = points.resolve("..")

      if (Files.exists(points.resolve(markerPath))) {
        return points
      }
    }

    throw RuntimeException("Cannot find greetgo/project-name.txt" +
      " from " + File(".").absoluteFile.toPath().normalize())
  }

  private fun getDir(moduleName: String): File {

    val modulePath = findGreetgoProjectNamePath().resolve(moduleName)

    if (Files.isDirectory(modulePath)) {
      return modulePath.toFile()
    }

    throw IllegalArgumentException("Cannot find directory " + moduleName
      + " from " + File(".").absoluteFile.toPath().normalize())
  }

  fun clientDir(): File {
///MODIFY replace sandbox {PROJECT_NAME}
    return getDir("sandbox.client")
  }

  fun registerDir(): File {
///MODIFY replace sandbox {PROJECT_NAME}
    return getDir("sandbox.register")
  }

  fun debugDir(): File {
///MODIFY replace sandbox {PROJECT_NAME}
    return getDir("sandbox.server/debug")
  }

  fun controllerDir(): File {
///MODIFY replace sandbox {PROJECT_NAME}
    return getDir("sandbox.controller")
  }
}
