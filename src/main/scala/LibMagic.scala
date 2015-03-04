package com.feye.ScalaYara

import sna.Library
import com.sun.jna.Pointer
import java.nio.ByteBuffer

class LibMagic(libraryLocation : String) { 
  
  private val MAGIC_TYPE = 0x000000
  private val MAGIC_MIME = 0x000010
  private val MAGIC_ENCODING = 0x000400
  
  val cLib = Library(libraryLocation)
  
  //Individual magic cookies are made every time for thread safety
  def magicMime(binary : ByteBuffer) : String = {
    val magic_mime_cookie = cLib.magic_open(MAGIC_MIME)[Pointer]
    cLib.magic_load(magic_mime_cookie, null)[Int]
    val ret = cLib.magic_buffer(magic_mime_cookie, binary, binary.remaining)[String]
    cLib.magic_close(magic_mime_cookie)[Unit]
    ret
  }
  
  def magicType(binary : ByteBuffer) : String = { 
    val magic_type_cookie = cLib.magic_open(MAGIC_TYPE)[Pointer]
    cLib.magic_load(magic_type_cookie, null)[Int]
    val ret = cLib.magic_buffer(magic_type_cookie, binary, binary.remaining)[String]
    cLib.magic_close(magic_type_cookie)[Unit]
    ret
  }
  
  def magicEncoding(binary : ByteBuffer) : String = { 
    val magic_encoding_cookie = cLib.magic_open(MAGIC_ENCODING)[Pointer]
    cLib.magic_load(magic_encoding_cookie, null)[Int]
    val ret = cLib.magic_buffer(magic_encoding_cookie, binary, binary.remaining)[String]
    cLib.magic_close(magic_encoding_cookie)[Unit]
    ret
  }
}
