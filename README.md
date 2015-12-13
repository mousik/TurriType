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
TurriType.write(text)
    .into(textView)
    .start();
```

The good thing about this is that `TurriType` returns just standard android animator so you can 
combine/chain with your other animations or do whatever you do with other animator.

```java
Animator animator = TurriType.write(text).into(textView);
Animator otherAnimator = // ... define my awesome fab animation
    
AnimatorSet animatorSet = new AnimatorSet();
animatorSet.playSequentially(animator, otherAnimator);
```

You can also apply standard `Animator.AnimatorListener` 

```java
Animator.AnimatorListener toastListener = new Animator.AnimatorListener() {
    @Override
    public void onAnimationStart(Animator animation) {
        showToast("Start");
    }

    @Override
    public void onAnimationEnd(Animator animation) {
        showToast("End");
    }

    @Override
    public void onAnimationCancel(Animator animation) {
        showToast("Cancel");
    }

    @Override
    public void onAnimationRepeat(Animator animation) {
        showToast("Repeat");
    }
};

TurriType.write(text)
    .withListener(toastListener)
    .into(textView)
    .start();
```

There are two ways to apply interpolator to your animation. You can define one interpolator for
the whole writing or you can specify a list of "word interpolators" and TurriType will apply random 
interpolator from your list to every word in order to create a little more human like writing.

```java
// Single interpolator
TurriType.write(text)
    .withInterpolator(new LinearOutSlowInInterpolator())
    .into(textView)
    .start();
    
// Random set of interpolators for every word
List <TimeInterpolator> interpolatorList = new ArrayList<>();
interpolatorList.add(new LinearInterpolator());
interpolatorList.add(new FastOutSlowInInterpolator());
interpolatorList.add(new AccelerateDecelerateInterpolator());
        
TurriType.write(text)
    .withWordInterpolatorList(interpolatorList)
    .into(textView)
    .start();
```

Or you can just use convenience method `naturally()` to use random word interpolators.

```java
TurriType.write(text)
    .naturally()
    .into(textView)
    .start();
```

Naturally also applies custom `PauseStrategy` called `NaturalPauseStrategy`. 
You can also define your own implementation of `PauseStrategy` to specify pauses after words and after sentences.

```java
PauseStrategy pauseStrategy = new PauseStrategy() {
    @Override
    public long getPauseAfterWord(String word, long millsPerChar) {
        // I want to wait longer after long words
        return word.length() * millsPerChar; 
    }

    @Override
    public long getPauseAfterSentence(long millsPerChar) {
        return 100; // same pause after every sentence
    }
};

TurriType.write(text)
        .setPauseStrategy(pauseStrategy)
        .into(textView)
        .start();
        
// if you want just the same pause after word and some other pause after sentence
// use LinearPauseStrategy

TurriType.write(text)
    .setPauseStrategy(new LinearPauseStrategy(100, 200))
    .into(textView)
    .start();


```

Download
--------

```groovy
compile 'com.github.semanticer:turritype:0.0.3'
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
