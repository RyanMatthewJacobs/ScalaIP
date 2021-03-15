import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.udf
val df = spark.read.json("/Users/prateeknarayanan/Documents/CSE115B/Databricks-115/ipFile.json")
df.write.saveAsTable("addresses")

//val addressTable = spark.sql("SELECT * FROM addresses")
//spark.time(addressTable.show())

val query1 = spark.sql("SELECT * FROM addresses WHERE IPAddress = \"192.0.2.1\" ")
spark.time(query1.show())

val query2 = spark.sql("SELECT * FROM addresses WHERE IPAddress LIKE \"192.0.2.%\"")
spark.time(query2.show())

val query3 = spark.sql("SELECT * FROM addresses WHERE IPAddress RLIKE('^192\\.0\\.[0-3]\\.[0-9]+$')")
spark.time(query3.show())

val query4 = spark.sql("SELECT * FROM addresses WHERE IPAddress RLIKE('^192\\.0\\.([0-9]|[0-9][0-9]|1[0-1][0-9]|12[0-7])\\.[0-9]+$')")
spark.time(query4.show())
//df.show()
//addressTable.show()
