package com.feye.ScalaYara

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
    
  }
  
}
