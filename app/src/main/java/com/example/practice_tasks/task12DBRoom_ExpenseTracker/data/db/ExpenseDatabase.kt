package com.example.practice_tasks.task12DBRoom_ExpenseTracker.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.practice_tasks.task12DBRoom_ExpenseTracker.data.dao.ExpenseDao
import com.example.practice_tasks.task12DBRoom_ExpenseTracker.data.entity.Expense
import com.example.practice_tasks.task12DBRoom_ExpenseTracker.data.entity.PaymentMethod
import com.example.practice_tasks.task12DBRoom_ExpenseTracker.data.entity.category
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [Expense::class, category::class, PaymentMethod::class],
    views = [ExpenseSummaryView::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class ExpenseDatabase : RoomDatabase() {

    abstract fun expenseDao(): ExpenseDao

    companion object {
        @Volatile
        private var INSTANCE: ExpenseDatabase? = null

        fun getDatabase(context: Context): ExpenseDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    ExpenseDatabase::class.java,
                    "expense_db"
                )
                    // ✅ ATTACH CALLBACK HERE
                    .addCallback(DatabaseCallback())
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }

    // ✅ FIXED NAME
    private class DatabaseCallback : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            CoroutineScope(Dispatchers.IO).launch {
                INSTANCE?.let { database ->
                    prepopulate(database)
                }
            }
        }

        // Added onOpen to populate if empty (e.g. valid DB file but no data)
        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            CoroutineScope(Dispatchers.IO).launch {
                INSTANCE?.let { database ->
                    val categories = database.expenseDao().getAllCategories()
                    val paymentMethods = database.expenseDao().getAllPaymentMethods()
                    
                    if (categories.isEmpty() || paymentMethods.isEmpty()) {
                        prepopulate(database)
                    }
                }
            }
        }

        private suspend fun prepopulate(db: ExpenseDatabase) {
            val dao = db.expenseDao()

            val categories = listOf(
                category(name = "Food"),
                category(name = "Transportation"),
                category(name = "Entertainment"),
                category(name = "Health"),
                category(name = "Education"),
                category(name = "Utilities"),
                category(name = "Shopping"),
                category(name = "Bills"),
                category(name = "Other")
            )

            val paymentMethods = listOf(
                PaymentMethod(type = "Cash"),
                PaymentMethod(type = "UPI"),
                PaymentMethod(type = "Card")
            )

            categories.forEach { dao.insertCategory(it) }
            paymentMethods.forEach { dao.insertPaymentMethod(it) }
        }
    }
}
