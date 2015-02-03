package com.feye.ScalaYara

import sna.Library
import com.sun.jna.ptr.PointerByReference
import com.sun.jna.ptr.IntByReference
import com.sun.jna.Callback

class Yara(libraryLocation : String) { 
  
  val cLib = Library(libraryLocation)
  cLib.yr_initialize()[Int]
  val compiler = new PointerByReference
  val rules = new PointerByReference
  cLib.yr_compiler_create(compiler)[Int]
  
  //Exception Handling to avoid crazy seg faults
  case class YaraException(ex : String) extends Exception
  
  //Callback that is called for each data input
  object ScanCallback extends Callback {
    def invoke(message : Int, message_data : PointerByReference, user_data : IntByReference) : Int = { 
      user_data.setValue(message)
      return message
    }
  }
  
  def addRule(rule : String, namespace : String = null) : Unit = { 
    val errors = cLib.yr_compiler_add_string(compiler.getValue, rule, namespace)[Int]
    if (errors > 0) throw YaraException(s"$errors found when compiling rules")
    cLib.yr_compiler_get_rules(compiler.getValue, rules)[Int]
  }
  
  def scan(data : Array[Byte]) : Int = { 
    val isSuccess = new IntByReference() 
    cLib.yr_rules_scan_mem(rules.getValue, data, data.length, 0, ScanCallback, isSuccess, 5)[Int]
    isSuccess.getValue
  }
  
  
  def shutdownThread = { 
    cLib.yr_finalize_thread()[Int]
  }
  
  //Required since we are accessing C (No GC!)
  def shutdown = { 
    cLib.yr_rules_destroy(rules.getValue)
    cLib.yr_compiler_destroy(compiler.getValue)
    cLib.yr_finalize()[Int]
  }
  
}
