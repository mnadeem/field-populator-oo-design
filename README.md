# field-populator-oo-design


```
R:58,2,10 
H:"R"
L:Value:"k1","k2"


L:Value:"k1",[PT]
L:Table: "K1",1022
F:Min non zero:1007,1024
F:Increment:1015
```


```java
		RowInstance rowInstance = rowTemplate.set("x", "R:58,2,10")
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