# Organisation Flapdoodle OSS

We are now a github organisation. You are invited to participate.

# de.flapdoodle.wicket 

custom wicket extensions which might be useful

## Why?

- my experiences are different from those of other - and these extensions are build with my experiences
- may be this is usefull for others, may be not at all

## Howto

### Maven

#### Java7 Version

Stable (Maven Central Repository, Released: 09.10.2015 - wait 24hrs for maven central)

	<dependency>
		<groupId>de.flapdoodle.wicket7</groupId>
		<artifactId>de.flapdoodle.wicket</artifactId>
		<version>7.0.1</version>
	</dependency>

Snapshots (Repository http://oss.sonatype.org/content/repositories/snapshots)

	<dependency>
		<groupId>de.flapdoodle.wicket7</groupId>
		<artifactId>de.flapdoodle.wicket</artifactId>
		<version>7.0.2-SNAPSHOT</version>
	</dependency>

#### Java8 Version

Stable (Maven Central Repository, Released: 08.04.2016 - wait 24hrs for maven central)

	<dependency>
		<groupId>de.flapdoodle.wicket7-java8</groupId>
		<artifactId>de.flapdoodle.wicket</artifactId>
		<version>7.0.3</version>
	</dependency>

Snapshots (Repository http://oss.sonatype.org/content/repositories/snapshots)

	<dependency>
		<groupId>de.flapdoodle.wicket7-java8</groupId>
		<artifactId>de.flapdoodle.wicket</artifactId>
		<version>7.0.4-SNAPSHOT</version>
	</dependency>

### Whats in it?

There are currently one module - models, which provides some model stuff. You must not include this wicket
library at whole. Use can use only parts. You can use the model stuff with this dependency declaration replacing
__/\*moduleIdGoesHere\*/__ with __models__ and the right version (i think you get the pattern):

	<dependency>
		<groupId>de.flapdoodle.wicket6</groupId>
		<artifactId>de.flapdoodle.wicket--/*moduleIdGoesHere*/</artifactId>
		<version>/*see above for the right one here*/</version>
	</dependency>

	<dependency>
		<groupId>de.flapdoodle.wicket6</groupId>
		<artifactId>de.flapdoodle.wicket--models</artifactId>
		<version>/*see above for the right one here*/</version>
	</dependency>

### Changelog

#### 7.0.4 (SNAPSHOT)


#### 7.0.3

- detach delegation added

#### 7.0.2

- java8 version started

#### 7.0.0

- init release based on 6.13.3
- works with wicket 7.0.0


### Usage

Any model used as source model for a model transformation are detached if the transformating model ist detached. Any transformation evaluation is only done
once as in LoadableDetachedModel.

#### simple model transformation

	public IModel<Integer> createSumModel(IModel<List<Integer>> source) {
		return Models.on(source).apply(new Function1<Integer, List<Integer>>() {
			@Override
			public Integer apply(List<Integer> values) {
				int sum=0;
				for (Integer v : values!=null ? values : new ArrayList<Integer>()) {
					if (v!=null) sum=sum+v;
				}
				return sum;
			}
		});
	}

#### complex model transformation setup

	public <T> IModel<List<T>> subListModel(IModel<List<T>> source,IModel<Integer> offsetModel, IModel<Integer> sizeModel) {
		IModel<List<T>> emptyIfNull = Models.emptyIfNull(source);
		
		return Models.on(emptyIfNull,offsetModel,sizeModel).apply(new Function3<List<T>, List<T>, Integer, Integer>() {
			@Override
			public List<T> apply(List<T> list, Integer offset, Integer size) {
				int startIdx=Math.min(list.size(), offset);
				int lastIdx=Math.min(list.size(),offset+size);
				return list.subList(startIdx, lastIdx);
			}
		});
	}
	
#### unmodifiable and read only

A model is read only if setObject can not be used. But if you can change the content of the
model value, the model is not unmodifiable. Its not the best idea to change a model value so there
are some functions to prevent this. 
		
	List<Integer> source=new ArrayList<Integer>(Arrays.asList(1,2,3));
	IModel<List<Integer>> unmodifiableListModel = Models.unmodifiable(source);
	
	IModel<List<? extends Integer>> modifiableListModel = Model.ofList(Arrays.asList(1,2,3));
	IModel<List<Integer>> asUnmodifiableListModel = Models.unmodifiable(modifiableListModel);
	IModel<List<? extends Integer>> readOnlyListModel = Models.readOnly(modifiableListModel);

