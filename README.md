<h4>ScalaYara</h4>

<p>ScalaYara is a Scala wrapper for a number of malware research tools:</p>

<p><a href="https://plusvic.github.io/yara/">Yara malware research tool</a>. It enables you to use Yara from within your Scala programs. The code leverages <a href="https://code.google.com/p/scala-native-access/">Scala Native Access</a> in order to make direct calls to the Yara C API.</p>

<p>Simple, concise md5, sha1, and sha256 hashing.</p>

<p>A UUID5 implementation.</p>

<p>A unix file Libmagic port -- again using SNA.</p>

<h4>Dependencies</h4>

<p>Java Native Access is the only jar dependency. Additionally, you will need to make sure you have a proper libyara.so file that works for your particular computer architecture. You can then store this library in your /lib folder. Details on installing libyara.so can be found <a href="http://yara.readthedocs.org/en/latest/gettingstarted.html">here</a>.</p>

<h4>Getting Started</h4>

<p>The simplest way to get started is to ensure you have a proper libyara.so file, and then typing sbt test to run the test cases.</p>

<h4>RoadMap</h4>

<p>In the future I plan to do a little bit of clean-up for this project. However, there are no major updates planned, just minor bug fixes here and there.</p>