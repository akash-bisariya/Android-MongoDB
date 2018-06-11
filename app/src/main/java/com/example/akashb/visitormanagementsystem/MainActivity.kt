package com.example.akashb.visitormanagementsystem

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.mongodb.MongoClient
import com.mongodb.ServerAddress
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.experimental.CoroutineDispatcher
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import org.bson.Document
import java.util.ArrayList

class MainActivity : AppCompatActivity(),View.OnClickListener {
    private lateinit var mongoClient:MongoClient
    private lateinit var databaseName:MongoDatabase
    private lateinit var collection:MongoCollection<Document>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_submit.setOnClickListener(this)
        val lstServer = ArrayList<ServerAddress>()
        lstServer.add(ServerAddress("10.111.44.16", 27017))
        mongoClient = MongoClient(lstServer)
        databaseName = mongoClient.getDatabase("VMS")
        collection = databaseName.getCollection("Visitor")

    }

    override fun onClick(v: View?) {
        if(v!!.id==R.id.btn_submit) {
            launch {
                var count = collection.count()
                val doc = Document("name", et_name.text.toString())
                        .append("address", et_address.text.toString()).append("purpose", et_purpose.text.toString())
                collection.insertOne(doc)
                delay(1000)
                count++
                launch(UI) {
                    Toast.makeText(this@MainActivity,"Record Saved",Toast.LENGTH_SHORT).show()
                }

            }

        }
    }
}
