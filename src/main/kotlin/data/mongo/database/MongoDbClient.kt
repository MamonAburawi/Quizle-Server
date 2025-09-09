package data.mongo.database




import com.mongodb.kotlin.client.coroutine.MongoClient
import common.constant.MongoDBConstants

object MongoDbClient {

    fun create(stringConnection: String) =
        MongoClient
            .create(stringConnection)
            .getDatabase(MongoDBConstants.DATABASE_NAME)

}