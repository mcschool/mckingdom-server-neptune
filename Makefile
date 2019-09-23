# コンパイルしてjarを配置
c:
	mvn install

compile:
	mvn install && cp target/neptune-1.0-SNAPSHOT.jar spigot/plugins/neptune-1.0-SNAPSHOT.jar