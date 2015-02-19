package com.feye.ScalaYara

import java.nio.file.{Files, Paths}
import java.nio.ByteBuffer
import org.specs2.mutable.Specification
import sna.Library

class YaraTest extends Specification { 
  
  val fake_file = ByteBuffer.wrap(Files.readAllBytes(Paths.get("/usr/bin/lsof")))
  
  "Yara native access" should { 
    
    "Produce code 2 on rule not matching" in { 
      
      val myYara = new Yara("lib/libyara.so")
      val rule = "rule AlwaysTrue { condition: 0x01 == 0x02 }"
  
      myYara.addRule(rule)
  
      val fakeData = Array.fill[Byte](100)(1)
      val output = myYara.scan(fakeData)
      myYara.shutdown
      
      output mustEqual 2
    }
    
    "Produce code 1 on rule matching" in { 
      
      val myYara = new Yara("lib/libyara.so")
      val rule = "rule AlwaysTrue { condition: 0x01 == 0x01 }"
  
      myYara.addRule(rule)
  
      val fakeData = Array.fill[Byte](100)(1)
      val output = myYara.scan(fakeData)
      myYara.shutdown
      
      output mustEqual 1
    }
    
    "Generate the correct mime type" in { 
      val myLibMagic = new LibMagic("/usr/lib/x86_64-linux-gnu/libmagic.so.1")
      val mime = myLibMagic.magicMime(fake_file)
      mime mustEqual "application/x-sharedlib"
    }
    
    "Generate the correct libmagic_type" in { 
      val myLibMagic = new LibMagic("/usr/lib/x86_64-linux-gnu/libmagic.so.1")
      val mtype = myLibMagic.magicType(fake_file)
      mtype mustEqual "ELF 64-bit LSB shared object, x86-64, version 1 (SYSV)"
    }
    
    "Generate the correct encoding" in { 
      val myLibMagic = new LibMagic("/usr/lib/x86_64-linux-gnu/libmagic.so.1")
      val mencoding = myLibMagic.magicEncoding(fake_file)
      mencoding mustEqual "binary"
    }
    
    "Generate the correct md5" in { 
      val h = FileHash.md5(fake_file.array)
      h mustEqual "8bd620061c15e87cce55aec1aa0d7ab6"
    }
    
    "Generate the correct sha1" in { 
      val h = FileHash.sha1(fake_file.array)
      h mustEqual "a09e74f493b075c6febaa4fbeb0a59445f404937"
    }
    
    "Generate the correct sha256" in { 
      val h = FileHash.sha256(fake_file.array)
      h mustEqual "dd8553477e01410b5f8e955603510ee70c48b679bef6a611b135049bb1cd2080"
    }
  }
  
}
