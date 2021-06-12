# field-populator-oo-design


```
R:58
H:"R"
L:Value:"k1","k2"


L:Value:"k1",[PT]
L:Table: "K1",1022
F:Min non zero:1007,1024
F:Increment:1015
```

```java
public static void main(String[] args) {
		RowTemplate rowTemplate = new RowTemplate(providers());
		RowInstance rowInstance = rowTemplate.set("x", "L:Value:[y],\"xk1\"")
				.set("y", "L:Value:[z],\"yk1\"")
				.set("z", "R:1")
				.set("a", "F:Min non zero:1007,1024")
				.build();
		
		System.out.println(rowInstance);
	}

	private static List<ValueProvider> providers() {
		return Arrays.asList(new HardCodedValueProvider(), new FunctionValueProvider(), new LookupValueProvider(), new RuleValueProvider());
	}
```


```java
RowInstance rowInstance = rowTemplate.set("x", "R:58")
		.set("y", "H:R")
		.set("z", "L:Value:\"k1\",\"k2\"")
		.set("a", "F:Min non zero:1007,1024")
		.build();
```

```java
RowInstance rowInstance = rowTemplate.set("x", "L:Value:\"k1\",[y]")
	   .set("y", "L:Value:\"k1\",[z]")
		.set("z", "H:R")
		.set("a", "F:Min non zero:1007,1024")
		.build();
```