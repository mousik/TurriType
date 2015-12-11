TurriType
========

A small android library which allows you to write text to your TextView gradually. You can easily customise
typing process with fluent API. TurriType uses standard android animation APIs to work seamlessly with rest
of your code.

TurriType is named after [Pellegrino Turri][turri] an Italian who invented one of the first typewrites for his
blind lover Countess Carolina Fantoni da Fivizzano.

Usage
-----

You can just use very simple fluent API to write some text to your TextView.

```java

```

The good thing about this is that `TurriType` returns just standard android animation so you can 
combine/chain with your other animations or do whatever you do with other animations.

```java

```

There are two ways to apply interpolator to your animation. You can define one interpolator for
the whole writing or you can specify a list of "word interpolators" and TurriType will apply random 
interpolator from your list to every word to create a little more human like writing.

```java

```

Or you can just use convenient method `naturally()` to use random word interpolators.

```java

```

Naturally also applies custom `PauseStrategy`. You can also define your own pause strategy `PauseStrategy`
to specify pauses after words and after sentences.

```java

```

Download
--------

```groovy
compile 'com.github.semanticer:turritype:0.0.2'
```





License
-------
    Copyright 2015 Tomáš Valenta
    
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.


 [turri]: https://en.wikipedia.org/wiki/Pellegrino_Turri
