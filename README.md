# Sheet 2 DbUnit

The program converts Excel XLSM files to DbUnit Flat XML format.

## Installation

Create a JAR: lein uberjar

It creates target/uberjar/sheet2dbunit-0.1.0-SNAPSHOT-standalone.jar.

## Usage

```shell
java -jar target/sheet2dbunit--0.1.0-SNAPSHOT-standalone.jar [options] files
```

## Options

```shell
| Short | Long             | Default         | Description                               |
| ----- | ---------------- | --------------- | ----------------------------------------- |
| -p    | --element-prefix |                 | Prefix added to all XML elements but root |
| -r    | --root-tag       | dataset         | XML root tag to use                       |
| -x    | --exclude-sheets |                 | Comma separated list of sheets to exclude |
```

## Examples

1. Convert test-data.xlsm to test-data.xml with default excludes:

```shell
java -jar target/uberjar/sheet2dbunit-0.1.0-SNAPSHOT-standalone.jar test-data.xlsm
```

2. Convert more-data.xlsm to more-data.xml, exclude _sheet1_ and _sheet2_ sheets:

```shell
java -jar target/uberjar/sheet2dbunit-0.1.0-SNAPSHOT-standalone.jar -x sheet1,sheet2 more-data.xlsm
```shell

### Contributing

Pull requests are always welcomed! :-)

### License
MIT
