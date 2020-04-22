# Spark SFTP Connector Library

A library for constructing Dataframes by downloading files from SFTP and writing Dataframe to a SFTP server

## Requirements

This library requires Spark 3.

## Linking
You can link against this library in your program at the following ways:

### Maven Dependency
```
<dependency>
	<groupId>com.springml</groupId>
	<artifactId>spark-sftp_2.12</artifactId>
	<version>1.2.0</version>
</dependency>

```

### SBT Dependency
```
libraryDependencies += "com.springml" % "spark-sftp_2.12" % "1.2.0"
```

## Features
This package can be used to construct spark Dataframe by downloading the files from SFTP server.

This package can also be used to write spark Dataframe as a csv|json|acro tp SFTP server

This library requires following options:
* `path`: FTP URL of the file to be used for Dataframe construction
* `username`: SFTP Server Username. 
* `password`: (Optional) SFTP Server Password. 
* `pem`: (Optional) Location of PEM file. Either pem or password has to be specified
* `pemPassphrase`: (Optional) Passphrase for PEM file.
* `host`: SFTP Host.
* `port`: (Optional) Port in which SFTP server is running. Default value 22.
* `fileType`: Type of the file. Supported types are csv, txt, json, avro and parquet
* `inferSchema`: (Optional) InferSchema from the file content. Currently applicable only for csv fileType
* `header`: (Optional) Applicable only for csv fileType. Is the first row in CSV file is header. 
* `delimiter`: (Optional) Set the field delimiter. Applicable only for csv fileType. Default is comma.
* `quote`: (Optional) Set the quote character. Applicable only for csv fileType. Default is ".
* `escape`: (Optional) Set the escape character. Applicable only for csv fileType. Default is \.
* `multiLine`: (Optional) Set the multiline. Applicable only for csv fileType. Default is false.
* `codec`: (Optional) Applicable only for csv fileType. Compression codec to use when saving to file. Should be the fully qualified name of a class implementing org.apache.hadoop.io.compress.CompressionCodec or one of case-insensitive shorten names (bzip2, gzip, lz4, and snappy). Defaults to no compression when a codec is not specified.

### Scala API
```scala

// Construct Spark Dataframe using file in FTP server
val df = spark.read.
            format("com.springml.spark.sftp").
            option("host", "SFTP_HOST").
            option("username", "SFTP_USER").
            option("password", "****").
            option("fileType", "csv").
            option("delimiter", ";").
            option("quote", "\"").
            option("escape", "\\").
            option("multiLine", "true").
            option("inferSchema", "true").
            load("/ftp/files/sample.csv")

// Write Dataframe as CSV file to FTP server
df.write.
      format("com.springml.spark.sftp").
      option("host", "SFTP_HOST").
      option("username", "SFTP_USER").
      option("password", "****").
      option("fileType", "csv").
      option("delimiter", ";").
      option("codec", "bzip2").
      save("/ftp/files/sample.csv")


// Construct spark Dataframe using text file in FTP server
 val df = spark.read.
            format("com.springml.spark.sftp").
            option("host", "SFTP_HOST").
            option("username", "SFTP_USER").
            option("password", "****").
            option("fileType", "txt").
            load("config")
            
 // Construct spark Dataframe using xml file in FTP server           
            val df = spark.read.
                 format("com.springml.spark.sftp").
                 option("host", "SFTP_HOST").
                 option("username", "SFTP_USER").
                 option("password", "*****").
                 option("fileType", "xml").
                 option("rowTag", "YEAR").load("myxml.xml")
                 
 // Write Dataframe as XML file to FTP server           
           
                 df.write.format("com.springml.spark.sftp").
                 option("host", "SFTP_HOST").
                 option("username", "SFTP_USER").
                 option("password", "*****").
                 option("fileType", "xml").
                 option("rootTag", "YTD").
                 option("rowTag", "YEAR").save("myxmlOut.xml.gz")

```


### Java API
```java
// Construct Spark Dataframe using file in FTP server
DataFrame df = spark.read().
					format("com.springml.spark.sftp").
				    option("host", "SFTP_HOST").
				    option("username", "SFTP_USER").
				    option("password", "****").
				    option("fileType", "json").
				    load("/ftp/files/sample.json")

// Write Dataframe as CSV file to FTP server
df.write().
      format("com.springml.spark.sftp").
      option("host", "SFTP_HOST").
      option("username", "SFTP_USER").
      option("password", "****").
      option("fileType", "json").
      save("/ftp/files/sample.json");
```

### R API
Spark 1.5+:
```r

if (nchar(Sys.getenv("SPARK_HOME")) < 1) {
  Sys.setenv(SPARK_HOME = "/home/spark")
}
library(SparkR, lib.loc = c(file.path(Sys.getenv("SPARK_HOME"), "R", "lib")))
sparkR.session(master = "local[*]", sparkConfig = list(spark.driver.memory = "2g"))

# Construct Spark Dataframe using Avro file in FTP server
df <- read.df(path="/ftp/files/sample.avro",
            source="com.springml.spark.sftp",
            host="SFTP_HOST",
            username="SFTP_USER",
            pem="/home/user/mypem.pem",
            fileType="avro")

# Write Dataframe as Avro file to FTP server
write.df(df,
        path="/ftp/files/sample.avro",
        source="com.springml.spark.sftp",
        host="SFTP_HOST",
        username="SFTP_USER",
        pem="/home/user/mypem.pem",
        fileType="avro")
```

### Note
1. SFTP files are fetched and written using [jsch](http://www.jcraft.com/jsch/). It will be executed as a single process
2. Files from SFTP server will be downloaded to temp location and it will be deleted only during spark shutdown

## Building From Source
This library is built with [SBT](http://www.scala-sbt.org/0.13/docs/Command-Line-Reference.html), which is automatically downloaded by the included shell script. To build a JAR file simply run `build/sbt package` from the project root.
