# field-populator-oo-design


## Features

* Easy to Use
* Light weight and fast
* Extensible
* Supports inter dependency between columns
* Detects Cyclic dependencies

## Format of fields


| Format | Description | Example |
| --- | --- | --- |
| `R:id` | Should invoke Rule Provider | R:58 |
| `H:"Hard coded value in double quotes"` | Should invoke hard coded Provider | H:"R" |
| `F:Increment:"1"` | Should invoke Function Provider | F:Increment:"1" |
| `L:Value:"k1","k2"` | Should invoke Lookup Provider | L:Value:"k1","k2" |

Anything in double quotes is treated as is. Example `F:Increment:"1"`

Anything without double quote should be invoked with Rule provider. For example `F:Increment:1` should be treated as `F:Increment:<Result of R:1>`

Anything between `[` and `]` should be treated as reference to existing field. For example `L:Value:"k1",[AnotherfieldName]` here k1 is treated as is since it with in double quotes, and  `[AnotherfieldName]` is reference field. `L:Value:"k1",[AnotherfieldName]` is equivaltent to `L:Value:"k1", <Value of field/column AnotherfieldName>`


Some examples 

```
R:58
H:"R"
L:Value:"k1","k2"


L:Value:"k1",[PT]
L:Table: "K1",1022
F:Min non zero:1007,1024
F:Increment:1015
```

Refer test cases for more details.


```java
public static void main(String[] args) {
		RowTemplate rowTemplate = new RowTemplate(providers());
		RowInstance rowInstance = rowTemplate.field("x", "L:Value:[y],\"xk1\"")
				.field("y", "L:Value:[z],\"yk1\"")
				.field("z", "R:1")
				.field("a", "F:Min non zero:1007,1024")
				.build();
		
		System.out.println(rowInstance);
	}

	private static List<ValueProvider> providers() {
		return Arrays.asList(new HardCodedValueProvider(), new FunctionValueProvider(), new LookupValueProvider(), new RuleValueProvider());
	}
```


```java
RowInstance rowInstance = rowTemplate.field("x", "R:58")
		.field("y", "H:R")
		.field("z", "L:Value:\"k1\",\"k2\"")
		.field("a", "F:Min non zero:1007,1024")
		.build();
```

```java
RowInstance rowInstance = rowTemplate.field("x", "L:Value:\"k1\",[y]")
	   .field("y", "L:Value:\"k1\",[z]")
		.field("z", "H:R")
		.field("a", "F:Min non zero:1007,1024")
		.build();
```