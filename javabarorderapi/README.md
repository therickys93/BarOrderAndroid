# Java BarOrder API

[![Build Status](https://travis-ci.org/therickys93/javabarorderapi.svg?branch=master)](https://travis-ci.org/therickys93/javabarorderapi)

Questa libreria è stata scritta in supporto al server [BarOrder](https://github.com/therickys93/barorder.git).

## installazione con Gradle

aggiungere repository jitpack nel file root build.gradle

```
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```

aggiungere nel file del progetto per esempio: ( controllare l'ultima versione nelle release )

```
dependencies {
        compile 'com.github.therickys93:javabarorderapi:1.1.0'
}

```
