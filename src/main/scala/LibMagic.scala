package com.feye.ScalaYara

import sna.Library
import com.sun.jna.ptr.PointerByReference

import com.sun.jna.Pointer

//import com.sun.jna.Structure.ByReference

import com.sun.jna.ptr.ByReference

class LibMagic(libraryLocation : String) { 
  
  private val MAGIC_TYPE = 0
  private val MAGIC_MIME = 16
  
  val cLib = Library(libraryLocation)
  val magic_mime_cookie = cLib.magic_open(MAGIC_MIME)[Pointer]
  val magic_type_cookie = cLib.magic_open(MAGIC_TYPE)[Pointer]
  
  cLib.magic_load(magic_mime_cookie, null)[Int]
  cLib.magic_load(magic_type_cookie, null)[Int]
  
  def magicMime(binary : Array[Byte]) : String = { 
    cLib.magic_buffer(magic_mime_cookie, binary, binary.length)[String]
  }
  
  def magicType(binary : Array[Byte]) : String = { 
    cLib.magic_buffer(magic_type_cookie, binary, binary.length)[String]
  }
  
  def close = { 
    cLib.magic_close(magic_mime_cookie)[Unit]
    cLib.magic_close(magic_type_cookie)[Unit]
  }
  
}
