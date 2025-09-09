package data.mongo.database




import com.mongodb.kotlin.client.coroutine.MongoClient
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import data.util.MongoDBConstants

object MongoDbFactory {

    fun create(): MongoDatabase {
        val stringConnection = System.getenv(MongoDBConstants.MONGO_DB_URL_VARIABLE)

        return MongoClient
            .create(stringConnection)
            .getDatabase(MongoDBConstants.DATABASE_NAME)

    }

}