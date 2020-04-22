name := "spark-sftp"

organization := "com.springml"

scalaVersion := "2.12.11"

version := "1.2.0"

libraryDependencies ++= Seq(
  "com.springml" % "sftp.client" % "1.0.3",
  "org.mockito" % "mockito-core" % "3.3.3",
  "com.databricks" %% "spark-xml" % "0.9.0",
  "org.apache.spark" %% "spark-sql" % "3.0.0-preview2" % "provided",
  "org.apache.spark" %% "spark-avro" % "3.0.0-preview2" % "provided" exclude("org.mortbay.jetty", "servlet-api")
)

publishConfiguration := publishConfiguration.value.withOverwrite(true)
publishLocalConfiguration := publishLocalConfiguration.value.withOverwrite(true)

libraryDependencies += "org.scalatest" %% "scalatest" % "3.1.1" % "test"
libraryDependencies += "org.apache.spark" %% "spark-hive" % "3.0.0-preview2" % "test"

publishTo := Some("Artifactory Realm" at "https://artifactory.enliven.systems/artifactory/sbt-dev-local/")

credentials += Credentials(Path.userHome / ".sbt" / ".credentials")