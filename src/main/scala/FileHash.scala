package com.feye.ScalaYara

import java.nio.ByteBuffer
import java.security.MessageDigest
import javax.xml.bind.annotation.adapters.HexBinaryAdapter

object FileHash { 
  
  val hexConverter = new HexBinaryAdapter()
  
  def hash(in : Array[Byte], hashType : String) : String = { 
    val md = MessageDigest.getInstance(hashType);
    hexConverter.marshal(md.digest(in)).toLowerCase
  }
  
  def md5(in : Array[Byte]) : String = { 
    hash(in, "MD5")
  }
  
  def sha1(in : Array[Byte]) : String = { 
    hash(in, "SHA-1")
  }
  
  def sha256(in : Array[Byte]) : String = { 
    hash(in, "SHA-256")
  }
  
}
