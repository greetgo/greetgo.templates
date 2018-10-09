///MODIFY replace sandbox {PROJECT_NAME}
package kz.greetgo.sandbox.debug.util

import java.util.ArrayList

object PageUtils {
  fun <T> cutPage(list: MutableList<T>, offset: Int, pageSize: Int): Boolean {

    val sizeAtTheBeginning = list.size

    val newList = ArrayList<T>()
    var i = 0
    val c = list.size
    while (i < c && newList.size < pageSize) {
      if (i >= offset) newList.add(list[i])
      i++
    }

    list.clear()
    list.addAll(newList)


    return offset + pageSize < sizeAtTheBeginning
  }
}
