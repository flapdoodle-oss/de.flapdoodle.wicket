# Organisation Flapdoodle OSS

We are now a github organisation. You are invited to participate.

# de.flapdoodle.wicket 

custom wicket extensions which might be useful

## Why?

- my experiences are different from those of other - and these extensions are build with my experiences
- may be this is usefull for others, may be not at all

## Howto

### Maven

Stable (Maven Central Repository, Released: 30.09.2012 - wait 24hrs for maven central)

	<dependency>
		<groupId>de.flapdoodle.wicket</groupId>
		<artifactId>de.flapdoodle.wicket</artifactId>
		<version>1.5.0</version>
	</dependency>

Snapshots (Repository http://oss.sonatype.org/content/repositories/snapshots)

	<dependency>
		<groupId>de.flapdoodle.wicket</groupId>
		<artifactId>de.flapdoodle.wicket</artifactId>
		<version>1.5.1-SNAPSHOT</version>
	</dependency>

### Whats in it?

There are currently one module - models, which provides some model stuff. You must not include this wicket
library at whole. Use can use only parts. You can use the model stuff with this dependency declaration replacing
__/\*moduleIdGoesHere\*/__ with __models__ and the right version (i think you get the pattern):

	<dependency>
		<groupId>de.flapdoodle.wicket</groupId>
		<artifactId>de.flapdoodle.wicket--/*moduleIdGoesHere*/</artifactId>
		<version>__/*see above for the right one here*/__</version>
	</dependency>

	<dependency>
		<groupId>de.flapdoodle.wicket</groupId>
		<artifactId>de.flapdoodle.wicket--models</artifactId>
		<version>/*see above for the right one here*/</version>
	</dependency>


