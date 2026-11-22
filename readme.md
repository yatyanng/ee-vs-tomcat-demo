### Building & Testing Both WARs

1. **Build both artifacts:**
```bash
mvn clean package

```


* `tomee-ee-demo/target/tomee-ee-demo-1.0.0-SNAPSHOT.war` (~15 KB — thin WAR)
* `tomcat-thirdparty-demo/target/tomcat-thirdparty-demo-1.0.0-SNAPSHOT.war` (~35 MB — fat WAR bundling Weld, Hibernate, Jersey, JAXB, and H2)


2. **Deploy & Verify:**
* Drop `tomee-ee-demo-1.0.0-SNAPSHOT.war` into **Apache TomEE 10** `webapps/`.
* Drop `tomcat-thirdparty-demo-1.0.0-SNAPSHOT.war` into **Apache Tomcat 10.1+** `webapps/`.
* Test both with the exact same `curl` request (adjusting the context path):
```bash
curl -X POST http://localhost:8080/tomee-ee-demo-1.0.0-SNAPSHOT/api/books \
  -H "Content-Type: application/json" \
  -d '{"title": "Effective Java", "isbn": "978-0134685991"}'

```