# Bloom-filter Kata05

Reference <a href=http://codekata.com/kata/kata05-bloom-filters/ >Kata05</a>

Bloom filters are a 30-year-old statistical way of testing for membership in a set. They greatly reduce the amount of 
storage you need to represent the set, but at a price: they’ll sometimes report that something is in the set when it 
isn’t (but it’ll never do the opposite; if the filter says that the set doesn’t contain your object, you know that it 
doesn’t)

## Sections

- [Features of the boom filter](#features-of-the-bloom-filter)
- [Clone Project](#clone-project)
- [Run Tests](#run-tests)
- [Build](#build)
- [Generste Java Doc](#generate-java-doc)
- [Summary](#summary)

### Features of the bloom filter

- Supports to change Bit array size.
```
//bit array size is 64
BloomFilter<String> bloomFilter = new BloomFilter(64, hashFunctions);

//bit array size is "Integer.MAX_VALUE - 8"
BloomFilter<String> bloomFilter = new BloomFilter(Integer.MAX_VALUE - 8, hashFunctions);
```
- Supports to have multiple hash algorithm evaluations

```
List<ToIntFunction<String>> hashFunctions =  new ArrayList<>();

// adding multiple hash functions
hashFunctions.add(Object::hashCode);
hashFunctions.add((x -> HashFunction.getDigestHashFunction(x, MD5)));
hashFunctions.add((x -> HashFunction.getDigestHashFunction(x, SHA256)));
hashFunctions.add((x -> HashFunction.getDigestHashFunction(x, SHA512)));

BloomFilter<String> bloomFilter = new BloomFilter(Integer.MAX_VALUE - 8, hashFunctions);
```
- Support multiple types as an extended feature of the bloom filter.

    <P>This feature allows to use this bloom filter for different use cases as it's supported for multiple types. </P>

```
BloomFilter<String> bloomFilter = new BloomFilter(Integer.MAX_VALUE - 8, hashFunctions);
BloomFilter<Integer> bloomFilter = new BloomFilter(Integer.MAX_VALUE - 8, hashFunctions);
```
### Clone Project
```
git clone https://github.com/senalw/bloom-filter-kata05
```

### Run Tests

- Go to project directory 
```
cd bloom-filter-kata05
```

- Run tests only
```
mvn clean test
```

- Run tests and generate reports in `${project.basedir}/target/site/surefire-report.html`
```
mvn clean test site
```

### Build
Code can be built using below maven command and then can be used as an external library.

Note: .jar file will be stored in `${project.basedir}/target/bloom-filter-kata05-*.jar`

```
mvn clean package
```

### Generate Java Doc
Java doc can be generated to use as an external library.

Below command will generate java doc and stored in `${project.basedir}/target/site/apidocs/index.html`
```
 mvn javadoc:javadoc
```

### Summary
<p> 
This bloom filter implementation is done using open-jdk 17 and uses md5, sha256 and sha512 hashing algorithms
to evaluate words in the dictionary and put them in the right place of the bit array.
</p>
<P>
Additionally this bloom filter allows to have multiple hash functions and also to change the size of the bit array
according to user preference.
</P>