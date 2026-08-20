# Flapdoodle Wicket Models

Any model used as source model for a model transformation are detached if the transformating model ist detached. Any transformation evaluation is only done
once as in LoadableDetachedModel.
           
```java
${sumModel}
```
           
You can aggregate multiple models into one:

```java
${subList}
```

A model is read only if setObject can not be used. But if you can change the content of the
model value, the model is not unmodifiable. Its not the best idea to change a model value so there
are some functions to prevent this.

```java
${unmodifiable}
```
