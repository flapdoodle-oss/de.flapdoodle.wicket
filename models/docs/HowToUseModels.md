# Flapdoodle Wicket Models

Any model used as source model for a model transformation are detached if the transformating model ist detached. Any transformation evaluation is only done
once as in LoadableDetachedModel.
           
```java
IModel<List<Integer>> listModel = Model.ofList(List.of(1, 3, 7));
IReadOnlyModel<Integer> sumModel = Models.on(listModel)
  .apply(list -> list.stream().mapToInt(Integer::intValue).sum());

assertThat(sumModel.getObject()).isEqualTo(11);
```
           
You can aggregate multiple models into one:

```java
IModel<List<String>> source=Model.ofList(List.of("A", "B", "C", "D", "E"));
IModel<Integer> offsetModel=Model.of(1);
IModel<Integer> sizeModel=Model.of(2);

IModel<List<String>> emptyIfNull = Models.emptyIfNull(source);

IReadOnlyModel<List<String>> testee = Models.on(emptyIfNull, offsetModel, sizeModel)
  .apply((List<String> list, Integer offset, Integer size) -> {
    int startIdx = Math.min(list.size(), offset);
    int lastIdx = Math.min(list.size(), offset + size);
    return list.subList(startIdx, lastIdx);
  });

assertThat(testee.getObject()).containsExactlyInAnyOrder("B", "C");
```

A model is read only if setObject can not be used. But if you can change the content of the
model value, the model is not unmodifiable. Its not the best idea to change a model value so there
are some functions to prevent this.

```java
List<Integer> source=new ArrayList<Integer>(Arrays.asList(1,2,3));
IModel<? extends List<? extends Integer>> unmodifiableListModel = Models.unmodifiable(source);

IModel<List<Integer>> modifiableListModel = Model.ofList(Arrays.asList(1, 2, 3));
IModel<List<Integer>> asUnmodifiableListModel = Models.unmodifiable(modifiableListModel);
IModel<List<Integer>> readOnlyListModel = Models.readOnly(modifiableListModel);
```