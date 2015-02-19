package com.feye.ScalaYara

import sna.Library
import com.sun.jna.Pointer
import java.nio.ByteBuffer

class LibMagic(libraryLocation : String) { 
  
  private val MAGIC_TYPE = 0x000000
  private val MAGIC_MIME = 0x000010
  private val MAGIC_ENCODING = 0x000400
  
  val cLib = Library(libraryLocation)
  val magic_mime_cookie = cLib.magic_open(MAGIC_MIME)[Pointer]
  val magic_type_cookie = cLib.magic_open(MAGIC_TYPE)[Pointer]
  val magic_encoding_cookie = cLib.magic_open(MAGIC_ENCODING)[Pointer]
  
  cLib.magic_load(magic_mime_cookie, null)[Int]
  cLib.magic_load(magic_type_cookie, null)[Int]
  cLib.magic_load(magic_encoding_cookie, null)[Int]
  
  def magicMime(binary : ByteBuffer) : String = {
    cLib.magic_buffer(magic_mime_cookie, binary, binary.remaining)[String]
  }
  
  def magicType(binary : ByteBuffer) : String = { 
    cLib.magic_buffer(magic_type_cookie, binary, binary.remaining)[String]
  }
  
  def magicEncoding(binary : ByteBuffer) : String = { 
    cLib.magic_buffer(magic_encoding_cookie, binary, binary.remaining)[String]
  }
  
  def close = {
    cLib.magic_close(magic_mime_cookie)[Unit]
    cLib.magic_close(magic_type_cookie)[Unit]
  }
  
}
