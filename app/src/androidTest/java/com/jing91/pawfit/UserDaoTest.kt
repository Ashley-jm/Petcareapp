package com.jing91.pawfit

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jing91.pawfit.database.AppDatabase
import com.jing91.pawfit.database.User
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UserDaoTest {

    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun register_and_login_user_success() = runBlocking {
        val user = User(
            email = "ashley@example.com",
            password = "1234",
            username = "Ashley",
            petName = "Coco"
        )


        db.userDao().register(user)


        val result = db.userDao().login("ashley@example.com", "1234")

        Assert.assertNotNull(result)
        Assert.assertEquals("Ashley", result?.username)
        Assert.assertEquals("Coco", result?.petName)
    }

    @Test
    fun login_fails_with_wrong_credentials() = runBlocking {
        val user = User(
            email = "ashley@example.com",
            password = "1234",
            username = "Ashley",
            petName = "Coco"
        )

        db.userDao().register(user)

        val result = db.userDao().login("ashley@example.com", "wrong_password")

        Assert.assertNull(result)
    }
}
