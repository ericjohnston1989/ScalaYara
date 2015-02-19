package com.feye.ScalaYara

import java.nio.file.{Files, Paths}
import java.nio.ByteBuffer
import org.specs2.mutable.Specification
import sna.Library

class YaraTest extends Specification { 
  
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
      val fake_file = Files.readAllBytes(Paths.get("/usr/bin/lsof"))
      val myLibMagic = new LibMagic("/usr/lib/x86_64-linux-gnu/libmagic.so.1")
      val mime = myLibMagic.magicMime(ByteBuffer.wrap(fake_file))
      mime mustEqual "application/x-sharedlib"
    }
    
    "Generate the correct libmagic_type" in { 
      val fake_file = Files.readAllBytes(Paths.get("/usr/bin/lsof"))
      val myLibMagic = new LibMagic("/usr/lib/x86_64-linux-gnu/libmagic.so.1")
      val mtype = myLibMagic.magicType(ByteBuffer.wrap(fake_file))
      mtype mustEqual "ELF 64-bit LSB shared object, x86-64, version 1 (SYSV)"
    }
    
    "Generate the correct encoding" in { 
      val fake_file = Files.readAllBytes(Paths.get("/usr/bin/lsof"))
      val myLibMagic = new LibMagic("/usr/lib/x86_64-linux-gnu/libmagic.so.1")
      val mencoding = myLibMagic.magicEncoding(ByteBuffer.wrap(fake_file))
      mencoding mustEqual "binary"
    }
  }
  
}
